package org.entando.entando.plugins.jpjenkins.aps.services.jenkins;

public interface IJenkinsClientDAO {
    
    public String executeLogin();
	
    public String getInfo(String url);

    public String createJob(); 
    
    public String getLogs(String url, String job);
    
}
