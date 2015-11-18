package org.entando.entando.plugins.jpjenkins.aps.system.services.params;

import java.util.List;

import com.agiletec.aps.system.common.FieldSearchFilter;

public interface IParamsDAO {

    public List<Integer> searchParams(FieldSearchFilter[] filters);
	
    public Params loadParams(int id);

    public List<Integer> loadParams();

    public void removeParams(int id);
	
    public void updateParams(Params params);

    public void insertParams(Params params);
	
}