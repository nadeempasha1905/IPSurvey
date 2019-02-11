/**
 * 
 */
package com.ipsurvey.user;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipsurvey.database.DBManagerIMPL;
import com.ipsurvey.database.DBManagerResourceRelease;
import com.ipsurvey.util.EncriptAndDecript;
import com.sun.org.apache.bcel.internal.generic.IFNULL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

/**
 * @author Nadeem
 *
 */
public class UserManagementImpl implements IUserManagement {
	
	// database connection init
		static DBManagerIMPL dbObject = new DBManagerIMPL();
		static Connection dbConn;
	
	public UserManagementImpl() {
		// TODO Auto-generated constructor stub
		try {
			dbConn = dbObject.getDatabaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject signin_pc(JSONObject object, HttpServletRequest request) {
		// TODO Auto-generated method stub

		
		// TODO Auto-generated method stub

		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		JSONObject json = null;
		HttpSession session = null;
		

		try {
			
			session = request.getSession();
			
			dbConn = dbObject.getDatabaseConnection();

			if (object.isEmpty()) {
				JSON_RESPONSE.put("status", "error");
				JSON_RESPONSE.put("message", "Invalid Object...!");
			} else {

				String subdiv_code = "";
				String subdiv_name = "";
				String location_code = "";
				String location_name = "";
				String division_code = "";
				String division_name = "";
				String circle_code = "";
				String circle_name = "";
				String zone_code = "";
				String zone_name = "";
				String company_code = "";
				String company_name = "";
				String user_role = "";
				String user_id = (String) object.get("username");
				String password = (String) object.get("password");
				// String imei_no = (String) object.get("imei_no");

				String Validate_user_query = " SELECT DUM_LOCATION_CODE LOCATION_CODE,"
						+ " (SELECT LOCATION_NAME FROM LOCATION "
						+ "  WHERE LOCATION_CODE = DUM_LOCATION_CODE) LOCATION_NAME,"
						+ " SUBSTR(DUM_LOCATION_CODE,1,7) SUBDIV_CODE,(SELECT LOCATION_NAME FROM LOCATION "
						+ " WHERE LOCATION_CODE = SUBSTR(DUM_LOCATION_CODE,1,7) ) SUBDIV_NAME,"
						+ " SUBSTR(DUM_LOCATION_CODE,1,5) DIV_CODE," + "  (SELECT LOCATION_NAME FROM LOCATION "
						+ "  WHERE LOCATION_CODE = SUBSTR(DUM_LOCATION_CODE,1,5) ) DIV_NAME,"
						+ " SUBSTR(DUM_LOCATION_CODE,1,3) CIR_CODE, " + " (SELECT LOCATION_NAME FROM LOCATION "
						+ " WHERE LOCATION_CODE = SUBSTR(DUM_LOCATION_CODE,1,3) ) CIR_NAME,"
						+ " SUBSTR(DUM_LOCATION_CODE,1,2) ZN_CODE, " + " (SELECT LOCATION_NAME FROM LOCATION "
						+ " WHERE LOCATION_CODE = SUBSTR(DUM_LOCATION_CODE,1,2) ) ZN_NAME,"
						+ " SUBSTR(DUM_LOCATION_CODE,1,1) COM_CODE," + "  (SELECT LOCATION_NAME FROM LOCATION"
						+ "  WHERE LOCATION_CODE = SUBSTR(DUM_LOCATION_CODE,1,1) ) COM_NAME ,DUM_USER_ROLE  "
						+ " FROM DEVICE_USER_MASTER , DEVICE_MASTER " + " where  " + " dm_id = dum_device_id and "
						/*
						 * + " dm_imei_no = '" + imei_no + "' and  "
						 */
						+ " dum_user_id = '" + user_id + "' " + " and " + " dum_password = '"
						+ EncriptAndDecript.encrypt(password) + "'";

				System.out.println(EncriptAndDecript.encrypt(password) + Validate_user_query);
				ps = dbConn.prepareStatement(Validate_user_query);
				rs = ps.executeQuery();

				if (rs.next()) {
					subdiv_code = rs.getString("SUBDIV_CODE");
					subdiv_name = rs.getString("SUBDIV_NAME");
					location_code = rs.getString("LOCATION_CODE");
					location_name = rs.getString("LOCATION_NAME");
					division_code = rs.getString("DIV_CODE");
					division_name = rs.getString("DIV_NAME");
					circle_code = rs.getString("CIR_CODE");
					circle_name = rs.getString("CIR_NAME");
					zone_code = rs.getString("ZN_CODE");
					zone_name = rs.getString("ZN_NAME");
					company_code = rs.getString("COM_CODE");
					company_name = rs.getString("COM_NAME");
					user_role = rs.getString("DUM_USER_ROLE");
				}
				System.out.println("location_code : " + location_code);

				if (!location_code.isEmpty() && location_code.length() > 0 && location_code != null) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Successfully Logged In ...!");
					JSON_RESPONSE.put("username", user_id);
					//JSON_RESPONSE.put("password", password);
					JSON_RESPONSE.put("location_code", location_code);
					JSON_RESPONSE.put("location_name", location_name);
					JSON_RESPONSE.put("division_code", division_code);
					JSON_RESPONSE.put("division_name", division_name);
					JSON_RESPONSE.put("circle_code", circle_code);
					JSON_RESPONSE.put("circle_name", circle_name);
					JSON_RESPONSE.put("zone_code", zone_code);
					JSON_RESPONSE.put("zone_name", zone_name);
					JSON_RESPONSE.put("company_code", company_code);
					JSON_RESPONSE.put("company_name", company_name);
					 JSON_RESPONSE.put("user_role", user_role);
					 JSON_RESPONSE.put("subdiv_code", subdiv_code);
					 JSON_RESPONSE.put("subdiv_name", subdiv_name);

				} else {
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Invalid Username And Password ...!");
				}

				ps.close();
				rs.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManagerResourceRelease.close(rs, ps, dbConn);
		}

		session.setAttribute("JSON_RESPONSE", JSON_RESPONSE);
		
		return JSON_RESPONSE;
	
	}

	@Override
	public JSONObject signout(JSONObject object, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getuserdetailslist(JSONObject object, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			//dbConn = dbObject.getDatabaseConnection();

				String sql = " SELECT ROWIDTOCHAR (DUM.ROWID) row_id,DUM_ID, DUM_DEVICE_ID,DUM_LOCATION_CODE, "
						+ " LOCATION_NAME DUM_LOCATION_NAME,"
						+ " DUM_USER_ID, DUM_PASSWORD, DUM_STATUS,	to_char(DUM_EFFECTIVE_FROM,'DD/MM/YYYY') DUM_EFFECTIVE_FROM, "
						+ " to_char(DUM_EFFECTIVE_TO,'DD/MM/YYYY') DUM_EFFECTIVE_TO, DUM_CREATED_BY, "
						+ " TO_CHAR(DUM_CREATED_ON, 'DD/MM/YYYY HH:MI:SS AM') DUM_CREATED_ON, DUM_UPDATED_BY, "
						+ " TO_CHAR(DUM_UPDATED_ON, 'DD/MM/YYYY HH:MI:SS AM') DUM_UPDATED_ON, DUM_USER_ROLE , "
						+ " CASE WHEN DUM_USER_ROLE = 'SURVEYOR' THEN DM_IMEI_NO ELSE NULL END IMEI_NO ,DUM_USER_NAME, "
						+ " DUM_USER_ADDRESS, DUM_USER_MOBILE, DUM_USER_EMAIL_ID"
						+ " FROM DEVICE_USER_MASTER DUM  "
						+ " LEFT OUTER JOIN DEVICE_MASTER DM ON DM.DM_ID = DUM.DUM_DEVICE_ID "
						+ " LEFT OUTER JOIN LOCATION L ON L.LOCATION_CODE = DUM.DUM_LOCATION_CODE "
						+ " WHERE DUM_LOCATION_CODE like '"+(String)object.get("location_code")+"%' " 
						+ " ORDER BY DUM_LOCATION_CODE, DUM_USER_ROLE, DUM_ID DESC" ;
				
				System.out.println(sql);

				ps = dbConn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					
					json = new JSONObject();
					
					json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
					json.put("DUM_ID", (rs.getString("DUM_ID") == null ? "" : rs.getString("DUM_ID")));
					json.put("DUM_DEVICE_ID", (rs.getString("DUM_DEVICE_ID") == null ? "" : rs.getString("DUM_DEVICE_ID")));
					json.put("DUM_LOCATION_CODE", (rs.getString("DUM_LOCATION_CODE") == null ? "" : rs.getString("DUM_LOCATION_CODE")));
					json.put("DUM_LOCATION_NAME", (rs.getString("DUM_LOCATION_NAME") == null ? "" : rs.getString("DUM_LOCATION_NAME")));
					json.put("DUM_USER_ID", (rs.getString("DUM_USER_ID") == null ? "" : rs.getString("DUM_USER_ID")));
					json.put("DUM_PASSWORD", (rs.getString("DUM_PASSWORD") == null ? "" : rs.getString("DUM_PASSWORD")));
					json.put("DUM_STATUS", (rs.getString("DUM_STATUS") == null ? "" : rs.getString("DUM_STATUS")));
					json.put("DUM_EFFECTIVE_FROM", (rs.getString("DUM_EFFECTIVE_FROM") == null ? "" : rs.getString("DUM_EFFECTIVE_FROM")));
					json.put("DUM_EFFECTIVE_TO", (rs.getString("DUM_EFFECTIVE_TO") == null ? "" : rs.getString("DUM_EFFECTIVE_TO")));
					json.put("DUM_CREATED_BY", (rs.getString("DUM_CREATED_BY") == null ? "" : rs.getString("DUM_CREATED_BY")));
					json.put("DUM_CREATED_ON", (rs.getString("DUM_CREATED_ON") == null ? "" : rs.getString("DUM_CREATED_ON")));
					json.put("DUM_UPDATED_BY", (rs.getString("DUM_UPDATED_BY") == null ? "" : rs.getString("DUM_UPDATED_BY")));
					json.put("DUM_UPDATED_ON", (rs.getString("DUM_UPDATED_ON") == null ? "" : rs.getString("DUM_UPDATED_ON")));
					json.put("DUM_USER_ROLE", (rs.getString("DUM_USER_ROLE") == null ? "" : rs.getString("DUM_USER_ROLE")));
					json.put("IMEI_NO", (rs.getString("IMEI_NO") == null ? "" : rs.getString("IMEI_NO")));
					json.put("DUM_USER_NAME", (rs.getString("DUM_USER_NAME") == null ? "" : rs.getString("DUM_USER_NAME")));
					json.put("DUM_USER_ADDRESS", (rs.getString("DUM_USER_ADDRESS") == null ? "" : rs.getString("DUM_USER_ADDRESS")));
					json.put("DUM_USER_MOBILE", (rs.getString("DUM_USER_MOBILE") == null ? "" : rs.getString("DUM_USER_MOBILE")));
					json.put("DUM_USER_EMAIL_ID", (rs.getString("DUM_USER_EMAIL_ID") == null ? "" : rs.getString("DUM_USER_EMAIL_ID")));
					
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
	
	
	/**
	 * Convert the ResultSet to a List of Maps, where each Map represents a row with columnNames and columValues
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
	    ResultSetMetaData md = rs.getMetaData();
	    int columns = md.getColumnCount();
	    List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
	    System.out.println("rows:"+rows);
	    while (rs.next()){
	        Map<String, Object> row = new HashMap<String, Object>(columns);
	        System.out.println("row:"+row);
	        for(int i = 1; i <= columns; ++i){
	            row.put(md.getColumnName(i), rs.getObject(i));
	        }
	        rows.add(row);
	    }
	    return rows;
	}

	@Override
	public JSONObject verifyuserid(JSONObject object, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();

		try {
				String sql = " select count(*) cnt from device_user_master where dum_user_id = '"+(String)object.get("userid")+"' " ;

				ps = dbConn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					if(rs.getInt("cnt") == 0) {
						JSON_RESPONSE.put("status", "success");
						JSON_RESPONSE.put("message", "No User Exists. You may continue !!!");
						JSON_RESPONSE.put("exists", false);
					}else {
						JSON_RESPONSE.put("status", "fail");
						JSON_RESPONSE.put("message", "User Already Exists !!!");
						JSON_RESPONSE.put("exists", true);
					}
				}
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
	public JSONObject upsertuserdetails(JSONObject object, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		CallableStatement calstmt = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			
			 if(object.isEmpty()) {
				 JSON_RESPONSE.put("status", "error");
				 JSON_RESPONSE.put("message", "Invalid Object");
			 }else {
				 
				 String sql = "{ call UPSERT_USER_MASTER(?,"
						+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("imeino") + "'," + "'"
						+ (String) object.get("location_code") + "'," + "'" + (String) object.get("userid") + "'," + "'"
						+ ((String)object.get("password") == null ? "" : EncriptAndDecript.encrypt((String)object.get("password"))) + "'," + "'" + (String) object.get("role") + "'," + "'"
						+ (String) object.get("username") + "'," + "'" + (String) object.get("address") + "'," + "'"
						+ (String) object.get("mobileno") + "'," + "'" + (String) object.get("emailid") + "'," + "'"
						+ (String) object.get("enabled") + "'," + "'" + (String) object.get("effectivefrom") + "'," + "'"
						+ (String) object.get("effectiveto") + "'," + "'" + (String) object.get("createdby") + "')}";

				 System.out.println(sql);
			calstmt = dbConn.prepareCall(sql);
			calstmt.registerOutParameter(1,OracleTypes.CURSOR );
			/*calstmt.setString(2,(String)object.get("rowid") );
			calstmt.setString(3,(String)object.get("imeino") );
			calstmt.setString(4,(String)object.get("location_code") );
			calstmt.setString(5,(String)object.get("userid") );
			calstmt.setString(6,EncriptAndDecript.encrypt((String)object.get("password")));
			calstmt.setString(7,(String)object.get("role") );
			calstmt.setString(8,(String)object.get("username") );
			calstmt.setString(9,(String)object.get("address") );
			calstmt.setString(10,(String)object.get("mobileno") );
			calstmt.setString(11,(String)object.get("emailid") );
			calstmt.setString(12,(String)object.get("enabled") );
			calstmt.setString(13,(String)object.get("effectivefrom") );
			calstmt.setString(14,(String)object.get("effectiveto") );
			calstmt.setString(15,(String)object.get("createdby") );*/
			
			calstmt.executeUpdate();
			
			rs = (ResultSet) calstmt.getObject(1);
			String result_proc = "";
			if(rs.next()) {
				result_proc = rs.getString("RESP");
			}
			
			if(result_proc.equals("success")) {
				JSON_RESPONSE.put("status", "success");
				JSON_RESPONSE.put("message", "User created/updated sucessfully .");
			}else {
				JSON_RESPONSE.put("status", "fail");
				JSON_RESPONSE.put("message", "Insert/Update Failed !!!");
			}

			 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JSON_RESPONSE.put("status", "fail");
		JSON_RESPONSE.put("message", "Insert/Update Failed");
	}finally {
		DBManagerResourceRelease.close(ps);
		DBManagerResourceRelease.close(calstmt);
	}
	return JSON_RESPONSE;
	}

	@Override
	public JSONObject chnagepassword(JSONObject object, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		CallableStatement calstmt = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		
		try {
			
			if (object.isEmpty()) {
				JSON_RESPONSE.put("status", "error");
				JSON_RESPONSE.put("message", "Invalid Object");
			} else {

				String sql = " UPDATE DEVICE_USER_MASTER SET DUM_PASSWORD='"+ EncriptAndDecript.encrypt((String) object.get("pass"))+"' WHERE DUM_USER_ID = '"+ (String) object.get("userid")+"' ";

				ps = dbConn.prepareStatement(sql);

				System.out.println(sql);

				int result_proc = ps.executeUpdate();
				if (result_proc > 0) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Password Changed Succesfully !!! .");
				} else {
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Password Not Changed !!! .");
				}

			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JSON_RESPONSE.put("status", "fail");
		JSON_RESPONSE.put("message", "Error Occured . Password Not Changed !!! .");
	}finally {
		DBManagerResourceRelease.close(ps);
		DBManagerResourceRelease.close(calstmt);
	}
	return JSON_RESPONSE;
	}
}
