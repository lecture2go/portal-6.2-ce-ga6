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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;

import de.uhh.l2g.plugins.InstitutionNameException;
import de.uhh.l2g.plugins.NoSuchInstitutionException;
import de.uhh.l2g.plugins.model.Creator;
import de.uhh.l2g.plugins.model.Institution;
import de.uhh.l2g.plugins.model.Institution_Host;
import de.uhh.l2g.plugins.model.impl.InstitutionImpl;
import de.uhh.l2g.plugins.model.impl.Institution_HostImpl;
import de.uhh.l2g.plugins.service.ClpSerializer;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil;
import de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.base.InstitutionLocalServiceBaseImpl;
import de.uhh.l2g.plugins.service.persistence.InstitutionFinderUtil;

/**
 * The implementation of the institution local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.uhh.l2g.plugins.service.InstitutionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Iavor Sturm
 * @see de.uhh.l2g.plugins.service.base.InstitutionLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil
 */
public class InstitutionLocalServiceImpl extends InstitutionLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil} to access the
	 * institution local service.
	 */
	protected static Log LOG = LogFactoryUtil.getLog(Institution.class.getName());

	public Institution addInstitution(Institution object){
		Long id;
		try {
			id = counterLocalService.increment(Institution.class.getName());
			object.setPrimaryKey(id);
			super.addInstitution(object);
		} catch (SystemException e) {
			LOG.error("can't add new object with id " + object.getPrimaryKey() + "!");
		}
		return object;
	}
	
	public Institution getById(long institutionId) throws SystemException {
		return institutionPersistence.fetchByPrimaryKey(institutionId);
	}

	public List<Institution> getRootInstitutionsByOpenAccessVideos() throws SystemException {
		return InstitutionFinderUtil.findRootInstitutionsByOpenAccessVideos();
	}

	public List<Institution> getByParentIdList(long parentId) throws SystemException {
		return institutionPersistence.findByParent(parentId);
	}

	public int getByParentIdCount(long parentId) throws SystemException {
		return institutionPersistence.countByParent(parentId);
	}

	public List<Institution> getByParentId(long parentId) throws SystemException {
		return institutionPersistence.findByParent(parentId);
	}

	public Institution getRoot() throws NoSuchInstitutionException, SystemException {
		return institutionPersistence.findByParent(0).iterator().next();
	}

	public Map<String, String> getByParentIdMap(long parentId) throws SystemException {
		Map<String, String> institutions = new LinkedHashMap<String, String>();
		List<Institution> fList = institutionPersistence.findByParent(parentId);

		for (Institution institution : fList) {
			String id = "" + institution.getInstitutionId();
			String name = "" + institution.getName();
			institutions.put(id, name);
		}
		return institutions;
	}

	public List<Institution> getByLevel(int level) throws SystemException {
		return institutionPersistence.findBylevel(level);
	}

	public List<Institution> getByLectureseriesId(long lectureseriesId, int begin, int end) throws SystemException {
		return InstitutionFinderUtil.findByLectureseriesId(lectureseriesId, begin, end);
	}

	public Map<String, String> getAllSortedAsTree(int begin, int end) throws SystemException {
		Map<String, String> allInstitutions = new LinkedHashMap<String, String>();
		List<Institution> einListAll = InstitutionFinderUtil.findAllSortedAsTree(begin, end);

		for (Institution institution : einListAll) {
			String id = "" + institution.getInstitutionId();
			String name = _indentFromPath(institution.getPath(), "/") + institution.getName();
			allInstitutions.put(id, name);
		}

		return allInstitutions;
	}

	public int getMaxSortByParentId(long parentId) throws SystemException {
		return InstitutionFinderUtil.findMaxSortByParent(parentId);
	}

	public int getLockingElements(long institutionId) throws SystemException {
		return InstitutionFinderUtil.findLockingElements(institutionId);
	}

	public List<Institution> getInstitutionsFromLectureseriesIdsAndVideoIds(ArrayList<Long> lectureseriesIds, ArrayList<Long> videoIds) {
		return InstitutionFinderUtil.findInstitutionsByLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds, new Long(0));
	}

	public List<Institution> getInstitutionsFromLectureseriesIdsAndVideoIds(ArrayList<Long> lectureseriesIds, ArrayList<Long> videoIds, Long parentId) {
		return InstitutionFinderUtil.findInstitutionsByLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds, parentId);
	}

	private String _indentFromPath(String path, String sep) {
		String s = "";
		for (int i = 1; i <= path.split(sep).length - 1; i++) {
			s += "--";
		}
		return s;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new InstitutionNameException();
		}
	}

	protected void up() {
	}

	protected void down() {
	}

	/**
	 * Refreshes sort number for given institution Adds new number on insert
	 * (newpos > 0) - shifts numbers on delete (newpos = 0)
	 *
	 */
	protected int updateSort(Institution inst, int newpos) throws SystemException {
		int validPosition = 0;
		int curPos = 1;
		int prevPos = inst.getSort();

		// System.out.println(inst.getSort());
		int subElements = getByParentIdCount(inst.getParentId());

		if (subElements < 1)
			validPosition = 1; // There is nothing to reorder and only one valid
								// position
		else { // sort Elements newpos <= 1 => shift all, newpos > max attach at
				// back
			List<Institution> subtree = getByParentId(inst.getParentId());

			// delete (virtually append to end and shift all other down)
			int increment = 0;
			// System.out.println("Start: " + curPos + " " + newpos + " " +
			// prevPos + " " +increment+ " " +validPosition);
			for (Institution subInstitution : subtree) {
				if (newpos <= curPos && increment == 0) { // insert new
															// Institution here
					if (newpos > 0) {
						validPosition = curPos;
						increment = 1; // shift all follwing up
					} else { // newpos = 0 <=> remove
						if (curPos > prevPos)
							increment = -1;
						// System.out.println(subInstitution.getInstitutionId()
						// +" "+ subInstitution.getName()+ " " + curPos + " " +
						// increment+ " " +validPosition);

					}
				}
				subInstitution.setSort(curPos + increment);
				institutionPersistence.update(subInstitution);
				// System.out.println(subInstitution.getInstitutionId() +" "+
				// subInstitution.getName()+ " " + curPos + " " + increment+ " "
				// +validPosition);
				curPos++;

			}
			if (increment == 0)
				validPosition = curPos;

		}

		// System.out.println(validPosition);

		return validPosition;
	}

	/**
	 * Refreshes sort of subinstitutions for given parent Brings values in
	 * natural order starting with 1
	 * 
	 * Finder results must be preorderd by sort
	 */
	protected void reorderChildren(Institution inst) throws SystemException {
		int subElements = getByParentIdCount(inst.getPrimaryKey());
		// There is nothing to reorder if number of children is smaller 1
		int startPos = 1;
		if (subElements > 1) { // sort Elements newpos <= 1 => shift all
			List<Institution> subtree = InstitutionLocalServiceUtil.getByParentId(inst.getPrimaryKey());
			//
			int increment = 0;
			for (Institution subInstitution : subtree) {
				// System.out.println("Reorder: "+startPos + increment);
				subInstitution.setSort(startPos + increment);
				institutionPersistence.update(subInstitution);
				increment++;
			}
		}
	}

	public long getDefaultInstitutionId() throws SystemException {
		Institution defaultInstitution = institutionPersistence.findByParent(0).iterator().next();
		//
		if (defaultInstitution == null)
			return 0;
		else
			return defaultInstitution.getPrimaryKey();
	}

	public Institution addDefaultInstitution(ServiceContext serviceContext) throws SystemException, PortalException {
		long institutionId = counterLocalService.increment(Institution.class.getName());
		Institution defaultInstitution = institutionPersistence.create(institutionId);
		// ParentId = 0 marks default
		defaultInstitution.setName(PropsUtil.get("lecture2go.default.rootInstitution"));
		// Set Top Level Attributes
		defaultInstitution.setParentId(0);
		defaultInstitution.setLevel(0);
		defaultInstitution.setParentId(0);
		defaultInstitution.setTyp("tree1");
		defaultInstitution.setExpandoBridgeAttributes(serviceContext);
		institutionPersistence.update(defaultInstitution);
		// add default host if set
		long hostId = HostLocalServiceUtil.getDefaultHost().getHostId();
		if (hostId > 0) {
			Institution_HostLocalServiceUtil.addEntry(institutionId, hostId, serviceContext);
		}
		//
		return defaultInstitution;
	}

	public Institution addInstitution(String name, long hostId, long parentId, int sort) throws SystemException, PortalException {
		validate(name);
		long institutionId = counterLocalService.increment(Institution.class.getName());
		Institution institution = InstitutionLocalServiceUtil.createInstitution(institutionId);
		institution.setInstitutionId(institutionId);
		Institution parent = InstitutionLocalServiceUtil.getById(parentId);
		institution.setName(name);
		institution.setParentId(parentId);
		institution.setTyp("tree1"); // there is no tree2 anymore

		if (parentId > 0 && parentId < Long.MAX_VALUE)
			institution.setLevel(parent.getLevel() + 1);
		else
			institution.setLevel(0);

		if (sort <= 0)
			sort = 1;
		institution.setSort(updateSort(institution, sort));
		//
		Institution nI = InstitutionLocalServiceUtil.addInstitution(institution);

		// only add if set
		if (hostId > 0) {
			long id = counterLocalService.increment(Institution_Host.class.getName());
			Institution_Host institution_Host = Institution_HostLocalServiceUtil.createInstitution_Host(id);
			institution_Host.setInstitutionId(nI.getInstitutionId());
			institution_Host.setHostId(hostId);
			Institution_HostLocalServiceUtil.addInstitution_Host(institution_Host);
		}

		return institution;
	}

	public Institution updateInstitution(long institutionId, String name, int sort) throws PortalException, SystemException {
		validate(name);
		Institution institution = getInstitution(institutionId);
		institution.setName(name);
		if (sort <= 0)
			sort = 1;
		institution.setSort(updateSort(institution, sort));
		institutionPersistence.update(institution);
		// refresh order
		if (institution.getParentId() > 0)
			reorderChildren(getById(institution.getParentId()));
		return institution;
	}

	public Institution removeByInstitutionId(Long institutionId) {
		Institution ret = InstitutionLocalServiceUtil.createInstitution(0);
		try {
			ret = institutionPersistence.remove(institutionId);
		} catch (Exception e) {
			System.out.println("Could not delete " + ret.getName());
		}
		return ret;
	}

	public Institution deleteInstitution(long institutionId) throws PortalException, SystemException {
		Institution institution = getInstitution(institutionId);
		// Check if Institution is empty, i.e. no subfacilities, lecture series,
		// videos, and members
		if (getLockingElements(institutionId) < 1) {
			updateSort(institution, 0);
			institution = InstitutionLocalServiceUtil.deleteInstitution(institution);

			// Remove anything from link table if there...
			List<Institution_Host> linstitution_Host = Institution_HostLocalServiceUtil.getListByInstitutionId(institutionId);
			if (linstitution_Host.size() > 0) {
				for (Institution_Host link : linstitution_Host) {
					long ihId = link.getPrimaryKey();
					// System.out.println(ihId);
					Institution_HostLocalServiceUtil.deleteLinkById(ihId);
				}
			}
		} else {
			System.out.println("Could not delte " + institution.getName() + "because there are lectures, videos or other entity instances associated with this institution");
		}

		return institution;
	}

	public long updateCounter() throws SystemException, PortalException {
		// get current Counter
		Counter counter = CounterLocalServiceUtil.getCounter(Institution.class.getName());
		LOG.debug(counter.getCurrentId());
		int count = InstitutionLocalServiceUtil.getInstitutionsCount();
		LOG.debug(count);
		long institutionId = 0; // actual maxId

		// Retrieve actual table data

		if (count > 0) { // our db is filled... with something at least
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");
			DynamicQuery query = DynamicQueryFactoryUtil.forClass(Institution.class, classLoader).addOrder(OrderFactoryUtil.desc("institutionId"));
			query.setLimit(0, 1);
			List<Institution> institutions = InstitutionLocalServiceUtil.dynamicQuery(query);

			if (institutions.size() > 0)
				institutionId = institutions.get(0).getInstitutionId();
		}
		LOG.debug(institutionId);
		// Update Counter if asynchronous with estimated value or data reseted
		if (counter.getCurrentId() < institutionId || institutionId == 0) {
			counter.setCurrentId(institutionId);
			CounterLocalServiceUtil.updateCounter(counter);
		}
		//
		return counter.getCurrentId();
	}
	
	/**
	 * Prepares the list of institutions for pending producers selection
	 * (institutions which should not be selected can be defined via properties file)
	 * @return the list of institutions for the producer to select
	 * @throws SystemException
	 */
	public List<Institution> getForProducerPending() throws SystemException {
		// this is a unmodifiable list, so we copy the elements later on
		List<Institution> institutions = getByLevel(1);
		 
		if (PropsUtil.contains("lecture2go.producer.pending.restrictedinstitutions")) {
			// get the restricted institutions ids from the properties file
			String[] restrictInstitutionIds = PropsUtil.getArray("lecture2go.producer.pending.restrictedinstitutions");
			
			List<Long> restrictInstitutionIdsList = new ArrayList<Long>();
			for (String restrictInstitutionIdString : restrictInstitutionIds) {
				restrictInstitutionIdsList.add(Long.parseLong(restrictInstitutionIdString));
			}
			
			List<Institution> nonRestrictedInstitutions = new ArrayList<Institution>();
			for (Institution institution: institutions) {
				if (!restrictInstitutionIdsList.contains(institution.getInstitutionId())) {
					nonRestrictedInstitutions.add(institution);
				}
			}
			return nonRestrictedInstitutions;
		}
		return institutions;
	}
	
}
