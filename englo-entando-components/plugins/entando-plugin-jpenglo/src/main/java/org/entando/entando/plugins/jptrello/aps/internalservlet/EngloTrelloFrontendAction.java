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
package org.entando.entando.plugins.jptrello.aps.internalservlet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.entando.entando.plugins.jptrello.aps.internalservlet.trello.TrelloFrontEndAction;
import java.util.List;
import org.entando.entando.plugins.jpenglo.aps.system.services.card.IEngloCardManager;
import org.entando.entando.plugins.jpenglo.aps.system.services.todo.IEngloTodolistManager;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trello4j.model.Board;
import org.trello4j.model.Card;

/**
 *
 * @author entando
 */
public class EngloTrelloFrontendAction extends TrelloFrontEndAction {
    
    private Logger _logger = LoggerFactory.getLogger(EngloTrelloFrontendAction.class);
    
    public String jsonlist() {
        try {
            JSONObject json = _engloCardManager.getBoardListById(getIdBoard());
            this.createStream(json.toString());
            setJsonList(json.toString());
        } catch (Throwable t) {
            _logger.error("Error getting board list", t);
            setOperationOk(false);
            return SUCCESS;
        }
        setOperationOk(true);
        return SUCCESS;
    }
    
    public String updatecard() {
        String boardName = null;
        try {
            this.getTrelloClientManager().updateCard(getIdCard(), getIdList(), getName(), getDesc(), "");
            Card card = getTrelloClientManager().getCardById(getIdCard());
            
            List<Board> boards = getTrelloClientManager().getBoardByOrganization();
            for (Board board: boards) {
                if (board.getId().equals(card.getIdBoard())) {
                    boardName = board.getName();
                    _logger.info("Selecting current board with name '{}'" , board.getName());
                }
            }
            
            List<org.trello4j.model.List> lists = getTrelloClientManager().getListByBoard(card.getIdBoard());
            for (org.trello4j.model.List list: lists) {
                if (list.getId().equals(getIdList())) {
                    if (_engloCardManager.isDone(list.getName())) {
                        _engloTodolistManager.updateTodoOnCardMove(boardName, card.getName(), null);
                        _logger.info("Marked card '{}' in list '{}' as completed", card.getName(), list.getName());
                    }
                }
            }
        } catch (Throwable t) {
            _logger.error("Error updating the Card " + getIdCard(), t);
            setOperationOk(false);
            return SUCCESS;
        }
        setOperationOk(true);
        return SUCCESS;
    }
    
    public String deletecard() throws Exception {
        try {
            this.getTrelloClientManager().deleteCard(getIdCard());
        } catch (Throwable t) {
            _logger.error("Error deleting card '{}'", getIdCard());
            setOperationOk(false);
            return SUCCESS;
        }
        return SUCCESS;
    }
    
    private void createStream(String json) throws Throwable {
        if (null != json) {
            this.setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
        }
    }
    
    public String getJsonList() {
        return _jsonList;
    }
    public void setJsonList(String jsonList) {
        this._jsonList = jsonList;
    }
    
    public boolean isOperationOk() {
        return _operationOk;
    }
    public void setOperationOk(boolean operationOk) {
        this._operationOk = operationOk;
    }
    
    public InputStream getInputStream() {
        return _inputStream;
    }
    protected void setInputStream(InputStream inputStream) {
        this._inputStream = inputStream;
    }
    
    public IEngloCardManager getEngloCardManager() {
        return _engloCardManager;
    }
    public void setEngloCardManager(IEngloCardManager engloCardManager) {
        this._engloCardManager = engloCardManager;
    }
    
    public IEngloTodolistManager getEngloTodolistManager() {
        return _engloTodolistManager;
    }
    public void setEngloTodolistManager(IEngloTodolistManager engloTodolistManager) {
        this._engloTodolistManager = engloTodolistManager;
    }
    
    private String _jsonList;
    private boolean _operationOk = true;
    
    private InputStream _inputStream;
    
    private IEngloCardManager _engloCardManager;
    private IEngloTodolistManager _engloTodolistManager;
    
}
