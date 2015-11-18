package org.entando.entando.plugins.jpgithub.apsadmin.config;

import com.agiletec.aps.system.exception.ApsSystemException;
import org.entando.entando.plugins.jpgithub.apsadmin.*;
import org.entando.entando.plugins.jpgithub.IGitHubTestParameters;

import com.opensymphony.xwork2.Action;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GitHubManager;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GithubConfig;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;

public class TestGithubConfigAction extends ApsAdminPluginBaseTestCase implements IGitHubTestParameters {
    
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testEdit() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "edit");
            GithubConfigAction action = (GithubConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            assertNotNull(action.getConfig().getUsername());
            assertEquals(IGitHubTestParameters.TEST_USER_ID, action.getConfig().getUsername());
            assertNotNull(action.getConfig().getPassword());
            assertEquals(IGitHubTestParameters.TEST_USER_PASSWORD, action.getConfig().getPassword());
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
            GithubConfigAction action = (GithubConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            GithubConfig config = _githubManager.getConfiguration();
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
    
     public void testTestSaveFail() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/Config", "save");
//            this.addParameter("config.username", TEST_REPOSITORY);
//            this.addParameter("config.password", TEST_REPOSITORY_MASTER);
            GithubConfigAction action = (GithubConfigAction) this.getAction();
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
            GithubConfigAction action = (GithubConfigAction) this.getAction();
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
            GithubConfigAction action = (GithubConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            
            assertNotNull(action.getConfig().getUsername());
            assertEquals(username, action.getConfig().getUsername());
            assertNotNull(action.getConfig().getPassword());
            assertEquals(password, action.getConfig().getPassword());
            
            GithubConfig config = _githubManager.getConfiguration();
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
    
    private void restoreConfiguration() throws ApsSystemException {
        GithubConfig config = new GithubConfig();
        
        config.setUsername(IGitHubTestParameters.TEST_USER_ID);
        config.setPassword(IGitHubTestParameters.TEST_USER_PASSWORD);
        _githubManager.updateConfiguration(config);
    }
            
    
    private void init() throws Exception {
        try {
            _githubManager = (IGithubManager) this.getService(GitHubManager.BEAN_ID);
            assertNotNull(_githubManager);
        } catch (Exception e) {
            throw e;
        }
    }
    
     private IGithubManager _githubManager;
}
