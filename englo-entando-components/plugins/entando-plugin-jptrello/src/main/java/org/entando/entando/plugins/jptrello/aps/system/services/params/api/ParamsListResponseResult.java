package org.entando.entando.plugins.jptrello.aps.system.services.params.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.entando.entando.aps.system.services.api.model.AbstractApiResponseResult;
import org.entando.entando.aps.system.services.api.model.ListResponse;

@XmlSeeAlso({JAXBParams.class})
public class ParamsListResponseResult extends AbstractApiResponseResult {
    
    @XmlElement(name = "items", required = false)
    public ListResponse<JAXBParams> getResult() {
        if (this.getMainResult() instanceof Collection) {
            List<JAXBParams> paramss = new ArrayList<JAXBParams>();
            paramss.addAll((Collection<JAXBParams>) this.getMainResult());
            ListResponse<JAXBParams> entity = new ListResponse<JAXBParams>(paramss) {};
            return entity;
        }
        return null;
    }

}