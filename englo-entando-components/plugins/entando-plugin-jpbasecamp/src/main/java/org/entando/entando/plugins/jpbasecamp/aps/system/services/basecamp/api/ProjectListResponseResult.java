package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.aps.system.services.api.model.ListResponse;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProjectReference;


@XmlSeeAlso({JAXBProjectReference.class})
public class ProjectListResponseResult extends AbstractApiResponseResult {

	@Override
	@XmlElement(name = "items", required = false)
	public ListResponse<JAXBProjectReference> getResult() {
		if (this.getMainResult() instanceof Collection) {
			List<JAXBProjectReference> projects = new ArrayList<JAXBProjectReference>();
			projects.addAll((Collection<JAXBProjectReference>) this.getMainResult());
			ListResponse<JAXBProjectReference> entity = new ListResponse<JAXBProjectReference>(projects) {};
			return entity;
		}
		return null;
	}

}
