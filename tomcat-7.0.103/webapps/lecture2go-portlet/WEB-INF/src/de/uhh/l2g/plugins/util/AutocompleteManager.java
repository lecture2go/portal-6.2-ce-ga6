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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.xml.XmlBeanFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import de.uhh.l2g.plugins.NoSuchAutocompleteException;
import de.uhh.l2g.plugins.guest.OpenAccessVideos;
import de.uhh.l2g.plugins.model.Autocomplete;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.service.AutocompleteLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;


public class AutocompleteManager {
	/** The dao bean factory. */
	private XmlBeanFactory daoBeanFactory; 
	
	public void setDaoBeanFactory(XmlBeanFactory daoBeanFactory) {
		this.daoBeanFactory = daoBeanFactory;
	}
	
	public XmlBeanFactory getDaoBeanFactory() {
		return daoBeanFactory;
	}
	
	public List<String> getAllVideos() {
		
		List<String> resultList = new ArrayList<String>();
		List<Video> videoList = new ArrayList<Video>();

		try {
			videoList = VideoLocalServiceUtil.getAll();
		} catch (SystemException e) {
			////e.printStackTrace();
		}
				
		for (Video video : videoList) resultList.add(video.getTitle());
		return resultList;
	}
	
	
	public static synchronized List<String> getAutocompleteResults() throws SystemException {
		List<String> resultList = new ArrayList<String>();
		List<Video> videoList = new ArrayList<Video>();	
		if (videoList.size() == 0) videoList = VideoLocalServiceUtil.getByAllSearchWords();
		for (Video video : videoList) {
			/** Return only the string, that contained the search term */
			String title = video.getTitle().trim();
			String series = video.getLectureseriesName().trim();
			String[] carr = video.getCreators().split("###");
			String tags = video.getTags().trim();

			if (!isDuplicate(resultList, title)) resultList.add(title);
			if (!isDuplicate(resultList, series)) resultList.add(series);
			for(int i = 0; i<carr.length; i++){
				if (!isDuplicate(resultList, carr[i])) resultList.add(carr[i]);
			}
			if (!isDuplicate(resultList, tags)) resultList.add(tags);

			/**
			 * Limit the number of result strings for ajax request to 10 if
			 * (resultList.size() >= 10) { break; }
			 */

		}
		return resultList;
	}
	
	public static synchronized boolean generateAutocompleteResults() throws SystemException  {
		OpenAccessVideos.wordsJSONArray = JSONFactoryUtil.createJSONArray();
		List<String> arrStr = new ArrayList<String>();
		JSONObject strJSON = null;
		arrStr = getAutocompleteResults();
		for (String str : arrStr) {
			strJSON = JSONFactoryUtil.createJSONObject();
			strJSON.put("word", str);
			OpenAccessVideos.wordsJSONArray.put(strJSON);
			//System.out.println(str);
		}
		
		try {
			// update autocomplete record
			Autocomplete autocomplete = AutocompleteLocalServiceUtil.getSingularAutocomplete();
			autocomplete.setSearchWordsJson(OpenAccessVideos.wordsJSONArray.toString());
			AutocompleteLocalServiceUtil.updateAutocomplete(autocomplete);
		} catch (NoSuchAutocompleteException e) {
			// no autocomplete record, this happens on a new setup, create one
			Autocomplete autocomplete = AutocompleteLocalServiceUtil.createAutocomplete(0);
			autocomplete.setSearchWordsJson(OpenAccessVideos.wordsJSONArray.toString());
			AutocompleteLocalServiceUtil.addAutocomplete(autocomplete);
		}
		return true;
	}
	
	private static boolean isDuplicate(List<String> resultList, String word){
		boolean ret = false;
		for(String w : resultList){
			w=w.trim();
			if(w.equals(word)) ret=true;
		}
		return ret;
	}

}
