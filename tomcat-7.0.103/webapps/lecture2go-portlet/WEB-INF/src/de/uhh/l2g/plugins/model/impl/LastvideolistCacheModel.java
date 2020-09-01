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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import de.uhh.l2g.plugins.model.Lastvideolist;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Lastvideolist in entity cache.
 *
 * @author Iavor Sturm
 * @see Lastvideolist
 * @generated
 */
public class LastvideolistCacheModel implements CacheModel<Lastvideolist>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{lastvideolistId=");
		sb.append(lastvideolistId);
		sb.append(", videoId=");
		sb.append(videoId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Lastvideolist toEntityModel() {
		LastvideolistImpl lastvideolistImpl = new LastvideolistImpl();

		lastvideolistImpl.setLastvideolistId(lastvideolistId);
		lastvideolistImpl.setVideoId(videoId);

		lastvideolistImpl.resetOriginalValues();

		return lastvideolistImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		lastvideolistId = objectInput.readLong();
		videoId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(lastvideolistId);
		objectOutput.writeLong(videoId);
	}

	public long lastvideolistId;
	public long videoId;
}