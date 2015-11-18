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
package org.entando.entando.plugins.jpenglo.aps.system.services.card;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.persistence.jaxb.JAXBContextProperties;

import org.entando.entando.plugins.jpenglo.aps.system.services.card.model.JaxbBoard;
import org.entando.entando.plugins.jpenglo.aps.system.services.card.model.JaxbList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpenglo.aps.system.EngloSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jptrello.aps.system.TrelloSystemConstants;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.trello4j.model.Card;

/**
 *
 * @author entando
 */
@Aspect
public class EngloCardManager extends AbstractService implements IEngloCardManager {
    
    private Logger _logger = LoggerFactory.getLogger(EngloCardManager.class);
    
    @Override
    public void init() throws Exception {
        _logger.info(this.getClass().getName() + " successfully initialized");
    }
    
    @AfterReturning(pointcut="execution(* org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TodolistManager.createTodo(..)) && args(todo, person, todolist, serviceData)")
    public void onTodoCreation(Todo todo, Person person, Todolist todolist, BasecampService serviceData) throws ApsSystemException {
        try {
            if (null != todo
                    && StringUtils.isNotBlank(todo.getContent())) {
                String cardName = todo.getContent();
                
                _logger.info("loading project id {}", todolist.getProjectId());
                Project project = _projectManager.getProject(todolist.getProjectId(), serviceData);
                String boardName = project.getName();
                _logger.info("get boards by organization...");
                String json = _trelloManager.getJsonBoardByOrganization();
                List<JaxbBoard> boards = parseBoards(json);
                
                // find the proper board
                for (JaxbBoard board: boards) {
                    if (StringUtils.isNotBlank(board.getName())
                            && board.getName().equals(boardName)) {
                        // get the list of the current board
                        json = _trelloManager.getJsonCardsByBoard(board.getId());
                        List<JaxbList> lists = parseBoardLists(json);
                        
                        // create the board in the proper list
                        for (JaxbList list: lists) {
                            String listName = list.getName();
                            
                            if (isBacklog(listName)) {
                                String desc = String.format(EngloSystemConstants.CARD_DESCRIPTION, cardName);
                                
                                _trelloManager.createCard(list.getId(), cardName, desc, "");
                            } else {
                                System.out.println("skipping list " + listName);
                            }
                        }
                    } else {
                        System.out.println("skipping board " + board.getName());
                    }
                }
            } else {
                _logger.error("could not create a card from invalid data");
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error creating card on todo creation", t);
        }
    }
    
    @AfterReturning(pointcut="execution(* org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ProjectManager.createProject(..)) && args(project, serviceData)")
    public void onProjectCreation(Project project, BasecampService serviceData) throws ApsSystemException {
        try {
            String boardName = project.getName();
            if (StringUtils.isNotBlank(boardName)) {
                if (!boardExists(boardName)) {
                    String desc = project.getDescription();
                    
                    _trelloManager.createBoard(boardName, desc, "");
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error creating board on project creation", t); 
        }
    }
    
    @AfterReturning(pointcut="execution(* org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TodolistManager.updateTodo(..)) && args(todo, projectId, serviceData)")
    public void onTodoUpdate(Todo todo, Long projectId, BasecampService serviceData) throws ApsSystemException {
        try {
            if (todo.getCompleted()) {
                _logger.info("loading project id {}", projectId);
                Project project = _projectManager.getProject(projectId, serviceData);
                String boardName = project.getName();
                _logger.info("get boards by organization...");
                String json = _trelloManager.getJsonBoardByOrganization();
                List<JaxbBoard> boards = parseBoards(json);
                
                // find the proper board
                for (JaxbBoard board: boards) {
                    if (StringUtils.isNotBlank(board.getName())
                            && board.getName().equals(boardName)) {
                        List<Card> cards = _trelloManager.getCardsByBoard(board.getId());
                        
                        // find the card to update
                        for (Card card: cards) {
                            if (card.getName().equals(todo.getContent())) {
                                _logger.info("Located card '{}' to move", todo.getContent());
                                List<org.trello4j.model.List> lists = _trelloManager.getListByBoard(board.getId());
                                
                                for (org.trello4j.model.List list: lists) {
                                    if (isDone(list.getName())) {
                                        // move the card
                                        String destinationListId = list.getId();
                                        if (!destinationListId.equals(card.getIdList())) {
                                            _trelloManager.updateCard(card.getId(), destinationListId, null, null, "");
                                            _logger.info("Moving card id '{}' name '{}' into list '{}'",
                                                    card.getId(),
                                                    todo.getContent(),
                                                    destinationListId);
                                            return;
                                        } else {
                                            _logger.info("the card '{}' is ALREADY in the done list", card.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error moving the card in the done list", t);
        }
    }
    
    @Override
    public boolean boardExists(String boardName) throws ApsSystemException {
        boolean exists = false;
        
        try {
            if (StringUtils.isNotBlank(boardName)) {
                String json = _trelloManager.getJsonBoardByOrganization();
                List<JaxbBoard> boards = parseBoards(json);
                for (JaxbBoard board: boards) {
                    if (StringUtils.isNotBlank(board.getName())
                            && board.getName().equals(boardName)) {
                        exists = true;
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error checking whether the board exists", t);
        }
        return exists;
    }
    
    @Override
    public JSONObject getBoardListByName(String boardName) throws ApsSystemException {
        JSONObject list = new JSONObject();
        
        try {
            if (StringUtils.isNotBlank(boardName)) {
                String json = _trelloManager.getJsonBoardByOrganization();
                List<JaxbBoard> boards = parseBoards(json);
                for (JaxbBoard board: boards) {
                    if (StringUtils.isNotBlank(board.getName())
                            && board.getName().equals(boardName)) {
                        // get the list of the current board
                        json = _trelloManager.getJsonCardsByBoard(board.getId());
                        List<JaxbList> lists = parseBoardLists(json);
                        
                        for (JaxbList cur: lists) {
                            list.put(cur.getId(), cur.getName());
                        }
                    }
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting list id by board name", t);
        }
        return list;
    }
        
    /**
     * Check whether the given list name can be used as a Backlog
     * @param name
     * @return 
     */
    @Override
    public boolean isBacklog(String name) {
        
        if (StringUtils.isNotBlank(name)) {
            List<String> list = Arrays.asList(TrelloSystemConstants.TRELLO_DEFAULT_BACKLOG);
            return list.contains(name.toLowerCase());
        }
        return false;
    }
    
    /**
     * Check whether the given list name can be used as a Done list
     * @param name
     * @return 
     */
    @Override
    public boolean isDone(String name) {
        if (StringUtils.isNotBlank(name)) {
            List<String> list = Arrays.asList(TrelloSystemConstants.TRELLO_DEFAULT_DONE);
            return list.contains(name.toLowerCase());
        }
        return false;
    }
    
    @Override
    public JSONObject getBoardListById(String id) throws ApsSystemException {
        JSONObject list = new JSONObject();
        JSONArray arraylist= new JSONArray();
        try {
            if (StringUtils.isNotBlank(id)) {
                String json = _trelloManager.getJsonCardsByBoard(id);
                List<JaxbList> lists = parseBoardLists(json);
                
                for (JaxbList cur: lists) {
                    JSONObject item = new JSONObject();
                    item.put("id",cur.getId());
                    item.put("name",cur.getName());
                    arraylist.put(item);
                }
                list.put("list", arraylist);
            }
            
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting list id by board name", t);
        }
        return list;
    }
    
    private List<JaxbBoard> parseBoards(String json) throws Throwable {
        Map<String, Object> properties = new HashMap<String, Object>(2);
        List<JaxbBoard> boards = new ArrayList<JaxbBoard>();
        StringReader reader = null;
        
        if (StringUtils.isNotBlank(json)) {
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
            JAXBContext boardsJc = JAXBContext.newInstance(new Class[]{JaxbBoard.class}, properties);
            Unmarshaller boardsUnmarshaller = boardsJc.createUnmarshaller();
            reader = new StringReader(json);
            boards = (List<JaxbBoard>)boardsUnmarshaller.unmarshal(new StreamSource(reader), JaxbBoard.class).getValue();
        }
        return boards;
    }
    
    private List<JaxbList> parseBoardLists(String json) throws Throwable {
        Map<String, Object> properties = new HashMap<String, Object>(2);
        List<JaxbList> lists = new ArrayList<JaxbList>();
        StringReader reader = null;
        
        if (StringUtils.isNotBlank(json)) {
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
            JAXBContext listsJc = JAXBContext.newInstance(new Class[]{JaxbList.class}, properties);
            Unmarshaller listsUnmarshaller = listsJc.createUnmarshaller();
            reader = new StringReader(json);
            lists = (List<JaxbList>)listsUnmarshaller.unmarshal(new StreamSource(reader), JaxbList.class).getValue();
        }
        return lists;
    }
    
    public ITrelloClientManager getTrelloManager() {
        return _trelloManager;
    }
    
    public void setTrelloManager(ITrelloClientManager trelloManager) {
        this._trelloManager = trelloManager;
    }
    
    public IProjectManager getProjectManager() {
        return _projectManager;
    }
    
    public void setProjectManager(IProjectManager projectManager) {
        this._projectManager = projectManager;
    }

    public IEngloCardManager getEngloCardManager() {
        return _engloCardManager;
    }

    public void setEngloCardManager(IEngloCardManager engloCardManager) {
        this._engloCardManager = engloCardManager;
    }
    
    private ITrelloClientManager _trelloManager;
    private IProjectManager _projectManager;
    private IEngloCardManager _engloCardManager;
}
