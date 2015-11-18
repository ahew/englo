INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_todolist_view', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - Todolist Details</property><property key="it">Connettore basecamp - Dettagli Todolist</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/todolistView.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_login', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - Login</property><property key="it">Connettore basecamp - Login</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Login/intro.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_project_list', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - Project list</property><property key="it">Connettore basecamp - Lista progetti</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/list.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_project_new', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - New Project</property><property key="it">Connettore basecamp - Nuovo progetto</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/new.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_project_new', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - New Project</property><property key="it">Connettore basecamp - Nuovo progetto</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/new.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_project_view', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - Project Details</property><property key="it">Connettore basecamp - Dettagli Progetto</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/view.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpbasecamp_project_document', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Basecamp connector - Project Document</property><property key="it">Connettore basecamp - Documenti Progetto</property></properties>',
 null, 'jpbasecamp', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpbasecamp/FrontEnd/Project/document.action</property>
</properties>',1);

INSERT INTO sysconfig (version, item, descr, config) VALUES ('production', 'jpbasecamp_config', 'Basecamp service configuration',
'<?xml version="1.0" encoding="UTF-8"?>
<config>
  <app_secret/>
  <app_id/>
  <auth_url_token>https://launchpad.37signals.com/authorization/token</auth_url_token>
  <auth_url_new>https://launchpad.37signals.com/authorization/new</auth_url_new>
  <auth_url>https://launchpad.37signals.com/authorization.json</auth_url>
  <username/>
  <password/>
  <account/>
  <disclose>true</disclose>
</config>');

INSERT INTO guifragment (code, widgettypecode, plugincode, gui, defaultgui, locked) VALUES ('basecamp-widget-project_trash', 'jpbasecamp_project_view', 'jpbasecamp', NULL, NULL, 0);
INSERT INTO guifragment (code, widgettypecode, plugincode, gui, defaultgui, locked) VALUES ('basecamp-widget-project_list', 'jpbasecamp_project_list', 'jpbasecamp', NULL, '<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign s=JspTaglibs["/struts-tags"]>
<#assign wp=JspTaglibs["/aps-core"]>
<#assign wpsa=JspTaglibs["/apsadmin-core"]>
<#assign wpsf=JspTaglibs["/apsadmin-form"]>
<#assign oa2=JspTaglibs["/jpoauth2-core"]>

<@oa2.authentication service="1"><#-- 1 for Basecamp -->
	<#assign logged=true>
</@oa2.authentication>
<section class="jpbasecamp jpbasecamp-project-list">
	<h2><@wp.i18n key="jpbasecamp_LIST_PROJECTS" /></h2>
	<#if logged?? && logged>
		
		<p>
			<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#jpbasecamp-search-form">
					<@wp.i18n key="SEARCH_FILTERS_BUTTON" /> <span class="icon-zoom-in icon-white"></span>
			</button>
		</p>
		
		<form id="jpbasecamp-search-form" action="<@wp.action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/search.action"/>" class="form-horizontal collapse">

			<#-- search by text (just cloned from the content viewer list search filter modules) -->
				<div class="control-group">
					<label for="jpbasecamp-search-searchText" class="control-label"><wp:i18n key="jpbasecamp_SEARCH_TEXT" /></label>
					<div class="controls">
						<@wpsf.textfield name="searchText" id="jpbasecamp-search-searchText" cssClass="input-xlarge" />
					</div>
				</div>

		<#-- checkbox archived -->
		<div class="control-group">
			<label for="jpbasecamp.label.archived" class="control-label">
				<@wpsf.checkbox name="archived" id="restrict_archived" />
		      	</label>
			<div class="controls">
				<@wp.i18n key="jpbasecamp_LABEL_INCLUDE_ARCHIVED" />
			</div>

		</div>

		<#-- checkbox starred -->
		<div class="control-group">
			<label for="jpbasecamp.label.starred" class="control-label">
				<@wpsf.checkbox name="starred" id="restrict_starred" />
		      	</label>
			<div class="controls">
				<@wp.i18n key="jpbasecamp_LABEL_SEARCH_STARRED" />
			</div>

		</div>

		<#-- checkbox client project -->
		<div class="control-group">
			<label for="jpbasecamp.label.isClientProject" class="control-label">
				<@wpsf.checkbox name="clientProject" id="restrict_isClientProject" />
		      	</label>
			<div class="controls">
				<@wp.i18n key="jpbasecamp_LABEL_SEARCH_CLIENT" />
			</div>

		</div>

		<#-- checkbox trashed -->
<#--
		<div class="control-group">
			<label for="jpbasecamp.label.trashed" class="control-label">
				<@wpsf.checkbox name="trashed" id="restrict_trashed" />
		      	</label>
			<div class="controls">
				<@wp.i18n key="jpbasecamp_LABEL_SEARCH_TRASHED" />
			</div>

		</div>
-->


				<#--
					<wpsf:checkbox name="trashed" id="jpbasecamp-lead-list-searc-field-trashed" />
				-->

				<p class="form-actions">
					<button type="submit" class="btn btn-primary">
						<@wp.i18n key="SEARCH" />
						&#32;
						<span class="icon-search icon-white"></span>
					</button>
				</p>
		</form>
		
		<p>
			<#-- add project button -->
			<@wp.currentPage param="code" var="currentPageCodeVar" />
			<@wp.pageWithWidget var="newPage" widgetTypeCode="jpbasecamp_project_new" />
				<#assign newPageCode=newPage.code>
			<a
				class="btn btn-primary"
				href="<@wp.url page="${newPageCode}">
					<@wp.parameter name="pageCode">${currentPageCodeVar}"</@wp.parameter>
					<@wp.parameter name="searchText"><@s.property value="searchText" /></@wp.parameter>
				</@wp.url>"
				>
					<span class="icon-plus icon-white"></span>&#32;
					<@wp.i18n key="jpbasecamp_PROJECT_NEW" />
			</a>
		</p>
		
		<@s.set value="projects" var="projectListVar" />
		<@s.if test="%{#projectListVar.size() > 0}">
			<div class="row-fluid">


			
				<@wp.pager listName="projectListVar" objectName="groupProjects" max=10 pagerIdFromFrame=true advanced=true offset=5>
				
					<@wp.freemarkerTemplateParameter var="group" valueName="groupProjects" removeOnEndTag=true>
						<#list projectListVar as projectVar>
						
						<#if (projectVar_index >= groupProjects.begin) && (projectVar_index <= groupProjects.end)>
							<article>
								<h3>
										<a href="<@wp.action path="/ExtStr2/do/jpbasecamp/FrontEnd/Project/view.action"><@wp.parameter name="pageCode"><@s.property value="homepage" /></@wp.parameter><@wp.parameter name="pid">${projectVar.id?c}</@wp.parameter></@wp.action>">${projectVar.name}</a>
										<a
											href="${projectVar.basecampUrl()}"
											class="pull-right status-icon-tooltip"
											title="Open on Basecamp: ${projectVar.name}">
												<span class="icon-globe"></span>
												<span class="sr-only noscreen">
													Open on Basecamp: ${projectVar.name}
												</span>
											</a>
									</h3>
									<p>
										<#-- updated -->
										<#assign diff = (((projectVar.updatedAt?long / 86400000)?round - (.now?long / 86400000)?round) * -1) />
										<time
											class="status-icon-tooltip"
											title="<@wp.i18n key="jpbasecamp_UPDATEDAT" />:&#32; ${projectVar.updatedAt?string("yyyy-MM-dd HH:mm:ss")}"
											datetime="${projectVar.updatedAt?string("yyyy-MM-dd HH:mm:ss")}" >
												<span class="icon-time"></span>
												<span class="sr-only noscreen"><@wp.i18n key="jpbasecamp_UPDATEDAT" />:</span>
												${diff} days ago<#-- <@wp.i18n key="jpbasecamp_DAYS_AGO" /> -->
										</time>
										
										<#-- starred -->
										<#if projectVar.starred?? && projectVar.starred >
											&emsp;<span
												class="icon-star status-icon-tooltip"
												title="Starred Project"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen"><@wp.i18n key="jpbasecamp_STARRED" /></span></span>
										</#if>
										
										<#-- archived -->
										<#if projectVar.archived?? && projectVar.archived >
											&emsp;<span
												class="icon-inbox status-icon-tooltip"
												title="Archived Prject"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen"><@wp.i18n key="jpbasecamp_ARCHIVED" /></span></span>
										</#if>
										
										<#-- trashed -->
										<#if projectVar.trashed?? && projectVar.trashed >
											&emsp;<span
												class="icon-trash status-icon-tooltip"
												title="Trashed Prject"
												data-toggle="tooltip"
												>
													<span class="sr-only noscreen"><@wp.i18n key="jpbasecamp_TRASHED" /></span></span>
										</#if>

										<#-- draft -->
										<#if projectVar.draft?? && projectVar.draft >
											&emsp;<span
												class="icon-trash status-icon-tooltip"
												title="Trashed Prject"
												data-toggle="tooltip"
												>
													<@wp.i18n key="jpbasecamp_DRAFT" />
										</#if>
								</p>
								
								<#-- description -->
								<p class="muted">
									<#assign diff = (((projectVar.createdAt?long / 86400000)?round - (.now?long / 86400000)?round) * -1) />
									<time
											title="<@wp.i18n key="jpbasecamp_CREATEDAT" />:&#32; ${projectVar.createdAt?string("yyyy-MM-dd HH:mm:ss")}"
											datetime="${projectVar.createdAt?string("yyyy-MM-dd HH:mm:ss")}"
											class="status-icon-tooltip muted"
											>
												<span class="sr-only noscreen">
													<@wp.i18n key="jpbasecamp_CREATEDAT" />:
												</span>
												${diff} days ago<#-- <@wp.i18n key="jpbasecamp_DAYS_AGO" /> -->
									</time>
									&mdash;
									${projectVar.description}
								</p>
								
							</article>
							</hr>
						</#if>
						</#list>
						<@wp.fragment code="default_pagerBlock" escapeXml=false />
					</@wp.freemarkerTemplateParameter>
				</@wp.pager>
			</div>
		</@s.if>
		<@s.else>
			<@wp.i18n key="jpbasecamp_NO_PROJECTS" />
		</@s.else>
		
	<#else>
		<@wp.i18n key="jpbasecamp_MUST_LOGIN" />
	</#if>
</section>', 1);

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TITLE_LOGIN', 'en', 'Basecamp Sign In');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TITLE_LOGIN', 'it', 'Accedi a Basecamp');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGIN_NOW', 'en', 'Sign In');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGIN_NOW', 'it', 'Connettiti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_MUST_LOGIN', 'en', 'Sign In to Basecamp');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_MUST_LOGIN', 'it', 'Accedi a Basecamp');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGOUT', 'en', 'Sign Out');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGOUT', 'it', 'Esci');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGIN_OK', 'en', 'You are signed in with your Basecamp account');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOGIN_OK', 'it', 'Hai effettuato l''accesso con il tuo account Basecamp');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_CREATEDAT', 'en', 'Created at');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_CREATEDAT', 'it', 'Creato il');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPDATEDAT', 'en', 'Updated at');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPDATEDAT', 'it', 'Aggiornato il');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_NEW', 'en', 'New');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_NEW', 'it', 'Crea');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_NEW_PROJECT', 'en', 'New project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_NEW_PROJECT', 'it', 'Nuovo progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_NAME', 'en', 'Name');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_NAME', 'it', 'Nome');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_DESCRIPTION', 'en', 'Description');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_DESCRIPTION', 'it', 'Description');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_EDIT_PROJECT', 'en', 'Edit project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_EDIT_PROJECT', 'it', 'Modifica progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LIST_PROJECTS', 'en', 'Projects list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LIST_PROJECTS', 'it', 'Lista progetti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_SEARCH_TEXT', 'en', 'Text');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_SEARCH_TEXT', 'it', 'Testo');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_SAVE', 'en', 'Save');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_SAVE', 'it', 'Salva');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_GOTO_LIST', 'en', 'Nevermind');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_GOTO_LIST', 'it', 'Ritorna alla lista');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_VIEW_PROJECT', 'en', 'Project Details');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_VIEW_PROJECT', 'it', 'Dettaglio progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_TRASH', 'en', 'Delete project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_TRASH', 'it', 'Cancella il progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_LIST', 'en', 'Projects list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_LIST', 'it', 'Lista progetti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE_CONFIRM', 'en', 'Do you really want to delete the project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE_CONFIRM', 'it', 'Cancellare il progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE', 'en', 'Delete');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE', 'it', 'Cancella');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_CREATED', 'en', 'Creation date');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_CREATED', 'it', 'Data creazione');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_UPDATED', 'en', 'Updated at');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_UPDATED', 'it', 'Ultimo aggiornamento');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_DRAFT', 'en', 'Draft');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_DRAFT', 'it', 'Bozza');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_STARRED', 'en', 'Starred');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_STARRED', 'it', 'Preferito');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_ISCLIENTPROJECT', 'en', 'Client project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_ISCLIENTPROJECT', 'it', 'Progetto di clienti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_VIEW', 'en', 'View Details');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_VIEW', 'it', 'Mostra dettagli');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_TODOLISTS', 'en', 'Todo list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_TODOLISTS', 'it', 'Todo list');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_NO_TODOS', 'en', 'No todo defined!');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_NO_TODOS', 'it', 'Nessun todo definito!');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NO_COMPLETED', 'en', 'No todo completed!');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NO_COMPLETED', 'it', 'Nessun todo completato!');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NO_REMAINING', 'en', 'No todo remaining');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NO_REMAINING', 'it', 'Nessun todo rimanente!');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_REMAINING', 'en', 'Remaining todo');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_REMAINING', 'it', 'Todo rimanenti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_COMPLETED', 'en', 'Completed Todo');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_COMPLETED', 'it', 'Todo completati');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NEW', 'en', 'New todo');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODO_NEW', 'it', 'New todo');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODOLIST_NEW', 'en', 'New todo list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TODOLIST_NEW', 'it', 'Nuova lista Todo');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_INCLUDE_ARCHIVED', 'en', 'Include archived projects');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_INCLUDE_ARCHIVED', 'it', 'Include progetti archiviati');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_STARRED', 'en', 'Consider only starred project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_STARRED', 'it', 'Considera solo i progetti preferiti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_CLIENT', 'en', 'Consider only client projects');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_CLIENT', 'it', 'Considera solo i progetti clienti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_TRASHED', 'en', 'Consider only trashed projects');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LABEL_SEARCH_TRASHED', 'it', 'Considera solo progetti cancellati');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_LIST', 'en', 'Documents list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_LIST', 'it', 'Lista documenti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_DETAIL_DOCUMENT', 'en', 'Return to project details');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_DETAIL_DOCUMENT', 'it', 'Ritorna dettaglio progetto');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPLOAD_ENTANDO', 'en', 'Upload in Entando');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPLOAD_ENTANDO', 'it', 'Carica in Entando');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPLOAD_PROTECTED', 'en', 'Upload as protected resource');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_UPLOAD_PROTECTED', 'it', 'Carica come risorsa protetta');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TRASH_ATTACHMENT_LABEL', 'en', 'Trash document');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TRASH_ATTACHMENT_LABEL', 'it', 'Cancella documento');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TRASH_ATTACHMENT_TITLE', 'en', 'Trash document');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_TRASH_ATTACHMENT_TITLE', 'it', 'Cancella documento');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOAD_DOC', 'en', 'Document Upload');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_LOAD_DOC', 'it', 'Upload documenti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_DELETE', 'en', 'Document delete');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_DELETE', 'it', 'Cancellazione documento');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DEL_DOC', 'en', 'Confirm document deletion');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DEL_DOC', 'it', 'Conferma cancellazione documento');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_NAME', 'en', 'Project:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_PROJECT_NAME', 'it', 'Project:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_NAME', 'en', 'File name:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DOCUMENT_NAME', 'it', 'Nome del file:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE_ATTACHMENT', 'en', 'Delete');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_DELETE_ATTACHMENT', 'it', 'Cancella');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_DOCUMENT_LIST', 'en', 'Return to list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_RETURN_DOCUMENT_LIST', 'it', 'Ritorna alla lista');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_NO_DOCUMENTS', 'en', 'This project contains no documents');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_NO_DOCUMENTS', 'it', 'Questo progetto non contiene documenti');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_ACTION_ERRORS', 'en', 'There are errors:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_ACTION_ERRORS', 'it', 'Errori rilevati:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_FIELD_ERRORS', 'en', 'Field errors:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_FIELD_ERRORS', 'it', 'Errori nei campi:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_ACTION_MESSAGES', 'en', 'Messages:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpbasecamp_ACTION_MESSAGES', 'it', 'Messaggi:');


