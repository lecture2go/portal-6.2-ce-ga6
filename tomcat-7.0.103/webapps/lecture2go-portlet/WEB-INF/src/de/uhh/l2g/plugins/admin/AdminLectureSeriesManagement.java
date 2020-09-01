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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import de.uhh.l2g.plugins.model.Category;
import de.uhh.l2g.plugins.model.Coordinator;
import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Lectureseries_Category;
import de.uhh.l2g.plugins.model.Lectureseries_Creator;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Tagcloud;
import de.uhh.l2g.plugins.model.Term;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Institution;
import de.uhh.l2g.plugins.model.impl.CategoryImpl;
import de.uhh.l2g.plugins.model.impl.CoordinatorImpl;
import de.uhh.l2g.plugins.model.impl.CreatorImpl;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.Lectureseries_CategoryImpl;
import de.uhh.l2g.plugins.model.impl.Lectureseries_CreatorImpl;
import de.uhh.l2g.plugins.model.impl.Lectureseries_InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.ProducerImpl;
import de.uhh.l2g.plugins.model.impl.Producer_LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.TagcloudImpl;
import de.uhh.l2g.plugins.model.impl.Video_InstitutionImpl;
import de.uhh.l2g.plugins.service.CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.Producer_LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil;
import de.uhh.l2g.plugins.service.TermLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.util.EmailManager;
import de.uhh.l2g.plugins.util.FileManager;
import de.uhh.l2g.plugins.util.Htaccess;
import de.uhh.l2g.plugins.util.Lecture2GoRoleChecker;
import de.uhh.l2g.plugins.util.OaiPmhManager;
import de.uhh.l2g.plugins.util.ProzessManager;

public class AdminLectureSeriesManagement extends MVCPortlet {
	
	public void viewLectureseries(ActionRequest request, ActionResponse response) throws SystemException, PortalException {
		// requested lectureseries
		//TagcloudLocalServiceUtil.generateForAllLectureseries();
		//LectureseriesLocalServiceUtil.updateUploadAndGenerationDate();
		//generateRSSforAllLectureseriesWhithOpenaccessVideos();
		
		long reqLectureseriesId = new Long(request.getParameterMap().get("lectureseriesId")[0]);
		String backURL = request.getParameter("backURL");
		Lectureseries reqLectureseries = LectureseriesLocalServiceUtil.getLectureseries(reqLectureseriesId);
		request.setAttribute("reqLectureseries", reqLectureseries);

		Map<String,String> institutions = new LinkedHashMap<String, String>();
		List<Producer> producers = new ArrayList<Producer>();
		request.setAttribute("institutions", institutions);
		request.setAttribute("producers", producers);
		request.setAttribute("backURL", backURL);

		response.setRenderParameter("jspPage", "/admin/editLectureseries.jsp");
	}
	
	public void removeLectureseries(ActionRequest request, ActionResponse response) {
		Long lId = new Long(request.getParameter("lectureseriesId"));
		String backURL = request.getParameter("backURL");
		//remove lecture series
		try {
			LectureseriesLocalServiceUtil.deleteLectureseries(lId);
			//dependencies
			Lectureseries_InstitutionLocalServiceUtil.removeByLectureseriesId(lId);//institution
			Producer_LectureseriesLocalServiceUtil.removeByLectureseriesId(lId);//producer
			Lectureseries_CategoryLocalServiceUtil.removeByLectureseriesId(lId);//category
			VideoLocalServiceUtil.unlinkLectureseriesFromVideos(lId);//video
			TagcloudLocalServiceUtil.deleteByObjectId(lId);//tag cloud
		} catch (PortalException e) {
			//e.printStackTrace();
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		//refresh htaccess authentication files 
		Htaccess.writePW(LectureseriesLocalServiceUtil.getAllLectureseriesWhithPassword());
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}	
		
		//generate new JSON date for auto complete functionality
	}

	public void editLectureseries(ActionRequest request, ActionResponse response) throws NumberFormatException, PortalException, SystemException, UnsupportedEncodingException{
//		List<Lectureseries> lll = LectureseriesLocalServiceUtil.getAll();
//		java.util.ListIterator<Lectureseries> itttt = lll.listIterator();
//		
//		while (itttt.hasNext()){
//			Lectureseries dfdf = itttt.next();
//			dfdf.setUSID(RandomStringUtils.random(11, true, true));
//			LectureseriesLocalServiceUtil.updateLectureseries(dfdf);
//		}
		
		User user = UserLocalServiceUtil.getUser(new Long(request.getRemoteUser()));
		EmailManager em = new EmailManager();

		Long lId = new Long(request.getParameter("lectureseriesId"));
		String[] producers = request.getParameterValues("producers");
		
		String[] institutions = request.getParameterValues("institutions");
		String s = request.getParameter("longDesc");
		String backURL = request.getParameter("backURL");
		Long semesterId = new Long(0);
		try{
			semesterId = new Long(request.getParameter("semesterId"));
		}catch(Exception e){}
		Long categoryId = new Long(0);
		try{
			categoryId = new Long(request.getParameter("categoryId"));
		}catch(Exception e){}
		Integer videoSort = new Integer(0);
		try{
			videoSort = "1".equals(request.getParameter("videoSort")) ? new Integer(1) : videoSort;
		}catch(Exception e){}

		
		Locale locale = request.getLocale(); 

		//update object
		Lectureseries lectureseries = LectureseriesLocalServiceUtil.getLectureseries(lId);
		//only for admin or coordinator can update this flag
		if(new Lecture2GoRoleChecker().isCoordinator(user) || new Lecture2GoRoleChecker().isL2gAdmin(user)){
			lectureseries.setApproved(1);
		}
		// If no lectureseries number is set, the default-number 00.000 will be set
		lectureseries.setNumber("".equals(request.getParameter("number")) ? LanguageUtil.get(getPortletConfig(), locale, "lecture-series-default-number"):request.getParameter("number"));
		//update categoryId
		lectureseries.setCategoryId(categoryId);
		//and all linked objects to this category over the lecture series object
		LectureseriesLocalServiceUtil.updateCategoryForLectureseries(lectureseries.getLectureseriesId(), categoryId);
		lectureseries.setName(request.getParameter("name"));
		lectureseries.setShortDesc(request.getParameter("shortDesc"));
		lectureseries.setTermId(semesterId);
		lectureseries.setLanguage(request.getParameter("language"));
		lectureseries.setFacultyName(request.getParameter("facultyName"));
		lectureseries.setPassword(request.getParameter("password"));
		lectureseries.setLongDesc(s);	
		lectureseries.setVideoSort(videoSort);
		
		//update database
		LectureseriesLocalServiceUtil.updateLectureseries(lectureseries);
		//update previewVideoId
		LectureseriesLocalServiceUtil.updatePreviewVideoOpenAccess(lectureseries);
		//refresh htaccess authentication files 
		Htaccess.writePW(LectureseriesLocalServiceUtil.getAllLectureseriesWhithPassword());
		
		//update institution link
		//delete old entries first
		Lectureseries_InstitutionLocalServiceUtil.removeByLectureseriesId(lectureseries.getLectureseriesId());
		//new links to institution
		for(int i=0;i<institutions.length;i++){
			Lectureseries_InstitutionImpl lf = new Lectureseries_InstitutionImpl();
			lf.setLectureseriesId(lId);
			Institution parentInst = new InstitutionImpl();
			Institution inst = new InstitutionImpl();
			inst = InstitutionLocalServiceUtil.getById(new Long(institutions[i]));
			parentInst = InstitutionLocalServiceUtil.getById(inst.getParentId());
			lf.setInstitutionId(inst.getInstitutionId());
			lf.setInstitutionParentId(inst.getParentId());
			if(!Lectureseries_InstitutionLocalServiceUtil.institutionAssignedToLectureseries(lf))
				Lectureseries_InstitutionLocalServiceUtil.addLectureseries_Institution(lf);
		}
		
		// get all videos of this lectureSeries 
		List<Video> videosByLectureseries = VideoLocalServiceUtil
				.getByLectureseries(lId);
		for (int i = 0; i < videosByLectureseries.size(); i++) {
			Video video = videosByLectureseries.get(i);

			// Update table LG_Video_Institution
			Video_InstitutionLocalServiceUtil.removeByVideoId(video
					.getVideoId()); // Delete old entries
			for (int j = 0; j < institutions.length; j++) {
				Institution inst = new InstitutionImpl();
				inst = InstitutionLocalServiceUtil.getById(new Long(
						institutions[j]));
				Video_Institution vi = Video_InstitutionLocalServiceUtil.createVideo_Institution(0);
				vi.setVideoId(video.getVideoId());
				vi.setInstitutionId(inst.getInstitutionId());
				vi.setInstitutionParentId(inst.getParentId());
				Video_InstitutionLocalServiceUtil.addVideo_Institution(vi);
			}

			// update column termId in lg_video table
			Long videoTermId = video.getTermId();
			if (semesterId.longValue() != videoTermId.longValue()) {
				video.setTermId(semesterId);
				VideoLocalServiceUtil.updateVideo(video);
			}
		}
		
		
		//update producer link
		//delete old entries first
		Producer_LectureseriesLocalServiceUtil.removeByLectureseriesId(lId);
		//new links to producer
		for(int i=0;i<producers.length;i++){
			Producer_LectureseriesImpl pl = new Producer_LectureseriesImpl();
			pl.setProducerId(new Long(producers[i]));
			pl.setLectureseriesId(lId);
			//producer not assigned to lecture series yet
			if(!Producer_LectureseriesLocalServiceUtil.producerAssignedToLectureseries(pl))
				Producer_LectureseriesLocalServiceUtil.addProducer_Lectureseries(pl);
		}	
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		

		//update tag cloud
		TagcloudLocalServiceUtil.generateForLectureseries(lectureseries.getLectureseriesId());

		// update the datestamp of all OAIRecords linked to the changed lectureseries
		OaiPmhManager.modifyByLectureseries(lId);
		
		//email notification after edit

		//send an email to all producer, if logged in as coordinator or admin
		//and lecture serice edited
		if(new Lecture2GoRoleChecker().isCoordinator(user) || new Lecture2GoRoleChecker().isL2gAdmin(user) ){
			List<Long> pIds = ProducerLocalServiceUtil.getAllProducerIds(lectureseries.getLectureseriesId());
			java.util.ListIterator<Long> itt=  pIds.listIterator();
			while(itt.hasNext()){
				String pId= itt.next()+"";
				Long pIdL=new Long(pId);
				Producer pr = ProducerLocalServiceUtil.getProdUcer(pIdL);
				String PRODEMAILADDRESS = pr.getEmailAddress();
				// Subject
				String SUBJECT = LanguageUtil.get(getPortletConfig(), locale, "lecture-series-edited-and-allowed");
				String BODY = user.getFirstName() + " " + user.getLastName()+ " " + LanguageUtil.get(getPortletConfig(), locale, "edited-and-allowed-lecture-series") + " \n" + LanguageUtil.get(getPortletConfig(), locale, "lecture-series") +":" + lectureseries.getNumber() + ": " + lectureseries.getName();
				// Send mail to producer
				em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), PRODEMAILADDRESS , SUBJECT, BODY);
			}
		}	
	}

	public void addLectureseries(ActionRequest request, ActionResponse response) throws SystemException, PortalException, UnsupportedEncodingException {
		User user = UserLocalServiceUtil.getUser(new Long(request.getRemoteUser()));
		EmailManager em = new EmailManager();
		
		String s = request.getParameter("longDesc");
		String[] producers = request.getParameterValues("producers");
		String[] institutions = request.getParameterValues("institutions");
		String backURL = request.getParameter("backURL");
		Long semesterId = new Long(0);
		try{
			semesterId = new Long(request.getParameter("semesterId"));
		}catch(Exception e){}
		Long categoryId = new Long(0);
		try{
			categoryId = new Long(request.getParameter("categoryId"));
		}catch(Exception e){}
		Integer videoSort = new Integer(0);
		try{
			videoSort = "1".equals(request.getParameter("videoSort")) ? new Integer(1) : videoSort;
		}catch(Exception e){}
		
		Locale locale = request.getLocale(); 
		
		//build lecture series object
		LectureseriesImpl lectureseries = new LectureseriesImpl();
		lectureseries.setApproved(1);
		// If no lectureseries number is set, the default-number 00.000 will be set
		lectureseries.setNumber("".equals(request.getParameter("number")) ? LanguageUtil.get(getPortletConfig(), locale, "lecture-series-default-number"):request.getParameter("number"));
		lectureseries.setCategoryId(categoryId);
		lectureseries.setName(request.getParameter("name"));
		lectureseries.setShortDesc(request.getParameter("shortDesc"));
		lectureseries.setTermId(semesterId);
		lectureseries.setLanguage(request.getParameter("language"));
		lectureseries.setFacultyName(request.getParameter("facultyName"));
		lectureseries.setPassword(request.getParameter("password"));
		lectureseries.setLongDesc(s);
		lectureseries.setVideoSort(videoSort);
		
		//add an USID
		lectureseries.setUSID(RandomStringUtils.random(11, true, true));
		
		//save object to database
		Lectureseries newlect = LectureseriesLocalServiceUtil.addLectureseries(lectureseries);
		//add to Lectureseries_Category table
		Lectureseries_Category lcat = new Lectureseries_CategoryImpl();
		lcat.setLectureseriesId(newlect.getLectureseriesId());
		lcat.setCategoryId(newlect.getCategoryId());
		Lectureseries_CategoryLocalServiceUtil.addLectureseries_Category(lcat);
		//
		Long lId = newlect.getLectureseriesId();

		//refresh htaccess authentication files 
		Htaccess.writePW(LectureseriesLocalServiceUtil.getAllLectureseriesWhithPassword());
		
		//link to institution
		for(int i=0;i<institutions.length;i++){
			Lectureseries_InstitutionImpl lf = new Lectureseries_InstitutionImpl();
			lf.setLectureseriesId(lId);
			Institution parentInst = new InstitutionImpl();
			Institution inst = new InstitutionImpl();
			inst = InstitutionLocalServiceUtil.getById(new Long(institutions[i]));
			parentInst = InstitutionLocalServiceUtil.getById(inst.getParentId());
			
			lf.setInstitutionId(inst.getInstitutionId());
			lf.setInstitutionParentId(inst.getParentId());
			Lectureseries_InstitutionLocalServiceUtil.addLectureseries_Institution(lf);
		}

		//new creators
		String[] firstNames = request.getParameterValues("firstName");
		String[] lastNames = request.getParameterValues("lastName");
		String[] jobTitles = request.getParameterValues("jobTitle");
		String[] genders = request.getParameterValues("gender");
		String[] creatorIds = request.getParameterValues("creatorId");
		Long cId = new Long(0);
		//remove all creators for this lecture series first
		Lectureseries_CreatorLocalServiceUtil.removeByLectureseriesId(lId);
		//and add the new creators
		try{
			for(int i=0;i<creatorIds.length;i++){
				if(creatorIds[i].equals("0")){
					Creator c = new CreatorImpl();
					c.setFirstName(firstNames[i]);
					c.setLastName(lastNames[i]);
					c.setJobTitle(jobTitles[i]);
					c.setGender(genders[i]);
					c.setFullName(jobTitles[i]+" "+firstNames[i]+" "+lastNames[i]);
					List<Creator> cl = CreatorLocalServiceUtil.getByFullName(jobTitles[i]+" "+firstNames[i]+" "+lastNames[i]);
					if(cl.size()==0)cId = CreatorLocalServiceUtil.addCreator(c).getCreatorId();
					else cId = CreatorLocalServiceUtil.getByFullName(jobTitles[i]+" "+firstNames[i]+" "+lastNames[i]).iterator().next().getCreatorId();
				}else{
					cId = new Long(creatorIds[i]);
				}
				//add created creator to lecture series
				Lectureseries_Creator lc = new Lectureseries_CreatorImpl();
				lc.setLectureseriesId(lId);
				lc.setCreatorId(cId);
				if(Lectureseries_CreatorLocalServiceUtil.getByLectureseriesIdAndCreatorId(lId, cId).size()==0){
					Lectureseries_CreatorLocalServiceUtil.addLectureseries_Creator(lc);
				}
			}
		}catch (NullPointerException e){}
		//link to producer
		for(int i=0;i<producers.length;i++){
			Producer_LectureseriesImpl pl = new Producer_LectureseriesImpl();
			pl.setProducerId(new Long(producers[i]));
			pl.setLectureseriesId(lId);
			Producer_LectureseriesLocalServiceUtil.addProducer_Lectureseries(pl);
		}
		
		
		//Tag cloud
		TagcloudLocalServiceUtil.generateForLectureseries(newlect.getLectureseriesId());

		//
		request.setAttribute("institutions", institutions);
		request.setAttribute("producers", producers);
		request.setAttribute("backURL", backURL);
		
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}		
		
		//send an email to coordinator and administrator, if logged in as producer
		
		// the approval is deactivated, do not send mails to producer,coordinators etc.
		/*
		if(new Lecture2GoRoleChecker().isProducer(user)){
			//get producer details
			Producer p = new ProducerImpl();
			p = ProducerLocalServiceUtil.getProdUcer(user.getUserId());//full object "getProdUser()"
			Coordinator c = new CoordinatorImpl();
			// Subject
			String SUBJECT = LanguageUtil.get(getPortletConfig(), locale, "new-request-for-approval") ;
			String BODY = LanguageUtil.get(getPortletConfig(), locale, "you-have-a-new-request-for-approval") + " \n" + LanguageUtil.get(getPortletConfig(), locale, "lecture-series") + " : " + newlect.getNumber() + ": " + newlect.getName();
			//if coordinator exists
			boolean coordExists = false;
			// Coordinator for this Producer
			try{
				c = CoordinatorLocalServiceUtil.getByInstitution(p.getInstitutionId());
				if(c.getCoordinatorId()>0)coordExists = true;
			}catch(NoSuchElementException e){}
			//check 
			if(coordExists){
				String COORDEMAILADDRESS = c.getEmailAddress();				
				String BODY2 =  LanguageUtil.get(getPortletConfig(), locale, "coordinator") + " " + c.getFirstName() + " " + c.getLastName() + " " + LanguageUtil.get(getPortletConfig(), locale, "got-a-new-request-for-approval") + "  \n" + LanguageUtil.get(getPortletConfig(), locale, "lecture-series") + ": "  +newlect.getNumber() + ": " + newlect.getName();
				// Send mail to Coordinator
				em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), COORDEMAILADDRESS, SUBJECT, BODY);
				// Send mail to L2Go
				em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), PropsUtil.get("lecture2go.response.email.address"), SUBJECT, BODY2);
			}
			// Send mail to Producer
			String PRODEMAILADDRESS = p.getEmailAddress();
			String BODY3 = LanguageUtil.get(getPortletConfig(), locale, "your-request-was-sent") +"  \n" + LanguageUtil.get(getPortletConfig(), locale, "lecture-series") +" :" + newlect.getNumber() + ": " + newlect.getName();
			em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), PRODEMAILADDRESS, SUBJECT, BODY3);
		}	

		//send an email to  administrator, if logged in as coordinator
		if(new Lecture2GoRoleChecker().isCoordinator(user)){
			//get coordinator details
			Coordinator c = new CoordinatorImpl();
			c = CoordinatorLocalServiceUtil.getById(user.getUserId());
			// Subject
			String SUBJECT = LanguageUtil.get(getPortletConfig(), locale, "new-lectureseries");
			String BODY = LanguageUtil.get(getPortletConfig(), locale, "coordinator") +" "+ c.getFirstName() + " " + c.getLastName()+ " " + LanguageUtil.get(getPortletConfig(), locale, "has-entered-a-new-lecture-series") + " \n" + LanguageUtil.get(getPortletConfig(), locale, "lecture-series") +":" + newlect.getNumber() + ": " + newlect.getName();
			// Send mail to L2Go
			em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), PropsUtil.get("lecture2go.response.email.address")  , SUBJECT, BODY);
		}	
		**/
		
	}
	
	public void generateRSSforAllLectureseriesWhithOpenaccessVideos(){
		List<Lectureseries> allLect = LectureseriesLocalServiceUtil.getAllLectureseriesWhithOpenaccessVideos();
		Iterator<Lectureseries> ittLect = allLect.iterator();
		Log LOG = LogFactoryUtil.getLog(AdminLectureSeriesManagement.class.getName());	
		int a = 1;
		while(ittLect.hasNext()){
			Lectureseries l = ittLect.next();
			try {
				try {
					Video v = VideoLocalServiceUtil.getVideo(l.getLatestOpenAccessVideoId());
				
					// generate RSS
					LOG.info("Generate RSS" +a+" fol lecture series with ID: "+l.getLectureseriesId()+" and latest open access video with ID: "+v.getVideoId());
					ProzessManager pm = new ProzessManager();
					for (String f: FileManager.MEDIA_FORMATS) {           
						try {
							pm.generateRSS(v, f);
						} catch (Exception e) {
							//e.printStackTrace();
						} 
					}
				} catch (Exception e) {
					
				}
				LOG.info("RSS "+a+" generated");
				a++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException, IOException {
		String resourceID = resourceRequest.getResourceID();
		
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
		
		if(resourceID.equals("updateCreators")){
			/* TODO: to be filled */
		}

	}

}
