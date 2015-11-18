package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

public interface ITodolistsFrontAction {

	/**
	 * Creates or updates a todolist element
	 * @return
	 */
	public String updateTodolist();

	/**
	 * Creates or updates e todo element
	 * @return
	 */
	public String updateTodo();

	/**
	 * Delete a todolist from a project
	 * @return
	 */
	public String deleteTodolist();

}
