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

import java.util.Calendar;
import java.util.Date;

import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.service.ServiceContext;

import de.uhh.l2g.plugins.model.Term;
import de.uhh.l2g.plugins.model.impl.TermImpl;
import de.uhh.l2g.plugins.service.TermLocalServiceUtil;


/*
 * Checks via cron job periodically what month it is and according to that adds a new Term
 *
 *
 */
public final class TermScheduler extends PortletScheduler implements MessageListener {

	private int wise = 9; // begins October
	private int sose = 3; // begins April
	
	public int getWise() {
		return wise;
	}

	public void setWise(int wise) {
		this.wise = wise;
	}

	public int getSose() {
		return sose;
	}

	public void setSose(int sose) {
		this.sose = sose;
	}

	public TermScheduler() {
		super();
		LOG = LogFactoryUtil.getLog(TermScheduler.class.getName());
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		// uncoment for further debug messages
		// super.receive(message);
		LOG.info("Term Scheduler running " + message.getValues().get(SchedulerEngine.JOB_NAME).toString() + "...");
		
		// Do Job
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);

		Term term = new TermImpl();
		// represent year as a string
		int year = cal.get(Calendar.YEAR);
		String yearString = String.valueOf(year).substring(2);
		
		boolean create = false;
		// prepare for sose
		if (month >= (sose-1) && month <= sose) {
			String prefix = "SoSe";
			term.setYear(yearString);
			term.setPrefix(prefix);
			create = true;
		}
		//prepare for wise
		if (month >= (wise-1) && month <= wise) {
			String prefix = "WiSe";
			int nextYear = ++year;
			String nextYearString = String.valueOf(nextYear).substring(2);
			// System.out.println("this year is: " + yearString +
			// " and next year: " + nextYearString);
			String composedYear = yearString + "/" + nextYearString;
			term.setYear(composedYear);
			term.setPrefix(prefix);
			create = true;
		}
		// create if not yet done
		try {
			int foundedTerms = 0;
			foundedTerms = TermLocalServiceUtil.getByPrefixAndYear(term.getPrefix(), term.getYear()).size();
			
			if(foundedTerms==0 && create == true){
				TermLocalServiceUtil.addTerm(term);
			}
			LOG.info("Term Scheduler finished.");
		} catch (Exception e) {
			LOG.info("Error runnig the term scheduler.");
		} 
	}

	public void start() {
		super.schedule();
		LOG.info("Scheduler start.");
	}

	public void stop() {
		super.unschedule();
		LOG.info("Scheduler stop.");
	}
	
	public void init(String schedulerClassName, ServiceContext serviceContext) {
        super.initScheduler(schedulerClassName,serviceContext);
	}
}
