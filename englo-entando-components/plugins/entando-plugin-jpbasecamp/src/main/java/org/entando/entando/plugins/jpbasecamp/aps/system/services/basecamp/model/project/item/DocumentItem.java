package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.AbstractProjectItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DocumentItem extends AbstractProjectItem {
	
	public DocumentItem() { }
	
	public DocumentItem(String json) {
		super(json);
	}
	
	public DocumentItem(JSONObject json) {
		super(json);
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(DocumentItem.class);
		
		return logger;
	}
	
}
