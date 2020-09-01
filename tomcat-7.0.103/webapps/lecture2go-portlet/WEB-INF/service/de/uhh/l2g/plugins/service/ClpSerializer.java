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

package de.uhh.l2g.plugins.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import de.uhh.l2g.plugins.model.AutocompleteClp;
import de.uhh.l2g.plugins.model.CategoryClp;
import de.uhh.l2g.plugins.model.CoordinatorClp;
import de.uhh.l2g.plugins.model.CreatorClp;
import de.uhh.l2g.plugins.model.HostClp;
import de.uhh.l2g.plugins.model.InstallWizardClp;
import de.uhh.l2g.plugins.model.InstitutionClp;
import de.uhh.l2g.plugins.model.Institution_HostClp;
import de.uhh.l2g.plugins.model.LastvideolistClp;
import de.uhh.l2g.plugins.model.LectureseriesClp;
import de.uhh.l2g.plugins.model.Lectureseries_CategoryClp;
import de.uhh.l2g.plugins.model.Lectureseries_CreatorClp;
import de.uhh.l2g.plugins.model.Lectureseries_InstitutionClp;
import de.uhh.l2g.plugins.model.LicenseClp;
import de.uhh.l2g.plugins.model.MetadataClp;
import de.uhh.l2g.plugins.model.OaiRecordClp;
import de.uhh.l2g.plugins.model.OaiRecord_OaiSetClp;
import de.uhh.l2g.plugins.model.OaiSetClp;
import de.uhh.l2g.plugins.model.OfficeClp;
import de.uhh.l2g.plugins.model.ProducerClp;
import de.uhh.l2g.plugins.model.Producer_LectureseriesClp;
import de.uhh.l2g.plugins.model.ScheduledThreadClp;
import de.uhh.l2g.plugins.model.SegmentClp;
import de.uhh.l2g.plugins.model.StatisticClp;
import de.uhh.l2g.plugins.model.SysClp;
import de.uhh.l2g.plugins.model.TagcloudClp;
import de.uhh.l2g.plugins.model.TermClp;
import de.uhh.l2g.plugins.model.VideoClp;
import de.uhh.l2g.plugins.model.Video_CategoryClp;
import de.uhh.l2g.plugins.model.Video_CreatorClp;
import de.uhh.l2g.plugins.model.Video_InstitutionClp;
import de.uhh.l2g.plugins.model.VideohitlistClp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iavor Sturm
 */
public class ClpSerializer {
	public static String getServletContextName() {
		if (Validator.isNotNull(_servletContextName)) {
			return _servletContextName;
		}

		synchronized (ClpSerializer.class) {
			if (Validator.isNotNull(_servletContextName)) {
				return _servletContextName;
			}

			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Class<?> portletPropsClass = classLoader.loadClass(
						"com.liferay.util.portlet.PortletProps");

				Method getMethod = portletPropsClass.getMethod("get",
						new Class<?>[] { String.class });

				String portletPropsServletContextName = (String)getMethod.invoke(null,
						"lecture2go-portlet-deployment-context");

				if (Validator.isNotNull(portletPropsServletContextName)) {
					_servletContextName = portletPropsServletContextName;
				}
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to locate deployment context from portlet properties");
				}
			}

			if (Validator.isNull(_servletContextName)) {
				try {
					String propsUtilServletContextName = PropsUtil.get(
							"lecture2go-portlet-deployment-context");

					if (Validator.isNotNull(propsUtilServletContextName)) {
						_servletContextName = propsUtilServletContextName;
					}
				}
				catch (Throwable t) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to locate deployment context from portal properties");
					}
				}
			}

			if (Validator.isNull(_servletContextName)) {
				_servletContextName = "lecture2go-portlet";
			}

			return _servletContextName;
		}
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(AutocompleteClp.class.getName())) {
			return translateInputAutocomplete(oldModel);
		}

		if (oldModelClassName.equals(CategoryClp.class.getName())) {
			return translateInputCategory(oldModel);
		}

		if (oldModelClassName.equals(CoordinatorClp.class.getName())) {
			return translateInputCoordinator(oldModel);
		}

		if (oldModelClassName.equals(CreatorClp.class.getName())) {
			return translateInputCreator(oldModel);
		}

		if (oldModelClassName.equals(HostClp.class.getName())) {
			return translateInputHost(oldModel);
		}

		if (oldModelClassName.equals(InstallWizardClp.class.getName())) {
			return translateInputInstallWizard(oldModel);
		}

		if (oldModelClassName.equals(InstitutionClp.class.getName())) {
			return translateInputInstitution(oldModel);
		}

		if (oldModelClassName.equals(Institution_HostClp.class.getName())) {
			return translateInputInstitution_Host(oldModel);
		}

		if (oldModelClassName.equals(LastvideolistClp.class.getName())) {
			return translateInputLastvideolist(oldModel);
		}

		if (oldModelClassName.equals(LectureseriesClp.class.getName())) {
			return translateInputLectureseries(oldModel);
		}

		if (oldModelClassName.equals(Lectureseries_CategoryClp.class.getName())) {
			return translateInputLectureseries_Category(oldModel);
		}

		if (oldModelClassName.equals(Lectureseries_CreatorClp.class.getName())) {
			return translateInputLectureseries_Creator(oldModel);
		}

		if (oldModelClassName.equals(
					Lectureseries_InstitutionClp.class.getName())) {
			return translateInputLectureseries_Institution(oldModel);
		}

		if (oldModelClassName.equals(LicenseClp.class.getName())) {
			return translateInputLicense(oldModel);
		}

		if (oldModelClassName.equals(MetadataClp.class.getName())) {
			return translateInputMetadata(oldModel);
		}

		if (oldModelClassName.equals(OaiRecordClp.class.getName())) {
			return translateInputOaiRecord(oldModel);
		}

		if (oldModelClassName.equals(OaiRecord_OaiSetClp.class.getName())) {
			return translateInputOaiRecord_OaiSet(oldModel);
		}

		if (oldModelClassName.equals(OaiSetClp.class.getName())) {
			return translateInputOaiSet(oldModel);
		}

		if (oldModelClassName.equals(OfficeClp.class.getName())) {
			return translateInputOffice(oldModel);
		}

		if (oldModelClassName.equals(ProducerClp.class.getName())) {
			return translateInputProducer(oldModel);
		}

		if (oldModelClassName.equals(Producer_LectureseriesClp.class.getName())) {
			return translateInputProducer_Lectureseries(oldModel);
		}

		if (oldModelClassName.equals(ScheduledThreadClp.class.getName())) {
			return translateInputScheduledThread(oldModel);
		}

		if (oldModelClassName.equals(SegmentClp.class.getName())) {
			return translateInputSegment(oldModel);
		}

		if (oldModelClassName.equals(StatisticClp.class.getName())) {
			return translateInputStatistic(oldModel);
		}

		if (oldModelClassName.equals(SysClp.class.getName())) {
			return translateInputSys(oldModel);
		}

		if (oldModelClassName.equals(TagcloudClp.class.getName())) {
			return translateInputTagcloud(oldModel);
		}

		if (oldModelClassName.equals(TermClp.class.getName())) {
			return translateInputTerm(oldModel);
		}

		if (oldModelClassName.equals(VideoClp.class.getName())) {
			return translateInputVideo(oldModel);
		}

		if (oldModelClassName.equals(Video_CategoryClp.class.getName())) {
			return translateInputVideo_Category(oldModel);
		}

		if (oldModelClassName.equals(Video_CreatorClp.class.getName())) {
			return translateInputVideo_Creator(oldModel);
		}

		if (oldModelClassName.equals(Video_InstitutionClp.class.getName())) {
			return translateInputVideo_Institution(oldModel);
		}

		if (oldModelClassName.equals(VideohitlistClp.class.getName())) {
			return translateInputVideohitlist(oldModel);
		}

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInputAutocomplete(BaseModel<?> oldModel) {
		AutocompleteClp oldClpModel = (AutocompleteClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getAutocompleteRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCategory(BaseModel<?> oldModel) {
		CategoryClp oldClpModel = (CategoryClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCategoryRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCoordinator(BaseModel<?> oldModel) {
		CoordinatorClp oldClpModel = (CoordinatorClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCoordinatorRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputCreator(BaseModel<?> oldModel) {
		CreatorClp oldClpModel = (CreatorClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getCreatorRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputHost(BaseModel<?> oldModel) {
		HostClp oldClpModel = (HostClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getHostRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputInstallWizard(BaseModel<?> oldModel) {
		InstallWizardClp oldClpModel = (InstallWizardClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getInstallWizardRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputInstitution(BaseModel<?> oldModel) {
		InstitutionClp oldClpModel = (InstitutionClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getInstitutionRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputInstitution_Host(BaseModel<?> oldModel) {
		Institution_HostClp oldClpModel = (Institution_HostClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getInstitution_HostRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLastvideolist(BaseModel<?> oldModel) {
		LastvideolistClp oldClpModel = (LastvideolistClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLastvideolistRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLectureseries(BaseModel<?> oldModel) {
		LectureseriesClp oldClpModel = (LectureseriesClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLectureseriesRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLectureseries_Category(
		BaseModel<?> oldModel) {
		Lectureseries_CategoryClp oldClpModel = (Lectureseries_CategoryClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLectureseries_CategoryRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLectureseries_Creator(
		BaseModel<?> oldModel) {
		Lectureseries_CreatorClp oldClpModel = (Lectureseries_CreatorClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLectureseries_CreatorRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLectureseries_Institution(
		BaseModel<?> oldModel) {
		Lectureseries_InstitutionClp oldClpModel = (Lectureseries_InstitutionClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLectureseries_InstitutionRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputLicense(BaseModel<?> oldModel) {
		LicenseClp oldClpModel = (LicenseClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getLicenseRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputMetadata(BaseModel<?> oldModel) {
		MetadataClp oldClpModel = (MetadataClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getMetadataRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputOaiRecord(BaseModel<?> oldModel) {
		OaiRecordClp oldClpModel = (OaiRecordClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getOaiRecordRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputOaiRecord_OaiSet(BaseModel<?> oldModel) {
		OaiRecord_OaiSetClp oldClpModel = (OaiRecord_OaiSetClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getOaiRecord_OaiSetRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputOaiSet(BaseModel<?> oldModel) {
		OaiSetClp oldClpModel = (OaiSetClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getOaiSetRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputOffice(BaseModel<?> oldModel) {
		OfficeClp oldClpModel = (OfficeClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getOfficeRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputProducer(BaseModel<?> oldModel) {
		ProducerClp oldClpModel = (ProducerClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getProducerRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputProducer_Lectureseries(
		BaseModel<?> oldModel) {
		Producer_LectureseriesClp oldClpModel = (Producer_LectureseriesClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getProducer_LectureseriesRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputScheduledThread(BaseModel<?> oldModel) {
		ScheduledThreadClp oldClpModel = (ScheduledThreadClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getScheduledThreadRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSegment(BaseModel<?> oldModel) {
		SegmentClp oldClpModel = (SegmentClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSegmentRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputStatistic(BaseModel<?> oldModel) {
		StatisticClp oldClpModel = (StatisticClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getStatisticRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSys(BaseModel<?> oldModel) {
		SysClp oldClpModel = (SysClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSysRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputTagcloud(BaseModel<?> oldModel) {
		TagcloudClp oldClpModel = (TagcloudClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getTagcloudRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputTerm(BaseModel<?> oldModel) {
		TermClp oldClpModel = (TermClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getTermRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputVideo(BaseModel<?> oldModel) {
		VideoClp oldClpModel = (VideoClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getVideoRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputVideo_Category(BaseModel<?> oldModel) {
		Video_CategoryClp oldClpModel = (Video_CategoryClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getVideo_CategoryRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputVideo_Creator(BaseModel<?> oldModel) {
		Video_CreatorClp oldClpModel = (Video_CreatorClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getVideo_CreatorRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputVideo_Institution(BaseModel<?> oldModel) {
		Video_InstitutionClp oldClpModel = (Video_InstitutionClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getVideo_InstitutionRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputVideohitlist(BaseModel<?> oldModel) {
		VideohitlistClp oldClpModel = (VideohitlistClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getVideohitlistRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.AutocompleteImpl")) {
			return translateOutputAutocomplete(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.CategoryImpl")) {
			return translateOutputCategory(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.CoordinatorImpl")) {
			return translateOutputCoordinator(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.CreatorImpl")) {
			return translateOutputCreator(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.HostImpl")) {
			return translateOutputHost(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.InstallWizardImpl")) {
			return translateOutputInstallWizard(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.InstitutionImpl")) {
			return translateOutputInstitution(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Institution_HostImpl")) {
			return translateOutputInstitution_Host(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.LastvideolistImpl")) {
			return translateOutputLastvideolist(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.LectureseriesImpl")) {
			return translateOutputLectureseries(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Lectureseries_CategoryImpl")) {
			return translateOutputLectureseries_Category(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Lectureseries_CreatorImpl")) {
			return translateOutputLectureseries_Creator(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Lectureseries_InstitutionImpl")) {
			return translateOutputLectureseries_Institution(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.LicenseImpl")) {
			return translateOutputLicense(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.MetadataImpl")) {
			return translateOutputMetadata(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.OaiRecordImpl")) {
			return translateOutputOaiRecord(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.OaiRecord_OaiSetImpl")) {
			return translateOutputOaiRecord_OaiSet(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.OaiSetImpl")) {
			return translateOutputOaiSet(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.OfficeImpl")) {
			return translateOutputOffice(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.ProducerImpl")) {
			return translateOutputProducer(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Producer_LectureseriesImpl")) {
			return translateOutputProducer_Lectureseries(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.ScheduledThreadImpl")) {
			return translateOutputScheduledThread(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.SegmentImpl")) {
			return translateOutputSegment(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.StatisticImpl")) {
			return translateOutputStatistic(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.SysImpl")) {
			return translateOutputSys(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.TagcloudImpl")) {
			return translateOutputTagcloud(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.TermImpl")) {
			return translateOutputTerm(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals("de.uhh.l2g.plugins.model.impl.VideoImpl")) {
			return translateOutputVideo(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Video_CategoryImpl")) {
			return translateOutputVideo_Category(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Video_CreatorImpl")) {
			return translateOutputVideo_Creator(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.Video_InstitutionImpl")) {
			return translateOutputVideo_Institution(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		if (oldModelClassName.equals(
					"de.uhh.l2g.plugins.model.impl.VideohitlistImpl")) {
			return translateOutputVideohitlist(oldModel);
		}
		else if (oldModelClassName.endsWith("Clp")) {
			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Method getClpSerializerClassMethod = oldModelClass.getMethod(
						"getClpSerializerClass");

				Class<?> oldClpSerializerClass = (Class<?>)getClpSerializerClassMethod.invoke(oldModel);

				Class<?> newClpSerializerClass = classLoader.loadClass(oldClpSerializerClass.getName());

				Method translateOutputMethod = newClpSerializerClass.getMethod("translateOutput",
						BaseModel.class);

				Class<?> oldModelModelClass = oldModel.getModelClass();

				Method getRemoteModelMethod = oldModelClass.getMethod("get" +
						oldModelModelClass.getSimpleName() + "RemoteModel");

				Object oldRemoteModel = getRemoteModelMethod.invoke(oldModel);

				BaseModel<?> newModel = (BaseModel<?>)translateOutputMethod.invoke(null,
						oldRemoteModel);

				return newModel;
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info("Unable to translate " + oldModelClassName, t);
				}
			}
		}

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Throwable translateThrowable(Throwable throwable) {
		if (_useReflectionToTranslateThrowable) {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsyncByteArrayOutputStream);

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
				objectOutputStream.close();

				UnsyncByteArrayInputStream unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(unsyncByteArrayOutputStream.unsafeGetByteArray(),
						0, unsyncByteArrayOutputStream.size());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				ObjectInputStream objectInputStream = new ClassLoaderObjectInputStream(unsyncByteArrayInputStream,
						contextClassLoader);

				throwable = (Throwable)objectInputStream.readObject();

				objectInputStream.close();

				return throwable;
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (Throwable throwable2) {
				_log.error(throwable2, throwable2);

				return throwable2;
			}
		}

		Class<?> clazz = throwable.getClass();

		String className = clazz.getName();

		if (className.equals(PortalException.class.getName())) {
			return new PortalException();
		}

		if (className.equals(SystemException.class.getName())) {
			return new SystemException();
		}

		if (className.equals("de.uhh.l2g.plugins.HostNameException")) {
			return new de.uhh.l2g.plugins.HostNameException();
		}

		if (className.equals("de.uhh.l2g.plugins.HostStreamerException")) {
			return new de.uhh.l2g.plugins.HostStreamerException();
		}

		if (className.equals("de.uhh.l2g.plugins.InstitutionNameException")) {
			return new de.uhh.l2g.plugins.InstitutionNameException();
		}

		if (className.equals("de.uhh.l2g.plugins.Institution_HostHostException")) {
			return new de.uhh.l2g.plugins.Institution_HostHostException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.Institution_HostInstitutionException")) {
			return new de.uhh.l2g.plugins.Institution_HostInstitutionException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoPropertyException")) {
			return new de.uhh.l2g.plugins.NoPropertyException();
		}

		if (className.equals("de.uhh.l2g.plugins.StatisticDateException")) {
			return new de.uhh.l2g.plugins.StatisticDateException();
		}

		if (className.equals("de.uhh.l2g.plugins.StatisticValueException")) {
			return new de.uhh.l2g.plugins.StatisticValueException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchAutocompleteException")) {
			return new de.uhh.l2g.plugins.NoSuchAutocompleteException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchCategoryException")) {
			return new de.uhh.l2g.plugins.NoSuchCategoryException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchCoordinatorException")) {
			return new de.uhh.l2g.plugins.NoSuchCoordinatorException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchCreatorException")) {
			return new de.uhh.l2g.plugins.NoSuchCreatorException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchHostException")) {
			return new de.uhh.l2g.plugins.NoSuchHostException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchInstallWizardException")) {
			return new de.uhh.l2g.plugins.NoSuchInstallWizardException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchInstitutionException")) {
			return new de.uhh.l2g.plugins.NoSuchInstitutionException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchInstitution_HostException")) {
			return new de.uhh.l2g.plugins.NoSuchInstitution_HostException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchLastvideolistException")) {
			return new de.uhh.l2g.plugins.NoSuchLastvideolistException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchLectureseriesException")) {
			return new de.uhh.l2g.plugins.NoSuchLectureseriesException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchLectureseries_CategoryException")) {
			return new de.uhh.l2g.plugins.NoSuchLectureseries_CategoryException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchLectureseries_CreatorException")) {
			return new de.uhh.l2g.plugins.NoSuchLectureseries_CreatorException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchLectureseries_InstitutionException")) {
			return new de.uhh.l2g.plugins.NoSuchLectureseries_InstitutionException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchLicenseException")) {
			return new de.uhh.l2g.plugins.NoSuchLicenseException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchMetadataException")) {
			return new de.uhh.l2g.plugins.NoSuchMetadataException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchOaiRecordException")) {
			return new de.uhh.l2g.plugins.NoSuchOaiRecordException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchOaiRecord_OaiSetException")) {
			return new de.uhh.l2g.plugins.NoSuchOaiRecord_OaiSetException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchOaiSetException")) {
			return new de.uhh.l2g.plugins.NoSuchOaiSetException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchOfficeException")) {
			return new de.uhh.l2g.plugins.NoSuchOfficeException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchProducerException")) {
			return new de.uhh.l2g.plugins.NoSuchProducerException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchProducer_LectureseriesException")) {
			return new de.uhh.l2g.plugins.NoSuchProducer_LectureseriesException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchScheduledThreadException")) {
			return new de.uhh.l2g.plugins.NoSuchScheduledThreadException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchSegmentException")) {
			return new de.uhh.l2g.plugins.NoSuchSegmentException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchStatisticException")) {
			return new de.uhh.l2g.plugins.NoSuchStatisticException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchSysException")) {
			return new de.uhh.l2g.plugins.NoSuchSysException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchTagcloudException")) {
			return new de.uhh.l2g.plugins.NoSuchTagcloudException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchTermException")) {
			return new de.uhh.l2g.plugins.NoSuchTermException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchVideoException")) {
			return new de.uhh.l2g.plugins.NoSuchVideoException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchVideo_CategoryException")) {
			return new de.uhh.l2g.plugins.NoSuchVideo_CategoryException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchVideo_CreatorException")) {
			return new de.uhh.l2g.plugins.NoSuchVideo_CreatorException();
		}

		if (className.equals(
					"de.uhh.l2g.plugins.NoSuchVideo_InstitutionException")) {
			return new de.uhh.l2g.plugins.NoSuchVideo_InstitutionException();
		}

		if (className.equals("de.uhh.l2g.plugins.NoSuchVideohitlistException")) {
			return new de.uhh.l2g.plugins.NoSuchVideohitlistException();
		}

		return throwable;
	}

	public static Object translateOutputAutocomplete(BaseModel<?> oldModel) {
		AutocompleteClp newModel = new AutocompleteClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setAutocompleteRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCategory(BaseModel<?> oldModel) {
		CategoryClp newModel = new CategoryClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCategoryRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCoordinator(BaseModel<?> oldModel) {
		CoordinatorClp newModel = new CoordinatorClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCoordinatorRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputCreator(BaseModel<?> oldModel) {
		CreatorClp newModel = new CreatorClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setCreatorRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputHost(BaseModel<?> oldModel) {
		HostClp newModel = new HostClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setHostRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputInstallWizard(BaseModel<?> oldModel) {
		InstallWizardClp newModel = new InstallWizardClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setInstallWizardRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputInstitution(BaseModel<?> oldModel) {
		InstitutionClp newModel = new InstitutionClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setInstitutionRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputInstitution_Host(BaseModel<?> oldModel) {
		Institution_HostClp newModel = new Institution_HostClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setInstitution_HostRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLastvideolist(BaseModel<?> oldModel) {
		LastvideolistClp newModel = new LastvideolistClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLastvideolistRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLectureseries(BaseModel<?> oldModel) {
		LectureseriesClp newModel = new LectureseriesClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLectureseriesRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLectureseries_Category(
		BaseModel<?> oldModel) {
		Lectureseries_CategoryClp newModel = new Lectureseries_CategoryClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLectureseries_CategoryRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLectureseries_Creator(
		BaseModel<?> oldModel) {
		Lectureseries_CreatorClp newModel = new Lectureseries_CreatorClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLectureseries_CreatorRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLectureseries_Institution(
		BaseModel<?> oldModel) {
		Lectureseries_InstitutionClp newModel = new Lectureseries_InstitutionClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLectureseries_InstitutionRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputLicense(BaseModel<?> oldModel) {
		LicenseClp newModel = new LicenseClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setLicenseRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputMetadata(BaseModel<?> oldModel) {
		MetadataClp newModel = new MetadataClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setMetadataRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputOaiRecord(BaseModel<?> oldModel) {
		OaiRecordClp newModel = new OaiRecordClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setOaiRecordRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputOaiRecord_OaiSet(BaseModel<?> oldModel) {
		OaiRecord_OaiSetClp newModel = new OaiRecord_OaiSetClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setOaiRecord_OaiSetRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputOaiSet(BaseModel<?> oldModel) {
		OaiSetClp newModel = new OaiSetClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setOaiSetRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputOffice(BaseModel<?> oldModel) {
		OfficeClp newModel = new OfficeClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setOfficeRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputProducer(BaseModel<?> oldModel) {
		ProducerClp newModel = new ProducerClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setProducerRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputProducer_Lectureseries(
		BaseModel<?> oldModel) {
		Producer_LectureseriesClp newModel = new Producer_LectureseriesClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setProducer_LectureseriesRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputScheduledThread(BaseModel<?> oldModel) {
		ScheduledThreadClp newModel = new ScheduledThreadClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setScheduledThreadRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSegment(BaseModel<?> oldModel) {
		SegmentClp newModel = new SegmentClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSegmentRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputStatistic(BaseModel<?> oldModel) {
		StatisticClp newModel = new StatisticClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setStatisticRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSys(BaseModel<?> oldModel) {
		SysClp newModel = new SysClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSysRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputTagcloud(BaseModel<?> oldModel) {
		TagcloudClp newModel = new TagcloudClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setTagcloudRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputTerm(BaseModel<?> oldModel) {
		TermClp newModel = new TermClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setTermRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputVideo(BaseModel<?> oldModel) {
		VideoClp newModel = new VideoClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setVideoRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputVideo_Category(BaseModel<?> oldModel) {
		Video_CategoryClp newModel = new Video_CategoryClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setVideo_CategoryRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputVideo_Creator(BaseModel<?> oldModel) {
		Video_CreatorClp newModel = new Video_CreatorClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setVideo_CreatorRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputVideo_Institution(BaseModel<?> oldModel) {
		Video_InstitutionClp newModel = new Video_InstitutionClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setVideo_InstitutionRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputVideohitlist(BaseModel<?> oldModel) {
		VideohitlistClp newModel = new VideohitlistClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setVideohitlistRemoteModel(oldModel);

		return newModel;
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);
	private static String _servletContextName;
	private static boolean _useReflectionToTranslateThrowable = true;
}