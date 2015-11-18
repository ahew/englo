package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssigneeItem {
	
	public AssigneeItem() { }
	
	public AssigneeItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public AssigneeItem(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}

	private Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(AssigneeItem.class);
		
		return logger;
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String type = null;
		String name = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			getLogger().error("Error getting ID");
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			getLogger().error("Error getting DESCRIPTION");
		}
		try {
			type = json.getString(TYPE);
		} catch (Throwable t) {
			getLogger().error("Error getting DESCRIPTION");
		}
		setId(id);
		setName(name);
		setType(type);
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}

	private long _id;
	private String _type;
	private String _name;
	
	public final static String ID = "id";
	public final static String TYPE = "type";
	public final static String NAME = "name";
	
	public final static String TYPE_PERSON = "Person";
	
	
}
