<%!com.liferay.portal.kernel.dao.search.SearchContainer<User> searchContainer = null;%>

<%@taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="javax.portlet.PortletException"%>
<%@page import="javax.portlet.RenderRequest"%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ListIterator"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.lang.String"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.PrintWriter"%>

<%@page import="de.uhh.l2g.plugins.util.HTMLFilter"%>

<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.util.PwdGenerator"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.CalendarFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchEntry"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.model.Portlet"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.security.permission.ActionKeys"%>
<%@page import="com.liferay.portal.service.permission.PortletPermissionUtil"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.UserGroupRoleServiceUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.PortletLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.util.portlet.PortletRequestUtil"%>
<%@page import="com.liferay.portlet.PortletURLUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PrefsPropsUtil"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer" %>
<%@page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@page import="org.apache.jasper.JasperException"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="org.springframework.scripting.config.LangNamespaceUtils"%>
<%@page import="org.springframework.web.bind.ServletRequestUtils"%>
<%@page import="org.springframework.web.portlet.bind.PortletRequestUtils"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>

<%@ page import="de.uhh.l2g.plugins.admin.AdminInstitutionManagement" %>
<%@ page import="de.uhh.l2g.plugins.admin.AdminStreamerManagement" %>
<%@ page import="de.uhh.l2g.plugins.admin.AdminUserManagement" %>

<%@page import="de.uhh.l2g.plugins.service.ProducerLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.InstitutionLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.LectureseriesLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Producer_LectureseriesLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Lectureseries_InstitutionLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.VideoLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Video_CategoryLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Video_InstitutionLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.CoordinatorLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.LicenseLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.SegmentLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Video_CreatorLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Lectureseries_CreatorLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.HostLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.TermLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.CategoryLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.CreatorLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.Institution_HostLocalServiceUtil" %>
<%@page import="de.uhh.l2g.plugins.service.MetadataLocalServiceUtil"%>
<%@page import="de.uhh.l2g.plugins.service.InstallWizardLocalServiceUtil"%>

<%@page import="de.uhh.l2g.plugins.NoSuchMetadataException"%>

<%@page import="de.uhh.l2g.plugins.model.impl.HostImpl"%>
<%@page import="de.uhh.l2g.plugins.model.Video_Category"%>
<%@page import="de.uhh.l2g.plugins.model.Term"%>
<%@page import="de.uhh.l2g.plugins.model.Creator"%>
<%@page import="de.uhh.l2g.plugins.model.Category"%>
<%@page import="de.uhh.l2g.plugins.model.Producer"%>
<%@page import="de.uhh.l2g.plugins.model.Segment"%>
<%@page import="de.uhh.l2g.plugins.model.Institution"%>
<%@page import="de.uhh.l2g.plugins.model.Lectureseries"%>
<%@page import="de.uhh.l2g.plugins.model.Producer_Lectureseries"%>
<%@page import="de.uhh.l2g.plugins.model.Video"%>
<%@page import="de.uhh.l2g.plugins.model.Coordinator"%>
<%@page import="de.uhh.l2g.plugins.model.License"%>
<%@page import="de.uhh.l2g.plugins.model.impl.LectureseriesImpl"%>
<%@page import="de.uhh.l2g.plugins.model.impl.MetadataImpl"%>
<%@page import="de.uhh.l2g.plugins.model.Metadata"%>
<%@page import="de.uhh.l2g.plugins.model.impl.VideoImpl"%>
<%@page import="de.uhh.l2g.plugins.model.Video_Institution"%>
<%@page import="de.uhh.l2g.plugins.model.Video_Creator"%>
<%@page import="de.uhh.l2g.plugins.model.Lectureseries_Creator"%>
<%@page import="de.uhh.l2g.plugins.model.impl.ProducerImpl"%>
<%@page import="de.uhh.l2g.plugins.model.impl.LicenseImpl"%>
<%@page import="de.uhh.l2g.plugins.model.Lectureseries_Institution"%>
<%@page import="de.uhh.l2g.plugins.model.Host"%>
<%@page import="de.uhh.l2g.plugins.model.Institution_Host"%>

<%@page import="de.uhh.l2g.plugins.util.Lecture2GoRoleChecker"%>
<%@page import="de.uhh.l2g.plugins.util.Security"%>
<%@page import="de.uhh.l2g.plugins.util.VideoProcessorManager"%>
<%@page import="de.uhh.l2g.plugins.util.FeatureManager"%>

<%@page import="de.uhh.l2g.plugins.search.VideoSearchContainer"%>
<%@page import="de.uhh.l2g.plugins.search.VideoDisplayTerms"%>
<%@page import="de.uhh.l2g.plugins.search.VideoSearchHelper"%>
<%@page import="de.uhh.l2g.plugins.search.UserSearchContainer"%>
<%@page import="de.uhh.l2g.plugins.search.UserDisplayTerms"%>
<%@page import="de.uhh.l2g.plugins.search.UserSearchHelper"%>
<%@page import="de.uhh.l2g.plugins.search.CreatorSearchContainer"%>
<%@page import="de.uhh.l2g.plugins.search.CreatorDisplayTerms"%>
<%@page import="de.uhh.l2g.plugins.search.CreatorSearchHelper"%>

<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />
<liferay-theme:defineObjects/>

<%
	//check lecture2go user permissions
	User remoteUser = UserLocalServiceUtil.createUser(0);
	//l2go administrator is logged in
	boolean permissionAdmin = false;
	//l2go coordinator is logged in
	boolean permissionCoordinator = false;
	//l2go producer is logged in
	boolean permissionProducer = false;
	//l2go student is logged in
	boolean permissionStudent = false;
	//l2go producerPending is logged in
	boolean permissionProducerPending = false;

	try{
		Lecture2GoRoleChecker rcheck = new Lecture2GoRoleChecker();
		remoteUser = UserLocalServiceUtil.getUser(new Long (request.getRemoteUser()));
		permissionAdmin = rcheck.isL2gAdmin(remoteUser);
		permissionCoordinator = rcheck.isCoordinator(remoteUser);
		permissionProducer = rcheck.isProducer(remoteUser);
		permissionStudent = rcheck.isStudent(remoteUser);
		permissionProducerPending = rcheck.isProducerPending(remoteUser);
		if(permissionAdmin){
			permissionCoordinator=false;
			permissionProducer=false;
			permissionStudent=false;
		}else{
			if(permissionCoordinator){
				permissionProducer=false;
				permissionStudent=false;		
			}else{
				if(permissionProducer){
					permissionStudent=false;
				}
			}
		}
		PortletPreferences prefs = renderRequest.getPreferences();	
	}catch(Exception e){
		//
	}
%>