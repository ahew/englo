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
package org.entando.entando.plugins.jpoauth2.aps.system.services.service.model;

public abstract class AbstractOAuth2Service {

	/**
	 * Return the ID of data in the session
	 */
	public String getSessionId;
	
	public String getAccessToken() {
		return _accessToken;
	}
	public void setAccessToken(String accessToken) {
		this._accessToken = accessToken;
	}
	public String getRefreshToken() {
		return _refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this._refreshToken = refreshToken;
	}
	
	private String _accessToken;
	private String _refreshToken;
	
}
