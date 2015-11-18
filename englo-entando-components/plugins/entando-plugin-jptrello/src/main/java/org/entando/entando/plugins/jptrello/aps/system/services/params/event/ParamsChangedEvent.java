package org.entando.entando.plugins.jptrello.aps.system.services.params.event;

import com.agiletec.aps.system.common.IManager;
import com.agiletec.aps.system.common.notify.ApsEvent;
import org.entando.entando.plugins.jptrello.aps.system.services.params.Params;


public class ParamsChangedEvent extends ApsEvent {
	
    @Override
    public void notify(IManager srv) {
        ((ParamsChangedObserver) srv).updateFromParamsChanged(this);
    }
    
    @Override
    public Class getObserverInterface() {
        return ParamsChangedObserver.class;
    }
	
    public int getOperationCode() {
        return _operationCode;
    }
    public void setOperationCode(int operationCode) {
        this._operationCode = operationCode;
    }
	
    public Params getParams() {
        return _params;
    }
    public void setParams(Params params) {
        this._params = params;
    }

    private Params _params;
    private int _operationCode;
	
    public static final int INSERT_OPERATION_CODE = 1;
    public static final int REMOVE_OPERATION_CODE = 2;
    public static final int UPDATE_OPERATION_CODE = 3;

}
