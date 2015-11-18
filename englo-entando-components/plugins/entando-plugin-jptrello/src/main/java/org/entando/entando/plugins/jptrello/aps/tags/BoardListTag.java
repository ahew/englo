package org.entando.entando.plugins.jptrello.aps.tags;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import org.entando.entando.plugins.jptrello.aps.system.services.trello.ITrelloClientManager;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import static javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jptrello.aps.system.TrelloSystemConstants;
import org.trello4j.model.Board;


public class BoardListTag extends TagSupport {
    
    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag() throws JspException {
        List<Board> boardList = null;
        ServletRequest request = this.pageContext.getRequest();
        ITrelloClientManager trelloClientManager = (ITrelloClientManager) ApsWebApplicationUtils.getBean(TrelloSystemConstants.TRELLO_CLIENT_MANAGER, this.pageContext);
        try {
            try {
                boardList = trelloClientManager.getBoardByOrganization();
            } catch (Exception e) {
                e.printStackTrace();
                ApsSystemUtils.getLogger().error("error loading boards");
            }
            if (StringUtils.isBlank(this.getVar())) {
                this.pageContext.getOut().print(boardList);
            } else {
                this.pageContext.setAttribute(this.getVar(), boardList);
            }
        } catch (Throwable t) {
            ApsSystemUtils.logThrowable(t, this, "doEndTag");
            throw new JspException("Error in CourseListTag", t);
        }
        this.release();
        return super.doEndTag();
    }
    
    public void setVar(String var) {
        this._var = var;
    }
    public String getVar() {
        return _var;
    }

    private String _var;
}
