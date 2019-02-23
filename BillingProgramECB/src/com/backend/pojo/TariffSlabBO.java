package com.backend.pojo;

import java.io.Serializable;

public class TariffSlabBO implements Serializable{
	
	private int TariffCode; 
	private char Item;
	private int  FromUnits;
	private int  ToUnits;
	private int TariffAmount; // in paise per unit
	
	
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
	public int getFromUnits() {
		return FromUnits;
	}
	public void setFromUnits(int fromUnits) {
		FromUnits = fromUnits;
	}
	public int getToUnits() {
		return ToUnits;
	}
	public void setToUnits(int toUnits) {
		ToUnits = toUnits;
	}
	public int getTariffAmount() {
		return TariffAmount;
	}
	public void setTariffAmount(int tariffAmount) {
		TariffAmount = tariffAmount;
	}
	

}
