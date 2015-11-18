package org.entando.entando.plugins.jpgithub.aps.system.services.github;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.json.JSONObject;
import org.json.XML;

public class GithubConfig {
    
    public GithubConfig() {
        // do nothing
    }
    
    public GithubConfig(String xml) {
        if (StringUtils.isNotBlank(xml)) {
            JSONObject json = XML.toJSONObject(xml);
            json = json.getJSONObject(CONFIG_ROOT);
            
            _username = json.getString(CONFIG_USERNAME);
            _password = json.getString(CONFIG_PASSWORD);
        }
    }
    
    public String toXml() throws Throwable {
        JSONObject json = new JSONObject();
        
        json.put(CONFIG_USERNAME, _username);
        json.put(CONFIG_PASSWORD, _password);
        return XML.toString(json, CONFIG_ROOT);
    }
    
    /**
     * Return the client for the connection
     * @return
     */
    public GitHubClient getClient() {
        GitHubClient client = new GitHubClient();
        client.setCredentials(_username, _password);
        
        return client;
    }
    
    /**
     * Return true if the current configuration is sufficient to log in
     * @return
     */
    public boolean isValidForMannedLogin() {
        return (StringUtils.isNotBlank(getPassword())
                && StringUtils.isNotBlank(getUsername()));
    }
    
    public String getUsername() {
        return _username;
    }
    public void setUsername(String appId) {
        this._username = appId;
    }
    public String getPassword() {
        return _password;
    }
    public void setPassword(String appSecret) {
        this._password = appSecret;
    }
    
    // Needed for the standard web flow
    private String _username;
    private String _password;
    
    public final static String CONFIG_ROOT = "config";
    public final static String CONFIG_USERNAME = "username";
    public final static String CONFIG_PASSWORD = "password";
    
}

