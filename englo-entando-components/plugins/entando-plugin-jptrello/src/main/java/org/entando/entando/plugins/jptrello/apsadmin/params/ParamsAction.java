package org.entando.entando.plugins.jptrello.apsadmin.params;

import org.entando.entando.plugins.jptrello.aps.system.services.params.Params;
import org.entando.entando.plugins.jptrello.aps.system.services.params.IParamsManager;



import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsAction extends BaseAction {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsAction.class);

    public String newParams() {
        try {
            this.setStrutsAction(ApsAdminSystemConstants.ADD);
        } catch (Throwable t) {
            _logger.error("error in newParams", t);
            return FAILURE;
        }
        return SUCCESS;
    }
	
    public String edit() {
        try {
            Params params = this.getParamsManager().getParams(this.getId());
            if (null == params) {
                this.addActionError(this.getText("error.params.null"));
                return INPUT;
            }
            this.populateForm(params);
            this.setStrutsAction(ApsAdminSystemConstants.EDIT);
        } catch (Throwable t) {
            _logger.error("error in edit", t);
            return FAILURE;
        }
        return SUCCESS;
    }

    public String save() {
        try {
            Params params = this.createParams();
            int strutsAction = this.getStrutsAction();
            if (ApsAdminSystemConstants.ADD == strutsAction) {
                this.getParamsManager().addParams(params);
            } else if (ApsAdminSystemConstants.EDIT == strutsAction) {
                this.getParamsManager().updateParams(params);
            }
        } catch (Throwable t) {
            _logger.error("error in save", t);
            return FAILURE;
        }
        return SUCCESS;
    }
	
    public String trash() {
        try {
            Params params = this.getParamsManager().getParams(this.getId());
            if (null == params) {
                this.addActionError(this.getText("error.params.null"));
                return INPUT;
            }
            this.populateForm(params);
            this.setStrutsAction(ApsAdminSystemConstants.DELETE);
        } catch (Throwable t) {
            _logger.error("error in trash", t);
            return FAILURE;
        }
        return SUCCESS;
    }
	
    public String delete() {
        try {
            if (this.getStrutsAction() == ApsAdminSystemConstants.DELETE) {
                this.getParamsManager().deleteParams(this.getId());
            }
        } catch (Throwable t) {
            _logger.error("error in delete", t);
            return FAILURE;
        }
        return SUCCESS;
    }
    
    public String view() {
        try {
            Params params = this.getParamsManager().getParams(this.getId());
            if (null == params) {
                this.addActionError(this.getText("error.params.null"));
                return INPUT;
            }
            this.populateForm(params);
        } catch (Throwable t) {
            _logger.error("error in view", t);
            return FAILURE;
        }
        return SUCCESS;
    }
	
    private void populateForm(Params params) throws Throwable {
        this.setId(params.getId());
        this.setOrganization(params.getOrganization());
        this.setApi_key(params.getApi_key());
        this.setApi_secret(params.getApi_secret());
        this.setToken(params.getToken());
    }
    
    private Params createParams() {
        Params params = new Params();
        params.setId(this.getId());
        params.setOrganization(this.getOrganization());
        params.setApi_key(this.getApi_key());
        params.setApi_secret(this.getApi_secret());
        params.setToken(this.getToken());
        return params;
    }
	

    public int getStrutsAction() {
        return _strutsAction;
    }
    public void setStrutsAction(int strutsAction) {
        this._strutsAction = strutsAction;
    }
	
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    public String getOrganization() {
        return _organization;
    }
    public void setOrganization(String organization) {
        this._organization = organization;
    }

    public String getApi_key() {
        return _api_key;
    }
    public void setApi_key(String api_key) {
        this._api_key = api_key;
    }

    public String getApi_secret() {
        return _api_secret;
    }
    public void setApi_secret(String api_secret) {
        this._api_secret = api_secret;
    }

    public String getToken() {
        return _token;
    }
    public void setToken(String token) {
        this._token = token;
    }

	
    protected IParamsManager getParamsManager() {
        return _paramsManager;
    }
    public void setParamsManager(IParamsManager paramsManager) {
        this._paramsManager = paramsManager;
    }
	
    private int _strutsAction;
    private int _id;
    private String _organization;
    private String _api_key;
    private String _api_secret;
    private String _token;
	
    private IParamsManager _paramsManager;

}