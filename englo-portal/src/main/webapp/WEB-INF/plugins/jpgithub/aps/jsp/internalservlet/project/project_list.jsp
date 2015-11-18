<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
<div class="corniceGit">

   <h2 class="title"><wp:i18n key="jpgithub_REPOSITORY" /></h2>

   <div class="jpbasecamp">

      <h4 style="margin-bottom: 2em;"><wp:i18n key="jpgithub_PROJECT_repositories" /></h4>

      <s:iterator value="repositories" var="repositoriesVar" >
         <section class=" blue_block row-fluid">
            <div class="blocks shape trans col-md-2 ng-scope">

               <%-- LINK project entry --%>
               <wp:pageWithWidget var="pullReqNewPage" widgetTypeCode="jpgithub_entry" />
               <c:set var="pullReqNewPageCodeVar" value="${pullReqNewPage.code}" />
               <a class="truncate"
                  href="<wp:url page="${pullReqNewPageCodeVar}">
                     <wp:parameter name="repository"><s:property value="%{#repositoriesVar}" /></wp:parameter>
                  </wp:url>"> <s:property value="%{#repositoriesVar}" /></a> >
            </div>
         </section>
         </br>
      </s:iterator>
   </div>
</div>

<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY --%>