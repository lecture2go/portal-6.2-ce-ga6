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

package de.uhh.l2g.plugins.service.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import de.uhh.l2g.plugins.service.AutocompleteLocalServiceUtil;
import de.uhh.l2g.plugins.service.CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstallWizardLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.LastvideolistLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LicenseLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.OaiRecordLocalServiceUtil;
import de.uhh.l2g.plugins.service.OaiRecord_OaiSetLocalServiceUtil;
import de.uhh.l2g.plugins.service.OaiSetLocalServiceUtil;
import de.uhh.l2g.plugins.service.OfficeLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.Producer_LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.ScheduledThreadLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.StatisticLocalServiceUtil;
import de.uhh.l2g.plugins.service.SysLocalServiceUtil;
import de.uhh.l2g.plugins.service.TagcloudLocalServiceUtil;
import de.uhh.l2g.plugins.service.TermLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_CreatorLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideohitlistLocalServiceUtil;

/**
 * @author Iavor Sturm
 */
public class ClpMessageListener extends BaseMessageListener {
	public static String getServletContextName() {
		return ClpSerializer.getServletContextName();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (command.equals("undeploy") &&
				servletContextName.equals(getServletContextName())) {
			AutocompleteLocalServiceUtil.clearService();

			CategoryLocalServiceUtil.clearService();

			CoordinatorLocalServiceUtil.clearService();

			CreatorLocalServiceUtil.clearService();

			HostLocalServiceUtil.clearService();

			InstallWizardLocalServiceUtil.clearService();

			InstitutionLocalServiceUtil.clearService();

			Institution_HostLocalServiceUtil.clearService();

			LastvideolistLocalServiceUtil.clearService();

			LectureseriesLocalServiceUtil.clearService();

			Lectureseries_CategoryLocalServiceUtil.clearService();

			Lectureseries_CreatorLocalServiceUtil.clearService();

			Lectureseries_InstitutionLocalServiceUtil.clearService();

			LicenseLocalServiceUtil.clearService();

			MetadataLocalServiceUtil.clearService();

			OaiRecordLocalServiceUtil.clearService();

			OaiRecord_OaiSetLocalServiceUtil.clearService();

			OaiSetLocalServiceUtil.clearService();

			OfficeLocalServiceUtil.clearService();

			ProducerLocalServiceUtil.clearService();

			Producer_LectureseriesLocalServiceUtil.clearService();

			ScheduledThreadLocalServiceUtil.clearService();

			SegmentLocalServiceUtil.clearService();

			StatisticLocalServiceUtil.clearService();

			SysLocalServiceUtil.clearService();

			TagcloudLocalServiceUtil.clearService();

			TermLocalServiceUtil.clearService();

			VideoLocalServiceUtil.clearService();

			Video_CategoryLocalServiceUtil.clearService();

			Video_CreatorLocalServiceUtil.clearService();

			Video_InstitutionLocalServiceUtil.clearService();

			VideohitlistLocalServiceUtil.clearService();
		}
	}
}