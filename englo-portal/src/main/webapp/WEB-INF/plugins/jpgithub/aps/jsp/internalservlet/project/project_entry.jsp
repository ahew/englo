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

      <h2 class="title"><wp:i18n key="jpgithub_REPOSITORY" /></h2>

      <div class="gitHubBubble">
          <s:if test="%{null != pid}" >
            <wp:pageWithWidget var="viewProjectPage" widgetTypeCode="jpbasecamp_project_view" />
            <a style="margin-bottom:1em;"
               class="btn btn-primary englo_button"
               href="<wp:url page="${viewProjectPage.code}">
                  <wp:parameter name="pid"><s:property value="%{pid}"/></wp:parameter>
               </wp:url> ">
               <wp:i18n key="jpbasecamp_BACK_TO" />
            </a>
          </s:if>
          

         <p>
         <h4 style="margin"><wp:i18n key="jpgithub_REPOSITORY_NAME" /></h4>
         <b class="repoName">
            <s:property value="repository" />
         </b>
         </p>

         <form id="jpgithub_set_branch" action="<wp:action path="/ExtStr2/do/jpgithub/FrontEnd/Project/entry.action"/>" method="post">
            <s:hidden name="repository"/>

            <s:if test="%{null == branches || branches.isEmpty()}">
                <wp:i18n key="jpgithub_NO_BRANCHES"/>
            </s:if>
            <s:else>
                <div class="githubBranches">
                   <h5><wp:i18n key="jpgithub_PROJECT_branches" />&nbsp;<br>
                      <s:select list="branches" name="headBranch"/></h5>
                </div>

                <p>
                   <button type="submit" class="btn englo_button">
                      <wp:i18n key="jpgithub_SET_BRANCH" />

                   </button>
                </p>
            </s:else>
         </form>
      </div>

      <h2 class="title3" style="margin-top:2em; " role="button" data-toggle="collapse" href="#collapseTodo" aria-expanded="true" aria-controls="collapseTodo"><wp:i18n key="jpgithub_PROJECT_commits:" /><i class="icon-chevron-down" style="float: right;margin-top: 10px;margin-right: 13px;"></i></h2>
      <div class="collapse" id="collapseTodo">
         <div class="todoList">
             
            <s:if test="%{null == commits || commits.isEmpty()}">
                <wp:i18n key="jpgithub_NO_COMMITS"/>
            </s:if>
            <s:else>
                <s:iterator value="commits" var="commitVar" >
                   <ul class="list-group">
                      <li class="list-group-item"><s:property value="%{#commitVar.commit.committer.date}" />&nbsp;:
                         <s:property value="%{#commitVar.commit.message}" /> 
                         &nbsp;<wp:i18n key="jpgithub_PROJECT_by" />&nbsp;
                         <s:property value="%{#commitVar.commit.committer.name}" />
                      </li>
                   </ul>
                   </br>
                </s:iterator>
            </s:else> 
         </div>   
      </div>

      <div style="display: inline-flex;">
         <div >
            <%-- LINK list pull request --%>
            <wp:pageWithWidget var="pullReqPage" widgetTypeCode="jpgithub_pullrequest" />
            <c:set var="pullReqPageCodeVar" value="${pullReqPage.code}" />
            <a
               class="btn englo_button btn-primary"
               href="<wp:url page="${pullReqPageCodeVar}">

                  <wp:parameter name="repository"><s:property value="repository" /></wp:parameter>
               </wp:url>"
               >

               <wp:i18n key="jpgithub_PULL_REQUEST_LIST" />
            </a>
         </div>

         <div style="margin-left:1em;">
            <%-- LINK new pull request button --%>
            <wp:pageWithWidget var="pullReqNewPage" widgetTypeCode="jpgithub_pullrequest_new" />
            <c:set var="pullReqNewPageCodeVar" value="${pullReqNewPage.code}" />
            <a
               class="btn englo_button btn-primary"
               href="<wp:url page="${pullReqNewPageCodeVar}">

                  <wp:parameter name="repository"><s:property value="repository" /></wp:parameter></wp:url>" >
               <wp:i18n key="jpgithub_PULL_REQUEST_NEW" />
            </a>
         </div>
      </div>
   </section>
</div>