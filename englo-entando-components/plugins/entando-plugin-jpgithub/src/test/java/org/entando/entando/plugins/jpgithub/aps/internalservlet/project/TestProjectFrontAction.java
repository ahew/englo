/*
* The MIT License
*
* Copyright 2015 Entando Corporation.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package org.entando.entando.plugins.jpgithub.aps.internalservlet.project;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.entando.entando.plugins.jpgithub.IGitHubTestParameters;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.GitHubManager;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;
import org.entando.entando.plugins.jpgithub.apsadmin.ApsAdminPluginBaseTestCase;

/**
 *
 * @author entando
 */
public class TestProjectFrontAction extends ApsAdminPluginBaseTestCase implements IGitHubTestParameters {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testCreatePullRequest1() throws Throwable {
        try {
//            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/FrontEnd/Project", "createPullRequest");
            String result = this.executeAction();
            assertEquals(Action.INPUT, result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void _testCreatePullRequest2() throws Throwable {
        try {
//            this.setUserOnSession("admin");
            this.initAction("/do/jpgithub/FrontEnd/Project", "createPullRequest");
            this.addParameter("repository", TEST_REPOSITORY);
            this.addParameter("title", "TEST_TITLE");
            this.addParameter("body", "TEST_BODY");
            this.addParameter("baseBranch", "b2");
            this.addParameter("headBranch", TEST_REPOSITORY_MASTER);
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    public void testList() throws Throwable {
        try {
            this.initAction("/do/jpgithub/FrontEnd/Project", "list");
            this.addParameter("repository", TEST_REPOSITORY);
            String result = this.executeAction();
            assertEquals(Action.SUCCESS, result);
            ProjectFrontAction action = (ProjectFrontAction) this.getAction();
            List<String> repo = action.getRepositories();
            assertNotNull(repo);
//            for (String cur: repo) {
//                System.out.println("||| " + cur);
//            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    private void init() throws Exception {
        try {
            _githubManager = (IGithubManager) this.getService(GitHubManager.BEAN_ID);
            assertNotNull(_githubManager);
        } catch (Exception e) {
            throw e;
        }
    }
    
    private IGithubManager _githubManager;
    
}
