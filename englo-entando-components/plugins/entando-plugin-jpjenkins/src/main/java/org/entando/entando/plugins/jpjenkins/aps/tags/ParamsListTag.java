package org.entando.entando.plugins.jpjenkins.aps.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import com.agiletec.aps.system.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.IParamsManager;

public class ParamsListTag extends TagSupport {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsListTag.class);

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request =  this.pageContext.getRequest();
        IParamsManager paramsManager = (IParamsManager) ApsWebApplicationUtils.getBean("jpjenkinsParamsManager", this.pageContext);
        RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
        try {
            List<Integer> list = paramsManager.getParamss();
            this.pageContext.setAttribute(this.getVar(), list);
        } catch (Throwable t) {
            _logger.error("Error in doStartTag", t);
            throw new JspException("Error in ParamsListTag doStartTag", t);
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
        super.release();
        this.setVar(null);
    }

    public String getVar() {
        return _var;
    }
    public void setVar(String var) {
        this._var = var;
    }

    private String _var;
}
