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

package de.uhh.l2g.plugins.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.uhh.l2g.plugins.model.VideoStatistic;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing VideoStatistic in entity cache.
 *
 * @author Iavor Sturm
 * @see VideoStatistic
 * @generated
 */
public class VideoStatisticCacheModel implements CacheModel<VideoStatistic>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

		sb.append("{videoStatisticId=");
		sb.append(videoStatisticId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", compareDate=");
		sb.append(compareDate);
		sb.append(", totalVideos=");
		sb.append(totalVideos);
		sb.append(", publicVideos=");
		sb.append(publicVideos);
		sb.append(", privateVideos=");
		sb.append(privateVideos);
		sb.append(", currentTotal=");
		sb.append(currentTotal);
		sb.append(", currentPublic=");
		sb.append(currentPublic);
		sb.append(", currentPrivate=");
		sb.append(currentPrivate);
		sb.append(", pubPercent=");
		sb.append(pubPercent);
		sb.append(", privPercent=");
		sb.append(privPercent);
		sb.append(", currentPubPercent=");
		sb.append(currentPubPercent);
		sb.append(", currentPrivPercent=");
		sb.append(currentPrivPercent);
		sb.append(", intervalName=");
		sb.append(intervalName);
		sb.append(", publicDiff=");
		sb.append(publicDiff);
		sb.append(", privateDiff=");
		sb.append(privateDiff);
		sb.append(", totalDiff=");
		sb.append(totalDiff);
		sb.append(", dateDiff=");
		sb.append(dateDiff);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VideoStatistic toEntityModel() {
		VideoStatisticImpl videoStatisticImpl = new VideoStatisticImpl();

		videoStatisticImpl.setVideoStatisticId(videoStatisticId);
		videoStatisticImpl.setGroupId(groupId);
		videoStatisticImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			videoStatisticImpl.setCreateDate(null);
		}
		else {
			videoStatisticImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			videoStatisticImpl.setModifiedDate(null);
		}
		else {
			videoStatisticImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (compareDate == Long.MIN_VALUE) {
			videoStatisticImpl.setCompareDate(null);
		}
		else {
			videoStatisticImpl.setCompareDate(new Date(compareDate));
		}

		videoStatisticImpl.setTotalVideos(totalVideos);
		videoStatisticImpl.setPublicVideos(publicVideos);
		videoStatisticImpl.setPrivateVideos(privateVideos);
		videoStatisticImpl.setCurrentTotal(currentTotal);
		videoStatisticImpl.setCurrentPublic(currentPublic);
		videoStatisticImpl.setCurrentPrivate(currentPrivate);
		videoStatisticImpl.setPubPercent(pubPercent);
		videoStatisticImpl.setPrivPercent(privPercent);
		videoStatisticImpl.setCurrentPubPercent(currentPubPercent);
		videoStatisticImpl.setCurrentPrivPercent(currentPrivPercent);

		if (intervalName == null) {
			videoStatisticImpl.setIntervalName(StringPool.BLANK);
		}
		else {
			videoStatisticImpl.setIntervalName(intervalName);
		}

		videoStatisticImpl.setPublicDiff(publicDiff);
		videoStatisticImpl.setPrivateDiff(privateDiff);
		videoStatisticImpl.setTotalDiff(totalDiff);
		videoStatisticImpl.setDateDiff(dateDiff);

		videoStatisticImpl.resetOriginalValues();

		return videoStatisticImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		videoStatisticId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		compareDate = objectInput.readLong();
		totalVideos = objectInput.readLong();
		publicVideos = objectInput.readLong();
		privateVideos = objectInput.readLong();
		currentTotal = objectInput.readLong();
		currentPublic = objectInput.readLong();
		currentPrivate = objectInput.readLong();
		pubPercent = objectInput.readInt();
		privPercent = objectInput.readInt();
		currentPubPercent = objectInput.readInt();
		currentPrivPercent = objectInput.readInt();
		intervalName = objectInput.readUTF();
		publicDiff = objectInput.readInt();
		privateDiff = objectInput.readInt();
		totalDiff = objectInput.readInt();
		dateDiff = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(videoStatisticId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(compareDate);
		objectOutput.writeLong(totalVideos);
		objectOutput.writeLong(publicVideos);
		objectOutput.writeLong(privateVideos);
		objectOutput.writeLong(currentTotal);
		objectOutput.writeLong(currentPublic);
		objectOutput.writeLong(currentPrivate);
		objectOutput.writeInt(pubPercent);
		objectOutput.writeInt(privPercent);
		objectOutput.writeInt(currentPubPercent);
		objectOutput.writeInt(currentPrivPercent);

		if (intervalName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(intervalName);
		}

		objectOutput.writeInt(publicDiff);
		objectOutput.writeInt(privateDiff);
		objectOutput.writeInt(totalDiff);
		objectOutput.writeInt(dateDiff);
	}

	public long videoStatisticId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long compareDate;
	public long totalVideos;
	public long publicVideos;
	public long privateVideos;
	public long currentTotal;
	public long currentPublic;
	public long currentPrivate;
	public int pubPercent;
	public int privPercent;
	public int currentPubPercent;
	public int currentPrivPercent;
	public String intervalName;
	public int publicDiff;
	public int privateDiff;
	public int totalDiff;
	public int dateDiff;
}
