package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpbasecamp.apsadmin.ApsAdminPluginBaseTestCase;

import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseAction;


public class TestProjectFrontAction extends ApsAdminPluginBaseTestCase {
	
	public void testList() throws Throwable {
		try {
//			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "list");
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
		} catch (Throwable t) {
			throw t;
		}
	}

	public void testNew() throws Throwable {
		try {
//			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "new");
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
		} catch (Throwable t) {
			throw t;
		}
	}

	// FIXME  move in the appropriate test class
	public void testConfigEntry() throws Throwable {
		try {
			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/Config", "edit");
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
		} catch (Throwable t) {
			throw t;
		}
	}

	public void testViewAdd() throws Throwable {
		ProjectFrontAction action = null;
		
		try {
//			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "view");
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (ProjectFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNull(up);
			assertEquals(ApsAdminSystemConstants.ADD, action.getStrutsAction());
		} catch (Throwable t) {
			throw t;
		}
	}
	
	public void testViewEdit() throws Throwable {
		ProjectFrontAction action = null;
		
		try {
//			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "view");
			this.addParameter("pid", _project.getId());
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (ProjectFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertEquals(ApsAdminSystemConstants.EDIT, action.getStrutsAction());
			assertNotNull(up);
			assertNotNull(up.getProject());
			assertEquals(up.getProject().getId(), _project.getId());
		} catch (Throwable t) {
			throw t;
		}
	}
	
	public void testSave() throws Throwable {
		try {
//			setUserOnSession("admin");
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "save");
			this.addParameter("strutsAction", ApsAdminSystemConstants.EDIT);
			String result = this.executeAction();
			assertEquals(BaseAction.INPUT, result);
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
	
	public void testSaveADD() throws Throwable {
		final String NAME = "aMeaningfulName";
		final String DESCRIPTION = "aMeaningfulDescription";
		ProjectFrontAction action = null;
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "save");
			this.addParameter("strutsAction", ApsAdminSystemConstants.ADD);
			this.addParameter("userProject.project.name", NAME);
			this.addParameter("userProject.project.description", DESCRIPTION);
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (ProjectFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNotNull(up);
			Project updated = up.getProject();
			assertNotNull(updated);
			assertEquals(NAME, updated.getName());
			assertEquals(DESCRIPTION, updated.getDescription());
			assertNotNull(updated.getId());
		} catch (Throwable t) {
			throw t;
		} finally {
			List<ProjectReference> pjs = _projectManager.getProjects(_service);
			if (null != pjs) {
				for (ProjectReference pref: pjs) {
					if (pref.getName().equals(NAME)
							&& pref.getDescription().equals(DESCRIPTION)) {
						_projectManager.deleteProject(pref.getId(), _service);
					}
				}
			}
		}
	}

	public void testSaveEDIT() throws Throwable {
		final String NAME = "aMeaningfulName";
		final String DESCRIPTION = "aMeaningfulDescription";
		ProjectFrontAction action = null;
		
		if (null == _service) return;
		
		try {
//			setUserOnSession("admin");
			setProjectInSession(_project);
			setServiceDataInSession(_service);
			this.initAction("/do/jpbasecamp/FrontEnd/Project", "save");
			this.addParameter("strutsAction", ApsAdminSystemConstants.EDIT);
			this.addParameter("userProject.project.name", NAME);
			this.addParameter("userProject.project.description", DESCRIPTION);
			this.addParameter("pid", _project.getId());
			
			String result = this.executeAction();
			assertEquals(BaseAction.SUCCESS, result);
			action = (ProjectFrontAction) this.getAction();
			UserProject up = action.getUserProjectOnSession();
			assertNotNull(up);
			Project updated = up.getProject();
			assertNotNull(updated);
			assertEquals(_project.getId(), updated.getId());
			assertEquals(NAME, updated.getName());
			assertEquals(DESCRIPTION, updated.getDescription());
			assertNotNull(updated.getId());
		} catch (Throwable t) {
			throw t;
		} finally {
			// no need it will be teared down
		}
	}
	
}
