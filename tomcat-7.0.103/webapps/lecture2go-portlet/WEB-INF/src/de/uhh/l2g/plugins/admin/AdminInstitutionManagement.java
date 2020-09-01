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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;

public class AdminInstitutionManagement extends MVCPortlet {
	protected static Log LOG = LogFactoryUtil.getLog(AdminInstitutionManagement.class.getName());
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException, IOException {
		long institutionId = ParamUtil.getLong(renderRequest, "institutionId", 0);
		long hostId = ParamUtil.getLong(renderRequest, "hostId", 0);
		//
		renderRequest.setAttribute("institutionId", institutionId);
		renderRequest.setAttribute("hostId", hostId);
		//
		super.render(renderRequest, renderResponse);
	}

	public void addInstitution(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String institutionName = "";
		String backURL = request.getParameter("backURL");
		try {
			institutionName = ParamUtil.getString(request, "institution");
			long hostId = ParamUtil.getLong(request, "serverselect");
			long parentId = ParamUtil.getLong(request, "parent");
			int sort = ParamUtil.getInteger(request, "order");
			InstitutionLocalServiceUtil.addInstitution(institutionName, hostId, parentId, sort);
			response.sendRedirect(backURL);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed adding Institution " + institutionName);
		}
	}

	/**
	 * Works analogous to addInstitution, but is separate method to enforce
	 * restrictions
	 */
	public void addSubInstitution(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String institutionName = "";
		String backURL = request.getParameter("backURL");
		try {
			institutionName = ParamUtil.getString(request, "subInstitution");
			long hostId = 0;
			long parentId = ParamUtil.getLong(request, "subInstitutionParentId");
			int sort = ParamUtil.getInteger(request, "subInstitutionOrder");
			InstitutionLocalServiceUtil.addInstitution(institutionName, hostId, parentId, sort);
			response.sendRedirect(backURL);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed adding Sub-Institution " + institutionName);
		}
	}

	public void updateInstitution(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String name = ParamUtil.getString(request, "outerListInstitution");
		long institutionId = ParamUtil.getLong(request, "outerListInstitutionId");
		int sort = ParamUtil.getInteger(request, "outerListOrder");
		String backURL = request.getParameter("backURL");
		try {
			InstitutionLocalServiceUtil.updateInstitution(institutionId, name, sort);
			response.sendRedirect(backURL);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed updating Institution", e);
		}
	}

	public void updateSubInstitution(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String institutionName = ParamUtil.getString(request, "innerListInstitution");
		long institutionId = ParamUtil.getLong(request, "innerListInstitutionId");
		int sort = ParamUtil.getInteger(request, "innerListOrder");
		String backURL = request.getParameter("backURL");
		try {
			InstitutionLocalServiceUtil.updateInstitution(institutionId, institutionName, sort);
			response.sendRedirect(backURL);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed updating SubInstitution");
		}
	}

	public void deleteInstitution(ActionRequest request, ActionResponse response) {
		long institutionId = ParamUtil.getLong(request, "outerListInstitutionId");
		String backURL = request.getParameter("backURL");
		try {
			InstitutionLocalServiceUtil.deleteInstitution(institutionId);
			response.sendRedirect(backURL);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed deleting Institution");
		}
	}

	public void deleteSubInstitution(ActionRequest request, ActionResponse response) {
		long institutionId = ParamUtil.getLong(request, "innerListInstitutionId");
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
			InstitutionLocalServiceUtil.deleteInstitution(institutionId);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed to remove SubInstitution");
		}
	}

}
