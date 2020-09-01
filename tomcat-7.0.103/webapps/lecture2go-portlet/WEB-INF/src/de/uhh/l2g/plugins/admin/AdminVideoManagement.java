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
package de.uhh.l2g.plugins.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.util.HtmlUtils;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.model.Category;
import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Lectureseries_Institution;
import de.uhh.l2g.plugins.model.License;
import de.uhh.l2g.plugins.model.Metadata;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.model.Term;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Category;
import de.uhh.l2g.plugins.model.Video_Creator;
import de.uhh.l2g.plugins.model.Video_Institution;
import de.uhh.l2g.plugins.model.impl.CategoryImpl;
import de.uhh.l2g.plugins.model.impl.CreatorImpl;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.LicenseImpl;
import de.uhh.l2g.plugins.model.impl.MetadataImpl;
import de.uhh.l2g.plugins.model.impl.ProducerImpl;
import de.uhh.l2g.plugins.model.impl.SegmentImpl;
import de.uhh.l2g.plugins.model.impl.VideoImpl;
import de.uhh.l2g.plugins.model.impl.Video_CategoryImpl;
import de.uhh.l2g.plugins.model.impl.Video_CreatorImpl;
import de.uhh.l2g.plugins.model.impl.Video_InstitutionImpl;
import de.uhh.l2g.plugins.service.CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LicenseLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil;
import de.uhh.l2g.plugins.service.TermLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.util.FFmpegManager;
import de.uhh.l2g.plugins.util.FileManager;
import de.uhh.l2g.plugins.util.Htaccess;
import de.uhh.l2g.plugins.util.OaiPmhManager;
import de.uhh.l2g.plugins.util.ProzessManager;
import de.uhh.l2g.plugins.util.S3Manager;
import de.uhh.l2g.plugins.util.Security;
import de.uhh.l2g.plugins.util.VideoGenerationDateComparator;
import de.uhh.l2g.plugins.util.VideoProcessorManager;

public class AdminVideoManagement extends MVCPortlet {

	private final static Logger logger = Logger.getLogger(AdminVideoManagement.class.getName());
	
	public void addSegment(ActionRequest request, ActionResponse response){
		Video reqVideo = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		try {
			reqVideo = VideoLocalServiceUtil.getVideo(reqVideoId);
			request.setAttribute("reqVideo", reqVideo);
		} catch (Exception e) {
			
		}
		String backURL = request.getParameter("backURL");
		request.setAttribute("backURL", backURL);
		response.setRenderParameter("jspPage", "/admin/segments.jsp");
	}
	
	public void viewVideo(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		// ****** START - DO NOT USE IN PRODUCTION ******
		// this block is temporary for fixing the tagcloud and the lectureseries_creator table 
		//TagcloudLocalServiceUtil.generateForAllVideos();
		//CreatorLocalServiceUtil.updateAllCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId();
		//TagcloudLocalServiceUtil.generateForAllLectureseries();
		// ****** END ******
		// updateSegmentsForVideos();

		// requested producer id
		Long reqPproducerId = (long)0;
		try{reqPproducerId = new Long(request.getParameterMap().get("producerId")[0]);}catch(Exception e){}
		
		// requested lecture series id
		Long reqLectureseriesId = (long)0;
		try{reqLectureseriesId = new Long(request.getParameterMap().get("lectureseriesId")[0]);}catch(Exception e){}
			
		// requested video
		Long reqVideoId = new Long(0);
		Video reqVideo = new VideoImpl(); 
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		try{reqVideo = VideoLocalServiceUtil.getVideo(reqVideoId);}catch(Exception e){}
		
		//hack for empty secure filename on the first upload
		if(reqVideo.getFilename().length()==0 && reqVideo.getSecureFilename().length()==0){
			reqVideo.setSecureFilename(Security.createSecureFileName()+".xx");
			VideoLocalServiceUtil.updateVideo(reqVideo);
		}
		
    	// create symlink to downloadable file if not existing
		VideoLocalServiceUtil.createSymLinkToDownloadableFileIfNotExisting(reqVideoId);
		
		//requested producer
		Producer reqProducer = new ProducerImpl();
		try{
			if(reqVideo.getVideoId()>0)reqProducer = ProducerLocalServiceUtil.getProdUcer(reqVideo.getProducerId());
			else {
				reqProducer = ProducerLocalServiceUtil.getProdUcer(reqPproducerId);
				reqVideo.setProducerId(reqPproducerId);
			}
		}catch(Exception e){}
		request.setAttribute("reqProducer", reqProducer);
		
		// requested lecture series object
		Lectureseries reqLectureseries = new LectureseriesImpl();
		try{
			if(reqVideo.getVideoId()>0)reqLectureseries = LectureseriesLocalServiceUtil.getLectureseries(reqVideo.getLectureseriesId());
			else{
				reqLectureseries = LectureseriesLocalServiceUtil.getLectureseries(reqLectureseriesId);
				reqVideo.setLectureseriesId(reqLectureseriesId);
			}
		}catch(Exception e){}
		request.setAttribute("reqLectureseries", reqLectureseries);
		
		// requested metadata
		Metadata reqMetadata = new MetadataImpl();
		try{
			if(reqVideo.getVideoId()>0)reqMetadata = (MetadataImpl)MetadataLocalServiceUtil.getMetadata(reqVideo.getMetadataId());
			else{
				reqMetadata.setDescription(reqLectureseries.getLongDesc());
				reqMetadata.setPublisher(reqProducer.getFirstName()+" "+reqProducer.getLastName());
				reqMetadata.setLanguage(reqLectureseries.getLanguage());
			}
		}catch(Exception e){}
		request.setAttribute("reqMetadata", reqMetadata);
		
		// requested lecture series list
		List<Lectureseries> reqLectureseriesList = new ArrayList<Lectureseries>();
		try{
			if(reqVideo.getVideoId()>0)reqLectureseriesList = LectureseriesLocalServiceUtil.getFilteredByApprovedSemesterFacultyProducer(1, (long) 0, (long) 0, reqVideo.getProducerId());
			else reqLectureseriesList = LectureseriesLocalServiceUtil.getFilteredByApprovedSemesterFacultyProducer(1, (long) 0, (long) 0, reqPproducerId);
		}catch(Exception e){}
		request.setAttribute("reqLectureseriesList", reqLectureseriesList);

		//requested sub institutions
		List<Video_Institution> reqSubInstitutions = new ArrayList<Video_Institution>();
		reqSubInstitutions = Video_InstitutionLocalServiceUtil.getByVideo(reqVideo.getVideoId());
		request.setAttribute("reqSubInstitutions", reqSubInstitutions);
		
		//requested license
		License reqLicense = new LicenseImpl();
		try {
			reqLicense = LicenseLocalServiceUtil.getLicense(reqVideo.getLicenseId());
		}catch(Exception e){}
		request.setAttribute("reqLicense", reqLicense);
		
		// requested license list - all licenses to choose from
		List<License> reqLicenseList = new ArrayList<License>();
		try{
			reqLicenseList = LicenseLocalServiceUtil.getLicenses(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}catch(Exception e){}
		request.setAttribute("reqLicenseList", reqLicenseList);
		
		String backURL = request.getParameter("backURL");
		request.setAttribute("backURL", backURL);

		request.setAttribute("reqVideo", reqVideo);
		request.setAttribute("video", reqVideo);
		response.setRenderParameter("jspPage", "/admin/editVideo.jsp");
	}
	
	public void addVideo(ActionRequest request, ActionResponse response) throws SystemException, PortalException {
		//first metadata
		Metadata reqMetadata = new MetadataImpl();
		reqMetadata = MetadataLocalServiceUtil.addMetadata(reqMetadata);
		request.setAttribute("reqMetadata", reqMetadata);

		//lecture series
		Long lectureseriesId = new Long(request.getParameter("lectureseriesId"));
		Lectureseries reqLectureseries = new LectureseriesImpl();
		try{
			reqLectureseries = (Lectureseries) LectureseriesLocalServiceUtil.getLectureseries(lectureseriesId); 
			Category ctgr = new CategoryImpl();
			try{ctgr = CategoryLocalServiceUtil.getCategory(reqLectureseries.getCategoryId());}catch(Exception e){}			
		}catch(Exception e){}
		request.setAttribute("reqLectureseries", reqLectureseries);
		
		//producer
		Long producerId = new Long(request.getParameter("producerId"));
		ProducerImpl reqProducer = new ProducerImpl();
		reqProducer = (ProducerImpl)ProducerLocalServiceUtil.getProdUcer(producerId);
		request.setAttribute("reqProducer", reqProducer);
	
		//license
		// use the first selectable license as a default license on video creation
		License license = new LicenseImpl();
		try {
			license = LicenseLocalServiceUtil.getBySelectable(true).get(0);
		}catch(Exception e){}
		request.setAttribute("reqLicense", license);
		
		//video
		Video newVideo = new VideoImpl();
		//long newVideoId = counterLocalServiceUtil.increment(Video.class.getName());
		//Video newVideo = videoPersistence.create(newVideoId);
		//newVideo = VideoLocalServiceUtil.createVideo(videoId);
		newVideo.setProducerId(producerId);
		if(lectureseriesId>0)newVideo.setLectureseriesId(lectureseriesId);
		else {
			java.util.Date date= new java.util.Date();
			newVideo.setLectureseriesId(-date.getTime());
		}
		newVideo.setHostId(reqProducer.getHostId());
		newVideo.setMetadataId(reqMetadata.getMetadataId());
		newVideo.setRootInstitutionId(reqProducer.getInstitutionId());
		newVideo.setOpenAccess(0);
		newVideo.setSecureFilename(Security.createSecureFileName()+".xx");
		newVideo.setCitation2go(1);
		newVideo.setLicenseId(license.getLicenseId());
		//save it
		Video video = VideoLocalServiceUtil.addVideo(newVideo);
		request.setAttribute("reqVideo", newVideo);
		request.setAttribute("video", newVideo);

		// update uploads for producer
		ProducerImpl p = new ProducerImpl();
		p = (ProducerImpl)ProducerLocalServiceUtil.getProducer(producerId);
		int n = 0;
		n = VideoLocalServiceUtil.getByProducer(p.getProducerId()).size();
		p.setNumberOfProductions(n);
		ProducerLocalServiceUtil.updateProducer(p);
			
		// requested lecture series list
		List<Lectureseries> reqLectureseriesList = new ArrayList<Lectureseries>();
		try{reqLectureseriesList = LectureseriesLocalServiceUtil.getFilteredByApprovedSemesterFacultyProducer(1, (long) 0, (long) 0, producerId);}catch(Exception e){}
		request.setAttribute("reqLectureseriesList", reqLectureseriesList);

		//requested sub institutions
		List<Video_Institution> reqSubInstitutions = new ArrayList<Video_Institution>();
		reqSubInstitutions = Video_InstitutionLocalServiceUtil.getByVideo(video.getVideoId());
		request.setAttribute("reqSubInstitutions", reqSubInstitutions);

		
		// requested license list
		List<License> reqLicenseList = new ArrayList<License>();
		try{
			//reqLicenseList = LicenseLocalServiceUtil.getBySelectable(true);
			reqLicenseList = LicenseLocalServiceUtil.getLicenses(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}catch(Exception e){}
		request.setAttribute("reqLicenseList", reqLicenseList);

		//update lg_video_institution table and update previewVideoId in lg_lecturseries table
		if(lectureseriesId>0){
			LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(LectureseriesLocalServiceUtil.getLectureseries(lectureseriesId));
			List<Lectureseries_Institution> li = Lectureseries_InstitutionLocalServiceUtil.getByLectureseries(lectureseriesId);
			ListIterator<Lectureseries_Institution> i = li.listIterator();
			while(i.hasNext()){
				Institution ins = InstitutionLocalServiceUtil.getInstitution(i.next().getInstitutionId());
				Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
				vi.setVideoId(video.getVideoId());
				vi.setInstitutionId(ins.getInstitutionId());
				vi.setInstitutionParentId(ins.getParentId());
				Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
			}
		}else{//no lecture series 
			Institution ins = InstitutionLocalServiceUtil.getInstitution(video.getRootInstitutionId());
			Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
			vi.setVideoId(video.getVideoId());
			vi.setInstitutionId(video.getRootInstitutionId());
			
			if(ins.getLevel()==1)vi.setInstitutionParentId(0);
			else vi.setInstitutionParentId(ins.getParentId());
			
			Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
		}
	
		//creators
		JSONArray creatorsArray = CreatorLocalServiceUtil.getJSONCreatorsByLectureseriesId(lectureseriesId);
		for (int i = 0; i< creatorsArray.length(); i++){
			org.json.JSONObject creator;
			try {
				creator = creatorsArray.getJSONObject(i);
				Long creatorId= creator.getLong("creatorId");
				Video_Creator vc = new Video_CreatorImpl();
				vc.setCreatorId(creatorId);
				vc.setVideoId(newVideo.getVideoId());
				Video_CreatorLocalServiceUtil.addVideo_Creator(vc);
			} catch (JSONException e) {
				//e.printStackTrace();
			}
		}
		
		//add tags to tag cloud
		TagcloudLocalServiceUtil.generateForVideo(video.getVideoId());
		//
		String backURL = request.getParameter("backURL");
		request.setAttribute("backURL", backURL);
		response.setRenderParameter("jspPage", "/admin/editVideo.jsp");
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException, IOException {
		String userID = resourceRequest.getRemoteUser();
		Long userId = new Long(userID);
		String resourceID = resourceRequest.getResourceID();
		Long videoId = ParamUtil.getLong(resourceRequest, "videoId");
		
		Video video = new VideoImpl();
		try {
			video = VideoLocalServiceUtil.getVideo(videoId);
		} catch (Exception e) {
			
		}
		
		Metadata metadata = new MetadataImpl();
		try {
			Long metadataId = video.getMetadataId();
			metadata = MetadataLocalServiceUtil.getMetadata(metadataId);
		} catch (PortalException e) {
			//e.printStackTrace();
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		if(resourceID.equals("updateVideoFileName")){
			String fileName = ParamUtil.getString(resourceRequest, "fileName");
			String secureFileName = ParamUtil.getString(resourceRequest, "secureFileName");
			String containerFormat = fileName.split("\\.")[fileName.split("\\.").length-1];
			//update data base
			try {
				video.setFilename(fileName);
				video.setSecureFilename(secureFileName);
				video.setContainerFormat(containerFormat);
				video.setUploadDate(new Date());
				VideoLocalServiceUtil.updateVideo(video);
				FFmpegManager.updateFfmpegMetadata(video);
				//update thumbs
				String image="";
				String fileLocation="";
				if(video.getOpenAccess()==1){
					image = video.getPreffix()+".jpg";
					fileLocation = ProducerLocalServiceUtil.getProdUcer(video.getProducerId()).getHomeDir() + "/" + video.getFilename();
				}else{
					image = video.getSPreffix()+".jpg";
					fileLocation = ProducerLocalServiceUtil.getProdUcer(video.getProducerId()).getHomeDir() + "/" + video.getSecureFilename();
				}
				String thumbnailLocation = PropsUtil.get("lecture2go.images.system.path") + "/" + image;
				//delete old thumbs
				ProzessManager pm = new ProzessManager();
				pm.deleteThumbnails(video);
				
				//create new thumbs
				VideoLocalServiceUtil.createThumbnailsIfNotExisting(video.getVideoId());
				
				//and thumbs for segments
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
			} catch (NumberFormatException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			} catch (PortalException e) {
				//e.printStackTrace();
			}
			
			JSONObject json = JSONFactoryUtil.createJSONObject();
			writeJSON(resourceRequest, resourceResponse, json);
		}
		
		if(resourceID.equals("updateThumbnail")){
			ProzessManager pm = new ProzessManager();
			Host host = HostLocalServiceUtil.createHost(0);
			Producer producer = ProducerLocalServiceUtil.createProducer(0);
			String fileLocation ="";
			try{
				producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
				host = HostLocalServiceUtil.getHost(video.getHostId());
				fileLocation = ProducerLocalServiceUtil.getProdUcer(video.getProducerId()).getHomeDir() + "/";
			}catch(Exception e){
				//
			}
			
			//
			String image="";
			String thumbnailLocation = "";
			float time = ParamUtil.getFloat(resourceRequest, "inputTime");
			
			//proceed only if time > 0
			if(time > 0){
				//create new thumb nail 
				if(video.getOpenAccess()==1){
					image = video.getPreffix()+".jpg";
					try {
						if(VideoLocalServiceUtil.checkSmilFile(video)){
							fileLocation = fileLocation + pm.getFileNameOfVideoWithReasonableBitrate(host, video, producer);
						}else{
							fileLocation = fileLocation + video.getFilename();
						}
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}else{
					image = video.getSPreffix()+".jpg";
					try {
						if(VideoLocalServiceUtil.checkSmilFile(video)){
							fileLocation = fileLocation + pm.getFileNameOfVideoWithReasonableBitrate(host, video, producer);
						}else{
							fileLocation = fileLocation + video.getSecureFilename();
						}						
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
				//
				try {
					thumbnailLocation = PropsUtil.get("lecture2go.images.system.path") + "/" + image;
					System.out.println("fileLocation ### "+fileLocation+" ### "+"thumbnailLocation ### "+thumbnailLocation);
					FFmpegManager.createThumbnail(fileLocation, thumbnailLocation, time);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		
		if(resourceID.equals("convertVideo")){
			String workflow = ParamUtil.getString(resourceRequest, "workflow");
			String additionalPropertiesString = ParamUtil.getString(resourceRequest, "additionalProperties");
			
			// the additionalProperties is given as a stringified JSON-Object, create a real JSON object for further processing
	 	    JSONObject additionalProperties = null;
	 	    try {
	 	    	additionalProperties = JSONFactoryUtil.createJSONObject(additionalPropertiesString);
	 	    } catch (Exception e) {
	 	    	
	 	    }
	 	    			
			JSONObject json = JSONFactoryUtil.createJSONObject();
			// if activated, notify the video processor to convert the video
			if (PropsUtil.contains("lecture2go.videoprocessing.provider") && (video.getContainerFormat().equalsIgnoreCase("mp4"))) {
				String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion");
				
				boolean isVideoConversionStarted;
				if (workflow.isEmpty()) {
					// the default case, use the workflow specified in properties file
					isVideoConversionStarted = VideoProcessorManager.startVideoConversion(video.getVideoId());
				} else {
					// another workflow is specified, use this
					isVideoConversionStarted = VideoProcessorManager.startVideoConversion(video.getVideoId(), workflow, additionalProperties);
				}
				//create thumbnails if not existing, this may be necessary if the thumbnails were generated and deleted by the video-processor (happens with the video caption integration)
				VideoLocalServiceUtil.createThumbnailsIfNotExisting(video.getVideoId());
				
				if (isVideoConversionStarted) {
					json.put("status", Boolean.TRUE);
				} else {
					json.put("status", Boolean.FALSE);
				}
			}
			writeJSON(resourceRequest, resourceResponse, json);
		}

		
		if(resourceID.equals("getVideoConversionStatus")){
			JSONObject json = JSONFactoryUtil.createJSONObject();
			// if activated, notify the video processor to convert the video
			if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
				String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion");
				String videoConversionStatus = VideoProcessorManager.getSimpleVideoConversionStatusForVideoId(video.getVideoId());
				
				json.put("videoConversionStatus", videoConversionStatus);
			}
			writeJSON(resourceRequest, resourceResponse, json);
		}
		
		if(resourceID.equals("getVideoConversionWorkflow")){
			JSONObject json = JSONFactoryUtil.createJSONObject();
			// get the video conversion workflow
			if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
				String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion");
				String videoConversionWorkflow = VideoProcessorManager.getVideoConversionWorkflow(video.getVideoId());
				
				json.put("videoConversionWorkflow", videoConversionWorkflow);
			}
			writeJSON(resourceRequest, resourceResponse, json);
		}
		
		if(resourceID.equals("updateAll")){
			ArrayList<String> errors = new ArrayList();
			//description start
			String description = ParamUtil.getString(resourceRequest, "description");
			metadata.setDescription(description);
			try {
				MetadataLocalServiceUtil.updateMetadata(metadata);
			} catch (SystemException e) {
				errors.add("DESCRIPTION_UPDATE_FAILED");
			}			
			//description end
			
			//license
			Long licenseId = ParamUtil.getLong(resourceRequest, "license");
			video.setLicenseId(licenseId);
			
			//creators start
			String creatorsJsonArray = ParamUtil.getString(resourceRequest, "creatorsJsonArray");
			try {
				com.liferay.portal.kernel.json.JSONArray creatorsArray = JSONFactoryUtil.createJSONArray(creatorsJsonArray);
				// remove creators for video
				try {
					Video_CreatorLocalServiceUtil.deleteByVideoId(videoId);
					// and update with new creators from list
					for (int i = 0; i < creatorsArray.length(); i++) {
						com.liferay.portal.kernel.json.JSONObject creator = creatorsArray.getJSONObject(i);
						Long creatorId = creator.getLong("creatorId");
						String firstName = creator.getString("firstName");
						String lastName = creator.getString("lastName");
						String middleName = creator.getString("middleName");
						String jobTitle = creator.getString("jobTitle");
						String gender = creator.getString("gender");
						String fullName = creator.getString("fullName");
						String affiliation = creator.getString("affiliation");
						String orcidId = creator.getString("orcidId");


						Video_Creator vc = Video_CreatorLocalServiceUtil.createVideo_Creator(0);
						Long newCreatorId = new Long(0);
						// if creator exists in DB, just add to video
						if (creatorId > new Long(0)) {
							newCreatorId = creatorId;
						} else {
							// if new creator doesn't exits in the creators DB
							// (check by full name),
							// create a new one in DB and add to video
							List<Creator> cL = CreatorLocalServiceUtil.getByFullName(fullName);
							if (cL.size() == 0) {
								Creator c = CreatorLocalServiceUtil.createCreator(0);
								c.setFirstName(firstName);
								c.setLastName(lastName);
								c.setMiddleName(middleName);
								c.setJobTitle(jobTitle);
								c.setGender(gender);
								c.setFullName(fullName);
								c.setAffiliation(affiliation);
								c.setOrcidId(orcidId);
								newCreatorId = CreatorLocalServiceUtil.addCreator(c).getCreatorId();
							} else {
								newCreatorId = cL.listIterator().next().getCreatorId();
							}
						}
						vc.setCreatorId(newCreatorId);
						vc.setVideoId(videoId);
						List<Video_Creator> vcl = new ArrayList<Video_Creator>();
						vcl = Video_CreatorLocalServiceUtil.getByVideoCreator(videoId, newCreatorId);
						if (vcl.size() == 0)
							Video_CreatorLocalServiceUtil.addVideo_Creator(vc);
					}
				} catch (SystemException e) {
					errors.add("CREATORS_UPDATE_FAILED_1");
				}
				// now update creators for the whole lecture series
				Long lId = video.getLectureseriesId();
				try {
					if (lId > 0) CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(lId);
				} catch (SystemException e) {
					errors.add("CREATORS_UPDATE_FAILED_2");
				}
			} catch (com.liferay.portal.kernel.json.JSONException e) {
				errors.add("CREATORS_UPDATE_FAILED_3");
			}		
			//creators end
			
			//sub institutions start
			String subInstitutions = ParamUtil.getString(resourceRequest, "subInstitutions");
			try {
				com.liferay.portal.kernel.json.JSONArray institutionsArray = JSONFactoryUtil.createJSONArray(subInstitutions);
				//remove sub-institutions for video
				try {
					Video_InstitutionLocalServiceUtil.removeByVideoId(videoId);
					//and update with new institutions from list
					if(institutionsArray.length()>0){
						for (int i = 0; i< institutionsArray.length(); i++){
							JSONObject institution =  institutionsArray.getJSONObject(i);
							Long institutionId= institution.getLong("institutionId");
							Institution in = InstitutionLocalServiceUtil.createInstitution(0);
							try {
								in = InstitutionLocalServiceUtil.getInstitution(institutionId);
							} catch (PortalException e) {}
							//
							List<Video_Institution> vil = new ArrayList<Video_Institution>();
							vil = Video_InstitutionLocalServiceUtil.getByVideoAndInstitution(videoId, institutionId);
							
							Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
							vi.setInstitutionId(in.getInstitutionId());
							vi.setVideoId(videoId);
							if(in.getLevel()==1)vi.setInstitutionParentId(0);
							else vi.setInstitutionParentId(in.getParentId());
							if(vil.size()==0)Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
						}						
					}
				} catch (SystemException e) {
					errors.add("SUB_INSTITUTIONS_UPDATE_FAILED_1");
				}
			} catch (com.liferay.portal.kernel.json.JSONException e) {
				errors.add("SUB_INSTITUTIONS_UPDATE_FAILED_2");
			}			
			//sub institutions end
			
			//metadata start
			String title = ParamUtil.getString(resourceRequest, "title");
			String datetime = ParamUtil.getString(resourceRequest, "datetimepicker");
			String language = ParamUtil.getString(resourceRequest, "language");
			String tags = ParamUtil.getString(resourceRequest, "tags");
			String publisher = ParamUtil.getString(resourceRequest, "publisher");
			Long newLsId = ParamUtil.getLong(resourceRequest, "lectureseriesId");
			Long termId = ParamUtil.getLong(resourceRequest, "termId");
			Long categoryId = ParamUtil.getLong(resourceRequest, "categoryId");
			Integer citationAllowed = ParamUtil.getInteger(resourceRequest, "citationAllowedCheckbox");
	 	    String password = ParamUtil.getString(resourceRequest, "password");	
	 	    //old lecture series
	 	    Lectureseries oldLect = LectureseriesLocalServiceUtil.createLectureseries(0);
			try { oldLect = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId()); } catch (Exception e) {}
			Long oldLsId = oldLect.getLectureseriesId();
			//new lecture series
			Lectureseries newLect = LectureseriesLocalServiceUtil.createLectureseries(0);
			try { newLect = LectureseriesLocalServiceUtil.getLectureseries(newLsId); } catch (Exception e1) { } 
			
			Category ctgr = CategoryLocalServiceUtil.createCategory(0);
			
			//check the lecture series id for further meta data informations update
			try {
				video.setTitle(title);
				video.setLectureseriesId(newLsId);
				video.setTags(tags);
				if(newLsId.longValue()>0){
					video.setLectureseriesId(newLsId);
					//
					termId = newLect.getTermId();
					categoryId = newLect.getCategoryId();
					//update lg_video_institution table
					Video_InstitutionLocalServiceUtil.removeByVideoId(video.getVideoId());
					List<Lectureseries_Institution> li = Lectureseries_InstitutionLocalServiceUtil.getByLectureseries(newLsId);
					ListIterator<Lectureseries_Institution> l_i = li.listIterator();
					//institutions for video
					while(l_i.hasNext()){
						Institution in = InstitutionLocalServiceUtil.createInstitution(0);
						Lectureseries_Institution lectinst = l_i.next();
						in = InstitutionLocalServiceUtil.getInstitution(lectinst.getInstitutionId());
						Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
						vi.setVideoId(video.getVideoId());
						vi.setInstitutionId(lectinst.getInstitutionId());
						vi.setInstitutionParentId(in.getParentId());
						Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
					}
					LectureseriesLocalServiceUtil.updateLectureseries(newLect);
				}else{
					java.util.Date date= new java.util.Date();
					video.setLectureseriesId(-date.getTime());
					//update institution for video only without lecture series
					List<Video_Institution> vinst = Video_InstitutionLocalServiceUtil.getByVideo(video.getVideoId());
					ListIterator<Video_Institution> vinstItt = vinst.listIterator();
					while(vinstItt.hasNext()){
						Institution inst = InstitutionLocalServiceUtil.getById(vinstItt.next().getInstitutionId());
						Institution parent = InstitutionLocalServiceUtil.getById(inst.getParentId());
					}
				}

				video.setTermId(termId);
				//and update categories in DB for video
				Video_CategoryLocalServiceUtil.removeByVideo(videoId);
				Video_Category vc = Video_CategoryLocalServiceUtil.createVideo_Category(0);
				vc.setVideoId(videoId);
				vc.setCategoryId(categoryId);
				if(categoryId>0)Video_CategoryLocalServiceUtil.addVideo_Category(vc);
				
				//update date and time for this video if changed 
				int compartion = VideoGenerationDateComparator.compare(video.getGenerationDate(), datetime);
				//override the video date_time stamp!
				if (compartion !=0)video.setGenerationDate(datetime);
				
				//set citation 
				video.setCitation2go(citationAllowed);
				//password
				video.setPassword(password);
				// update video
				VideoLocalServiceUtil.updateVideo(video);
				//update lecture series fields for latest open access video id 
				// preview video id 
				if(newLsId.longValue()==0 && newLsId.longValue()!=oldLsId.longValue()){
					//update new and old lecture series
					LectureseriesLocalServiceUtil.updateOpenAccess(video, oldLect);
					LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(oldLect);
					LectureseriesLocalServiceUtil.updateUploadAndGenerationDate(oldLect);	
				}
				//update lecture series fields for latest open access video id 
				// preview video id 
				if(oldLsId.longValue()>0 && newLsId.longValue()!=oldLsId.longValue()){
					//update new and old lecture series
					LectureseriesLocalServiceUtil.updateOpenAccess(video, newLect);
					LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(newLect);
					LectureseriesLocalServiceUtil.updateUploadAndGenerationDate(newLect);
					//
					LectureseriesLocalServiceUtil.updateOpenAccess(video, oldLect);
					LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(oldLect);
					LectureseriesLocalServiceUtil.updateUploadAndGenerationDate(oldLect);						
				}
				//update lg_lectureseries_creators
				if(newLsId.longValue() != oldLsId.longValue())
				{
					try{
						if(newLsId.longValue()>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(newLsId);
					}catch(SystemException e){}
					//
					try{
						if(oldLsId.longValue()>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(oldLsId);
					}catch(SystemException e){}
				}
				
			} catch (NumberFormatException e) {
				errors.add("METADATA_UPDATE_FAILED_1");
			} catch (SystemException e) {
				errors.add("METADATA_UPDATE_FAILED_2");
			} catch (PortalException e) {
				errors.add("METADATA_UPDATE_FAILED_3");
			}
			//metadata
			try {
				metadata.setTitle(title);
				metadata.setPublisher(publisher);
				metadata.setLanguage(language);
				MetadataLocalServiceUtil.updateMetadata(metadata);
			} catch (NumberFormatException e) {
				errors.add("METADATA_UPDATE_FAILED_4");
			} catch (SystemException e) {
				errors.add("METADATA_UPDATE_FAILED_5");
			}
			
			//update tag cloud for this video
			TagcloudLocalServiceUtil.generateForVideo(video.getVideoId());
			//update tag cloud for the lectureseries of this video
			if(video.getLectureseriesId()>0)TagcloudLocalServiceUtil.generateForLectureseries(video.getLectureseriesId());
			// update tag cloud for the old lectureseries of this video
			if(newLsId.longValue() != oldLsId.longValue())
				if(oldLsId>0)TagcloudLocalServiceUtil.generateForLectureseries(oldLsId);
			
			//rebuild rss
			// generate RSS
			ProzessManager pm = new ProzessManager();
			for (String f: FileManager.MEDIA_FORMATS) {           
				try {
					pm.generateRSS(video, f);
				} catch (Exception e) {
					errors.add("METADATA_UPDATE_FAILED_6");
				} 
			}	 	    
	 	    //metadata end
			
			// update the video in the OAI-PMH repository if existing
			OaiPmhManager.modify(video.getVideoId());
	 	    
			//return errors cont result after update
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("errorsCount", errors.size());
			//
			PrintWriter writer = resourceResponse.getWriter();
	        writer.print(jo.toString());
	        writer.flush();
	        writer.close();
		}
		
		// this method seems not to be used anymore
		/*if(resourceID.equals("updateMetadata")){
			
	 	    String title = ParamUtil.getString(resourceRequest, "title");
			String language = ParamUtil.getString(resourceRequest, "language");
			String tags = ParamUtil.getString(resourceRequest, "tags");
			String publisher = ParamUtil.getString(resourceRequest, "publisher");
			Long lId = ParamUtil.getLong(resourceRequest, "lectureseriesId");
			Long termId = ParamUtil.getLong(resourceRequest, "termId");
			Long categoryId = ParamUtil.getLong(resourceRequest, "categoryId");
			Integer citationAllowed = ParamUtil.getInteger(resourceRequest, "citationAllowedCheckbox");
	 	    String password = ParamUtil.getString(resourceRequest, "password");

			Lectureseries oldLs = new LectureseriesImpl();
			Long oldLsId = video.getLectureseriesId();
			try {
				oldLs = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
			} catch (PortalException e1) {
				//e1.printStackTrace();
			} catch (SystemException e1) {
				//e1.printStackTrace();
			}
			//search tags
			ArrayList<String> tagCloudArrayString = new ArrayList<String>();

			Lectureseries newLect = new LectureseriesImpl();
			Category ctgr = new CategoryImpl();
			//update data base
			try {
				video.setTitle(title);
				video.setLectureseriesId(lId);
				if(lId>0)video.setLectureseriesId(lId);
				else {
					java.util.Date date= new java.util.Date();
					video.setLectureseriesId(-date.getTime());
				}
				video.setTags(tags);
				if(lId>0){
					//update lecture series id for this video first !!!
					//important, because of dependencies
					video.setLectureseriesId(lId);
					VideoLocalServiceUtil.updateVideo(video);

					//forward
					newLect = LectureseriesLocalServiceUtil.getLectureseries(lId);
					//
					termId = newLect.getTermId();
					categoryId = newLect.getCategoryId();
					//update lg_video_institution table
					Video_InstitutionLocalServiceUtil.removeByVideoId(video.getVideoId());
					List<Lectureseries_Institution> li = Lectureseries_InstitutionLocalServiceUtil.getByLectureseries(lId);
					ListIterator<Lectureseries_Institution> l_i = li.listIterator();
					//institutions for video
					while(l_i.hasNext()){
						Institution in = new InstitutionImpl();
						Lectureseries_Institution lectinst = l_i.next();
						in = InstitutionLocalServiceUtil.getInstitution(lectinst.getInstitutionId());
						tagCloudArrayString.add(in.getName());
						Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
						vi.setVideoId(video.getVideoId());
						vi.setInstitutionId(lectinst.getInstitutionId());
						vi.setInstitutionParentId(in.getParentId());
						Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
					}
					//update lectureseries
					LectureseriesLocalServiceUtil.updateOpenAccess(video, newLect);
					LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(newLect);
					
					//add lecture series parameter to tag cloud
					tagCloudArrayString.add(newLect.getName());
					tagCloudArrayString.add(newLect.getNumber());
				}else{
					//update institution for video only without lecture series
					List<Video_Institution> vinst = Video_InstitutionLocalServiceUtil.getByVideo(video.getVideoId());
					ListIterator<Video_Institution> vinstItt = vinst.listIterator();
					while(vinstItt.hasNext()){
						Institution inst = InstitutionLocalServiceUtil.getById(vinstItt.next().getInstitutionId());
						Institution parent = InstitutionLocalServiceUtil.getById(inst.getParentId());
						tagCloudArrayString.add(inst.getName());
						tagCloudArrayString.add(parent.getName());
					}
				}
				//add category and term to tag cloud
				//category
				try{ctgr = CategoryLocalServiceUtil.getCategory(categoryId);}catch(Exception e){}			
				tagCloudArrayString.add(ctgr.getName());
				//term
				try{
					Term t = TermLocalServiceUtil.getTerm(termId);
					tagCloudArrayString.add(t.getPrefix()+" "+t.getYear());
				}catch(Exception e){}
				video.setTermId(termId);
				//
				//and update categories in DB for video
				Video_CategoryLocalServiceUtil.removeByVideo(videoId);
				Video_Category vc = new Video_CategoryImpl();
				vc.setVideoId(videoId);
				vc.setCategoryId(categoryId);
				if(categoryId>0)Video_CategoryLocalServiceUtil.addVideo_Category(vc);
				//
				//title to tag cloud
				tagCloudArrayString.add(video.getTitle());
				
				//add creators to tag cloud
				JSONArray creatorsArray = CreatorLocalServiceUtil.getJSONCreatorsByVideoId(videoId);
				for (int i = 0; i< creatorsArray.length(); i++){
					org.json.JSONObject creator;
					try {
						creator = creatorsArray.getJSONObject(i);
						tagCloudArrayString.add(creator.getString("fullName"));
					} catch (JSONException e) {
						////e.printStackTrace();
					}
				}
				//update tag cloud for this video
				TagcloudLocalServiceUtil.updateByObjectIdAndObjectClassType(tagCloudArrayString, video.getClass().getName(), video.getVideoId());
				//set citation 
				video.setCitation2go(citationAllowed);
				//password
				video.setPassword(password);
				// update video
				VideoLocalServiceUtil.updateVideo(video);
				// refresh open access and previewVideoId for old lecture if lid > 0
				if(oldLs.getLectureseriesId()>0) {
					LectureseriesLocalServiceUtil.updateOpenAccess(video, oldLs);
					LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(oldLs);
				}
				
				//update lg_lectureseries_creators
				if(lId.longValue() != oldLsId.longValue())
				{
					try{
						if(lId>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(lId);
					}catch(SystemException e){}
					
					try{
						if(oldLsId>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(oldLsId);
					}catch(SystemException e){}
				}
				
			} catch (NumberFormatException e) {
				//System.out.println(e);
			} catch (SystemException e) {
				//System.out.println(e);
			} catch (PortalException e) {
				////e.printStackTrace();
			}
			//metadata
			try {
				String description = ParamUtil.getString(resourceRequest, "description");
				metadata.setDescription(description);				
				metadata.setTitle(title);
				metadata.setPublisher(publisher);
				metadata.setLanguage(language);
				MetadataLocalServiceUtil.updateMetadata(metadata);
			} catch (NumberFormatException e) {
//				System.out.println(e);
			} catch (SystemException e) {
//				System.out.println(e);
			}
			//rebuild rss
			// generate RSS
			ProzessManager pm = new ProzessManager();
			for (String f: FileManager.MEDIA_FORMATS) {           
				try {
					pm.generateRSS(video, f);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 
			}			
			JSONObject json = JSONFactoryUtil.createJSONObject();
			//generate new JSON date for auto complete functionality
			writeJSON(resourceRequest, resourceResponse, json);
		}
		*/

		if(resourceID.equals("videoUpdateGenerationDate")){
			String generationDate = ParamUtil.getString(resourceRequest, "generationDate");
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			video.setGenerationDate(generationDate);
			try {
				VideoLocalServiceUtil.updateVideo(video);
				jo.put("generationDate", generationDate);
				writeJSON(resourceRequest, resourceResponse, jo);
			} catch (SystemException e) {
				////e.printStackTrace();
			}
			
			// update the video in the OAI-PMH repository if existing
			OaiPmhManager.modify(video.getVideoId());
		}
		
		if (resourceID.equals("handleVttUpload")) {
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			try {
				VideoLocalServiceUtil.createSymLinkForCaptionIfExisting(videoId);
				writeJSON(resourceRequest, resourceResponse, jo);
			} catch (Exception e) {
				
			}
		}

		if (resourceID.equals("updateHtaccess")) {
			try {
				Host host = HostLocalServiceUtil.getHost(video.getHostId());
				Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
				String url = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/";
				long producerId = video.getProducerId();
				List<Video> lockedVideosByProducer;
				lockedVideosByProducer = VideoLocalServiceUtil.getByProducerAndDownloadLink(producerId, 0);
				Htaccess.makeHtaccess(url, lockedVideosByProducer);
			} catch (Exception e) {
				// 
			} 
		}
			
		if(resourceID.equals("getJSONVideo")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("title", video.getTitle());
			jo.put("playerUris", video.getJsonPlayerUris().toString());
			jo.put("thumbnail", video.getImage());
			writeJSON(resourceRequest, resourceResponse, jo);
		}

		if(resourceID.equals("updateNumberOfProductions")){
			Producer producer = new ProducerImpl();
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			try {
				producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
				// update uploads for producer
				int n = 0;
				n = VideoLocalServiceUtil.getByProducer(producer.getProducerId()).size();
				producer.setNumberOfProductions(n);
				jo.put("numberOfProductions", n);				
				ProducerLocalServiceUtil.updateProducer(producer);
			} catch (Exception e1) {
				jo.put("numberOfProductions", "-1");
			} 
			writeJSON(resourceRequest, resourceResponse, jo);
		}

		if(resourceID.equals("videoUpdateFirstTitle")){
			String firsttitle = ParamUtil.getString(resourceRequest, "firsttitle");
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			video.setTitle(firsttitle);
			try {
				VideoLocalServiceUtil.updateVideo(video);
				jo.put("firsttitle", firsttitle);
				writeJSON(resourceRequest, resourceResponse, jo);
			} catch (SystemException e) {
				////e.printStackTrace();
			}
		}
		
		if(resourceID.equals("getGenerationDate")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("generationDate", video.getGenerationDate());
			writeJSON(resourceRequest, resourceResponse, jo);
		}

		if(resourceID.equals("getFileName")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			String fileName="";
			if(video.getFilename().length()>0)fileName=video.getFilename();
			jo.put("fileName", fileName);
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("getSecureFileName")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			String secureFileName="";
			if(video.getSecureFilename().length()>0)secureFileName=video.getSecureFilename();
			jo.put("secureFileName", secureFileName);
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("getSecureTokenExpirationTime")){
			// the upload is initialized directly, so the token is only valid a short time (1 minute)
			long expiration = System.currentTimeMillis() + (1000*60);
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("secureTokenExpirationTime", String.valueOf(expiration));
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("getSecureToken")){
			String expirationTime = ParamUtil.getString(resourceRequest, "secureTokenExpirationTime");

			JSONObject jo = JSONFactoryUtil.createJSONObject();
			try {
				String secureToken = Security.getSignatureKey(Security.getSignatureKey(expirationTime, String.valueOf(video.getVideoId())));
				jo.put("secureToken", secureToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			writeJSON(resourceRequest, resourceResponse, jo);
		}

		if(resourceID.equals("getShare")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			String url = "";
			if(video.getOpenAccess()==0)url=video.getSecureUrl();
			else url=video.getUrl();
			jo.put("commsyEmbed", video.getEmbedCommsy());
			jo.put("html5Embed", video.getEmbedHtml5());
			jo.put("iframeEmbed", video.getEmbedIframe());
			jo.put("url", url);
			writeJSON(resourceRequest, resourceResponse, jo);
		}		
		
		if(resourceID.equals("videoFileNameExists")){
			String filename = ParamUtil.getString(resourceRequest, "fileName");
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			try {
				List<Video> vl = VideoLocalServiceUtil.getByFilename(filename); 
				if(vl.size()>0)jo.put("exist", "1");
				else jo.put("exist", "0");
			} catch (SystemException e) {
//				//e.printStackTrace();
				jo.put("exist", "0");
			}
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("updateDescription")){
			String description = ParamUtil.getString(resourceRequest, "description");
			metadata.setDescription(description);
			try {
				MetadataLocalServiceUtil.updateMetadata(metadata);
				logger.info("DESCRIPTION_UPDATE_SUCCESS");
			} catch (Exception e) {
				logger.info("DESCRIPTION_UPDATE_FAILED");
			}
			JSONObject json = JSONFactoryUtil.createJSONObject();
			writeJSON(resourceRequest, resourceResponse, json);
		}
		
		if(resourceID.equals("toggleSegmentation")){
			String segmentation = ParamUtil.getString(resourceRequest, "segmentationPermittedCheckbox");
			int sAllowed = new Integer(segmentation);
			if(sAllowed==0)video.setPermittedToSegment(0);
			else video.setPermittedToSegment(1);
			
			try {
				VideoLocalServiceUtil.updateVideo(video);
			} catch (SystemException e) {
//				//e.printStackTrace();
			}
			
			JSONObject json = JSONFactoryUtil.createJSONObject();
			writeJSON(resourceRequest, resourceResponse, json);
		}
		
		if (resourceID.equals("addSegment")) {
			String shortTitle = ParamUtil.getString(resourceRequest, "chortTitle");
			String timeStart = ParamUtil.getString(resourceRequest, "timeStart");
			String timeEnd = ParamUtil.getString(resourceRequest, "timeEnd");
			String text = ParamUtil.getString(resourceRequest, "text");
			String chapter = ParamUtil.getString(resourceRequest, "chapter");
			String comment = ParamUtil.getString(resourceRequest, "comment");

			int chap = 0;
			if (chapter.equals("1") && comment.equals("0"))
				chap = 1;

			if (!shortTitle.trim().equals("") && !timeStart.trim().equals("") && !timeEnd.trim().equals("")) {
				Segment segment = new SegmentImpl();
				segment.setVideoId(videoId);
				segment.setTitle(shortTitle);
				segment.setStart(timeStart);
				segment.setEnd(timeEnd);
				segment.setChapter(chap);
				segment.setDescription(text);
				segment.setUserId(userId);
				try {
					// save
					Segment s = SegmentLocalServiceUtil.addSegment(segment);
					
					JSONObject jo = JSONFactoryUtil.createJSONObject();
					jo.put("chapter", s.getChapter());
					jo.put("description", s.getDescription());
					jo.put("end", s.getEnd());
					jo.put("image", s.getImage());
					jo.put("number", s.getNumber());
					jo.put("segmentId", s.getPrimaryKey());
					jo.put("seconds", s.getSeconds());
					jo.put("start", s.getStart());
					jo.put("title", s.getTitle());
					jo.put("userId", s.getUserId());
					jo.put("videoId", s.getVideoId());
					jo.put("previousSegmentId", SegmentLocalServiceUtil.getPreviusSegmentId(s.getSegmentId()));
					
					// and return response
					writeJSON(resourceRequest, resourceResponse, jo);
				} catch (SystemException e) {
					//e.printStackTrace();
				} catch (PortalException e) {
					//e.printStackTrace();
				}
			}
			//update chapter file (vtt)
			updateVttChapterFile(video);
		}

		if(resourceID.equals("showSegments")){
			String vId = ParamUtil.getString(resourceRequest, "videoId");
			Long vID = new Long(vId);
			com.liferay.portal.kernel.json.JSONArray ja = JSONFactoryUtil.createJSONArray();
			//get segments for video and convert to json array
			try {
				List<Segment> sl= SegmentLocalServiceUtil.getSegmentsByVideoId(vID);
				ListIterator<Segment> sIt = sl.listIterator();
				while(sIt.hasNext()){
					Segment s = sIt.next();
					JSONObject jo = JSONFactoryUtil.createJSONObject();
					jo.put("chapter", s.getChapter());
					jo.put("description", s.getDescription());
					jo.put("end", s.getEnd());
					jo.put("image", s.getImage());
					jo.put("number", s.getNumber());
					jo.put("segmentId", s.getPrimaryKey());
					jo.put("seconds", s.getSeconds());
					jo.put("start", s.getStart());
					jo.put("title", s.getTitle());
					jo.put("userId", s.getUserId());
					jo.put("videoId", s.getVideoId());
					jo.put("previousSegmentId", SegmentLocalServiceUtil.getPreviusSegmentId(s.getSegmentId()));
					ja.put(jo);
				}
				
			} catch (PortalException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			
			writeJSON(resourceRequest, resourceResponse, ja);
		}
		
		if(resourceID.equals("deleteSegment")){
			String sId = ParamUtil.getString(resourceRequest, "segmentId");
			Long sID = new Long(sId);
			//delete requested segment
			try {
				Segment s = SegmentLocalServiceUtil.removeSegment(sID);
				JSONObject jo = JSONFactoryUtil.createJSONObject();
				jo.put("chapter", s.getChapter());
				jo.put("description", s.getDescription());
				jo.put("end", s.getEnd());
				jo.put("image", s.getImage());
				jo.put("number", s.getNumber());
				jo.put("segmentId", s.getPrimaryKey());
				jo.put("seconds", s.getSeconds());
				jo.put("start", s.getStart());
				jo.put("title", s.getTitle());
				jo.put("userId", s.getUserId());
				jo.put("videoId", s.getVideoId());
				writeJSON(resourceRequest, resourceResponse, jo);
			} catch (SystemException e) {
				//e.printStackTrace();
			} catch (PortalException e) {
				//e.printStackTrace();
			}
			//update chapter file (vtt)
			updateVttChapterFile(video);
		}

		if(resourceID.equals("isFirstUpload")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			if(video.getFilename().isEmpty())jo.put("firstUpload", "1");
			else jo.put("firstUpload", "0");
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("defaultContainer")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("containerFormat", video.getContainerFormat());
			writeJSON(resourceRequest, resourceResponse, jo);
		}
		
		if(resourceID.equals("deleteFile")){
			String fileName = ParamUtil.getString(resourceRequest, "fileName");
			String mainContainerFormat = "";

			//delete file
			String fPath="";
			try {
				fPath = PropsUtil.get("lecture2go.media.repository")+"/"+HostLocalServiceUtil.getByHostId(video.getHostId()).getDirectory()+"/"+ProducerLocalServiceUtil.getProducer(video.getProducerId()).getHomeDir()+"/";
				mainContainerFormat = VideoLocalServiceUtil.getVideo(videoId).getContainerFormat();
			} catch (PortalException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			JSONArray jarr = new JSONArray();
			//delete all
			
			if(fileName.endsWith(mainContainerFormat)){
				//delete all file on disc 
				//but not from DB
				JSONArray jVids = VideoLocalServiceUtil.getJSONVideo(video.getVideoId());
				for (int i = 0; i < jVids.length(); i++) {
					try {
						org.json.JSONObject o = jVids.getJSONObject(i);
						String fs = (String) o.get("name");
						File f = new File(fPath+fs);
						if(f.delete()){
							o.put("fileId", fs.replace(".", ""));
						}else{
							o.put("fileId", "");
						}
						jarr.put(o);
					} catch (JSONException e) {
						//e.printStackTrace();
					}
				}
				ProzessManager pm = new ProzessManager();
				try {
					pm.deleteFilesImagesFromVideo(video);
				} catch (Exception e) {
					//e.printStackTrace();
				} 
				
				//update entries for lecture series object such as last open access video id
				// and previe video for open access related videos
				
				Lectureseries ls = LectureseriesLocalServiceUtil.createLectureseries(0);
				try {
					ls = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
					if(ls.getLectureseriesId()>0) {
						LectureseriesLocalServiceUtil.updateOpenAccess(video, ls);
						LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(ls);
					}
				} catch (Exception e) {
					logger.info("FAILED_TO_FETCH_OR_UPDATE_LECTURESERIES ");
				} 
				
				// delete all created files from the video-processor if activated
				if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
					// send DELETE request to video processor
					VideoProcessorManager.deleteVideoConversion(video.getVideoId());
				}
			}else{
				org.json.JSONObject o = new org.json.JSONObject();
					try {
						File f = new File(fPath+fileName);
						if(f.delete()){
							o.put("fileId", fileName.replace(".", ""));
						}else{
							o.put("fileId", "");
						}
						jarr.put(o);
					} catch (JSONException e) {
						//e.printStackTrace();
					}
			}
			
			// clean up symbolic links for the deleted file if there are any (e.g. download-folder, caption-folder)
			ProzessManager pm = new ProzessManager();
			pm.removeSymbolicLinksForSingularFileIfExisting(fileName);
		
			writeJSON(resourceRequest, resourceResponse, jarr);
		}
		
		if(resourceID.equals("getJSONCreator")){
			String creatorId = ParamUtil.getString(resourceRequest, "creatorId");
			Long cId = new Long(0);
			try{
				cId = new Long(creatorId);
			}catch(Exception e){
				//e.printStackTrace();
			}
			JSONArray json = new JSONArray();
			try {
				json = CreatorLocalServiceUtil.getJSONCreator(cId);
			} catch (PortalException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			writeJSON(resourceRequest, resourceResponse, json);			
		}
		
		if(resourceID.equals("getJSONAllCreators")){
			
			JSONArray json = CreatorLocalServiceUtil.getJSONCreatorsByVideoId(video.getVideoId());
			
			writeJSON(resourceRequest, resourceResponse, json);			
		}
		
		if(resourceID.equals("updateCreators")){
			String creators = ParamUtil.getString(resourceRequest, "creator");
			try {
				JSONArray creatorsArray = new JSONArray(creators);
				//remove creators for video
				try {
					Video_CreatorLocalServiceUtil.deleteByVideoId(videoId);			
					//and update with new creators from list
					for (int i = 0; i< creatorsArray.length(); i++){
						org.json.JSONObject creator =  creatorsArray.getJSONObject(i);
						Long creatorId= creator.getLong("creatorId");
						String firstName = creator.getString("firstName");
						String lastName = creator.getString("lastName");
						String middleName = creator.getString("middleName");
						String jobTitle = creator.getString("jobTitle");
						String gender = creator.getString("gender");
						String fullName = creator.getString("fullName");
						String affiliation = creator.getString("affiliation");
						String orcidId = creator.getString("orcidId");
						
						Video_Creator vc = new Video_CreatorImpl();
						Long newCreatorId = new Long(0);
						//if creator exists in DB, just add to video
						if(creatorId>new Long(0)){
							newCreatorId = creatorId;
						}else{ 
							//if new creator doesn't exits in the creators DB (check by full name),
							//create a new one in DB and add to video
							List<Creator> cL = CreatorLocalServiceUtil.getByFullName(fullName);
							if(cL.size()==0){
								Creator c = new CreatorImpl();
								c.setFirstName(firstName);
								c.setLastName(lastName);
								c.setMiddleName(middleName);
								c.setJobTitle(jobTitle);
								c.setGender(gender);
								c.setFullName(fullName);
								c.setAffiliation(affiliation);
								c.setOrcidId(orcidId);
								newCreatorId = CreatorLocalServiceUtil.addCreator(c).getCreatorId();
							}else{
								newCreatorId = cL.listIterator().next().getCreatorId();
							}
						}
						vc.setCreatorId(newCreatorId);
						vc.setVideoId(videoId);
						List<Video_Creator> vcl = new ArrayList<Video_Creator>();
						vcl = Video_CreatorLocalServiceUtil.getByVideoCreator(videoId, newCreatorId);
						if(vcl.size()==0)Video_CreatorLocalServiceUtil.addVideo_Creator(vc);
					}
				} catch (SystemException e) {
					//e.printStackTrace();
				}
				//now update creators for the whole lecture series
				Long lId = video.getLectureseriesId();
				try{
					if(lId>0)CreatorLocalServiceUtil.updateCreatorsForLectureseriesOverTheAssigenedVideosByLectureseriesId(lId);
				}catch(SystemException e){}
			} catch (JSONException e) {
				//e.printStackTrace();
			}
			writeJSON(resourceRequest, resourceResponse, CreatorLocalServiceUtil.getJSONCreatorsByVideoId(videoId));			
		}
		
		if(resourceID.equals("updateSubInstitutions")){
			String subInstitutions = ParamUtil.getString(resourceRequest, "subInstitution");
			try {
				JSONArray institutionsArray = new JSONArray(subInstitutions);
				try {
					//remove sub-institutions for video
					Video_InstitutionLocalServiceUtil.removeByVideoId(videoId);
					//and update with new institutions from list
					if(institutionsArray.length()>0){
						for (int i = 0; i< institutionsArray.length(); i++){
							org.json.JSONObject institution =  institutionsArray.getJSONObject(i);
							Long institutionId= institution.getLong("institutionId");
							Institution in = new InstitutionImpl();
							try {
								in = InstitutionLocalServiceUtil.getInstitution(institutionId);
								System.out.print("LEVEEEEELLL---->"+in.getLevel());
							} catch (PortalException e) {
								//e.printStackTrace();
							}
							List<Video_Institution> vil = new ArrayList<Video_Institution>();
							vil = Video_InstitutionLocalServiceUtil.getByVideoAndInstitution(videoId, institutionId);
							
							Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
							vi.setInstitutionId(in.getInstitutionId());
							vi.setVideoId(videoId);
							if(in.getLevel()==1)vi.setInstitutionParentId(0);
							else vi.setInstitutionParentId(in.getParentId());
							if(vil.size()==0)Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
						}						
					}
				} catch (SystemException e) {
					//e.printStackTrace();
				}
			} catch (JSONException e) {
				//e.printStackTrace();
			}
			writeJSON(resourceRequest, resourceResponse, CreatorLocalServiceUtil.getJSONCreatorsByVideoId(videoId));			
		}
		
		if(resourceID.equals("updateupdateOpenAccessForLectureseries")){
			Lectureseries lect = new LectureseriesImpl();
			try {
				lect = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());
			} catch (PortalException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			try {
				LectureseriesLocalServiceUtil.updateOpenAccess(video, lect);
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			writeJSON(resourceRequest, resourceResponse, CreatorLocalServiceUtil.getJSONCreatorsByVideoId(videoId));			
		}
		
		if(resourceID.equals("signS3Request")){
			// get the data send by the evaporateJS s3 upload framework
			String signData = ParamUtil.getString(resourceRequest, "to_sign");
			String dateStamp = ParamUtil.getString(resourceRequest, "datetime").substring(0, 8);
		
			S3Manager s3 = new S3Manager().withDefaultCredentials();
	
			if(StringUtils.isEmpty(signData)) {
				resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
	        } else {
	            try {
	            	resourceResponse.getWriter().write(s3.getSignatureKey(s3.getSignatureKey(dateStamp, signData)));
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
		}
		
		if(resourceID.equals("getS3RawDataForVideo")){
			S3Manager s3 = new S3Manager().withDefaults();
			
			s3.initS3Client();
			
			com.liferay.portal.kernel.json.JSONArray jsonArray = s3.getItemsFromBucketWithPrefixAsJson("raw-data/" + videoId + "/");
			
			if (jsonArray == null) {
				resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(HttpServletResponse.SC_SERVICE_UNAVAILABLE));
				jsonArray = JSONFactoryUtil.createJSONArray();
			}
	
			writeJSON(resourceRequest, resourceResponse, jsonArray);
		}
		
		if(resourceID.equals("deleteS3Object")){
			JSONObject jo = JSONFactoryUtil.createJSONObject();

			String objectKey = ParamUtil.getString(resourceRequest, "key");
			
			S3Manager s3 = new S3Manager().withDefaults();
			
			s3.initS3Client();
			
			s3.deleteObject(objectKey);
				
			writeJSON(resourceRequest, resourceResponse, jo);

		}
		
	}
	
	public void removeVideo(ActionRequest request, ActionResponse response) throws PortalException, SystemException{
		Video video = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		video = VideoLocalServiceUtil.getVideo(reqVideoId);
		ProzessManager pm = new ProzessManager();	
		pm.deleteVideo(video);
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		// unpublish the video in the OAI-PMH repository if existing
		OaiPmhManager.unpublish(video.getVideoId());
	}
	
	public void lockVideo(ActionRequest request, ActionResponse response){
		Video video = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		try {
			video = VideoLocalServiceUtil.getVideo(reqVideoId);
			ProzessManager pm = new ProzessManager();	
			//deactivate open access
			//and refresh lecture series with this video
			pm.deactivateOpenaccess(video);
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void unlockVideo(ActionRequest request, ActionResponse response){
		Video video = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		//activate open access
		//and refresh lecture series with this video
		ProzessManager pm = new ProzessManager();	
		try {
			video = VideoLocalServiceUtil.getVideo(reqVideoId);
			pm.activateOpenaccess(video);
		} catch (SystemException e) {
		} catch (PortalException e) {
		}
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void activateDownload(ActionRequest request, ActionResponse response) throws SystemException, PortalException{
		Video video = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		video = VideoLocalServiceUtil.getVideo(reqVideoId);
		ProzessManager pm = new ProzessManager();
		pm.activateDownload(video);
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void deactivateDownload(ActionRequest request, ActionResponse response) throws SystemException, PortalException{
		Video video = new VideoImpl();
		Long reqVideoId = new Long(0);
		try{reqVideoId = new Long(request.getParameterMap().get("videoId")[0]);}catch(Exception e){}
		video = VideoLocalServiceUtil.getVideo(reqVideoId);
		ProzessManager pm = new ProzessManager();
		pm.deactivateDownload(video);
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void updateVttChapterFile(Video video){
		File vttDirectory = new File(PropsUtil.get("lecture2go.chapters.system.path"));
		String dateiName = vttDirectory + "/" + video.getVideoId()+".vtt";
		//get all segments
		List<Segment> sL = new ArrayList<Segment>();
		try {
			sL = SegmentLocalServiceUtil.getSegmentsByVideoId(video.getVideoId());
		} 
		catch (PortalException e) {} 
		catch (SystemException e) {}		
		
		ListIterator<Segment> sLi = sL.listIterator();
		List<String> lines = new ArrayList<String>();
		lines.add("WEBVTT");
		lines.add("");
		while(sLi.hasNext()){
			Segment seg = sLi.next();
			lines.add(seg.getStart()+" --> "+seg.getEnd());
			if(seg.getChapter()==1){
				lines.add(seg.getTitle());
				lines.add("");
			}else{
				String desc="";
				if(seg.getDescription().trim().length()>0)desc = " ("+seg.getDescription().trim() + ")";
				lines.add(seg.getTitle() + desc);
				lines.add("");
			}
		}
		
		try {
			Files.write(Paths.get(dateiName), lines, StandardCharsets.UTF_8);
		} 
		catch (Exception e) {}
	}
	
	public void updateSegmentsForVideos(){
		try {
			List<Video> vl = VideoLocalServiceUtil.getAll();
			ListIterator<Video> vit = vl.listIterator();
			while(vit.hasNext()){
				try {
					Video v = VideoLocalServiceUtil.getVideo(vit.next().getVideoId());
					if(v.isHasChapters())updateVttChapterFile(v);
				} catch (Exception e) {
					
				}
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
	}
}
