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

import com.liferay.portal.kernel.util.PropsUtil;

public class FeatureManager {
	
	private static final String CITATION2GO_PROPERTY = "de.uhh.l2g.guest.video.citation2go";
	private static final String COMMSY_PROPERTY = "de.uhh.l2g.guest.video.commsy";
	private static final String SOCIALMEDIA_PROPERTY = "de.uhh.l2g.guest.video.socialmedia";
	private static final String CAPTION_INCLUDE_PROPERTY = "de.uhh.l2g.admin.captioninclude";
	private static final String CATALOG_FILTER_TERMS = "de.uhh.l2g.guest.catalog.filter.terms";
	private static final String CATALOG_FILTER_INSTITUTION = "de.uhh.l2g.guest.catalog.filter.institution";
	private static final String CATALOG_FILTER_SUBINSTITUTION = "de.uhh.l2g.guest.catalog.filter.subinstitution";
	private static final String CATALOG_FILTER_CATEGORY = "de.uhh.l2g.guest.catalog.filter.category";
	
	/**
	 * Checks if citation2go is allowed via config or not
	 * @return true if service is allowed, false if not
	 */
	public static boolean hasCitation2Go() {
		return Boolean.parseBoolean(PropsUtil.get(CITATION2GO_PROPERTY));
	}
	
	/**
	 * Checks if commsy support is allowed via config or not
	 * @return true if service is allowed, false if not
	 */
	public static boolean hasCommsy() {
		return Boolean.parseBoolean(PropsUtil.get(COMMSY_PROPERTY));
	}
	
	/**
	 * Checks if commsy support is allowed via config or not
	 * @return true if service is allowed, false if not
	 */
	public static boolean hasSocialMediaSharing() {
		return Boolean.parseBoolean(PropsUtil.get(SOCIALMEDIA_PROPERTY));
	}
	
	/**
	 * Checks if caption include is is allowed via config or not
	 * @return true if service is allowed, false if not
	 */
	public static boolean hasCaptionInclude() {
		return Boolean.parseBoolean(PropsUtil.get(CAPTION_INCLUDE_PROPERTY));
	}
	
	/**
	 * Checks if catalog term filter is allowed via config or not
	 * @return true if filter is allowed, false if not
	 */
	public static boolean hasTermsCatalogFilter() {
		return Boolean.parseBoolean(PropsUtil.get(CATALOG_FILTER_TERMS));
	}
	
	/**
	 * Checks if catalog institution filter is allowed via config or not
	 * @return true if filter is allowed, false if not
	 */
	public static boolean hasInstitutionCatalogFilter() {
		return Boolean.parseBoolean(PropsUtil.get(CATALOG_FILTER_INSTITUTION));
	}
	
	/**
	 * Checks if catalog sub institution filter is allowed via config or not
	 * @return true if filter is allowed, false if not
	 */
	public static boolean hasSubInstitutionCatalogFilter() {
		return Boolean.parseBoolean(PropsUtil.get(CATALOG_FILTER_SUBINSTITUTION));
	}
	
	/**
	 * Checks if catalog institution filter is allowed via config or not
	 * @return true if filter is allowed, false if not
	 */
	public static boolean hasCategoryCatalogFilter() {
		return Boolean.parseBoolean(PropsUtil.get(CATALOG_FILTER_CATEGORY));
	}

}
