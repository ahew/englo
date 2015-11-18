package org.entando.entando.plugins.jpjenkins.apsadmin.config;

import com.agiletec.aps.system.exception.ApsSystemException;

import com.opensymphony.xwork2.Action;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.JenkinsClientManager;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.JenkinsConfig;
import org.entando.entando.plugins.jpjenkins.apsadmin.JpjenkinsApsAdminBaseTestCase;


public class TestJenkinsConfigAction extends JpjenkinsApsAdminBaseTestCase {
    
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testEdit() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpjenkins/Config", "edit");
            JenkinsConfigAction action = (JenkinsConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            JenkinsConfig config = action.getConfig();
            assertNotNull(config);
            assertEquals("USERNAME", config.getUsername());
            assertEquals("PASSWORD", config.getPassword());
            assertEquals("URL", config.getUrl());
            assertEquals("TOKEN", config.getToken());
        } catch (Throwable t) {
            throw t;
        }
    }
    public void testTestSave() throws Throwable {
        final String username = "wrong_username";
        final String password = "wrong_password";
        
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "save");
            this.addParameter("config.username", username);
            this.addParameter("config.password", password);
            JenkinsConfigAction action = (JenkinsConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            JenkinsConfig config = _jenkinsManager.getConfig();
            assertNotNull(config.getUsername());
            assertEquals(username, config.getUsername());
            assertNotNull(config.getPassword());
            assertEquals(password, config.getPassword());
            assertTrue(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        } finally {
            restoreConfiguration();
        }
    }
    
    /*
     public void testTestSaveFail() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "save");
//            this.addParameter("config.username", TEST_REPOSITORY);
//            this.addParameter("config.password", TEST_REPOSITORY_MASTER);
            JenkinsConfigAction action = (JenkinsConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.INPUT, result);
            assertTrue(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void testTestConfigOk() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "test");
            this.addParameter("config.username", TEST_USER_ID);
            this.addParameter("config.password", TEST_USER_PASSWORD);
            JenkinsConfigAction action = (JenkinsConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            assertNotNull(action.getConfig().getUsername());
            assertEquals(IGitHubTestParameters.TEST_USER_ID, action.getConfig().getUsername());
            assertNotNull(action.getConfig().getPassword());
            assertEquals(IGitHubTestParameters.TEST_USER_PASSWORD, action.getConfig().getPassword());
            assertTrue(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        } finally {
            restoreConfiguration();
        }
    }
    
    public void _testTestConfigFail() throws Throwable {
        final String username = "wrongusername";
        final String password = "wrongpassword";
        
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "test");
            this.addParameter("config.username", username);
            this.addParameter("config.password", password);
            JenkinsConfigAction action = (JenkinsConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            
            assertNotNull(action.getConfig().getUsername());
            assertEquals(username, action.getConfig().getUsername());
            assertNotNull(action.getConfig().getPassword());
            assertEquals(password, action.getConfig().getPassword());
            
            JenkinsConfig config = _githubManager.getConfig();
            assertNotNull(config.getUsername());
            assertEquals(IGitHubTestParameters.TEST_USER_ID, config.getUsername());
            assertNotNull(config.getPassword());
            assertEquals(IGitHubTestParameters.TEST_USER_PASSWORD, config.getPassword());
            
            assertFalse(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        } finally {
            restoreConfiguration();
        }
    }
    
    */     
    private void restoreConfiguration() throws ApsSystemException {
        JenkinsConfig config = new JenkinsConfig();
        
        config.setUsername("USERNAME");
        config.setPassword("PASSWORD");
         config.setUrl("URL");
        config.setToken("TOKEN");
        _jenkinsManager.updateConfiguration(config);
    }
    
    private void init() throws Exception {
        try {
            _jenkinsManager = (JenkinsClientManager) this.getService(JenkinsClientManager.BEAN_ID);
            assertNotNull(_jenkinsManager);
        } catch (Exception e) {
            throw e;
        }
    }
    
     private JenkinsClientManager _jenkinsManager;
}
