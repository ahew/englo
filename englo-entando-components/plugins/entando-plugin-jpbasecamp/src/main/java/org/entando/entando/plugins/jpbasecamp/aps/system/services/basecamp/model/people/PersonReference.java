package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonReference extends CreatorItem {
	
	public PersonReference(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}
	
	public PersonReference(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	private Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(PersonReference.class);
		
		return logger;
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		long identityId = 0;
		String emailAddress = null;
		Boolean admin = false;
		Date createdAt = null;
		Date updatedAt = null;
		String url = null;
		
		super.parseJson(json);
		try {
			identityId = json.getLong(IDENTITY_ID);
		} catch (Throwable t) {
			getLogger().error("Error getting ID");
		}
		try {
			emailAddress = json.getString(EMAIL_ADDRESS);
		} catch (Throwable t) {
			getLogger().error("Error getting NAME");
		}
		try {
			String createdAtStr = json.getString(CREATED_AT);
			createdAt = DateUtils.convertBasecampDate(createdAtStr);
		} catch (Throwable t) {
			getLogger().error("Error getting CREATED_AT");
		}
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			getLogger().error("Error getting UPDATED_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			getLogger().error("Error getting URL");
		}
		try {
			admin = json.getBoolean(ADMIN);
		} catch (Throwable t) {
			getLogger().error("Error getting ADMIN");
		}
		
		setIdentityId(identityId);
		setEmailAddress(emailAddress);
		setIdentityId(identityId);
		setAdmin(admin);
		setUpdatedAt(updatedAt);
		setCreatedAt(createdAt);
		setUrl(url);
	}
	
	public long getIdentityId() {
		return _identityId;
	}

	public void setIdentityId(long identityId) {
		this._identityId = identityId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this._emailAddress = emailAddress;
	}

	public Boolean getAdmin() {
		return _admin;
	}

	public void setAdmin(Boolean admin) {
		this._admin = admin;
	}

	public Date getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return _updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	private long _identityId;
	private String _emailAddress;
	private Boolean _admin;
	private Date _createdAt;
	private Date _updatedAt;
	private String _url;
	
	
//	public final static String ID = "id";
	public final static String IDENTITY_ID = "identity_id";
//	public final static String NAME = "name";
	public final static String EMAIL_ADDRESS = "email_address";
	public final static String ADMIN = "admin";
//	public final static String AVATAR_URL = "avatar_url";
//	public final static String FULLSIZE_AVATAR_URL = "fullsize_avatar_url";
	public final static String CREATED_AT = "created_at";
	public final static String UPDATED_AT = "updated_at";
	public final static String URL = "url";
}
