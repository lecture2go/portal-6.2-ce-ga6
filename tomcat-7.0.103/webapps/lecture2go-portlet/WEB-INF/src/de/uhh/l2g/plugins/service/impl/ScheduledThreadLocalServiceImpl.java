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
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.ServiceContext;

import de.uhh.l2g.plugins.NoSuchScheduledThreadException;
import de.uhh.l2g.plugins.model.ScheduledThread;
import de.uhh.l2g.plugins.service.base.ScheduledThreadLocalServiceBaseImpl;

/**
 * DummyTable! This could be used to persist Thread Information in the Future 
 * 
 * The implementation of the scheduled thread local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.ScheduledThreadLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.ScheduledThreadLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.ScheduledThreadLocalServiceUtil
 */
public class ScheduledThreadLocalServiceImpl
	extends ScheduledThreadLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.ScheduledThreadLocalServiceUtil} to access the scheduled thread local service.
	 */

	private ScheduledThread getBySchedulerName(String schedulerClassName) throws SystemException {
		
		return scheduledThreadPersistence.fetchBySchedulerClassName(schedulerClassName);
	}

	public String getCronBySchedulerName(String schedulerClassName) throws SystemException {
	
			try {
				return GetterUtil.getString(scheduledThreadPersistence.findBySchedulerClassName(schedulerClassName).getCronText());
			} catch (NoSuchScheduledThreadException e) {
				return "";
			}
		
	}
	
	public ScheduledThread addScheduledThread(String schedulerClassName, String cronText, ServiceContext serviceContext) throws SystemException, PortalException {

		long scheduleId = counterLocalService.increment(ScheduledThread.class.getName());

		ScheduledThread schedule = scheduledThreadPersistence.create(scheduleId);

		//Generate Anonymous values for debug/consistency check: should be added by System only
		//schedule.setUserId(userId);
		//schedule.setUserName(UserLocalServiceUtil.getUser(userId).getLogin());
		//schedule.setCreateDate(serviceContext.getCreateDate(now));
		//schedule.setModifiedDate(serviceContext.getModifiedDate(now));
		
		
		//schedule.setGroupId(groupId);
		//schedule.setCompanyId(companyId);
		
		schedule.setSchedulerClassName(schedulerClassName);
		schedule.setCronText(cronText);

		schedule.setExpandoBridgeAttributes(serviceContext);
		
		scheduledThreadPersistence.update(schedule);

		//resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
		//ScheduledThread.class.getName(), scheduleId, false, true, true);


		return schedule;

	}
	
	public ScheduledThread updateScheduledThread(String schedulerClassName, String cronText, ServiceContext serviceContext) throws SystemException, PortalException {

		//LOG.info("Update: " +schedulerClassName +" "+cronText);
		//Date now = new Date();

		//Both Must be filled
		//validate(schedulerClassName,cronText);


		ScheduledThread schedule = getBySchedulerName(schedulerClassName);
		//System.out.println ("Link Contents: "+ statistic.toString());

		
		schedule.setCronText(cronText);
	
		//schedule.setModifiedDate(serviceContext.getModifiedDate(now));

		schedule.setExpandoBridgeAttributes(serviceContext);

		scheduledThreadPersistence.update(schedule);

	//	resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
	//		       Statistic.class.getName(), statisticId, false, true, true);


		return schedule;

	}
}
