/**
 * 
 */
package com.ipsurvey.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */
public interface IUserManagement {

	JSONObject signin_pc(JSONObject object, HttpServletRequest request);

	JSONObject signout(JSONObject object, HttpServletRequest request, HttpServletResponse response);
	
	JSONObject getuserdetailslist(JSONObject object, HttpServletRequest request, HttpServletResponse response);

	JSONObject verifyuserid(JSONObject object, HttpServletRequest request, HttpServletResponse response);

	JSONObject upsertuserdetails(JSONObject object, HttpServletRequest request, HttpServletResponse response);
	
	JSONObject chnagepassword(JSONObject object, HttpServletRequest request, HttpServletResponse response);

}
