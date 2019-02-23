package com.backend.pojo;

import java.io.Serializable;

public class TariffFixBO implements Serializable{

	
	private int TariffCode; 
	private char Item;
	private char ChargeType; // M - Monthly / Q - Quarterly, H - Half yearly, A - Yearly
	private int From_Units;   // From sanctioned power  
	private int To_Units;       // To sanctioned power 
	private long Tariff_Amount; // in paise per unit 
	
	
	public int getTariffCode() {
		return TariffCode;
	}
	public void setTariffCode(int tariffCode) {
		TariffCode = tariffCode;
	}
	public char getItem() {
		return Item;
	}
	public void setItem(char item) {
		Item = item;
	}
	public char getChargeType() {
		return ChargeType;
	}
	public void setChargeType(char chargeType) {
		ChargeType = chargeType;
	}
	public int getFrom_Units() {
		return From_Units;
	}
	public void setFrom_Units(int from_Units) {
		From_Units = from_Units;
	}
	public int getTo_Units() {
		return To_Units;
	}
	public void setTo_Units(int to_Units) {
		To_Units = to_Units;
	}
	public long getTariff_Amount() {
		return Tariff_Amount;
	}
	public void setTariff_Amount(long tariff_Amount) {
		Tariff_Amount = tariff_Amount;
	}
    
}
