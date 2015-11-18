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
package org.entando.entando.plugins.jpoauth2.aps.system.services.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;

public interface IOAuth2Manager {

	/**
	 * Return the authorization URL used to request access
	 * @param callback TODO
	 * @param state TODO
	 * @return
	 */
	public String getAuthorizationNewURL(String callback, String state) throws Throwable;
	
	/**
	 * Return the authentication URL used to exchange access token
	 * @param callback TODO
	 * @param code TODO
	 * @return
	 */
	public HttpPost getAuthenticationUrlPost(String callback, String code) throws Throwable;
	
	
	/**
	 * Invoked optionally after the login process to perform post-login operations
	 * @param serviceData
	 */
	public void postLogin(AbstractOAuth2Service serviceData);
	
	
	/**
	 * Get a fresh access token
	 * @param currentTokens
	 * @return
	 */
	public AbstractOAuth2Service refreshAccess(AbstractOAuth2Service currentToken);
	
	/**
	 * Invalidate access token
	 */
	public void retire();

	
	/**
	 * Process response end extract access token and other relevant data
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public AbstractOAuth2Service processAnswer(HttpResponse response) throws Throwable;
}
