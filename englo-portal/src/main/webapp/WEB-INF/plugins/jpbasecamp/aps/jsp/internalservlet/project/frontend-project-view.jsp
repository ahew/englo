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

<wp:currentPage param="code" var="currentPageCodeVar" />
<wp:pageWithWidget var="viewPage" widgetTypeCode="jpbasecamp_todolist_view" />
<c:set var="viewPageVar" value="${viewPage.code}" />


<div class="cornice">
   <section class="jpbasecamp jpbasecamp-project-new">

      <div class="row-fluid">
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

               <div class="col-md-6 todoList">

                  <div style="margin-top:-1em;">
                     <label for="jpbasecamp-name-name" class="control-labels"></label><br>
                     <h5><wp:i18n key="jpbasecamp_LABEL_NAME" /></h5>
                     <div class="controls2">
                        <wpsf:textfield name="userProject.project.name" id="jpbasecamp-name-name" cssClass="input-xlarge" />
                     </div>
                  </div>
                  <div class=""style="margin-bottom:2em;">
                     <label for="jpbasecamp-description" class="control-labels"></label><br>
                     <h5><wp:i18n key="jpbasecamp_LABEL_DESCRIPTION" /></h5>
                     <div class="controls2">
                        <wpsf:textfield name="userProject.project.description" id="jpbasecamp-description" cssClass="input-xlarge" />
                     </div>
                  </div>

                  <s:if test="%{StrutsAction == 2}">

                     <div class="control-groups">
                        <label for="jpbasecamp-description" class="control-labels"><wp:i18n key="jpbasecamp_LABEL_CREATED" /></label>
                        <div class="controls2">
                           <s:date name="userProject.project.createdAt" nice="false"/>
                        </div>
                     </div>

                     <div class="control-groups">
                        <label for="jpbasecamp-description" class="control-labels"><wp:i18n key="jpbasecamp_LABEL_UPDATED" /></label>
                        <div class="controls2">
                           <s:date name="userProject.project.updatedAt" nice="false"/>
                        </div>
                     </div>

                     <div class="control-groups">
                        <label for="jpbasecamp-description" class="control-labels"><wp:i18n key="jpbasecamp_LABEL_DRAFT" /></label>
                        <div class="controls2">
                           <s:property value="userProject.project.draft" />
                        </div>
                     </div>

                     <div class="control-groups">
                        <label for="jpbasecamp-description" class="control-labels"><wp:i18n key="jpbasecamp_LABEL_STARRED" /></label>
                        <div class="controls2">
                           <s:property value="userProject.project.starred" />
                        </div>
                     </div>

                     <div class="control-groups">
                        <label for="jpbasecamp-description" class="control-labels"><wp:i18n key="jpbasecamp_LABEL_ISCLIENTPROJECT" /></label>
                        <div class="controls2">
                           <s:property value="userProject.project.isClientProject" />
                        </div>
                     </div>
               </div>

               <div class="col-md-12">
                  <h2 class="title2" role="button" data-toggle="collapse" href="#collapseTodo" aria-expanded="true" aria-controls="collapseTodo"><wp:i18n key="jpbasecamp_LABEL_TODOLISTS" /><i class="icon-chevron-down" style="float: right;margin-top: 10px;margin-right: 13px;" ></i></h2>
                     <%-- new Todolist --%>

                  <div class="collapse in" id="collapseTodo">
                     <div class="todoList">
                        <a class="btn englo_button btn-primary"
                           href="<wp:url page="${viewPageVar}" >
                           <wp:parameter name="pid"><s:property value="%{userProject.project.id}"/></wp:parameter>
                           <wp:parameter name="pageCode"><c:out value="${currentPageCodeVar}" /></wp:parameter>
                           </wp:url>"
                           >
                           <wp:i18n key="jpbasecamp_TODOLIST_NEW" />
                        </a>
                        <%-- Integration with github --%>
                        <h3>
                           <wp:pageWithWidget var="projectEntryPage" widgetTypeCode="jpgithub_entry" />
                           <c:set var="projectEntryVar" value="${projectEntryPage.code}" />
                           <a
                              class="btn englo_button btn-primary" href="<wp:url page="${projectEntryVar}">

                              <wp:parameter name="repository"><s:property value="userProject.project.name" /></wp:parameter>
                              <wp:parameter name="pid"><s:property value="%{userProject.project.id}" /></wp:parameter>
                              </wp:url>"
                              >
                              &#32;
                              <wp:i18n key="jpgithub_PROJECT_ENTRY" />
                           </a>
                        </h3>              
                        <div class="control-groups">
                           <div class="row-fluid">
                              <s:iterator value="userProject.todolist" status="stat">

                                 <%-- Todolists // START --%>
                                 <s:set var="todolistVar" value="%{userProject.getTodolist(#stat.index)}"/>

                                 <s:set var="totalCountVar" value="%{userProject.getTotalCount(#stat.index)}" />
                                 <s:set var="remainingCountVar" value="%{userProject.getRemainingCount(#stat.index)}" />
                                 <s:set var="completedCountVar" value="%{#totalCountVar - #remainingCountVar}" />
                                 <div class="col-md-5 toDobubble">

                                    <a href="<wp:url page="${viewPageVar}">
                                       <wp:parameter name="pid"><s:property value="%{userProject.project.id}"/></wp:parameter>
                                       <wp:parameter name="tdlid"><s:property value="%{#todolistVar.id}"/></wp:parameter>
                                       <wp:parameter name="isTodolistEdit">true</wp:parameter>
                                       <wp:parameter name="pageCode"><c:out value="${currentPageCodeVar}" /></wp:parameter>
                                       </wp:url>"
                                       >
                                       <h4><s:property value="%{#todolistVar.name}"/></h4>
                                    </a>
                                    <p class="progressBar">
                                    <h5 style="margin-bottom: 1em;margin-top: 1em;"><wp:i18n key="jpbasecamp_PROGRESS_BAR" />&nbsp;&nbsp;<s:property value="%{#completedCountVar}"/>/<s:property value="%{#totalCountVar}"/></h5>
                                    </p>

                                    <h4><s:property value="%{#todolistVar.description}"/></h4>

                                    <%-- Todos // START --%>
                                    <s:if test="%{#totalCountVar > 0 }">

                                       <s:if test="%{#remainingCountVar > 0}" >

                                          <h5 style="margin-bottom: 1em;margin-top: 1em;"><wp:i18n key="jpbasecamp_TODO_REMAINING" /></h5>

                                          <%--remaining Todo --%>
                                          <s:iterator value="%{#todolistVar.remaningList}" var="todoVar">

                                             <p>
                                                <%--
                                                        <s:property value="%{userProject.project.id}"/>-<s:property value="%{#todolistVar.id}"/>-<s:property value="%{#todoVar.id}"/></b>
                                                --%>

                                             </p>
                                             <!--                                             <wpsf:checkbox name="todo_%{#todoVar.id}"/>-->

                                             <%-- link for todo editing --%>
                                             <a href="<wp:url page="${viewPageVar}">
                                                <wp:parameter name="pid"><s:property value="%{userProject.project.id}"/></wp:parameter>
                                                <wp:parameter name="tdlid"><s:property value="%{#todolistVar.id}"/></wp:parameter>
                                                <wp:parameter name="tdid"><s:property value="%{#todoVar.id}"/></wp:parameter>
                                                <wp:parameter name="pageCode"><c:out value="${currentPageCodeVar}" /></wp:parameter>
                                                </wp:url>"
                                                >
                                                <s:property value="%{#todoVar.content}"/>
                                             </a>


                                             <s:if test="%{#todoVar.dueOn != null}">
                                                &nbsp;
                                                <s:date name="%{#todoVar.dueOn}" format="dd/MM/yyyy"/>
                                             </s:if>
                                             <s:if test="%{#todoVar.assignee != null}">
                                                &nbsp;
                                                <s:property value="%{#todoVar.assignee.name}"/>
                                             </s:if>
                                             </p>

                                          </s:iterator>
                                       </s:if>
                                       <s:else>
                                          <wp:i18n key="jpbasecamp_TODO_NO_REMAINING" />
                                       </s:else>
                                       <s:if test="%{#completedCountVar > 0}" >

                                          <h5 style="margin-bottom: 1em;margin-top: 1em;"><wp:i18n key="jpbasecamp_TODO_COMPLETED" /></h5>

                                          <%--completed Todo --%>
                                          <s:iterator value="%{#todolistVar.completedList}" var="todoVar">

                                             <p>
                                                <!--<wpsf:checkbox name="todo_%{#todoVar.id}"/>-->
                                             <s:property value="%{#todoVar.content}"/>
                                             <s:if test="%{#todoVar.dueOn != null}">
                                                &nbsp;
                                                <s:date name="%{#todoVar.dueOn}" format="dd/MM/yyyy"/>
                                             </s:if>
                                             <s:if test="%{#todoVar.assignee != null}">
                                                &nbsp;
                                                <s:property value="%{#todoVar.assignee.name}"/>
                                             </s:if>
                                             </p>

                                          </s:iterator>
                                       </s:if>
                                       <s:else>
                                          <wp:i18n key="jpbasecamp_TODO_NO_COMPLETED" />
                                       </s:else>

                                       <%-- Todos // END --%>
                                    </s:if>
                                    <s:else>
                                       <wp:i18n key="jpbasecamp_LABEL_NO_TODOS" />
                                    </s:else>
                                    <%-- Todolists // END --%>

                                    <p>
                                       <%-- new Todo button --%>
                                       <a class="btn englo_button btn-primary pull-right"
                                          href="<wp:url page="${viewPageVar}">
                                          <wp:parameter name="pid"><s:property value="%{userProject.project.id}"/></wp:parameter>
                                          <wp:parameter name="tdlid"><s:property value="%{#todolistVar.id}"/></wp:parameter>
                                          <wp:parameter name="pageCode"><c:out value="${currentPageCodeVar}" /></wp:parameter>

                                          </wp:url>"
                                          >
                                          <span class="icon-plus icon-white"></span>&#32;
                                          <wp:i18n key="jpbasecamp_TODO_NEW" />
                                       </a>
                                    </p>
                                 </div>
                              </s:iterator>
                           </div>
                        </div>
                     </div>


                  </div>


                  </s:if><%-- end of block for existing projects --%>
               </div>      

               <div class="col-md-12" style="margin-top: 2em;margin-bottom: -2em;margin-left: -1em;" >
                  <p>
                     <%-- save --%>
                  <wpsf:submit type="button" cssClass="btn englo_button btn-primary" action="save" >
                     <wp:i18n key="jpbasecamp_PROJECT_SAVE" />
                  </wpsf:submit>

                  <%-- upload --%><%--
                  <s:if test="%{StrutsAction == 2}">
                     <wpsf:submit type="button" cssClass="btn englo_button btn-primary" action="trash">
                        <wp:i18n key="jpbasecamp_PROJECT_TRASH" />
                     </wpsf:submit>
                  </s:if>
                  --%>


                  <%-- trash --%>
                  <s:if test="%{StrutsAction == 2}">
                     <wpsf:submit type="button" cssClass="btn englo_button btn-primary" action="trash">
                        <wp:i18n key="jpbasecamp_PROJECT_TRASH" />
                     </wpsf:submit>
                  </s:if>

                  <wp:pageWithWidget var="listPage" widgetTypeCode="jpbasecamp_project_list" />
                  <a class="btn englo_button btn-link" style="margin-left: 3em" href="<wp:url page="${empty param.returnPage ? listPage.code : param.returnPage}"><wp:parameter name="searchText" value="${param.searchText}" /></wp:url>">
                     <s:if test="%{strutsAction == 1}">
                        <wp:i18n key="jpbasecamp_GOTO_LIST" />
                     </s:if>
                     <s:else>
                        <wp:i18n key="jpbasecamp_RETURN_LIST" />
                     </s:else>
                  </a>
                     
                  <s:if test="%{StrutsAction == 2}">
                    <wp:pageWithWidget var="documentListPageVar" widgetTypeCode="jpbasecamp_project_document" />
                    <a class="btn englo_button btn-link" style="margin-left: 3em" href="<wp:url page="${documentListPageVar.code}"></wp:url>">
                      <wp:i18n key="jpbasecamp_DOCUMENT_LIST" />               
                    </a>
                  </s:if> 

               </div>                
            </form> 

         </s:if>
         <s:else>
            <wp:i18n key="jpbasecamp_MUST_LOGIN" />
         </s:else>
   </section>


</div>


<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>
