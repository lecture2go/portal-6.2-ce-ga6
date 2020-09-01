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
package de.uhh.l2g.plugins.util;

/***************************************************************************
 * The Lecture2Go software is based on the liferay portal 6.1.1
 * <http://www.liferay.com>
 *
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 *
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 *
 * @Autor Lecture2Go Team
 * @Version 1.0
 * @Contact lecture2go-open-source@uni-hamburg.de
 *
 * Copyright (c) 2013 University of Hamburg / Computer and Data Center (RRZ)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ***************************************************************************/

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.ReceiverKey;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.uhh.l2g.plugins.service.ScheduledThreadLocalServiceUtil;

/** There is no built-in time based scheduler in Java, though Quartz Extension is build-in for liferay 6.2
 * (https://quartz-scheduler.org/)
 * 
 * It setting work pretty much like cron and are configured in liferay-portlet.xml
 * http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
 * 
 * Unfortunately Liferay Scheduler Configuration comes without start/stop/pause/resume functionality
 * This Class uses the Liferay Quartz Scheduler as specified within <scheduler-entry> 
 * and adds start/stop functionality to it until server restart
 */
/**
 * The Class PortletScheduler.
 */
@SuppressWarnings("serial")
public class PortletScheduler extends SchedulerResponse implements MessageListener {

	// Liferay Log
	protected static Log LOG;

	protected String destination;
	protected String receiverKey;
	protected int exceptionsMaxSize;

	// Assumed as Constants (requires further implementation for dynamics)
	protected final String DEST = DestinationNames.SCHEDULER_DISPATCH;
	protected final StorageType STOR = StorageType.MEMORY_CLUSTERED;
	protected String initialCron;

	// This has to be kept unique
	protected SchedulerEntry schedulerEntry;

	// The timing
	protected Trigger generalTrigger;
	protected TriggerState triggerState;

	// These have to be set if invoked by general constructor
	protected String schedulerClassName;
	protected String portletId;

	public String getSchedulerClassName() {
		return schedulerClassName;
	}

	public void setSchedulerClassName(String schedulerClassName) {
		this.schedulerClassName = schedulerClassName;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	/**
	 * Create empty scheduler for ClassLoader
	 * 
	 */
	public PortletScheduler() {
		this.schedulerClassName = PortletScheduler.class.getName();
		PortletScheduler.LOG = LogFactoryUtil.getLog(PortletScheduler.class.getName());

	}

	/**
	 * Init Scheduler Entry and Message by gathering information from PortletId
	 * and SchedulerEngine
	 * 
	 * 
	 * @param schedulerClassName
	 * @param portletId
	 */
	public PortletScheduler(String schedulerClassName, ServiceContext serviceContext) {
		this.schedulerClassName = schedulerClassName;
		LOG = LogFactoryUtil.getLog(schedulerClassName);
		initScheduler(schedulerClassName, serviceContext);
	}

	/**
	 * Reads Scheduler entries from Portlet Preferences
	 * 
	 * WARN: This will disable Multiple Quartz Jobs (Triggers) per Listener Feature
	 * 
	 * @param schedulerClassName
	 * @param portletId
	 */
	public void initScheduler(String schedulerClassName, ServiceContext serviceContext) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(serviceContext.getPortletId());
		List<SchedulerEntry> jobs = new LinkedList<SchedulerEntry>();
		List<SchedulerEntry> duplicates = new LinkedList<SchedulerEntry>();

		LOG.info("Initializing schedulers for " + schedulerClassName + " " + portletId);

		this.schedulerClassName = schedulerClassName;
		this.portletId = serviceContext.getPortletId();

		// Load Quartz Job (Unique for ClassName!)
		assembleMessageFromResponses();

		// Fetch List of Schedulers started on portlet initialization (maybe we
		// do have a Trigger,...)
		jobs = portlet.getSchedulerEntries();
		for (SchedulerEntry job : jobs) {
			// Grab first scheduler associated with given Class
			if (job.getEventListenerClass().equalsIgnoreCase(this.schedulerClassName)) {
				if (this.schedulerEntry == null) {
					LOG.info(job.toString());
					this.schedulerEntry = job;
					// Get Trigger
					try {
						this.generalTrigger = job.getTrigger();
						if (this.generalTrigger != null) {
							// Try persisting our Trigger in DB
							String currentCron = GetterUtil.getString(this.generalTrigger.getTriggerContent().toString());
							this.triggerState = SchedulerEngineHelperUtil.getJobState(job.getEventListenerClass(), job.getEventListenerClass(), this.getStorageType());
							String persistCron = ScheduledThreadLocalServiceUtil.getCronBySchedulerName(job.getEventListenerClass());
							if (persistCron == null || persistCron.isEmpty())
								ScheduledThreadLocalServiceUtil.addScheduledThread(schedulerClassName, currentCron, serviceContext);
							// Overwrite if we encounter new value (assume
							// PortletScheduler Names are unique)
							if (!persistCron.equals(currentCron) && !currentCron.isEmpty()) ScheduledThreadLocalServiceUtil.updateScheduledThread(schedulerClassName, currentCron, serviceContext);
						}
					} catch (SchedulerException t) {
						LOG.warn(t);
					} catch (SystemException e) {
						LOG.warn("Failed Loading Persitence - Recovery of Triggers will fail", e);
					} catch (PortalException e) {
						LOG.warn("", e);
					}
				} else { // Collect duplicate SchedulerEntries
					LOG.warn("Multiple runnging Jobs found for same Scheduled Task! Removing ... " + job);
					duplicates.add(job);
				}
			}
		}
		// remove duplicates
		jobs.removeAll(duplicates);

		// Check if we got what we've been looking for
		if (schedulerEntry == null) {
			LOG.warn("No SchedulerEntry found for this Job. Generating new Entry...");
			assembleEntryFromSettings(portletId, this.triggerState);
		}

	}

	/**
	 * Assembles (adds and schedules) an missing SchedulerEntry
	 * 
	 * @return SchedulerEntry
	 */
	public SchedulerEntry assembleEntryFromSettings(String portletId, TriggerState state) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
		SchedulerEntry entry = new SchedulerEntryImpl();
		String cron = "";
		try {
			cron = GetterUtil.getString(ScheduledThreadLocalServiceUtil.getCronBySchedulerName(this.schedulerClassName));
			// TODO: Persist Description String description;
			this.generalTrigger = new CronTrigger(this.schedulerClassName, this.schedulerClassName, cron);
			LOG.info("Building Trigger: " + cron);
			entry.setDescription(this.schedulerClassName);
			entry.setEventListenerClass(this.schedulerClassName);
			entry.setTriggerType(TriggerType.CRON);
			entry.setTriggerValue(cron);

			this.schedulerEntry = entry;

		} catch (Exception e) {
			LOG.error("Scheduler CronText was lost. Requires server restart to recover.");
		}
		// Add and schedule if it is supposed to be running
		if (state != TriggerState.UNSCHEDULED) {
			portlet.getSchedulerEntries().add(entry);
			// , description=This scheduler is used to collect Statistical Data
			// from Database,
			// eventListenerClass=de.uhh.l2g.plugins.util.StatisticsScheduler,
			// propertyKey=, timeUnit=null, trigger={cronText=0 */1 * * * ?,
			// {endDate=null,
			// groupName=de.uhh.l2g.plugins.util.StatisticsScheduler,
			// jobName=de.uhh.l2g.plugins.util.StatisticsScheduler,
			// startDate=null, triggerType=CRON}}, triggerType=CRON,
			// triggerValue=0 */1 * * * ?}
			try {
				SchedulerEngineHelperUtil.schedule(entry, this.STOR, portletId, 0);
			} catch (SchedulerException e) {
				LOG.error("Failed adding Scheduler!");
			}
		}
		return this.schedulerEntry;

	}

	/**
	 * Crowds this Scheduler with ResponseMessage Data Unique iff (jobName ==
	 * schedulerClassName == groupName && storageType == STOR)
	 * 
	 * @return PortletScheduler
	 */
	public PortletScheduler assembleMessageFromResponses() {

		// These should be unique if everything is configured by liferay default
		try {
			List<SchedulerResponse> scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
			for (SchedulerResponse resp : scheduledJobs) {
				if (resp.getJobName().equalsIgnoreCase(this.schedulerClassName)) {
					this.setJobName(resp.getJobName());
					this.setGroupName(resp.getGroupName());
					this.setStorageType(resp.getStorageType());
					this.setDescription(resp.getDescription());
					this.setMessage(resp.getMessage());
					this.setTrigger(resp.getTrigger());

					if (this.getMessage() == null)
						this.setMessage(new Message());
					LOG.debug("Job found. Copy Portlet Information: " + this.getMessage().toString());
					Map<String, Object> map = this.getMessage().getValues();

					// Fill this message with portlet specific data
					if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME)) this.getMessage().put(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME, map.get(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME).toString());
					if (map.containsKey(SchedulerEngine.PORTLET_ID)) this.getMessage().put(SchedulerEngine.PORTLET_ID, map.get(SchedulerEngine.PORTLET_ID).toString());
					if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_UUID)) this.getMessage().put(SchedulerEngine.MESSAGE_LISTENER_UUID, map.get(SchedulerEngine.MESSAGE_LISTENER_UUID).toString());

					LOG.debug(this.getMessage().get(SchedulerEngine.DESTINATION_NAME) + " " + this.getMessage().getDestinationName() + " " + this.getMessage().getValues().get(SchedulerEngine.DESTINATION_NAME) + " " + this.getMessage().getDestinationName());

					if (map.containsKey("MESSAGE_LISTENER_CLASS_NAME") && map.get("DESTINATION_NAME") != null) {
						this.destination = map.get("DESTINATION_NAME").toString();
						// this.getMessage().put(SchedulerEngine.DESTINATION_NAME,
						// map.get("DESTINATION_NAME").toString());
						this.getMessage().getValues().put(SchedulerEngine.DESTINATION_NAME, map.get("DESTINATION_NAME").toString());
						this.getMessage().setDestinationName(this.getMessage().getValues().get(SchedulerEngine.DESTINATION_NAME).toString());
					} else {
						LOG.debug("Destination not available yet. Setting to " + DEST);
						this.destination = DEST;
						// this.getMessage().put(SchedulerEngine.DESTINATION_NAME,
						// DEST);
						this.getMessage().getValues().put(SchedulerEngine.DESTINATION_NAME, DEST);
						this.getMessage().setDestinationName(this.getMessage().getValues().get(SchedulerEngine.DESTINATION_NAME).toString());
					}
					// new ReceiverKey(this.getJobName(), this.getGroupName())
					this.getMessage().put(SchedulerEngine.RECEIVER_KEY, GetterUtil.getString(new ReceiverKey(this.getJobName(), this.getGroupName())));
					LOG.debug("ReceiverKey: " + new ReceiverKey(this.getJobName(), this.getGroupName()));
					TriggerState state = SchedulerEngineHelperUtil.getJobState(this.getJobName(), this.getGroupName(), this.getStorageType());
					LOG.debug(state);
				}
			}
		} catch (SchedulerException e) {
			LOG.warn(e);
		}
		return this;
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		String values = "No values";
		// Debug Information on running job
		if (message != null) {
			LOG.info("Message :" + message.toString());
			Thread thread = Thread.currentThread();
			LOG.info("Thread :" + thread.getContextClassLoader());
			LOG.info("Thread :" + thread.toString());
			Map<String, Object> map = message.getValues();
			LOG.info(message.get(SchedulerEngine.DESTINATION_NAME) + " " + message.getDestinationName() + " " + message.getValues().get(SchedulerEngine.DESTINATION_NAME) + " " + message.getDestinationName());
			values = map.toString();
		}
		LOG.info("Portlet Scheduler running... " + values);
	}

	public void pause() throws SchedulerException {
		LOG.info("Pausing... :" + GetterUtil.getString(this.schedulerClassName));
		SchedulerEngineHelperUtil.pause(this.schedulerClassName, this.schedulerClassName, this.getStorageType());
	}

	public void resume() throws SchedulerException {
		LOG.info("Resuming... :" + GetterUtil.getString(this.schedulerClassName));
		SchedulerEngineHelperUtil.resume(this.schedulerClassName, this.schedulerClassName, this.getStorageType());
	}

	public void update() throws SchedulerException {
		LOG.info("Updating... :" + GetterUtil.getString(this.schedulerClassName));
		SchedulerEngineHelperUtil.update(this.getTrigger(), this.getStorageType());
	}



	/**
	 * Schedule unscheduled Scheduler (requires valid SchedulerEntry Data &&
	 * Message Date)
	 * 
	 * There is no Issue in Liferay Tracker about this: Manually assembling.
	 * Message, Trigger and Job from previously "working" Job, in order to use
	 * 
	 * SchedulerEngineHelperUtil.schedule(Trigger, StorageType, Description,
	 * Destination, Message, exceptionsMaxSize);
	 * 
	 * Currently (6.2 GA 4 - GA 6) also fails to register the MessageListener
	 * correctly for a single consumer. This might be a configuration detail
	 * issue, which is not documented in liferays SchedulerEngineHelperUtil, at
	 * first sight source investigation does not reveal conclusive hints.
	 * 
	 * Though the more complicated issue is that Quartz does persist the Job on
	 * UNSCHEDULE but not the Trigger.
	 * 
	 * Therefore a SchedulerEntry must be available in Memory, to schedule an
	 * unscheduled Job Liferay SchedulerEntry however does persit the Trigger...
	 * but does not check for duplicates on deploy.
	 * 
	 * If we keep the SchedulerEntry in the List we have the Trigger but
	 * unregister fails and duplicates the Scheduler If we remove the
	 * SchedulerEntry unregister works correctly but we lose the Trigger
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws SchedulerException
	 * @throws Exception
	 * @throws InstantiationException
	 *             *
	 *

	public void start() {
		int exceptionsMaxSize = 0;
		try {
			String messageText = GetterUtil.getString((this.getMessage().toString()));
			LOG.info("Starting :" + messageText);

			Map<String, Object> map = this.getMessage().getValues();

			String portletId = "";
			String listenerName = "";

			if (map.containsKey(SchedulerEngine.PORTLET_ID))
				portletId = map.get(SchedulerEngine.PORTLET_ID).toString();
			if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME))
				listenerName = map.get(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME).toString();
			if (map.containsKey(SchedulerEngine.EXCEPTIONS_MAX_SIZE))
				exceptionsMaxSize = Integer.valueOf(map.get(SchedulerEngine.EXCEPTIONS_MAX_SIZE).toString());

			LOG.info("Message :" + portletId + " " + listenerName + " " + this.destination);

			if (this.getTrigger() == null) this.setTrigger(this.generalTrigger);
			TriggerState state = SchedulerEngineHelperUtil.getJobState(this.getJobName(), this.getGroupName(), this.getStorageType());
			LOG.info("Trigger :" + GetterUtil.getString(this.getTrigger().toString()) + state);
			if (state != null && state.equals(TriggerState.UNSCHEDULED)) {
				this.getMessage().put(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME, this.schedulerClassName);
				this.getMessage().setDestinationName(this.DEST);
				this.getMessage().put(SchedulerEngine.RECEIVER_KEY, this.getMessage().getValues().get(SchedulerEngine.RECEIVER_KEY).toString());
				LOG.info("Scheduling :" + this.schedulerClassName + " ");
				LOG.info("New Message " + this.getMessage().toString());
				Thread thread = Thread.currentThread();
				LOG.info("Thread :" + thread.getContextClassLoader() + thread.toString());
				if (this.schedulerEntry != null && this.getTrigger() != null) SchedulerEngineHelperUtil.schedule(schedulerEntry, this.getStorageType(), portletId, exceptionsMaxSize);
				else assembleEntryFromSettings(this.schedulerClassName, TriggerState.NORMAL);
			} else {
				if (state == null) LOG.error("Could not find Job with Name: " + this.schedulerClassName);
				else LOG.info("Could not schedule Job with State: " + state);
			}

		} catch (SchedulerException e) {
			LOG.warn("", e);
		} catch (IllegalArgumentException e) {
			LOG.warn(e);
		}
	} */
	
	/**
	 * Schedule unscheduled Scheduler (requires valid SchedulerEntry Data &&
	 * Message Date)
	 * 
	 * There is no Issue in Liferay Tracker about this: Manually assembling.
	 * Message, Trigger and Job from previously "working" Job, in order to use
	 * 
	 * SchedulerEngineHelperUtil.schedule(Trigger, StorageType, Description,
	 * Destination, Message, exceptionsMaxSize);
	 * 
	 * Currently (6.2 GA 4 - GA 6) also fails to register the MessageListener
	 * correctly for a single consumer. This might be a configuration detail
	 * issue, which is not documented in liferays SchedulerEngineHelperUtil, at
	 * first sight source investigation does not reveal conclusive hints.
	 * 
	 * Though the more complicated issue is that Quartz does persist the Job on
	 * UNSCHEDULE but not the Trigger.
	 * 
	 * Therefore a SchedulerEntry must be available in Memory, to schedule an
	 * unscheduled Job Liferay SchedulerEntry however does persit the Trigger...
	 * but does not check for duplicates on deploy.
	 * 
	 * If we keep the SchedulerEntry in the List we have the Trigger but
	 * unregister fails and duplicates the Scheduler If we remove the
	 * SchedulerEntry unregister works correctly but we lose the Trigger
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws SchedulerException
	 * @throws Exception
	 * @throws InstantiationException
	 *             *
	 */
	public void schedule() {
		int exceptionsMaxSize = 0;
		try {
			String messageText = GetterUtil.getString((this.getMessage().toString()));
			LOG.info("Starting :" + messageText);

			Map<String, Object> map = this.getMessage().getValues();

			String portletId = "";
			String listenerName = "";

			if (map.containsKey(SchedulerEngine.PORTLET_ID))
				portletId = map.get(SchedulerEngine.PORTLET_ID).toString();
			if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME))
				listenerName = map.get(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME).toString();
			if (map.containsKey(SchedulerEngine.EXCEPTIONS_MAX_SIZE))
				exceptionsMaxSize = Integer.valueOf(map.get(SchedulerEngine.EXCEPTIONS_MAX_SIZE).toString());

			LOG.info("Message :" + portletId + " " + listenerName + " " + this.destination);

			if (this.getTrigger() == null) this.setTrigger(this.generalTrigger);
			TriggerState state = SchedulerEngineHelperUtil.getJobState(this.getJobName(), this.getGroupName(), this.getStorageType());
			LOG.info("Trigger :" + GetterUtil.getString(this.getTrigger().toString()) + state);
			if (state != null && state.equals(TriggerState.UNSCHEDULED)) {
				this.getMessage().put(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME, this.schedulerClassName);
				this.getMessage().setDestinationName(this.DEST);
				this.getMessage().put(SchedulerEngine.RECEIVER_KEY, this.getMessage().getValues().get(SchedulerEngine.RECEIVER_KEY).toString());
				LOG.info("Scheduling :" + this.schedulerClassName + " ");
				LOG.info("New Message " + this.getMessage().toString());
				Thread thread = Thread.currentThread();
				LOG.info("Thread :" + thread.getContextClassLoader() + thread.toString());
				if (this.schedulerEntry != null && this.getTrigger() != null) SchedulerEngineHelperUtil.schedule(schedulerEntry, this.getStorageType(), portletId, exceptionsMaxSize);
				else assembleEntryFromSettings(this.schedulerClassName, TriggerState.NORMAL);
			} else {
				if (state == null) LOG.error("Could not find Job with Name: " + this.schedulerClassName);
				else LOG.info("Could not schedule Job with State: " + state);
			}

		} catch (SchedulerException e) {
			LOG.warn("", e);
		} catch (IllegalArgumentException e) {
			LOG.warn(e);
		}
	}

	/**
	 * Unschedules Quartz Job but keeps SchedulerEntry in Memory (Scheduler will
	 * be not correctly (re)deployed if in state UNSCHEDULED
	 * 
	 */
	public void unschedule() {
		try {
			List<SchedulerResponse> scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
			for (SchedulerResponse resp : scheduledJobs) {
				if (resp.getJobName().equalsIgnoreCase(this.schedulerClassName)) {

					LOG.info("Stopping :" + resp.toString());
					Map<String, Object> map = resp.getMessage().getValues();

					String portletId = "";
					String listenerName = "";

					if (map.containsKey(SchedulerEngine.PORTLET_ID)) portletId = map.get(SchedulerEngine.PORTLET_ID).toString();
					if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME)) listenerName = map.get(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME).toString();

					LOG.info("Associated to :" + portletId + " " + listenerName + " " + this.destination);

					TriggerState state = SchedulerEngineHelperUtil.getJobState(resp.getJobName(), resp.getGroupName(), resp.getStorageType());

					if (state != (TriggerState.UNSCHEDULED)) {

						Thread thread = Thread.currentThread();
						LOG.info("Thread :" + thread.getContextClassLoader() + thread.toString());

						LOG.info("Unscheduling :" + this.schedulerClassName + " " + resp.getTrigger().toString());
						SchedulerEngineHelperUtil.unschedule(schedulerEntry, resp.getStorageType());
						// TODO: Check Workaround
						// This removes the SchedulerEntry from Liferay's
						// Scheduler List so it is not started aditionally on
						// re-deploy
						// BUT, removing this entry will also destroy our
						// Trigger (as Quartz unschedule(), removes the Trigger
						// from QuartzJob
						this.removeFromEntries();
					} else {
						LOG.warn("Scheduler could not be unscheduled beacuse it was in state " + state);
					}
				}
			}

		} catch (SchedulerException e) {
			LOG.warn("Failed to unschedule job" + this.schedulerClassName, e);
		}
	}

	/**
	 * Deletes the Scheduler thoroughly until server restart!
	 * 
	 */
	public void delete() {
		try {
			List<SchedulerResponse> scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
			for (SchedulerResponse resp : scheduledJobs) {
				if (resp.getJobName().equalsIgnoreCase(this.schedulerClassName)) {

					LOG.info("Stopping :" + resp.toString());
					Map<String, Object> map = resp.getMessage().getValues();

					String portletId = "";
					String listenerName = "";

					if (map.containsKey(SchedulerEngine.PORTLET_ID)) portletId = map.get(SchedulerEngine.PORTLET_ID).toString();
					if (map.containsKey(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME)) listenerName = map.get(SchedulerEngine.MESSAGE_LISTENER_CLASS_NAME).toString();

					LOG.info("Associated to :" + portletId + " " + listenerName + " " + this.destination);

					TriggerState state = SchedulerEngineHelperUtil.getJobState(resp.getJobName(), resp.getGroupName(), resp.getStorageType());

					if (state.equals(TriggerState.NORMAL)) {

						Thread thread = Thread.currentThread();
						LOG.info("Thread :" + thread.getContextClassLoader() + thread.toString());

						LOG.info("Unscheduling :" + this.schedulerClassName + " " + resp.getTrigger().toString());
						SchedulerEngineHelperUtil.delete(schedulerEntry, resp.getStorageType());
						// This removes the SchedulerEntry from Liferay's
						// Scheduler List so it is not started aditionally on
						// re-deploy
						// Removing this entry will also destroy our Trigger (as
						// Quartz unschedule(), removes the Trigger from
						// QuartzJob
						this.removeFromEntries();
					} else {
						LOG.warn("Scheduler could not be unscheduled beacuse it was in state " + state);
					}
				}
			}

		} catch (SchedulerException e) {
			LOG.warn("Failed to unschedule job" + this.schedulerClassName, e);
		}
	}

	/**
	 * Unschedules all Jobs associated with this Listener Class'
	 * 
	 * Assumes Job and Group Name are equal
	 */
	public void stopAll() {
		if (this.schedulerClassName != null && this.schedulerClassName != "") {
			List<SchedulerResponse> scheduledJobs;
			try {
				scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
				for (SchedulerResponse resp : scheduledJobs) {
					if (resp.getJobName().equalsIgnoreCase(this.schedulerClassName)) {
						SchedulerEngineHelperUtil.unschedule(resp.getJobName(), resp.getGroupName(), resp.getStorageType());
					}
				}
				LOG.warn("Removed all Schedulers associated with " + this.schedulerClassName);
			} catch (SchedulerException e) {
				LOG.error("Could not retrieve ScheduledJobs");
			}
		} else {
			LOG.info("No Scheduler set!");
		}
	}

	/**
	 * Unschedules and deletes all Jobs associated with this Listener Class'
	 * Does not affect SchedulerEntries!
	 * Assumes Job and Group Name are equal
	 */
	public static void pauseAllJobs() {

			List<SchedulerResponse> scheduledJobs;
			try {
				scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
				for (SchedulerResponse resp : scheduledJobs) {
					if (resp != null && resp.getJobName().contains(PortletScheduler.class.getPackage().getName())) {
						SchedulerEngineHelperUtil.pause(resp.getJobName(), resp.getGroupName(), resp.getStorageType());
					}
				}
				LOG.info("Paused all Schedulers associated with " + PortletScheduler.class.getPackage().getName());
			} catch (SchedulerException e) {
				LOG.error("Could not retrieve ScheduledJobs");
			}
		
	}

	/**
	 * Unschedules and removes all Jobs associated with this Listener Class'
	 * 
	 * Assumes Job and Group Name are equal
	 */
	public static void removeAllJobs(String portletId) {
		List<SchedulerResponse> scheduledJobs;
		List<SchedulerEntry> entries;
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
		List<SchedulerEntry> toRemove = new LinkedList<SchedulerEntry>();
		TriggerState state;
		
		try {
			scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
			for (SchedulerResponse resp : scheduledJobs) {
				if (resp.getJobName().startsWith(PortletScheduler.class.getPackage().getName())) {
					state = SchedulerEngineHelperUtil.getJobState(resp.getJobName(), resp.getGroupName(), resp.getStorageType());
					if (state != (TriggerState.UNSCHEDULED)) SchedulerEngineHelperUtil.unschedule(resp.getJobName(), resp.getGroupName(), resp.getStorageType());
					entries = portlet.getSchedulerEntries();
					for (SchedulerEntry entry : entries) {
						if(entry.getEventListenerClass().equalsIgnoreCase(resp.getJobName())){
							LOG.info("SchedulerEntry: " + entry.toString());
							toRemove.add(entry);
						}
					}
					entries.removeAll(toRemove);
				}
			}
			LOG.warn("Removed all Schedulers associated with " + PortletScheduler.class.getPackage().getName());
		} catch (SchedulerException e) {
			LOG.error("Could not retrieve ScheduledJobs");
		}
	}

	/*
	 * List Jobs recognized by Scheduler Engine (these are naturally unique)
	 */
	public static List<PortletScheduler> ListSchedulers() {
		List<PortletScheduler> schedulers = new LinkedList<PortletScheduler>();
		PortletScheduler psch = null;

		try {
			List<SchedulerResponse> scheduledJobs = SchedulerEngineHelperUtil.getScheduledJobs();
			for (SchedulerResponse resp : scheduledJobs) {
				if (resp != null && resp.getJobName().contains(PortletScheduler.class.getPackage().getName())) {
					psch = new PortletScheduler();

					LOG.info(resp.toString());
					psch.setJobName(resp.getJobName());
					psch.setGroupName(resp.getGroupName());
					psch.setStorageType(resp.getStorageType());
					psch.setDescription(resp.getDescription());
					psch.setMessage(resp.getMessage());
					psch.setTrigger(resp.getTrigger());
					TriggerState state = SchedulerEngineHelperUtil.getJobState(resp.getJobName(), resp.getGroupName(), resp.getStorageType());
					schedulers.add(psch);
					LOG.info("SchedulerResponse: " + psch.getJobName());
				}

			}

		} catch (SchedulerException e) {
			LOG.error("Could not retrieve ScheduledJobs");
		}
		return schedulers;
	}

	/**
	 * Removes this Scheduler's SchedulerEntry from Liferay's List
	 * 
	 */
	public void removeFromEntries() {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(this.portletId);
		// List<SchedulerEntry> schedulerEntries =
		// portlet.getSchedulerEntries();
		portlet.getSchedulerEntries().remove(this.schedulerEntry);
	}

	/**
	 * Removes this Scheduler's SchedulerEntry from Liferay's List
	 */
	public void removeFromEntries(SchedulerEntry entry) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(this.portletId);
		// List<SchedulerEntry> schedulerEntries =
		// portlet.getSchedulerEntries();
		portlet.getSchedulerEntries().remove(entry);
	}

	/**
	 * From HotDeployListener (this one should do garbage collect, but fails if
	 * a scheduler is UNSCHEDULED allready) if (PropsValues.SCHEDULER_ENABLED) {
	 * List<SchedulerEntry> schedulerEntries = portlet.getSchedulerEntries();
	 * 
	 * if ((schedulerEntries != null) && !schedulerEntries.isEmpty()) { for
	 * (SchedulerEntry schedulerEntry : schedulerEntries) {
	 * SchedulerEngineHelperUtil.unschedule( schedulerEntry,
	 * StorageType.MEMORY_CLUSTERED); } } }
	 */

	/**
	 * List SchedulerEntries associated with this Portlet These are Scheduler
	 * entries as persited by liferay
	 * 
	 * Allows duplicates!
	 * 
	 * @param portletId
	 * @return Liferay Portlet SchedulerEntries
	 */
	public static List<SchedulerEntry> ListSchedulerEntries(String portletId) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);
		List<SchedulerEntry> myJobs = new LinkedList<SchedulerEntry>();

		List<SchedulerEntry> jobs = portlet.getSchedulerEntries();
		for (SchedulerEntry job : jobs) {
			LOG.info("SchedulerEntry: " + job.toString());
			myJobs.add(job);
		}

		return myJobs;
	}


}
