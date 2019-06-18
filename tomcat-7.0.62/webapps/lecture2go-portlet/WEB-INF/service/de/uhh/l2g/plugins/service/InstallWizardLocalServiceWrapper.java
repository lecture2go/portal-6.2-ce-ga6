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
 * Provides a wrapper for {@link InstallWizardLocalService}.
 *
 * @author Iavor Sturm
 * @see InstallWizardLocalService
 * @generated
 */
public class InstallWizardLocalServiceWrapper
	implements InstallWizardLocalService,
		ServiceWrapper<InstallWizardLocalService> {
	public InstallWizardLocalServiceWrapper(
		InstallWizardLocalService installWizardLocalService) {
		_installWizardLocalService = installWizardLocalService;
	}

	/**
	* Adds the install wizard to the database. Also notifies the appropriate model listeners.
	*
	* @param installWizard the install wizard
	* @return the install wizard that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard addInstallWizard(
		de.uhh.l2g.plugins.model.InstallWizard installWizard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.addInstallWizard(installWizard);
	}

	/**
	* Creates a new install wizard with the primary key. Does not add the install wizard to the database.
	*
	* @param installWizardId the primary key for the new install wizard
	* @return the new install wizard
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard createInstallWizard(
		long installWizardId) {
		return _installWizardLocalService.createInstallWizard(installWizardId);
	}

	/**
	* Deletes the install wizard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param installWizardId the primary key of the install wizard
	* @return the install wizard that was removed
	* @throws PortalException if a install wizard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard deleteInstallWizard(
		long installWizardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.deleteInstallWizard(installWizardId);
	}

	/**
	* Deletes the install wizard from the database. Also notifies the appropriate model listeners.
	*
	* @param installWizard the install wizard
	* @return the install wizard that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard deleteInstallWizard(
		de.uhh.l2g.plugins.model.InstallWizard installWizard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.deleteInstallWizard(installWizard);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _installWizardLocalService.dynamicQuery();
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
		return _installWizardLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstallWizardModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _installWizardLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstallWizardModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _installWizardLocalService.dynamicQuery(dynamicQuery, start,
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
		return _installWizardLocalService.dynamicQueryCount(dynamicQuery);
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
		return _installWizardLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public de.uhh.l2g.plugins.model.InstallWizard fetchInstallWizard(
		long installWizardId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.fetchInstallWizard(installWizardId);
	}

	/**
	* Returns the install wizard with the primary key.
	*
	* @param installWizardId the primary key of the install wizard
	* @return the install wizard
	* @throws PortalException if a install wizard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard getInstallWizard(
		long installWizardId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.getInstallWizard(installWizardId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the install wizards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstallWizardModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of install wizards
	* @param end the upper bound of the range of install wizards (not inclusive)
	* @return the range of install wizards
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.uhh.l2g.plugins.model.InstallWizard> getInstallWizards(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.getInstallWizards(start, end);
	}

	/**
	* Returns the number of install wizards.
	*
	* @return the number of install wizards
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getInstallWizardsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.getInstallWizardsCount();
	}

	/**
	* Updates the install wizard in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param installWizard the install wizard
	* @return the install wizard that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.InstallWizard updateInstallWizard(
		de.uhh.l2g.plugins.model.InstallWizard installWizard)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _installWizardLocalService.updateInstallWizard(installWizard);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _installWizardLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_installWizardLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _installWizardLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	@Override
	public void activate(de.uhh.l2g.plugins.model.InstallWizard wiz)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_installWizardLocalService.activate(wiz);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public InstallWizardLocalService getWrappedInstallWizardLocalService() {
		return _installWizardLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedInstallWizardLocalService(
		InstallWizardLocalService installWizardLocalService) {
		_installWizardLocalService = installWizardLocalService;
	}

	@Override
	public InstallWizardLocalService getWrappedService() {
		return _installWizardLocalService;
	}

	@Override
	public void setWrappedService(
		InstallWizardLocalService installWizardLocalService) {
		_installWizardLocalService = installWizardLocalService;
	}

	private InstallWizardLocalService _installWizardLocalService;
}