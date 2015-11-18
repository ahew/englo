package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.PersonReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;

public class PeopleManager extends AbstractService implements IPeopleManager {

Logger _logger = LoggerFactory.getLogger(TodolistManager.class);
	
	@Override
	public void init() throws Exception {
		_logger.info(this.getClass().getCanonicalName() + " initialized");
	}
	
	@Override
	public List<PersonReference> getUsers(BasecampService serviceData) throws ApsSystemException {
		List<PersonReference> list = new ArrayList<PersonReference>();
		HttpGet httpGet = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String atok = "Bearer ".concat(serviceData.getAccessToken());
		String url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
		url = url.concat(PEOPLE_API_URL);
		
		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", atok);
			
			_logger.debug("Querying '{}' for people", url);
			HttpResponse response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				_logger.debug("status OK");
				InputStream ris = response.getEntity().getContent();
				String body = IOUtils.toString(ris, "UTF-8");
				JSONArray jar = new JSONArray(body);
				
				for (int i = 0; i < jar.length(); i++) {
					JSONObject jper = jar.getJSONObject(i);
					PersonReference person = new PersonReference(jper);
					
					list.add(person);
				}
			} else {
				_logger.error("server reported error status  '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while getting people list\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error getting people list", t);
		} finally {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
		}
		return list;
	}
	
	@Override
	public Person getPerson(PersonReference reference, BasecampService serviceData) throws ApsSystemException {
		Person person = null;
		HttpGet httpGet = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String atok = "Bearer ".concat(serviceData.getAccessToken());
		String url = reference.getUrl();
		
		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", atok);
			
			_logger.debug("Querying '{}' for person", url);
			HttpResponse response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				_logger.debug("status OK");
				InputStream ris = response.getEntity().getContent();
				String body = IOUtils.toString(ris, "UTF-8");
				person = new Person(body);
				person.setPersonReference(reference);
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while querying projects\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error loading person detail", t);
		} finally {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
		}
		return person;
	}
	
	@Override
	public Person whoAmI(BasecampService serviceData) throws ApsSystemException {
		Person person = null;
		HttpGet httpGet = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String atok = "Bearer ".concat(serviceData.getAccessToken());
		String url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
		url = url.concat(PEOPLE_WHOAMI_URL);
		
		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", atok);

			_logger.debug("Querying '{}' for current user identity", url);
			HttpResponse response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				_logger.debug("status OK");
				InputStream ris = response.getEntity().getContent();
				String body = IOUtils.toString(ris, "UTF-8");
				person = new Person(body);
				
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while getting current user identity\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error getting currently logged user data", t);
		} finally {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
		}
		return person;
	}
	
	private void deletePerson(String url, BasecampService serviceData) throws ApsSystemException {
		HttpDelete httpDelete = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		try {
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			httpDelete = new HttpDelete(url);
			httpDelete.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpDelete.setHeader("Content-Type", "application/json");
			httpDelete.setHeader("Authorization", atok);
			
			_logger.debug("Querying '{}' to delete user", url.toString());
			HttpResponse response = httpclient.execute(httpDelete);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT
//					|| response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
					) {
				_logger.debug("status OK");
				
			}
			else {
				_logger.error("server reported error status  '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while deleting a project\n");
			}
			
		} catch (Throwable t) {
			throw new ApsSystemException("Error getting deleting user", t);
		} finally {
			if (null != httpDelete) {
				httpDelete.releaseConnection();
			}
		}
	}
	
	@Override
	public void deletePerson(PersonReference reference, BasecampService serviceData) throws ApsSystemException {
		String url = reference.getUrl();
		
		deletePerson(url, serviceData);
	}
	
	@Override
	public void deletePerson(Person person, BasecampService serviceData) throws Throwable {
		PersonReference reference = person.getPersonReference();
		String url = reference.getUrl();
		
		deletePerson(url, serviceData);
	}
	
	public static final String PEOPLE_API_URL = "/people.json";
	public static final String PEOPLE_WHOAMI_URL = "/people/me.json";
}
