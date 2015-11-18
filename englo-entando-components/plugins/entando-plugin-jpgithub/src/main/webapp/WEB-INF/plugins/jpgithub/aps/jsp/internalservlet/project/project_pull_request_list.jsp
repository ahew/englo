<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<h2><wp:i18n key="jpgithub_PROJECT_PR_LIST" /></h2>

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


<s:if test="%{pullRequests != null}" >
    
    <s:set var="acceptPreqLabel" ><wp:i18n key="jpgithub_ACCEPT_PULL_REQUEST" /></s:set>
    <s:set var="acceptPreqTitle" ><wp:i18n key="jpgithub_ACCEPT_PULL_REQUEST" /></s:set>
    
        <form action="<wp:action path="/ExtStr2/do/jpgithub/FrontEnd/Project/confirmPullRequest.action" />"  method="post" enctype="multipart/form-data">
    
        <s:iterator value="pullRequests" var="prVar" >
        <p>
            <wp:i18n key="jpgithub_NUMBER"/>:&nbsp;
            <s:property value="%{#prVar.number}" /><br/>
            <wp:i18n key="jpgithub_TITLE"/>:&nbsp;
            <s:property value="%{#prVar.title}" /><br/>
            <wp:i18n key="jpgithub_BODY"/>:&nbsp;
            <s:property value="%{#prVar.body}" /><br/>

            <%-- MERGEABLE --%>
            <s:if test="%{#prVar.isMergeable()}">
                <wp:i18n key="jpgithub_MERGEABLE"/>
            </s:if>
            <s:else>
                <wp:i18n key="jpgithub_NOT_MERGEABLE"/>
            </s:else>
            <br/>
            <wp:i18n key="jpgithub_MERGED"/>:&nbsp;
            <s:property value="%{#prVar.isMerged()}" /><br/>
        </p>


        <s:if test="%{#prVar.isMergeable()}">
            <p>
                <wpsa:actionParam action="confirmPullRequest" var="actionNameVar" >
                    <wpsa:actionSubParam name="repository" value="%{repository}" />
                    <wpsa:actionSubParam name="pullRequestNumber" value="%{#prVar.number}" />
                    <wpsa:actionSubParam name="baseBranch" value="%{#prVar.head.label}" />
                </wpsa:actionParam>
                <wpsf:submit cssClass="button" action="%{#actionNameVar}" value="%{#acceptPreqLabel}" title="%{#acceptPreqTitle}" />

            </p>
        </s:if>
        
        <%--
        <p> 
            <wpsa:actionParam action="closePullRequest" var="actionNameVar" >
                <wpsa:actionSubParam name="repository" value="%{repository}" />
                <wpsa:actionSubParam name="pullRequestNumber" value="%{#prVar.number}" />
            </wpsa:actionParam>
            <wpsf:submit cssClass="button" action="%{#actionNameVar}" value="%{#acceptPreqLabel}" title="%{#acceptPreqTitle}" />
            
        </p>
        --%>

        </s:iterator>
   
    </form>
   
</s:if>
<s:else>
   <wp:i18n key="jpgithub_NO_PULL_REQUEST"/>     
</s:else>
    
    
<p>
        <%-- return to project entry link --%>
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
</p>