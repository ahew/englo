package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;

public interface IProjectManager {

	/**
	 * Get the projects in Basecamp
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public List<ProjectReference> getProjects(BasecampService serviceData) throws Throwable;
	
	/**
	 * Get archived projects
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public List<ProjectReference> getArchivedProjects(BasecampService serviceData) throws Throwable;
	
	/**
	 * Load the details of a project given the corresponding element returned 
	 * @param project
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Project getProject(ProjectReference reference, BasecampService serviceData) throws Throwable;
	
	/**
	 * Create a new project
	 * used whatever it is
	 * @param project
	 * @param serviceData TODO
	 * @return
	 * @throws Throwable
	 */
	public Project createProject(Project project, BasecampService serviceData) throws Throwable;
	
	/**
	 * Delete the given project
	 * @param project
	 * @param serviceData
	 * @throws Throwable
	 */
	public void deleteProject(Project project, BasecampService serviceData) throws Throwable;
	
	/**
	 * Update the given project
	 * @param project
	 * @param serviceData
	 * @return TODO
	 * @throws Throwable
	 */
	public Project updateProject(Project project, BasecampService serviceData) throws Throwable;
	
	/**
	 * Load a project given its id. Needed by the API REST engine
	 * @param id
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Project getProject(long id, BasecampService serviceData) throws Throwable;

	/**
	 * Delete a project given its ID
	 * @param id
	 * @param serviceData
	 * @throws Throwable
	 */
	public void deleteProject(long id, BasecampService serviceData) throws Throwable;
}
