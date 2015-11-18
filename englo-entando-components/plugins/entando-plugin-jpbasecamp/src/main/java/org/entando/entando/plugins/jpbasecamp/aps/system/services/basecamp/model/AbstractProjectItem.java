package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProjectItem {

	public AbstractProjectItem() { }

	public AbstractProjectItem(String json) {
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject();
			parseJson(jobj);
		}
	}

	public AbstractProjectItem(JSONObject json) {
		if (null != json) {
			parseJson(json);
		}
	}

	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(AbstractProjectItem.class);

		logger.warn("You should extend 'getLogger()'");
		return logger;
	};

	private void parseJson(JSONObject json) {
		Integer count = null;
		Date updatedAt = null;
		String url = null;

		try {
			count = json.getInt(COUNT);
		} catch (Throwable t) {
			getLogger().error("Could not get COUNT");
		}
		try {
			String updatedAtStr = json.getString(UPDATED_AT);
			updatedAt = DateUtils.convertBasecampDate(updatedAtStr);
		} catch (Throwable t) {
			getLogger().error("Could not get UPDATED_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			getLogger().error("Could not get URL");
		}

		setCount(count);
		setUrl(url);
		setUpdatedAt(updatedAt);
	}

	public Integer getCount() {
		return _count;
	}
	public void setCount(Integer count) {
		this._count = count;
	}
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}

	private Integer _count;
	private Date _updatedAt;
	private String _url;

	public final static String COUNT = "count";
	public final static String UPDATED_AT = "updated_at";
	public final static String URL = "url";
}
