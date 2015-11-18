package org.entando.entando.plugins.jptrello.aps.system.init.servdb;
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
	
    @DatabaseField(columnName = "organization",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _organization;
	
    @DatabaseField(columnName = "api_key",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _api_key;
	
    @DatabaseField(columnName = "api_secret",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _api_secret;
	
    @DatabaseField(columnName = "token",
            dataType = DataType.LONG_STRING,
            canBeNull= true)
    private String _token;
	
    public static final String TABLE_NAME = "jptrello_params";
}
