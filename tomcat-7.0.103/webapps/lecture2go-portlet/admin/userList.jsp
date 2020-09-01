<%@include file="/init.jsp"%>
<liferay-ui:error key="host-or-institution-error" message="host-or-institution-error"/>
<%
	long groupId = themeDisplay.getLayout().getGroupId();
	String name = User.class.getName();
	User u = UserLocalServiceUtil.getUser(new Long (request.getRemoteUser()));
	AdminUserManagement aum = new AdminUserManagement();
	aum.initL2goRoles(u,renderRequest); //init roles
	List<Role> l2goRoles = aum.getL2GoRoles(u);
	String keywords = ServletRequestUtils.getStringParameter(request, "keywords", "");
	//update role id if keword has been set!
	Long roleId = ServletRequestUtils.getLongParameter(request, "roleId", 0);
	if(!keywords.isEmpty()) roleId = new Long(0);
	//
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("roleId", roleId+"");
	//
	String portletURLString = portletURL.toString();
	UserSearchContainer userSearchContainer = new UserSearchContainer(renderRequest, portletURL);	
	UserDisplayTerms displayTerms = (UserDisplayTerms)userSearchContainer.getDisplayTerms();
	//	
	String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
%>

<!-- if user has pending role, load the selectUserInstitution.jsp instead of the list -->
<c:choose>
	<c:when test = "<%=permissionProducerPending %>">
		<liferay-util:include 
		  page="/admin/selectUserInstitution.jsp" 
		  servletContext="<%= application %>"
		/>
	</c:when>
	<c:when test= "<%=permissionAdmin %>">
		
		<portlet:renderURL var="allRoles"><portlet:param name="jspPage" value="/admin/userList.jsp" /></portlet:renderURL>
		<div class="noresponsive">
			<label class="edit-video-lable"><%=pageName%></label>
			<aui:fieldset helpMessage="choose-filter" column="true" cssClass="list">
				<form  action="<%= allRoles.toString() %>" method="post">
					<select name="<portlet:namespace/>roleId" label="" onChange="submit();">
						<option value=""><liferay-ui:message key="select-l2go-role"/></option>
						<%for (int i = 0; i < l2goRoles.size(); i++) {
								if(l2goRoles.get(i).getRoleId()==roleId){%>
									<option value='<%=l2goRoles.get(i).getRoleId()%>' selected="true"><%=l2goRoles.get(i).getName()%></option>
								<%}else{%>
									<option value='<%=l2goRoles.get(i).getRoleId()%>'><%=l2goRoles.get(i).getName()%></option>
								<%}					
						}%>
					</select>
				</form>
			</aui:fieldset>
			
			<aui:form action="<%= portletURLString %>" method="post" name="fm">
				<liferay-ui:search-container emptyResultsMessage="no-l2go-roles-found" searchContainer="<%= userSearchContainer %>">
						
						<div id="modifiedSearch">
						<liferay-ui:search-form page="/admin/searchUsers.jsp" servletContext="<%= application %>" />
						</div>
						
						<liferay-ui:search-container-results 
							results="<%= UserSearchHelper.getUser(displayTerms,userSearchContainer.getStart(), userSearchContainer.getEnd()) %>" 
							total="<%= UserSearchHelper.getTotalUserCount(displayTerms,userSearchContainer.getStart(), userSearchContainer.getEnd()) %>"
						/>
				
					<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usr">
						<portlet:actionURL name="viewRole" var="editURL">
							<portlet:param name="userId" value="<%= String.valueOf(usr.getUserId())%>" />
							<portlet:param name="backURL" value="<%=String.valueOf(portletURL)%>"/>
						</portlet:actionURL>
						<liferay-ui:search-container-column-text name="name">
						  <div class="adminrow wide">
							<div class="admintile wide">
								<strong><%=usr.getFullName()%></strong>
								<br/>
								<%
								List<Role> roles = usr.getRoles();
								String n = "";
								for (int i = 0; i < roles.size(); i++) {
									//check for l2g role
									String rn = roles.get(i).getName();
									if(rn.contains("L2Go Coordinator")){
										long fId = new Long(0);
										try{ fId=CoordinatorLocalServiceUtil.getCoordinator(usr.getUserId()).getInstitutionId(); }catch (Exception e){}
										String fN = "";
										try{
											fN= InstitutionLocalServiceUtil.getInstitution(fId).getName(); 
											n+= LanguageUtil.get(pageContext, "coordinator-for") +" "+ fN+"<br/>";
										}catch (Exception e){}
									}
									if(rn.contains("L2Go Producer")){
										long fId = new Long(0);
										try{fId = ProducerLocalServiceUtil.getProducer(usr.getUserId()).getInstitutionId();}catch (Exception e){}
										String fN = "";
										try{
											fN= InstitutionLocalServiceUtil.getInstitution(fId).getName(); 
											n+= fN+"<br/>";
										}catch (Exception e){}
									}
									if(!rn.contains("L2Go Producer") && !rn.contains("L2Go Coordinator")){
										n+=rn+"<br/>";
									}
								}%>
							<%=n%>
							</div>
							<div class="admintile wide icons">
								<%if(permissionAdmin){
									%>
									 <a href="<%=editURL.toString()%>">
							   		 	<span class="icon-large icon-pencil"></span>
									 </a>			
									<%
								}else{
									if(new Lecture2GoRoleChecker().isProducer(usr) || new Lecture2GoRoleChecker().isStudent(usr)){
									 %>
									 <a href="<%=editURL.toString()%>">
							   		 	<span class="icon-large icon-pencil"></span>
									 </a>
									 <%
									}
								 }%>
							</div>
						</div>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
				
					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</aui:form>
		</div>
	
	</c:when>
</c:choose>
