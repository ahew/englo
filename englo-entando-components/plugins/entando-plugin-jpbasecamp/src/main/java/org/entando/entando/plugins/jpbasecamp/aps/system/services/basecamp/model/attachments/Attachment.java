package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments;

import java.util.Date;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.AttachableItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attachment extends AttachmentReference {
	
	public Attachment(JSONObject json) {
		super(json);
	}
	
	public Attachment(String json) {
		super(json);
	}

	protected Logger getLogger() {
		return _logger = LoggerFactory.getLogger(Attachment.class);
	}
	
	@Override
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
		setAttachable(attachable);
		
//		setLinkedSource(linkedSource);
//		setLinkedType(linkedType);
//		setLinkUrl(linkUrl);
	}
	
}
