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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Lectureseries;
import de.uhh.l2g.plugins.model.Metadata;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.model.Video_Institution;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.MetadataImpl;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil;
import de.uhh.l2g.plugins.service.MetadataLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;
import de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil;


/**
 * The Class RSSManager.
 */
public class RSSManager {

	/** The title. */
	private String title = "";

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** The description. */
	private String description = "";

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** The language. */
	private String language = "";

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/** The pub date. */
	private String pubDate = "";

	/**
	 * Gets the pub date.
	 *
	 * @return the pub date
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * Sets the pub date.
	 *
	 * @param pubDate the new pub date
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	/** The last build date. */
	private String lastBuildDate = "";

	/**
	 * Gets the last build date.
	 *
	 * @return the last build date
	 */
	public String getLastBuildDate() {
		return lastBuildDate;
	}

	/**
	 * Sets the last build date.
	 *
	 * @param lastBuildDate the new last build date
	 */
	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	/** The image title. */
	private String imageTitle = "";

	/**
	 * Gets the image title.
	 *
	 * @return the image title
	 */
	public String getImageTitle() {
		return imageTitle;
	}

	/**
	 * Sets the image title.
	 *
	 * @param imageTitle the new image title
	 */
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	/**
	 * Generate item.
	 */
	public void generateItem() {
	}

	/**
	 * Delete rss.
	 *
	 * @param name the name
	 */
	public void deleteRss(String name) {
	}

	/**
	 * Creates the rss file.
	 *
	 * @param videoList the video list
	 * @param type the type
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	/*	public void createRssFile(List<Video> videoList, String type) throws IOException, PortalException, SystemException {
		Log LOG = LogFactoryUtil.getLog(RSSManager.class.getName());
		try {
			String imageLink = PropsUtil.get("lecture2go.web.home") + PropsUtil.get("lecture2go.theme.root.path") + "/" + "images" + "/" + "l2go" + "/" + "itunesu" + "/" + "logo.jpg";

			Date date = new Date();
			String[] dateString = date.toString().split(" ");
			String pubDate = dateString[0] + " , " + dateString[2] + " " + dateString[1] + " " + dateString[5] + " " + dateString[3] + " +2000";

			File imageF = new File(PropsUtil.get("lecture2go.web.home")+"/lecture2go-portlet/img/l2go_logo_transp.png");
			if (imageF.isFile()) imageLink = PropsUtil.get("lecture2go.web.home")+"/lecture2go-portlet/img/l2go_logo_transp.png";

			String text = "<?xml version='1.0' encoding='ISO-8859-1'?> \n";
			text += "<rss xmlns:itunes='http://www.itunes.com/dtds/podcast-1.0.dtd' xmlns:itunesu='http://www.itunesu.com/feed' version='2.0'>  \n";
			text += "<channel> \n";
			text += "<title>" + title + "</title> \n";
			text += "<link>" + PropsUtil.get("lecture2go.web.home") + "</link> \n";
			text += "<description>" + description + "</description> \n";
			text += "<language>" + language + "</language> \n";
			// here iTunes tags
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			String yearString = String.valueOf(year);
			text += "<copyright>&#x2117; &amp; &#xA9; "+yearString+" University of Hamburg Lecture2Go</copyright> \n";
			text += "<itunes:author>University of Hamburg - Lecture2Go</itunes:author> \n";
			text += "<itunes:summary>The University of Hamburg offers a steadily growing portion of its lectures online and similar to a take-away. For further information and more video lectures visit the central media platform of the University of Hamburg at http://lecture2go.uni-hamburg.de!</itunes:summary> \n";
			text += "<itunes:image href='" + imageLink + "' /> \n";
			// here iTunes tags end
			text += "<pubDate>" + pubDate + "</pubDate> \n";
			text += "<lastBuildDate>" + pubDate + "</lastBuildDate> \n";
			text += "<image> \n";
			text += "<title>" + imageTitle + "</title> \n";
			text += "<link>" + imageLink + "</link> \n";
			text += "<url>" + PropsUtil.get("lecture2go.web.home")+"/lecture2go-portlet/img/l2go_logo_transp.png" + "</url> \n";
			text += "</image> \n \n";

			try {
				// add item to the feed
				ListIterator<Video> it = videoList.listIterator();
				while (it.hasNext()) {
					String link = null;
					Video v = VideoLocalServiceUtil.getVideo(it.next().getVideoId());
					
					Host objectHost = HostLocalServiceUtil.getHost(v.getHostId());
					Producer objectProducer = ProducerLocalServiceUtil.getProducer(v.getProducerId());

					link = PropsUtil.get("lecture2go.web.home")+"/l2go/-/get/v/"+v.getVideoId();

					String title = v.getTitle().trim();

					title = stringToIsoString(title);

					title = title.replace("<", "&lt;");
					title = title.replace(">", "&gt;");
					title = title.replace("&", "&amp;");
					title = title.replace("\"", "&quot;");
					title = title.replace("'", "&apos;");
					title = title.replace("?", "-");

					String d = parseGMTDate(v.getDate());
					String duration = "";

					try {
						duration = v.getDuration().split(":")[0] + ":" + v.getDuration().split(":")[1] + ":" + v.getDuration().split(":")[2].split(".")[0];
					} catch (Exception e) {
						duration = "";
					}

					text += "<item>\n";
					text += "<title>" + title + "</title>\n";

					// TODO here iTunes tags
					String creator = "";	
					creator = stringToIsoString(creator);

					text += "<itunes:author>" + creator + "</itunes:author> \n";
					text += "<itunes:summary>" + link + "</itunes:summary> \n";
					text += "<itunes:duration>" + duration + "</itunes:duration> \n";
					text += "<itunes:image>" + imageLink + "</itunes:image> \n";
					// here iTunes tags end

					text += "<link>" + link + "</link>\n";
					if (v.getDownloadLink()==1) {
						for (String mf : FileManager.MEDIA_FORMATS) {
							String mfFile = PropsUtil.get("lecture2go.media.repository") + "/" + objectHost.getServerRoot() + "/" + objectProducer.getHomeDir() + "/" + v.getPreffix() +"."+ mf;
							File mfM = new File(mfFile);
							if (mfM.isFile() && type.equals("mp4")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".mp4' type='video/mp4'/>\n";
							if (mfM.isFile() && type.equals("mp3")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".mp3' type='video/mp3'/>\n";
							if (mfM.isFile() && type.equals("m4a")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".m4a' type='video/m4a'/>\n";
							if (mfM.isFile() && type.equals("m4v")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".m4v' type='video/m4v'/>\n";
							if (mfM.isFile() && type.equals("ogg")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".ogg' type='video/ogg'/>\n";
							if (mfM.isFile() && type.equals("webm")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".webm' type='video/webm'/>\n";
							if (mfM.isFile() && type.equals("flv")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".flv' type='video/flv'/>\n";
							if (mfM.isFile() && type.equals("pdf")) text += "<enclosure url='" + PropsUtil.get("lecture2go.downloadserver.web.root")+"/"+PropsUtil.get("lecture2go.symboliclinks.repository.name")+"/"+v.getPreffix() + ".pdf' type='video/pdf'/>\n";
						}
					}
					text += "<pubDate>" + d + "</pubDate>\n";
					text += "<guid>" + link + "</guid>\n";
					text += "</item>\n\n";
				}
			} catch (Exception e) {
			}

			text += "</channel>\n";
			text += "</rss> \n";
			
			File rrsDirectory = new File(System.getProperty("catalina.base") + "/" + "webapps" + "/" + "rss" + "/" );
			if(!rrsDirectory.exists())rrsDirectory.mkdir();
			
			String dateiName = rrsDirectory + "/" + rssFilename;
			/*FileOutputStream schreibeStrom = new FileOutputStream(dateiName);

			for (int i = 0; i < text.length(); i++) {
				schreibeStrom.write((byte) text.charAt(i));
			}
			schreibeStrom.close();

			
			String[] linesArray = text.split("\n"); 
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(linesArray));
			
			
			try {
				Files.write(Paths.get(dateiName), lines, StandardCharsets.UTF_8);
			} 
			catch (Exception e) {}
			
		} catch (NullPointerException npe) {
			LOG.error("RSS FAILED !");
		}
	}*/
	
	
	public void createRssFile(List<Video> videoList, String type) throws IOException, PortalException, SystemException {
		Log LOG = LogFactoryUtil.getLog(RSSManager.class.getName());
		
		XmlUtil xmlUtil = new XmlUtil();
		
		// prepare the publication date (the current time) in RFC-822 date-time format
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getTimeZone("CET"));
		String pubDate = formatter.format(new Date());
		
		/*
		// prepare the lecture2go logo
		String imageLink = "";
		File imageF = new File(PropsUtil.get("lecture2go.web.home")+"/lecture2go-portlet/img/l2go_logo_transp.png");
		if (imageF.isFile()) imageLink = PropsUtil.get("lecture2go.web.home")+"/lecture2go-portlet/img/l2go_logo_transp.png";
		*/
		
		// prepare the link to the lectureseries and the image url of the whole rss feed
		String lectureseriesUrl = "";
		String imageUrl = "";
		String language = "";
		// the default description consists of a space, because some feed readers require a non-empty description field 
		String description = " ";
		if (!videoList.isEmpty()) {
			lectureseriesUrl = videoList.get(0).getLectureseriesUrl();
			imageUrl = getAbsoluteUrl(videoList.get(0).getImageMedium());
			// prepare the language (we set the language to the language of the first video in the list)
			Metadata metadata = new MetadataImpl();
			try {
				metadata = MetadataLocalServiceUtil.getMetadata(videoList.get(0).getMetadataId());
			} catch (PortalException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			// we need to replace the hyphen to be ISO-639 language code compliant
			language = metadata.getLanguage().replaceAll("_", "-");	
			
			// prepare the description
			try {
				Lectureseries lec = LectureseriesLocalServiceUtil.getLectureseries(videoList.get(0).getLectureseriesId());
				// replace empty description value with real description if there is any (all html tags removed)
				if (!(lec.getLongDesc().isEmpty())) {
					description = xmlUtil.cleanInvalidXmlCharacters(lec.getLongDesc().replaceAll("\\<[^>]*>",""));
				}
			} catch (Exception e) {
			}
		}
		
		
		// starting XML DOM
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();
			String namespaceURI = "http://www.itunes.com/dtds/podcast-1.0.dtd";
			String itunesuURI = "http://www.itunesu.com/feed";
			Element rootElement = doc.createElement("rss");
			rootElement.setAttribute("xmlns:itunes", namespaceURI);
			rootElement.setAttribute("xmlns:itunesu", itunesuURI);
			rootElement.setAttribute("version", "2.0");


			doc.appendChild(rootElement);
			
			// channel node
			Element channelElement = doc.createElement("channel");
			rootElement.appendChild(channelElement);
			// title
			Element titleElement = doc.createElement("title");
			titleElement.setTextContent(xmlUtil.cleanInvalidXmlCharacters(title));
			channelElement.appendChild(titleElement);
			// link
			Element linkElement = doc.createElement("link");
			//linkElement.setTextContent(PropsUtil.get("lecture2go.web.home"));
			linkElement.setTextContent(lectureseriesUrl);
			channelElement.appendChild(linkElement);
			// description
			Element descriptionElement = doc.createElement("description");
			descriptionElement.setTextContent(description);
			channelElement.appendChild(descriptionElement);
			//  language
			Element languageElement = doc.createElement("language");
			languageElement.setTextContent(language);
			channelElement.appendChild(languageElement);
			// copyright
			Element copyrightElement = doc.createElement("copyright");
			copyrightElement.setTextContent("University of Hamburg Lecture2Go " + Calendar.getInstance().get(Calendar.YEAR));
			channelElement.appendChild(copyrightElement);
			// itunes:author 
			Element authorElement = doc.createElement("itunes:author");
			authorElement.setTextContent("University of Hamburg - Lecture2Go");
			channelElement.appendChild(authorElement);
			// itunes:summary
			Element summaryElement = doc.createElement("itunes:summary");
			summaryElement.setTextContent("The University of Hamburg offers a steadily growing portion of its lectures online and similar to a take-away. For further information and more video lectures visit the central media platform of the University of Hamburg at http://lecture2go.uni-hamburg.de!");
			channelElement.appendChild(summaryElement);
			// itunes:image
			Element itunesImageElement = doc.createElement("itunes:image");
			itunesImageElement.setAttribute("href", imageUrl);
			channelElement.appendChild(itunesImageElement);
			// pubDate
			Element pubDateElement = doc.createElement("pubDate");
			pubDateElement.setTextContent(pubDate);
			channelElement.appendChild(pubDateElement);
			// lastBuildDate
			Element lastBuildDateElement = doc.createElement("lastBuildDate");
			lastBuildDateElement.setTextContent(pubDate);
			channelElement.appendChild(lastBuildDateElement);
			// image
			Element imageElement = doc.createElement("image");
			// image - title
			Element imageTitleElement = doc.createElement("title");
			imageTitleElement.setTextContent(title);
			imageElement.appendChild(imageTitleElement);
			// image - link
			Element imageLinkElement = doc.createElement("link");
			imageLinkElement.setTextContent(lectureseriesUrl);
			imageElement.appendChild(imageLinkElement);
			// image - url
			Element imageUrlElement = doc.createElement("url");
			imageUrlElement.setTextContent(imageUrl);
			imageElement.appendChild(imageUrlElement);
			channelElement.appendChild(imageElement);
			
			// list of items
			for (Video v: videoList) {
				// reload the video with all informations
				//v = VideoLocalServiceUtil.getVideo(v.getVideoId());
				
				// prepare title
				String title = xmlUtil.cleanInvalidXmlCharacters(v.getTitle().trim());
				
				// prepare the duration field
				String duration = "";
				if(v.getDuration().length() > 0)
				{
					// remove the milliseconds
					duration = v.getDuration().substring(0,v.getDuration().length()-3).trim();
				}
				// prepare the link to video
				String link = PropsUtil.get("lecture2go.web.home")+"/l2go/-/get/v/"+v.getVideoId();
				
				// prepare the publication date of the video
				String mediaPubDate = parseCETDate(v.getGenerationDate());
				
				// prepare the item image
				String videoImage = getAbsoluteUrl(v.getImageMedium());

				// item
				Element itemElement = doc.createElement("item");
				// item - title
				Element itemTitleElement = doc.createElement("title");
				itemTitleElement.setTextContent(title);
				itemElement.appendChild(itemTitleElement);
				// item - itunes:summary
				Element itemSummaryElement = doc.createElement("itunes:summary");
				itemSummaryElement.setTextContent(link);
				itemElement.appendChild(itemSummaryElement);
				// item - itunes:duration
				Element itemDurationElement = doc.createElement("itunes:duration");
				itemDurationElement.setTextContent(duration);
				itemElement.appendChild(itemDurationElement);
				// item - itunes:image
				Element itemImageElement = doc.createElement("itunes:image");
				itemImageElement.setAttribute("href", videoImage);
				itemElement.appendChild(itemImageElement);
				// item - link
				Element itemLinkElement = doc.createElement("link");
				itemLinkElement.setTextContent(link);
				itemElement.appendChild(itemLinkElement);
				// item - enclosure: include only if a file is downloadable
				if (v.getDownloadLink()==1) {			
					String mimeType = "";
					String url = "";
					String length = "";
					if (type.equals("mp4") && v.getMp4File().isFile()) {
						mimeType = "video/mp4";
						url = v.getMp4DownloadLink();
						length = Long.toString(v.getMp4File().length());
						itemElement.appendChild(buildEnclosureElement(doc, url, mimeType, length));
					} else if (type.equals("mp3") && v.getMp3File().isFile()) {
						mimeType = "audio/mpeg";
						url = v.getMp3DownloadLink();
						length = Long.toString(v.getMp3File().length());
						itemElement.appendChild(buildEnclosureElement(doc, url, mimeType, length));
					}
				}
				// item pubDate
				Element itemPubDateElement = doc.createElement("pubDate");
				itemPubDateElement.setTextContent(mediaPubDate);
				itemElement.appendChild(itemPubDateElement);
				// item - guid
				Element itemGuidElement = doc.createElement("guid");
				itemGuidElement.setTextContent(link);
				itemElement.appendChild(itemGuidElement);				
				channelElement.appendChild(itemElement);
			}
			
			// write the xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			// sets the indentation amount for the output
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			File rrsDirectory = new File(System.getProperty("catalina.base") + "/" + "webapps" + "/" + "rss" + "/" );
			if(!rrsDirectory.exists())rrsDirectory.mkdir();
			
			String fileName = rrsDirectory + "/" + rssFilename;
			
			StreamResult result = new StreamResult(new File(fileName));
			
			// activate indentation
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
			
		} catch (Exception e) {
			LOG.error("RSS FAILED !");
		}
	}
	
	private Element buildEnclosureElement(Document doc, String url, String mimeType, String length) {
		Element itemEnclosureElement = doc.createElement("enclosure");
		itemEnclosureElement.setAttribute("url", url);
		itemEnclosureElement.setAttribute("type", mimeType);
		itemEnclosureElement.setAttribute("length", length);
		return itemEnclosureElement;
	}
	
	private String getAbsoluteUrl(String url) {
		// only absolute urls should be used, add base url accordingly
		if (!(url.startsWith("http://") || url.startsWith("https://"))) { 
			url = PropsUtil.get("lecture2go.web.root") + url;
		}
		return url;
	}

	/**
	 * String to iso string.
	 *
	 * @param text the text
	 * @return the string
	 */
	private String stringToIsoString(String text) {
		try {
			// Wandelt den Text in ein ISO byte-Array
			byte[] encoded = text.getBytes("ISO-8859-1");

			// Wandelt das byte-Array in einen String
			text = "";
			for (int i = 0; i < encoded.length; i++) {
				text = text + (char) toUnsignedInt(encoded[i]);
			}

			return text;
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			return "";
		}
	}

	/**
	 * To unsigned int.
	 *
	 * @param value the value
	 * @return the int
	 */
	private int toUnsignedInt(byte value) {
		return (value & 0x7F) + (value < 0 ? 128 : 0);
	}

	/**
	 * Encode.
	 *
	 * @param text the text
	 * @param encType the enc type
	 * @return the string
	 */
	public String encode(String text, String encType) {
		String ret = null;
		try {
			String str = text;
			// Copy the contents of the String to a byte array using the ASCII
			// encoding.
			byte[] arr = str.getBytes(encType);
			// Create a new String using the contents of the byte array.
			String newStr = new String(arr);
			ret = newStr;
		} catch (Exception ex) {
		}
		return ret;
	}

	/**
	 * Parses the gmt date.
	 *
	 * @param l2goDate the l2go date
	 * @return the string
	 */
	public String parseCETDate(String l2goDate) {
		// input
		SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        inputFormatter.setTimeZone(TimeZone.getTimeZone("CET"));

        // output
       	SimpleDateFormat outputFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
       	outputFormatter.setTimeZone(TimeZone.getTimeZone("CET"));
		
        Date inputDate = new Date();
        try {
            inputDate = inputFormatter.parse(l2goDate);
        } catch (Exception e) {
        	return "";
        }
        
		String outputDate = outputFormatter.format(inputDate);
		
		return outputDate;
	}

	/** The rss filename. */
	private String rssFilename = "";

	/**
	 * Gets the rss filename.
	 *
	 * @return the rss filename
	 */
	public String getRssFilename() {
		return rssFilename;
	}

	/**
	 * Sets the rss filename.
	 *
	 * @param rssFilename the new rss filename
	 */
	public void setRssFilename(String rssFilename) {
		this.rssFilename = rssFilename;
	}

	/** The rss inhalt. */
	private String rssInhalt = "";

	/**
	 * Gets the rss inhalt.
	 *
	 * @return the rss inhalt
	 */
	public String getRssInhalt() {
		return rssInhalt;
	}

	/**
	 * Checks if is konferenz video.
	 *
	 * @param id the id
	 * @return true, if is konferenz video
	 */
	public boolean isKonferenzVideo(Long videoId) {
		boolean retur = false;
		List<Video_Institution> vi = Video_InstitutionLocalServiceUtil.getByVideo(videoId);
		ListIterator<Video_Institution> le = vi.listIterator();
		while (le.hasNext()) {
			Institution inst = new InstitutionImpl();
			try {
				inst = InstitutionLocalServiceUtil.getInstitution(le.next().getInstitutionId());
			} catch (PortalException e) {
				//e.printStackTrace();
			} catch (SystemException e) {
				//e.printStackTrace();
			}
			if (inst.getTyp().equals("tree2")) retur = true;
		}
		return retur;
	}

	/**
	 * Sets the rss inhalt.
	 *
	 * @param rssInhalt the new rss inhalt
	 */
	public void setRssInhalt(String rssInhalt) {
		this.rssInhalt = rssInhalt;
	}

	/**
	 * Instantiates a new rSS manager.
	 */
	public RSSManager() {
		//
	}

}

