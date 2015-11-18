package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import javax.xml.bind.annotation.XmlElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodo;

public class TodoResponseResult extends AbstractApiResponseResult {

	@Override
  @XmlElement(name = "todo", required = false)
  public JAXBTodo getResult() {
      return (JAXBTodo) this.getMainResult();
  }
	
}
