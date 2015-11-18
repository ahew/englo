package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.InputStream;
import java.net.URI;
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
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ProjectManager extends AbstractService implements IProjectManager {
    
    Logger _logger = LoggerFactory.getLogger(ProjectManager.class);
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass().getCanonicalName() + " initialized");
    }
    
    private List<ProjectReference> getProjects(CloseableHttpClient httpclient, HttpGet httpGet, String apiUrl, HttpClientContext context) throws Throwable {
        List<ProjectReference> list = new ArrayList<ProjectReference>();
        
        URI uri = BasecampUrlHelper.appendToUri(httpGet, apiUrl);
        httpGet.setURI(uri);
        try {
            _logger.debug("Querying '{}' for projects", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, context);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                _logger.debug("status OK");
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONArray jrec = new JSONArray(body);
                
                for (int i = 0; i < jrec.length(); i++) {
                    JSONObject jcur = jrec.getJSONObject(i);
                    ProjectReference project = new ProjectReference(jcur);
                    list.add(project);
                }
            } else {
                _logger.error("server reported error status  '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while querying projects\n");
            }
        } catch (Throwable t) {
            _logger.error("Error querying projects" + t.getMessage() + "\n");
            throw new ApsSystemException("HTTP status error while querying projects\n");
        } finally {
            httpGet.releaseConnection();
        }
        return list;
    }
    
    @Override
    public List<ProjectReference> getArchivedProjects(BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        List<ProjectReference> list = null;
        HttpGet httpGet = new HttpGet();
        
        BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
        list = getProjects(httpclient, httpGet, PROJECTS_ARCHIVED_QUERY_URL, null);
        return list;
    }
    
    @Override
    public List<ProjectReference> getProjects(BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        List<ProjectReference> list = null;
        HttpGet httpGet = new HttpGet();
        
        BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
        list = getProjects(httpclient, httpGet, PROJECTS_QUERY_URL, localContext);
        return list;
    }
    
    @Override
    public Project getProject(ProjectReference reference, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        Project project = null;
        
        
        try {
            String url = reference.getUrl();
            HttpGet httpGet = new HttpGet(url);
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            
            _logger.debug("Querying '{}' for projects", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                _logger.debug("status OK");
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                project = new Project(body);
                project.setLocation(url);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while querying projects\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error loading project", t);
        }
        return project;
    }
    
    @Override
    public Project getProject(long id, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpGet httpGet = new HttpGet();
        Project project = null;
        
        try {
            String sid = String.valueOf(id);
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            BasecampUrlHelper.appendToUri(httpGet, PROJECTS_BASE_URL.concat(sid).concat(".json"));
            
            _logger.debug("Querying '{}' to load a project", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                _logger.debug("status OK");
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                project = new Project(body);
                project.setLocation(httpGet.getRequestLine().getUri());
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                _logger.error("server reported 'not found, {}'", response.getStatusLine().getStatusCode());
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while querying projects\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error loading project", t);
        } finally {
            httpGet.releaseConnection();
        }
        return project;
    }
    
    @Override
    public Project createProject(Project project, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpPost httpPost = new HttpPost();
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpPost, localContext);
            BasecampUrlHelper.appendToUri(httpPost, PROJECTS_QUERY_URL);
            StringEntity jsonEntity = new StringEntity(project.toJSON());
//			jsonEntity.setContentType("application/json");
            httpPost.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' to create a project", httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                Header[] location = response.getHeaders("Location");
                String locationUrl = null;
                
                if (null != location
                        && location.length >= 1) {
                    locationUrl =  location[0].getValue();
                }
                
                project = new Project(body);
                project.setLocation(locationUrl);
            } else {
                _logger.error("unexpected status '{}' for '{}'", response.getStatusLine().getStatusCode(), httpPost.getRequestLine().getUri());
                _logger.error("body of the object '{}'", project.toJSON());
                throw new ApsSystemException("HTTP status error while creating a new project\n");
            }
        } catch (Throwable t) {
            _logger.error("error creating new project");
            throw new ApsSystemException("Error creating a new project", t);
        } finally {
            httpPost.releaseConnection();
        }
        return project;
    }
    
    private void deleteProject(HttpClientContext context, HttpDelete httpDelete) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            _logger.debug("Querying '{}' to delete a project", httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, context);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT ||
                    response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // FIXME BUG?!?!?!
                _logger.debug("status OK");
            }
            else {
                _logger.error("server reported error status '{}' for '{}'", response.getStatusLine().getStatusCode(), httpDelete.getRequestLine().getUri());
                throw new ApsSystemException("HTTP status error while deleting a project\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error deleting project");
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Override
    public void deleteProject(Project project, BasecampService serviceData) throws Throwable {
        HttpClientContext localContext = HttpClientContext.create();
        HttpDelete httpDelete = new HttpDelete(project.getLocation());
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            deleteProject(localContext, httpDelete);
        } catch (Throwable t) {
            throw new ApsSystemException("Error deleting project");
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Override
    public void deleteProject(long id, BasecampService serviceData) throws Throwable {
        HttpClientContext localContext = HttpClientContext.create();
        HttpDelete httpDelete = new HttpDelete();
        
        try {
            String lid = String.valueOf(id);
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            BasecampUrlHelper.appendToUri(httpDelete, PROJECTS_BASE_URL.concat(lid).concat(".json"));
            deleteProject(localContext, httpDelete);
        } catch (Throwable t) {
            throw new ApsSystemException("Error deleting project");
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Override
    public Project updateProject(Project project, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpPut httpPut = new HttpPut();
        
        try {
            String id = String.valueOf(project.getId());
            BasecampUrlHelper.setUpConnection(serviceData, httpPut, localContext);
            BasecampUrlHelper.appendToUri(httpPut, PROJECTS_BASE_URL.concat(id).concat(".json"));
            
            StringEntity jsonEntity = new StringEntity(project.toJSON());
//			jsonEntity.setContentType("application/json");
            httpPut.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' tu update a project", httpPut.getRequestLine());
            HttpResponse response = httpclient.execute(httpPut, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                project = new Project(body);
                project.setLocation(httpPut.getRequestLine().getUri());
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error updating project\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error updating project", t);
        } finally {
            httpPut.releaseConnection();
        }
        return project;
    }
    
    public static final String PROJECTS_QUERY_URL = "/projects.json";
    public static final String PROJECTS_BASE_URL = "/projects/";
    public static final String PROJECTS_ARCHIVED_QUERY_URL = PROJECTS_BASE_URL.concat("archived.json");
}
