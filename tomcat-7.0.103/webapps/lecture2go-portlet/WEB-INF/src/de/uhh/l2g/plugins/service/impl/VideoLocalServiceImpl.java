/*******************************************************************************
 * License
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Lecture2Go 
 * 
 * The Lecture2Go software is based on the liferay portal 6.2-ga6
 * <http://www.liferay.com> (Copyright notice see below)
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 * Copyright (c) 2013 - present University of Hamburg / Computer and Data Center (RRZ)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * The Liferay Plugins SDK:
 * 
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Third Party Software
 * 
 * Lecture2Go uses third-party libraries which may be distributed under different licenses
 * to the above (but are compatible with the used GPL license). Informations about these
 * licenses and copyright informations are mostly detailed in the library source code or jars themselves.
 * You must agree to the terms of these licenses, in addition tothe above Lecture2Go source code license,
 * in order to use this software.
 ******************************************************************************/
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

package de.uhh.l2g.plugins.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.NoSuchInstitutionException;
import de.uhh.l2g.plugins.NoSuchLectureseriesException;
import de.uhh.l2g.plugins.NoSuchProducerException;
import de.uhh.l2g.plugins.NoSuchVideoException;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Lastvideolist;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Category;
import de.uhh.l2g.plugins.model.Videohitlist;
import de.uhh.l2g.plugins.model.impl.HostImpl;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.LastvideolistImpl;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.ProducerImpl;
import de.uhh.l2g.plugins.model.impl.VideoImpl;
import de.uhh.l2g.plugins.model.impl.Video_CategoryImpl;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.LastvideolistLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.VideoLocalServiceBaseImpl;
import de.uhh.l2g.plugins.service.persistence.VideoFinderUtil;
import de.uhh.l2g.plugins.util.FFmpegManager;
import de.uhh.l2g.plugins.util.ProzessManager;

/**
 * The implementation of the video local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.uhh.l2g.plugins.service.VideoLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.VideoLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.VideoLocalServiceUtil
 */
public class VideoLocalServiceImpl extends VideoLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.uhh.l2g.plugins.service.VideoLocalServiceUtil} to access the video
	 * local service.
	 */
	
	protected static Log LOG = LogFactoryUtil.getLog(Video.class.getName());

	public Video addVideo(Video object){
		Long id;
		try {
			id = counterLocalService.increment(Video.class.getName());
			object.setPrimaryKey(id);
			super.addVideo(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	public List<Video> getByOpenAccess(int bool) throws SystemException {
		return videoPersistence.findByOpenAccess(bool);
	}

	public int getByOpenAccessAndUploadedFile(int bool) throws SystemException {
		return	videoPersistence.countByOpenAccessAndUploadedFile(bool);
	}

	public Video getLatestOpenAccessVideoForLectureseries(Long lectureseriesId) {
		return VideoFinderUtil.findLatestOpenAccessVideoForLectureseries(lectureseriesId);
	}
	
	public List<Video> getByTerm(Long termId) throws SystemException {
		List<Video> vl = videoPersistence.findByTerm(termId);
		return vl;
	}

	public int unlinkLectureseriesFromVideos(Long lectureseriesId) {
		return VideoFinderUtil.unlinkLectureseriesFromVideos(lectureseriesId);
	}

	public List<Video> getByRootInstitution(Long rootInstitutionId) throws SystemException {
		List<Video> vl = videoPersistence.findByRootInstitution(rootInstitutionId);
		return vl;
	}
	
	public List<Video> getByFilename(String filename) throws SystemException {
		List<Video> vl = videoPersistence.findByFilename(filename);
		return vl;
	}

	public List<Video> getByProducer(Long producerId) throws SystemException {
		List<Video> vl = videoPersistence.findByProducer(producerId);
		return vl;
	}

	public List<Video> getByLectureseries(Long lectureseriesId) throws SystemException {
		List<Video> vl = videoPersistence.findByLectureseries(lectureseriesId);
		List<Video> rvl = getSortedVideoList(vl, lectureseriesId);
		return rvl;
	}

	public int countByLectureseries(Long lectureseriesId) throws SystemException {
		return videoPersistence.countByLectureseries(lectureseriesId);
	}

	public List<Video> getByProducerAndLectureseries(Long producerId, Long lectureseriesId) throws SystemException {
		List<Video> vl = videoPersistence.findByProducerAndLectureseries(producerId, lectureseriesId);
		return vl;
	}

	public List<Video> getByProducerAndDownloadLink(Long producerId, int downloadLink) throws SystemException {
		List<Video> vl = videoPersistence.findByProducerAndDownloadLink(producerId, downloadLink);
		return vl;
	}
	
	public List<Video> getPopular(int limit){
		return VideoFinderUtil.findPopular(limit);
	}
	
	public List<Video> getLatestVideos(){
		return VideoFinderUtil.findLatestVideos();
	}
	
	public void createThumbnailsIfNotExisting(Long videoId) {
		Video objectVideo = new VideoImpl();
		try {
			objectVideo = getVideo(videoId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// only create thumbnails for mp4 files
		if (objectVideo.getContainerFormat().equals("mp4")) {
			/*String filePath = PropsUtil.get("lecture2go.media.repository") + "/" + objectVideo.getHost().getServerRoot() + "/" + objectVideo.getProducer().getHomeDir() + "/" + objectVideo.getFilename();
			File videoFile = new File(filePath);*/
			File videoFile = objectVideo.getMp4File();

			if (videoFile.isFile()) {
				if (!FFmpegManager.thumbnailsExists(objectVideo)) {
					// create thumbnail
					String thumbnailLocation = PropsUtil.get("lecture2go.images.system.path") + "/" + objectVideo.getCurrentPrefix() + ".jpg";
					//duration in seconds 
					String myDateString = objectVideo.getDuration();
					//SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
					//the above commented line was changed to the one below, as per Grodriguez's pertinent comment:
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					java.util.Date date = null;
					try {
						date = sdf.parse(myDateString);
					} catch (Exception e) {
						// the duration could not be parsed
					}
					int dur;
					if (date != null) {
						Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
						calendar.setTime(date);   // assigns calendar to given date 
						int hour = calendar.get(Calendar.HOUR);
						int min = calendar.get(Calendar.MINUTE);
						int sec = calendar.get(Calendar.SECOND);
						dur = hour+sec+min;
					} else {
						// if duration could not be parsed use a default of 2 seconds
						dur = 2;
					}
					FFmpegManager.createThumbnail(videoFile.getPath(), thumbnailLocation, dur/2);
				}
			}
		}
	}
	
	public void createSymLinkToDownloadableFileIfNotExisting(Long videoId) {
		Video objectVideo = new VideoImpl();
		try {
			objectVideo = getVideo(videoId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Host objectHost = objectVideo.getHost();
		Producer objectProducer = objectVideo.getProducer();

		// check if file with download-suffix exists, if not create it 
		// (we always create a _symlink_ with download suffix even if download is not permitted, as this file is also used as an RSTP fallback)
		if(checkSmilFile(objectVideo)){
			File file = new File(PropsUtil.get("lecture2go.media.repository") + "/" + objectHost.getDirectory() + "/" + objectProducer.getHomeDir() + "/" + objectVideo.getCurrentPrefix()+PropsUtil.get("lecture2go.videoprocessing.downloadsuffix")+".mp4");
			try {
				if (!isSymlink(file)) {
					ProzessManager pm = new ProzessManager();
					pm.createSymLinkToDownloadableFile(objectHost, objectVideo, objectProducer);
					// remove the download sym link to the original video file in the download repository and replace it with a symlink to the new downloadable file
					if (!TextUtils.isEmpty(objectVideo.getFilename())) {
						File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + objectVideo.getFilename());
						symLink.delete(); 
					}
					
					// recreate the sym link if applicable
					if (objectVideo.getOpenAccess() == 1 && objectVideo.getDownloadLink() == 1) {
						pm.generateSymbolicLinks(objectVideo);
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
			} 
		}
	}
	
	public JSONArray getJSONVideo(Long videoId){
		JSONArray json = new JSONArray();
		try {
			Video video = getVideo(videoId);
					
			if(video.getMp4File().isFile()){
				JSONObject jsonoMp4 = new JSONObject();
				try {
					String name="";
					if(video.getOpenAccess()==1){
						name=video.getPreffix()+".mp4";
					}else{
						name=video.getSPreffix()+".mp4";
					}
					jsonoMp4.put("name", name);
					jsonoMp4.put("id", name.replace(".", ""));
					jsonoMp4.put("size", video.getFileSize());
					jsonoMp4.put("type", "mp4");
					json.put(jsonoMp4);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
	
			if(video.getMp3File().isFile()){
				JSONObject jsonoMp3 = new JSONObject();
				try {
					jsonoMp3.put("name", video.getMp3File().getName());
					jsonoMp3.put("id", video.getMp3File().getName().replace(".", ""));
					jsonoMp3.put("size", video.getMp3File().getTotalSpace());
					jsonoMp3.put("type", "mp3");
					json.put(jsonoMp3);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getM4aFile().isFile()){
				JSONObject jsonoM4a = new JSONObject();
				try {
					jsonoM4a.put("name", video.getM4aFile().getName());
					jsonoM4a.put("id", video.getM4aFile().getName().replace(".", ""));
					jsonoM4a.put("size",  video.getM4aFile().getTotalSpace());
					jsonoM4a.put("type", "m4a");
					json.put(jsonoM4a);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				} 
			}
	
			if(video.getM4vFile().isFile()){
				JSONObject jsonoM4v = new JSONObject();
				try {
					jsonoM4v.put("name", video.getM4vFile().getName());
					jsonoM4v.put("id", video.getM4vFile().getName().replace(".", ""));
					jsonoM4v.put("size", video.getM4vFile().getTotalSpace());
					jsonoM4v.put("type", "m4v");
					json.put(jsonoM4v);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getPdfFile().isFile()){
				JSONObject pdf = new JSONObject();
				try {
					pdf.put("name", video.getPdfFile().getName());
					pdf.put("id", video.getPdfFile().getName().replace(".", ""));
					pdf.put("size", video.getPdfFile().getTotalSpace());
					pdf.put("type", "pdf");
					json.put(pdf);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getFlvFile().isFile()){
				JSONObject flv = new JSONObject();
				try {
					flv.put("name", video.getFlvFile().getName());
					flv.put("id", video.getFlvFile().getName().replace(".", ""));
					flv.put("size", video.getFlvFile().getTotalSpace());
					flv.put("type", "flv");
					json.put(flv);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getOggFile().isFile()){
				JSONObject ogg = new JSONObject();
				try {
					ogg.put("name", video.getOggFile().getName());
					ogg.put("id", video.getOggFile().getName().replace(".", ""));
					ogg.put("size", video.getOggFile().getTotalSpace());
					ogg.put("type", "ogg");
					json.put(ogg);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getWebmFile().isFile()){
				JSONObject webm = new JSONObject();
				try {
					webm.put("name", video.getWebmFile().getName());
					webm.put("id", video.getWebmFile().getName().replace(".", ""));
					webm.put("size", video.getWebmFile().getTotalSpace());
					webm.put("type", "webm");
					json.put(webm);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
			
			if(video.getVttFile().isFile()){
				JSONObject vtt = new JSONObject();
				try {
					vtt.put("name", video.getVttFile().getName());
					vtt.put("id", video.getVttFile().getName().replace(".", ""));
					vtt.put("size", video.getVttFile().getTotalSpace());
					vtt.put("type", "vtt");
					json.put(vtt);
				} catch (JSONException e) {
	//				//e.printStackTrace();
				}
			}
		
		} catch (Exception e) {

		}
		return json;
	}
	
	public void createLastVideoList() throws SystemException {
		List<Video> vlist = this.getLatestVideos();
		//refresh the whole video list in the table
		lastvideolistPersistence.removeAll();
		Iterator<Video> vIt = vlist.listIterator();
		while(vIt.hasNext()){
			Video v = vIt.next();
			//save videos in table
			Lastvideolist lastvideolist = new LastvideolistImpl();
			lastvideolist.setVideoId(v.getVideoId());
			LastvideolistLocalServiceUtil.addLastvideolist(lastvideolist);
		}
	}

	public int countByLectureseriesAndOpenaccess(Long lectureseriesId, int openAccess) throws SystemException{
		return videoPersistence.countByLectureseriesAndOpenaccess(lectureseriesId, openAccess);
	}
	
	public List<Video> getByLectureseriesAndOpenaccess(Long lectureseriesId, int openAccess) throws SystemException{
		List<Video> vl = new ArrayList<Video>();
		if(lectureseriesId!=0)vl=videoPersistence.findByLectureseriesAndOpenaccess(lectureseriesId, openAccess);	
		List<Video> rvl = getSortedVideoList(vl, lectureseriesId);		
		return rvl;
	}
	
	/**
	 * required properties for jwplayer in portal-ext.properties file
	 * 
	 * [host]=configured host in database (automatically e.g. streaming.server.com)
	 * [ext]=file extension (automatically e.g mp3)
	 * [l2go_path]=generated lecture2go file path (automatically e.g. 3l2gproducer1)
	 * [filename]=video file name (automatically e.g 00.000_video_2015-06-08_08-06.mp4)
	 * [protocol]=host protocol (automatically e.g rtmpt)
	 * [port]=host port (automatically e.g 80)
	 * [smilfile]=adaptive streaming file 
	 * 
	 * example for lecture2go configuration
	 * lecture2go.uri1.player.template=https://[host]/vod/_definst/smil:[l2go_path]/[smilfile]/playlist.m3u8
	 * lecture2go.uri2.player.template=https://[host]/vod/_definst/[ext]:[l2go_path]/[filename]/playlist.m3u8
	 * lecture2go.uri3.player.template=rtmpt://[host]/vod/_definst/[ext]:[l2go_path]/[filename]
	 * lecture2go.uri4.player.template=${lecture2go.downloadserver.web.root}/abo/[filename]
	 * lecture2go.uri5.player.template=rtsp://[host]:[port]/vod/_definst/[ext]:[l2go_path]/[filename]
	**/
	public void addPlayerUris2Video(Video video){
		
		Host host = video.getHost();
		Producer producer = video.getProducer();
		
		ArrayList<String> playerUris = new ArrayList<String>();
		JSONArray playerUrisSortedJSON = new JSONArray();
		
		String l2go_path = video.getRootInstitutionId() + "l2g" + producer.getHomeDir();
		
		String uri1 = PropsUtil.get("lecture2go.uri1.player.template");
		String uri2 = PropsUtil.get("lecture2go.uri2.player.template");
		String uri3 = PropsUtil.get("lecture2go.uri3.player.template");
		String uri4 = PropsUtil.get("lecture2go.uri4.player.template");
		String uri5 = PropsUtil.get("lecture2go.uri5.player.template");
		
		ArrayList<String> uris = new ArrayList<String>();
		uris.add(uri1);uris.add(uri2);uris.add(uri3);uris.add(uri4);uris.add(uri5);
		
		for(int i=0; i<uris.size();i++){
			String playerUri = "";
			playerUri += uris.get(i);
			/*
			if(video.getOpenAccess()==1){
				if (checkSmilFile(video)) {
					playerUri = playerUri.replace("[smilfile]", video.getPreffix()+".smil");
				}
				playerUri = playerUri.replace("[filename]", video.getFilename());
			}else{
				if (checkSmilFile(video)) {
					playerUri = playerUri.replace("[smilfile]", video.getSPreffix()+".smil");
				}
				playerUri = playerUri.replace("[filename]", video.getSecureFilename());
			}*/
			
			if (checkSmilFile(video)) {
				playerUri = playerUri.replace("[smilfile]", video.getCurrentPrefix() +".smil");
			}
			
			String filename;
			if (video.getContainerFormat().equals("mp4")) {
				// this returns the correct filename even for videos with multiple qualities
				filename = video.getCurrentMp4FilenameWithReasonableBitrate();
			} else {
				filename = video.getCurrentFilename();
			}
			playerUri = playerUri.replace("[filename]", filename);
			//
			playerUri = playerUri.replace("[host]", host.getName());
			playerUri = playerUri.replace("[ext]", video.getContainerFormat());
			playerUri = playerUri.replace("[prefix]", host.getPrefix());
			playerUri = playerUri.replace("[l2go_path]", l2go_path);
//			playerUri = playerUri.replace("[protocol]", host.getProtocol());
//			playerUri = playerUri.replace("[port]", host.getPort()+"");
			//
			if( playerUri.length()>0 && !playerUri.contains("[") && !playerUri.contains("]") )playerUris.add(playerUri);
			
		}
		//sort player with priority set in the portal-ext.properties
		for(int i=0; i<playerUris.size();i++){
			String uri = playerUris.get(i);
			//json object
			JSONObject o = new JSONObject();
			//container
			String container ="";
			int l = uri.trim().split("\\.").length;
			container = uri.trim().split("\\.")[l-1];
			String downloadServ = PropsUtil.get("lecture2go.downloadserver.web.root");

			//check player files!
			boolean smilFileAllowed = (uri.contains("/_definst/smil") && checkSmilFile(video) && container.contains("m3u8"));
			boolean hlsStreamingAllowed = ((uri.contains("/_definst/mp4") || uri.contains("/_definst/mp3"))  && !checkSmilFile(video));
			boolean downloadAllowed = (uri.contains(downloadServ) && video.getDownloadLink()==1);
			boolean rtspAllowed = (uri.contains("rtsp"));

			if(smilFileAllowed || hlsStreamingAllowed || downloadAllowed || rtspAllowed){
				//custom case for download allowed 
				//and oper or closed case
				if(downloadAllowed && video.getOpenAccess()==0){
					String downloadServerDownPath = "/" + PropsUtil.get("lecture2go.downloadserver.down.path") + "/";
					uri=downloadServ+downloadServerDownPath+l2go_path+"/"+video.getSecureFilename();
				}
				// in some cases this is necessary to correct the filename of the open access files in the download folder
				// (case: smil file available for adaptive streaming, in combination with open access and download allowed -> wrong filename (with suffix) is set for the downloadfolder (but correct one for rtsp streaming))
				if(downloadAllowed && video.getOpenAccess()==1){
					String downloadServerPath = "/" + PropsUtil.get("lecture2go.downloadserver.path") + "/";
					uri=downloadServ+downloadServerPath+video.getFilename();
				}
				try {
					o.put("file", uri);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				playerUrisSortedJSON.put(o);
			}
		}
		//
		video.setJsonPlayerUris(playerUrisSortedJSON);
	}
	
	
	/**
	 * This adds the "tracks" section for the video player json if there are any captions or chapters and sets the label to
	 * language of the caption file (translated to the userLocale)
	 */
	public void addTextTracks2VideoWithLanguageLabel(Video video, Locale userLocale){
		JSONArray playerTracksJSON = new JSONArray();
		try {
			// add chapter info to track if video has chapters
			if (video.isHasChapters()) {
				JSONObject chapterTrackJSON = new JSONObject();
				chapterTrackJSON.put("file", video.getVttChapterFile());
				chapterTrackJSON.put("kind", "chapters");
				playerTracksJSON.put(chapterTrackJSON);
			}
			
			// add captions info to track if video has captions
			if (video.isHasCaption()) {
				// try to retrieve the language from the caption file itself, returns a default value if language property could not be read
				String language = retrieveLanguageDisplayNameOfCaptionFile(video.getVttFile(), userLocale);

				JSONObject captionTrackJSON = new JSONObject();
				captionTrackJSON.put("file", video.getVttCaptionUrl());
				captionTrackJSON.put("kind", "captions");
				captionTrackJSON.put("label", language);
				playerTracksJSON.put(captionTrackJSON);
			}
		} catch (Exception e) {
			
		}
		video.setJsonPlayerTracks(playerTracksJSON);
	}
	
	/**
	 * This adds the "tracks" section for the video player json if there are any captions or chapters
	 */
	public void addTextTracks2Video(Video video){
		addTextTracks2VideoWithLanguageLabel(video, null);
	}
	
	public Video getBySecureUrl(String surl) throws NoSuchVideoException, SystemException{
		return VideoFinderUtil.findVideoBySerureUrl(surl);
	}
	
	public List<Video> getAll() throws SystemException{
		return videoPersistence.findAll();
	}

	public List<Video> getBySearchWord(String word, int limit) throws SystemException{
		return VideoFinderUtil.findVideosBySearchWord(word, limit);
	}	
	
	public List<Video> getByAllSearchWords() throws SystemException{
		return VideoFinderUtil.findVideosByAllSearchWords();
	}	

	public List<Video> getBySearchWordAndLectureseriesId(String word, Long lectureseriesId) throws SystemException{
		List<Video> vl = new ArrayList<Video>();
		if(lectureseriesId!=0)vl=VideoFinderUtil.findVideosBySearchWordAndLectureseriesId(word, lectureseriesId);	
		List<Video> rvl = getSortedVideoList(vl, lectureseriesId);		
		return rvl;
	}	
	
	public List<Video> getByHits(Long hits){
		return VideoFinderUtil.findVideosByHits(hits);
	}

	public List<Video> getByHitsAndOpenAccess(Long hits){
		return VideoFinderUtil.findVideosByHitsAndOpenAccess(hits);
	}
	
	public List<Video> getByHits(){
		return VideoFinderUtil.findVideosByHits(0);
	}
	
	@Override
	public Video updateVideo(Video video){
		Video v = new VideoImpl();
		if(video.getVideoId()>0)
			try {
				v=super.updateVideo(video);
			} catch (SystemException e) {
				//e.printStackTrace();
			}
		return v;
	}
	
	public Video incrementHitCounter(Video video) throws SystemException {
		/* We explicitly clear the cache. This seems necessary for clustering, as the remove cache trigger is send and handled with some delay, so a page view may
		   overwrite changes made in the backend if the entity still remains in the cache. */
		videoPersistence.clearCache(video);
		video = videoPersistence.fetchByPrimaryKey(video.getVideoId());
		video.setHits(video.getHits()+1);
		videoPersistence.update(video);
		return video;
	}
	
	
	private List<Video> getSortedVideoList(List<Video> vl, Long lectureseriesId) throws SystemException
	{ 
		List<Video> sortedVideoList = new ArrayList<Video>();
		
		if(vl == null || lectureseriesId < 1)
			return sortedVideoList;
		
		for(Video objectVideo: vl) {
			if(objectVideo.getFilename().trim().length()>0)sortedVideoList.add(objectVideo); 
		}
		int sortVideo = 0;
		try {
			Lectureseries lectureseriesObject = lectureseriesPersistence.findByPrimaryKey(lectureseriesId);
			sortVideo = lectureseriesObject.getVideoSort();
		} catch (NoSuchModelException e) {
			//e.printStackTrace();
		}
		
		// Sort by generation date
		Collections.sort(sortedVideoList, new Comparator<Video>() {
				@Override
				public int compare(Video v1, Video v2) {
					return  v2.getGenerationDate().compareTo(v1.getGenerationDate());
				}
		    });
		
		// Sort videos ascending
		if(sortVideo == 1)
		{
			Collections.reverse(sortedVideoList);
		}
		
		return sortedVideoList;
	}
	
	public Long getLatestClosedAccessVideoId(Long lectureseriesId){
		List<Video> vl = new ArrayList<Video>();
		try {
			vl = getByLectureseriesAndOpenaccess(lectureseriesId,0);
			vl = stripVideosWithMissingMetadataFromList(vl);
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		return vl.get(0).getVideoId();
	}
	
	/**
	 * Checks if the video has a related smil-file in the file system
	 */
	public boolean checkSmilFile(Video video) {
		Host host = new HostImpl();
		try {
			host = HostLocalServiceUtil.getHost(video.getHostId());
		} catch (PortalException e1) {
//			e1.printStackTrace();
		} catch (SystemException e1) {
//			e1.printStackTrace();
		}
		Producer producer = new ProducerImpl();
		try {
			producer = producerPersistence.findByPrimaryKey(video.getProducerId());
		} catch (NoSuchProducerException e1) {
//			e1.printStackTrace();
		} catch (SystemException e1) {
//			e1.printStackTrace();
		}
		String  mediaRep = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir();

		// set prefix according to openaccess filename or secured
		String prefix = video.getOpenAccess()==1 ? video.getPreffix() : video.getSPreffix();
		String smilPath = mediaRep + "/" + prefix +".smil";
		File smilFile = new File(smilPath);
		return smilFile.isFile();
	}

	public boolean fileStringSegmentFoundInArray(String file, JSONArray jsonArray){
		boolean ret = false;
		for(int i=0;i<jsonArray.length();i++){
			try {
				Object o = jsonArray.get(i);
				int df = 0;
				df++;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return ret;
	}
	
	/**
	 * Creates a symlink for the caption of the video to to captions folder
	 */
	public void createSymLinkForCaptionIfExisting(Long videoId) throws PortalException, SystemException {
		Video video = getVideo(videoId);
		File vttFile = video.getVttFile();
		if(vttFile.isFile()){
			String symLinkPath = PropsUtil.get("lecture2go.captions.system.path") + "/" + vttFile.getName();
			ProzessManager pm = new ProzessManager();
			pm.generateSymLink(vttFile.getAbsolutePath(), symLinkPath);
		}
	}
	

	/**
	 * Checks if file is a symoblic link
	 * @param file the file to check
	 * @return true if file is sym link, false if not
	 * @throws IOException
	 */
	public boolean isSymlink(File file) throws IOException {
		return Files.isSymbolicLink(file.toPath());
	}
	
	/**
	 * Tries to retrieve the language from the caption file and returns a translated language display name
	 * 
	 * Reads first lines of the file (specs of webvtt define headers must be before first blank line) and looks for a language property
	 * @param captionFile the caption file from which the language will be extracted
	 * @param userLocale the locale which is used to return the translated language display name
	 * @return the language display name in the language of the locale property or "Default" if none found
	 */
	public String retrieveLanguageDisplayNameOfCaptionFile(File captionFile, Locale userLocale) {
		String language = "Default"; // fallback
		
		// try to read the languageId (e.g. "de", "en_US", "en-US") from the caption file header
		try {
			String patternString = "Language:\\s*([^\\s]*)"; //"Language:" following 0 to n whitespaces and the languageId until next whitespace occurs]
		    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		    Matcher matcher;
			
			BufferedReader captionFileBufferedReader = new BufferedReader(new FileReader(captionFile));
            String line;
            // we read the file until the first blank line, as this separates the header from the rest of the file
			while ((line = captionFileBufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					// the header is finished, do not parse the file any further
					break;
				}
				// search the current line for the language property via regex
			    matcher = pattern.matcher(line);
			    if (matcher.find()) {
			    	// group 1 is the correct regexp group match to match only the language-id without the property-prefix ("Language:")
			    	String languageId = matcher.group(1);
			    	// use Liferay API to get a user readable name for the language from the language-id while using the current users local
			    	// e.g. "German" for the languageId "de_DE" when the userLocale is english
					
					if (userLocale != null) {
						language = LocaleUtil.fromLanguageId(languageId,true).getDisplayLanguage(userLocale);
					} else {
				    	// for iframe embeds we have no userLocale, use the default language
				    	language = LocaleUtil.fromLanguageId(languageId,true).getDisplayLanguage();
					}
			    	break;
			    }
            }
			captionFileBufferedReader.close();
		} catch (Exception e) {
			// vtt file can not be read, return the default language String
			e.printStackTrace();
			return "Default";
		}
	    
	    return language;
	}
	
	public boolean hasMissingMetadata(Long videoId) {
		return VideoFinderUtil.checkVideoHasMissingMetadata(videoId);
	}
	
	public List<Video> getVideosWithMissingMetadata() {
		return VideoFinderUtil.findVideosWithMissingMetadata();
	}
	
	public List<Video> stripVideosWithMissingMetadataFromList(List<Video> videos) {
		ListIterator<Video> vi = videos.listIterator();
		while (vi.hasNext()) {
		   Video v = vi.next();
		   if (v.isWithMissingMetadata()) {
			   vi.remove();
		   }
		}
		return videos;
	}
	
	/**
	 * This method is only used to fix missing database entries
	 * Uses the lectureseries information for filling the missing data
	 */
	public void fixMissingMetadataForVideosFromRelatedLectureseries() {
		// get list of videos with missing metadata
		List<Video> lv = getVideosWithMissingMetadata();
		for (Video v: lv) {
			// only fix videos with related lectureseries, otherwise we have no information which data is correct, those need to be fixed manually
			if (v.getLectureseriesId()>0) {
				Lectureseries l = new LectureseriesImpl();
				try {
					l = LectureseriesLocalServiceUtil.getLectureseries(v.getLectureseriesId());
				} catch (Exception e) {
					// there is no lectureseries with the given id (should not happen), continue to next video in loop
					e.printStackTrace();
					continue;
				}
				
				// fix wrong term with information from lectureseries
				if (v.getTermId()==0) {
					v.setTermId(l.getTermId());
					this.updateVideo(v);
				}
				
				// fix missing category with information from lectureseries
				try {
					if (Video_CategoryLocalServiceUtil.getByVideo(v.getVideoId()).isEmpty()) {
						Video_Category vc = Video_CategoryLocalServiceUtil.createVideo_Category(0);
						vc.setVideoId(v.getVideoId());
						vc.setCategoryId(l.getCategoryId());
						Video_CategoryLocalServiceUtil.addVideo_Category(vc);
					}
				} catch (SystemException e) {
					e.printStackTrace();
					continue;
				}
			}
			
		}
		
	}
}
