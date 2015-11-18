<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wp" uri="/aps-core"%>
<%@ taglib prefix="jptrello" uri="/jptrello-core"%>

<jptrello:params var="params" />
<article>
<c:choose>
	<c:when test="${not empty params}">
	<h1><wp:i18n key="jptrello_PARAMS_ID" />: <c:out value="${params.id}" /></h1>
	<ul>
		<li>
			<wp:i18n key="jptrello_PARAMS_ORGANIZATION" />: <c:out value="${params.organization}" /><br />
			<wp:i18n key="jptrello_PARAMS_API_KEY" />: <c:out value="${params.api_key}" /><br />
			<wp:i18n key="jptrello_PARAMS_API_SECRET" />: <c:out value="${params.api_secret}" /><br />
			<wp:i18n key="jptrello_PARAMS_TOKEN" />: <c:out value="${params.token}" /><br />
		</li>
	</ul>
	</c:when>
	<c:otherwise>
	<div class="alert alert-error">
		<p><wp:i18n key="jptrello_PARAMS_NOT_FOUND" /></p>
	</div>
	</c:otherwise>
</c:choose>
</article>