package org.entando.entando.plugins.jpgithub.aps.system.services.github;

import java.util.List;

import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.entando.entando.plugins.jpoauth2.aps.system.services.service.IOAuth2Manager;

import com.agiletec.aps.system.exception.ApsSystemException;
import org.eclipse.egit.github.core.Repository;

public interface IGithubManager extends IOAuth2Manager {
	
	/**
	 * Get authorization of the current logged user
	 * @param serviceData
	 * @throws Throwable
	 */
	public void getAutorization(GithubConfig serviceData) throws Throwable;

	/**
	 * Return a copy of the configuration
	 * @return
	 * @throws Throwable TODO
	 */
	public GithubConfig getConfiguration() throws Throwable;

	
	/**
	 * Update the Basecamp configuration
	 * @param config
	 * @throws ApsSystemException
	 */
	public void updateConfiguration(GithubConfig config) throws ApsSystemException;

	/**
	 * Reset the endpoints of the given configuration. If no configuration is
	 * not given then the actual configuration will be reset
	 * @param config
	 * @return TODO
	 * @throws ApsSystemException
	 */
	public GithubConfig resetEndpoints(GithubConfig config) throws ApsSystemException;

	/**
	 * Get the list of repository for the given user
	 * @param client
	 * @return
         * @throws ApsSystemException
	 */
	public List<String> getRepositories(GitHubClient client) throws ApsSystemException;

	/**
	 * Get all the branches of a repository.
	 * @param client
	 * @param repo
	 * @return
	 * @throws Throwable if the repository does not exist
	 */
	public List<String> getRepositoryBranches(GitHubClient client, String repo) throws Throwable;

	/**
	 * Get the commits given the repository and the branch
	 * @param client
	 * @param repository
	 * @param branch
	 * @return
	 * @throws ApsSystemException
	 */
	public List<RepositoryCommit> getCommits(GitHubClient client, String repository, String branch) throws ApsSystemException;

	/**
	 * Create a pull request for the given repository
	 * @param client
	 * @param repository
	 * @param head TODO
	 * @param base TODO
	 * @param title TODO
	 * @param body TODO
	 * @return TODO
	 * @throws Throwable
	 */
	public PullRequest createPullRequest(GitHubClient client, String repository, String head, String base, String title, String body) throws Throwable;

	/**
	 * Merge pull request
	 * @param client
	 * @param repository
	 * @param number
	 * @param comment
	 * @return true if the code is mergeable
	 * @throws ApsSystemException
	 */
	public boolean mergePullRequest(GitHubClient client, String repository, int number, String comment) throws Throwable;

	/**
	 * Search for the pull requests
	 * @param client
	 * @param repository
	 * @param status
	 * @return
	 * @throws Throwable
	 */
	public List<PullRequest> getPullRequests(GitHubClient client, String repository, String status) throws Throwable;

	/**
	 * Check whether the pull request is automatically mergeable
	 * @param client
	 * @param repository
	 * @param requestNumber
	 * @param comment
	 * @return
	 * @throws ApsSystemException
	 */
	public boolean isPullRequestMergeable(GitHubClient client, String repository, int requestNumber) throws ApsSystemException;

	/**
	 * Close the pull request
	 * @param client
	 * @param repository
	 * @param requestNumber
	 * @return
	 * @throws ApsSystemException
	 */
	public boolean closePullRequest(GitHubClient client, String repository, int requestNumber) throws Throwable;

	/**
	 * Open again the pull request
	 * @param client
	 * @param repository
	 * @param requestNumber
	 * @return
	 * @throws ApsSystemException
	 */
	public boolean reopenPullRequest(GitHubClient client, String repository, int requestNumber) throws Throwable;

        
        /**
         * Create a new repository
         * @param client
         * @param name
         * @param description
         * @return
         * @throws Throwable 
         */
        public Repository createRepository(GitHubClient client, String name, String description) throws Throwable;
        
        /**
         * Bean ID
         */
        public static final String BEAN_ID = "jpGithubManager";
}
