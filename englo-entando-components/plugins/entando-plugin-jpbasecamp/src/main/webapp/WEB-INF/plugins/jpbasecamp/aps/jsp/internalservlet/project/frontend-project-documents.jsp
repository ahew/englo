<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri ="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>
    
    
<h1><wp:i18n key="jpbasecamp_DOCUMENT_LIST" /> </h1>
    
<s:if test="hasActionErrors()">
    <div class="alert alert-error">
        <p class="alert-heading"><wp:i18n key="jpbasecamp_ACTION_ERRORS" /></p>
        <ul>
            <s:iterator value="actionErrors">
                <li><s:property escape="false" /></li>
                </s:iterator>
        </ul>
    </div>
</s:if>
<s:if test="hasFieldErrors()">
    <div class="alert alert-error">
        <p class="alert-heading"><wp:i18n key="jpbasecamp_FIELD_ERRORS" /></p>
        <ul>
            <s:iterator value="fieldErrors">
                <s:iterator value="value">
                    <li><s:property escape="false" /></li>
                    </s:iterator>
                </s:iterator>
        </ul>
    </div>
</s:if>
<s:if test="hasActionMessages()">
    <div class="alert alert-info">
        <p class="alert-heading"><wp:i18n key="jpbasecamp_ACTION_MESSAGES" /></p>
        <ul>
            <s:iterator value="actionMessages">
                <li><s:property escape="false" /></li>
                </s:iterator>
        </ul>
    </div>
</s:if>
    
    
<s:set var="uploadInEntandoLabel"><wp:i18n key="jpbasecamp_UPLOAD_ENTANDO"/></s:set>
    
<wp:i18n key="jpbasecamp_document"/>
    
<wp:i18n key="jpbasecamp_TRASH_ATTACHMENT_LABEL" var="acceptPreqLabel" />
<wp:i18n key="jpbasecamp_TRASH_ATTACHMENT_TITLE" var="acceptPreqTitle"/>
    
<form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/documentTrash.action" />"  method="post" enctype="multipart/form-data">
    
    <s:iterator value="userProject.documents" var="document" >
        <s:property value="%{#document.key}" />
            
        <wpsa:actionParam action="documentTrash" var="actionNameVar" >
            <wpsa:actionSubParam name="did" value="%{#document.value.id}" />
            <wpsa:actionSubParam name="pid" value="%{userProject.project.id}" />
        </wpsa:actionParam>
        <wpsf:submit cssClass="button" action="%{#actionNameVar}" value="%{#acceptPreqLabel}" title="%{#acceptPreqTitle}" />
            
            
        <br/>
    </s:iterator>
        
</form>
    
<form 
    action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/doUpload.action"/>" 
    method="post" 
    enctype="multipart/form-data">
        
        
    <s:hidden name="pid" value="%{userProject.project.id}"/>
    <wp:i18n key="jpbasecamp_UPLOAD_ENTANDO" />
    <s:checkbox name="uploadInEntando" /> <br/>
    <wp:i18n key="jpbasecamp_UPLOAD_PROTECTED" />
    <s:checkbox name="uploadProtected" /> <br/>
    <!--    <s:checkbox name="uploadResource" label="Upload as resource"/> <br/>-->
        
    <s:file name="upload" label="upload" id="upload"/>
    <s:submit/>
</form>
    
<wp:pageWithWidget var="projectViewPageVar" widgetTypeCode="jpbasecamp_project_view" />
<a class="btn englo_button btn-link" style="margin-left: 3em" href="<wp:url page="${projectViewPageVar.code}"><wp:parameter name="pid"><s:property value="%{userProject.project.id}" /></wp:parameter></wp:url>">
    <wp:i18n key="jpbasecamp_RETURN_DOCUMENT_LIST" />               
</a>
    
    
    
<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>