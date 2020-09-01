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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 ***************************************************************************/

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.liferay.portal.kernel.util.PropsUtil;

import sun.misc.BASE64Encoder;

/**
 * The Class Security.
 */
public class Security {

	/** The tomcat l2 go video repository. */
	private String tomcatL2GoVideoRepository = "";

	/**
	 * Gets the tomcat l2 go video repository.
	 *
	 * @return the tomcat l2 go video repository
	 */
	public String getTomcatL2GoVideoRepository() {
		return tomcatL2GoVideoRepository;
	}

	/**
	 * Sets the tomcat l2 go video repository.
	 *
	 * @param tomcatL2GoVideoRepository the new tomcat l2 go video repository
	 */
	public void setTomcatL2GoVideoRepository(String tomcatL2GoVideoRepository) {
		this.tomcatL2GoVideoRepository = tomcatL2GoVideoRepository;
	}

	/** The fms l2 go video repository. */
	private String fmsL2GoVideoRepository = "";

	/**
	 * Gets the fms l2 go video repository.
	 *
	 * @return the fms l2 go video repository
	 */
	public String getFmsL2GoVideoRepository() {
		return fmsL2GoVideoRepository;
	}

	/**
	 * Sets the fms l2 go video repository.
	 *
	 * @param fmsL2GoVideoRepository the new fms l2 go video repository
	 */
	public void setFmsL2GoVideoRepository(String fmsL2GoVideoRepository) {
		this.fmsL2GoVideoRepository = fmsL2GoVideoRepository;
	}

	/**
	 * Creates the secure symbolic link for tomcat l2 go repository.
	 *
	 * @param pfad the pfad
	 * @param filenamePreffix the filename preffix
	 * @param filenameFormat the filename format
	 * @throws Exception the exception
	 */
	public void createSecureSymbolicLinkForTomcatL2GoRepository(String pfad, String filenamePreffix, String filenameFormat) throws Exception {
		String secureFilename = createSecureFileName();
		Runtime runCmd = Runtime.getRuntime();
		String command = "ln -s " + pfad + "/" + filenamePreffix + "." + filenameFormat + " " + tomcatL2GoVideoRepository + "/" + secureFilename + "." + filenameFormat;
		runCmd.exec(command);
	}

	/**
	 * Creates the secure hard link for fms l2 go repository.
	 *
	 * @param pfad the pfad
	 * @param filenamePreffix the filename preffix
	 * @param filenameFormat the filename format
	 * @throws Exception the exception
	 */
	public void createSecureHardLinkForFmsL2GoRepository(String pfad, String filenamePreffix, String filenameFormat) throws Exception {
		String secureFilename = createSecureFileName();
		Runtime runCmd = Runtime.getRuntime();
		String command = "ln " + pfad + "/" + filenamePreffix + "." + filenameFormat + " " + fmsL2GoVideoRepository + "/" + secureFilename + "." + filenameFormat;
		runCmd.exec(command);
	}

	/**
	 * Creates the secure file name.
	 *
	 * @return the string
	 */
	public static String createSecureFileName() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			long msec = System.currentTimeMillis();
			String time = msec + "";
			ReadWriteDES.encode(time.getBytes(), out, "11091980");
			String s = new BASE64Encoder().encode(out.toByteArray());
			// replace bad characters!
			s = s.replace('/', 'a');
			s = s.replace('\\', 'b');
			s = s.replace('\'', 'n');
			s = s.replace('+', 'm');
			s = s.replace('-', 'r');
			s = s.replace('#', 't');
			s = s.replace('(', 'k');
			s = s.replace(')', 'l');
			s = s.replace('.', 'i');
			s = s.replace(',', 'n');
			s = s.replace('_', 'e');
			s = s.replace('"', 'v');
			s = s.replace('=', 'x');
			return s;
		} catch (Exception e) {
			
			//e.printStackTrace();
			return null;
		}
	}

	/**
	 * Encode string.
	 *
	 * @param p_sString the p_s string
	 * @return the string
	 */
	public static String encodeString(String p_sString) {
		if (p_sString == null) return "";
		return (new sun.misc.BASE64Encoder()).encode(p_sString.getBytes());
	}

	/**
	 * Decode string.
	 *
	 * @param p_sString the p_s string
	 * @return the string
	 */
	public static String decodeString(String p_sString) {
		if (p_sString == null || p_sString.equals("")) return null;
		try {
			return new String((new sun.misc.BASE64Decoder()).decodeBuffer(p_sString));
		} catch (IOException e) {
			return null;
		}
	}
	
	
	/**
	 * Hash algorithm (copy from s3 handling)
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA256(String data, byte[] key) throws Exception {
	    String algorithm="HmacSHA256";
	    Mac mac = Mac.getInstance(algorithm);
	    mac.init(new SecretKeySpec(key, algorithm));
	    return mac.doFinal(data.getBytes("UTF-8"));
	}
	
	/**
	 * Hex encode (copy from s3 handling)
	 * @param signer
	 * @return
	 */
	public static String getSignatureKey(byte[] signer) {
		return new String(Hex.encodeHex(signer));
	}

	/**
	 * Hash the datestamp and message to sign (copy from s3 handling)
	 * @param dateStamp
	 * @param toSign
	 * @return
	 * @throws Exception
	 */
	public static byte[] getSignatureKey(String dateStamp, String toSign) throws Exception {
	    byte[] kSecret = (PropsUtil.get("lecture2go.fileupload.secret")).getBytes("UTF-8");
	    byte[] kDate = HmacSHA256(dateStamp, kSecret);
	    byte[] dataSigning = HmacSHA256(toSign, kDate);
	    return dataSigning;
	}

}
