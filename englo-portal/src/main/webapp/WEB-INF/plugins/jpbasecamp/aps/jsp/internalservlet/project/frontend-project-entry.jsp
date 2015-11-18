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

<section class="jpbasecamp jpbasecamp-project-new">

   <h2 class="title"><wp:i18n key="jpbasecamp_VIEW_PROJECT" /></h2>

   <s:if test="%{#logged}">

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

      <form
         action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/save.action"/>"
         method="post"
         class="form-horizontal">

         <p class="sr-only noscreen">
         <wpsf:hidden name="pid" value="%{userProject.project.id}"/>
         <wpsf:hidden name="strutsAction" />
         </p>

         <div class="control-group">
            <label for="jpbasecamp-name-name" class="control-label"><wp:i18n key="jpbasecamp_LABEL_NAME" /></label>
            <div class="controls">
               <wpsf:textfield name="userProject.project.name" id="jpbasecamp-name-name" cssClass="input-xlarge" />
            </div>
         </div>
         <div class="control-group">
            <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_DESCRIPTION" /></label>
            <div class="controls2">
               <wpsf:textfield name="userProject.project.description" id="jpbasecamp-description" cssClass="input-xlarge" />
            </div>
         </div>

         <s:if test="%{StrutsAction == 2}">

            <div class="control-group">
               <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_CREATED" /></label>
               <div class="controls">
                  <s:date name="userProject.project.createdAt" nice="false"/>
               </div>
            </div>

            <div class="control-group">
               <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_UPDATED" /></label>
               <div class="controls">
                  <s:date name="userProject.project.updatedAt" nice="false"/>
               </div>
            </div>

            <div class="control-group">
               <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_DRAFT" /></label>
               <div class="controls">
                  <s:property value="userProject.project.draft" />
               </div>
            </div>

            <div class="control-group">
               <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_STARRED" /></label>
               <div class="controls">
                  <s:property value="userProject.project.starred" />
               </div>
            </div>

            <div class="control-group">
               <label for="jpbasecamp-description" class="control-label"><wp:i18n key="jpbasecamp_LABEL_ISCLIENTPROJECT" /></label>
               <div class="controls">
                  <s:property value="userProject.project.isClientProject" />
               </div>
            </div>
         </s:if>

         <p class="form-actions">
            <%-- save --%>
         <wpsf:submit type="button" cssClass="btn englo btn-primary" action="save" >
            <wp:i18n key="jpbasecamp_PROJECT_SAVE" />
         </wpsf:submit>


         <%-- trash --%>
         <s:if test="%{StrutsAction == 2}">
            <wpsf:submit type="button" cssClass="btn englo btn-primary" action="trash">
               <wp:i18n key="jpbasecamp_PROJECT_TRASH" />
            </wpsf:submit>
         </s:if>

         <wp:pageWithWidget var="listPage" widgetTypeCode="jpbasecamp_project_list" />
         <a class="btn englo btn-link" href="<wp:url page="${empty param.returnPage ? listPage.code : param.returnPage}"><wp:parameter name="searchText" value="${param.searchText}" /></wp:url>">
            <s:if test="%{strutsAction == 1}">
               <wp:i18n key="jpbasecamp_GOTO_LIST" />
            </s:if>
            <s:else>
               <wp:i18n key="jpbasecamp_RETURN_LIST" />
            </s:else>
         </a>
         </p>

      </form>

   </s:if>
   <s:else>
      <wp:i18n key="jpbasecamp_MUST_LOGIN" />
   </s:else>
</section>


<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>