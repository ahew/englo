<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri ="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>


<div class="cornice">
    <div class="jpbasecamp jpbasecamp-todo-list">
        <h2 class="title"><wp:i18n key="jpbasecamp_DOCUMENT_LIST" /> </h2>
        <div class="col-md-6 todoList">
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
            
            
            <s:set var="uploadInEntandoLabel"><wp:i18n key="jpbasecamp_UPLOAD_ENTANDO"/></s:set>
            
            <s:set var="acceptPreqLabel" ><wp:i18n key="jpbasecamp_TRASH_ATTACHMENT_LABEL" /></s:set>
            <s:set var="acceptPreqTitle" ><wp:i18n key="jpbasecamp_TRASH_ATTACHMENT_TITLE" /></s:set>
            
            <form action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/documentTrash.action" />"  method="post" enctype="multipart/form-data">
                  
                  <s:if test="%{null == userProject.documents || userProject.documents.isEmpty()}">
                    <wp:i18n key="jpbasecamp_NO_DOCUMENTS"/>
                </s:if>
                <s:else>
                    <s:iterator value="userProject.documents" var="document" >
                        
                        <div class="bubbleDoc">
                            <s:property value="%{#document.key}" />
                            <wpsa:actionParam action="documentTrash" var="actionNameVar" >
                                <wpsa:actionSubParam name="did" value="%{#document.value.id}" />
                                <wpsa:actionSubParam name="pid" value="%{userProject.project.id}" />
                            </wpsa:actionParam>
                            <div class="pippo">
                                <wpsf:submit cssClass="btn englo_button btn-link " action="%{#actionNameVar}" value="%{#acceptPreqLabel}" title="%{#acceptPreqTitle}" />
                            </div>
                        </div>
                        
                        <br/>
                    </s:iterator>
                </s:else>
                
            </form>
            
            <h4><wp:i18n key="jpbasecamp_LOAD_DOC"/></h4>
            <div class="bubbleDoc">
                <form 
                    action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/doUpload.action"/>" 
                    method="post" 
                    enctype="multipart/form-data">
                    
                    
                    <s:hidden name="pid" value="%{userProject.project.id}"/>
                    <div class="checkBorder">
                        <span><wp:i18n key="jpbasecamp_UPLOAD_ENTANDO" /> </span>
                        <span class="checkSpacer"><s:checkbox name="uploadInEntando" /> <br/></span>
                    </div>
                    <div class="checkBorder">
                        
                        <span ><wp:i18n key="jpbasecamp_UPLOAD_PROTECTED" /></span>
                        <span class="checkSpacer"><s:checkbox name="uploadProtected" /> <br/></span>
                    </div>
                    
                    <!--    <s:checkbox name="uploadResource" label="Upload as resource"/> <br/>-->
                    <div class="checkBorder">
                        <s:file name="upload" label="upload" id="upload" cssClass="englo_button"/>
                    </div>
                    
                    <div class="checkBorder">
                        <s:submit cssClass="btn englo_button btn-link"/>
                    </div>
                    
                </form>
            </div>
            
            <wp:pageWithWidget var="projectViewPageVar" widgetTypeCode="jpbasecamp_project_view" />
            <a class="btn englo_button btn-link btnBack2" href="<wp:url page="${projectViewPageVar.code}"><wp:parameter name="pid"><s:property value="%{userProject.project.id}" /></wp:parameter></wp:url>">
                <wp:i18n key="jpbasecamp_RETURN_DETAIL_DOCUMENT" />               
            </a>
        </div>
    </div>
</div>



<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>
