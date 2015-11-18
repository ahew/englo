package org.entando.entando.plugins.jptrello.apsadmin.config;

import com.agiletec.aps.system.exception.ApsSystemException;
import com.opensymphony.xwork2.Action;
import static junit.framework.Assert.assertNotNull;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.TrelloConfig;
import org.entando.entando.plugins.jptrello.apsadmin.JptrelloApsAdminBaseTestCase;



public class TestTrelloConfigAction extends JptrelloApsAdminBaseTestCase  {
    
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    public void _testEdit() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jptrello/Config", "edit");
            TrelloConfigAction action = (TrelloConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            assertNotNull(action.getConfig().getApiKey());
            assertEquals("KEY", action.getConfig().getApiKey());
            assertNotNull(action.getConfig().getApiSecret());
            assertEquals("SECRET", action.getConfig().getApiSecret());
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void _testTestSave() throws Throwable {
        final String api = "wrong_api";
        final String secret = "wrong_secret";
        final String token = "wrong_token";
        final String organization = "wrong_organization";
        
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jptrello/Config", "save");
            this.addParameter("config.apiKey", api);
            this.addParameter("config.apiSecret", secret);
            this.addParameter("config.organization", organization);
            this.addParameter("config.token", token);
            TrelloConfigAction action = (TrelloConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            TrelloConfig config = _clientManager.getConfiguration();
            assertNotNull(config.getApiKey());
            assertEquals(api, config.getApiKey());
            assertNotNull(config.getApiSecret());
            assertEquals(secret, config.getApiSecret());
            assertNotNull(config.getToken());
            assertEquals(token, config.getToken());
            assertNotNull(config.getOrganization());
            assertEquals(organization, config.getOrganization());
            assertTrue(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        } finally {
            restoreConfiguration();
        }
    }
    
     public void _testTestSaveFail() throws Throwable {
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jptrello/Config", "save");
//            this.addParameter("config.username", TEST_REPOSITORY);
//            this.addParameter("config.password", TEST_REPOSITORY_MASTER);
            TrelloConfigAction action = (TrelloConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.INPUT, result);
            assertTrue(action.getActionErrors().isEmpty());
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void testTestConfigFail() throws Throwable {
        final String api = "wrong_api";
        final String secret = "wrong_secret";
        final String token = "wrong_token";
        final String organization = "wrong_organization";
        
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jptrello/Config", "test");
            this.addParameter("config.apiKey", api);
            this.addParameter("config.apiSecret", secret);
            this.addParameter("config.organization", organization);
            this.addParameter("config.token", token);
            TrelloConfigAction action = (TrelloConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            
        } catch (Throwable t) {
            throw t;
        } finally {
            restoreConfiguration();
        }
    }
    
    /*
    public void _testTestConfigFail() throws Throwable {
        final String username = "wrongusername";
        final String password = "wrongpassword";
        
        try {
            this.setUserOnSession("admin");
            this.initAction("/do/jptrello/Config", "test");
            this.addParameter("config.username", username);
            this.addParameter("config.password", password);
            TrelloConfigAction action = (TrelloConfigAction) this.getAction();
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            
            assertNotNull(action.getConfig().getUsername());
            assertEquals(username, action.getConfig().getUsername());
            assertNotNull(action.getConfig().getPassword());
            assertEquals(password, action.getConfig().getPassword());
            
            TrelloConfig config = _clientManager.getConfiguration();
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
        TrelloConfig config = new TrelloConfig();
        
        config.setApiKey("KEY");
        config.setApiSecret("SECRET");
        config.setOrganization("ORGANIZATION");
        config.setToken("TOKEN");
        _clientManager.updateConfiguration(config);
    }
            
    
    private void init() throws Exception {
        try {
            _clientManager = (ITrelloClientManager) this.getService(ITrelloClientManager.BEAN_NAME);
            assertNotNull(_clientManager);
        } catch (Exception e) {
            throw e;
        }
    }
    
     private ITrelloClientManager _clientManager;
}
