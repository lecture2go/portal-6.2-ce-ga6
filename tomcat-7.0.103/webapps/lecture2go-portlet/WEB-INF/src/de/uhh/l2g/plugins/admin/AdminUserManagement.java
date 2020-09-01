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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.model.Coordinator;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Institution_Host;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.impl.CoordinatorImpl;
import de.uhh.l2g.plugins.model.impl.HostImpl;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.ProducerImpl;
import de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.util.EmailManager;
import de.uhh.l2g.plugins.util.PermissionManager;

public class AdminUserManagement extends MVCPortlet {
	public static final String L2G = "L2Go";
	public static final String L2G_ADMIN = "L2Go Admin";
	public static final String L2G_COORDINATOR = "L2Go Coordinator";
	public static final String L2G_PRODUCER = "L2Go Producer";
	public static final String L2G_STUDENT = "L2Go Student";
	public static final String L2G_PRODUCER_PENDING = "L2Go Producer Pending";

	public void viewRole(ActionRequest request, ActionResponse response) throws SystemException, PortalException {
		//updateUsersScreenName();
		// logged in user
		User remoteUser = UserLocalServiceUtil.getUser(new Long(request.getRemoteUser()));
		
		// requested user
		long reqUserId = new Long(request.getParameterMap().get("userId")[0]);
		
		User reqUser = UserLocalServiceUtil.getUser(reqUserId);
		request.setAttribute("reqUser", reqUser);

		// faculties, producer, coordinator, student, admin - objects
		// check whether user is coordinator
		
		//check whether user is l2go coordinator
		boolean isL2goCoordinator = false;
		for (int i = 0; i < reqUser.getRoles().size(); i++)
			if(reqUser.getRoles().get(i).getName().equals(L2G_COORDINATOR)) isL2goCoordinator = true;
		
		Coordinator reqCoord = CoordinatorLocalServiceUtil.createCoordinator(reqUserId);
		//not coordinator
		if(!isL2goCoordinator){
			deleteL2GoRole(L2G_COORDINATOR, reqUser);
		}else{
		//is coordinator
			try {
				reqCoord = CoordinatorLocalServiceUtil.getCoordinator(reqUserId);
			} catch (PortalException e) {
			}
		}
		request.setAttribute("reqCoordinator", reqCoord);
		
		//check whether user is l2go coordinator
		boolean isL2goProducer = false;
		for (int i = 0; i < reqUser.getRoles().size(); i++)
			if(reqUser.getRoles().get(i).getName().equals(L2G_PRODUCER)) isL2goProducer = true;
		// producer
		Producer reqProd = ProducerLocalServiceUtil.createProducer(reqUserId);		
		if(!isL2goProducer){
			deleteL2GoRole(L2G_PRODUCER, reqUser);
		}else{
			try {
				reqProd = ProducerLocalServiceUtil.getProducer(reqUserId);
			} catch (PortalException e) {
			}
		}
		request.setAttribute("reqProducer", reqProd);
		
		System.out.println(reqProd.getProducerId() +" "+ reqUser.getUserId() + " " + remoteUser.getUserId());
		
		// institutions
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();
		
		//l2go admin is logged in 
		//Remark: Test could be solved rolebased, current action is counter intuitive (ability to add admins 
		//is generallaly expected to be independent from visibility of Institutions and Coord./Prod. Mapping))
		boolean permissionAdmin = permissionChecker.hasPermission(remoteUser.getGroupId(), User.class.getName(), remoteUser.getPrimaryKey(), "ADD_L2GOADMIN");
		List<Institution> cfL = new ArrayList<Institution>();//for coordinator
		List<Institution> pfL = new ArrayList<Institution>();//for producer
		
		if(permissionAdmin){//logged in as l2go admin
			List<Institution> allFacil = InstitutionLocalServiceUtil.getByLevel(1);//all institutions for coordinator drop down menu
			List<User> allCoord = UserLocalServiceUtil.getRoleUsers(RoleLocalServiceUtil.getRole(remoteUser.getCompanyId(), L2G_COORDINATOR).getRoleId()); 
			List<Institution> assignedInstitutions = new ArrayList<Institution>();		
			try{
				for (int i = 0; i < allCoord.size(); i++){
					Long cId = allCoord.get(i).getUserId();
					Coordinator c = CoordinatorLocalServiceUtil.getCoordinator(cId);
					Institution instId = InstitutionLocalServiceUtil.getInstitution(c.getInstitutionId());
					assignedInstitutions.add(instId);
				}
			}catch(Exception e){}
			List<Institution> notAssignedInstitutions = new ArrayList<Institution>();
  			for(int i = 0; i < allFacil.size(); i++){
				boolean assigned = false;
				for (int j = 0; j < assignedInstitutions.size(); j++){
					if(allFacil.get(i).equals(assignedInstitutions.get(j)) && allFacil.get(i).getInstitutionId()!=reqCoord.getInstitutionId())assigned=true;
				}
				if(!assigned)notAssignedInstitutions.add(allFacil.get(i));
			}
				
			cfL = notAssignedInstitutions;	
			pfL = allFacil;//all institutions for producer drop down menu
		}
		
		//l2go coordinator is logged in
		//check if current user can add producers... on site scope
		boolean permissionCoordinator = permissionChecker.hasPermission(remoteUser.getGroupId(), User.class.getName(), remoteUser.getPrimaryKey(), "ADD_L2GOPRODUCER");		
		Coordinator loggedInCoord = CoordinatorLocalServiceUtil.createCoordinator(remoteUser.getUserId());
		if(permissionCoordinator && !permissionAdmin){//logged in as coordinator
			try{
				loggedInCoord = CoordinatorLocalServiceUtil.getCoordinator(remoteUser.getUserId());
				//add whole institution object which is assigned to coordinator
				cfL.add(InstitutionLocalServiceUtil.getInstitution(loggedInCoord.getInstitutionId()));//institutions for coordinator
				//logged in is coordinator and requested producer belong to different institutions
				if(loggedInCoord.getInstitutionId()==reqProd.getInstitutionId())pfL=cfL; //pfL = not assigned institutions
				else {
					if(reqProd.getInstitutionId()!=0){
						Long instId = reqProd.getInstitutionId();
						Institution inst = InstitutionLocalServiceUtil.getInstitution(instId);
						pfL.add(inst);
					}
					else{
						Institution inst = InstitutionLocalServiceUtil.getInstitution(loggedInCoord.getInstitutionId()); 
						pfL.add(inst);
					}
				}
			}catch(PortalException e){
			}
		}
		request.setAttribute("loggedInCoordinator", loggedInCoord);
		request.setAttribute("cInstitutions", cfL);
		request.setAttribute("pInstitutions", pfL);
		
		//check whether user is student
		boolean isStudent = false;
		List<Role> rl = reqUser.getRoles(); 
		for (int i = 0; i < rl.size(); i++)
			if(reqUser.getRoles().get(i).getName().equals(L2G_STUDENT)) isStudent = true;
		request.setAttribute("isStudent", isStudent);
		
		//check whether user is l2go admin
		boolean isL2goAdmin = false;
		for (int i = 0; i < reqUser.getRoles().size(); i++)
			if(reqUser.getRoles().get(i).getName().equals(L2G_ADMIN)) isL2goAdmin = true;
		request.setAttribute("isL2goAdmin", isL2goAdmin);
		String backURL = request.getParameter("backURL");		
		request.setAttribute("backURL", backURL);
		response.setRenderParameter("jspPage", "/admin/editL2GoRole.jsp");
	}
	
	public void updateUsersScreenName(){
		List<Producer> pl = new ArrayList<Producer>();
		try {
			pl = ProducerLocalServiceUtil.getAllProducers(0, 300);
			ListIterator<Producer> pit = pl.listIterator();
			while (pit.hasNext()){
				Producer p = pit.next();
				try {
					User u = UserLocalServiceUtil.getUser(p.getProducerId());
					UserLocalServiceUtil.updateScreenName(u.getUserId(), p.getHomeDir());
				} catch (PortalException e) {
					//e.printStackTrace();
				}
			}
		} catch (SystemException e) {
			//e.printStackTrace();
		}
	}
	public static List<Role> getL2GoRoles(User u) throws SystemException{
		//prepare l2go roles
		List<Role> l2goRoles = new ArrayList<Role>();
		List<Role> allRoles = RoleLocalServiceUtil.getRoles(u.getCompanyId());
		for(int i = 0; i < allRoles.size(); i++){
			Role r = allRoles.get(i);
			if(r.getName().contains(L2G)){
				l2goRoles.add(r);
			}
		}
		return l2goRoles;
	}
	
	
	public void editRole(ActionRequest request, ActionResponse response) throws SystemException, NumberFormatException, PortalException, IOException {
		User u = UserLocalServiceUtil.getUser(new Long(request.getParameter("userId")));
		
		// coordinator request --- start
		try{//parameter is null for not authorized user to use this function
			if (!request.getParameter("cfId").isEmpty())
				handleCoordinatorRequest(request);
			else {
				// delete role for user
				deleteL2GoRole(L2G_COORDINATOR, u);
			}			
		}catch(NullPointerException npe){}
		// coordinator request --- end

		// producer request --- start
		try{//parameter is null for not authorized user to use this function
			if (!request.getParameter("pfId").isEmpty()) {
				handleProducerRequest(request);
				sendNotifyMailNewProducer(request.getLocale(),u);
			} else {
				// delete role for user
				deleteL2GoRole(L2G_PRODUCER, u);
			}
		}catch(NullPointerException npe){}
		// producer request --- end		
		
		// student request --- start
		if (request.getParameter("isStud").equals("true"))
			addL2GoRole(L2G_STUDENT, u);
		else
			deleteL2GoRole(L2G_STUDENT, u);
		// student request --- end	
		
		// l2go admin request --- start
		try{//parameter is null for not authorized user to use this function
			if (request.getParameter("isL2goAdmin").equals("true"))
				addL2GoRole(L2G_ADMIN, u);
			else
				deleteL2GoRole(L2G_ADMIN, u);
		}catch(NullPointerException npe){}
		// l2go admin request --- end
		
		// remove l2go pending role if set
		deleteL2GoRole(L2G_PRODUCER_PENDING, u);
		
		String backURL = request.getParameter("backURL");
		try {
			response.sendRedirect(backURL);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	private void deleteL2GoRole(String n, User u) throws PortalException, SystemException{
		Role r = RoleLocalServiceUtil.getRole(u.getCompanyId(), n);
		RoleLocalServiceUtil.deleteUserRole(u.getUserId(), r);
		UserLocalServiceUtil.deleteRoleUser(r.getRoleId(), u.getUserId());		
		if(n.equals(L2G_COORDINATOR)){
			// remove role from l2go coordinator table, because empty
			if (CoordinatorLocalServiceUtil.fetchCoordinator(u.getUserId()) != null)
				CoordinatorLocalServiceUtil.deleteCoordinator(u.getUserId());			
		}
		if(n.equals(L2G_PRODUCER)){
			// remove role from l2go producer table, because empty
			Producer p = ProducerLocalServiceUtil.fetchProducer(u.getUserId());
			if ( p != null) {
				p.setInstitutionId(0);
				p.setApproved(0);
				ProducerLocalServiceUtil.updateProducer(p);	
			}
		}
	}
	
	private void addL2GoRole(String n, User u) throws PortalException, SystemException{
		// add role to user
		Role nRo = RoleLocalServiceUtil.getRole(u.getCompanyId(), n);
		//user roles
		List<Role> uRo = RoleLocalServiceUtil.getUserRoles(u.getUserId());
		//add new role to user roles
		uRo.add(nRo);
		//set roles to user
		RoleLocalServiceUtil.addUserRoles(u.getUserId(), uRo);		
	}
	
	private void handleCoordinatorRequest(ActionRequest request) throws NumberFormatException, PortalException, SystemException {
		User u = UserLocalServiceUtil.getUser(new Long(request.getParameter("userId")));
		
		Coordinator c = new CoordinatorImpl();
		try {
			c = CoordinatorLocalServiceUtil.getCoordinator(new Long(u.getUserId()));
		} catch (PortalException e) {
			c = CoordinatorLocalServiceUtil.createCoordinator(u.getUserId());
		}

		// add role to user
		addL2GoRole(L2G_COORDINATOR, u);
		
		// save role to l2go coordinator table
		c.setCoordinatorId(u.getUserId());
		c.setInstitutionId(new Long(request.getParameter("cfId")));
		CoordinatorLocalServiceUtil.updateCoordinator(c);// add or edit entry
	}
	
	private void handleProducerRequest(ActionRequest request) throws NumberFormatException, PortalException, SystemException, IOException {
		User u = UserLocalServiceUtil.getUser(new Long(request.getParameter("userId")));
		Producer p = new ProducerImpl();
		boolean existingProducer = false;
		//initialize producer
		try {
			// throws exception if producer does not exists
			p = ProducerLocalServiceUtil.getProducer(u.getUserId());
			existingProducer = true;
		} catch (Exception e) {
			p.setProducerId(u.getUserId());
		}
		// save role to l2go producer table
		p.setInstitutionId(new Long(request.getParameter("pfId")));
		p.setApproved(1);
		// home directory
		if (!existingProducer) {
			// only set home directory for new producers
			p.setHomeDir(u.getScreenName());
		}
		p.setIdNum(u.getScreenName());
		// repository for producer
		Host h = new HostImpl();
		try{
			h = Institution_HostLocalServiceUtil.getByInstitution(p.getInstitutionId());
			// host to producer 
			p.setHostId(h.getHostId());
		}catch(Exception e){
			SessionErrors.add(request, "host-or-institution-error");
		}
		if(createProducersRepository(h, p)){
			// add or update entry
			ProducerLocalServiceUtil.updateProducer(p);				
			// finaly add role to user
			addL2GoRole(L2G_PRODUCER, u);
			UserLocalServiceUtil.addRoleUser(RoleLocalServiceUtil.getRole(u.getCompanyId(), L2G_PRODUCER).getRoleId(), u.getUserId());	
		}else{
			SessionErrors.add(request, "system-permissions-error");
		}
	}
	
	public static boolean createProducersRepository(Host host, Producer producer) throws IOException{
		boolean ret = false;
		File folder = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/");
		Runtime runtime = Runtime.getRuntime();
		if (!folder.exists()) {
			//create repository for producer
			if (folder.mkdir()) {
				String[] cmdArray = {PropsUtil.get("lecture2go.shell.bin"), "-c", "chown nobody " + folder.getAbsolutePath() };
				runtime.exec(cmdArray);
				String[] cmdArray1 = { PropsUtil.get("lecture2go.shell.bin"), "-c", "chown nobody:nobody " + folder.getAbsolutePath() };
				runtime.exec(cmdArray1);
				String[] cmdArray2 = { PropsUtil.get("lecture2go.shell.bin"), "-c", "chmod 701 " + folder.getAbsolutePath() };
				runtime.exec(cmdArray2);

				ret = true;
			}
		}else{
			ret = true;
		}
		//link to main server dir
		File prodFolder = new File(PropsUtil.get("lecture2go.httpstreaming.video.repository") + "/" + producer.getInstitutionId() + "l2g" + producer.getHomeDir());
		if (!prodFolder.exists()) {
			String cmd = "ln -s " + folder.getAbsolutePath() + " " + prodFolder.getAbsolutePath();
			runtime.exec(cmd);
		}
		return ret;
	}
	
	public void initL2goRoles(User u, RenderRequest renderRequest) throws SystemException, PortalException{
	   
	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
		         User.class.getName(), renderRequest);
	
		
		PermissionManager pm = new PermissionManager(serviceContext);
	
		try {
			RoleLocalServiceUtil.getRole(u.getCompanyId(), L2G_COORDINATOR);//if role don't exist, go to catch block and create the role coordinator 
		} catch (PortalException e) {
			// delete l2g coordinators from custom table
			/**List<Coordinator> cL = CoordinatorLocalServiceUtil.getCoordinators(com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			for (int i = 0; i < cL.size(); i++)
				CoordinatorLocalServiceUtil.deleteCoordinator(cL.get(i));*/
			
			Role role = createRole(L2G_COORDINATOR, u);
			
			//add default Permissions
			setL2GCoordinatorPermissions(role, pm);
		}
		try {
			RoleLocalServiceUtil.getRole(u.getCompanyId(), L2G_PRODUCER);
		} catch (PortalException e) {
			// delete l2g producer from custom table
			/**List<Producer> cL = ProducerLocalServiceUtil.getProducers(com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
			for (int i = 0; i < cL.size(); i++)
				ProducerLocalServiceUtil.deleteProducer(cL.get(i)); */
			Role role = createRole(L2G_PRODUCER, u);
			//add default Permissions
			setL2GProducerPermissions(role, pm);

		}
		try {
			RoleLocalServiceUtil.getRole(u.getCompanyId(), L2G_STUDENT);
		} catch (PortalException e) {
			Role role = createRole(L2G_STUDENT, u);
			//add default Permissions
			setL2GStudentPermissions(role, pm);				
		}
		try {
			RoleLocalServiceUtil.getRole(u.getCompanyId(), L2G_ADMIN);
		} catch (PortalException e) {
			Role role = createRole(L2G_ADMIN, u);
			//add default Permissions
			setL2GAdminPermissions(role, pm);
			
			//Remove defaults for non L2G Users (https://github.com/liferay/liferay-portal/blob/master/portal-impl/src/resource-actions/sites.xml)
			setL2GDefaultRolesPermissions(pm);		
		}

	}
	
	
	private Role createRole(String roleName, User user) throws SystemException{
		long roleId = CounterLocalServiceUtil.increment();
		long classNameId = ClassNameLocalServiceUtil.getClassNameId("com.liferay.portal.model.Role");
		Date date = new Date();
		Role role = RoleLocalServiceUtil.createRole(roleId);
		role.setRoleId(roleId);
		role.setClassNameId(classNameId);
		role.setCompanyId(user.getCompanyId());
		role.setClassPK(roleId);
		role.setName(roleName);
		role.setTitle(roleName);
		role.setDescription(roleName);
		role.setType(1);
		role.setUserName(user.getFullName());
		role.setUserId(user.getUserId());
		role.setCreateDate(date);
		role.setModifiedDate(date);
		return RoleLocalServiceUtil.addRole(role);//save role to database	
	}


	/**Permission Defaults on L2Go Admin Role creation
	 * 
	 * Remark: Permission in Liferay are granted hierarchically Company > Group > Entity and cant't be revoked 
	 * on a lower scope
	 * 
	 * Individiual Permissions only work when addResource is performed for each entity instance (Migration Portlet does not 
	 * correctly add Resources for old entities yet)
	 * 
	 * @param role - the Admin Role
	 * @param u
	 * @throws SystemException
	 */
	public void setL2GAdminPermissions(Role role, PermissionManager pm) throws SystemException{
		try {
						
			//setPageViewPermissionByFriendlyUrl(u.getCompanyId(),layoutGroupId, role,"/user-management");
			pm.setL2GLayoutViewPermission(role.getName());
			
            //Portlet Permissions (User Managemnt)
			pm.setL2GPortletPermissions(role.getName(), ActionKeys.VIEW);
			
			//User Model: Add Permission
			pm.setL2GEntityPermissions(role.getName(),User.class.getName(), new String[] {"ADD_L2GOADMIN","ADD_L2GOCOORDINATOR","ADD_L2GOPRODUCER","ADD_L2GOSTUDENT"});
				
		
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	/**Permission Defaults on L2Go Coordinator Role creation
	 * 
	 * Remark: Permission in Liferay are granted hierarchically Company > Group > Entity and cant't be revoked 
	 * on a lower scope
	 * 
	 * Individiual Permissions only work when addResource is performed for each entity instance (Migration Portlet does not 
	 * correctly add Resources for old entities yet)
	 * 
	 * @param role - the Admin Role
	 * @param u
	 * @throws SystemException
	 */
	public void setL2GCoordinatorPermissions(Role role, PermissionManager pm) throws SystemException{
		try {
			
			
			//setPageViewPermissionByFriendlyUrl(u.getCompanyId(),layoutGroupId, role,"/user-management");
			pm.setL2GLayoutViewPermission(role.getName());
			
            //Portlet Permissions (User Management)
			pm.setL2GPortletPermissions(role.getName(), ActionKeys.VIEW);
			
			//User Model: Add Permission
			pm.setL2GEntityPermissions(role.getName(),User.class.getName(), new String[] {"ADD_L2GOPRODUCER","ADD_L2GOSTUDENT"});
		
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**Permission Defaults on L2Go Producer Role creation
	 * 
	 * Remark: Permission in Liferay are granted hierarchically Company > Group > Entity and cant't be revoked 
	 * on a lower scope
	 * 
	 * Individiual Permissions only work when addResource is performed for each entity instance (Migration Portlet does not 
	 * correctly add Resources for old entities yet)
	 * 
	 * @param role - the Admin Role
	 * @param u
	 * @throws SystemException
	 */
	public void setL2GProducerPermissions(Role role, PermissionManager pm) throws SystemException{
	
		
	}
	
	/**Permission Defaults on L2Go Coordinator Role creation
	 * 
	 * Remark: Permission in Liferay are granted hierarchically Company > Group > Entity and cant't be revoked 
	 * on a lower scope
	 * 
	 * Individiual Permissions only work when addResource is performed for each entity instance (Migration Portlet does not 
	 * correctly add Resources for old entities yet)
	 * 
	 * @param role - the Admin Role
	 * @param u
	 * @throws SystemException
	 */
	public void setL2GStudentPermissions(Role role, PermissionManager pm) throws SystemException{

	}
	
	
	
	/**Handle Permissions for default roles to restrict public pages for non LG_ Roles
	 * 
	 * All layout permissions are defined on individual scope for instances of layouts (identified by plid)
	 * 
	 * By default Liferay adds permissions for pages (layouts) on individual scope
	 * @param role
	 * @param u
	 * @param page
	 * @throws SystemException
	 * @throws PortalException
	 */
	public void setL2GDefaultRolesPermissions(PermissionManager pm) throws SystemException, PortalException{
		
		//Remove defaults for non L2G Users (https://github.com/liferay/liferay-portal/blob/master/portal-impl/src/resource-actions/sites.xml)
		try {
			pm.removeL2GLayoutPermissions(RoleConstants.GUEST, new String[] {ActionKeys.VIEW, ActionKeys.ADD_DISCUSSION});
			} catch (PortalException e) {
			RoleLocalServiceUtil.checkSystemRoles();
			//e.printStackTrace();
		}
		try {
			
			pm.removeL2GLayoutPermissions(RoleConstants.SITE_MEMBER, new String[] {ActionKeys.VIEW, ActionKeys.ADD_DISCUSSION, ActionKeys.CUSTOMIZE});
		} catch (PortalException e) {
			RoleLocalServiceUtil.checkSystemRoles();
			//e.printStackTrace();
		}		
        //Permission of this role should be removed iff L2G Layout Admin should be seperated from OmniAdmin Role
		try {
			pm.removeL2GLayoutPermissions(RoleConstants.GUEST, new String[] {ActionKeys.VIEW, ActionKeys.ADD_DISCUSSION});	

		} catch (PortalException e) {
			RoleLocalServiceUtil.checkSystemRoles();
			//e.printStackTrace();
		}
		
	}
	
	/**
	 * Send mail notification to coordinator and admins, for new or edited producer right
	 * @param locale language locale
	 * @param user the user which producer rights changed
	 */
	private void sendNotifyMailNewProducer(Locale locale, User user) {
		// notify coordinators and admins via mail that a user was edited
		EmailManager em = new EmailManager();

		// get coordinator of producer/ user
		Producer producer = new ProducerImpl();
		Institution institution = new InstitutionImpl();
		try {
			producer = ProducerLocalServiceUtil.getProducer(user.getUserId());
			institution = InstitutionLocalServiceUtil.getInstitution(producer.getInstitutionId());
		} catch(Exception e) {
			// producer or linked institution does not exist, something went wrong
			return;
		}
		
		String SUBJECT = LanguageUtil.format(getPortletConfig(), locale, "mail-new-producer-subject",new String[]{user.getFullName()});
		String BODY = LanguageUtil.format(getPortletConfig(), locale, "mail-new-producer-body",new String[]{institution.getName(),user.getFullName()});

		Coordinator coordinator = new CoordinatorImpl();
		boolean coordExists = false;
		try{
			coordinator = CoordinatorLocalServiceUtil.getByInstitution(producer.getInstitutionId());
			if(coordinator.getCoordinatorId()>0)coordExists = true;
			if(coordExists){
				String COORDEMAILADDRESS = coordinator.getEmailAddress();
				// Send mail to Coordinator
				em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), COORDEMAILADDRESS, SUBJECT, BODY);
			}
		}catch(Exception e){}
		// Send mail to L2Go
		em.sendEmail(PropsUtil.get("lecture2go.response.email.address"), PropsUtil.get("lecture2go.response.email.address"), SUBJECT, BODY);
	}
	
}
