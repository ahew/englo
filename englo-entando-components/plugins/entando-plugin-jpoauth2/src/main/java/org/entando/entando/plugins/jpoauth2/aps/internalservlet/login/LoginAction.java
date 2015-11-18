/*
*
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando software.
* Entando is a free software;
* You can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package org.entando.entando.plugins.jpoauth2.aps.internalservlet.login;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.AbstractLoginAction;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session.OAuth2Session;
import org.entando.entando.plugins.jpoauth2.aps.system.OAuth2SystemConstants;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.IOAuth2Manager;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;
import org.entando.entando.plugins.jpoauth2.aps.system.utils.OAuth2Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.user.UserDetails;
import com.agiletec.aps.util.ApsWebApplicationUtils;

public class LoginAction extends AbstractLoginAction {
	
	private static final Logger _logger =  LoggerFactory.getLogger(LoginAction.class);
	
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * Get the redirection URL for the given portal page
	 * @return
	 */
	protected String getPageUrl(String pageCode) {
		String url = null;
		
		if (StringUtils.isNotBlank(pageCode)) {
			IPage page = getPageManager().getPage(pageCode);
			url = getUrlManager().createUrl(page, getCurrentLang(), null);
		} else {
			_logger.debug("Couldn't generate the page URL because of missing or invalid code");
		}
		return url;
	}
	
	/**
	 * Generate the callback URL needed by OAuth2
	 * @return the callbackURL to the current action
	 * @throws Throwable
	 */
	private final String getCallbackUrl() throws Throwable {
		StringBuilder redirUrl = new StringBuilder();
		String baseUrl = this.getConfigManager().getParam(SystemConstants.PAR_APPL_BASE_URL);

		if (null != baseUrl) {
			String namespace = ServletActionContext.getActionMapping().getNamespace();
			redirUrl.append(baseUrl);
			redirUrl.append(namespace);
			redirUrl.append("/");
			redirUrl.append(LOGIN_ACTION_NAME);
		} else {
			_logger.error("Cannot generate the redirection URL");
		}
		String encUrl = OAuth2Utils.normalizeUrl(redirUrl);
		return encUrl;
	}
	
	
	/**
	 * Initiate the login process with OAuth2
	 * This gets also invoked when returning from the login page with all the 
	 * arguments needed to complete the authentication process
	 * @return
	 */
	public String access() {
		UserDetails user = getCurrentUser();
		HttpServletRequest request = this.getRequest();
		
		try {
			String callback = getCallbackUrl();
			Map pmap = request.getParameterMap();
			
			// first we set default path for error JSP
			setBasedir("jpoauth2");
			if (null == pmap 
					|| pmap.isEmpty() 
					|| !pmap.containsKey(REQUEST_PARAM_CODE)) {
				
				// retrieve the manager to use for the authentication flow
				String id = request.getParameter(REQUEST_PARAM_SERVICE_ID);
				_logger.info("Retrieving manager for id '{}'", id);
				
				String value = getIndexerManager().getManagerById(id);
				String managerName = "jp".concat(value).concat("Manager");
				_logger.info("Looking for manager: '{}'", managerName);
				
				IOAuth2Manager oauth2Manager = (IOAuth2Manager) ApsWebApplicationUtils.getBean(managerName, request);
				// set path for error JSP
				setBasedir(value);
				_logger.info("Setting: base '{}' for error messages", value);
				String state = encodeState(getRedirectPage(), id);
				String redirUrl = oauth2Manager.getAuthorizationNewURL(callback, state);

				_logger.debug("Sending user to authentication page '{}'", redirUrl);
				ServletActionContext.getResponse().sendRedirect(redirUrl);
				return NONE;
				
			} else {
				
				_logger.debug("Retrieving login authentication '{}'", "tokenUrl");
				String code = request.getParameter(REQUEST_PARAM_CODE);
				String state = request.getParameter(REQUEST_PARAM_STATE);
				String id = decodeStateService(state);
				String portalPage = decodeStatePortalPage(state);
				String portalRedirectUrl = getPageUrl(portalPage);
				_logger.debug("Will redirect to '{}' after login", portalRedirectUrl);
				
				setRedirectPage(portalRedirectUrl);
				
				String value = getIndexerManager().getManagerById(id);
				String managerName = "jp".concat(value).concat("Manager");
				_logger.info("Looking for manager: '{}'", managerName);	
				IOAuth2Manager oauth2Manager = (IOAuth2Manager) ApsWebApplicationUtils.getBean(managerName, request);
				
				
				if (StringUtils.isNotBlank(code)) {
					DefaultHttpClient client = new DefaultHttpClient();
					HttpPost post = oauth2Manager.getAuthenticationUrlPost(callback, code);
					
					try {
						HttpResponse response = client.execute(post);
						int status = response.getStatusLine().getStatusCode();
						AbstractOAuth2Service serviceData = null;

						if ( status == HttpStatus.SC_OK) {
							_logger.debug("ok, parsing response to get access token");
							try {
								serviceData = oauth2Manager.processAnswer(response);
								OAuth2Session sessionServices = (OAuth2Session) request.getSession().getAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT);
								if (null == sessionServices) {
									sessionServices = new OAuth2Session();
									_logger.debug("Creating OAuth2 session object for service '{}'", id);
								} else {
									_logger.debug("OAuth2 session object found, appending new service '{}'", id);
								}
								sessionServices.addServiceData(id, serviceData);
								request.getSession().setAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT, sessionServices);
							} catch (Throwable t) {
								_logger.info("Error detected while negotiating access token", t);
							}
							oauth2Manager.postLogin(serviceData);
						} else {
							_logger.info("Invalid status on response '{}' ", status);
							return "error";
						}
					} finally {
						post.releaseConnection();
					}

				} else {
					_logger.error("'{}' parameter not found in the request, cannot proceed", REQUEST_PARAM_CODE);
					return "error";
				}
			}
		} catch (Throwable t) {
			_logger.error("login with OAuth2 failed!", t);
			return "error";
		}
		 
		return "redirect";
	}

	public String getRedirectPage() {
		return _redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this._redirectPage = redirectPage;
	}
	
	public String getBasedir() {
		return _basedir;
	}

	public void setBasedir(String basedir) {
		this._basedir = basedir;
	}

	public String _redirectPage;
	public String _basedir;
	
	private final static String LOGIN_ACTION_NAME = "access"; // FIXME
	private final static String REQUEST_PARAM_CODE = "code";
	public final static String REQUEST_PARAM_SERVICE_ID = "id"; 
	
	
	public static final String REQUEST_PARAM_STATE = "state";
}
