package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
//import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.AttachmentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CalendarEventItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.DocumentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.ForwardItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.TodolistItem;
import org.json.JSONObject;

@XmlRootElement(name = "project")
@XmlType(propOrder = {"id", "name", "description", "archived", "isClientProject", "createdAt", "updatedAt", "draft", "starred", "trashed", "creator", "accesses", "attachments", "calendarEvents", "documents", "forwards", "topics", "todolists"})
@XmlSeeAlso({CreatorItem.class, JSONObject.class, AttachmentItem.class, CalendarEventItem.class, DocumentItem.class, ForwardItem.class, TodolistItem.class})
public class JAXBProject {

	public JAXBProject() {
//		super();
	}
	
	public JAXBProject(Project project) {
		this.setId(project.getId());
		this.setName(project.getName());
		this.setDescription(project.getDescription());
		this.setArchived(project.getArchived());
		this.setIsClientProject(project.getIsClientProject());
		this.setCreatedAt(project.getCreatedAt());
		this.setUpdatedAt(project.getUpdatedAt());
		this.setDraft(project.getDraft());
		this.setStarred(project.getStarred());
		this.setTrashed(project.getTrashed());
		this.setCreator(project.getCreator());
		this.setAccesses(project.getAccesses());
		this.setAttachments(project.getAttachments());
		this.setCalendarEvents(project.getCalendarEvents());
		this.setDocuments(project.getDocuments());
		this.setForwards(project.getForwards());
		this.setTopics(project.getTopics());
		this.setTodolists(project.getTodolists());
	}
	
	public Project getProject() {
		Project project = Project.fromJAXBProject(this);
		
		return project;
	}
	
	@XmlElement(name = "id", required = false)
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	@XmlElement(name = "name", required = true)
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	@XmlElement(name = "description", required = true)
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	@XmlElement(name = "archived", required = false)
	public Boolean getArchived() {
		return _archived;
	}
	public void setArchived(Boolean archived) {
		this._archived = archived;
	}
	@XmlElement(name = "isClientProject", required = false)
	public Boolean getIsClientProject() {
		return _isClientProject;
	}
	public void setIsClientProject(Boolean isClientProject) {
		this._isClientProject = isClientProject;
	}
	@XmlElement(name = "createdAt", required = false)
	public Date getCreatedAt() {
		return _createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	@XmlElement(name = "updatedAt", required = false)
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	@XmlElement(name = "draft", required = false)
	public Boolean getDraft() {
		return _draft;
	}
	public void setDraft(Boolean draft) {
		this._draft = draft;
	}
	@XmlElement(name = "starred", required = false)
	public Boolean getStarred() {
		return _starred;
	}
	public void setStarred(Boolean starred) {
		this._starred = starred;
	}
	@XmlElement(name = "trashed", required = false)
	public Boolean getTrashed() {
		return _trashed;
	}
	public void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}
	@XmlElement(name = "creator", required = false)
	public CreatorItem getCreator() {
		return _creator;
	}
	public void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	@XmlElement(name = "accesses", required = false)
	public JSONObject getAccesses() {
		return _accesses;
	}
	public void setAccesses(JSONObject accesses) {
		this._accesses = accesses;
	}
	@XmlElement(name = "attachments", required = false)
	public AttachmentItem getAttachments() {
		return _attachments;
	}
	public void setAttachments(AttachmentItem attachments) {
		this._attachments = attachments;
	}
	@XmlElement(name = "calendarEvents", required = false)
	public CalendarEventItem getCalendarEvents() {
		return _calendarEvents;
	}
	public void setCalendarEvents(CalendarEventItem calendarEvents) {
		this._calendarEvents = calendarEvents;
	}
	@XmlElement(name = "documents", required = false)
	public DocumentItem getDocuments() {
		return _documents;
	}
	public void setDocuments(DocumentItem documents) {
		this._documents = documents;
	}
	@XmlElement(name = "forwards", required = false)
	public ForwardItem getForwards() {
		return _forwards;
	}
	public void setForwards(ForwardItem forwards) {
		this._forwards = forwards;
	}
	@XmlElement(name = "topics", required = false)
	public JSONObject getTopics() {
		return _topics;
	}
	public void setTopics(JSONObject topics) {
		this._topics = topics;
	}
	@XmlElement(name = "todolists", required = false)
	public TodolistItem getTodolists() {
		return _todolists;
	}
	public void setTodolists(TodolistItem todolists) {
		this._todolists = todolists;
	}


	private long _id;
	private String _name;
	private String _description;
	private Boolean _archived;
	private Boolean _isClientProject;
	private Date _createdAt;
	private Date _updatedAt;
	private Boolean _draft;
	private Boolean _starred;
	private Boolean _trashed;
	private CreatorItem _creator;
	private JSONObject _accesses;
	private AttachmentItem _attachments;
	private CalendarEventItem _calendarEvents;
	private DocumentItem _documents;
	private ForwardItem _forwards;
	private JSONObject _topics;
	private TodolistItem _todolists;
	
}
