package org.entando.entando.plugins.jpbasecamp.aps.system;

public interface BasecampSystemConstants {
	
	
	public final static String BASECAMP_OAUTH2_MANAGER = "jpbasecampManager";
	public final static String BASECAMP_PROJECT_MANAGER = "jpbasecampProjectManager";
	public final static String BASECAMP_DOCUMENT_MANAGER = "jpbasecampDocumentManager";
	public final static String BASECAMP_ATTACHMENT_MANAGER = "jpbasecampAttachmentManager";
	public final static String BASECAMP_PEOPLE_MANAGER = "jpbasecampPeopleManager";
	public final static String BASECAMP_TODOLIST_MANAGER = "jpbasecampTodolistManager";
	
	// Endpoint for the code request
	public static final String DEFAULT_AUTHORIZATION_NEW_URL = "https://launchpad.37signals.com/authorization/new";
	// Endpoint for the token backchannel request
	public static final String DEFAULT_AUTHORIZATION_TOKEN_URL = "https://launchpad.37signals.com/authorization/token";
	// Endpoint for the authorization check
	public static final String DEFAULT_AUTHORIZATION_URL = "https://launchpad.37signals.com/authorization.json";
	
	public static final String USER_AGENT = "Matteo's Ingenious Integration (info4sales-temp@yahoo.it)";
	
	public static final String SERVICE_BASECAMP = "bcx";
	public static final String SERVICE_ID = "1";
	
	public static final String BASECAMP_CONFIG_ITEM = "jpbasecamp_config";
	
	public static final String SESSIONPARAM_PROJECT_OBJECT = "jpbasecamp_session_project";
	
}
