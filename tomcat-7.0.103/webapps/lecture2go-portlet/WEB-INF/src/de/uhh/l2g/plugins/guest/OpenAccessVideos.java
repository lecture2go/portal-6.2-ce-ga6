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
package de.uhh.l2g.plugins.guest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.uhh.l2g.plugins.NoSuchAutocompleteException;
import de.uhh.l2g.plugins.NoSuchLectureseriesException;
import de.uhh.l2g.plugins.NoSuchLicenseException;
import de.uhh.l2g.plugins.NoSuchVideoException;
import de.uhh.l2g.plugins.model.Autocomplete;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.License;
import de.uhh.l2g.plugins.model.Metadata;
import de.uhh.l2g.plugins.model.Segment;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Institution;
import de.uhh.l2g.plugins.model.impl.LectureseriesImpl;
import de.uhh.l2g.plugins.model.impl.LicenseImpl;
import de.uhh.l2g.plugins.model.impl.MetadataImpl;
import de.uhh.l2g.plugins.model.impl.VideoImpl;
import de.uhh.l2g.plugins.service.AutocompleteLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.LicenseLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.SegmentLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.util.ProzessManager;

public class OpenAccessVideos extends MVCPortlet {
	protected static Log LOG = LogFactoryUtil.getLog(Video.class.getName());
	
	@Override
	public void serveResource( ResourceRequest resourceRequest, ResourceResponse resourceResponse ) throws IOException, PortletException {
		String resourceID = resourceRequest.getResourceID();
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		if (cmd.equals("get_search_words")) {
			getSearchWords(resourceRequest, resourceResponse);
		}
	}
	
	public static JSONArray wordsJSONArray = JSONFactoryUtil.createJSONArray();
	private void getSearchWords(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		PrintWriter out = resourceResponse.getWriter();
		String wordsJSONArrayString = "";
		try {
			Autocomplete autocomplete = AutocompleteLocalServiceUtil.getSingularAutocomplete();
			wordsJSONArrayString = autocomplete.getSearchWordsJson();
		} catch (NoSuchAutocompleteException e) {
			// no autocomplete yet, return empty String
		} catch (SystemException e) {
			// something went wrong with fetching the data, return empty String, too
		}
		out.println(wordsJSONArrayString);
	}
	
	public void addFilter(ActionRequest request, ActionResponse response){
		String jspPage = request.getParameter("jspPage");
		Long institutionId =  getLongParameterFromString(request, "institutionId");
		Long parentInstitutionId = getLongParameterFromString(request,"parentInstitutionId");
		Long termId = getLongParameterFromString(request, "termId");
		Long categoryId = getLongParameterFromString(request, "categoryId");
		Long creatorId = getLongParameterFromString(request,"creatorId");
		String searchQuery = "";
		if (request.getParameter("searchQuery") != null) {
			searchQuery = request.getParameter("searchQuery");
		}

		response.setRenderParameter("institutionId", institutionId+"");
		response.setRenderParameter("parentInstitutionId", parentInstitutionId+"");
		response.setRenderParameter("termId", termId+"");
		response.setRenderParameter("categoryId", categoryId+"");
		response.setRenderParameter("creatorId", creatorId+"");
		response.setRenderParameter("searchQuery", searchQuery);
		response.setRenderParameter("jspPage", jspPage);
	}
	
	public void addSearch(ActionRequest request, ActionResponse response) {
		String jspPage = request.getParameter("jspPage");
		String searchQuery = request.getParameter("searchQuery");
		
		response.setRenderParameter("searchQuery", searchQuery);
		response.setRenderParameter("jspPage", jspPage);
	}

	public void viewOpenAccessVideo(ActionRequest request, ActionResponse response) {
		String objectType = ParamUtil.getString(request, "objectType");
		String password = request.getParameter("password");
		boolean objectExists = true;
		
		Long objectId = new Long(0);
		boolean secLink = false;
	   	String oid = request.getParameter("objectId");
		
	    try{
	    	objectId = new Long(oid);
	    }catch(NumberFormatException e){
	    	// objectId can not be parsed as long, first check if it contains a html anchor symbol (which was transmitted via URL encoding, otherwise 
	    	// request.getParameter would have removed this from the parameter
	    	if (oid.contains("#")) {
	    		objectId = Long.valueOf(oid.substring(0 , oid.indexOf("#")));
	    	} else {
	    		// no encoded URL parts, so it may be secure video or lectureseries objects
			    if(objectType.equals("v")){ //for video objects
		    		try {
						objectId = VideoLocalServiceUtil.getBySecureUrl(oid).getVideoId();		
						secLink = true;
					} catch (NoSuchVideoException e1) {
					} catch (SystemException e1) {}
		    	 }
			    if(objectType.equals("l")){ //for lecture series objects
			    	try {
			    		objectId = LectureseriesLocalServiceUtil.getByUSID(oid).getLectureseriesId();
						secLink = true;
			    	} catch (Exception e2) {
			    		// USID does not exist, a "no videos found" page will be returned
			    	}
			    }
	    	}
	    }

	    Long timeStart = new Long(0);
	    Long timeEnd = new Long(0);
	    
	    try{
	    	timeStart = getLongParameterFromString(request, "timeStart");
	    	timeEnd = getLongParameterFromString(request, "timeEnd");
	    }catch(Exception e){}
	   
	    Video video = new VideoImpl();
	    //lecture series object
	    Lectureseries lectureseries = new LectureseriesImpl();
	    
	    //Lecture series
	    if(objectType.equals("l")){
	    	try{
	    		lectureseries = LectureseriesLocalServiceUtil.getLectureseries(objectId);
	    		if(!secLink){
	    			try {
		    			video = VideoLocalServiceUtil.getVideo(lectureseries.getPreviewVideoId());
	    			} catch (Exception e) {
	    				
	    			}
	    		}else{
	    			Long videoId = VideoLocalServiceUtil.getLatestClosedAccessVideoId(objectId);
	    			try {
	    				video = VideoLocalServiceUtil.getVideo(videoId);
	    			} catch (Exception e) {
	    				
	    			}
	    		}
	    	}catch(Exception e){
	    		objectExists = false;
	    		response.setRenderParameter("jspPage","/guest/noVideosFound.jsp");	
	    	}
	    }else if(objectType.equals("v")){
	    	try {
				video = VideoLocalServiceUtil.getVideo(objectId);
			} catch (Exception e) {
				
			}
	    	try{lectureseries = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());}catch (Exception e){}
	    }
	    
    	if(video.getVideoId()==0)objectExists=false;
    	
	    if(objectExists){
	    	// create symlink to downloadable file if not existing
	    	VideoLocalServiceUtil.createSymLinkToDownloadableFileIfNotExisting(video.getVideoId());
	    	
			/* generate symbolic links in the download folder if the download is enabled and symbolic links are not yet existing. 
	    	we currently check this on every video page view, as there may be files generated in the file system (like a generation of a mp3 by the postprocessing engine ) which 
	    	do not trigger an event in lecture2go, so it could be handled there
	    	*/
			if (video.getDownloadLink()==1) {
				ProzessManager pm = new ProzessManager();
				pm.generateSymbolicLinks(video);
			}
	    	
		    List<Video> relatedVideos = new ArrayList<Video>();
		    //related videos by lectureseries id
	    	try {
	    		int os = 0;
	    		if(video.getOpenAccess()==1)os=1;
				relatedVideos = VideoLocalServiceUtil.getByLectureseriesAndOpenaccess(lectureseries.getLectureseriesId(),os);
				relatedVideos = VideoLocalServiceUtil.stripVideosWithMissingMetadataFromList(relatedVideos);
			} catch (SystemException e) {}
		    
		    //chapters and segments
		    List<Segment> segments= new ArrayList<Segment>();
			try {
				segments = SegmentLocalServiceUtil.getSegmentsByVideoId(objectId);
			} catch (PortalException e) {
			} catch (SystemException e) {}
		    
		    //institutions for video
		    List<Video_Institution> vi = new ArrayList<Video_Institution>();
		    vi = Video_InstitutionLocalServiceUtil.getByVideo(video.getVideoId());
		    
		    //metadata for video
		    Metadata m = new MetadataImpl();
		    try {
				m = MetadataLocalServiceUtil.getMetadata(video.getMetadataId());
			} catch (PortalException e) {
			} catch (SystemException e) {}
		    
		    //license for video
		    License l = new LicenseImpl();
		    try {
				l = LicenseLocalServiceUtil.getLicense(video.getLicenseId());
			} catch (Exception e) {} 
		    
		    //update video hits
		    try {
			    video = VideoLocalServiceUtil.incrementHitCounter(video);
		    } catch(SystemException e) {
				LOG.error("Can't update hit counter for video with id " + video.getVideoId() + "!");
		    }
		  
		    
		    //check password access
		    if(secLink==false){
		    	if(video.getOpenAccess()==1) video.setAccessPermitted(1);
		    	else video.setAccessPermitted(2);
		    }else{
		    	//access denied by default
		    	video.setAccessPermitted(0);
		    	
	    		//1. authentication by lecture series password
				try{
			    	if(password.equals(lectureseries.getPassword()))video.setAccessPermitted(1);
		   			else video.setAccessPermitted(0);				
				}catch(Exception e){}

	   			
	    		//2. authentication by cookie
				Cookie[] c = request.getCookies();
				try{
					for(int i=0; i<c.length;i++){
						Cookie coo = c[i];
						String cooVal ="";
						if(coo.getName().equals("L2G_LSID"))cooVal=c[i].getValue();
						//has been already logged in
						if(cooVal.equals(video.getLectureseriesId()+"")){
							video.setAccessPermitted(1);
						}
					}
				}catch(java.lang.NullPointerException e){
				}
	    		
	    		//3. authentication by video password
	    		if(!video.getPassword().isEmpty()){
	    			try{
	        			if(password.equals(video.getPassword())){
	        				video.setAccessPermitted(1);
	        			}else{
	        				video.setAccessPermitted(0);
	        			}   				
	    			}catch(Exception e){
	    				video.setAccessPermitted(0);
	    			}
	    		}
	    	}
		    
		    request.setAttribute("license",l);
		    request.setAttribute("videoMetadata",m);
		    request.setAttribute("videoInstitutions",vi);
		    request.setAttribute("video",video);
		    request.setAttribute("relatedVideos",relatedVideos);
		    request.setAttribute("segments",segments);
		    request.setAttribute("lectureseries",lectureseries);
		    request.setAttribute("timeStart",timeStart);
		    request.setAttribute("timeEnd",timeEnd);
		    request.setAttribute("objectType",objectType);
		    request.setAttribute("objectId",oid);
	        
		    //
		    buildOpenGraphMetaTag("og:title", video.getTitle(), response);
	        buildOpenGraphMetaTag("og:url", video.getUrl(), response);
	        buildOpenGraphMetaTag("og:image", video.getImage(), response);
	        buildOpenGraphMetaTag("og:description", lectureseries.getLongDesc(), response);
	        
		    if(video.getVideoId()==0) response.setRenderParameter("jspPage","/guest/noVideosFound.jsp");	
		    else response.setRenderParameter("jspPage","/guest/videoDetails.jsp");	    	
	    }
	}
	
	private void buildOpenGraphMetaTag(String property, String content, ActionResponse response){

        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element element = doc.createElement( "meta");
        element.setAttribute( "content", content );
        element.setAttribute( "property", property );
        response.addProperty( MimeResponse.MARKUP_HEAD_ELEMENT, element );

    }
	
	/**
	 * This method parses a Long value from a string request parameter
	 * It may strip trailing non-numeric characters. This is done because in some situations (URL encoded HTML anchor: %23 => #)
	 * there may be additional wrong characters in the request parameter 
	 * @param request the actionRequest
	 * @param parameterName the name of the parameter
	 * @return the parameter value as Long
	 */
	private Long getLongParameterFromString(ActionRequest request, String parameterName) {
		String parameter = request.getParameter(parameterName);
		Long parameterAsLong;
		try {
			parameterAsLong = new Long(request.getParameter(parameterName));
		} catch (Exception e) {
			// removes first non digit and everything after it
			try {
				parameterAsLong = Long.valueOf(parameter.replaceAll("\\D.*", ""));
			} catch (Exception e1) {
				// parameter is not parsable even with non digits removed
				parameterAsLong = new Long(0);
			}
		}		
		return parameterAsLong;
	}

}
