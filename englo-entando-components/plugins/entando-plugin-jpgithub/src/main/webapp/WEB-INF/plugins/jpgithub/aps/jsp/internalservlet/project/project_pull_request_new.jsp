<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>


<h2><wp:i18n key="jpgithub_PROJECT_PR_NEW" /></h2>

<s:if test="hasActionErrors()">
        <div class="alert alert-error">
                <p class="alert-heading"><wp:i18n key="jpgithub_ACTION_ERRORS" /></p>
                <ul>
                        <s:iterator value="actionErrors">
                                <li><s:property escape="false" /></li>
                        </s:iterator>
                </ul>
        </div>
</s:if>
<s:if test="hasFieldErrors()">
        <div class="alert alert-error">
                <p class="alert-heading"><wp:i18n key="jpgithub_FIELD_ERRORS" /></p>
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
                <p class="alert-heading"><wp:i18n key="jpgithub_ACTION_MESSAGES" /></p>
                <ul>
                        <s:iterator value="actionMessages">
                                <li><s:property escape="false" /></li>
                        </s:iterator>
                </ul>
        </div>
</s:if>


<form id="jpgithub_new_preq" action="<wp:action path="/ExtStr2/do/jpgithub/FrontEnd/Project/createPullRequest.action"/>" method="post">
    <s:hidden name="repository"/>
    
    <div>
        <wp:i18n key="jpgithub_PROJECT_title" />
        <wpsf:textfield name="title" />
    </div>
    
    <div>
        <wp:i18n key="jpgithub_PROJECT_body" />
        <wpsf:textfield name="body" />
    </div>
        
    <div>
        <wp:i18n key="jpgithub_PROJECT_BASE_BRANCH" />
        <wpsf:select list="branches" name="baseBranch"/>
    </div>
    
    <div>
        <wp:i18n key="jpgithub_PROJECT_HEAD_BRANCH" />
        <s:select list="branches" name="headBranch"/>
    </div>
    
    <p>
        <button type="submit">
            <wp:i18n key="jpgithub_NEW_PREQ" />
            &#32;
            <span class="icon-search icon-white"></span>
        </button>
    </p>
                                    
</form>
            
            
    <%-- Integration with github --%>
    <h3>
        <wp:pageWithWidget var="projectEntryPage" widgetTypeCode="jpgithub_entry" />
        <c:set var="projectEntryVar" value="${projectEntryPage.code}" />
        <a
            class="btn btn-primary"
            href="<wp:url page="${projectEntryVar}">

            <wp:parameter name="repository"><s:property value="repository" /></wp:parameter>
            </wp:url>"
            >
            &#32;
            <wp:i18n key="jpgithub_PROJECT_ENTRY" />
        </a>
    </h3>      