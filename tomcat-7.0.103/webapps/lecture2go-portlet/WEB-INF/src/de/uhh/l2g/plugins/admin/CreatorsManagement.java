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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.impl.CreatorImpl;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.util.OaiPmhManager;

public class CreatorsManagement extends MVCPortlet {

	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException, IOException {
		//
	}
		
	public void add(ActionRequest request, ActionResponse response) throws SystemException, PortalException{
		String backURL = request.getParameterMap().get("backURL")[0];
		String fn=request.getParameter("firstName");
		String mn=request.getParameter("middleName");
		String ln=request.getParameter("lastName");
		String t=request.getParameter("jobTitle");
		String a=request.getParameter("affiliation");
		String o=request.getParameter("orcidId");
		if (isValid(fn, ln)) {
			Creator creator = new CreatorImpl();
			creator.setFirstName(fn);
			creator.setMiddleName(mn);
			creator.setLastName(ln);
			creator.setJobTitle(t);
			creator.setAffiliation(a);
			creator.setOrcidId(o);
			creator.setFullName(fullName(fn, mn, ln, t));
			CreatorLocalServiceUtil.addCreator(creator);
			try {
				response.sendRedirect(backURL);
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	public void edit(ActionRequest request, ActionResponse response) throws SystemException, PortalException{
		long reqCreatorId = new Long(request.getParameterMap().get("creatorId")[0]);
		String backURL = request.getParameterMap().get("backURL")[0];
		String fn=request.getParameter("firstName");
		String mn=request.getParameter("middleName");
		String ln=request.getParameter("lastName");
		String t=request.getParameter("jobTitle");
		String a=request.getParameter("affiliation");
		String o=request.getParameter("orcidId");
		if (isValid(fn, ln)) {
			Creator creator = CreatorLocalServiceUtil.getCreator(reqCreatorId);
			creator.setFirstName(fn);
			creator.setMiddleName(mn);
			creator.setLastName(ln);
			creator.setJobTitle(t);
			creator.setAffiliation(a);
			creator.setOrcidId(o);
			creator.setFullName(fullName(fn, mn, ln, t));
			CreatorLocalServiceUtil.updateCreator(creator);
			
			// update the datestamp of all OAIRecords linked to the changed creator
			OaiPmhManager.modifyByCreator(reqCreatorId);
			try {
				response.sendRedirect(backURL);
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		

	}
	
	public void delete(ActionRequest request, ActionResponse response) throws SystemException, PortalException{
		long reqCreatorId = new Long(request.getParameterMap().get("creatorId")[0]);
		String backURL = request.getParameterMap().get("backURL")[0];
		//Video_Creator, Lecture_Creator, Creator
		CreatorLocalServiceUtil.deleteById(reqCreatorId);
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	private boolean isValid(String fn, String ln) {
		String _fn = fn.trim();
		String _ln = ln.trim();
		return !"".equals(_fn) && !"".equals(_ln) && _fn.length() > 1
				&& _ln.length() > 1;
	}

	private String fullName(String fn, String mn, String ln, String t) {
		return (t.trim() + " " + (fn.trim() + " " + mn.trim()).trim() + " " + ln
				.trim()).trim();
	}

}
