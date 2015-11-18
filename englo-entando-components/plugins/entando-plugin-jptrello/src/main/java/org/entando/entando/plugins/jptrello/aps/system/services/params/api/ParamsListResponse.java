package org.entando.entando.plugins.jptrello.aps.system.services.params.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponse;
import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;


@XmlRootElement(name = "response")
public class ParamsListResponse extends AbstractApiResponse {
    
    @Override
    @XmlElement(name = "result", required = true)
    public ParamsListResponseResult getResult() {
        return (ParamsListResponseResult) super.getResult();
    }
    
    @Override
    protected AbstractApiResponseResult createResponseResultInstance() {
        return new ParamsListResponseResult();
    }
    
}