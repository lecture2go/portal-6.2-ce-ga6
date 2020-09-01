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
package de.uhh.l2g.plugins.service.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.impl.CreatorImpl;

public class CreatorFinderImpl extends BasePersistenceImpl<Creator> implements CreatorFinder {
	public static final String FIND_CREATORS_FOR_LECTURESERIES = CreatorFinder.class.getName() + ".findCreatorsForLectureseries";
	public static final String FIND_CREATORS_FOR_VIDEO = CreatorFinder.class.getName() + ".findCreatorsForVideo";
	public static final String FIND_CREATORS_FOR_LECTURESERIES_OVER_THE_ASSIGNED_VIDEOS = CreatorFinder.class.getName() + ".findCreatorsForLectureseriesOverTheAssigenedVideos";

	
	public List<Creator> findCreatorsByLectureseries(long lectureseriesId) {
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_CREATORS_FOR_LECTURESERIES);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("creatorId", Type.LONG);
			q.addScalar("firstName", Type.STRING);
			q.addScalar("lastName", Type.STRING);
			q.addScalar("middleName", Type.STRING);
			q.addScalar("jobTitle", Type.STRING);
			q.addScalar("gender", Type.STRING);
			q.addScalar("fullName", Type.STRING);
			q.addScalar("affiliation", Type.STRING);
			q.addScalar("orcidId", Type.STRING);
			q.setCacheable(false);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(lectureseriesId);
			@SuppressWarnings("unchecked")
			List <Object[]> fl =  (List<Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleCreators(fl);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	public List<Creator> findCreatorsByVideo(long videoId) {
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_CREATORS_FOR_VIDEO);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("creatorId", Type.LONG);
			q.addScalar("firstName", Type.STRING);
			q.addScalar("lastName", Type.STRING);
			q.addScalar("middleName", Type.STRING);
			q.addScalar("jobTitle", Type.STRING);
			q.addScalar("gender", Type.STRING);
			q.addScalar("fullName", Type.STRING);
			q.addScalar("affiliation", Type.STRING);
			q.addScalar("orcidId", Type.STRING);
			q.setCacheable(false);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(videoId);
			@SuppressWarnings("unchecked")
			List <Object[]> fl =  (List<Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleCreators(fl);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	public List<Creator> findCreatorsForLectureseriesOverTheAssigenedVideos(long lectureseriesId) {
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_CREATORS_FOR_LECTURESERIES_OVER_THE_ASSIGNED_VIDEOS);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("creatorId", Type.LONG);
			q.addScalar("firstName", Type.STRING);
			q.addScalar("lastName", Type.STRING);
			q.addScalar("middleName", Type.STRING);
			q.addScalar("jobTitle", Type.STRING);
			q.addScalar("gender", Type.STRING);
			q.addScalar("fullName", Type.STRING);
			q.addScalar("affiliation", Type.STRING);
			q.addScalar("orcidId", Type.STRING);
			q.setCacheable(false);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(lectureseriesId);
			@SuppressWarnings("unchecked")
			List <Object[]> fl =  (List<Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleCreators(fl);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	public List<Creator> findCreatorsByLectureseriesIdsAndVideoIds (ArrayList<Long> lectureseriesIds, ArrayList<Long> videoIds)  {
		Session session = null;
		try {
			session = openSession();
			String sql = sqlCreatorsByLectureseriesIdsAndVideoIds(lectureseriesIds,videoIds);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("creatorId", Type.LONG);
			q.addScalar("firstName", Type.STRING);
			q.addScalar("lastName", Type.STRING);
			q.addScalar("middleName", Type.STRING);
			q.addScalar("jobTitle", Type.STRING);
			q.addScalar("gender", Type.STRING);
			q.addScalar("fullName", Type.STRING);
			q.addScalar("affiliation", Type.STRING);
			q.addScalar("orcidId", Type.STRING);
			q.setCacheable(false);
			@SuppressWarnings("unchecked")
			List <Object[]> fl =  (List<Object[]>) QueryUtil.list(q, getDialect(),com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleCreators(fl);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	private String sqlCreatorsByLectureseriesIdsAndVideoIds (ArrayList<Long> lectureseriesIds, ArrayList<Long> videoIds) {
		boolean hasLectureseries 	= !lectureseriesIds.isEmpty();
		boolean hasVideos 			= !videoIds.isEmpty();
		String lquery = "";
		String vquery = "";
		
		if (hasLectureseries) {
			// convert the list of ids to a comma-seperated string for the sql query
			String lectureseriesIdsQuery = StringUtils.join(lectureseriesIds, ',');
			lquery = "SELECT creatorId FROM LG_Lectureseries_Creator WHERE lectureseriesId IN (" + lectureseriesIdsQuery + ")";
		}
		if (hasVideos) {
			// convert the list of ids to a comma-seperated string for the sql query
			String videoIdsQuery = StringUtils.join(videoIds, ',');
			vquery = "SELECT creatorId FROM LG_Video_Creator WHERE videoId IN (" + videoIdsQuery + ")";
		}
				
		String query =  "SELECT DISTINCT c.creatorId,c.firstName,c.lastName,c.middleName,c.jobTitle,c.gender,c.fullName FROM (";
		
		if (hasLectureseries && hasVideos) {
			query += lquery + " UNION " + vquery;
 		} else if (hasLectureseries) {
			query += lquery;
		} else if (hasVideos) {
			query += vquery;
		}
		else { ////surpress sql error on empty lists 
			query += "SELECT creatorId FROM LG_Video_Creator WHERE videoId IN (0)";
		}
		
		query += ") AS a JOIN LG_Creator AS c	ON a.creatorId = c.creatorId ORDER BY c.lastName";
					
		return query;
	}
	
	
	private List<Creator> assembleCreators(List<Object[]> objectList){
		List<Creator> fl = new ArrayList<Creator>();
		for (Object[] creator: objectList){
			CreatorImpl f = new CreatorImpl();
			f.setCreatorId((Long) creator[0]);
			f.setFirstName((String) creator[1]);
			f.setLastName((String) creator[2]);
			f.setMiddleName((String) creator[3]);
			f.setJobTitle((String) creator[4]);
			f.setGender((String) creator[5]);
			f.setFullName((String) creator[6]);
			f.setAffiliation((String) creator[7]);
			f.setOrcidId((String) creator[8]);
			fl.add(f);
		}
		return fl;
	}
}
