<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wp" uri="/aps-core"%>
<%@ taglib prefix="jpjenkins" uri="/jpjenkins-core"%>

<jpjenkins:params var="params" />
<article>
<c:choose>
	<c:when test="${not empty params}">
	<h1><wp:i18n key="jpjenkins_PARAMS_ID" />: <c:out value="${params.id}" /></h1>
	<ul>
		<li>
			<wp:i18n key="jpjenkins_PARAMS_USERNAME" />: <c:out value="${params.username}" /><br />
			<wp:i18n key="jpjenkins_PARAMS_PASSWORD" />: <c:out value="${params.password}" /><br />
			<wp:i18n key="jpjenkins_PARAMS_JENKINS_URL" />: <c:out value="${params.jenkins_url}" /><br />
			<wp:i18n key="jpjenkins_PARAMS_TOKEN" />: <c:out value="${params.token}" /><br />
		</li>
	</ul>
	</c:when>
	<c:otherwise>
	<div class="alert alert-error">
		<p><wp:i18n key="jpjenkins_PARAMS_NOT_FOUND" /></p>
	</div>
	</c:otherwise>
</c:choose>
</article>