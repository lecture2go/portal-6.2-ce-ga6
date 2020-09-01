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

import javax.xml.stream.XMLStreamException;

import org.dspace.xoai.xml.XSISchema;
import org.dspace.xoai.xml.XmlWritable;
import org.dspace.xoai.xml.XmlWriter;

import com.lyncode.xml.exceptions.XmlWriteException;


/**
 * This description for oai identifier (see: http://www.openarchives.org/OAI/2.0/guidelines-oai-identifier.htm) is used with the verb identify
 * and returns an xml encoded info about the oai identifier used in the repository
 */
public class OaiIdentifierDescription implements XmlWritable {
	private static final String NAMESPACE_URI = "http://www.openarchives.org/OAI/2.0/oai-identifier";
	private static final String SCHEMA_LOCATION = "http://www.openarchives.org/OAI/2.0/oai-identifier.xsd";
	
	protected String repositoryIdentifier;
	protected String delimiter;
	protected String scheme;

    public String getRepositoryIdentifier() {
        return repositoryIdentifier;
    }

    public OaiIdentifierDescription withRepositoryIdentifier(String repositoryIdentifier) {
        this.repositoryIdentifier = repositoryIdentifier;
        return this;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public OaiIdentifierDescription withDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }
    
    public String getScheme() {
        return scheme;
    }

    public OaiIdentifierDescription withScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }
    
	@Override
	public void write(XmlWriter writer) throws XmlWriteException {
		try {
			writer.writeStartElement("oai-identifier");
			
			writer.writeDefaultNamespace(NAMESPACE_URI);
            writer.writeNamespace(XSISchema.PREFIX, XSISchema.NAMESPACE_URI);
	        writer.writeAttribute(XSISchema.PREFIX, XSISchema.NAMESPACE_URI, "schemaLocation", NAMESPACE_URI + " " + SCHEMA_LOCATION);

		 	writer.writeElement("scheme", getScheme());
            writer.writeElement("repositoryIdentifier", getRepositoryIdentifier());
            writer.writeElement("delimiter", getDelimiter());
            writer.writeElement("sampleIdentifier", getScheme() +  getDelimiter() + getRepositoryIdentifier() + getDelimiter() + 123);
            writer.writeEndElement();
		} catch (XMLStreamException e) {
            throw new XmlWriteException(e);
        }
	}
}
