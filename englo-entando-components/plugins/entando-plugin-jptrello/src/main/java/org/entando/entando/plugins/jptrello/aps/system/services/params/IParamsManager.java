package org.entando.entando.plugins.jptrello.aps.system.services.params;

import java.util.List;
import com.agiletec.aps.system.exception.ApsSystemException;

import com.agiletec.aps.system.common.FieldSearchFilter;

public interface IParamsManager {

    public Params getParams(int id) throws ApsSystemException;

    public List<Integer> getParamss() throws ApsSystemException;

    public List<Integer> searchParamss(FieldSearchFilter filters[]) throws ApsSystemException;

    public void addParams(Params params) throws ApsSystemException;

    public void updateParams(Params params) throws ApsSystemException;

    public void deleteParams(int id) throws ApsSystemException;

}