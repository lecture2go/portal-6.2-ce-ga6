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

package de.uhh.l2g.plugins.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import de.uhh.l2g.plugins.NoSuchTagcloudException;
import de.uhh.l2g.plugins.model.Category;
import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Lectureseries_Creator;
import de.uhh.l2g.plugins.model.Lectureseries_Institution;
import de.uhh.l2g.plugins.model.Tagcloud;
import de.uhh.l2g.plugins.model.Term;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Creator;
import de.uhh.l2g.plugins.model.Video_Institution;
import de.uhh.l2g.plugins.model.impl.CategoryImpl;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.TagcloudImpl;
import de.uhh.l2g.plugins.model.impl.TermImpl;
import de.uhh.l2g.plugins.model.impl.VideoImpl;
import de.uhh.l2g.plugins.service.CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil;
import de.uhh.l2g.plugins.service.TermLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.TagcloudLocalServiceBaseImpl;

/**
 * The implementation of the tagcloud local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.TagcloudLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.TagcloudLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil
 */
public class TagcloudLocalServiceImpl extends TagcloudLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil} to access the tagcloud local service.
	 */
	protected static Log LOG = LogFactoryUtil.getLog(Tagcloud.class.getName());

	public Tagcloud addTagcloud(Tagcloud object){
		Long id;
		try {
			id = counterLocalService.increment(Tagcloud.class.getName());
			object.setPrimaryKey(id);
			super.addTagcloud(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	public void deleteByObjectId(long objectId) throws SystemException{
		tagcloudPersistence.removeByObjectId(objectId);
	}
	
	public Tagcloud getByObjectIdAndObjectClassType(long objectId, String objectClassType) throws SystemException, NoSuchTagcloudException{
		return tagcloudPersistence.findByObjectClassTypeAndObjectId(objectClassType, objectId);
	}
	
	public void add(ArrayList<String> tagCloudArrayString, String className, Long objectId) throws SystemException{
		String tagCloudString = "";
		for(int i=0;i<tagCloudArrayString.size();i++){
			tagCloudString += tagCloudArrayString.get(i) +" ### ";
		}
		
		Tagcloud tagcloud = new TagcloudImpl();
		try {
			tagcloud=TagcloudLocalServiceUtil.getByObjectIdAndObjectClassType(objectId, className);
			tagcloud.setTags(tagCloudString);
			tagcloud.setObjectClassType(className);
			tagcloud.setObjectId(objectId);
			TagcloudLocalServiceUtil.updateTagcloud(tagcloud);
		} catch (NoSuchTagcloudException e) {
			tagcloud.setTags(tagCloudString);
			tagcloud.setObjectClassType(className);
			tagcloud.setObjectId(objectId);
			TagcloudLocalServiceUtil.addTagcloud(tagcloud);
		}
		//
	}
	
	public void updateByObjectIdAndObjectClassType(ArrayList<String> tagCloudArrayString, String className, long objectId) throws SystemException{
		String tagCloudString = "";
		for(int i=0;i<tagCloudArrayString.size();i++) tagCloudString += tagCloudArrayString.get(i) +" ### ";
		
		Tagcloud tagcloud = new TagcloudImpl();
		try{
			tagcloud = TagcloudLocalServiceUtil.getByObjectIdAndObjectClassType(objectId, className);
		}catch(NoSuchTagcloudException nste){}
		
		tagcloud.setTags(tagCloudString);
		tagcloud.setObjectClassType(className);
		tagcloud.setObjectId(objectId);
		TagcloudLocalServiceUtil.updateTagcloud(tagcloud);
	}
	
	public void generateForAllLectureseries(){
		List<Lectureseries> all = new ArrayList<Lectureseries>();
		try {
			all = LectureseriesLocalServiceUtil.getAll();
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		ListIterator<Lectureseries> li = all.listIterator();
		while(li.hasNext()){
			Lectureseries l = li.next();
			generateForLectureseries(l.getLectureseriesId());
		}
	}
	
	public void generateForLectureseries(Long lectureseriesId) {
		ArrayList<String> tagCloudArrayString = new ArrayList<String>();
		Lectureseries l = new LectureseriesImpl();
		try {
			l = LectureseriesLocalServiceUtil.getLectureseries(lectureseriesId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//self
		tagCloudArrayString.add(l.getName());
		tagCloudArrayString.add(l.getNumber());
		tagCloudArrayString.add(l.getEventType());
		
		//Term
		try {
			Term t = TermLocalServiceUtil.getTerm(l.getTermId());
			tagCloudArrayString.add(t.getTermName());
		} catch (Exception e) {} 
		
		//Category
		try{
			Category ctgr = CategoryLocalServiceUtil.getCategory(l.getCategoryId());
			tagCloudArrayString.add(ctgr.getName());
		}catch(Exception e){}
		
		//Institution
		try {
			List<Lectureseries_Institution>il = Lectureseries_InstitutionLocalServiceUtil.getByLectureseries(l.getLectureseriesId());
			ListIterator<Lectureseries_Institution> li1 = il.listIterator();
			while(li1.hasNext()){
				long instId = li1.next().getInstitutionId();
				try {
					tagCloudArrayString.add(InstitutionLocalServiceUtil.getInstitution(instId).getName());
				} catch (PortalException e) {
					//e.printStackTrace();
				}
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		//Creators (only creators of videos which are open access may be used)	
		try {
			List<Creator> creators = CreatorLocalServiceUtil.getCreatorsByLectureseriesIdForOpenAccessVideosOnly(l.getLectureseriesId());
			ListIterator<Creator> li2 = creators.listIterator();
			while(li2.hasNext()){
				Creator cr = li2.next();
				tagCloudArrayString.add(cr.getFullName());
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		//add to tag cloud
		try {
			add(tagCloudArrayString, l.getClass().getName(), l.getLectureseriesId());
		} catch (SystemException e) {
			//e.printStackTrace();
		}	
	}

	public void generateForAllVideos(){
		List<Video> all = new ArrayList<Video>();
		try {
			all = VideoLocalServiceUtil.getAll();
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		ListIterator<Video> li = all.listIterator();
		while(li.hasNext()){
			Video v = li.next();
			generateForVideo(v.getVideoId());
		}
	}
	
	public void generateForVideo(Long videoId){
		ArrayList<String> tagCloudArrayString = new ArrayList<String>();
		
		Video v = new VideoImpl();
		try {
			v = VideoLocalServiceUtil.getVideo(videoId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//self
		tagCloudArrayString.add(v.getTitle());
		
		//tags
		tagCloudArrayString.add(v.getTags());
		
		//lecture series for this video
		try {
			Lectureseries lect = LectureseriesLocalServiceUtil.getLectureseries(v.getLectureseriesId());
			tagCloudArrayString.add(lect.getNumber());
			tagCloudArrayString.add(lect.getEventType());
			tagCloudArrayString.add(lect.getName());
		} catch (Exception e1) {
			// propably a single video without lectureseries, continue
		}
		
		//Term
		try {
			Term t = TermLocalServiceUtil.getTerm(v.getTermId());
			tagCloudArrayString.add(t.getTermName());
		} catch (Exception e) {} 
		
		//Category
		try{
			Category ctgr = CategoryLocalServiceUtil.getCategory(Video_CategoryLocalServiceUtil.getByVideo(v.getVideoId()).get(0).getCategoryId());
			tagCloudArrayString.add(ctgr.getName());
		}catch(Exception e){}
		
		//Institution
		try {
			List<Video_Institution> vi = Video_InstitutionLocalServiceUtil.getByVideo(v.getVideoId());
			ListIterator<Video_Institution> li1 = vi.listIterator();
			while(li1.hasNext()){
				long instId = li1.next().getInstitutionId();
				try {
					tagCloudArrayString.add(InstitutionLocalServiceUtil.getInstitution(instId).getName());
				} catch (PortalException e) {
					//e.printStackTrace();
				}
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		//Creators
		try {
			List<Video_Creator> vcl = Video_CreatorLocalServiceUtil.getByVideo(v.getVideoId());
			ListIterator<Video_Creator> li2 = vcl.listIterator();
			while(li2.hasNext()){
				long creatId = li2.next().getCreatorId();
				try {
					Creator cr = CreatorLocalServiceUtil.getCreator(creatId);
					tagCloudArrayString.add(cr.getFullName());
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		
		//add to tag cloud
		try {
			add(tagCloudArrayString, v.getClass().getName(), v.getVideoId());
		} catch (SystemException e) {
			//e.printStackTrace();
		}		
	}
}
