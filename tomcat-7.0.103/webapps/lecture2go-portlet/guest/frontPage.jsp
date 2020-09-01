<%@page import="de.uhh.l2g.plugins.util.RepositoryManager"%>
<%@page import="de.uhh.l2g.plugins.model.impl.InstitutionImpl"%>
<%@page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="de.uhh.l2g.plugins.util.InstallWizardManager"%>
<%@include file="/init.jsp"%>
<%
Long institutionId = new Long(0);
Long parentInstitutionId = new Long(0);
Long termId = new Long(0);
Long categoryId = new Long(0);
Long creatorId = new Long(0);
String searchQuery = "";

//get latest 
//-lecture series = lectureseriesId>0
//-videos = lectureseriesId<0
//example -> top 12
List<Lectureseries> latest = LectureseriesLocalServiceUtil.getLatest(12);
ListIterator<Lectureseries> lli = latest.listIterator();

//get popular videos
//example -> top 12
List<Video> popular = VideoLocalServiceUtil.getPopular(12);
ListIterator<Video> pli = popular.listIterator();

//get all root (1st level) institutions with open access videos
List<Institution> institutions = InstitutionLocalServiceUtil.getRootInstitutionsByOpenAccessVideos();

//this proceeds only if the install wizart is activ
InstallWizardManager installWizardManager = new InstallWizardManager(portletGroupId, company.getCompanyId());
installWizardManager.installRepository();
%>
   
<div class="front-page-teaser">
 	<div class="bg-video-container">
		<video id="bg-vid" loop poster="/lecture2go-portlet/img/background_still.jpg" preload="none" muted>
		    <!-- the video source is added dynamically to avoid unnecessary traffic -->
		    <img src="/lecture2go-portlet/img/background_still.jpg">
		</video>
		<div class="dark-overlay"></div>
		<div id="vid-control" role="button" tabindex="0" aria-label="Pause Video">
			<i class="icon-play"></i>
		</div>
	</div>
	<div class="l2go-info-container">
		<div class="l2go-info">
			<div class="l2go-title">
				<%if(company.getName().equals("Lecture2Go")){%>
					Lecture<span class="orange">2</span>Go
				<%}else{%>
					<%=company.getName()%>
				<%}%>
			</div>
			<div class="l2go-subtitle">
				<p><liferay-ui:message key="l2go-description"/></p>
			</div>
			<div class="big-search">
			</div>
			
			<button id="outer-catalogue-button" class="catalogue-button" onclick="window.location='/web/vod/l2go';"><liferay-ui:message key="to-catalogue"/></button>
			
		</div>
	</div>
</div>


<div id="front-page-content">
<!-- new videos -->
	<div class="news">
		<h2><liferay-ui:message key="last-added"/></h2>
		<div class="video-box-list-container">
			<div id="news-carousel" class="carousel slide" data-interval="false" data-wrap="false">
				<a class="left carousel-control" href="#news-carousel" data-slide="prev" aria-role='button' aria-label='<liferay-ui:message key="previous-elements"/>'><i class="icon-chevron-left"></i></a>
           		<!-- Carousel items -->
	            <div class="carousel-inner">
	                <div class="item active">
						<div class="row-fluid video-box-list">
							<c:set var="count" value="0" scope="page" />
							<%
								while(lli.hasNext()){
									Lectureseries lectser = lli.next();
									String oId = "";
									boolean isVideo = false;
									Video vid = new VideoImpl();
									if(lectser.getLatestOpenAccessVideoId()<0){
										isVideo = true;
										try {
											vid = VideoLocalServiceUtil.getVideo(lectser.getLectureseriesId());
										} catch (Exception e) {
											
										}
										oId = vid.getVideoId()+"";
									}else{
										oId = lectser.getLectureseriesId()+"";
										try {
											vid = VideoLocalServiceUtil.getVideo(lectser.getLatestOpenAccessVideoId());
										} catch (Exception e) {
											
										}

									}

									List<Video_Institution> vi = Video_InstitutionLocalServiceUtil.getByVideo(vid.getVideoId());
						        	// only get the first institution
						        	Institution inst = new InstitutionImpl();
						        	Institution rootInst = new InstitutionImpl();
						        	try{
						        		inst=InstitutionLocalServiceUtil.getById(vi.get(0).getInstitutionId());
						        	}catch(Exception e){
						        		// no institution for the video, use the root institution
						        		rootInst=InstitutionLocalServiceUtil.getById(vid.getRootInstitutionId());
						        	}
						        	
									%>
									
									<c:if test='${count % 4 == 0 && count != 0}'>
										<!-- row-fluid -->
											</div>
											<!-- item -->
										</div>
										<div class="item">
		                    				<div class="row-fluid video-box-list">
		                    		</c:if>
										<div class="span3 video-box" onClick="window.location='<%=vid.getUrl() %>'">											
											<div class="video-box-image-container">
												<div class="video-box-image">
													<img src="<%=vid.getImageMedium() %>" alt="<liferay-ui:message key="thumbnail"/> - <%=vid.getTitle() %>" />
												</div>
											</div>

											<div class="video-box-content">
												<div class="date"><%=vid.getSimpleDate() %></div>
												<a href='<%=vid.getUrl() %>' aria-label="<liferay-ui:message key="video-title"/>: <%=vid.getTitle() %> " >					
													<h3 class="title-small dot-ellipsis dot-height-60 dot-resize-update "><%=vid.getTitle() %></h3>
												</a>
												<div class="creator-small2 dot-ellipsis dot-height-25 dot-resize-update "><%=vid.getLinkedCreators() %></div>
												<div class="lectureseries-small dot-ellipsis dot-height-25 dot-resize-update">
													<% if (!isVideo) { %>
														<%=lectser.getName() %>
													<% } else { %>
														&nbsp;
													<% } %>
												</div> 
												<%if(inst.getInstitutionId()>0){ %>
												<div class="labels">
													<%
														String instLink="<a href='/l2go/-/get/"+inst.getInstitutionId()+"/"+inst.getParentId()+"/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution-name")+": " + inst.getName() + "'>"+inst.getName()+"</a>"; 
													%>
										        	<span class="label label-light2"><%=instLink%></span>												
												</div>
												<%} else if(rootInst.getInstitutionId()>0){ %>
													<div class="labels">
														<%
															String instLink="<a href='/l2go/-/get/0/"+rootInst.getInstitutionId()+"/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution-name")+": " + rootInst.getName() + "'>"+rootInst.getName()+"</a>"; 
														%>
											        	<span class="label label-light2"><%=instLink%></span>												
													</div>
												<%} %>
											</div> 
										</div>
										
									<c:set var="count" value="${count + 1}" scope="page"/>

									<!-- span -->
									<%
								}
							%>
						</div>
					</div>
				</div>
				<a class="right carousel-control" href="#news-carousel" data-slide="next" aria-role='button' aria-label='<liferay-ui:message key="next-elements"/>'><i class="icon-chevron-right"></i></a>
			</div>
		</div>
	</div>





<!-- popular videos -->
	<div class="popular">
		<h2><liferay-ui:message key="popular-videos"/></h2>
		<div class="video-box-list-container">
			<div id="popular-carousel" class="carousel slide" data-interval="false" data-wrap="false">
				<a class="left carousel-control" href="#popular-carousel" data-slide="prev" aria-role='button' aria-label='<liferay-ui:message key="previous-elements"/>'><i class="icon-chevron-left"></i></a>
            	<!-- Carousel items -->
	            <div class="carousel-inner">
	                <div class="item active">
						<div class="row-fluid video-box-list">
							<c:set var="count" value="0" scope="page" />
							<%
								while(pli.hasNext()){
									Video video = pli.next();
									
									Video vid = new VideoImpl();
									try {
										vid = VideoLocalServiceUtil.getVideo(video.getVideoId());
									} catch (Exception e) {
										
									}

									boolean isVideo = (vid.getLectureseriesId() < 0);

									String creators = CreatorLocalServiceUtil.getCommaSeparatedCreatorsByVideoIdAndMaxCreators(vid.getVideoId(),3);

									String lectureseries = "";
									
									if (!isVideo) {
										try {
											Lectureseries lec = LectureseriesLocalServiceUtil.getLectureseries(vid.getLectureseriesId());
											lectureseries = lec.getName();
										} catch (Exception e) {}
									}
									
						        	List<Video_Institution> vi = Video_InstitutionLocalServiceUtil.getByVideo(vid.getVideoId());
						        	// only get the first institution
						        	Institution inst = new InstitutionImpl();
						        	Institution rootInst = new InstitutionImpl();
						        	try{
						        		inst=InstitutionLocalServiceUtil.getById(vi.get(0).getInstitutionId());
						        	}catch(Exception e){
						        		// no institution for the video, use the root institution
						        		rootInst=InstitutionLocalServiceUtil.getById(vid.getRootInstitutionId());
						        	}						%>
								<c:if test="${count % 4 == 0 && count != 0}">
									<!-- row-fluid -->
										</div>
										<!-- item -->
									</div>
									<div class="item">
	                    				<div class="row-fluid video-box-list">
	                    		</c:if>
									<div class="span3 video-box" onClick="window.location='<%=vid.getUrl() %>'">	
										<div class="video-box-image-container">
											<div class="video-box-image"> 
												<img src="<%=vid.getImageMedium() %>" alt="<liferay-ui:message key="thumbnail"/> - <%=vid.getTitle() %>" />
											</div>
										</div>
	
										<div class="video-box-content"> 
											<div class="date"><%=vid.getSimpleDate() %></div>
											<a href='<%=vid.getUrl() %>' aria-label="<liferay-ui:message key="video-title"/>: <%=vid.getTitle() %> ">					
												<h3 class="title-small dot-ellipsis dot-height-60 dot-resize-update "><%= vid.getTitle() %></h3>
											</a>
											<div class="creator-small2 dot-ellipsis dot-height-25 dot-resize-update "><%= vid.getLinkedCreators() %></div>
											<div class="lectureseries-small dot-ellipsis dot-height-25 dot-resize-update">
												<% if (!isVideo) { %>
													<%=lectureseries %>
												<% } else { %>
													&nbsp;
												<% } %>
											</div>
											<%if(inst.getInstitutionId()>0){ %>
											<div class="labels">
												<%
													String instLink="<a href='/l2go/-/get/"+inst.getInstitutionId()+"/"+inst.getParentId()+"/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution-name")+": " + inst.getName() + "'>"+inst.getName()+"</a>"; 
												%>
									        	<span class="label label-light2"><%=instLink%></span>
									        </div>
									        <%} else if(rootInst.getInstitutionId()>0){ %>
												<div class="labels">
													<%
														String instLink="<a href='/l2go/-/get/0/"+rootInst.getInstitutionId()+"/0/0/0/' aria-label='"+LanguageUtil.get(themeDisplay.getLocale(),"institution-name")+": " + rootInst.getName() + "'>"+rootInst.getName()+"</a>"; 
													%>
										        	<span class="label label-light2"><%=instLink%></span>												
												</div>
											<%} %>
										</div>
									</div>
								<c:set var="count" value="${count + 1}" scope="page"/>
							<%
								}
							%>
						</div>
					</div>
				</div>
				<a class="right carousel-control" href="#popular-carousel" data-slide="next" aria-role='button' aria-label='<liferay-ui:message key="next-elements"/>'><i class="icon-chevron-right"></i></a>
			</div>
		</div>
	</div>

</div>


<script>
$(document).ready(function(){
    // process different things depending on the screen size
    MQS.add({
        ref: 'desktop',
        mediaQuery: '(min-width: 768px)', 
        action: () => {
        	transformSearchToWideView();
		  	//hide all carousel items but the first and show carousel navigation
		  	$("#news-carousel .item:not(:first)").removeClass("active");
		  	$("#popular-carousel .item:not(:first)").removeClass("active");
		  	$(".carousel-control").show();
		  	showOrHideCarouselControl('#news-carousel');
		  	showOrHideCarouselControl('#popular-carousel');
		  	addBGVideo();
        } 
    });
    
    MQS.add({
        ref: 'mobile',
        mediaQuery: '(max-width: 767px)', 
        action: () => {
        	transformSearchToSmallView();
		  	// do not show the big button on desktop
		  	$(".filter-facility-menu").hide();
		  	//show all carousel items and hide carousel navigation
		  	$(".item").addClass("active");
		  	$(".carousel-control").hide();
        }
    });
    

	// handle the previous and next buttons of the carousel
    //showOrHideCarouselControl('#news-carousel');
    //showOrHideCarouselControl('#popular-carousel');

    $('#news-carousel').on('slid.bs.carousel', function() { 
    	showOrHideCarouselControl('#news-carousel');
    	// the truncation of the elements needs to be triggered manually on carousel switch
    	$(".active .title-small, .active .creator-small2, .active .lectureseries-small").trigger("update.dot");
    	});

    $('#popular-carousel').on('slid.bs.carousel', function() { 
    	showOrHideCarouselControl('#popular-carousel');
    	// the truncation of the elements needs to be triggered manually on carousel switch
    	$(".active .title-small, .active .creator-small2, .active .lectureseries-small").trigger("update.dot");
    });
    
    $('#vid-control').on("click", function(){
        var video = document.getElementById("bg-vid");
  		var $controlIcon = $(this).find('i').eq(0);
  		if (video.paused) {
  			video.play();
  			$controlIcon.switchClass("icon-play", "icon-pause");
  			$('#vid-control').attr("aria-label", "Pause video");
  		} else {
  			video.pause();
  			$controlIcon.switchClass("icon-pause", "icon-play");
  			$('#vid-control').attr("aria-label", "Pause video");
  		}
	});

});

		
/* 
	hides the "previous"-control-button on first carousel page and 
	the "next"-control-button on the last carousel page
*/
function showOrHideCarouselControl(id) {
  	var $this = $(id);
  	if($this.find('.carousel-inner .item:first').hasClass('active')) {
  		$this.children('.left.carousel-control').hide();
  	} else if($this.find('.carousel-inner .item:last').hasClass('active')) {
    	$this.children('.right.carousel-control').hide();
  	} else {
    	$this.children('.carousel-control').show();
  	}
}

function transformSearchToWideView() {
  	// move search box from top to center
  	$(".search").appendTo(".big-search");
  	// change image to button
	$('#_lgopenaccessvideos_WAR_lecture2goportlet_searchButton span').text(" <liferay-ui:message key='search'/>");
	$('#_lgopenaccessvideos_WAR_lecture2goportlet_searchQuery').attr("placeholder", "<liferay-ui:message key='search-videos-long'/>");
}

function transformSearchToSmallView() {
  	// move search box from center to top
  	$(".search").appendTo("header");
  	$('#_lgopenaccessvideos_WAR_lecture2goportlet_searchButton span').text("");
	$('#_lgopenaccessvideos_WAR_lecture2goportlet_searchQuery').attr("placeholder", "<liferay-ui:message key='search-videos'/>");
}

// add the video source to the video
function addBGVideo() {
	$('#bg-vid').prepend('<source src="/lecture2go-portlet/img/background.mp4" type="video/mp4">');
}
    
</script>

<style type="text/css">
	#breadcrumbs .breadcrumb {
	    visibility: hidden;
	}
	
	#breadcrumbs {
	    height: 17px !important;
	    margin-bottom: 3px;
	    margin-top: 0;
	    padding: 0;
	}
	
	.current-page{
		display: none;
	}
</style>  
