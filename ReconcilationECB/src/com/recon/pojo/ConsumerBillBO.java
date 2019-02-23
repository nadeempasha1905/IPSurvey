package com.recon.pojo;

import java.io.Serializable;

public class ConsumerBillBO implements Serializable{
	
	private String CB_RR_NO;
	private String CB_BILL_DT;
	private String CB_BILL_NO;
	private String CC_CREDIT_AMT;
	private String rowid;
	private String cc_credit_dt;
	public String getCB_RR_NO() {
		return CB_RR_NO;
	}
	public void setCB_RR_NO(String cB_RR_NO) {
		CB_RR_NO = cB_RR_NO;
	}
	public String getCB_BILL_DT() {
		return CB_BILL_DT;
	}
	public void setCB_BILL_DT(String cB_BILL_DT) {
		CB_BILL_DT = cB_BILL_DT;
	}
	public String getCB_BILL_NO() {
		return CB_BILL_NO;
	}
	public void setCB_BILL_NO(String cB_BILL_NO) {
		CB_BILL_NO = cB_BILL_NO;
	}
	public String getCC_CREDIT_AMT() {
		return CC_CREDIT_AMT;
	}
	public void setCC_CREDIT_AMT(String cC_CREDIT_AMT) {
		CC_CREDIT_AMT = cC_CREDIT_AMT;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getCc_credit_dt() {
		return cc_credit_dt;
	}
	public void setCc_credit_dt(String cc_credit_dt) {
		this.cc_credit_dt = cc_credit_dt;
	}
	
	
	

}
