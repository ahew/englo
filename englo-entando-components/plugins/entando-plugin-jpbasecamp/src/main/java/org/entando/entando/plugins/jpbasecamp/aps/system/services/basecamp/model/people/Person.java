package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Person extends PersonReference {

	public Person(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}

	public Person(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	private Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(Person.class);
		
		return logger;
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		Boolean hasBasecampSiblings = null;
		Boolean accountOwner = null;

		super.parseJson(json);
		try {
			hasBasecampSiblings = json.getBoolean(HAS_BASECAMP_SIBLINGS);
		} catch (Throwable t) {
			getLogger().error("Error getting HAS_BASECAMP_SIBLINGS");
		}
		try {
			accountOwner = json.getBoolean(ACCOUNT_OWNER);
		} catch (Throwable t) {
			getLogger().error("Error getting ACCOUNT_OWNER");
		}
		try {
			JSONObject events = json.getJSONObject(EVENTS);
			setEvents(events);
		} catch (Throwable t) {
			getLogger().error("Error getting EVENTS");
		}
		try {
			JSONObject calendarEvents = json.getJSONObject(CALENDAR_EVENTS);
			setCalendarEvents(calendarEvents);
		} catch (Throwable t) {
			getLogger().error("Error getting CALENDAR_EVENTS");
		}
		try {
			JSONObject assignedTodos = json.getJSONObject(ASSIGNED_TODOS);
			setAssignedTodos(assignedTodos);
		} catch (Throwable t) {
			getLogger().error("Error getting ASSIGNED_TODOS");
		}
		try {
			JSONObject outstandingTodos = json.getJSONObject(OUTSTANDING_TODOS);
			setOutstandingTodos(outstandingTodos);
		} catch (Throwable t) {
			getLogger().error("Error getting OUTSTANDING_TODOS");
		}
		
		setHasBasecampSiblings(hasBasecampSiblings);
		setAccountOwner(accountOwner);
	}
	
	public Boolean getHasBasecampSiblings() {
		return _hasBasecampSiblings;
	}

	public void setHasBasecampSiblings(Boolean hasBasecampSiblings) {
		this._hasBasecampSiblings = hasBasecampSiblings;
	}

	public Boolean getAccountOwner() {
		return _accountOwner;
	}

	public void setAccountOwner(Boolean accountOwner) {
		this._accountOwner = accountOwner;
	}

	public JSONObject getCalendarEvents() {
		return _calendarEvents;
	}

	public void setCalendarEvents(JSONObject calendarEvents) {
		this._calendarEvents = calendarEvents;
	}

	public JSONObject getAssignedTodos() {
		return _assignedTodos;
	}

	public void setAssignedTodos(JSONObject assignedTodos) {
		this._assignedTodos = assignedTodos;
	}

	public JSONObject getOutstandingTodos() {
		return _outstandingTodos;
	}

	public void setOutstandingTodos(JSONObject outstandingTodos) {
		this._outstandingTodos = outstandingTodos;
	}

	public JSONObject getEvents() {
		return _events;
	}

	public void setEvents(JSONObject events) {
		this._events = events;
	}
	
	public PersonReference getPersonReference() {
		return _personReference;
	}

	public void setPersonReference(PersonReference personReference) {
		this._personReference = personReference;
	}

	private Boolean _hasBasecampSiblings;
	private Boolean _accountOwner;
	private JSONObject _events;
	private JSONObject _calendarEvents;
	private JSONObject _assignedTodos;
	private JSONObject _outstandingTodos;

	private PersonReference _personReference;
	
	public final static String HAS_BASECAMP_SIBLINGS = "has_basecamp_siblings";
	public final static String ACCOUNT_OWNER = "account_owner";
	public final static String EVENTS = "events";
	public final static String CALENDAR_EVENTS = "calendar_events";
	public final static String ASSIGNED_TODOS = "assigned_todos";
	public final static String OUTSTANDING_TODOS = "outstanding_todos";

	
}
