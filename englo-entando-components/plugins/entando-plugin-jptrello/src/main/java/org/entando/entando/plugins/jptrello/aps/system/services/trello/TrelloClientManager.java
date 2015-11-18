package org.entando.entando.plugins.jptrello.aps.system.services.trello;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.system.services.keygenerator.IKeyGeneratorManager;
import com.google.gson.Gson;
import org.entando.entando.plugins.jptrello.aps.system.services.params.IParamsDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jptrello.aps.system.TrelloSystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trello4j.Trello;
import org.trello4j.TrelloImpl;
import org.trello4j.model.Board;
import org.trello4j.model.Card;
import org.trello4j.model.CustomList;
import org.trello4j.model.Member;
import org.trello4j.model.Organization;

public class TrelloClientManager extends AbstractService implements ITrelloClientManager{
    
    private static final Logger _logger =  LoggerFactory.getLogger(TrelloClientManager.class);
        
    @Override
    public void init() throws Exception {
        try {
            loadConfig();
            _logger.info(this.getClass() + " initialized");
            getToken();
        } catch (Throwable t) {
            _logger.error("Error statrting ", t);
        }
    }
    
    protected void loadConfig() throws ApsSystemException {
        try {
            ConfigInterface configManager = this.getConfigManager();
            String xml = configManager.getConfigItem(TrelloSystemConstants.TRELLO_CONFIG);
            
            _configuration = new TrelloConfig(xml);
            
        } catch (Throwable t) {
            _logger.error("Error in loadConfig", t);
            throw new ApsSystemException("Error in loadConfig", t);
        }
    }
    
    @Override
    public void updateConfiguration(TrelloConfig config) throws ApsSystemException {
        try {
            String xml = config.toXml();
            
            this.getConfigManager().updateConfigItem(TrelloSystemConstants.TRELLO_CONFIG, xml);
            this.setConfiguration(config);
        } catch (Throwable t) {
            _logger.error("Error in loadConfig", t);
            throw new ApsSystemException("Error updating Trello configuration", t);
        }
    }
    
    @Override
    public String executeLogin() throws ApsSystemException {
        String token = null;
        return token;
    }
    
    @Override
    public String getToken() throws ApsSystemException {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpClientContext localContext = HttpClientContext.create();
//        TrelloConfig config = getConfiguration();
//        
//        try {
//            String url = String.format(TOKEN_URL, 
//                    config.getApiKey(),
//                    config.getOrganization());
//            HttpGet httpGet = new HttpGet(url);
//            _logger.debug("Querying '{}' for projects", httpGet.getRequestLine());
//            HttpResponse response = httpclient.execute(httpGet, localContext);
//            
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                _logger.debug("status OK");
//                InputStream ris = response.getEntity().getContent();
//                String body = IOUtils.toString(ris, "UTF-8");
//                
//                System.out.println("BODY " + body);
//            } else {
//                _logger.error("server reported error status '{}'", response.getStatusLine().getStatusCode());
//                throw new ApsSystemException("HTTP status error while querying projects\n");
//            }
//            
//        } catch (Throwable ex) {
//            java.util.logging.Logger.getLogger(TrelloClientManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }
    
    @Override
    public List<Board> getBoardByOrganization() throws ApsSystemException {
        _logger.info("invoked getBoardByOrganization");
        
        Trello trello = prepareClient();
        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        List<Board> boards = trello.getBoardsByOrganization(org.getId(), null);
        return boards;
    }
    
    @Override
    public List<Card> getCardsByBoard(String idBoard) throws ApsSystemException {
        _logger.info("invoked getCardsByBoard");
        
        Trello trello = prepareClient();
//        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        List<Card> cards = trello.getCardsByBoard(idBoard, null);
        return cards;
    }
    
    public Card getCardById(String idCard) throws ApsSystemException {
        _logger.info("invoked getCardById");
        
        Trello trello = prepareClient();
        Card card = trello.getCard(idCard);
        return card;
    }
    
    // FIXME setIdOrganization only works when the organization has AT LEAST ONE BOARD
    @Override
    public String getJsonBoardByOrganization() throws ApsSystemException {
        _logger.info("invoked getJsonBoardByOrganization");
        
        Trello trello = prepareClient();
        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        List<Board> boards = trello.getBoardsByOrganization(org.getId(), null);
        
        if (null != boards
                && !boards.isEmpty()) {
            setIdOrganization(boards.get(0).getIdOrganization());
            _logger.info("Current organization ID is '{}'", boards.get(0).getIdOrganization());
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(boards);

        return json;
    }
    
    @Override
    public String getJsonCardsByBoard(String idBoard) throws ApsSystemException {
        _logger.info("invoked getJsonCardsByBoard");
        Trello trello = prepareClient();
        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        List<org.trello4j.model.List> lists = trello.getListByBoard(idBoard, null);
        List<CustomList> boardLists = new ArrayList<CustomList>();
        for(org.trello4j.model.List list : lists){
            CustomList cl = new CustomList();
            cl.setId(list.getId());
            cl.setClosed(list.isClosed());
            cl.setIdBoard(list.getIdBoard());
            cl.setName(list.getName());
            cl.setPos(list.getPos());
            cl.setCards(trello.getCardsByList(list.getId(), null));
            boardLists.add(cl);
        }
        Gson gson = new Gson();
        String json = gson.toJson(boardLists);
        return json;
    }
    
    public String getEffortVsEstimationValues(String idBoard, List<String> listIds) throws ApsSystemException {
        StringBuilder result = new StringBuilder("{");
        double effort = 0.0;
        double estimation = 0.0;
        
        try {
            List<Card> cards = getCardsByBoard(idBoard);
            for (Card card: cards) {
                if (null == listIds
                        || listIds.isEmpty()
                        || listIds.contains(card.getIdList())) {
                    _logger.info("evaluating story points vs effort for the card '{}' list '{}'", card.getName(), card.getIdList());
                    // TODO valuatazione card
                }
            }
        } catch (Throwable t) {
            throw new ApsSystemException("Error getting effort/estimation values", t);
        }
        // finally
        result.append("\"estimation\":");
        result.append(String.valueOf(estimation));
        result.append(",");
        result.append("\"effort\":");
        result.append(String.valueOf(effort));
        result.append("}");
        return result.toString();
    }
    
    @Override
    public List<org.trello4j.model.List> getListByBoard(String idBoard) throws Throwable {
        Trello trello = prepareClient();
        return trello.getListByBoard(idBoard, null);
    }
    
    
    public String getBoards() throws ApsSystemException {
        _logger.info("invoked getBoards");
        Trello trello = prepareClient();
        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        List<Board> boards = new ArrayList<Board>();
        Gson gson = new Gson();
        String json = gson.toJson(boards);
        return json;
    }
    
    public String getJsonBoardsByMember() throws ApsSystemException {
        _logger.info("invoked getJsonBoardsByMember");       
        Trello trello = prepareClient();
//        Token token = getToken();
        Organization org = trello.getOrganization(getConfiguration().getOrganization(), null);
        //Member member = trello.getMemberByToken(tokenReadWrite, null);
        Member member = trello.getMemberByToken(getToken(), null);
        
        List<Board> boards = trello.getBoardsByMember(member.getId(), null);
        Gson gson = new Gson();
        String json = gson.toJson(boards);
//        System.out.println(json);
        return json;
    }
    
    @Override
    public Card createCard(String idList, String name, String desc, String idMembers) throws ApsSystemException {
        Map m = new HashMap();
        m.put("desc", desc);
        m.put("idMembers", idMembers);
   
        Trello trello = prepareClient();
        Card card = trello.createCard(idList, name, m);
        return card;
    }
    
    @Override
    public void deleteCard(String idCard) throws ApsSystemException {
        Trello trello = prepareClient();
        Card card = trello.deleteCard(idCard);
    }
    
    @Override
    public Board createBoard(String name, String desc, String idMembers) throws ApsSystemException {
        Map m = new HashMap();
        
        m.put("desc", desc);
        if (StringUtils.isNotBlank(idOrganization)) {
            _logger.info("Defaulting to rganization Id '{}'", idOrganization);
            m.put("idOrganization", idOrganization);
        }
        m.put("idMembers", idMembers);
        
        Trello trello = prepareClient();
        Board board = trello.createBoard(name, m);
        if (null != board
                && null != board.getIdOrganization()) {
            setIdOrganization(board.getIdOrganization());
            _logger.info("Current organization ID is '{}'", board.getIdOrganization());
        }
        return board;
    }
    
    @Override
    public Board updateBoard(String idBoard, String name, String desc, Boolean closed) throws ApsSystemException {
        Map m = new HashMap();
        Board board = null;
        _logger.info("invoked updateCard");
        
        try {
            Trello trello = prepareClient();
            if (StringUtils.isNotBlank(name)) {
                m.put("name", name);
            } 
            if (StringUtils.isNotBlank(desc)) {
                m.put("desc", desc);
            }
            if (null != closed) {
                m.put("closed", String.valueOf(closed));
            }
            
            board = trello.updateBoard(idBoard, m);
        } catch (Throwable t) {
            _logger.error("Error updating board '{}'", idBoard, t);
        }
        return board;
    }
    
    @Deprecated
    @Override
    public void updateCardList(String idCard, String idList, String idMembers) throws ApsSystemException {
        Map m = new HashMap();
        Card card = null;
        
         _logger.info("invoked updateCardList");
        try {
            Trello trello = prepareClient();
            m.put("idList", idList);
            m.put("idMembers", idMembers);
            card = trello.updateCardList(idCard, idList, m);
        } catch (Throwable t) {
            _logger.error("Error updating the card list", t);
        }
    }
   
    @Override
    public void updateCard(String idCard, String idList, String name, String desc, String idMembers) throws ApsSystemException {
        Map m = new HashMap();
        Card card = null;
        _logger.info("invoked updateCard");
        
        try {
            Trello trello = prepareClient();
            if (StringUtils.isNotBlank(name)) {
                m.put("name", name);
            } 
            if (StringUtils.isNotBlank(desc)) {
                m.put("desc", desc);
            }
            if (StringUtils.isNotBlank(idList)) {
                m.put("idList", idList);
            }
            if (null != idMembers
                    && StringUtils.isNotBlank(idMembers)) {
                m.put("idMembers", idMembers);
            }
            card = trello.updateCard(idCard, idList, desc, m);
        } catch (Throwable t) {
            _logger.error("Error updating", t);
        }
    }
    
    @Override
    public org.trello4j.model.List createList(String name, String boardId, String pos) throws Throwable {
        org.trello4j.model.List list = null;
        Map m = new HashMap();
        
        try {
            Trello trello = prepareClient();
            
            m.put("name", name);
            m.put("idBoard", boardId);
            if (StringUtils.isNotBlank(pos)) {
                m.put("pos", pos);
            }
            
            list = trello.createList(name, m);
        } catch (Throwable t) {
            _logger.error("Error creating list '{}' in the board '{}'", name, boardId, t);
        }
        return list;
    }
    
    @Deprecated
    @Override
    public void closeList(String listId) throws Throwable {
        org.trello4j.model.List list = null;
        Map m = new HashMap();
        
        try {
            Trello trello = prepareClient();
            
            m.put("value", "true");
            
            list = trello.closeList(listId, m);
        } catch (Throwable t) {
            _logger.error("Error ", t);
        }
    }
    
    @Override
    public org.trello4j.model.List updateList(String listId, 
            String name,
            Boolean closed,
            String idBoard,
            String pos,
            Boolean subscribed) throws Throwable {
        org.trello4j.model.List list = null;
        Map m = new HashMap();
        
        try {
            Trello trello = prepareClient();
            
            if (StringUtils.isNotBlank(name)) {
                m.put("name", name);
            }
            if (null != closed) {
                m.put("closed", String.valueOf(closed));
            }
            if (StringUtils.isNotBlank(idBoard)) {
                m.put("idBoard", idBoard);
            }
            if (StringUtils.isNotBlank(pos)) {
                m.put("pos", pos);
            }
            if (null != subscribed) {
                m.put("subscribed", String.valueOf(subscribed));
            }
            
            list = trello.updateList(listId, m);
        } catch (Throwable t) {
            _logger.error("Error ", t);
        }
        
        return list;
    }
    
    @Override
    public List<Member> getMembersByBoard(String idBoard) throws ApsSystemException {
        _logger.info("invoked getMembersByBoard");
        
        Trello trello = prepareClient();
        List<Member> members =  trello.getMembersByBoard(idBoard, null);
        return members;
    }
    
    @Override
    public String getJsonCardById(String idCard) throws ApsSystemException {
        _logger.info("invoked getMembersByBoard");
        
        Trello trello = prepareClient();
        Card card = trello.getCard(idCard);
        Gson gson = new Gson();
        String json = gson.toJson(card);
        return json;
    }
    
    @Override
    public String getJsonMembersByBoard(String idBoard) throws ApsSystemException {
        _logger.info("invoked getJsonMembersByBoard");
        Trello trello = prepareClient();
        List<Member> members =  trello.getMembersByBoard(idBoard, null);
        Gson gson = new Gson();
        String json = gson.toJson(members);        
        return json;
    }
    
    @Override
    public String getJsonCardMemberById(String idCard) throws ApsSystemException {
        _logger.info("invoked getJsonCardMemberById");
        
        Trello trello = prepareClient();
        List<Member> members =  trello.getMembersByCard(idCard);
        Gson gson = new Gson();
        String json = gson.toJson(members);
        return json;
    }

    @Override
    public TrelloConfig getConfiguration() {
        return _configuration;
    }

    public void setConfiguration(TrelloConfig configuration) {
        this._configuration = configuration;
    }
    
    protected Trello prepareClient() {
        TrelloConfig config = getConfiguration();
        
        if (null != config) {
            return new TrelloImpl(config.getApiKey(), config.getToken());
        } else {
            throw new RuntimeException("cannot instantiate Trello client!");
        }
    }
    
    @Deprecated
    protected IKeyGeneratorManager getKeyGeneratorManager() {
        return _keyGeneratorManager;
    }
    @Deprecated
    public void setKeyGeneratorManager(IKeyGeneratorManager keyGeneratorManager) {
        this._keyGeneratorManager = keyGeneratorManager;
    }
    
    @Deprecated
    public void setParamsDAO(IParamsDAO paramsDAO) {
        this._paramsDAO = paramsDAO;
    }
    @Deprecated
    protected IParamsDAO getParamsDAO() {
        return _paramsDAO;
    }

    public ConfigInterface getConfigManager() {
        return _configManager;
    }

    public void setConfigManager(ConfigInterface configManager) {
        this._configManager = configManager;
    }

    @Override
    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }
    
    @Deprecated
    private IKeyGeneratorManager _keyGeneratorManager;
    @Deprecated
    private IParamsDAO _paramsDAO;
    
    private TrelloConfig _configuration;
    private ConfigInterface _configManager;
        
    @Deprecated
    public String idOrganization;
    
    private final String USER_AGENT = "Mozilla/5.0";
    
    public final static String TOKEN_URL = "https://trello.com/1/authorize?key=%s&name=%s&expiration=1day&response_type=token&scope=read,write";
            
    public final static String POS_BOTTOM = "bottom";
    public final static String POS_TOP = "top";
}
