package org.entando.entando.plugins.jpgithub.aps.system.internalservlet;

import org.entando.entando.plugins.jpgithub.IGitHubTestParameters;

import com.opensymphony.xwork2.Action;
import org.entando.entando.plugins.jpgithub.apsadmin.ApsAdminPluginBaseTestCase;

public class TestProjectFrontAction extends ApsAdminPluginBaseTestCase implements IGitHubTestParameters {
    
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testEntry() throws Throwable {
        try {
//			this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/FrontEnd/Project", "entry");
            this.addParameter("repository", TEST_REPOSITORY);
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void testEntryWithBranch() throws Throwable {
        try {
//			this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/FrontEnd/Project", "entry");
            this.addParameter("repository", TEST_REPOSITORY);
            this.addParameter("headBranch", TEST_REPOSITORY_MASTER);
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
        } catch (Throwable t) {
            throw t;
        }
    }
    
    private void init() throws Exception {
        try {
        } catch (Exception e) {
            throw e;
        }
    }
    
}
