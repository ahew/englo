package org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.internalservlet.project.model.UserProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseAction;
import com.agiletec.plugins.jacms.aps.system.services.resource.IResourceManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.lang.StringUtils;
import org.entando.entando.aps.system.services.storage.IStorageManager;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.UploadItem;

public class ProjectFrontAction extends AbstractBasecampFrontAction implements IProjectFrontAction {
    
    private static final Logger _logger =  LoggerFactory.getLogger(ProjectFrontAction.class);
    
    @Override
    public String newProject() {
        this.setStrutsAction(ApsAdminSystemConstants.ADD);
        return SUCCESS;
    }
    
    @Override
    public String save() {
        Project project = null;
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            // get the project from submit
            UserProject formUserProject = getUserProject();
            
            if (null != formUserProject) {
                Project formProject = formUserProject.getProject();
                
                if (getStrutsAction() == ApsAdminSystemConstants.EDIT) {
                    // get the project on session
                    UserProject up = getUserProjectOnSession();
                    // update the project with the ID of the original project
                    _logger.debug("Will update project '{}'", up.getProject().getId());
                    formProject.updateFrom(up.getProject());
                    // update
                    project = getProjectManager().updateProject(formProject, serviceData);
                    // update the user project on session
                    up.setProject(formProject);
                    // load data
                    refreshUserProjectDetails();
                    _logger.info("Updated project with id '{}'", project.getId());
                } else if (getStrutsAction() == ApsAdminSystemConstants.ADD) {
                    // create the new from the user project just created upon submit
                    project = getProjectManager().createProject(formProject, serviceData);
                    _logger.info("Created new project with id '{}'", project.getId());
                    // update the project in the session
                    updateUserProject(project);
                    String pid = String.valueOf(project.getId());
                    setPid(pid);
                } else {
                    addActionError(this.getText("jpbasecamp.error.save"));
                    return INPUT;
                }
            } else {
                _logger.warn("Save action invoked without corresponding project valued");
                return INPUT;
            }
        } catch (Throwable t) {
            _logger.error("Error saving or updating project", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String saveExit() {
        String result = save();
        
        if (!result.equals(BaseAction.SUCCESS)) {
            purgeUserProjectOnSession();
            _logger.info("User project removed from session on exit");
        }
        return result;
    }
    
    @Override
    public String view() {
        try {
            if (null != getPid()) {
                setStrutsAction(ApsAdminSystemConstants.EDIT); // 2
                updateUserProject();
                refreshUserProjectDetails();
            } else {
                setStrutsAction(ApsAdminSystemConstants.ADD); // 1
            }
        } catch (Throwable t) {
            _logger.error("Error creating user project for view", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    @Deprecated
    public String edit() {
        try {
            setStrutsAction(ApsAdminSystemConstants.EDIT); // 2
            updateUserProject();
        } catch (Throwable t) {
            t.printStackTrace();
            _logger.error("Error creating user project for edit", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String trash() {
        UserProject up = getUserProjectOnSession();
        
        if (null != up) {
            setUserProject(up);
            _logger.error("set user project for trash operation");
        } else {
            _logger.error("Have no userproject in session");
            return INPUT;
        }
        return SUCCESS;
    }
    
    @Override
    public String delete() {
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            // Retrieve the current project...
            UserProject up = getUserProjectOnSession();
            long pid = up.getProject().getId();
            _logger.info("Deleting project '{}'", pid);
            // ... and delete it
            getProjectManager().deleteProject(up.getProject(), serviceData);
            // clean session
            purgeUserProjectOnSession();
        } catch (Throwable t) {
            _logger.error("Error creating user project for edit", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String document() {
        UserProject up = getUserProjectOnSession();
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            if (null != up) {
                setUserProject(up);
                _logger.info("restored project from the session");
            } else if (StringUtils.isNotBlank(getPid())) {
                updateUserProject();
                refreshUserProjectDetails();
                _logger.info("restoring session with project '{}'", getPid());
            } else {
                _logger.error("could not retrieve the project");
                return INPUT;
            }
        } catch (Throwable t) {
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String upload() {
        BasecampService service = getSessionServiceData();
        UserProject up = getUserProjectOnSession();
        BasecampService serviceData = null;
        
        try {
            serviceData = getSessionServiceData();
            Project project = (null != up && null != up.getProject()) ? up.getProject() : getProject(getPid());
            InputStream is = new FileInputStream(getUpload());
            
            // create attachment and _upload it
            UploadItem uploadItem = getAttachmentManager().createAttachment(getUploadFileName(), is, null, service);
            // attach it to the current project
            getAttachmentManager().createUpload(project, uploadItem, null);
            // just reopen
            is = new FileInputStream(getUpload());
            // upload optionally in entando
            if (isUploadInEntando()) {
                String uploadPath = File.separator + getUploadFileName();
                
                _storageManager.saveFile(uploadPath, isUploadProtected(), is);
                addActionMessage(this.getText("jpbasecamp.message.file.upload", new String[] {this.getUploadFileName()}));
                _logger.info("resource uplaoded as '{}' in Basecamp and as a FILE resource in Entando (protected resource: '{}')",
                        getUploadFileName(),
                        isUploadProtected());
            }
            if (isUploadResource()) {
//                ResourceInterface attachResource = getResourceManager().createResourceType("Attach");
//
//                attachResource.setMasterFileName(getUploadFileName());
//                attachResource.setCreationDate(new Date());
//                attachResource.setFolder(NONE);
//                getResourceManager().addResource(attachResource);
                _logger.info("resource uplaoded as '{}' resource in Basecamp and Entando ", getUploadFileName());
            }
            if (!isUploadInEntando()) {
                _logger.info("resource uplaoded as '{}' only in Basecamp", getUploadFileName());
            }
            // finally
            refreshUserProjectDetails();
            
        } catch (Throwable t) {
            t.printStackTrace();
            return SUCCESS;
        }
        return SUCCESS;
    }
    
    @Override
    public String documentTrash() {
        
        try {
            UserProject project = getUserProjectOnSession();
            BasecampService serviceData = getSessionServiceData();
            
            if (null != project) {
                setUserProject(project);
                _logger.info("restored project from the session");
            } else if (StringUtils.isNotBlank(getPid())) {
                updateUserProject();
                refreshUserProjectDetails();
                _logger.info("restoring session with project '{}'", getPid());
            } else {
                _logger.error("could not retrieve the project");
                return INPUT;
            }
        } catch (Throwable t) {
            _logger.error("error trashing document", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    @Override
    public String documentDelete() {
        
        try {
            UserProject project = getUserProjectOnSession();
            BasecampService serviceData = getSessionServiceData();
            
            if (null != project) {
                setUserProject(project);
                _logger.info("restored project from the session");
            } else if (StringUtils.isNotBlank(getPid())) {
                updateUserProject();
                refreshUserProjectDetails();
                _logger.info("restoring session with project '{}'", getPid());
            } else {
                _logger.error("could not retrieve the project");
                return INPUT;
            }
            long id = Long.valueOf(getDid());
            // look for the attachment to delete
            for (AttachmentReference ref: project.getDocuments().values()) {
                if (ref.getId() == id) {
                    getAttachmentManager().deleteAttachment(ref, serviceData);
                    _logger.info("document '{}' deleted from project '{}'",
                            getDid(),
                            project.getProject().getName());
                    refreshUserProjectDetails();
                    break;
                }
            }
        } catch (Throwable t) {
            _logger.error("error deleting document", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    /**
     * Load the project with the given 'pid' and create the corresponding user
     * project for the edit operation
     * @throws Throwable
     */
    private void updateUserProject() throws Throwable {
        BasecampService serviceData = null;
        UserProject up = null;
        
        long pid = Long.valueOf(getPid());
        serviceData = getSessionServiceData();
        
        Project project = getProjectManager().getProject(pid, serviceData);
        if (null != project) {
            _logger.debug("loaded project '{}' for editing operations", pid);
            // create the user project for handling
            up = new UserProject(project);
            // put it on session
            putUserProjectOnSession(up);
            // made it available to the form
            setUserProject(up);
            _logger.info("User project '{}' in session", project.getId());
        } else {
            _logger.error("Could not restore existing project '{}' in session", project.getId());
        }
    }
    
    /**
     * Update the session object with the NEWLY CREATED project.
     * @param project
     */
    private void updateUserProject(Project project) {
        if (null != project) {
            UserProject up = new UserProject();
            up.setProject(project);
            putUserProjectOnSession(up);
            // update current pid - needed for correct redirection
            String pid = String.valueOf(project.getId());
            setPid(pid);
            _logger.info("User project '{}' in session", project.getId());
        } else {
            _logger.error("Could not put the given project in session");
        }
    }
    
    /**
     * Expose project object through its ID
     * @param id
     * @return
     */
    public Project getProject(long id) {
        BasecampService service = getSessionServiceData();
        Project project = null;
        
        try {
            List<ProjectReference> projects = this.getProjectManager().getProjects(service);
            
            for (ProjectReference ref: projects) {
                if (ref.getId() == id) {
                    _logger.debug("Loding project '{}'", ref.getName());
                    project = this.getProjectManager().getProject(ref, service);
                }
            }
            // Ouch!
            if (null == project) {
                _logger.error("Could not find project '{}'", id);
            }
        } catch (Throwable t) {
            _logger.error("Error getting project with id {}", id, t);
            throw new RuntimeException("Error getting project with id " + id, t);
        }
        return project;
    }
    
        
    /**
     * Return the
     * @param id
     * @return
     */
    public String getDocumentFileName(int id) {
        UserProject project = getUserProjectOnSession();
        String name = null;
        
        try {
            // look for the attachment to delete
            for (AttachmentReference ref: project.getDocuments().values()) {
                if (ref.getId() == id) {
                    name = ref.getName();
                }
            }
        } catch (Throwable t) {
            _logger.error("error getting document filename " + id, t);
        }
        return name;
    }
    
    
    public Project getProject(String id) {
        return getProject(Long.valueOf(id));
    }
    
    public void setUpload(File upload) {
        this._upload = upload;
    }
    
    public void setUploadContentType(String uploadContentType) {
        this._uploadContentType = uploadContentType;
    }
    
    public void setUploadFileName(String uploadFileName) {
        this._uploadFileName = uploadFileName;
    }
    
    public File getUpload() {
        return _upload;
    }
    
    public String getUploadContentType() {
        return _uploadContentType;
    }
    
    public String getUploadFileName() {
        return _uploadFileName;
    }
    
    public IStorageManager getStorageManager() {
        return _storageManager;
    }
    
    public void setStorageManager(IStorageManager storageManager) {
        this._storageManager = storageManager;
    }
    
    public boolean isUploadInEntando() {
        return _uploadInEntando;
    }
    
    public void setUploadInEntando(boolean uploadInEntando) {
        this._uploadInEntando = uploadInEntando;
    }
    
    public boolean isUploadProtected() {
        return _uploadProtected;
    }
    
    public void setUploadProtected(boolean uploadProtected) {
        this._uploadProtected = uploadProtected;
    }
    
    public IResourceManager getResourceManager() {
        return _resourceManager;
    }
    
    public void setResourceManager(IResourceManager resourceManager) {
        this._resourceManager = resourceManager;
    }
    
    public boolean isUploadResource() {
        return _uploadResource;
    }
    
    public void setUploadResource(boolean uploadResource) {
        this._uploadResource = uploadResource;
    }
    
    public String getDid() {
        return _did;
    }
    
    public void setDid(String did) {
        this._did = did;
    }
    
    private File _upload;
    private String _uploadContentType;
    private String _uploadFileName;
    private boolean _uploadInEntando;
    private boolean _uploadProtected;
    private boolean _uploadResource;
    private String _did;
    
    private IStorageManager _storageManager;
    private IResourceManager _resourceManager;
}
