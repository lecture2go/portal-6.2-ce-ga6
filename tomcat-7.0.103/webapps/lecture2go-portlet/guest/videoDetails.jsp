<%@include file="/init.jsp"%>

<jsp:useBean id="video" type="de.uhh.l2g.plugins.model.Video" scope="request" />
<jsp:useBean id="relatedVideos" type="java.util.List<de.uhh.l2g.plugins.model.Video>" scope="request" />
<jsp:useBean id="segments" type="java.util.List<de.uhh.l2g.plugins.model.Segment>" scope="request" />
<jsp:useBean id="videoInstitutions" type="java.util.List<de.uhh.l2g.plugins.model.Video_Institution>" scope="request" />
<jsp:useBean id="videoMetadata" type="de.uhh.l2g.plugins.model.Metadata" scope="request" />
<jsp:useBean id="lectureseries" type="de.uhh.l2g.plugins.model.Lectureseries" scope="request" />
<jsp:useBean id="license" type="de.uhh.l2g.plugins.model.License" scope="request" />
<jsp:useBean id="objectType" type="java.lang.String" scope="request" />
<jsp:useBean id="objectId" type="java.lang.String" scope="request" />

<jsp:useBean id="timeStart" type="java.lang.Long" scope="request" />
<jsp:useBean id="timeEnd" type="java.lang.Long" scope="request" />

<portlet:actionURL var="backURL0" name="addFilter">
	<portlet:param name="jspPage" value="/guest/videosList.jsp" />
	<portlet:param name="parentInstitutionId" value="0"/>
	<portlet:param name="institutionId" value="0"/>
	<portlet:param name="termId" value="0"/>
	<portlet:param name="categoryId" value="0"/>
	<portlet:param name="creatorId" value="0"/>
</portlet:actionURL>

<%
	String pageTitle = "";

	if (video.getOpenAccess() == 1) {
		String tags = video.getTags().length() > 0 ? " - " + video.getTags() + " - " : "";
		pageTitle = video.getTitle() + " - " + lectureseries.getName() + " - " + CreatorLocalServiceUtil.getCommaSeparatedCreatorsByVideoIdAndMaxCreators(video.getVideoId(), 200) + " - Universität Hamburg" + tags;
	} else {
		pageTitle = company.getName();
	}

	PortalUtil.setPageTitle(pageTitle, request);

	String companyName = company.getName();
	String portalURL = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_LANDING_PAGE_PATH);

	boolean isCitation2Go = false;
	if (timeStart > 0 && timeEnd > timeStart && video.getCitation2go() == 1)
		isCitation2Go = true;

	String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
	
	// this is necessary because we want to pass the current locale of the user to the method, as the result for the caption label is language specific
	VideoLocalServiceUtil.addTextTracks2VideoWithLanguageLabel(video, themeDisplay.getLocale());
%>

<%if(video.getVideoId()>0){%>
	<%if(video.getAccessPermitted()==1){%>
		<%if(video.getOpenAccess()==1 || video.getOpenAccess()==0){%>
			<div class="col-xs-10 col-md-10">
				<c:if test="${relatedVideos.size()>1}"> <div class="path"></c:if>
			    <c:if test="${relatedVideos.size()<=1}"> <div class="path-wide"></c:if>
			    	<%
			    	if(video.getLectureseriesId()>0){
			    			Lectureseries lec = LectureseriesLocalServiceUtil.getLectureseries(video.getLectureseriesId());	
				    		List<Institution> institutions = InstitutionLocalServiceUtil.getByLectureseriesId(lec.getLectureseriesId(), com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS);
							for(int j=0; j<1; j++){
				    			Institution insti = InstitutionLocalServiceUtil.getById(institutions.get(j).getInstitutionId());    			
				    			Institution pInst = InstitutionLocalServiceUtil.getById(institutions.get(j).getParentId());
				    			Institution rInst = InstitutionLocalServiceUtil.getById(pInst.getParentId());
					    		%>
								<portlet:actionURL var="backURL1" name="addFilter">
									<portlet:param name="jspPage" value="/guest/videosList.jsp" />
									<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
									<portlet:param name="institutionId" value="0"/>
									<portlet:param name="termId" value="0"/>
									<portlet:param name="categoryId" value="0"/>
									<portlet:param name="creatorId" value="0"/>
								</portlet:actionURL>					
								<portlet:actionURL var="backURL2" name="addFilter">
									<portlet:param name="jspPage" value="/guest/videosList.jsp" />
									<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
									<portlet:param name="institutionId" value="<%=insti.getInstitutionId()+\"\"%>"/>
									<portlet:param name="termId" value="0"/>
									<portlet:param name="categoryId" value="0"/>
									<portlet:param name="creatorId" value="0"/>
								</portlet:actionURL>	
								
								<A HREF="<%=portalURL%>"><%=companyName %></A><span class="uhh-icon-arrow-right"></span> 
								<A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="page-name"/>: <%=pageName %>'><%=pageName %></A><span class="uhh-icon-arrow-right"></span> 
								<A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="root-institution-name"/>: <%=rInst.getName() %>'><%=rInst.getName() %></A> <span class="uhh-icon-arrow-right"></span> 
						    	<A HREF="<%=backURL1%>" aria-label='<liferay-ui:message key="parent-institution-name"/>: <%=pInst.getName() %>'><%=pInst.getName() %></A> <span class="uhh-icon-arrow-right"></span> 
						    	<A HREF="<%=backURL2%>" aria-label='<liferay-ui:message key="institution-name"/>: <%=insti.getName() %>'><%=insti.getName() %></A> 
						    	<%if(lec.getLectureseriesId()>0) {%>
					    			<span class="uhh-icon-arrow-right"></span> <SPAN><%=lec.getName()%></SPAN>
					    		<%}%>
						    	<br/>
				    		<%}
				    	//}
			    	}else{
				    	for(int i=0; i<videoInstitutions.size(); i++){
				    		try{
					    		Institution insti = InstitutionLocalServiceUtil.getById(videoInstitutions.get(i).getInstitutionId());    			
					    		Institution pInst = InstitutionLocalServiceUtil.getById(videoInstitutions.get(i).getInstitutionParentId());
					    		Institution rInst = InstitutionLocalServiceUtil.getById(pInst.getParentId());
						    	%>
									<portlet:actionURL var="backURL3" name="addFilter">
										<portlet:param name="jspPage" value="/guest/videosList.jsp" />
										<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
										<portlet:param name="institutionId" value="0"/>
										<portlet:param name="termId" value="0"/>
										<portlet:param name="categoryId" value="0"/>
										<portlet:param name="creatorId" value="0"/>
									</portlet:actionURL>					
									<portlet:actionURL var="backURL4" name="addFilter">
										<portlet:param name="jspPage" value="/guest/videosList.jsp" />
										<portlet:param name="parentInstitutionId" value="<%=pInst.getInstitutionId()+\"\"%>"/>
										<portlet:param name="institutionId" value="<%=insti.getInstitutionId()+\"\"%>"/>
										<portlet:param name="termId" value="0"/>
										<portlet:param name="categoryId" value="0"/>
										<portlet:param name="creatorId" value="0"/>
									</portlet:actionURL>		    	
							    	
   									<A HREF="/"><%=companyName %></A><span class="uhh-icon-arrow-right"></span> 
							    	<A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="page-name"/>: <%=pageName %>'><%=pageName %></A><span class="uhh-icon-arrow-right"></span> 
							    	<A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="root-institution-name"/>: <%=rInst.getName() %>'><%=rInst.getName() %></A> <span class="uhh-icon-arrow-right"></span> 
							    	<%if(pInst.getLevel()>0) {%>
							    	  <A HREF="<%=backURL3%>" aria-label='<liferay-ui:message key="parent-institution-name"/>: <%=pInst.getName() %>'><%=pInst.getName() %></A>
							    	  <span class="uhh-icon-arrow-right"></span> 
							    	<%}%>
							    	<A HREF="<%=backURL4%>" aria-label='<liferay-ui:message key="institution-name"/>: <%=insti.getName() %>'><%=insti.getName() %></A>	
							    	<br/> 
					      		<%
				    		}catch(Exception e){}
				      }  
				      if (videoInstitutions.size()==0){
				    	  Institution rootI = InstitutionLocalServiceUtil.getRoot();
				    	  %>
								<A HREF="/"><%=companyName %></A><span class="uhh-icon-arrow-right"></span> 
							    <A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="page-name"/>: <%=pageName %>'><%=pageName %></A><span class="uhh-icon-arrow-right"></span> 				    	  
							    <A HREF="<%=backURL0%>" aria-label='<liferay-ui:message key="root-institution-name"/>: <%=rootI.getName() %>'><%=rootI.getName() %></A>
							    <br/> 				    	  
				    	  <%
				      }
			    	}
			    	%>
			    </div>
			</div> 
		<%}%>
	
		<h1><liferay-ui:message key="video-catalog"/></h1>
		    
		<div class="col-md-7">
		    <div id="main" >
			  <%
			    String title = video.getTitle();  	
			  	Long lTermId = lectureseries.getTermId();

			  	String series = "";
		  		String t = "";
		  		try{
		  			t = TermLocalServiceUtil.getById(lTermId).getPrefix()+" "+TermLocalServiceUtil.getById(lTermId).getYear();
		  		}catch (Exception e){};
			  		
		  		if (t.trim().length()>0)series = lectureseries.getName() +"("+t+")";
		  		else series= lectureseries.getName();
			  	%>
		       <c:if test="${relatedVideos.size()>1}"><div class="player"></c:if>
			   <c:if test="${relatedVideos.size()<=1}"><div class="player-wide"></c:if>
				<%@ include file="/player/includePlayer.jsp"%>
					<div class="license">
			   			<c:if test="${not empty license}">
			   				<a href="${license.url}" title="${license.fullName}"><liferay-ui:message key="license"/>: ${license.shortIdentifier}</a>   
				   		</c:if>
				   	</div>
			       <div class="views"><liferay-ui:message key="views"/>: ${video.hits}</div>	
					  <c:if test="${relatedVideos.size()>1}"> <div class="meta-video-info"></c:if>
					  <c:if test="${relatedVideos.size()<=1}"> <div class="meta-video-info-wide"></c:if>
					    <h2 class="meta-title">
							<%
								if(isCitation2Go){
									%><div class="c2go-title"><liferay-ui:message key='citation-of'/></div><%
								}
							%>	
						    <%=title%>
					    </h2>
				      	<div class="meta-creators">
										<%
									        String date1 = "";
											String creators = "";
											creators=CreatorLocalServiceUtil.getCommaSeparatedLinkedCreatorsByVideoIdAndMaxCreators(video.getVideoId(), 100);
									        try{ date1 = video.getDate().trim().substring(0, 10);}catch(Exception e){}
									    %>
										<%=creators%>
										<div class="date"><%=date1%></div>
						</div>
					    <div class="lectureseries-small"><%=series%></div>
						<div class="meta-description-container">
							<div class="meta-description">
								  <%if(videoMetadata.getDescription().trim().length()>0){ %>	
								  	${videoMetadata.description}
								  <%}else{%>
							      	${lectureseries.longDesc}
								  <%}%>
							</div>
						</div>
				    </div>
				    
				    <%
				    if(timeEnd==0 || timeEnd<timeStart || video.getCitation2go()==0){
					%>
					
					    <c:if test="${relatedVideos.size()>1}"><div class="metainfo"></c:if>
					    <c:if test="${relatedVideos.size()<=1}"><div class="metainfo-wide"></c:if>
							<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
							    <c:if test="${video.downloadLink==1}">
								    <li id="down"><a href="#download" data-toggle="tab"><liferay-ui:message key="download"/></a></li>
							    </c:if>
							    <%if(video.getOpenAccess()==1){%>
							   	 	<li><a href="#share" data-toggle="tab"><liferay-ui:message key="share"/></a></li>
							    <%}%>

							    <%if(video.isHasChapters()){ %>
							    	<li><a href="#chapters" data-toggle="tab"><liferay-ui:message key="chapters"/></a></li>
							    <%}%>				    
							</ul>
							    
							<div id="my-tab-content" class="tab-content">
								<c:if test="${video.downloadLink==1}">
									<div class="tab-pane" id="download">
								        <p><%@ include file="/guest/includeDownload.jsp" %></p>
								    </div>
								</c:if>
								<%if(video.getOpenAccess()==1){%>
								    <div class="tab-pane" id="share">
								        <p><%@ include file="/guest/includeShare.jsp" %></p>
								    </div>
							    <%}%>
									
									<%if(video.isHasChapters() || video.isHasComments()){%>
									    <ul class="tab-pane" id="chapters">
									    	<c:forEach items="<%=segments %>" var="segment">
									    		<li class="chaptertile" id="${segment.segmentId}" begin="${segment.start}" end="${segment.end}">
													<div class="image">
												    	<a aria-label='<liferay-ui:message key="go-to-video-segment"/>'><img src="${segment.image}" alt="<liferay-ui:message key="thumbnail"/> - ${segment.title}"></a>
												    </div>
												    <div class="title">
												    	<a><b>${segment.start} </b> ${segment.title}</a>
												    </div>
											    </li>
									    	</c:forEach>
								    	</ul>
							    	<%}%>
							</div>    
						</div>
					<%
					}
				    %>	  
			  </div>

			  <%
			  if(relatedVideos.size()>1){
			  %>
			    <div class="related">
					<div class="col-md-5">
						    <div class="related-lectureseries-name"><h3><liferay-ui:message key="lecture-series"/></h3> <a title="<liferay-ui:message key='rss-feed'/>" aria-label="<liferay-ui:message key='rss-feed'/>" target="_blank" class="icon-small icon-rss" href="${video.mp4RssLink}"></a> </div>
							<ul class="ul-related">
								<%
								ListIterator<Video> vli = relatedVideos.listIterator();
								while(vli.hasNext()){
								Video vid = vli.next();	
								String oId = "";
								if(vid.getOpenAccess()==1) oId = vid.getVideoId()+"";
								else oId = vid.getSPreffix();
								%>
								<portlet:actionURL name="viewOpenAccessVideo" var="viewOpenAccessVideoURL1">
									<portlet:param name="objectId" value="<%=oId%>"/>
									<portlet:param name="objectType" value="v"/>
								</portlet:actionURL>
								<%-- test for the active video and highlight it --%>
								<c:set value="" var="activeClass"></c:set>
								<c:if test='<%=video.getVideoId() == vid.getVideoId() %>'> 
  									<c:set value="active" var="activeClass"></c:set>
								</c:if>
								<li class="videotile small related ${activeClass}" onClick="window.location='<%=viewOpenAccessVideoURL1.toString()%>'">
										<div class="video-image-wrapper-small related">
											<img class="video-image related" src="<%=vid.getImageSmall()%>" alt="<liferay-ui:message key="thumbnail"/> - <%=vid.getTitle() %>" />
										</div>
										<div class="metainfo-small related">
												<%
													String date = "";
							           				try{ date = vid.getDate().trim().substring(0, 10);}catch(Exception e){}
												%>
												<a href="<%=viewOpenAccessVideoURL1.toString()%>" aria-label='<liferay-ui:message key="go-to-video"/>: <%=vid.getTitle()%>'>
													<h4 class="title-small related"><%=vid.getTitle()%></h4>
												</a>
							          			<p class="creator-small2 related">
												<%=vid.getLinkedCreators() %>
							           			</p>
								           		<div class="date related"><%=date%></div> 
						            	</div>
								</li>
							<%}%>
							</ul>
					</div>
			    </div>
			  <%
			  }
			  %>			  
		</div>
		<%if(video.getOpenAccess()==0){%>
			<!-- coockie start -->
			<script type="text/javascript">
				$(function(){
					//cookie
					$.cookie("L2G_LSID", "<%=lectureseries.getLectureseriesId()%>", { path: "/" });
				});
			</script>		
			<!-- coockie end -->
		<%}%>
		
		<%
	}else{
		if(video.getAccessPermitted()==0){
			%>
			<portlet:actionURL name="viewOpenAccessVideo" var="viewOpenAccessVideoURL2">
				<portlet:param name="objectId" value="<%=objectId%>"/>
				<%if(objectType.equals("v")){%><portlet:param name="objectType" value="v"/><%}%>
				<%if(objectType.equals("l")){%><portlet:param name="objectType" value="l"/><%}%>
			</portlet:actionURL>
			<aui:form action="<%=viewOpenAccessVideoURL2.toString() %>" method="post">
				<aui:fieldset helpMessage="enter-password" column="true" label='<%="l2go-video-password"%>'>
					<aui:input name="password" label="password" required="true" value=""/>
					<aui:button type="submit" value="enter"/>
				</aui:fieldset>
			</aui:form>	
			<%
		}
	}
}
%>
<script type="text/javascript">
    $( function () {
        $('#tabs').tabs();
        $('[data-toggle=tab]').click(function(e){
    	    if ($(this).parent().hasClass('active')){
                $(this).parent().removeClass('active');
    		    $($(this).attr("href")).removeClass('active');
                // do not trigger the opening sequence from the tabs widget
    		    e.stopPropagation();
            }
    	});
    });
    
    $( document ).ready(function() {
		$('.meta-description').readmore({
			collapsedHeight: 60,
		  	moreLink: '<a href="#" aria-label="<liferay-ui:message key='more'/>"><div><liferay-ui:message key="more"/><img class ="down_up" src="/lecture2go-theme/images/arrow_down.svg"/></div></a>',
		  	lessLink: '<a href="#" aria-label="<liferay-ui:message key='more'/>"><div><liferay-ui:message key="less"/><img class ="down_up" src="/lecture2go-theme/images/arrow_up.svg"/></div></a>'
		});
	}); 
</script> 

<style type="text/css">
	.aui #breadcrumbs .breadcrumb {
	    display: none;
	}
	.current-page{
		display: none;
	}
</style>  		
