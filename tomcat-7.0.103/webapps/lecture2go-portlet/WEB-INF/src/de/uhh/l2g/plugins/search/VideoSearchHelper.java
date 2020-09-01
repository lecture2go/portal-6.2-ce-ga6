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
package de.uhh.l2g.plugins.search;

import java.util.Collections;
import java.util.List;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import de.uhh.l2g.plugins.NoSuchCoordinatorException;
import de.uhh.l2g.plugins.model.Coordinator;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;

public class VideoSearchHelper {

	protected static Log LOG = LogFactoryUtil.getLog(VideoSearchHelper.class.getName());

	public static List<Video>getVideo(VideoDisplayTerms displayTerms,int start,int end) throws SystemException{
		boolean adW = displayTerms.isAdvancedSearch();
		boolean iAnO = displayTerms.isAndOperator();
		//
		String cId = displayTerms.getCoordinatorId();
		String pId = displayTerms.getProducerId();
		String lId = displayTerms.getLectureseriesId();
		String kWr = displayTerms.getKeywords();
		//
        List<Video> videoList = getVideoData(adW, iAnO, cId, pId, lId, kWr);
        return ListUtil.subList(videoList, start , end);
	}
	
	public static int getTotalVideoCount(VideoDisplayTerms displayTerms,int start,int end) throws SystemException{
	       return getVideoData(displayTerms.isAdvancedSearch(), displayTerms.isAndOperator(), displayTerms.getCoordinatorId(), displayTerms.getProducerId(), displayTerms.getLectureseriesId(), displayTerms.getKeywords()).size();
	}
	
	public static List<Video>getVideoData(boolean isAdvancedSearch,boolean isAndOperator, String coordinatorId, String producerId, String lectureseriesId, String keywords) throws SystemException {
		List<Video> videoList = Collections.EMPTY_LIST;
		//root institution id from coordinator
		try {
			Long cId = new Long(0);
			String rootInstitutionId ="";
			Coordinator co = CoordinatorLocalServiceUtil.createCoordinator(0);
			try{ 
				cId = new Long (coordinatorId);
				co = CoordinatorLocalServiceUtil.getCoordinator(cId); 
				rootInstitutionId = InstitutionLocalServiceUtil.getInstitution(co.getInstitutionId()).getInstitutionId() + "";
			}catch(Exception nd){};
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Video.class, "vid", classLoader);
			Junction junction = null;
			junction = RestrictionsFactoryUtil.conjunction(); 
			//
			if (!Validator.isBlank(keywords)) {
				Criterion c1 = PropertyFactoryUtil.forName("vid.title").like("%" + keywords + "%");
				junction.add(c1);
			}
			if (!Validator.isBlank(rootInstitutionId) && !Validator.equals(rootInstitutionId, "0")) {
				Criterion c2 = PropertyFactoryUtil.forName("vid.rootInstitutionId").eq(Long.valueOf(rootInstitutionId));
				junction.add(c2);
			}
			if (Validator.isDigit(producerId) && !Validator.equals(producerId, "0")) {
				Criterion c3 = PropertyFactoryUtil.forName("vid.producerId").eq(Long.valueOf(producerId));
				junction.add(c3);
			}
			if (Validator.isDigit(lectureseriesId) && !Validator.equals(lectureseriesId, "0")) {
				Criterion c4 = PropertyFactoryUtil.forName("vid.lectureseriesId").eq(Long.valueOf(lectureseriesId));
				junction.add(c4);
			}
			//
			dynamicQuery.add(junction);
			//order by 
			dynamicQuery.addOrder(OrderFactoryUtil.desc("vid.videoId"));
			//fire up the query
			videoList = VideoLocalServiceUtil.dynamicQuery(dynamicQuery);
			//
		} catch (Exception e) {
			LOG.warn("can't fetch video list!");
		}
        return videoList;
	}
}
