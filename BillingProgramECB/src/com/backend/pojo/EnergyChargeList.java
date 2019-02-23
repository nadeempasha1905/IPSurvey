package com.backend.pojo;

import java.io.Serializable;

public class EnergyChargeList implements Serializable {
	
	private long Units;
    private long Rate;
    private double Amount;
	public long getUnits() {
		return Units;
	}
	public void setUnits(long units) {
		Units = units;
	}
	public long getRate() {
		return Rate;
	}
	public void setRate(long rate) {
		Rate = rate;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}

}
