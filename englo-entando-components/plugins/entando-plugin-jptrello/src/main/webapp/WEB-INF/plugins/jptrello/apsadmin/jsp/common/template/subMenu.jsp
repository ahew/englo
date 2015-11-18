<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li>
		
		<a href="<s:url namespace="/do/jptrello/Config" action="edit" />">
			<s:text name="jptrello.name" />
		</a>
	</li>
</wp:ifauthorized>
