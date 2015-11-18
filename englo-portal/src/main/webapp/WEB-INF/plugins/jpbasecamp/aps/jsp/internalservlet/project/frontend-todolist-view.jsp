<%@page import="org.apache.struts2.components.Include"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
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
<div class="cornice">
   <section class="jpbasecamp jpbasecamp-todo-list">

      <h2 class="title"><wp:i18n key="jpbasecamp_TODOLIST_NEW" />&nbsp;&nbsp;</h2>
      <%--
      +++ DEBUG DATA +++<br/>
      <s:if test="%{pid != null}">
              <p>
                      PID <s:property value="pid"/>
              </p>
      </s:if>

<s:if test="%{tdlid != null}">
        <p>
                TDLID <s:property value="tdlid"/>
        </p>
</s:if>

<s:if test="%{tdid != null}">
        <p>
                TDID <s:property value="tdid"/>
        </p>
</s:if>
<br/><br/>

      --%>

      <s:if test="%{pid != null}">
         <s:if test="%{tdlid != null && tdid != null}">
            <!-- EDIT TODO  -->

         </s:if>
         <s:elseif test="%{tdlid != null && tdid == null}">
            <!-- ADD TODO -->

         </s:elseif>
         <s:else>
            <!-- ADD TODOLIST -->

         </s:else>
      </s:if>
      <br/>


      <form class=" col-md-6 todoList" style="margin-top:-1em;width:46.5em;" action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/updateTodo.action"/>" method="post">

            <s:hidden class="noscreen">
            <s:hidden name="isTodolistEdit" />
            <s:hidden name="pid"/>
            <s:hidden name="tdlid"/>
            <s:hidden name="tdid"/>
            <s:hidden name="pageCode"/>
         </s:hidden>


         <s:if test="%{(isTodolistEdit != null && isTodolistEdit) || (null == tdlid && null == tdid)}">

            <div class="form-horizontal" style="padding:1em;">

               <div class="form-group">

                  <label class="control-label"><wp:i18n key="jpbasecamp_TODOLIST_NEW_NAME" /></label>
                  <div class="col-sm-3">
                     <wpsf:textfield name="name" cssClass="input-xlarge"/>
                  </div>
               </div>

               <div class="form-group">
                  <label class="control-label"><wp:i18n key="jpbasecamp_TODOLIST_NEW_DESCRIPTION" /></label>
                  <div class="col-sm-3">
                     <wpsf:textfield name="description" cssClass="input-xlarge"/>
                  </div>
               </div>

         </s:if>
         <%--
         <s:else>
                 <p>
                         <s:property value="name"/>
                 </p>
                 <p>
                         <s:property value="description"/>
                 </p>
         </s:else>
         --%>

         <s:if test="%{null != tdlid}">
            <div class="control-groups">
               <div class="toDobubble" style="width:30em;">

                  <%-- Todolists // START --%>
                  <s:set var="todolistVar" value="todolist"/>

                  <s:set var="totalCountVar" value="%{userProject.getTodolistTodos(tdlid)}" />
                  <s:set var="remainingCountVar" value="todolist.remaningList.size" />
                  <s:set var="completedCountVar" value="todolist.completedList.size" />
                  <p>
                  <h3 class="todoTitle"><s:property value="%{#todolistVar.name}"/></h3>
                  </p>
                  <p>
                  <h4><wp:i18n key="jpbasecamp_PROGRESS_BAR" />&nbsp;<s:property value="%{#completedCountVar}"/>/<s:property value="%{#totalCountVar}"/></h4>
                  </p>


                  <p>
                  <h4><s:property value="%{#todolistVar.description}"/></h4>
                  </p>

                  <%-- Todos // START --%>
                  <s:if test="%{#totalCountVar > 0}">

                     <s:if test="%{#remainingCountVar > 0}" >

                        <wp:i18n key="jpbasecamp_TODO_REMAINING" />

                        <%--remaining Todo --%>
                        <s:iterator value="%{#todolistVar.remaningList}" var="todoVar">

                           <p>
                              <%--
                                      <s:property value="%{userProject.project.id}"/>-<s:property value="%{#todolistVar.id}"/>-<s:property value="%{#todoVar.id}"/></b>
                              --%>

                           <wp:currentPage param="code" var="currentPageCodeVar" />
                           <wp:pageWithWidget var="viewPage" widgetTypeCode="jpbasecamp_todolist_view" />
                           <c:set var="viewPageVar" value="${viewPage.code}" />

                           </p>

                           <p>
                           <s:if test="%{tdid == #todoVar.id}">

                              <s:set var="updateButtonLabelVar"><wp:i18n key="jpbasecamp_SAVE"/></s:set>
                              <s:include value="inc/todo.jsp"/>
                           </s:if>
                           <s:else>
                              <%-- link for todo editing --%>
                              <a href="<wp:url page="${viewPageVar}">
                                 <wp:parameter name="pid"><s:property value="%{userProject.project.id}"/></wp:parameter>
                                 <wp:parameter name="tdlid"><s:property value="%{#todolistVar.id}"/></wp:parameter>
                                 <wp:parameter name="tdid"><s:property value="%{#todoVar.id}"/></wp:parameter>
                                 <wp:parameter name="pageCode"><s:property value="pageCode"/></wp:parameter>
                                 </wp:url>" >
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
                           </s:else>
                           </p>

                        </s:iterator>
                     </s:if>
                     <s:else>
                        <wp:i18n key="jpbasecamp_TODO_NO_REMAINING" />
                     </s:else>

                     <s:if test="%{#completedCountVar > 0}" >

                        <wp:i18n key="jpbasecamp_TODO_COMPLETED" />

                        <%--completed Todo --%>
                        <s:iterator value="%{#todolistVar.completedList}" var="todoVar">

                           <p>
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

                  <%-- display form for new todos only --%>

                  <s:if test="%{null == tdid}">
                     <p style="margin-top: 17px;">
                     <wp:i18n key="jpbasecamp_LABEL_NEW_TODO_TITLE" />
                     </p>
                     <s:include value="inc/todo.jsp"/>
                  </s:if>

                  <p>
               </div>
            </div>
         </s:if>

         <s:set var="saveButtonLabelVar"><wp:i18n key="jpbasecamp_SAVE"/></s:set>
         <wpsf:submit 
            cssClass="btn btn-primary englo_button margin-bottom"
            useTabindexAutoIncrement="true" 
            action="updateTodolist" 
            value="%{saveButtonLabelVar}" />
         </p>

      </form>
   </section>
</div>
