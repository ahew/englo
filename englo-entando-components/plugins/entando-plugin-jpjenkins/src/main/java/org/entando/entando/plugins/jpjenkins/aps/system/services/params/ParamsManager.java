package org.entando.entando.plugins.jpjenkins.aps.system.services.params;

import org.entando.entando.plugins.jpjenkins.aps.system.services.params.event.ParamsChangedEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.api.JAXBParams;
import org.entando.entando.aps.system.services.api.IApiErrorCodes;
import org.entando.entando.aps.system.services.api.model.ApiException;

import com.agiletec.aps.system.common.FieldSearchFilter;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.keygenerator.IKeyGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsManager extends AbstractService implements IParamsManager {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsManager.class);

    @Override
    public void init() throws Exception {
        _logger.debug("{} ready.", this.getClass().getName());
    }
 
    @Override
    public Params getParams(int id) throws ApsSystemException {
        Params params = null;
        try {
            params = this.getParamsDAO().loadParams(id);
        } catch (Throwable t) {
            _logger.error("Error loading params with id '{}'", id,  t);
            throw new ApsSystemException("Error loading params with id: " + id, t);
        }
        return params;
    }

    @Override
    public List<Integer> getParamss() throws ApsSystemException {
        List<Integer> paramss = new ArrayList<Integer>();
        try {
            paramss = this.getParamsDAO().loadParams();
        } catch (Throwable t) {
            _logger.error("Error loading Params list",  t);
            throw new ApsSystemException("Error loading Params ", t);
        }
        return paramss;
    }

    @Override
    public List<Integer> searchParamss(FieldSearchFilter filters[]) throws ApsSystemException {
        List<Integer> paramss = new ArrayList<Integer>();
        try {
            paramss = this.getParamsDAO().searchParams(filters);
        } catch (Throwable t) {
            _logger.error("Error searching Paramss", t);
            throw new ApsSystemException("Error searching Paramss", t);
        }
        return paramss;
    }

    @Override
    public void addParams(Params params) throws ApsSystemException {
        try {
            int key = this.getKeyGeneratorManager().getUniqueKeyCurrentValue();
            params.setId(key);
            this.getParamsDAO().insertParams(params);
            this.notifyParamsChangedEvent(params, ParamsChangedEvent.INSERT_OPERATION_CODE);
        } catch (Throwable t) {
            _logger.error("Error adding Params", t);
            throw new ApsSystemException("Error adding Params", t);
        }
    }
 
    @Override
    public void updateParams(Params params) throws ApsSystemException {
        try {
            this.getParamsDAO().updateParams(params);
            this.notifyParamsChangedEvent(params, ParamsChangedEvent.UPDATE_OPERATION_CODE);
        } catch (Throwable t) {
            _logger.error("Error updating Params", t);
            throw new ApsSystemException("Error updating Params " + params, t);
        }
    }

    @Override
    public void deleteParams(int id) throws ApsSystemException {
        try {
            Params params = this.getParams(id);
            this.getParamsDAO().removeParams(id);
            this.notifyParamsChangedEvent(params, ParamsChangedEvent.REMOVE_OPERATION_CODE);
        } catch (Throwable t) {
            _logger.error("Error deleting Params with id {}", id, t);
            throw new ApsSystemException("Error deleting Params with id:" + id, t);
        }
    }


    public List<JAXBParams> getParamssForApi(Properties properties) throws Throwable {
        List<JAXBParams> list = new ArrayList<JAXBParams>();
        List<Integer> idList = this.getParamss();
        if (null != idList && !idList.isEmpty()) {
            Iterator<Integer> paramsIterator = idList.iterator();
            while (paramsIterator.hasNext()) {
                int currentid = paramsIterator.next();
                Params params = this.getParams(currentid);
                if (null != params) {
                    list.add(new JAXBParams(params));
                }
            }
        }
        return list;
    }


    public JAXBParams getParamsForApi(Properties properties) throws Throwable {
        String idString = properties.getProperty("id");
        int id = 0;
        JAXBParams jaxbParams = null;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ApiException(IApiErrorCodes.API_PARAMETER_VALIDATION_ERROR, "Invalid Integer format for 'id' parameter - '" + idString + "'", Response.Status.CONFLICT);
        }
        Params params = this.getParams(id);
        if (null == params) {
            throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Params with id '" + idString + "' does not exist", Response.Status.CONFLICT);
        }
        jaxbParams = new JAXBParams(params);
        return jaxbParams;
    }

    public void addParamsForApi(JAXBParams jaxbParams) throws ApiException, ApsSystemException {
        if (null != this.getParams(jaxbParams.getId())) {
            throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Params with id " + jaxbParams.getId() + " already exists", Response.Status.CONFLICT);
        }
        Params params = jaxbParams.getParams();
        this.addParams(params);
    }

   
    public void updateParamsForApi(JAXBParams jaxbParams) throws ApiException, ApsSystemException {
        if (null == this.getParams(jaxbParams.getId())) {
            throw new ApiException(IApiErrorCodes.API_VALIDATION_ERROR, "Params with id " + jaxbParams.getId() + " does not exist", Response.Status.CONFLICT);
        }
        Params params = jaxbParams.getParams();
        this.updateParams(params);
    }

    
    public void deleteParamsForApi(Properties properties) throws Throwable {
        String idString = properties.getProperty("id");
        int id = 0;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new ApiException(IApiErrorCodes.API_PARAMETER_VALIDATION_ERROR, "Invalid Integer format for 'id' parameter - '" + idString + "'", Response.Status.CONFLICT);
        }
        this.deleteParams(id);
    }

    private void notifyParamsChangedEvent(Params params, int operationCode) {
        ParamsChangedEvent event = new ParamsChangedEvent();
        event.setParams(params);
        event.setOperationCode(operationCode);
        this.notifyEvent(event);
    }


    protected IKeyGeneratorManager getKeyGeneratorManager() {
        return _keyGeneratorManager;
    }
    public void setKeyGeneratorManager(IKeyGeneratorManager keyGeneratorManager) {
        this._keyGeneratorManager = keyGeneratorManager;
    }

    public void setParamsDAO(IParamsDAO paramsDAO) {
        this._paramsDAO = paramsDAO;
    }
    protected IParamsDAO getParamsDAO() {
        return _paramsDAO;
    }

    private IKeyGeneratorManager _keyGeneratorManager;
    private IParamsDAO _paramsDAO;
}
