/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package de.uhh.l2g.plugins.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the Video service. Represents a row in the &quot;LG_Video&quot; database table, with each column mapped to a property of this class.
 *
 * @author Iavor Sturm
 * @see VideoModel
 * @see de.uhh.l2g.plugins.model.impl.VideoImpl
 * @see de.uhh.l2g.plugins.model.impl.VideoModelImpl
 * @generated
 */
public interface Video extends VideoModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link de.uhh.l2g.plugins.model.impl.VideoImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public boolean isWithMissingMetadata();

	public de.uhh.l2g.plugins.model.Host getHost();

	public de.uhh.l2g.plugins.model.Producer getProducer();

	/**
	* Returns the current file prefix depending on openaccess field
	*/
	public java.lang.String getCurrentPrefix();

	/**
	* Returns the current file name depending on openaccess field
	*/
	public java.lang.String getCurrentFilename();

	public java.lang.String getLectureseriesName();

	public void setLectureseriesName(java.lang.String lectureseriesName);

	public java.lang.String getLectureseriesNumber();

	public void setLectureseriesNumber(java.lang.String lectureseriesNumber);

	public java.lang.String getCreatorFullName();

	public void setCreatorFullName(java.lang.String creatorFullName);

	/**
	* Returns the complete URL to the lectureseries of this video
	*/
	public java.lang.String getLectureseriesUrl();

	public void setLectureseriesUrl(java.lang.String lectureseriesUrl);

	public java.lang.Integer getAccessPermitted();

	public void setAccessPermitted(java.lang.Integer accessPermitted);

	/**
	* Returns and sets the URIs for the video player as a JSONAray
	*/
	public org.json.JSONArray getJsonPlayerUris();

	public void setJsonPlayerUris(org.json.JSONArray jsonPlayerUris);

	public java.util.ArrayList<java.lang.String> getPlayerUris();

	public void setPlayerUris(java.util.ArrayList<java.lang.String> playerUris);

	/**
	* Returns and sets the URIs for the video player as a JSONAray
	*/
	public org.json.JSONArray getJsonPlayerTracks();

	public void setJsonPlayerTracks(org.json.JSONArray jsonPlayerTracks);

	/**
	* Returns the complete embed code for commsy depending on the openaccess field
	*/
	public java.lang.String getEmbedCommsy();

	public void setEmbedCommsy(java.lang.String embedCommsy);

	/**
	* Returns the complete iframe embed code (usable for openaccess and non-openaccess videos)
	*/
	public java.lang.String getEmbedIframe();

	public void setEmbedIframe(java.lang.String embedIframe);

	/**
	* Returns the complete html5 embed code depending on the openaccess field and container format
	*/
	public java.lang.String getEmbedHtml5();

	public void setEmbedHtml5(java.lang.String embedHtml5);

	/**
	* Returns the path to the vtt-chapter-file
	*/
	public java.lang.String getVttChapterFile();

	public void setVttChapterFile(java.lang.String vttChapterFile);

	/**
	* Returns the url to the vtt-caption-file
	*/
	public java.lang.String getVttCaptionUrl();

	public void setVttCaptionUrl(java.lang.String vttCaptionUrl);

	public java.lang.String getVttThumbsFilde();

	public void setVttThumbsFilde(java.lang.String vttThumbsFilde);

	public java.lang.String getMp4RssLink();

	public void setMp4RssLink(java.lang.String mp4RssLink);

	public java.lang.String getMp3RssLink();

	public void setMp3RssLink(java.lang.String mp3RssLink);

	public java.lang.String getM4vRssLink();

	public void setM4vRssLink(java.lang.String m4vRssLink);

	public java.lang.String getM4aRssLink();

	public void setM4aRssLink(java.lang.String m4aRssLink);

	public java.lang.String getOggRssLink();

	public void setOggRssLink(java.lang.String oggRssLink);

	public java.lang.String getFlvRssLink();

	public void setFlvRssLink(java.lang.String flvRssLink);

	public java.lang.String getWebmRssLink();

	public void setWebmRssLink(java.lang.String webmRssLink);

	/**
	* Checks if file has chapters
	*/
	public boolean isHasChapters();

	public boolean isHasComments();

	public void setHasComments(boolean hasComments);

	/**
	* Returns the complete URL to the open access video
	*/
	public java.lang.String getUrl();

	public void setUrl(java.lang.String url);

	/**
	* Returns the complete secure URL to the NON open access video
	*/
	public java.lang.String getSecureUrl();

	public void setSecureUrl(java.lang.String secureUrl);

	/**
	* Returns the current URL depending on the openaccess field
	*/
	public java.lang.String getCurrentURL();

	/**
	* Returns the mp4 file (unchecked if existing)
	*/
	public java.io.File getMp4File();

	public void setMp4File(java.io.File mp4File);

	/**
	* Returns the pdf file (unchecked if existing)
	*/
	public java.io.File getPdfFile();

	public void setPdfFile(java.io.File pdfFile);

	/**
	* Returns the mp3 file (unchecked if existing)
	*/
	public java.io.File getMp3File();

	public void setMp3File(java.io.File mp3File);

	/**
	* Returns the m4v file (unchecked if existing)
	*/
	public java.io.File getM4vFile();

	public void setM4vFile(java.io.File m4vFile);

	/**
	* Returns the m4a file (unchecked if existing)
	*/
	public java.io.File getM4aFile();

	public void setM4aFile(java.io.File m4aFile);

	/**
	* Returns the flv file (unchecked if existing)
	*/
	public java.io.File getFlvFile();

	public void setFlvFile(java.io.File flvFile);

	/**
	* Returns the ogg file (unchecked if existing)
	*/
	public java.io.File getOggFile();

	public void setOggFile(java.io.File oggFile);

	/**
	* Returns the webm file (unchecked if existing)
	*/
	public java.io.File getWebmFile();

	public void setWebmFile(java.io.File webmFile);

	/**
	* Returns the vtt file (unchecked if existing) from the user repository
	*/
	public java.io.File getVttFile();

	public void setVttFile(java.io.File vttFile);

	/**
	* Returns the current filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are
	* referenced from a _smil_ file, the version with the download suffix.
	* Checks if prefix is _openaccess_ or secure and returns the correct one.
	*/
	public java.lang.String getCurrentMp4FilenameWithReasonableBitrate();

	/**
	* Returns the secure filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are
	* referenced from a _smil_ file, the version with the download suffix.
	*/
	public java.lang.String getSecureMp4FilenameWithReasonableBitrate();

	/**
	* Returns the _openaccess_ filename of the video with a reasonable _bitrate_, this is either the original file or, if multiple qualities are
	* referenced from a _smil_ file, the version with the download suffix.
	*/
	public java.lang.String getMp4FilenameWithReasonableBitrate();

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getMp4DownloadLink();

	public void setMp4DownloadLink(java.lang.String mp4DownloadLink);

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getPdfDownloadLink();

	public void setPdfDownloadLink(java.lang.String pdfDownloadLink);

	/**
	* Returns the download link to the audio file depending on the openaccess field
	*/
	public java.lang.String getMp3DownloadLink();

	public void setMp3DownloadLink(java.lang.String mp3DownloadLink);

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getM4vDownloadLink();

	public void setM4vDownloadLink(java.lang.String m4vDownloadLink);

	/**
	* Returns the download link to the audio file depending on the openaccess field
	*/
	public java.lang.String getM4aDownloadLink();

	public void setM4aDownloadLink(java.lang.String m4aDownloadLink);

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getFlvDownloadLink();

	public void setFlvDownloadLink(java.lang.String flvDownloadLink);

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getOggDownloadLink();

	public void setOggDownloadLink(java.lang.String oggDownloadLink);

	/**
	* Returns the download link to the video file depending on the openaccess field
	*/
	public java.lang.String getWebmDownloadLink();

	public void setWebmDownloadLink(java.lang.String webmDownloadLink);

	/**
	* Returns the formatted generation date with time
	*/
	public java.lang.String getDate();

	public void setDate(java.lang.String date);

	/**
	* Returns the formatted generation date without time
	*/
	public java.lang.String getSimpleDate();

	public void setSimpleDate(java.lang.String simpleDate);

	public java.lang.String getBitrate();

	public void setBitrate(java.lang.String bitrate);

	/**
	* Returns the image in original size (checks which file should be used (thumbnail, audio, ... ))
	*/
	public java.lang.String getImage();

	public void setImage(java.lang.String image);

	/**
	* Returns the image in small size (checks which file should be used (thumbnail, audio, ... ))
	*/
	public java.lang.String getImageSmall();

	public void setImageSmall(java.lang.String imageSmall);

	/**
	* Returns the image in medium size (checks which file should be used (thumbnail, audio, ... ))
	*/
	public java.lang.String getImageMedium();

	public void setImageMedium(java.lang.String imageMedium);

	/**
	* Returns the title in a truncated form
	*/
	public java.lang.String getShortTitle();

	public void setShortTitle(java.lang.String shortTitle);

	/**
	* Returns the secure prefix of the file (filename without extension)
	*/
	public java.lang.String getSPreffix();

	/**
	* Returns the open access prefix of the file (filename without extension)
	*/
	public java.lang.String getPreffix();

	public java.lang.String getLinkedCreators();

	public void setLinkedCreators(java.lang.String linkedCreators);

	public java.lang.String getCreators();

	public void setCreators(java.lang.String creators);

	public boolean isHasCaption();

	public void set360(boolean is360);

	public boolean is360();
}