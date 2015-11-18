package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.BucketItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TodolistReference {
	
	public TodolistReference(String json) { 
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public TodolistReference(JSONObject json) { 
		if (null != json) {
			parseJson(json);
		}
	}
	
	public TodolistReference(String name, String description) {
		_name = name;
		_description = description;
	}

	private Logger getLogger() {
		return LoggerFactory.getLogger(TodolistReference.class); 
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String name = null;
		String description = null;
		Date updatedAt = null;
		String url = null;
		Boolean completed = null;
		long position = 0;
		Boolean isPrivate = null;
		Boolean trashed = null;
		long completedCount = 0;
		long remaninigCount = 0;
		CreatorItem creator = null;
		BucketItem bucket = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			getLogger().error("Error getting ID");
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			getLogger().error("Error getting NAME");
		}
		try {
			description = json.getString(DESCRIPTION);
		} catch (Throwable t) {
			getLogger().error("Error getting DESCRIPTION");
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
			completed = json.getBoolean(COMPLETED);
		} catch (Throwable t) {
			getLogger().error("Error getting COMPLETED");
		}
		try {
			position = json.getLong(POSITION);
		} catch (Throwable t) {
			getLogger().error("Error getting POSITION");
		}
		try {
			isPrivate = json.getBoolean(PRIVATE);
		} catch (Throwable t) {
			getLogger().error("Error getting PRIVATE");
		}
		try {
			trashed = json.getBoolean(TRASHED);
		} catch (Throwable t) {
			getLogger().error("Error getting TRASHED");
		}
		try {
			completedCount = json.getLong(COMPLETED_COUNT);
		} catch (Throwable t) {
			getLogger().error("Error getting COMPLETED_COUNT");
		}
		try {
			remaninigCount = json.getLong(REMAINING_COUNT);
		} catch (Throwable t) {
			getLogger().error("Error getting REMAINING_COUNT");
		}
		try {
			JSONObject creatorj = json.getJSONObject(CREATOR);
			creator = new CreatorItem(creatorj);
		} catch (Throwable t) {
			getLogger().error("Error getting CREATOR");
		}
		try {
			JSONObject bucketj = json.getJSONObject(BUCKET);
			bucket = new BucketItem(bucketj);
		} catch (Throwable t) {
			getLogger().error("Error getting BUCKET");
		}
		
		setId(id);
		setName(name);
		setDescription(description);
		setUpdatedAt(updatedAt);
		setUrl(url);
		setCompleted(completed);
		setPosition(position);
		setIsPrivate(isPrivate);
		setTrashed(trashed);
		setCompletedCount(completedCount);
		setRemaninigCount(remaninigCount);
		setCreator(creator);
		setBucket(bucket);
	}
	
	public long getId() {
		return _id;
	}
	protected void setId(long id) {
		this._id = id;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	protected void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	public String getUrl() {
		return _url;
	}
	protected void setUrl(String url) {
		this._url = url;
	}
	public Boolean getCompleted() {
		return _completed;
	}
	public void setCompleted(Boolean completed) {
		this._completed = completed;
	}
	public long getPosition() {
		return _position;
	}
	public void setPosition(long position) {
		this._position = position;
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
	protected void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}
	public long getCompletedCount() {
		return _completedCount;
	}
	protected void setCompletedCount(long completedCount) {
		this._completedCount = completedCount;
	}
	public long getRemaninigCount() {
		return _remaninigCount;
	}
	protected void setRemaninigCount(long remaninigCount) {
		this._remaninigCount = remaninigCount;
	}
	public CreatorItem getCreator() {
		return _creator;
	}
	protected void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	public BucketItem getBucket() {
		return _bucket;
	}
	protected void setBucket(BucketItem bucket) {
		this._bucket = bucket;
	}
	public Long getProjectId() {
		return _projectId;
	}
	public void setProjectId(Long projectId) {
		this._projectId = projectId;
	}


	private long _id;
	private String _name;
	private String _description;
	private Date _updatedAt;
	private String _url;
	private Boolean _completed;
	private long _position;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private long _completedCount;
	private long _remaninigCount;
	private CreatorItem _creator;
	private BucketItem _bucket;
	
	private Long _projectId;
	
	public final static String ID = "id";
	public final static String NAME = "name";
	public final static String DESCRIPTION = "description";
	public final static String UPDATED_AT = "updated_at";
	public final static String URL = "url";
	public final static String COMPLETED = "completed";
	public final static String POSITION = "position";
	public final static String PRIVATE = "private";
	public final static String TRASHED = "trashed";
	public final static String COMPLETED_COUNT = "completed_count";
	public final static String REMAINING_COUNT = "remaining_count";
	public final static String CREATOR = "creator";
	public final static String BUCKET = "bucket";
	
}
