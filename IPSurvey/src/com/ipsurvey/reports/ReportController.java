/**
 * 
 */
package com.ipsurvey.reports;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import javax.ws.rs.core.Response;

/**
 * @author Nadeem
 *
 */
public class ReportController implements IReportController {

	@Override
	public Response downloadreport( HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		byte[] buffer2 = null;
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
		    .header("Content-Disposition","attachment; filename=angularjs_tutorial.pdf").build();
		 }

}
