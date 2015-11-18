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

<div class="cornice">
   <div class="jpbasecamp jpbasecamp-todo-list">
      <h2 class="title"><wp:i18n key="jpbasecamp_DOCUMENT_DELETE"/></h2>
      <s:if test="logged">
         <div class="col-md-6 todoList">
            <h4><wp:i18n key="jpbasecamp_DEL_DOC"/></h4>

            <div class="bubbleDoc">
               <form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/documentDelete.action" />"  method="post" enctype="multipart/form-data">
                     <%--
                         
                     <s:form action="documentDelete">
                     --%>

                     <s:hidden name="did"/>
                  <s:hidden name="pid" value="%{userProject.project.id}" />
                  <div class="checkBorder">
                     <span class="bold"><wp:i18n key="jpbasecamp_PROJECT_NAME" />&nbsp;&nbsp;&nbsp;</span>
                     <span><s:property value="%{userProject.project.name}" /></span>
                  </div>

                  <div class="checkBorder">
                     <span class="bold"><wp:i18n key="jpbasecamp_DOCUMENT_NAME" />&nbsp;&nbsp;&nbsp;</span>
                     <span><s:property value="%{getDocumentFileName(did)}" /></span>
                  </div>


                  <wpsf:submit type="button" cssClass="englo_button " >
                     <wp:i18n key="jpbasecamp_DELETE_ATTACHMENT" />
                  </wpsf:submit>

               </form>
            </div>
            <div class="col-md-12"style="margin-left: -1.2em">
               <wp:pageWithWidget var="documentListPageVar" widgetTypeCode="jpbasecamp_project_document" />
               <a class="btn englo_button btn-link btnBack2" href="<wp:url page="${documentListPageVar.code}"></wp:url>">
                  <wp:i18n key="jpbasecamp_RETURN_DOCUMENT_LIST" />               
               </a>
            </div>
         </div>


      </s:if>
      <s:else>
         <wp:i18n key="jpbasecamp_MUST_LOGIN" />
      </s:else>

   </div>
</div>

<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>
