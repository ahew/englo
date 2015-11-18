<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>

<wp:ifauthorized permission="superuser">
	<li>
		
		<a href="<s:url namespace="/do/jpgithub/Config" action="edit" />">
			<s:text name="jpgithub.admin.menu" />
		</a>
	</li>
</wp:ifauthorized>
