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
package org.entando.entando.plugins.jpoauth2.aps.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session.OAuth2Session;
import org.entando.entando.plugins.jpoauth2.aps.system.OAuth2SystemConstants;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.ApsSystemUtils;

public class AuthenticatationTag extends TagSupport {
	
	Logger _logger = LoggerFactory.getLogger(AuthenticatationTag.class);
	
	@Override
	public int doStartTag() throws JspException { 
		HttpSession session = this.pageContext.getSession();
		boolean hasLogged = false;
		boolean hasService = false;
		
		try {
			OAuth2Session sessionServices = (OAuth2Session) session.getAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT);
			AbstractOAuth2Service service = null;
			
			if (null != sessionServices
					&& StringUtils.isNotBlank(_service)) {
				service = sessionServices.getServiceData(_service);
			}
			hasLogged = (sessionServices != null);
			hasService = hasLogged && (null != service);
			_logger.info("Currentuser authetication: hasLogged '{}', using service '{}'", hasLogged, hasService);
			if (StringUtils.isNotBlank(_var)) {
				boolean status = _service != null ? hasService : hasLogged;
				
				_logger.info("Returning overall result '{}'", status);
				this.pageContext.setAttribute(_var, new Boolean(status));
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "doStartTag");
			throw new JspException("Errore tag", t);
		}
		if (hasLogged || hasService) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
	
	
	public String getService() {
		return _service;
	}
	public void setService(String service) {
		this._service = service;
	}
	public String getVar() {
		return _var;
	}
	public void setVar(String var) {
		this._var = var;
	}

	private String _service;
	private String _var;
	
}
