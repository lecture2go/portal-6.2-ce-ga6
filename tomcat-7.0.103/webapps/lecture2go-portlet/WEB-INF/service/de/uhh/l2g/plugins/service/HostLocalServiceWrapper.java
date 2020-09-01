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
 * Provides a wrapper for {@link HostLocalService}.
 *
 * @author Iavor Sturm
 * @see HostLocalService
 * @generated
 */
public class HostLocalServiceWrapper implements HostLocalService,
	ServiceWrapper<HostLocalService> {
	public HostLocalServiceWrapper(HostLocalService hostLocalService) {
		_hostLocalService = hostLocalService;
	}

	/**
	* Adds the host to the database. Also notifies the appropriate model listeners.
	*
	* @param host the host
	* @return the host that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host addHost(
		de.uhh.l2g.plugins.model.Host host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.addHost(host);
	}

	/**
	* Creates a new host with the primary key. Does not add the host to the database.
	*
	* @param hostId the primary key for the new host
	* @return the new host
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host createHost(long hostId) {
		return _hostLocalService.createHost(hostId);
	}

	/**
	* Deletes the host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param hostId the primary key of the host
	* @return the host that was removed
	* @throws PortalException if a host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host deleteHost(long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.deleteHost(hostId);
	}

	/**
	* Deletes the host from the database. Also notifies the appropriate model listeners.
	*
	* @param host the host
	* @return the host that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host deleteHost(
		de.uhh.l2g.plugins.model.Host host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.deleteHost(host);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _hostLocalService.dynamicQuery();
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
		return _hostLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _hostLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _hostLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _hostLocalService.dynamicQueryCount(dynamicQuery);
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
		return _hostLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host fetchHost(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.fetchHost(hostId);
	}

	/**
	* Returns the host with the primary key.
	*
	* @param hostId the primary key of the host
	* @return the host
	* @throws PortalException if a host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host getHost(long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getHost(hostId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of hosts
	* @param end the upper bound of the range of hosts (not inclusive)
	* @return the range of hosts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Host> getHosts(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getHosts(start, end);
	}

	/**
	* Returns the number of hosts.
	*
	* @return the number of hosts
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getHostsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getHostsCount();
	}

	/**
	* Updates the host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param host the host
	* @return the host that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host updateHost(
		de.uhh.l2g.plugins.model.Host host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.updateHost(host);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _hostLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_hostLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _hostLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host getByInstitution(long institutionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getByInstitution(institutionId);
	}

	@Override
	public int countAll() {
		return _hostLocalService.countAll();
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Host> getAll() {
		return _hostLocalService.getAll();
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Host> getAll(int start,
		int end) {
		return _hostLocalService.getAll(start, end);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host getByHostId(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getByHostId(hostId);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host getDefaultHost() {
		return _hostLocalService.getDefaultHost();
	}

	/**
	* Host is locked if it is linked to an institution
	*/
	@Override
	public int getLockingElements(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.getLockingElements(hostId);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host addHost(java.lang.String name,
		java.lang.String prefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.addHost(name, prefix);
	}

	@Override
	public de.uhh.l2g.plugins.model.Host updateHost(long hostId,
		java.lang.String name, java.lang.String prefix)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.updateHost(hostId, name, prefix);
	}

	/**
	* Removes database record of Host
	*
	* This will not remove Folder on Filesystem, Folder will not be reused
	*/
	@Override
	public de.uhh.l2g.plugins.model.Host deleteHost(long hostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _hostLocalService.deleteHost(hostId, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public HostLocalService getWrappedHostLocalService() {
		return _hostLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedHostLocalService(HostLocalService hostLocalService) {
		_hostLocalService = hostLocalService;
	}

	@Override
	public HostLocalService getWrappedService() {
		return _hostLocalService;
	}

	@Override
	public void setWrappedService(HostLocalService hostLocalService) {
		_hostLocalService = hostLocalService;
	}

	private HostLocalService _hostLocalService;
}