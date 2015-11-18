<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="oa2" uri="/jpoauth2-core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form"%>

<oa2:authentication service="1"><%-- 1 for Basecamp --%>
	<s:set var="logged" value="true"/>
</oa2:authentication>
        
<section class="jpbasecamp jpbasecamp-project-list">
	<h2><wp:i18n key="jpbasecamp_LIST_PROJECTS" /></h2>

	<s:if test="%{#logged}">
		<p>
			<button
				type="button"
				class="btn btn-info"
				data-toggle="collapse"
				data-target="#jpbasecamp-search-form"
				>
					<wp:i18n key="SEARCH_FILTERS_BUTTON" /> <span class="icon-zoom-in icon-white"></span>
			</button>
		</p>

		<form
			id="jpbasecamp-search-form"
			action="<wp:action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/search.action"/>"
			class="form-horizontal collapse"
			>

			<%-- search by text (just cloned from the content viewer list search filter modules) --%>
				<div class="control-group">
					<label for="jpbasecamp-search-searchText" class="control-label"><wp:i18n key="jpbasecamp_SEARCH_TEXT" /></label>
					<div class="controls">
						<wpsf:textfield name="searchText" id="jpbasecamp-search-searchText" cssClass="input-xlarge" />
					</div>
				</div>

			<%-- Fort title and description
				<br/>

				<wp:i18n key="jpbasecamp_PREFERRED" />
				<wpsf:checkbox name="starred" id="jpbasecamp-lead-list-searc-field-starred" /> <br/>

				<wp:i18n key="jpbasecamp_CREATOR" />
				<wpsf:textfield name="creator" id="jpbasecamp-lead-list-searc-field-creator" /> <br/>
			--%>

				<%--
					<wpsf:checkbox name="trashed" id="jpbasecamp-lead-list-searc-field-trashed" />
				--%>

				<p class="form-actions">
					<button type="submit" class="btn btn-primary">
						<wp:i18n key="SEARCH" />
						&#32;
						<span class="icon-search icon-white"></span>
					</button>
				</p>
		</form>
		
		<p>
			<%-- add project button --%>
			<wp:currentPage param="code" var="currentPageCodeVar" />
			<wp:pageWithWidget var="newPage" widgetTypeCode="jpbasecamp_project_new" />
			<c:set var="newPageCode" value="${newPage.code}" />
			<a
				class="btn btn-primary"
				href="<wp:url page="${newPageCode}">
					<wp:parameter name="pageCode"><c:out value="${currentPageCodeVar}" /></wp:parameter>
					<wp:parameter name="searchText"><s:property value="searchText" /></wp:parameter>
				</wp:url>"
				>
					<span class="icon-plus icon-white"></span>&#32;
					<wp:i18n key="jpbasecamp_PROJECT_NEW" />
			</a>
		</p>
		
		<s:set value="projects" var="projectList" />
		<s:if test="%{#projectList.size() > 0}">
			<div class="row-fluid">
				<wp:pager listName="projectList" objectName="groupProjects" max="10" pagerIdFromFrame="true" advanced="true" offset="5">
					
					<c:set var="group" value="${groupProjects}" scope="request" />
					
					<s:iterator value="#projectList" var="projectVar" begin="%{#attr.groupProjects.begin}" end="%{#attr.groupProjects.end}">
					
							<s:set var="project" value="#projectVar" scope="page" />
							<article>
								<h3>
									<a href="#"><s:property value="#projectVar.name"/></a>
									<a
										href="<s:property value="#projectVar.url"/>"
										class="pull-right status-icon-tooltip"
										title="Open on Basecamp: <s:property value="#projectVar.name"/>"
										>
											<span class="icon-globe"></span>
											<span class="sr-only noscreen">
												Open on Basecamp: <s:property value="#projectVar.name"/>
											</span>
										</a>
								</h3>
                                                            
								<p>
									<%-- updated --%>
										<time
											class="status-icon-tooltip"
											title="<wp:i18n key="jpbasecamp_UPDATEDAT" />:&#32;<s:date name="#projectVar.updatedAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
											datetime="<s:date name="#projectVar.updatedAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
											>
												<span class="icon-time"></span>
												<span class="sr-only noscreen"><wp:i18n key="jpbasecamp_UPDATEDAT" />:</span>
												<s:date name="#projectVar.updatedAt" nice="true" />
										</time>

									<%-- starred --%>
										<s:if test="%{#projectVar.starred}">
											&emsp;<span
												class="icon-star status-icon-tooltip"
												title="Starred Project"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen"><wp:i18n key="jpbasecamp_STARRED" /></span></span>
										</s:if>

									<%-- archived --%>
										<s:if test="%{#projectVar.archived}">
											&emsp;<span
												class="icon-inbox status-icon-tooltip"
												title="Archived Prject"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen"><wp:i18n key="jpbasecamp_ARCHIVED" /></span></span>
										</s:if>

									<%-- trashed --%>
										<s:if test="%{#projectVar.trashed}">
											&emsp;<span
												class="icon-trash status-icon-tooltip"
												title="Trashed Prject"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen">Trashed Project</span></span>
										</s:if>

									<%-- draft --%>
										<s:if test="%{#projectVar.trashed}">
											&emsp;<span
												class="icon-trash status-icon-tooltip"
												title="Trashed Prject"
												data-toggle="tooltip"
												>
													<wp:i18n key="jpbasecamp_DRAFT" />
										</s:if>
								</p>

								<%-- description --%>
									<p class="muted">
										<time
												title="<wp:i18n key="jpbasecamp_CREATEDAT" />:&#32;<s:date name="#projectVar.createdAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
												datetime="<s:date name="#projectVar.createdAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
												class="status-icon-tooltip muted"
												>
													<span class="sr-only noscreen">
														<wp:i18n key="jpbasecamp_CREATEDAT" />:
													</span>
													<s:date name="#projectVar.createdAt" nice="true" />
										</time>
										&mdash;
										<s:property value="#projectVar.description"/>
									</p>

									<p class="text-right">
										<%-- add project button --%>
										<wp:currentPage param="code" var="currentPageCodeVar" />
										<wp:pageWithWidget var="viewPage" widgetTypeCode="jpbasecamp_project_view" />
										<c:set var="viewPageVar" value="${viewPage.code}" />
										<a
											class="btn btn-small btn-default"
											href="<wp:url page="${viewPageVar}">
												<wp:parameter name="pid"><c:out value="${currentPageCodeVar}" /></wp:parameter>
											</wp:url>"
											>
												<span class="icon-plus icon-white"></span>&#32;
												<wp:i18n key="jpbasecamp_PROJECT_NEW" />
										</a>
									</p>

								<%-- description --%>
                                                                <p class="muted">
                                                                        <time
                                                                                        title="<wp:i18n key="jpbasecamp_CREATEDAT" />:&#32;<s:date name="#projectVar.createdAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
                                                                                        datetime="<s:date name="#projectVar.createdAt" format="yyyy-MM-dd HH:mm:ss" nice="false" />"
                                                                                        class="status-icon-tooltip muted"
                                                                                        >
                                                                                                <span class="sr-only noscreen">
                                                                                                        <wp:i18n key="jpbasecamp_CREATEDAT" />:
                                                                                                </span>
                                                                                                <s:date name="#projectVar.createdAt" nice="true" />
                                                                        </time>
                                                                        &mdash;
                                                                        <s:property value="#projectVar.description"/>
                                                                </p>
						</article>
						<hr />
					</s:iterator>

					<c:import url="/WEB-INF/plugins/jacms/aps/jsp/widgets/inc/pagerBlock.jsp" />
				</wp:pager>
			</div>
		</s:if>
		<s:else>
			<wp:i18n key="jpbasecamp_NO_PROJECTS" />
		</s:else>
	</s:if>
	<s:else>
		<wp:i18n key="jpbasecamp_MUST_LOGIN" />
	</s:else>

</section>

<c:set var="js_code">
	if (window.jQuery) {
		jQuery(function() {
			$('.status-icon-tooltip').tooltip();
		});
	}
</c:set>
<wp:headInfo type="JS_RAW" info="${js_code}" />

<%-- OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY OVERLAY  --%>