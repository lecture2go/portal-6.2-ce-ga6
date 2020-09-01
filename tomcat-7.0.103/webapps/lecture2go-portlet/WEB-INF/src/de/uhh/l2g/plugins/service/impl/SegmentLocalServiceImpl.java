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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.impl.SegmentImpl;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.SegmentLocalServiceBaseImpl;
import de.uhh.l2g.plugins.util.FFmpegManager;

/**
 * The implementation of the segment local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.SegmentLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.SegmentLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.SegmentLocalServiceUtil
 */
public class SegmentLocalServiceImpl extends SegmentLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.SegmentLocalServiceUtil} to access the segment local service.
	 */

	protected static Log LOG = LogFactoryUtil.getLog(Segment.class.getName());

	public Segment addSegment(Segment object){
		Long id;
		try {
			id = counterLocalService.increment(Segment.class.getName());
			object.setPrimaryKey(id);
			super.addSegment(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	public void deleteThumbhailsFromSegments(List<Segment> segmentList) throws PortalException, SystemException {
		Iterator<Segment> it = segmentList.iterator();
		while (it.hasNext()) {
			Segment objectSegment = it.next();
			Video objectVideo = VideoLocalServiceUtil.getVideo(objectSegment.getVideoId());
			try{
				int sec = new Integer(objectSegment.getStart().split(":")[0]) * 60 * 60 + new Integer(objectSegment.getStart().split(":")[1]) * 60 + new Integer(objectSegment.getStart().split(":")[2]);
				File thumbNail = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + objectVideo.getVideoId() + "_" + sec + ".jpg");
				if (thumbNail.isFile()) thumbNail.delete();
			}catch (ArrayIndexOutOfBoundsException e) {}
		}
	}
	
	public void deleteThumbhailsFromSegment(Segment segment) throws PortalException, SystemException {
		Video objectVideo = VideoLocalServiceUtil.getVideo(segment.getVideoId());
		try {
			int sec = new Integer(segment.getStart().split(":")[0]) * 60 * 60 + new Integer(segment.getStart().split(":")[1]) * 60 + new Integer(segment.getStart().split(":")[2]);
			File thumbNail = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + objectVideo.getVideoId() + "_" + sec + ".jpg");
			if (thumbNail.isFile())thumbNail.delete();
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	
	public List<Segment> getSegmentsByVideoId(Long videoId) throws SystemException, PortalException {
		List<Segment> sl = segmentPersistence.findByVideo(videoId);
		
		Iterator<Segment> it = sl.iterator();
		
		while (it.hasNext()) {
			Segment objSeg = it.next();
			fillWithProperties(objSeg);
		}
		return sl;
	}
	
	public Segment getSegmentById(Long segmentId) throws SystemException, PortalException {
		Segment objectSegment = segmentPersistence.findByPrimaryKey(segmentId);
		return fillWithProperties(objectSegment);
	}
	
	public Segment fillWithProperties(Segment objectSegment) throws SystemException, PortalException {
		Video objectVideo = VideoLocalServiceUtil.getVideo(objectSegment.getVideoId());
		//
		int sec = new Integer(objectSegment.getStart().split(":")[0]) * 60 * 60 + new Integer(objectSegment.getStart().split(":")[1]) * 60 + new Integer(objectSegment.getStart().split(":")[2]);
		objectSegment.setSeconds(sec);
		
		String thumbNailString = PropsUtil.get("lecture2go.images.system.path") + "/" + objectVideo.getVideoId() + "_" + sec + ".jpg";
		File thumbNail = new File(thumbNailString);
		String strt = objectSegment.getStart();
		
		// generate thumbs
		// for audio
		if (objectVideo.getContainerFormat().equals("mp3")) {
			objectSegment.setImage(PropsUtil.get("lecture2go.web.root") + "/lecture2go-portlet/img/audio_only_small.png");
		}
		// for video
		if (objectVideo.getContainerFormat().equals("mp4")) {
			if (!thumbNail.isFile())FFmpegManager.createThumbnail(objectVideo, strt, PropsUtil.get("lecture2go.images.system.path"));
			objectSegment.setImage(PropsUtil.get("lecture2go.web.root")+"/" + "images" + "/" + objectVideo.getVideoId() + "_" + sec + ".jpg");
		}
		return objectSegment;
	}
	
	public void deleteByVideoId(Long videoId) throws SystemException {
		 segmentPersistence.removeByVideo(videoId);
	}
	
	public Segment removeSegment(Long segmentId) throws SystemException, PortalException{
		Segment s = SegmentLocalServiceUtil.deleteSegment(segmentId);
		//delete thumb nails
		deleteThumbhailsFromSegment(s);
		return s;
	}
	
	public Segment getPreviusSegment(Segment segment) throws SystemException, PortalException{
		List<Segment> sl = new ArrayList<Segment>();
		int previousSegmentIndex = 0;
		try {
			sl = segmentPersistence.findByVideo(segment.getVideoId());
			ListIterator<Segment> lis = sl.listIterator();
			while(lis.hasNext()){
				Segment s = lis.next();
				if(s.getSegmentId()==segment.getSegmentId())previousSegmentIndex=lis.previousIndex();
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
		int ps = previousSegmentIndex-1;
		Segment previousSegment = sl.get(ps);
		if(!previousSegment.equals("-1"))return fillWithProperties(previousSegment);
		else return new SegmentImpl();
	}
	
	public Long getPreviusSegmentId(Long segmentId) throws SystemException, PortalException{
		Segment s = new SegmentImpl();
		try {
			s = getSegmentById(segmentId);
		} catch (SystemException e) {
			//e.printStackTrace();
		} catch (PortalException e) {
			//e.printStackTrace();
		} 
		Long prevSegId = new Long(0);
		try{
			prevSegId = getPreviusSegment(s).getSegmentId();
		}catch(ArrayIndexOutOfBoundsException e){
			prevSegId = new Long(-1);
		}
		return prevSegId;
	}
	
}
