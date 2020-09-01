<%@page import="de.uhh.l2g.plugins.model.impl.InstitutionImpl"%>
<%@page import = "com.liferay.portal.kernel.util.WebKeys" %>
<%@page import = "com.liferay.portal.theme.ThemeDisplay" %>

<%@include file="/init.jsp"%>
 
<%
	String companyName = company.getName();
	String portalURL = PrefsPropsUtil.getString(company.getCompanyId(),PropsKeys.DEFAULT_LANDING_PAGE_PATH);

	// defines how many terms and creators are shown initially	
	int maxTerms	= 4;

	// get all filter-requests
	Long parentInstitutionId 	= ServletRequestUtils.getLongParameter(request, "parentInstitutionId", 0);
	Long institutionId 			= ServletRequestUtils.getLongParameter(request, "institutionId", 0);
	Long termId 				= ServletRequestUtils.getLongParameter(request, "termId", 0);
	Long categoryId 			= ServletRequestUtils.getLongParameter(request, "categoryId", 0);
	
	// TODO: creators are removed, the creatorId temporary exists for compatibility
	Long creatorId 				= ServletRequestUtils.getLongParameter(request, "creatorId", 0);

	String searchQuery			= ServletRequestUtils.getStringParameter(request, "searchQuery", "");
	//XSS anti scripting required!
	HTMLFilter hF = new HTMLFilter();
	searchQuery = hF.filter(searchQuery);
	
	// filters are set if they have a value different than 0
	boolean hasInstitutionFiltered 			= (institutionId != 0);
	boolean hasParentInstitutionFiltered 	= (parentInstitutionId != 0);
	boolean hasTermFiltered 				= (termId != 0);
	boolean hasCategoryFiltered				= (categoryId != 0);
	boolean isSearched						= (searchQuery.trim().length()>0);

	// the institution is dependent on the parentinstitution, do not allow institution-filters without parentinstitution-filter
	if (hasInstitutionFiltered && !hasParentInstitutionFiltered) {
		institutionId = new Long(0);
	}

	// get filtered lectureseries and single videos
	List<Lectureseries> reqLectureseries = LectureseriesLocalServiceUtil.getFilteredByInstitutionParentInstitutionTermCategoryCreatorSearchString(institutionId, parentInstitutionId, termId, categoryId, creatorId, searchQuery);

	// differentiate returned lectureseries in real lectureseries and fake video lectureseries (openAccessVideoId is negative on videos)
	ArrayList<Long> lectureseriesIds = new ArrayList<Long>();
	ArrayList<Long> videoIds = new ArrayList<Long>();
	long id;
 	for (Lectureseries lecture : reqLectureseries) {
		id = lecture.getLectureseriesId();
		if (lecture.getLatestOpenAccessVideoId() < 0) {
			videoIds.add(id);
		} else {
			lectureseriesIds.add(id);
		}
	} 
	
	// get the institutions, parentinstitutuons, terms, categories and creators which are part of the dataset. those are displayed so the user can do further filtering
	List<Institution> presentParentInstitutions 	= new ArrayList<Institution>();
	List<Institution> presentInstitutions 			= new ArrayList<Institution>();
	List<Term> presentTerms 						= new ArrayList<Term>();
	List<Creator> presentCreators 					= new ArrayList<Creator>();
	List<Category> presentCategories 				= new ArrayList<Category>();

	// if a filter is selected, only show the selected one else show all
 	if (hasParentInstitutionFiltered) {
		presentParentInstitutions.add(InstitutionLocalServiceUtil.getById(parentInstitutionId));
	} else {
		if (FeatureManager.hasInstitutionCatalogFilter()) {
			presentParentInstitutions = InstitutionLocalServiceUtil.getInstitutionsFromLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds);
		}
	} 
	
 	if (hasParentInstitutionFiltered && hasInstitutionFiltered) {
		presentInstitutions.add(InstitutionLocalServiceUtil.getById(institutionId));
	} else {
		if (FeatureManager.hasSubInstitutionCatalogFilter()) {
			presentInstitutions = InstitutionLocalServiceUtil.getInstitutionsFromLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds, parentInstitutionId);
		}
	}
	
	if (hasTermFiltered) {
		presentTerms.add(TermLocalServiceUtil.getById(termId));
	} else {
		if (FeatureManager.hasTermsCatalogFilter()) {
			presentTerms = TermLocalServiceUtil.getTermsFromLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds);
		}
	}
	
	if (hasCategoryFiltered) {
		presentCategories.add(CategoryLocalServiceUtil.getById(categoryId));
	} else {
		if (FeatureManager.hasCategoryCatalogFilter()) {
			presentCategories = CategoryLocalServiceUtil.getCategoriesFromLectureseriesIdsAndVideoIds(lectureseriesIds, videoIds);
		}
	}
	
	List<Lectureseries> tempLectureseriesList = new ArrayList();
	
	PortletURL portletURL = renderResponse.createRenderURL();
	
	// set parameter for search iterator or possible backURL
	portletURL.setParameter("parentInstitutionId", parentInstitutionId.toString());
	portletURL.setParameter("institutionId", institutionId.toString());
	portletURL.setParameter("termId", termId.toString());
	portletURL.setParameter("categoryId", categoryId.toString());
	portletURL.setParameter("creatorId", creatorId.toString());
	portletURL.setParameter("searchQuery", searchQuery);
	
	// set page context for better use in taglibs
	pageContext.setAttribute("hasParentInstitutionFiltered", hasParentInstitutionFiltered);
	pageContext.setAttribute("hasInstitutionFiltered", hasInstitutionFiltered);
	pageContext.setAttribute("hasTermFiltered", hasTermFiltered);
	pageContext.setAttribute("hasCategoryFiltered", hasCategoryFiltered);
	pageContext.setAttribute("hasManyTerms", presentTerms.size() > maxTerms);
	
	boolean resultSetEmpty = true;
	if(presentParentInstitutions.size()>0||presentInstitutions.size()>0||presentTerms.size()>0||presentCategories.size()>0){
		resultSetEmpty=false;
	}
	
	Institution insti = new InstitutionImpl();
	Institution pInst = new InstitutionImpl();
	Institution rInst = new InstitutionImpl();
	String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
	%>
	<portlet:actionURL var="backURL0" name="addFilter">
		<portlet:param name="jspPage" value="/guest/videosList.jsp" />
		<portlet:param name="parentInstitutionId" value="0"/>
		<portlet:param name="institutionId" value="0"/>
		<portlet:param name="termId" value="0"/>
		<portlet:param name="categoryId" value="0"/>
		<portlet:param name="creatorId" value="0"/>
	</portlet:actionURL>
		
	<div class="path-wide">
	<A HREF=<%=portalURL%>><%=companyName %></A><span class="uhh-icon-arrow-right"></span> 
	<A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="page-name"/>: <%=pageName %>'><%=pageName %></A>
	<span class="uhh-icon-arrow-right"></span> 
	<%
	try{
		rInst=InstitutionLocalServiceUtil.getRoot(); 
		if(rInst.getName().length()>0){%>
		<A HREF="<%=backURL0%>"><%=rInst.getName() %></A>
	<%} 
	}catch(Exception e){}

	try{
		pInst=InstitutionLocalServiceUtil.getById(parentInstitutionId);
		if(pInst.getName().length()>0){%>
		<portlet:actionURL var="backURL1" name="addFilter">
			<portlet:param name="jspPage" value="/guest/videosList.jsp" />
			<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
			<portlet:param name="institutionId" value="0"/>
			<portlet:param name="termId" value="0"/>
			<portlet:param name="categoryId" value="0"/>
			<portlet:param name="creatorId" value="0"/>
		</portlet:actionURL>
		<span class="uhh-icon-arrow-right"></span> 
		<A HREF="<%=backURL1%>" aria-label='<liferay-ui:message key="parent-institution-name"/>: <%=pInst.getName() %>'><%=pInst.getName() %></A>
	<%}		
	}catch(Exception e){}

	try{
		insti=InstitutionLocalServiceUtil.getById(institutionId);
		if(insti.getName().length()>0){%>
			<portlet:actionURL var="backURL2" name="addFilter">
				<portlet:param name="jspPage" value="/guest/videosList.jsp" />
				<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
				<portlet:param name="institutionId" value="<%=insti.getInstitutionId()+\"\"%>"/>
				<portlet:param name="termId" value="0"/>
				<portlet:param name="categoryId" value="0"/>
				<portlet:param name="creatorId" value="0"/>
			</portlet:actionURL>	
			<span class="uhh-icon-arrow-right"></span> 
			<A HREF="<%=backURL2%>" aria-label='<liferay-ui:message key="institution-name"/>: <%=insti.getName() %>'><%=insti.getName() %></A> 
		<%}		
	}catch(Exception e){}
%>
</div>
<h1><liferay-ui:message key="video-catalog"/></h1>
<div class="catalogue-container">
	<div class="row-fluid">
	<%if(!resultSetEmpty){ %>
		<div class="span3">
			<liferay-ui:panel-container>
				<!-- 	parentinstitution filter -->
				<%if(presentParentInstitutions.size()>0){ %>
				<liferay-ui:panel id="filter-institution" extended="false" defaultState="collapsed" title="institution" cssClass='${hasParentInstitutionFiltered ? "filtered" : "notFiltered"}'>
					<ul>
					<c:forEach items="<%=presentParentInstitutions %>" var="parentInstitution">
						<portlet:actionURL var="filterByParentInstitution" name="addFilter">
							<portlet:param name="jspPage" value="/guest/videosList.jsp" />
							<portlet:param name="parentInstitutionId" value='${hasParentInstitutionFiltered ? "0" : parentInstitution.institutionId}'/>
							<portlet:param name="institutionId" value="<%=institutionId.toString() %>"/>				
							<portlet:param name="termId" value="<%=termId.toString() %>"/>
							<portlet:param name="categoryId" value="<%=categoryId.toString() %>"/>
							<portlet:param name="creatorId" value="<%=creatorId.toString() %>"/>
							<portlet:param name="searchQuery" value="<%=searchQuery %>"/>	
						</portlet:actionURL>
						<li class="filter-menu"><a href="${filterByParentInstitution}"><div class="filter-menu-link"><span ${hasParentInstitutionFiltered ? 'class="icon-large icon-remove"' : ''}></span>${parentInstitution.name}</div></a></li>
					</c:forEach>
					</ul>
				</liferay-ui:panel>
				<%}%>
				
			 	<!-- 	institution filter  -->
				<c:if test="${hasParentInstitutionFiltered}">
				<%if(presentInstitutions.size()>0){ %>
				<liferay-ui:panel id="filter-subinstitution" extended="false" defaultState="collapsed" title="sub-institution" cssClass='${hasInstitutionFiltered ? "filtered" : "notFiltered"}'>
					<ul>
					<c:forEach items="<%=presentInstitutions %>" var="institution">
						<portlet:actionURL var="filterByInstitution" name="addFilter">
							<portlet:param name="jspPage" value="/guest/videosList.jsp" />
							<portlet:param name="parentInstitutionId" value="<%=parentInstitutionId.toString() %>"/>
							<portlet:param name="institutionId" value='${hasInstitutionFiltered ? "0" : institution.institutionId}'/>
							<portlet:param name="termId" value="<%=termId.toString() %>"/>
							<portlet:param name="categoryId" value="<%=categoryId.toString() %>"/>
							<portlet:param name="creatorId" value="<%=creatorId.toString() %>"/>
							<portlet:param name="searchQuery" value="<%=searchQuery %>"/>	
						</portlet:actionURL>
						<li class="filter-menu"><a href="${filterByInstitution}"><div class="filter-menu-link"><span ${hasInstitutionFiltered ? 'class="icon-large icon-remove"' : ''}></span>${institution.name}</div></a></li>
					</c:forEach>
					</ul>
				</liferay-ui:panel>
				<%}%>
				</c:if>
				
				<!-- 	terms filter -->
				<%if(presentTerms.size()>0){%>
				<liferay-ui:panel id="filter-term" extended="false" defaultState="collapsed" title="term" cssClass='${hasTermFiltered ? "filtered" : "notFiltered"}'>
					<ul class="terms">
					<c:forEach items="<%=presentTerms %>" var="term">
						<portlet:actionURL var="filterByTerm" name="addFilter">
							<portlet:param name="jspPage" value="/guest/videosList.jsp" />
							<portlet:param name="institutionId" value="<%=institutionId.toString() %>"/>
							<portlet:param name="parentInstitutionId" value="<%=parentInstitutionId.toString() %>"/>	
							<portlet:param name="termId" value='${hasTermFiltered ? "0" : term.termId}'/>
							<portlet:param name="categoryId" value="<%=categoryId.toString() %>"/>
							<portlet:param name="creatorId" value="<%=creatorId.toString() %>"/>
							<portlet:param name="searchQuery" value="<%=searchQuery %>"/>	
						</portlet:actionURL>
						<li class="filter-menu"><a href="${filterByTerm}"><div class="filter-menu-link"><span ${hasTermFiltered ? 'class="icon-large icon-remove"' : ''}></span><c:choose><c:when test="${term.termName==''}"><liferay-ui:message key="no-term"/></c:when><c:otherwise>${term.termName}</c:otherwise></c:choose></div></a></li>
					</c:forEach>
					</ul>
					<c:if test="${hasManyTerms}">
						<a href="#" id="loadMoreTerms" aria-label='<liferay-ui:message key="load-more-terms"/>'><liferay-ui:message key="more"/></a>
					</c:if>
				</liferay-ui:panel>
				<%}%>
			
				
				<!-- category filter -->
				<%if(presentCategories.size()>0){%>
				<liferay-ui:panel id="filter-category" extended="false" defaultState="collapsed" title="category" cssClass='${hasCategoryFiltered ? "filtered" : "notFiltered"}'>
					<ul>
					<c:forEach items="<%=presentCategories %>" var="category">
			    		<portlet:actionURL var="filterByCategory" name="addFilter">
							<portlet:param name="jspPage" value="/guest/videosList.jsp" />
							<portlet:param name="institutionId" value="<%=institutionId.toString() %>"/>
							<portlet:param name="parentInstitutionId" value="<%=parentInstitutionId.toString() %>"/>
							<portlet:param name="termId" value="<%=termId.toString() %>"/>
							<portlet:param name="categoryId" value='${hasCategoryFiltered ? "0" : category.categoryId}'/>
							<portlet:param name="creatorId" value="<%=creatorId.toString() %>"/>	
							<portlet:param name="searchQuery" value="<%=searchQuery %>"/>	
						</portlet:actionURL>
						<li class="filter-menu"><a href="${filterByCategory}" aria-label='<liferay-ui:message key="category-filter"/>'><div class="filter-menu-link"><span ${hasCategoryFiltered ? 'class="icon-large icon-remove"' : ''}></span>${category.name}</div></a></li>
					</c:forEach>
					</ul>
				</liferay-ui:panel>
				<%}%>
			</liferay-ui:panel-container>
		<!-- span3 -->
		</div>
	<%}%>

<%if(!resultSetEmpty){%><div class="span9"><%}%>
<%if(resultSetEmpty){%><div class="none"><%}%>
		
<portlet:actionURL var="filterBySearchQuery" name="addFilter">
	<portlet:param name="jspPage" value="/guest/videosList.jsp" />
	<portlet:param name="institutionId" value="0"/>
	<portlet:param name="parentInstitutionId" value="0"/>
	<portlet:param name="termId" value="0"/>
	<portlet:param name="categoryId" value="0"/>
	<portlet:param name="creatorId" value="0"/>
	<portlet:param name="jspPage" value="/guest/videosList.jsp" />
</portlet:actionURL>		

		
<liferay-ui:search-container emptyResultsMessage="no-lectureseries-found" delta="10" iteratorURL="<%=portletURL %>" >
	<liferay-ui:search-container-results>
		<%
			tempLectureseriesList = reqLectureseries;
			results = ListUtil.subList(tempLectureseriesList, searchContainer.getStart(), searchContainer.getEnd());
			total = tempLectureseriesList.size();
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
		%>
	</liferay-ui:search-container-results>
	
	<liferay-ui:search-container-row className="de.uhh.l2g.plugins.model.Lectureseries" keyProperty="lectureseriesId" modelVar="lectser">
		<%
			String oId = "";
			boolean isVideo = false;
			Video vidDummy = new VideoImpl();
			if(lectser.getLatestOpenAccessVideoId()<0){
				isVideo = true;
				try {
					vidDummy = VideoLocalServiceUtil.getVideo(lectser.getLectureseriesId());
				} catch (Exception e) {
					
				}
				oId = vidDummy.getVideoId()+"";
			}else{
				oId = lectser.getLectureseriesId()+"";
				try {
					vidDummy = VideoLocalServiceUtil.getVideo(lectser.getPreviewVideoId());
				} catch (Exception e) {
					
				}
			}
			int videoCount = lectser.getNumberOfOpenAccessVideos();
			List<Creator> cl = CreatorLocalServiceUtil.getCreatorsByLectureseriesId(lectser.getLectureseriesId());
			ListIterator<Creator> cli = cl.listIterator();
			List<Video> vl = new ArrayList<Video>();
			ListIterator<Video> vli = vl.listIterator();

			if (videoCount > 0 && isSearched) {
				// get videos by search word and lecture series
				vl = VideoLocalServiceUtil.getBySearchWordAndLectureseriesId(searchQuery, new Long(oId));
			} else {
				// get all videos of the lecture series
				vl = VideoLocalServiceUtil.getByLectureseriesAndOpenaccess(new Long(oId), 1);
			}
			vli = vl.listIterator();
		%>
		<liferay-ui:search-container-column-text>
						<portlet:actionURL name="viewOpenAccessVideo" var="view1URL">
							<portlet:param name="objectId" value="<%=oId%>"/>
							<%if(isVideo){%><portlet:param name="objectType" value="v"/><%}%>
							<%if(!isVideo){%><portlet:param name="objectType" value="l"/><%}%>
						</portlet:actionURL>
						<%
							if(videoCount==0){
								if(isVideo){
									%>
								<div class="videotile wide" onClick="window.location='<%=view1URL%>'">
									<div class="video-image-wrapper">
									   <img class="video-image-big" src="<%=vidDummy.getImageMedium()%>" alt="<liferay-ui:message key="thumbnail"/> - <%=vidDummy.getTitle() %>" />
									</div>
									<div class="video-content-wrapper">
										<div class="video-content">
											<%
												String term ="";
												try{term = TermLocalServiceUtil.getTerm(lectser.getTermId()).getTermName();}catch(Exception e){}
											%>
											<span class="term-of-creation"><%=term%></span>
											
									        <h2 class="lectureseries-title dot-ellipsis dot-resize-update">
									        	<%
									        	String lectName = "";
									        	try{lectName=lectser.getName();}catch(Exception e){}
									        	%>
									        	<a href="<%=view1URL%>" aria-label='<liferay-ui:message key="lectureseries-title"/>: <%=lectName%>'><%=lectName%></a>
									        </h2>
												
												<div class="allcreators">
													<%=vidDummy.getLinkedCreators() %>
												</div>
											</div>
											<div class="video-content-footer">
																		
										        <div class="labels">
										          <%
										        	String cat = "";
										        	List<Video_Institution> vi = Video_InstitutionLocalServiceUtil.getByVideo(vidDummy.getVideoId());
										            try{
										            	Long cId = Video_CategoryLocalServiceUtil.getByVideo(lectser.getLectureseriesId()).get(0).getCategoryId();
										            	cat ="<a href='/l2go/-/get/0/0/"+cId+"/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"category")+": "+CategoryLocalServiceUtil.getById(cId).getName()+"'>"+CategoryLocalServiceUtil.getById(cId).getName()+"</a>";
	
										            }catch(Exception e){
										            	System.out.print(e);
										            }
										          %>
										          <span class="label label-light2"><%=cat%></span>
										          <%
										          		String instLink= "";
										         	 	try{
															Institution inst = InstitutionLocalServiceUtil.getById(vi.get(0).getInstitutionId());
															instLink="<a href='/l2go/-/get/"+inst.getInstitutionId() + "/" + inst.getParentId() + "/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution-name")+": " +inst.getName() + "'>" + inst.getName() + "</a>"; 
											          		
										          		}catch(Exception e){
										          			// no institution for the video, use the root institution
										          			Institution rootInst=InstitutionLocalServiceUtil.getById(vidDummy.getRootInstitutionId());
															instLink="<a href='/l2go/-/get/0/" + rootInst.getInstitutionId() + "/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"root-institution-name")+": " + rootInst.getName() +"'>" + rootInst.getName() + "</a>"; 
										          		}
											          %>
											         	 <span class="label label-light2"><%=instLink%></span>
									          		<%
										          %>
										        </div>
									        </div>	
								        </div>
							    	<%									
								}
							}else{
								// multiple videos in lecture series
								%>
										<div class="videotile wide">
									        <div class="video-image-wrapper">
									          <img class="video-image-big layered-paper darker" src="<%=vidDummy.getImageMedium()%>" alt="<liferay-ui:message key="thumbnail"/> - <%=vidDummy.getTitle() %>" />
											  <span class="badge"><%=videoCount%></span>
									          <span class="tri"></span>
									          <span class="overlay"></span>
									        </div>
									     
									<div class="video-content-wrapper">
										<div class="video-content">
											<%
												String term ="";
												try{term = TermLocalServiceUtil.getTerm(lectser.getTermId()).getTermName();}catch(Exception e){}
											%>
											<span class="term-of-creation"><%=term%></span>
										        
											<h2 class="lectureseries-title dot-ellipsis dot-resize-update dot-height-40">
									        	<%
									        	String lectName = "";
									        	try{lectName=lectser.getName();}catch(Exception e){}
									        	%>
									        	<a href="<%=view1URL%>" aria-label='<liferay-ui:message key="lectureseries-title"/>'><%=lectName%></a>
									        </h2>
												
												<div class="allcreators">
													<%=CreatorLocalServiceUtil.getCommaSeparatedLinkedCreatorsByLectureseriesIdAndMaxCreators(vidDummy.getLectureseriesId(), 3)%>
												</div>
											</div>
											
											<div class="video-content-footer">
										        <button title="<liferay-ui:message key='open-video-list'/>" aria-label="<liferay-ui:message key='open-video-list'/>" id="<%="b"+oId%>" >
													<span class="lfr-icon-menu-text">
														<i class="icon-large icon-chevron-down"></i>
													</span>	
												</button>
										        <div class="labels">
										          <%
										        	String cat ="";
										            try{
											        	cat ="<a href='/l2go/-/get/0/0/"+lectser.getCategoryId()+"/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"category")+": "+CategoryLocalServiceUtil.getById(lectser.getCategoryId()).getName()+"'>"+CategoryLocalServiceUtil.getById(lectser.getCategoryId()).getName()+"</a>";
										            }catch(Exception e){}
										        	List<Lectureseries_Institution> li = Lectureseries_InstitutionLocalServiceUtil.getByLectureseries(lectser.getLectureseriesId());
													%>
										          <span class="label label-light2"><%=cat%></span>
										          <%
										          		try{
												        	Institution inst = InstitutionLocalServiceUtil.getById(li.get(0).getInstitutionId());
												        	
															String instLink="<a href='/l2go/-/get/"+inst.getInstitutionId()+"/" + inst.getParentId() + "/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution")+": " + inst.getName() +"'>"+inst.getName()+"</a>"; 
											          		%>
													          <span class="label label-light2"><%=instLink%></span>
											          		<%
										          		}catch(NullPointerException e){
										          			//
										          		}
										          %>
										        </div>
										     </div>
								        </div>
								<%	
							}
						%>
				<!-- videotile  -->
				</div>
				
				<!-- sublist for videos -->
				<%
				String videoDivTitle = "";
				if (videoCount>0) {%>
					<div id="videolist">
						<ul id="<%="p"+oId%>" class="list-group toggler-content-collapsed content">
							<%
							while(vli.hasNext()){
							try {
								Video v =  VideoLocalServiceUtil.getVideo(vli.next().getVideoId());
							
								String vId = v.getVideoId()+"";
	       						int i=0;
							%>
								<portlet:actionURL name="viewOpenAccessVideo" var="vURL">
									<portlet:param name="objectId" value="<%=vId%>"/>
									<portlet:param name="objectType" value="v"/>
								</portlet:actionURL>				
								<li class="videotile small" onClick="window.location='<%=vURL%>'">
										<div class="videotile metainfolist small">
											<div class="video-image-wrapper-small">
												<img class="video-image" src="<%=v.getImageSmall()%>" alt="<liferay-ui:message key="thumbnail"/> - <%=v.getTitle() %>" />
											</div>
										</div>
										<%
		              						String date = "";
		              						String dur = "";
		              						try{ date = v. getSimpleDate().trim();}catch(Exception e){}
		              						try{ dur = v.getDuration().trim().substring(0, 8);}catch(Exception e){}
		              					%>
										<div class="metainfo-small">
		              						<div class="generation-date"><%=date%></div>
											<a href='<%=vURL%>' aria-label='<liferay-ui:message key="video-title"/>'>
												<h3 class="title-small"><%=v.getTitle()%></h3>		   
											</a>           							
			              					<div class="allcreators">		              							
		              							<%=v.getLinkedCreators()%>
		              						</div>
	              						</div>
								</li>
							<%
							} catch (Exception e) {
								
							}
							}%>
							<%if (isSearched && (videoCount>1)) { %>
								<li class="videotile small show-all" onClick="window.location='<%=view1URL%>'">
									<liferay-ui:message key="all-videos"/>
								</li>
							<%} %>
							</ul>
						</div>
				<%}%>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>
		<!-- span9 -->
		</div>
	<!-- row-fluid -->
	</div>
<!-- catalogue-container -->
</div>

<script>
//workaround: as neither DOM-ready nor Liferay.Portlet.ready are working right now, set an interval until the element is there
var checkExist = setInterval(function() {
	if ($('#_lgopenaccessvideos_WAR_lecture2goportlet_lectureseriesesSearchContainer').length) {
		// decode the search query to plain text (via a in cache div)
		var searchQuery = $('<div/>').html('<%= searchQuery %>').text();
		var markOptions = {
			"separateWordSearch": false,
			 "each": function(node){
				 // open the video list of a lectureseries if a search query is found in a video BUT NOT in the lectureseries itself
				 if(!(($(node).closest(".table-cell").find(".videotile.wide").find("mark")).length)){
					// open the video list of a lectureseries if a video is found
					$(node).closest("ul").show();
					$button = $(node).closest(".table-cell").find(".video-content-footer").find("button");
				 	$button.addClass("rotated");
				 	$button.attr("aria-label","<liferay-ui:message key='close-video-list'/>");
					$button.attr("title","<liferay-ui:message key='close-video-list'/>");				
				 }
			
			 }
		};
		if (searchQuery) {
			$("#_lgopenaccessvideos_WAR_lecture2goportlet_lectureseriesesSearchContainer").mark(searchQuery, markOptions);
		}
	   	clearInterval(checkExist);
 	}
}, 100);
</script>

<style type="text/css">
	.aui #breadcrumbs .breadcrumb {
	    display: none;
	}
	.current-page{
		display: none;
	}
</style>  
