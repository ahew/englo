<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li><a href="<s:url namespace="/do/jptrello/Params" action="list" />" ><s:text name="jptrello.title.paramsManagement" /></a></li>
</wp:ifauthorized>
