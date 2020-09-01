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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;

public class AdminStreamerManagement extends MVCPortlet {
	protected static Log LOG = LogFactoryUtil.getLog(AdminInstitutionManagement.class.getName());
	public static final String DEFAULT_STREAMER = "Default";

	public void updateStreamingServer(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String name = ParamUtil.getString(request, "hostName");
		long hostId = ParamUtil.getLong(request, "hostId");
		String hostPrefix = ParamUtil.getString(request, "hostPrefix");
		LOG.info("Trying to update: " + name);
		try {
			HostLocalServiceUtil.updateHost(hostId, name, hostPrefix);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed updating Streaming Server", e);
			//PortalUtil.copyRequestParameters(request, response);
			response.setRenderParameter("mvcPath", "/admin/streamingServerList.jsp");
		}
	}
	
	public void deleteStreamingServer(ActionRequest request, ActionResponse response) {
		long hostId = ParamUtil.getLong(request, "hostId");
		LOG.info("Trying to remove " + hostId);
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Host.class.getName(), request);
			if (hostId > 0)
				HostLocalServiceUtil.deleteHost(hostId, serviceContext);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed deleting Streaming Server", e);
			PortalUtil.copyRequestParameters(request, response);
			response.setRenderParameter("mvcPath", "/admin/streamingServerList.jsp");
		}
	}	
	
	public void addStreamingServer(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String name = ParamUtil.getString(request, "hostName");
		String hostPrefix = ParamUtil.getString(request, "hostPrefix");
		LOG.info("Trying to add " + name);
		try {
			HostLocalServiceUtil.addHost(name, hostPrefix);
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed adding Streaming Server", e);
			response.setRenderParameter("mvcPath", "/admin/streamingServerList.jsp");
		}
	}
	
	public void updateTreeRoot(ActionRequest request, ActionResponse response) throws PortalException, SystemException {
		String institutionName = ParamUtil.getString(request, "treeRoot");
		long institutionId = ParamUtil.getLong(request, "treeRootId");
		LOG.info("Root: " + institutionId);
		try {
			InstitutionLocalServiceUtil.updateInstitution(institutionId, institutionName, 1);
			response.setRenderParameter("mvcPath", "/admin/institutionList.jsp");
		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			LOG.error("Failed updating top level institution name ", e);
			PortalUtil.copyRequestParameters(request, response);
			response.setRenderParameter("mvcPath", "/admin/institutionList.jsp");
		}
	}
	
}
