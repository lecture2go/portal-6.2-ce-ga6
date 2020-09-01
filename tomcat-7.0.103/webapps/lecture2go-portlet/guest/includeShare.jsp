<div id="metadata">
	<p class="smallitalic"><liferay-ui:message key="embed-conditions"/></p>
	<form name="embedForm" id="embedForm">
		<!-- embed start -->
			<div class="sharetile">
				<h3><liferay-ui:message key="links"/></h3>
				<div id="urls">
					<ul>
						<li>
							<aui:input name="embed_code3" label="video-url" helpMessage="about-video-url" required="false" id="embed_code3" readonly="true" value="${video.url}" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code3.focus();document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code3.select();"/>
						</li>
						<li>
							<aui:input name="embed_code" label="embed-iframe" helpMessage="about-iframe-embed" required="false" id="embed_code" readonly="true" value="${video.embedIframe}" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code.focus();document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code.select();"/>
						</li>
						<c:if test="${video.downloadLink==1}">
							<li>
								<aui:input name="embed_code1" label="embed-html5" helpMessage="about-html5-embed" required="false" id="embed_code1" readonly="true" value="${video.embedHtml5}" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code1.focus();document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code1.select();"/>							
							</li>
						</c:if>
						<c:if test="<%= FeatureManager.hasCommsy()%>">
							<li>
								<aui:input name="embed_code4" label="embed-commsy" helpMessage="about-commsy-embed" required="false" id="embed_code4" readonly="true" value="(:lecture2go ${video.videoId}:)" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code4.focus();document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code4.select();"/>
							</li>
						</c:if>
						<c:if test="${video.lectureseriesId>0}">
							<li>
								<aui:input name="embed_code2" label="lecture-series-url" helpMessage="about-lecture-series-url" required="false" id="embed_code2" readonly="true" value="${video.lectureseriesUrl}" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code2.focus();document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_embed_code2.select();"/>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		<!-- embed end -->
		
		<!-- citation2go allowed -->
		<c:if test="<%= FeatureManager.hasCitation2Go()%>">
			<c:if test="${video.citation2go==1}">
				<div class="sharetile">
					<h3><liferay-ui:message key="citation2go"/></h3>
					<div id="c2g-generate">
						<ul>
							<li>
								<aui:input name="timeStart" label="citation-start" required="false" id="timeStart" readonly="true" helpMessage="about-time-start"/>
							</li>
							<li>
								<aui:input name="timeEnd" label="citation-end" required="false" id="timeEnd" readonly="true" helpMessage="about-time-end"/>
							</li>
							<li>
							<li>
								<aui:input name="citation" label="citation2go-link" required="false" id="citation" readonly="true"  helpMessage="about-citation" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_citation.focus(); document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_citation.select();"/>
							</li>
							<li>
								<aui:input name="citationiframe" label="embed-iframe" required="false" id="citationiframe" readonly="true"  helpMessage="about-citation-iframe" onclick="document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_citationiframe.focus(); document.embedForm._lgopenaccessvideos_WAR_lecture2goportlet_citationiframe.select();"/>
							</li>
						</ul>
					</div>
				</div>
			</c:if>
		</c:if>
		<!-- citation2go allowed end-->		
		
		<!-- Facebook Twitter Google+ -->
		<c:if test="<%= FeatureManager.hasSocialMediaSharing()%>">
			<div class="sharetile">
				<h3><liferay-ui:message key="social-media"/></h3>
				<div id="socialshareprivacy"></div>
			</div>
		</c:if>
		<!-- Facebook Twitter Google+ Ende -->
	</form>
	
	<c:if test="<%= FeatureManager.hasSocialMediaSharing()%>">
		<script type="text/javascript">
			$('#socialshareprivacy').socialSharePrivacy({
				services : {
					gplus : {
						status : 'off'
					}
				}
			});
		</script>
	</c:if>
	
	<%@ include file="/guest/includeQR.jsp" %>
</div>