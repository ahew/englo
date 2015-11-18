<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2"   uri="/jpoauth2-core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="wp"    uri="/aps-core"%>
<%@ taglib prefix="wpsa"  uri="/apsadmin-core"%>
<%@ taglib prefix="wpsf"  uri="/apsadmin-form"%>
<wp:currentPage param="code" var="currentPageCodeVar" />
<oa2:authentication service="1">
	<s:set var="logged" value="true"/>
</oa2:authentication>

<div class="jpbasecamp jpbasecam-login">
	<h2 class="title">
		<wp:i18n key="jpbasecamp_TITLE_LOGIN" />
		<s:if test="%{logged}">
			&#32;<span class="icon-ok"></span>
		</s:if>
	</h2>

	<s:if test="%{!#logged}">
		<s:form action="access" namespace="/do/authentication">
			<wpsf:hidden name="id" value="1" />
			<wpsf:hidden name="redirectPage" value="%{#attr.currentPageCodeVar}" />
			<wpsf:submit type="button" cssClass="btn btn-small">
				<wp:i18n key="jpbasecamp_LOGIN_NOW" />
			</wpsf:submit>
		</s:form>
	</s:if>
	<s:else>
		&#32;
		<s:set var="basecamp_logout_url" value="%{'###logout'}" />
		<span class="noscreen sr-only"><wp:i18n key="jpbasecamp_LOGIN_OK" /></span>
		<a
			class="btn btn-default btn-small"
			href="<wp:info key="systemParam" paramName="applicationBaseURL" />do/logout">
				<wp:i18n key="jpbasecamp_LOGOUT" />
				&#32;
				<span class="icon-hand-right"></span>&#32;
		</a>
	</s:else>
</div>
