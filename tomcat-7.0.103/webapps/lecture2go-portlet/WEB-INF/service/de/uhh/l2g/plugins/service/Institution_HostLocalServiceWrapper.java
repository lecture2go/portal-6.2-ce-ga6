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

package de.uhh.l2g.plugins.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link Institution_HostLocalService}.
 *
 * @author Iavor Sturm
 * @see Institution_HostLocalService
 * @generated
 */
public class Institution_HostLocalServiceWrapper
	implements Institution_HostLocalService,
		ServiceWrapper<Institution_HostLocalService> {
	public Institution_HostLocalServiceWrapper(
		Institution_HostLocalService institution_HostLocalService) {
		_institution_HostLocalService = institution_HostLocalService;
	}

	/**
	* Adds the institution_ host to the database. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host addInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.addInstitution_Host(institution_Host);
	}

	/**
	* Creates a new institution_ host with the primary key. Does not add the institution_ host to the database.
	*
	* @param institutionHostId the primary key for the new institution_ host
	* @return the new institution_ host
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host createInstitution_Host(
		long institutionHostId) {
		return _institution_HostLocalService.createInstitution_Host(institutionHostId);
	}

	/**
	* Deletes the institution_ host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param institutionHostId the primary key of the institution_ host
	* @return the institution_ host that was removed
	* @throws PortalException if a institution_ host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host deleteInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.deleteInstitution_Host(institutionHostId);
	}

	/**
	* Deletes the institution_ host from the database. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host deleteInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.deleteInstitution_Host(institution_Host);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _institution_HostLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.Institution_HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.Institution_HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host fetchInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.fetchInstitution_Host(institutionHostId);
	}

	/**
	* Returns the institution_ host with the primary key.
	*
	* @param institutionHostId the primary key of the institution_ host
	* @return the institution_ host
	* @throws PortalException if a institution_ host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host getInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getInstitution_Host(institutionHostId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the institution_ hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.Institution_HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of institution_ hosts
	* @param end the upper bound of the range of institution_ hosts (not inclusive)
	* @return the range of institution_ hosts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getInstitution_Hosts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getInstitution_Hosts(start, end);
	}

	/**
	* Returns the number of institution_ hosts.
	*
	* @return the number of institution_ hosts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getInstitution_HostsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getInstitution_HostsCount();
	}

	/**
	* Updates the institution_ host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Institution_Host updateInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.updateInstitution_Host(institution_Host);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _institution_HostLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_institution_HostLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _institution_HostLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host getByInstitutionId(long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getByInstitutionId(institutionId);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host getByInstitution(long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getByInstitution(institutionId);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host getLinkByInstitutionId(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getLinkByInstitutionId(institutionId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Institution> getByHostId(
		long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getByHostId(hostId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getListByHostId(
		long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getListByHostId(hostId);
	}

	@Override
	public int getByHostIdCount(long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getByHostIdCount(hostId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getListByInstitutionId(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.getListByInstitutionId(institutionId);
	}

	/**
	* Assume one Institution has at most one Host that remains constant
	*/
	@Override
	public long getDefaultInstitutionHostId() {
		return _institution_HostLocalService.getDefaultInstitutionHostId();
	}

	/**
	* if not added with Institution
	*
	* @throws SystemException
	* @throws PortalException
	*/
	@Override
	public long addDefaultInstitutionHost(long defaultInstitutionId,
		long defaultHostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.addDefaultInstitutionHost(defaultInstitutionId,
			defaultHostId, serviceContext);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host addEntry(
		long institutionId, long hostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.addEntry(institutionId, hostId,
			serviceContext);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host updateEntry(
		long institutionId, long hostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.updateEntry(institutionId, hostId,
			serviceContext);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host deleteLinkById(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.deleteLinkById(institutionHostId);
	}

	@Override
	public de.uhh.l2g.plugins.model.Institution_Host deleteLinkByInstitution(
		de.uhh.l2g.plugins.model.Institution institution, long groupId,
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _institution_HostLocalService.deleteLinkByInstitution(institution,
			groupId, companyId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public Institution_HostLocalService getWrappedInstitution_HostLocalService() {
		return _institution_HostLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedInstitution_HostLocalService(
		Institution_HostLocalService institution_HostLocalService) {
		_institution_HostLocalService = institution_HostLocalService;
	}

	@Override
	public Institution_HostLocalService getWrappedService() {
		return _institution_HostLocalService;
	}

	@Override
	public void setWrappedService(
		Institution_HostLocalService institution_HostLocalService) {
		_institution_HostLocalService = institution_HostLocalService;
	}

	private Institution_HostLocalService _institution_HostLocalService;
}