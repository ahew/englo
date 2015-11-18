/*
*
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando software.
* Entando is a free software;
* You can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2014 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package org.entando.entando.plugins.jpoauth2.aps.system.services.indexer;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;

/**
 * This class collects all the services which needs OAuth2 authentication
 * @author entando
 *
 */
public class IndexerManager extends AbstractService implements IIndexerManager {
	
	private static final Logger _logger =  LoggerFactory.getLogger(IndexerManager.class);
	

	@Override
	public void init() throws Exception {
		_logger.info(this.getClass().getCanonicalName() + ": initialized");
	}



	@Override
	public void addService(String key, String value) {
		if (null == _services) {
			_services = new Properties();
		}
		_services.put(key, value);
	}
	
	@Override
	public String getManagerById(String id) {
		String name = null;
		
		try {
			name = _services.getProperty(id);
		} catch (Throwable t) {
			_logger.error("Error detected retrieving service name for id '{}'", id);
			_logger.error("Stacktrace ", t);
			throw new RuntimeException("Unknown service");
		}
		return name;
	}
	
	public Properties getServices() {
		return _services;
	}

	public void setServices(Properties services) {
		this._services = services;
	}

	private Properties _services;

}
