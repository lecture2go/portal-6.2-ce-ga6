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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dspace.xoai.dataprovider.DataProvider;
import org.dspace.xoai.dataprovider.exceptions.OAIException;
import org.dspace.xoai.dataprovider.model.Context;
import org.dspace.xoai.dataprovider.model.MetadataFormat;
import org.dspace.xoai.dataprovider.parameters.OAIRequest;
import org.dspace.xoai.dataprovider.repository.Repository;
import org.dspace.xoai.dataprovider.repository.RepositoryConfiguration;
import org.dspace.xoai.model.oaipmh.DeletedRecord;
import org.dspace.xoai.model.oaipmh.Granularity;
import org.dspace.xoai.model.oaipmh.OAIPMH;
import org.dspace.xoai.xml.XmlWriter;

import com.liferay.portal.kernel.util.PropsUtil;
import com.lyncode.xml.exceptions.XmlWriteException;

import de.uhh.l2g.plugins.service.OaiRecordLocalServiceUtil;

/**
 * The OAI-PMH-Dataprovider Servlet entry point
 */
public class OaiPmhDataProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OaiPmhDataProvider() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// the access to the oai pmh dataprovider may be disabled via the config file
		if (!Boolean.parseBoolean(PropsUtil.get("lecture2go.oaipmh.accessible"))) {
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			PrintWriter out = response.getWriter();
			out.println("OAI-PMH service is unavailable");
			out.flush();
			return;
		}
		
		// retrieve the earliest datestamp of all oaiRecords
		Date earliestDatestamp = OaiRecordLocalServiceUtil.getEarliestDatestamp();
		if (earliestDatestamp == null) {
			// set to 1970 if no earliest datestamp can be obtained from the OaiRecords
			earliestDatestamp = new Date(0L);
		}
		
		// get the base URL from the request
		String baseUrl = request.getRequestURL().toString();
				
		RepositoryConfiguration repositoryConfig = new RepositoryConfiguration()
				.withRepositoryName(PropsUtil.get("lecture2go.oaipmh.repositoryname"))
				.withAdminEmail(PropsUtil.get("lecture2go.oaipmh.adminemail"))
				.withBaseUrl(baseUrl)
				.withMaxListIdentifiers(Integer.parseInt(PropsUtil.get("lecture2go.oaipmh.limitperrequest.identifiers")))
				.withMaxListRecords(Integer.parseInt(PropsUtil.get("lecture2go.oaipmh.limitperrequest.records")))
				.withMaxListSets(Integer.parseInt(PropsUtil.get("lecture2go.oaipmh.limitperrequest.sets")))
				.withDeleteMethod(DeletedRecord.PERSISTENT)
				.withGranularity(Granularity.Second)
				.withEarliestDate(earliestDatestamp);
		
		// set the optional oai-identifier-description (this also overwrites the awful xoai description, which is hardcoded if no description is given)
		OaiIdentifierDescription oaiIdentifierDescription = new OaiIdentifierDescription()
				.withScheme(PropsUtil.get("lecture2go.oaipmh.identifierscheme"))
				.withRepositoryIdentifier(PropsUtil.get("lecture2go.oaipmh.identifierprefix"))
				.withDelimiter(PropsUtil.get("lecture2go.oaipmh.identifierdelimiter"));
		try {
			String descriptionString = "";
			descriptionString = XmlWriter.toString(oaiIdentifierDescription);
			if (!descriptionString.isEmpty()) {
				repositoryConfig.withDescription(descriptionString);
			}
		} catch (Exception e) {
			// there is a problem creating an xml encoded string for the oai description: proceed anyway, as this is optional
		}
		
		// the set repository (sets may be deactivated via config
		L2GoSetRepository l2gSetRepository = new L2GoSetRepository();
		if (!Boolean.parseBoolean(PropsUtil.get("lecture2go.oaipmh.supportsets"))) {
			l2gSetRepository.doesNotSupportSets();
		}

		// initialize the repository with the relevant infos
		Repository repository = Repository.repository().
				withConfiguration(repositoryConfig).
				withItemRepository(new L2GoItemRepository()).
				withSetRepository(l2gSetRepository);
		
		// initialize the transformers for the metadata formats
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		List<MetadataFormat> metadataFormats = new ArrayList<MetadataFormat>();
		metadataFormats.add(initializeMetadataFormat(transformerFactory, "/WEB-INF/oai/oai_datacite.xml", "oai_datacite", "http://datacite.org/schema/kernel-4", "http://schema.datacite.org/meta/kernel-4.2/metadata.xsd"));
		metadataFormats.add(initializeMetadataFormat(transformerFactory, "/WEB-INF/oai/oai_dublincore.xml", "oai_dc", "http://www.openarchives.org/OAI/2.0/oai_dc/", "http://www.openarchives.org/OAI/2.0/oai_dc.xsd"));

		// context
		Context context = new Context();
		
		// add the metadata-formats to the context
		for (MetadataFormat metadataFormat: metadataFormats) {
			if (metadataFormat != null) 
				context.withMetadataFormat(metadataFormat);
		}
		
		
		// init dataProvider
		DataProvider dataProvider = DataProvider.dataProvider(context, repository);
		
		// get the request parameters as map
		Map<String, List<String>> requestAsMap = requestAsMap(request);

					
		// handle OAI request
		response.setContentType("text/xml;charset=UTF-8");
		OAIRequest requestParameters = new OAIRequest(requestAsMap);
		OAIPMH oaiPmh;
		try {
			oaiPmh = dataProvider.handle(requestParameters);
			OutputStream outputStream = response.getOutputStream();
			XmlWriter writer;
			writer = new XmlWriter(outputStream);
			if (PropsUtil.contains("lecture2go.oaipmh.stylesheet")) {
				String stylesheetRelativePath = PropsUtil.get("lecture2go.oaipmh.stylesheet");
				try {
					writer.writeProcessingInstruction("xml-stylesheet type='text/xsl' href='"+stylesheetRelativePath+"'");
				} catch (XMLStreamException e) {
					// no problem, only the xsl stylesheet for end users have a problem, proceed
				}
			}
				
			oaiPmh.write(writer);
			writer.flush();
	        writer.close();
		} catch (OAIException e) {
			sendServerError(response);
		} catch (XMLStreamException e) {
			sendServerError(response);
		} catch (XmlWriteException e) {
			sendServerError(response);
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * Sends a server error
	 * @param response
	 */
	private void sendServerError(HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		} catch (IOException e) {
		}
	}
	
	/**
	 * Translates a request to a complex map to use with OAIRequest
	 * @param request
	 * @return
	 */
	private Map<String, List<String>> requestAsMap(HttpServletRequest request) {
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		
		// translate to map consisting of a list instead of array, as the OAIRequest uses a list here
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (Map.Entry<String, String[]> entry : requestParameterMap.entrySet()) {
			map.put(entry.getKey(), Arrays.asList(entry.getValue()));
		}
		return map;
	}

	/**
	 * Initializes the metadataformat with the corresponding transformation stylesheet
	 * @param transformerFactory
	 * @param pathToXSLT
	 * @param prefix
	 * @param namespace
	 * @param SchemaLocation
	 * @return
	 */
	private MetadataFormat initializeMetadataFormat(TransformerFactory transformerFactory, String pathToXSLT, String prefix, String namespace, String SchemaLocation) {
		InputStream stylesheet = getServletContext().getResourceAsStream(pathToXSLT);
	    StreamSource stylesource = new StreamSource(stylesheet);
		try {
			Transformer transformer = transformerFactory.newTransformer(stylesource);
			MetadataFormat metadataFormat = new MetadataFormat().withPrefix(prefix)
					.withNamespace(namespace)
					.withSchemaLocation(SchemaLocation)
					.withTransformer(transformer);
			return metadataFormat;
		} catch (TransformerConfigurationException e) {
			return null;
		}
	}

}
