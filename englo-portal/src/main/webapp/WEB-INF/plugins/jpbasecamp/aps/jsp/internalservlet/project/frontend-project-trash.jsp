<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="wp" uri="/aps-core"%>
<%@ taglib prefix="wpsa" uri="/apsadmin-core"%>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<link rel="stylesheet" href="<wp:cssURL />master.css" media="screen" />
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<oa2:authentication service="1"><%-- 1 for Basecamp --%>
   <s:set var="logged" value="true"/>
</oa2:authentication>
<div class="corniceGit">
   <div class="jpsaleforce-lead-trash">
      <h2 class="title"><wp:i18n key="jpbasecamp_PROJECT_TRASH"/></h2>
      <div class="todoList">
         <s:if test="logged">
            <form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/delete.action"/>" method="post" >

               <p>
               <p class="sr-only noscreen">
                  <wpsf:hidden name="pid" value="%{userProject.project.id}"/>
               </p>
               <h4><wp:i18n key="jpbasecamp_DELETE_CONFIRM"/>
                  &#32;
                  <em class="color"><s:property	value="userProject.project.name" /></em>
                  ?
               </h4>
               <p class="">
                  <%-- delete button --%>
                  <wpsf:submit type="button" cssClass="englo_button">
                     <wp:i18n key="jpbasecamp_DELETE"/>
                  </wpsf:submit>
               </p>
            </form>
                  <%-- go to list --%>
                  <a href="<wp:url page="${empty param.returnPage ? viewPageVar.code : param.returnPage}" >
                             <wp:parameter name="pid"><s:property value="%{userProject.project.id}" /></wp:parameter></wp:url>
                                ">
                      <wp:i18n key="jpbasecamp_GOTO_LIST" />
                  </a>
         </s:if>
         <s:else>
            <wp:i18n key="jpbasecamp_MUST_LOGIN" />
         </s:else>
      </div>
   </div>
</div>
      
<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>