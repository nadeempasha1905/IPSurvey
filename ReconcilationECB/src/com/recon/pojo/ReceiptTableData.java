/**
 * 
 */
package com.recon.pojo;

import java.io.Serializable;

/**
 * @author Nadeem
 *
 */
public class ReceiptTableData implements Serializable{
	
	
	private String rtt_tab_name;
	private String rtt_date;
	private String rowId;
	private double amountReceived;
	private double amountPosted;
	private char   status;
	public String getRtt_tab_name() {
		return rtt_tab_name;
	}
	public void setRtt_tab_name(String rtt_tab_name) {
		this.rtt_tab_name = rtt_tab_name;
	}
	public String getRtt_date() {
		return rtt_date;
	}
	public void setRtt_date(String rtt_date) {
		this.rtt_date = rtt_date;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public double getAmountReceived() {
		return amountReceived;
	}
	public void setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
	}
	public double getAmountPosted() {
		return amountPosted;
	}
	public void setAmountPosted(double amountPosted) {
		this.amountPosted = amountPosted;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
	

}
