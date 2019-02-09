/**
 * 
 */
package com.ipsurvey.map;

import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */
public interface IViewMap {

	JSONObject gettransformerpoints(JSONObject object, String ipAdress);

	JSONObject getmappingpoints(JSONObject object, String ipAdress);
	
	

}
