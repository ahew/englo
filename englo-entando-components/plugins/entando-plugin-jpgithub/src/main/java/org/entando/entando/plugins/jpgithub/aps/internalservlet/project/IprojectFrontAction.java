package org.entando.entando.plugins.jpgithub.aps.internalservlet.project;

public interface IprojectFrontAction {
    
    /**
     *
     * @return
     */
    public String entry();
    
    /**
     *
     * @return
     */
    public String createPullRequest();
    
    /**
     *
     * @return
     */
    public String mergePullRequest();
    
    /**
     *
     * @return
     */
    public String closePullRequest();
    
    /**
     *
     * @return
     */
    public String reopenPullRequest();

    /**
     * Entry for the new request trampoline
     * @return 
     */
    public String newPullRequest();
    
    /**
     * List the pull request for the current project
     * @return 
     */
    public String listPullRequest();
    
    
    /**
     * Entry for the merge pull request action
     * @return 
     */
    public String confirmPullRequest();
    
    
    /**
     * Get the list of the projects
     * @return 
     */
    public String list();
    
}
