package com.ipsurvey.database;

import java.sql.Connection;

/**
@author Nadeem
 */
public interface IDBManger {
	/**
	 * getDatabaseConnection() to obtain connection object with specific database 
	 * along with  parameters specified
	 * If connection exits returns the instance of existing connection
	 * @return Connection
	 */
	public Connection getDatabaseConnection() throws Exception;
	
}
