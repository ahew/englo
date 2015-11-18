package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.BucketItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Todolist extends TodolistReference {
	
public Todolist(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}

	public Todolist(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	private Logger getLogger() {
		return LoggerFactory.getLogger(Todolist.class);
	}
	
	public Todolist(String name, String description, long position) {
		super(name, description);
		setPosition(position);
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String name = null;
		String description = null;
		Date updatedAt = null;
		Date createdAt = null;
		String url = null;
		Boolean completed = null;
		long position = 0;
		Boolean isPrivate = null;
		Boolean trashed = null;
		long completedCount = 0;
		long remaninigCount = 0;
		CreatorItem creator = null;
		BucketItem bucket = null;
		List<TodoItem> remaningList = new ArrayList<TodoItem>();
		List<TodoItem> completedList = new ArrayList<TodoItem>();
		List<TodoItem> trashedList = new ArrayList<TodoItem>();
		
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
			JSONObject todos = json.getJSONObject(TODOS);
			
			try {
				JSONArray jar = todos.getJSONArray(REMAINING);
				for (int i = 0; i < jar.length(); i++) {
					JSONObject remaningj = jar.getJSONObject(i);
					TodoItem todoItem = new TodoItem(remaningj);
					remaningList.add(todoItem);
				}
			} catch (Throwable t) {
				getLogger().error("Error getting TODOS-REMAINING");
			}
			try {
				JSONArray jar = todos.getJSONArray(COMPLETED);
				for (int i = 0; i < jar.length(); i++) {
					JSONObject completedj = jar.getJSONObject(i);
					TodoItem todoItem = new TodoItem(completedj);
					completedList.add(todoItem);
				}
			} catch (Throwable t) {
				getLogger().error("Error getting TODOS-COMPLETED");
			}
			try {
				JSONArray jar = todos.getJSONArray(TRASHED);
				for (int i = 0; i < jar.length(); i++) {
					JSONObject trashedj = jar.getJSONObject(i);
					TodoItem todoItem = new TodoItem(trashedj);
					trashedList.add(todoItem);
				}
			} catch (Throwable t) {
				getLogger().error("Error getting TODOS-TRASHED");
			}
		} catch (Throwable t) {
			getLogger().error("Error getting TODOS");
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
		setRemaningList(remaningList);
		setTrashedList(trashedList);
		setCompletedList(completedList);
		setCreatedAt(createdAt);
	}
	
	public String toJSON() {
		JSONObject json = new JSONObject();
		
		String name = this.getName();
		if (StringUtils.isNotBlank(name)) {
			json.put(Todolist.NAME, name);
		} else {
			json.put(Todolist.NAME, "");
		}
		String description = this.getDescription();
		if (StringUtils.isNotBlank(description)) {
			json.put(Todolist.DESCRIPTION, description);
		} else {
			json.put(Todolist.DESCRIPTION, "");
		}
//		Boolean isCompleted = this.getCompleted();
//		if (null != isCompleted) {
//			json.put(Todolist.COMPLETED, isCompleted);
//		} else {
//			json.put(Todolist.COMPLETED, Boolean.FALSE);
//		}
		json.put(Todolist.POSITION, this.getPosition());
		Boolean isPrivate = this.getIsPrivate();
		if (null != isPrivate) {
			json.put(Todolist.PRIVATE, isPrivate);
		} else {
			json.put(Todolist.PRIVATE, Boolean.FALSE);
		}
		return json.toString();
	}
	
	public static Todolist fromJAXBTodolist(JAXBTodolist jaxbtodolist) {
		Todolist todolist = new Todolist((String)null);
		
		todolist.setId(jaxbtodolist.getId());
		todolist.setName(jaxbtodolist.getName());
		todolist.setDescription(jaxbtodolist.getDescription());
		todolist.setUpdatedAt(jaxbtodolist.getUpdatedAt());
		todolist.setUrl(jaxbtodolist.getUrl());
		todolist.setCompleted(jaxbtodolist.getCompleted());
		todolist.setPosition(jaxbtodolist.getPosition());
		todolist.setIsPrivate(jaxbtodolist.getIsPrivate());
		todolist.setTrashed(jaxbtodolist.getTrashed());
		todolist.setCompletedCount(jaxbtodolist.getCompletedCount());
		todolist.setRemaninigCount(jaxbtodolist.getRemaninigCount());
		todolist.setCreator(jaxbtodolist.getCreator());
		todolist.setBucket(jaxbtodolist.getBucket());
		todolist.setCreatedAt(jaxbtodolist.getCreatedAt());
		todolist.setRemaningList(jaxbtodolist.getRemaningList());
		todolist.setCompletedList(jaxbtodolist.getCompletedList());
		todolist.setTrashedList(jaxbtodolist.getTrashedList());
		
		return todolist;
	}
	
	public Date getCreatedAt() {
		return _createdAt;
	}

	protected void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	
	public List<TodoItem> getRemaningList() {
		return _remaningList;
	}

	protected void setRemaningList(List<TodoItem> remaningList) {
		this._remaningList = remaningList;
	}

	public List<TodoItem> getCompletedList() {
		return _completedList;
	}

	protected void setCompletedList(List<TodoItem> completedList) {
		this._completedList = completedList;
	}

	public List<TodoItem> getTrashedList() {
		return _trashedList;
	}

	protected void setTrashedList(List<TodoItem> trashedList) {
		this._trashedList = trashedList;
	}
	
	public String getLocation() {
		return _location;
	}

	public void setLocation(String location) {
		this._location = location;
	}

	private Date _createdAt;
	private List<TodoItem> _remaningList = new ArrayList<TodoItem>();
	private List<TodoItem> _completedList = new ArrayList<TodoItem>();
	private List<TodoItem> _trashedList = new ArrayList<TodoItem>();
	
	private String _location;
	
	public final static String CREATED_AT = "created_at";
	public final static String COMMENTS = "comments";
	
	public final static String REMAINING = "remaining";
	public final static String COMPLETED = "completed";
	public final static String TRASHED = "trashed";
	public final static String TODOS = "todos";
	
}
