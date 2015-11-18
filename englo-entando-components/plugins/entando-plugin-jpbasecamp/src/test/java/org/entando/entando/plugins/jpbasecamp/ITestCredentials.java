package org.entando.entando.plugins.jpbasecamp;

import java.io.File;

public interface ITestCredentials {
    
    public final static String ACCESS_TOKEN = "BAhbByIBsHsidXNlcl9pZHMiOlsxODk4MzMyOF0sInZlcnNpb24iOjEsImNsaWVudF9pZCI6IjExOTExMmMwOTEzNGQ3ODBiNDZmYjFiOGQ1MmQwZWMwNGQyMmUyMzAiLCJleHBpcmVzX2F0IjoiMjAxNS0xMS0wM1QwOTo0NDozMFoiLCJhcGlfZGVhZGJvbHQiOiJkMDgxNzdmODk5MGZiOGUyMmRmNWFmYjgyYjdhYTkzOSJ9dToJVGltZQ1p6BzAMX3osQ==--e7da43acc151d31e3ebd2e71b6441b431457b06d";
    public final static String USERNAME = "EMAIL";
    public final static String PASSWORD = "PASSWORD";
    public final static String ACCOUNT = "1234456";
    public final static String ADMIN_ID = "7890123";
    
    //INSERT A WORKING ACCESS TOKEN BELOW
    
    public final static String RESOURCE_PATH = "/Users/entando/workspace/progetti/englo/entando-components/plugins/entando-plugin-jpbasecamp/src/test/resources/files";
    
    
    // You don't really need to modify these
    public final static String TEST_IMAGE = RESOURCE_PATH + File.separator + "User_speaks_Italian.png" ;
    public final static String UPLOADED_RESOURCE_PREFIX = "uploaded_";
    public final static String INSTANCE_URL = "https://basecamp.com/" + ACCOUNT + "/api/v1";
    public final static String AUTHRIZATION_JSON = "{\"identity\":{\"id\":4752027,\"first_name\":\"Matteo\",\"last_name\":\"Emanuele\",\"email_address\":\"EMAIL\"},\"expires_at\":\"2014-09-09T13:08:36Z\",\"accounts\":[{\"product\":\"bcx\",\"id\":" + ACCOUNT + ",\"name\":\"Entando\",\"href\":\"" + INSTANCE_URL + "\"}]}";
    
}
