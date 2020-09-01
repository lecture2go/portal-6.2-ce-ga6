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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.uhh.l2g.plugins.StatisticDateException;
import de.uhh.l2g.plugins.StatisticValueException;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Statistic;
import de.uhh.l2g.plugins.model.impl.StatisticImpl;
import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.StatisticLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.StatisticLocalServiceBaseImpl;
import de.uhh.l2g.plugins.service.persistence.StatisticFinderUtil;
import de.uhh.l2g.plugins.util.RepositoryManager;

/**
 * The implementation of the daily satitistics record table
 * 
 * Combining records by time intervals is done in views, modeled by its own Entity like below
 * https://www.liferay.com/de/community/wiki/-/wiki/Main/Working+with+Database+Views+in+Liferay
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.StatisticLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.StatisticLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.StatisticLocalServiceUtil
 */
@SuppressWarnings("unused")
public class StatisticLocalServiceImpl extends StatisticLocalServiceBaseImpl {

	 protected static Log LOG = LogFactoryUtil.getLog(Statistic.class.getName());	
	/*
	 * NOTE FOR DEVELOPERS:
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.StatisticLocalServiceUtil} to access the institution_ host local service.
	 */

	public Statistic addStatistic(Statistic object){
		Long id;
		try {
			id = counterLocalService.increment(Statistic.class.getName());
			object.setPrimaryKey(id);
			super.addStatistic(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
		
	public JSONObject getAllStatistics() throws SystemException {
			return StatisticFinderUtil.findAllStatistics();
	}
		
	public List<Statistic> getByCompanyIdandGroupId(long companyId, long groupId) throws SystemException, PortalException {
		List<Statistic> institution_host = statisticPersistence.findByC_G(companyId, groupId);
		return institution_host; 
	}

	public Statistic getByStatisticId(long statisticId) throws SystemException, PortalException {
			Statistic statistic = statisticPersistence.findByStatisticId(statisticId);
			return statistic;
	}
	
	public Statistic getByStatisticId(long companyId, long groupId, long statisticId) throws SystemException, PortalException {
		Statistic statistic = statisticPersistence.findByStatisticId(statisticId);
		
		return statistic;
	}

	/** Drop table via custom query
	 * https://web.liferay.com/de/community/wiki/-/wiki/Main/Working+with+Database+Views+in+Liferay
	 */
	public boolean removeVideoStatisticDefaultTable(){
		StatisticFinderUtil.removeVideoStatisticTable();
		return true;	
	}
	
	/**
	 * Add Database View with same Name
	 */
	public boolean addVideoStatisticView(){
		StatisticFinderUtil.createVideoStatisticView();
		return true;
	
	}

	protected void validate(long privateVideos, long publicVideos, Date date) throws PortalException {

		if (Validator.isNull(privateVideos) || Validator.isNull(publicVideos)) {
	       throw new StatisticValueException();
		 }

	     if (Validator.isNull(date)) {
	       throw new StatisticDateException();
	     }

	}
	
	public Statistic addDefaultEntry(ServiceContext serviceContext) throws SystemException, PortalException{

		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();
		long companyId = serviceContext.getCompanyId();
		
		User user = userPersistence.findByPrimaryKey(userId);
		
		Date now = new Date(0);

		validate(0, 0, now);

		long statisticId = counterLocalService.increment(Statistic.class.getName());

		Statistic statistic = statisticPersistence.create(statisticId);

		//Anonymous values for debug/consitency check: Stats should be added by System only
		statistic.setUserId(userId);
		statistic.setUserName(UserLocalServiceUtil.getUser(userId).getLogin());
		statistic.setCreateDate(serviceContext.getCreateDate(now));
		statistic.setModifiedDate(serviceContext.getModifiedDate(now));
		
		
		statistic.setGroupId(groupId);
		statistic.setCompanyId(companyId);
		statistic.setPrivateVideos(0);
		statistic.setPublicVideos(0);

		statistic.setExpandoBridgeAttributes(serviceContext);
		
		statisticPersistence.update(statistic);

		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
			       Statistic.class.getName(), statisticId, false, true, true);


		return statistic;
		
	}

	public Statistic addEntry(int privateVideos, int publicVideos, ServiceContext serviceContext) throws SystemException, PortalException {

		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();
		long companyId = serviceContext.getCompanyId();
		
		User user = userPersistence.findByPrimaryKey(userId);
		
		Date now = new Date();

		validate(privateVideos, publicVideos, now);

		long statisticId = counterLocalService.increment(Statistic.class.getName());

		Statistic statistic = statisticPersistence.create(statisticId);

		//Anonymous values for debug/consitency check: Stats should be added by System only
		statistic.setUserId(userId);
		statistic.setUserName(UserLocalServiceUtil.getUser(userId).getLogin());
		statistic.setCreateDate(serviceContext.getCreateDate(now));
		statistic.setModifiedDate(serviceContext.getModifiedDate(now));
		
		
		statistic.setGroupId(groupId);
		statistic.setCompanyId(companyId);
		statistic.setPrivateVideos(privateVideos);
		statistic.setPublicVideos(publicVideos);

		statistic.setExpandoBridgeAttributes(serviceContext);
		
		statisticPersistence.update(statistic);

		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
			       Statistic.class.getName(), statisticId, false, true, true);


		return statistic;

	}

	public Statistic add(int privateVideos, int publicVideos) throws SystemException, PortalException {
		Date now = new Date();
		Statistic statistic = new StatisticImpl();

		statistic.setCreateDate(now);
		statistic.setModifiedDate(now);
		
		statistic.setPrivateVideos(privateVideos);
		statistic.setPublicVideos(publicVideos);
		
		StatisticLocalServiceUtil.addStatistic(statistic);
		return statistic;
	}

	public Statistic updateEntry(long statisticId, int privateVideos, int publicVideos, ServiceContext serviceContext) throws SystemException, PortalException {

		long companyId = serviceContext.getCompanyId();
		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();

		User user = userPersistence.findByPrimaryKey(userId);
		LOG.info("Input: " +privateVideos +" "+publicVideos);
		Date now = new Date();

		validate(privateVideos, publicVideos, now);


		Statistic statistic = getByStatisticId(companyId, groupId, statisticId);
		//System.out.println ("Link Contents: "+ statistic.toString());

		statistic.setGroupId(groupId);
		statistic.setPrivateVideos(privateVideos);
		statistic.setPublicVideos(publicVideos);
		
		statistic.setModifiedDate(serviceContext.getModifiedDate(now));

		statistic.setExpandoBridgeAttributes(serviceContext);

		statisticPersistence.update(statistic);

		resourceLocalService.addResources(user.getCompanyId(), groupId, userId,
			       Statistic.class.getName(), statisticId, false, true, true);


		return statistic;

	}


	   public Statistic deleteLinkById(long statisticId, ServiceContext serviceContext)
		        throws PortalException, SystemException {
		   		Statistic statistic = null;

		   		long groupId = serviceContext.getScopeGroupId();
		   		long userId = serviceContext.getUserId();

				statistic = deleteStatistic(statisticId);

				statistic.setExpandoBridgeAttributes(serviceContext);
				resourceLocalService.deleteResource(serviceContext.getCompanyId(),
				        		Statistic.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				        		statisticId);

		        return statistic;

		    }
	   

	   public long updateCounter() throws SystemException, PortalException {
	        //current counter
	        Counter counter = CounterLocalServiceUtil.getCounter(Statistic.class.getName());
	        LOG.debug(counter.getCurrentId());
  			//check Host Count (if 1 it is supposed to be default statistic)
	        int count = HostLocalServiceUtil.getHostsCount();
	        LOG.debug(count); 
	        long newHostId = 0; //Reset if table is empty
	        long statisticId = 0;   //the actual Id value
	        
	        if (count > 0){ //our db is filled... with something at least
	        	
				//Retrieve actual table data
				ClassLoader classLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");    		
				DynamicQuery query = DynamicQueryFactoryUtil.forClass(Statistic.class,classLoader).addOrder(OrderFactoryUtil.desc("statisticId"));
				query.setLimit(0,1);
				List<Statistic> statistics = StatisticLocalServiceUtil.dynamicQuery(query);
				if (statistics.size() > 0) statisticId = statistics.get(0).getStatisticId(); //current maximum Id
			
	        }

	        LOG.debug(statisticId);
			//Increment Counter if assynchrone with estimated value or data reseted
			if (counter.getCurrentId() <  statisticId || statisticId == 0){
				counter.setCurrentId(statisticId);
				CounterLocalServiceUtil.updateCounter(counter);
			}			
			return counter.getCurrentId();
	   	}
	   
	   

}
