<%@include file="/init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
 
<portlet:renderURL var="viewURL"><portlet:param name="jspPage" value="/admin/oaipmh.jsp" /></portlet:renderURL>

<portlet:actionURL name="republishAll" var="republishAllURL"></portlet:actionURL>
<portlet:actionURL name="republishVideo" var="republishVideoURL"></portlet:actionURL>
<portlet:actionURL name="unpublishVideo" var="unpublishVideoURL"></portlet:actionURL>

<portlet:actionURL name="fixMissingMetadataForVideoFromRelatedLectureseries" var="fixMissingMetadataForVideoFromRelatedLectureseriesURL"></portlet:actionURL>


<div class="noresponsive">	  
	<label class="edit-video-lable"><liferay-ui:message key="oaipmh"/></label>

	<aui:form action="${republishAllURL}" name="republishAll">
		<liferay-ui:message key="oai-pmh-republish-all"/>
		<aui:button type="submit" value="Start"></aui:button>
	</aui:form>
	
	<aui:form action="${republishVideoURL}" name="republishVideo">
			<aui:fieldset>
				<aui:input name="videoId" inlineField="true"></aui:input>
				<aui:button type="submit" value="Republish"></aui:button>
			</aui:fieldset>
	</aui:form>
	
	<aui:form action="${unpublishVideoURL}" name="unpublishVideo">
		<aui:row>
			<aui:input name="videoId" inlineField="true"></aui:input>
			<aui:button type="submit" value="Unpublish"></aui:button>
		</aui:row>
		
	</aui:form>
</div>