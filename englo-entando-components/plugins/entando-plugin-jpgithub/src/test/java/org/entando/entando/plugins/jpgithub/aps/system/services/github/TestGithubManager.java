package org.entando.entando.plugins.jpgithub.aps.system.services.github;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.entando.entando.plugins.jpgithub.IGitHubTestParameters;
import org.entando.entando.plugins.jpgithub.aps.ApsPluginBaseTestCase;

public class TestGithubManager extends ApsPluginBaseTestCase implements IGitHubTestParameters {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        init();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
//	public void _testUnmannedAuth() throws Throwable {
//		try {
//			_githubManager.createNewAuthorization();
//		} catch (Throwable t) {
//			throw t;
//		}
//	}
    
    public void testRepositoryList1() throws Throwable {
        try {
            List<String> list = _githubManager.getRepositories(null);
            assertNotNull(list);
            assertTrue(list.isEmpty());
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testRepositoryList2() throws Throwable {
        try {
            
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            List<String> list = _githubManager.getRepositories(client);
            assertNotNull(list);
            assertFalse(list.isEmpty());
            
//			for (String repo: list) {
//				System.out.println(">>> " + repo);
//			}
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    
    // throw an exception if invalid repository is used
    public void testBranch1() throws Throwable {
        boolean isOk = false;
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            try {
                List<String> branches = _githubManager.getRepositoryBranches(client, "meh");
            } catch (Throwable t) {
                isOk = true;
            }
            assertTrue(isOk);
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testBranch2() throws Throwable {
        
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            List<String> branches = _githubManager.getRepositoryBranches(client, null);
            assertNotNull(branches);
            assertTrue(branches.isEmpty());
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testBranch3() throws Throwable {
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            List<String> list = _githubManager.getRepositories(client);
            assertNotNull(list);
            assertFalse(list.isEmpty());
            
            String reponame = list.get(0);
            
            List<String> branches = _githubManager.getRepositoryBranches(client, reponame);
            assertNotNull(branches);
            assertFalse(branches.isEmpty());
            
//			for (String branch: branches) {
//				System.out.println("@@@ " + branch);
//			}
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void _testGetCommit1() throws Throwable {
        try {
            
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            List<RepositoryCommit> commits = _githubManager.getCommits(client, null, "develop");
            assertNotNull(commits);
            assertTrue(commits.isEmpty());
            
            commits = _githubManager.getCommits(client, "meh!_repo", null);
            assertNotNull(commits);
            assertTrue(commits.isEmpty());
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void _testGetCommit3() throws Throwable {
        try {
            
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            List<String> list = _githubManager.getRepositories(client);
            assertNotNull(list);
            assertFalse(list.isEmpty());
            
            String reponame = list.get(0);
            List<RepositoryCommit> commits = _githubManager.getCommits(client, reponame, "develop");
            assertNotNull(commits);
            assertFalse(commits.isEmpty());
            
//			for (RepositoryCommit commit: commits) {
//				System.out.println("§§§ " + commit.getCommit().getMessage());
//			}
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testGetPullRequests() throws Throwable {
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            List<PullRequest> list = _githubManager.getPullRequests(client, TEST_REPOSITORY, "open");
            assertNotNull(list);
//            System.out.println(">>> " + list.size());
//            for (PullRequest preq: list) {
//                System.out.println("@@@ " + preq.getCreatedAt() + " " + preq.getBody() + " " + preq.getTitle());
//            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void _testCreatePullRequest() throws Throwable {
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            PullRequest request = _githubManager.createPullRequest(client, TEST_REPOSITORY, "master", "b1", "Title", "Body");
            assertNotNull(request);
            assertTrue(request.getId() > 0);
            assertTrue(request.getNumber() > 0);
            
            System.out.println(">>> " + request.getId()); 			// real ID
            System.out.println(">>> " + request.getNumber());		// progress number
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void _testMergePullRequest() throws Throwable {
        try {
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            _githubManager.mergePullRequest(client, TEST_REPOSITORY, 6, "newest merge comment");
//			_githubManager.reopenPullRequest(client, TEST_REPOSITORY, 6);
//			_githubManager.closePullRequest(client, TEST_REPOSITORY, 6);
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testCreateRepository() throws Throwable {
        try {
            
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            _githubManager.createRepository(client, TEST_REPO_NAME, TEST_REPO_DESCRIPTION);
        } catch (Throwable t) {
            throw t;
        }
    }
    
    public void testGetPullRequest() throws Throwable {
        try {
            
            // leave if settings not completed
            if (!StringUtils.isNotBlank(TEST_USER_ID)
                    || !StringUtils.isNotBlank(TEST_USER_PASSWORD)) {
                return;
            }
            
            GitHubClient client = new GitHubClient();
            client.setCredentials(TEST_USER_ID, TEST_USER_PASSWORD);
            
            if (null != client) {
                PullRequestService service = new PullRequestService(client);
                String username = client.getUser();
                
                if (StringUtils.isNotBlank(username)) {
                    PullRequest request=service.getPullRequest(new SearchRepository(TEST_USER_ID, TEST_REPOSITORY), 5);
                    
                    assertNotNull(request);
//					System.out.println("*** " + request.getTitle());
//					System.out.println("*** " + request.getBody());
//					System.out.println(">>> " + request.getHead().getRef());
//					System.out.println("--- " + request.getHead().getLabel());
//					System.out.println("--- " + request.getHead().getRepo().getName());
//					System.out.println("--- " + request.getHead().getUser().getLogin());
//					System.out.println(">>> " + request.getBase().getRef());
//					System.out.println("--- " + request.getBase().getLabel());
//					System.out.println("--- " + request.getBase().getRepo().getName());
//					System.out.println("--- " + request.getBase().getUser().getLogin());
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    private void init() {
        _githubManager = (IGithubManager) super.getService(GitHubManager.BEAN_ID);
    }
    
    private IGithubManager _githubManager;
    
}
