package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.aps.system.services.api.model.ListResponse;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolistReference;


@XmlSeeAlso({JAXBTodolistReference.class})
public class TodolistListResponseResult extends AbstractApiResponseResult {

	@Override
	@XmlElement(name = "items", required = false)
	public ListResponse<JAXBTodolistReference> getResult() {
		if (this.getMainResult() instanceof Collection) {
			List<JAXBTodolistReference> projects = new ArrayList<JAXBTodolistReference>();
			projects.addAll((Collection<JAXBTodolistReference>) this.getMainResult());
			ListResponse<JAXBTodolistReference> entity = new ListResponse<JAXBTodolistReference>(projects) {};
			return entity;
		}
		return null;
	}

}
