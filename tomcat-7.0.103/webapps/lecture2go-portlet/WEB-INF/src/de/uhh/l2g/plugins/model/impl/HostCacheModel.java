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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.uhh.l2g.plugins.model.Host;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Host in entity cache.
 *
 * @author Iavor Sturm
 * @see Host
 * @generated
 */
public class HostCacheModel implements CacheModel<Host>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{hostId=");
		sb.append(hostId);
		sb.append(", directory=");
		sb.append(directory);
		sb.append(", name=");
		sb.append(name);
		sb.append(", defaultHost=");
		sb.append(defaultHost);
		sb.append(", prefix=");
		sb.append(prefix);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Host toEntityModel() {
		HostImpl hostImpl = new HostImpl();

		hostImpl.setHostId(hostId);

		if (directory == null) {
			hostImpl.setDirectory(StringPool.BLANK);
		}
		else {
			hostImpl.setDirectory(directory);
		}

		if (name == null) {
			hostImpl.setName(StringPool.BLANK);
		}
		else {
			hostImpl.setName(name);
		}

		hostImpl.setDefaultHost(defaultHost);

		if (prefix == null) {
			hostImpl.setPrefix(StringPool.BLANK);
		}
		else {
			hostImpl.setPrefix(prefix);
		}

		hostImpl.resetOriginalValues();

		return hostImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		hostId = objectInput.readLong();
		directory = objectInput.readUTF();
		name = objectInput.readUTF();
		defaultHost = objectInput.readInt();
		prefix = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(hostId);

		if (directory == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(directory);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(defaultHost);

		if (prefix == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(prefix);
		}
	}

	public long hostId;
	public String directory;
	public String name;
	public int defaultHost;
	public String prefix;
}