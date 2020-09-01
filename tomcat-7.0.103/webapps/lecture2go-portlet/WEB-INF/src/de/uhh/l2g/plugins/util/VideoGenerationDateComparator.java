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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import de.uhh.l2g.plugins.model.Video;


/**
 * The Class VideoGenerationDateComparator.
 */
public class VideoGenerationDateComparator implements Comparator<Video> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	/**
	 * Compare.
	 *
	 * @param v1 the v1
	 * @param v2 the v2
	 * @return the int
	 */
	public int compare(Video v1, Video v2) {
		Date d1 = new Date();
		Date d2 = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
		try {
			d1 = df.parse(v1.getGenerationDate());
			d2 = df.parse(v2.getGenerationDate());
		} catch (ParseException e) {
			//e.printStackTrace();
		}

		int ret = 0;
		if (d1.before(d2)) ret = 1;
		if (d1.after(d2)) ret = -1;

		return ret;
	}
	
	/**
	 * return 0 => is equal
	 * return 1 => date 1 before date 2
	 * return 1 => date 1 after date 2
	 *
	 * @param d1 the date 1
	 * @param d2 the date 2
	 * @return return number of integer 0, 1, or -1
	 */	
	public static int compare(String d1, String d2) {
		Date date1 = new Date();
		Date date2 = new Date();
		//
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
		try {
			date1 = df.parse(d1);
			date2 = df.parse(d2);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		
		int ret = 0;
		if (date1.before(date2)) ret = 1;
		if (date1.after(date2)) ret = -1;
		if (date1.equals(date2)) ret = 0;
		//
		return ret;
	}

}
