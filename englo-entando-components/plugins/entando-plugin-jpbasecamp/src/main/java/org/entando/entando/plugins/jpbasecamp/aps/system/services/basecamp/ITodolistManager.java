package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.AssignedTodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssignedTodoItem;

import com.agiletec.aps.system.exception.ApsSystemException;

public interface ITodolistManager {

	/**
	 * Get the todolists for the given project
	 * @param project
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public List<TodolistReference> getTodolists(Project project, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Get the list of the todos given to a given user
	 * @param project
	 * @param person TODO
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public List<AssignedTodolistReference> getAssignedTodolists(Project project, Person person, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Get the completed todo list
	 * @param project
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public List<TodolistReference> getCompletedTodolists(Project project, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Get the todolist given its reference
	 * @param reference
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	@Deprecated
	public Todolist getTodolist(TodolistReference reference, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Update given todolist
	 * @param todolist
	 * @param serviceData
	 * @return TODO
	 * @throws ApsSystemException
	 */
	public Todolist updateTodolist(Todolist todolist, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete the given todolist
	 * @param todolist
	 * @param serviceData
	 * @throws ApsSystemException
	 */
	void deleteTodolist(Todolist todolist, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Create a new todolist
	 * @param todolist
	 * @param project
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todolist createTodolist(Todolist todolist, Project project, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Create a new todo for the desired todolist and assign it to the given user
	 * @param todo
	 * @param person
	 * @param todolist
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todo createTodo(Todo todo, Person person, Todolist todolist, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Load the details of the desired todo
	 * @param reference
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todo getTodo(AssignedTodoItem reference, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete the given todo
	 * @param todo
	 * @param serviceData
	 * @throws ApsSystemException
	 */
	@Deprecated
	public void deleteTodo(Todo todo, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete the todo given its id and the project it belongs to
	 * @param projectId
	 * @param todolistId
	 * @param serviceData
	 * @throws ApsSystemException
	 */
	public void deleteTodolist(long projectId, long todolistId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Update the Todo
	 * @param todo
	 * @param todolist
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	@Deprecated
	public Todo updateTodo(Todo todo, Todolist todolist, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Update the Todo
	 * @param todo
	 * @param projectId if null, will be taken from teh Todo object itself
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todo updateTodo(Todo todo, Long projectId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete a todo Object
	 * @param todo
	 * @param projectId if null, will be taken from teh Todo object itself
	 * @param serviceData
	 * @throws ApsSystemException
	 * @Deprecated
	 */
	void deleteTodo(Todo todo, Long projectId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Load the details of a Todo given the Project and the Todo IDs
	 * @param projectId
	 * @param todoId
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todo getTodo(long projectId, long todoId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Get the Todolist details
	 * @param projectId
	 * @param todolistId
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public Todolist getTodolist(long projectId, long todolistId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Load the Todolists list for a given project
	 * @param projectId
	 * @param serviceData
	 * @return
	 * @throws ApsSystemException
	 */
	public List<TodolistReference> getTodolists(long projectId, BasecampService serviceData) throws ApsSystemException;

	/**
	 * Delete a todo from the given project
	 * @param id
	 * @param projectId
	 * @param serviceData
	 * @throws ApsSystemException
	 */
	public void deleteTodo(long id, long projectId, BasecampService serviceData) throws ApsSystemException;

        
        /**
         * Manager bean ID
         */
        public String BEAN_ID = "jpbasecampTodolistManager";
}
