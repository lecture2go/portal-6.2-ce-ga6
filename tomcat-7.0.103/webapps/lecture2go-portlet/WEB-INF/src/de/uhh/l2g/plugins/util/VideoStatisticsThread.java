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

import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;

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

/**There is no built-in time based sheduler in Java 
 * Statistics would be more reliable on doing snapshot at concrete time (ideally arround midnight) 
 * (https://quartz-scheduler.org/)
 */
/**
 * The Class VideoStatisticsThread.
 */
public final class VideoStatisticsThread extends Thread {

	/** The should stop. */
	private boolean shouldStop = false;
	
	/**
	 * Should stop.
	 *
	 * @return true, if successful
	 */
	public boolean shouldStop() {
		return shouldStop;
	}

	/**
	 * Sets the should stop.
	 *
	 * @param shouldStop the new should stop
	 */
	public void setShouldStop(boolean shouldStop) {
		this.shouldStop = shouldStop;
	}

	/** The Constant INSTANCE. */
	private static final VideoStatisticsThread STATSINSTANCE = new VideoStatisticsThread();

	/** The time. */
	private static int time = 86400000; //TODO

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public static int getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public static void setTime(int time) {
		VideoStatisticsThread.time = time;
	}

	/**
	 * Gets the single instance of VideoStatisticsThread.
	 *
	 * @return single instance of VideoStatisticsThread
	 */
	public static VideoStatisticsThread getInstance() {
		return STATSINSTANCE;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	/**
	 * Run.
	 */
	@Override
	public void run() {
		while(true){
			if(!shouldStop){
				System.out.println("Refresh Stats Table!");
				//StatisticsLocalServiceUtil.rebuildVideoStatsTable();
				System.out.println("Stored results!");
				try { Thread.sleep(time); } catch (InterruptedException e) {}
			}else{
				try { Thread.sleep(1000); } catch (InterruptedException e) {}				
			}
		}
	}

	/**
	 * Start thread.
	 */
	public void startThread(){
		//start thread
		try{
			this.start();
			synchronized (this) {notify();}			
		}catch(IllegalThreadStateException ite){
			//or continue executing
		}
	}
}
