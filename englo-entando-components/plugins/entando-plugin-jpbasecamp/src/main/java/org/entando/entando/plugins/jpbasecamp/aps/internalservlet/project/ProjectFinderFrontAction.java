/*
 *
 * <Your licensing text here>
 *
 */
package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session.OAuth2Session;
import org.entando.entando.plugins.jpoauth2.aps.system.OAuth2SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;

public class ProjectFinderFrontAction extends BaseAction {
	
	private static final Logger _logger =  LoggerFactory.getLogger(ProjectFinderFrontAction.class);

	public List<ProjectReference> getProjects() {
		List<ProjectReference> projects = new ArrayList<ProjectReference>();
		List<ProjectReference> archived = new ArrayList<ProjectReference>();
		List<ProjectReference> result = new ArrayList<ProjectReference>();
		BasecampService service = getSessionServiceData();
		
		try {
			// FIXME more comprehensive condition when search fields are added
			boolean restrict = StringUtils.isNotBlank(_searchText)
					|| isClientProject() || isStarred() || isTrashed();
			
			projects = this.getProjectManager().getProjects(service);
			
//			System.out.println("isArchived " + isArchived());
//			System.out.println("isClientProject " + isClientProject());
//			System.out.println("isStarred " + isStarred());
//			System.out.println("isTrashed " + isTrashed());
			
			if (isArchived()) {
				archived = this.getProjectManager().getArchivedProjects(service);
				projects.addAll(archived);
			}
			
			if (restrict) {
				for (ProjectReference ref: projects) {
					boolean include = false;
					boolean conditionMet = false;
					
					// text search on title and description
					String txt = getSearchText();
					if (StringUtils.isNotBlank(txt)) {
						txt = txt.toLowerCase();
						String tgt = String.format("%s %s", ref.getName(), ref.getDescription()).toLowerCase();
						include = tgt.contains(txt);
						conditionMet = true;
					}
					if (this.isStarred()) {
						if (conditionMet) {
							include &= ref.getStarred();
						} else {
							include = ref.getStarred();
						}
						conditionMet = true;
					}
					if (this.isClientProject()) {
						if (conditionMet) {
							include &= ref.getIsClientProject();
						} else {
							include = ref.getIsClientProject();
						}
						conditionMet = true;
					}
					// include if needed
					if (include) {
						result.add(ref);
					}
				}
			} else {
				result = new ArrayList<ProjectReference>(projects);
			}
			
			_logger.debug("{} projects found", projects.size());
		} catch (Throwable t) {
			_logger.error("Error getting projects list", t);
			throw new RuntimeException("Error getting projects list", t);
		}
		return result;
	}

	public Project getProject(ProjectReference reference) {
		BasecampService service = getSessionServiceData();
		Project project = null;
		
		try {
			project = this.getProjectManager().getProject(reference, service);
			
		} catch (Throwable t) {
			_logger.error("Error getting project with id {}", reference.getId(), t);
			throw new RuntimeException("Error getting project with id " + reference.getId(), t);
		}
		return project;
	}
	
	/**
	 * Get authentication data for the basecamp service
	 * @return
	 */
	private BasecampService getSessionServiceData() {
		BasecampService service = null; 
		OAuth2Session sessionData = (OAuth2Session) this.getRequest().getSession().getAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT);
		
		if (null != sessionData) {
			service = (BasecampService) sessionData.getServiceData(BasecampSystemConstants.SERVICE_ID);
			if (null == service) {
				_logger.error("Basecamp service authentication id '{}' not found", 
						BasecampSystemConstants.SERVICE_ID);
			}
		} else {
			_logger.error("Could not retrieve session authentication");
		}
		return service;
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}

	protected IProjectManager getProjectManager() {
		return _projectManager;
	}
	public void setProjectManager(IProjectManager projectManager) {
		this._projectManager = projectManager;
	}
	
	public String getSearchText() {
		return _searchText;
	}

	public void setSearchText(String searchText) {
		this._searchText = searchText;
	}

//	public String getCreator() {
//		return _creator;
//	}

//	public void setCreator(String creator) {
//		this._creator = creator;
//	}

	private long _id;
	
	public boolean isStarred() {
		return _starred;
	}

	public void setStarred(boolean starred) {
		this._starred = starred;
	}

	public boolean isArchived() {
		return _archived;
	}

	public void setArchived(boolean archived) {
		this._archived = archived;
	}

	public boolean isClientProject() {
		return _clientProject;
	}

	public void setClientProject(boolean clientProject) {
		this._clientProject = clientProject;
	}

	public boolean isTrashed() {
		return _trashed;
	}

	public void setTrashed(boolean trashed) {
		this._trashed = trashed;
	}

	// form search
	private String _searchText;
	
	private boolean _starred;
	private boolean _archived;
	private boolean _clientProject;
	
//	private String _creator;
	private boolean _trashed;

	
	private IProjectManager _projectManager;
}

