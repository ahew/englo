package org.entando.entando.plugins.jptrello.aps.system.services.params;


public class Params {

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    public String getOrganization() {
        return _organization;
    }
    public void setOrganization(String organization) {
        this._organization = organization;
    }

    public String getApi_key() {
        return _api_key;
    }
    public void setApi_key(String api_key) {
        this._api_key = api_key;
    }

    public String getApi_secret() {
        return _api_secret;
    }
    public void setApi_secret(String api_secret) {
        this._api_secret = api_secret;
    }

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
