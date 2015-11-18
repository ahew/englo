package org.entando.entando.plugins.jpjenkins.apsadmin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.IJenkinsClientManager;
import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.JenkinsConfig;


public class JenkinsConfigAction extends BaseAction implements IJenkinsConfigAction {
    
    Logger _logger = LoggerFactory.getLogger(JenkinsConfigAction.class);
    
    @Override
    public String edit() {
        try {
            _config = this.getJenkinsManager().getConfig();
        } catch (Throwable t) {
            _logger.error("Error editing configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String save() {
        JenkinsConfig config = this.getConfig();        
        try {
            getJenkinsManager().updateConfiguration(config);
            _logger.info("Configuration updated");
        } catch (Throwable t) {
            _logger.error("Error saving configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String test() {
        String info = null;
        JenkinsConfig config = this.getConfig();
        
        try {
            getJenkinsManager().updateConfiguration(config);
            info = _jenkinsManager.getInfo();
            if (StringUtils.isNotBlank(info)) {
                addActionMessage(getText("jpjenkins.test.ok"));
                _logger.info("Configuration verified");
            } else {
                addActionError(getText("jpjenkins.test.invalid.configuration"));
                _logger.info("Invalid configuration detected");
            }
        } catch (Throwable t) {
            addActionError(getText("jpjenkins.test.invalid.configuration"));
            _logger.info("Invalid configuration detected");
        }
        return SUCCESS;
    }
    
    public JenkinsConfig getConfig() {
        return _config;
    }
    
    public void setConfig(JenkinsConfig config) {
        this._config = config;
    }
    
    public IJenkinsClientManager getJenkinsManager() {
        return _jenkinsManager;
    }
    
    public void setJenkinsManager(IJenkinsClientManager jenkinsManager) {
        this._jenkinsManager = jenkinsManager;
    }
    
    private JenkinsConfig _config;
    
    private IJenkinsClientManager _jenkinsManager;
    
}
