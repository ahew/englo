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
package org.entando.entando.plugins.jptrello.aps.system.services.trello;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author entando
 */
public class TrelloConfig {
    
    public TrelloConfig() {
        // do nothing
    }
    
    public TrelloConfig(String xml) {
        if (StringUtils.isNotBlank(xml)) {
            JSONObject json = XML.toJSONObject(xml);
            json = json.getJSONObject(CONFIG_ROOT);
            
            if (json.has(CONFIG_ORGANIZATION)) {
                _organization = json.getString(CONFIG_ORGANIZATION);
            }
            if (json.has(CONFIG_KEY)) {
                _apiKey = json.getString(CONFIG_KEY);
            }
            if (json.has(CONFIG_SECRET)) {
                _apiSecret = json.getString(CONFIG_SECRET);
            }
            if (json.has(CONFIG_TOKEN)) {
                _token = json.getString(CONFIG_TOKEN);
            }
        }
    }
    
    public String toXml() throws Throwable {
        JSONObject json = new JSONObject();
        
        json.put(CONFIG_ORGANIZATION, _organization);
        json.put(CONFIG_KEY, _apiKey);
        json.put(CONFIG_SECRET, _apiSecret);
        json.put(CONFIG_TOKEN, _token);
        return XML.toString(json, CONFIG_ROOT);
    }
    
    public String getOrganization() {
        return _organization;
    }
    public void setOrganization(String organization) {
        this._organization = organization;
    }

    public String getApiKey() {
        return _apiKey;
    }
    public void setApiKey(String apiKey) {
        this._apiKey = apiKey;
    }

    public String getApiSecret() {
        return _apiSecret;
    }
    public void setApiSecret(String apiSecret) {
        this._apiSecret = apiSecret;
    }

    public String getToken() {
        return _token;
    }
    public void setToken(String token) {
        this._token = token;
    }

    public String getIdOrganization() {
        return _idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this._idOrganization = idOrganization;
    }
    

    private String _organization;
    private String _apiKey;
    private String _apiSecret;
    private String _token;
    // THIS IS NOT PERSISTED
    private String _idOrganization;
    
    public final static String CONFIG_ROOT = "config";
    public final static String CONFIG_KEY = "key";
    public final static String CONFIG_SECRET = "secret";
    public final static String CONFIG_TOKEN = "token";
    public final static String CONFIG_ORGANIZATION = "organization";
    
}
