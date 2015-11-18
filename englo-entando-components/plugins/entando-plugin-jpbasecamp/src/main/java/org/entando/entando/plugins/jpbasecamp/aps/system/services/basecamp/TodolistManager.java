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
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.AssignedTodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssignedTodoItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TodolistManager extends AbstractService implements ITodolistManager {
    
    Logger _logger = LoggerFactory.getLogger(TodolistManager.class);
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass().getCanonicalName() + " initialized");
    }
    
    @Override
    public List<TodolistReference> getTodolists(Project project, BasecampService serviceData) throws ApsSystemException {
        return getTodolists(project.getId(), serviceData);
    }
    
    @Override
    public List<TodolistReference> getTodolists(long projectId, BasecampService serviceData) throws ApsSystemException {
        List<TodolistReference> list = new ArrayList<TodolistReference>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpGet httpGet = new HttpGet();
        
        try {
            
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpGet, projectId);
            BasecampUrlHelper.appendToUri(httpGet, TODOLISTS_QUERY_URL);
            
            _logger.info("Querying '{}' for projects", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONArray jrec = new JSONArray(body);
                
                for (int i = 0; i < jrec.length(); i++) {
                    JSONObject jcur = jrec.getJSONObject(i);
                    TodolistReference todolist = new TodolistReference(jcur);
                    todolist.setProjectId(projectId);
                    list.add(todolist);
                }
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                _logger.info("no todolist for the current project");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todolist");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while getting todolist", t);
        } finally {
            httpGet.releaseConnection();
        }
        return list;
    }
    
    // FIXME untested
    @Override
    public List<TodolistReference> getCompletedTodolists(Project project, BasecampService serviceData) throws ApsSystemException {
        List<TodolistReference> list = new ArrayList<TodolistReference>();
//        String atok = "Bearer ".concat(serviceData.getAccessToken());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = project.getTodolists().getUrl();
        url = url.replace(".json", "/completed.json");
        HttpGet httpGet = null;
        
        try {
            httpGet = new HttpGet(url);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            
//            httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
//            httpGet.setHeader("Content-Type", "application/json");
//            httpGet.setHeader("Authorization", atok);
            
            _logger.debug("Querying '{}' for todolist", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONArray jrec = new JSONArray(body);
                
                for (int i = 0; i < jrec.length(); i++) {
                    JSONObject jcur = jrec.getJSONObject(i);
                    TodolistReference todolist = new TodolistReference(jcur);
                    todolist.setProjectId(project.getId());
                    list.add(todolist);
                }
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                _logger.info("no completed todolists for the current project");
            }  else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todo list");
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
    
    // FIXME untested
    @Override
    public List<AssignedTodolistReference> getAssignedTodolists(Project project, Person person, BasecampService serviceData) throws ApsSystemException {
        List<AssignedTodolistReference> list = new ArrayList<AssignedTodolistReference>();
        HttpGet httpGet = null;
//        String atok = "Bearer ".concat(serviceData.getAccessToken());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
        
        url = url.concat("/people/");
        url = url.concat(String.valueOf(person.getId()));
        url = url.concat("/assigned_todos.json");
        
        try {
            httpGet = new HttpGet(url);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            
//            httpGet.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
//            httpGet.setHeader("Content-Type", "application/json");
//            httpGet.setHeader("Authorization", atok);
            
            _logger.debug("Querying '{}' to get assigned todolists", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                JSONArray jrec = new JSONArray(body);
                
                for (int i = 0; i < jrec.length(); i++) {
                    JSONObject jcur = jrec.getJSONObject(i);
                    AssignedTodolistReference assignedTodoList = new AssignedTodolistReference(jcur);
                    assignedTodoList.setProjectId(project.getId());
                    list.add(assignedTodoList);
                }
            } else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                _logger.info("no assigned todolists for the current project");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todo list");
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
    @Deprecated
    public Todolist getTodolist(TodolistReference reference, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        Todolist todolist = null;
        String url = reference.getUrl();
        HttpGet httpGet = new HttpGet(url);
        
        try {
            Long projectId = BasecampUrlHelper.getProjectIdFromUrl(url);
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            
            _logger.debug("Querying '{}' to load todolist", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                JSONObject json = new JSONObject(body);
                todolist = new Todolist(json);
                todolist.setLocation(reference.getUrl());
                todolist.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todolist detail");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while getting todolist detail", t);
        } finally {
            httpGet.releaseConnection();
        }
        return todolist;
    }
    
    @Override
    public Todolist getTodolist(long projectId, long todolistId, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        Todolist todolist = null;
        HttpGet httpGet = new HttpGet();
        
        try {
            StringBuilder url = new StringBuilder(TODOLISTS_BASE_URL);
            url.append(todolistId);
            url.append(".json");
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpGet, projectId);
            BasecampUrlHelper.appendToUri(httpGet, url.toString());
            
            _logger.debug("Querying '{}' to load todolist", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                JSONObject json = new JSONObject(body);
                todolist = new Todolist(json);
                todolist.setLocation(httpGet.getRequestLine().getUri());
                todolist.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todolist detail");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while getting todolist detail", t);
        } finally {
            httpGet.releaseConnection();
        }
        return todolist;
    }
    
    @Override
    public Todolist createTodolist(Todolist todolist, Project project, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = project.getTodolists().getUrl();
        HttpPost httpPost = new HttpPost(url);
        String locationHeader = null;
        Long projectId = null;
        
        try {
            StringEntity jsonEntity = new StringEntity(todolist.toJSON());
            httpPost.setEntity(jsonEntity);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpPost, localContext);
            
            _logger.debug("Querying '{}' to creare todolist", httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                Header[] location = response.getHeaders("Location");
                
                if (null != location && location.length > 0) {
                    locationHeader = location[0].getValue();
                    _logger.debug("location for the newly created todolist: '{}'", locationHeader);
                }
                todolist = new Todolist(body);
                todolist.setLocation(locationHeader);
                projectId = BasecampUrlHelper.getProjectIdFromUrl(locationHeader);
                todolist.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while creating a new document\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error creating new todolist");
        } finally {
            httpPost.releaseConnection();
        }
        return todolist;
    }
    
    @Override
    public Todolist updateTodolist(Todolist todolist, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = todolist.getLocation();
        HttpPut httpPut = new HttpPut(url);
        Long projectId = null;
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpPut, localContext);
            
            StringEntity jsonEntity = new StringEntity(todolist.toJSON());
//			jsonEntity.setContentType("application/json");
            httpPut.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' to update todolist", httpPut.getRequestLine());
            HttpResponse response = httpclient.execute(httpPut, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                todolist = new Todolist(body);
                todolist.setLocation(url);
                projectId = BasecampUrlHelper.getProjectIdFromUrl(httpPut.getRequestLine().getUri());
                todolist.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error updating todolist\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while updating todolist detail", t);
        } finally {
            httpPut.releaseConnection();
        }
        return todolist;
    }
    
    /*
    public Todolist updateTodolist(Todolist todolist, Long projectId, BasecampService serviceData) throws ApsSystemException {
    DefaultHttpClient httpclient = new DefaultHttpClient();
    HttpPut httpPut = new HttpPut();
    
    try {
    // if the projectId is null then retrieve it from the object itself.
    if (null == projectId) {
    projectId = todolist.getProjectId();
    }
    StringBuilder url = new StringBuilder(TODOLISTS_BASE_URL);
    url.append(todolist.getId());
    url.append(".json");
    BasecampUrlHelper.setUpConnection(serviceData, httpPut, httpclient);
    BasecampUrlHelper.appendProjectBaseToUri(httpPut, projectId);
    BasecampUrlHelper.appendToUri(httpPut, url.toString());
    
    StringEntity jsonEntity = new StringEntity(todolist.toJSON());
    //			jsonEntity.setContentType("application/json");
    httpPut.setEntity(jsonEntity);
    
    _logger.debug("Querying '{}' to update todolist", httpPut.getRequestLine());
    HttpResponse response = httpclient.execute(httpPut);
    
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
    String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
    todolist = new Todolist(body);
    todolist.setLocation(httpPut.getRequestLine().getUri());
    projectId = BasecampUrlHelper.getProjectIdFromUrl(httpPut.getRequestLine().getUri());
    todolist.setProjectId(projectId);
    } else {
    _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
    throw new ApsSystemException("HTTP status error updating todolist\n");
    }
    } catch (Throwable t) {
    throw new ApsSystemException("Error while updating todolist detail", t);
    } finally {
    httpPut.releaseConnection();
    }
    return todolist;
    }
    */
    
    @Override
    public void deleteTodolist(Todolist todolist, BasecampService serviceData) throws ApsSystemException {
        String url = todolist.getLocation();
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            _logger.debug("Querying '{}' to delete todolist",
                    httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT
                    || response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // BUG?!?!?!
                _logger.debug("status OK");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while deleting a todolist\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while deleting todolist", t);
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Override
    public void deleteTodolist(long projectId, long todolistId, BasecampService serviceData) throws ApsSystemException {
        HttpDelete httpDelete = new HttpDelete();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = "/todolists/".concat(String.valueOf(todolistId)).concat(".json");
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpDelete, projectId);
            BasecampUrlHelper.appendToUri(httpDelete, url);
            
            _logger.debug("Querying '{}' to delete todolist", httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT
                    || response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // BUG?!?!?!
                _logger.debug("status OK");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException(
                        "HTTP status error while deleting a todolist\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while deleting todolist", t);
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Override
    public Todo getTodo(AssignedTodoItem reference, BasecampService serviceData) throws ApsSystemException {
        Todo todo = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = reference.getUrl();
        HttpGet httpGet = new HttpGet(url);
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            
            _logger.debug("Querying '{}' for Todo details", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                JSONObject json = new JSONObject(body);
                todo = new Todo(json);
                todo.setLocation(reference.getUrl());
                Long projectId = BasecampUrlHelper.getProjectIdFromUrl(url);
                todo.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todo detail");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while getting todo detail", t);
        } finally {
            httpGet.releaseConnection();
        }
        return todo;
    }
    
    @Override
    public Todo getTodo(long projectId, long todoId, BasecampService serviceData) throws ApsSystemException {
        Todo todo = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpGet httpGet = new HttpGet();
        
        try {
            StringBuilder url = new StringBuilder(TODOS_BASE_URL);
            url.append(todoId);
            url.append(".json");
            BasecampUrlHelper.setUpConnection(serviceData, httpGet, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpGet, projectId);
            BasecampUrlHelper.appendToUri(httpGet, url.toString());
            
            _logger.debug("Querying '{}' to load Todo details", httpGet.getRequestLine());
            HttpResponse response = httpclient.execute(httpGet, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream ris = response.getEntity().getContent();
                String body = IOUtils.toString(ris, "UTF-8");
                
                JSONObject json = new JSONObject(body);
                todo = new Todo(json);
                todo.setLocation(httpGet.getRequestLine().getUri());
                
                todo.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while getting todo detail");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while getting todo detail", t);
        } finally {
            httpGet.releaseConnection();
        }
        return todo;
    }
    
    @Override
    public Todo createTodo(Todo todo, Person person, Todolist todolist, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = todolist.getUrl();
        url = url.replace(".json", TODOS_QUERY_URL);
        String locationHeader = null;
        HttpPost httpPost = new HttpPost(url);
        
        try {
            String json = todo.toJSON(person);
            BasecampUrlHelper.setUpConnection(serviceData, httpPost, localContext);
            StringEntity jsonEntity = new StringEntity(json);
//			jsonEntity.setContentType("application/json");
            httpPost.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' to creare todo", httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                Header[] location = response.getHeaders("Location");
                
                if (null != location && location.length > 0) {
                    locationHeader = location[0].getValue();
                    _logger.debug("location for the newly created todo: '{}'", locationHeader);
                }
                todo = new Todo(body);
                todo.setLocation(locationHeader);
                Long projectId = BasecampUrlHelper.getProjectIdFromUrl(locationHeader);
                todo.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while creating a new Todo\n");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ApsSystemException("Error creating new todolist", t);
        } finally {
            httpPost.releaseConnection();
        }
        return todo;
    }
    
    
    @Override
    @Deprecated
    public void deleteTodo(Todo todo, BasecampService serviceData) throws ApsSystemException {
        HttpDelete httpDelete = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        
        try {
            String url = todo.getLocation();
            String atok = "Bearer ".concat(serviceData.getAccessToken());
            httpDelete = new HttpDelete(url.toString());
//            httpDelete.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
//            httpDelete.setHeader("Content-Type", "application/json");
//            httpDelete.setHeader("Authorization", atok);
            
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            
            _logger.debug("Querying '{}' to delete todolist", httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                _logger.debug("status OK");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while deleting a todo\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while deleting a todo", t);
        } finally {
            if (null != httpDelete) {
                httpDelete.releaseConnection();
            }
        }
    }
    
    @Override
    @Deprecated
    public void deleteTodo(Todo todo, Long projectId, BasecampService serviceData) throws ApsSystemException {
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpDelete httpDelete = new HttpDelete();
//
//		try {
//			StringBuilder url = new StringBuilder(TODOS_BASE_URL);
//			url.append(todo.getId());
//			url.append(".json");
//			// if the projectId is null then retrieve it from the object itself.
//			if (null == projectId) {
//				projectId = todo.getProjectId();
//			}
//			BasecampUrlHelper.setUpConnection(serviceData, httpDelete, httpclient);
//			BasecampUrlHelper.appendProjectBaseToUri(httpDelete, projectId);
//			BasecampUrlHelper.appendToUri(httpDelete, url.toString());
//
//			_logger.debug("Querying '{}' to delete todolist", httpDelete.getRequestLine());
//			HttpResponse response = httpclient.execute(httpDelete);
//
//			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
//				_logger.debug("status OK");
//			} else {
//				_logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
//				throw new ApsSystemException("HTTP status error while deleting a todo\n");
//			}
//		} catch (Throwable t) {
//			throw new ApsSystemException("Error while deleting a todo", t);
//		} finally {
//			httpDelete.releaseConnection();
//		}
        deleteTodo(todo.getId(), projectId, serviceData);
    }
    
    @Override
    public void deleteTodo(long todoId, long projectId, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpDelete httpDelete = new HttpDelete();
        
        try {
            StringBuilder url = new StringBuilder(TODOS_BASE_URL);
            url.append(todoId);
            url.append(".json");
            BasecampUrlHelper.setUpConnection(serviceData, httpDelete, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpDelete, projectId);
            BasecampUrlHelper.appendToUri(httpDelete, url.toString());
            
            _logger.debug("Querying '{}' to delete todolist", httpDelete.getRequestLine());
            HttpResponse response = httpclient.execute(httpDelete, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                _logger.debug("status OK");
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error while deleting a todo\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while deleting a todo", t);
        } finally {
            httpDelete.releaseConnection();
        }
    }
    
    @Deprecated
    @Override
    public Todo updateTodo(Todo todo, Todolist todolist, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        String url = todo.getUrl();
        HttpPut httpPut = new HttpPut(url);
        
        try {
            BasecampUrlHelper.setUpConnection(serviceData, httpPut, localContext);
            
            StringEntity jsonEntity = new StringEntity(todo.toJSON());
// jsonEntity.setContentType("application/json");
            httpPut.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' to update todolist", httpPut.getRequestLine());
            HttpResponse response = httpclient.execute(httpPut, localContext);
            String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                
                todo = new Todo(body);
                Long projectId = BasecampUrlHelper.getProjectIdFromUrl(httpPut.getRequestLine().getUri());
                todo.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error updating todolist\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while updating todolist detail", t);
        } finally {
            httpPut.releaseConnection();
        }
        return todo;
    }
    
    @Override
    public Todo updateTodo(Todo todo, Long projectId, BasecampService serviceData) throws ApsSystemException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext localContext = HttpClientContext.create();
        HttpPut httpPut = new HttpPut();
        StringBuilder url = new StringBuilder(TODOS_BASE_URL);
        url.append(todo.getId());
        url.append(".json");
        
        try {
            // if the projectId is null then retrieve it from the object itself.
            if (null == projectId) {
                projectId = todo.getProjectId();
            }
            BasecampUrlHelper.setUpConnection(serviceData, httpPut, localContext);
            BasecampUrlHelper.appendProjectBaseToUri(httpPut, projectId);
            BasecampUrlHelper.appendToUri(httpPut, url.toString());
            
            StringEntity jsonEntity = new StringEntity(todo.toJSON());
            httpPut.setEntity(jsonEntity);
            
            _logger.debug("Querying '{}' to update todolist", httpPut.getRequestLine());
            HttpResponse response = httpclient.execute(httpPut, localContext);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String body = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                
                todo = new Todo(body);
                todo.setProjectId(projectId);
            } else {
                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
                throw new ApsSystemException("HTTP status error updating todolist\n");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error while updating todolist detail", t);
        } finally {
            httpPut.releaseConnection();
        }
        return todo;
    }
    
    public ITodolistManager getTodolistManager() {
        return _todolistManager;
    }
    
    public void setTodolistManager(ITodolistManager todolistManager) {
        this._todolistManager = todolistManager;
    }
    
    private ITodolistManager _todolistManager;
    
    public static final String TODOS_QUERY_URL = "/todos.json";
    public static final String TODOLISTS_QUERY_URL = "/todolists.json";
    public static final String TODOLISTS_BASE_URL = "/todolists/";
    public static final String TODOS_BASE_URL = "/todos/";
    
}
