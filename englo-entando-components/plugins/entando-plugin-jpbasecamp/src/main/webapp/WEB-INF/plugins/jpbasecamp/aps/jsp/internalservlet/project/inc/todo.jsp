<%@page import="org.apache.struts2.components.Include"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<p>
	<%-- todo content --%>
	<wpsf:textfield name="content"/>
</p>

<p>
	<%-- todo assegnee --%>
	<select name="aid">
	
		<s:iterator var="person" value="people">
			
			<option value='<s:property value="#person.id"/>' 
					<s:if test="%{#person.id == #todoVar.assignee.id}">selected="selected"</s:if>
				>
			<s:property value="%{#person.name}"/></option>
			
		</s:iterator>
	</select>
	
	
	<%-- TODO due date --%>
	
	
</p>

<s:if test="%{tdlid != null && tdid != null}">
	<s:set var="updateButtonLabelVar"><wp:i18n key="jpbasecamp_UPDATE_TODO"/></s:set>
        <s:set var="deleteButtonLabelVar"><wp:i18n key="jpbasecamp_DELETE_TODO"/></s:set>    
</s:if>
<s:else>
	<s:set var="updateButtonLabelVar"><wp:i18n key="jpbasecamp_ADD_TODO"/></s:set>
</s:else>

<p>
	<wpsf:submit 
	cssClass="btn"
	useTabindexAutoIncrement="true" 
	action="updateTodo" 
	value="%{updateButtonLabelVar}" 
	title="%{updateButtonLabelVar}" />
</p>

<s:if test="%{tdlid != null && tdid != null}">
	<!-- EDIT TODO  -->
	<p>
	<wpsf:submit 
	cssClass="btn"
	useTabindexAutoIncrement="true" 
	action="deleteTodo" 
	value="%{deleteButtonLabelVar}" 
	title="%{deleteButtonLabelVar}" />
</p>

		 
</s:if>