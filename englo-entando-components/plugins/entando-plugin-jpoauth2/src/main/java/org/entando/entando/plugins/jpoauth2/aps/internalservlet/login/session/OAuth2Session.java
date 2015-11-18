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
package org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OAuth2Session {
	
	private final Logger _logger = LoggerFactory.getLogger(OAuth2Session.class);
	
	public void addServiceData(String id, AbstractOAuth2Service data) {
		if (StringUtils.isNotBlank(id)
				&& null != data) {
			if (_services.containsKey(id)) {
				_logger.info("Overwriting service with id '{}'", id);
			}
			_services.put(id, data);
		} else {
			throw new RuntimeException("Adding invalid OAuth2 service to the session");
		}
	}
	
	public AbstractOAuth2Service getServiceData(String id) {
		AbstractOAuth2Service serviceData = null;
		
		if (StringUtils.isNotBlank(id)) {
			serviceData = _services.get(id);
		}
		return serviceData;
	}
	
	private Map<String, AbstractOAuth2Service> _services = new HashMap<String, AbstractOAuth2Service>();
}
