package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.AbstractProjectItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarEventItem extends AbstractProjectItem {
	
	// needed by JAXB
	public CalendarEventItem() {
		super();
	}
	
	public CalendarEventItem(String json) {
		super(json);
	}
	
	public CalendarEventItem(JSONObject json) {
		super(json);
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(CalendarEventItem.class);
		
		return logger;
	};
	

}
