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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SyntaxManager {

	public static boolean isL2gFile(String fileName){
		boolean isL2gFile=false;
		fileName = replaceIllegalFilenameCharacters(fileName);
		//pick file container
		String container = fileName.split("\\.")[fileName.split("\\.").length-1];
		
		String suffix = fileName.split("\\."+container)[0];
		
		String[] parameter = suffix.split("\\_");
		
		// the parameter array length has to be 4,
		// if not -> this is not l2go upload
		
		String l2gDate = parameter[2]+"_"+parameter[3];
		
		if (parameter.length == 4 && isL2gDate(l2gDate)){
			
			isL2gFile=true;
		}
		return isL2gFile;
	}
	
	public static String replaceIllegalFilenameCharacters(String input){
		return input.replaceAll("[^a-zA-Z0-9\\._-]+", "");
	}

	public static boolean isL2gDate(String input) {
		boolean isL2gDate = false;
        
        // xxxx-xx-xx_xx-xx
        Pattern p = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\_\\d{2}\\-\\d{2}");
        Matcher m = p.matcher(input);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        df.setLenient( false );
        
        if ( input.length() == 16 && m.find() ) {
        	try {
        		Date d1 = df.parse(input);
        		df.format(d1);
        		isL2gDate = true;
        	} catch ( ParseException e ) {
        		System.out.println(e);
        	} 
        }
        
		return isL2gDate;
	}
	
	
	
	
}
