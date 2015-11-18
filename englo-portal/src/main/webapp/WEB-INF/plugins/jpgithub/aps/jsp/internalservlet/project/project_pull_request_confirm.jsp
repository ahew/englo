<%-- PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<div class="cornice">
   <section class="jpbasecamp ">

      <h2 class="title"><wp:i18n key="jpgithub_PROJECT_PR_CONFIRM" /></h2>

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

      <form class="todoList" style="width:31.5em" id="jpgithub_new_preq" action="<wp:action path="/ExtStr2/do/jpgithub/FrontEnd/Project/mergePullRequest.action"/>" method="post">
         <s:hidden name="repository"/>
         <s:hidden name="pullRequestNumber"/>
         <s:hidden name="baseBranch"/>

         <div style="margin-top:2em;">
            <h4><wp:i18n key="jpgithub_PROJECT_body" /></h4>
            <wpsf:textarea name="body" />
         </div>

         <p>
            <button type="submit" class="englo_button">
               <wp:i18n key="jpgithub_CONFIRM_PREQ" />

            </button>
         </p>

      </form>


      <p>
         <%-- return to project entry link --%>
         <wp:pageWithWidget var="projectEntryPage" widgetTypeCode="jpgithub_entry" />
         <c:set var="projectEntryVar" value="${projectEntryPage.code}" />
         <a
            class="btn btn-primary englo_button"
            href="<wp:url page="${projectEntryVar}">

               <wp:parameter name="repository"><s:property value="repository" /></wp:parameter>
            </wp:url>"
            >
            &#32;
            <wp:i18n key="jpgithub_PROJECT_ENTRY" />
         </a>
      </p>

      <section>
         </div>
         <%-- PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY PROJECT OVERLAY --%>