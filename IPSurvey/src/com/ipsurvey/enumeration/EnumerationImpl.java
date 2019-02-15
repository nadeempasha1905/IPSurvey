/**
 * 
 */
package com.ipsurvey.enumeration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.codec.binary.Base64;

import com.ipsurvey.database.DBManagerIMPL;
import com.ipsurvey.database.DBManagerResourceRelease;
import com.ipsurvey.util.EncriptAndDecript;
import com.sun.jersey.core.header.FormDataContentDisposition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

/**
 * @author Nadeem
 *
 */
 


public class EnumerationImpl implements IEnumeration{
	
	/** The path to the folder where we want to store the uploaded files */
	//private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
	
	ResourceBundle propsBundle=ResourceBundle.getBundle("ipsurveydb");
	String IMAGE_PATH = propsBundle.getString("PATH");

//database connection init
		static DBManagerIMPL dbObject = new DBManagerIMPL();
		static Connection dbConn;
	
	public EnumerationImpl() {
		// TODO Auto-generated constructor stub
		try {
			dbConn = dbObject.getDatabaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getvillageenumerationdetails(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			//dbConn = dbObject.getDatabaseConnection();

				String sql = " SELECT ROWIDTOCHAR (ROWID) row_id,VE_LOCATION_CODE, "
						   + " (SELECT location_name FROM LOCATION WHERE LOCATION_CODE = VE_LOCATION_CODE) VE_LOCATION_name , VE_VILLAGE_NAME, "
						   + " VE_REMARKS, VE_STATUS, VE_LONGITUDE, VE_LATTITUDE, VE_ALTITUDE,"
						   + " VE_CREATED_BY, VE_CREATED_ON, VE_UPDATED_BY, VE_UPDATED_ON, VE_SURVEYOR_NAME, VE_SYNC_FLAG, VE_UPLOADED_ON ,VE_IMAGE_PATH "
						   + " FROM VILLAGE_ENUMERATION "
						   + " WHERE VE_LOCATION_CODE = '"+(String)object.get("location_code")+"' "
						   + " order by VE_LOCATION_CODE, VE_VILLAGE_NAME "  ;
				
				System.out.println(sql);

				ps = dbConn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					
					json = new JSONObject();
					
					json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
					json.put("VE_LOCATION_name", (rs.getString("VE_LOCATION_name") == null ? "" : rs.getString("VE_LOCATION_name")));
					json.put("VE_LOCATION_CODE", (rs.getString("VE_LOCATION_CODE") == null ? "" : rs.getString("VE_LOCATION_CODE")));
					json.put("VE_VILLAGE_NAME", (rs.getString("VE_VILLAGE_NAME") == null ? "" : rs.getString("VE_VILLAGE_NAME")));
					json.put("VE_REMARKS", (rs.getString("VE_REMARKS") == null ? "" : rs.getString("VE_REMARKS")));
					json.put("VE_STATUS", (rs.getString("VE_STATUS") == null ? "" : rs.getString("VE_STATUS")));
					json.put("VE_LONGITUDE", (rs.getString("VE_LONGITUDE") == null ? "" : rs.getString("VE_LONGITUDE")));
					json.put("VE_LATTITUDE", (rs.getString("VE_LATTITUDE") == null ? "" : rs.getString("VE_LATTITUDE")));
					json.put("VE_ALTITUDE", (rs.getString("VE_ALTITUDE") == null ? "" : rs.getString("VE_ALTITUDE")));
					json.put("VE_CREATED_BY", (rs.getString("VE_CREATED_BY") == null ? "" : rs.getString("VE_CREATED_BY")));
					json.put("VE_CREATED_ON", (rs.getString("VE_CREATED_ON") == null ? "" : rs.getString("VE_CREATED_ON")));
					json.put("VE_UPDATED_BY", (rs.getString("VE_UPDATED_BY") == null ? "" : rs.getString("VE_UPDATED_BY")));
					json.put("VE_UPDATED_ON", (rs.getString("VE_UPDATED_ON") == null ? "" : rs.getString("VE_UPDATED_ON")));
					json.put("VE_SURVEYOR_NAME", (rs.getString("VE_SURVEYOR_NAME") == null ? "" : rs.getString("VE_SURVEYOR_NAME")));
					json.put("VE_SYNC_FLAG", (rs.getString("VE_SYNC_FLAG") == null ? "" : rs.getString("VE_SYNC_FLAG")));
					json.put("VE_UPLOADED_ON", (rs.getString("VE_UPLOADED_ON") == null ? "" : rs.getString("VE_UPLOADED_ON")));
					json.put("VE_IMAGE_PATH", (rs.getString("VE_IMAGE_PATH") == null ? "" : rs.getString("VE_IMAGE_PATH")));
					
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
	public JSONObject verifyvillageexists(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject JSON_RESPONSE = new JSONObject();

		try {
				String sql = " SELECT COUNT(*) RECORD_EXISTS FROM VILLAGE_ENUMERATION "
						+ " WHERE VE_LOCATION_CODE='"+(String)object.get("location_code")+"' AND "
								+ " VE_VILLAGE_NAME ='"+(String)object.get("village_name")+"' " ;

				ps = dbConn.prepareStatement(sql);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					if(rs.getInt("RECORD_EXISTS") == 0) {
						JSON_RESPONSE.put("status", "success");
						JSON_RESPONSE.put("message", "No Village Enumerated. You may continue !!!");
						JSON_RESPONSE.put("exists", false);
					}else {
						JSON_RESPONSE.put("status", "fail");
						JSON_RESPONSE.put("message", "Village Is Already Enumerated !!!");
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
	public Response saveimage(InputStream uploadedInputStream, FormDataContentDisposition fileDetail,String filename) {
		// TODO Auto-generated method stub
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(400).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists(IMAGE_PATH);
		} catch (SecurityException se) {
			return Response.status(500)
					.entity("Can not create destination folder on server")
					.build();
		}
		//String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
		String uploadedFileLocation = IMAGE_PATH + filename;
		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(500).entity("Can not save file").build();
		}
		
		JSONObject object = new JSONObject();
		object.put("status", "success");
		object.put("message", "File Saved Successfully to " + uploadedFileLocation);
		/*return Response.status(200)
				.entity("File saved to " + uploadedFileLocation).build();*/
		return Response.status(200)
				.entity(object).build();
	}
	
		/**
		 * Utility method to save InputStream data to target location/file
		 * 
		 * @param inStream
		 *            - InputStream to be saved
		 * @param target
		 *            - full path to destination file
		 */
		private void saveToFile(InputStream inStream, String target)
				throws IOException {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(target));
			while ((read = inStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		}
		/**
		 * Creates a folder to desired location if it not already exists
		 * 
		 * @param dirName
		 *            - full path to the folder
		 * @throws SecurityException
		 *             - in case you don't have permission to create the folder
		 */
		private void createFolderIfNotExists(String dirName)
				throws SecurityException {
			File theDir = new File(dirName);
			if (!theDir.exists()) {
				theDir.mkdir();
			}
		}

		@Override
		public JSONObject upsertvillageenumeration(JSONObject object, String ipAdress) {
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
					
					/* PROCEDURE UPSERT_VILLAGE_ENUMERATION(CUR OUT SYS_REFCURSOR, P_ROW_ID IN VARCHAR2, P_LOCATION_CODE IN VARCHAR2, P_VILLAGE_NAME IN VARCHAR2, P_REMARKS IN VARCHAR2, P_STATUS IN VARCHAR2 DEFAULT 'Y', 
                             P_LONGITUDE IN VARCHAR2, P_LATTITUDE IN VARCHAR2, P_ALTITUDE IN VARCHAR2, P_CREATED_UPDATED_BY IN VARCHAR2, P_IMAGE_PATH IN VARCHAR2)
					*/		
					 String sql = "{ call UPSERT_VILLAGE_ENUMERATION(?,"
							+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("location_code") + "'," + "'"
							+ (String) object.get("village_name") + "'," + "'" + (String) object.get("remarks") + "'," 
							+ "'Y'," + "'" 
							+ (String) object.get("longitude") + "'," + "'"
							+ (String) object.get("latitude") + "'," + "'" + (String) object.get("altitude") + "'," + "'"
							+ (String) object.get("userid") + "'," + "'" + (String) object.get("imagepath") + "')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeUpdate();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				if(rs.next()) {
					result_proc = rs.getString("RESP");
				}
				
				if(result_proc.equals("success")) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Village Enumeration Inserted/Updated sucessfully .");
				}else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Village Enumeration Inserted/Updated Failed !!!");
				}

				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Village Enumeration Inserted/Updated Failed");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
	}

		@Override
		public JSONObject getimagedata(JSONObject object, HttpServletRequest request) {
			// TODO Auto-generated method stub
			
			JSONObject JSON_RESPONSE =  new JSONObject();
			
			File originalFile = new File(IMAGE_PATH+(String)object.get("filename"));
	        String encodedBase64 = null;
	        
	        if(originalFile.isFile()) {
		        try {
		            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
		            byte[] bytes = new byte[(int)originalFile.length()];
		            fileInputStreamReader.read(bytes);
		            encodedBase64 = new String(Base64.encodeBase64(bytes));
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	        }
			
			JSON_RESPONSE.put("status", "success");
			JSON_RESPONSE.put("encodedBase64", encodedBase64);
			
			return JSON_RESPONSE;
		}

		@Override
		public JSONObject gettransformerenumerationdetails(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			try {
				//dbConn = dbObject.getDatabaseConnection();

					/*String sql = " SELECT ROWIDTOCHAR (ROWID) row_id,TE_CIRCLE_CODE, TE_DIVISION_CODE, "
							+ " (SELECT location_name FROM LOCATION WHERE location_code=TE_SUBDIVISION_CODE) SUBDIVISION_NAME,TE_SUBDIVISION_CODE,"
							+ " (SELECT location_name FROM LOCATION WHERE location_code= TE_OM_CODE) OMSECTION_NAME,TE_SUBDIVISION_CODE, TE_OM_CODE, "
							//+ "	 (select SM_STATION_NAME from STATION_MASTER where SM_STATION_CODE = TE_STATION_CODE ) station_name ,"
							+ "  TE_STATION_CODE, "
							//+ "  (select FM_FEEDER_NAME from FEEDER_MASTER where FM_STATION_CODE = TE_STATION_CODE AND FM_FEEDER_CODE = TE_FEEDER_CODE ) feeder_name ,"
							+ "  TE_FEEDER_CODE,  "
							+ "  TE_TRANSFORMER_CODE, TE_TRANSFORMER_NAME, TE_TIMS_CODE, TE_CAPACITY_KVA,"
							+ "  TE_LONGITUDE, TE_LATTITUDE, TE_ALTITUDE, TE_REMARKS, TE_CREATED_BY, TE_CREATED_ON, TE_UPDATED_BY, TE_UPDATED_ON, "
							+ "  TE_VILLAGE, TE_IMAGE_PATH "
							+ "  FROM TRANSFORMER_ENUMERATION  "
							+ "  WHERE 1 = 1 " ;*/
				
				String sql =  " SELECT ROWIDTOCHAR (T.ROWID) ROW_ID,TE_CIRCLE_CODE, TE_DIVISION_CODE,"
							+ " TE_SUBDIVISION_CODE, L1.LOCATION_NAME  SUBDIVISION_NAME,"
							+ " TE_OM_CODE, L2.LOCATION_NAME  OM_NAME,"
							+ " TE_STATION_CODE, SM.SM_STATION_NAME   STATION_NAME,"
							+ " TE_FEEDER_CODE, FM.FM_FEEDER_NAME FEEDER_NAME,"
							+ " TE_TRANSFORMER_CODE, TE_TRANSFORMER_NAME, "
							+ " TE_TIMS_CODE, TE_CAPACITY_KVA, TE_VILLAGE,  "
							+ "	TE_LATTITUDE, TE_LONGITUDE, TE_ALTITUDE, TE_REMARKS, TE_IMAGE_PATH, "
							/*+ " TE_CREATED_BY, TO_CHAR(TE_CREATED_ON,'DD/MM/YYYY HH:MI:SS AM') TE_CREATED_ON, "*/
							+ " nvl(TE_CREATED_BY,TE_UPDATED_BY) TE_UPDATED_BY, TO_CHAR(nvl(TE_UPDATED_ON,TE_CREATED_ON),'DD/MM/YYYY HH:MI:SS AM') TE_UPDATED_ON "
							+ " FROM TRANSFORMER_ENUMERATION T "
							+ " LEFT OUTER JOIN LOCATION L1 ON L1.LOCATION_CODE  = T.TE_SUBDIVISION_CODE "
							+ " LEFT OUTER JOIN LOCATION L2 ON L2.LOCATION_CODE  = T.TE_OM_CODE "
							+ "	LEFT OUTER JOIN STATION_MASTER SM ON SM.SM_LOCATION_CODE = T.TE_SUBDIVISION_CODE AND SM.SM_STATION_CODE = T.TE_STATION_CODE "
							+ "	LEFT OUTER JOIN FEEDER_MASTER FM ON FM.FM_LOCATION_CODE = T.TE_SUBDIVISION_CODE AND FM.FM_STATION_CODE = T.TE_STATION_CODE AND FM.FM_FEEDER_CODE = T.TE_FEEDER_CODE "
							+ " WHERE TE_OM_CODE = '"+(String)object.get("location_code")+"' ";
						
					if(((String)object.get("station_code")).length() > 0) {
						sql = sql + " AND TE_STATION_CODE = '"+(String)object.get("station_code")+"' " ;
					}
					if(((String)object.get("feeder_code")).length() > 0) {
						sql = sql + " AND TE_FEEDER_CODE = '"+(String)object.get("feeder_code")+"' " ;
					}

					/*sql = sql + " ORDER BY TE_OM_CODE, TE_STATION_CODE,TE_FEEDER_CODE,TE_VILLAGE,TE_TRANSFORMER_CODE " ;*/
					
					sql = sql + " ORDER BY nvl(TE_UPDATED_ON,TE_CREATED_ON) " ;
					
					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
						json.put("TE_CIRCLE_CODE", (rs.getString("TE_CIRCLE_CODE") == null ? "" : rs.getString("TE_CIRCLE_CODE")));
						json.put("TE_DIVISION_CODE", (rs.getString("TE_DIVISION_CODE") == null ? "" : rs.getString("TE_DIVISION_CODE")));
						json.put("TE_SUBDIVISION_CODE", (rs.getString("TE_SUBDIVISION_CODE") == null ? "" : rs.getString("TE_SUBDIVISION_CODE")));
						json.put("SUBDIVISION_NAME", (rs.getString("SUBDIVISION_NAME") == null ? "" : rs.getString("SUBDIVISION_NAME")));
						json.put("TE_OM_CODE", (rs.getString("TE_OM_CODE") == null ? "" : rs.getString("TE_OM_CODE")));
						json.put("OM_NAME", (rs.getString("OM_NAME") == null ? "" : rs.getString("OM_NAME")));
						json.put("TE_STATION_CODE", (rs.getString("TE_STATION_CODE") == null ? "" : rs.getString("TE_STATION_CODE")));
						json.put("STATION_NAME", (rs.getString("STATION_NAME") == null ? "" : rs.getString("STATION_NAME")));
						json.put("TE_FEEDER_CODE", (rs.getString("TE_FEEDER_CODE") == null ? "" : rs.getString("TE_FEEDER_CODE")));
						json.put("FEEDER_NAME", (rs.getString("FEEDER_NAME") == null ? "" : rs.getString("FEEDER_NAME")));
						json.put("TE_TRANSFORMER_CODE", (rs.getString("TE_TRANSFORMER_CODE") == null ? "" : rs.getString("TE_TRANSFORMER_CODE")));
						json.put("TE_TRANSFORMER_NAME", (rs.getString("TE_TRANSFORMER_NAME") == null ? "" : rs.getString("TE_TRANSFORMER_NAME")));
						json.put("TE_CAPACITY_KVA", (rs.getString("TE_CAPACITY_KVA") == null ? "" : rs.getString("TE_CAPACITY_KVA")));
						json.put("TE_TIMS_CODE", (rs.getString("TE_TIMS_CODE") == null ? "" : rs.getString("TE_TIMS_CODE")));
						json.put("TE_VILLAGE", (rs.getString("TE_VILLAGE") == null ? "" : rs.getString("TE_VILLAGE")));
						json.put("TE_LATTITUDE", (rs.getString("TE_LATTITUDE") == null ? "" : rs.getString("TE_LATTITUDE")));
						json.put("TE_LONGITUDE", (rs.getString("TE_LONGITUDE") == null ? "" : rs.getString("TE_LONGITUDE")));
						json.put("TE_ALTITUDE", (rs.getString("TE_ALTITUDE") == null ? "" : rs.getString("TE_ALTITUDE")));
						json.put("TE_REMARKS", (rs.getString("TE_REMARKS") == null ? "" : rs.getString("TE_REMARKS")));
						json.put("TE_IMAGE_PATH", (rs.getString("TE_IMAGE_PATH") == null ? "" : rs.getString("TE_IMAGE_PATH")));
						json.put("TE_UPDATED_BY", (rs.getString("TE_UPDATED_BY") == null ? "" : rs.getString("TE_UPDATED_BY")));
						json.put("TE_UPDATED_ON", (rs.getString("TE_UPDATED_ON") == null ? "" : rs.getString("TE_UPDATED_ON")));
						
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
		public JSONObject getipcodenumber(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			
			try {
				String get_permission_query = " SELECT '"+(String)object.get("om_code")+"'||'"+(String)object.get("transformer_code")+"'||'00'||LPAD(NVL(MAX(TO_NUMBER(SUBSTR(IE_CODE_NUMBER,16))),0)+1,2,'0') CODE_NUMBER"
											+ " FROM IP_ENUMERATION"
											+ " WHERE IE_OM_CODE='"+(String)object.get("om_code")+"' AND IE_TRANSFORMER_CODE='"+(String)object.get("transformer_code")+"' AND IE_POLE_CODE='00'";
						
				ps = dbConn.prepareStatement(get_permission_query);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("code_number", rs.getString("CODE_NUMBER"));
				}else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Error occured in generating code number !!!");
				}
				
			} catch (Exception e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSON_RESPONSE.put("status", "fail");
				JSON_RESPONSE.put("message", "Error occured in generating code number !!!");
			}finally {
				DBManagerResourceRelease.close(rs);
				DBManagerResourceRelease.close(ps);
			}
			
			return JSON_RESPONSE;
		}

		@Override
		public JSONObject validatetransformerdetails(JSONObject object, String ipAdress) {
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
					
					 String sql = "{ call GET_TC_MASTER_DETAILS(?,'"+(String)object.get("location_code")+"',"
					 		+ "'"+((String)object.get("tc_code") == null || (String)object.get("tc_code") == "" ? "" : (String)object.get("tc_code") )+"',"
					 				+ "'"+((String)object.get("tc_name") == null || (String)object.get("tc_name") == "" ? "" : (String)object.get("tc_name"))+"')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeQuery();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				
				if(rs.next()) {
					result_proc = rs.getString("RESP");
					
					if(result_proc.equals("success")) {
						
						JSON_RESPONSE.put("status", "success");
						JSON_RESPONSE.put("action", "success");
						JSON_RESPONSE.put("message", "Transformer Not Enumerated . You may continue !!!");
						JSON_RESPONSE.put("TM_OM_CODE", rs.getString("TM_OM_CODE"));
						JSON_RESPONSE.put("TM_STATION_CODE", rs.getString("TM_STATION_CODE"));
						JSON_RESPONSE.put("TM_FEEDER_CODE", rs.getString("TM_FEEDER_CODE"));
						JSON_RESPONSE.put("TM_TRANSFORMER_CODE", rs.getString("TM_TRANSFORMER_CODE"));
						JSON_RESPONSE.put("TM_TRANSFORMER_NAME", rs.getString("TM_TRANSFORMER_NAME"));
						JSON_RESPONSE.put("TM_CAPACITY_KVA", rs.getString("TM_CAPACITY_KVA"));
						
					}
					
					if(result_proc.equals("surveyed")) {
						
						JSON_RESPONSE.put("status", "error");
						JSON_RESPONSE.put("message", "Transformer Already Surveyed !!!");
						
					}
					
					if(result_proc.equals("dup")) {
						
						JSON_RESPONSE.put("status", "error");
						JSON_RESPONSE.put("message", "Transformer Name Already Exists . Cannot Duplicate !!!");
						
					}
					
					if(result_proc.equals("new")) {
						
						JSON_RESPONSE.put("status", "success");
						JSON_RESPONSE.put("action", "new");
						JSON_RESPONSE.put("message", "Adding New Transformer !!!");
						JSON_RESPONSE.put("TIMS_CODE", rs.getString("TIMS_CODE"));
						JSON_RESPONSE.put("NEW_TC_CODE", rs.getString("NEW_TC_CODE"));
						
					}
				}
				
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

		@Override
		public JSONObject upserttransformerenumeration(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub

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
					
					/*PROCEDURE UPSERT_TRANSFORMER_ENUMERATION(CUR OUT SYS_REFCURSOR, P_ROW_ID IN VARCHAR2, P_LOCATION_CODE IN VARCHAR2, P_STATION_CODE IN VARCHAR2, P_FEEDER_CODE IN VARCHAR2, 
                                         P_TRANSFORMER_CODE IN VARCHAR2, P_TRANSFORMER_NAME IN VARCHAR2, P_TIMS_CODE IN VARCHAR2, P_CAPACITY_KVA IN VARCHAR2, P_CONN_LD_KVA IN VARCHAR2, 
                                         P_LONGITUDE IN VARCHAR2, P_LATTITUDE IN VARCHAR2, P_ALTITUDE IN VARCHAR2, P_REMARKS IN VARCHAR2, 
                                         P_CREATED_UPDATED_BY IN VARCHAR2, P_VILLAGE IN VARCHAR2, P_IMAGE_PATH IN VARCHAR2) IS */		
					 
					 String sql = "{ call UPSERT_TRANSFORMER_ENUMERATION(?,"
							+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("location_code") + "'," + "'"
							+ (String) object.get("stationcode") + "'," + "'" + (String) object.get("feedercode") + "','" 
							+ (String) object.get("transformercode") + "'," + "'" + (String) object.get("transformername") + "','" 
							+ (String) object.get("timscode") + "'," + "'" + (String) object.get("capacitykva") + "','" 
							+ (String) object.get("connectedloadkva") + "'," + "'" + (String) object.get("longitude") + "','" 
							+ (String) object.get("latitude") + "'," + "'" + (String) object.get("altitude") + "'," + "'"
							+ (String) object.get("remarks") + "'," + "'" + (String) object.get("userid") + "'," + "'"
							+ (String) object.get("village_name") + "'," + "'" + (String) object.get("imagepath") + "')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeUpdate();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				if(rs.next()) {
					result_proc = rs.getString("RESP");
				}
				
				if(result_proc.equals("success")) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Transformer Enumeration Inserted/Updated sucessfully .");
				}else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Transformer Enumeration Inserted/Updated Failed !!!");
				}

				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Transformer Enumeration Inserted/Updated Failed");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
	
		}

		@Override
		public JSONObject getconsumerdetailsbyrrno(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			PreparedStatement ps1 = null;
			ResultSet rs1= null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			try {
				//dbConn = dbObject.getDatabaseConnection();

					String sql2 = "  SELECT NVL(IM_CUSTOMER_NAME,'~') IM_CUSTOMER_NAME, NVL(IM_ADDRESS1,'~') IM_ADDRESS1,"
							+ "  NVL(IM_ADDRESS2,'~') IM_ADDRESS2, NVL(IM_VILLAGE,'~')  IM_VILLAGE  "
							+ "  FROM IP_MASTER  "
							+ "  WHERE "
							+ "  IM_LOCATION_CODE = '"+(String)object.get("location_code")+"' AND   "
							+ "  IM_RR_NO = '"+(String)object.get("rr_number")+"' ";
					
					String sql1 = "  SELECT count(*) cnt  "
							+ "  FROM IP_ENUMERATION  "
							+ "  WHERE "
							+ "  IE_SUBDIVISION_CODE = '"+(String)object.get("location_code")+"' AND   "
							+ "  IE_RR_NO = '"+(String)object.get("rr_number")+"' ";
					
					System.out.println(sql1);

					ps = dbConn.prepareStatement(sql1);
					rs = ps.executeQuery();
					
					if(rs.next()){
						if(rs.getInt("cnt") > 0) {
							JSON_RESPONSE.put("status", "error");
							JSON_RESPONSE.put("message", "Ipset already enumerated !!!");
						}else {
							ps1 = dbConn.prepareStatement(sql2);
							rs1 = ps.executeQuery();
							if(rs.next()){
								JSON_RESPONSE.put("status", "success");
								JSON_RESPONSE.put("status1", "found");
								JSON_RESPONSE.put("customer_name", rs.getString("IM_CUSTOMER_NAME"));
								JSON_RESPONSE.put("address1", rs.getString("IM_ADDRESS1"));
								JSON_RESPONSE.put("address2", rs.getString("IM_ADDRESS2"));
								JSON_RESPONSE.put("village", rs.getString("IM_VILLAGE"));
							}else {
								JSON_RESPONSE.put("status", "success");
								JSON_RESPONSE.put("status1", "notfound");
								JSON_RESPONSE.put("message", "IPset not enumerated. You may continue !!!");
							}
							
						}
					}else{
						
						JSON_RESPONSE.put("status", "fail");
						JSON_RESPONSE.put("message", "No Details Found  !!!");
					}

					//ps.close();
					//rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSON_RESPONSE.put("status", "error");
				JSON_RESPONSE.put("message", "Error Occured !!! ");
			} finally {
				//DBManagerResourceRelease.close(rs, ps, dbConn);
				DBManagerResourceRelease.close(rs);
				DBManagerResourceRelease.close(ps);
				DBManagerResourceRelease.close(rs1);
				DBManagerResourceRelease.close(ps1);
			}
			
			return JSON_RESPONSE;
		}

		@Override
		public JSONObject upsertipsetenumeration(JSONObject object, String ipAdress) {
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
					
					/*PROCEDURE UPSERT_IP_ENUMERATION(CUR OUT SYS_REFCURSOR, P_ROW_ID IN VARCHAR2, P_LOCATION_CODE IN VARCHAR2, P_STATION_CODE IN VARCHAR2, P_FEEDER_CODE IN VARCHAR2,
					 *  P_TRANSFORMER_CODE IN VARCHAR2, 
                                P_POLE_CODE IN VARCHAR2, P_RR_NO IN VARCHAR2, P_CUSTOMER_NAME IN VARCHAR2, P_ADDRESS1 IN VARCHAR2, P_ADDRESS2 IN VARCHAR2, P_VILLIAGE IN VARCHAR2, 
                                P_LOAD_KW IN VARCHAR2, P_LOAD_HP IN VARCHAR2, P_SCHEME IN VARCHAR2, P_CONNECTION_TYPE IN VARCHAR2, P_CUSTOMER_STATUS IN VARCHAR2, 
                                P_WATER_SOURCE IN VARCHAR2, P_SERVICE_DATE IN VARCHAR2, P_METER_FLAG IN VARCHAR2, P_CROP IN VARCHAR2, 
                                P_VOLTAGE_RY IN VARCHAR2, P_VOLTAGE_RB IN VARCHAR2, P_VOLTAGE_BR IN VARCHAR2, P_CURRENT_R IN VARCHAR2, P_CURRENT_Y IN VARCHAR2, P_CURRENT_B IN VARCHAR2, 
                                P_LONGITUDE IN VARCHAR2, P_LATTITUDE IN VARCHAR2, P_ALTITUDE IN VARCHAR2, P_REMARKS IN VARCHAR2, P_CREATED_UPDATED_BY IN VARCHAR2, 
                                P_MTR_SLNO IN VARCHAR2, P_MTR_MAKE IN VARCHAR2, P_MTR_TYPE IN VARCHAR2, P_FINAL_READING IN VARCHAR2, P_CODE_NUMBER VARCHAR2, 
                                P_PHONE_NUMBER IN VARCHAR2, P_PHASE IN VARCHAR2, P_IMAGE_PATH IN VARCHAR2) IS
*/		
					 String sql = "{ call UPSERT_IP_ENUMERATION(?,"
							+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("location_code") + "'," + "'"
							+ (String) object.get("stationcode") + "'," + "'" + (String) object.get("feedercode") + "','" 
							+ (String) object.get("transformercode") + "'," + "'" + (String) object.get("polecode") + "','" 
							+ (String) object.get("rrnumber") + "'," + "'" + (String) object.get("consumername") + "','" 
							+ (String) object.get("address1") + "'," + "'" + (String) object.get("address2") + "','" 
							+ (String) object.get("village") + "'," + "'" + (String) object.get("loadkw") + "'," + "'"
							+ (String) object.get("loadhp") + "'," + "'" + (String) object.get("connectionscheme") + "'," + "'"
							+ (String) object.get("connectiontypes") + "'," + "'" + (String) object.get("connectionstatus") + "'," + "'"
							+ (String) object.get("watersource") + "'," + "'" + (String) object.get("servicedate") + "'," + "'"
							+ (String) object.get("meterstatus") + "'," + "''," + "'"  //p_crop
							+ (String) object.get("voltage1") + "'," + "'" + (String) object.get("voltage2") + "'," + "'"
							+ (String) object.get("voltage3") + "'," + "'" + (String) object.get("current1") + "'," + "'"
							+ (String) object.get("current2") + "'," + "'" + (String) object.get("current3") + "'," + "'"
							+ (String) object.get("longitude") + "'," + "'" + (String) object.get("latitude") + "'," + "'"
							+ (String) object.get("altitude") + "'," + "'" + (String) object.get("remarks") + "'," + "'"
							+ (String) object.get("userid") + "'," + "'" + (String) object.get("meterslno") + "'," + "'"
							+ (String) object.get("metermake") + "'," + "'" + (String) object.get("metertype") + "'," + "'"
							+ (String) object.get("finalreading") + "'," + "'" + (String) object.get("codenumber") + "'," + "'"
							+ (String) object.get("phone") + "'," + "'3'," + "'" + (String) object.get("imagepath") + "')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeUpdate();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				if(rs.next()) {
					result_proc = rs.getString("RESP");
				}
				
				if(result_proc.equals("success")) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Ipset Enumeration Inserted/Updated sucessfully .");
				}else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Ipset Enumeration Inserted/Updated Failed !!!");
				}

				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Ipset Enumeration Inserted/Updated Failed");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
		}

		@Override
		public JSONObject getipsetenumerationdetails(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			

			try {
				String sql =  " SELECT ROWIDTOCHAR (T.ROWID) ROW_ID,IE_SUBDIVISION_CODE , L1.LOCATION_NAME  SUBDIVISION_NAME, IE_OM_CODE, L2.LOCATION_NAME  OM_NAME ,"
						    + "  IE_STATION_CODE, SM.SM_STATION_NAME   STATION_NAME, IE_FEEDER_CODE, FM.FM_FEEDER_NAME FEEDER_NAME, IE_TRANSFORMER_CODE,"
							+ " IE_POLE_CODE, IE_RR_NO, IE_CUSTOMER_NAME, IE_ADDRESS1, IE_ADDRESS2, IE_VILLIAGE, IE_LOAD_KW, IE_LOAD_HP,"
							+ " FN_GET_CODE_NAME(IE_SCHEME,'SCHEME') IE_SCHEME_DESCR,IE_SCHEME,"
							+ " FN_GET_CODE_NAME(IE_CONNECTION_TYPE,'SER_STS') IE_CONNECTION_TYPE_DESCR, IE_CONNECTION_TYPE,"
							+ " FN_GET_CODE_NAME(IE_CUSTOMER_STATUS,'INSTL_STS') IE_CUSTOMER_STATUS_DESCR, IE_CUSTOMER_STATUS,"
							+ " FN_GET_CODE_NAME(IE_WATER_SOURCE,'WTR_SOURCE') IE_WATER_SOURCE_DESCR, IE_WATER_SOURCE, "
							+ " to_char(IE_SERVICE_DATE,'DD/MM/YYYY') IE_SERVICE_DATE,"
							+ " to_char(IE_INSPECTION_DATE,'DD/MM/YYYY') IE_INSPECTION_DATE, "
							+ " IE_METER_FLAG, IE_CROP, IE_VOLTAGE_RY, IE_VOLTAGE_RB, IE_VOLTAGE_BR, "
							+ " IE_CURRENT_R, IE_CURRENT_Y, IE_CURRENT_B, IE_LONGITUDE, IE_LATTITUDE, IE_ALTITUDE, IE_SURVEYOR_NAME, IE_MTR_SLNO,"
							+ " FN_GET_CODE_NAME(IE_MTR_MAKE,'MTR_MAKE') IE_MTR_MAKE_DESCR, IE_MTR_MAKE,"
							+ " FN_GET_CODE_NAME(IE_MTR_TYPE,'MTR_TYP') IE_MTR_TYPE_DESCR,  IE_MTR_TYPE, "
							+ " IE_FINAL_READING, IE_CODE_NUMBER, IE_PHONE_NUMBER, IE_PHASE, IE_IMAGE_PATH , "
							+ " nvl(IE_CREATED_BY, IE_UPDATED_BY) IE_UPDATED_BY, TO_CHAR(nvl(t.IE_UPDATED_ON,t.IE_CREATED_ON),'DD/MM/YYYY HH:MI:SS AM') IE_UPDATED_ON "
							+ " FROM IP_ENUMERATION T "
							+ " LEFT OUTER JOIN LOCATION L1 ON L1.LOCATION_CODE  = T.IE_SUBDIVISION_CODE "
							+ " LEFT OUTER JOIN LOCATION L2 ON L2.LOCATION_CODE  = T.IE_OM_CODE "
							+ "	LEFT OUTER JOIN STATION_MASTER SM ON SM.SM_LOCATION_CODE = T.IE_SUBDIVISION_CODE AND SM.SM_STATION_CODE = T.IE_STATION_CODE "
							+ "	LEFT OUTER JOIN FEEDER_MASTER FM ON FM.FM_LOCATION_CODE = T.IE_SUBDIVISION_CODE AND FM.FM_STATION_CODE = T.IE_STATION_CODE AND FM.FM_FEEDER_CODE = T.IE_FEEDER_CODE  "
							+ " WHERE IE_OM_CODE = '"+(String)object.get("location_code")+"' ";
						
					if(((String)object.get("station_code")).length() > 0) {
						sql = sql + " AND IE_STATION_CODE = '"+(String)object.get("station_code")+"' " ;
					}
					if(((String)object.get("feeder_code")).length() > 0) {
						sql = sql + " AND IE_FEEDER_CODE = '"+(String)object.get("feeder_code")+"' " ;
					}
					if(((String)object.get("transformer_code")).length() > 0) {
						sql = sql + " AND IE_TRANSFORMER_CODE = '"+(String)object.get("transformer_code")+"' " ;
					}
					if(((String)object.get("village")).length() > 0) {
						sql = sql + " AND IE_VILLIAGE = '"+(String)object.get("village")+"' " ;
					}
					if(((String)object.get("rr_no")).length() > 0) {
						sql = sql + " AND IE_RR_NO like '%"+(String)object.get("rr_no")+"%' " ;
					}
					
					sql = sql + " ORDER BY nvl(t.IE_UPDATED_ON,t.IE_CREATED_ON) desc " ;
					
					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
						json.put("IE_SUBDIVISION_CODE", (rs.getString("IE_SUBDIVISION_CODE") == null ? "" : rs.getString("IE_SUBDIVISION_CODE")));
						json.put("SUBDIVISION_NAME", (rs.getString("SUBDIVISION_NAME") == null ? "" : rs.getString("SUBDIVISION_NAME")));
						json.put("IE_OM_CODE", (rs.getString("IE_OM_CODE") == null ? "" : rs.getString("IE_OM_CODE")));
						json.put("OM_NAME", (rs.getString("OM_NAME") == null ? "" : rs.getString("OM_NAME")));
						json.put("IE_STATION_CODE", (rs.getString("IE_STATION_CODE") == null ? "" : rs.getString("IE_STATION_CODE")));
						json.put("STATION_NAME", (rs.getString("STATION_NAME") == null ? "" : rs.getString("STATION_NAME")));
						json.put("IE_FEEDER_CODE", (rs.getString("IE_FEEDER_CODE") == null ? "" : rs.getString("IE_FEEDER_CODE")));
						json.put("FEEDER_NAME", (rs.getString("FEEDER_NAME") == null ? "" : rs.getString("FEEDER_NAME")));
						json.put("IE_TRANSFORMER_CODE", (rs.getString("IE_TRANSFORMER_CODE") == null ? "" : rs.getString("IE_TRANSFORMER_CODE")));
						json.put("IE_POLE_CODE", (rs.getString("IE_POLE_CODE") == null ? "" : rs.getString("IE_POLE_CODE")));
						json.put("IE_RR_NO", (rs.getString("IE_RR_NO") == null ? "" : rs.getString("IE_RR_NO")));
						json.put("IE_CUSTOMER_NAME", (rs.getString("IE_CUSTOMER_NAME") == null ? "" : rs.getString("IE_CUSTOMER_NAME")));
						json.put("IE_ADDRESS1", (rs.getString("IE_ADDRESS1") == null ? "" : rs.getString("IE_ADDRESS1")));
						json.put("IE_ADDRESS2", (rs.getString("IE_ADDRESS2") == null ? "" : rs.getString("IE_ADDRESS2")));
						json.put("IE_VILLIAGE", (rs.getString("IE_VILLIAGE") == null ? "" : rs.getString("IE_VILLIAGE")));
						json.put("IE_LOAD_KW", (rs.getString("IE_LOAD_KW") == null ? "" : rs.getString("IE_LOAD_KW")));
						json.put("IE_LOAD_HP", (rs.getString("IE_LOAD_HP") == null ? "" : rs.getString("IE_LOAD_HP")));
						json.put("IE_SCHEME", (rs.getString("IE_SCHEME") == null ? "" : rs.getString("IE_SCHEME")));
						json.put("IE_CONNECTION_TYPE", (rs.getString("IE_CONNECTION_TYPE") == null ? "" : rs.getString("IE_CONNECTION_TYPE")));
						json.put("IE_CUSTOMER_STATUS", (rs.getString("IE_CUSTOMER_STATUS") == null ? "" : rs.getString("IE_CUSTOMER_STATUS")));
						json.put("IE_WATER_SOURCE", (rs.getString("IE_WATER_SOURCE") == null ? "" : rs.getString("IE_WATER_SOURCE")));
						json.put("IE_SERVICE_DATE", (rs.getString("IE_SERVICE_DATE") == null ? "" : rs.getString("IE_SERVICE_DATE")));
						json.put("IE_INSPECTION_DATE", (rs.getString("IE_INSPECTION_DATE") == null ? "" : rs.getString("IE_INSPECTION_DATE")));
						json.put("IE_METER_FLAG", (rs.getString("IE_METER_FLAG") == null ? "" : rs.getString("IE_METER_FLAG")));
						json.put("IE_CROP", (rs.getString("IE_CROP") == null ? "" : rs.getString("IE_CROP")));
						json.put("IE_VOLTAGE_RY", (rs.getString("IE_VOLTAGE_RY") == null ? "" : rs.getString("IE_VOLTAGE_RY")));
						json.put("IE_VOLTAGE_RB", (rs.getString("IE_VOLTAGE_RB") == null ? "" : rs.getString("IE_VOLTAGE_RB")));
						json.put("IE_VOLTAGE_BR", (rs.getString("IE_VOLTAGE_BR") == null ? "" : rs.getString("IE_VOLTAGE_BR")));
						json.put("IE_CURRENT_R", (rs.getString("IE_CURRENT_R") == null ? "" : rs.getString("IE_CURRENT_R")));
						json.put("IE_CURRENT_Y", (rs.getString("IE_CURRENT_Y") == null ? "" : rs.getString("IE_CURRENT_Y")));
						json.put("IE_CURRENT_B", (rs.getString("IE_CURRENT_B") == null ? "" : rs.getString("IE_CURRENT_B")));
						json.put("IE_LONGITUDE", (rs.getString("IE_LONGITUDE") == null ? "" : rs.getString("IE_LONGITUDE")));
						json.put("IE_LATTITUDE", (rs.getString("IE_LATTITUDE") == null ? "" : rs.getString("IE_LATTITUDE")));
						json.put("IE_ALTITUDE", (rs.getString("IE_ALTITUDE") == null ? "" : rs.getString("IE_ALTITUDE")));
						json.put("IE_SURVEYOR_NAME", (rs.getString("IE_SURVEYOR_NAME") == null ? "" : rs.getString("IE_SURVEYOR_NAME")));
						json.put("IE_MTR_SLNO", (rs.getString("IE_MTR_SLNO") == null ? "" : rs.getString("IE_MTR_SLNO")));
						json.put("IE_MTR_MAKE", (rs.getString("IE_MTR_MAKE") == null ? "" : rs.getString("IE_MTR_MAKE")));
						json.put("IE_MTR_TYPE", (rs.getString("IE_MTR_TYPE") == null ? "" : rs.getString("IE_MTR_TYPE")));
						json.put("IE_FINAL_READING", (rs.getString("IE_FINAL_READING") == null ? "" : rs.getString("IE_FINAL_READING")));
						json.put("IE_CODE_NUMBER", (rs.getString("IE_CODE_NUMBER") == null ? "" : rs.getString("IE_CODE_NUMBER")));
						json.put("IE_PHONE_NUMBER", (rs.getString("IE_PHONE_NUMBER") == null ? "" : rs.getString("IE_PHONE_NUMBER")));
						json.put("IE_PHASE", (rs.getString("IE_PHASE") == null ? "" : rs.getString("IE_PHASE")));
						json.put("IE_IMAGE_PATH", (rs.getString("IE_IMAGE_PATH") == null ? "" : rs.getString("IE_IMAGE_PATH")));
						json.put("IE_UPDATED_BY", (rs.getString("IE_UPDATED_BY") == null ? "" : rs.getString("IE_UPDATED_BY")));
						json.put("IE_UPDATED_ON", (rs.getString("IE_UPDATED_ON") == null ? "" : rs.getString("IE_UPDATED_ON")));
						json.put("IE_SCHEME_DESCR", (rs.getString("IE_SCHEME_DESCR") == null ? "" : rs.getString("IE_SCHEME_DESCR")));
						json.put("IE_CONNECTION_TYPE_DESCR", (rs.getString("IE_CONNECTION_TYPE_DESCR") == null ? "" : rs.getString("IE_CONNECTION_TYPE_DESCR")));
						json.put("IE_CUSTOMER_STATUS_DESCR", (rs.getString("IE_CUSTOMER_STATUS_DESCR") == null ? "" : rs.getString("IE_CUSTOMER_STATUS_DESCR")));
						json.put("IE_WATER_SOURCE_DESCR", (rs.getString("IE_WATER_SOURCE_DESCR") == null ? "" : rs.getString("IE_WATER_SOURCE_DESCR")));
						json.put("IE_MTR_MAKE_DESCR", (rs.getString("IE_MTR_MAKE_DESCR") == null ? "" : rs.getString("IE_MTR_MAKE_DESCR")));
						json.put("IE_MTR_TYPE_DESCR", (rs.getString("IE_MTR_TYPE_DESCR") == null ? "" : rs.getString("IE_MTR_TYPE_DESCR")));
						
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
		public JSONObject getstationmasterdetails(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			try {
				
				String sql = " SELECT ROWIDTOCHAR (S.ROWID) ROW_ID,"
						   + " SM_LOCATION_CODE SUBDIV_CODE, LOCATION_NAME SUBDIV_NAME, "
						   + " SM_STATION_CODE STATION_CODE, SM_STATION_NAME STATION_NAME,"
						   + " SM_STATION_TYPE STATION_TYPE, FN_GET_CODE_NAME(SM_STATION_TYPE,'ST_TYPE') STATION_TYPE_DESCR,"
						   + " NVL(SM_UPDATED_BY,SM_CREATED_BY) UPDATED_BY, TO_CHAR(NVL(SM_UPDATED_ON,SM_CREATED_ON),'DD/MM/YYYY HH:MI:SS AM') UPDATED_ON"
						   + " FROM STATION_MASTER S"
						   + " LEFT OUTER JOIN LOCATION ON LOCATION_CODE = SM_LOCATION_CODE"
						   + " WHERE SM_LOCATION_CODE='"+(String)object.get("location_code")+"'"
						   + " ORDER BY SM_STATION_CODE";


					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
						json.put("SUBDIV_CODE", (rs.getString("SUBDIV_CODE") == null ? "" : rs.getString("SUBDIV_CODE")));
						json.put("SUBDIV_NAME", (rs.getString("SUBDIV_NAME") == null ? "" : rs.getString("SUBDIV_NAME")));
						json.put("STATION_CODE", (rs.getString("STATION_CODE") == null ? "" : rs.getString("STATION_CODE")));
						json.put("STATION_NAME", (rs.getString("STATION_NAME") == null ? "" : rs.getString("STATION_NAME")));
						json.put("STATION_TYPE", (rs.getString("STATION_TYPE") == null ? "" : rs.getString("STATION_TYPE")));
						json.put("STATION_TYPE_DESCR", (rs.getString("STATION_TYPE_DESCR") == null ? "" : rs.getString("STATION_TYPE_DESCR")));
						json.put("UPDATED_BY", (rs.getString("UPDATED_BY") == null ? "" : rs.getString("UPDATED_BY")));
						json.put("UPDATED_ON", (rs.getString("UPDATED_ON") == null ? "" : rs.getString("UPDATED_ON")));
						
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
		public JSONObject upsertstationmaster(JSONObject object, String ipAdress) {
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
					
					 String sql = "{ call UPSERT_STATION_MASTER(?,"
							+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("location_code") + "'," + "'"
							+ (String) object.get("stationcode") + "'," + "'" + (String) object.get("stationname") + "','" 
							+ (String) object.get("stationtype") + "'," + "'" + (String) object.get("userid")  + "')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeUpdate();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				if(rs.next()) {
					result_proc = rs.getString("RESP");
				}
				
				if(result_proc.equals("success")) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Station Inserted/Updated sucessfully .");
				}else if(result_proc.equals("stncode_exists")) {
					
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Station Code Already Exists !!!");
					
				}else if(result_proc.equals("stnname_exists")) {
					
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Station Name Already Exists !!!");
					
				}
				else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Station Inserted/Updated Failed !!!");
				}

				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Station Inserted/Updated Failed");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
		}
		
		@Override
		public JSONObject getfeedermasterdetails(JSONObject object, String ipAdress) {
			// TODO Auto-generated method stub
			PreparedStatement ps = null;
			ResultSet rs = null;
			JSONObject JSON_RESPONSE = new JSONObject();
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();

			try {
				
				String sql = " SELECT ROWIDTOCHAR (F.ROWID) ROW_ID,"
						   + " FM_LOCATION_CODE SUBDIV_CODE, LOCATION_NAME SUBDIV_NAME, "
						   + " FM_STATION_CODE STATION_CODE, SM_STATION_NAME STATION_NAME,"
						   + " FM_FEEDER_CODE FEEDER_CODE, FM_FEEDER_NAME FEEDER_NAME,"
						   + " FM_STATUS FEEDER_STATUS, CASE WHEN NVL(FM_STATUS,'Y')='Y' THEN 'Active' ELSE 'Inactive' END FEEDER_STATUS_DESCR,"
						   + " NVL(FM_UPDATED_BY,FM_CREATED_BY) UPDATED_BY, TO_CHAR(NVL(FM_UPDATED_ON,FM_CREATED_ON),'DD/MM/YYYY HH:MI:SS AM') UPDATED_ON"
						   + " FROM FEEDER_MASTER F"
						   + " LEFT OUTER JOIN STATION_MASTER ON SM_LOCATION_CODE = FM_LOCATION_CODE AND SM_STATION_CODE = FM_STATION_CODE"
						   + " LEFT OUTER JOIN LOCATION ON LOCATION_CODE = FM_LOCATION_CODE"
						   + " WHERE FM_LOCATION_CODE='"+(String)object.get("location_code")+"'";
						   
							
						if(((String)object.get("stationcode")).length() > 0) {
								sql = sql + " AND FM_STATION_CODE = '"+(String)object.get("stationcode")+"' " ;
							}
								
						sql = sql + " ORDER BY FM_LOCATION_CODE, FM_STATION_CODE, FM_FEEDER_CODE " ;	


					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
						json.put("SUBDIV_CODE", (rs.getString("SUBDIV_CODE") == null ? "" : rs.getString("SUBDIV_CODE")));
						json.put("SUBDIV_NAME", (rs.getString("SUBDIV_NAME") == null ? "" : rs.getString("SUBDIV_NAME")));
						json.put("STATION_CODE", (rs.getString("STATION_CODE") == null ? "" : rs.getString("STATION_CODE")));
						json.put("STATION_NAME", (rs.getString("STATION_NAME") == null ? "" : rs.getString("STATION_NAME")));
						json.put("FEEDER_CODE", (rs.getString("FEEDER_CODE") == null ? "" : rs.getString("FEEDER_CODE")));
						json.put("FEEDER_NAME", (rs.getString("FEEDER_NAME") == null ? "" : rs.getString("FEEDER_NAME")));
						json.put("FEEDER_STATUS", (rs.getString("FEEDER_STATUS") == null ? "" : rs.getString("FEEDER_STATUS")));
						json.put("FEEDER_STATUS_DESCR", (rs.getString("FEEDER_STATUS_DESCR") == null ? "" : rs.getString("FEEDER_STATUS_DESCR")));
						json.put("UPDATED_BY", (rs.getString("UPDATED_BY") == null ? "" : rs.getString("UPDATED_BY")));
						json.put("UPDATED_ON", (rs.getString("UPDATED_ON") == null ? "" : rs.getString("UPDATED_ON")));
						
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
		public JSONObject upsertfeedermaster(JSONObject object, String ipAdress) {
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
					
					 String sql = "{ call UPSERT_FEEDER_MASTER(?,"
							+ "'" + (String) object.get("rowid") + "'," + "'" + (String) object.get("location_code") + "'," + "'"
							+ (String) object.get("stationcode") + "'," + "'" + (String) object.get("feedercode") + "','" 
							+ (String) object.get("feedername") + "','"  + (String) object.get("feederstatus") + "'," + "'" 
							+ (String) object.get("userid")  + "')}";

					 System.out.println(sql);
				calstmt = dbConn.prepareCall(sql);
				calstmt.registerOutParameter(1,OracleTypes.CURSOR );
				calstmt.executeUpdate();
				
				rs = (ResultSet) calstmt.getObject(1);
				String result_proc = "";
				if(rs.next()) {
					result_proc = rs.getString("RESP");
				}
				
				if(result_proc.equals("success")) {
					JSON_RESPONSE.put("status", "success");
					JSON_RESPONSE.put("message", "Feeder Inserted/Updated sucessfully .");
				}else if(result_proc.equals("fdrcode_exists")) {
					
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Feeder Code Already Exists !!!");
					
				}else if(result_proc.equals("fdrname_exists")) {
					
					JSON_RESPONSE.put("status", "error");
					JSON_RESPONSE.put("message", "Feeder Name Already Exists !!!");
					
				}
				else {
					JSON_RESPONSE.put("status", "fail");
					JSON_RESPONSE.put("message", "Feeder Inserted/Updated Failed !!!");
				}

				 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSON_RESPONSE.put("status", "fail");
			JSON_RESPONSE.put("message", "Feeder Inserted/Updated Failed");
		}finally {
			DBManagerResourceRelease.close(ps);
			DBManagerResourceRelease.close(calstmt);
		}
		return JSON_RESPONSE;
		}



}
