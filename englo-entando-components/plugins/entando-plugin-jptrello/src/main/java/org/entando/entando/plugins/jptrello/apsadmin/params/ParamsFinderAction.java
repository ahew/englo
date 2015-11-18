package org.entando.entando.plugins.jptrello.apsadmin.params;

import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.agiletec.aps.system.common.FieldSearchFilter;
import org.entando.entando.plugins.jptrello.aps.system.services.params.Params;
import org.entando.entando.plugins.jptrello.aps.system.services.params.IParamsManager;
import com.agiletec.apsadmin.system.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsFinderAction extends BaseAction {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsFinderAction.class);

    public List<Integer> getParamssId() {
        try {
            FieldSearchFilter[] filters = new FieldSearchFilter[0];
            if (null != this.getId()) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("id"), this.getId(), false);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getOrganization())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("organization"), this.getOrganization(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getApi_key())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("api_key"), this.getApi_key(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getApi_secret())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("api_secret"), this.getApi_secret(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getToken())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("token"), this.getToken(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            List<Integer> paramss = this.getParamsManager().searchParamss(filters);
            return paramss;
        } catch (Throwable t) {
            _logger.error("Error getting paramss list", t);
            throw new RuntimeException("Error getting paramss list", t);
        }
    }

    protected FieldSearchFilter[] addFilter(FieldSearchFilter[] filters, FieldSearchFilter filterToAdd) {
        int len = filters.length;
        FieldSearchFilter[] newFilters = new FieldSearchFilter[len + 1];
        for(int i=0; i < len; i++){
            newFilters[i] = filters[i];
        }
        newFilters[len] = filterToAdd;
        return newFilters;
    }

    public Params getParams(int id) {
        Params params = null;
        try {
            params = this.getParamsManager().getParams(id);
        } catch (Throwable t) {
            _logger.error("Error getting params with id {}", id, t);
            throw new RuntimeException("Error getting params with id " + id, t);
        }
        return params;
    }

    public Integer getId() {
        return _id;
    }
    public void setId(Integer id) {
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
	
    private Integer _id;
    private String _organization;
    private String _api_key;
    private String _api_secret;
    private String _token;
    private IParamsManager _paramsManager;
    
}