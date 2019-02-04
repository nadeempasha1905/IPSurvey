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

					String sql = " SELECT ROWIDTOCHAR (ROWID) row_id,TE_CIRCLE_CODE, TE_DIVISION_CODE, "
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
							+ "  WHERE 1 = 1 " ;
					if(((String)object.get("location_code")).length() > 0) {
						sql = sql + " AND TE_OM_CODE = '"+(String)object.get("location_code")+"' " ;
					}
					if(((String)object.get("station_code")).length() > 0) {
						sql = sql + " AND TE_STATION_CODE = '"+(String)object.get("station_code")+"' " ;
					}
					if(((String)object.get("feeder_code")).length() > 0) {
						sql = sql + " AND TE_FEEDER_CODE = '"+(String)object.get("feeder_code")+"' " ;
					}
					
					System.out.println(sql);

					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					
					while(rs.next()) {
						
						json = new JSONObject();
						
						json.put("row_id", (rs.getString("row_id") == null ? "" : rs.getString("row_id")));
						json.put("TE_CIRCLE_CODE", (rs.getString("TE_CIRCLE_CODE") == null ? "" : rs.getString("TE_CIRCLE_CODE")));
						json.put("TE_DIVISION_CODE", (rs.getString("TE_DIVISION_CODE") == null ? "" : rs.getString("TE_DIVISION_CODE")));
						json.put("SUBDIVISION_NAME", (rs.getString("SUBDIVISION_NAME") == null ? "" : rs.getString("SUBDIVISION_NAME")));
						json.put("TE_SUBDIVISION_CODE", (rs.getString("TE_SUBDIVISION_CODE") == null ? "" : rs.getString("TE_SUBDIVISION_CODE")));
						json.put("OMSECTION_NAME", (rs.getString("OMSECTION_NAME") == null ? "" : rs.getString("OMSECTION_NAME")));
						json.put("TE_SUBDIVISION_CODE", (rs.getString("TE_SUBDIVISION_CODE") == null ? "" : rs.getString("TE_SUBDIVISION_CODE")));
						json.put("TE_STATION_CODE", (rs.getString("TE_STATION_CODE") == null ? "" : rs.getString("TE_STATION_CODE")));
						//json.put("station_name", (rs.getString("station_name") == null ? "" : rs.getString("station_name")));
						//json.put("feeder_name", (rs.getString("feeder_name") == null ? "" : rs.getString("feeder_name")));
						json.put("TE_FEEDER_CODE", (rs.getString("TE_FEEDER_CODE") == null ? "" : rs.getString("TE_FEEDER_CODE")));
						json.put("TE_TRANSFORMER_CODE", (rs.getString("TE_TRANSFORMER_CODE") == null ? "" : rs.getString("TE_TRANSFORMER_CODE")));
						json.put("TE_TRANSFORMER_NAME", (rs.getString("TE_TRANSFORMER_NAME") == null ? "" : rs.getString("TE_TRANSFORMER_NAME")));
						json.put("TE_TIMS_CODE", (rs.getString("TE_TIMS_CODE") == null ? "" : rs.getString("TE_TIMS_CODE")));
						json.put("TE_LONGITUDE", (rs.getString("TE_LONGITUDE") == null ? "" : rs.getString("TE_LONGITUDE")));
						json.put("TE_LATTITUDE", (rs.getString("TE_LATTITUDE") == null ? "" : rs.getString("TE_LATTITUDE")));
						json.put("TE_ALTITUDE", (rs.getString("TE_ALTITUDE") == null ? "" : rs.getString("TE_ALTITUDE")));
						json.put("TE_REMARKS", (rs.getString("TE_REMARKS") == null ? "" : rs.getString("TE_REMARKS")));
						json.put("TE_CREATED_BY", (rs.getString("TE_CREATED_BY") == null ? "" : rs.getString("TE_CREATED_BY")));
						json.put("TE_CREATED_ON", (rs.getString("TE_CREATED_ON") == null ? "" : rs.getString("TE_CREATED_ON")));
						json.put("TE_UPDATED_BY", (rs.getString("TE_UPDATED_BY") == null ? "" : rs.getString("TE_UPDATED_BY")));
						json.put("TE_UPDATED_ON", (rs.getString("TE_UPDATED_ON") == null ? "" : rs.getString("TE_UPDATED_ON")));
						json.put("TE_VILLAGE", (rs.getString("TE_VILLAGE") == null ? "" : rs.getString("TE_VILLAGE")));
						json.put("TE_IMAGE_PATH", (rs.getString("TE_IMAGE_PATH") == null ? "" : rs.getString("TE_IMAGE_PATH")));
						
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
