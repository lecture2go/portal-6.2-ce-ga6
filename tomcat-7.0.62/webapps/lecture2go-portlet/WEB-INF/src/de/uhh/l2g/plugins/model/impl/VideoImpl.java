/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.uhh.l2g.plugins.model.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.persistence.ProducerPersistenceImpl;
import de.uhh.l2g.plugins.util.ProzessManager;

/**
 * The extended model implementation for the Video service. Represents a row in the &quot;LG_Video&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.model.Video} interface.
 * </p>
 *
 * @author Iavor Sturm
 */
public class VideoImpl extends VideoBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a video model instance should use the {@link de.uhh.l2g.plugins.model.Video} interface instead.
	 */
	
	/**
	 * This model uses quite a few constants, some may be better kept in a config file...
	 */
	private static final String WEBROOT 				= PropsUtil.get("lecture2go.web.root");
	private static final String WEBHOME 				= WEBROOT.contains("localhost") ? WEBROOT+"/web/vod" : WEBROOT;
	private static final String MEDIA_REPOSITORY		= PropsUtil.get("lecture2go.media.repository");
	private static final String IMAGES_REPOSITORY		= PropsUtil.get("lecture2go.images.system.path") + "/";
	private static final String DOWNLOAD_SERVER			= PropsUtil.get("lecture2go.downloadserver.web.root");
	private static final String IMAGES_URL 				= WEBROOT + "/images/";
	private static final String CHAPTERFOLDER 			= PropsUtil.get("lecture2go.chapters.web.root");
	private static final String CAPTIONSFOLDER 			= PropsUtil.get("lecture2go.captions.web.root");
	private static final String DOWNLOADFOLDER 			= DOWNLOAD_SERVER + "/abo/";
	private static final String VIDEOREPFOLDER 			= DOWNLOAD_SERVER + "/videorep/";

	private static final String VIDEO_URL_BASE 			= WEBHOME + "/l2go/-/get/v/";
	private static final String LECTURESERIES_URL_BASE 	= WEBHOME + "/l2go/-/get/l/";
	private static final String RSS_URL_BASE		 	= WEBROOT + "/rss/";
	private static final String DOWNLOAD_SERVLET_BASE 	= PropsUtil.get("lecture2go.downloadserver.web.root") + "/servlet-file-download/getFile?downloadAllowed=";
	
	private static final String IMAGE_SUFFIX 			= ".jpg";
	private static final String IMAGE_MEDIUM_SUFFIX 	= "_m.jpg";
	private static final String IMAGE_SMALL_SUFFIX 		= "_s.jpg";
	private static final String IMAGE_NO_MEDIA	 		= "/lecture2go-portlet/img/nomedia.png";
	private static final String IMAGE_NO_MEDIA_MEDIUM 	= "/lecture2go-portlet/img/nomedia.png";
	private static final String IMAGE_NO_MEDIA_SMALL 	= "/lecture2go-portlet/img/nomedia.png";
	private static final String IMAGE_AUDIO 			= "/lecture2go-portlet/img/audio_only_big.png";
	private static final String IMAGE_AUDIO_MEDIUM 		= "/lecture2go-portlet/img/audio_only_medium.png";
	private static final String IMAGE_AUDIO_SMALL 		= "/lecture2go-portlet/img/audio_only_small.png";
	private static final String IMAGE_NO_IMAGE 			= "/lecture2go-portlet/img/noimage.png";
	private static final String IMAGE_NO_IMAGE_MEDIUM 	= "/lecture2go-portlet/img/noimage.png";
	private static final String IMAGE_NO_IMAGE_SMALL 	= "/lecture2go-portlet/img/noimage.png";
	
	private static final String FILE_SUFFIX_MP4		= ".mp4";
	private static final String FILE_SUFFIX_MP3 	= ".mp3";
	private static final String FILE_SUFFIX_M4V 	= ".m4v";
	private static final String FILE_SUFFIX_PDF		= ".pdf";
	private static final String FILE_SUFFIX_M4A 	= ".m4a";
	private static final String FILE_SUFFIX_FLV		= ".flv";
	private static final String FILE_SUFFIX_OGG 	= ".ogg";
	private static final String FILE_SUFFIX_WEBM 	= ".webm";
	private static final String FILE_SUFFIX_VTT 	= ".vtt";
	private static final String FILE_SUFFIX_XML 	= ".xml";
	
	private static final int EMBED_WIDTH	= 647;
	private static final int EMBED_HEIGHT	= 373;

	private static final String HTML5_EMBED_VIDEO_START 	= "<video width='" + EMBED_WIDTH + "' height='" + EMBED_HEIGHT + "' controls><source src='";
	private static final String HTML5_EMBED_VIDEO_END		= "' type='video/mp4'>Your browser does not support the video tag.</video>"; 
	private static final String HTML5_EMBED_AUDIO_START 	= "<audio controls><source src='";
	private static final String HTML5_EMBED_AUDIO_END		= "' type='audio/mpeg'>Your browser does not support the audio element.</audio>";  

	private String shortTitle;
	
	private String lectureseriesName;
	private String lectureseriesNumber;
	private String lectureseriesUrl;
	
	private String creatorFullName;
	private String linkedCreators;
	private String creators;
	
	private Host host;
	private Producer producer;
	
	private Integer accessPermitted = 0;
	
	private String embedCommsy;
	private String embedIframe;
	private String embedHtml5;
	
	private ArrayList<String> playerUris;
	private JSONArray jsonPlayerUris;
	private JSONArray jsonPlayerTracks;
	
	private String vttChapterFile;
	private String vttCaptionUrl;
	private String vttThumbsFilde;
	
	private boolean hasComments = false;
	
	private String url;
	private String secureUrl;
	
	private String image;
	private String imageSmall;
	private String imageMedium;

	private String bitrate;	
	
	private String date;
	private String simpleDate;
	
	private File mp4File;
	private File pdfFile;
	private File mp3File;
	private File m4vFile;
	private File m4aFile;
	private File flvFile;
	private File oggFile;
	private File webmFile;
	private File vttFile;

	
	private String mp4DownloadLink;
	private String pdfDownloadLink;
	private String mp3DownloadLink;
	private String m4vDownloadLink;
	private String m4aDownloadLink;
	private String flvDownloadLink;
	private String oggDownloadLink;
	private String webmDownloadLink;
	
	private String mp4RssLink;
	private String mp3RssLink;
	private String m4vRssLink;
	private String m4aRssLink;
	private String oggRssLink;
	private String flvRssLink;
	private String webmRssLink;
	
	
	public VideoImpl() {
		
	}
	
	public Host getHost() {
		if (host == null) {
			try {
				return HostLocalServiceUtil.getHost(getHostId());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return host;
	}
	
	public Producer getProducer() {
		if (producer == null) {
			try {
				return ProducerLocalServiceUtil.getById(getProducerId());
			} catch (SystemException e) {
				e.printStackTrace();
				return null;
			}
		}
		return producer;
	}
	
	/**
	 * Returns the current file prefix depending on openaccess field
	 */
	public String getCurrentPrefix() {
		return getOpenAccess()==1 ? getPreffix() : getSPreffix();
	}
	
	/**
	 * Returns the current file name depending on openaccess field
	 */
	public String getCurrentFilename() {
		return getOpenAccess()==1 ? getFilename() : getSecureFilename();
	}
	
	public String getLectureseriesName() {
		return lectureseriesName;
	}

	public void setLectureseriesName(String lectureseriesName) {
		this.lectureseriesName = lectureseriesName;
	}

	public String getLectureseriesNumber() {
		return lectureseriesNumber;
	}

	public void setLectureseriesNumber(String lectureseriesNumber) {
		this.lectureseriesNumber = lectureseriesNumber;
	}

	public String getCreatorFullName() {
		return creatorFullName;
	}

	public void setCreatorFullName(String creatorFullName) {
		this.creatorFullName = creatorFullName;
	}
	
	/**
	 * Returns the complete URL to the lectureseries of this video
	 */
	public String getLectureseriesUrl() {
		if (lectureseriesUrl == null) {
			if (getLectureseriesId()>0) 
				lectureseriesUrl = LECTURESERIES_URL_BASE + getLectureseriesId();
			else 
				lectureseriesUrl = "";
		}
		return lectureseriesUrl;
	}
	
	public void setLectureseriesUrl(String lectureseriesUrl) {
		this.lectureseriesUrl = lectureseriesUrl;
	}

	public Integer getAccessPermitted() {
		return accessPermitted;
	}

	public void setAccessPermitted(Integer accessPermitted) {
		this.accessPermitted = accessPermitted;
	}

	/**
	 * Returns and sets the URIs for the video player as a JSONAray
	 */
	public JSONArray getJsonPlayerUris() {
		if (jsonPlayerUris == null) {
			VideoLocalServiceUtil.addPlayerUris2Video(this);
		}
		return jsonPlayerUris;
	}

	public void setJsonPlayerUris(JSONArray jsonPlayerUris) {
		this.jsonPlayerUris = jsonPlayerUris;
	}

	public ArrayList<String> getPlayerUris() {
		return playerUris;
	}

	public void setPlayerUris(ArrayList<String> playerUris) {
		this.playerUris = playerUris;
	}
	
	/**
	 * Returns and sets the URIs for the video player as a JSONAray
	 */
	public JSONArray getJsonPlayerTracks() {
		if (jsonPlayerTracks == null) {
			VideoLocalServiceUtil.addTextTracks2Video(this);
		}
		return jsonPlayerTracks;
	}

	public void setJsonPlayerTracks(JSONArray jsonPlayerTracks) {
		this.jsonPlayerTracks = jsonPlayerTracks;
	}
	
	
	/**
	 * Returns the complete embed code for commsy depending on the openaccess field
	 */
	public String getEmbedCommsy() {
		if (embedCommsy == null) {
			if(getOpenAccess()==1){
				return getEmbedCommsyCode(String.valueOf(getVideoId()));
			}else{
				return getEmbedCommsyCode(getSPreffix());
			}
		}
		return embedCommsy;
	}
	
	public void setEmbedCommsy(String embedCommsy) {
		this.embedCommsy = embedCommsy;
	}
	
	/**
	 * Returns the complete iframe embed code (usable for openaccess and non-openaccess videos)
	 */
	public String getEmbedIframe() {
		if (embedIframe == null) {
			String vId = "0";
			if(this.getOpenAccess()==1)vId = getVideoId()+"";
			else vId = getSPreffix();
			embedIframe = "<iframe src='"+WEBROOT+"/lecture2go-portlet/player/iframe/?v="+vId+"' frameborder='0' width='"+EMBED_WIDTH+"' height='"+EMBED_HEIGHT+"' allowfullscreen></iframe>";
		}
		return embedIframe;
	}
	
	public void setEmbedIframe(String embedIframe) {
		this.embedIframe = embedIframe;
	}

	/**
	 * Returns the complete html5 embed code depending on the openaccess field and container format
	 */
	public String getEmbedHtml5() {
		if (embedHtml5 == null) {
			if(getDownloadLink()==1 && !getContainerFormat().isEmpty()){
				if(getContainerFormat().equals("mp4"))
					embedHtml5 = getVideoEmbedHtml5();
				else 
					embedHtml5 = getAudioEmbedHtml5();
			}
		}
		return embedHtml5;
	}
	
	public void setEmbedHtml5(String embedHtml5) {
		this.embedHtml5 = embedHtml5;
	}
	
	/**
	 * Returns the path to the vtt-chapter-file
	 */
	public String getVttChapterFile() {
		if (vttChapterFile == null) {
			vttChapterFile = CHAPTERFOLDER + "/" + getVideoId() + FILE_SUFFIX_VTT;
		}
		return vttChapterFile;
	}
	
	public void setVttChapterFile(String vttChapterFile) {
		this.vttChapterFile = vttChapterFile;
	}
	
	/**
	 * Returns the url to the vtt-caption-file
	 */
	public String getVttCaptionUrl() {
		if (vttCaptionUrl == null) {
			vttCaptionUrl = CAPTIONSFOLDER + "/" + getCurrentPrefix() + FILE_SUFFIX_VTT;
		}
		return vttCaptionUrl;
	}
	
	public void setVttCaptionUrl(String vttCaptionUrl) {
		this.vttCaptionUrl = vttCaptionUrl;
	}

	public String getVttThumbsFilde() {
		return vttThumbsFilde;
	}

	public void setVttThumbsFilde(String vttThumbsFilde) {
		this.vttThumbsFilde = vttThumbsFilde;
	}
	
	public String getMp4RssLink() {
		if (mp4RssLink == null) {
			mp4RssLink = getFileRssLink(FILE_SUFFIX_MP4);
		}
		return mp4RssLink;
	}
	
	public void setMp4RssLink(String mp4RssLink) {
		this.mp4RssLink = mp4RssLink;
	}

	public String getMp3RssLink() {
		if (mp3RssLink == null) {
			mp3RssLink = getFileRssLink(FILE_SUFFIX_MP3);
		}
		return mp3RssLink;
	}
	
	public void setMp3RssLink(String mp3RssLink) {
		this.mp3RssLink = mp3RssLink;
	}

	public String getM4vRssLink() {
		if (m4vRssLink == null) {
			m4vRssLink = getFileRssLink(FILE_SUFFIX_M4V);
		}
		return m4vRssLink;
	}
	
	public void setM4vRssLink(String m4vRssLink) {
		this.m4vRssLink = m4vRssLink;
	}

	public String getM4aRssLink() {
		if (m4aRssLink == null) {
			m4aRssLink = getFileRssLink(FILE_SUFFIX_M4A);
		}
		return m4aRssLink;
	}
	
	public void setM4aRssLink(String m4aRssLink) {
		this.m4aRssLink = m4aRssLink;
	}

	public String getOggRssLink() {
		if (oggRssLink == null) {
			oggRssLink = getFileRssLink(FILE_SUFFIX_OGG);
		}
		return oggRssLink;
	}
	
	public void setOggRssLink(String oggRssLink) {
		this.oggRssLink = oggRssLink;
	}

	public String getFlvRssLink() {
		if (flvRssLink == null) {
			flvRssLink = getFileRssLink(FILE_SUFFIX_FLV);
		}
		return flvRssLink;
	}
	
	public void setFlvRssLink(String flvRssLink) {
		this.flvRssLink = flvRssLink;
	}

	public String getWebmRssLink() {
		if (webmRssLink == null) {
			webmRssLink = getFileRssLink(FILE_SUFFIX_WEBM);
		}
		return webmRssLink;
	}
	
	public void setWebmRssLink(String webmRssLink) {
		this.webmRssLink = webmRssLink;
	}
	
	/**
	 * Checks if file has chapters
	 */
	public boolean isHasChapters() {
		boolean hasChapters;
		try {
			hasChapters = SegmentLocalServiceUtil.getSegmentsByVideoId(getVideoId()).size()>0;
		} catch (Exception e) {
			e.printStackTrace();
			hasChapters = false;
		}

		return hasChapters;
	}

	public boolean isHasComments() {
		return hasComments;
	}

	public void setHasComments(boolean hasComments) {
		this.hasComments = hasComments;
	}
	
	/**
	 * Returns the complete URL to the open access video
	 */
	public String getUrl() {
		if (url == null) {
			url = VIDEO_URL_BASE + getVideoId();
		}
		return url; 
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the complete secure URL to the NON open access video
	 */
	public String getSecureUrl() {
		if (secureUrl == null) {
			secureUrl = getOpenAccess()==1 ? "" : VIDEO_URL_BASE + getSPreffix();
		}
		return secureUrl; 
	}
	
	public void setSecureUrl(String secureUrl) {
		this.secureUrl = secureUrl;
	}
	
	/**
	 * Returns the current URL depending on the openaccess field
	 */
	public String getCurrentURL() {
		return getOpenAccess()==1 ? getUrl() : getSecureUrl();
	}

	/**
	 * Returns the mp4 file (unchecked if existing)
	 */
	public File getMp4File() {
		if (mp4File == null) {
			mp4File = getFile(FILE_SUFFIX_MP4);
		}
		return mp4File;
	}
	
	public void setMp4File(File mp4File) {
		this.mp4File = mp4File;
	}

	/**
	 * Returns the pdf file (unchecked if existing)
	 */
	public File getPdfFile() {
		if (pdfFile == null) {
			pdfFile = getFile(FILE_SUFFIX_PDF);
		}
		return pdfFile;
	}
	
	public void setPdfFile(File pdfFile) {
		this.pdfFile = pdfFile;
	}

	/**
	 * Returns the mp3 file (unchecked if existing)
	 */
	public File getMp3File() {
		if (mp3File == null) {
			mp3File = getFile(FILE_SUFFIX_MP3);
		}
		return mp3File;
	}
	
	public void setMp3File(File mp3File) {
		this.mp3File = mp3File;
	}

	/**
	 * Returns the m4v file (unchecked if existing)
	 */
	public File getM4vFile() {
		if (m4vFile == null) {
			m4vFile = getFile(FILE_SUFFIX_M4V);
		}
		return m4vFile;
	}
	
	public void setM4vFile(File m4vFile) {
		this.m4vFile = m4vFile;
	}

	/**
	 * Returns the m4a file (unchecked if existing)
	 */
	public File getM4aFile() {
		if (m4aFile == null) {
			m4aFile = getFile(FILE_SUFFIX_M4A);
		}
		return m4aFile;
	}
	
	public void setM4aFile(File m4aFile) {
		this.m4aFile = m4aFile;
	}
	
	/**
	 * Returns the flv file (unchecked if existing)
	 */
	public File getFlvFile() {
		if (flvFile == null) {
			flvFile = getFile(FILE_SUFFIX_FLV);
		}
		return flvFile;
	}
	
	public void setFlvFile(File flvFile) {
		this.flvFile = flvFile;
	}

	/**
	 * Returns the ogg file (unchecked if existing)
	 */
	public File getOggFile() {
		if (oggFile == null) {
			oggFile = getFile(FILE_SUFFIX_OGG);
		}
		return oggFile;
	}
	
	public void setOggFile(File oggFile) {
		this.oggFile = oggFile;
	}

	/**
	 * Returns the webm file (unchecked if existing)
	 */
	public File getWebmFile() {
		if (webmFile == null) {
			webmFile = getFile(FILE_SUFFIX_WEBM);
		}
		return webmFile; 
	}
	
	public void setWebmFile(File webmFile) {
		this.webmFile = webmFile;
	}
	
	/**
	 * Returns the vtt file (unchecked if existing) from the user repository
	 */
	public File getVttFile() {
		if (vttFile == null) {
			vttFile = getFile(FILE_SUFFIX_VTT);
		}
		return vttFile;
	}
	
	public void setVttFile(File vttFile) {
		this.vttFile = vttFile;
	}
	
	/**
	 * Returns the current filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are 
	 * referenced from a _smil_ file, the version with the download suffix.
	 * Checks if prefix is _openaccess_ or secure and returns the correct one.
	 */
	public String getCurrentMp4FilenameWithReasonableBitrate() {
		return this.getCurrentPrefix() + getDownloadSuffix() + FILE_SUFFIX_MP4;
	}
	
	/**
	 * Returns the secure filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are 
	 * referenced from a _smil_ file, the version with the download suffix.
	 */
	public String getSecureMp4FilenameWithReasonableBitrate() {
		return this.getSPreffix() + getDownloadSuffix() + FILE_SUFFIX_MP4;
	}
	
	/**
	 * Returns the _openaccess_ filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are 
	 * referenced from a _smil_ file, the version with the download suffix.
	 */
	public String getMp4FilenameWithReasonableBitrate() {
		return this.getPreffix()+ getDownloadSuffix() + FILE_SUFFIX_MP4;
	}
	
	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getMp4DownloadLink() {
		if (mp4DownloadLink == null) {
			if (getOpenAccess() == 1) {
				mp4DownloadLink = getOpenAccessFileDownloadLink(FILE_SUFFIX_MP4);
			} else {
				// The link to the downloadable secure mp4 can vary, if there is a smil file for adaptive streaming, 
				// the video with the download suffix is used (private method getDownloadSuffix checks this)
				mp4DownloadLink = getSecureFileDownloadLink(getDownloadSuffix() + FILE_SUFFIX_MP4);
			}
		}
		return mp4DownloadLink;
	}
	
	public void setMp4DownloadLink(String mp4DownloadLink) {
		this.mp4DownloadLink = mp4DownloadLink;
	}

	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getPdfDownloadLink() {
		if (pdfDownloadLink == null) {
			pdfDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_PDF);
		}
		return pdfDownloadLink;
	}
	
	public void setPdfDownloadLink(String pdfDownloadLink) {
		this.pdfDownloadLink = pdfDownloadLink;
	}

	/**
	 * Returns the download link to the audio file depending on the openaccess field
	 */
	public String getMp3DownloadLink() {
		if (mp3DownloadLink == null) {
			mp3DownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_MP3);
		}
		return mp3DownloadLink;
	}
	
	public void setMp3DownloadLink(String mp3DownloadLink) {
		this.mp3DownloadLink = mp3DownloadLink;
	}

	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getM4vDownloadLink() {
		if (m4vDownloadLink == null) {
			m4vDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_M4V);
		}
		return m4vDownloadLink;
	}
	
	public void setM4vDownloadLink(String m4vDownloadLink) {
		this.m4vDownloadLink = m4vDownloadLink;
	}

	/**
	 * Returns the download link to the audio file depending on the openaccess field
	 */
	public String getM4aDownloadLink() {
		if (m4aDownloadLink == null) {
			m4aDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_M4A);
		}
		return m4aDownloadLink;
	}
	
	public void setM4aDownloadLink(String m4aDownloadLink) {
		this.m4aDownloadLink = m4aDownloadLink;
	}

	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getFlvDownloadLink() {
		if (flvDownloadLink == null) {
			flvDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_FLV);
		}
		return flvDownloadLink;
	}
	
	public void setFlvDownloadLink(String flvDownloadLink) {
		this.flvDownloadLink = flvDownloadLink;
	}

	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getOggDownloadLink() {
		if (oggDownloadLink == null) {
			oggDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_OGG);
		}
		return oggDownloadLink;
	}
	
	public void setOggDownloadLink(String oggDownloadLink) {
		this.oggDownloadLink = oggDownloadLink;
	}

	/**
	 * Returns the download link to the video file depending on the openaccess field
	 */
	public String getWebmDownloadLink() {
		if (webmDownloadLink == null) {
			webmDownloadLink = getCurrentFileDownloadLink(FILE_SUFFIX_WEBM);
		}
		return webmDownloadLink;
	}
	
	public void setWebmDownloadLink(String webmDownloadLink) {
		this.webmDownloadLink = webmDownloadLink;
	}

	/**
	 * Returns the formatted generation date with time
	 */
	public String getDate() {
		if (date == null) {
			date = formatDate("yyy-MM-dd_HH-mm", "dd.MM.yyyy - HH:mm");
		}
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Returns the formatted generation date without time
	 */
	public String getSimpleDate() {
		if (simpleDate == null) {
			simpleDate = formatDate("yyy-MM-dd_HH-mm", "dd.MM.yyyy");
		}
		return simpleDate;
	}
	
	public void setSimpleDate(String simpleDate) {
		this.simpleDate = simpleDate;
	}
	
	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}
	
	/**
	 * Returns the image in original size (checks which file should be used (thumbnail, audio, ... ))
	 */
	public String getImage() {
		if (image == null) {
			image = checkCorrectImage(IMAGE_SUFFIX, IMAGE_AUDIO, IMAGE_NO_MEDIA, IMAGE_NO_IMAGE);
		}
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Returns the image in small size (checks which file should be used (thumbnail, audio, ... ))
	 */
	public String getImageSmall() {
		if (imageSmall == null) {
			imageSmall = checkCorrectImage(IMAGE_SMALL_SUFFIX, IMAGE_AUDIO_SMALL, IMAGE_NO_MEDIA_SMALL, IMAGE_NO_IMAGE_SMALL);
		}
		return imageSmall;
	}
	
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}
	
	/**
	 * Returns the image in medium size (checks which file should be used (thumbnail, audio, ... ))
	 */
	public String getImageMedium() {
		if (imageMedium == null) {
			imageMedium = checkCorrectImage(IMAGE_MEDIUM_SUFFIX, IMAGE_AUDIO_MEDIUM, IMAGE_NO_MEDIA_MEDIUM, IMAGE_NO_IMAGE_MEDIUM);
		}
		return imageMedium;
	}
	
	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}

	/**
	 * Returns the title in a truncated form
	 */
	public String getShortTitle() {
		if (shortTitle == null) {
			String shortTitle = getTitle();
			if (shortTitle.length() > 45)
				shortTitle = shortTitle.substring(0, 45) + "...";
		}
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
		
	/**
	 * Returns the secure prefix of the file (filename without extension)
	 */
	public String getSPreffix() {
		String preffix = "";
		try {
			preffix = FileUtil.stripExtension(getSecureFilename());
		} catch(Exception e) {
			preffix = "";
		}
		return preffix;
	}
	
	/**
	 * Returns the open access prefix of the file (filename without extension)
	 */
	public String getPreffix() {
		String preffix = "";
		try {
			preffix = FileUtil.stripExtension(getFilename());
		} catch(Exception e) {
			preffix = "";
		}
		return preffix;
	}
	
	public String getLinkedCreators() {
		if (linkedCreators == null) {
			linkedCreators = CreatorLocalServiceUtil.getCommaSeparatedLinkedCreatorsByVideoIdAndMaxCreators(getVideoId(),3);
		}
		return linkedCreators;
	}
	
	public void setLinkedCreators(String linkedCreators) {
		this.linkedCreators = linkedCreators;
	}

	public String getCreators() {
		if (creators == null) {
			creators = CreatorLocalServiceUtil.getCommaSeparatedCreatorsByVideoIdAndMaxCreators(getVideoId(),3);
		}
		return creators;
	}
	
	public void setCreators(String creators) {
		this.creators = creators;
	}
	
	public boolean isHasCaption() {
		return getVttFile().isFile();
	}
	
	/**
	 * Checks if filename and container format exists (= file was correctly uploaded)
	 * @return true if exists, false if not
	 */
	private boolean checkFilenameAndContainerFormatExisting() {
		return !(getContainerFormat().equals("") && getFilename().equals(""));
	}
	
	/**
	 * Checks if a file is an audio file
	 * @return true if it is an audio file, false if not
	 */
	private boolean checkIsAudio() {
		return getContainerFormat().equals("mp3");
	}
	
	/**
	 * Returns the directory of the video
	 * @return the directory of the video
	 */
	private String getHomedirPath(){
		return MEDIA_REPOSITORY + "/" + getHost().getServerRoot() + "/" + getProducer().getHomeDir() + "/";
	}
	
	/**
	 * Returns the file with the given suffix/ extension from the home directory (unchecked if existing)
	 * @param suffix the suffix/ extension of the file
	 * @return the file (unchecked if existing)
	 */
	private File getFile(String suffix){
		return new File(getHomedirPath() + getCurrentPrefix() + suffix);
	}
	
	/**
	 * Returns the lecture2go commsy code
	 * @param identifier the identifier for the commsy embed code
	 * @return the commsy embed code
	 */
	private String getEmbedCommsyCode(String identifier) {
		return "(:lecture2go " + identifier + ":)";
	}
	
	/**
	 * Returns the HTML5 video embed code depending on the openaccess field
	 * @return the HTML5 video embed code
	 */
	private String getVideoEmbedHtml5() {
		if(getOpenAccess()==1){
			return HTML5_EMBED_VIDEO_START + DOWNLOADFOLDER + getPreffix() + FILE_SUFFIX_MP4 + HTML5_EMBED_VIDEO_END;
		} else {
			return HTML5_EMBED_VIDEO_START + VIDEOREPFOLDER + getHost().getServerRoot() + "/" + getProducer().getHomeDir() + "/" + getSPreffix() + getDownloadSuffix() + FILE_SUFFIX_MP4 + HTML5_EMBED_VIDEO_END;
		}
	}
	
	/**
	 * Returns the HTML5 audio embed code depending on the openaccess field
	 * @return the HTML5 audio embed code
	 */
	private String getAudioEmbedHtml5() {
		if(getOpenAccess()==1){
			return HTML5_EMBED_AUDIO_START + DOWNLOADFOLDER + getPreffix() + FILE_SUFFIX_MP3 + HTML5_EMBED_AUDIO_END;
		}else{
			return HTML5_EMBED_AUDIO_START + VIDEOREPFOLDER + getHost().getServerRoot() + "/" + getProducer().getHomeDir() + "/" + getSecureFilename()+ HTML5_EMBED_AUDIO_END;
		}	
	}
	
	/**
	 * Returns the rss URL for the file with the given suffix
	 * @param suffix the suffix/ extension of the file
	 * @return the rss url
	 */
	private String getFileRssLink(String suffix) {
		if(getLectureseriesId()>0)
			return RSS_URL_BASE + getLectureseriesId() + suffix + FILE_SUFFIX_XML;
		else 
			return null;
	}
	
	/**
	 * Returns the download suffix (without extension) if necessary (if a smil file exists) (e.g. "_dl")
	 * @return the download suffix
	 */
	private String getDownloadSuffix() {
		String suffixPrefix = "";
		if (VideoLocalServiceUtil.checkSmilFile(this))
			suffixPrefix = PropsUtil.get("lecture2go.videoprocessing.downloadsuffix");
		return suffixPrefix;
	}
	
	/**
	 * Returns the download link for the file with the given suffix depending on the openaccess field
	 * @param suffix the suffix of the file (e.g. ".mp4")
	 * @return the download link
	 */
	private String getCurrentFileDownloadLink(String suffix) {
		if (getOpenAccess() == 1) {
			return getOpenAccessFileDownloadLink(suffix);
		} else {
			return getSecureFileDownloadLink(suffix);
		}
	}
	
	/**
	 * Returns the download link (using the download servlet) to the secure file
	 * @param suffix the suffix of the file (e.g. ".mp4") 
	 * @return the download link
	 */
	private String getSecureFileDownloadLink(String suffix) {
		return DOWNLOAD_SERVLET_BASE + getDownloadLink() + "&downloadPath=/" + getHost().getName() + "/" + getProducer().getHomeDir() + "/" + getSPreffix() + suffix;
	}
	
	/**
	 * Returns the download link to the openaccess file
	 * @param suffix the suffix of the file (e.g. ".mp4") 
	 * @return the download link
	 */
	private String getOpenAccessFileDownloadLink(String suffix) {
		return DOWNLOADFOLDER + getPreffix() + suffix;
	}
	
	/**
	 * Formats the generationDate from the source format to the target format
	 * @param sourceFormat the source format (using SimpleDateFormat formats)
	 * @param targetFormat the target format (using SimpleDateFormat formats)
	 * @return the formatted date as a String
	 */
	private String formatDate(String sourceFormat, String targetFormat) {
		String unformattedDate = getGenerationDate();
		SimpleDateFormat dateReader =  new SimpleDateFormat(sourceFormat); 
	    try {
	      Date generationDate = dateReader.parse(unformattedDate);
	      SimpleDateFormat dateFormatter =  new SimpleDateFormat(targetFormat); 
	      return dateFormatter.format(generationDate);
	    } catch (ParseException e) {
	      e.printStackTrace();
	      // return the unformatted date if something goes wrong
	      return unformattedDate;
	    }
	}
	
	/**
	 * Checks which image should be returned for the Video (thumbnail, audio, no-media or a placeholder)
	 * @param imageSuffix the suffix for dynamic named video thumbnails
	 * @param audioImage the complete path to the audio image
	 * @param noMediaImage the complete path to the no-media image
	 * @param noImage the complete path to the placeholder image
	 * @return the url to the appropriate image
	 */
	private String checkCorrectImage(String imageSuffix, String audioImage, String noMediaImage, String noImage) {
		if (!checkFilenameAndContainerFormatExisting()) 
			return noMediaImage;
		else if (checkIsAudio()) 
			return audioImage;
		else if (!(new File(IMAGES_REPOSITORY + getCurrentPrefix() + imageSuffix).exists()))
			return noImage;
		else 
			return IMAGES_URL + getCurrentPrefix() + imageSuffix;
	}
}