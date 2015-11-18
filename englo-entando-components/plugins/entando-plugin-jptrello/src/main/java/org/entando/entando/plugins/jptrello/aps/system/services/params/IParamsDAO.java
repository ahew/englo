package org.entando.entando.plugins.jptrello.aps.system.services.params;

import java.util.List;

import com.agiletec.aps.system.common.FieldSearchFilter;

public interface IParamsDAO {

    public List<Integer> searchParamss(FieldSearchFilter[] filters);
	
    public Params loadParams(int id);

    public List<Integer> loadParamss();

    public void removeParams(int id);
	
    public void updateParams(Params params);

    public void insertParams(Params params);
    
}