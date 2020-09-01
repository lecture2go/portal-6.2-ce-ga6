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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Host}.
 * </p>
 *
 * @author Iavor Sturm
 * @see Host
 * @generated
 */
public class HostWrapper implements Host, ModelWrapper<Host> {
	public HostWrapper(Host host) {
		_host = host;
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

	/**
	* Returns the primary key of this host.
	*
	* @return the primary key of this host
	*/
	@Override
	public long getPrimaryKey() {
		return _host.getPrimaryKey();
	}

	/**
	* Sets the primary key of this host.
	*
	* @param primaryKey the primary key of this host
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_host.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the host ID of this host.
	*
	* @return the host ID of this host
	*/
	@Override
	public long getHostId() {
		return _host.getHostId();
	}

	/**
	* Sets the host ID of this host.
	*
	* @param hostId the host ID of this host
	*/
	@Override
	public void setHostId(long hostId) {
		_host.setHostId(hostId);
	}

	/**
	* Returns the directory of this host.
	*
	* @return the directory of this host
	*/
	@Override
	public java.lang.String getDirectory() {
		return _host.getDirectory();
	}

	/**
	* Sets the directory of this host.
	*
	* @param directory the directory of this host
	*/
	@Override
	public void setDirectory(java.lang.String directory) {
		_host.setDirectory(directory);
	}

	/**
	* Returns the name of this host.
	*
	* @return the name of this host
	*/
	@Override
	public java.lang.String getName() {
		return _host.getName();
	}

	/**
	* Sets the name of this host.
	*
	* @param name the name of this host
	*/
	@Override
	public void setName(java.lang.String name) {
		_host.setName(name);
	}

	/**
	* Returns the default host of this host.
	*
	* @return the default host of this host
	*/
	@Override
	public int getDefaultHost() {
		return _host.getDefaultHost();
	}

	/**
	* Sets the default host of this host.
	*
	* @param defaultHost the default host of this host
	*/
	@Override
	public void setDefaultHost(int defaultHost) {
		_host.setDefaultHost(defaultHost);
	}

	/**
	* Returns the prefix of this host.
	*
	* @return the prefix of this host
	*/
	@Override
	public java.lang.String getPrefix() {
		return _host.getPrefix();
	}

	/**
	* Sets the prefix of this host.
	*
	* @param prefix the prefix of this host
	*/
	@Override
	public void setPrefix(java.lang.String prefix) {
		_host.setPrefix(prefix);
	}

	@Override
	public boolean isNew() {
		return _host.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_host.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _host.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_host.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _host.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _host.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_host.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _host.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_host.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_host.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_host.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new HostWrapper((Host)_host.clone());
	}

	@Override
	public int compareTo(de.uhh.l2g.plugins.model.Host host) {
		return _host.compareTo(host);
	}

	@Override
	public int hashCode() {
		return _host.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.uhh.l2g.plugins.model.Host> toCacheModel() {
		return _host.toCacheModel();
	}

	@Override
	public de.uhh.l2g.plugins.model.Host toEscapedModel() {
		return new HostWrapper(_host.toEscapedModel());
	}

	@Override
	public de.uhh.l2g.plugins.model.Host toUnescapedModel() {
		return new HostWrapper(_host.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _host.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _host.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_host.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof HostWrapper)) {
			return false;
		}

		HostWrapper hostWrapper = (HostWrapper)obj;

		if (Validator.equals(_host, hostWrapper._host)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Host getWrappedHost() {
		return _host;
	}

	@Override
	public Host getWrappedModel() {
		return _host;
	}

	@Override
	public void resetOriginalValues() {
		_host.resetOriginalValues();
	}

	private Host _host;
}