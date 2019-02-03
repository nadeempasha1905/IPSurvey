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

	JSONObject getzonelist(JSONObject object, String ipAdress);

	JSONObject getcirclelist(JSONObject object, String ipAdress);

	JSONObject getdivisionlist(JSONObject object, String ipAdress);

	JSONObject getsubdivisionlist(JSONObject object, String ipAdress);

	JSONObject getomsectionlist(JSONObject object, String ipAdress);

	JSONObject getvillagemasterdata(JSONObject object, String ipAdress);
	
}
