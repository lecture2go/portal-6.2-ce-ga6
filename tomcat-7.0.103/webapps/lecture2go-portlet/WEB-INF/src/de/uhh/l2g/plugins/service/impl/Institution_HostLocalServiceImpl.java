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
import java.util.List;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
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

import de.uhh.l2g.plugins.Institution_HostHostException;
import de.uhh.l2g.plugins.Institution_HostInstitutionException;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Institution_Host;
import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.Institution_HostLocalServiceBaseImpl;

/**
 * The implementation of the institution_ host local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.uhh.l2g.plugins.service.Institution_HostLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.Institution_HostLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil
 */
public class Institution_HostLocalServiceImpl extends Institution_HostLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil} to access
	 * the institution_ host local service.
	 */

	protected static Log LOG = LogFactoryUtil.getLog(Institution_Host.class.getName());

	public Host getByInstitutionId(long institutionId) throws SystemException, PortalException {
		// Assumes there is at most one Host per Institution with uniqueId
		long hId = institution_HostPersistence.findByinstitutionId(institutionId).get(0).getHostId();

		Host h = HostLocalServiceUtil.getByHostId(hId);
		return h;
	}

	public Host getByInstitution(long institutionId) throws SystemException, PortalException {
		long hid = institution_HostPersistence.findByinstitutionId(institutionId).iterator().next().getHostId();
		Host h = HostLocalServiceUtil.getByHostId(hid);
		return h;
	}

	public Institution_Host getLinkByInstitutionId(long institutionId) throws SystemException, PortalException {
		Institution_Host ih = institution_HostPersistence.findByinstitutionId(institutionId).iterator().next();
		return ih;
	}

	public List<Institution> getByHostId(long hostId) throws SystemException, PortalException {
		List<Institution_Host> linkList = institution_HostPersistence.findByhostId(hostId);
		List<Institution> institutions = new ArrayList<Institution>();

		for (Institution_Host link : linkList) {
			long iId = link.getInstitutionId();
			Institution i = InstitutionLocalServiceUtil.getById(iId);
			institutions.add(i);
		}
		return institutions;
	}

	public List<Institution_Host> getListByHostId(long hostId) throws SystemException, PortalException {
		List<Institution_Host> institution_host = institution_HostPersistence.findByhostId(hostId);
		return institution_host;
	}

	public int getByHostIdCount(long hostId) throws SystemException, PortalException {
		int institution_host = institution_HostPersistence.countByhostId(hostId);
		return institution_host;
	}

	public List<Institution_Host> getListByInstitutionId(long institutionId) throws SystemException, PortalException {
		List<Institution_Host> institution_host = institution_HostPersistence.findByinstitutionId(institutionId);
		return institution_host;
	}

	/**
	 * TODO: Maximum one host per institution
	 *
	 * */
	protected void validate(long institutionId, long hostId) throws PortalException {

		if (Validator.isNull(institutionId)) {
			throw new Institution_HostInstitutionException();
		}

		if (Validator.isNull(hostId)) {
			throw new Institution_HostHostException();
		}

	}

	/** Assume one Institution has at most one Host that remains constant */
	public long getDefaultInstitutionHostId() {
		List<Institution_Host> defaults = null;

		Institution defaultInstitution;
		try {
			defaultInstitution = institutionPersistence.findByParent(0).iterator().next();
			defaults = institution_HostPersistence.findByinstitutionId(defaultInstitution.getInstitutionId());
		} catch (SystemException e) {
		}
		//
		if (defaults.size() > 0)
			return defaults.get(0).getInstitutionHostId();
		else
			return 0;
	}

	/**
	 * if not added with Institution
	 * 
	 * @throws SystemException
	 * @throws PortalException
	 */
	public long addDefaultInstitutionHost(long defaultInstitutionId, long defaultHostId, ServiceContext serviceContext) throws PortalException, SystemException {
		Institution_Host defaultInstitution_Host = Institution_HostLocalServiceUtil.addEntry(defaultInstitutionId, defaultHostId, serviceContext);
		return defaultInstitution_Host.getInstitutionHostId();
	}

	public Institution_Host addEntry(long institutionId, long hostId, ServiceContext serviceContext) throws SystemException, PortalException {
		long institution_HostId = counterLocalService.increment(Institution_Host.class.getName());
		Institution_Host institution_Host = institution_HostPersistence.create(institution_HostId);
		institution_Host.setInstitutionId(institutionId);
		institution_Host.setHostId(hostId);
		institution_Host.setExpandoBridgeAttributes(serviceContext);
		super.addInstitution_Host(institution_Host);
		//
		return institution_Host;
	}

	public Institution_Host updateEntry(long institutionId, long hostId, ServiceContext serviceContext) throws SystemException, PortalException {
		validate(institutionId, hostId);
		Institution_Host institution_Host = getLinkByInstitutionId(institutionId);
		institution_HostPersistence.update(institution_Host);

		return institution_Host;
	}

	public Institution_Host deleteLinkById(long institutionHostId) throws PortalException, SystemException {
		Institution_Host institution_Host = null;
		institution_Host = deleteInstitution_Host(institutionHostId);
		return institution_Host;
	}

	public Institution_Host deleteLinkByInstitution(Institution institution, long groupId, long companyId) throws PortalException, SystemException {
		long iId = institution.getInstitutionId();
		Institution_Host institution_Host = getLinkByInstitutionId(iId);
		long ihId = institution_Host.getInstitutionHostId();
		institution_Host = deleteInstitution_Host(ihId);
		return institution_Host;
	}

}
