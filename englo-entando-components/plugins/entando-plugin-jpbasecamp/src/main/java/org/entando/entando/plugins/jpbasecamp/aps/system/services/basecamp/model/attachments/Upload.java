package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments;

import java.util.Date;

import org.json.JSONObject;

public class Upload {

	private void parseJson(JSONObject json) {
		long id = 0;
		Date createdAt = null;
		Date updatedAt =  null;
		String content = null;
		Boolean trashed = null;
		AttachmentReference attachment = null;
	}
	
	public final static String ID = "id";
	public final static String CREATED_AT = "created_at";
	public final static String UPDATED_AT = "updated_at";
	public final static String CONTENT = "content";
	public final static String TRASHED = "trashed";
	public final static String ATTACHMENTS = "attachments";
	public final static String COMMENTS = "comments";
	public final static String SUBSCRIBERS = "subscribers";
	
}
