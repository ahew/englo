package org.entando.entando.plugins.jpbasecamp.apsadmin.config;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.BasecampConfig;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IBasecampManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;

public class BasecampConfigAction extends BaseAction implements IBasecampConfigAction {
	
	Logger _logger = LoggerFactory.getLogger(BasecampConfigAction.class);
	
	@Override
	public String edit() {
		try {
			_config = this.getBasecampManager().getConfiguration();
		} catch (Throwable t) {
			_logger.error("Error editing configuration", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String save() {
		BasecampConfig config = this.getConfig();
		
		try {
			if (_resetEndopoint) {
				getBasecampManager().resetEndpoints(config);
				_logger.info("Enpoints resetted as requested");
			} else {
				getBasecampManager().updateConfiguration(config);
				_logger.info("Configuration updated");
			}
		} catch (Throwable t) {
			_logger.error("Error saving configuration", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public String test() {
		BasecampConfig config = this.getConfig();

		try {
			getBasecampManager().updateConfiguration(config);
			if (config.isValidForUnmannedUtilization()) {
				List<ProjectReference> list = getProjectManager().getProjects(null);
				addActionMessage(getText("jpbasecamp.test.ok"));
				_logger.info("Configuration verified");
			} else {
				addActionError(getText("jpbasecamp.test.missing.parameters"));
				_logger.info("Missing configuration parameters");
			}
		} catch (Throwable t) {
			addActionError(getText("jpbasecamp.test.invalid.configuration"));
			_logger.info("Invalid configuration detected");
		}
		return SUCCESS;
	}
	
	public BasecampConfig getConfig() {
		return _config;
	}

	public void setConfig(BasecampConfig config) {
		this._config = config;
	}

	public IBasecampManager getBasecampManager() {
		return _basecampManager;
	}

	public void setBasecampManager(IBasecampManager basecampManager) {
		this._basecampManager = basecampManager;
	}
	
	public boolean isResetEndopoint() {
		return _resetEndopoint;
	}

	public void setResetEndopoint(boolean resetEndopoint) {
		this._resetEndopoint = resetEndopoint;
	}

	public IProjectManager getProjectManager() {
		return _projectManager;
	}

	public void setProjectManager(IProjectManager projectManager) {
		this._projectManager = projectManager;
	}


	private BasecampConfig _config;
	private boolean _resetEndopoint;
	
	private IBasecampManager _basecampManager;
	private IProjectManager _projectManager;
	
}
