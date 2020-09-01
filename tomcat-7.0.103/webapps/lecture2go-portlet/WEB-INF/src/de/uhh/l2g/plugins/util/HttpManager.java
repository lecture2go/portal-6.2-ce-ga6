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

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.kernel.json.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * This utility class provides methods to send different requests to a http server
 * It uses default java packages (HttpURLConnection etc). 
 */
public class HttpManager {
	
	private HttpURLConnection connection;
	private String url;
	private String user;
	private String pass;
	private HashMap<String, String> headers = new HashMap<String, String>();

	
	/**
	 * Sends a GET request to the url with basic authentication
	 * @return an HttpURLConnection object
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HttpURLConnection sendGet() throws MalformedURLException, IOException {
		// open the connection
		this.connection = (HttpURLConnection) new URL(this.url).openConnection();
		
		boolean hasBasicAuth = this.user != null && this.pass != null;
		if (hasBasicAuth) {
			String userAndPasswordEncoded = "";
			String userAndPassword = user + ":" + pass;
			userAndPasswordEncoded = Base64.encode(userAndPassword.getBytes());
			// set basic authentication
			this.connection.setRequestProperty("Authorization", "Basic " + userAndPasswordEncoded);
		}
		
	    // set the connection parameters
	    this.connection.setRequestMethod("GET");
	    this.connection.setDoInput(true);
	    this.connection.setDoOutput(false);
	    addHeadersToConnection();
		
		return this.connection;
	}
	
	/**
	 * Sends a POST request to the url with a given json with basic authentication
	 * @param jo a json object to post to the url
	 * @return an HttpURLConnection object
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HttpURLConnection sendPost(JSONObject jo) throws MalformedURLException, IOException {
		final String charset = "UTF-8";

	    // open the connection
		this.connection = (HttpURLConnection) new URL(this.url).openConnection();
		
		boolean hasBasicAuth = this.user != null && this.pass != null;
		if (hasBasicAuth) {
			String userAndPasswordEncoded = "";
			String userAndPassword = user + ":" + pass;
			userAndPasswordEncoded = Base64.encode(userAndPassword.getBytes());
			// set basic authentication
			this.connection.setRequestProperty("Authorization", "Basic " + userAndPasswordEncoded);
		}
		
	    // set the connection parameters
	    this.connection.setRequestMethod("POST");
	    this.connection.setDoOutput(true);
	    this.connection.setRequestProperty("Accept-Charset", charset);
	    this.connection.setRequestProperty("Content-type", "application/json");
	    addHeadersToConnection();
	    
	    // write to the connection
	    OutputStream output = this.connection.getOutputStream();
	    output.write(jo.toString().getBytes(charset));
	    output.close();
	    
	    // finally send it
	    int responseCode = this.connection.getResponseCode();
	   
		return this.connection;
	}
	
	/**
	 * Sends a PUT request to the url with a given json with basic authentication
	 * @param jo a json object to post to the url
	 * @return an HttpURLConnection object
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HttpURLConnection sendPut(JSONObject jo) throws MalformedURLException, IOException {
		final String charset = "UTF-8";

	    // open the connection
		this.connection = (HttpURLConnection) new URL(this.url).openConnection();
		
		boolean hasBasicAuth = this.user != null && this.pass != null;
		if (hasBasicAuth) {
			String userAndPasswordEncoded = "";
			String userAndPassword = user + ":" + pass;
			userAndPasswordEncoded = Base64.encode(userAndPassword.getBytes());
			// set basic authentication
			this.connection.setRequestProperty("Authorization", "Basic " + userAndPasswordEncoded);
		}
		
	    // set the connection parameters
	    this.connection.setRequestMethod("PUT");
	    this.connection.setDoOutput(true);
	    this.connection.setRequestProperty("Accept-Charset", charset);
	    this.connection.setRequestProperty("Content-type", "application/json");
	    addHeadersToConnection();
	    
	    // write to the connection
	    OutputStream output = this.connection.getOutputStream();
	    output.write(jo.toString().getBytes(charset));
	    output.close();
	    this.connection.getResponseCode();
		return this.connection;
    }
	
	/**
	 * Sends a DELETE request to the url with Basic Authentication
	 * @return an HttpURLConnection object
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public HttpURLConnection sendDelete() throws MalformedURLException, IOException {
	    // open the connection
		this.connection = (HttpURLConnection) new URL(this.url).openConnection();
		
		boolean hasBasicAuth = this.user != null && this.pass != null;
		if (hasBasicAuth) {
			String userAndPasswordEncoded = "";
			String userAndPassword = user + ":" + pass;
			userAndPasswordEncoded = Base64.encode(userAndPassword.getBytes());
			// set basic authentication
			this.connection.setRequestProperty("Authorization", "Basic " + userAndPasswordEncoded);
		}
		
	    // set the connection parameters
		this.connection.setDoOutput(true);
	    this.connection.setRequestMethod("DELETE");
	    addHeadersToConnection();
	    
	    this.connection.getResponseCode();
		return this.connection;
	}
	
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
     * Closes the connection if open
     */
    public void close() {
        if (this.connection != null) {
        	this.connection.disconnect();
        }
    }

	public void addHeader(String headerField, String headerValue) {
		headers.put(headerField, headerValue);
	} 
	
	public HashMap<String, String> getHeaders() {
		return headers;
	} 
	
	private void addHeadersToConnection() {
		 // add all specified headers
	    for (Map.Entry<String, String> header : this.headers.entrySet()) {
			this.connection.setRequestProperty(header.getKey(), header.getValue());
		}
	}
}
