/**
 * 
 */
package com.ipsurvey.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */
public interface IUtility {
	
	JSONObject getcodedetails(JSONObject object, String ipAdress);
	
	JSONObject getzonelist(JSONObject object, String ipAdress);

	JSONObject getcirclelist(JSONObject object, String ipAdress);

	JSONObject getdivisionlist(JSONObject object, String ipAdress);

	JSONObject getsubdivisionlist(JSONObject object, String ipAdress);

	JSONObject getomsectionlist(JSONObject object, String ipAdress);

	JSONObject getvillagemasterdata(JSONObject object, String ipAdress);

	JSONObject getstationlist(JSONObject object, String ipAdress);

	JSONObject getfeederlist(JSONObject object, String ipAdress);

	JSONObject gettransformermasterdata(JSONObject object, String ipAdress);

	JSONObject getenumeratedvillageslist(JSONObject object, String ipAdress);
	
}
