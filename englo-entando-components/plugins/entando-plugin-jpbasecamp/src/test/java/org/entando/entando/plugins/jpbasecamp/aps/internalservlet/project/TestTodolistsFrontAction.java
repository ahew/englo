package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TestBasecampManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;
import org.entando.entando.plugins.jpbasecamp.apsadmin.ApsAdminPluginBaseTestCase;

import com.agiletec.apsadmin.system.BaseAction;

public class TestTodolistsFrontAction extends ApsAdminPluginBaseTestCase {
	
	public void testTodolistNew() throws Throwable {
		final String NAME = "aMeaningfulName4Todolist";
		final String DESCRIPTION = "aMeaningfulDescription4Todolist";
		TodolistsFrontAction action = null;
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			setProjectInSession(_project);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodolist");
			this.addParameter("pid", _project.getId());
			this.addParameter("name", NAME);
			this.addParameter("description", DESCRIPTION);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNotNull(up);
			assertNotNull(up.getProject());
			assertEquals(_project.getId(), up.getProject().getId());
			Map<Integer, Todolist> list = up.getTodolist();
			assertNotNull(list);
			assertFalse(list.isEmpty());
			// check whether the user project has been updated
			boolean isCreated = false;
			for (Todolist todolist: list.values()) {
				if (todolist.getDescription().equals(DESCRIPTION)
						&& todolist.getName().equals(NAME)) {
					if (!isCreated) {
						isCreated = true;
					} else {
						throw new RuntimeException("duplicated todolist ?!?");
					}
				}
			}
			assertTrue(isCreated);
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	
	public void testTodolistEdit() throws Throwable {
		final String NAME = "aMeaningfulName4TodolistUPdated";
		final String DESCRIPTION = "aMeaningfulDescription4TodolistUpdated";
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodolist");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("isTodolistEdit", true);
			this.addParameter("name", NAME);
			this.addParameter("description", DESCRIPTION);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNotNull(up);
			Map<Integer, Todolist> list = up.getTodolist();
			assertNotNull(list);
			assertFalse(list.isEmpty());
			Todolist tdl = list.get(1);
			assertNotNull(tdl);
			assertEquals(testTodolist.getId(), tdl.getId());
			assertEquals(NAME, tdl.getName());
			assertEquals(DESCRIPTION, tdl.getDescription());
			
			// test #2
			Todolist vtdl = _todolistManager.getTodolist(_project.getId(), testTodolist.getId(), _service);
			assertNotNull(vtdl);
			assertEquals(NAME, vtdl.getName());
			assertEquals(DESCRIPTION, vtdl.getDescription());
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	
	public void testTodoEdit() throws Throwable {
		final String CONTENT = "aMeaningfulName4Todo";
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			Todo todo = createTodoForTest(testTodolist);
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodo");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("tdid", todo.getId());
			this.addParameter("content", CONTENT);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			// check if up is updated #1
			Todo vtd = _todolistManager.getTodo(_project.getId(), todo.getId(), _service);
			assertNotNull(vtd);
			assertEquals(todo.getId(), vtd.getId());
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	public void testTodoCreate() throws Throwable {
		final String CONTENT = "aMeaningfulName4Todo";
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			Todo todo = createTodoForTest(testTodolist);
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodo");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("content", CONTENT);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			// check if up is created correctly
			Todolist ver = up.findTodolist(testTodolist.getId());
			assertNotNull(ver);
			ver.getRemaningList();
			
			boolean found = false;
			for (TodoItem todolist: ver.getRemaningList()) {
				if (todolist.getContent().equals(CONTENT)) {
					found = true;
				}
			}
			assertTrue(found);
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	public void testTodoCreateAssignee() throws Throwable {
		final String CONTENT = "aMeaningfulName4Todo";
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			Todo todo = createTodoForTest(testTodolist);
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodo");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("content", CONTENT);
			this.addParameter("aid", ADMIN_ID);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			// check if up is updated #1
			Todolist ver = up.findTodolist(testTodolist.getId());
			assertNotNull(ver);
			ver.getRemaningList();
			
			boolean found = false;
			for (TodoItem todolist: ver.getRemaningList()) {
				if (todolist.getContent().equals(CONTENT)) {
					found = true;
				}
			}
			assertTrue(found);
			
			boolean isOk = false;
			Todolist vtdl = _todolistManager.getTodolist(_project.getId(), testTodolist.getId(), _service);
			List<TodoItem> vtdil = vtdl.getRemaningList();
			for (TodoItem tdi: vtdil) {
				if (null != tdi.getAssignee()
						&& tdi.getAssignee().getId() == Long.valueOf(ADMIN_ID)
					&& todo.getContent().equals(TestBasecampManager.TODO_CONTENT)) {
						isOk = true;
				}
			}
			assertTrue(isOk);
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	public void testTodoEditAssignee() throws Throwable {
		final String CONTENT = "aMeaningfulName4Todo";
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			Todo todo = createTodoForTest(testTodolist);
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "updateTodo");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("tdid", todo.getId());
			this.addParameter("content", CONTENT);
			this.addParameter("aid", ADMIN_ID);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			// check if up is updated #1
			Todo vtd = _todolistManager.getTodo(_project.getId(), todo.getId(), _service);
			assertNotNull(vtd);
			assertEquals(todo.getId(), vtd.getId());
			AssigneeItem assignee = vtd.getAssignee();
			assertNotNull(assignee);
			assertEquals(Long.valueOf(ADMIN_ID), Long.valueOf(assignee.getId()));
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	public void testTodolistDelete() throws Throwable {
		TodolistsFrontAction action = null;
		Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "deleteTodolist");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNotNull(up);
			Map<Integer, Todolist> list = up.getTodolist();
			assertNotNull(list);
			assertTrue(list.isEmpty());
			
			// test #2
			Todolist vtdl = _todolistManager.getTodolist(_project.getId(), testTodolist.getId(), _service);
			assertNotNull(vtdl);
			assertTrue(vtdl.getTrashed());
			assertEquals(TestBasecampManager.TODOLIST_NAME, vtdl.getName());
			assertEquals(TestBasecampManager.TODOLIST_DESCRIPTION, vtdl.getDescription());
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
	
	public void testDeleteTodo() throws Throwable {
		final String CONTENT = "aMeaningfulName4Todo";
		TodolistsFrontAction action = null;
Map<Integer, Todolist> todolistMap = new HashMap<Integer, Todolist>();
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			Todolist testTodolist = createTodolistForTest();
			Todo todo = createAssignedTodoForTest(testTodolist);
			todolistMap.put(1, testTodolist);
			UserProject formUP = setProjectInSession(_project);
			formUP.setTodolist(todolistMap);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "deleteTodo");
			this.addParameter("pid", _project.getId());
			this.addParameter("tdlid", testTodolist.getId());
			this.addParameter("tdid", todo.getId());
			
			this.addParameter("content", CONTENT);
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (TodolistsFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			// check if up is updated #1
			Todolist ver = up.findTodolist(testTodolist.getId());
			assertNotNull(ver);
			ver.getRemaningList();
			
			boolean found = false;
			for (TodoItem todolist: ver.getRemaningList()) {
				if (todolist.getContent().equals(CONTENT)) {
					found = true;
				}
			}
			assertFalse(found);
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need to delete it will be teared down
		}
	}
	
}
