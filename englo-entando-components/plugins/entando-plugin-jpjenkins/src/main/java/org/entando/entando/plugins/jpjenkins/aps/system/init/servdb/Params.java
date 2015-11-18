package org.entando.entando.plugins.jpjenkins.aps.system.init.servdb;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Params.TABLE_NAME)
public class Params {
    
    public Params() {}
    
    @DatabaseField(columnName = "id",
            dataType = DataType.INTEGER,
            canBeNull=false, id = true)
    private int _id;
	
    @DatabaseField(columnName = "username",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _username;
	
    @DatabaseField(columnName = "password",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _password;
	
    @DatabaseField(columnName = "jenkins_url",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _jenkins_url;
	
    @DatabaseField(columnName = "token",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _token;
	

    public static final String TABLE_NAME = "jpjenkins_params";
}