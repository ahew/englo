package org.entando.entando.plugins.jpbasecamp.aps;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.ITestCredentials;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.BasecampConfig;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.BasecampUrlHelper;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractBasecampManagerTest extends ApsPluginBaseTestCase implements ITestCredentials {
	
	private static Logger _logger =  LoggerFactory.getLogger(AbstractBasecampManagerTest.class);
	
	public BasecampService getBasecampServiceForTest() {
		BasecampService service = null;
		
		if (StringUtils.isNotBlank(ACCESS_TOKEN)) {
			service = new BasecampService();
			
			service.setAccessToken(ACCESS_TOKEN);
		} else {
			_logger.info("WARNING: insert a valid access token and a valid instace URL in");
			_logger.info("WARNING: ITestCredentials to run actual tests");
		}
		return service;
	}
	
	public BasecampConfig getBasecampConfigForTest() {
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
}
