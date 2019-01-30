package com.ipsurvey.database;

public class DBQueries {
	
	//User Module Procedures
	
	public static final String VERIFY_USER = "{call PKG_USER.VERIFY_USER(?,?,?)}";
	
	//User Module End
	
	//PreLoader Module Procedures
	
	public static final String LOAD_VEHICLE_MODELS = "{call PKG_VEHICLE_MASTER.GET_VEHICLE_MASTER_DETAILS(?)}";
		
	//PreLoader Module End
	
}
