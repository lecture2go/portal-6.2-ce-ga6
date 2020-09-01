<%@page import="com.liferay.portal.service.persistence.PortletUtil"%>
<%@include file="/init.jsp"%>


<%!com.liferay.portal.kernel.dao.search.SearchContainer<Institution> searchInstitutionContainer = null;%>
<%!com.liferay.portal.kernel.dao.search.SearchContainer<Institution> searchSubInstitutionContainer = null;%>

<liferay-ui:success key="request_processed" message="request_processed"/>
<liferay-ui:error key="host-or-institution-error" message="host-or-institution-error"/>
<liferay-ui:error key="no-property-error" message="property-not-configured" />
<liferay-ui:error key="no-roles-error" message="roles-not-configured" />

<portlet:renderURL var="viewURL"><portlet:param name="jspPage" value="/admin/institutionList.jsp" /></portlet:renderURL>

<%
//Variables required for Permissions
String institutionPortletName = portletDisplay.getRootPortletId();

//Portlet Instance Id (can be string)
String institutionPortletPrimKey = portletDisplay.getResourcePK();

//page name
String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());

%>
<%--END: DEBUG INFO--%>
<%
	//Current Selection in Request Object
	long institutionId = ParamUtil.getLong(request, "institutionId");
	long hostId = ParamUtil.getLong(request, "hostId");

	//Get Top Level institution of current scope
	Institution root = InstitutionLocalServiceUtil.getByParentId(0).iterator().next();
	long rootId = GetterUtil.getLong(root.getInstitutionId());
	
	//Get First Level institution List
	List<Institution> institutions = InstitutionLocalServiceUtil.getByParentId(rootId);
	
	//Get StreamingServers
	List<Host> hostList = HostLocalServiceUtil.getHosts(0, 100);
	//System.out.println(hostList.toString());
	
	//Sort preset for first level Institutions
	int maxOrder = InstitutionLocalServiceUtil.getMaxSortByParentId(rootId)+1;

	Institution treeBase = root;
    int ownInstitutionMax = InstitutionLocalServiceUtil.getMaxSortByParentId(rootId)+1;
			
%>
	<liferay-portlet:renderURL varImpl="outerURL">
		<portlet:param name="jspPage" value="/admin/institutionList.jsp" />
	</liferay-portlet:renderURL>
	<liferay-portlet:renderURL varImpl="innerURL">
		<portlet:param name="jspPage" value="/admin/institutionList.jsp" />
	</liferay-portlet:renderURL>

	<portlet:actionURL name="addInstitution" var="addInstitutionURL">
		<portlet:param name="backURL" value='<%=String.valueOf(PortalUtil.getCurrentCompleteURL(request))%>' />
	</portlet:actionURL>
	<portlet:actionURL name="addSubInstitution" var="addSubInstitutionURL">
		<portlet:param name="backURL" value='<%=String.valueOf(PortalUtil.getCurrentCompleteURL(request))%>' />
	</portlet:actionURL>
	<portlet:actionURL name="updateInstitution" var="updateInstitutionURL">
		<portlet:param name="backURL" value='<%=String.valueOf(PortalUtil.getCurrentCompleteURL(request))%>' />
	</portlet:actionURL>
	<portlet:actionURL name="updateSubInstitution" var="updateSubInstitutionURL">
		<portlet:param name="backURL" value='<%=String.valueOf(PortalUtil.getCurrentCompleteURL(request))%>' />
	</portlet:actionURL>
<div class="noresponsive">
		<%--INSTITUTION LIST START--%>
		<c:if test='<%= permissionAdmin %>'>
			<aui:fieldset column="false" label="<%=pageName%>" cssClass="add-institution" >
				<aui:layout cssClass="aist">
						<aui:fieldset>
							<aui:form action="<%= addInstitutionURL %>" name="fm">
								<aui:input name="institution" label="institution" required="true" inlineField="true"/>
	            				<aui:select name="serverselect" id="select-streamer" label="streaming-server-name" inlineField="true">
									<%
										for(Host host : hostList){
											if (host.getDefaultHost() > 0) {
											%>
											<aui:option label="default" value="<%= host.getHostId() %>"></aui:option>
											<%			
											} else {
												%>
												<aui:option label="<%= host.getName() %>" value="<%= host.getHostId() %>"></aui:option>
												<%
											}
								 		} 
								 	%>
					            </aui:select>
	           				 	<aui:input name="order" label="order" inlineField="true" value='<%= maxOrder %>'/>
					            <aui:input name='institutionId' type='hidden' inlineField="true" value='<%= ParamUtil.getString(renderRequest, "institutionId") %>'/>
					            <aui:input name='parent' type='hidden' inlineField="true" value='<%= rootId %>'/>
								<aui:button type="submit" value="add" ></aui:button>
							</aui:form>
					     </aui:fieldset>
				</aui:layout>
			</aui:fieldset>
		</c:if>
		<%--INSTITUTION LIST END--%>
	
		<%--TOP LEVEL INSTITUTIONS START--%>
			<liferay-ui:search-container searchContainer="<%= searchInstitutionContainer %>" curParam ="curOuter" orderByType="asc" emptyResultsMessage="there-are-no-institutions" delta="5" iteratorURL="<%= outerURL %>" deltaConfigurable="true">
				<liferay-ui:search-container-results results="<%=InstitutionLocalServiceUtil.getByParentId(rootId)%>" total="<%=InstitutionLocalServiceUtil.getByParentId(rootId).size()%>" />
				<liferay-ui:search-container-row className="de.uhh.l2g.plugins.model.Institution" modelVar="institution" keyProperty="institutionId"  escapedModel="<%= false %>" indexVar="i">
		        <liferay-ui:search-container-column-text name="institution" cssClass="toplevel-institutions">
				        <%
				 			ResultRow container_row = (ResultRow)request.getAttribute("curOuter");
				 			//Institution institution_row = (Institution)row.getObject();
				 			//long institution_id = institution.getInstitutionId();
				 			String id_row = "Inst"+String.valueOf(institution.getInstitutionId());
				 			String curParam_row = "curInner"+String.valueOf(institution.getInstitutionId());
				 			long outerOrder = institution.getSort();
				 			Host curHost = Institution_HostLocalServiceUtil.getByInstitutionId(institution.getInstitutionId());
 							String curHostName = AdminStreamerManagement.DEFAULT_STREAMER;
				 			if (curHost != null && curHost.getDefaultHost() < 1 ) curHostName = curHost.getName();
				 			int subInstitutionMax = InstitutionLocalServiceUtil.getMaxSortByParentId(institution.getPrimaryKey())+1;
				 		%>
							<portlet:actionURL name="deleteInstitution" var="deleteInstitutionURL">
								<portlet:param name="outerListInstitutionId" value='<%= (new Long(institution.getPrimaryKey())).toString() %>' />
								<portlet:param name="institutionId" value='<%= (new Long(institutionId)).toString() %>' />
								<portlet:param name="backURL" value="<%=String.valueOf(outerURL)%>"/>
							</portlet:actionURL>
							
				 			<aui:fieldset>
		 						<aui:form action="<%= updateInstitutionURL %>" name="<portlet:namespace />fm">
									<aui:input name="outerListInstitution" label="institution-name" inlineField="true" value = "<%= institution.getName() %>" />
									<aui:input name="outerListOrder" label="order" inlineField="true" value='<%= institution.getSort() %>'/>
									<%-- Only display streamer if user is allowed to view host and institution is child of top level --%>
									<c:if test='<%= institution.getParentId() == rootId %>'>
										<aui:input name="outerListStreamer" label="host-name" inlineField="true" value = "<%= curHostName %>" cssClass="field"/>
									</c:if>
									<aui:input name="outerListInstitutionId" type='hidden' inlineField="true" value = "<%= institution.getPrimaryKey() %>"/>
									<aui:input name="outerListHostId" type='hidden' inlineField="true" value = "<%= curHost.getPrimaryKey() %>"/>
									<aui:input name='institutionId' type='hidden' inlineField="true" value='<%= ParamUtil.getString(renderRequest, "institutionId") %>'/>
									<aui:button type="submit" value="edit"></aui:button>
						 		</aui:form>
								<c:if test='<%=  InstitutionLocalServiceUtil.getLockingElements(institution.getInstitutionId()) < 1 %>'>
									<aui:button cssClass="subdelete" name="delete" value="delete" type="button" href="<%=deleteInstitutionURL.toString() %>" />
								</c:if>
							</aui:fieldset>
					 		<%--SUB INSTITUTIONS--%>
					 		
							<liferay-ui:panel defaultState="closed" extended="false" id="<%= id_row %>" persistState="true" title="sub-institutions" cssClass="sub-inst">
						 			<aui:fieldset>
										<aui:form action="<%= addSubInstitutionURL %>" name="fm">
											<aui:input name="subInstitution" label="sub-institution-name" inlineField="true" />
											<aui:input name="subInstitutionOrder" label="order" inlineField="true" value='<%= subInstitutionMax  %>'/>
											<aui:input name='subInstitutionParentId' type='hidden' inlineField="true" value='<%= institution.getPrimaryKey() %>'/>
											<aui:button type="submit" value="add"/>
							 			</aui:form>
									</aui:fieldset>
						
								<liferay-ui:search-container searchContainer="<%= searchSubInstitutionContainer %>" curParam ="<%=curParam_row%>" orderByType="asc" emptyResultsMessage="there-are-no-institutions" iteratorURL="<%= innerURL %>" delta="100" deltaConfigurable="true" >
									<liferay-ui:search-container-results results="<%=InstitutionLocalServiceUtil.getByParentId(institution.getPrimaryKey())%>" total="<%=InstitutionLocalServiceUtil.getByParentId( institution.getPrimaryKey()).size()%>" />
										<liferay-ui:search-container-row className="de.uhh.l2g.plugins.model.Institution" modelVar="subInstitution" rowVar="thisRow" keyProperty="institutionId"  escapedModel="<%=false%>" indexVar="j">
										<c:choose>
											<c:when  test='<%= true %>'> 
							        			<liferay-ui:search-container-column-text name="institution" >
									        			<portlet:actionURL name="deleteSubInstitution" var="deleteSubInstitutionURL">
										 					<portlet:param name="innerListInstitutionId" value='<%= (new Long(subInstitution.getPrimaryKey())).toString() %>' />
										 					<portlet:param name="innerListInstitutionParentId" value='<%= (new Long(institution.getPrimaryKey())).toString() %>' />
															<portlet:param name="backURL" value="<%=String.valueOf(innerURL) %>"/>
														</portlet:actionURL>
													
														<aui:fieldset>
															<aui:form action="<%= updateSubInstitutionURL %>" name="fm">
																<aui:input name="innerListInstitution" label="sub-institution-name" inlineField="true" value = "<%= subInstitution.getName() %>" />
																<aui:input cssClass="smallInput" name="innerListOrder" label="order" inlineField="true" value='<%= subInstitution.getSort() %>'/>
																<aui:input name="innerListInstitutionId" type='hidden' inlineField="true" value = "<%= subInstitution.getInstitutionId() %>"/>
																<aui:button type="submit" value="edit"></aui:button>
															</aui:form>
															<c:if test='<%=  InstitutionLocalServiceUtil.getLockingElements(subInstitution.getInstitutionId()) < 1 %>'>
																	<aui:button cssClass="maindelete" name="delete" value="delete" type="button" href="<%=deleteSubInstitutionURL.toString() %>" />
															</c:if>
														</aui:fieldset>
						        				</liferay-ui:search-container-column-text>
						        			</c:when>
											<c:otherwise>
												<liferay-ui:message key="<%= subInstitution.getName() %>"></liferay-ui:message>
											</c:otherwise>
										</c:choose>
					        		</liferay-ui:search-container-row>
					        	<liferay-ui:search-iterator searchContainer="<%= searchSubInstitutionContainer %>" />
								</liferay-ui:search-container>
							</liferay-ui:panel>
						<%--END: SUB INSTUTIONS--%>
					</liferay-ui:search-container-column-text>
		    	</liferay-ui:search-container-row>
		   	 	<liferay-ui:search-iterator searchContainer="<%=searchInstitutionContainer%>" />
		</liferay-ui:search-container>
	<%--TOP LEVEL INSTITUTIONS END--%>
<%--INSTITUTIONS END--%>
</div>