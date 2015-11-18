<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>
<%@ taglib prefix="wpsf" uri="/apsadmin-form" %>

<h1 class="panel panel-default title-page">
	<span class="panel-body display-block"><s:text name="title.jpbasecamp.config" /></span>
</h1>

<div id="main">
	<s:form action="save">
	
		<s:if test="hasActionMessages()">
			<div class="alert alert-danger alert-dismissable fade in">
				<button class="close" data-dismiss="alert"><span class="icon fa fa-times"></span></button>
				<h2 class="h4 margin-none"><s:text name="messages.error" /></h2>
				<ul class="margin-base-top">
					<s:iterator value="actionMessages">
						<li><s:property escape="false" /></li>
					</s:iterator>
				</ul>
			</div>
		</s:if>
		<s:if test="hasFieldErrors()">
			<div class="alert alert-danger alert-dismissable fade in">
				<button class="close" data-dismiss="alert"><span class="icon fa fa-times"></span></button>
				<h2 class="h4 margin-none">
					<s:text name="messages.error" />
					&ensp;
					<span
						class=" text-muted icon fa fa-question-circle cursor-pointer"
						title="<s:text name="label.all" />"
						data-toggle="collapse"
						data-target="#content-field-messages"></span>
				</h2>
				<ul class="collapse margin-small-top" id="content-field-messages">
					<s:iterator value="fieldErrors" var="e">
						<s:iterator value="#e.value">
							<li><s:property escape="false" /></li>
						</s:iterator>
					</s:iterator>
				</ul>
			</div>
		</s:if>

		<fieldset class="col-xs-12">
			<legend><s:text name="jpbasecamp.legend.authentication.param" /></legend> 
			<div class="form-group">
				<label for="jpbasecamp.label.appId"><s:text name="jpbasecamp.label.appId" /></label>
				<wpsf:textfield name="config.appId" id="appId" cssClass="form-control" />
			</div>
			
			<div class="form-group">
				<label for="jpbasecamp.label.appSecret"><s:text name="jpbasecamp.label.appSecret" /></label>
				<wpsf:textfield name="config.appSecret" id="appSecret" cssClass="form-control" />
			</div>
			
			<div class="form-group">
				<label for="jpbasecamp.label.username"><s:text name="jpbasecamp.label.username" /></label>
				<wpsf:textfield name="config.username" id="username" cssClass="form-control" />
			</div>
			
			<div class="form-group">
				<label for="jpbasecamp.label.password"><s:text name="jpbasecamp.label.password" /></label>
				<wpsf:textfield name="config.password" id="password" cssClass="form-control" />
			</div>
			
			<div class="form-group">
				<label for="jpbasecamp.label.account"><s:text name="jpbasecamp.label.account" /></label>
				<wpsf:textfield name="config.account" id="account" cssClass="form-control" />
			</div>
			
		</fieldset>
		
		<fieldset class="col-xs-12">
			<legend><s:text name="jpbasecamp.legend.authentication.url" /></legend>
			
			<div class="alert alert-info">
				<s:text name="jpbasecamp.legend.note" />
			</div>
			
			<div class="checkbox">
					<label for="jpbasecamp.label.authorizationNewUrl">
						<wpsf:checkbox name="resetEndopoint" id="authorizationNewUrl" />
						<s:text name="jpbasecamp.label.reset" />
					</label>
			</div>
			
			<div class="form-group">
				<label for="jpbasecamp.label.authorizationNewUrl"><s:text name="jpbasecamp.label.authorizationNewUrl" /></label>
				<wpsf:textfield name="config.authorizationNewUrl" id="authorizationNewUrl" cssClass="form-control" />
			</div>

			<div class="form-group">
				<label for="jpbasecamp.label.authorizationTokenUrl"><s:text name="jpbasecamp.label.authorizationTokenUrl" /></label>
				<wpsf:textfield name="config.authorizationTokenUrl" id="authorizationTokenUrl" cssClass="form-control" />
			</div>
				
			<div class="form-group">
				<label for="jpbasecamp.label.authorizationUrl"><s:text name="jpbasecamp.label.authorizationUrl" /></label>
				<wpsf:textfield name="config.authorizationUrl" id="authorizationUrl" cssClass="form-control" />
			</div>
			
			<%--
			<div class="form-group">
				<label for="smtpPassword"><s:text name="smtpPassword" /></label>
				<wpsf:hidden value="%{getSmtpPassword()}" />
				<wpsf:password  name="smtpPassword" id="smtpPassword" cssClass="form-control" />
			</div>
			 --%>
		</fieldset> 
	
		<div class="form-horizontal">
			<div class="form-group<s:property value="#controlGroupErrorClassVar" />">
				<div class="col-xs-12 col-sm-4 col-md-3 margin-small-vertical">
					<wpsf:submit type="button" cssClass="btn btn-primary btn-block">
						<span class="icon fa fa-floppy-o"></span>&#32;
						<s:text name="label.save" />
					</wpsf:submit>
				</div>
			</div>
		</div>

	</s:form>
</div>
