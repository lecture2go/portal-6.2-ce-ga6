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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.uhh.l2g.plugins.NoSuchVideoStatisticException;
import de.uhh.l2g.plugins.model.VideoStatistic;
import de.uhh.l2g.plugins.model.impl.VideoStatisticImpl;
import de.uhh.l2g.plugins.model.impl.VideoStatisticModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the video statistic service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Iavor Sturm
 * @see VideoStatisticPersistence
 * @see VideoStatisticUtil
 * @generated
 */
public class VideoStatisticPersistenceImpl extends BasePersistenceImpl<VideoStatistic>
	implements VideoStatisticPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link VideoStatisticUtil} to access the video statistic persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = VideoStatisticImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED,
			VideoStatisticImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED,
			VideoStatisticImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDANDGROUPID =
		new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED,
			VideoStatisticImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyIdAndGroupId",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDANDGROUPID =
		new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED,
			VideoStatisticImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyIdAndGroupId",
			new String[] { Long.class.getName(), Long.class.getName() },
			VideoStatisticModelImpl.GROUPID_COLUMN_BITMASK |
			VideoStatisticModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYIDANDGROUPID = new FinderPath(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyIdAndGroupId",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the video statistics where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findByCompanyIdAndGroupId(long groupId,
		long companyId) throws SystemException {
		return findByCompanyIdAndGroupId(groupId, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the video statistics where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoStatisticModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of video statistics
	 * @param end the upper bound of the range of video statistics (not inclusive)
	 * @return the range of matching video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findByCompanyIdAndGroupId(long groupId,
		long companyId, int start, int end) throws SystemException {
		return findByCompanyIdAndGroupId(groupId, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the video statistics where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoStatisticModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of video statistics
	 * @param end the upper bound of the range of video statistics (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findByCompanyIdAndGroupId(long groupId,
		long companyId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDANDGROUPID;
			finderArgs = new Object[] { groupId, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYIDANDGROUPID;
			finderArgs = new Object[] {
					groupId, companyId,
					
					start, end, orderByComparator
				};
		}

		List<VideoStatistic> list = (List<VideoStatistic>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (VideoStatistic videoStatistic : list) {
				if ((groupId != videoStatistic.getGroupId()) ||
						(companyId != videoStatistic.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_VIDEOSTATISTIC_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_GROUPID_2);

			query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VideoStatisticModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<VideoStatistic>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<VideoStatistic>(list);
				}
				else {
					list = (List<VideoStatistic>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first video statistic in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching video statistic
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a matching video statistic could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic findByCompanyIdAndGroupId_First(long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchVideoStatisticException, SystemException {
		VideoStatistic videoStatistic = fetchByCompanyIdAndGroupId_First(groupId,
				companyId, orderByComparator);

		if (videoStatistic != null) {
			return videoStatistic;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVideoStatisticException(msg.toString());
	}

	/**
	 * Returns the first video statistic in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching video statistic, or <code>null</code> if a matching video statistic could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic fetchByCompanyIdAndGroupId_First(long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws SystemException {
		List<VideoStatistic> list = findByCompanyIdAndGroupId(groupId,
				companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last video statistic in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching video statistic
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a matching video statistic could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic findByCompanyIdAndGroupId_Last(long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchVideoStatisticException, SystemException {
		VideoStatistic videoStatistic = fetchByCompanyIdAndGroupId_Last(groupId,
				companyId, orderByComparator);

		if (videoStatistic != null) {
			return videoStatistic;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchVideoStatisticException(msg.toString());
	}

	/**
	 * Returns the last video statistic in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching video statistic, or <code>null</code> if a matching video statistic could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic fetchByCompanyIdAndGroupId_Last(long groupId,
		long companyId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByCompanyIdAndGroupId(groupId, companyId);

		if (count == 0) {
			return null;
		}

		List<VideoStatistic> list = findByCompanyIdAndGroupId(groupId,
				companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the video statistics before and after the current video statistic in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param videoStatisticId the primary key of the current video statistic
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next video statistic
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic[] findByCompanyIdAndGroupId_PrevAndNext(
		long videoStatisticId, long groupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchVideoStatisticException, SystemException {
		VideoStatistic videoStatistic = findByPrimaryKey(videoStatisticId);

		Session session = null;

		try {
			session = openSession();

			VideoStatistic[] array = new VideoStatisticImpl[3];

			array[0] = getByCompanyIdAndGroupId_PrevAndNext(session,
					videoStatistic, groupId, companyId, orderByComparator, true);

			array[1] = videoStatistic;

			array[2] = getByCompanyIdAndGroupId_PrevAndNext(session,
					videoStatistic, groupId, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected VideoStatistic getByCompanyIdAndGroupId_PrevAndNext(
		Session session, VideoStatistic videoStatistic, long groupId,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_VIDEOSTATISTIC_WHERE);

		query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_GROUPID_2);

		query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(VideoStatisticModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(videoStatistic);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VideoStatistic> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the video statistics where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByCompanyIdAndGroupId(long groupId, long companyId)
		throws SystemException {
		for (VideoStatistic videoStatistic : findByCompanyIdAndGroupId(
				groupId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(videoStatistic);
		}
	}

	/**
	 * Returns the number of video statistics where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByCompanyIdAndGroupId(long groupId, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYIDANDGROUPID;

		Object[] finderArgs = new Object[] { groupId, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VIDEOSTATISTIC_WHERE);

			query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_GROUPID_2);

			query.append(_FINDER_COLUMN_COMPANYIDANDGROUPID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYIDANDGROUPID_GROUPID_2 = "videoStatistic.groupId = ? AND ";
	private static final String _FINDER_COLUMN_COMPANYIDANDGROUPID_COMPANYID_2 = "videoStatistic.companyId = ?";

	public VideoStatisticPersistenceImpl() {
		setModelClass(VideoStatistic.class);
	}

	/**
	 * Caches the video statistic in the entity cache if it is enabled.
	 *
	 * @param videoStatistic the video statistic
	 */
	@Override
	public void cacheResult(VideoStatistic videoStatistic) {
		EntityCacheUtil.putResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticImpl.class, videoStatistic.getPrimaryKey(),
			videoStatistic);

		videoStatistic.resetOriginalValues();
	}

	/**
	 * Caches the video statistics in the entity cache if it is enabled.
	 *
	 * @param videoStatistics the video statistics
	 */
	@Override
	public void cacheResult(List<VideoStatistic> videoStatistics) {
		for (VideoStatistic videoStatistic : videoStatistics) {
			if (EntityCacheUtil.getResult(
						VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
						VideoStatisticImpl.class, videoStatistic.getPrimaryKey()) == null) {
				cacheResult(videoStatistic);
			}
			else {
				videoStatistic.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all video statistics.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(VideoStatisticImpl.class.getName());
		}

		EntityCacheUtil.clearCache(VideoStatisticImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the video statistic.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(VideoStatistic videoStatistic) {
		EntityCacheUtil.removeResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticImpl.class, videoStatistic.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<VideoStatistic> videoStatistics) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (VideoStatistic videoStatistic : videoStatistics) {
			EntityCacheUtil.removeResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
				VideoStatisticImpl.class, videoStatistic.getPrimaryKey());
		}
	}

	/**
	 * Creates a new video statistic with the primary key. Does not add the video statistic to the database.
	 *
	 * @param videoStatisticId the primary key for the new video statistic
	 * @return the new video statistic
	 */
	@Override
	public VideoStatistic create(long videoStatisticId) {
		VideoStatistic videoStatistic = new VideoStatisticImpl();

		videoStatistic.setNew(true);
		videoStatistic.setPrimaryKey(videoStatisticId);

		return videoStatistic;
	}

	/**
	 * Removes the video statistic with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param videoStatisticId the primary key of the video statistic
	 * @return the video statistic that was removed
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic remove(long videoStatisticId)
		throws NoSuchVideoStatisticException, SystemException {
		return remove((Serializable)videoStatisticId);
	}

	/**
	 * Removes the video statistic with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the video statistic
	 * @return the video statistic that was removed
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic remove(Serializable primaryKey)
		throws NoSuchVideoStatisticException, SystemException {
		Session session = null;

		try {
			session = openSession();

			VideoStatistic videoStatistic = (VideoStatistic)session.get(VideoStatisticImpl.class,
					primaryKey);

			if (videoStatistic == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVideoStatisticException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(videoStatistic);
		}
		catch (NoSuchVideoStatisticException nsee) {
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
	protected VideoStatistic removeImpl(VideoStatistic videoStatistic)
		throws SystemException {
		videoStatistic = toUnwrappedModel(videoStatistic);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(videoStatistic)) {
				videoStatistic = (VideoStatistic)session.get(VideoStatisticImpl.class,
						videoStatistic.getPrimaryKeyObj());
			}

			if (videoStatistic != null) {
				session.delete(videoStatistic);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (videoStatistic != null) {
			clearCache(videoStatistic);
		}

		return videoStatistic;
	}

	@Override
	public VideoStatistic updateImpl(
		de.uhh.l2g.plugins.model.VideoStatistic videoStatistic)
		throws SystemException {
		videoStatistic = toUnwrappedModel(videoStatistic);

		boolean isNew = videoStatistic.isNew();

		VideoStatisticModelImpl videoStatisticModelImpl = (VideoStatisticModelImpl)videoStatistic;

		Session session = null;

		try {
			session = openSession();

			if (videoStatistic.isNew()) {
				session.save(videoStatistic);

				videoStatistic.setNew(false);
			}
			else {
				session.merge(videoStatistic);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !VideoStatisticModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((videoStatisticModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDANDGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						videoStatisticModelImpl.getOriginalGroupId(),
						videoStatisticModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDANDGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDANDGROUPID,
					args);

				args = new Object[] {
						videoStatisticModelImpl.getGroupId(),
						videoStatisticModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYIDANDGROUPID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYIDANDGROUPID,
					args);
			}
		}

		EntityCacheUtil.putResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
			VideoStatisticImpl.class, videoStatistic.getPrimaryKey(),
			videoStatistic);

		return videoStatistic;
	}

	protected VideoStatistic toUnwrappedModel(VideoStatistic videoStatistic) {
		if (videoStatistic instanceof VideoStatisticImpl) {
			return videoStatistic;
		}

		VideoStatisticImpl videoStatisticImpl = new VideoStatisticImpl();

		videoStatisticImpl.setNew(videoStatistic.isNew());
		videoStatisticImpl.setPrimaryKey(videoStatistic.getPrimaryKey());

		videoStatisticImpl.setVideoStatisticId(videoStatistic.getVideoStatisticId());
		videoStatisticImpl.setGroupId(videoStatistic.getGroupId());
		videoStatisticImpl.setCompanyId(videoStatistic.getCompanyId());
		videoStatisticImpl.setCreateDate(videoStatistic.getCreateDate());
		videoStatisticImpl.setModifiedDate(videoStatistic.getModifiedDate());
		videoStatisticImpl.setCompareDate(videoStatistic.getCompareDate());
		videoStatisticImpl.setTotalVideos(videoStatistic.getTotalVideos());
		videoStatisticImpl.setPublicVideos(videoStatistic.getPublicVideos());
		videoStatisticImpl.setPrivateVideos(videoStatistic.getPrivateVideos());
		videoStatisticImpl.setCurrentTotal(videoStatistic.getCurrentTotal());
		videoStatisticImpl.setCurrentPublic(videoStatistic.getCurrentPublic());
		videoStatisticImpl.setCurrentPrivate(videoStatistic.getCurrentPrivate());
		videoStatisticImpl.setPubPercent(videoStatistic.getPubPercent());
		videoStatisticImpl.setPrivPercent(videoStatistic.getPrivPercent());
		videoStatisticImpl.setCurrentPubPercent(videoStatistic.getCurrentPubPercent());
		videoStatisticImpl.setCurrentPrivPercent(videoStatistic.getCurrentPrivPercent());
		videoStatisticImpl.setIntervalName(videoStatistic.getIntervalName());
		videoStatisticImpl.setPublicDiff(videoStatistic.getPublicDiff());
		videoStatisticImpl.setPrivateDiff(videoStatistic.getPrivateDiff());
		videoStatisticImpl.setTotalDiff(videoStatistic.getTotalDiff());
		videoStatisticImpl.setDateDiff(videoStatistic.getDateDiff());

		return videoStatisticImpl;
	}

	/**
	 * Returns the video statistic with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the video statistic
	 * @return the video statistic
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVideoStatisticException, SystemException {
		VideoStatistic videoStatistic = fetchByPrimaryKey(primaryKey);

		if (videoStatistic == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVideoStatisticException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return videoStatistic;
	}

	/**
	 * Returns the video statistic with the primary key or throws a {@link de.uhh.l2g.plugins.NoSuchVideoStatisticException} if it could not be found.
	 *
	 * @param videoStatisticId the primary key of the video statistic
	 * @return the video statistic
	 * @throws de.uhh.l2g.plugins.NoSuchVideoStatisticException if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic findByPrimaryKey(long videoStatisticId)
		throws NoSuchVideoStatisticException, SystemException {
		return findByPrimaryKey((Serializable)videoStatisticId);
	}

	/**
	 * Returns the video statistic with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the video statistic
	 * @return the video statistic, or <code>null</code> if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		VideoStatistic videoStatistic = (VideoStatistic)EntityCacheUtil.getResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
				VideoStatisticImpl.class, primaryKey);

		if (videoStatistic == _nullVideoStatistic) {
			return null;
		}

		if (videoStatistic == null) {
			Session session = null;

			try {
				session = openSession();

				videoStatistic = (VideoStatistic)session.get(VideoStatisticImpl.class,
						primaryKey);

				if (videoStatistic != null) {
					cacheResult(videoStatistic);
				}
				else {
					EntityCacheUtil.putResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
						VideoStatisticImpl.class, primaryKey,
						_nullVideoStatistic);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(VideoStatisticModelImpl.ENTITY_CACHE_ENABLED,
					VideoStatisticImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return videoStatistic;
	}

	/**
	 * Returns the video statistic with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param videoStatisticId the primary key of the video statistic
	 * @return the video statistic, or <code>null</code> if a video statistic with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public VideoStatistic fetchByPrimaryKey(long videoStatisticId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)videoStatisticId);
	}

	/**
	 * Returns all the video statistics.
	 *
	 * @return the video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the video statistics.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoStatisticModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of video statistics
	 * @param end the upper bound of the range of video statistics (not inclusive)
	 * @return the range of video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the video statistics.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoStatisticModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of video statistics
	 * @param end the upper bound of the range of video statistics (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of video statistics
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<VideoStatistic> findAll(int start, int end,
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

		List<VideoStatistic> list = (List<VideoStatistic>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_VIDEOSTATISTIC);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_VIDEOSTATISTIC;

				if (pagination) {
					sql = sql.concat(VideoStatisticModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<VideoStatistic>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<VideoStatistic>(list);
				}
				else {
					list = (List<VideoStatistic>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the video statistics from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (VideoStatistic videoStatistic : findAll()) {
			remove(videoStatistic);
		}
	}

	/**
	 * Returns the number of video statistics.
	 *
	 * @return the number of video statistics
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

				Query q = session.createQuery(_SQL_COUNT_VIDEOSTATISTIC);

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
	 * Initializes the video statistic persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.uhh.l2g.plugins.model.VideoStatistic")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<VideoStatistic>> listenersList = new ArrayList<ModelListener<VideoStatistic>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<VideoStatistic>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(VideoStatisticImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_VIDEOSTATISTIC = "SELECT videoStatistic FROM VideoStatistic videoStatistic";
	private static final String _SQL_SELECT_VIDEOSTATISTIC_WHERE = "SELECT videoStatistic FROM VideoStatistic videoStatistic WHERE ";
	private static final String _SQL_COUNT_VIDEOSTATISTIC = "SELECT COUNT(videoStatistic) FROM VideoStatistic videoStatistic";
	private static final String _SQL_COUNT_VIDEOSTATISTIC_WHERE = "SELECT COUNT(videoStatistic) FROM VideoStatistic videoStatistic WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "videoStatistic.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No VideoStatistic exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No VideoStatistic exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(VideoStatisticPersistenceImpl.class);
	private static VideoStatistic _nullVideoStatistic = new VideoStatisticImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<VideoStatistic> toCacheModel() {
				return _nullVideoStatisticCacheModel;
			}
		};

	private static CacheModel<VideoStatistic> _nullVideoStatisticCacheModel = new CacheModel<VideoStatistic>() {
			@Override
			public VideoStatistic toEntityModel() {
				return _nullVideoStatistic;
			}
		};
}
