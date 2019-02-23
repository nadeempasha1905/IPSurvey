package com.backend.pojo;

import java.io.Serializable;

public class RRNumberBO implements Serializable{
	
	String rrNo;
	String bill_dt;
	String bill_no;
	String status;
	
	public String getRrNo() {
		return rrNo;
	}
	public void setRrNo(String rrNo) {
		this.rrNo = rrNo;
	}
	public String getBill_dt() {
		return bill_dt;
	}
	public void setBill_dt(String bill_dt) {
		this.bill_dt = bill_dt;
	}
	public String getBill_no() {
		return bill_no;
	}
	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
