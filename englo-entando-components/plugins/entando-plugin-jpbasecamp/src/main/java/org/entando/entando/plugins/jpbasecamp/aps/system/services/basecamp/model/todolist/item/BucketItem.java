package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BucketItem {
	
	Logger _logger = LoggerFactory.getLogger(BucketItem.class);
	
	public BucketItem(String json) { 
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public BucketItem(JSONObject json) { 
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		long id = 0;
		String name = null;
		String url = null;
		String type = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Error getting ID");
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			_logger.error("Error getting NAME");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			_logger.error("Error getting URL");
		}
		try {
			type = json.getString(TYPE);
		} catch (Throwable t) {
			_logger.error("Error getting TYPE");
		}
		
		setId(id);
		setName(name);
		setType(type);
		setUrl(url);
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
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		this._type = type;
	}

	private long _id;
	private String _name;
	private String _url;
	private String _type;
	
	public final static String ID = "id";
	public final static String NAME = "name";
	public final static String TYPE = "type";
	public final static String URL = "url";
	
}
