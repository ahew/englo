package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.AttachmentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CalendarEventItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.DocumentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.ForwardItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.TodolistItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Project extends AbstractProject {
	
	
	public Project() {
		super();
	}

	public Project(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public Project(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}
	
	public Project(String name, String description, boolean isClientProject) {
		super(name, description, isClientProject);
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(Project.class);
		
		return logger;
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		Boolean trashed = null;
		AttachmentItem attachment = null;
		TodolistItem todolist = null;
		CalendarEventItem calendarEvent = null;
		ForwardItem forward = null;
		CreatorItem creator = null;
		DocumentItem document = null;
		JSONObject accesses = null;
		JSONObject topics = null;

		super.parseJson(json);
		
		try {
			trashed = json.getBoolean(TRASHED);
		} catch (Throwable t) {
			getLogger().error("Error getting TRASHED");
		}
		try {
			JSONObject creatorj = json.getJSONObject(CREATOR);
			creator = new CreatorItem(creatorj);
		} catch (Throwable t) {
			getLogger().error("Error getting CREATOR");
		}
		try {
			accesses = json.getJSONObject(ACCESSES);
		} catch (Throwable t) {
			getLogger().error("Error getting ACCESSES");
		}
		try {
			JSONObject attachments = json.getJSONObject(ATTACHMENTS);
			attachment = new AttachmentItem(attachments);
		} catch (Throwable t) {
			getLogger().error("Error getting ATTACHMENTS");
		}
		try {
			JSONObject calendarEvents = json.getJSONObject(CALENDAR_EVENTS);
			calendarEvent = new CalendarEventItem(calendarEvents);
		} catch (Throwable t) {
			getLogger().error("Error getting CALENDAR_EVENTS");
		}
		try {
			JSONObject documents = json.getJSONObject(DOCUMENTS);
			document = new DocumentItem(documents);
		} catch (Throwable t) {
			getLogger().error("Error getting DOCUMENTS");
		}
		try {
			JSONObject forwards = json.getJSONObject(FORWARDS);
			forward = new ForwardItem(forwards);
		} catch (Throwable t) {
			getLogger().error("Error getting FORWARDS");
		}
		try {
			topics = json.getJSONObject(TOPICS);
		} catch (Throwable t) {
			getLogger().error("Error getting TOPICS");
		}
		try {
			JSONObject todolists = json.getJSONObject(TODOLISTS);
			todolist = new TodolistItem(todolists);
		} catch (Throwable t) {
			getLogger().error("Error getting TODOLISTS");
		}
		
		setDocuments(document);
		setAttachments(attachment);
		setCalendarEvents(calendarEvent);
		setForwards(forward);
		setTodolists(todolist);
		setTrashed(trashed);
		setCreator(creator);
		setAccesses(accesses);
		setTopics(topics);
	}
	
	/**
	 * Create all the expected voices, bearing a NULL if needed.
	 * Consider that we update only those fields which don't need a separated
	 * management URL 
	 * We also exclude ID to avoid troubles in updated operations
	 * @return
	 */
	public String toJSON() {
		JSONObject obj = new JSONObject();
		
		String name = getName();
		if (StringUtils.isNotBlank(name)) {
			obj.put(NAME, name);
		} else {
			obj.put(NAME, "");
		}
		String description = getDescription();
		if (StringUtils.isNotBlank(description)) {
			obj.put(DESCRIPTION, description);
		} else {
			obj.put(DESCRIPTION, "");
		}
		Boolean archived = getArchived();
		if (null != archived) {
			obj.put(ARCHIVED, archived);
		} else {
			obj.put(ARCHIVED, Boolean.FALSE);
		}
		Boolean isClientProject = getIsClientProject();
		if (null != isClientProject) {
			obj.put(IS_CLIENT_PROJECT, isClientProject);
		} else {
			obj.put(IS_CLIENT_PROJECT, Boolean.FALSE);
		}
//		Boolean trashed = getTrashed();
//		if (null != trashed) {
//			obj.put(TRASHED, trashed);
//		} else {
//			obj.put(TRASHED, Boolean.FALSE);
//		}
//		Boolean draft = getTrashed();
//		if (null != draft) {
//			obj.put(DRAFT, draft);
//		} else {
//			obj.put(DRAFT, Boolean.FALSE);
//		}
		
		return obj.toString();
	}
	
	@Override
	public Project clone() {
		Project project = new Project((String)null);
		
		project.setTrashed(this.getTrashed());
		project.setCreator(this.getCreator());
		project.setAccesses(this.getAccesses());
		project.setAttachments(this.getAttachments());
		project.setCalendarEvents(this.getCalendarEvents());
		project.setDocuments(this.getDocuments());
		project.setForwards(this.getForwards());
		project.setTopics(this.getTopics());
		project.setTodolists(this.getTodolists());
		
		return project;
	}
	
	/**
	 * Needed in order to enforce protection on setters
	 * @param jaxbProject
	 * @return
	 */
	public static Project fromJAXBProject(JAXBProject jaxbProject) {
		Project project = new Project((String)null);
		
		project.setId(jaxbProject.getId());
		project.setName(jaxbProject.getName());
		project.setDescription(jaxbProject.getDescription());
		project.setArchived(jaxbProject.getArchived());
		project.setIsClientProject(jaxbProject.getIsClientProject());
		project.setCreatedAt(jaxbProject.getCreatedAt());
		project.setUpdatedAt(jaxbProject.getUpdatedAt());
		project.setDraft(jaxbProject.getDraft());
		project.setStarred(jaxbProject.getStarred());
		project.setTrashed(jaxbProject.getTrashed());
		project.setCreator(jaxbProject.getCreator());
		project.setAccesses(jaxbProject.getAccesses());
		project.setAttachments(jaxbProject.getAttachments());
		project.setCalendarEvents(jaxbProject.getCalendarEvents());
		project.setDocuments(jaxbProject.getDocuments());
		project.setForwards(jaxbProject.getForwards());
		project.setTopics(jaxbProject.getTopics());
		project.setTodolists(jaxbProject.getTodolists());
		
		return project;
	}
	
	/**
	 * This is used to inherit the id of the passed project just before
	 * an update operation
	 * @param project
	 */
	public void updateFrom(Project project) {
		if (null != project) {
			this.setId(project.getId());
		}
	}
	
	public Boolean getTrashed() {
		return _trashed;
	}

	protected void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}

	public AttachmentItem getAttachments() {
		return _attachments;
	}

	protected void setAttachments(AttachmentItem attachments) {
		this._attachments = attachments;
	}

	public DocumentItem getDocuments() {
		return _documents;
	}

	protected void setDocuments(DocumentItem documents) {
		this._documents = documents;
	}

	public TodolistItem getTodolists() {
		return _todolists;
	}

	protected void setTodolists(TodolistItem todolists) {
		this._todolists = todolists;
	}
	
	public CalendarEventItem getCalendarEvents() {
		return _calendarEvents;
	}

	protected void setCalendarEvents(CalendarEventItem calendarEvents) {
		this._calendarEvents = calendarEvents;
	}

	public ForwardItem getForwards() {
		return _forwards;
	}

	protected void setForwards(ForwardItem forwards) {
		this._forwards = forwards;
	}
	
	public CreatorItem getCreator() {
		return _creator;
	}

	protected void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	
	public String getLocation() {
		return _location;
	}

	public void setLocation(String location) {
		this._location = location;
	}

	public JSONObject getAccesses() {
		return _accesses;
	}

	protected void setAccesses(JSONObject accesses) {
		this._accesses = accesses;
	}

	public JSONObject getTopics() {
		return _topics;
	}

	protected void setTopics(JSONObject topics) {
		this._topics = topics;
	}


	private Boolean _trashed;
	private CreatorItem _creator;
	@Deprecated
	private JSONObject _accesses;
	private AttachmentItem _attachments;
	private CalendarEventItem _calendarEvents;
	private DocumentItem _documents;
	private ForwardItem _forwards;
	@Deprecated
	private JSONObject _topics;
	private TodolistItem _todolists;
	
	@XmlTransient
	private String _location;
	
	public final static String CREATOR = "creator";
	public final static String ACCESSES = "accesses";
	public final static String ATTACHMENTS = "attachments";
	public final static String CALENDAR_EVENTS = "calendar_events";
	public final static String DOCUMENTS = "documents";
	public final static String TOPICS = "topics";
	public final static String TODOLISTS = "todolists";
	public final static String TRASHED = "trashed";
	public final static String FORWARDS = "forwards";

}
