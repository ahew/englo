package org.entando.entando.plugins.jptrello.aps.system.services.trello;

import com.agiletec.aps.system.exception.ApsSystemException;
import java.util.List;
import org.trello4j.model.Board;
import org.trello4j.model.Card;
import org.trello4j.model.Member;

public interface ITrelloClientManager {
    
    public static final String BEAN_NAME = "jptrelloTrelloClientManager";
    
    public String getIdOrganization();
    
    public String executeLogin() throws ApsSystemException;

    public String getJsonBoardByOrganization() throws ApsSystemException;
    
    public String getJsonCardsByBoard(String idBoard) throws ApsSystemException ;
    
    public List<Board> getBoardByOrganization() throws ApsSystemException ;
    
    public List<Card> getCardsByBoard(String idBoard) throws ApsSystemException; 
    
    public Card getCardById(String idCard) throws ApsSystemException; 
    
    public String getToken() throws ApsSystemException ;
    
    public List<Member> getMembersByBoard(String idBoard) throws ApsSystemException ;

    public String getJsonCardById(String idCard) throws ApsSystemException ;

    public String getJsonMembersByBoard(String idBoard) throws ApsSystemException ;

    public String getJsonCardMemberById(String idCard) throws ApsSystemException ;

    public String getJsonBoardsByMember() throws ApsSystemException;
    
    public Card createCard(String idList, String name, String desc, String idMembers) throws ApsSystemException;
    
    @Deprecated
    public void updateCardList(String idCard, String idList, String idMembers) throws ApsSystemException;
    
    public Board createBoard(String name, String desc, String idMembers) throws ApsSystemException;
    
    public void updateCard(String idCard, String idList,String name, String desc, String idMembers) throws ApsSystemException;
    
    public List<org.trello4j.model.List> getListByBoard(String idBoard) throws Throwable;
    
    public void deleteCard(String idCard) throws ApsSystemException;
    
    public org.trello4j.model.List createList(String name, String boardId, String pos) throws Throwable;
   
    @Deprecated
    public void closeList(String listId) throws Throwable;
    
    public org.trello4j.model.List updateList(String listId, String name, Boolean closed, String idBoard, String pos, Boolean subscribed) throws Throwable;
    
    public Board updateBoard(String idBoard, String name, String desc, Boolean closed) throws ApsSystemException;
    
    public void updateConfiguration(TrelloConfig config) throws ApsSystemException;
    
    public TrelloConfig getConfiguration();
}
