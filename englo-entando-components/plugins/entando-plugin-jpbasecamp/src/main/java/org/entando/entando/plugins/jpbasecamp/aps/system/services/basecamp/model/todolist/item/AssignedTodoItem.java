package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FIXME da rifattorizzare
public class AssignedTodoItem {
	
	private static final Logger _logger = LoggerFactory.getLogger(AssignedTodoItem.class);
	
	public AssignedTodoItem() { }
	
	public AssignedTodoItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public AssignedTodoItem(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}

	protected void parseJson(JSONObject json) {
		long id = 0;
		String content = null;
		Date dueAt = null;
		long commentsCount = 0;
		Date createdAt = null;
		Date updatedAt = null;
		String url = null;
		long position = 0;
		Date dueOn = null;
		AssigneeItem assigneeItem = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Error getting ID");
		}
		try {
			content = json.getString(CONTENT);
		} catch (Throwable t) {
			_logger.error("Error getting CONTENT");
		}
		try {
			String dueAtStr = json.getString(DUE_AT);
			dueAt = DateUtils.convertBasecampDate(dueAtStr);
		} catch (Throwable t) {
			_logger.error("Error getting DUE_AT " + t.getLocalizedMessage());
		}
		try {
			String dueOnStr = json.getString(DUE_ON);
			dueOn = DateUtils.convertBasecampDate(dueOnStr);
		} catch (Throwable t) {
			_logger.error("Error getting DUE_ON");
		}
		try {
			commentsCount = json.getLong(COMMENTS_COUNT);
		} catch (Throwable t) {
			_logger.error("Error getting COMMENTS_COUNT");
		}
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
			url = json.getString(URL);
		} catch (Throwable t) {
			_logger.error("Error getting URL");
		}
		try {
			position = json.getLong(POSITION);
		} catch (Throwable t) {
			_logger.error("Error getting POSITION");
		}
		try {
			JSONObject jassignee = json.getJSONObject(ASSIGNEE);
			assigneeItem = new AssigneeItem(jassignee);
		} catch (Throwable t) {
			_logger.error("Error getting ASSIGNEE");
		}
		
		setId(id);
		setContent(content);
		setDueAt(dueAt);
		setCreatedAt(createdAt);
		setUpdatedAt(updatedAt);
		setUrl(url);
		setPosition(position);
		setCommentsCount(commentsCount);
		setDueOn(dueOn);
		setAssignee(assigneeItem);
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public String getContent() {
		return _content;
	}
	public void setContent(String content) {
		this._content = content;
	}
	public Date getDueAt() {
		return _dueAt;
	}
	public void setDueAt(Date dueAt) {
		this._dueAt = dueAt;
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
	public long getPosition() {
		return _position;
	}
	public void setPosition(long position) {
		this._position = position;
	}
	public Date getDueOn() {
		return _dueOn;
	}
	public void setDueOn(Date dueOn) {
		this._dueOn = dueOn;
	}
	public AssigneeItem getAssignee() {
		return _assignee;
	}
	public void setAssignee(AssigneeItem assigneeItem) {
		this._assignee = assigneeItem;
	}
	public long getCommentsCount() {
		return _commentsCount;
	}
	public void setCommentsCount(long commentsCount) {
		this._commentsCount = commentsCount;
	}

	private long _id;
	private String _content;
	private Date _dueAt;
	private long _commentsCount;
	private Date _createdAt;
	private Date _updatedAt;
	private String _url;
	private long _position;
	private Date _dueOn;
	private AssigneeItem _assignee;
	
	public final static String ID = "id";
	public final static String CONTENT = "content";
	public final static String DUE_AT = "due_at";
	public final static String DUE_ON = "due_on";
	public final static String COMMENTS_COUNT = "comments_count";
	public final static String CREATED_AT = "created_at";
	public final static String UPDATED_AT = "updated_at";
	public final static String URL = "url";
	public final static String POSITION = "position";
	public final static String ASSIGNEE = "assignee";
	
}
