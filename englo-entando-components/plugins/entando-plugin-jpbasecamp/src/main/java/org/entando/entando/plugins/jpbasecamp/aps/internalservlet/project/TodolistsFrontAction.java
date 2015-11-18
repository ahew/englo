package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.PersonReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TodolistsFrontAction extends AbstractBasecampFrontAction implements ITodolistsFrontAction {
    
    private static Logger _logger = LoggerFactory.getLogger(TodolistsFrontAction.class);
    
    public String todolistView() {
        try {
            // refresh userproject
            UserProject up = reenter();
            setUserProject(up);
            // todolist
            if (StringUtils.isNotBlank(getTdlid())) {
                long tdlid = Long.valueOf(getTdlid());
                Todolist todolist = up.findTodolist(tdlid);
                
                _name = todolist.getName();
                _description = todolist.getDescription();
                // todo
                if (StringUtils.isNotBlank(getTdid())) {
                    long tdid = Long.valueOf(getTdid());
                    TodoItem todo = up.findTodo(tdid);
                    
                    _content = todo.getContent();
                }
            }
        } catch (Throwable t) {
            _logger.error("Error editing the todolist", t);
        }
        return SUCCESS;
    }
    
    @Override
    public String updateTodolist() {
        UserProject up = reenter();
        BasecampService serviceData = null;
        
        try {
            Map<Integer, Todolist> todolistMap = up.getTodolist();
            
            serviceData = getSessionServiceData();
            // process todoList
            if (StringUtils.isNotBlank(_name)) {
                if (null == getIsTodolistEdit()
                        || !getIsTodolistEdit()) {
                    int position = 1;
                    // adjust position
                    if (null != todolistMap
                            && !todolistMap.isEmpty()) {
                        position += todolistMap.size();
                    } else {
                        // if no todolist are present in this project then create a map
                        todolistMap = new HashMap<Integer, Todolist>();
                        up.setTodolist(todolistMap);
                    }
                    // create new todolist
                    Todolist todolist = new Todolist(_name, _description, position);
                    
                    todolist = this.getTodolistManager().createTodolist(todolist, up.getProject(), serviceData);
                    _logger.info("Created new todolist for project '{}' at position '{}'", up.getProject().getId(), position);
                    up.getTodolist().put(position, todolist);
                    _logger.info("New todolist id is '{}'", todolist.getId());
                } else {
                    for (Todolist todolist: todolistMap.values()) {
                        
                        if (todolist.getId() == Long.valueOf(getTdlid())) {
                            // update fields
                            todolist.setName(_name);
                            todolist.setDescription(_description);
                            // update record
                            this.getTodolistManager().updateTodolist(todolist, serviceData);
                            
                            _logger.info("Updated todolist '{}' for project '{}'", todolist.getId(), up.getProject().getId());
                            break;
                        }
                    }
                }
            } else {
                _logger.info("skipping todolist update");
            }
        } catch (Throwable t) {
            _logger.error("Error updating todolist");
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String updateTodo() {
        String result = INPUT;
        BasecampService serviceData = null;
        UserProject up = reenter();
        Todo todo = null;
        Person assignee = null;
        Todolist todolist = null;
        Project project = null;
        
        try {
            serviceData = getSessionServiceData();
            project = up.getProject();
            assignee = fetchAssignee();
            if (null != _isTodolistEdit
                    && _isTodolistEdit) {
                result = updateTodolist();
                _logger.info("Todolist updated before processing todo with result '{}'", result);
            }
            // check whether update or create a new todo
            if (StringUtils.isNotBlank(getTdid())) {
                long tdid = Long.valueOf(getTdid());
                // load todo before updating
                todo = getTodolistManager().getTodo(project.getId(), tdid, serviceData);
                todo.setContent(_content);
                // update assignee if needed
                if (null != assignee) {
                    AssigneeItem assigneeItem = new AssigneeItem();
                    // update assignee
                    assigneeItem.setId(assignee.getId());
                    todo.setAssignee(assigneeItem);
                }
                todo = getTodolistManager().updateTodo(todo, project.getId(), serviceData);
                _logger.info("Updated todo '{}' for project id '{}' and todolist id '{}'", todo.getId(), getPid(), getTdlid());
            } else {
                long tdlid = Long.valueOf(getTdlid());
                todo = new Todo();
                todo.setContent(_content);
                todolist = up.findTodolist(tdlid);
                todo = getTodolistManager().createTodo(todo, assignee, todolist, serviceData);
                _logger.info("Created new todo with id '{}' for project '{}', toodlist '{}'", todo.getId(), getTdlid(), getPid());
            }
            // update user project
            refreshUserProjectDetails();
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error updating todo");
            return FAILURE;
        }
        // finally, invalidate the todoId
        setTdid(null);
        setContent(null);
        return SUCCESS;
    }
    
    @Override
    public String deleteTodolist() {
        UserProject up = reenter();
        Project project = null;
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            project = up.getProject();
            long tdlid = Long.valueOf(getTdlid());
            
            getTodolistManager().deleteTodolist(project.getId(), tdlid, serviceData);
            _logger.info("Deleted todolist '{}' of project '{}'", tdlid, project.getId());
            // update project on session
            up.deleteTodolist(tdlid);
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error deleting todolist '{}'", getTdlid());
            return FAILURE;
        }
        return SUCCESS;
    }
    
    
    public String deleteTodo() {
        UserProject up = reenter();
        Project project = null;
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            project = up.getProject();
            long tdid = Long.valueOf(getTdid());
            
            getTodolistManager().deleteTodo(tdid, project.getId(), serviceData);
            _logger.info("Deleted todo '{}' from project '{}'", tdid, project.getId());
            // update project on session
            up.deleteTodo(tdid);
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error deleting todolist '{}'", getTdlid());
            return FAILURE;
        }
        return SUCCESS;
    }
    
    /**
     * Get people available for task assignment
     * @return
     */
    public List<PersonReference> getPeople() {
        List<PersonReference> list = null;
        try {
            BasecampService serviceData = getSessionServiceData();
            list = getPeopleManager().getUsers(serviceData);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return list;
    }
    
    /**
     * Return the current todo from session (if any)
     * @return
     */
    public TodoItem getTodo() {
        UserProject up = reenter();
        TodoItem todo = null;
        
        if (StringUtils.isNotBlank(getTdid())) {
            long tdid = Long.valueOf(getTdid());
            
            todo = up.findTodo(tdid);
        }
        return todo;
    }
    
    /**
     * Return the current todolist from the session (if any)
     * @return
     */
    public Todolist getTodolist() {
        UserProject up = reenter();
        Todolist todolist = null;
        
        if (StringUtils.isNotBlank(getTdlid())) {
            long tdlid = Long.valueOf(getTdlid());
            
            todolist = up.findTodolist(tdlid);
        }
        return todolist;
    }
    
    /**
     * Reload user project from the session
     * @return
     */
    public UserProject reenter() {
        UserProject up = getUserProjectOnSession();
        
        setUserProject(up);
        return up;
    }
    
    public Boolean getIsTodolistEdit() {
        return _isTodolistEdit;
    }
    public void setIsTodolistEdit(Boolean isTodolistEdit) {
        this._isTodolistEdit = isTodolistEdit;
    }
    public String getTodoManagementAction() {
        return _todoManagementAction;
    }
    public void setTodoManagementAction(String todoManagementAction) {
        this._todoManagementAction = todoManagementAction;
    }
    public String getName() {
        return _name;
    }
    public void setName(String name) {
        this._name = name;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        this._description = description;
    }
    public String getContent() {
        return _content;
    }
    public void setContent(String content) {
        this._content = content;
    }
    
    // form stuff
    private String _name;
    private String _description;
    private String _content;
    // TODO / FIXME
    private Date _dueDate;
    
    private String _todoManagementAction;
    private Boolean _isTodolistEdit; // otherwise is a new todolist
}
