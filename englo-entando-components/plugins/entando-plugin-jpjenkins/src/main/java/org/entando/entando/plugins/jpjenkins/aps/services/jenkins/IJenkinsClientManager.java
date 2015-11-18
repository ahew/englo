package org.entando.entando.plugins.jpjenkins.aps.services.jenkins;

import com.agiletec.aps.system.exception.ApsSystemException;

public interface IJenkinsClientManager {
    
    public final static String BEAN_ID = "jpjenkinsJenkinsClientManager";
    
    public String getInfo() throws ApsSystemException;

    public void createJob() throws ApsSystemException;
    
    public String getLogs(String job) throws ApsSystemException ;

    public void saveAndBuild(String jobName, String github, String url, String build_on_change) throws ApsSystemException ;

    public void build(String jobname) throws ApsSystemException ;
    
    public JenkinsConfig getConfig();
    
    public void updateConfiguration(JenkinsConfig config) throws ApsSystemException ;
    
}
