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

package de.uhh.l2g.plugins.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import de.uhh.l2g.plugins.model.Videohitlist;
import de.uhh.l2g.plugins.model.VideohitlistModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Videohitlist service. Represents a row in the &quot;LG_Videohitlist&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link de.uhh.l2g.plugins.model.VideohitlistModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link VideohitlistImpl}.
 * </p>
 *
 * @author Iavor Sturm
 * @see VideohitlistImpl
 * @see de.uhh.l2g.plugins.model.Videohitlist
 * @see de.uhh.l2g.plugins.model.VideohitlistModel
 * @generated
 */
public class VideohitlistModelImpl extends BaseModelImpl<Videohitlist>
	implements VideohitlistModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a videohitlist model instance should use the {@link de.uhh.l2g.plugins.model.Videohitlist} interface instead.
	 */
	public static final String TABLE_NAME = "LG_Videohitlist";
	public static final Object[][] TABLE_COLUMNS = {
			{ "videohitlistId", Types.BIGINT },
			{ "hitsPerDay", Types.BIGINT },
			{ "hitsPerWeek", Types.BIGINT },
			{ "hitsPerMonth", Types.BIGINT },
			{ "hitsPerYear", Types.BIGINT },
			{ "videoId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table LG_Videohitlist (videohitlistId LONG not null primary key,hitsPerDay LONG,hitsPerWeek LONG,hitsPerMonth LONG,hitsPerYear LONG,videoId LONG)";
	public static final String TABLE_SQL_DROP = "drop table LG_Videohitlist";
	public static final String ORDER_BY_JPQL = " ORDER BY videohitlist.videohitlistId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY LG_Videohitlist.videohitlistId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.de.uhh.l2g.plugins.model.Videohitlist"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.de.uhh.l2g.plugins.model.Videohitlist"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.de.uhh.l2g.plugins.model.Videohitlist"),
			true);
	public static long VIDEOID_COLUMN_BITMASK = 1L;
	public static long VIDEOHITLISTID_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.de.uhh.l2g.plugins.model.Videohitlist"));

	public VideohitlistModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _videohitlistId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setVideohitlistId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _videohitlistId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Videohitlist.class;
	}

	@Override
	public String getModelClassName() {
		return Videohitlist.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("videohitlistId", getVideohitlistId());
		attributes.put("hitsPerDay", getHitsPerDay());
		attributes.put("hitsPerWeek", getHitsPerWeek());
		attributes.put("hitsPerMonth", getHitsPerMonth());
		attributes.put("hitsPerYear", getHitsPerYear());
		attributes.put("videoId", getVideoId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long videohitlistId = (Long)attributes.get("videohitlistId");

		if (videohitlistId != null) {
			setVideohitlistId(videohitlistId);
		}

		Long hitsPerDay = (Long)attributes.get("hitsPerDay");

		if (hitsPerDay != null) {
			setHitsPerDay(hitsPerDay);
		}

		Long hitsPerWeek = (Long)attributes.get("hitsPerWeek");

		if (hitsPerWeek != null) {
			setHitsPerWeek(hitsPerWeek);
		}

		Long hitsPerMonth = (Long)attributes.get("hitsPerMonth");

		if (hitsPerMonth != null) {
			setHitsPerMonth(hitsPerMonth);
		}

		Long hitsPerYear = (Long)attributes.get("hitsPerYear");

		if (hitsPerYear != null) {
			setHitsPerYear(hitsPerYear);
		}

		Long videoId = (Long)attributes.get("videoId");

		if (videoId != null) {
			setVideoId(videoId);
		}
	}

	@Override
	public long getVideohitlistId() {
		return _videohitlistId;
	}

	@Override
	public void setVideohitlistId(long videohitlistId) {
		_videohitlistId = videohitlistId;
	}

	@Override
	public long getHitsPerDay() {
		return _hitsPerDay;
	}

	@Override
	public void setHitsPerDay(long hitsPerDay) {
		_hitsPerDay = hitsPerDay;
	}

	@Override
	public long getHitsPerWeek() {
		return _hitsPerWeek;
	}

	@Override
	public void setHitsPerWeek(long hitsPerWeek) {
		_hitsPerWeek = hitsPerWeek;
	}

	@Override
	public long getHitsPerMonth() {
		return _hitsPerMonth;
	}

	@Override
	public void setHitsPerMonth(long hitsPerMonth) {
		_hitsPerMonth = hitsPerMonth;
	}

	@Override
	public long getHitsPerYear() {
		return _hitsPerYear;
	}

	@Override
	public void setHitsPerYear(long hitsPerYear) {
		_hitsPerYear = hitsPerYear;
	}

	@Override
	public long getVideoId() {
		return _videoId;
	}

	@Override
	public void setVideoId(long videoId) {
		_columnBitmask |= VIDEOID_COLUMN_BITMASK;

		if (!_setOriginalVideoId) {
			_setOriginalVideoId = true;

			_originalVideoId = _videoId;
		}

		_videoId = videoId;
	}

	public long getOriginalVideoId() {
		return _originalVideoId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			Videohitlist.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Videohitlist toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Videohitlist)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		VideohitlistImpl videohitlistImpl = new VideohitlistImpl();

		videohitlistImpl.setVideohitlistId(getVideohitlistId());
		videohitlistImpl.setHitsPerDay(getHitsPerDay());
		videohitlistImpl.setHitsPerWeek(getHitsPerWeek());
		videohitlistImpl.setHitsPerMonth(getHitsPerMonth());
		videohitlistImpl.setHitsPerYear(getHitsPerYear());
		videohitlistImpl.setVideoId(getVideoId());

		videohitlistImpl.resetOriginalValues();

		return videohitlistImpl;
	}

	@Override
	public int compareTo(Videohitlist videohitlist) {
		long primaryKey = videohitlist.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Videohitlist)) {
			return false;
		}

		Videohitlist videohitlist = (Videohitlist)obj;

		long primaryKey = videohitlist.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		VideohitlistModelImpl videohitlistModelImpl = this;

		videohitlistModelImpl._originalVideoId = videohitlistModelImpl._videoId;

		videohitlistModelImpl._setOriginalVideoId = false;

		videohitlistModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Videohitlist> toCacheModel() {
		VideohitlistCacheModel videohitlistCacheModel = new VideohitlistCacheModel();

		videohitlistCacheModel.videohitlistId = getVideohitlistId();

		videohitlistCacheModel.hitsPerDay = getHitsPerDay();

		videohitlistCacheModel.hitsPerWeek = getHitsPerWeek();

		videohitlistCacheModel.hitsPerMonth = getHitsPerMonth();

		videohitlistCacheModel.hitsPerYear = getHitsPerYear();

		videohitlistCacheModel.videoId = getVideoId();

		return videohitlistCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{videohitlistId=");
		sb.append(getVideohitlistId());
		sb.append(", hitsPerDay=");
		sb.append(getHitsPerDay());
		sb.append(", hitsPerWeek=");
		sb.append(getHitsPerWeek());
		sb.append(", hitsPerMonth=");
		sb.append(getHitsPerMonth());
		sb.append(", hitsPerYear=");
		sb.append(getHitsPerYear());
		sb.append(", videoId=");
		sb.append(getVideoId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("de.uhh.l2g.plugins.model.Videohitlist");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>videohitlistId</column-name><column-value><![CDATA[");
		sb.append(getVideohitlistId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hitsPerDay</column-name><column-value><![CDATA[");
		sb.append(getHitsPerDay());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hitsPerWeek</column-name><column-value><![CDATA[");
		sb.append(getHitsPerWeek());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hitsPerMonth</column-name><column-value><![CDATA[");
		sb.append(getHitsPerMonth());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hitsPerYear</column-name><column-value><![CDATA[");
		sb.append(getHitsPerYear());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>videoId</column-name><column-value><![CDATA[");
		sb.append(getVideoId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Videohitlist.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Videohitlist.class
		};
	private long _videohitlistId;
	private long _hitsPerDay;
	private long _hitsPerWeek;
	private long _hitsPerMonth;
	private long _hitsPerYear;
	private long _videoId;
	private long _originalVideoId;
	private boolean _setOriginalVideoId;
	private long _columnBitmask;
	private Videohitlist _escapedModel;
}