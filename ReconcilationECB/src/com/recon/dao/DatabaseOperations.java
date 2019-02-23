/**
 * 
 */
package com.recon.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nadeem
 *
 */
public class DatabaseOperations {
	
	
	public static Connection getOracleConnection(String databaseIP,String databaseUSERNAME,String databasePASSWORD, String databaseNAME) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					//"jdbc:oracle:thin:@192.168.0.201:1521:billnet", "bill",
					"jdbc:oracle:thin:@"+databaseIP+":1521:"+databaseNAME, ""+databaseUSERNAME+"", ""+databasePASSWORD+"");
		} catch (Exception e) {
			System.out.println("Error Occured :" + e.getMessage());
		}
		return con;
	}

	public static void cleanUp(PreparedStatement ps, ResultSet rs) {
		// TODO Auto-generated method stub
			try {
				if(ps != null){
					ps.close();
				}
				
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void cleanUp(PreparedStatement ps, ResultSet rs,Connection con) {
		// TODO Auto-generated method stub
			try {
				if(con != null){
					con.close();
				}
				
				if(ps != null){
					ps.close();
				}
				
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
