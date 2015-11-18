package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.item.CommentItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.AttachmentItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// FIXME subscribers missing
public class CommentUploadItem extends CommentItem {

	Logger _logger = LoggerFactory.getLogger(CommentUploadItem.class);
	
	public CommentUploadItem(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}

	public CommentUploadItem(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject();
			parseJson(jobj);
		}
	}
	
	private void parseJson(JSONObject json) {
		long id = 0;
		List<AttachmentItem> attachments = new ArrayList<AttachmentItem>();
		
		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Error getting ID");
		}
		try {
			JSONArray jarattach = json.getJSONArray(ATTACHMENTS);
			for (int i = 0; i < jarattach.length(); i++) {
				JSONObject jattachment = jarattach.getJSONObject(i);
				AttachmentItem attachment = new AttachmentItem(jattachment);
				attachments.add(attachment);
			}
		} catch (Throwable t) {
			_logger.error("Error getting ATTACHMENTS");
		}
		
		setId(id);
		setAttachmentItem(attachments);
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	public List<AttachmentItem> getAttachmentItem() {
		return _attachmentItem;
	}
	public void setAttachmentItem(List<AttachmentItem> attachmentItem) {
		this._attachmentItem = attachmentItem;
	}

	private long _id;
	private List<AttachmentItem> _attachmentItem; // FIXME to test
	
	public final static String ID = "id";
	public final static String ATTACHMENTS = "attachments";
}
