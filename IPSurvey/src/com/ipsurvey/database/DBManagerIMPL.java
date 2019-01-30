package com.ipsurvey.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ipsurevy.logging.LogWrapper;


/**
@author Nadeem
 */
public class DBManagerIMPL implements IDBManger{
	
	//private static DBManagerIMPL _instance=null;
	LogWrapper Log=new LogWrapper(this.getClass().getSimpleName());
	
	private static Connection dbconn=null;
	String sURL;
	
	

	/**
 	 * Constructor to create load instance of database driver class
 	 */
    public DBManagerIMPL(){

    	DBInfo dbInfo=DBInfo.getinstance();

		// Load the database driver
		try {
			//Obtain class object with driver name specified
			Class.forName(dbInfo.driver).newInstance();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		//String to hold url to be passed to getConnection()
		 sURL =dbInfo.jdbc+":@"+dbInfo.ip+":"+dbInfo.port+":"+dbInfo.database;
		 
	}
     


	/**
	 * getDatabaseConnection() to obtain connection object with database parameters specified
	 * If connection exits returns the instance of existing connection
	 * @return Connection
	 */
	public Connection getDatabaseConnection() throws Exception {
    	DBInfo dbInfo=DBInfo.getinstance();

		/**
		 * Check for connection object is either null or any previous connection were closed
		 * if so return new connection or else return existing connection 			
		 */
		if((dbconn == null)||(dbconn.isClosed()))
		{
			//get the connection object
			dbconn = DriverManager.getConnection(sURL,dbInfo.user, dbInfo.pass);
			//System.out.println("Returning new Database ..."+dbconn);
			Log.info("["+this.getClass().getSimpleName()+"]-->"+"Returning new Database ..."+dbconn);
			//returning new database connection 
			return dbconn;
		}
		//System.out.println("Database Connected...Returning Existing Connection"+dbconn);
		//Log.info("["+this.getClass().getSimpleName()+"]-->"+"Database Connected...Returning Existing Connection"+dbconn);
		//returning existing database connection
	return dbconn ;
	}
}
