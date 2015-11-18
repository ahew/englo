package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.Document;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.DocumentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;

public class DocumentManager extends AbstractService implements IDocumentManager {

	Logger _logger = LoggerFactory.getLogger(DocumentManager.class);
	
	@Override
	public void init() throws Exception {
		_logger.info(this.getClass().getCanonicalName() + " initialized");
	}
	
	@Override
	public List<DocumentReference> getProjectDocuments(Project project, BasecampService serviceData) throws Throwable {
		List<DocumentReference> list = new ArrayList<DocumentReference>();
		HttpGet httpGet = null;
		String atok = "Bearer ".concat(serviceData.getAccessToken());
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String url = project.getDocuments().getUrl();
		
		try {
			httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", atok);
			
			_logger.debug("Querying '{}' for projects", url);
			HttpResponse response = httpclient.execute(httpGet);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream ris = response.getEntity().getContent();
				String body = IOUtils.toString(ris, "UTF-8");	
				JSONArray jrec = new JSONArray(body);
				
				for (int i = 0; i < jrec.length(); i++) {
					JSONObject jcur = jrec.getJSONObject(i);
					DocumentReference document = new DocumentReference(jcur);
					document.setProject(project);
					list.add(document);
				}
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while getting document list\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error while getting document list", t);
		} finally {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
		}
		return list;
	}
	
	@Override
	public Document getDocument(DocumentReference reference, BasecampService serviceData) throws Throwable {
		Document document = null;
		HttpGet httpGet = null;
		
		try {
			String url = reference.getUrl();
			Project project = reference.getProject();
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setHeader("Authorization", atok);

			_logger.debug("Querying '{}' for document", url);
			HttpResponse response = httpclient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				_logger.debug("status OK");
				InputStream ris = response.getEntity().getContent();
				String body = IOUtils.toString(ris, "UTF-8");
				document = new Document(body);
				document.setProject(project);
				document.setLocation(url);
//			} else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
//				_logger.error("server reported error status 'Document not found'");
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while loading a document");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("HTTP status error while loading a document", t);
		} finally {
			if (null != httpGet) {
				httpGet.releaseConnection();
			}
		}
		return document;
	}
	
	@Override
	public Document createDocument(Document document, Project project, BasecampService serviceData) throws Throwable {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = null;
		String locationHeader = null;
		
		try {
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			String url = project.getDocuments().getUrl();
			httpPost = new HttpPost(url);
			httpPost.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpPost.setHeader("Authorization", atok);
			StringEntity jsonEntity = new StringEntity(document.toJSON());
			jsonEntity.setContentType("application/json");
			httpPost.setEntity(jsonEntity);
			HttpResponse response = httpclient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
				Header[] location = response.getHeaders("Location");
				
				if (null != location
						&& location.length > 0) {
					locationHeader = location[0].getValue();
					_logger.debug("location for the newly created document: '{}'", locationHeader);
				}
				document = new Document(body);
				document.setProject(project);
				document.setLocation(locationHeader);
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while creating a new document\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error creating new document", t);
		} finally {
			if (null != httpPost) {
				httpPost.releaseConnection();
			}
		}
		return document;
	}
	
	@Override
	public void deleteDocument(Document document, BasecampService serviceData) throws Throwable {
		HttpDelete httpDelete = null;
		String url = null;

		try {
			url = document.getLocation();
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpDelete = new HttpDelete(url.toString());
			httpDelete.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpDelete.setHeader("Content-Type", "application/json");
			httpDelete.setHeader("Authorization", atok);

			_logger.debug("Querying '{}' for projects", url);
			HttpResponse response = httpclient.execute(httpDelete);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
				_logger.debug("status OK");
			}	else {
				_logger.error("server reported error status  '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error while deleting a document\n");
			}

		} catch (Throwable t) {
			throw new ApsSystemException("Error deleting document", t);
		} finally {
			if (null != httpDelete) {
				httpDelete.releaseConnection();
			}
		}
	}
	
	public Document updateDocument(Document document, BasecampService serviceData) throws Throwable {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPut httpPut = null;
		String url = null;
		
		try {
			Project project = document.getProject();
			url = document.getLocation();
			
			// prepare PUT
			String atok = "Bearer ".concat(serviceData.getAccessToken());
			httpPut = new HttpPut(url.toString());
			httpPut.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
			httpPut.setHeader("Authorization", atok);
			StringEntity jsonEntity = new StringEntity(document.toJSON());
			jsonEntity.setContentType("application/json");
			httpPut.setEntity(jsonEntity);
			HttpResponse response = httpclient.execute(httpPut);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
				document = new Document(body);
				document.setProject(project);
				document.setLocation(url);
			} else {
				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
				throw new ApsSystemException("HTTP status error updating document\n");
			}
		} catch (Throwable t) {
			throw new ApsSystemException("Error creating a new project", t);
		} finally {
			if (null != httpPut) {
				httpPut.releaseConnection();
			}
		}
		return document;
	}
	
	
	private static String DOCUMENT_BASE_URL = "/documents/";
}
