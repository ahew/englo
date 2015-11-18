package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponse;
import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;

@XmlRootElement(name = "response")
public class ProjectListResponse extends AbstractApiResponse {

	@XmlElement(name = "result", required = true)
	public ProjectListResponseResult getResult() {
		return (ProjectListResponseResult) super.getResult();
	}
	
	@Override
	protected AbstractApiResponseResult createResponseResultInstance() {
		return new ProjectListResponseResult();
	}

}
