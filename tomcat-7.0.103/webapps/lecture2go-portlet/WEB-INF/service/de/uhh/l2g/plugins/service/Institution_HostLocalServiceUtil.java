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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Institution_Host. This utility wraps
 * {@link de.uhh.l2g.plugins.service.impl.Institution_HostLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Iavor Sturm
 * @see Institution_HostLocalService
 * @see de.uhh.l2g.plugins.service.base.Institution_HostLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.impl.Institution_HostLocalServiceImpl
 * @generated
 */
public class Institution_HostLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.uhh.l2g.plugins.service.impl.Institution_HostLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the institution_ host to the database. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host addInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addInstitution_Host(institution_Host);
	}

	/**
	* Creates a new institution_ host with the primary key. Does not add the institution_ host to the database.
	*
	* @param institutionHostId the primary key for the new institution_ host
	* @return the new institution_ host
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host createInstitution_Host(
		long institutionHostId) {
		return getService().createInstitution_Host(institutionHostId);
	}

	/**
	* Deletes the institution_ host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param institutionHostId the primary key of the institution_ host
	* @return the institution_ host that was removed
	* @throws PortalException if a institution_ host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host deleteInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteInstitution_Host(institutionHostId);
	}

	/**
	* Deletes the institution_ host from the database. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host deleteInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteInstitution_Host(institution_Host);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host fetchInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchInstitution_Host(institutionHostId);
	}

	/**
	* Returns the institution_ host with the primary key.
	*
	* @param institutionHostId the primary key of the institution_ host
	* @return the institution_ host
	* @throws PortalException if a institution_ host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host getInstitution_Host(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitution_Host(institutionHostId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getInstitution_Hosts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitution_Hosts(start, end);
	}

	/**
	* Returns the number of institution_ hosts.
	*
	* @return the number of institution_ hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int getInstitution_HostsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitution_HostsCount();
	}

	/**
	* Updates the institution_ host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param institution_Host the institution_ host
	* @return the institution_ host that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution_Host updateInstitution_Host(
		de.uhh.l2g.plugins.model.Institution_Host institution_Host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateInstitution_Host(institution_Host);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static de.uhh.l2g.plugins.model.Host getByInstitutionId(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByInstitutionId(institutionId);
	}

	public static de.uhh.l2g.plugins.model.Host getByInstitution(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByInstitution(institutionId);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host getLinkByInstitutionId(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLinkByInstitutionId(institutionId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getByHostId(
		long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByHostId(hostId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getListByHostId(
		long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getListByHostId(hostId);
	}

	public static int getByHostIdCount(long hostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getByHostIdCount(hostId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution_Host> getListByInstitutionId(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getListByInstitutionId(institutionId);
	}

	/**
	* Assume one Institution has at most one Host that remains constant
	*/
	public static long getDefaultInstitutionHostId() {
		return getService().getDefaultInstitutionHostId();
	}

	/**
	* if not added with Institution
	*
	* @throws SystemException
	* @throws PortalException
	*/
	public static long addDefaultInstitutionHost(long defaultInstitutionId,
		long defaultHostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDefaultInstitutionHost(defaultInstitutionId,
			defaultHostId, serviceContext);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host addEntry(
		long institutionId, long hostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addEntry(institutionId, hostId, serviceContext);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host updateEntry(
		long institutionId, long hostId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEntry(institutionId, hostId, serviceContext);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host deleteLinkById(
		long institutionHostId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLinkById(institutionHostId);
	}

	public static de.uhh.l2g.plugins.model.Institution_Host deleteLinkByInstitution(
		de.uhh.l2g.plugins.model.Institution institution, long groupId,
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteLinkByInstitution(institution, groupId, companyId);
	}

	public static void clearService() {
		_service = null;
	}

	public static Institution_HostLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					Institution_HostLocalService.class.getName());

			if (invokableLocalService instanceof Institution_HostLocalService) {
				_service = (Institution_HostLocalService)invokableLocalService;
			}
			else {
				_service = new Institution_HostLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(Institution_HostLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(Institution_HostLocalService service) {
	}

	private static Institution_HostLocalService _service;
}