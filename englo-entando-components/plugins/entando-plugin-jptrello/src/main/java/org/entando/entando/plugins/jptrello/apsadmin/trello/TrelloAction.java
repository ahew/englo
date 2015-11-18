package org.entando.entando.plugins.jptrello.apsadmin.trello;

import com.agiletec.apsadmin.system.BaseAction;
import static com.opensymphony.xwork2.Action.SUCCESS;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trello4j.model.Card;
import org.trello4j.model.Member;

public class TrelloAction extends BaseAction {
    
    private static final Logger _logger = LoggerFactory.getLogger(TrelloAction.class);
    
    public String angular() {
        return SUCCESS;
    }
    
    public String create() {
        try {
            if (this.getId() != null) {
                this.setCard(this.getTrelloClientManager().getCardById(this.getId()));
            }
            this.setMembers(this.getTrelloClientManager().getMembersByBoard(this.getIdBoard()));
        } catch (Throwable t) {
            _logger.error("error in create", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String list() {
        return SUCCESS;
    }
    
    public String board() {
        try {
            this.setCards(this.getTrelloClientManager().getCardsByBoard(this.getId()));
            this.setIdBoard(this.getId());
        } catch (Throwable t) {
            _logger.error("error in board", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String card() {
        try {
            this.setCard(this.getTrelloClientManager().getCardById(this.getId()));
        } catch (Throwable t) {
            _logger.error("error in card", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String jsonboards() throws Exception {
        try {
            String json = this.getTrelloClientManager().getJsonBoardByOrganization();
            this.createStream(json);
            this.setJsonBoards(json);
        } catch (Throwable t) {
            _logger.error("error in jsonboards", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String jsoncards() throws Exception {
        try {
            String json = this.getTrelloClientManager().getJsonCardsByBoard(this.getIdBoard());
            this.createStream(json);
            this.setJsonCards(json);
        } catch (Throwable t) {
            _logger.error("error in jsoncards", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String jsonmembers() throws Exception {
        try {
            String json = this.getTrelloClientManager().getJsonMembersByBoard(this.getIdBoard());
            this.createStream(json);
            this.setJsonMembers(json);
        } catch (Throwable t) {
            _logger.error("error in jsonmembers", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String jsoncard() throws Exception {
        try {
            String json = this.getTrelloClientManager().getJsonCardById(this.getIdCard());
            this.createStream(json);
            this.setJsonCard(json);
        } catch (Throwable t) {
            _logger.error("error in jsoncard", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String jsoncardmember() throws Exception {
        try {
            String json = this.getTrelloClientManager().getJsonCardMemberById(this.getIdCard());
            this.createStream(json);
            this.setJsonCard(json);
        } catch (Throwable t) {
            _logger.error("error in jsoncardmember", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String addcard() throws Exception {
        try {
            this.getTrelloClientManager().createCard(this.getIdList(),
                    this.getName(),
                    this.getDesc(),
                    this.getIdMembers());
        } catch (Throwable t) {
            _logger.error("error in addcard", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    private void createStream(String json) throws Throwable {
        if (null != json) {
            this.setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
        }
    }
    
    public String getId() {
        return _id;
    }
    public void setId(String id) {
        this._id = id;
    }
    
    public List<Card> getCards() {
        return _cards;
    }
    public void setCards(List<Card> _cards) {
        this._cards = _cards;
    }
    
    public Card getCard() {
        return _card;
    }
    public void setCard(Card _card) {
        this._card = _card;
    }
    
    public List<Member> getMembers() {
        return _members;
    }
    public void setMembers(List<Member> _members) {
        this._members = _members;
    }
    
    public String getIdBoard() {
        return _idBoard;
    }
    public void setIdBoard(String _idBoard) {
        this._idBoard = _idBoard;
    }
    
    public String getIdCard() {
        return _idCard;
    }
    public void setIdCard(String _idCard) {
        this._idCard = _idCard;
    }
    
    public String getJsonCards() {
        return _jsonCards;
    }
    public void setJsonCards(String _jsonCards) {
        this._jsonCards = _jsonCards;
    }
    
    public String getJsonBoards() {
        return _jsonBoards;
    }
    public void setJsonBoards(String _jsonBoards) {
        this._jsonBoards = _jsonBoards;
    }
    
    public String getJsonMembers() {
        return _jsonMembers;
    }
    public void setJsonMembers(String _jsonMembers) {
        this._jsonMembers = _jsonMembers;
    }
    
    public String getJsonCard() {
        return _jsonCard;
    }
    public void setJsonCard(String _jsonCard) {
        this._jsonCard = _jsonCard;
    }
    
    public String getName() {
        return _name;
    }
    public void setName(String _name) {
        this._name = _name;
    }
    
    public String getDesc() {
        return _desc;
    }
    public void setDesc(String _desc) {
        this._desc = _desc;
    }
    
    public String getIdList() {
        return _idList;
    }
    public void setIdList(String _idList) {
        this._idList = _idList;
    }
    
    public String getIdMembers() {
        return _idMembers;
    }
    public void setIdMembers(String _idMembers) {
        this._idMembers = _idMembers;
    }
    
    public Map getData() {
        return _data;
    }
    public void setData(Map _data) {
        this._data = _data;
    }
    
    public InputStream getInputStream() {
        return _inputStream;
    }
    protected void setInputStream(InputStream inputStream) {
        this._inputStream = inputStream;
    }
    
    protected ITrelloClientManager getTrelloClientManager() {
        return _trelloClientManager;
    }
    public void setTrelloClientManager(ITrelloClientManager trelloClientManager) {
        this._trelloClientManager = trelloClientManager;
    }
    
    private List<Member> _members;
    private Card _card;
    private List<Card> _cards;
    private String _id;
    private String _idBoard;
    private String _idCard;
    
    private String _jsonBoards;
    private String _jsonCards;
    private String _jsonMembers;
    private String _jsonCard;
    
    private Map _data = new HashMap();
    
    private String _name;
    private String _desc;
    private String _idList;
    private String _idMembers;
    
    private InputStream _inputStream;
    
    private ITrelloClientManager _trelloClientManager;
    
}
