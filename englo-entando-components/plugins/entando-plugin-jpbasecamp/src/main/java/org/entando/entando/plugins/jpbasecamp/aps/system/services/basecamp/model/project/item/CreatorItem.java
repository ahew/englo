
package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatorItem {

	// needed by JAXB
	public CreatorItem() { }
	
	public CreatorItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}

	public CreatorItem(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	private Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(CreatorItem.class);
		
		return logger;
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String name = null;
		String avatarUrl = null;
		String fullsizeAvatarUrl = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			getLogger().warn("Error getting ID " + t.getMessage());
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			getLogger().warn("Error getting NAME " + t.getMessage());
		}
		try {
			avatarUrl = json.getString(AVATAR_URL);
		} catch (Throwable t) {
			getLogger().warn("Error getting AVATAR_URL " + t.getMessage());
		}
		try {
			fullsizeAvatarUrl = json.getString(FULLSIZE_AVATAR_URL);
		} catch (Throwable t) {
			getLogger().warn("Error getting FULLSIZE_AVATAR_URL " + t.getMessage());
		}
		
		setId(id);
		setName(name);
		setAvatarUrl(avatarUrl);
		setFullsizeAvatarUrl(fullsizeAvatarUrl);
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getAvatarUrl() {
		return _avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this._avatarUrl = avatarUrl;
	}
	public String getFullsizeAvatarUrl() {
		return _fullsizeAvatarUrl;
	}
	public void setFullsizeAvatarUrl(String fullsizeAvatarUrl) {
		this._fullsizeAvatarUrl = fullsizeAvatarUrl;
	}
	
	private long _id;
	private String _name;
	private String _avatarUrl;
	private String _fullsizeAvatarUrl;
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String AVATAR_URL = "avatar_url";
	public static final String FULLSIZE_AVATAR_URL = "fullsize_avatar_url";
	
}
