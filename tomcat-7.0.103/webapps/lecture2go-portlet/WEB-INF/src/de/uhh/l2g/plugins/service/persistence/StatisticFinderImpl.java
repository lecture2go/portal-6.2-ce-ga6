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

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.uhh.l2g.plugins.model.Statistic;

public class StatisticFinderImpl extends BasePersistenceImpl<Statistic> implements StatisticFinder {

	public static final String CREATE_VIDEO_STATISTIC_VIEW = StatisticFinder.class.getName() + ".createVideoStatisticView";
	public static final String REMOVE_VIDEO_STATISTIC_TABLE = StatisticFinder.class.getName() + ".removeVideoStatisticTable";
	public static final String ADD_NEW_STATISTIC_ENTRY = StatisticFinder.class.getName() + ".addNewStatisticEntry";
	public static final String GET_ALL_STATISTICS = StatisticFinder.class.getName() + ".getAllStatistics";

	public JSONObject findAllStatistics ()  {
		Session session = null;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(GET_ALL_STATISTICS);
			SQLQuery q = session.createSQLQuery(sql);
			@SuppressWarnings("unchecked")
			List <Object[]> sl =  (List<Object[]>) QueryUtil.list(q, getDialect(),com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS , com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			return assemble(sl);
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

	private JSONObject assemble(List<Object[]> objectList) throws JSONException{
		JSONArray ja = new JSONArray();
		JSONObject mainObj = new JSONObject();
		for (Object[] obj: objectList){
			JSONObject jo = new JSONObject();
			jo.put("public", obj[0]);
			jo.put("private", obj[1]);
			jo.put("total", obj[2]);
			jo.put("date", obj[3]);
			jo.put("timespan", obj[4]);
			jo.put("publicDiff", obj[5]);
			jo.put("privateDiff", obj[6]);
			jo.put("totalDiff", obj[7]);
			ja.put(jo);
		}
		mainObj.put("statistic", ja);
		return mainObj;
	}
	
	/**
	 * WARNING: As Custom Query is restricted executeQuery() 
	 * this methods uses Core DB functionality of Liferay 
	 */
	public int createVideoStatisticView() {
		Session session = null;
		int out = 0;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(CREATE_VIDEO_STATISTIC_VIEW);

			DB db = DBFactoryUtil.getDB();
			//This requires to use executeUpdate instead of executeQuery
			db.runSQL(sql);
			
	      } catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return out;
	}

	/**
	 * WARNING: As Custom Query is restricted executeQuery() 
	 * this methods uses Core DB functionality of Liferay 
	 */
	public int removeVideoStatisticTable() {
		Session session = null;
		int out = 0;
		try {
			session = openSession();
			String sql = CustomSQLUtil.get(REMOVE_VIDEO_STATISTIC_TABLE);
			
			DB db = DBFactoryUtil.getDB();
			//This requires to use executeUpdate instead of executeQuery
			db.runSQL(sql);
			
	      } catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				//e.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return out;
	}


}
