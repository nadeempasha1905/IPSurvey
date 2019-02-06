/**
 * 
 */
package com.ipsurvey.enumeration;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
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
	
}
