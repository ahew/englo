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

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
//import org.entando.entando.plugins.jptrello.aps.services.trello.ITrelloClientManager;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ITodolistManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;
//import org.entando.entando.plugins.jpenglo.aps.system.services.card.IEngloCardManager;
import org.entando.entando.plugins.jpgithub.aps.system.services.github.IGithubManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author entando
 */
@Aspect
public class EngloTodolistManager extends AbstractService implements IEngloTodolistManager {
    
    Logger _logger = LoggerFactory.getLogger(EngloTodolistManager.class);
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass() + " initialized");
    }
    
    @Override
    public void updateTodoOnPullRequest(String projectId, String todoContent, BasecampService service) throws ApsSystemException {
        try {
            if (StringUtils.isNotBlank(projectId)) {
                Long pid = Long.valueOf(projectId);
                
                List<TodolistReference> todolistReference = _todolistManager.getTodolists(pid, service);
                
                for (TodolistReference todolist: todolistReference) {
                    Todolist todos = _todolistManager.getTodolist(pid, todolist.getId(), service);
                    // get the pending todos
                    List<TodoItem> remaining = todos.getRemaningList();
                    for (TodoItem todoItem: remaining) {
                        // load the todoDetails if the content is matched
                        if (null != todoItem
                                && StringUtils.isNotBlank(todoItem.getContent())
                                && (todoContent.equals(todoItem.getContent())
                                    // consider whitespaces
                                    || todoContent.replaceAll("-", " ").equals(todoItem.getContent()))) {
                            Todo todo = _todolistManager.getTodo(todoItem, service);

                            // mark the todo completed
                            todo.setCompleted(Boolean.TRUE);
                            // update!
                            _todolistManager.updateTodo(todo, pid, service);
                            _logger.info("Task automatically completed '{}'", todoContent);
                        } else {
                            _logger.info("Discarding todolist {} (looking for {})", todoContent, todoItem.getContent());
                        }
                    }
                }
            } else {
                _logger.error("Method invoked with invalid parameters");
            }
        } catch (Throwable t) {
            _logger.error("Error marking the todo completed ", t);
            throw new ApsSystemException("Error marking the todo completed ");
        }
    }
    
    /*
    @AfterReturning(pointcut="org.entando.entando.plugins.jptrello.aps.services.trello.TrelloClientManager.updateCard(..)) && args(idCard, idList, name, desc, idMembers)")
    public void onCardUpdate(String idCard, String idList, String name, String desc, String idMembers) throws Throwable {
        String boardName = null;
        
        try {
            Card card = getTrelloClientManager().getCardById(idCard);
            
            List<Board> boards = getTrelloClientManager().getBoardByOrganization();
            for (Board board: boards) {
                if (board.getId().equals(card.getIdBoard())) {
                    boardName = board.getName();
                    _logger.info("Selecting current board with name '{}'" , board.getName());
                }
            }
            
            List<org.trello4j.model.List> lists = getTrelloClientManager().getListByBoard(card.getIdBoard());
            for (org.trello4j.model.List list: lists) {
                if (list.getId().equals(idList)) {
                    if (_engloCardManager.isDone(list.getName())) {
                        updateTodoOnCardMove(boardName, card.getName(), null);
                        _logger.info("Marked card '{}' in list '{}' as completed", card.getName(), list.getName());
                    }
                }
            }
        } catch (Throwable t) {
             _logger.error("Error marking the todo completed ", t);
        }
    }
    */
    
    @Override
    public void updateTodoOnCardMove(String projectName, String todoContent, BasecampService service) throws Throwable {
        try {
            if (StringUtils.isNotBlank(projectName)) {
                // find the project given the board name
                List<ProjectReference> projects = _projectManager.getProjects(service);
                for (ProjectReference project: projects) {
                    if (project.getName().equals(projectName)) {
                        long pid = project.getId();
                        
                        _logger.info("Will mark Todo '{}' as completed for project '{}'", todoContent, projectName);
                        updateTodoOnPullRequest(String.valueOf(pid), todoContent, service);
                    } else {
                        _logger.info("Discarding project '{}'", project.getName());
                    }
                }
            } else {
                _logger.error("Method invoked with invalid parameters");
            }
        } catch (Throwable t) {
            _logger.error("Error marking the todo completed ", t);
            throw new ApsSystemException("Error marking the todo completed ");
        }
    }
    
    
    public IGithubManager getGithubManager() {
        return _githubManager;
    }
    
    public void setGithubManager(IGithubManager githubManager) {
        this._githubManager = githubManager;
    }
    
    public ITodolistManager getTodolistManager() {
        return _todolistManager;
    }
    
    public void setTodolistManager(ITodolistManager todolistManager) {
        this._todolistManager = todolistManager;
    }

    public IProjectManager getProjectManager() {
        return _projectManager;
    }

    public void setProjectManager(IProjectManager projectManager) {
        this._projectManager = projectManager;
    }

//    public ITrelloClientManager getTrelloClientManager() {
//        return _trelloClientManager;
//    }
//
//    public void setTrelloClientManager(ITrelloClientManager trelloClientManager) {
//        this._trelloClientManager = trelloClientManager;
//    }
//
//    public IEngloCardManager getEngloCardManager() {
//        return _engloCardManager;
//    }
//
//    public void setEngloCardManager(IEngloCardManager engloCardManager) {
//        this._engloCardManager = engloCardManager;
//    }
    
    
    private IGithubManager _githubManager;
    private ITodolistManager _todolistManager;
    private IProjectManager _projectManager;
//    private ITrelloClientManager _trelloClientManager;
//    private IEngloCardManager _engloCardManager;
    
}
