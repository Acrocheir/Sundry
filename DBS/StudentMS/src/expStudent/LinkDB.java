package expStudent;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.RowSet;

public class LinkDB {
	private static final String DriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; //加载JDBC驱动
	private static final String Url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=StudentMS";
	private static final String UserName = "sa";
	private static final String Password = "mtj9527";
	/**
	 * 连接数据库
	 */
	public Connection lindDataBase(){
		try{
			Class.forName(DriverName);
			Connection dbconn = DriverManager.getConnection(Url,UserName,Password);
			return dbconn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
