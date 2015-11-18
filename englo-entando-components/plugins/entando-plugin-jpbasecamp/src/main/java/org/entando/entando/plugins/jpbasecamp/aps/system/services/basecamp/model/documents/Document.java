package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.item.CommentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO comments

public class Document {
	
	private Logger _logger = LoggerFactory.getLogger(Document.class);
	
	public Document(String title, String content) {
		setTitle(title);
		setContent(content);
	}
	
	public Document(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}
	
	public Document(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	private void parseJson(JSONObject json) {
		Long id = null;
		String title = null;
		Boolean trashed = null;
		Date createdAt = null;
		Date updatedAt = null;
		String content = null;
		List<CommentItem> comments = new ArrayList<CommentItem>();
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Error getting ID");
		}
		try {
			title = json.getString(TITLE);
		} catch (Throwable t) {
			_logger.error("Error getting NAME");
		}
		try {
			content = json.getString(CONTENT);
		} catch (Throwable t) {
			_logger.error("Error getting CONTENT");
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
			trashed = json.getBoolean(TRASHED);
		} catch (Throwable t) {
			_logger.error("Error getting TRASHED");
		}
		try {
			JSONArray jar = json.getJSONArray(COMMENTS);
			
			for (int i = 0; i < jar.length(); i++) {
				JSONObject jcur = jar.getJSONObject(i);
				CommentItem comment = new CommentItem(jcur);
				comments.add(comment);
			}
		} catch (Throwable t) {
			_logger.error("Error getting COMMENTS");
		}
		
		setId(id);
		setTitle(title);
		setContent(content);
		setUpdatedAt(updatedAt);
		setCreatedAt(createdAt);
		setTrashed(trashed);
		setComments(comments);
	}
	
	public String toJSON() {
		JSONObject obj = new JSONObject();
		
		String title = getTitle();
		if (StringUtils.isNotBlank(title)) {
			obj.put(TITLE, title);
		} else {
			obj.put(TITLE, JSONObject.NULL);
		}
		String content = getContent();
		if (StringUtils.isNotBlank(content)) {
			obj.put(CONTENT, content);
		} else {
			obj.put(CONTENT, JSONObject.NULL);
		}
		Boolean trashed = getTrashed();
		if (null != trashed) {
			obj.put(TRASHED, trashed);
		} else {
			obj.put(TRASHED, Boolean.FALSE);
		}
		Boolean isPrivate = getIsPrivate();
		if (null != isPrivate) {
			obj.put(PRIVATE, isPrivate);
		} else {
			obj.put(PRIVATE, Boolean.FALSE);
		}
		return obj.toString();
	}
	
	
	public long getId() {
		return _id;
	}
	protected void setId(long id) {
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
	protected void setUpdatedAt(Date updatedAt) {
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
	public Date getCreatedAt() {
		return _createdAt;
	}
	protected void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	public String getContent() {
		return _content;
	}
	public void setContent(String content) {
		this._content = content;
	}
	protected List<CommentItem> getComments() {
		return _comments;
	}
	protected void setComments(List<CommentItem> comments) {
		this._comments = comments;
	}
	public Project getProject() {
		return _project;
	}
	public void setProject(Project project) {
		this._project = project;
	}
	public String getLocation() {
		return _location;
	}
	public void setLocation(String location) {
		this._location = location;
	}


	private long _id;
	private String _title;
	private Date _createdAt;
	private Date _updatedAt;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private String _content;
	private List<CommentItem> _comments = new ArrayList<CommentItem>();
	
	private Project _project;
	private String _location;
	
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String PRIVATE = "private";
	public static final String TRASHED = "trashed";
	public final static String COMMENTS = "comments";
}
