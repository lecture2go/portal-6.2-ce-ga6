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
package de.uhh.l2g.plugins.util;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;

public class Lecture2GoRoleChecker extends RoleLocalServiceUtil {
	
	private  boolean producer = false;
	private  boolean coordinator = false;
	private  boolean l2gAdmin = false;
	private  boolean student = false;
	private  boolean producerPending = false;
	
	public  boolean isProducer(User user) {
		List<Role> rL = new ArrayList<Role>();
		try {
			rL = user.getRoles();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
		for (Role role : rL) if(role.getName().equals("L2Go Producer"))producer=true;
		return producer;
	}
	public  boolean isCoordinator(User user) {
		List<Role> rL = new ArrayList<Role>();
		try {
			rL = user.getRoles();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
		for (Role role : rL) if(role.getName().equals("L2Go Coordinator"))coordinator=true;
		return coordinator;
	}
	public  boolean isL2gAdmin(User user) {
		List<Role> rL = new ArrayList<Role>();
		try {
			rL = user.getRoles();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
		for (Role role : rL) if(role.getName().equals("L2Go Admin") || role.getName().equals("Administrator") )l2gAdmin=true;
		return l2gAdmin;
	}
	public  boolean isStudent(User user) {
		List<Role> rL = new ArrayList<Role>();
		try {
			rL = user.getRoles();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
		for (Role role : rL) if(role.getName().equals("L2Go Student"))student=true;
		return student;
	}
	public  boolean isProducerPending(User user) {
		List<Role> rL = new ArrayList<Role>();
		try {
			rL = user.getRoles();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
		for (Role role : rL) if(role.getName().equals("L2Go Producer Pending"))producerPending=true;
		return producerPending;
	}
	
	
}
