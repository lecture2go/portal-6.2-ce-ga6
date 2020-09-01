<%@include file="/init.jsp" %>
<%@ page import="de.uhh.l2g.plugins.util.PortletScheduler" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.scheduler.SchedulerEntry" %>
<%@ page import="com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil" %>
<%@ page import="com.liferay.portal.kernel.scheduler.StorageType" %>
<%@ page import="com.liferay.portal.kernel.scheduler.TriggerState" %>
<%@ page import="com.liferay.portal.kernel.scheduler.Trigger" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%
	String actionURL = "";
	//quartz responses/jobs
	List<PortletScheduler> portletScheduler = PortletScheduler.ListSchedulers();
	//Portlet URL
	PortletURL portletURL = renderResponse.createRenderURL();
	String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
%>
 
<portlet:renderURL var="viewURL"><portlet:param name="jspPage" value="/admin/threads.jsp" /></portlet:renderURL>

<liferay-ui:success key="request_processed" message="request_processed"/>

<portlet:actionURL name="scheduleJob" var="scheduleJobURL"></portlet:actionURL>
<portlet:actionURL name="resumeJob" var="resumeJobURL"></portlet:actionURL>
<portlet:actionURL name="pauseJob" var="pauseJobURL"></portlet:actionURL>
<portlet:actionURL name="updateJob" var="updateJobURL"></portlet:actionURL>
<portlet:actionURL name="removeAllJobs" var="removeAllJobsURL"></portlet:actionURL>

<liferay-portlet:resourceURL id="triggerVideohitlistThread" var="triggerVideohitlistThread" />

<div class="noresponsive">	  
<label class="edit-video-lable"><liferay-ui:message key="cronjobs"/></label>
<liferay-ui:message key="common-descritpion-for-l2go-cronjobs"/>
<%-- This list all unique schedulers --%>
	<% 
    //Initialize parameters
    TriggerState state = null;
	Trigger trigger = null;
	String schedulerPanelId = "panel";
    String cron = "";
    int iC = 0;
    %>
    <table width="100%">
    <%
    //Do for each Quartz Response 
	for(PortletScheduler job : portletScheduler) {  	
		state = SchedulerEngineHelperUtil.getJobState(job.getJobName(), job.getJobName(), StorageType.MEMORY_CLUSTERED);
		trigger = job.getTrigger();
		if (trigger != null) cron = trigger.getTriggerContent().toString(); 
  		schedulerPanelId = "panel"+ String.valueOf(iC);
   		iC++;  
	%>
		<tr><td>
		<div class="adminrow wide">
			<div class="admintile wide">
				<h6><liferay-ui:message key="<%= job.getJobName() %>"/></h6> 
			</div>
			<div class="admintile wide icons creators">
		 		<%
		 		if (state.equals(TriggerState.UNSCHEDULED)) {
			 	%>
			 		<aui:form action="<%= scheduleJobURL %>" name="fm">         
						<aui:input name='schedulerName' type='hidden' inlineField="true" value='<%= job.getJobName() %>'/>
						<aui:button type="submit" value="schedule" ></aui:button>
					</aui:form>
				<% 
				}else{
					%>
						<portlet:actionURL name="unscheduleJob" var="unscheduleJobURL">
							<portlet:param name="schedulerName" value="<%= job.getJobName() %>"/>
						</portlet:actionURL>
						<a href="<%=unscheduleJobURL%>">
							<span class="icon-large icon-remove" onclick="return confirm('<liferay-ui:message key="really-unschedule-question"/>')"></span>
						</a>
					<%
			 		if (state.equals(TriggerState.PAUSED)) {
				 	%>
				 		<aui:form action="<%= resumeJobURL %>" name="resume">         
							<aui:input name='schedulerName' type='hidden' inlineField="true" value='<%= job.getJobName() %>'/>
							<aui:button type="submit" value="resume" ></aui:button>
						</aui:form>
					<% 
					}else {
				 	%>
				 		<aui:form action="<%= pauseJobURL %>" name="pause">         
							<aui:input name='schedulerName' type='hidden' inlineField="true" value='<%= job.getJobName() %>'/>
							<aui:button type="submit" value="pause" ></aui:button>
						</aui:form>
					<% 
					}					
				}
		 		%>
			</div>
			<div class="undertile wide">
				<p><liferay-ui:message key="status"/>: <%=state.toString()%> </p>
				<p><liferay-ui:message key="trigger-value"/>: <%=cron%></p>
			</div>
		</div>
		</td></tr>
	<%
    }
	%>
	</table>
</div>


