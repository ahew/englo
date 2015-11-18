package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.utils.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class describes a project descriptor as returned by a search
 * @author entando
 *
 */

public class ProjectReference extends AbstractProject {
	
	public ProjectReference(JSONObject json) {
		super(json);
		if (null != json) {
			parseJson(json);
		}
	}
	
	public ProjectReference(String json) {
		super(json);
		if (StringUtils.isNotBlank(json)) {
			JSONObject jobj = new JSONObject(json);
			parseJson(jobj);
		}
	}
	
	@Override
	protected Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(ProjectReference.class);
		
		return logger;
	}
	
	@Override
	protected void parseJson(JSONObject json) {
		Date lastEventAt = null;
		String url = null;
		
		super.parseJson(json);
		try {
			String lastEventAtStr = json.getString(LAST_EVENT_AT);
			lastEventAt = DateUtils.convertBasecampDate(lastEventAtStr);
		} catch (Throwable t) {
			getLogger().error("Could not get LAST_EVENT_AT");
		}
		try {
			url = json.getString(URL);
		} catch (Throwable t) {
			getLogger().error("Could not get URL");
		}
		
		setLastEventAt(lastEventAt);
		setUrl(url);
	}
	
	public static ProjectReference fromJAXBProjectReference(JAXBProjectReference jaxbProjectReference) {
		ProjectReference reference = new ProjectReference((String)null);
		
		reference.setId(jaxbProjectReference.getId());
		reference.setName(jaxbProjectReference.getName());
		reference.setDescription(jaxbProjectReference.getDescription());
		reference.setArchived(jaxbProjectReference.getArchived());
		reference.setIsClientProject(jaxbProjectReference.getIsClientProject());
		reference.setCreatedAt(jaxbProjectReference.getCreatedAt());
		reference.setUpdatedAt(jaxbProjectReference.getUpdatedAt());
		reference.setDraft(jaxbProjectReference.getDraft());
		reference.setStarred(jaxbProjectReference.getStarred());
		reference.setLastEventAt(jaxbProjectReference.getLastEventAt());
		reference.setUrl(jaxbProjectReference.getUrl());
		return reference;
	}
	
	/**
	 * Return the url of the project for the basecamp site
	 * @return
	 */
	public String basecampUrl() {
		String url = "#";
		
		if (StringUtils.isNotBlank(url)) {
			url = _url.replace("api/v1/", "").replace(".json", "");
		}
		return url;
	}
	
	public Date getLastEventAt() {
		return _lastEventAt;
	}
	public void setLastEventAt(Date lastEventAt) {
		this._lastEventAt = lastEventAt;
	}
	
	
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	
	private Date _lastEventAt;
	private String _url;
	
	public final static String LAST_EVENT_AT = "last_event_at";
	public final static String URL = "url"; 
}
