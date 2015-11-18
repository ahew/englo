package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.net.URI;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletec.aps.system.exception.ApsSystemException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class BasecampUrlHelper {
    
    
    private static Logger _logger = LoggerFactory.getLogger(BasecampUrlHelper.class);
    
    
    private BasecampUrlHelper() { };
    
    public static BasecampUrlHelper getInstance() {
        
        if (null == _instance) {
            _instance = new BasecampUrlHelper();
        }
        return _instance;
    }
    
    /**
     * Pass the configuration so that the factory can handle the request of
     * connection without a service configuration
     * @param config
     */
    public static void updateConfiguration(BasecampConfig config) {
        if (null != config) {
            BasecampUrlHelper instance = getInstance();
            instance._config = config;
        } else {
            throw new RuntimeException(" Invalid configuration passed to the Connection factory");
        }
    }
    
    /**
     * Evaluate the base URL from the original configuration
     * @return
     * @throws Throwable
     */
    private static String generateBaseUrlFromConfig() throws Throwable {
        StringBuilder urlBuilder = new StringBuilder(BASECAMP_BASE_URL);
        BasecampUrlHelper instance = getInstance();
        BasecampConfig config = instance._config;
        
        if (null != config
                && config.isValidForUnmannedUtilization()) {
            urlBuilder.append(instance._config.getAccount());
            urlBuilder.append('/');
            urlBuilder.append(BASECAMP_API_PREFIX);
            urlBuilder.append('/');
        } else {
            _logger.error("Error while generating URL from the plugin configuration");
            throw new ApsSystemException("Missing configuration or invalid account specification");
        }
        return urlBuilder.toString();
    }
    
    /**
     * Prepare the client for basic authentication
     * @return
     * @throws Throwable
     */
    @Deprecated
    private static void prepareClientForBasicAuth(DefaultHttpClient client) throws Throwable {
        BasecampUrlHelper instance = getInstance();
        BasecampConfig config = instance._config;
        
        if (null != config
                && config.isValidForUnmannedUtilization()) {
            AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT);
            String username = config.getUsername();
            String password = config.getPassword();
            
            UsernamePasswordCredentials authCredentials = new UsernamePasswordCredentials(username, password);
            client.getCredentialsProvider().setCredentials(authScope, authCredentials);
        } else {
            _logger.error("Error while preparing http client for basic authentication");
            throw new ApsSystemException("Missing configuration or invalid account specification");
        }
    }
    
    /*
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user:pass"));
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCredentialsProvider(credentialsProvider);

        HttpGet httpget = new HttpGet("http://localhost/");

        CloseableHttpResponse response = httpclient.execute(httpget, localContext);
        try {
            EntityUtils.consume(response.getEntity());
        } finally {
            response.close();
        }
    */
    private static HttpClientContext prepareContextForBasicAuth(HttpClientContext localContext) throws Throwable {
//        HttpClientContext localContext = HttpClientContext.create();
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        BasecampUrlHelper instance = getInstance();
        BasecampConfig config = instance._config;
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                config.getUsername(), config.getPassword());
        
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        localContext.setCredentialsProvider(credentialsProvider);
        _logger.info("preparing local context for basic authentication");
        return localContext;
    }
    
    /**
     * Prepare the connection to the Basecamp API endpoint, given the plugin
     * configuration or the service data of the currently (interactively) logged
     * user
     * @param serviceData
     * @param httpVerb
     * @param client
     * @return
     * @throws Throwable
     */
    @Deprecated
    public static HttpRequestBase setUpConnection(BasecampService serviceData, HttpRequestBase httpVerb, DefaultHttpClient client) throws Throwable {
        String url = null;
        URI uri = null;
        boolean isBaseUrl = (null != httpVerb && httpVerb.getRequestLine().getUri().equals("/"));
        boolean useServiceData = (null != serviceData);
        
        if (null != httpVerb) {
            // if we don't provide a base URL we retrieve it from the service
            // (for those users authenticated with OAuth2) or from the
            // plugin configuration
            if (isBaseUrl) {
                if (useServiceData) {
                    _logger.debug("Retrieving base URL from service data");
                    url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
                } else {
                    _logger.debug("Retrieving base URL from plugin configuration");
                    url = generateBaseUrlFromConfig();
                    _logger.debug("Preparing client for basic auth");
                }
                // set URI
                uri = new URI(url);
                httpVerb.setURI(uri);
            } else {
                _logger.debug("using existing URL '{}'", httpVerb.getRequestLine().getUri());
            }
            // must process access token
            if (useServiceData) {
                String atok = "Bearer ".concat(serviceData.getAccessToken());
                httpVerb.setHeader("Authorization", atok);
                _logger.debug("Bearer provided");
            } else {
                prepareClientForBasicAuth(client);
            }
            // common headers
            httpVerb.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
            httpVerb.setHeader("Content-Type", "application/json");
            _logger.debug("Common headers set");
        }
        return httpVerb;
    }
    
    public static HttpRequestBase setUpConnection(BasecampService serviceData, HttpRequestBase httpVerb, HttpClientContext context) throws Throwable {
        String url = null;
        URI uri = null;
        boolean isBaseUrl = (null != httpVerb && httpVerb.getRequestLine().getUri().equals("/"));
        boolean useServiceData = (null != serviceData);
        
        if (null != httpVerb) {
            // if we don't provide a base URL we retrieve it from the service
            // (for those users authenticated with OAuth2) or from the
            // plugin configuration
            if (isBaseUrl) {
                if (useServiceData) {
                    _logger.debug("Retrieving base URL from service data");
                    url = serviceData.getAuthorization().getServiceUrl(BasecampSystemConstants.SERVICE_BASECAMP);
                } else {
                    _logger.info("Retrieving base URL from plugin configuration");
                    url = generateBaseUrlFromConfig();
                    _logger.debug("Preparing client for basic auth");
                }
                // set URI
                uri = new URI(url);
                httpVerb.setURI(uri);
            } else {
                _logger.debug("using existing URL '{}'", httpVerb.getRequestLine().getUri());
            }
            // must process access token
            if (useServiceData) {
                String atok = "Bearer ".concat(serviceData.getAccessToken());
                httpVerb.setHeader("Authorization", atok);
                _logger.debug("Authorization provided");
            } else {
                prepareContextForBasicAuth(context);
            }
            // common headers
            httpVerb.setHeader("User-Agent", BasecampSystemConstants.USER_AGENT);
            httpVerb.setHeader("Content-Type", "application/json");
            _logger.debug("Common headers set");
        }
        return httpVerb;
    }
    
    /**
     * Append to the given HTTP verb the given address
     * @param httpVerb
     * @param url
     * @return
     * @throws Throwable
     */
    public static URI appendToUri(HttpRequestBase httpVerb, String url) throws Throwable {
        URI uri = null;
        
        if (httpVerb != null) {
            String originalUrl = httpVerb.getRequestLine().getUri();
            
            StringBuilder tmp = new StringBuilder(originalUrl);
            if (!originalUrl.endsWith("/")
                    && !url.startsWith("/")) {
                tmp.append("/");
            } else if (originalUrl.endsWith("/")
                    && url.startsWith("/")) {
                url = url.substring(1);
            }
            tmp.append(url);
            uri = new URI(tmp.toString());
        }
        httpVerb.setURI(uri);
        return uri;
    }
    
    /**
     * Append to the given HTTP verb the address base of the desired project
     * @param httpVerb
     * @param id
     * @return
     * @throws Throwable
     */
    public static URI appendProjectBaseToUri(HttpRequestBase httpVerb, Long id) throws Throwable {
        URI uri = null;
        String sid = String.valueOf(id);
        String url = ProjectManager.PROJECTS_BASE_URL.concat(sid).concat("/");
        
        if (httpVerb != null) {
            String originalUrl = httpVerb.getRequestLine().getUri();
            
            StringBuilder tmp = new StringBuilder(originalUrl);
            if (!originalUrl.endsWith("/")
                    && !url.startsWith("/")) {
                tmp.append("/");
            } else if (originalUrl.endsWith("/")
                    && url.startsWith("/")) {
                url = url.substring(1);
            }
            tmp.append(url);
            uri = new URI(tmp.toString());
        }
        httpVerb.setURI(uri);
        return uri;
    }
    
    /**
     * Generate the correctUrl to insert a new Todo given the project and todolist IDs
     * @param verb
     * @param projectId
     * @param todolistId
     * @return
     * @throws Throwable
     */
    public static URI appendTodoCreationUrl(HttpRequestBase verb, Long projectId, Long todolistId) throws Throwable {
        URI uri = null;
        StringBuilder cmp = new StringBuilder(ProjectManager.PROJECTS_BASE_URL);
        cmp.append(projectId);
        cmp.append(TodolistManager.TODOLISTS_BASE_URL);
        cmp.append(todolistId);
        cmp.append(TodolistManager.TODOS_QUERY_URL);
        
        if (verb != null) {
            String url = cmp.toString();
            String originalUrl = verb.getRequestLine().getUri();
            
            StringBuilder tmp = new StringBuilder(originalUrl);
            if (!originalUrl.endsWith("/")
                    && !url.startsWith("/")) {
                tmp.append("/");
            } else if (originalUrl.endsWith("/")
                    && url.startsWith("/")) {
                url = url.substring(1);
            }
            tmp.append(url);
            uri = new URI(tmp.toString());
        }
        verb.setURI(uri);
        return uri;
    }
    
    /**
     * Extract the project ID from the location header returned by Basecamp
     * @param url
     * @return
     */
    public static Long getProjectIdFromUrl(String url) {
        Long id = null;
        String[] tokens = url.split(ProjectManager.PROJECTS_BASE_URL);
        
        if (tokens.length > 1) {
            tokens = tokens[1].split("/");
            id = new Long(tokens[0]);
        }
        return id;
    }
    
    /**
     * Extract the Todolist ID from the location header returned by Basecamp
     * @param url
     * @return
     */
    public static Long getTodolistIdFromUrl(String url) {
        Long id = null;
        
        String[] tokens = url.split(TodolistManager.TODOLISTS_BASE_URL);
        
        if (tokens.length > 1) {
            tokens = tokens[1].split("/");
            id = new Long(tokens[0]);
        }
        return id;
    }
    
    private BasecampConfig _config;
    
    private static BasecampUrlHelper _instance;
    
    public final static String BASECAMP_BASE_URL = "https://basecamp.com/";
    private static final Object BASECAMP_API_PREFIX = "api/v1";
}
