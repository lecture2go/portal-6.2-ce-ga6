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

package de.uhh.l2g.plugins.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import de.uhh.l2g.plugins.model.Host;

import java.util.List;

/**
 * The persistence utility for the host service. This utility wraps {@link HostPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Iavor Sturm
 * @see HostPersistence
 * @see HostPersistenceImpl
 * @generated
 */
public class HostUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Host host) {
		getPersistence().clearCache(host);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Host> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Host> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Host> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Host update(Host host) throws SystemException {
		return getPersistence().update(host);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Host update(Host host, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(host, serviceContext);
	}

	/**
	* Returns the host where hostId = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	*
	* @param hostId the host ID
	* @return the matching host
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host findByhostId(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().findByhostId(hostId);
	}

	/**
	* Returns the host where hostId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hostId the host ID
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchByhostId(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByhostId(hostId);
	}

	/**
	* Returns the host where hostId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hostId the host ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchByhostId(long hostId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByhostId(hostId, retrieveFromCache);
	}

	/**
	* Removes the host where hostId = &#63; from the database.
	*
	* @param hostId the host ID
	* @return the host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host removeByhostId(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().removeByhostId(hostId);
	}

	/**
	* Returns the number of hosts where hostId = &#63;.
	*
	* @param hostId the host ID
	* @return the number of matching hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByhostId(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByhostId(hostId);
	}

	/**
	* Returns the host where name = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	*
	* @param name the name
	* @return the matching host
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host findByname(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().findByname(name);
	}

	/**
	* Returns the host where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchByname(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByname(name);
	}

	/**
	* Returns the host where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchByname(
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByname(name, retrieveFromCache);
	}

	/**
	* Removes the host where name = &#63; from the database.
	*
	* @param name the name
	* @return the host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host removeByname(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().removeByname(name);
	}

	/**
	* Returns the number of hosts where name = &#63;.
	*
	* @param name the name
	* @return the number of matching hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countByname(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByname(name);
	}

	/**
	* Returns the host where defaultHost = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	*
	* @param defaultHost the default host
	* @return the matching host
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host findBydefaultHost(
		int defaultHost)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().findBydefaultHost(defaultHost);
	}

	/**
	* Returns the host where defaultHost = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param defaultHost the default host
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchBydefaultHost(
		int defaultHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBydefaultHost(defaultHost);
	}

	/**
	* Returns the host where defaultHost = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param defaultHost the default host
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchBydefaultHost(
		int defaultHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchBydefaultHost(defaultHost, retrieveFromCache);
	}

	/**
	* Removes the host where defaultHost = &#63; from the database.
	*
	* @param defaultHost the default host
	* @return the host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host removeBydefaultHost(
		int defaultHost)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().removeBydefaultHost(defaultHost);
	}

	/**
	* Returns the number of hosts where defaultHost = &#63;.
	*
	* @param defaultHost the default host
	* @return the number of matching hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countBydefaultHost(int defaultHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBydefaultHost(defaultHost);
	}

	/**
	* Returns the host where directory = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	*
	* @param directory the directory
	* @return the matching host
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host findBydirectory(
		java.lang.String directory)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().findBydirectory(directory);
	}

	/**
	* Returns the host where directory = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param directory the directory
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchBydirectory(
		java.lang.String directory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBydirectory(directory);
	}

	/**
	* Returns the host where directory = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param directory the directory
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching host, or <code>null</code> if a matching host could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchBydirectory(
		java.lang.String directory, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchBydirectory(directory, retrieveFromCache);
	}

	/**
	* Removes the host where directory = &#63; from the database.
	*
	* @param directory the directory
	* @return the host that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host removeBydirectory(
		java.lang.String directory)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().removeBydirectory(directory);
	}

	/**
	* Returns the number of hosts where directory = &#63;.
	*
	* @param directory the directory
	* @return the number of matching hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countBydirectory(java.lang.String directory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBydirectory(directory);
	}

	/**
	* Caches the host in the entity cache if it is enabled.
	*
	* @param host the host
	*/
	public static void cacheResult(de.uhh.l2g.plugins.model.Host host) {
		getPersistence().cacheResult(host);
	}

	/**
	* Caches the hosts in the entity cache if it is enabled.
	*
	* @param hosts the hosts
	*/
	public static void cacheResult(
		java.util.List<de.uhh.l2g.plugins.model.Host> hosts) {
		getPersistence().cacheResult(hosts);
	}

	/**
	* Creates a new host with the primary key. Does not add the host to the database.
	*
	* @param hostId the primary key for the new host
	* @return the new host
	*/
	public static de.uhh.l2g.plugins.model.Host create(long hostId) {
		return getPersistence().create(hostId);
	}

	/**
	* Removes the host with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param hostId the primary key of the host
	* @return the host that was removed
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host remove(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().remove(hostId);
	}

	public static de.uhh.l2g.plugins.model.Host updateImpl(
		de.uhh.l2g.plugins.model.Host host)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(host);
	}

	/**
	* Returns the host with the primary key or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	*
	* @param hostId the primary key of the host
	* @return the host
	* @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host findByPrimaryKey(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchHostException {
		return getPersistence().findByPrimaryKey(hostId);
	}

	/**
	* Returns the host with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param hostId the primary key of the host
	* @return the host, or <code>null</code> if a host with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.uhh.l2g.plugins.model.Host fetchByPrimaryKey(long hostId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(hostId);
	}

	/**
	* Returns all the hosts.
	*
	* @return the hosts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.uhh.l2g.plugins.model.Host> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<de.uhh.l2g.plugins.model.Host> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the hosts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.HostModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of hosts
	* @param end the upper bound of the range of hosts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of hosts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.uhh.l2g.plugins.model.Host> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the hosts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of hosts.
	*
	* @return the number of hosts
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static HostPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (HostPersistence)PortletBeanLocatorUtil.locate(de.uhh.l2g.plugins.service.ClpSerializer.getServletContextName(),
					HostPersistence.class.getName());

			ReferenceRegistry.registerReference(HostUtil.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(HostPersistence persistence) {
	}

	private static HostPersistence _persistence;
}