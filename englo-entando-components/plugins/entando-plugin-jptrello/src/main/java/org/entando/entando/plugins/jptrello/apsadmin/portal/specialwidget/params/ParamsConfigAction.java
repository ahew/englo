package org.entando.entando.plugins.jptrello.apsadmin.portal.specialwidget.params;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.apsadmin.portal.specialwidget.SimpleWidgetConfigAction;
import org.entando.entando.plugins.jptrello.aps.system.services.params.IParamsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsConfigAction extends SimpleWidgetConfigAction {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsConfigAction.class);
	
    protected String extractInitConfig() {
        String result = super.extractInitConfig();
        String id = this.getWidget().getConfig().getProperty("id");
        if (StringUtils.isNotBlank(id)) {
            this.setId(new Integer(id));
        }
        return result;
    }

    public List<Integer> getParamssId() {
        try {
            List<Integer> paramss = this.getParamsManager().searchParamss(null);
            return paramss;
        } catch (Throwable t) {
            _logger.error("error in getParamssId", t);
            throw new RuntimeException("Error getting paramss list", t);
        }
    }
	
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    protected IParamsManager getParamsManager() {
        return _paramsManager;
    }
    public void setParamsManager(IParamsManager paramsManager) {
        this._paramsManager = paramsManager;
    }

    private int _id;
    private IParamsManager _paramsManager;
    
}

