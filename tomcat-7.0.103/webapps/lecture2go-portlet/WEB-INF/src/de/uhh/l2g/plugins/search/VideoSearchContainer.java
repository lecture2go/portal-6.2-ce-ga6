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

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.Validator;

import de.uhh.l2g.plugins.model.Video;

public class VideoSearchContainer extends SearchContainer<Video> {
	public static final String EMPTY_RESULTS_MESSAGE = "No Record Found";
	public static final int DEFAULT_DELTA = 10;
	static List<String> headerNames = new ArrayList<String>();
	static {
		headerNames.add("coordinatorId");
		headerNames.add("producerId");
		headerNames.add("lectureseriesId");
	}

	public VideoSearchContainer(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(portletRequest, new VideoDisplayTerms(portletRequest), new VideoDisplayTerms(portletRequest), DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);
		VideoDisplayTerms displayTerms = (VideoDisplayTerms) getDisplayTerms();
		//
		String cId = "0"; 
		String pId = "0"; 
		//check weather the coordinator has been set
		try{ 
			cId = portletRequest.getAttribute("coordinatorId").toString(); 
			if(Validator.isDigit(cId) && !cId.equals("0")){
				displayTerms.setCoordinatorId(cId);
			}
		}catch(NullPointerException npe){}
		//check weather the producer has been set
		try{ 
			pId = portletRequest.getAttribute("producerId").toString(); 
			if(Validator.isDigit(pId) && !pId.equals("0")){
				displayTerms.setProducerId(pId);
			}
		}catch(NullPointerException npe){}
		//
		iteratorURL.setParameter("coordinatorId", displayTerms.getCoordinatorId());
		iteratorURL.setParameter("producerId", displayTerms.getProducerId());
		iteratorURL.setParameter("lectureseriesId", displayTerms.getLectureseriesId());
	}
}
