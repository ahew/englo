package org.entando.entando.plugins.jpjenkins.aps.system.services.params.api;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jpjenkins.aps.system.services.params.Params;

@XmlRootElement(name = "params")
@XmlType(propOrder = {"id", "username", "password", "jenkins_url", "token"})
public class JAXBParams {

    public JAXBParams() {
        super();
    }

    public JAXBParams(Params params) {
        this.setId(params.getId());
        this.setUsername(params.getUsername());
        this.setPassword(params.getPassword());
        this.setJenkins_url(params.getJenkins_url());
        this.setToken(params.getToken());
    }
    
    public Params getParams() {
    	Params params = new Params();
        params.setId(this.getId());
        params.setUsername(this.getUsername());
        params.setPassword(this.getPassword());
        params.setJenkins_url(this.getJenkins_url());
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

    @XmlElement(name = "username", required = true)
    public String getUsername() {
        return _username;
    }
    
    public void setUsername(String username) {
        this._username = username;
    }

    @XmlElement(name = "password", required = true)
    public String getPassword() {
        return _password;
    }
    
    public void setPassword(String password) {
        this._password = password;
    }

	@XmlElement(name = "jenkins_url", required = true)
	public String getJenkins_url() {
		return _jenkins_url;
	}
	public void setJenkins_url(String jenkins_url) {
		this._jenkins_url = jenkins_url;
	}

	@XmlElement(name = "token", required = true)
	public String getToken() {
		return _token;
	}
	public void setToken(String token) {
		this._token = token;
	}


	private int _id;
	private String _username;
	private String _password;
	private String _jenkins_url;
	private String _token;

}
