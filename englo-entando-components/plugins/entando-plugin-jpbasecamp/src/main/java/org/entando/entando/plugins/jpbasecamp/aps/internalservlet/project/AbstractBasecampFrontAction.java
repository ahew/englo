package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.AttachmentManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.DocumentManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.IPeopleManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.ProjectManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.TodolistManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpoauth2.aps.internalservlet.login.session.OAuth2Session;
import org.entando.entando.plugins.jpoauth2.aps.system.OAuth2SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.url.IURLManager;
import com.agiletec.apsadmin.system.BaseAction;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;

public class AbstractBasecampFrontAction extends BaseAction {
    
    private static Logger _logger = LoggerFactory.getLogger(AbstractBasecampFrontAction.class);
    
    /**
     * Get the redirection URL for the given portal page
     * @param pageCode
     * @return
     */
    protected String getRedirectPageUrl(String pageCode) {
        String url = null;
        
        if (StringUtils.isNotBlank(pageCode)) {
            IPage page = getPageManager().getPage(pageCode);
            url = getUrlManager().createUrl(page, getCurrentLang(), null);
            
        } else {
            _logger.debug("Couldn't generate the page URL because of missing or invalid code");
        }
        return url;
    }
    
    /**
     * Load the data related to the project (todolist, todo etc.) needed
     */
    protected void refreshUserProjectDetails() throws Throwable {
        UserProject up = getUserProjectOnSession();
        BasecampService serviceData = getSessionServiceData();
        Map<Integer, Todolist> tdlMap = new HashMap<Integer, Todolist>();
        Map<String, Todo> undoneMap = new HashMap<String, Todo>();
        Map<String, Todo> doneMap = new HashMap<String, Todo>();
        Map<String, AttachmentReference> attachmentMap = new HashMap<String, AttachmentReference>();
        
        if (null != up) {
            up.clearLists();
            Project project = up.getProject();
            List<TodolistReference> todolists = getTodolistManager().getTodolists(project.getId(), serviceData);
            
            if (null != todolists
                    && !todolists.isEmpty()) {
                int todolistCnt = 0;
                
                // process Todolist
                for (TodolistReference ref: todolists) {
                    Todolist todolist = getTodolistManager().getTodolist(ref, serviceData);
                    
                    tdlMap.put(todolistCnt++, todolist);
                }
                // finally
                up.setTodolist(tdlMap);
            } else {
                _logger.info("no todolist for the project '{}'", project.getId());
            }
            List<AttachmentReference> attachments = getAttachmentManager().getProjectAttachments(project, serviceData);
            if (null != attachments
                    && !attachments.isEmpty()) {
                for (AttachmentReference attachment: attachments) {
                    
                    String nameKey = generateName(attachment.getName(), attachmentMap);
                    attachmentMap.put(nameKey, attachment);
                    _logger.info("Adding document '{}'", nameKey);
                }
                up.setDocuments(attachmentMap);
            } else {
                _logger.info("no attachments for the project '{}'", project.getId());
            }
        }
    }
    
    /**
     * Generate a unique filename to show in the frontend
     * @param fileName
     * @param map
     * @return 
     */
    protected String generateName(String fileName,  Map<String, AttachmentReference> map) {
        String result = fileName;
        
        try {
            if (StringUtils.isNotBlank(fileName)) {
                int cnt = -1;
                
                do {
                    if (cnt++ == -1) {
                        continue;
                    }
                    result = cnt + "-" + fileName;
                } while (map.containsKey(result));
            }
        } catch (Throwable t) {
            _logger.error("error generating unique filename", t);
        }
        return result;
    }
    
    /**
     * Get authentication data for the Basecamp service
     * @return
     */
    protected BasecampService getSessionServiceData() {
        BasecampService service = null;
        OAuth2Session sessionData = (OAuth2Session) this.getRequest().getSession().getAttribute(OAuth2SystemConstants.SESSIONPARAM_SERVICE_OBJECT);
        
        if (null != sessionData) {
            service = (BasecampService) sessionData.getServiceData(BasecampSystemConstants.SERVICE_ID);
            if (null == service) {
                _logger.error("Basecamp service authentication id '{}' not found", BasecampSystemConstants.SERVICE_ID);
            }
        } else {
            _logger.error("Could not retrieve session authentication");
        }
        return service;
    }
    
    /**
     * Return the project currently on session
     * @return
     */
    protected UserProject getUserProjectOnSession() {
        UserProject project = null;
        
        project = (UserProject) this.getRequest().getSession().getAttribute(BasecampSystemConstants.SESSIONPARAM_PROJECT_OBJECT);
        if (null != project) {
            _logger.debug("project taken from session");
        } else {
            _logger.warn("no project in session");
        }
        return project;
    }
    
    /**
     * Set a project on session eventually overwriting the one already there
     * @param project
     */
    protected void putUserProjectOnSession(UserProject project) {
        if (null != project) {
            purgeUserProjectOnSession();
            this.getRequest().getSession().setAttribute(BasecampSystemConstants.SESSIONPARAM_PROJECT_OBJECT, project);
            _logger.debug("putting project on session");
        } else {
            _logger.warn("cannot put an empty project in session");
        }
    }
    
    /**
     * If the project already exists in session nothing is done
     * @return
     */
    protected UserProject putUserProjectOnSessionIfNone(UserProject project) {
        UserProject existingProject = getUserProjectOnSession();
        
        if (null == existingProject) {
            putUserProjectOnSession(project);
        } else {
            _logger.debug("Could not overwrite the project already in session");
        }
        return existingProject;
    }
    
    /**
     * Get the person corresponding to the given ID
     * @return
     */
    protected Person fetchAssignee() {
        Person person = null;
        
        Long id = getAid();
        if (null != id) {
            person = new Person((String)null);
            person.setId(id);
        }
        return person;
    }
    
    /**
     * Clear the cache from the current user project
     */
    protected void purgeUserProjectOnSession() {
        this.getRequest().getSession().removeAttribute(BasecampSystemConstants.SESSIONPARAM_PROJECT_OBJECT);
    }
    
    // project id
    public String getPid() {
        return _pid;
    }
    public void setPid(String pid) {
        this._pid = pid;
    }
    // todolist id
    public String getTdlid() {
        return _tdlid;
    }
    public void setTdlid(String tdlid) {
        this._tdlid = tdlid;
    }
    // todo id
    public String getTdid() {
        return _tdid;
    }
    public void setTdid(String tdid) {
        this._tdid = tdid;
    }
    public ProjectManager getProjectManager() {
        return _projectManager;
    }
    public void setProjectManager(ProjectManager projectManager) {
        this._projectManager = projectManager;
    }
    public DocumentManager getDocumentManager() {
        return _documentManager;
    }
    public void setDocumentManager(DocumentManager documentManager) {
        this._documentManager = documentManager;
    }
    public TodolistManager getTodolistManager() {
        return _todolistManager;
    }
    public void setTodolistManager(TodolistManager todolistManager) {
        this._todolistManager = todolistManager;
    }
    public AttachmentManager getAttachmentManager() {
        return _attachmentManager;
    }
    public void setAttachmentManager(AttachmentManager attachmentManager) {
        this._attachmentManager = attachmentManager;
    }
    public int getStrutsAction() {
        return _strutsAction;
    }
    public void setStrutsAction(int strutsAction) {
        this._strutsAction = strutsAction;
    }
    public IPeopleManager getPeopleManager() {
        return _peopleManager;
    }
    public void setPeopleManager(IPeopleManager peopleManager) {
        this._peopleManager = peopleManager;
    }
    public String getPageCode() {
        return _pageCode;
    }
    public void setPageCode(String pageCode) {
        this._pageCode = pageCode;
    }
    public Long getAid() {
        return _aid;
    }
    public void setAid(Long aid) {
        this._aid = aid;
    }
    public UserProject getUserProject() {
        return _userProject;
    }
    public void setUserProject(UserProject userProject) {
        this._userProject = userProject;
    }
    public IPageManager getPageManager() {
        return _pageManager;
    }
    public void setPageManager(IPageManager pageManager) {
        this._pageManager = pageManager;
    }
    public IURLManager getUrlManager() {
        return _urlManager;
    }
    public void setUrlManager(IURLManager urlManager) {
        this._urlManager = urlManager;
    }
    public String getRedirectUrl() {
        return _redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this._redirectUrl = redirectUrl;
    }
    
    // form stuff
    private String _pid;
    private String _tdlid;
    private String _tdid;
    private Long _aid;
    private UserProject _userProject;
    
    // internal stuff
    private String _pageCode;
    private String _redirectUrl;
    
    private ProjectManager _projectManager;
    private DocumentManager _documentManager;
    private TodolistManager _todolistManager;
    private AttachmentManager _attachmentManager;
    private IPeopleManager _peopleManager;
    private IPageManager _pageManager;
    private IURLManager _urlManager;
    
    private Integer _strutsAction;
    
    @Deprecated
    public static final int VIEW = 100;
}
