<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="wp" uri="/aps-core"%>
<%@ taglib prefix="wpsa" uri="/apsadmin-core"%>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>

<oa2:authentication service="1"><%-- 1 for Basecamp --%>
	<s:set var="logged" value="true"/>
</oa2:authentication>

<div class="jpsaleforce-lead-trash">
	<h2><wp:i18n key="jpbasecamp_PROJECT_TRASH"/></h2>
	<s:if test="logged">
		<form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/delete.action"/>" method="post" >
		
			<p>
				<p class="sr-only noscreen">
				<wpsf:hidden name="pid" value="%{userProject.project.id}"/>
				</p>
				<wp:i18n key="jpbasecamp_DELETE_CONFIRM"/>
				&#32;
				<em><s:property	value="userProject.project.name" /></em>
				?
			</p>
			<p class="form-actions">
				<%-- delete button --%>
				<wpsf:submit type="button" cssClass="btn btn-danger">
					<wp:i18n key="jpbasecamp_DELETE"/>
				</wpsf:submit>
				<%-- go to list --%>
					<wp:pageWithWidget var="viewPageVar" widgetTypeCode="jpbasecamp_project_view" />
					<a class="btn btn-link"
						href="<wp:url page="${empty param.returnPage ? viewPageVar.code : param.returnPage}" >
							<wp:parameter name="pid"><s:property value="%{userProject.project.id}" /></wp:parameter></wp:url>
						">
						<wp:i18n key="jpbasecamp_GOTO_LIST" />
					</a>
			</p>
		</form>
	</s:if>
	<s:else>
		<wp:i18n key="jpbasecamp_MUST_LOGIN" />
	</s:else>
</div>