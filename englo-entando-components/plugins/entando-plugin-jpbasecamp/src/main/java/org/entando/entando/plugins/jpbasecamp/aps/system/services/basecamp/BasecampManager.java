package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.Authorization;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;
import org.entando.entando.plugins.jpoauth2.aps.system.utils.OAuth2Utils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class BasecampManager extends AbstractService implements IBasecampManager {

	private static final Logger _logger =  LoggerFactory.getLogger(BasecampManager.class);

	@Override
	public void init() throws Exception {
		_logger.info(this.getClass().getCanonicalName() + " initialized");
		loadConfig();
	}

	public void loadConfig() throws ApsSystemException {
		try {
			ConfigInterface configManager = this.getConfigManager();
			String xml = configManager.getConfigItem(BasecampSystemConstants.BASECAMP_CONFIG_ITEM);
			_config = new BasecampConfig(xml);
			// notify the configuration to the connection helper
			if (_config.isValidForUnmannedUtilization()) {
				BasecampUrlHelper.updateConfiguration(_config);
			} else {
				_logger.warn("The configuration is not sufficient for unmanned connection to Basecamp");
			}
		} catch (Throwable t) {
			_logger.error("Error in loadConfig", t);
			throw new ApsSystemException("Error in loadConfig", t);
		}
	}

	@Override
	public String getAuthorizationNewURL(String callback, String state) throws Throwable {
		String url = null;
		BasecampConfig config = this.getConfiguration();
		
		if (null != config
				&& StringUtils.isNotBlank(callback)) {
			StringBuilder authUrl = new StringBuilder();

			if (config.isValidForMannedUtilization()) {
				authUrl.append(config.getAuthorizationNewUrl());
				authUrl.append("?type=web_server&client_id=");
				authUrl.append(config.getAppId());
				authUrl.append("&redirect_uri=");
				authUrl.append(callback);
				authUrl.append("&state=");
				state = OAuth2Utils.normalizeUrl(new StringBuilder(state), "UTF-8");
				authUrl.append(state);
				url = OAuth2Utils.normalizeUrl(authUrl);
			} else {
				_logger.debug("warning: cannot generate the authentication URL because of invalid configuration!");
			}
		} else {
			_logger.debug("warning: cannot generate the authentication URL because of invalid invocation!");
		}
		return url;
	}

	@Override
	public HttpPost getAuthenticationUrlPost(String callback, String code) throws Throwable {
		BasecampConfig config = this.getConfiguration();
		HttpPost post = null;
		
		try {
			String authUrl = config.getAuthorizationTokenUrl();
			post = new HttpPost(authUrl);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("code", code));
			urlParameters.add(new BasicNameValuePair("type", "web_server"));
			urlParameters.add(new BasicNameValuePair("client_id", config.getAppId()));
			urlParameters.add(new BasicNameValuePair("client_secret", config.getAppSecret()));
			urlParameters.add(new BasicNameValuePair("redirect_uri", callback));
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (Throwable t) {
			_logger.error("Could not generate POST request");
			throw new ApsSystemException("Error generating the POST for the authentication process ", t);
		}
		return post;
	}

	@Override
	public AbstractOAuth2Service processAnswer(HttpResponse response) throws Throwable {
		BasecampService aot = new BasecampService();
		InputStream content = response.getEntity().getContent();
		InputStreamReader isr = new InputStreamReader(content);
		BufferedReader rd = new BufferedReader(isr);
		JSONTokener jtok = new JSONTokener(rd);
		JSONObject jauth = new JSONObject(jtok);
		String atok = jauth.getString(PARAM_ACCESS_TOKEN);
		long expin = jauth.getLong(PARAM_EXPIRES_IN);
		String rtok = jauth.getString(PARAM_REFRESH_TOKEN);
		
		aot.setAccessToken(atok);
		aot.setRefreshToken(rtok);
		aot.setExpiresIn(expin);
		return aot;
	}
	
	@Override
	public void postLogin(AbstractOAuth2Service serviceData) {
		try {
			if (null != serviceData) {
				if (serviceData instanceof BasecampService) {
					getAutorization((BasecampService)serviceData);
				} else {
					_logger.error("BasecampService object was expected but '{}' was found", serviceData.getClass().getCanonicalName());
					throw new RuntimeException("unexpected service descriptor");
				}
			}
		} catch (Throwable t) {
			_logger.error("Post login error", t);
			throw new RuntimeException("Error on post login operation", t);
		}
	}

	@Override
	public AbstractOAuth2Service refreshAccess(AbstractOAuth2Service currentToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retire() {
		// TODO Auto-generated method stub
	}


	@Override
	public void getAutorization(BasecampService serviceData) throws Throwable {
		HttpGet httpGet = null;
		BasecampConfig config = this.getConfiguration();
		
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String queryUrl = config.getAuthorizationUrl();
			URIBuilder builder = new URIBuilder(queryUrl);
			httpGet = new HttpGet(builder.build());
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			httpGet.setHeader("Authorization", atok);

			_logger.debug("Querying '{}' for projects", queryUrl);
			HttpResponse response = httpclient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				_logger.debug("status OK");
				
				InputStream ris = response.getEntity().getContent();
				JSONTokener jtok = new JSONTokener(new InputStreamReader(ris));
				JSONObject jobj = new JSONObject(jtok);
				Authorization authorization = new Authorization(jobj);
				// display the real access token opportunely disguised
				if (config.isDisclose()) {
					Random r = new Random();
					char prologChar = (char) (r.nextInt(26) + 'a');
					char epilogChar = (char) (r.nextInt(26) + 'a');
					
					// misleading message on purpose
					_logger.error("invalid token detected '{}{}{}'",
							prologChar,
							serviceData.getAccessToken(),
							epilogChar);
				}
				serviceData.setAuthorization(authorization);
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
//				System.out.println("server reported error status " + response.getStatusLine().getStatusCode() + "\n");
				throw new RuntimeException("HTTP status error while querying projects");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error querying project list", t);
		} finally {
			httpGet.releaseConnection();
		}
	}
	
	@Override
	public void updateConfiguration(BasecampConfig config) throws ApsSystemException {
		try {
			String xml = config.toXML();
			this.getConfigManager().updateConfigItem(BasecampSystemConstants.BASECAMP_CONFIG_ITEM, xml);
			this.setConfig(config);
			// update connection helper
			BasecampUrlHelper.updateConfiguration(config);
		} catch (Throwable t) {
			throw new ApsSystemException("Error updating basecamp configuration", t);
		}
	}
	
	@Override
	public BasecampConfig resetEndpoints(BasecampConfig config) throws ApsSystemException {
		try {
			if (null == config) {
				config = _config;
			}
			config.setAuthorizationNewUrl(BasecampSystemConstants.DEFAULT_AUTHORIZATION_NEW_URL);
			config.setAuthorizationTokenUrl(BasecampSystemConstants.DEFAULT_AUTHORIZATION_TOKEN_URL);
			config.setAuthorizationUrl(BasecampSystemConstants.DEFAULT_AUTHORIZATION_URL);
			updateConfiguration(config);
			// update connection helper
			BasecampUrlHelper.updateConfiguration(_config);
		} catch (Throwable t) {
			throw new ApsSystemException("Error resetting basecamp configuration", t);
		}
		return config;
	}
	
	@Override
	public BasecampConfig getConfiguration() {
		
		String xml = _config.toXML();
		BasecampConfig config = new BasecampConfig(xml);
		
		return config;
	}

	protected void setConfig(BasecampConfig config) {
		this._config = config;
	}
	
	public ConfigInterface getConfigManager() {
		return _configManager;
	}

	public void setConfigManager(ConfigInterface configManager) {
		this._configManager = configManager;
	}
	
	private BasecampConfig _config;
	private ConfigInterface _configManager;
	
	private final static String PARAM_ACCESS_TOKEN = "access_token";
	private final static String PARAM_EXPIRES_IN = "expires_in";
	private final static String PARAM_REFRESH_TOKEN = "refresh_token";

}
