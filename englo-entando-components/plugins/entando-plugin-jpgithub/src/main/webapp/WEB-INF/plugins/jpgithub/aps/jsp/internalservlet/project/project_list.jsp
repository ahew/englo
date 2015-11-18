<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<h2><wp:i18n key="jpgithub_REPOSITORY" /></h2>

    <div>
    <p>
        <wp:i18n key="jpgithub_PROJECT_repositories:" />
    </p>
    <s:iterator value="repositories" var="repositoriesVar" >

        <%-- LINK project entry --%>
        <wp:pageWithWidget var="pullReqNewPage" widgetTypeCode="jpgithub_entry" />
        <c:set var="pullReqNewPageCodeVar" value="${pullReqNewPage.code}" />
        <a
                class="btn btn-primary"
                href="<wp:url page="${pullReqNewPageCodeVar}">
                        
                        <wp:parameter name="repository"><s:property value="%{#repositoriesVar}" /></wp:parameter>
                </wp:url>"
                >
                        <s:property value="%{#repositoriesVar}" />
        </a>
        </br>
    </s:iterator>
</div>
