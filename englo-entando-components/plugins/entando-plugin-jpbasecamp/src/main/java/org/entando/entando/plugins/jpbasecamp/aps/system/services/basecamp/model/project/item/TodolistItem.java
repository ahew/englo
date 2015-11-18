package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodolistItem {
	
	private Logger _logger = LoggerFactory.getLogger(TodolistItem.class);
	
	// needed by JAXB
	public TodolistItem() { }
	
	public TodolistItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public TodolistItem(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		int remainingCount = 0;
		int completedCount = 0;
		Date updatedAt = null;
		String url = null;

		try {
			remainingCount = json.getInt(REMAINING_COUNT);
		} catch (Throwable t) {
			_logger.error("Couldn't get REMAINING_COUNT");
		}
		try {
			completedCount = json.getInt(COMPLETED_COUNT);
		} catch (Throwable t) {
			_logger.error("Couldn't get COMPLETED_COUNT");
		}
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			_logger.error("Couldn't get UPDATED_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			_logger.error("Couldn't get URL");
		}
		
		setRemainingCount(remainingCount);
		setCompletedCount(completedCount);
		setUpdatedAt(updatedAt);
		setUrl(url);
	}

	
	public int getRemainingCount() {
		return _remainingCount;
	}
	public void setRemainingCount(int remainingCount) {
		this._remainingCount = remainingCount;
	}
	public int getCompletedCount() {
		return _completedCount;
	}
	public void setCompletedCount(int completedCount) {
		this._completedCount = completedCount;
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

	private int _remainingCount;
	private int _completedCount ;
	private Date _updatedAt;
	private String _url;
	
	public final static String TODOLISTS = "todolists";
	public final static String REMAINING_COUNT = "remaining_count";
	public final static String COMPLETED_COUNT = "completed_count";
	public final static String UPDATED_AT = "updated_at";
	public final static String URL = "url";
}
