<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>
    
<oa2:authentication service="1"><%-- 1 for Basecamp --%>
    <s:set var="logged" value="true"/>
</oa2:authentication>
    
<div class="jpsaleforce-lead-trash">
    <h2><wp:i18n key="jpbasecamp_DOCUMENT_TRASH"/></h2>
    <s:if test="logged">
        
        <form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/documentDelete.action" />"  method="post" enctype="multipart/form-data">
              <%--
                  
              <s:form action="documentDelete">
              --%>
                  
              <s:hidden name="did"/>
            <s:hidden name="pid" value="%{userProject.project.id}" />
                
            <wp:i18n key="jpbasecamp_PROJECT_NAME" />:&nbsp;
            <s:property value="%{userProject.project.name}" /> <br/>
            <wp:i18n key="jpbasecamp_DOCUMENT_NAME" />:&nbsp;
            <s:property value="%{getDocumentFileName(did)}" />
                
            <wpsf:submit type="button" >
                <span class="icon fa fa-times-circle"></span>&#32;
                <wp:i18n key="jpbasecamp_DELETE_ATTACHMENT" />
            </wpsf:submit>
                
            </form>

                <wp:pageWithWidget var="documentListPageVar" widgetTypeCode="jpbasecamp_project_document" />
                <a class="btn englo_button btn-link" style="margin-left: 3em" href="<wp:url page="${documentListPageVar.code}"></wp:url>">
                    <wp:i18n key="jpbasecamp_RETURN_DOCUMENT_LIST" />               
                </a>

        </s:if>
        <s:else>
            <wp:i18n key="jpbasecamp_MUST_LOGIN" />
        </s:else>
</div>

<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>