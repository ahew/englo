package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.AbstractProjectItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardItem extends AbstractProjectItem {
	
//needed by JAXB
	public ForwardItem() {
		super();
	}
	
	public ForwardItem(String json) {
		super(json);
	}
	
	public ForwardItem(JSONObject json) {
		super(json);
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(ForwardItem.class);
		
		return logger;
	};
	

}
