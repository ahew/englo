/*
 *
 * <Your licensing text here>
 *
 */
package org.entando.entando.plugins.jpjenkins.aps.system.services;

import org.entando.entando.plugins.jpjenkins.aps.JpjenkinsBaseTestCase;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.IJenkinsClientManager;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.JenkinsConfig;

public class TestJenkinsClientManager extends JpjenkinsBaseTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
        public void testConfig() throws Throwable {
            try {
                JenkinsConfig config = _clientManager.getConfig();
                
                assertNotNull(config);
                assertEquals("USERNAME", config.getUsername());
                assertEquals("PASSWORD", config.getPassword());
                assertEquals("URL", config.getUrl());
                assertEquals("TOKEN", config.getToken());
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        }
	
	private void init() {
		//TODO add the spring bean id as constant
		this._clientManager = (IJenkinsClientManager) this.getService(IJenkinsClientManager.BEAN_ID);
	}
	
	private IJenkinsClientManager _clientManager;
}

