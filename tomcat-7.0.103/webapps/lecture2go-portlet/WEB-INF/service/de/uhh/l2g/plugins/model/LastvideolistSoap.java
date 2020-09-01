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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Iavor Sturm
 * @generated
 */
public class LastvideolistSoap implements Serializable {
	public static LastvideolistSoap toSoapModel(Lastvideolist model) {
		LastvideolistSoap soapModel = new LastvideolistSoap();

		soapModel.setLastvideolistId(model.getLastvideolistId());
		soapModel.setVideoId(model.getVideoId());

		return soapModel;
	}

	public static LastvideolistSoap[] toSoapModels(Lastvideolist[] models) {
		LastvideolistSoap[] soapModels = new LastvideolistSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LastvideolistSoap[][] toSoapModels(Lastvideolist[][] models) {
		LastvideolistSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LastvideolistSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LastvideolistSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LastvideolistSoap[] toSoapModels(List<Lastvideolist> models) {
		List<LastvideolistSoap> soapModels = new ArrayList<LastvideolistSoap>(models.size());

		for (Lastvideolist model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LastvideolistSoap[soapModels.size()]);
	}

	public LastvideolistSoap() {
	}

	public long getPrimaryKey() {
		return _lastvideolistId;
	}

	public void setPrimaryKey(long pk) {
		setLastvideolistId(pk);
	}

	public long getLastvideolistId() {
		return _lastvideolistId;
	}

	public void setLastvideolistId(long lastvideolistId) {
		_lastvideolistId = lastvideolistId;
	}

	public long getVideoId() {
		return _videoId;
	}

	public void setVideoId(long videoId) {
		_videoId = videoId;
	}

	private long _lastvideolistId;
	private long _videoId;
}