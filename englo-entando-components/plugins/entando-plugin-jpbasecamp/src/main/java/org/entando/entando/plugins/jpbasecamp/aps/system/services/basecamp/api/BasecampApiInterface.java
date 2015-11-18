package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.aps.system.services.api.IApiErrorCodes;
import org.entando.entando.aps.system.services.api.model.ApiException;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IPeopleManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ITodolistManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.exception.ApsSystemException;

public class BasecampApiInterface {
    
    private static Logger _logger = LoggerFactory.getLogger(BasecampApiInterface.class);
    
    // Projects - START
    
    /**
     * Load a list of projects; this list might be restricted by a text
     * @param properties
     * @return
     * @throws Throwable
     */
    public List<JAXBProjectReference> getProjectsForApi(Properties properties) throws Throwable {
        List<JAXBProjectReference> jref= new ArrayList<JAXBProjectReference>();
        
        try {
            String text = null;
            List<ProjectReference> references = getProjectManager().getProjects(null);
            if (null != properties
                    && !properties.isEmpty()) {
                text = properties.getProperty(PARAM_TEXT_FILTER);
            }
            for (ProjectReference reference: references) {
                JAXBProjectReference ref = new JAXBProjectReference(reference);
                
                if (StringUtils.isNotBlank(text)) {
                    if (reference.getName().contains(text)
                            || reference.getDescription().contains(text)) {
                        jref.add(ref);
                    }
                } else {
                    jref.add(ref);
                }
            }
        } catch (Throwable t) {
            _logger.error("Error executing search API request");
            throw new ApsSystemException("Error executing the search project API", t);
        }
        return jref;
    }
    
    /**
     * Load a project given its ID
     * @param properties
     * @return
     * @throws Throwable
     */
    public JAXBProject getProjectForApi(Properties properties) throws Throwable {
        JAXBProject jproject = null;
        
        try {
            String sid = properties.getProperty(PARAM_ID);
            long id = Long.valueOf(sid);
            Project project = getProjectManager().getProject(id, null);
            if (null != project) {
                jproject = new JAXBProject(project);
            } else {
                throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Project with id '" + id + "' not found", Response.Status.CONFLICT);
            }
        } catch (Throwable t) {
            _logger.error("Error executing API load project", t);
            throw new ApsSystemException("Error executing the load project API", t);
        }
        return jproject;
    }
    
    /**
     * Add a new project
     * @param jproject
     * @throws Throwable
     */
    public void addProjectForApi(JAXBProject jproject) throws Throwable {
        try {
            Project project = Project.fromJAXBProject(jproject);
            Project updated = getProjectManager().createProject(project, null);
            _logger.info("New project created '{}'", updated.getId());
        } catch (Throwable t) {
            _logger.error("Error executing API create project", t);
            throw new ApsSystemException("Error executing the load project API", t);
        }
    }
    
    /**
     * Update a project given its ID
     * @param jproject
     * @throws Throwable
     */
    public void updateProjectForApi(JAXBProject jproject) throws Throwable {
        try {
            Project project = getProjectManager().getProject(jproject.getId(), null);
            
            _logger.info("Updating project id '{}'", jproject.getId());
            if (null != project) {
                if (StringUtils.isNotBlank(jproject.getName())) {
                    project.setName(jproject.getName());
                }
                if (StringUtils.isNotBlank(jproject.getDescription())) {
                    project.setDescription(jproject.getDescription());
                }
                if (null != jproject.getDraft()) {
                    project.setDraft(jproject.getDraft());
                }
                if (null != jproject.getIsClientProject()) {
                    project.setDraft(jproject.getIsClientProject());
                }
                if (null != jproject.getStarred()) {
                    project.setDraft(jproject.getStarred());
                }
                project = getProjectManager().updateProject(project, null);
            } else {
                throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Project with id '" + jproject.getId() + "' not found", Response.Status.CONFLICT);
            }
        } catch (Throwable t) {
            _logger.error("Error executing API update project", t);
            throw new ApsSystemException("Error executing the update project API", t);
        }
    }
    
    /**
     * Delete a project given its ID
     * @param properties
     * @throws Throwable
     */
    public void deleteProjectForApi(Properties properties) throws Throwable {
        try {
            String sid = properties.getProperty(PARAM_ID);
            long id = Long.valueOf(sid);
            getProjectManager().deleteProject(id, null);
        } catch (Throwable t) {
            _logger.error("Error executing API delete project", t);
            throw new ApsSystemException("Error executing the delete project API", t);
        }
    }
    // Projects - END
    
    // Todolists - START
    
    /**
     * Get the Todolists for the given project; this list might be restricted by a text
     * @param properties
     * @return
     * @throws Throwable
     */
    public List<JAXBTodolistReference> getTodolistsForApi(Properties properties) throws Throwable {
        List<JAXBTodolistReference> jref= new ArrayList<JAXBTodolistReference>();
        Project project = null;
        
        try {
            String pid = null;
            if (null != properties
                    && !properties.isEmpty()) {
                pid = properties.getProperty(PARAM_PROJECT_ID);
                project = getProjectManager().getProject(new Long(pid), null);
            }
            String text = null;
            List<TodolistReference> references = getTodolistManager().getTodolists(project, null);
            if (null != properties
                    && !properties.isEmpty()) {
                text = properties.getProperty(PARAM_TEXT_FILTER);
            }
            for (TodolistReference reference: references) {
                JAXBTodolistReference ref = new JAXBTodolistReference(reference);
                
                if (StringUtils.isNotBlank(text)) {
                    if (reference.getName().contains(text)
                            || reference.getDescription().contains(text)) {
                        jref.add(ref);
                    }
                } else {
                    jref.add(ref);
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error executing the get Todolist list API", t);
        }
        return jref;
    }
    
    /**
     * Load the details of a Todolist
     * @param properties
     * @return
     * @throws Throwable
     */
    /*
    public JAXBTodolist getTodolistForApi(Properties properties) throws Throwable {
    JAXBTodolist todolist = null;
    Project project = null;
    try {
    String pid = null;
    if (null != properties
    && !properties.isEmpty()) {
    pid = properties.getProperty(PARAM_PROJECT_ID);
    project = getProjectManager().getProject(new Long(pid), null);
    }
    String id = null;
    if (null != properties
    && !properties.isEmpty()) {
    id = properties.getProperty(PARAM_ID);
    List<TodolistReference> todolists = getTodolistManager().getTodolists(project, null);
    
    _logger.info("Loading todolist details '{}' for project '{}' ", id, pid);
    
    for (TodolistReference ref: todolists) {
    if (ref.getId() == new Long(id)) {
    Todolist todolistElem = getTodolistManager().getTodolist(ref, null);
    if (null != todolistElem) {
    todolist = new JAXBTodolist(todolistElem);
    }
    break;
    }
    }
    }
    } catch (Throwable t) {
    throw new ApsSystemException("Error executing the get Todolist details API", t);
    }
    return todolist;
    }
    */
    
    public JAXBTodolist getTodolistForApi(Properties properties) throws Throwable {
        JAXBTodolist todolist = null;
        Long projectId = null;
        Long todolistId = null;
        
        try {
            String pid = null;
            if (null != properties
                    && !properties.isEmpty()) {
                pid = properties.getProperty(PARAM_PROJECT_ID);
                projectId = new Long(pid);
            }
            String id = null;
            if (null != properties
                    && !properties.isEmpty()) {
                id = properties.getProperty(PARAM_ID);
                todolistId = new Long(id);
            }
            Todolist tdl = getTodolistManager().getTodolist(projectId, todolistId, null);
            todolist = new JAXBTodolist(tdl);
            _logger.info("Loading todolist details '{}' for project '{}' ", id, pid);
        } catch (Throwable t) {
            throw new ApsSystemException("Error executing the get Todolist details API", t);
        }
        return todolist;
    }
    
    /**
     * Create a new todolist for the given project
     * @param jproject
     * @throws Throwable
     */
    public void addTodolistForApi(JAXBTodolist jaxbtodolist) throws Throwable {
        Project project = null;
        
        try {
            Todolist todolist= Todolist.fromJAXBTodolist(jaxbtodolist);
            project = getProjectManager().getProject(jaxbtodolist.getProjectId(), null);
            _logger.info("Creating new todolist for project '{}'", jaxbtodolist.getProjectId());
            Todolist updated = getTodolistManager().createTodolist(todolist, project, null);
            _logger.info("New project created '{}'", updated.getId());
        } catch (Throwable t) {
            _logger.error("Error executing API create new Todolist");
            throw new ApsSystemException("Error executing the create new Todolist API", t);
        }
    }
    
    /**
     * Update a project given its ID
     * @param jproject
     * @throws Throwable
     */
    public void updateTodolistForApi(JAXBTodolist jaxbtodolist) throws Throwable {
        Long projectId = null;
        Long todolistId = null;
        Todolist todolist = null;
        
        try {
            projectId = jaxbtodolist.getProjectId();
            todolistId = jaxbtodolist.getId();
            todolist = getTodolistManager().getTodolist(projectId, todolistId, null);
            _logger.info("Updating todolist id '{}' of project '{}'", todolistId, projectId);
            if (null != todolist) {
                if (StringUtils.isNotBlank(jaxbtodolist.getName())) {
                    todolist.setName(jaxbtodolist.getName());
                }
                if (StringUtils.isNotBlank(jaxbtodolist.getDescription())) {
                    todolist.setDescription(jaxbtodolist.getDescription());
                }
                todolist.setPosition(jaxbtodolist.getPosition());
                if (null != jaxbtodolist.getIsPrivate()) {
                    todolist.setIsPrivate(jaxbtodolist.getIsPrivate());
                }
                todolist = getTodolistManager().updateTodolist(todolist, null);
                _logger.info("Updated todolist id '{}' of project '{}'", todolist.getId(), projectId);
            } else {
                throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Todolist with id '" + jaxbtodolist.getId() + "' not found", Response.Status.CONFLICT);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error executing API update Todolist");
            throw new ApsSystemException("Error executing the update Todolist API", t);
        }
    }
    
    /**
     * Delete the given todolist
     * @param properties
     * @throws Throwable
     */
    public void deleteTodolistForApi(Properties properties) throws Throwable {
        Long projectId = null;
        Long todolistId = null;
        
        try {
            String pid = null;
            if (null != properties
                    && !properties.isEmpty()) {
                pid = properties.getProperty(PARAM_PROJECT_ID);
                projectId = new Long(pid);
            }
            String id = null;
            if (null != properties
                    && !properties.isEmpty()) {
                id = properties.getProperty(PARAM_ID);
                todolistId = new Long(id);
            }
            getTodolistManager().deleteTodolist(projectId, todolistId, null);
            _logger.info("Deleted todolist id '{}' belonging to project '{}'", id, pid);
        } catch (Throwable t) {
            _logger.error("Error executing API delete Todolist");
            throw new ApsSystemException("Error executing the delete Todolist API", t);
        }
    }
    
    // Todolists - END
    
    
    // Todo - START
    public JAXBTodo getTodoForApi(Properties properties) throws Throwable {
        JAXBTodo todo = null;
        Long projectId = null;
        Long todoId = null;
        
        try {
            String id = null;
            if (null != properties
                    && !properties.isEmpty()) {
                id = properties.getProperty(PARAM_ID);
                todoId = new Long(id);
            }
            String pid = null;
            if (null != properties
                    && !properties.isEmpty()) {
                pid = properties.getProperty(PARAM_PROJECT_ID);
                projectId = new Long(pid);
            }
            Todo td = getTodolistManager().getTodo(projectId, todoId, null);
            todo = new JAXBTodo(td);
            _logger.info("Loading todo details '{}' for project '{}'", id, pid);
        } catch (Throwable t) {
            throw new ApsSystemException("Error executing the get todolist detail API", t);
        }
        return todo;
    }
    
    /**
     * Create a new Todo
     * @param jaxbtodo
     * @throws Throwable
     */
    public void addTodoForApi(JAXBTodo jaxbtodo) throws Throwable {
        Project project = null;
        Todolist todolist = null;
        Todo todo = Todo.fromJAXBTodo(jaxbtodo);
        
        try {
            project = getProjectManager().getProject(jaxbtodo.getProjectId(), null);
            _logger.info("Creating new todo for project '{}'", jaxbtodo.getProjectId());
            List<TodolistReference> todolists = getTodolistManager().getTodolists(project, null);
            for (TodolistReference tdlr: todolists) {
                if (tdlr.getId() == jaxbtodo.getTodolistId()) {
                    todolist = getTodolistManager().getTodolist(tdlr, null);
                    todo = getTodolistManager().createTodo(todo, null, todolist, null);
                    break;
                }
            }
            _logger.info("New Todo created '{}'", todo.getId());
        } catch (Throwable t) {
            _logger.error("Error executing API create new Todo");
            throw new ApsSystemException("Error executing the create new Todo API", t);
        }
    }
    
    /**
     * Update Todo
     * @param jaxbTodo
     * @throws Throwable
     */
    public void updateTodoForApi(JAXBTodo jaxbTodo) throws Throwable {
        try {
            Todo todo = Todo.fromJAXBTodo(jaxbTodo);
            todo = _todolistManager.updateTodo(todo,(Long) null, null);
            _logger.info("Update Todo with id '{}' of project '{}'", todo.getId(), todo.getProjectId());
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error executing API upload Todo");
            throw new ApsSystemException("Error executing the update Todo API", t);
        }
    }
    
    /**
     * Delete Todo
     * @param properties
     * @throws Throwable
     */
    public void deleteTodoForApi(Properties properties) throws Throwable {
        Long projectId = null;
        Long todoId = null;
        JAXBTodo jtodo = new JAXBTodo();
        
        try {
            if (null != properties
                    && !properties.isEmpty()) {
                String id = properties.getProperty(PARAM_ID);
                todoId = new Long(id);
            }
            if (null != properties
                    && !properties.isEmpty()) {
                String pid = properties.getProperty(PARAM_PROJECT_ID);
                projectId = new Long(pid);
            }
            jtodo.setId(todoId);
            jtodo.setProjectId(projectId);
            Todo todo = Todo.fromJAXBTodo(jtodo);
            _todolistManager.deleteTodo(todo, null, null);
            _logger.info("Deleted Todo with id '{}' of project '{}'", todo.getId(), todo.getProjectId());
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error executing API telete todo");
            throw new ApsSystemException("Error deleting Todo", t);
        }
    }
    
    // Todo - END
    
    public IProjectManager getProjectManager() {
        return _projectManager;
    }
    
    public void setProjectManager(IProjectManager projectManager) {
        this._projectManager = projectManager;
    }
    
    public ITodolistManager getTodolistManager() {
        return _todolistManager;
    }
    
    public void setTodolistManager(ITodolistManager todolistManager) {
        this._todolistManager = todolistManager;
    }
    
    public IPeopleManager getPeopleManager() {
        return _peopleManager;
    }
    
    public void setPeopleManager(IPeopleManager peopleManager) {
        this._peopleManager = peopleManager;
    }
    
    private IProjectManager _projectManager;
    private ITodolistManager _todolistManager;
    private IPeopleManager _peopleManager;
    
    private static final String PARAM_TEXT_FILTER = "search";
    private static final String PARAM_ID = "id";
    private static final String PARAM_PROJECT_ID = "pid";
}
