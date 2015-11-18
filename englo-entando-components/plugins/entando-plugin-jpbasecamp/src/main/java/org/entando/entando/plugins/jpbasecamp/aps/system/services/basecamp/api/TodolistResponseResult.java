package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import javax.xml.bind.annotation.XmlElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolist;

public class TodolistResponseResult extends AbstractApiResponseResult {
	
	@Override
  @XmlElement(name = "todolist", required = false)
  public JAXBTodolist getResult() {
      return (JAXBTodolist) this.getMainResult();
  }
	
}
