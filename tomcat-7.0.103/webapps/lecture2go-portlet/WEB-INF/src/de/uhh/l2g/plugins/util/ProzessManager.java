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
package de.uhh.l2g.plugins.util;

/***************************************************************************
 * The Lecture2Go software is based on the liferay portal 6.1.1
 * <http://www.liferay.com>
 *
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 *
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 *
 * @Autor Lecture2Go Team
 * @Version 1.0
 * @Contact lecture2go-open-source@uni-hamburg.de
 *
 * Copyright (c) 2013 University of Hamburg / Computer and Data Center (RRZ)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ***************************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.http.util.TextUtils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Metadata;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.impl.HostImpl;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.MetadataImpl;
import de.uhh.l2g.plugins.model.impl.ProducerImpl;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.LastvideolistLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideohitlistLocalServiceUtil;

public class ProzessManager {
	String[] THUMBNAIL_SUFFIX = { ".jpg","_m.jpg","_s.jpg" };   

	Htaccess HTACCESS = new Htaccess();
	
	@SuppressWarnings("static-access")
	public void deactivateDownload(Video video) throws SystemException, PortalException {
		Host host = HostLocalServiceUtil.getHost(video.getHostId());
		Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		
		video.setDownloadLink(0);
		VideoLocalServiceUtil.updateVideo(video);
		
		//remove symbolic links
		removeSymbolicLinks(video);
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {           
			generateRSS(video, f);
		}
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
	}

	@SuppressWarnings("static-access")
	public void activateDownload(Video video) throws SystemException, PortalException {
		Host host = HostLocalServiceUtil.getHost(video.getHostId());
		Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		
		video.setDownloadLink(1);
		
		VideoLocalServiceUtil.updateVideo(video);
		//symbolic links for download
		if(video.getOpenAccess()==1)generateSymbolicLinks(video);
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {
			generateRSS(video, f);
		}
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
	}

	@SuppressWarnings("static-access")
	public void activateOpenaccess(Video video) throws SystemException, PortalException {
		Host host = HostLocalServiceUtil.getHost(video.getHostId());
		Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		Lectureseries lectureseries = new LectureseriesImpl();
		try{
			lectureseries= LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
		}catch(Exception e){}

		// first rename the file from the filesystem first
		String path = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir();
		String videoPreffix = video.getPreffix();
		String videoSPreffix = video.getSPreffix();
		try {
			// then update the filesystem
			//default media
			File fMp3 = new File(path + "/" + videoSPreffix + ".mp3");
			File fMp4 = new File(path + "/" + videoSPreffix + ".mp4");

			// if mp4 or mp3 exists
			if (fMp4.isFile() || fMp3.isFile()) {
				//for images
				for (String s: THUMBNAIL_SUFFIX) {
					File imgFile = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + videoSPreffix + s);
					imgFile.renameTo(new File(PropsUtil.get("lecture2go.images.system.path") + "/" + videoPreffix + s));
				}
				//for av media
				for (String f: FileManager.MEDIA_FORMATS) {           
					File file = new File(path + "/" + videoSPreffix + "."+f);
					File newFile = new File(path + "/" + videoPreffix + "."+f);
					file.renameTo(newFile);
				}
				// then update the video in the database
				video.setOpenAccess(1);
				video.setSecureFilename("");
				VideoLocalServiceUtil.updateVideo(video);
				
				// publish the video to the OAI-PMH repository
				OaiPmhManager.publish(video.getVideoId());
			}
		} catch (Exception e) {}
		
		// if activated, notify the video processor to handle the processed video files
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			VideoProcessorManager.renameFileOfVideoConversion(video.getVideoId(), video.getFilename());
		}
		
		if (VideoLocalServiceUtil.checkSmilFile(video)) {
			// delete old download symbolic link with secure file name if existing
			File downloadSymLink = new File(path + "/" + videoSPreffix + PropsUtil.get("lecture2go.videoprocessing.downloadsuffix") + ".mp4");
			downloadSymLink.delete();
			
			// delete the download sym link in the download repository which may point to the original video file 
			// (will be correctly recreated in the generateSymboliLinks method)
			if (!TextUtils.isEmpty(video.getFilename())) {
				File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + video.getFilename());
				symLink.delete(); 
			}
			
			// create a symlink to the video file which has a reasonable bitrate
			try {
				createSymLinkToDownloadableFile(host, video, producer);
			} catch (Exception e) {
				//e.printStackTrace();
			} 
		}
		
		//activate symbolic links for download if allowed
		if(video.getDownloadLink()==1)generateSymbolicLinks(video);
		
		//recreate the sym link to captions to point to the renamed file
		// remove old vtt file if existing
		File symLink = new File(PropsUtil.get("lecture2go.captions.system.path") + "/" + videoSPreffix + ".vtt");
		symLink.delete(); // just returns false if file not existing
		// create new sym link if caption vtt file exists
		VideoLocalServiceUtil.createSymLinkForCaptionIfExisting(video.getVideoId());
		
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {           
			generateRSS(video, f);
		}
		
		//update tag cloud for the lectureseries of this video (necessary to add creators of the now-open-access-video to the tagcloud)
		if(video.getLectureseriesId()>0)TagcloudLocalServiceUtil.generateForLectureseries(video.getLectureseriesId());
		
		//update LectureSeries previewVideoId
		LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(lectureseries);
		//
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
		// refresh last video list
		//VideoLocalServiceUtil.createLastVideoList();
		// refresh open acces for lecture series
		LectureseriesLocalServiceUtil.updateOpenAccess(video, lectureseries);
	}

	@SuppressWarnings("static-access")
	public void deactivateOpenaccess(Video video) throws PortalException, SystemException {
		Host host = HostLocalServiceUtil.getHost(video.getHostId());
		Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		Lectureseries lectureseries = new LectureseriesImpl();
		try{
			lectureseries= LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
		}catch(Exception e){}
		
		// then update the filesystem
		String path = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir();
		String videoPreffix = video.getPreffix();
		
		//default media
		File fMp3 = new File(path + "/" + videoPreffix + ".mp3");
		File fMp4 = new File(path + "/" + videoPreffix + ".mp4");
		//
		if (fMp4.isFile() || fMp3.isFile()) {
			// first update the video in the database and, set openaccess
			video.setOpenAccess(0);
			//generate secure file name
			String secureUrl = Security.createSecureFileName() + "." + video.getContainerFormat();
			video.setSecureFilename(secureUrl);
			VideoLocalServiceUtil.updateVideo(video);
			
			// unpublish the video from the OAI-PMH repository
			OaiPmhManager.unpublish(video.getVideoId());
		
			String videoSPreffix = video.getSPreffix();
			//for images
			for (String s: THUMBNAIL_SUFFIX) {
				File imgFile = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + videoPreffix + s);
				imgFile.renameTo(new File(PropsUtil.get("lecture2go.images.system.path") + "/" + videoSPreffix + s));
			}
			//for av media
			for (String f: FileManager.MEDIA_FORMATS) {           
				File file = new File(path + "/" + videoPreffix + "."+f);
				File newFile = new File(path + "/" + videoSPreffix + "."+f);
				file.renameTo(newFile);
			}
		}
		
		// if activated, notify the video processor to handle the processed video files
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			VideoProcessorManager.renameFileOfVideoConversion(video.getVideoId(), video.getSecureFilename());
		}
		
		if (VideoLocalServiceUtil.checkSmilFile(video)) {
			// delete old download symbolic link
			File downloadSymLink = new File(path + "/" + videoPreffix + PropsUtil.get("lecture2go.videoprocessing.downloadsuffix") + ".mp4");
			downloadSymLink.delete();
			
			// create a new symbolic link to the video file which has a reasonable bitrate
			try {
				createSymLinkToDownloadableFile(host, video, producer);
			} catch (Exception e) {
				//e.printStackTrace();
			} 
		}
		
		// delete all symbolic links
		for (String f: FileManager.MEDIA_FORMATS) {           
			File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + video.getPreffix() + "."+f);
			symLink.delete();
		}
		
		//recreate the sym link to captions to point to the renamed file
		// remove old vtt file if existing
		File symLink = new File(PropsUtil.get("lecture2go.captions.system.path") + "/" + videoPreffix + ".vtt");
		symLink.delete(); // just returns false if file not existing
		// create new sym link if caption vtt file exists
		VideoLocalServiceUtil.createSymLinkForCaptionIfExisting(video.getVideoId());
		
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {           
			generateRSS(video, f);
		}
		// delete video from videohitlist
		VideohitlistLocalServiceUtil.deleteByVideoId(video.getVideoId());
		
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
		
		// refresh last video list
		//VideoLocalServiceUtil.createLastVideoList();
		
		// refresh open access for lecture series
		LectureseriesLocalServiceUtil.updateOpenAccess(video, lectureseries); 
		
		//update tag cloud for the lectureseries of this video (necessary to remove creators of the now-closed-access-video to the tagcloud)
		if(video.getLectureseriesId()>0)TagcloudLocalServiceUtil.generateForLectureseries(video.getLectureseriesId());
		
		//update LectureSeries previewVideoId
		LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(lectureseries);
	}

	public void deleteThumbnails(Video video) {
			String videoPreffix = "";
			if (video.getOpenAccess()==1) videoPreffix = video.getPreffix();
			else videoPreffix = video.getSPreffix();
			
			//delete
			for (String s: THUMBNAIL_SUFFIX) {
				File file = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + videoPreffix + s);
				file.delete();
			}
	}

	@SuppressWarnings("static-access")
	public boolean deleteVideo(Video video) throws PortalException, SystemException{
		Host host = new HostImpl();
		Lectureseries lectureseries = new LectureseriesImpl();
		
		try {
			lectureseries = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
		} catch (Exception e3) {
			//e3.printStackTrace();
		}

		try {
			host = HostLocalServiceUtil.getHost(video.getHostId());
		} catch (PortalException e1) {
//			e1.printStackTrace();
		} catch (SystemException e1) {
//			e1.printStackTrace();
		}
		
		Producer producer = new ProducerImpl();
		try {
			producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		} catch (PortalException e1) {
//			e1.printStackTrace();
		} catch (SystemException e1) {
//			e1.printStackTrace();
		}
		
		Metadata metadata = new MetadataImpl();
		try {
			metadata = MetadataLocalServiceUtil.getMetadata(video.getMetadataId());
		} catch (PortalException e1) {
//			e1.printStackTrace();
		} catch (SystemException e1) {
//			e1.printStackTrace();
		}

		// delete video_institution
		Video_InstitutionLocalServiceUtil.removeByVideoId(video.getVideoId());
				
		// delete all segment images from repository location
		try{
			List<Segment> segmentList = SegmentLocalServiceUtil.getSegmentsByVideoId(video.getVideoId());
			SegmentLocalServiceUtil.deleteThumbhailsFromSegments(segmentList);
		}catch (PortalException e) {
			//e.printStackTrace();
		} catch (SystemException e) {
			//e.printStackTrace();
		} catch (NullPointerException e){
			//e.printStackTrace();
		}
		
		//and all segments
		try {
			SegmentLocalServiceUtil.deleteByVideoId(video.getVideoId());
		} catch (SystemException e2) {
			//e2.printStackTrace();
		}
		
		// delete video from videohitlist
		try {
			VideohitlistLocalServiceUtil.deleteByVideoId(video.getVideoId());
		} catch (SystemException e2) {
			//e2.printStackTrace();
		}
		
		// delete meta data which belongs to video 
		try {
			MetadataLocalServiceUtil.deleteMetadata(metadata);
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		// delete video itself
		try {
			VideoLocalServiceUtil.deleteVideo(video);
		} catch (SystemException e1) {
			//e1.printStackTrace();
		}
		
		// unpublish the video from the OAI-PMH repository
		OaiPmhManager.unpublish(video.getVideoId());
		
		//delete physical files 
		String videoPreffix = "";
		if (video.getOpenAccess()==1) videoPreffix = video.getPreffix();
		else videoPreffix = video.getSPreffix();
		
		// delete all video contents
		if (video.getFilename() != null) {
			for (String f: FileManager.MEDIA_FORMATS) {           
				//all media
				File file = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + videoPreffix + "." + f);
				file.delete();
				//all symbolic links
				File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + videoPreffix + "." + f);
				symLink.delete();
			}
			// delete old download symbolic link if existing
			File downloadSymLink = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + videoPreffix + PropsUtil.get("lecture2go.videoprocessing.downloadsuffix") + ".mp4");
			downloadSymLink.delete();
			
			//delete the old symbolic link to the caption file
			File symLink = new File(PropsUtil.get("lecture2go.captions.system.path") + "/" + videoPreffix + ".vtt");
			symLink.delete();
			
			//all thumn nails
			deleteThumbnails(video);
		}
		
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {           
			generateRSS(video, f);
		}
		
		// update last video list
		LastvideolistLocalServiceUtil.deleteByVideoId(video.getVideoId());
		
		// update uploads for producer
		int n = 0;
		n = VideoLocalServiceUtil.getByProducer(producer.getProducerId()).size();
		producer.setNumberOfProductions(n);
		ProducerLocalServiceUtil.updateProducer(producer);
		
		//Update htaccess
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
		
		// refresh open access for lecture series
		if(lectureseries.getLectureseriesId()>0)LectureseriesLocalServiceUtil.updateOpenAccess(video, lectureseries); 
		
		//delete creators from video
		Video_CreatorLocalServiceUtil.deleteByVideoId(video.getVideoId());
		//update creators in Lectureseries
		//now update creators for the whole lecture series
		Long lId = lectureseries.getLectureseriesId();
		try{
			if(lId>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(lId);
		}catch(SystemException e){}
		
		//delete tag cloud for this video
		TagcloudLocalServiceUtil.deleteByObjectId(video.getVideoId());
		
		//delete all categories for video
		Video_CategoryLocalServiceUtil.removeByVideo(video.getVideoId());
		
		//update LectureSeries previewVideoId
		LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(lectureseries);
		

		// delete all created files from the video-processor if activated
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			// send DELETE request to video processor
			VideoProcessorManager.deleteVideoConversion(video.getVideoId());
		}
		
		return true;
	}
	
	@SuppressWarnings("static-access")
	public boolean deleteFilesImagesFromVideo(Video video) throws PortalException, SystemException{
		Host host = new HostImpl();
		try {
			host = HostLocalServiceUtil.getHost(video.getHostId());
		} catch (PortalException e1) {
			//e1.printStackTrace();
		} catch (SystemException e1) {
			//e1.printStackTrace();
		}
		
		Producer producer = new ProducerImpl();
		try {
			producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		} catch (PortalException e1) {
			//e1.printStackTrace();
		} catch (SystemException e1) {
			//e1.printStackTrace();
		}
		
		// delete all segments and images from repository location
		try{
			List<Segment> segmentList = SegmentLocalServiceUtil.getSegmentsByVideoId(video.getVideoId());
			SegmentLocalServiceUtil.deleteThumbhailsFromSegments(segmentList);
			
		}catch (PortalException e) {
			//e.printStackTrace();
		} catch (SystemException e) {
			//e.printStackTrace();
		} catch (NullPointerException e){
			//e.printStackTrace();
		}
		//and all segments
		try {
			SegmentLocalServiceUtil.deleteByVideoId(video.getVideoId());
		} catch (SystemException e2) {
			//e2.printStackTrace();
		}
		
		//delete physical files 
		String videoPreffix = "";
		if (video.getOpenAccess()==1) videoPreffix = video.getPreffix();
		else videoPreffix = video.getSPreffix();
		
		for (String f: FileManager.MEDIA_FORMATS) {           
			//all media
			File file = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + videoPreffix + "." + f);
			file.delete();
			//all symbolic links
			File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + videoPreffix + "." + f);
			symLink.delete();
		}
		//all thumn nails
		deleteThumbnails(video);
		
		// generate RSS
		for (String f: FileManager.MEDIA_FORMATS) {           
			generateRSS(video, f);
		}
		
		//Update htaccess
		String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		try {
			HTACCESS.makeHtaccess(url, VideoLocalServiceUtil.getByProducerAndDownloadLink(producer.getProducerId(), 0));
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		// set empty parameter to video itself
		try {
			video.setFilename("");
			video.setContainerFormat("");
			video.setDate("");
			video.setGenerationDate("");
			video.setSecureFilename(Security.createSecureFileName()+".xx");
			video.setUploadDate(null);
			video.setDownloadLink(0);
			video.setOpenAccess(0);
			video.setResolution("");
			video.setDuration("");
			video.setFileSize("");
			VideoLocalServiceUtil.updateVideo(video);
		} catch (SystemException e1) {
			//e1.printStackTrace();
		}
		return true;
	}

	public void generateRSS(Video video, String type) throws PortalException, SystemException {
		Long lsId = video.getLectureseriesId();
		RSSManager rssMan = new RSSManager();
		String feedName = "";
		String title = "";
		// RSS generate for this lecture or video object
		if(lsId>=0){
			Lectureseries lectser = LectureseriesLocalServiceUtil.getLectureseries(lsId);
			List<Video> videoList = VideoLocalServiceUtil.getByLectureseriesAndOpenaccess(lsId, 1);
			title = lectser.getName();
			rssMan.setTitle(title);
			//	
			for (String f: FileManager.MEDIA_FORMATS) {           
				if (type.equals(f)){
					feedName = "" + lsId + "."+f+".xml";
					rssMan.setRssFilename(feedName);
					try {
						rssMan.createRssFile(videoList, f);
					} catch (IOException e) {
						//e.printStackTrace();
					}
				}
			}
		}
		// RSS end
	}

	public void addNewMediaDirectoryForProducer(Host host, Producer producer) throws IOException {
		File folder = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/");
		if (!folder.exists()) {
			if (folder.mkdir()) {
				Runtime runtime = Runtime.getRuntime();
				String[] cmdArray = { PropsUtil.get("lecture2go.shell.bin"), "-c", "chown nobody " + folder.getAbsolutePath() };
				runtime.exec(cmdArray);
				String[] cmdArray1 = { PropsUtil.get("lecture2go.shell.bin"), "-c", "chown nobody:nobody " + folder.getAbsolutePath() };
				runtime.exec(cmdArray1);
				String[] cmdArray2 = { PropsUtil.get("lecture2go.shell.bin"), "-c", "chmod 701 " + folder.getAbsolutePath() };
				runtime.exec(cmdArray2);
				File prodFolder = new File(PropsUtil.get("lecture2go.httpstreaming.video.repository") + "/" + producer.getInstitutionId() + "l2g" + producer.getHomeDir());
				if (!prodFolder.exists()) {
					String cmd = "ln -s " + folder.getAbsolutePath() + " " + prodFolder.getAbsolutePath();
					runtime.exec(cmd);
				}
			}
		}
	}

	public boolean generateSymbolicLinks(Video v){
		boolean ret = false;
		Host objectHost = new HostImpl();
		Producer objectProducer = new ProducerImpl();
		try {
			objectHost = HostLocalServiceUtil.getByHostId(v.getHostId());
			objectProducer = ProducerLocalServiceUtil.getProducer(v.getProducerId());

		} catch (SystemException e) {
			//e.printStackTrace();
		} catch (PortalException e) {
			//e.printStackTrace();
		}
		Runtime runCmd = Runtime.getRuntime();
		
		for (String mf : FileManager.MEDIA_FORMATS) {
			String mFile;
			String mFileAbo;
			if (mf == "mp4" && VideoLocalServiceUtil.checkSmilFile(v)) {
				// if there is a smil file, do not use the default video file but the specific version with download suffix
				mFile = PropsUtil.get("lecture2go.media.repository") + "/" + objectHost.getDirectory() + "/" + objectProducer.getHomeDir() + "/" + v.getPreffix() + PropsUtil.get("lecture2go.videoprocessing.downloadsuffix") + ".mp4";
				mFileAbo = PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + v.getPreffix() + ".mp4";
			} else {
				// default handling of all files
				mFile = PropsUtil.get("lecture2go.media.repository") + "/" + objectHost.getDirectory() + "/" + objectProducer.getHomeDir() + "/" + v.getPreffix() + "." + mf;
				mFileAbo = PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + v.getPreffix() + "." + mf;
			}
			
			String command = "ln -s " + mFile + " " + mFileAbo;
			
			File medFile = new File(mFile);
			File aboFile = new File(mFileAbo);
			
			if (medFile.isFile() && !aboFile.isFile()){
				try {
					runCmd.exec(command);
					ret = true;
				} catch (IOException e) {
					//e.printStackTrace();
				}				
			}
		}
		return ret;
	}
	
	/**
	 * Remove the symbolic link for one file from all possible sym link folders
	 * @param filename the name of the file which should be deleted (only filename)
	 */
	public void removeSymbolicLinksForSingularFileIfExisting(String filename){
		// remove symbolic link from download folder if existing
		if (!TextUtils.isEmpty(filename)) {
			File symLinkDownloadfolder = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/" + filename);
			symLinkDownloadfolder.delete(); // just returns false if file not existing
		}
		// remove symbolic link from caption folder if existing (necessary for vtt caption files)
		if (filename.endsWith(".vtt")) {
			File symLinkCaptionFolder = new File(PropsUtil.get("lecture2go.captions.system.path") + "/" + filename);
			symLinkCaptionFolder.delete();
		}
	}
	

	public boolean removeSymbolicLinks(Video v){
		boolean ret = false;
		for (String mf : FileManager.MEDIA_FORMATS) {
			File symLink = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root") + "/"+v.getPreffix() + "." + mf);
			symLink.delete();
			ret=true;
		}
		return ret;
	}
	
	/**
	 * Generates a symbolic link
	 * @param filePath the filepath which is linked to
	 * @param symLinkPath the symbolic link which will be created
	 * @return true if the creation was sucessful, false if not
	 */
	public boolean generateSymLink(String filePath, String symLinkPath) {
		boolean ret = false;
		Runtime runCmd = Runtime.getRuntime();
		String command = "ln -s " + filePath + " " + symLinkPath;
		File file = new File(filePath);
		File symFile = new File(symLinkPath);
		
		if (file.isFile() && !symFile.isFile()){
			try {
				runCmd.exec(command);
				ret = true;
			} catch (IOException e) {
				//e.printStackTrace();
			}				
		}
		return ret;
	}
	
	/**
	 * Creates a symbolic link to the downloadable file
	 * @param host the host to determine the correct directory of the video file
	 * @param video the video
	 * @param producer the producer to determine the correct directory of the video file
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void createSymLinkToDownloadableFile(Host host, Video video, Producer producer) throws FileNotFoundException, DocumentException {
		String homePath = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
		// parses optimal file from the smil file
		String filename = getFileNameOfVideoWithReasonableBitrate(host, video, producer);
		String filePath = homePath + filename;
		// set prefix according to openaccess filename or secured
		String prefix = video.getOpenAccess()==1 ? video.getPreffix() : video.getSPreffix();
		String symLinkPath = homePath + prefix + PropsUtil.get("lecture2go.videoprocessing.downloadsuffix") + ".mp4";
		generateSymLink(filePath, symLinkPath);
	}
	
	/**
	 * Returns the filename of the video whose bitrate is nearest to the defined target bitrate
	 * Uses the smil xml file to get the information about the videos.
	 * @param host the host (necessary to determine the path to the smil file)
	 * @param video the video (necessary to determine the path to the smil file)
	 * @param producer the producer (necessary to determine the path to the smil file)
	 * @return the filename of the video with reasonable bitrate
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public String getFileNameOfVideoWithReasonableBitrate(Host host, Video video, Producer producer) throws FileNotFoundException, DocumentException {
		final int targetBitrate = Integer.parseInt(PropsUtil.get("lecture2go.videoprocessing.targetdownloadbitrate"));
		String filename = "";

		String mediaRep = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir();
		// set prefix according to openaccess filename or secured
		String prefix = video.getOpenAccess()==1 ? video.getPreffix() : video.getSPreffix();
		String smilPath = mediaRep + "/" + prefix +".smil";
		
		// read the smil file as an xml document
		Document xml = SAXReaderUtil.read(new FileInputStream(smilPath));
	
		// select all video nodes with a system-bitrate
		List<Node> videoNodes = xml.selectNodes("/smil/body/switch/video[@system-bitrate]");

		if (!videoNodes.isEmpty()) {
			// sort the video nodes by the difference from the system-bitrate to the targetBitrate (ascending)
			Collections.sort(videoNodes, new Comparator<Node>() {
			    @Override
			    public int compare(Node n1, Node n2) {
			    	Integer n1IntervalToTargetBitrate = Math.abs(Integer.valueOf(((Element) n1).attributeValue("system-bitrate")) - targetBitrate);
			    	Integer n2IntervalToTargetBitrate = Math.abs(Integer.valueOf(((Element) n2).attributeValue("system-bitrate")) - targetBitrate);
			        return n1IntervalToTargetBitrate.compareTo(n2IntervalToTargetBitrate);
			    }
			});
			// use the video which bitrate is the nearest to the target bitrate (cast to element)
			Element correctNode = (Element) videoNodes.get(0);
			filename = correctNode.attributeValue("src");
		}
	
		return filename;
	}
}
