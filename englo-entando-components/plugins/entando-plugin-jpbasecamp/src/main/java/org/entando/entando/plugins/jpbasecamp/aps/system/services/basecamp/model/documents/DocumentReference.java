package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentReference {
	
	private Logger _logger = LoggerFactory.getLogger(DocumentReference.class);
	
	public DocumentReference(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	public DocumentReference(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		long id = 0;
		Boolean isPrivate = null;
		Boolean trashed = null;
		Date updatedAt = null;
		String url = null;
		String title = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Could not get ID");
		}
		try {
			isPrivate = json.getBoolean(PRIVATE);
		} catch (Throwable t) {
			_logger.error("Could not get IS_CLIENT_PROJECT");
		}
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			_logger.error("Could not get UPDATED_AT");
		}
		try {
			title = json.getString(TITLE);
		} catch (Throwable t) {
			_logger.error("Could not get LAST_EVENT_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			_logger.error("Could not get URL");
		}
		
		setId(id);
		setUpdatedAt(updatedAt);
		setUrl(url);
		setIsPrivate(isPrivate);
		setTrashed(trashed);
		setTitle(title);
	}
	
	public long getId() {
		return _id;
	}

	public void setId(long id) {
		this._id = id;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public Date getUpdatedAt() {
		return _updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
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
	
	public Project getProject() {
		return _project;
	}

	public void setProject(Project project) {
		this._project = project;
	}
	
	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	private long _id;
	private String _title;
	private Date _updatedAt;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private String _url;
	
	private Project _project;
	
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String UPDATED_AT = "updated_at";
	public static final String URL = "url";
	public static final String PRIVATE = "private";
	public static final String TRASHED = "trashed";
}
