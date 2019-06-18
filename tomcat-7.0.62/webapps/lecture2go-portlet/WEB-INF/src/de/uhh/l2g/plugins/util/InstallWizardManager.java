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
			LOG.error("Install wizard not found for companyId - "+companyId+" and group id - "+groupId+"!");
		} 
	}
	
	public void installRepository() {
		File repository = new File(PropsUtil.get("lecture2go.media.repository"));
		if(!repository.isFile())
			try {
				InstallWizardLocalServiceUtil.activate(installWizard);
				// install wizart is active
				if(installWizard.getWizartActive()==1){
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
