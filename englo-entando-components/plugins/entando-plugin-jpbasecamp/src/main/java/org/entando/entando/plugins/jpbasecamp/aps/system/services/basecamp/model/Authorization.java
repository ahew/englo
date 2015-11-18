package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authorization {
	
	private Logger _logger = LoggerFactory.getLogger(Authorization.class);
	
	public Authorization(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public Authorization(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject jobj) {
		Identity identity = null;
		String expiresAt = null;
		JSONArray jar = null;
		
		try {
			JSONObject jIdentity = null;
			jIdentity = jobj.getJSONObject(IDENTITY);
			identity = new Identity(jIdentity);
		} catch (Throwable t) {
			_logger.error("Could not get IDENTITY");
		}
		try {
			expiresAt = jobj.getString(EXPIRES_AT);
		} catch (Throwable t) {
			_logger.error("Could not get EXPIRES_AT");
		}
		try {
			jar = jobj.getJSONArray(ACCOUNTS);
			for (int i = 0; i < jar.length(); i++) {
				
				JSONObject json = (JSONObject) jar.get(i);
				Account account = new Account(json);
				_accounts.add(account);
			}
		} catch (Throwable t) {
			_logger.error("Could not get ACCOUNTS");
		}
		
		setExpiresAt(expiresAt);
		setIdentity(identity);
	}
	
	/**
	 * Get the service URL
	 * @param productCode
	 */
	public String getServiceUrl(String productCode) {
		String href = null;
		
		if (StringUtils.isNotBlank(productCode)
				&& null != _accounts 
				&& !_accounts.isEmpty()) {
			for (Account account: _accounts) {
				if (account.getProduct().equalsIgnoreCase(productCode)) {
					href = account.getHref();
				}
			}
		}
		return href;
	}
	
	public List<Account> getAccounts() {
		return _accounts;
	}
	protected void setAccounts(List<Account> accounts) {
		this._accounts = accounts;
	}
	public String getExpiresAt() {
		return _expiresAt;
	}
	protected void setExpiresAt(String expiresAt) {
		this._expiresAt = expiresAt;
	}
	public Identity getIdentity() {
		return _identity;
	}
	protected void setIdentity(Identity identity) {
		this._identity = identity;
	}


	private List<Account> _accounts = new ArrayList<Account>();
	private String _expiresAt;
	private Identity _identity;
	
	public final static String IDENTITY = "identity";
	public final static String EXPIRES_AT = "expires_at";
	public final static String ACCOUNTS = "accounts";
}
