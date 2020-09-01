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
package de.uhh.l2g.plugins.servlets.oai;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dspace.xoai.dataprovider.exceptions.IdDoesNotExistException;
import org.dspace.xoai.dataprovider.exceptions.OAIException;
import org.dspace.xoai.dataprovider.filter.Filter;
import org.dspace.xoai.dataprovider.filter.FilterResolver;
import org.dspace.xoai.dataprovider.filter.Scope;
import org.dspace.xoai.dataprovider.filter.ScopedFilter;
import org.dspace.xoai.dataprovider.handlers.results.ListItemIdentifiersResult;
import org.dspace.xoai.dataprovider.handlers.results.ListItemsResults;
import org.dspace.xoai.dataprovider.model.Item;
import org.dspace.xoai.dataprovider.model.ItemIdentifier;
import org.dspace.xoai.dataprovider.repository.ItemRepository;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;

import org.dspace.xoai.dataprovider.model.conditions.Condition;

import de.uhh.l2g.plugins.model.OaiRecord;
import de.uhh.l2g.plugins.model.OaiRecord_OaiSet;
import de.uhh.l2g.plugins.model.OaiSet;
import de.uhh.l2g.plugins.model.impl.OaiRecordImpl;
import de.uhh.l2g.plugins.service.OaiRecordLocalServiceUtil;
import de.uhh.l2g.plugins.service.OaiSetLocalServiceUtil;
import de.uhh.l2g.plugins.servlets.oai.filters.DateFromFilter;
import de.uhh.l2g.plugins.servlets.oai.filters.DateUntilFilter;
import de.uhh.l2g.plugins.servlets.oai.filters.L2GoFilter;
import de.uhh.l2g.plugins.servlets.oai.filters.SetFilter;



/**
 * The item repository which handles lecture2go items
 * Here the data is fetched (via Liferay dynamic queries) for the OAI-PMH-responses regarding identifiers and items
 */
public class L2GoItemRepository implements ItemRepository {

	@Override
	public Item getItem(String identifier) throws IdDoesNotExistException, OAIException {
		// returns the oai record item without metadata
		L2GoItem item = new L2GoItem(); 
		
		OaiRecord oaiRecord = new OaiRecordImpl();
		
		try {
			oaiRecord = OaiRecordLocalServiceUtil.getByIdentifier(identifier);
		} catch (Exception e) {
			// identifier does not exist
			throw new IdDoesNotExistException();
		}

				
		item.with("identifier", oaiRecord.getIdentifier())
			.with("videoId", oaiRecord.getVideoId())
			.with("deleted", oaiRecord.getDeleted())
			.with("datestamp", oaiRecord.getDatestamp());
		
		// if the item has sets add them, otherwise add an empty list
		List<OaiSet> oaiSets = OaiSetLocalServiceUtil.getByOaiRecordId(oaiRecord.getOaiRecordId());
		List<String> oaiSetsString = new ArrayList<String>();
		if (oaiSets != null) {
			for (OaiSet oaiSet: oaiSets) {
				oaiSetsString.add(oaiSet.getSetSpec());
			}
		}
		item.with("sets", oaiSetsString);

		return item;
	}
	
	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length) throws OAIException {
		// default case without from/ until parameters so no filters added
		QueryResult queryResult;
		try {
			queryResult = retrieveItems(filters, offset, length);
		} catch (IdDoesNotExistException e) {
			throw new OAIException();
		}
        return new ListItemIdentifiersResult(queryResult.hasMore(), queryResult.getResults());
	}
	
	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length, Date from)
			throws OAIException {
		// restrict by from date, so the DateFromFilter is added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length);
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiersUntil(List<ScopedFilter> filters, int offset, int length,
			Date until) throws OAIException {
		// restrict by until date, so the DateUntilFilter is added
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length);
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length, Date from,
			Date until) throws OAIException {
		// restrict by from and until date, so the DateFromFilter and DateUntilFilter are added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length);
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length,
			String setSpec) throws OAIException {
		// default case with set restriction, but without from/ until parameters
		filters.add(new ScopedFilter(getSetFilter(setSpec), Scope.Query));
		QueryResult queryResult;
		try {
			queryResult = retrieveItemsFilteredBySet(filters, offset, length, setSpec);
		} catch (IdDoesNotExistException e) {
			throw new OAIException();
		}
		
        return new ListItemIdentifiersResult(queryResult.hasMore(), queryResult.getResults());
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length,
			String setSpec, Date from) throws OAIException {
		// restrict by from date, so the DateFromFilter is added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length, setSpec);
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiersUntil(List<ScopedFilter> filters, int offset, int length,
			String setSpec, Date until) throws OAIException {
		// restrict by until date, so the DateUntilFilter is added
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length, setSpec);
	}

	@Override
	public ListItemIdentifiersResult getItemIdentifiers(List<ScopedFilter> filters, int offset, int length,
			String setSpec, Date from, Date until) throws OAIException {
		// restrict by from and until date, so the DateFromFilter and DateUntilFilter are added
		filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItemIdentifiers(filters, offset, length, setSpec);
	}

	
	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length) throws OAIException {
		// default case without from/ until parameters so no filters added
		QueryResult queryResult;
		try {
			queryResult = retrieveItems(filters, offset, length);
		} catch (IdDoesNotExistException e) {
			throw new OAIException();
		}
		
		List<? extends ItemIdentifier> result = queryResult.getResults();
		
        return new ListItemsResults(queryResult.hasMore(), (List<Item>) result);
	}
	

	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length, Date from)
			throws OAIException {
		// restrict by from date, so the DateFromFilter is added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        return this.getItems(filters, offset, length);
	}

	@Override
	public ListItemsResults getItemsUntil(List<ScopedFilter> filters, int offset, int length, Date until)
			throws OAIException {
		// restrict by until date, so the DateUntilFilter is added
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItems(filters, offset, length);
	}

	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length, Date from, Date until)
			throws OAIException {
		// restrict by from and until date, so the DateFromFilter and DateUntilFilter are added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItems(filters, offset, length);
	}

	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length, String setSpec)
			throws OAIException {
		// default case with set restriction, but without from/ until parameters
		QueryResult queryResult;
		try {
			queryResult = retrieveItemsFilteredBySet(filters, offset, length, setSpec);
		} catch (IdDoesNotExistException e) {
			throw new OAIException();
		}
		
		List<? extends ItemIdentifier> result = queryResult.getResults();
		
        return new ListItemsResults(queryResult.hasMore(), (List<Item>) result);
	}

	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length, String setSpec, Date from)
			throws OAIException {
		// restrict by from date, so the DateFromFilter is added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        return this.getItems(filters, offset, length, setSpec);
	}

	@Override
	public ListItemsResults getItemsUntil(List<ScopedFilter> filters, int offset, int length, String setSpec,
			Date until) throws OAIException {
		// restrict by until date, so the DateUntilFilter is added
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItems(filters, offset, length, setSpec);
	}

	@Override
	public ListItemsResults getItems(List<ScopedFilter> filters, int offset, int length, String setSpec, Date from,
			Date until) throws OAIException {
		// restrict by from and until date, so the DateFromFilter and DateUntilFilter are added
        filters.add(new ScopedFilter(getDateFromFilter(from), Scope.Query));
        filters.add(new ScopedFilter(getDateUntilFilter(until), Scope.Query));
        return this.getItems(filters, offset, length, setSpec);
	}
	
	/**
	 * This translates the scoped filters to a dynamic query
	 * @param filters the scopedFilters which are used for restricting the query to the database
	 * @return the dynamic query which can be further processed
	 */
	private DynamicQuery getDynamicQueryFromFilters(List<ScopedFilter> filters) {
		DynamicQuery dynamicQuery = OaiRecordLocalServiceUtil.dynamicQuery();
		
		for (ScopedFilter scopedFilter: filters) {
			// if filter is a L2GoFilter and has the Query scope add the criterion to the query
			Filter filter = scopedFilter.getCondition().getFilter(null);
			if (filter instanceof L2GoFilter && scopedFilter.getScope() == Scope.Query) {
				Criterion criterion = ((L2GoFilter) filter).getCriterion();
				dynamicQuery.add(criterion);
			}
		}			
		
		return dynamicQuery;
	}
	
	/**
	 * This translates the scoped filters to a dynamic query
	 * @param filters the scopedFilters which are used for restricting the query to the database
	 * @return the dynamic query which can be further processed
	 */
	private DynamicQuery getDynamicQueryFromFiltersFilteredBySet(List<ScopedFilter> filters) {
		
		// separate the filter into regular criterions and the criterion specific for set filtering
		List<Criterion> criterions = new ArrayList<Criterion>();
		List<Criterion> setFilterCriterions = new ArrayList<Criterion>();;
		for (ScopedFilter scopedFilter: filters) {
			// if filter is a L2GoFilter and has the Query scope add the criterion to the query
			Filter filter = scopedFilter.getCondition().getFilter(null);
			if (filter instanceof SetFilter && scopedFilter.getScope() == Scope.Query) {
				Criterion criterion = ((SetFilter) filter).getCriterion();
				setFilterCriterions.add(criterion);
			}
			else if (filter instanceof L2GoFilter && scopedFilter.getScope() == Scope.Query) {
				Criterion criterion = ((L2GoFilter) filter).getCriterion();
				criterions.add(criterion);
			}
		}		
		
		// the dynamic query with set filtering it way more complex, as we need to use multiple tables, so we link dynamic queries to each other
		DynamicQuery oaiSetQuery = DynamicQueryFactoryUtil.forClass(OaiSet.class, "oaiset")
				.setProjection(ProjectionFactoryUtil.property("oaiset.oaiSetId"))
				.add(PropertyFactoryUtil.forName("oaiset.oaiSetId").eqProperty("oairecordoaiset.oaiSetId"));

		for (Criterion criterion: setFilterCriterions) {
			oaiSetQuery.add(criterion);
		}
		
		DynamicQuery oaiRecordOaiSetQuery = DynamicQueryFactoryUtil.forClass(OaiRecord_OaiSet.class, "oairecordoaiset")
				.setProjection(ProjectionFactoryUtil.property("oairecordoaiset.oaiRecordId"))
				.add(PropertyFactoryUtil.forName("oairecordoaiset.oaiRecordId").eqProperty("oairecord.oaiRecordId"))
				.add(PropertyFactoryUtil.forName("oairecordoaiset.oaiSetId").in(oaiSetQuery));
		
		DynamicQuery oaiRecordQuery = DynamicQueryFactoryUtil.forClass(OaiRecord.class, "oairecord")
				.add(PropertyFactoryUtil.forName("oairecord.oaiRecordId").in(oaiRecordOaiSetQuery));
	
		for (Criterion criterion: criterions) {
			oaiRecordQuery.add(criterion);
		}
		
		return oaiRecordQuery;
	}
	
	
	/**
	 * Uses the Liferay DynamicQuery to fetch the corresponding data and add them to the query result
	 * @param filters
	 * @param offset
	 * @param length
	 * @return
	 * @throws OAIException 
	 * @throws IdDoesNotExistException 
	 */
	private QueryResult retrieveItems(List<ScopedFilter> filters, int offset, int length) throws IdDoesNotExistException, OAIException {		
		List<ItemIdentifier> l2GoItems =  new ArrayList<ItemIdentifier>();
		
		long count = 0;

		// get the dynamic query according to the filters
		DynamicQuery dynamicQuery = getDynamicQueryFromFilters(filters);
		// we need to set another dynamicQuery for the count or otherwise there would be problems when using the same dynamicQuery for different purposes
		DynamicQuery countDynamicQuery = getDynamicQueryFromFilters(filters);
		
		List<OaiRecord> oaiRecords;
		
		try {
			oaiRecords = OaiRecordLocalServiceUtil.dynamicQuery(dynamicQuery,offset, offset + length);
			// get the count of all OaiRecords which fit the filters
			count = OaiRecordLocalServiceUtil.dynamicQueryCount(countDynamicQuery);
			
			for (OaiRecord oaiRecord: oaiRecords) {
				l2GoItems.add(getItem(oaiRecord.getIdentifier()));
			}
		}
		catch (SystemException e) {
			throw new OAIException();
		}

		return new QueryResult(l2GoItems, offset + length < count, count);
	}
	
	/**
	 * Uses the Liferay DynamicQuery to fetch the corresponding data filtered by set and add them to the query result
	 * @param filters
	 * @param offset
	 * @param length
	 * @param setSpec
	 * @return
	 * @throws IdDoesNotExistException
	 * @throws OAIException
	 */
	private QueryResult retrieveItemsFilteredBySet(List<ScopedFilter> filters, int offset, int length, String setSpec) throws IdDoesNotExistException, OAIException {		
		List<ItemIdentifier> l2GoItems =  new ArrayList<ItemIdentifier>();
		
		long count = 0;

		// get the dynamic query according to the filters
		DynamicQuery dynamicQuery = getDynamicQueryFromFiltersFilteredBySet(filters);
		// we need to set another dynamicQuery for the count or otherwise there would be problems when using the same dynamicQuery for different purposes
		DynamicQuery countDynamicQuery = getDynamicQueryFromFiltersFilteredBySet(filters);
		
		List<OaiRecord> oaiRecords;
		
		try {
			oaiRecords = OaiRecordLocalServiceUtil.dynamicQuery(dynamicQuery,offset, offset + length);
			// get the count of all OaiRecords which fit the filters
			count = OaiRecordLocalServiceUtil.dynamicQueryCount(countDynamicQuery);
			
			for (OaiRecord oaiRecord: oaiRecords) {
				l2GoItems.add(getItem(oaiRecord.getIdentifier()));
			}
		}
		catch (SystemException e) {
 
		}

		return new QueryResult(l2GoItems, offset + length < count, count);
	}
	
	/**
	 * returns the DateFromFilter as a condition
	 * @param from the from date to filter
	 * @return
	 */
	private Condition getDateFromFilter(final Date from) {
		return new Condition() {
			@Override
			public Filter getFilter(FilterResolver filterResolver) {
                return new DateFromFilter(from);
			}
        };
	}
	
	/**
	 * returns the UntilFromFilter as a condition
	 * @param unti the until date to filter
	 * @return
	 */
	private Condition getDateUntilFilter(final Date unti) {
		return new Condition() {
			@Override
			public Filter getFilter(FilterResolver filterResolver) {
                return new DateUntilFilter(unti);
			}
        };
	}
	
	/**
	 * returns the SetFilter as a condition
	 * @param set the set to filter
	 * @return
	 */
	private Condition getSetFilter(final String set) {
		return new Condition() {
			@Override
			public Filter getFilter(FilterResolver filterResolver) {
                return new SetFilter(set);
			}
        };
	}
	
	/**
	 * A helper class to maintain results from the query
	 */
	private class QueryResult {
        private List<ItemIdentifier> results;
        private boolean hasMore;
        private long total;

        private QueryResult(List<ItemIdentifier> results, boolean hasMore, long count) {
            this.results = results;
            this.hasMore = hasMore;
            this.total = count;
        }

        private List<ItemIdentifier> getResults() {
            return results;
        }

        private boolean hasMore() {
            return hasMore;
        }

        private long getTotal() {
            return total;
        }
    }
}
