<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<h2><wp:i18n key="jpgithub_REPOSITORY" /></h2>


<p>
    <wp:i18n key="jpgithub_PROJECT_current" />
    <b>
        <s:property value="repository" />
    </b>
</p>

<form id="jpgithub_set_branch" action="<wp:action path="/ExtStr2/do/jpgithub/FrontEnd/Project/entry.action"/>" method="post">
    <s:hidden name="repository"/>
    
    <div>
        <wp:i18n key="jpgithub_PROJECT_branches" />
        <s:select list="branches" name="headBranch"/>
    </div>
        
    <p>
        <button type="submit">
            <wp:i18n key="jpgithub_SET_BRANCH" />
            &#32;
            <span class="icon-search icon-white"></span>
        </button>
    </p>
                                    
</form>

<div>
    <p>
        <wp:i18n key="jpgithub_PROJECT_commits:" />
    </p>
    <s:iterator value="commits" var="commitVar" >
        <s:property value="%{#commitVar.commit.committer.date}" />   :
        <s:property value="%{#commitVar.commit.message}" /> 
        &nbsp;<wp:i18n key="jpgithub_PROJECT_by" />&nbsp;
        <s:property value="%{#commitVar.commit.committer.name}" />
        </br>
    </s:iterator>
</div>

<p>
        <%-- LINK list pull request --%>
        <wp:pageWithWidget var="pullReqPage" widgetTypeCode="jpgithub_pullrequest" />
        <c:set var="pullReqPageCodeVar" value="${pullReqPage.code}" />
        <a
                class="btn btn-primary"
                href="<wp:url page="${pullReqPageCodeVar}">
                        
                        <wp:parameter name="repository"><s:property value="repository" /></wp:parameter>
                </wp:url>"
                >
                        <span class="icon-plus icon-white"></span>&#32;
                        <wp:i18n key="jpgithub_PULL_REQUEST_LIST" />
        </a>
</p>

<p>
        <%-- LINK new pull request button --%>
        <wp:pageWithWidget var="pullReqNewPage" widgetTypeCode="jpgithub_pullrequest_new" />
        <c:set var="pullReqNewPageCodeVar" value="${pullReqNewPage.code}" />
        <a
                class="btn btn-primary"
                href="<wp:url page="${pullReqNewPageCodeVar}">
                        
                        <wp:parameter name="repository"><s:property value="repository" /></wp:parameter>
                </wp:url>"
                >
                        <span class="icon-plus icon-white"></span>&#32;
                        <wp:i18n key="jpgithub_PULL_REQUEST_NEW" />
        </a>
</p>