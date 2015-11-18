<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li>
		
		<a href="<s:url namespace="/do/jpbasecamp/Config" action="edit" />">
			<s:text name="jpbasecamp.admin.menu" />
		</a>
	</li>
</wp:ifauthorized>
