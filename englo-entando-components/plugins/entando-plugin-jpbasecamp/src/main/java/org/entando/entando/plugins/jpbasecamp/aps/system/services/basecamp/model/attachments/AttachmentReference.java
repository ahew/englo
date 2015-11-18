package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.AttachableItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FIXME attachable missing

public class AttachmentReference {
	
	Logger _logger = LoggerFactory.getLogger(AttachmentReference.class);
	
	public AttachmentReference(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			
			parseJson(jobj);
		}
	}

	public AttachmentReference(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	protected Logger getLogger() {
		return _logger = LoggerFactory.getLogger(AttachmentReference.class);
	}
	
	protected void parseJson(JSONObject json) {
		long id = 0;
		String key = null;
		String name = null;
		long byteSize = 0;
		String contentType = null;
		Date createdAt = null;
		String url = null;
		Boolean isPrivate = null;
		Boolean trashed = null;
		CreatorItem creator = null;
		AttachableItem attachable = null;
		String linkedSource = null;
		String linkedType = null;
		String linkUrl = null;
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			getLogger().error("Error getting ID");
		}
		try {
			key = json.getString(KEY);
		} catch (Throwable t) {
			getLogger().error("Error getting KEY");
		}
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			getLogger().error("Error getting NAME");
		}
		try {
			byteSize = json.getLong(BYTE_SIZE);
		} catch (Throwable t) {
			getLogger().error("Error getting BYTE_SIZE");
		}
		try {
			contentType = json.getString(CONTENT_TYPE);
		} catch (Throwable t) {
			getLogger().error("Error getting CONTENT_TYPE");
		}
		try {
			String createdAtStr = json.getString(CREATED_AT);
			createdAt = DateUtils.convertBasecampDate(createdAtStr);
		} catch (Throwable t) {
			getLogger().error("Error getting CREATED_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			getLogger().error("Error getting URL");
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
			JSONObject creatorj = json.getJSONObject(CREATOR);
			creator = new CreatorItem(creatorj);
		} catch (Throwable t) {
			getLogger().error("Error getting CREATOR");
		}
		try {
			linkedSource = json.getString(LINKED_SOURCE);
		} catch (Throwable t) {
			getLogger().error("Error getting LINKED_SOURCE");
		}
		try {
			linkedType = json.getString(LINKED_SOURCE);
		} catch (Throwable t) {
			getLogger().error("Error getting LINKED_SOURCE");
		}
		try {
			linkedType = json.getString(LINKED_TYPE);
		} catch (Throwable t) {
			getLogger().error("Error getting LINKED_TYPE");
		}
		try {
			JSONObject attachablej = json.getJSONObject(ATTACHABLE);
			attachable = new AttachableItem(attachablej);
		} catch (Throwable t) {
			getLogger().error("Error getting ATTACHABLE");
		}
		
		setId(id);
		setKey(key);
		setName(name);
		setByteSize(byteSize);
		setContentType(contentType);
		setCreatedAt(createdAt);
		setUrl(url);
		setIsPrivate(isPrivate);
		setTrashed(trashed);
		setCreator(creator);
		
		setLinkedSource(linkedSource);
		setLinkedType(linkedType);
		setLinkUrl(linkUrl);
		setAttachable(attachable);
	}
	
	/**
	 * Return whether the document is externally linked
	 * @return
	 */
	public boolean isLinkedDocument() {
		return (StringUtils.isNotBlank(_linkedSource)
				&& StringUtils.isNotBlank(_linkedType)
				&& StringUtils.isNotBlank(_linkUrl));
	}
	
	public long getId() {
		return _id;
	}
	protected void setId(long id) {
		this._id = id;
	}
	public String getKey() {
		return _key;
	}
	protected void setKey(String key) {
		this._key = key;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	public long getByteSize() {
		return _byteSize;
	}
	protected void setByteSize(long byteSize) {
		this._byteSize = byteSize;
	}
	public String getContentType() {
		return _contentType;
	}
	protected void setContentType(String contentType) {
		this._contentType = contentType;
	}
	public Date getCreatedAt() {
		return _createdAt;
	}
	protected void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	public String getUrl() {
		return _url;
	}
	protected void setUrl(String url) {
		this._url = url;
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
	public CreatorItem getCreator() {
		return _creator;
	}
	protected void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	public String getLinkedSource() {
		return _linkedSource;
	}
	protected void setLinkedSource(String linkedSource) {
		this._linkedSource = linkedSource;
	}
	public String getLinkedType() {
		return _linkedType;
	}
	protected void setLinkedType(String linkedType) {
		this._linkedType = linkedType;
	}
	public String getLinkUrl() {
		return _linkUrl;
	}
	protected void setLinkUrl(String linkUrl) {
		this._linkUrl = linkUrl;
	}
	public Project getProject() {
		return _project;
	}
	public void setProject(Project project) {
		this._project = project;
	}
	public AttachableItem getAttachable() {
		return _attachable;
	}
	public void setAttachable(AttachableItem attachable) {
		this._attachable = attachable;
	}

	private long _id;
	private String _key;
	private String _name;
	private long _byteSize;
	private String _contentType;
	private Date _createdAt;
	private String _url;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private CreatorItem _creator;
	private AttachableItem _attachable;
	
	private String _linkedSource;
	private String _linkedType;
	private String _linkUrl;
	
	private Project _project;
	
	public final static String ID = "id";
	public final static String KEY = "key";
	public final static String NAME = "name";
	public final static String BYTE_SIZE = "byte_size";
	public final static String CONTENT_TYPE = "content_type";
	public final static String CREATED_AT = "created_at";
	public final static String URL = "url";
	public final static String PRIVATE = "private";
	public final static String TRASHED = "trashed";
	public final static String CREATOR = "creator";
	public final static String ATTACHABLE = "attachable";
	
	public final static String LINKED_SOURCE = "linked_source";
	public final static String LINKED_TYPE = "linked_type";
	public final static String LINK_URL = "link_url";

}
