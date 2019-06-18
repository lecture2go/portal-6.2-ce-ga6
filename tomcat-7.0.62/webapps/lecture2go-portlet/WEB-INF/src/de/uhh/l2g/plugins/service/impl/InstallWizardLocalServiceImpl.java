/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.uhh.l2g.plugins.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import de.uhh.l2g.plugins.model.InstallWizard;
import de.uhh.l2g.plugins.service.base.InstallWizardLocalServiceBaseImpl;

/**
 * The implementation of the install wizard local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.InstallWizardLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.InstallWizardLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.InstallWizardLocalServiceUtil
 */
public class InstallWizardLocalServiceImpl
	extends InstallWizardLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.InstallWizardLocalServiceUtil} to access the install wizard local service.
	 */
	
	public void activate(InstallWizard wiz) throws PortalException, SystemException{
		wiz.setWizartActive(1);
		installWizardPersistence.update(wiz);
	}
}