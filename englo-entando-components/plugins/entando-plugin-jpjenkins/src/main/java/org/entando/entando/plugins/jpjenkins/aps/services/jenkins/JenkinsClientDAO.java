package org.entando.entando.plugins.jpjenkins.aps.services.jenkins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsClientDAO implements IJenkinsClientDAO{
    
    private static final Logger _logger =  LoggerFactory.getLogger(JenkinsClientDAO.class);

    @Override
    public String executeLogin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInfo(String url) {
        String info = "";
        try {
            info = IOUtils.toString(new URL(url+"/api/json?pretty=true"));
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(JenkinsClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JenkinsClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
        
    }

    @Override
    public String createJob() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLogs(String url, String job) {
        String info = "";
        try {
            info = IOUtils.toString(new URL(url+"/job/"+job+"/api/json?pretty=true"));
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(JenkinsClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JenkinsClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
        
    }
    
}
