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

import java.io.File;
import java.io.IOException;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.InstallWizard;
import de.uhh.l2g.plugins.service.InstallWizardLocalServiceUtil;

public class InstallWizardManager {

	private static final Log LOG = LogFactoryUtil.getLog(RepositoryManager.class.getName());
	private Long groupId;
	private Long companyId;
	private InstallWizard installWizard;
	static Runtime runCmd = Runtime.getRuntime();
	
	public InstallWizard getInstallWizard() {
		return installWizard;
	}

	public void setInstallWizard(InstallWizard installWizard) {
		this.installWizard = installWizard;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public 	InstallWizardManager(Long groupId, Long companyId){
		this.companyId = companyId;
		this.groupId = groupId;
		try {
		installWizard = InstallWizardLocalServiceUtil.getInstallWizard(1);
		} catch (Exception e) {
			System.out.println("--->" + e);
			LOG.error("Install wizard not found for companyId - "+companyId+" and group id - "+groupId+"!");
		} 
	}
	
	public void installRepository() {
		File repository = new File(PropsUtil.get("lecture2go.media.repository"));
		if(!repository.exists() && !repository.isDirectory())
			try {
				InstallWizardLocalServiceUtil.activate(installWizard);
				// install wizart is active
				if(installWizard.getWizartActive()==1){
					System.out.print("Create media repository!");
					//call the repository manager for repository implementation. 
					//insure, that there aren't any symbolic links in the tomcat webapp root directory
					//generated before (e. for abo->, videorep->, rss-> etc.)
					try {
						RepositoryManager.createRepository(groupId);
					} catch (IOException e) {
						LOG.error("Repository installation failed! Pleace check the installation folders permitions.");
					}
					
					//copy the demo video and images to the user folder
					File images = new File(PropsUtil.get("liferay.home")+"/data/l2g-demo/images/");
					File video = new File(PropsUtil.get("liferay.home")+"/data/l2g-demo/producer/");
					
					File imagesTarget = new File(PropsUtil.get("lecture2go.images.system.path"));
					File videoTarget = new File(PropsUtil.get("lecture2go.media.repository")+"/vh_101/producer/");
					//		
					try {
						FileUtil.copyDirectory(images, imagesTarget);
					} catch (IOException e) {
						LOG.error("The demo images have not been copied!");
					}
					try {
						FileUtil.copyDirectory(video, videoTarget);
					} catch (IOException e) {
						LOG.error("The demo video has not been copied!");
					}
					
					//deactivate the install wizard
					installWizard.setWizartActive(0);
					try {
						InstallWizardLocalServiceUtil.updateInstallWizard(installWizard);
					} catch (SystemException e) {
						LOG.error("The wizard status could't be updated - still active!");
					}
				}
			} catch (Exception e1) {
				LOG.error("Media repository not available");
			} 
	}

}
