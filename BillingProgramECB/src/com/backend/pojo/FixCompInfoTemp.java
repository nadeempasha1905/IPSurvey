package com.backend.pojo;

import java.io.Serializable;

public class FixCompInfoTemp implements Serializable{
	
	private long Units;
    private long Rate;
    private double Amount;
	public long getUnits() {
		return Units;
	}
	public void setUnits(long units) {
		Units = units;
	}
	
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public long getRate() {
		return Rate;
	}
	public void setRate(long rate) {
		Rate = rate;
	}
    
    

}
