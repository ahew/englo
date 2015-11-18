package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model;

import java.util.HashMap;
import java.util.Map;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;

public class UserProject {
    
    public UserProject() {
        _todolist = new HashMap<Integer, Todolist>();
        _documents = new HashMap<String, AttachmentReference>();
    }
    
    public void clearLists() {
        if (null != _todolist) {
            _todolist.clear();
        } else {
            _todolist = new HashMap<Integer, Todolist>();   
        }
        if (null != _documents) {
            _documents.clear();
        } else {
            _documents = new HashMap<String, AttachmentReference>();    
        }
    }
    
    /**
     * Return the total count of the Todos at the given position
     * @param position
     * @return
     */
    public int getTotalCount(int position) {
        if (null != _todolist
                && _todolist.containsKey(position)) {
            Todolist tdl = _todolist.get(position);
            
            return (tdl.getRemaningList().size() +
                    tdl.getCompletedList().size());
        }
        return 0;
    }
    
    /**
     * Return the count of the Todos of the given todolist
     * @param tdlid
     * @return
     */
    public long getTodolistTodos(long tdlid) {
        if (null != _todolist
                && !_todolist.isEmpty()) {
            Todolist todolist = findTodolist(tdlid);
            if (null != todolist) {
                return (todolist.getCompletedCount() + todolist.getRemaninigCount());
            }
        }
        return 0;
    }
    
    /**
     * Return the total count of the Todos
     * @param id
     * @return
     */
    public int getRemainingCount(int id) {
        if (null != _todolist
                && _todolist.containsKey(id)) {
            Todolist tdl = _todolist.get(id);
            
            return (tdl.getRemaningList().size());
        }
        return 0;
    }
    
    /**
     * Get the Todolist desired by position, invoked from the frontend
     * @param position
     * @return
     */
    public Todolist getTodolist(int position) {
        if (_todolist.containsKey(position)) {
            return _todolist.get(position);
        }
        return null;
    }
    
    /**
     * Get the Todolist desired by id
     * @return
     */
    public Todolist findTodolist(long id) {
        if (null != _todolist
                && !_todolist.isEmpty()) {
            for (Todolist tdl: _todolist.values()) {
                if (tdl.getId() == id) {
                    return tdl;
                }
            }
        }
        return null;
    }
    
    /**
     * Find a Todo given its id
     * @param id
     * @return
     */
    public TodoItem findTodo(long id) {
        if (_todolist != null
                && !_todolist.isEmpty()) {
            for (Todolist todolist: _todolist.values()) {
                for (TodoItem todo: todolist.getRemaningList()) {
                    if (todo.getId() == id) {
                        return todo;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Delete the given todolist
     * @param id
     */
    public void deleteTodolist(long id) {
        int position = -1;
        
        if (null != _todolist
                && !_todolist.isEmpty()) {
            for (int pos: _todolist.keySet()) {
                Todolist todolist = _todolist.get(pos);
                
                if (todolist.getId() == id) {
                    position = pos;
                    break;
                }
            }
            _todolist.remove(position);
        }
    }
    
    /**
     * Delete the given todolist
     * @param id
     */
    public void deleteTodo(long id) {
        int position = -1;
        TodoItem delete = null;
        
        
        
        if (null != _todolist
                && !_todolist.isEmpty()) {
            
            for (int pos: _todolist.keySet()) {
                Todolist todolist = _todolist.get(pos);
                
                for (TodoItem td: todolist.getRemaningList()) {
                    
                    if (td.getId() == id) {
                        delete = td;
                        break;
                    }
                }
                for (TodoItem td: todolist.getCompletedList()) {
                    
                    if (td.getId() == id) {
                        delete = td;
                        break;
                    }
                }
                if (delete != null) {
                    
                    if (delete.getCompleted()) {
                        todolist.getCompletedList().remove(delete);
                    } else {
                        todolist.getRemaningList().remove(delete);
                    }
                }
            }
            _todolist.remove(position);
        }
    }
    
    public UserProject(Project project) {
        _project = project;
    }
    
    public Project getProject() {
        return _project;
    }
    
    public void setProject(Project project) {
        this._project = project;
    }
    
    public Map<Integer, Todolist> getTodolist() {
        return _todolist;
    }
    
    public void setTodolist(Map<Integer, Todolist> todolist) {
        this._todolist = todolist;
    }
    
    public Map<String, AttachmentReference> getDocuments() {
        return _documents;
    }
    
    public void setDocuments(Map<String, AttachmentReference> documents) {
        this._documents = documents;
    }
    
    private Project _project;
    // indexed by position
    private Map<Integer, Todolist> _todolist;
    // indexed by file name
    private Map<String, AttachmentReference> _documents;
}
