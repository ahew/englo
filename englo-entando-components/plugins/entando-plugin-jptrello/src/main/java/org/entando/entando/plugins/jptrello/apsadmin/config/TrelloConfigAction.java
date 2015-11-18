package org.entando.entando.plugins.jptrello.apsadmin.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.TrelloConfig;


public class TrelloConfigAction extends BaseAction implements ITrelloConfigAction {
    
    Logger _logger = LoggerFactory.getLogger(TrelloConfigAction.class);
    
    @Override
    public String edit() {
        try {
            _config = this.getTrelloClientManager().getConfiguration();
        } catch (Throwable t) {
            _logger.error("Error editing configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String save() {
        TrelloConfig config = this.getConfig();
        
        try {
            this.getTrelloClientManager().updateConfiguration(config);
            _logger.info("Configuration updated");
        } catch (Throwable t) {
            _logger.error("Error saving configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String test() {
        TrelloConfig config = this.getConfig();
        
        try {
            this.getTrelloClientManager().updateConfiguration(config);
            
            String json = getTrelloClientManager().getJsonBoardByOrganization();
            if (StringUtils.isNotBlank(getTrelloClientManager().getIdOrganization())) {
                addActionMessage(getText("jptrello.test.valid.configuration"));
                _logger.info("Configuration verified");
            } else {
                _logger.info("Invalid configuration detected");    
            }
        } catch (Throwable t) {
            addActionError(getText("jptrello.test.invalid.configuration"));
            _logger.info("Invalid configuration detected");
        }
        return SUCCESS;
    }
    
    public TrelloConfig getConfig() {
        return _config;
    }
    
    public void setConfig(TrelloConfig config) {
        this._config = config;
    }
    
    public ITrelloClientManager getTrelloClientManager() {
        return _trelloClientManager;
    }
    
    public void setTrelloClientManager(ITrelloClientManager trelloClientManager) {
        this._trelloClientManager = trelloClientManager;
    }
    
    private TrelloConfig _config;
    
    private ITrelloClientManager _trelloClientManager;
    
}
