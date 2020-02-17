/**
 * 
 */
package com.ipsurvey.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;

import org.apache.commons.io.IOUtils;

import com.ipsurvey.database.DBManagerIMPL;

import javax.ws.rs.core.Response;

/**
 * @author Nadeem
 *
 */
public class ReportController implements IReportController {
	
	/** The path to the folder where we want to store the uploaded files */
	//private static final String UPLOAD_FOLDER = "c:/uploadedFiles/";
	
	ResourceBundle propsBundle=ResourceBundle.getBundle("ipsurveydb");
	String REPORT_PATH = propsBundle.getString("REPORT_PATH");

//database connection init
		static DBManagerIMPL dbObject = new DBManagerIMPL();
		static Connection dbConn;
	
	public ReportController() {
		// TODO Auto-generated constructor stub
		try {
			dbConn = dbObject.getDatabaseConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Response downloadreport( HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HashMap parameterMap = new HashMap();
		String path = null ;
		
		String report_type = request.getParameter("report_type").trim();
		String fileName = "";
		String report = "";
		String report_title = "";
		
		String circle_code = request.getParameter("circle").trim();
		String division_code = request.getParameter("division").trim();
		String subdivision = request.getParameter("subdivision").trim();
		String om_code = request.getParameter("omsection").trim();
		String station = request.getParameter("station").trim();
		String feeder = request.getParameter("feeder").trim();
		String transformer = request.getParameter("transformer").trim();
		String village = request.getParameter("village").trim();
		String location_code = request.getParameter("location_code").trim();
		String datetimepicker = request.getParameter("reportdate").trim();
		String login_location = request.getParameter("login_location").trim();
		
		
		if(report_type.length() > 0){
			
			if(report_type.equals("1")){
				fileName = "Transformer_Enumeration";
				report = "TRANSFORMER";
				report_title = "Details Of Transformer Enumerated";
			}else if(report_type.equals("2")){
				fileName = "Pole_Enumeration";
				report = "POLE";
				report_title = "Details Of Pole Enumerated";
			}else if(report_type.equals("3")){
				fileName = "IPset_Enumeration";
				report = "IPSET";
				report_title = "Details Of Irrigation Pumpset Enumerated";
			}else if(report_type.equals("4")){
				fileName = "Village_Enumeration";
				report = "VILLAGE";
				report_title = "Details Of Village Enumerated";
			}else if(report_type.equals("5")){
				fileName = "gps_survey_progress";
				//subdivision = "2";
			}else if(report_type.equals("6")){
				fileName = "gps_survey_progress_user_wise";
				//subdivision = "";
			}else if(report_type.equals("11")){
				fileName = "gps_survey_progress_new";
				//subdivision = "";
			}else if(report_type.equals("7")){
				fileName = "Transformer_Enumeration_new";
				report = "TRANSFORMER";
				report_title = "Details Of Transformer Enumerated";
			}else if(report_type.equals("8")){
				fileName = "Pole_Enumeration_new";
				report = "POLE";
				report_title = "Details Of Pole Enumerated";
			}else if(report_type.equals("9")){
				fileName = "IPset_Enumeration_new";
				report = "IPSET";
				report_title = "Details Of Irrigation Pumpset Enumerated";
			}else if(report_type.equals("10")){
				fileName = "IPReport";
				report = "IPSET";
				report_title = "Detailed Report Of IPsets";
			}else if(report_type.equals("15")){
				fileName = "progress_kerc";
				report = "KERC";
				report_title = "Details Of KERC";
			}
			else if(report_type.equals("21")){
				fileName = "annex1";
			}
			else if(report_type.equals("22")){
				fileName = "annex2";
			}
			else if(report_type.equals("23")){
				fileName = "annex3";
			}
			else if(report_type.equals("24")){
				fileName = "annex4";
			}
			else if(report_type.equals("25")){
				fileName = "annex5";
			}else if(report_type.equals("26")){
				fileName = "Annex_abstract";
			}

	        try {
	        	String contextPath = request.getServletContext().getRealPath("ReportController.java");
	        	File contextfile = new File(contextPath);
	        	String parentfile = contextfile.getParent();
	        	
	        	/*File f = new File(parentfile+"/Jaspers/" + fileName );*/
	        	File f = new File(REPORT_PATH+ fileName );
				path = f.getPath();
				
				/*String imgPath = "";
					imgPath = request.getServletContext().getRealPath("app/images/mescom_logo.jpg");*/
				
				String imgPath = REPORT_PATH + "mescom_logo.jpg";
				//imgPath = request.getServletContext().getRealPath("app/images/mescom_logo.jpg");
				
					
				/*	if(om_code != null && om_code.length()>0){
						subdivision = om_code;
					}*/
	            
	            parameterMap.put("REPORT_TITLE", report_title);
	            parameterMap.put("REPORT_TYPE", report);
	            parameterMap.put("P_CIRCLE_CODE", circle_code);
	            parameterMap.put("P_DIVISION_CODE", division_code);
	            parameterMap.put("P_SUBDIVISION_CODE", subdivision);
	            
	            parameterMap.put("P_LOCATION_CODE", location_code);
	            parameterMap.put("P_LOC", (location_code == null || location_code == "" || location_code.equals("") ? login_location : location_code));
	            
	            parameterMap.put("P_OM_CODE", om_code);
	            parameterMap.put("P_STATION_CODE", station);
	            parameterMap.put("P_FEEDER_CODE", feeder);
	            parameterMap.put("P_TC_CODE", transformer);
	            parameterMap.put("P_VILLAGE", village);
	            parameterMap.put("P_UPLOADED_DATE", "");
	            parameterMap.put("P_INSPECTED_DATE", "");
	            
	            parameterMap.put("P_ASONDATE", datetimepicker);
	            
	            parameterMap.put("imagepath", imgPath);
	            
	            System.out.println(parameterMap);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	            	//dbConn.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        response.setContentType("application/pdf");
	        
			JasperPrint jasperPrint = null;
			InputStream input = null;
			ServletOutputStream outStream = null;
			FileInputStream fin = null;
			try {

				input = new FileInputStream(new File(path+ ".jasper"));
				//System.out.println("before going to conn");
				
				jasperPrint = JasperFillManager.fillReport(input,parameterMap,dbConn);
				//System.out.println("after going to conn");

				JRPdfExporter pdfExporter = new JRPdfExporter();
	
				pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				//System.out.println("dir abspath .pdf " + dir.getPath()+ "/" + fname + ".pdf");
				//pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, dir.getPath()+ "/" + PDFname + ".pdf");
				
				//System.out.println("dir abspath .pdf " + path);
				pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path+ ".pdf");
				
				pdfExporter.exportReport();
				
				JasperExportManager.exportReportToPdfFile(jasperPrint,path+ ".pdf");
				
				fin = new FileInputStream(new File(path+ ".pdf"));
				outStream = response.getOutputStream();
				// SET THE MIME TYPE.
				response.setContentType("application/pdf");
				// set content dispostion to attachment in with file name.
				// case the open/save dialog needs to appear.
				response.setHeader("Content-Disposition", "attachment;filename="+fileName+".pdf");
				

				byte[] buffer = new byte[1024];
				int n = 0;
				while ((n = fin.read(buffer)) != -1) {
				outStream.write(buffer, 0, n);
				}
				
				
				
				

				outStream.flush();
				fin.close();
				outStream.close();
				
			} catch (JRException e) {
				System.out.println("ERRR IN iREPORT GENERATION FROM SERVER");
				//rbo.setReport_status("E");
				//ReferenceUtill.updateReportStatus(rbo);
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("ERRR IN iREPORT GENERATION FROM SERVER");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		byte[] buffer2 = null;
		  try {
		   File file = new File(path+ ".pdf");
		   FileInputStream fis = new FileInputStream(file);
		   buffer2 = IOUtils.toByteArray(fis);
		   fis.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		   System.out.println(buffer2);
		  return Response.ok(buffer2)
		    .header("Content-Length", buffer2.length)
		    .header("Content-Disposition","attachment; filename=angularjs_tutorial.pdf").build();
		
		/*byte[] buffer2 = null;
		  try {
		   File file = new File("C:\\angularjs_tutorial.pdf");
		   FileInputStream fis = new FileInputStream(file);
		   buffer2 = IOUtils.toByteArray(fis);
		   fis.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		   System.out.println(buffer2);
		  return Response.ok(buffer2)
		    .header("Content-Length", buffer2.length)
		    .header("Content-Disposition","attachment; filename=angularjs_tutorial.pdf").build();*/
		 }

	@Override
	public JSONObject getmaindashboarddetails(JSONObject object, String ipAdress) {
		// TODO Auto-generated method stub
		
		
		CallableStatement accountsCS = null;
		ResultSet accountsRS = null;
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();

		String location_code = object.getString("location_code");
		
		System.out.println(object);

		try {

				dbConn = dbObject.getDatabaseConnection();

			accountsCS = dbConn.prepareCall("{ call  GET_DASHBOARD (?,'"+location_code+"')}");
			accountsCS.registerOutParameter(1, OracleTypes.CURSOR);
			
			accountsCS.executeUpdate();
			accountsRS = (ResultSet) accountsCS.getObject(1);
			System.out.println(accountsRS);
			while (accountsRS.next()) {
				JSONObject ackobj = new JSONObject();

				ackobj.put("slno", accountsRS.getString("slno"));
				ackobj.put("GROUPNAME", accountsRS.getString("GROUPNAME"));
				ackobj.put("LOCATION_CODE", accountsRS.getString("LOCATION_CODE"));
				ackobj.put("LOCATION", accountsRS.getString("LOCATION"));
				ackobj.put("NOOFFDRS", accountsRS.getString("NOOFFDRS"));
				ackobj.put("NOOFTCS", accountsRS.getString("NOOFTCS"));
				ackobj.put("TOTINTL", accountsRS.getString("TOTINTL"));
				ackobj.put("AUTHORIZED", accountsRS.getString("AUTHORIZED"));
				ackobj.put("UNAUTHORIZED", accountsRS.getString("UNAUTHORIZED"));
				ackobj.put("INUSE", accountsRS.getString("INUSE"));
				ackobj.put("NOTINUSE", accountsRS.getString("NOTINUSE"));
				ackobj.put("DRY", accountsRS.getString("DRY"));
				ackobj.put("HP10ANDBELOW", accountsRS.getString("HP10ANDBELOW"));
				ackobj.put("HP10ABOVE", accountsRS.getString("HP10ABOVE"));
				
				array.add(ackobj);

			}
			if (array.isEmpty()) {
				// no tasks for user
				obj.put("status", "success");
				obj.put("message", "No Records Found !!!");
			} else {
				obj.put("status", "success");
				obj.put("message", "Main-Dashboard Retrieved !!!");
				obj.put("MAINDASHBOARD", array);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("status", "fail");
			e.printStackTrace();
			obj.put("message", "database not connected");
		}

		return obj;
	}

}
