package com.backend.pojo;

public class EtvPenaltyTabBO {
	
	public EtvPenaltyTabBO() {
		// TODO Auto-generated constructor stub
		this.startHP = -1.0 ;
		this.endHP   = -1.0 ;
		this.rate    =  0 ;
	}
	
	private double startHP ;
	private double endHP ;
	private double rate;
	public double getStartHP() {
		return startHP;
	}
	public void setStartHP(double startHP) {
		this.startHP = startHP;
	}
	public double getEndHP() {
		return endHP;
	}
	public void setEndHP(double endHP) {
		this.endHP = endHP;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	

}
