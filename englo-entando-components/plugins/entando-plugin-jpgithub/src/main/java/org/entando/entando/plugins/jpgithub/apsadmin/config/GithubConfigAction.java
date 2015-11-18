package org.entando.entando.plugins.jpgithub.apsadmin.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GithubConfig;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;

public class GithubConfigAction extends BaseAction implements IGithubConfigAction {
    
    Logger _logger = LoggerFactory.getLogger(GithubConfigAction.class);
    
    @Override
    public String edit() {
        try {
            _config = this.getGithubManager().getConfiguration();
        } catch (Throwable t) {
            _logger.error("Error editing configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String save() {
        GithubConfig config = this.getConfig();        
        try {
            getGithubManager().updateConfiguration(config);
            _logger.info("Configuration updated");
        } catch (Throwable t) {
            _logger.error("Error saving configuration", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String test() {
        GithubConfig config = this.getConfig();
        
        try {
            getGithubManager().updateConfiguration(config);
            
            GitHubClient client = getGithubManager().getConfiguration().getClient();
            List<String> list = getGithubManager().getRepositories(client);
            addActionMessage(getText("jpgithub.test.ok"));
            _logger.info("Configuration verified");
            
        } catch (Throwable t) {
            addActionError(getText("jpgithub.test.invalid.configuration"));
            _logger.info("Invalid configuration detected");
        }
        return SUCCESS;
    }
    
    public GithubConfig getConfig() {
        return _config;
    }
    
    public void setConfig(GithubConfig config) {
        this._config = config;
    }
    
    public IGithubManager getGithubManager() {
        return _githubManager;
    }
    
    public void setGithubManager(IGithubManager githubManager) {
        this._githubManager = githubManager;
    }
    
    private GithubConfig _config;
    
    private IGithubManager _githubManager;
    
}
