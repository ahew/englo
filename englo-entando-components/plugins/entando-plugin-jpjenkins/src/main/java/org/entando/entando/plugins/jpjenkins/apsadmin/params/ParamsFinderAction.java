package org.entando.entando.plugins.jpjenkins.apsadmin.params;

import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.agiletec.aps.system.common.FieldSearchFilter;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.Params;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.IParamsManager;
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
            if (StringUtils.isNotBlank(this.getUsername())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("username"), this.getUsername(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getPassword())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("password"), this.getPassword(), true);
                filters = this.addFilter(filters, filterToAdd);
            }
            if (StringUtils.isNotBlank(this.getJenkins_url())) {
                FieldSearchFilter filterToAdd = new FieldSearchFilter(("jenkins_url"), this.getJenkins_url(), true);
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


    public String getUsername() {
        return _username;
    }
    public void setUsername(String username) {
        this._username = username;
    }


    public String getPassword() {
        return _password;
    }
    public void setPassword(String password) {
        this._password = password;
    }


    public String getJenkins_url() {
        return _jenkins_url;
    }
    public void setJenkins_url(String jenkins_url) {
        this._jenkins_url = jenkins_url;
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
    private String _username;
    private String _password;
    private String _jenkins_url;
    private String _token;
    private IParamsManager _paramsManager;
}