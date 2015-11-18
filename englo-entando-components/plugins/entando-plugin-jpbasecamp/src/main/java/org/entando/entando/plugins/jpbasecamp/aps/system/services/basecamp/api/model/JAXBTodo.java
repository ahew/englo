package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;


@XmlRootElement(name = "todo")
@XmlType(propOrder = {"id","content","dueAt","count","createdAt","updatedAt","url","position","dueOn","assignee","todolistId","completed","commentsCount","isPrivate","trashed","creator", "projectId"})
@XmlSeeAlso({CreatorItem.class, AssigneeItem.class})
public class JAXBTodo {
	
	public JAXBTodo() { }
	
	public JAXBTodo(Todo todo) {
		setId(todo.getId());
		setContent(todo.getContent());
		setDueAt(todo.getDueAt());
		setCreatedAt(todo.getCreatedAt());
		setUpdatedAt(todo.getUpdatedAt());
		setUrl(todo.getUrl());
		setPosition(todo.getPosition());
		setCommentsCount(todo.getCommentsCount());
		setDueOn(todo.getDueOn());
		setAssignee(todo.getAssignee());
		setTodolistId(todo.getTodolistId());
		setCompleted(todo.getCompleted());
		setIsPrivate(todo.getIsPrivate());
		setTrashed(todo.getTrashed());
		setCreator(todo.getCreator());
	}

	@XmlElement(name = "id", required = true)
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	@XmlElement(name = "content", required = true)
	public String getContent() {
		return _content;
	}
	public void setContent(String content) {
		this._content = content;
	}
	@XmlElement(name = "dueAt", required = true)
	public Date getDueAt() {
		return _dueAt;
	}
	public void setDueAt(Date dueAt) {
		this._dueAt = dueAt;
	}
	@XmlElement(name = "count", required = true)
	public long getCount() {
		return _count;
	}
	public void setCount(long count) {
		_count = count;
	}
	@XmlElement(name = "createdAt", required = true)
	public Date getCreatedAt() {
		return _createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	@XmlElement(name = "updatedAt", required = true)
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	@XmlElement(name = "url", required = true)
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	@XmlElement(name = "position", required = true)
	public long getPosition() {
		return _position;
	}
	public void setPosition(long position) {
		this._position = position;
	}
	@XmlElement(name = "dueOn", required = true)
	public Date getDueOn() {
		return _dueOn;
	}
	public void setDueOn(Date dueOn) {
		this._dueOn = dueOn;
	}
	@XmlElement(name = "todolistId", required = true)
	public long getTodolistId() {
		return _todolistId;
	}
	public void setTodolistId(long todolistId) {
		this._todolistId = todolistId;
	}
	@XmlElement(name = "completed", required = true)
	public Boolean getCompleted() {
		return _completed;
	}
	public void setCompleted(Boolean completed) {
		this._completed = completed;
	}
	@XmlElement(name = "commentsCount", required = true)
	public long getCommentsCount() {
		return _commentsCount;
	}
	public void setCommentsCount(long commentsCount) {
		this._commentsCount = commentsCount;
	}
	@XmlElement(name = "isPrivate", required = true)
	public Boolean getIsPrivate() {
		return _isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this._isPrivate = isPrivate;
	}
	@XmlElement(name = "trashed", required = true)
	public Boolean getTrashed() {
		return _trashed;
	}
	public void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}
	@XmlElement(name = "creator", required = true)
	public CreatorItem getCreator() {
		return _creator;
	}
	public void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	@XmlElement(name = "projectId")
	public long getProjectId() {
		return _projectId;
	}
	public void setProjectId(long projectId) {
		this._projectId = projectId;
	}
	@XmlElement(name = "assignee", required = true)
	public AssigneeItem getAssignee() {
		return _assignee;
	}
	public void setAssignee(AssigneeItem assignee) {
		this._assignee = assignee;
	}


	private long _id;
	private String _content;
	private Date _dueAt;
	private long _count;
	private Date _createdAt;
	private Date _updatedAt;
	private String _url;
	private long _position;
	private Date _dueOn;
	private AssigneeItem _assignee;
	private long _todolistId;
	private Boolean _completed;
	private long _commentsCount;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private CreatorItem _creator;
	// needed by the REST API engine only
	private long _projectId;
}
