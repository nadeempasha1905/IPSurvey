/**
 * 
 */
package com.ipsurvey.util;

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
public class UtilityImpl implements IUtility{
	
	// database connection init
	static DBManagerIMPL dbObject = new DBManagerIMPL();
	static Connection dbConn;
	
	public UtilityImpl() {
		// TODO Auto-generated constructor stub
		try {
			dbConn = dbObject.getDatabaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public JSONObject getcodedetails(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT  CM_VALUE , CM_DESCR  FROM CODE_MASTER WHERE NVL(CM_DEL_FLG,'N') = 'N' AND CM_TYPE='"+(String)object.get("code_type")+"'  ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key",   rs.getString("CM_VALUE"));
				json.put("value", rs.getString("CM_DESCR"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("CODES_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getzonelist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT LOCATION_CODE, LOCATION_NAME  FROM LOCATION WHERE LOCATION_TYPE = 'ZN' ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key", 	rs.getString("LOCATION_CODE"));
				json.put("value", 			rs.getString("LOCATION_NAME"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("ZONE_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getcirclelist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT LOCATION_CODE, LOCATION_NAME  FROM LOCATION WHERE LOCATION_CODE like '"+(String)object.get("location_code")+"%' AND LOCATION_TYPE = 'CR' ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key", 	rs.getString("LOCATION_CODE"));
				json.put("value", 			rs.getString("LOCATION_NAME"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("CIRCLE_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getdivisionlist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT LOCATION_CODE, LOCATION_NAME  FROM LOCATION WHERE LOCATION_CODE like '"+(String)object.get("location_code")+"%' AND LOCATION_TYPE = 'DN' ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key", 	rs.getString("LOCATION_CODE"));
				json.put("value", 			rs.getString("LOCATION_NAME"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("DIVISION_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getsubdivisionlist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT LOCATION_CODE, LOCATION_NAME  FROM LOCATION WHERE LOCATION_CODE like '"+(String)object.get("location_code")+"%' AND LOCATION_TYPE = 'SD' ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key", 	rs.getString("LOCATION_CODE"));
				json.put("value", 			rs.getString("LOCATION_NAME"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("SUBDIVISION_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getomsectionlist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT LOCATION_CODE, LOCATION_NAME  FROM LOCATION WHERE LOCATION_CODE like '"+(String)object.get("location_code")+"%' AND LOCATION_TYPE = 'OM' ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key", 	rs.getString("LOCATION_CODE"));
				json.put("value", 			rs.getString("LOCATION_NAME"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("OMSECTION_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getvillagemasterdata(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT VM_VILLAGE_NAME FROM VILLAGE_MASTER "
										+ "	WHERE VM_LOCATION_CODE = '"+(String)object.get("location_code")+"' " ;
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("VM_VILLAGE_NAME", 	rs.getString("VM_VILLAGE_NAME"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("VILLAGE_MASTER_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getstationlist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT SM_STATION_CODE, SM_STATION_NAME  "
					+ " FROM STATION_MASTER "
					+ " WHERE SM_LOCATION_CODE = '"+(String)object.get("location_code")+"' AND SM_DELETE_STATUS = 'N' " ;
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("key", 	rs.getString("SM_STATION_CODE"));
				json.put("value", 			rs.getString("SM_STATION_NAME"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("STATION_MASTER_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getfeederlist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT FM_FEEDER_CODE, FM_FEEDER_NAME "
					+ " FROM FEEDER_MASTER  "
					+ " WHERE FM_LOCATION_CODE = '"+(String)object.get("location_code")+"' AND NVL(FM_DELETE_FLAG,'N') = 'N' AND "
					+ " FM_STATION_CODE = '"+(String)object.get("station_code")+"' ORDER BY FM_FEEDER_NAME ASC " ;

			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("key", 	rs.getString("FM_FEEDER_CODE"));
				json.put("value", 			rs.getString("FM_FEEDER_NAME"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("FEEDER_MASTER_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject gettransformermasterdata(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " SELECT TM_LOCATION_CODE, TM_STATION_CODE, TM_FEEDER_CODE, TM_OM_CODE, TM_TRANSFORMER_CODE, "
					+ " TM_TRANSFORMER_NAME, TM_SURVEY_STS, TM_CREATED_BY, TO_CHAR(TM_CREATED_ON,'DD/MM/YYYY HH:MI::SS AM') TM_CREATED_ON, TM_UPDATED_BY, TO_CHAR(TM_UPDATED_ON,'DD/MM/YY HH:MI:SS AM') TM_UPDATED_ON, TM_CAPACITY_KVA,"
					+ " TM_VILLAGE  "
					+ " FROM TRANSFORMER_MASTER "
					+ "	WHERE "
					+ " TM_OM_CODE = '"+(String)object.get("location_code")+"' "
					+ " and nvl(TM_SURVEY_STS,'N') = 'N' " ;
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("TM_LOCATION_CODE", 	rs.getString("TM_LOCATION_CODE"));
				json.put("TM_STATION_CODE", 	rs.getString("TM_STATION_CODE"));
				json.put("TM_FEEDER_CODE", 	rs.getString("TM_FEEDER_CODE"));
				json.put("TM_OM_CODE", 	rs.getString("TM_OM_CODE"));
				json.put("TM_TRANSFORMER_CODE", 	rs.getString("TM_TRANSFORMER_CODE"));
				json.put("TM_TRANSFORMER_NAME", 	rs.getString("TM_TRANSFORMER_NAME"));
				json.put("TM_SURVEY_STS", 	rs.getString("TM_SURVEY_STS"));
				json.put("TM_CREATED_BY", 	rs.getString("TM_CREATED_BY"));
				json.put("TM_CREATED_ON", 	rs.getString("TM_CREATED_ON"));
				json.put("TM_UPDATED_BY", 	rs.getString("TM_UPDATED_BY"));
				json.put("TM_UPDATED_ON", 	rs.getString("TM_UPDATED_ON"));
				json.put("TM_CAPACITY_KVA", 	rs.getString("TM_CAPACITY_KVA"));
				json.put("TM_VILLAGE", 	rs.getString("TM_VILLAGE"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("TRANSFORMER_MASTER_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getenumeratedvillageslist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " select VE_VILLAGE_NAME from VILLAGE_ENUMERATION  "
					+ " where VE_LOCATION_CODE = '"+(String)object.get("location_code")+"' AND NVL(VE_DELETED_FLAG,'N') = 'N'  "
							+ " order by nvl(VE_UPDATED_ON,VE_CREATED_ON) desc " ;
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("key", 	rs.getString("VE_VILLAGE_NAME"));
				json.put("value", 	rs.getString("VE_VILLAGE_NAME"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("VILLAGE_ENUM_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getenumeratedtransformerslist(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " select TE_TRANSFORMER_CODE, TE_TRANSFORMER_NAME  "
					+ " from TRANSFORMER_ENUMERATION  "
					+ " where "
					+ " TE_OM_CODE like '"+(String)object.get("location_code")+"%' " ;
			
					if(((String)object.get("station_code")).length() > 1) {
						get_permission_query = get_permission_query + "  AND  TE_STATION_CODE like '"+(String)object.get("station_code")+"%' " ;
					}
					
					if(((String)object.get("feeder_code")).length() > 1) {
						get_permission_query = get_permission_query + "  AND  TE_FEEDER_CODE like '"+(String)object.get("feeder_code")+"%' " ;
					}
					
					get_permission_query = get_permission_query + "  AND nvl(TE_DELETED_FLAG,'N') = 'N' order by TE_TRANSFORMER_NAME " ;
			
					System.out.println(get_permission_query);
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			while (rs.next()) {
				json = new JSONObject();
				json.put("key", 	rs.getString("TE_TRANSFORMER_CODE"));
				json.put("value", 	rs.getString("TE_TRANSFORMER_NAME"));
				json_array.add(json);
			}
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("TRANSFORMER_ENUM_DATA", json_array);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		return JSON_RESPONSE;
	}

	@Override
	public JSONObject getcastecategory(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		JSONObject json = null;
		JSONArray  json_array = new JSONArray();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			String get_permission_query = " select distinct categoryid, categoryname from caste_master order by categoryid ";
			
			ps = dbConn.prepareStatement(get_permission_query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				json = new JSONObject();
				json.put("key",   rs.getString("categoryid"));
				json.put("value", rs.getString("categoryname"));
				json_array.add(json);
			}
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("CASTECATEGORY_LIST", json_array);
			
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBManagerResourceRelease.close(rs);
			DBManagerResourceRelease.close(ps);
		}
		
		return JSON_RESPONSE;
	}
	
	

}
