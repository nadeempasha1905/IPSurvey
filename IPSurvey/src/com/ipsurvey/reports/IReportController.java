/**
 * 
 */
package com.ipsurvey.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import net.sf.json.JSONObject;

/**
 * @author Nadeem
 *
 */
public interface IReportController {

	Response downloadreport( HttpServletRequest request, HttpServletResponse response);

	JSONObject getmaindashboarddetails(JSONObject object, String ipAdress);

}
