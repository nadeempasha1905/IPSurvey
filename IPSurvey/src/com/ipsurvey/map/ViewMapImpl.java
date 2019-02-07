/**
 * 
 */
package com.ipsurvey.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ipsurvey.database.DBManagerIMPL;
import com.ipsurvey.database.DBManagerResourceRelease;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

}
