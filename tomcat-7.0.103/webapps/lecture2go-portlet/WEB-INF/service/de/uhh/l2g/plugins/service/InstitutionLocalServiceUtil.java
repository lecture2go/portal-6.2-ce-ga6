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
 * Provides the local service utility for Institution. This utility wraps
 * {@link de.uhh.l2g.plugins.service.impl.InstitutionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Iavor Sturm
 * @see InstitutionLocalService
 * @see de.uhh.l2g.plugins.service.base.InstitutionLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.impl.InstitutionLocalServiceImpl
 * @generated
 */
public class InstitutionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.uhh.l2g.plugins.service.impl.InstitutionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the institution to the database. Also notifies the appropriate model listeners.
	*
	* @param institution the institution
	* @return the institution that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution addInstitution(
		de.uhh.l2g.plugins.model.Institution institution)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addInstitution(institution);
	}

	/**
	* Creates a new institution with the primary key. Does not add the institution to the database.
	*
	* @param institutionId the primary key for the new institution
	* @return the new institution
	*/
	public static de.uhh.l2g.plugins.model.Institution createInstitution(
		long institutionId) {
		return getService().createInstitution(institutionId);
	}

	/**
	* Deletes the institution with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param institutionId the primary key of the institution
	* @return the institution that was removed
	* @throws PortalException if a institution with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution deleteInstitution(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteInstitution(institutionId);
	}

	/**
	* Deletes the institution from the database. Also notifies the appropriate model listeners.
	*
	* @param institution the institution
	* @return the institution that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution deleteInstitution(
		de.uhh.l2g.plugins.model.Institution institution)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteInstitution(institution);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstitutionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstitutionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static de.uhh.l2g.plugins.model.Institution fetchInstitution(
		long institutionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchInstitution(institutionId);
	}

	/**
	* Returns the institution with the primary key.
	*
	* @param institutionId the primary key of the institution
	* @return the institution
	* @throws PortalException if a institution with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution getInstitution(
		long institutionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitution(institutionId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the institutions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.InstitutionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of institutions
	* @param end the upper bound of the range of institutions (not inclusive)
	* @return the range of institutions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getInstitutions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitutions(start, end);
	}

	/**
	* Returns the number of institutions.
	*
	* @return the number of institutions
	* @throws SystemException if a system exception occurred
	*/
	public static int getInstitutionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getInstitutionsCount();
	}

	/**
	* Updates the institution in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param institution the institution
	* @return the institution that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Institution updateInstitution(
		de.uhh.l2g.plugins.model.Institution institution)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateInstitution(institution);
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

	public static de.uhh.l2g.plugins.model.Institution getById(
		long institutionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getById(institutionId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getRootInstitutionsByOpenAccessVideos()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRootInstitutionsByOpenAccessVideos();
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getByParentIdList(
		long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByParentIdList(parentId);
	}

	public static int getByParentIdCount(long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByParentIdCount(parentId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getByParentId(
		long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByParentId(parentId);
	}

	public static de.uhh.l2g.plugins.model.Institution getRoot()
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchInstitutionException {
		return getService().getRoot();
	}

	public static java.util.Map<java.lang.String, java.lang.String> getByParentIdMap(
		long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByParentIdMap(parentId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getByLevel(
		int level) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByLevel(level);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getByLectureseriesId(
		long lectureseriesId, int begin, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getByLectureseriesId(lectureseriesId, begin, end);
	}

	public static java.util.Map<java.lang.String, java.lang.String> getAllSortedAsTree(
		int begin, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAllSortedAsTree(begin, end);
	}

	public static int getMaxSortByParentId(long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMaxSortByParentId(parentId);
	}

	public static int getLockingElements(long institutionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLockingElements(institutionId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getInstitutionsFromLectureseriesIdsAndVideoIds(
		java.util.ArrayList<java.lang.Long> lectureseriesIds,
		java.util.ArrayList<java.lang.Long> videoIds) {
		return getService()
				   .getInstitutionsFromLectureseriesIdsAndVideoIds(lectureseriesIds,
			videoIds);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getInstitutionsFromLectureseriesIdsAndVideoIds(
		java.util.ArrayList<java.lang.Long> lectureseriesIds,
		java.util.ArrayList<java.lang.Long> videoIds, java.lang.Long parentId) {
		return getService()
				   .getInstitutionsFromLectureseriesIdsAndVideoIds(lectureseriesIds,
			videoIds, parentId);
	}

	public static long getDefaultInstitutionId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDefaultInstitutionId();
	}

	public static de.uhh.l2g.plugins.model.Institution addDefaultInstitution(
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addDefaultInstitution(serviceContext);
	}

	public static de.uhh.l2g.plugins.model.Institution addInstitution(
		java.lang.String name, long hostId, long parentId, int sort)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addInstitution(name, hostId, parentId, sort);
	}

	public static de.uhh.l2g.plugins.model.Institution updateInstitution(
		long institutionId, java.lang.String name, int sort)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateInstitution(institutionId, name, sort);
	}

	public static de.uhh.l2g.plugins.model.Institution removeByInstitutionId(
		java.lang.Long institutionId) {
		return getService().removeByInstitutionId(institutionId);
	}

	public static long updateCounter()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCounter();
	}

	/**
	* Prepares the list of institutions for pending producers selection
	* (institutions which should not be selected can be defined via properties file)
	*
	* @return the list of institutions for the producer to select
	* @throws SystemException
	*/
	public static java.util.List<de.uhh.l2g.plugins.model.Institution> getForProducerPending()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getForProducerPending();
	}

	public static void clearService() {
		_service = null;
	}

	public static InstitutionLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					InstitutionLocalService.class.getName());

			if (invokableLocalService instanceof InstitutionLocalService) {
				_service = (InstitutionLocalService)invokableLocalService;
			}
			else {
				_service = new InstitutionLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(InstitutionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(InstitutionLocalService service) {
	}

	private static InstitutionLocalService _service;
}