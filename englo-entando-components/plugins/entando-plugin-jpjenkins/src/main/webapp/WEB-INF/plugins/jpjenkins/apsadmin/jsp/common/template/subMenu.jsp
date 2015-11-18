<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li>
		
		<a href="<s:url namespace="/do/jpjenkins/Config" action="edit" />">
			<s:text name="jpjenkins.name" />
		</a>
	</li>
</wp:ifauthorized>
