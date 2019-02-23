package com.backend.pojo;

import java.io.Serializable;

public class FlagsBO implements Serializable{
	
	private String Location;
	private String RR_NO;
	private String Bill_Date;
	private String Mr_Code;
	private char  Solar_rebate;
	private char  FL_Rebate;
	private char  PH_Rebate;
	private char  Telep_Rebate;
	private char  Bill_Gen_Flag;
	private char  Meter;               
	private char  Rebate_Cap;
	private char  Demand_Based;   //char 1(Y/N)    
	private char  Rural_Rebate;       //char 1(Y/N)        
	private char  Normal;         // char 1(Y/N)
	private char  Meter_TVM;
	private char  ECSUser;
	public char getSolar_rebate() {
		return Solar_rebate;
	}
	public void setSolar_rebate(char solar_rebate) {
		Solar_rebate = solar_rebate;
	}
	public char getFL_Rebate() {
		return FL_Rebate;
	}
	public void setFL_Rebate(char fL_Rebate) {
		FL_Rebate = fL_Rebate;
	}
	public char getPH_Rebate() {
		return PH_Rebate;
	}
	public void setPH_Rebate(char pH_Rebate) {
		PH_Rebate = pH_Rebate;
	}
	public char getTelep_Rebate() {
		return Telep_Rebate;
	}
	public void setTelep_Rebate(char telep_Rebate) {
		Telep_Rebate = telep_Rebate;
	}
	public char getBill_Gen_Flag() {
		return Bill_Gen_Flag;
	}
	public void setBill_Gen_Flag(char bill_Gen_Flag) {
		Bill_Gen_Flag = bill_Gen_Flag;
	}
	public char getMeter() {
		return Meter;
	}
	public void setMeter(char meter) {
		Meter = meter;
	}
	public char getRebate_Cap() {
		return Rebate_Cap;
	}
	public void setRebate_Cap(char rebate_Cap) {
		Rebate_Cap = rebate_Cap;
	}
	public char getDemand_Based() {
		return Demand_Based;
	}
	public void setDemand_Based(char demand_Based) {
		Demand_Based = demand_Based;
	}
	public char getRural_Rebate() {
		return Rural_Rebate;
	}
	public void setRural_Rebate(char rural_Rebate) {
		Rural_Rebate = rural_Rebate;
	}
	public char getNormal() {
		return Normal;
	}
	public void setNormal(char normal) {
		Normal = normal;
	}
	public char getMeter_TVM() {
		return Meter_TVM;
	}
	public void setMeter_TVM(char meter_TVM) {
		Meter_TVM = meter_TVM;
	}
	public char getECSUser() {
		return ECSUser;
	}
	public void setECSUser(char eCSUser) {
		ECSUser = eCSUser;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getRR_NO() {
		return RR_NO;
	}
	public void setRR_NO(String rR_NO) {
		RR_NO = rR_NO;
	}
	public String getBill_Date() {
		return Bill_Date;
	}
	public void setBill_Date(String bill_Date) {
		Bill_Date = bill_Date;
	}
	public String getMr_Code() {
		return Mr_Code;
	}
	public void setMr_Code(String mr_Code) {
		Mr_Code = mr_Code;
	}
}
