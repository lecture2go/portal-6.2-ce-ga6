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
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.uhh.l2g.plugins.service.impl;

import java.util.Iterator;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.uhh.l2g.plugins.NoSuchCoordinatorException;
import de.uhh.l2g.plugins.model.Category;
import de.uhh.l2g.plugins.model.Coordinator;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.impl.CoordinatorImpl;
import de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.CoordinatorLocalServiceBaseImpl;

/**
 * The implementation of the coordinator local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.CoordinatorLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.CoordinatorLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil
 */
public class CoordinatorLocalServiceImpl extends CoordinatorLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil} to access the coordinator local service.
	 */

	protected static Log LOG = LogFactoryUtil.getLog(Coordinator.class.getName());
	
	public Coordinator addCoordinator(Coordinator object){
		Long id;
		try {
			id = counterLocalService.increment(Coordinator.class.getName());
			object.setPrimaryKey(id);
			super.addCoordinator(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	private List<Coordinator> fillProps(List<Coordinator> cl) throws SystemException{
		Iterator<Coordinator> it = cl.iterator();
		while (it.hasNext()){
			Coordinator c = it.next();
			User u;
			try {
				u = UserLocalServiceUtil.getUser(c.getCoordinatorId());
				c.setLastName(u.getLastName());
				c.setFirstName(u.getFirstName());
				c.setEmailAddress(u.getEmailAddress());
				c.setLastLoginDate(u.getLastLoginDate());
			} catch (PortalException e) {
				//e.printStackTrace();
			}
		}
		return cl;
	}
	
	public List<Coordinator> getAllCoordinators(int begin, int end) throws SystemException{
		List<Coordinator> coords = CoordinatorLocalServiceUtil.getCoordinators(begin, end);
		return fillProps(coords);
	}
	
	public Coordinator getById(long coordinatorId) throws SystemException {
		Coordinator c = new CoordinatorImpl();
		try {
			c = coordinatorPersistence.fetchByPrimaryKey(coordinatorId);
			User u = UserLocalServiceUtil.getUser(c.getCoordinatorId());
			c.setEmailAddress(u.getEmailAddress());
			c.setFirstName(u.getFirstName());
			c.setLastName(u.getLastName());
			c.setLastLoginDate(u.getLastLoginDate());
		} catch (NoSuchCoordinatorException e) {
			//e.printStackTrace();
		} catch (PortalException e) {
			//e.printStackTrace();
		} 
		return c;
	}
	
	public Institution getInstitutionByCoordinator(long coordinatorId) throws SystemException {
		return institutionPersistence.fetchByPrimaryKey(getById(coordinatorId).getInstitutionId());
	}
	
	public Coordinator getByInstitution(long institutionId) throws SystemException {
		Coordinator c = new CoordinatorImpl();
		try {
			c = coordinatorPersistence.findByInstitution(institutionId);
			User u = UserLocalServiceUtil.getUser(c.getCoordinatorId());
			c.setEmailAddress(u.getEmailAddress());
			c.setFirstName(u.getFirstName());
			c.setLastName(u.getLastName());
			c.setLastLoginDate(u.getLastLoginDate());
		} catch (NoSuchCoordinatorException e) {
			//e.printStackTrace();
		} catch (PortalException e) {
			//e.printStackTrace();
		} 
		return c;
	}
	
}
