/**
 * 
 */
package com.recon.pojo;

import java.io.Serializable;

/**
 * @author Nadeem
 *
 */
public class BillBreakupBO implements Serializable {
	
	private String rrno;
	private String billDate;
	private String billNumber;
	private short  priority;
	private String chargeCode;
	private double amount;
	private double amountPaid;
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
	public short getPriority() {
		return priority;
	}
	public void setPriority(short priority) {
		this.priority = priority;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	
}
