<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li><a href="<s:url namespace="/do/jpjenkins/Params" action="list" />" ><s:text name="jpjenkins.title.paramsManagement" /></a></li>
</wp:ifauthorized>
