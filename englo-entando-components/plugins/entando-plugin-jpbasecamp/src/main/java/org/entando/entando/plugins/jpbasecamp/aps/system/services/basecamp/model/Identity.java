package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Identity {

	private Logger _logger = LoggerFactory.getLogger(Identity.class);
	
	public Identity(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}

	public Identity(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}

	protected void parseJson(JSONObject json) {
		Long id = null;
		String first_name = null;
		String last_name = null;
		String email_address = null;

		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Could not get ID");
		}
		try {
			first_name = json.getString(FIRST_NAME);
		} catch (Throwable t) {
			_logger.error("Could not get FIRST NAME");
		}	
		try {
			last_name = json.getString(LAST_NAME);
		} catch (Throwable t) {
			_logger.error("Could not get LAST NAME");
		}
		try {
			email_address = json.getString(EMAIL_ADDRESS);
		} catch (Throwable t) {
			_logger.error("Could not get EMAIL ADDRESS");
		}

		setId(id);
		setFirstName(first_name);
		setEmailAddress(email_address);
		setLastName(last_name);
	}
	
	public long getId() {
		return _id;
	}
	protected void setId(long id) {
		this._id = id;
	}
	public String getFirstName() {
		return _firstName;
	}
	protected void setFirstName(String firstName) {
		this._firstName = firstName;
	}
	public String getLastName() {
		return _lastName;
	}
	protected void setLastName(String lastName) {
		this._lastName = lastName;
	}
	public String getEmailAddress() {
		return _emailAddress;
	}
	protected void setEmailAddress(String emailAddress) {
		this._emailAddress = emailAddress;
	}

	private long _id;
	private String _firstName;
	private String _lastName;
	private String _emailAddress;

	public static final String ID = "id";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String EMAIL_ADDRESS = "email_address";

}
