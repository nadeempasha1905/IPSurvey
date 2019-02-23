package com.backend.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class BillingRecordBO implements Serializable {

	private String location; // 1
	private String rr_No; // 2
	private String ledger_No; // 3
	private String actual_Folio_No; // 4
	private String Spot_Folio_No; // 5
	private int Tariff_Code; // 6
	private int Bill_No; // 7
	private String Bill_Date; // 8
	private String Consmr_Name; // 9
	private String Address1; // 10
	private String Address2; // 11
	private String Address3; // 12
	private String Billing_Month; // 13
	private String Reading_Date; // 14
	private String Reader_Code; // 15
	private int Installation_Status; // 16
	private double Line_Minimum; // 17
	private double Sanct_HP; // 18
	private double Sanct_KW; // 19
	private double CT_Ratio; // 20
	private long Prev_Mtr_Rdg; // 21
	private double Avg_Consumption; // 22
	private double Power_Factor; // 23
	private String Mtr_Chng_Dt1; // 24
	private double Mtr_Chng_Rdg1; // 25
	private String Mtr_Chng_Dt2; // 26
	private double Mtr_Chng_Rgd2; // 27
	private int FAC_Rate; // 28
	private double Dmd_Arrears; // 29
	private double int_Arrears; // 30
	private double Tax_Arrears; // 31
	private double Delay_interest; // 32
	private double Amt_Paid1; // 33
	private String Paid_Date1; // 34
	private double Amt_Paid2; // 35
	private String Paid_Date2; // 36
	private double Others; // 37
	private double Previous_Demand1; // 38
	private double Previous_Demand2; // 39
	private double Previous_Demand3; // 40
	private String Billing_Mode; // 41
	private double Meter_Const; // 42
	private double Appeal_Amount; // 43
	private double int_on_Appeal_Amt; // 44
	private double KVAH; // 45
	private String Inst_Typ; // 46
	private double HP_Round; // 47
	private double KW_Round; // 48
	private String Previous_Bill_Date; // 49
	private double CreditBF; // 50
	private double DebitBF; // 51
	private String Due_Date; // 52
	private String IvrsId; // 53
	private double partFraction; // 54
	private String tax_flag; // 55
	private String prev_rdg_date; // 56
	private long Prst_Rdg; // 57
	private String Mtr_Sts; // 58
	private long nUnitsConsumed; // 59
	private double nTax; // 060
	private double temp_Tax; // 61
	private double TotalBill; // 62
	private double nRebate; // 63
	private double nTotalFixedTariff; // 64
	private double p_nTotalEnergyTariff; // 65
	private double CreditCF; // 66
	private int numDL; // 67
	private String DC_Flg; // 68
	private double int_Arrears2; // 69
	private double int_Tax; // 70
	private String First_Rdg_Flg; // 71
	private int Creaping_Perc; // 72
	private String MNR_Flg; // 73
	private long Old_Mtr_Rdg; //74
	private double no_tax_comp; // 75
	private String prev_rdg_flg; // 76
	private String PLFlag; // 77
	private double PLRebate; // 78
	//private double RbtMoreClaimed; // 79
	//private double RbtLessClaimed; // 80
	private double Less_MoreClaimedint; // 81
	private int temp_Tariff_Code; // 82
	private int temp_PLTariff_Code; // 83
	private double DiffAmt; // 84
	private String CGexempt_flg; // 85
	private int tempKW_Round; // 86
	private double energyamtplustax; // 87
	private double lessclaimed; // 88
	private double moreclaimed; // 89
	private double int_on_tax; // 90
	private String subdiv; // 91
	private double PrevCKWH; // 92
	private double PrstCKWH; // 93
	private double reg_penalty; // 94
	private double PFPenaltyamt; // 95
	private String zygox; // 96
	private double PHRebate; // 97
	private String pf_flag; // 98
	private double temp_total; // 99
	private int lastmonthfraction; // 100
	private String firstflag; // 101
	private String fullmonflag; // 102
	private int cur_qrtr; // 103
	private int frequency; // 104
	private double Annual_Min_Fix; // 105
	private double HP_Min_Fix; // 106
	private double lt4debit; // 107
	private double RR_Rebate; // 108
	private double Capacitor; // 109
	private double CapRbtAmt; // 110
	private int z; // 111
	private String cheqd; // 112
	private double OrphnRbt; // 113
	private double OrphnAmt; // 114
	//private String dtbcd; // 115
	//private String duprint; // 116
	private double mmdcredit; // 117
	private String mtr_chng_flg; // 118
	private double add3mmd_dep; // 119
	private long PrevConsmp; // 120
	//private String filler; // 121
	private String billPrinted; // 122
	private String UPLOADEDTOSERVER; // 123
	private int RecordIndex; // 124
	
	private double Previous_Bill_Amnt;
	private double RecordedBMD; 
	private double lt1_rbt_rate;
	private String user;
	private double weekMinAmt;
	private double weekadjamt;
	private double flRebate;
	private double recordedBKWH;
	
	/*
	 * 
	*/
	private  int p_nComputedInfoCounter;
	private  int p_nCompFixInfoCounter;
	private  int DGPCompInfoCounter;
	private  double penaltyonexcessload;
	//
	FlagsBO flagsBO;
	//EnergyChargeList energyChargeListBO;
	//FixedChargeList  fixedChargeListBO;
	
	ArrayList<EnergyChargeList> energyChargeListBO;
	ArrayList<FixedChargeList>  fixedChargeListBO;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRr_No() {
		return rr_No;
	}
	public void setRr_No(String rr_No) {
		this.rr_No = rr_No;
	}
	public String getLedger_No() {
		return ledger_No;
	}
	public void setLedger_No(String ledger_No) {
		this.ledger_No = ledger_No;
	}
	public String getActual_Folio_No() {
		return actual_Folio_No;
	}
	public void setActual_Folio_No(String actual_Folio_No) {
		this.actual_Folio_No = actual_Folio_No;
	}
	public String getSpot_Folio_No() {
		return Spot_Folio_No;
	}
	public void setSpot_Folio_No(String spot_Folio_No) {
		Spot_Folio_No = spot_Folio_No;
	}
	public int getTariff_Code() {
		return Tariff_Code;
	}
	public void setTariff_Code(int tariff_Code) {
		Tariff_Code = tariff_Code;
	}
	public int getBill_No() {
		return Bill_No;
	}
	public void setBill_No(int bill_No) {
		Bill_No = bill_No;
	}
	public String getBill_Date() {
		return Bill_Date;
	}
	public void setBill_Date(String bill_Date) {
		Bill_Date = bill_Date;
	}
	public String getConsmr_Name() {
		return Consmr_Name;
	}
	public void setConsmr_Name(String consmr_Name) {
		Consmr_Name = consmr_Name;
	}
	public String getAddress1() {
		return Address1;
	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getAddress2() {
		return Address2;
	}
	public void setAddress2(String address2) {
		Address2 = address2;
	}
	public String getAddress3() {
		return Address3;
	}
	public void setAddress3(String address3) {
		Address3 = address3;
	}
	public String getBilling_Month() {
		return Billing_Month;
	}
	public void setBilling_Month(String billing_Month) {
		Billing_Month = billing_Month;
	}
	public String getReading_Date() {
		return Reading_Date;
	}
	public void setReading_Date(String reading_Date) {
		Reading_Date = reading_Date;
	}
	public String getReader_Code() {
		return Reader_Code;
	}
	public void setReader_Code(String reader_Code) {
		Reader_Code = reader_Code;
	}
	public int getInstallation_Status() {
		return Installation_Status;
	}
	public void setInstallation_Status(int installation_Status) {
		Installation_Status = installation_Status;
	}
	public double getLine_Minimum() {
		return Line_Minimum;
	}
	public void setLine_Minimum(double line_Minimum) {
		Line_Minimum = line_Minimum;
	}
	public double getSanct_HP() {
		return Sanct_HP;
	}
	public void setSanct_HP(double sanct_HP) {
		Sanct_HP = sanct_HP;
	}
	public double getSanct_KW() {
		return Sanct_KW;
	}
	public void setSanct_KW(double sanct_KW) {
		Sanct_KW = sanct_KW;
	}
	public double getCT_Ratio() {
		return CT_Ratio;
	}
	public void setCT_Ratio(double cT_Ratio) {
		CT_Ratio = cT_Ratio;
	}
	public long getPrev_Mtr_Rdg() {
		return Prev_Mtr_Rdg;
	}
	public void setPrev_Mtr_Rdg(long prev_Mtr_Rdg) {
		Prev_Mtr_Rdg = prev_Mtr_Rdg;
	}
	public double getAvg_Consumption() {
		return Avg_Consumption;
	}
	public void setAvg_Consumption(double avg_Consumption) {
		Avg_Consumption = avg_Consumption;
	}
	public double getPower_Factor() {
		return Power_Factor;
	}
	public void setPower_Factor(double power_Factor) {
		Power_Factor = power_Factor;
	}
	public String getMtr_Chng_Dt1() {
		return Mtr_Chng_Dt1;
	}
	public void setMtr_Chng_Dt1(String mtr_Chng_Dt1) {
		Mtr_Chng_Dt1 = mtr_Chng_Dt1;
	}
	public double getMtr_Chng_Rdg1() {
		return Mtr_Chng_Rdg1;
	}
	public void setMtr_Chng_Rdg1(double mtr_Chng_Rdg1) {
		Mtr_Chng_Rdg1 = mtr_Chng_Rdg1;
	}
	public String getMtr_Chng_Dt2() {
		return Mtr_Chng_Dt2;
	}
	public void setMtr_Chng_Dt2(String mtr_Chng_Dt2) {
		Mtr_Chng_Dt2 = mtr_Chng_Dt2;
	}
	public double getMtr_Chng_Rgd2() {
		return Mtr_Chng_Rgd2;
	}
	public void setMtr_Chng_Rgd2(double mtr_Chng_Rgd2) {
		Mtr_Chng_Rgd2 = mtr_Chng_Rgd2;
	}
	public int getFAC_Rate() {
		return FAC_Rate;
	}
	public void setFAC_Rate(int fAC_Rate) {
		FAC_Rate = fAC_Rate;
	}
	public double getDmd_Arrears() {
		return Dmd_Arrears;
	}
	public void setDmd_Arrears(double dmd_Arrears) {
		Dmd_Arrears = dmd_Arrears;
	}
	public double getInt_Arrears() {
		return int_Arrears;
	}
	public void setInt_Arrears(double int_Arrears) {
		this.int_Arrears = int_Arrears;
	}
	public double getTax_Arrears() {
		return Tax_Arrears;
	}
	public void setTax_Arrears(double tax_Arrears) {
		Tax_Arrears = tax_Arrears;
	}
	public double getDelay_interest() {
		return Delay_interest;
	}
	public void setDelay_interest(double delay_interest) {
		Delay_interest = delay_interest;
	}
	public double getAmt_Paid1() {
		return Amt_Paid1;
	}
	public void setAmt_Paid1(double amt_Paid1) {
		Amt_Paid1 = amt_Paid1;
	}
	public String getPaid_Date1() {
		return Paid_Date1;
	}
	public void setPaid_Date1(String paid_Date1) {
		Paid_Date1 = paid_Date1;
	}
	public double getAmt_Paid2() {
		return Amt_Paid2;
	}
	public void setAmt_Paid2(double amt_Paid2) {
		Amt_Paid2 = amt_Paid2;
	}
	public String getPaid_Date2() {
		return Paid_Date2;
	}
	public void setPaid_Date2(String paid_Date2) {
		Paid_Date2 = paid_Date2;
	}
	public double getOthers() {
		return Others;
	}
	public void setOthers(double others) {
		Others = others;
	}
	public double getPrevious_Demand1() {
		return Previous_Demand1;
	}
	public void setPrevious_Demand1(double previous_Demand1) {
		Previous_Demand1 = previous_Demand1;
	}
	public double getPrevious_Demand2() {
		return Previous_Demand2;
	}
	public void setPrevious_Demand2(double previous_Demand2) {
		Previous_Demand2 = previous_Demand2;
	}
	public double getPrevious_Demand3() {
		return Previous_Demand3;
	}
	public void setPrevious_Demand3(double previous_Demand3) {
		Previous_Demand3 = previous_Demand3;
	}
	public String getBilling_Mode() {
		return Billing_Mode;
	}
	public void setBilling_Mode(String billing_Mode) {
		Billing_Mode = billing_Mode;
	}
	public double getMeter_Const() {
		return Meter_Const;
	}
	public void setMeter_Const(double meter_Const) {
		Meter_Const = meter_Const;
	}
	public double getAppeal_Amount() {
		return Appeal_Amount;
	}
	public void setAppeal_Amount(double appeal_Amount) {
		Appeal_Amount = appeal_Amount;
	}
	public double getInt_on_Appeal_Amt() {
		return int_on_Appeal_Amt;
	}
	public void setInt_on_Appeal_Amt(double int_on_Appeal_Amt) {
		this.int_on_Appeal_Amt = int_on_Appeal_Amt;
	}
	public double getKVAH() {
		return KVAH;
	}
	public void setKVAH(double kVAH) {
		KVAH = kVAH;
	}
	public String getInst_Typ() {
		return Inst_Typ;
	}
	public void setInst_Typ(String inst_Typ) {
		Inst_Typ = inst_Typ;
	}
	public double getHP_Round() {
		return HP_Round;
	}
	public void setHP_Round(double hP_Round) {
		HP_Round = hP_Round;
	}
	public double getKW_Round() {
		return KW_Round;
	}
	public void setKW_Round(double kW_Round) {
		KW_Round = kW_Round;
	}
	public String getPrevious_Bill_Date() {
		return Previous_Bill_Date;
	}
	public void setPrevious_Bill_Date(String previous_Bill_Date) {
		Previous_Bill_Date = previous_Bill_Date;
	}
	public double getCreditBF() {
		return CreditBF;
	}
	public void setCreditBF(double creditBF) {
		CreditBF = creditBF;
	}
	public double getDebitBF() {
		return DebitBF;
	}
	public void setDebitBF(double debitBF) {
		DebitBF = debitBF;
	}
	public String getDue_Date() {
		return Due_Date;
	}
	public void setDue_Date(String due_Date) {
		Due_Date = due_Date;
	}
	public String getIvrsId() {
		return IvrsId;
	}
	public void setIvrsId(String ivrsId) {
		IvrsId = ivrsId;
	}
	public double getPartFraction() {
		return partFraction;
	}
	public void setPartFraction(double partFraction) {
		this.partFraction = partFraction;
	}
	public String getTax_flag() {
		return tax_flag;
	}
	public void setTax_flag(String tax_flag) {
		this.tax_flag = tax_flag;
	}
	public String getPrev_rdg_date() {
		return prev_rdg_date;
	}
	public void setPrev_rdg_date(String prev_rdg_date) {
		this.prev_rdg_date = prev_rdg_date;
	}
	public long getPrst_Rdg() {
		return Prst_Rdg;
	}
	public void setPrst_Rdg(long prst_Rdg) {
		Prst_Rdg = prst_Rdg;
	}
	public String getMtr_Sts() {
		return Mtr_Sts;
	}
	public void setMtr_Sts(String mtr_Sts) {
		Mtr_Sts = mtr_Sts;
	}
	public long getnUnitsConsumed() {
		return nUnitsConsumed;
	}
	public void setnUnitsConsumed(long nUnitsConsumed) {
		this.nUnitsConsumed = nUnitsConsumed;
	}
	public double getnTax() {
		return nTax;
	}
	public void setnTax(double nTax) {
		this.nTax = nTax;
	}
	public double getTemp_Tax() {
		return temp_Tax;
	}
	public void setTemp_Tax(double temp_Tax) {
		this.temp_Tax = temp_Tax;
	}
	public double getTotalBill() {
		return TotalBill;
	}
	public void setTotalBill(double totalBill) {
		TotalBill = totalBill;
	}
	public double getnRebate() {
		return nRebate;
	}
	public void setnRebate(double nRebate) {
		this.nRebate = nRebate;
	}
	public double getnTotalFixedTariff() {
		return nTotalFixedTariff;
	}
	public void setnTotalFixedTariff(double nTotalFixedTariff) {
		this.nTotalFixedTariff = nTotalFixedTariff;
	}
	public double getP_nTotalEnergyTariff() {
		return p_nTotalEnergyTariff;
	}
	public void setP_nTotalEnergyTariff(double p_nTotalEnergyTariff) {
		this.p_nTotalEnergyTariff = p_nTotalEnergyTariff;
	}
	public double getCreditCF() {
		return CreditCF;
	}
	public void setCreditCF(double creditCF) {
		CreditCF = creditCF;
	}
	public int getNumDL() {
		return numDL;
	}
	public void setNumDL(int numDL) {
		this.numDL = numDL;
	}
	public String getDC_Flg() {
		return DC_Flg;
	}
	public void setDC_Flg(String dC_Flg) {
		DC_Flg = dC_Flg;
	}
	public double getInt_Arrears2() {
		return int_Arrears2;
	}
	public void setInt_Arrears2(double int_Arrears2) {
		this.int_Arrears2 = int_Arrears2;
	}
	public double getInt_Tax() {
		return int_Tax;
	}
	public void setInt_Tax(double int_Tax) {
		this.int_Tax = int_Tax;
	}
	public String getFirst_Rdg_Flg() {
		return First_Rdg_Flg;
	}
	public void setFirst_Rdg_Flg(String first_Rdg_Flg) {
		First_Rdg_Flg = first_Rdg_Flg;
	}
	public int getCreaping_Perc() {
		return Creaping_Perc;
	}
	public void setCreaping_Perc(int creaping_Perc) {
		Creaping_Perc = creaping_Perc;
	}
	public String getMNR_Flg() {
		return MNR_Flg;
	}
	public void setMNR_Flg(String mNR_Flg) {
		MNR_Flg = mNR_Flg;
	}
	public long getOld_Mtr_Rdg() {
		return Old_Mtr_Rdg;
	}
	public void setOld_Mtr_Rdg(long old_Mtr_Rdg) {
		Old_Mtr_Rdg = old_Mtr_Rdg;
	}
	public double getNo_tax_comp() {
		return no_tax_comp;
	}
	public void setNo_tax_comp(double no_tax_comp) {
		this.no_tax_comp = no_tax_comp;
	}
	public String getPrev_rdg_flg() {
		return prev_rdg_flg;
	}
	public void setPrev_rdg_flg(String prev_rdg_flg) {
		this.prev_rdg_flg = prev_rdg_flg;
	}
	public String getPLFlag() {
		return PLFlag;
	}
	public void setPLFlag(String pLFlag) {
		PLFlag = pLFlag;
	}
	public double getPLRebate() {
		return PLRebate;
	}
	public void setPLRebate(double pLRebate) {
		PLRebate = pLRebate;
	}
	/*public double getRbtMoreClaimed() {
		return RbtMoreClaimed;
	}
	public void setRbtMoreClaimed(double rbtMoreClaimed) {
		RbtMoreClaimed = rbtMoreClaimed;
	}
	public double getRbtLessClaimed() {
		return RbtLessClaimed;
	}
	public void setRbtLessClaimed(double rbtLessClaimed) {
		RbtLessClaimed = rbtLessClaimed;
	}*/
	public double getLess_MoreClaimedint() {
		return Less_MoreClaimedint;
	}
	public void setLess_MoreClaimedint(double less_MoreClaimedint) {
		Less_MoreClaimedint = less_MoreClaimedint;
	}
	public int getTemp_Tariff_Code() {
		return temp_Tariff_Code;
	}
	public void setTemp_Tariff_Code(int temp_Tariff_Code) {
		this.temp_Tariff_Code = temp_Tariff_Code;
	}
	public int getTemp_PLTariff_Code() {
		return temp_PLTariff_Code;
	}
	public void setTemp_PLTariff_Code(int temp_PLTariff_Code) {
		this.temp_PLTariff_Code = temp_PLTariff_Code;
	}
	public double getDiffAmt() {
		return DiffAmt;
	}
	public void setDiffAmt(double diffAmt) {
		DiffAmt = diffAmt;
	}
	public String getCGexempt_flg() {
		return CGexempt_flg;
	}
	public void setCGexempt_flg(String cGexempt_flg) {
		CGexempt_flg = cGexempt_flg;
	}
	public int getTempKW_Round() {
		return tempKW_Round;
	}
	public void setTempKW_Round(int tempKW_Round) {
		this.tempKW_Round = tempKW_Round;
	}
	/*public double getEnergyamtplustax() {
		return energyamtplustax;
	}
	public void setEnergyamtplustax(double energyamtplustax) {
		this.energyamtplustax = energyamtplustax;
	}*/
	public double getLessclaimed() {
		return lessclaimed;
	}
	public void setLessclaimed(double lessclaimed) {
		this.lessclaimed = lessclaimed;
	}
	public double getMoreclaimed() {
		return moreclaimed;
	}
	public void setMoreclaimed(double moreclaimed) {
		this.moreclaimed = moreclaimed;
	}
	public double getInt_on_tax() {
		return int_on_tax;
	}
	public void setInt_on_tax(double int_on_tax) {
		this.int_on_tax = int_on_tax;
	}
	public String getSubdiv() {
		return subdiv;
	}
	public void setSubdiv(String subdiv) {
		this.subdiv = subdiv;
	}
	public double getPrevCKWH() {
		return PrevCKWH;
	}
	public void setPrevCKWH(double prevCKWH) {
		PrevCKWH = prevCKWH;
	}
	public double getPrstCKWH() {
		return PrstCKWH;
	}
	public void setPrstCKWH(double prstCKWH) {
		PrstCKWH = prstCKWH;
	}
	public double getReg_penalty() {
		return reg_penalty;
	}
	public void setReg_penalty(double reg_penalty) {
		this.reg_penalty = reg_penalty;
	}
	public double getPFPenaltyamt() {
		return PFPenaltyamt;
	}
	public void setPFPenaltyamt(double pFPenaltyamt) {
		PFPenaltyamt = pFPenaltyamt;
	}
	public String getZygox() {
		return zygox;
	}
	public void setZygox(String zygox) {
		this.zygox = zygox;
	}
	public double getPHRebate() {
		return PHRebate;
	}
	public void setPHRebate(double pHRebate) {
		PHRebate = pHRebate;
	}
	public String getPf_flag() {
		return pf_flag;
	}
	public void setPf_flag(String pf_flag) {
		this.pf_flag = pf_flag;
	}
	public double getTemp_total() {
		return temp_total;
	}
	public void setTemp_total(double temp_total) {
		this.temp_total = temp_total;
	}
	public int getLastmonthfraction() {
		return lastmonthfraction;
	}
	public void setLastmonthfraction(int lastmonthfraction) {
		this.lastmonthfraction = lastmonthfraction;
	}
	public String getFirstflag() {
		return firstflag;
	}
	public void setFirstflag(String firstflag) {
		this.firstflag = firstflag;
	}
	public String getFullmonflag() {
		return fullmonflag;
	}
	public void setFullmonflag(String fullmonflag) {
		this.fullmonflag = fullmonflag;
	}
	public int getCur_qrtr() {
		return cur_qrtr;
	}
	public void setCur_qrtr(int cur_qrtr) {
		this.cur_qrtr = cur_qrtr;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public double getAnnual_Min_Fix() {
		return Annual_Min_Fix;
	}
	public void setAnnual_Min_Fix(double annual_Min_Fix) {
		Annual_Min_Fix = annual_Min_Fix;
	}
	public double getHP_Min_Fix() {
		return HP_Min_Fix;
	}
	public void setHP_Min_Fix(double hP_Min_Fix) {
		HP_Min_Fix = hP_Min_Fix;
	}
	public double getLt4debit() {
		return lt4debit;
	}
	public void setLt4debit(double lt4debit) {
		this.lt4debit = lt4debit;
	}
	public double getRR_Rebate() {
		return RR_Rebate;
	}
	public void setRR_Rebate(double rR_Rebate) {
		RR_Rebate = rR_Rebate;
	}
	public double getCapacitor() {
		return Capacitor;
	}
	public void setCapacitor(double capacitor) {
		Capacitor = capacitor;
	}
	public double getCapRbtAmt() {
		return CapRbtAmt;
	}
	public void setCapRbtAmt(double capRbtAmt) {
		CapRbtAmt = capRbtAmt;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public String getCheqd() {
		return cheqd;
	}
	public void setCheqd(String cheqd) {
		this.cheqd = cheqd;
	}
	public double getOrphnRbt() {
		return OrphnRbt;
	}
	public void setOrphnRbt(double orphnRbt) {
		OrphnRbt = orphnRbt;
	}
	public double getOrphnAmt() {
		return OrphnAmt;
	}
	public void setOrphnAmt(double orphnAmt) {
		OrphnAmt = orphnAmt;
	}
	/*public String getDtbcd() {
		return dtbcd;
	}
	public void setDtbcd(String dtbcd) {
		this.dtbcd = dtbcd;
	}
	public String getDuprint() {
		return duprint;
	}
	public void setDuprint(String duprint) {
		this.duprint = duprint;
	}*/
	public double getMmdcredit() {
		return mmdcredit;
	}
	public void setMmdcredit(double mmdcredit) {
		this.mmdcredit = mmdcredit;
	}
	public String getMtr_chng_flg() {
		return mtr_chng_flg;
	}
	public void setMtr_chng_flg(String mtr_chng_flg) {
		this.mtr_chng_flg = mtr_chng_flg;
	}
	public double getAdd3mmd_dep() {
		return add3mmd_dep;
	}
	public void setAdd3mmd_dep(double add3mmd_dep) {
		this.add3mmd_dep = add3mmd_dep;
	}
	public long getPrevConsmp() {
		return PrevConsmp;
	}
	public void setPrevConsmp(long prevConsmp) {
		PrevConsmp = prevConsmp;
	}
	/*public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}*/
	public String getBillPrinted() {
		return billPrinted;
	}
	public void setBillPrinted(String billPrinted) {
		this.billPrinted = billPrinted;
	}
	public FlagsBO getFlagsBO() {
		return flagsBO;
	}
	public void setFlagsBO(FlagsBO flagsBO) {
		this.flagsBO = flagsBO;
	}
	
	public ArrayList<FixedChargeList> getFixedChargeListBO() {
		return fixedChargeListBO;
	}
	public void setFixedChargeListBO(ArrayList<FixedChargeList> fixedChargeListBO) {
		this.fixedChargeListBO = fixedChargeListBO;
	}
	public double getEnergyamtplustax() {
		return energyamtplustax;
	}
	public void setEnergyamtplustax(double energyamtplustax) {
		this.energyamtplustax = energyamtplustax;
	}
	public String getUPLOADEDTOSERVER() {
		return UPLOADEDTOSERVER;
	}
	public void setUPLOADEDTOSERVER(String uPLOADEDTOSERVER) {
		UPLOADEDTOSERVER = uPLOADEDTOSERVER;
	}
	public int getRecordIndex() {
		return RecordIndex;
	}
	public void setRecordIndex(int recordIndex) {
		RecordIndex = recordIndex;
	}
	public double getPrevious_Bill_Amnt() {
		return Previous_Bill_Amnt;
	}
	public void setPrevious_Bill_Amnt(double previous_Bill_Amnt) {
		Previous_Bill_Amnt = previous_Bill_Amnt;
	}
	public double getRecordedBMD() {
		return RecordedBMD;
	}
	public void setRecordedBMD(double recordedBMD) {
		RecordedBMD = recordedBMD;
	}
	public double getLt1_rbt_rate() {
		return lt1_rbt_rate;
	}
	public void setLt1_rbt_rate(double lt1_rbt_rate) {
		this.lt1_rbt_rate = lt1_rbt_rate;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public ArrayList<EnergyChargeList> getEnergyChargeListBO() {
		return energyChargeListBO;
	}
	public void setEnergyChargeListBO(ArrayList<EnergyChargeList> energyChargeListBO) {
		this.energyChargeListBO = energyChargeListBO;
	}
	public double getWeekMinAmt() {
		return weekMinAmt;
	}
	public void setWeekMinAmt(double weekMinAmt) {
		this.weekMinAmt = weekMinAmt;
	}
	public double getWeekadjamt() {
		return weekadjamt;
	}
	public void setWeekadjamt(double weekadjamt) {
		this.weekadjamt = weekadjamt;
	}
	public double getFlRebate() {
		return flRebate;
	}
	public void setFlRebate(double flRebate) {
		this.flRebate = flRebate;
	}
	public double getRecordedBKWH() {
		return recordedBKWH;
	}
	public void setRecordedBKWH(double recordedBKWH) {
		this.recordedBKWH = recordedBKWH;
	}
	public int getP_nComputedInfoCounter() {
		return p_nComputedInfoCounter;
	}
	public void setP_nComputedInfoCounter(int p_nComputedInfoCounter) {
		this.p_nComputedInfoCounter = p_nComputedInfoCounter;
	}
	public int getP_nCompFixInfoCounter() {
		return p_nCompFixInfoCounter;
	}
	public void setP_nCompFixInfoCounter(int p_nCompFixInfoCounter) {
		this.p_nCompFixInfoCounter = p_nCompFixInfoCounter;
	}
	public int getDGPCompInfoCounter() {
		return DGPCompInfoCounter;
	}
	public void setDGPCompInfoCounter(int dGPCompInfoCounter) {
		DGPCompInfoCounter = dGPCompInfoCounter;
	}
	public double getPenaltyonexcessload() {
		return penaltyonexcessload;
	}
	public void setPenaltyonexcessload(double penaltyonexcessload) {
		this.penaltyonexcessload = penaltyonexcessload;
	}
	
	

}
