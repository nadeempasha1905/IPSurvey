/**
 * 
 */
package com.ipsurvey.map;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ipsurvey.database.DBManagerIMPL;
import com.ipsurvey.database.DBManagerResourceRelease;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

/**
 * @author Nadeem
 *
 */
public class ViewMapImpl implements IViewMap {
	
	//database connection init
			static DBManagerIMPL dbObject = new DBManagerIMPL();
			static Connection dbConn;
		
		public ViewMapImpl() {
			// TODO Auto-generated constructor stub
			try {
				dbConn = dbObject.getDatabaseConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public JSONObject gettransformerpoints(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			try {
				String sql = " SELECT ROWIDTOCHAR (ROWID) row_id,TE_TRANSFORMER_NAME, TE_TIMS_CODE, TE_CAPACITY_KVA,"
						+ "  TE_LONGITUDE, TE_LATTITUDE, TE_ALTITUDE,TE_VILLAGE "
						+ "  FROM TRANSFORMER_ENUMERATION WHERE "
						+ "  TE_OM_CODE = '"+(String)object.get("location_code")+"' "
					//			+ " AND "
					//	+ " EXISTS (SELECT 'X' FROM IP_ENUMERATION WHERE IE_OM_CODE = TE_OM_CODE AND IE_TRANSFORMER_CODE = TE_TRANSFORMER_CODE)"
								;
				
				if(object.getString("village") .length() > 0) {
					sql = sql +  "  AND TE_VILLAGE = '"+object.getString("village")+"' ";
				}
					
					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", rs.getString("row_id"));
						json.put("TE_TRANSFORMER_NAME", rs.getString("TE_TRANSFORMER_NAME"));
						json.put("TE_TIMS_CODE", rs.getString("TE_TIMS_CODE"));
						json.put("TE_CAPACITY_KVA", rs.getString("TE_CAPACITY_KVA").toString().trim());
						json.put("TE_LONGITUDE", rs.getString("TE_LONGITUDE"));
						json.put("TE_LATTITUDE", rs.getString("TE_LATTITUDE"));
						json.put("TE_ALTITUDE", rs.getString("TE_ALTITUDE"));
						json.put("TE_VILLAGE", rs.getString("TE_VILLAGE"));
						json.put("type","transformer");
						
						array.add(json);
					}

					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("data", array);

					//ps.close();
					//rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSON_RESPONSE.put("status", "error");
				JSON_RESPONSE.put("data", "");
			} finally {
				//DBManagerResourceRelease.close(rs, ps, dbConn);
				DBManagerResourceRelease.close(rs);
				DBManagerResourceRelease.close(ps);
			}
			
			return JSON_RESPONSE;
		}

		@Override
		public JSONObject getmappingpoints(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			CallableStatement calstmt = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			
			try {
				
				 if(object.isEmpty()) {
					 JSON_RESPONSE.put("status", "error");
					 JSON_RESPONSE.put("message", "Invalid Object");
				 }else {
					
					 String sql = "{ call GET_MAP_DETAILS(?,'"+(String)object.get("location_code")+"',"
					 		+ "'"+((String)object.get("station_code") == null || (String)object.get("station_code") == "" ? "" : (String)object.get("station_code") )+"',"
					 				+ "'"+((String)object.get("feeder_code") == null || (String)object.get("feeder_code") == "" ? "" : (String)object.get("feeder_code"))+"',"
					 						+ "'"+((String)object.get("village") == null || (String)object.get("village") == "" ? "" : (String)object.get("village"))+"',"
					 								+ "'"+((String)object.get("transformer_code") == null || (String)object.get("transformer_code") == "" ? "" : (String)object.get("transformer_code"))+"')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeQuery();
				
				rs = (ResultSet) calstmt.getObject(1);
				
				while(rs.next()) {
					
					json = new JSONObject();
					
					json.put("OM_CODE", (rs.getString("OM_CODE") == null ? "" : rs.getString("OM_CODE")));
					json.put("OM_NAME", (rs.getString("OM_NAME") == null ? "" : rs.getString("OM_NAME")));
					json.put("STATION_CODE", (rs.getString("STATION_CODE") == null ? "" : rs.getString("STATION_CODE")));
					json.put("STATION_NAME", (rs.getString("STATION_NAME") == null ? "" : rs.getString("STATION_NAME")));
					json.put("FEEDER_CODE", (rs.getString("FEEDER_CODE") == null ? "" : rs.getString("FEEDER_CODE")));
					json.put("FEEDER_NAME", (rs.getString("FEEDER_NAME") == null ? "" : rs.getString("FEEDER_NAME")));
					json.put("VILLAGE_NAME", (rs.getString("VILLAGE_NAME") == null ? "" : rs.getString("VILLAGE_NAME")));
					json.put("VILLAGE_LATTITUDE", (rs.getString("VILLAGE_LATTITUDE") == null ? "" : rs.getString("VILLAGE_LATTITUDE")));
					json.put("VILLAGE_LONGITUDE", (rs.getString("VILLAGE_LONGITUDE") == null ? "" : rs.getString("VILLAGE_LONGITUDE")));
					json.put("VILLAGE_ALTITUDE", (rs.getString("VILLAGE_ALTITUDE") == null ? "" : rs.getString("VILLAGE_ALTITUDE")));
					json.put("VILLAGE_IMAGE_PATH", (rs.getString("VILLAGE_IMAGE_PATH") == null ? "" : rs.getString("VILLAGE_IMAGE_PATH")));
					json.put("TC_CODE", (rs.getString("TC_CODE") == null ? "" : rs.getString("TC_CODE")));
					json.put("TC_NAME", (rs.getString("TC_NAME") == null ? "" : rs.getString("TC_NAME")));
					json.put("TC_CAPACITY_KVA", (rs.getString("TC_CAPACITY_KVA") == null ? "" : rs.getString("TC_CAPACITY_KVA")));
					json.put("TC_LATTITUDE", (rs.getString("TC_LATTITUDE") == null ? "" : rs.getString("TC_LATTITUDE")));
					json.put("TC_LONGITUDE", (rs.getString("TC_LONGITUDE") == null ? "" : rs.getString("TC_LONGITUDE")));
					json.put("TC_ALTITUDE", (rs.getString("TC_ALTITUDE") == null ? "" : rs.getString("TC_ALTITUDE")));
					json.put("TC_IMAGE_PATH", (rs.getString("TC_IMAGE_PATH") == null ? "" : rs.getString("TC_IMAGE_PATH")));
					
					if(((String)object.get("transformer_code")).length() > 0) {
						json.put("POLE_CODE", (rs.getString("POLE_CODE") == null ? "" : rs.getString("POLE_CODE")));
						json.put("POLE_NUMBER", (rs.getString("POLE_NUMBER") == null ? "" : rs.getString("POLE_NUMBER")));
						json.put("POLE_LATTITUDE", (rs.getString("POLE_LATTITUDE") == null ? "" : rs.getString("POLE_LATTITUDE")));
						json.put("POLE_LONGITUDE", (rs.getString("POLE_LONGITUDE") == null ? "" : rs.getString("POLE_LONGITUDE")));
						json.put("POLE_ALTITUDE", (rs.getString("POLE_ALTITUDE") == null ? "" : rs.getString("POLE_ALTITUDE")));
						json.put("POLE_IMAGE_PATH", (rs.getString("POLE_IMAGE_PATH") == null ? "" : rs.getString("POLE_IMAGE_PATH")));
						json.put("RR_NO", (rs.getString("RR_NO") == null ? "" : rs.getString("RR_NO")));
						json.put("CODE_NUMBER", (rs.getString("CODE_NUMBER") == null ? "" : rs.getString("CODE_NUMBER")));
						json.put("SERVICE_DATE", (rs.getString("SERVICE_DATE") == null ? "" : rs.getString("SERVICE_DATE")));
						json.put("CUSTOMER_NAME", (rs.getString("CUSTOMER_NAME") == null ? "" : rs.getString("CUSTOMER_NAME")));
						json.put("ADDRESS1", (rs.getString("ADDRESS1") == null ? "" : rs.getString("ADDRESS1")));
						json.put("ADDRESS2", (rs.getString("ADDRESS2") == null ? "" : rs.getString("ADDRESS2")));
						json.put("PHONE_NUMBER", (rs.getString("PHONE_NUMBER") == null ? "" : rs.getString("PHONE_NUMBER")));
						json.put("LOAD_KW", (rs.getString("LOAD_KW") == null ? "" : rs.getString("LOAD_KW")));
						json.put("LOAD_HP", (rs.getString("LOAD_HP") == null ? "" : rs.getString("LOAD_HP")));
						json.put("SCHEME", (rs.getString("SCHEME") == null ? "" : rs.getString("SCHEME")));
						json.put("CONNECTION_TYPE", (rs.getString("CONNECTION_TYPE") == null ? "" : rs.getString("CONNECTION_TYPE")));
						json.put("CUSTOMER_STATUS", (rs.getString("CUSTOMER_STATUS") == null ? "" : rs.getString("CUSTOMER_STATUS")));
						json.put("WATER_SOURCE", (rs.getString("WATER_SOURCE") == null ? "" : rs.getString("WATER_SOURCE")));
						json.put("INSPECTION_DATE", (rs.getString("INSPECTION_DATE") == null ? "" : rs.getString("INSPECTION_DATE")));
						json.put("PHASE", (rs.getString("PHASE") == null ? "" : rs.getString("PHASE")));
						json.put("VOLTAGE_RY", (rs.getString("VOLTAGE_RY") == null ? "" : rs.getString("VOLTAGE_RY")));
						json.put("VOLTAGE_RB", (rs.getString("VOLTAGE_RB") == null ? "" : rs.getString("VOLTAGE_RB")));
						json.put("VOLTAGE_BR", (rs.getString("VOLTAGE_BR") == null ? "" : rs.getString("VOLTAGE_BR")));
						json.put("CURRENT_R", (rs.getString("CURRENT_R") == null ? "" : rs.getString("CURRENT_R")));
						json.put("CURRENT_Y", (rs.getString("CURRENT_Y") == null ? "" : rs.getString("CURRENT_Y")));
						json.put("CURRENT_B", (rs.getString("CURRENT_B") == null ? "" : rs.getString("CURRENT_B")));
						json.put("IP_LATTITUDE", (rs.getString("IP_LATTITUDE") == null ? "" : rs.getString("IP_LATTITUDE")));
						json.put("IP_LONGITUDE", (rs.getString("IP_LONGITUDE") == null ? "" : rs.getString("IP_LONGITUDE")));
						json.put("IP_ALTITUDE", (rs.getString("IP_ALTITUDE") == null ? "" : rs.getString("IP_ALTITUDE")));
						json.put("REMARKS", (rs.getString("REMARKS") == null ? "" : rs.getString("REMARKS")));
						json.put("METER_SLNO", (rs.getString("METER_SLNO") == null ? "" : rs.getString("METER_SLNO")));
						json.put("METER_MAKE", (rs.getString("METER_MAKE") == null ? "" : rs.getString("METER_MAKE")));
						json.put("METER_TYPE", (rs.getString("METER_TYPE") == null ? "" : rs.getString("METER_TYPE")));
						json.put("FINAL_READING", (rs.getString("FINAL_READING") == null ? "" : rs.getString("FINAL_READING")));
						json.put("IP_IMAGE_PATH", (rs.getString("IP_IMAGE_PATH") == null ? "" : rs.getString("IP_IMAGE_PATH")));
						json.put("CREATED_BY", (rs.getString("CREATED_BY") == null ? "" : rs.getString("CREATED_BY")));
						json.put("CREATED_ON", (rs.getString("CREATED_ON") == null ? "" : rs.getString("CREATED_ON")));
					}
					array.add(json);
				}
				
				JSON_RESPONSE.put("status", "success");
				JSON_RESPONSE.put("data", array);
				
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Database error occured !!!");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
		}

}
