package org.entando.entando.plugins.jpjenkins.aps.system.services.params;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.agiletec.aps.system.common.AbstractSearcherDAO;
import com.agiletec.aps.system.common.FieldSearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamsDAO extends AbstractSearcherDAO implements IParamsDAO {

    private static final Logger _logger =  LoggerFactory.getLogger(ParamsDAO.class);

    @Override
    protected String getTableFieldName(String metadataFieldKey) {
        return metadataFieldKey;
    }
	
    @Override
    protected String getMasterTableName() {
        return "jpjenkins_params";
    }
	
    @Override
    protected String getMasterTableIdFieldName() {
        return "id";
    }
	
    @Override
    protected boolean isForceCaseInsensitiveLikeSearch() {
        return true;
    }

    @Override
    public List<Integer> searchParams(FieldSearchFilter[] filters) {
        List paramssId = null;
        try {
            paramssId  = super.searchId(filters);
        } catch (Throwable t) {
            _logger.error("error in searchParamss",  t);
            throw new RuntimeException("error in searchParamss", t);
        }
        return paramssId;
    }

    @Override
    public List<Integer> loadParams() {
        List<Integer> paramssId = new ArrayList<Integer>();
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            stat = conn.prepareStatement(LOAD_PARAMSS_ID);
            res = stat.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                paramssId.add(id);
            }
        } catch (Throwable t) {
            _logger.error("Error loading Params list",  t);
            throw new RuntimeException("Error loading Params list", t);
        } finally {
            closeDaoResources(res, stat, conn);
        }
        return paramssId;
	}
	
    @Override
    public void insertParams(Params params) {
        PreparedStatement stat = null;
        Connection conn  = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            this.insertParams(params, conn);
            conn.commit();
        } catch (Throwable t) {
            this.executeRollback(conn);
            _logger.error("Error on insert params",  t);
            throw new RuntimeException("Error on insert params", t);
        } finally {
            this.closeDaoResources(null, stat, conn);
        }
    }

    public void insertParams(Params params, Connection conn) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(ADD_PARAMS);
            int index = 1;
            stat.setInt(index++, params.getId());
            if(StringUtils.isNotBlank(params.getUsername())) {
                stat.setString(index++, params.getUsername());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getPassword())) {
                stat.setString(index++, params.getPassword());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getJenkins_url())) {
                stat.setString(index++, params.getJenkins_url());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getToken())) {
                stat.setString(index++, params.getToken());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            stat.executeUpdate();
        } catch (Throwable t) {
            _logger.error("Error on insert params",  t);
            throw new RuntimeException("Error on insert params", t);
        } finally {
            this.closeDaoResources(null, stat, null);
        }
    }

    @Override
    public void updateParams(Params params) {
        PreparedStatement stat = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            this.updateParams(params, conn);
            conn.commit();
        } catch (Throwable t) {
            this.executeRollback(conn);
            _logger.error("Error updating params {}", params.getId(),  t);
            throw new RuntimeException("Error updating params", t);
        } finally {
            this.closeDaoResources(null, stat, conn);
        }
    }

    public void updateParams(Params params, Connection conn) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(UPDATE_PARAMS);
            int index = 1;

            if(StringUtils.isNotBlank(params.getUsername())) {
                stat.setString(index++, params.getUsername());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getPassword())) {
                stat.setString(index++, params.getPassword());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getJenkins_url())) {
                stat.setString(index++, params.getJenkins_url());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            if(StringUtils.isNotBlank(params.getToken())) {
                stat.setString(index++, params.getToken());
            } else {
                stat.setNull(index++, Types.VARCHAR);
            }
            stat.setInt(index++, params.getId());
            stat.executeUpdate();
        } catch (Throwable t) {
            _logger.error("Error updating params {}", params.getId(),  t);
            throw new RuntimeException("Error updating params", t);
        } finally {
            this.closeDaoResources(null, stat, null);
        }
    }

    @Override
    public void removeParams(int id) {
        PreparedStatement stat = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            this.removeParams(id, conn);
            conn.commit();
        } catch (Throwable t) {
            this.executeRollback(conn);
            _logger.error("Error deleting params {}", id, t);
            throw new RuntimeException("Error deleting params", t);
        } finally {
            this.closeDaoResources(null, stat, conn);
        }
    }
	
    public void removeParams(int id, Connection conn) {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(DELETE_PARAMS);
            int index = 1;
            stat.setInt(index++, id);
            stat.executeUpdate();
        } catch (Throwable t) {
            _logger.error("Error deleting params {}", id, t);
            throw new RuntimeException("Error deleting params", t);
        } finally {
            this.closeDaoResources(null, stat, null);
        }
    }

    public Params loadParams(int id) {
        Params params = null;
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            params = this.loadParams(id, conn);
        } catch (Throwable t) {
            _logger.error("Error loading params with id {}", id, t);
            throw new RuntimeException("Error loading params with id " + id, t);
        } finally {
            closeDaoResources(res, stat, conn);
        }
        return params;
    }

    public Params loadParams(int id, Connection conn) {
        Params params = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        try {
            stat = conn.prepareStatement(LOAD_PARAMS);
            int index = 1;
            stat.setInt(index++, id);
            res = stat.executeQuery();
            if (res.next()) {
                params = this.buildParamsFromRes(res);
            }
        } catch (Throwable t) {
            _logger.error("Error loading params with id {}", id, t);
            throw new RuntimeException("Error loading params with id " + id, t);
        } finally {
            closeDaoResources(res, stat, null);
        }
        return params;
    }

    protected Params buildParamsFromRes(ResultSet res) {
        Params params = null;
        try {
            params = new Params();
            params.setId(res.getInt("id"));
            params.setUsername(res.getString("username"));
            params.setPassword(res.getString("password"));
            params.setJenkins_url(res.getString("jenkins_url"));
            params.setToken(res.getString("token"));
        } catch (Throwable t) {
            _logger.error("Error in buildParamsFromRes", t);
        }
        return params;
    }

    private static final String ADD_PARAMS = "INSERT INTO jpjenkins_params (id, username, password, jenkins_url, token ) VALUES (?, ?, ?, ?, ? )";

    private static final String UPDATE_PARAMS = "UPDATE jpjenkins_params SET  username=?,  password=?,  jenkins_url=?, token=? WHERE id = ?";

    private static final String DELETE_PARAMS = "DELETE FROM jpjenkins_params WHERE id = ?";
	
    private static final String LOAD_PARAMS = "SELECT id, username, password, jenkins_url, token  FROM jpjenkins_params WHERE id = ?";
	
    private static final String LOAD_PARAMSS_ID  = "SELECT id FROM jpjenkins_params";
	
}