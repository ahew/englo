package org.entando.entando.plugins.jptrello.aps.system.services.params.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jptrello.aps.system.services.params.Params;

@XmlRootElement(name = "params")
@XmlType(propOrder = {"id", "organization", "api_key", "api_secret", "token"})
public class JAXBParams {

    public JAXBParams() {
        super();
    }

    public JAXBParams(Params params) {
        this.setId(params.getId());
        this.setOrganization(params.getOrganization());
        this.setApi_key(params.getApi_key());
        this.setApi_secret(params.getApi_secret());
        this.setToken(params.getToken());
    }
    
    public Params getParams() {
    	Params params = new Params();
        params.setId(this.getId());
        params.setOrganization(this.getOrganization());
        params.setApi_key(this.getApi_key());
        params.setApi_secret(this.getApi_secret());
        params.setToken(this.getToken());
        return params;
    }

    @XmlElement(name = "id", required = true)
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    @XmlElement(name = "organization", required = true)
    public String getOrganization() {
        return _organization;
    }
    public void setOrganization(String organization) {
        this._organization = organization;
    }

    @XmlElement(name = "api_key", required = true)
    public String getApi_key() {
        return _api_key;
    }
    public void setApi_key(String api_key) {
        this._api_key = api_key;
    }

    @XmlElement(name = "api_secret", required = true)
    public String getApi_secret() {
        return _api_secret;
    }
    public void setApi_secret(String api_secret) {
        this._api_secret = api_secret;
    }

    @XmlElement(name = "token", required = true)
    public String getToken() {
        return _token;
    }
    public void setToken(String token) {
        this._token = token;
    }

    private int _id;
    private String _organization;
    private String _api_key;
    private String _api_secret;
    private String _token;
}