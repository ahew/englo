package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachableItem {

	Logger _logger = LoggerFactory.getLogger(AttachableItem.class);
	
	public AttachableItem(String json) { 
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}

	public AttachableItem(JSONObject json) { 
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		long id = 0;
		String type = null;
		String url = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Error getting ID");
		}
		try {
			type = json.getString(TYPE);
		} catch (Throwable t) {
			_logger.error("Error getting NAME");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			_logger.error("Error getting NAME");
		}
		
		setId(id);
		setType(type);
		setUrl(url);
	}
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}

	public String getType() {
		return _type;
	}

	protected void setType(String type) {
		this._type = type;
	}

	public String getUrl() {
		return _url;
	}

	protected void setUrl(String url) {
		this._url = url;
	}

	private long _id;
	private String _type;
	private String _url;	
	
	public final static String ID = "id";
	public final static String TYPE = "type";
	public final static String URL = "url";	
	
}
