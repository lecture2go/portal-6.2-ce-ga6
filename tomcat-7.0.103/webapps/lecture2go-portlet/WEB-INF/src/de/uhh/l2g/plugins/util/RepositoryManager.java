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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ***************************************************************************/

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.NoPropertyException;
import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;


public class RepositoryManager {
	
	public static boolean SYMLINKS_INSTALLED = false;
	private static final Log LOG = LogFactoryUtil.getLog(RepositoryManager.class.getName());

	//get runtime
	/** The run cmd. */
	static Runtime runCmd = Runtime.getRuntime();
	
    public static final String SYS_ROOT = "vh_0000";
    public static final String SYS_SERVER = "localhost";
    public static final String SYS_PROTOCOL = "http";
    public static final int SYS_PORT = 80;
	
	/**
	 * Creates the folder.
	 *
	 * @param path the path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws PortalException 
	 */
	public static void createFolder(String path) throws IOException{
		File folder = new File(path);
		String shell = GetterUtil.getString(PropsUtil.get("lecture2go.shell.bin"));
		String OS = System.getProperty("os.name");
		if (!OS.startsWith("Windows")){
			if (shell == null || folder.exists()) {
				folder.mkdirs();
				if(shell == null)LOG.error("Shell not configured! Check paramter lecture2go.shell.bin in your portal properties");
				if(folder.exists())LOG.error("Folder exists allready!");
			}
			else {
				if(folder.mkdirs()){}
					String[] cmdArray = {shell, "-cr", "chown nobody " + folder.getAbsolutePath() };
					runCmd.exec(cmdArray);
					String[] cmdArray1 = {shell, "-cr", "chown nobody:nobody " + folder.getAbsolutePath() };
					runCmd.exec(cmdArray1);
					String[] cmdArray2 =  {shell, "-cr", "chmod 701 " + folder.getAbsolutePath() };
					runCmd.exec(cmdArray2);
				}
			}
		else{
			folder.mkdirs();
			LOG.warn("Security settings not supported by operating System");
		}
		
	}
	
	/**
	 * Creates repository and folders if none is existing.
	 *
	 * @param host
	 * @param producer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createRepository(long groupId) throws IOException{
		File mediaRep = new File(PropsUtil.get("lecture2go.media.repository"));
		if(!mediaRep.isDirectory()){
			createFolder(PropsUtil.get("lecture2go.media.repository")); //media repository 
			createFolder(PropsUtil.get("lecture2go.images.system.path")); //image subfolder
			createFolder(PropsUtil.get("lecture2go.symboliclinks.repository.root")); //abo
			createFolder(PropsUtil.get("lecture2go.chapters.system.path")); //chapters
			createFolder(PropsUtil.get("lecture2go.captions.system.path")); //captions
			createFolder(PropsUtil.get("lecture2go.security.folder")); //security
			createFolder(PropsUtil.get("lecture2go.rss.system.path")); //rss
			createVHosts(groupId);
			//
			symlinkToAboHome();
			symlinkToChapterHome();
			symlinkToCaptionsHome();
			symlinkToImagesHome();
			symlinkToRssHome();
			symlinkToVideorepHome();
		}
	}
	
	
	/**
	 * Test if repository and all required subfolders exist.
	 *
	 * @return true, if successful
	 */
	public static boolean repositoryExists(){
		boolean exists = false;

		File mediaRep = new File(PropsUtil.get("lecture2go.media.repository"));
		File aboRep = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root"));
		File imagesRep = new File(PropsUtil.get("lecture2go.images.system.path"));
		File securityRep = new File(PropsUtil.get("lecture2go.security.folder"));
		File chaptersRep = new File(PropsUtil.get("lecture2go.chapters.system.path"));
		File captionsRep = new File(PropsUtil.get("lecture2go.captions.system.path"));
		File rssRep = new File(PropsUtil.get("lecture2go.rss.system.path"));
		
		if(mediaRep.isDirectory() && aboRep.isDirectory() && imagesRep.isDirectory() && securityRep.isDirectory() && chaptersRep.isDirectory() && rssRep.isDirectory() && captionsRep.isDirectory())exists = true;
		
		return exists;
	}
	
	/**
	 * Symlink to chapter home.
	 */
	public static boolean symlinkToChapterHome(){
		boolean ret = false;
		File chapterFolder = new File(PropsUtil.get("lecture2go.chapters.system.path"));
		if (chapterFolder.exists()) {
			String cmd = "ln -s " + chapterFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + "chapters";
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}		
		return ret;
	}
	
	/**
	 * Symlink to captions home.
	 */
	public static boolean symlinkToCaptionsHome(){
		boolean ret = false;
		File captionsFolder = new File(PropsUtil.get("lecture2go.captions.system.path"));
		if (captionsFolder.exists()) {
			String cmd = "ln -s " + captionsFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + "captions";
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}		
		return ret;
	}
	
	/**
	 * Symlink to rss home.
	 */
	public static boolean symlinkToRssHome(){
		boolean ret = false;
		File rssFolder = new File(PropsUtil.get("lecture2go.rss.system.path"));
		if (rssFolder.exists()) {
			String cmd = "ln -s " + rssFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + "rss";
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}	
		return ret;
	}
	
	/**
	 * Symlink to videorep home.
	 */
	public static boolean symlinkToVideorepHome(){
		boolean ret = false;
		File videorepFolder = new File(PropsUtil.get("lecture2go.httpstreaming.video.repository"));
		if (videorepFolder.exists()) {
			String cmd = "ln -s " + videorepFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + "videorep";
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}	
		return ret;
	}
	
	/**
	 * Symlink to abo home.
	 */
	public static boolean symlinkToAboHome(){
		boolean ret = false;
		File aboFolder = new File(PropsUtil.get("lecture2go.symboliclinks.repository.root"));
		if (aboFolder.exists()) {
			String cmd = "ln -s " + aboFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + PropsUtil.get("lecture2go.symboliclinks.repository.name");
			try {runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}		
		return ret;
	}

	/**
	 * Symlink to images home.
	 */
	public static boolean symlinkToImagesHome(){
		boolean ret = false;
		File imgFolder = new File(PropsUtil.get("lecture2go.images.system.path"));
		if (imgFolder.exists()) {
			String cmd = "ln -s " + imgFolder.getAbsolutePath() + " " + System.getProperty("catalina.base") + "/" + "webapps" + "/" + "images";
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}	
		return ret;
	}
	
	/**
	 * Symlink to user home.
	 *
	 * @param host the host
	 * @param producer the producer
	 */
	public static boolean symlinkToUserHome(Host host, Producer producer){
		boolean ret = false;
		File folder = new File(PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/");
		File httpFolder = new File(PropsUtil.get("lecture2go.httpstreaming.video.repository") + "/" + producer.getInstitutionId() + "l2g" + producer.getHomeDir());
		if (!httpFolder.exists()) {
			String cmd = "ln -s " + folder.getAbsolutePath() + " " + httpFolder.getAbsolutePath();
			try { runCmd.exec(cmd); ret = true;} catch (IOException e) {}
		}	
		return ret;
	}

	/**
	 * Creates all vhosts according to database record.
	 *
	 * @param groupId - the scope of host records to be considered
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createVHosts(long groupId) throws IOException{
		List<Host> hosts;

		//create httpstreaming.video.repository i.e. default folder
		createFolder(PropsUtil.get("lecture2go.httpstreaming.video.repository"));
		
		//retrieving hosts should only fail if default Host has not been generated yet
		try {
			hosts = HostLocalServiceUtil.getAll();
		
			//then directories
			for (Host h : hosts) {
				//test if producers exist, before writing directory
				if ( ProducerLocalServiceUtil.getProducersByHostIdCount(h.getHostId()) > 0) {
					
					List<Producer> producers = ProducerLocalServiceUtil.getProducersByHostId(h.getHostId());
					//create host
					createFolder(PropsUtil.get("lecture2go.media.repository")+"/"+h.getDirectory());
					//and user directories 
					for (Producer p : producers) {
						createFolder(PropsUtil.get("lecture2go.media.repository")+"/"+h.getDirectory()+"/"+p.getHomeDir());
						//create symbolic link to required directory
						symlinkToUserHome(h, p);
					}
				}
		}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
}
