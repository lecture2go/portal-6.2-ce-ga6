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
package de.uhh.l2g.hook;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * This class hooks the login process to control user handling.
 */
public class LoginAction extends Action {
	// Role Names
	private static final String L2G_ADMIN = "L2Go Admin";
	private static final String L2G_COORDINATOR = "L2Go Coordinator";
	private static final String L2G_PRODUCER = "L2Go Producer";
	private static final String ADMIN = "Administrator";
	private static final String L2G_PRODUCER_PENDING = "L2Go Producer Pending";
	
    public void run(HttpServletRequest req, HttpServletResponse res) {

    	// only handle login processing if qualifiedgroups is defined in properties file 
    	if (!PropsUtil.contains("lecture2go.producer.pending.qualifiedgroups")) {
    		return;
    	}
    	
    	User user;
		try {
			user = PortalUtil.getUser(req);
			// add permissionchecker manually as it may not be available (necessary for role check)
	    	PermissionChecker checker = PermissionCheckerFactoryUtil.create(user);
	    	PermissionThreadLocal.setPermissionChecker(checker);
		} catch (Exception e) {
			// user could not be retrieved from request
			 return;
		}


    	// only handle login processing if user has no active l2go role
    	if (hasRole(user, L2G_COORDINATOR) ||
    		hasRole(user, L2G_PRODUCER) || 
    		hasRole(user, L2G_ADMIN) || 
    		hasRole(user, ADMIN)) {
    			return;
    	}
    	
    	// no active l2go role, check if user is member of qualified groups
    	String[] qualifiedGroups = PropsUtil.getArray("lecture2go.producer.pending.qualifiedgroups");
    	List<String> qualifiedGroupList = Arrays.asList(qualifiedGroups);
    	
    	boolean hasQualifiedUserGroup = hasUserGroup(user, qualifiedGroupList);
    	
		if (!hasQualifiedUserGroup) {
			return;
		} else {
			// set the producer pending role for the user
			if (!hasRole(user, L2G_PRODUCER_PENDING)) {
    			try {
					setRoleForUser(user, L2G_PRODUCER_PENDING);
				} catch (SystemException e) {
					// role could not be set
					return;
				}
			}
		}
    		
    	// get the session, which is used for the redirect
    	HttpSession session = req.getSession();
    	Map params = new HashMap();
    	
		// users with no role selected and which are qualified to self-select their institution are redirected
		LastPath lastPath = new LastPath( "", PropsUtil.get("lecture2go.producer.pending.page"), params );

        session.setAttribute( WebKeys.LAST_PATH, lastPath );

    }
    
    private void setRoleForUser(User user, String roleName) throws SystemException {
        Role liferayRole = RoleLocalServiceUtil.fetchRole(user.getCompanyId(), roleName);
        RoleLocalServiceUtil.addUserRole(user.getUserId(), liferayRole.getRoleId());
        UserLocalServiceUtil.updateUser(user);
    }
    
    private boolean hasRole(User user, String role) {
    	try {
			return RoleServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), role, false);
		} catch (PortalException e) {
			e.printStackTrace();
			// role could not be checked, return false
			return false;			
		} catch (SystemException e) {
			e.printStackTrace();

			// role could not be checked, return false
			return false;
		}
    }
    
    private boolean hasUserGroup(User user, List<String> userGroupNames) {
    	try {
			for (UserGroup userGroup: user.getUserGroups()) {
				if (userGroupNames.contains(userGroup.getName())) {
					return true;
				}
			}
		} catch (SystemException e) {
			// user group could not be checked, return false
			return false;
		}
    	return false;
    }
}
