package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasecampConfig {
	
	private static Logger _logger = LoggerFactory.getLogger(BasecampConfig.class);
	
	public BasecampConfig() {
		_authorizationNewUrl = BasecampSystemConstants.DEFAULT_AUTHORIZATION_NEW_URL;
		_authorizationTokenUrl = BasecampSystemConstants.DEFAULT_AUTHORIZATION_TOKEN_URL;
		_authorizationUrl =  BasecampSystemConstants.DEFAULT_AUTHORIZATION_URL;
	}
	
	/**
	 * Check whether the configuration has the setting needed for a manned
	 * utilization present
	 * @return
	 */
	public boolean isValidForMannedUtilization() {
		return (StringUtils.isNotBlank(getAuthorizationTokenUrl())
				&& StringUtils.isNotBlank(getAuthorizationNewUrl())
				&& StringUtils.isNotBlank(getAppSecret())
				&& StringUtils.isNotBlank(getAppId()));
	}
	
	/**
	 * Check whether the configuration has the setting needed for an unmanned
	 * utilization present
	 * @return
	 */
	public boolean isValidForUnmannedUtilization() {
		return (StringUtils.isNotBlank(getUsername())
				&& StringUtils.isNotBlank(getPassword())
				&& StringUtils.isNotBlank(getAccount()));
	}
	
	public BasecampConfig(String xml) {
		String appId = null;
		String appSecret = null;
		String authorizationNewUrl = null;
		String authorizationTokenUrl = null;
		String authorizationUrl = null;
		String username = null;
		String password = null;
		String account = null;
		boolean disclose = false;
		
		JSONObject json = XML.toJSONObject(xml);
		json = json.getJSONObject(CONFIG_ROOT);
		
		try {
			appId = json.getString(CONFIG_APP_ID);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			appSecret = json.getString(CONFIG_APP_SECRET);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			authorizationNewUrl = json.getString(CONFIG_URL_AUTHORIZATION_NEW);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			authorizationTokenUrl = json.getString(CONFIG_URL_AUTHORIZATION_TOKEN);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			authorizationUrl = json.getString(CONFIG_URL_AUTHORIZATION);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			username = json.getString(CONFIG_ACCOUNT_USERNAME);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			password = json.getString(CONFIG_ACCOUNT_PASSWORD);
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		try {
			Object obj = json.get(CONFIG_ACCOUNT_NUMBER);
			
			if (obj instanceof Integer) {
				account = String.valueOf((Integer)obj);
			} else if (obj instanceof Long) {
				account = String.valueOf((Long)obj);
			} else if (obj instanceof String) {
				account = String.valueOf((String)obj);
			} else {
				throw new RuntimeException("Unknown account formt found in the configuration");
			}
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		// hidden feature
		try {
			disclose = json.getBoolean(CONFIG_ACCOUNT_DISCLOSE);
			if (disclose) {
				_logger.info("Debug mode active");
			}
		} catch (Throwable t) {
			_logger.error("Erro loading configuration property: '{}' ", t.getLocalizedMessage());
		}
		
		this.setAppId(appId);
		this.setAppSecret(appSecret);
		this.setAuthorizationNewUrl(authorizationNewUrl);
		this.setAuthorizationTokenUrl(authorizationTokenUrl);
		this.setAuthorizationUrl(authorizationUrl);
		this.setUsername(username);
		this.setPassword(password);
		this.setAccount(account);
		this.setDisclose(disclose);
	}
	
	public String toXML() {
		JSONObject json = new JSONObject();

		if (null != getAppId()) {
			json.put(CONFIG_APP_ID, getAppId());
		} else {
			json.put(CONFIG_APP_ID, JSONObject.NULL);
		}
		if (null != getAppSecret()) {
			json.put(CONFIG_APP_SECRET, getAppSecret());
		} else {
			json.put(CONFIG_APP_SECRET, JSONObject.NULL);
		}
		if (null != getAuthorizationUrl()) {
			json.put(CONFIG_URL_AUTHORIZATION, getAuthorizationUrl());
		} else {
			json.put(CONFIG_URL_AUTHORIZATION, JSONObject.NULL);
		}
		if (null != getAuthorizationNewUrl()) {
			json.put(CONFIG_URL_AUTHORIZATION_NEW, getAuthorizationNewUrl());
		} else {
			json.put(CONFIG_URL_AUTHORIZATION_NEW, JSONObject.NULL);
		}
		if (null != getAuthorizationTokenUrl()) {
			json.put(CONFIG_URL_AUTHORIZATION_TOKEN, getAuthorizationTokenUrl());
		} else {
			json.put(CONFIG_URL_AUTHORIZATION_TOKEN, JSONObject.NULL);
		}
		if (null != getUsername()) {
			json.put(CONFIG_ACCOUNT_USERNAME, getUsername());
		} else {
			json.put(CONFIG_ACCOUNT_USERNAME, JSONObject.NULL);
		}
		if (null != getPassword()) {
			json.put(CONFIG_ACCOUNT_PASSWORD, getPassword());
		} else {
			json.put(CONFIG_ACCOUNT_PASSWORD, JSONObject.NULL);
		}
		if (null != getAccount()) {
			json.put(CONFIG_ACCOUNT_NUMBER, getAccount());
		} else {
			json.put(CONFIG_ACCOUNT_NUMBER, JSONObject.NULL);
		}
		json.put(CONFIG_ACCOUNT_DISCLOSE, isDisclose());
		return XML.toString(json, CONFIG_ROOT);
	}
	
	public String getAppId() {
		return _appId;
	}
	public void setAppId(String appId) {
		this._appId = appId;
	}
	public String getAppSecret() {
		return _appSecret;
	}
	public void setAppSecret(String appSecret) {
		this._appSecret = appSecret;
	}
	public String getAuthorizationNewUrl() {
		return _authorizationNewUrl;
	}
	public void setAuthorizationNewUrl(String authorizationUrl) {
		this._authorizationNewUrl = authorizationUrl;
	}
	public String getAuthorizationTokenUrl() {
		return _authorizationTokenUrl;
	}
	public void setAuthorizationTokenUrl(String authenticationUrl) {
		this._authorizationTokenUrl = authenticationUrl;
	}
	public String getAuthorizationUrl() {
		return _authorizationUrl;
	}
	public void setAuthorizationUrl(String authorizationUrl) {
		this._authorizationUrl = authorizationUrl;
	}
	public String getUsername() {
		return _username;
	}
	public void setUsername(String username) {
		this._username = username;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public String getAccount() {
		return _account;
	}
	public void setAccount(String account) {
		this._account = account;
	}
	public boolean isDisclose() {
		return _disclose;
	}
	public void setDisclose(boolean disclose) {
		this._disclose = disclose;
	}

	// manned login
	private String _appId;
	private String _appSecret;
	private String _authorizationNewUrl;
	private String _authorizationTokenUrl;
	private String _authorizationUrl;
	
	// unmanned login
	private String _username;
	private String _password;
	private String _account;
	
	// misc
	private boolean _disclose;
	
	public final static String CONFIG_ROOT = "config";
	public final static String CONFIG_APP_ID = "app_id";
	public final static String CONFIG_APP_SECRET = "app_secret";
	public final static String CONFIG_URL_AUTHORIZATION = "auth_url";
	public final static String CONFIG_URL_AUTHORIZATION_NEW = CONFIG_URL_AUTHORIZATION.concat("_new");
	public final static String CONFIG_URL_AUTHORIZATION_TOKEN = CONFIG_URL_AUTHORIZATION.concat("_token");
	public final static String CONFIG_ACCOUNT_USERNAME = "username";
	public final static String CONFIG_ACCOUNT_PASSWORD = "password";
	public final static String CONFIG_ACCOUNT_NUMBER = "account";
	public final static String CONFIG_ACCOUNT_DISCLOSE = "disclose";
}
