package org.entando.entando.plugins.jpgithub.aps.internalservlet.project;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GithubConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.agiletec.apsadmin.system.BaseAction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.PullRequest;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpenglo.aps.system.EngloSystemConstants;
import org.entando.entando.plugins.jpenglo.aps.system.services.todo.IEngloTodolistManager;

public class EngloProjectFrontAction extends ProjectFrontAction implements IprojectFrontAction {
    
    private static final Logger _logger =  LoggerFactory.getLogger(EngloProjectFrontAction.class);
    
    @Override
    public String entry() {
        String result = super.entry();
        
        if (result.equals(BaseAction.SUCCESS)) {
            setPidInSession(getPid());
        }
        return result;
    }
    
    /*
    @Override
    public String listPullRequest() {
        String result = super.listPullRequest();
        
        try {
            if (result.equals(BaseAction.SUCCESS)) {
                // retrieve basecamp parameters

//                for (PullRequest pr: getPullRequests()) {
//                    
//                    System.out.println("BASE " + pr.getBase().getLabel());
//                    System.out.println("HEAD " + pr.getHead().getLabel());
//                }
            }
        } catch (Throwable t) {
            _logger.error("Error creating listing request", t);
            return FAILURE;
        }
        return result;
    }
    */
    
    @Override
    public String mergePullRequest() {
        try {
            GitHubClient client = getClient();
            String pid = getPidInSession();
            
            super.listPullRequest();
            // merge!
            getGithubManager().mergePullRequest(client, getRepository(),
                    getPullRequestNumber(),
                    getBody());
            // get the project id if there's nothing in the session
            List<ProjectReference> projects = _projectManager.getProjects(null);
            if (null != projects
                    && null == getPidInSession()) {
                _logger.info("must lookup projet '{}'", getRepository());
                for (ProjectReference project: projects) {
                    if (project.getName().equals(getRepository())) {
                        pid = String.valueOf(project.getId());
                        _logger.info("repository '{}' corresponds to project '{}'",
                                getRepository(),
                                pid);
                    }
                }
            }
            // invoke the todolist manager
            for (PullRequest pr: getPullRequests()) {
                if (pr.getNumber() == getPullRequestNumber()) {
                    String name = pr.getHead().getLabel(); // the same as todo content
                    
                    // discard the 'username:' from the branch name
                    String[] tok = name.split(":");
                    if (tok.length == 2) {
                        name = tok[1];
                    }
                    _logger.info("will look for Todo named '{}' in the project '{}' todolist", name, pid);
                    _todolistManager.updateTodoOnPullRequest(pid, name, null);
                }
            }
        } catch (Throwable t) {
            _logger.error("Error merging pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }

    
    private GitHubClient getClient() {
        GitHubClient client = null;
        
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            client = config.getClient();
        } catch (Throwable t) {
            _logger.error("Error getting the current client", t);
        }
        return client;
    }
    
    /**
     * Put the project code in session; if pid isn't specified the argument in 
     * the request is taken
     * @param pid 
     */
    private void setPidInSession(String pid) {
        HttpServletRequest req = this.getRequest();
        HttpSession session = req.getSession();
        if (StringUtils.isBlank(pid)) {
            pid = getPid();
        }
        if (StringUtils.isNotBlank(pid)) {
            _logger.info("Placing project id {} in the session as main project", pid);
            
            session.removeAttribute(EngloSystemConstants.SESSION_PARAM_PID);
            session.setAttribute(EngloSystemConstants.SESSION_PARAM_PID, pid);
        } else {
            _logger.error("No valid project id to place in the session");
        }
    }
    
    /**
     * Get the project id from the session
     * @return 
     */
    private String getPidInSession() {
        HttpServletRequest req = this.getRequest();
        HttpSession session = req.getSession();
        
        return (String) session.getAttribute(EngloSystemConstants.SESSION_PARAM_PID);
    }
    
    public String getPid() {
        return _pid;
    }

    public void setPid(String pid) {
        this._pid = pid;
    }

    public IProjectManager getProjectManager() {
        return _projectManager;
    }

    public void setProjectManager(IProjectManager projectManager) {
        this._projectManager = projectManager;
    }

    protected IEngloTodolistManager getTodolistManager() {
        return _todolistManager;
    }

    public void setTodolistManager(IEngloTodolistManager todolistManager) {
        this._todolistManager = todolistManager;
    }
    
    // Project id
    private String _pid;
    
    private IEngloTodolistManager _todolistManager;
    private IProjectManager _projectManager;
}
