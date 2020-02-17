/**
 * 
 */
package com.ipsurvey.router;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ipsurvey.enumeration.EnumerationImpl;
import com.ipsurvey.enumeration.IEnumeration;
import com.ipsurvey.map.IViewMap;
import com.ipsurvey.map.ViewMapImpl;
import com.ipsurvey.reports.IReportController;
import com.ipsurvey.reports.ReportController;
import com.ipsurvey.user.IUserManagement;
import com.ipsurvey.user.UserManagementImpl;
import com.ipsurvey.util.IUtility;
import com.ipsurvey.util.UtilityImpl;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */

@Path("/services")
public class ServiceRouter {
	
	IUtility utilObj = new UtilityImpl();
	IUserManagement userObj = new UserManagementImpl();
	IEnumeration enumObj =  new EnumerationImpl();
	IViewMap mapObj =  new ViewMapImpl();
	IReportController reportObj =  new ReportController();
	
	@POST
	@Path("/signin_pc")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject signin_pc(final JSONObject object, @Context HttpServletRequest request){
		//String ipAdress = request.getRemoteHost();
		System.out.println("signin_pc....."+object);
		return userObj.signin_pc(object,request);
	}
	
	@POST
	@Path("/signin_location_object")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject signin_location_object(final JSONObject object, @Context HttpServletRequest request){
		//String ipAdress = request.getRemoteHost();
		System.out.println("signin_pc....."+object);
		return userObj.signin_location_object(object,request);
	}
	
	@POST
	@Path("/signout")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject signout(final JSONObject object, @Context HttpServletRequest request,@Context HttpServletResponse response){
		//String ipAdress = request.getRemoteHost();
		System.out.println("signout....."+object);
		return userObj.signout(object,request,response);
	}
	
	@POST
	@Path("/getzonelist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getzonelist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getzonelist(object,ipAdress);
	}
	
	@POST
	@Path("/getcirclelist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getcirclelist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getcirclelist(object,ipAdress);
	}
	
	@POST
	@Path("/getdivisionlist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getdivisionlist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getdivisionlist(object,ipAdress);
	}
	
	@POST
	@Path("/getsubdivisionlist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getsubdivisionlist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getsubdivisionlist(object,ipAdress);
	}
	
	@POST
	@Path("/getomsectionlist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getomsectionlist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getomsectionlist(object,ipAdress);
	}
	
	@POST
	@Path("/getstationlist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getstationlist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getstationlist(object,ipAdress);
	}
	
	@POST
	@Path("/getfeederlist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getfeederlist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getfeederlist(object,ipAdress);
	}
	
	@POST
	@Path("/getcodedetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getcodedetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getcodedetails(object,ipAdress);
	}
	
	@POST
	@Path("/getcastecategory")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getcastecategory(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		System.out.println("hi....."+object);
		return utilObj.getcastecategory(object,ipAdress);
	}
	
	@POST
	@Path("/getuserdetailslist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getuserdetailslist(final JSONObject object,@Context HttpServletRequest request,@Context HttpServletResponse response){
		return userObj.getuserdetailslist(object,request,response);
	}
	
	@POST
	@Path("/verifyuserid")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject verifyuserid(final JSONObject object,@Context HttpServletRequest request,@Context HttpServletResponse response){
		return userObj.verifyuserid(object,request,response);
	}
	
	@POST
	@Path("/upsertuserdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upsertuserdetails(final JSONObject object,@Context HttpServletRequest request,@Context HttpServletResponse response){
		return userObj.upsertuserdetails(object,request,response);
	}
	
	@POST
	@Path("/chnagepassword")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject chnagepassword(final JSONObject object,@Context HttpServletRequest request,@Context HttpServletResponse response){
		return userObj.chnagepassword(object,request,response);
	}

	@POST
	@Path("/getvillagemasterdata")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getvillagemasterdata(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return utilObj.getvillagemasterdata(object,ipAdress);
	}
	
	@POST
	@Path("/getvillageenumerationdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getvillageenumerationdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getvillageenumerationdetails(object,ipAdress);
	}
	
	@POST
	@Path("/verifyvillageexists")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject verifyvillageexists(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.verifyvillageexists(object,ipAdress);
	}
	
	@POST
	@Path("/upsertvillageenumeration")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upsertvillageenumeration(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.upsertvillageenumeration(object,ipAdress);
	}
	
	
	@POST
	@Path("/gettransformerenumerationdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject gettransformerenumerationdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.gettransformerenumerationdetails(object,ipAdress);
	}
	
	@POST
	@Path("/gettransformermasterdata")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject gettransformermasterdata(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return utilObj.gettransformermasterdata(object,ipAdress);
	}
	
	@POST
	@Path("/getenumeratedvillageslist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getenumeratedvillageslist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return utilObj.getenumeratedvillageslist(object,ipAdress);
	}
	
	@POST
	@Path("/getenumeratedtransformerslist")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getenumeratedtransformerslist(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return utilObj.getenumeratedtransformerslist(object,ipAdress);
	}
	
	@POST
	@Path("/validatetransformerdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject validatetransformerdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.validatetransformerdetails(object,ipAdress);
	}
	
	@POST
	@Path("/upserttransformerenumeration")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upserttransformerenumeration(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.upserttransformerenumeration(object,ipAdress);
	}
	
	@POST
	@Path("/getipcodenumber")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getipcodenumber(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getipcodenumber(object,ipAdress);
	}
	
	@POST
	@Path("/getconsumerdetailsbyrrno")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getconsumerdetailsbyrrno(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getconsumerdetailsbyrrno(object,ipAdress);
	}
	
	@POST
	@Path("/upsertipsetenumeration")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upsertipsetenumeration(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.upsertipsetenumeration(object,ipAdress);
	}
	
	
	@POST
	@Path("/getipsetenumerationdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getipsetenumerationdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getipsetenumerationdetails(object,ipAdress);
	}
	
	@POST
	@Path("/getstationmasterdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getstationmasterdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getstationmasterdetails(object,ipAdress);
	}
	
	
	@POST
	@Path("/upsertstationmaster")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upsertstationmaster(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.upsertstationmaster(object,ipAdress);
	}
	
	@POST
	@Path("/getfeedermasterdetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getfeedermasterdetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getfeedermasterdetails(object,ipAdress);
	}
	
	@POST
	@Path("/upsertfeedermaster")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject upsertfeedermaster(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.upsertfeedermaster(object,ipAdress);
	}
	
	@POST
	@Path("/dotransformertransfer")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject dotransformertransfer(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.dotransformertransfer(object,ipAdress);
	}
	
	@POST
	@Path("/doipsettransfer")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject doipsettransfer(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.doipsettransfer(object,ipAdress);
	}
	
	@POST
	@Path("/getipsetdetails_to_transferipset")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getipsetdetails_to_transferipset(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getipsetdetails_to_transferipset(object,ipAdress);
	}
	
	@POST
	@Path("/getrrnumberslistfortransfer")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getrrnumberslistfortransfer(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return enumObj.getrrnumberslistfortransfer(object,ipAdress);
	}
	
	
	
	/**********************************/
	//View Map
	
	@POST
	@Path("/gettransformerpoints")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject gettransformerpoints(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return mapObj.gettransformerpoints(object,ipAdress);
	}
	
	@POST
	@Path("/getmappingpoints")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getmappingpoints(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return mapObj.getmappingpoints(object,ipAdress);
	}
	
	@POST
	@Path("/getmaindashboarddetails")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getmaindashboarddetails(final JSONObject object, @Context HttpServletRequest request){
		String ipAdress = request.getRemoteHost();
		return reportObj.getmaindashboarddetails(object,ipAdress);
	}
	
	
	
	
	
	/*********************************/
	
	
	
	
	
	
	
	
	
	
	@POST
	@Path("/saveimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveimage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, 
			@FormDataParam("filename") String filename) {
		System.out.println(uploadedInputStream);
		System.out.println(filename);
		
		return enumObj.saveimage(uploadedInputStream,fileDetail,filename);
	}
	
	@POST
	@Path("/getimagedata")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getimagedata(final JSONObject object, @Context HttpServletRequest request){
		System.out.println("Fetching Image Data.......");
		
		return enumObj.getimagedata(object,request);
	}
	
	@GET
	@Path("/downloadreport")
	/*@Consumes({MediaType.APPLICATION_JSON})*/
	/*@Produces({MediaType.APPLICATION_OCTET_STREAM} )*/
	@Produces("application/pdf")
	public Response downloadreport(@Context HttpServletRequest request, @Context HttpServletResponse response){
		System.out.println("downloading report......."+request.getParameter("location"));
		
		return reportObj.downloadreport(request,response);
	}
	
			
	
}
