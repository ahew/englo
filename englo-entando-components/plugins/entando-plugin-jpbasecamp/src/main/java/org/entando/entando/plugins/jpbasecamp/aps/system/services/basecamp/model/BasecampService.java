package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model;

import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;

public class BasecampService extends AbstractOAuth2Service {

	
	public long getExpiresIn() {
		return _expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this._expiresIn = expiresIn;
	}
	
	public Authorization getAuthorization() {
		return _authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this._authorization = authorization;
	}

	private long _expiresIn;
	private Authorization _authorization;
}
