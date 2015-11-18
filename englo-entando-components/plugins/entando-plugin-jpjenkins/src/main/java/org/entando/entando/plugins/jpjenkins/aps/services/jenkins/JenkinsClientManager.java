package org.entando.entando.plugins.jpjenkins.aps.services.jenkins;

import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.baseconfig.ConfigInterface;
import com.agiletec.aps.system.services.keygenerator.IKeyGeneratorManager;
import org.entando.entando.plugins.jpjenkins.aps.system.services.params.IParamsDAO;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.entando.entando.plugins.jpjenkins.aps.system.JpJenkinsSystemConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsClientManager extends AbstractService implements IJenkinsClientManager{
    
    private static final Logger _logger =  LoggerFactory.getLogger(JenkinsClientManager.class);
    
    /*
    @Deprecated
    private static String username;
    @Deprecated
    private static String password;
    @Deprecated
    private static String jenkinsUrl;
    @Deprecated
    private static String buildToken;
    
    @Deprecated
    public static String getUsername() {
    return username;
    }
    @Deprecated
    public static void setUsername(String username) {
    JenkinsClientManager.username = username;
    }
    @Deprecated
    public static String getPassword() {
    return password;
    }
    @Deprecated
    public static void setPassword(String password) {
    JenkinsClientManager.password = password;
    }
    @Deprecated
    public static String getJenkinsUrl() {
    return getConfig().getUrl();
    }
    @Deprecated
    public static void setJenkinsUrl(String getConfig().getUrl()) {
    JenkinsClientManager.getConfig().getUrl() = getConfig().getUrl();
    }
    @Deprecated
    public static String getBuildToken() {
    return buildToken;
    }
    @Deprecated
    public static void setBuildToken(String buildToken) {
    JenkinsClientManager.buildToken = buildToken;
    }
    */
    
    
    @Override
    public void createJob() throws ApsSystemException {
        
        try {
            this.getJenkinsClientDAO().createJob();
        } catch (Throwable t) {
            _logger.error("Error loading info '",  t);
            throw new ApsSystemException("Error loading info '", t);
        }
    }
    
    @Override
    public String getLogs(String job) throws ApsSystemException {
        String logs = null;
        try {
            logs = this.getJenkinsClientDAO().getLogs(getConfig().getUrl(), job);
        } catch (Throwable t) {
            _logger.error("Error loading info '",  t);
            throw new ApsSystemException("Error loading info '", t);
        }
        return logs;
    }
    
    @Override
    public void init() throws Exception {
        try {
            loadConfig();
            /*
            ApsSystemUtils.getLogger().debug(this.getClass().getName() + ": initialized ");
            List<Integer> paramsList = this.getParamsDAO().loadParams();
            
            if (null != paramsList
                    && ! paramsList.isEmpty()) {
                Params p = this.getParamsDAO().loadParams(paramsList.get(0));
                setUsername(p.getUsername());
                setPassword(p.getPassword());
                setJenkinsUrl(p.getJenkins_url());
                setBuildToken(p.getToken());
            } else {
                _logger.error("Missing Jenkins configuration Parameters");
            }
            */
        } catch (Throwable t) {
            _logger.error("error loading configuration item '{}'" , JpJenkinsSystemConstants.JENKINS_CONFIG);
        }
    }
    
    private void loadConfig() throws ApsSystemException {
        try {
            ConfigInterface configManager = this.getConfigManager();
            String xml = configManager.getConfigItem(JpJenkinsSystemConstants.JENKINS_CONFIG);
            
            _config = new JenkinsConfig(xml);
        } catch (Throwable t) {
            _logger.error("Error in loadConfig", t);
            throw new ApsSystemException("Error in loadConfig", t);
        }
    }
    
    @Override
    public void updateConfiguration(JenkinsConfig config) throws ApsSystemException {
        try {
            String xml = config.toXml();
            this.getConfigManager().updateConfigItem(JpJenkinsSystemConstants.JENKINS_CONFIG, xml);
            this.setConfig(config);
        } catch (Throwable t) {
            _logger.error("Error in updating configuration", t);
            throw new ApsSystemException("Error updating Jenkins configuration");
        }
    }
    
    @Override
    public String getInfo() throws ApsSystemException {
        String info = null;
        try {
            info = this.getJenkinsClientDAO().getInfo(getConfig().getUrl());
        } catch (Throwable t) {
            _logger.error("Error loading info '",  t);
            throw new ApsSystemException("Error loading info '", t);
        }
        return info;
    }
    
    @Override
    public void saveAndBuild(String jobName, String github, String url, String build_on_change) throws ApsSystemException {
        
        String requestUrl = getConfig().getUrl() + "/createItem?name=" + jobName;
        String xml_data = "<project>\n" +
                "<actions/>\n" +
                "<description/>\n" +
                "<keepDependencies>false</keepDependencies>\n" +
                "<properties>\n" +
                "<com.coravy.hudson.plugins.github.GithubProjectProperty plugin=\"github@1.11.3\">\n" +
                "<projectUrl>"+github+"</projectUrl>\n" +
                "</com.coravy.hudson.plugins.github.GithubProjectProperty>\n" +
                "</properties>\n" +
                "<scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@2.3.5\">\n" +
                "<configVersion>2</configVersion>\n" +
                "<userRemoteConfigs>\n" +
                "<hudson.plugins.git.UserRemoteConfig>\n" +
                "<url>"+url+"</url>\n" +
                "</hudson.plugins.git.UserRemoteConfig>\n" +
                "</userRemoteConfigs>\n" +
                "<branches>\n" +
                "<hudson.plugins.git.BranchSpec>\n" +
                "<name>*/master</name>\n" +
                "</hudson.plugins.git.BranchSpec>\n" +
                "</branches>\n" +
                "<doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>\n" +
                "<submoduleCfg class=\"list\"/>\n" +
                "<extensions/>\n" +
                "</scm>\n" +
                "<canRoam>true</canRoam>\n" +
                "<disabled>false</disabled>\n" +
                "<blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
                "<blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
                "<authToken>620124b9bb76a138fdafbe15543beadb</authToken>\n";
        if(build_on_change.equals("true")){
            xml_data += "<triggers>\n" +
                    "<com.cloudbees.jenkins.GitHubPushTrigger plugin=\"github@1.11.3\">\n" +
                    "<spec/>\n" +
                    "</com.cloudbees.jenkins.GitHubPushTrigger>\n" +
                    "</triggers>\n";
        } else {
            xml_data += "<triggers/>";
        }
        xml_data += "<concurrentBuild>false</concurrentBuild>\n" +
                "<builders/>\n" +
                "<publishers/>\n" +
                "<buildWrappers/>\n" +
                "</project>";
        doRequest(requestUrl, xml_data);
    }
    
    @Override
    public void build(String jobName) throws ApsSystemException {
        final String token = getConfig().getToken();
        
        String requestUrl = getConfig().getUrl() + "/job/" + jobName + "/build?token=" + token;
        doRequest(requestUrl, null);
    }
    
    private void doRequest(String requestUrl, String xml_data) {
        final String username = getConfig().getUsername();
        final String password = getConfig().getPassword();
        DefaultHttpClient client = new DefaultHttpClient();
        
        client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username, password));
        
        BasicScheme basicAuth = new BasicScheme();
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute("preemptive-auth", basicAuth);
        client.addRequestInterceptor(new PreemptiveAuth(), 0);
        HttpPost request = new HttpPost(requestUrl);
        
        try {
            if (xml_data != null) {
                request.setEntity(new StringEntity(xml_data, ContentType.create("text/xml", "utf-8")));
            }
            HttpResponse response = client.execute(request, context);
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class PreemptiveAuth implements HttpRequestInterceptor {
        
        @Override
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
            
            if (authState.getAuthScheme() == null) {
                AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
                CredentialsProvider credsProvider = (CredentialsProvider) context
                        .getAttribute(ClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (authScheme != null) {
                    Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost
                            .getPort()));
                    if (creds == null) {
                        throw new HttpException("No credentials for preemptive authentication");
                    }
                    authState.setAuthScheme(authScheme);
                    authState.setCredentials(creds);
                }
            }
            
        }
        
    }
    
    @Override
    public JenkinsConfig getConfig() {
        return _config;
    }
    
    public void setConfig(JenkinsConfig config) {
        this._config = config;
    }
    @Deprecated
    public IJenkinsClientDAO getJenkinsClientDAO() {
        return _jenkinsClientDAO;
    }
    @Deprecated
    public void setJenkinsClientDAO(IJenkinsClientDAO _jenkinsClientDAO) {
        this._jenkinsClientDAO = _jenkinsClientDAO;
    }
    
    public ConfigInterface getConfigManager() {
        return _configManager;
    }
    
    public void setConfigManager(ConfigInterface configmanager) {
        this._configManager = configmanager;
    }
    
    @Deprecated
    protected IKeyGeneratorManager getKeyGeneratorManager() {
        return _keyGeneratorManager;
    }
    @Deprecated
    public void setKeyGeneratorManager(IKeyGeneratorManager keyGeneratorManager) {
        this._keyGeneratorManager = keyGeneratorManager;
    }
    @Deprecated
    public void setParamsDAO(IParamsDAO paramsDAO) {
        this._paramsDAO = paramsDAO;
    }
    @Deprecated
    protected IParamsDAO getParamsDAO() {
        return _paramsDAO;
    }
    
    private JenkinsConfig _config;
    
    @Deprecated
    private IJenkinsClientDAO _jenkinsClientDAO;
    @Deprecated
    private IKeyGeneratorManager _keyGeneratorManager;
    @Deprecated
    private IParamsDAO _paramsDAO;
    
    private ConfigInterface _configManager;
}
