/*
 *
 * <Your licensing text here>
 *
 */package org.entando.entando.plugins.jptrello.aps.system.services;

import org.entando.entando.plugins.jptrello.aps.JptrelloBaseTestCase;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;
import java.util.List;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.TrelloConfig;
import org.trello4j.model.Board;
import org.trello4j.model.Card;

public class TestTrelloClientManager extends JptrelloBaseTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
        
        public void testConfig() throws Throwable {
            try {
                TrelloConfig config = _trelloManager.getConfiguration();
                
                assertNotNull(config);
                assertEquals("KEY", config.getApiKey());
                assertEquals("SECRET", config.getApiSecret());
                assertEquals("TOKEN", config.getToken());
                assertEquals("ORGANIZATION", config.getOrganization());
            } catch (Throwable t) {
                throw t;
            }
        }
        
         public void testUpdateCardList() throws Throwable {
            try {
                _trelloManager.updateCardList(CARD_ID, CARD_LIST_ID, "");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
         public void testUpdateCard() throws Throwable {
            try {
                _trelloManager.updateCard(CARD_ID, CARD_LIST_ID, CARD_NAME, CARD_DESCRIPTION, "");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
         public void testBoard() throws Throwable {
            String id = null; //  "561bbecb0460f4381888fe14";
            
            try {
                String json = _trelloManager.getJsonBoardByOrganization(); // NEEDED TO RETRIEVE THE ORGANIZATION ID
                Board board = _trelloManager.createBoard(BOARD_NAME, BOARD_DESCRIPTION, "");
                
                assertNotNull(board);
                assertNotNull(board.getId());
                id = board.getId();
                assertNotNull(board.getIdOrganization());
//                System.out.println(">>> id " + board.getId());
//                System.out.println(">>> organization id" + board.getIdOrganization());
//                System.out.println(">>> description " + board.getDesc());
                Thread.sleep(2000);
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                if (null != id) {
                _trelloManager.updateBoard(id, null, null, Boolean.TRUE);
                }
            }
        }
        
         public void testGetBoardByOrganization() throws Throwable {
            try {
                String json = _trelloManager.getJsonBoardByOrganization();
                assertNotNull(json);
//                System.out.println(">>> " + json);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
         public void testGetBoard() throws Throwable {
            try {
                String json = _trelloManager.getJsonBoardByOrganization();
                assertNotNull(json);
//                System.out.println(">>> " + json);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
         public void testCards() throws Throwable {
            try {
                List<Card> cards = _trelloManager.getCardsByBoard(BOARD_ID);
                assertNotNull(cards);
                assertFalse(cards.isEmpty());
//                for (Card card: cards) {
//                    assertEquals(BOARD_ID, card.getIdBoard());
//                    System.out.println("!!! " + card.getName());
//                    System.out.println("!!! " + card.getIdList());
//                    System.out.println("");
//                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
         public void testListByBoard() throws Throwable {
            try {
                List<org.trello4j.model.List> cards = _trelloManager.getListByBoard(BOARD_ID);
                assertNotNull(cards);
                assertFalse(cards.isEmpty());
//                for (org.trello4j.model.List list: cards) {
//                    System.out.println("!!! " + list.getName());
//                    System.out.println("!!! " + list.getId());
//                    System.out.println("!!! " + list.getIdBoard());
//                    System.out.println("!!! " + list.getPos());
//                    System.out.println("");
//                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        
        
         public void testCardCreate() throws Throwable {
            String id = null;
            
            try {
                Card card = _trelloManager.createCard(CARD_LIST_ID, CARD_NAME, CARD_DESCRIPTION, "");
                assertNotNull(card);
                assertNotNull(card.getId());
                id = card.getId();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                Thread.sleep(3000);
                if (null != id) {
                    _trelloManager.deleteCard(id);
                }
            }
        }
        
         public void testCreateList() throws Throwable {
            String id = null; // "5624c48f97d9fe0e17a00bbe";
            
            try {
                org.trello4j.model.List list = _trelloManager.createList(LIST_NAME, BOARD_ID, null);
                
                assertNotNull(list);
                assertNotNull(list.getId());
                id = list.getId();
                assertNotNull(list.getName());
                assertEquals(LIST_NAME, list.getName());
                assertNotNull(list.getIdBoard());
                assertEquals(BOARD_ID, list.getIdBoard());
            } catch (Throwable t) {
                throw t;
            } finally {
                if (null != id) {
                    _trelloManager.updateList(id, null, Boolean.TRUE, null, "65538", null);
                }
            }
        }
        
	private void init() {
                this._trelloManager = (ITrelloClientManager) this.getService("jptrelloTrelloClientManager");
	}
	
//	private IParamsManager _paramsManager;
        private ITrelloClientManager _trelloManager;
        
        public final static String CARD_ID = "56127fa06d5e43c5109384ab";
        public final static String CARD_LIST_ID = "560e40ec3d4de451016b878f";
        public final static String CARD_NAME = "UPDATED NAME";
        public final static String CARD_DESCRIPTION = "TEST UPDATED DESCRIPION";
        
        public final static String BOARD_NAME = "TESTING BOARD";
        public final static String BOARD_ID = "560e40de0053c713ea8111b5";
        public final static String BOARD_DESCRIPTION = "TEST DESCRIPTION";
        
        public final static String LIST_NAME = "TestList";
}

