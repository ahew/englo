package org.entando.entando.plugins.jpjenkins.aps.system.services.params;

public class Params {

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    public String getUsername() {
        return _username;
    }
    public void setUsername(String username) {
        this._username = username;
    }

    public String getPassword() {
        return _password;
    }
    public void setPassword(String password) {
        this._password = password;
    }

    public String getJenkins_url() {
        return _jenkins_url;
    }
    public void setJenkins_url(String jenkins_url) {
        this._jenkins_url = jenkins_url;
    }

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
