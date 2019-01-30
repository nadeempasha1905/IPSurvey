
package com.ipsurvey.database;


import java.util.ResourceBundle;

import com.ipsurevy.logging.LogWrapper;

/**
 * DBInfo Class to obtain database related connection parameters from properties file
 * These parameters will be used by DBManagerIMPL class to establish connection with database.
 * Parameters Specified are:
@author Madhu
 */

public class DBInfo {
	/**
	 * Parameter related variables declaration 
	 */
	 private static DBInfo _instance = null;
	 public String database = null;
     public String ip = null;
     public String user = null;
     public String pass = null;
     public String port = null;
     public String jdbc = null;
     public String driver = null;
     public String email = null;
     public String email_password = null;
     /**
 	 * Constructor to obtain parameters from properties file
 	 */
     LogWrapper Log=new LogWrapper(this.getClass().getSimpleName());

    protected DBInfo(){
    	
    try{
    	//resouce bundle to read string's specified in properties file
    	ResourceBundle propsBundle=ResourceBundle.getBundle("apmcdb");
        
    	ip = propsBundle.getString("IP");
        database = propsBundle.getString("DATABASE");
        user = propsBundle.getString("USER");
        pass = propsBundle.getString("PASS");
        port = propsBundle.getString("PORT");
        jdbc = propsBundle.getString("JDBC");
        driver = propsBundle.getString("DRIVERNAME");
        email =  propsBundle.getString("EMAIL");
        email_password =  propsBundle.getString("PASSWORD");
       
       } 
    catch(Exception e){
        //System.out.println("error" + e);
    	Log.error(this.getClass().getSimpleName()+e);
       }	 
    }
   
	/**
 	 * getinstance() method to return single instance of DBInfo Class
 	 * @return DBInfo
 	 */
    static public DBInfo getinstance(){
        if (_instance == null) {
        	
            _instance = new DBInfo();
            return _instance;
        }
        
        return _instance;
    }

}
