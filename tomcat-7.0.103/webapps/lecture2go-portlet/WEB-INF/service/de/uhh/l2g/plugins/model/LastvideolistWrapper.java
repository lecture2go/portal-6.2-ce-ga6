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
 * This class is a wrapper for {@link Lastvideolist}.
 * </p>
 *
 * @author Iavor Sturm
 * @see Lastvideolist
 * @generated
 */
public class LastvideolistWrapper implements Lastvideolist,
	ModelWrapper<Lastvideolist> {
	public LastvideolistWrapper(Lastvideolist lastvideolist) {
		_lastvideolist = lastvideolist;
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

	/**
	* Returns the primary key of this lastvideolist.
	*
	* @return the primary key of this lastvideolist
	*/
	@Override
	public long getPrimaryKey() {
		return _lastvideolist.getPrimaryKey();
	}

	/**
	* Sets the primary key of this lastvideolist.
	*
	* @param primaryKey the primary key of this lastvideolist
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_lastvideolist.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the lastvideolist ID of this lastvideolist.
	*
	* @return the lastvideolist ID of this lastvideolist
	*/
	@Override
	public long getLastvideolistId() {
		return _lastvideolist.getLastvideolistId();
	}

	/**
	* Sets the lastvideolist ID of this lastvideolist.
	*
	* @param lastvideolistId the lastvideolist ID of this lastvideolist
	*/
	@Override
	public void setLastvideolistId(long lastvideolistId) {
		_lastvideolist.setLastvideolistId(lastvideolistId);
	}

	/**
	* Returns the video ID of this lastvideolist.
	*
	* @return the video ID of this lastvideolist
	*/
	@Override
	public long getVideoId() {
		return _lastvideolist.getVideoId();
	}

	/**
	* Sets the video ID of this lastvideolist.
	*
	* @param videoId the video ID of this lastvideolist
	*/
	@Override
	public void setVideoId(long videoId) {
		_lastvideolist.setVideoId(videoId);
	}

	@Override
	public boolean isNew() {
		return _lastvideolist.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_lastvideolist.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _lastvideolist.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_lastvideolist.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _lastvideolist.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _lastvideolist.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_lastvideolist.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _lastvideolist.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_lastvideolist.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_lastvideolist.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_lastvideolist.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LastvideolistWrapper((Lastvideolist)_lastvideolist.clone());
	}

	@Override
	public int compareTo(de.uhh.l2g.plugins.model.Lastvideolist lastvideolist) {
		return _lastvideolist.compareTo(lastvideolist);
	}

	@Override
	public int hashCode() {
		return _lastvideolist.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.uhh.l2g.plugins.model.Lastvideolist> toCacheModel() {
		return _lastvideolist.toCacheModel();
	}

	@Override
	public de.uhh.l2g.plugins.model.Lastvideolist toEscapedModel() {
		return new LastvideolistWrapper(_lastvideolist.toEscapedModel());
	}

	@Override
	public de.uhh.l2g.plugins.model.Lastvideolist toUnescapedModel() {
		return new LastvideolistWrapper(_lastvideolist.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _lastvideolist.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _lastvideolist.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_lastvideolist.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LastvideolistWrapper)) {
			return false;
		}

		LastvideolistWrapper lastvideolistWrapper = (LastvideolistWrapper)obj;

		if (Validator.equals(_lastvideolist, lastvideolistWrapper._lastvideolist)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Lastvideolist getWrappedLastvideolist() {
		return _lastvideolist;
	}

	@Override
	public Lastvideolist getWrappedModel() {
		return _lastvideolist;
	}

	@Override
	public void resetOriginalValues() {
		_lastvideolist.resetOriginalValues();
	}

	private Lastvideolist _lastvideolist;
}