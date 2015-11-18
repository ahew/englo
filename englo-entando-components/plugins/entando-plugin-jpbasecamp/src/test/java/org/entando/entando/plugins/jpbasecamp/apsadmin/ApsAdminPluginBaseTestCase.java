/*
*
* Copyright 2013 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando Enterprise Edition software.
* You can redistribute it and/or modify it
* under the terms of the Entando's EULA
* 
* See the file License for the specific language governing permissions
* and limitations under the License
* 
* 
* 
* Copyright 2013 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package org.entando.entando.plugins.jpbasecamp.apsadmin;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.ITestCredentials;
import org.entando.entando.plugins.jpbasecamp.PluginConfigTestUtils;
import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.BasecampConfig;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.BasecampUrlHelper;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IBasecampManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IDocumentManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IPeopleManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ITodolistManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TestBasecampManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.Authorization;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session.OAuth2Session;
import org.entando.entando.plugins.jpoauth2.aps.system.OAuth2SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.ConfigTestUtils;
import com.agiletec.apsadmin.ApsAdminBaseTestCase;

public class ApsAdminPluginBaseTestCase extends ApsAdminBaseTestCase implements ITestCredentials {
	
	private static Logger _logger =  LoggerFactory.getLogger(ApsAdminPluginBaseTestCase.class);
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
	@Override
	public void tearDown() throws Exception {
		try {
			if (null != _project
					&& _project.getId() != 0) {
//				System.out.println("@@@ TEARING DOWN @@@ " + _project.getId());
				_projectManager.deleteProject(_project.getId(), null); // NOTE THIS!!!
			}
		} catch (Throwable t) {
			throw new Exception("Error in tearing down test", t);
		}
	}
	
	@Override
	protected ConfigTestUtils getConfigUtils() {
		return new PluginConfigTestUtils();
	}
	
	protected void setServiceDataInSession(BasecampService service) {
		HttpSession session = this.getRequest().getSession();
		OAuth2Session sessionData = new OAuth2Session();
		
		sessionData.addServiceData(BasecampSystemConstants.SERVICE_ID, service);
		session.removeAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT);
		session.setAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT, sessionData);
	}
	
	protected void setUserProjectInSession(UserProject up) {
		HttpSession session = this.getRequest().getSession();
		
		session.removeAttribute(BasecampSystemConstants.SESSIONPARAM_PROJECT_OBJECT);
		session.setAttribute(BasecampSystemConstants.SESSIONPARAM_PROJECT_OBJECT, up);
	}
	
	protected UserProject setProjectInSession(Project project) {
		UserProject up = new UserProject(project);
		
		setUserProjectInSession(up);
		return up;
	}
	
	protected BasecampConfig getBasecampConfigForTest() {
		BasecampConfig config= null;
		
		if (StringUtils.isNotBlank(ACCOUNT)
				&& StringUtils.isNotBlank(USERNAME)
				&& StringUtils.isNotBlank(PASSWORD)) {
			config = new BasecampConfig();
			
			config.setAccount(ACCOUNT);
			config.setUsername(USERNAME);
			config.setPassword(PASSWORD);
			BasecampUrlHelper.updateConfiguration(config);
		} else {
			_logger.info("WARNING: insert a valid access token and a valid instace URL in");
			_logger.info("WARNING: ITestCredentials to run actual tests");
		}
		return config;
	}
	
	public BasecampService getBasecampServiceForTest() {
		BasecampService service = null;
		Authorization authorization = new Authorization(AUTHRIZATION_JSON);
		
		if (StringUtils.isNotBlank(ACCESS_TOKEN)) {
			service = new BasecampService();
			service.setAuthorization(authorization);
			service.setAccessToken(ACCESS_TOKEN);
		} else {
			_logger.info("WARNING: insert a valid access token and a valid instace URL in");
			_logger.info("WARNING: ITestCredentials to run actual tests");
		}
		return service;
	}
	
	/**
	 * Create a todolist for tests
	 * @return
	 * @throws Throwable
	 */
	protected Todolist createTodolistForTest() throws Throwable {
		Todolist todolist = new Todolist(TestBasecampManager.TODOLIST_NAME,
				TestBasecampManager.TODOLIST_DESCRIPTION, 1);
		
		Todolist updated = _todolistManager.createTodolist(todolist, _project, _service);
		assertNotNull(updated);
		assertFalse(updated.getId() == 0);
		assertEquals(TestBasecampManager.TODOLIST_NAME, updated.getName());
		assertEquals(TestBasecampManager.TODOLIST_DESCRIPTION, updated.getDescription());
		return updated;
	}
	
	/**
	 * Create a todo for tests
	 * @param todolist
	 * @return
	 * @throws Throwable
	 */
	protected Todo createAssignedTodoForTest(Todolist todolist) throws Throwable {
		Person me = _peopleManager.whoAmI(_service);
		Todo todo = new Todo();
		todo.setDueAt(new Date());
		todo.setContent(TestBasecampManager.TODO_CONTENT);
		Todo updated = _todolistManager.createTodo(todo, me, todolist, _service);
		assertNotNull(updated);
		assertEquals(TestBasecampManager.TODO_CONTENT, updated.getContent());
		assertNotNull(updated.getProjectId());
		assertEquals(new Long(_project.getId()), updated.getProjectId());
		assertFalse(updated.getTodolistId() == 0);
		return updated;
	}
	
	
	/**
	 * Create a todo for tests
	 * @param todolist
	 * @return
	 * @throws Throwable
	 */
	protected Todo createTodoForTest(Todolist todolist) throws Throwable {
		Todo todo = new Todo();
		todo.setDueAt(new Date());
		todo.setContent(TestBasecampManager.TODO_CONTENT);
		Todo updated = _todolistManager.createTodo(todo, null, todolist, _service);
		assertNotNull(updated);
		assertEquals(TestBasecampManager.TODO_CONTENT, updated.getContent());
		assertNotNull(updated.getProjectId());
		assertEquals(new Long(_project.getId()), updated.getProjectId());
		assertFalse(updated.getTodolistId() == 0);
		return updated;
	}
	
	protected void init() throws Exception {
		_service = getBasecampServiceForTest();
		_config = getBasecampConfigForTest();
		try {
			_basecampManager = (IBasecampManager) super.getService(BasecampSystemConstants.BASECAMP_OAUTH2_MANAGER);
			_projectManager = (IProjectManager) super.getService(BasecampSystemConstants.BASECAMP_PROJECT_MANAGER);
			_documentManager = (IDocumentManager) super.getService(BasecampSystemConstants.BASECAMP_DOCUMENT_MANAGER);
			_todolistManager = (ITodolistManager) super.getService(BasecampSystemConstants.BASECAMP_TODOLIST_MANAGER);
			_peopleManager = (IPeopleManager) super.getService(BasecampSystemConstants.BASECAMP_PEOPLE_MANAGER);
			// project
			_project = new Project(PROJECT_NAME, PROJECT_DESCRIPTION, false);
			if (null != _service) {
				_project = _projectManager.createProject(_project, _service);
//				System.out.println("@@@ INIT @@@ " + _project.getId());
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Exception("Error in init");
		}
	}
	
	protected Project _project;
	
	protected BasecampService _service;
	protected BasecampConfig _config;

	protected IBasecampManager _basecampManager;
	protected IProjectManager _projectManager;
	protected IDocumentManager _documentManager;
	protected ITodolistManager _todolistManager;
	protected IPeopleManager _peopleManager;
	
	public static final String PROJECT_NAME = "My clever (junit) project";
	public static final String PROJECT_DESCRIPTION = "Hyping description";
	
}
