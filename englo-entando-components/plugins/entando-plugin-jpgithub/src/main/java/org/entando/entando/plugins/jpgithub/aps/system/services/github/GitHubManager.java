package org.entando.entando.plugins.jpgithub.aps.system.services.github;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.entando.entando.plugins.jpgithub.aps.system.GithubSystemConstants;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.model.AbstractOAuth2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;

public class GitHubManager extends AbstractService implements IGithubManager {
    
    Logger _logger = LoggerFactory.getLogger(GitHubManager.class);
    
    @Override
    public String getAuthorizationNewURL(String callback, String state) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public HttpPost getAuthenticationUrlPost(String callback, String code) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void postLogin(AbstractOAuth2Service serviceData) {
        // TODO Auto-generated method stub
    }
    
    @Override
    public AbstractOAuth2Service refreshAccess(AbstractOAuth2Service currentToken) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void retire() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public AbstractOAuth2Service processAnswer(HttpResponse response) throws Throwable {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void init() throws Exception {
        try {
            loadConfig();
            _logger.info(this.getClass() + " initialized");
        } catch (Throwable t) {
            _logger.error("error loading configuration item '{}'" , GithubSystemConstants.GITHUB_CONFIG);
        }
    }
    
    @Override
    public void getAutorization(GithubConfig serviceData) throws Throwable {
        // TODO Auto-generated method stub
    }
    
    private void loadConfig() throws ApsSystemException {
        try {
            ConfigInterface configManager = this.getConfigManager();
            String xml = configManager.getConfigItem(GithubSystemConstants.GITHUB_CONFIG);
            _config = new GithubConfig(xml);
        } catch (Throwable t) {
            _logger.error("Error loading configuration", t);
            throw new ApsSystemException("Error in loading github configuration", t);
        }
    }
    
    @Override
    public void updateConfiguration(GithubConfig config) throws ApsSystemException {
        try {
            String xml = config.toXml();
            this.getConfigManager().updateConfigItem(GithubSystemConstants.GITHUB_CONFIG, xml);
            this.setConfig(config);
        } catch (Throwable t) {
            _logger.error("Error in updating configuration", t);
            throw new ApsSystemException("Error updating Github configuration");
        }
    }
    
    public List<RepositoryCommit> getCommits(GitHubClient client, String repository) throws ApsSystemException {
        return getCommits(client, repository, MASTER_BRANCH);
    }
    
    @Override
    public List<RepositoryCommit> getCommits(GitHubClient client, String repository, String branch) throws ApsSystemException {
        List<RepositoryCommit> commits = new ArrayList<RepositoryCommit>();
        try {
            if (null != client
                    && StringUtils.isNotBlank(repository)
                    && StringUtils.isNotBlank(branch)) {
                CommitService service = new CommitService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    IRepositoryIdProvider repoId = new RepositoryId(username, repository);
                    commits = service.getCommits(repoId, branch, null);
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting repository commits",t);
        }
        return commits;
    }
    
    public List<RepositoryCommit> getCommits(GitHubClient client, String repository, String branch, String path) throws ApsSystemException {
        List<RepositoryCommit> commits = new ArrayList<RepositoryCommit>();
        try {
            if (null != client
                    && StringUtils.isNotBlank(repository)
                    && StringUtils.isNotBlank(branch)) {
                CommitService service = new CommitService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    IRepositoryIdProvider repoId = new RepositoryId(username, repository);
                    commits = service.getCommits(repoId, branch, path);
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting repository commits",t);
        }
        return commits;
    }
    
    @Override
    public List<String> getRepositoryBranches(GitHubClient client, String repo) throws Throwable {
        List<String> branches = new ArrayList<String>();
        
        try {
            if (null != client
                    && StringUtils.isNotBlank(repo)) {
                RepositoryService service = new RepositoryService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    IRepositoryIdProvider repoId = new RepositoryId(username, repo);
                    List<RepositoryBranch> ghBranches = service.getBranches(repoId);
                    
                    for (RepositoryBranch branch: ghBranches) {
                        branches.add(branch.getName());
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting list of repository branch", t);
        }
        return branches;
    }
    
    @Override
    public List<String> getRepositories(GitHubClient client) throws ApsSystemException {
        List<String> repositories = new ArrayList<String>();
        
        try {
            if (null != client) {
                RepositoryService service = new RepositoryService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    for (Repository repo : service.getRepositories(username)) {
                        repositories.add(repo.getName());
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting list of repositories");
        }
        return repositories;
    }
    
    @Override
    public Repository createRepository(GitHubClient client, String name, String description) throws Throwable {
        Repository created = null;
        
        try {
            if (StringUtils.isNotBlank(name)
                    && null != client) {
                if (name.contains(" ")) {
                    name = name.replaceAll(" ", "-");
                    _logger.warn("Repository will be created with the name '{}'", name);
                }
                RepositoryService service = new RepositoryService(client);
                Repository repository = new Repository();
                
                repository.setName(name);
                repository.setDescription(description);
                
                created = service.createRepository(repository);
            }
        } catch (Throwable t) {
            _logger.error("error creating repository '{}'", name);
            throw t;
        }
        return created;
    }
    
    @Override
    public PullRequest createPullRequest(GitHubClient client, String repository,
            String head, String base,
            String title, String body) throws Throwable {
        PullRequest request = null;
        
        try {
            if (null != client
                    && StringUtils.isNotBlank(repository)
                    && StringUtils.isNotBlank(head)
                    && StringUtils.isNotBlank(base)
                    && StringUtils.isNotBlank(title)
                    && StringUtils.isNotBlank(body)) {
                PullRequestService service = new PullRequestService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
//					IRepositoryIdProvider repo = new RepositoryId(username, repository);
                    request = new PullRequest();
                    PullRequestMarker headMarker = new PullRequestMarker();
                    PullRequestMarker baseMarker = new PullRequestMarker();
                    UserService us = new UserService(client);
                    User user = us.getUser(username);
                    RepositoryService rs = new RepositoryService(client);
                    Repository repo = rs.getRepository(username, repository);
                    
                    // prepare head
                    headMarker.setRef(head);
                    headMarker.setUser(user);
                    headMarker.setRepo(repo);
                    headMarker.setLabel(head);
                    
                    // prepare base
                    baseMarker.setRef(base);
                    baseMarker.setUser(user);
                    baseMarker.setRepo(repo);
                    baseMarker.setLabel(base);
                    // prepare request
                    request.setBody(body);
                    request.setTitle(title);
                    request.setHead(baseMarker);
                    request.setBase(headMarker);
                    request = service.createPullRequest(repo, request);
                }
            } else {
                // TODO throw runetime exception
            }
        } catch (Throwable t) {
            _logger.error("Error creating pull request {}", t.getMessage());
            throw t;
        }
        return request;
    }
    
    @Override
    public boolean mergePullRequest(GitHubClient client, String repository, int requestNumber, String comment) throws Throwable {
        return handlePullRequest(client, repository, requestNumber, comment, PULLREQUEST_STATE.MERGE);
    }
    
    @Override
    public boolean closePullRequest(GitHubClient client, String repository, int requestNumber) throws Throwable {
        return handlePullRequest(client, repository, requestNumber, null, PULLREQUEST_STATE.CLOSE);
    }
    
    @Override
    public boolean reopenPullRequest(GitHubClient client, String repository, int requestNumber) throws Throwable {
        return handlePullRequest(client, repository, requestNumber, null, PULLREQUEST_STATE.OPEN);
    }
    
    private boolean handlePullRequest(GitHubClient client, String repository, int requestNumber, String comment, PULLREQUEST_STATE state) throws Throwable {
        boolean mergeable = false;
        
        try {
            if (null != client
                    && StringUtils.isNotBlank(repository)
//					&& StringUtils.isNotBlank(head)
//					&& StringUtils.isNotBlank(base)
//					&& StringUtils.isNotBlank(title)
//					&& StringUtils.isNotBlank(body)
                    ) {
                PullRequestService service = new PullRequestService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    RepositoryId repo = new RepositoryId(username, repository);
                    
                    PullRequest pullRequest = service.getPullRequest(repo, requestNumber);
                    if (state == PULLREQUEST_STATE.MERGE) {
                        // check whether the code can be merged
                        if (pullRequest.isMergeable()) {
                            service.merge(repo, requestNumber, comment);
                        } else {
                            _logger.info("The code for pull request#{} can't be automatically merged", requestNumber);
                        }
                        // merge!
                    } else {
                        if (state == PULLREQUEST_STATE.CLOSE) {
                            _logger.info("Closing pull request #{} (previous state was '{}')", requestNumber, pullRequest.getState());
                            // close
                            pullRequest.setState("Close");
                            pullRequest.setClosedAt(new Date());
                        } else {
                            pullRequest.setState("open");
                            _logger.info("Opening pull request #{} (previous state was '{}')", requestNumber, pullRequest.getState());
                        }
                        service.editPullRequest(repo, pullRequest);
                    }
                    mergeable = true;
                }
            }
        } catch (Throwable t) {
            _logger.error("Error handling pull request", t.getLocalizedMessage());
            throw t;
        }
        return mergeable;
    }
    
    @Override
    public boolean isPullRequestMergeable(GitHubClient client, String repository, int requestNumber) throws ApsSystemException {
        boolean mergeable = false;
        
        try {
            if (null != client
                    && StringUtils.isNotBlank(repository)
//					&& StringUtils.isNotBlank(head)
//					&& StringUtils.isNotBlank(base)
//					&& StringUtils.isNotBlank(title)
//					&& StringUtils.isNotBlank(body)
                    ) {
                PullRequestService service = new PullRequestService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    RepositoryId repo = new RepositoryId(username, repository);
                    // check whether the code can be merged
                    if (service.getPullRequest(repo, requestNumber).isMergeable()) {
                        mergeable = true;
                    } else {
                        _logger.info("The code for pull request#{} can't be automatically merged", requestNumber);
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("error merging pull request", t);
        }
        return mergeable;
    }
    
    @Override
    public List<PullRequest> getPullRequests(GitHubClient client, String repository, String status) throws Throwable {
        List<PullRequest> list = new ArrayList<PullRequest>();
        
        try {
            if (null != client) {
                PullRequestService service = new PullRequestService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    list = service.getPullRequests(new SearchRepository(username, repository), status);
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error searching for pull requests", t);
        }
        return list;
    }
    
    @Override
    public GithubConfig getConfiguration() throws Throwable {
        GithubConfig config = new GithubConfig(_config.toXml());
        
        return config;
    }
    
    @Override
    public GithubConfig resetEndpoints(GithubConfig config) throws ApsSystemException {
        // TODO Auto-generated method stub
        return null;
    }
    
    public GithubConfig getConfig() {
        return _config;
    }
    
    public void setConfig(GithubConfig config) {
        this._config = config;
    }
    
    public ConfigInterface getConfigManager() {
        return _configManager;
    }
    
    public void setConfigManager(ConfigInterface configmanager) {
        this._configManager = configmanager;
    }
    
    private GithubConfig _config;
    private ConfigInterface _configManager;
    
    // default name of the master branch
    public static final String MASTER_BRANCH = "master";
    
    public enum PULLREQUEST_STATE {
        MERGE,
        OPEN, // REOPEN actually
        CLOSE
    }
}
