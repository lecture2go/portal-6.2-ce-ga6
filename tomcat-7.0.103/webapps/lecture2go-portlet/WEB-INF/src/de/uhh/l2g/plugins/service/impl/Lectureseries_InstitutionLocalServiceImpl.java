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
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import de.uhh.l2g.plugins.model.Lectureseries_Creator;
import de.uhh.l2g.plugins.model.Lectureseries_Institution;
import de.uhh.l2g.plugins.service.base.Lectureseries_InstitutionLocalServiceBaseImpl;
import de.uhh.l2g.plugins.service.persistence.Lectureseries_InstitutionUtil;

/**
 * The implementation of the lectureseries_ institution local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.Lectureseries_InstitutionLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil
 */
public class Lectureseries_InstitutionLocalServiceImpl
	extends Lectureseries_InstitutionLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil} to access the lectureseries_ institution local service.
	 */
	
	protected static Log LOG = LogFactoryUtil.getLog(Lectureseries_Institution.class.getName());

	public Lectureseries_Institution addLectureseries_Institution(Lectureseries_Institution object){
		Long id;
		try {
			id = counterLocalService.increment(Lectureseries_Institution.class.getName());
			object.setPrimaryKey(id);
			super.addLectureseries_Institution(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	public boolean removeByLectureseriesId(Long lectureseriesId) {
		boolean ret = false;
		try {
			Lectureseries_InstitutionUtil.removeByLectureseriesId(lectureseriesId);
		} catch (SystemException e) {
			ret = true;
			//e.printStackTrace();
		}
		return ret;
	}

	public boolean removeByacilityId(Long institutionId){
		boolean ret = false;
		try {
			Lectureseries_InstitutionUtil.removeByInstitutionId(institutionId);
		} catch (SystemException e) {
			ret = true;
			//e.printStackTrace();
		}
		return ret;
	}
	
	public boolean institutionAssignedToLectureseries(Lectureseries_Institution lf){
		boolean ret = false;
				List<Lectureseries_Institution> lfL = new ArrayList();
				try {
					lfL = lectureseries_InstitutionPersistence.findByLectureseriesIdAndInstitutionId(lf.getLectureseriesId(), lf.getInstitutionId());
				} catch (Exception e) {
					//e.printStackTrace();
				}
		if (lfL.size()>0) ret=true;
		return ret;
	}
	
	public List<Lectureseries_Institution> getByLectureseries(long lectureseriesId) throws SystemException{
		return lectureseries_InstitutionPersistence.findByLectureseriesId(lectureseriesId);
	}
	
	
	public Lectureseries_Institution findFirstByLectureseriesIdAndInstitutionId(long lectureseriesId, long institutionId) throws SystemException{
		List<Lectureseries_Institution> result = lectureseries_InstitutionPersistence.findByLectureseriesIdAndInstitutionId(lectureseriesId, institutionId);
		if (result != null && result.size() > 0 ) {
			return result.get(0);
		} else {
			return null;
		}
		
	}
}
