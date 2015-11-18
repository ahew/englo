package org.entando.entando.plugins.jpgithub.aps.internalservlet.project;

import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GitHubManager;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GithubConfig;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.PullRequest;
import org.entando.entando.plugins.jpgithub.aps.system.GithubSystemConstants;

public class ProjectFrontAction extends BaseAction implements IprojectFrontAction {
    
    private static final Logger _logger =  LoggerFactory.getLogger(ProjectFrontAction.class);
    
    
    @Override
    public String list() {
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            GitHubClient client = config.getClient();
            
            _repositories = getGithubManager().getRepositories(client);
        } catch (Throwable t) {
            _logger.info("Error getting the list of the projects");
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String entry() {
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            GitHubClient client = config.getClient();
            
            _branches = getGithubManager().getRepositoryBranches(client, getRepository());
            if (null != _branches
                    && !_branches.isEmpty()) {
                if (StringUtils.isBlank(_headBranch))  {
                    _logger.info("No starting branch given, choosing one automatically");
                    if (_branches.contains(GitHubManager.MASTER_BRANCH)) {
                        _headBranch = GitHubManager.MASTER_BRANCH;
                    } else {
                        _headBranch = _branches.get(0);
                    }
                }
                _logger.info("Opening project with the default branch '{}'", _headBranch);
                _commits = getGithubManager().getCommits(client, getRepository(), _headBranch);
                _logger.info("{} commits found for branch '{}'", _commits.size(), _headBranch);
            } else {
                _logger.info("No branches have been found");
            }
        } catch (Throwable t) {
            _logger.error("Error on project entry", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String newPullRequest() {
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            GitHubClient client = config.getClient();
            
            _branches = getGithubManager().getRepositoryBranches(client,
                    getRepository());
        } catch (Throwable t) {
            _logger.error("Error entering pull request creation view", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String listPullRequest() {
        try {
            GitHubClient client = getClient();
            
            _pullRequests = getGithubManager().getPullRequests(client, getRepository(), GithubSystemConstants.PULL_REQUEST_OPEN);
            
            // set the isMergeable attribute
            for (PullRequest pr: _pullRequests) {
                boolean isMergeable = getGithubManager().isPullRequestMergeable(client, getRepository(), pr.getNumber());
                
                pr.setMergeable(isMergeable);
            }
            
        } catch (Throwable t) {
            _logger.error("Error creating listing request", t);
            return FAILURE;
        }
        return SUCCESS;
    } 

    @Override
    public String createPullRequest() {    
        if (StringUtils.isNotBlank(_headBranch)
                && StringUtils.isNotBlank(_baseBranch)) {
            if (_headBranch.equals(_baseBranch)) {
                addActionError(this.getText("gpgithub.label.msg.same.branch"));
                return INPUT;
            }
        }
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            GitHubClient client = config.getClient();
            
            getGithubManager().createPullRequest(client, getRepository(),
                    _headBranch, _baseBranch,
                    _title, _body);
        } catch (RequestException exception) {
            addActionError(this.getText("gpgithub.label.msg.prerror", new String[] {exception.getMessage()}));
            return INPUT;
        } catch (Throwable t) {
            _logger.error("Error creating pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String mergePullRequest() {
        try {
            GitHubClient client = getClient();
            
            _githubManager.mergePullRequest(client, getRepository(),
                    _pullRequestNumber,
                    _body);
        } catch (RequestException exception) {
            addActionError(this.getText("gpgithub.label.msg.prmergeerror", new String[] {exception.getMessage()}));
            return INPUT;
        }  catch (Throwable t) {
            _logger.error("Error mergin pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String closePullRequest() {
        try {
            GitHubClient client = getClient();
            
            _githubManager.closePullRequest(client, getRepository(),
                    _pullRequestNumber);
        } catch (Throwable t) {
            _logger.error("Error closing pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String reopenPullRequest() {
        try {
            GitHubClient client = getClient();
            
            _githubManager.reopenPullRequest(client, getRepository(),
                    _pullRequestNumber);
        } catch (Throwable t) {
            _logger.error("Error reopening pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String confirmPullRequest() {
        try {
            GithubConfig config = getGithubManager().getConfiguration();
            
            if (StringUtils.isNotBlank(_baseBranch)) {
                String[] toks = _baseBranch.split(":");
                String branchTodoName;
                
                if (toks.length == 2) {
                    branchTodoName = toks[1];
                } else {
                    branchTodoName = _baseBranch;
                }
                setBaseBranch(branchTodoName);
                _logger.info("Will log for Todo named " + branchTodoName);
            }
        } catch (Throwable t) {
            _logger.error("Error confirming pull request", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    /**
     * Get the access data from the configuration
     * @return 
     */
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
    
    public Integer getPullRequestNumber() {
        return _pullRequestNumber;
    }
    
    public void setPullRequestNumber(Integer pullRequestNumber) {
        this._pullRequestNumber = pullRequestNumber;
    }
    
    public String getBaseBranch() {
        return _baseBranch;
    }
    
    public void setBaseBranch(String baseBranch) {
        this._baseBranch = baseBranch;
    }
    
    public String getTitle() {
        return _title;
    }
    
    public void setTitle(String title) {
        this._title = title;
    }
    
    public String getBody() {
        return _body;
    }
    
    public void setBody(String body) {
        this._body = body;
    }
    
    public List<RepositoryCommit> getCommits() {
        return _commits;
    }
    
    public void setCommits(List<RepositoryCommit> commits) {
        this._commits = commits;
    }
    
    public String getHeadBranch() {
        return _headBranch;
    }
    
    public void setHeadBranch(String defaultBranch) {
        this._headBranch = defaultBranch;
    }
    
    public List<String> getBranches() {
        return _branches;
    }
    
    public void setBranches(List<String> branches) {
        this._branches = branches;
    }
    
    public String getRepository() {
        if (StringUtils.isNotBlank(_repository)
                && _repository.contains(" ")) {
            _repository = _repository.replaceAll(" ", "-");
            _logger.warn("Repository renamed to '{}'", _repository);
        }
        return _repository;
    }
    
    public void setRepository(String repository) {
        this._repository = repository;
    }
    
    public List<PullRequest> getPullRequests() {
        return _pullRequests;
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        this._pullRequests = pullRequests;
    }
    
    public Integer getTodoId() {
        return _todoId;
    }

    public void setTodoId(Integer todoId) {
        this._todoId = todoId;
    }
    
    public IGithubManager getGithubManager() {
        return _githubManager;
    }
    
    public void setGithubManager(IGithubManager githubManager) {
        this._githubManager = githubManager;
    }

    public List<String> getRepositories() {
        return _repositories;
    }

    public void setRepositories(List<String> repositories) {
        this._repositories = repositories;
    }
    
    private String _repository;
    private String _headBranch;
    private String _baseBranch;
    private String _title;
    private String _body;
    
    private Integer _pullRequestNumber;
    private Integer _todoId;
    
    private List<String> _branches;
    private List<RepositoryCommit> _commits;
    private List<PullRequest> _pullRequests;
    private List<String> _repositories;
    
//    private List<PullRequest> _pullRequestsClosed;
    
    
    private IGithubManager _githubManager;
}
