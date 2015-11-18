package org.entando.entando.plugins.jptrello.aps.system.services.params.api;

import javax.xml.bind.annotation.XmlElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;


public class ParamsResponseResult extends AbstractApiResponseResult {
    
    @Override
    @XmlElement(name = "params", required = false)
    public JAXBParams getResult() {
        return (JAXBParams) this.getMainResult();
    }
    
}