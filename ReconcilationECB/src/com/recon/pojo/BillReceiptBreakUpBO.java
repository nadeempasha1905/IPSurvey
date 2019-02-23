/**
 * 
 */
package com.recon.pojo;

import java.io.Serializable;

/**
 * @author Nadeem
 *
 */
public class BillReceiptBreakUpBO implements Serializable{
	
	
	private String tableName; 
	private String rrno;
	private String billDate;
	private String billNumber;
	private String chargeCode;
	private int    maxItemNum;
	private String RcptDate;
	private String billRcptStatus;
	private String Rcpt_Detail;
	private String Detail_from;
	private String user;
	private String tmpst;
	private double amountPaid;
	private double amountTobeAdjusted;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRrno() {
		return rrno;
	}
	public void setRrno(String rrno) {
		this.rrno = rrno;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	public int getMaxItemNum() {
		return maxItemNum;
	}
	public void setMaxItemNum(int maxItemNum) {
		this.maxItemNum = maxItemNum;
	}
	public String getRcptDate() {
		return RcptDate;
	}
	public void setRcptDate(String rcptDate) {
		RcptDate = rcptDate;
	}
	public String getBillRcptStatus() {
		return billRcptStatus;
	}
	public void setBillRcptStatus(String billRcptStatus) {
		this.billRcptStatus = billRcptStatus;
	}
	public String getRcpt_Detail() {
		return Rcpt_Detail;
	}
	public void setRcpt_Detail(String rcpt_Detail) {
		Rcpt_Detail = rcpt_Detail;
	}
	public String getDetail_from() {
		return Detail_from;
	}
	public void setDetail_from(String detail_from) {
		Detail_from = detail_from;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTmpst() {
		return tmpst;
	}
	public void setTmpst(String tmpst) {
		this.tmpst = tmpst;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public double getAmountTobeAdjusted() {
		return amountTobeAdjusted;
	}
	public void setAmountTobeAdjusted(double amountTobeAdjusted) {
		this.amountTobeAdjusted = amountTobeAdjusted;
	}
	
	
	

}
