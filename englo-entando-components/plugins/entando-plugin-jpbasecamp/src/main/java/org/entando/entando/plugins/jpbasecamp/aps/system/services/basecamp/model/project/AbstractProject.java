package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractProject {
	
	
	public AbstractProject() {};
	
	public AbstractProject(JSONObject json) {
		super();
		if (null != json) {
			parseJson(json);
		}
	}
	
	public AbstractProject(String json) {
		super();
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public AbstractProject(String name, String description, boolean isClientProject) {
		setName(name);
		setDescription(description);
		setIsClientProject(isClientProject);
	}
	
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(AbstractProject.class);
		
		return logger;
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String name = null;
		String description = null;
		Boolean archived = null;
		Boolean isClientProject = null;
		Date createdAt = null;
		Date updatedAt = null;
		Boolean draft = null;
		Boolean starred = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			getLogger().error("Could not get ID");
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			getLogger().error("Could not get NAME");
		}
		try {
			description = json.getString(DESCRIPTION);
		} catch (Throwable t) {
			getLogger().error("Could not get DESCRIPTION");
		}
		
		try {
			archived = json.getBoolean(ARCHIVED);
		} catch (Throwable t) {
			getLogger().error("Could not get ARCHIVED");
		}
		try {
			isClientProject = json.getBoolean(IS_CLIENT_PROJECT);
		} catch (Throwable t) {
			getLogger().error("Could not get IS_CLIENT_PROJECT");
		}
		
		try {
			String createdAtStr = json.getString(CREATED_AT);
			createdAt = DateUtils.convertBasecampDate(createdAtStr);
		} catch (Throwable t) {
			getLogger().error("Could not get CREATED_AT");
		}
		
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			getLogger().error("Could not get UPDATED_AT");
		}
		
		try {
			draft = json.getBoolean(DRAFT);
		} catch (Throwable t) {
			getLogger().error("Could not get DRAFT");
		}
		
		try {
			starred = json.getBoolean(STARRED);
		} catch (Throwable t) {
			getLogger().error("Could not get STARRED");
		}
		
		setId(id);
		setName(name);
		setDescription(description);
		setArchived(archived);
		setIsClientProject(isClientProject);
		setCreatedAt(createdAt);
		setUpdatedAt(updatedAt);
		setStarred(starred);
		setDraft(draft);
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
	public Boolean getArchived() {
		return _archived;
	}
	public void setArchived(Boolean archived) {
		this._archived = archived;
	}
	public Boolean getIsClientProject() {
		return _isClientProject;
	}
	public void setIsClientProject(Boolean isClientProject) {
		this._isClientProject = isClientProject;
	}
	public Date getCreatedAt() {
		return _createdAt;
	}
	protected void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	protected void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	public Boolean getDraft() {
		return _draft;
	}
	public void setDraft(Boolean draft) {
		this._draft = draft;
	}
	public Boolean getStarred() {
		return _starred;
	}
	public void setStarred(Boolean starred) {
		this._starred = starred;
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
	
	public final static String ID = "id";
	public final static String NAME = "name";
	public final static String DESCRIPTION = "description";
	public final static String ARCHIVED = "archived";
	public final static String IS_CLIENT_PROJECT = "is_client_project";
	public final static String CREATED_AT = "created_at";
	public final static String UPDATED_AT = "updated_at";
	public final static String DRAFT = "draft";
	public final static String STARRED = "starred";
}
