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

package de.uhh.l2g.plugins.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.LastvideolistLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iavor Sturm
 */
public class LastvideolistClp extends BaseModelImpl<Lastvideolist>
	implements Lastvideolist {
	public LastvideolistClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Lastvideolist.class;
	}

	@Override
	public String getModelClassName() {
		return Lastvideolist.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _lastvideolistId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLastvideolistId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _lastvideolistId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("lastvideolistId", getLastvideolistId());
		attributes.put("videoId", getVideoId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long lastvideolistId = (Long)attributes.get("lastvideolistId");

		if (lastvideolistId != null) {
			setLastvideolistId(lastvideolistId);
		}

		Long videoId = (Long)attributes.get("videoId");

		if (videoId != null) {
			setVideoId(videoId);
		}
	}

	@Override
	public long getLastvideolistId() {
		return _lastvideolistId;
	}

	@Override
	public void setLastvideolistId(long lastvideolistId) {
		_lastvideolistId = lastvideolistId;

		if (_lastvideolistRemoteModel != null) {
			try {
				Class<?> clazz = _lastvideolistRemoteModel.getClass();

				Method method = clazz.getMethod("setLastvideolistId", long.class);

				method.invoke(_lastvideolistRemoteModel, lastvideolistId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getVideoId() {
		return _videoId;
	}

	@Override
	public void setVideoId(long videoId) {
		_videoId = videoId;

		if (_lastvideolistRemoteModel != null) {
			try {
				Class<?> clazz = _lastvideolistRemoteModel.getClass();

				Method method = clazz.getMethod("setVideoId", long.class);

				method.invoke(_lastvideolistRemoteModel, videoId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getLastvideolistRemoteModel() {
		return _lastvideolistRemoteModel;
	}

	public void setLastvideolistRemoteModel(
		BaseModel<?> lastvideolistRemoteModel) {
		_lastvideolistRemoteModel = lastvideolistRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _lastvideolistRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_lastvideolistRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			LastvideolistLocalServiceUtil.addLastvideolist(this);
		}
		else {
			LastvideolistLocalServiceUtil.updateLastvideolist(this);
		}
	}

	@Override
	public Lastvideolist toEscapedModel() {
		return (Lastvideolist)ProxyUtil.newProxyInstance(Lastvideolist.class.getClassLoader(),
			new Class[] { Lastvideolist.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		LastvideolistClp clone = new LastvideolistClp();

		clone.setLastvideolistId(getLastvideolistId());
		clone.setVideoId(getVideoId());

		return clone;
	}

	@Override
	public int compareTo(Lastvideolist lastvideolist) {
		long primaryKey = lastvideolist.getPrimaryKey();

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

		if (!(obj instanceof LastvideolistClp)) {
			return false;
		}

		LastvideolistClp lastvideolist = (LastvideolistClp)obj;

		long primaryKey = lastvideolist.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{lastvideolistId=");
		sb.append(getLastvideolistId());
		sb.append(", videoId=");
		sb.append(getVideoId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("de.uhh.l2g.plugins.model.Lastvideolist");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>lastvideolistId</column-name><column-value><![CDATA[");
		sb.append(getLastvideolistId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>videoId</column-name><column-value><![CDATA[");
		sb.append(getVideoId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _lastvideolistId;
	private long _videoId;
	private BaseModel<?> _lastvideolistRemoteModel;
	private Class<?> _clpSerializerClass = de.uhh.l2g.plugins.service.ClpSerializer.class;
}