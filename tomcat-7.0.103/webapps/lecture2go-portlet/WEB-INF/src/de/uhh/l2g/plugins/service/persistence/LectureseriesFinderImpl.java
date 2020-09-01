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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideohitlistLocalServiceUtil;

public class LectureseriesFinderImpl extends BasePersistenceImpl<Lectureseries> implements LectureseriesFinder {

	public static final String FIND_ALL_SEMESTERS = LectureseriesFinder.class.getName() + ".findAllSemesters";
	public static final String FIND_ALL_LECTURESERIES_WITH_OPENACCESS_VIDEOS = LectureseriesFinder.class.getName() + ".findAllLectureseriesWithOpenAccessVideos";
	public static final String FIND_ALL_LECTURESERIES_WITH_PASSWORD = LectureseriesFinder.class.getName() + ".findAllLectureseriesWithPassword";
	public static final String FIND_ALL_LECTURESERIES_FOR_VIDEO = LectureseriesFinder.class.getName() + ".findAllLectureseriesForVideo";
	
	public List<Lectureseries> findAllLectureseriesWhithPassword(){
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_ALL_LECTURESERIES_WITH_PASSWORD);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("number_", Type.STRING);
			q.addScalar("eventType", Type.STRING);
			q.addScalar("categoryId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("shortDesc", Type.STRING);
			q.addScalar("termId", Type.LONG);
			q.addScalar("language", Type.STRING);
			q.addScalar("facultyName", Type.STRING);
			q.addScalar("lectureseriesId", Type.STRING);
			q.addScalar("password_", Type.STRING);
			q.addScalar("approved", Type.STRING);
			q.addScalar("longDesc", Type.STRING);
			q.addScalar("latestOpenAccessVideoId", Type.LONG);
			q.addScalar("videoSort", Type.INTEGER);
			q.addScalar("USID", Type.STRING);
			q.addScalar("previewVideoId", Type.LONG);
			q.setCacheable(false);			
			@SuppressWarnings("unchecked")
			List <Object[]> ls =  (List <Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleLectureseries(ls);
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
	
	public List<Lectureseries> findAllLectureseriesForVideo(Video video){
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_ALL_LECTURESERIES_FOR_VIDEO);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("number_", Type.STRING);
			q.addScalar("eventType", Type.STRING);
			q.addScalar("categoryId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("shortDesc", Type.STRING);
			q.addScalar("termId", Type.LONG);
			q.addScalar("language", Type.STRING);
			q.addScalar("facultyName", Type.STRING);
			q.addScalar("lectureseriesId", Type.STRING);
			q.addScalar("password_", Type.STRING);
			q.addScalar("approved", Type.STRING);
			q.addScalar("longDesc", Type.STRING);
			q.addScalar("latestOpenAccessVideoId", Type.LONG);
			q.addScalar("videoSort", Type.INTEGER);
			q.addScalar("USID", Type.STRING);
			q.addScalar("previewVideoId", Type.LONG);
			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(video.getVideoId());
			q.setCacheable(false);	
			@SuppressWarnings("unchecked")
			List <Object[]> ls =  (List <Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleLectureseries(ls);
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
	
	public List<Lectureseries> findAllLectureseriesWhithOpenaccessVideos(){
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_ALL_LECTURESERIES_WITH_OPENACCESS_VIDEOS);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("number_", Type.STRING);
			q.addScalar("eventType", Type.STRING);
			q.addScalar("categoryId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("shortDesc", Type.STRING);
			q.addScalar("termId", Type.LONG);
			q.addScalar("language", Type.STRING);
			q.addScalar("facultyName", Type.STRING);
			q.addScalar("lectureseriesId", Type.STRING);
			q.addScalar("password_", Type.STRING);
			q.addScalar("approved", Type.STRING);
			q.addScalar("longDesc", Type.STRING);
			q.addScalar("latestOpenAccessVideoId", Type.LONG);
			q.addScalar("videoSort", Type.INTEGER);
			q.addScalar("USID", Type.STRING);
			q.addScalar("previewVideoId", Type.LONG);
			q.setCacheable(false);			
			@SuppressWarnings("unchecked")
			List <Object[]> ls =  (List <Object[]>) QueryUtil.list(q, getDialect(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleLectureseries(ls);
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
	
	public List<Lectureseries> findLatest(int limit) {
		return findFilteredByInstitutionParentInstitutionTermCategoryCreatorSearchString(new Long(0), new Long(0), new Long(0), new Long(0), new Long(0), "", limit);
	}
	
	public List<Lectureseries> findFilteredByInstitutionParentInstitutionTermCategoryCreatorSearchString(Long institutionId, Long parentInstitutionId, Long termId, Long categoryId, Long creatorId, String searchQuery) {
		return findFilteredByInstitutionParentInstitutionTermCategoryCreatorSearchString(institutionId, parentInstitutionId, termId, categoryId, creatorId, searchQuery, 0);
	}
	/**
	 * Queries the database for data by single filter id's
	 * @param institutionId
	 * @param parentInstitutionId
	 * @param termId
	 * @param categoryId
	 * @param creatorId
	 * @return a list with lectureseries which fit to the given filters
	 */
	@SuppressWarnings("unchecked")
	public List<Lectureseries> findFilteredByInstitutionParentInstitutionTermCategoryCreatorSearchString(Long institutionId, Long parentInstitutionId, Long termId, Long categoryId, Long creatorId, String searchQuery, int limit) {
		int start =com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS;
		int stop =com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS;
		List<Lectureseries> ret = new ArrayList<Lectureseries>();
		if (limit>0){
			start=0;
			stop = limit;
		}
		//
		Session session = null;
		try {
			session = openSession();
			String sql = sqlFilterForOpenAccessLectureseries(institutionId, parentInstitutionId, termId, categoryId, creatorId, searchQuery);
			SQLQuery q = session.createSQLQuery(sql);
	        q.addEntity("Lectureseries", LectureseriesImpl.class);
			q.setCacheable(false);
			
			/*
			 *  the filter query has a variable number of parameters and those are used in subqueries (lectureseries/single videos/ all videos if a search is used)
			 *  an array is created with the specific filter values and iterated for every subquery
			 */
			String searchString = searchQuery.replace("&amp;", "&");//get from entity &amp; only the character & for this specific search
			QueryPos qPos = QueryPos.getInstance(q);
			boolean hasSearch = (searchString.trim().length()>0);
			int y=1;
			if (hasSearch)y=2;
			//
			for (int i=0 ;i<=y;i++){ //for all queries "lQueryForSearch" "lQuery" and "vQuery"
				if (termId > 0) qPos.add(termId);
				if (creatorId > 0) qPos.add(creatorId);
				if (categoryId > 0) qPos.add(categoryId);
				if (institutionId > 0) qPos.add(institutionId);
				if (parentInstitutionId > 0) qPos.add(parentInstitutionId);					
				if (hasSearch) qPos.add("%" + searchString + "%");
			}
			ret = (List<Lectureseries>) QueryUtil.list(q, getDialect(), start, stop);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return ret;
	}
		
	private String sqlFilterForOpenAccessLectureseries(Long institutionId, Long institutionParentId, Long termId, Long categoryId, Long creatorId, String searchQuery) {
		boolean hasInstitution 			= (institutionId > 0);
		boolean hasParentInstitution	= (institutionParentId > 0);
		boolean hasTerm 				= (termId > 0);
		boolean hasCategory 			= (categoryId > 0);
		boolean hasCreator 				= (creatorId > 0);
		boolean hasSearch 				= (searchQuery.trim().length()>0);
		
		// this is an additional query only used for searching. videos which are part of a lectureseries must be searched for the searchquery but are not relevant of the normal filtering
		
		//for lecture series
		String lQuery = "SELECT l.number_, l.eventType, l.categoryId, l.name, l.shortDesc, l.termId, \"\" AS language, \"\" AS facultyName, l.lectureseriesId, NULL AS password_, 1 AS approved, l.longDesc, l. latestOpenAccessVideoId, l.latestVideoUploadDate, l.latestVideoGenerationDate, l.videoSort, l.USID, l.previewVideoId FROM LG_Video v ";
			   lQuery+= "JOIN LG_Lectureseries AS l ON (v.lectureseriesId = l.lectureseriesId)";
		String lQueryForSeach="";
		
		//for videos
		String vQuery = "SELECT \"00.000\" AS number_, NULL AS eventType, 0 AS categoryId, v.title AS name, v.title AS shortDesc, v.termId, \"\" AS language, \"\" AS facultyName, v.videoId AS lectureseriesId, NULL AS password_, 1 AS approved, v.title AS longDesc, v.lectureseriesId AS latestOpenAccessVideoId, v.uploadDate AS latestVideoUploadDate, v.generationDate as latestVideoGenerationDate, 1 as videoSort, \"\"  as USID, 0 as previewVideoId FROM LG_Video v ";
		
		//final query
		String query = "";

		if (hasInstitution || hasParentInstitution) {
			lQuery += "INNER JOIN LG_Lectureseries_Institution AS li ON ( l.lectureseriesId = li.lectureseriesId ) ";
			vQuery += "INNER JOIN LG_Video_Institution AS vi ON ( v.videoId = vi.videoId ) ";
		}

		if (hasTerm) {
			lQuery += "INNER JOIN LG_Term AS t ON ( l.termId = t.termId ) ";
			vQuery += "INNER JOIN LG_Term AS t ON ( v.termId = t.termId ) ";
		}
		
		if (hasCreator) {
			lQuery += "INNER JOIN LG_Lectureseries_Creator AS lc ON ( l.lectureseriesId = lc.lectureseriesId ) ";
			vQuery += "INNER JOIN LG_Video_Creator AS vc ON ( v.videoId = vc.videoId ) ";
		}
		
		if (hasCategory) {
			vQuery += "INNER JOIN LG_Video_Category AS vcat ON ( v.videoId = vcat.videoId ) ";
		}
		
		if(hasSearch){
			lQueryForSeach = lQuery;
			String INNER_JOIN = "INNER JOIN LG_Tagcloud AS tag ON (v.videoId = tag.objectId AND tag.objectClassType=\"de.uhh.l2g.plugins.model.impl.VideoImpl\") ";
			lQuery  += "INNER JOIN LG_Tagcloud AS tag ON (v.lectureseriesId = tag.objectId AND tag.objectClassType=\"de.uhh.l2g.plugins.model.impl.LectureseriesImpl\")  ";
			lQueryForSeach += INNER_JOIN;
			vQuery  += INNER_JOIN;			
		}
		
		String WHERE = "WHERE v.openAccess=1 ";
		lQuery += WHERE;
		lQueryForSeach += WHERE;
		vQuery += WHERE + "AND v.lectureseriesId<0 ";
		
		if (hasTerm) {
			String termQuery = "AND t.termId = ? ";
			lQuery += termQuery;
			vQuery += termQuery;
			lQueryForSeach += termQuery;
		}
		
		if (hasCreator) {
			lQuery += "AND lc.creatorId = ? ";
			vQuery += "AND vc.creatorId = ? ";
			lQueryForSeach += "AND lc.creatorId = ? ";
		}
	
		if (hasCategory) {
			lQuery += "AND l.categoryId = ? ";
			vQuery += "AND vcat.categoryId = ? ";
			lQueryForSeach += "AND l.categoryId = ? ";
		}

		if (hasInstitution) {
			lQuery += "AND li.institutionId = ? ";
			vQuery += "AND vi.institutionId = ? ";
			lQueryForSeach += "AND li.institutionId = ? ";
		}

		if (hasParentInstitution) {
			lQuery += "AND li.institutionParentId = ? ";
			vQuery += "AND vi.institutionParentId = ? ";
			lQueryForSeach += "AND li.institutionParentId = ? ";
		}

		if(hasSearch){
			String LIKE="AND tag.tags LIKE ? ";
			lQuery += LIKE;
			lQueryForSeach += LIKE;
			vQuery += LIKE + "AND v.lectureseriesId<0 ";			
		}
		String GROUP = "GROUP BY v.lectureseriesId ";
		lQueryForSeach += GROUP;
		lQuery += GROUP;
		
		query = "SELECT * FROM  ( ";
		query+= lQuery;
		query+= "UNION "; 
		query+= vQuery;
		if(hasSearch){
			query+= "UNION "; 
			query+= lQueryForSeach;				
		}
		query+= ") ";
		query+= "AS a ";
		query+= "GROUP BY lectureseriesId ";
		query+= "ORDER BY a.latestVideoUploadDate DESC";	
    	 
	    return query;
	}

	public List<Lectureseries> findFilteredByApprovedSemesterFacultyProducer(Integer approved, Long termId, Long facultyId, Long producerId) {
		Session session = null;
		try {
			session = openSession();
			String sql = sqlFilterForLectureseries(approved, termId, facultyId, producerId);
			SQLQuery q = session.createSQLQuery(sql);
			q.addScalar("number_", Type.STRING);
			q.addScalar("eventType", Type.STRING);
			q.addScalar("categoryId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("shortDesc", Type.STRING);
			q.addScalar("termId", Type.LONG);
			q.addScalar("language", Type.STRING);
			q.addScalar("facultyName", Type.STRING);
			q.addScalar("lectureseriesId", Type.STRING);
			q.addScalar("password_", Type.STRING);
			q.addScalar("approved", Type.STRING);
			q.addScalar("longDesc", Type.STRING);
			q.addScalar("latestOpenAccessVideoId", Type.LONG);
			q.addScalar("videoSort", Type.INTEGER);
			q.addScalar("USID", Type.STRING);
			q.addScalar("previewVideoId", Type.LONG);
			q.setCacheable(false);
			@SuppressWarnings("unchecked")
			List <Object[]> l =  (List<Object[]>) QueryUtil.list(q, getDialect(),com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assembleLectureseries(l);
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
	
	private List<Lectureseries> assembleLectureseries(List<Object[]> objectList){
		List<Lectureseries> ll = new ArrayList<Lectureseries>();
		for (Object[] lectser: objectList){
			LectureseriesImpl l = new LectureseriesImpl();
			l.setNumber((String) lectser[0]);
			l.setEventType((String) lectser[1]);
			l.setCategoryId((Long) lectser[2]);
			l.setName((String) lectser[3]);
			l.setShortDesc((String) lectser[4]);
			l.setTermId((Long) lectser[5]);
			l.setLanguage((String) lectser[6]);
			l.setFacultyName((String) lectser[7]);
			l.setLectureseriesId(new Long((String) lectser[8]));
			l.setPassword((String) lectser[9]);
			l.setApproved(new Integer((String) lectser[10]));
			l.setLongDesc((String) lectser[11]);
			l.setLatestOpenAccessVideoId((Long) lectser[12]);
			l.setVideoSort((Integer) lectser[13]);
			l.setUSID((String) lectser[14]);
			l.setPreviewVideoId((Long) lectser[15]);
			//additional parameter
			try{
				String string = (String)lectser[16];
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				Date date = format.parse(string);
				l.setLatestVideoUploadDate(date);
			}catch (Exception e){}
			// 
			ll.add(l);
		}
		return ll;
	}
	
	private String sqlFilterForLectureseries(Integer approved, Long termId, Long facultyId, Long producerId) {
		// build query
		String query = "SELECT c.number_, c.eventType, c.categoryId, c.name, c.shortDesc, c.longDesc, c.termId, c.language, c.facultyName, c.lectureseriesId, c.password_, c.approved, c.longDesc, c.latestOpenAccessVideoId, c.videoSort, c.USID, c.previewVideoId ";
			   query += "FROM LG_Lectureseries AS c ";

		if (facultyId > 0) {
			query += "INNER JOIN LG_Lectureseries_Institution AS ce ON ( c.lectureseriesId = ce.lectureseriesId ) ";
			query += "INNER JOIN LG_Institution AS e ON ( ce.institutionId = e.institutionId ) ";
		}

		if (producerId > 0) {
			query += "INNER JOIN LG_Producer_Lectureseries AS pc ON ( c.lectureseriesId = pc.lectureseriesId ) ";
			query += "INNER JOIN LG_Producer AS p ON ( pc.producerId = p.producerId ) ";
		}

		if (termId>0 || (approved==1 || approved==0) || facultyId > 0 || producerId > 0) {
			query += "WHERE ";
			int i = 0;
			if (termId > 0) {
				query += "c.termId = \""+termId + "\" ";
				i++;
			}

			if (approved==1 || approved==0) {
				query += i > 0 ? "AND " : "";
				query += "c.approved = "+approved + " ";
				i++;
			}

			if (facultyId > 0) {
				query += i > 0 ? "AND " : "";
				query += "ce.institutionId IN ";
				query += "(select institutionId from LG_Institution AS ein WHERE ein.parentId = "+facultyId+" OR ein.institutionId = "+facultyId+") ";
				i++;
			}

			if (producerId > 0) {
				query += i > 0 ? "AND " : "";
				query += "pc.producerId = "+producerId + " ";
				i++;
			}
		}
		query += "GROUP BY c.lectureseriesId ";
		query += "ORDER BY c.name ASC ";

		return query;
	}
	
}
