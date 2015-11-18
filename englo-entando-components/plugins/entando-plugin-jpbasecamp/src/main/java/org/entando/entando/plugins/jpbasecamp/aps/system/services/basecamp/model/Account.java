package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Account {
	
	private Logger _logger = LoggerFactory.getLogger(Account.class);

	public Account(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}
	
	public Account(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	protected void parseJson(JSONObject json) {
		Long id = null;
		String product = null;
		String name = null;
		String href = null;

		try {
			id = json.getLong(ID);
		} catch (Throwable t) {
			_logger.error("Could not get ID");
		}
		try {
			product = json.getString(PRODUCT);
		} catch (Throwable t) {
			_logger.error("Could not get PRODUCT");
		}	
		try {
			name = json.getString(NAME);
		} catch (Throwable t) {
			_logger.error("Could not get NAME");
		}
		try {
			href = json.getString(HREF);
		} catch (Throwable t) {
			_logger.error("Could not get HREF");
		}

		setId(id);
		setProduct(product);
		setHref(href);
		setName(name);
	}

	public long getId() {
		return _id;
	}
	protected void setId(long id) {
		this._id = id;
	}
	public String getProduct() {
		return _product;
	}
	protected void setProduct(String product) {
		this._product = product;
	}
	public String getName() {
		return _name;
	}
	protected void setName(String name) {
		this._name = name;
	}
	public String getHref() {
		return _href;
	}
	protected void setHref(String href) {
		this._href = href;
	}
	
	private long _id;
	private String _product;
	private String _name;
	private String _href;
	
	public static final String ID = "id";
	public static final String PRODUCT = "product";
	public static final String NAME = "name";
	public static final String HREF = "href";
}
