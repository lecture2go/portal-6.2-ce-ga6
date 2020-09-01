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

import java.io.BufferedReader;
import java.io.IOException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;

public class VideoProcessorManager {
	protected static Log LOG = LogFactoryUtil.getLog(VideoProcessorManager.class.getName());
	
	private static final String REBUILD_SMIL_PATH = "/rebuild-smil";


	
	/**
	 * Starts a video conversion for the video with the given id
	 * @param videoId the id of the video which will be converted
	 */
	public static boolean startVideoConversion(Long videoId) {
		// if there is a videoprocessing workflow defined in the properties use this, otherwise the default workflow defined at the video-processor will be used
		if (PropsUtil.contains("lecture2go.videoprocessing.workflow")) {
			return startVideoConversion(videoId, PropsUtil.get("lecture2go.videoprocessing.workflow"), JSONFactoryUtil.createJSONObject()); 
		}
		return startVideoConversion(videoId, null, null);
	}
	
	/**
	 * Starts a video conversion for the video with the given id
	 * @param videoId the id of the video which will be converted
	 * @param workflow the workflow to run
	 * @param additionalProperties a json object with additional workflow variables
	 */
	public static boolean startVideoConversion(Long videoId, String workflow, JSONObject additionalProperties) {
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion");

			try {
				// create json object with the necessary informations for the videoprocessor
				JSONObject jo = JSONFactoryUtil.createJSONObject();
				// the video id
				jo.put("sourceId", videoId);
				
				if (workflow != null) {
					// the workflow
					jo.put("workflow", workflow);
					
					// add additional properties if there are any
					if (additionalProperties.length() != 0) {
						Iterator<String> keys = additionalProperties.keys();
						while(keys.hasNext()) {
						    String key = keys.next();
						    String value = additionalProperties.getString(key);
						    jo.put(key, value);
						}
					}
				}
				
				// set the complete path to the file
				Video video = VideoLocalServiceUtil.getVideo(videoId);
				String folder = PropsUtil.get("lecture2go.media.repository")+"/"+HostLocalServiceUtil.getByHostId(video.getHostId()).getDirectory()+"/"+ProducerLocalServiceUtil.getProducer(video.getProducerId()).getHomeDir()+"/";
				String filePath;
				if(video.getOpenAccess()==1){
					filePath = folder + video.getFilename();
				}else{
					filePath = folder + video.getSecureFilename();
				}
				jo.put("sourceFilePath", filePath);
			
				// set whether a smil file should be created
				jo.put("createSmil", true);
				
				// send POST request to video processor
				try {
					HttpManager httpManager = new HttpManager();
					httpManager.addHeader("Tenant", PropsUtil.get("lecture2go.videoprocessing.tenant"));
					httpManager.setUrl(videoConversionUrl);
					if (PropsUtil.contains("lecture2go.videoprocessing.basicauth.user") && PropsUtil.contains("lecture2go.videoprocessing.basicauth.pass")) {
						httpManager.setUser(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.user"));
						httpManager.setPass(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.pass"));
					}
					HttpURLConnection conn = httpManager.sendPost(jo);
					httpManager.close();
					
					// check response
					int responseCode = conn.getResponseCode();
					if (responseCode == 201) {
						return true;
					} else {
						LOG.error("Failed starting a new video conversion of video with id: " + videoId + ". Responsecode: " + responseCode); 
						return false;
					}
				} catch (IOException e) {
					LOG.error("Failed connecting to videoprocessor to start a new video conversion of video with id: " + videoId); 
					//e.printStackTrace();
					return false;
				}
			} catch (SystemException e) {
				//e.printStackTrace();
				return false;
			} catch (PortalException e) {
				//e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Renames the files of a video conversion for the video with the given id
	 * @param videoId the id of the video whose files will be renamed
	 */
	public static void renameFileOfVideoConversion(Long videoId, String filename) {
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
	
			String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion") + "/sourceid/" + String.valueOf(videoId) + "/filename";
			// create json object with current (non-open access) filename
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			jo.put("sourceFileName", filename);
			// send PUT request to video processor
			try {
				HttpManager httpManager = new HttpManager();
				httpManager.setUrl(videoConversionUrl);
				httpManager.addHeader("Tenant", PropsUtil.get("lecture2go.videoprocessing.tenant"));
				if (PropsUtil.contains("lecture2go.videoprocessing.basicauth.user") && PropsUtil.contains("lecture2go.videoprocessing.basicauth.pass")) {
					httpManager.setUser(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.user"));
					httpManager.setPass(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.pass"));
				}
				HttpURLConnection conn = httpManager.sendPut(jo);
				httpManager.close();
				
				// check response
				int responseCode = conn.getResponseCode();
				if (responseCode != 200) {
					LOG.error("Failed renaming the files of a video conversion of video with id: " + videoId + ". Responsecode: " + responseCode); 
				}
			} catch (IOException e) {
				LOG.error("Failed connecting to videoprocessor to rename the files of the video conversion of video with id: " + videoId); 
				//e.printStackTrace();
			}
		}
	}

	/**
	 * Deletes the files of a video conversion for the video with the given id
	 * @param videoId the id of the video whose files will be deleted
	 */
	public static boolean deleteVideoConversion(Long videoId) {
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion") + "/sourceid/" + String.valueOf(videoId);
			// send DELETE request to video processor
			try {
				HttpManager httpManager = new HttpManager();
				httpManager.setUrl(videoConversionUrl);
				httpManager.addHeader("Tenant", PropsUtil.get("lecture2go.videoprocessing.tenant"));
				if (PropsUtil.contains("lecture2go.videoprocessing.basicauth.user") && PropsUtil.contains("lecture2go.videoprocessing.basicauth.pass")) {
					httpManager.setUser(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.user"));
					httpManager.setPass(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.pass"));
				}
				HttpURLConnection conn = httpManager.sendDelete();
				httpManager.close();
				
				// check response
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					return true;
				} else {
					LOG.error("Failed deleting the files of a video conversion of video with id: " + videoId + ". Responsecode: " + responseCode); 
					return false;
				}
			} catch (IOException e) {
				LOG.error("Failed connecting to videoprocessor to delete the files of the video conversion of video with id: " + videoId); 
				//e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Gets video conversion status of a video conversion for the video with the given id
	 * @param videoId the id of the video whose info will be retrieved
	 * @return the exact status returned by the videoconversion provider
	 */
	public static String getVideoConversionStatusForVideoId(Long videoId) {
		JSONObject jsonResponse = getVideoConversionResponseAsJson(videoId);
		if (jsonResponse == null) {
			return "";
		}
		// return the conversion status
		return jsonResponse.getString("status");	
				
	}
	
	/**
	 * Returns the workflow of the video conversion
	 * @param videoId the id of the video
	 * @return the simple status
	 */
	public static String getVideoConversionWorkflow(Long videoId) {
		JSONObject jsonResponse = getVideoConversionResponseAsJson(videoId);
		if (jsonResponse == null) {
			return "";
		}
		// return conversion workflow
		return jsonResponse.getString("workflow");
	}	
	
	/**
	 * Returns a simple status: Error, Running, Finished
	 * @param videoId the id of the video
	 * @return the simple status
	 */
	public static String getSimpleVideoConversionStatusForVideoId(Long videoId) {
		String complexStatus = getVideoConversionStatusForVideoId(videoId);
		
		// return empty value if complexStatus is empty
		if (complexStatus.isEmpty()) {
			return "";
		}
		
		// otherwise translate the complex status to the simple status
		String simpleStatus = "";
		if (complexStatus.startsWith("ERROR")) {
			// all ERROR_* status codes are translated to ERROR
			simpleStatus = "ERROR";
		} else if (complexStatus.equals("FINISHED")) {
			// FINISHED status code stays FINISHED status code
			simpleStatus = complexStatus;
		} else if (complexStatus.equals("DELETED")) { 
			// DELETED status code translates to empty String, as it is handled like the Processing didn't exist
			simpleStatus = "";
		} else {
			// all other codes are translated to RUNNING
			simpleStatus = "RUNNING";
		}
		return simpleStatus;
	}
	
	/**
	 * Sends a request to the Videoprocessor to rebuild all smil files with the given quality restrictions
	 * @param tenants array of tenants, for which SMIL files are processed
	 * @param maxHeight the max height restriction (0 for no restriction)
	 * @param maxBitrate the max bitrate restriction (0 for no restriction)
	 * @return the amount of SMIL files which could not be processed 
	 */
	public static long rebuildAllSmilFiles(String[] tenants, long maxHeight, long maxBitrate) {
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			LOG.info("Rebuild all SMIL files started for: ");
			for (String tenant: tenants) {
				LOG.info("tenant: " + tenant);
			}
			if (maxHeight>0) {
				LOG.info("... with resolution limit (height) of: " + maxHeight + "pixels.");
			} else {
				LOG.info("... with no resolution limit");
			}
			if (maxBitrate>0) {
				LOG.info("... with bitrate limit of " + maxBitrate + "bit/s");
			} else {
				LOG.info("... with no bitrate limit");
			}
			
			long errorCount = 0;
			String rebuildSmilUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion") + REBUILD_SMIL_PATH;
			// create json object with info 
			JSONObject jo = JSONFactoryUtil.createJSONObject();
			
			// tenants
			JSONArray tenantsJsonArray = JSONFactoryUtil.createJSONArray();
			for (String tenant: tenants) {
				tenantsJsonArray.put(tenant);
			}
			jo.put("tenants", tenantsJsonArray);
			
			if (maxHeight > 0) 
				jo.put("maxHeight", String.valueOf(maxHeight));
			if (maxBitrate > 0) 
				jo.put("maxBitrate", String.valueOf(maxBitrate));
			
			// send POST request to video processor
			try {
				HttpManager httpManager = new HttpManager();
				httpManager.addHeader("Tenant", PropsUtil.get("lecture2go.videoprocessing.tenant"));
				httpManager.setUrl(rebuildSmilUrl);
				if (PropsUtil.contains("lecture2go.videoprocessing.basicauth.user") && PropsUtil.contains("lecture2go.videoprocessing.basicauth.pass")) {
					httpManager.setUser(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.user"));
					httpManager.setPass(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.pass"));
				}
				HttpURLConnection conn = httpManager.sendPost(jo);
				httpManager.close();
				
				// check response
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					return 0;
				} else if (responseCode == 500) {
					JSONObject jsonResponse = getJsonObjectFromResponse(conn);
					jsonResponse.getLong("errorCount");
					
					LOG.error("There were some errors with rebuild SMIL files. Amount of SMIL Files with errors: " + errorCount); 
					return errorCount;
				} else {
					LOG.error("There were unknown errors with rebuild SMIL files. Responsecode: " + responseCode);
				}
			} catch (IOException e) {
				LOG.error("Failed connecting to videoprocessor"); 
				//e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}
	

	/**
	 * Returns the video conversion object as a JSONObject
	 * @param videoId the id of the video
	 * @return the video conversion as a JSONObject
	 */
	private static JSONObject getVideoConversionResponseAsJson(Long videoId) {
		if (PropsUtil.contains("lecture2go.videoprocessing.provider")) {
			String videoConversionUrl = PropsUtil.get("lecture2go.videoprocessing.provider.videoconversion") + "/sourceid/" + String.valueOf(videoId);
			// send GET request to video processor to check 
			try {
				HttpManager httpManager = new HttpManager();
				httpManager.setUrl(videoConversionUrl);
				httpManager.addHeader("Tenant", PropsUtil.get("lecture2go.videoprocessing.tenant"));
				if (PropsUtil.contains("lecture2go.videoprocessing.basicauth.user") && PropsUtil.contains("lecture2go.videoprocessing.basicauth.pass")) {
					httpManager.setUser(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.user"));
					httpManager.setPass(PropsUtil.get("lecture2go.videoprocessing.provider.basicauth.pass"));
				}
				HttpURLConnection conn = httpManager.sendGet();
				httpManager.close();
			
				// videoprocessor return status 200 ok, if a given video id was processed
				int responseCode = conn.getResponseCode();
				if (responseCode == 200) {
					BufferedReader in = new BufferedReader(
					        new InputStreamReader(conn.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					JSONObject jsonResponse = JSONFactoryUtil.createJSONObject(response.toString());
					
					return jsonResponse;
				}
			} catch (IOException e) {
				LOG.error("Failed connecting to videoprocessor to get status of video with id: " + videoId); 
			} catch (JSONException e) {
				LOG.error("Failed reading json from videoprocessor for video with id: " + videoId); 
			}
		}
		return null;
	}
	
	private static JSONObject getJsonObjectFromResponse(HttpURLConnection conn) {
		JSONObject jsonResponse = null;
		
		try {

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			jsonResponse = JSONFactoryUtil.createJSONObject(response.toString());

		} catch (Exception e) {
			//
		}

		return jsonResponse;
	}
}
