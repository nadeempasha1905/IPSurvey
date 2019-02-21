/**
 * 
 */
package com.ipsurvey.enumeration;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;

import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */
public interface IEnumeration {

	JSONObject getvillageenumerationdetails(JSONObject object, String ipAdress);

	JSONObject verifyvillageexists(JSONObject object, String ipAdress);

	Response saveimage(InputStream uploadedInputStream, FormDataContentDisposition fileDetail, String filename);

	JSONObject upsertvillageenumeration(JSONObject object, String ipAdress);

	JSONObject getimagedata(JSONObject object, HttpServletRequest request);

	JSONObject gettransformerenumerationdetails(JSONObject object, String ipAdress);

	JSONObject getipcodenumber(JSONObject object, String ipAdress);

	JSONObject validatetransformerdetails(JSONObject object, String ipAdress);

	JSONObject upserttransformerenumeration(JSONObject object, String ipAdress);

	JSONObject getconsumerdetailsbyrrno(JSONObject object, String ipAdress);

	JSONObject upsertipsetenumeration(JSONObject object, String ipAdress);

	JSONObject getipsetenumerationdetails(JSONObject object, String ipAdress);

	JSONObject getstationmasterdetails(JSONObject object, String ipAdress);

	JSONObject upsertstationmaster(JSONObject object, String ipAdress);

	JSONObject upsertfeedermaster(JSONObject object, String ipAdress);

	JSONObject getfeedermasterdetails(JSONObject object, String ipAdress);
	
}
