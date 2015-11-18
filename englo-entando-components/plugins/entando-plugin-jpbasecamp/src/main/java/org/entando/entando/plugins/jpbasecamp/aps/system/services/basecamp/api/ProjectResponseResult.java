package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import javax.xml.bind.annotation.XmlElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProject;

public class ProjectResponseResult extends AbstractApiResponseResult {

	@Override
  @XmlElement(name = "project", required = false)
  public JAXBProject getResult() {
      return (JAXBProject) this.getMainResult();
  }

}
