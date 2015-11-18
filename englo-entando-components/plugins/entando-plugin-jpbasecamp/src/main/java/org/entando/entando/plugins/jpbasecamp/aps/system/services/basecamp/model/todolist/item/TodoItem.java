package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodoItem extends AssignedTodoItem {
	
	public TodoItem() { }
	
	public TodoItem(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public TodoItem(JSONObject json) {
		super(json);
		if (null != json) {
			
			parseJson(json);
		}
	}
	
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(TodoItem.class);
		
		return logger;
	}
	
	protected void parseJson(JSONObject json) {
		long todolistId = 0;
		Boolean completed = null;
		Boolean isPrivate = null;
		Boolean trashed = null;
		CreatorItem creator = null;
		
		super.parseJson(json);

		try {
			todolistId = json.getLong(TODOLIST_ID);
		} catch (Throwable t) {
			getLogger().warn("Error getting TODOLIST_ID");
		}
		try {
			completed = json.getBoolean(COMPLETED);
		} catch (Throwable t) {
			getLogger().warn("Error getting COMPLETED");
		}
		try {
			isPrivate = json.getBoolean(PRIVATE);
		} catch (Throwable t) {
			getLogger().warn("Error getting PRIVATE");
		}
		try {
			trashed = json.getBoolean(TRASHED);
		} catch (Throwable t) {
			getLogger().warn("Error getting TRASHED");
		}
		try {
			JSONObject creatorj = json.getJSONObject(CREATOR);
			creator = new CreatorItem(creatorj);
		} catch (Throwable t) {
			getLogger().warn("Error getting CREATOR");
		}
		
		setTodolistId(todolistId);
		setCompleted(completed);
		setIsPrivate(isPrivate);
		setTrashed(trashed);
		setCreator(creator);
	}
	
	public long getTodolistId() {
		return _todolistId;
	}
	protected void setTodolistId(long todolistId) {
		this._todolistId = todolistId;
	}
	public Boolean getCompleted() {
		return _completed;
	}
	public void setCompleted(Boolean completed) {
		this._completed = completed;
	}
	public Boolean getIsPrivate() {
		return _isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this._isPrivate = isPrivate;
	}
	public Boolean getTrashed() {
		return _trashed;
	}
	public void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}
	public CreatorItem getCreator() {
		return _creator;
	}
	public void setCreator(CreatorItem creator) {
		this._creator = creator;
	}


	private long _todolistId;
	private Boolean _completed;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private CreatorItem _creator;

	
	public final static String TODOLIST_ID = "todolist_id";
	public final static String COMPLETED = "completed";
	public final static String PRIVATE = "private";
	public final static String TRASHED = "trashed";
	public final static String CREATOR = "creator";
	public final static String SUBSCRIBERS = "subscribers";
	
}
