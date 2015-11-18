package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssignedTodoItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.BucketItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssignedTodolistReference extends TodolistReference {

	public AssignedTodolistReference(JSONObject json) {
		super(json);
	}

	public AssignedTodolistReference(String json) {
		super(json);
	}
	
	private Logger getLogger() {
		return LoggerFactory.getLogger(AssignedTodolistReference.class); 
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		List<AssignedTodoItem> list = new ArrayList<AssignedTodoItem>();
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
		Date createdAt = null;
		
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
			getLogger().error("Error getting UPDATED_AT");
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
		try {
			JSONArray jassignedTodo = json.getJSONArray(ASSIGNED_TODOS);
			for (int i = 0; i < jassignedTodo.length(); i++) {
				JSONObject atdlj = jassignedTodo.getJSONObject(i);
				AssignedTodoItem assignedTodolist = new AssignedTodoItem(atdlj);
				list.add(assignedTodolist);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			getLogger().error("Error getting ASSIGNED_TODOS");
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
		setAssignedTodolist(list);
		setCreatedAt(createdAt);
	}
	
	public Date getCreatedAt() {
		return _createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	public List<AssignedTodoItem> getAssignedTodolist() {
		return this._assignedTodolist;
	}

	public void setAssignedTodolist(List<AssignedTodoItem> assignedTodolist) {
		this._assignedTodolist = assignedTodolist;
	}

	private List<AssignedTodoItem> _assignedTodolist;
	private Date _createdAt;
	
	public final static String ASSIGNED_TODOS = "assigned_todos";
	public final static String CREATED_AT = "created_at";
	
}
