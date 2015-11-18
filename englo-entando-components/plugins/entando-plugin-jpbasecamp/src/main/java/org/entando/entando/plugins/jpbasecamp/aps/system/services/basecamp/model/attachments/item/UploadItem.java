package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class describes a newly created attachment which is going to become an upload
 * @author entando
 *
 */
public class UploadItem {
	
	public UploadItem(String token, String content, String name) {
		this._token = token;
		this._content = content;
		this._name = name;
	}
	
	public UploadItem(String token, String content, String name, List<String> subscribers) {
		this._token = token;
		this._content = content;
		this._name = name;
		this._subscribers = subscribers;
	}
	
	public String toJSON() {
		JSONObject json = new JSONObject();
		JSONArray jattacharray = new JSONArray();
		JSONArray jsubarray = new JSONArray();
		JSONObject jat = new JSONObject();
		
		jat.put(TOKEN, _token);
		jat.put(NAME, _name);
		jattacharray.put(jat);
		
		for (String subcriber: _subscribers) {
			jsubarray.put(subcriber);
		}
		
		json.put(CONTENT, _content);
		json.put(ATTACHMENTS, jattacharray);
		json.put(SUBSCRIBERS, jsubarray);
		if (null != _isPrivate) {
			json.put(PRIVATE, _isPrivate);
		}
		return json.toString();
	}
	
	public String getContent() {
		return _content;
	}
	protected void setContent(String content) {
		this._content = content;
	}
	public String getToken() {
		return _token;
	}
	public void setToken(String token) {
		this._token = token;
	}
	public String getName() {
		return _name;
	}
	protected void setName(String name) {
		this._name = name;
	}
	public List<String> getSubscribers() {
		return _subscribers;
	}
	public void setSubscribers(List<String> subscribers) {
		this._subscribers = subscribers;
	}
	public boolean isPrivate() {
		return _isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this._isPrivate = isPrivate;
	}

	private String _content;
	private String _token;
	private String _name;
	private List<String> _subscribers = new ArrayList<String>();
	private Boolean _isPrivate;
	
	public final static String CONTENT = "content";
	public final static String ATTACHMENTS = "attachments";
	public final static String TOKEN = "token";
	public final static String NAME = "name";
	public final static String SUBSCRIBERS = "subscribers";
	public final static String PRIVATE = "private";
}
