/*
* The MIT License
*
* Copyright 2015 Entando Corporation.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package org.entando.entando.plugins.jpjenkins.aps.services.jenkins;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author entando
 */
public class JenkinsConfig {
    
    public JenkinsConfig() {
        // do nothing
    }
    
    public JenkinsConfig(String xml) {
        if (StringUtils.isNotBlank(xml)) {
            JSONObject json = XML.toJSONObject(xml);
            json = json.getJSONObject(CONFIG_ROOT);
            
            if (json.has(CONFIG_USERNAME)) {
                _username = json.getString(CONFIG_USERNAME);
            }
            if (json.has(CONFIG_PASSWORD)) {
                _password = json.getString(CONFIG_PASSWORD);
            }
            if (json.has(CONFIG_URL)) {
                _url = json.getString(CONFIG_URL);
            }
            if (json.has(CONFIG_TOKEN)) {
                _token = json.getString(CONFIG_TOKEN);
            }
        }
    }
    
    public String toXml() throws Throwable {
        JSONObject json = new JSONObject();
        
        json.put(CONFIG_USERNAME, _username);
        json.put(CONFIG_PASSWORD, _password);
        json.put(CONFIG_URL, _url);
        json.put(CONFIG_TOKEN, _token);
        return XML.toString(json, CONFIG_ROOT);
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
    
    public String getUrl() {
        return _url;
    }
    public void setUrl(String url) {
        this._url = url;
    }
    
    public String getToken() {
        return _token;
    }
    public void setToken(String token) {
        this._token = token;
    }
    
    
    private String _username;
    private String _password;
    private String _url;
    private String _token;
    
    public final static String CONFIG_ROOT = "config";
    public final static String CONFIG_USERNAME = "username";
    public final static String CONFIG_PASSWORD = "password";
    public final static String CONFIG_URL = "url";
    public final static String CONFIG_TOKEN = "token";
    
}
