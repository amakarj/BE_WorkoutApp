package conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Connections {
	private static HikariDataSource pool=null;
	
	public static Connection getConnection(){
		Connection conn=null;
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {
	    	try {
				conn=Connections.getProductionConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else {    
			try {
				conn=Connections.getDevConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    return conn;
	}

	//testinggggggggg
	public static Connection getProductionConnection() throws SQLException {
		if (pool!=null) {
			return pool.getConnection();
		}
	    // Set up URL parameters
	    String jdbcURL = String.format("jdbc:mysql:///%s", System.getProperty("googledatabasename"));
	    Properties connProps = new Properties();
	    connProps.setProperty("user", System.getProperty("googleusername"));
	    connProps.setProperty("password", System.getProperty("googlepassword"));
	    connProps.setProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
	    connProps.setProperty("cloudSqlInstance", System.getProperty("cloudsqlinstance"));
	    connProps.setProperty("ssl", System.getProperty("usessl"));
	    
	    // Initialize connection pool
	    HikariConfig config = new HikariConfig();
	    config.setJdbcUrl(jdbcURL);
	    config.setDataSourceProperties(connProps);
	    config.setConnectionTimeout(10000); // 10s
	    config.setDriverClassName(System.getProperty("drivername"));
	    config.setMaximumPoolSize(10);//Maximum of ten connections
	    config.setMinimumIdle(2);//At least two connections waiting in the pool (if not all in use) 
	    
	    pool = new HikariDataSource(config);
	    return pool.getConnection();
	}	
	public static Connection getDevConnection() throws Exception{
		if (pool!=null) {
			return pool.getConnection();
		}
		// The configuration object specifies behaviors for the connection pool.
		HikariConfig config = new HikariConfig();
		 // Configure which instance and what database user to connect with.
		config.setDriverClassName(System.getProperty("drivername")); // see appengine-web.xml
		config.setJdbcUrl("jdbc:mysql://localhost:3306/"+System.getProperty("localdatabasename")+"?useSSL=false"); // see appengine-web.xml
		config.setUsername(System.getProperty("localusername")); // see appengine-web.xml
		config.setPassword(System.getProperty("localpassword")); // see appengine-web.xml
		
		  // Initialize the connection pool using the configuration object.
		pool = new HikariDataSource(config);
		  
		Connection conn=null;
		conn = pool.getConnection();
		return conn;
	}
}