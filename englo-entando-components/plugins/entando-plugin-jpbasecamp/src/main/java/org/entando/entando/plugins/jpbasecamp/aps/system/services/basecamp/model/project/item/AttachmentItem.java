package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.AbstractProjectItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentItem extends AbstractProjectItem {	

	// needed by JAXB
	public AttachmentItem() {
		super();
	};
	
	public AttachmentItem(String json) {
		super(json);
	}
	
	public AttachmentItem(JSONObject json) {
		super(json);
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(AttachmentItem.class);
		
		return logger;
	};
	

}
