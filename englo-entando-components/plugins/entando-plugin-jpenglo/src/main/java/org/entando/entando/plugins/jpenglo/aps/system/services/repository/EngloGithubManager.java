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
package org.entando.entando.plugins.jpenglo.aps.system.services.repository;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import java.util.List;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author entando
 */
@Aspect
public class EngloGithubManager extends AbstractService implements IEngloGithubManager {
    
    private Logger _logger = LoggerFactory.getLogger(EngloGithubManager.class);
    
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass().getCanonicalName() + ": initialized");
    }

    @AfterReturning(pointcut="execution(* org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ProjectManager.createProject(..)) && args(project, serviceData)")
    public void onProjectCreation(Project project, BasecampService serviceData) throws ApsSystemException {
        try {
            GitHubClient client = _githubManager.getConfiguration().getClient();
            String name = project.getName();
            String description = project.getDescription();
            
            List<String> repos = _githubManager.getRepositories(client);
            if ((null != repos 
                    && !repos.isEmpty()
                    && !repos.contains(name)
                    // consider projects containig spaces!
                    && !repos.contains(name.replaceAll(" ", "-"))
                    )
                || (null != repos 
                    && repos.isEmpty())) {
                // must create new repository
                _githubManager.createRepository(client, name, description);
                _logger.info("created repository '{}' ", name);
            } else {
                _logger.info("skipped creation of the github repository");
            }
        } catch (Throwable t) {
            _logger.error("error creating repository on new basecamp project", t);
            throw new ApsSystemException("Error creating board on project creation", t); 
        }
    }
    
    
    public IGithubManager getGithubManager() {
        return _githubManager;
    }

    public void setGithubManager(IGithubManager githubManager) {
        this._githubManager = githubManager;
    }
    
    private IGithubManager _githubManager;
     
}
