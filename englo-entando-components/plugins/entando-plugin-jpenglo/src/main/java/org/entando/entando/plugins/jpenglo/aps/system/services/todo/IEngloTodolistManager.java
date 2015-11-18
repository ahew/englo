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
package org.entando.entando.plugins.jpenglo.aps.system.services.todo;

import com.agiletec.aps.system.exception.ApsSystemException;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;

/**
 *
 * @author entando
 */
public interface IEngloTodolistManager {
    
    /**
     * Marks a task with the given content as completed, following a pull request acceptance
     * @param pid
     * @param todoContent
     * @param service
     * @throws ApsSystemException 
     */
    public void updateTodoOnPullRequest(String pid, String todoContent, BasecampService service) throws ApsSystemException;
    
    /**
     * Mark a task with the given content as completed, following a trello updated card
     * @param projectName
     * @param todoContent
     * @param service
     * @throws Throwable 
     */
    public void updateTodoOnCardMove(String projectName, String todoContent, BasecampService service) throws Throwable;
    
    /**
     * Bean ID
     */
    public String BEAN_ID = "jpengloTodolistManager";
}
