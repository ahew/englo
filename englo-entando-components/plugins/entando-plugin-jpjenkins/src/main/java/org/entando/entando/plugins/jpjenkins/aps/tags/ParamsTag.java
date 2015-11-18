package org.entando.entando.plugins.jpjenkins.aps.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.agiletec.aps.system.services.page.Widget;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.aps.system.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.IParamsManager;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.Params;

public class ParamsTag extends TagSupport {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsTag.class);
	
    @Override
    public int doStartTag() throws JspException {
        ServletRequest request =  this.pageContext.getRequest();
        IParamsManager paramsManager = (IParamsManager) ApsWebApplicationUtils.getBean("jpjenkinsParamsManager", this.pageContext);
	RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
        try {
            Params params = null;
            if (null != this.getKey()) {
                params = paramsManager.getParams(this.getKey());
            } else {
                Widget widget = this.extractWidget(reqCtx);
                ApsProperties widgetConfig = widget.getConfig();
                String varid = widgetConfig.getProperty("id");
                if (StringUtils.isNotBlank(varid)) {
                    params = paramsManager.getParams(new Integer(varid));
                }
            }
            this.pageContext.setAttribute(this.getVar(), params);
        } catch (Throwable t) {
            _logger.error("Error in doStartTag", t);
            throw new JspException("Error in ParamsTag doStartTag", t);
        }
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        this.release();
        return super.doEndTag();
    }

    @Override
    public void release() {
        this.setVar(null);
        this.setKey(null);
    }

    private Widget extractWidget(RequestContext reqCtx) {
        Widget widget = null;
        widget = (Widget) reqCtx.getExtraParam((SystemConstants.EXTRAPAR_CURRENT_WIDGET));
        return widget;
    }

    protected String extractWidgetParameter(String parameterName, ApsProperties widgetConfig, RequestContext reqCtx) {
        return (String) widgetConfig.get(parameterName);
    }

    public String getVar() {
        return _var;
    }
    public void setVar(String var) {
        this._var = var;
    }

    public Integer getKey() {
        return _key;
    }
    public void setKey(Integer key) {
        this._key = key;
    }

    private String _var;
    private Integer _key;
}
