package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import org.entando.entando.plugins.jpbasecamp.aps.ApsPluginBaseTestCase;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;

public class TestBasecampManagerConfig extends ApsPluginBaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
	private void init() throws Exception {
		try {
			this._basecampManager = (IBasecampManager) super.getService(BasecampSystemConstants.BASECAMP_OAUTH2_MANAGER);
		} catch (Throwable e) {
			throw new RuntimeException("Error creeating a new project");
		}
	}
	
	
	public void testConfigLoad() throws Throwable {
		try {
			BasecampConfig config = _basecampManager.getConfiguration();
			
			assertNotNull(config);
			assertEquals("ID", config.getAppId());
			assertEquals("SECRET", config.getAppSecret());
			assertEquals("https://launchpad.37signals.com/authorization.json", config.getAuthorizationUrl());
			assertEquals("https://launchpad.37signals.com/authorization/new", config.getAuthorizationNewUrl());
			assertEquals("https://launchpad.37signals.com/authorization/token", config.getAuthorizationTokenUrl());
			assertEquals("username", config.getUsername());
			assertEquals("password", config.getPassword());
			assertEquals("1112223", config.getAccount());
			assertTrue(config.isDisclose());
			
//			assertEquals(CONFIG_XML, config.toXML());
		} catch (Throwable t) {
			throw t;
		}
	}
	
	public void testConfigReset() throws Throwable {
		BasecampConfig config = _basecampManager.getConfiguration();
		BasecampConfig originalConfig = _basecampManager.getConfiguration();
		
		String FAKEURL1 = "http://w";
		String FAKEURL2 = "http://ww";
		String FAKEURL3 = "http://www";
				
		try {
			
			config.setAuthorizationNewUrl(FAKEURL1);
			config.setAuthorizationTokenUrl(FAKEURL2);
			config.setAuthorizationUrl(FAKEURL3);
			config.setDisclose(false);
			
			_basecampManager.updateConfiguration(config);
			
			config = _basecampManager.getConfiguration();
			assertEquals("ID", config.getAppId());
			assertEquals("SECRET", config.getAppSecret());
			assertEquals(FAKEURL3, config.getAuthorizationUrl());
			assertEquals(FAKEURL1, config.getAuthorizationNewUrl());
			assertEquals(FAKEURL2, config.getAuthorizationTokenUrl());
			
			config = _basecampManager.resetEndpoints(config);
			assertNotNull(config);
			assertEquals("ID", config.getAppId());
			assertEquals("SECRET", config.getAppSecret());
			assertEquals("https://launchpad.37signals.com/authorization.json", config.getAuthorizationUrl());
			assertEquals("https://launchpad.37signals.com/authorization/new", config.getAuthorizationNewUrl());
			assertEquals("https://launchpad.37signals.com/authorization/token", config.getAuthorizationTokenUrl());
			assertFalse(config.isDisclose());
		} catch (Throwable t) {
			throw t;
		} finally {
			_basecampManager.updateConfiguration(originalConfig);
		}
	}
	
	public void testConfigConversion() throws Throwable {
		try {
			BasecampConfig config = _basecampManager.getConfiguration();
			
//			System.out.println("@@@\n" + config.toXML());
			
			assertNotNull(config);
			assertEquals(CONFIG_XML, config.toXML());
		} catch (Throwable t) {
			throw t;
		}
	}
	
	private IBasecampManager _basecampManager;
	
	public final static String CONFIG_XML = "<config><username>username</username><disclose>true</disclose><account>1112223</account><app_secret>SECRET</app_secret><app_id>ID</app_id><auth_url_token>https://launchpad.37signals.com/authorization/token</auth_url_token><auth_url_new>https://launchpad.37signals.com/authorization/new</auth_url_new><auth_url>https://launchpad.37signals.com/authorization.json</auth_url><password>password</password></config>";
	
}
