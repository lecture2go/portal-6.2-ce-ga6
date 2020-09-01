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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.uhh.l2g.plugins.NoSuchHostException;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.impl.HostImpl;
import de.uhh.l2g.plugins.model.impl.HostModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the host service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Iavor Sturm
 * @see HostPersistence
 * @see HostUtil
 * @generated
 */
public class HostPersistenceImpl extends BasePersistenceImpl<Host>
	implements HostPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link HostUtil} to access the host persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = HostImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_HOSTID = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByhostId",
			new String[] { Long.class.getName() },
			HostModelImpl.HOSTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HOSTID = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByhostId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the host where hostId = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	 *
	 * @param hostId the host ID
	 * @return the matching host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findByhostId(long hostId)
		throws NoSuchHostException, SystemException {
		Host host = fetchByhostId(hostId);

		if (host == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("hostId=");
			msg.append(hostId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchHostException(msg.toString());
		}

		return host;
	}

	/**
	 * Returns the host where hostId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param hostId the host ID
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByhostId(long hostId) throws SystemException {
		return fetchByhostId(hostId, true);
	}

	/**
	 * Returns the host where hostId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param hostId the host ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByhostId(long hostId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { hostId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_HOSTID,
					finderArgs, this);
		}

		if (result instanceof Host) {
			Host host = (Host)result;

			if ((hostId != host.getHostId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_HOST_WHERE);

			query.append(_FINDER_COLUMN_HOSTID_HOSTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(hostId);

				List<Host> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"HostPersistenceImpl.fetchByhostId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Host host = list.get(0);

					result = host;

					cacheResult(host);

					if ((host.getHostId() != hostId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTID,
							finderArgs, host);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Host)result;
		}
	}

	/**
	 * Removes the host where hostId = &#63; from the database.
	 *
	 * @param hostId the host ID
	 * @return the host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host removeByhostId(long hostId)
		throws NoSuchHostException, SystemException {
		Host host = findByhostId(hostId);

		return remove(host);
	}

	/**
	 * Returns the number of hosts where hostId = &#63;.
	 *
	 * @param hostId the host ID
	 * @return the number of matching hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByhostId(long hostId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_HOSTID;

		Object[] finderArgs = new Object[] { hostId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_HOST_WHERE);

			query.append(_FINDER_COLUMN_HOSTID_HOSTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(hostId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_HOSTID_HOSTID_2 = "host.hostId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_NAME = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByname",
			new String[] { String.class.getName() },
			HostModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_NAME = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByname",
			new String[] { String.class.getName() });

	/**
	 * Returns the host where name = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	 *
	 * @param name the name
	 * @return the matching host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findByname(String name)
		throws NoSuchHostException, SystemException {
		Host host = fetchByname(name);

		if (host == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchHostException(msg.toString());
		}

		return host;
	}

	/**
	 * Returns the host where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByname(String name) throws SystemException {
		return fetchByname(name, true);
	}

	/**
	 * Returns the host where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByname(String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs, this);
		}

		if (result instanceof Host) {
			Host host = (Host)result;

			if (!Validator.equals(name, host.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_HOST_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				List<Host> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"HostPersistenceImpl.fetchByname(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Host host = list.get(0);

					result = host;

					cacheResult(host);

					if ((host.getName() == null) ||
							!host.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
							finderArgs, host);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Host)result;
		}
	}

	/**
	 * Removes the host where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host removeByname(String name)
		throws NoSuchHostException, SystemException {
		Host host = findByname(name);

		return remove(host);
	}

	/**
	 * Returns the number of hosts where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByname(String name) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_NAME;

		Object[] finderArgs = new Object[] { name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_HOST_WHERE);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_NAME_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_NAME_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_NAME_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_NAME_NAME_1 = "host.name IS NULL";
	private static final String _FINDER_COLUMN_NAME_NAME_2 = "host.name = ?";
	private static final String _FINDER_COLUMN_NAME_NAME_3 = "(host.name IS NULL OR host.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_DEFAULTHOST = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchBydefaultHost",
			new String[] { Integer.class.getName() },
			HostModelImpl.DEFAULTHOST_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DEFAULTHOST = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBydefaultHost",
			new String[] { Integer.class.getName() });

	/**
	 * Returns the host where defaultHost = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	 *
	 * @param defaultHost the default host
	 * @return the matching host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findBydefaultHost(int defaultHost)
		throws NoSuchHostException, SystemException {
		Host host = fetchBydefaultHost(defaultHost);

		if (host == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("defaultHost=");
			msg.append(defaultHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchHostException(msg.toString());
		}

		return host;
	}

	/**
	 * Returns the host where defaultHost = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param defaultHost the default host
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchBydefaultHost(int defaultHost) throws SystemException {
		return fetchBydefaultHost(defaultHost, true);
	}

	/**
	 * Returns the host where defaultHost = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param defaultHost the default host
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchBydefaultHost(int defaultHost, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { defaultHost };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
					finderArgs, this);
		}

		if (result instanceof Host) {
			Host host = (Host)result;

			if ((defaultHost != host.getDefaultHost())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_HOST_WHERE);

			query.append(_FINDER_COLUMN_DEFAULTHOST_DEFAULTHOST_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(defaultHost);

				List<Host> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"HostPersistenceImpl.fetchBydefaultHost(int, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Host host = list.get(0);

					result = host;

					cacheResult(host);

					if ((host.getDefaultHost() != defaultHost)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
							finderArgs, host);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Host)result;
		}
	}

	/**
	 * Removes the host where defaultHost = &#63; from the database.
	 *
	 * @param defaultHost the default host
	 * @return the host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host removeBydefaultHost(int defaultHost)
		throws NoSuchHostException, SystemException {
		Host host = findBydefaultHost(defaultHost);

		return remove(host);
	}

	/**
	 * Returns the number of hosts where defaultHost = &#63;.
	 *
	 * @param defaultHost the default host
	 * @return the number of matching hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countBydefaultHost(int defaultHost) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_DEFAULTHOST;

		Object[] finderArgs = new Object[] { defaultHost };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_HOST_WHERE);

			query.append(_FINDER_COLUMN_DEFAULTHOST_DEFAULTHOST_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(defaultHost);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DEFAULTHOST_DEFAULTHOST_2 = "host.defaultHost = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_DIRECTORY = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, HostImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByDirectory",
			new String[] { String.class.getName() },
			HostModelImpl.DIRECTORY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_DIRECTORY = new FinderPath(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDirectory",
			new String[] { String.class.getName() });

	/**
	 * Returns the host where directory = &#63; or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	 *
	 * @param directory the directory
	 * @return the matching host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findByDirectory(String directory)
		throws NoSuchHostException, SystemException {
		Host host = fetchByDirectory(directory);

		if (host == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("directory=");
			msg.append(directory);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchHostException(msg.toString());
		}

		return host;
	}

	/**
	 * Returns the host where directory = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param directory the directory
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByDirectory(String directory) throws SystemException {
		return fetchByDirectory(directory, true);
	}

	/**
	 * Returns the host where directory = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param directory the directory
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching host, or <code>null</code> if a matching host could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByDirectory(String directory, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { directory };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_DIRECTORY,
					finderArgs, this);
		}

		if (result instanceof Host) {
			Host host = (Host)result;

			if (!Validator.equals(directory, host.getDirectory())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_HOST_WHERE);

			boolean bindDirectory = false;

			if (directory == null) {
				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_1);
			}
			else if (directory.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_3);
			}
			else {
				bindDirectory = true;

				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDirectory) {
					qPos.add(directory);
				}

				List<Host> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTORY,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"HostPersistenceImpl.fetchByDirectory(String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Host host = list.get(0);

					result = host;

					cacheResult(host);

					if ((host.getDirectory() == null) ||
							!host.getDirectory().equals(directory)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTORY,
							finderArgs, host);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTORY,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Host)result;
		}
	}

	/**
	 * Removes the host where directory = &#63; from the database.
	 *
	 * @param directory the directory
	 * @return the host that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host removeByDirectory(String directory)
		throws NoSuchHostException, SystemException {
		Host host = findByDirectory(directory);

		return remove(host);
	}

	/**
	 * Returns the number of hosts where directory = &#63;.
	 *
	 * @param directory the directory
	 * @return the number of matching hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByDirectory(String directory) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_DIRECTORY;

		Object[] finderArgs = new Object[] { directory };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_HOST_WHERE);

			boolean bindDirectory = false;

			if (directory == null) {
				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_1);
			}
			else if (directory.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_3);
			}
			else {
				bindDirectory = true;

				query.append(_FINDER_COLUMN_DIRECTORY_DIRECTORY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDirectory) {
					qPos.add(directory);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DIRECTORY_DIRECTORY_1 = "host.directory IS NULL";
	private static final String _FINDER_COLUMN_DIRECTORY_DIRECTORY_2 = "host.directory = ?";
	private static final String _FINDER_COLUMN_DIRECTORY_DIRECTORY_3 = "(host.directory IS NULL OR host.directory = '')";

	public HostPersistenceImpl() {
		setModelClass(Host.class);
	}

	/**
	 * Caches the host in the entity cache if it is enabled.
	 *
	 * @param host the host
	 */
	@Override
	public void cacheResult(Host host) {
		EntityCacheUtil.putResult(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostImpl.class, host.getPrimaryKey(), host);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTID,
			new Object[] { host.getHostId() }, host);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME,
			new Object[] { host.getName() }, host);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
			new Object[] { host.getDefaultHost() }, host);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTORY,
			new Object[] { host.getDirectory() }, host);

		host.resetOriginalValues();
	}

	/**
	 * Caches the hosts in the entity cache if it is enabled.
	 *
	 * @param hosts the hosts
	 */
	@Override
	public void cacheResult(List<Host> hosts) {
		for (Host host : hosts) {
			if (EntityCacheUtil.getResult(HostModelImpl.ENTITY_CACHE_ENABLED,
						HostImpl.class, host.getPrimaryKey()) == null) {
				cacheResult(host);
			}
			else {
				host.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all hosts.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(HostImpl.class.getName());
		}

		EntityCacheUtil.clearCache(HostImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the host.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Host host) {
		EntityCacheUtil.removeResult(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostImpl.class, host.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(host);
	}

	@Override
	public void clearCache(List<Host> hosts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Host host : hosts) {
			EntityCacheUtil.removeResult(HostModelImpl.ENTITY_CACHE_ENABLED,
				HostImpl.class, host.getPrimaryKey());

			clearUniqueFindersCache(host);
		}
	}

	protected void cacheUniqueFindersCache(Host host) {
		if (host.isNew()) {
			Object[] args = new Object[] { host.getHostId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_HOSTID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTID, args, host);

			args = new Object[] { host.getName() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME, args, host);

			args = new Object[] { host.getDefaultHost() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DEFAULTHOST, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DEFAULTHOST, args,
				host);

			args = new Object[] { host.getDirectory() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTORY, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTORY, args, host);
		}
		else {
			HostModelImpl hostModelImpl = (HostModelImpl)host;

			if ((hostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_HOSTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { host.getHostId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_HOSTID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_HOSTID, args,
					host);
			}

			if ((hostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { host.getName() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_NAME, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_NAME, args, host);
			}

			if ((hostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_DEFAULTHOST.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { host.getDefaultHost() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DEFAULTHOST,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DEFAULTHOST,
					args, host);
			}

			if ((hostModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_DIRECTORY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { host.getDirectory() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DIRECTORY, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_DIRECTORY, args,
					host);
			}
		}
	}

	protected void clearUniqueFindersCache(Host host) {
		HostModelImpl hostModelImpl = (HostModelImpl)host;

		Object[] args = new Object[] { host.getHostId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_HOSTID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTID, args);

		if ((hostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_HOSTID.getColumnBitmask()) != 0) {
			args = new Object[] { hostModelImpl.getOriginalHostId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_HOSTID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_HOSTID, args);
		}

		args = new Object[] { host.getName() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME, args);

		if ((hostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_NAME.getColumnBitmask()) != 0) {
			args = new Object[] { hostModelImpl.getOriginalName() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_NAME, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_NAME, args);
		}

		args = new Object[] { host.getDefaultHost() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DEFAULTHOST, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DEFAULTHOST, args);

		if ((hostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_DEFAULTHOST.getColumnBitmask()) != 0) {
			args = new Object[] { hostModelImpl.getOriginalDefaultHost() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DEFAULTHOST, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DEFAULTHOST, args);
		}

		args = new Object[] { host.getDirectory() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DIRECTORY, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTORY, args);

		if ((hostModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_DIRECTORY.getColumnBitmask()) != 0) {
			args = new Object[] { hostModelImpl.getOriginalDirectory() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_DIRECTORY, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_DIRECTORY, args);
		}
	}

	/**
	 * Creates a new host with the primary key. Does not add the host to the database.
	 *
	 * @param hostId the primary key for the new host
	 * @return the new host
	 */
	@Override
	public Host create(long hostId) {
		Host host = new HostImpl();

		host.setNew(true);
		host.setPrimaryKey(hostId);

		return host;
	}

	/**
	 * Removes the host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param hostId the primary key of the host
	 * @return the host that was removed
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host remove(long hostId) throws NoSuchHostException, SystemException {
		return remove((Serializable)hostId);
	}

	/**
	 * Removes the host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the host
	 * @return the host that was removed
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host remove(Serializable primaryKey)
		throws NoSuchHostException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Host host = (Host)session.get(HostImpl.class, primaryKey);

			if (host == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchHostException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(host);
		}
		catch (NoSuchHostException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Host removeImpl(Host host) throws SystemException {
		host = toUnwrappedModel(host);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(host)) {
				host = (Host)session.get(HostImpl.class, host.getPrimaryKeyObj());
			}

			if (host != null) {
				session.delete(host);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (host != null) {
			clearCache(host);
		}

		return host;
	}

	@Override
	public Host updateImpl(de.uhh.l2g.plugins.model.Host host)
		throws SystemException {
		host = toUnwrappedModel(host);

		boolean isNew = host.isNew();

		Session session = null;

		try {
			session = openSession();

			if (host.isNew()) {
				session.save(host);

				host.setNew(false);
			}
			else {
				session.merge(host);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !HostModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(HostModelImpl.ENTITY_CACHE_ENABLED,
			HostImpl.class, host.getPrimaryKey(), host);

		clearUniqueFindersCache(host);
		cacheUniqueFindersCache(host);

		return host;
	}

	protected Host toUnwrappedModel(Host host) {
		if (host instanceof HostImpl) {
			return host;
		}

		HostImpl hostImpl = new HostImpl();

		hostImpl.setNew(host.isNew());
		hostImpl.setPrimaryKey(host.getPrimaryKey());

		hostImpl.setHostId(host.getHostId());
		hostImpl.setDirectory(host.getDirectory());
		hostImpl.setName(host.getName());
		hostImpl.setDefaultHost(host.getDefaultHost());
		hostImpl.setPrefix(host.getPrefix());

		return hostImpl;
	}

	/**
	 * Returns the host with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the host
	 * @return the host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findByPrimaryKey(Serializable primaryKey)
		throws NoSuchHostException, SystemException {
		Host host = fetchByPrimaryKey(primaryKey);

		if (host == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchHostException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return host;
	}

	/**
	 * Returns the host with the primary key or throws a {@link de.uhh.l2g.plugins.NoSuchHostException} if it could not be found.
	 *
	 * @param hostId the primary key of the host
	 * @return the host
	 * @throws de.uhh.l2g.plugins.NoSuchHostException if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host findByPrimaryKey(long hostId)
		throws NoSuchHostException, SystemException {
		return findByPrimaryKey((Serializable)hostId);
	}

	/**
	 * Returns the host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the host
	 * @return the host, or <code>null</code> if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Host host = (Host)EntityCacheUtil.getResult(HostModelImpl.ENTITY_CACHE_ENABLED,
				HostImpl.class, primaryKey);

		if (host == _nullHost) {
			return null;
		}

		if (host == null) {
			Session session = null;

			try {
				session = openSession();

				host = (Host)session.get(HostImpl.class, primaryKey);

				if (host != null) {
					cacheResult(host);
				}
				else {
					EntityCacheUtil.putResult(HostModelImpl.ENTITY_CACHE_ENABLED,
						HostImpl.class, primaryKey, _nullHost);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(HostModelImpl.ENTITY_CACHE_ENABLED,
					HostImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return host;
	}

	/**
	 * Returns the host with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param hostId the primary key of the host
	 * @return the host, or <code>null</code> if a host with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Host fetchByPrimaryKey(long hostId) throws SystemException {
		return fetchByPrimaryKey((Serializable)hostId);
	}

	/**
	 * Returns all the hosts.
	 *
	 * @return the hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Host> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	public List<Host> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
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
	@Override
	public List<Host> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Host> list = (List<Host>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_HOST);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_HOST;

				if (pagination) {
					sql = sql.concat(HostModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Host>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Host>(list);
				}
				else {
					list = (List<Host>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the hosts from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Host host : findAll()) {
			remove(host);
		}
	}

	/**
	 * Returns the number of hosts.
	 *
	 * @return the number of hosts
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_HOST);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the host persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.uhh.l2g.plugins.model.Host")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Host>> listenersList = new ArrayList<ModelListener<Host>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Host>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(HostImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_HOST = "SELECT host FROM Host host";
	private static final String _SQL_SELECT_HOST_WHERE = "SELECT host FROM Host host WHERE ";
	private static final String _SQL_COUNT_HOST = "SELECT COUNT(host) FROM Host host";
	private static final String _SQL_COUNT_HOST_WHERE = "SELECT COUNT(host) FROM Host host WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "host.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Host exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Host exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(HostPersistenceImpl.class);
	private static Host _nullHost = new HostImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Host> toCacheModel() {
				return _nullHostCacheModel;
			}
		};

	private static CacheModel<Host> _nullHostCacheModel = new CacheModel<Host>() {
			@Override
			public Host toEntityModel() {
				return _nullHost;
			}
		};
}