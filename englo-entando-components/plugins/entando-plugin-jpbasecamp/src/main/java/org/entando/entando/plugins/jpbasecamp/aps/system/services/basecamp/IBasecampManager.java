package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.IOAuth2Manager;

import com.agiletec.aps.system.exception.ApsSystemException;

public interface IBasecampManager extends IOAuth2Manager {
	
	/**
	 * Get authorization of the current logged user
	 * @param serviceData
	 * @throws Throwable
	 */
	public void getAutorization(BasecampService serviceData) throws Throwable;

	/**
	 * Return a copy of the configuration
	 * @return
	 */
	public BasecampConfig getConfiguration();

	
	/**
	 * Update the Basecamp configuration
	 * @param config
	 * @throws ApsSystemException
	 */
	public void updateConfiguration(BasecampConfig config) throws ApsSystemException;

	/**
	 * Reset the endpoints of the given configuration. If no configuration is 
	 * not given then the actual configuration will be reset
	 * @param config
	 * @return TODO
	 * @throws ApsSystemException
	 */
	public BasecampConfig resetEndpoints(BasecampConfig config) throws ApsSystemException;
	
}
