package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.item;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentItem {

	
	Logger _logger = LoggerFactory.getLogger(CommentItem.class);

	public CommentItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public CommentItem(JSONObject json) { 
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		String content = null;
		Date createdAt = null;
		Date updatedAt = null;
		CreatorItem creator = null;
		
		try {
			String createdAtStr = json.getString(CREATED_AT);
			createdAt = DateUtils.convertBasecampDate(createdAtStr);
		} catch (Throwable t) {
			_logger.error("Error getting CREATED_AT");
		}
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			_logger.error("Error getting UPDATED_AT");
		}
		try {
			JSONObject creatorj = json.getJSONObject(CREATOR);
			creator = new CreatorItem(creatorj);
		} catch (Throwable t) {
			_logger.error("Error getting CREATOR");
		}
		
		setContent(content);
		setCreatedAt(createdAt);
		setUpdatedAt(updatedAt);
		setCreator(creator);
	}
	
	public String getContent() {
		return _content;
	}
	public void setContent(String content) {
		this._content = content;
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
	public CreatorItem getCreator() {
		return _creator;
	}
	public void setCreator(CreatorItem creator) {
		this._creator = creator;
	}

	private String _content;
	private Date _createdAt;
	private Date _updatedAt;
	private CreatorItem _creator;

	
	public final static String CONTENT = "content";
	public final static String CREATED_AT = "created_at";
	public final static String UPDATED_AT = "updated_at";
	public final static String CREATOR = "creator";
}
