package org.entando.entando.plugins.jpbasecamp.aps.system.services.indexer;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpoauth2.aps.system.services.indexer.IIndexerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;

public class BasecampIndexerManager extends AbstractService {
	
	private static final Logger _logger =  LoggerFactory.getLogger(BasecampIndexerManager.class);
	
	@Override
	public void init() throws Exception {
		_logger.info(this.getClass().getCanonicalName() + ": initialized");
		config();
	}
	
	private void config() throws ApsSystemException {
		String sid = getServiceid();
		
		if (StringUtils.isNotBlank(sid)) {
			this.getIndexerManager().addService(BasecampSystemConstants.SERVICE_ID, sid);
		} else {
			throw new ApsSystemException("Invalid service specification");
		}
	}
	
	public String getServiceid() {
		return _serviceid;
	}

	public void setServiceid(String serviceid) {
		this._serviceid = serviceid;
	}

	public IIndexerManager getIndexerManager() {
		return _indexerManager;
	}

	public void setIndexerManager(IIndexerManager indexerManager) {
		this._indexerManager = indexerManager;
	}

	private String _serviceid;
	private IIndexerManager _indexerManager;

}
