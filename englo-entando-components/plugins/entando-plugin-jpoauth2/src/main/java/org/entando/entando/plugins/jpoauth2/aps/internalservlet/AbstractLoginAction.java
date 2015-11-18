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
package org.entando.entando.plugins.jpoauth2.aps.internalservlet;

import org.entando.entando.plugins.jpoauth2.aps.system.services.indexer.IIndexerManager;

import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.url.IURLManager;
import com.agiletec.apsadmin.system.BaseAction;

public class AbstractLoginAction extends BaseAction {
	
//	public boolean isLogged();
	
	protected String encodeState(String portalPage, String serviceIndex) {
		return portalPage.concat(";").concat(serviceIndex);
	}
	
	protected String decodeStatePortalPage(String state) {
		String[] array = state.split(";");
		
		return array[0];
	}
	
	protected String decodeStateService(String state) {
		String[] array = state.split(";");
		
		return array[1];
	}
	
	public IURLManager getUrlManager() {
		return _urlManager;
	}
	
	public void setUrlManager(IURLManager urlManager) {
		this._urlManager = urlManager;
	}
	
	public IPageManager getPageManager() {
		return _pageManager;
	}
	
	public void setPageManager(IPageManager pageManager) {
		this._pageManager = pageManager;
	}
	
	public IIndexerManager getIndexerManager() {
		return _indexerManager;
	}

	public void setIndexerManager(IIndexerManager indexerManager) {
		this._indexerManager = indexerManager;
	}
	
	public ConfigInterface getConfigManager() {
		return _configManager;
	}

	public void setConfigManager(ConfigInterface configManager) {
		this._configManager = configManager;
	}

	private IURLManager _urlManager;
	private IPageManager _pageManager;
	private IIndexerManager _indexerManager;
	private ConfigInterface _configManager;
}
