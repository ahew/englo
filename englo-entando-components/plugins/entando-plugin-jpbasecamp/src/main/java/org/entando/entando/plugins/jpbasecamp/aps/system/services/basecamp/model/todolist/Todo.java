package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssignedTodoItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.SubscriberItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Todo extends TodoItem {
	
	public Todo() {
		super();
	}
	
	public Todo(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public Todo(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(Todo.class);
		
		return logger;
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		super.parseJson(json);

		try {
			JSONArray subscribers = json.getJSONArray(SUBSCRIBERS);
			for (int i = 0; i < subscribers.length(); i++) {
				JSONObject jsub = subscribers.getJSONObject(i);
				SubscriberItem subscriber = new SubscriberItem(jsub);
				getSubscribers().add(subscriber);
			}
		} catch (Throwable t) {
			getLogger().warn("Error getting TODOLIST_ID: " + t.getMessage());
		}
	}
	
	/**
	 * Generate the JSON for the creation of a new object
	 * 
	 * @param todo
	 * @param subscriberId
	 * @return
	 */
	public String toJSON(Person person) {
		JSONObject json = new JSONObject();
		JSONObject jassignee = new JSONObject();

		if (null != person) {
			try {
				jassignee.put(AssigneeItem.ID, person.getId());
				jassignee.put(AssigneeItem.TYPE, AssigneeItem.TYPE_PERSON);
				json.put(AssignedTodoItem.ASSIGNEE, jassignee);
//                                json.put(TodoItem.COMPLETED, getCompleted());
			} catch (Throwable t) {
				getLogger().error("Invalid ASSIGNEE data for JSON conversion:" + t.getMessage());
			}
		} else {
			getLogger().debug("Todo won't be assigned to anybody");
		}
		String content = this.getContent();
		if (StringUtils.isNotBlank(content)) {
			json.put(TodoItem.CONTENT, content);
		} else {
			getLogger().error("Invalid CONTENT data for JSON conversion");
		}
		String date = DateUtils.convertToBasecampDate(this.getDueAt());
		if (StringUtils.isNotBlank(date)) {
			json.put(TodoItem.DUE_AT, date);
		} else {
			getLogger().error("Invalid DUE_AT data for JSON conversion");
		}

		return json.toString();

	}
	
	/**
	 * Generate the JSON for the creation of a new object.
	 * 
	 * @param todo
	 * @param subscriberId
	 * @return
	 */
	public String toJSON() {
		JSONObject json = new JSONObject();
		JSONObject jassignee = new JSONObject();

		if (null != this.getAssignee()) {
			try {
				jassignee.put(AssigneeItem.ID, getAssignee().getId());
				jassignee.put(AssigneeItem.TYPE, AssigneeItem.TYPE_PERSON);
				json.put(AssignedTodoItem.ASSIGNEE, jassignee);
                                json.put(TodoItem.COMPLETED, getCompleted());
			} catch (Throwable t) {
				getLogger().error("Invalid ASSIGNEE data for JSON conversion:" + t.getMessage());
			}
		} else {
			getLogger().debug("Todo won't be assigned to anybody");
		}
		String content = this.getContent();
		if (StringUtils.isNotBlank(content)) {
			json.put(TodoItem.CONTENT, content);
		} else {
			getLogger().error("Invalid CONTENT data for JSON conversion");
		}
		String date = DateUtils.convertToBasecampDate(this.getDueAt());
		if (StringUtils.isNotBlank(date)) {
			json.put(TodoItem.DUE_AT, date);
		} else {
			getLogger().error("Invalid DUE_AT data for JSON conversion");
		}
//		json.put(TodoItem.POSITION, this.getPosition());
		return json.toString();
	}
	
	public static Todo fromJAXBTodo(JAXBTodo jaxbTodo) {
		Todo todo = new Todo((String)null);
		
		todo.setId(jaxbTodo.getId());
		todo.setContent(jaxbTodo.getContent());
		todo.setDueAt(jaxbTodo.getDueAt());
		todo.setCreatedAt(jaxbTodo.getCreatedAt());
		todo.setUpdatedAt(jaxbTodo.getUpdatedAt());
		todo.setUrl(jaxbTodo.getUrl());
		todo.setPosition(jaxbTodo.getPosition());
		todo.setCommentsCount(jaxbTodo.getCommentsCount());
		todo.setDueOn(jaxbTodo.getDueOn());
		todo.setAssignee(jaxbTodo.getAssignee());
		todo.setTodolistId(jaxbTodo.getTodolistId());
		todo.setCompleted(jaxbTodo.getCompleted());
		todo.setIsPrivate(jaxbTodo.getIsPrivate());
		todo.setTrashed(jaxbTodo.getTrashed());
		todo.setCreator(jaxbTodo.getCreator());
		todo.setProjectId(jaxbTodo.getProjectId());
		return todo;
	}
	
	public List<SubscriberItem> getSubscribers() {
		return _subscribers;
	}
	public void setSubscribers(List<SubscriberItem> subscribers) {
		this._subscribers = subscribers;
	}
	public String getLocation() {
		return _location;
	}
	public void setLocation(String location) {
		this._location = location;
	}
	public Long getProjectId() {
		return _projectId;
	}
	public void setProjectId(Long projectId) {
		this._projectId = projectId;
	}

	private String _location;
	private List<SubscriberItem> _subscribers = new ArrayList<SubscriberItem>();
	
	private Long _projectId;
}
