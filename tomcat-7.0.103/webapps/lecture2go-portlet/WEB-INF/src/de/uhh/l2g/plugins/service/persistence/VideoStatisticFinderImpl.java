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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.uhh.l2g.plugins.model.VideoStatistic;
import de.uhh.l2g.plugins.model.impl.VideoStatisticImpl;

public class VideoStatisticFinderImpl extends BasePersistenceImpl<VideoStatistic> implements VideoStatisticFinder {

	public static final String FIND_BY_DATE_DIFF = VideoFinder.class.getName() + ".findVideoStatistics";
	
	public List<VideoStatistic> findAllStats() {
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(FIND_BY_DATE_DIFF);
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addScalar("dateDiff", Type.INTEGER);
			q.addScalar("timespanId", Type.INTEGER);
			q.addScalar("maxDate", Type.DATE);
			q.addScalar("public", Type.INTEGER);
			q.addScalar("private", Type.INTEGER);
			q.addScalar("total", Type.INTEGER);
			q.addScalar("pubPercent", Type.INTEGER);
			q.addScalar("privPercent", Type.INTEGER);
			q.addScalar("current", Type.DATE);
			q.addScalar("currentPublic", Type.INTEGER);
			q.addScalar("currentPrivate", Type.INTEGER);
			q.addScalar("currentTotal", Type.INTEGER);
			q.addScalar("curPubPercent", Type.INTEGER);
			q.addScalar("curPrivPercent", Type.INTEGER);
			q.addScalar("publicDiff", Type.INTEGER);
			q.addScalar("privateDiff", Type.INTEGER);
			q.addScalar("totalDiff", Type.INTEGER);
			//
			@SuppressWarnings("unchecked")
			List <Object[]> l =  (List<Object[]>) QueryUtil.list(q, getDialect(),com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assemble(l);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				se.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	private List<VideoStatistic> assemble(List<Object[]> objectList){
		List<VideoStatistic> sl = new ArrayList<VideoStatistic>();
		for (Object[] video: objectList){
			VideoStatisticImpl vs = new VideoStatisticImpl();
			vs.setDateDiff((Integer)video[0]);
			vs.setPublicVideos((Integer)video[3]);
			vs.setPrivateVideos((Integer)video[4]);
			vs.setTotalVideos((Integer)video[5]);
			vs.setPubPercent((Integer)video[6]);
			vs.setPrivPercent((Integer)video[7]);
			vs.setCurrentTotal((Integer)video[11]);
			vs.setCurrentPublic((Integer)video[9]);
			vs.setCurrentPrivate((Integer)video[10]);
			vs.setCurrentPubPercent((Integer)video[12]);
			vs.setCurrentPrivPercent((Integer)video[13]);
			vs.setPublicDiff((Integer)video[14]);
			vs.setPrivateDiff((Integer)video[15]);
			vs.setTotalDiff((Integer)video[16]);
			sl.add(vs);
		}
		return sl;		
	}
	
}
