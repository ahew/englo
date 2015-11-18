package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.Attachment;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.UploadItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import java.io.File;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TodolistManager.TODOLISTS_QUERY_URL;

public class AttachmentManager extends AbstractService implements IAttachmentManager {
    
    Logger _logger = LoggerFactory.getLogger(AttachmentManager.class);
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass().getCanonicalName() + " initialized");
    }
    
    @Override
    public List<AttachmentReference> getProjectAttachments(Project project, BasecampService serviceData) throws Throwable {
        List<AttachmentReference> list = new ArrayList<AttachmentReference>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = project.getAttachments().getUrl();
        HttpGet httpGet = null;
        
        try {
            httpGet = new HttpGet(url);
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            _logger.debug("Querying '{}' for projects", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONArray jrec = new JSONArray(body);
                
                for (int i = 0; i < jrec.length(); i++) {
                    JSONObject jcur = jrec.getJSONObject(i);
                    AttachmentReference attachment = new AttachmentReference(jcur);
                    attachment.setProject(project);
                    list.add(attachment);
                }
            }  else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                _logger.info("no attachments for the current project");
            } else {
                _logger.error("server reported error status  '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting attachment list\n");
            }
            
        } catch (Throwable t) {
            _logger.error("Error getting documents " + t.getMessage() + "\n");
            throw new ApsSystemException("Error while getting attachment list\n");
        }
        return list;
    }
    
    @Override
    public UploadItem createAttachment(String name, InputStream stream, String text, BasecampService serviceData) throws Throwable {
        String url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        UploadItem uploadItem = null;
        HttpPost httpPost = null;
        InputStream ris = null;
        
        try {
            url = url.concat(ATTCHMENTS_NEW);
            httpPost = new HttpPost(url);
            BasecampUrlHelper.setUpConnection(serviceData, httpPost, localContext);
            // must remove default content type header "application/json"
            httpPost.removeHeaders("Content-Type");
            
            InputStreamEntity requestEntity = new InputStreamEntity(
                    stream,
                    -1,
                    ContentType.APPLICATION_OCTET_STREAM
            );
            requestEntity.setChunked(true);
            httpPost.setEntity(requestEntity);
            _logger.debug("Querying '{}' for attachment creation", httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost, localContext);
            HttpEntity responseEntity = response.getEntity();
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ris = responseEntity.getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONObject json = new JSONObject(body);
                String token = json.getString(TOKEN);
                uploadItem = new UploadItem(token, text, name);
                
                _logger.info("Token for the newly created resource " + token);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while creating attachment\n");
            }
            EntityUtils.consume(responseEntity);
            httpPost.releaseConnection();
        } catch (Throwable t) {
            throw new ApsSystemException("Error creating attachment", t);
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
            if (null != ris) {
                ris.close();
            }
        }
        
        return uploadItem;
    }   
    
    @Override
    public void createUpload(Project project, UploadItem item, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpPost httpPost =  null;
        
        try {
            String json = item.toJSON();
            String url = project.getLocation();
            url = url.replace(".json", UPLOAD_BASE_URL);
            httpPost = new HttpPost(url);
            StringEntity jsonEntity = new StringEntity(json);
            httpPost.setEntity(jsonEntity);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpPost, localContext);
            _logger.debug("Querying '{}' for upload creation", httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost, localContext);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                // String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while creating a new document\n");
            }
        } catch (Throwable t) {
            _logger.error("Error uploading a document association");
            throw new ApsSystemException("Error creating upload", t);
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
    }
    
    @Override
    public String downaloadAttachment(AttachmentReference reference, String path, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpGet httpGet = null;
        InputStream is = null;
        
        try {
            String url = reference.getUrl();
            httpGet = new HttpGet(url);
            // evaluat destination path
            if (!path.endsWith(File.separator)) {
                path = path.concat(File.separator);
            }
            path = path.concat(reference.getName());
            _logger.info("Downloading resource in '{}'", path);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            _logger.debug("Querying '{}' for attachment", url);
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                is = response.getEntity().getContent();
                byte[] buffer = new byte[1024];
                
                FileOutputStream output = new FileOutputStream(path);
                for (int length; (length = is.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
                try {
                    output.close();
                } catch (Throwable t) {
                    _logger.error("Error closing the the file stream", t);
                }
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while downloading attachment");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error detected while downloading attachment", t);
        } finally {
            if (null != httpGet) {
                try {
                    httpGet.releaseConnection();
                } catch (Throwable t) {
                    _logger.error("Error releasing connection", t);
                };
            }
            if (null != is) {
                try {
                    is.close();
                } catch (Throwable t) {
                    _logger.error("Error closing stream", t);
                };
            }
        }
        return path;
    }
    
    @Override
    public void deleteAttachment(AttachmentReference reference, BasecampService serviceData) throws Throwable {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpDelete httpDelete = new HttpDelete();
        InputStream is = null;
        
        try {
            Long projectId = BasecampUrlHelper.getProjectIdFromUrl(reference.getUrl());
            
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpDelete, projectId);
            String append = "/attachments/" + reference.getId() + ".json";
            BasecampUrlHelper.appendToUri(httpDelete, append);
            // evaluat destination path                        
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            _logger.debug("Querying '{}' for deletig attachment", httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                // do nothing
            } else {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                System.out.println("Errore\n"+ body);
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while deleting attachment");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error detected while deleting attachment", t);
        } finally {
            try {
                httpDelete.releaseConnection();
            } catch (Throwable t) {
                _logger.error("Error releasing connection", t);
            };
            if (null != is) {
                try {
                    is.close();
                } catch (Throwable t) {
                    _logger.error("Error closing stream", t);
                };
            }
        }
    }
    
    @Override
    public Attachment getAttachment(AttachmentReference reference, BasecampService serviceData) throws Throwable {
        Attachment attachment = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = null;
        
        try {
            Project project = reference.getProject();
            String atok = "Bearer ".concat(serviceData.getAccessToken());
            String url = project.getLocation().replace(".json", ATTACHMENT_BASE_URL);
            url = url.concat(String.valueOf(reference.getId()));
            url = url.concat(".json");
            
            httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Authorization", atok);
            
            _logger.debug("Querying '{}' for projects", url);
            HttpResponse response = httpclient.execute(httpGet);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                _logger.debug("status OK");
                
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                attachment = new Attachment(body);
                attachment.setProject(project);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting attachment metadata\n");
            }
            
        } catch (Throwable t) {
            throw new ApsSystemException("Error detected while getting attachment metadata", t);
        } finally {
            if (null != httpGet) {
                httpGet.releaseConnection();
            }
        }
        return attachment;
    }
    
    public static final String ATTACHMENT_BASE_URL = "/attachments/";
    public static final String ATTCHMENTS_NEW = "/attachments.json";
    public static final String UPLOAD_BASE_URL = "/uploads.json";
    
    public static final String TOKEN = "token";
    
}
