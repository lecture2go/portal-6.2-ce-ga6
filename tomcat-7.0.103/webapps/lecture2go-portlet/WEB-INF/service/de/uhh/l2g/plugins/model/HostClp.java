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
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iavor Sturm
 */
public class HostClp extends BaseModelImpl<Host> implements Host {
	public HostClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Host.class;
	}

	@Override
	public String getModelClassName() {
		return Host.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _hostId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setHostId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _hostId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("hostId", getHostId());
		attributes.put("directory", getDirectory());
		attributes.put("name", getName());
		attributes.put("defaultHost", getDefaultHost());
		attributes.put("prefix", getPrefix());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long hostId = (Long)attributes.get("hostId");

		if (hostId != null) {
			setHostId(hostId);
		}

		String directory = (String)attributes.get("directory");

		if (directory != null) {
			setDirectory(directory);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer defaultHost = (Integer)attributes.get("defaultHost");

		if (defaultHost != null) {
			setDefaultHost(defaultHost);
		}

		String prefix = (String)attributes.get("prefix");

		if (prefix != null) {
			setPrefix(prefix);
		}
	}

	@Override
	public long getHostId() {
		return _hostId;
	}

	@Override
	public void setHostId(long hostId) {
		_hostId = hostId;

		if (_hostRemoteModel != null) {
			try {
				Class<?> clazz = _hostRemoteModel.getClass();

				Method method = clazz.getMethod("setHostId", long.class);

				method.invoke(_hostRemoteModel, hostId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getDirectory() {
		return _directory;
	}

	@Override
	public void setDirectory(String directory) {
		_directory = directory;

		if (_hostRemoteModel != null) {
			try {
				Class<?> clazz = _hostRemoteModel.getClass();

				Method method = clazz.getMethod("setDirectory", String.class);

				method.invoke(_hostRemoteModel, directory);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;

		if (_hostRemoteModel != null) {
			try {
				Class<?> clazz = _hostRemoteModel.getClass();

				Method method = clazz.getMethod("setName", String.class);

				method.invoke(_hostRemoteModel, name);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getDefaultHost() {
		return _defaultHost;
	}

	@Override
	public void setDefaultHost(int defaultHost) {
		_defaultHost = defaultHost;

		if (_hostRemoteModel != null) {
			try {
				Class<?> clazz = _hostRemoteModel.getClass();

				Method method = clazz.getMethod("setDefaultHost", int.class);

				method.invoke(_hostRemoteModel, defaultHost);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getPrefix() {
		return _prefix;
	}

	@Override
	public void setPrefix(String prefix) {
		_prefix = prefix;

		if (_hostRemoteModel != null) {
			try {
				Class<?> clazz = _hostRemoteModel.getClass();

				Method method = clazz.getMethod("setPrefix", String.class);

				method.invoke(_hostRemoteModel, prefix);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getHostRemoteModel() {
		return _hostRemoteModel;
	}

	public void setHostRemoteModel(BaseModel<?> hostRemoteModel) {
		_hostRemoteModel = hostRemoteModel;
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

		Class<?> remoteModelClass = _hostRemoteModel.getClass();

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

		Object returnValue = method.invoke(_hostRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			HostLocalServiceUtil.addHost(this);
		}
		else {
			HostLocalServiceUtil.updateHost(this);
		}
	}

	@Override
	public Host toEscapedModel() {
		return (Host)ProxyUtil.newProxyInstance(Host.class.getClassLoader(),
			new Class[] { Host.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		HostClp clone = new HostClp();

		clone.setHostId(getHostId());
		clone.setDirectory(getDirectory());
		clone.setName(getName());
		clone.setDefaultHost(getDefaultHost());
		clone.setPrefix(getPrefix());

		return clone;
	}

	@Override
	public int compareTo(Host host) {
		long primaryKey = host.getPrimaryKey();

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

		if (!(obj instanceof HostClp)) {
			return false;
		}

		HostClp host = (HostClp)obj;

		long primaryKey = host.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{hostId=");
		sb.append(getHostId());
		sb.append(", directory=");
		sb.append(getDirectory());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", defaultHost=");
		sb.append(getDefaultHost());
		sb.append(", prefix=");
		sb.append(getPrefix());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("de.uhh.l2g.plugins.model.Host");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>hostId</column-name><column-value><![CDATA[");
		sb.append(getHostId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>directory</column-name><column-value><![CDATA[");
		sb.append(getDirectory());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>defaultHost</column-name><column-value><![CDATA[");
		sb.append(getDefaultHost());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>prefix</column-name><column-value><![CDATA[");
		sb.append(getPrefix());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _hostId;
	private String _directory;
	private String _name;
	private int _defaultHost;
	private String _prefix;
	private BaseModel<?> _hostRemoteModel;
	private Class<?> _clpSerializerClass = de.uhh.l2g.plugins.service.ClpSerializer.class;
}