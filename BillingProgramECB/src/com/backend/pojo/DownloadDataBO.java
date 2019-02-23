package com.backend.pojo;

import java.io.Serializable;

public class DownloadDataBO implements Serializable{
	
	String mdd_loc_cd;
	String mdd_rr_no;
	String mdd_ldgr_no;
	String mdd_actl_folio_no;
	String mdd_spot_folio_no;
	String mdd_trf_code;
	String mdd_bill_dt;
	String mdd_consmr_name;
	String mdd_addr1;
	String mdd_addr2;
	String mdd_addr3;
	String mdd_billing_month;
	String mdd_rdg_dt;
	String mdd_mtr_rdr_cd;
	int mdd_instal_sts;
	double mdd_line_min;
	float mdd_sanct_hp;
	float mdd_sanct_kw;
	double mdd_ct_ratio;
	long mdd_prev_rdg;
	double mdd_avg_consmp;
	char mdd_solar_rebate;
	char mdd_fl_rebate;
	char mdd_ph_rebate;
	char mdd_telep_rebate;
	double mdd_pwr_factor;
	String mdd_mtrchng_dt1;
	double mdd_mtrchng_rdg1;
	String mdd_mtrchn_gdt2;
	double mdd_mtrchn_grdg2;
	double mdd_bill_amt;
	double mdd_demand_arrears;
	double mdd_int_arrears;
	double mdd_tax_arrears;
	double mdd_delay_int;
	double mdd_amt_paid1;
	String mdd_paid_date1;
	double mdd_amt_paid2;
	String mdd_paid_date2;
	double mdd_others;
	char mdd_bill_gen_flag;
	char mdd_meter;
	char mdd_rebate_cap;
	double mdd_previous_demand1;
	double mdd_previous_demand2;
	double mdd_previous_demand3;
	String mdd_billing_mode;
	String mdd_mtr_const;
	char mdd_demand_based;
	char mdd_rural_rebate;
	char mdd_normal;
	double mdd_appeal_amount;
	double mdd_int_on_appeal_amt;
	double mdd_kvah;
	char mdd_meter_tvm;
	char mdd_inst_typ;
	double mdd_p_credit;
	double mdd_p_debit;
	String mdd_due_date;
	String mdd_dlmnr;
	int  mdd_bill_num;
	double mdd_delay_arrs;
	double mdd_int_on_tax;
	String mdd_ivrs_id;
	char mdd_ecs_flg;
	double mdd_part_period;
	String mdd_user;
	String mdd_tmpstp;
	double mdd_diffamt;
	char mdd_tax_flg;  
	char mdd_DC_Flg;
	double mdd_Int_Arrears2;
	double mdd_Int_Tax;
	char mdd_First_Rdg_Flg;
	int mdd_Creaping_Perc;
	char mdd_MNR_Flg;
	int mdd_Old_Mtr_Rdg;
	double mdd_RecordedBMD;
	double mdd_PrstCKWH;
	double mdd_PrevCKWH;
	char mdd_PLFlag;
	double mdd_no_tax_comp;
	char mdd_prev_rdg_flg;
	char mdd_pf_flag;
	double mdd_reg_penalty;
	int mdd_lastmonthfraction;
	double mdd_Annual_Min_Fix;
	double mdd_capacity_rbt; 
	int mdd_cur_qrtr;
	double mdd_OrphnRbt;
	char mdd_mtr_chng_flg;
	double mdd_lt1_rbt;
	public String getMdd_loc_cd() {
		return mdd_loc_cd;
	}
	public void setMdd_loc_cd(String mdd_loc_cd) {
		this.mdd_loc_cd = mdd_loc_cd;
	}
	public String getMdd_rr_no() {
		return mdd_rr_no;
	}
	public void setMdd_rr_no(String mdd_rr_no) {
		this.mdd_rr_no = mdd_rr_no;
	}
	public String getMdd_ldgr_no() {
		return mdd_ldgr_no;
	}
	public void setMdd_ldgr_no(String mdd_ldgr_no) {
		this.mdd_ldgr_no = mdd_ldgr_no;
	}
	public String getMdd_actl_folio_no() {
		return mdd_actl_folio_no;
	}
	public void setMdd_actl_folio_no(String mdd_actl_folio_no) {
		this.mdd_actl_folio_no = mdd_actl_folio_no;
	}
	public String getMdd_spot_folio_no() {
		return mdd_spot_folio_no;
	}
	public void setMdd_spot_folio_no(String mdd_spot_folio_no) {
		this.mdd_spot_folio_no = mdd_spot_folio_no;
	}
	
	public String getMdd_bill_dt() {
		return mdd_bill_dt;
	}
	public void setMdd_bill_dt(String mdd_bill_dt) {
		this.mdd_bill_dt = mdd_bill_dt;
	}
	public String getMdd_consmr_name() {
		return mdd_consmr_name;
	}
	public void setMdd_consmr_name(String mdd_consmr_name) {
		this.mdd_consmr_name = mdd_consmr_name;
	}
	public String getMdd_addr1() {
		return mdd_addr1;
	}
	public void setMdd_addr1(String mdd_addr1) {
		this.mdd_addr1 = mdd_addr1;
	}
	public String getMdd_addr2() {
		return mdd_addr2;
	}
	public void setMdd_addr2(String mdd_addr2) {
		this.mdd_addr2 = mdd_addr2;
	}
	public String getMdd_addr3() {
		return mdd_addr3;
	}
	public void setMdd_addr3(String mdd_addr3) {
		this.mdd_addr3 = mdd_addr3;
	}
	public String getMdd_billing_month() {
		return mdd_billing_month;
	}
	public void setMdd_billing_month(String mdd_billing_month) {
		this.mdd_billing_month = mdd_billing_month;
	}
	public String getMdd_rdg_dt() {
		return mdd_rdg_dt;
	}
	public void setMdd_rdg_dt(String mdd_rdg_dt) {
		this.mdd_rdg_dt = mdd_rdg_dt;
	}
	public String getMdd_mtr_rdr_cd() {
		return mdd_mtr_rdr_cd;
	}
	public void setMdd_mtr_rdr_cd(String mdd_mtr_rdr_cd) {
		this.mdd_mtr_rdr_cd = mdd_mtr_rdr_cd;
	}
	public int getMdd_instal_sts() {
		return mdd_instal_sts;
	}
	public void setMdd_instal_sts(int mdd_instal_sts) {
		this.mdd_instal_sts = mdd_instal_sts;
	}
	public double getMdd_line_min() {
		return mdd_line_min;
	}
	public void setMdd_line_min(double mdd_line_min) {
		this.mdd_line_min = mdd_line_min;
	}
	public float getMdd_sanct_hp() {
		return mdd_sanct_hp;
	}
	public void setMdd_sanct_hp(float mdd_sanct_hp) {
		this.mdd_sanct_hp = mdd_sanct_hp;
	}
	public float getMdd_sanct_kw() {
		return mdd_sanct_kw;
	}
	public void setMdd_sanct_kw(float mdd_sanct_kw) {
		this.mdd_sanct_kw = mdd_sanct_kw;
	}
	public double getMdd_ct_ratio() {
		return mdd_ct_ratio;
	}
	public void setMdd_ct_ratio(double mdd_ct_ratio) {
		this.mdd_ct_ratio = mdd_ct_ratio;
	}
	public long getMdd_prev_rdg() {
		return mdd_prev_rdg;
	}
	public void setMdd_prev_rdg(long mdd_prev_rdg) {
		this.mdd_prev_rdg = mdd_prev_rdg;
	}
	public double getMdd_avg_consmp() {
		return mdd_avg_consmp;
	}
	public void setMdd_avg_consmp(double mdd_avg_consmp) {
		this.mdd_avg_consmp = mdd_avg_consmp;
	}
	public char getMdd_solar_rebate() {
		return mdd_solar_rebate;
	}
	public void setMdd_solar_rebate(char mdd_solar_rebate) {
		this.mdd_solar_rebate = mdd_solar_rebate;
	}
	public char getMdd_fl_rebate() {
		return mdd_fl_rebate;
	}
	public void setMdd_fl_rebate(char mdd_fl_rebate) {
		this.mdd_fl_rebate = mdd_fl_rebate;
	}
	public char getMdd_ph_rebate() {
		return mdd_ph_rebate;
	}
	public void setMdd_ph_rebate(char mdd_ph_rebate) {
		this.mdd_ph_rebate = mdd_ph_rebate;
	}
	public char getMdd_telep_rebate() {
		return mdd_telep_rebate;
	}
	public void setMdd_telep_rebate(char mdd_telep_rebate) {
		this.mdd_telep_rebate = mdd_telep_rebate;
	}
	public double getMdd_pwr_factor() {
		return mdd_pwr_factor;
	}
	public void setMdd_pwr_factor(double mdd_pwr_factor) {
		this.mdd_pwr_factor = mdd_pwr_factor;
	}
	public String getMdd_mtrchng_dt1() {
		return mdd_mtrchng_dt1;
	}
	public void setMdd_mtrchng_dt1(String mdd_mtrchng_dt1) {
		this.mdd_mtrchng_dt1 = mdd_mtrchng_dt1;
	}
	public double getMdd_mtrchng_rdg1() {
		return mdd_mtrchng_rdg1;
	}
	public void setMdd_mtrchng_rdg1(double mdd_mtrchng_rdg1) {
		this.mdd_mtrchng_rdg1 = mdd_mtrchng_rdg1;
	}
	public String getMdd_mtrchn_gdt2() {
		return mdd_mtrchn_gdt2;
	}
	public void setMdd_mtrchn_gdt2(String mdd_mtrchn_gdt2) {
		this.mdd_mtrchn_gdt2 = mdd_mtrchn_gdt2;
	}
	public double getMdd_mtrchn_grdg2() {
		return mdd_mtrchn_grdg2;
	}
	public void setMdd_mtrchn_grdg2(double mdd_mtrchn_grdg2) {
		this.mdd_mtrchn_grdg2 = mdd_mtrchn_grdg2;
	}
	public double getMdd_bill_amt() {
		return mdd_bill_amt;
	}
	public void setMdd_bill_amt(double mdd_bill_amt) {
		this.mdd_bill_amt = mdd_bill_amt;
	}
	public double getMdd_demand_arrears() {
		return mdd_demand_arrears;
	}
	public void setMdd_demand_arrears(double mdd_demand_arrears) {
		this.mdd_demand_arrears = mdd_demand_arrears;
	}
	public double getMdd_int_arrears() {
		return mdd_int_arrears;
	}
	public void setMdd_int_arrears(double mdd_int_arrears) {
		this.mdd_int_arrears = mdd_int_arrears;
	}
	public double getMdd_tax_arrears() {
		return mdd_tax_arrears;
	}
	public void setMdd_tax_arrears(double mdd_tax_arrears) {
		this.mdd_tax_arrears = mdd_tax_arrears;
	}
	public double getMdd_delay_int() {
		return mdd_delay_int;
	}
	public void setMdd_delay_int(double mdd_delay_int) {
		this.mdd_delay_int = mdd_delay_int;
	}
	public double getMdd_amt_paid1() {
		return mdd_amt_paid1;
	}
	public void setMdd_amt_paid1(double mdd_amt_paid1) {
		this.mdd_amt_paid1 = mdd_amt_paid1;
	}
	public String getMdd_paid_date1() {
		return mdd_paid_date1;
	}
	public void setMdd_paid_date1(String mdd_paid_date1) {
		this.mdd_paid_date1 = mdd_paid_date1;
	}
	public double getMdd_amt_paid2() {
		return mdd_amt_paid2;
	}
	public void setMdd_amt_paid2(double mdd_amt_paid2) {
		this.mdd_amt_paid2 = mdd_amt_paid2;
	}
	public String getMdd_paid_date2() {
		return mdd_paid_date2;
	}
	public void setMdd_paid_date2(String mdd_paid_date2) {
		this.mdd_paid_date2 = mdd_paid_date2;
	}
	public double getMdd_others() {
		return mdd_others;
	}
	public void setMdd_others(double mdd_others) {
		this.mdd_others = mdd_others;
	}
	public char getMdd_bill_gen_flag() {
		return mdd_bill_gen_flag;
	}
	public void setMdd_bill_gen_flag(char mdd_bill_gen_flag) {
		this.mdd_bill_gen_flag = mdd_bill_gen_flag;
	}
	public char getMdd_meter() {
		return mdd_meter;
	}
	public void setMdd_meter(char mdd_meter) {
		this.mdd_meter = mdd_meter;
	}
	public char getMdd_rebate_cap() {
		return mdd_rebate_cap;
	}
	public void setMdd_rebate_cap(char mdd_rebate_cap) {
		this.mdd_rebate_cap = mdd_rebate_cap;
	}
	public double getMdd_previous_demand1() {
		return mdd_previous_demand1;
	}
	public void setMdd_previous_demand1(double mdd_previous_demand1) {
		this.mdd_previous_demand1 = mdd_previous_demand1;
	}
	public double getMdd_previous_demand2() {
		return mdd_previous_demand2;
	}
	public void setMdd_previous_demand2(double mdd_previous_demand2) {
		this.mdd_previous_demand2 = mdd_previous_demand2;
	}
	public double getMdd_previous_demand3() {
		return mdd_previous_demand3;
	}
	public void setMdd_previous_demand3(double mdd_previous_demand3) {
		this.mdd_previous_demand3 = mdd_previous_demand3;
	}
	public String getMdd_billing_mode() {
		return mdd_billing_mode;
	}
	public void setMdd_billing_mode(String mdd_billing_mode) {
		this.mdd_billing_mode = mdd_billing_mode;
	}
	public String getMdd_mtr_const() {
		return mdd_mtr_const;
	}
	public void setMdd_mtr_const(String mdd_mtr_const) {
		this.mdd_mtr_const = mdd_mtr_const;
	}
	public char getMdd_demand_based() {
		return mdd_demand_based;
	}
	public void setMdd_demand_based(char mdd_demand_based) {
		this.mdd_demand_based = mdd_demand_based;
	}
	public char getMdd_rural_rebate() {
		return mdd_rural_rebate;
	}
	public void setMdd_rural_rebate(char mdd_rural_rebate) {
		this.mdd_rural_rebate = mdd_rural_rebate;
	}
	public char getMdd_normal() {
		return mdd_normal;
	}
	public void setMdd_normal(char mdd_normal) {
		this.mdd_normal = mdd_normal;
	}
	public double getMdd_appeal_amount() {
		return mdd_appeal_amount;
	}
	public void setMdd_appeal_amount(double mdd_appeal_amount) {
		this.mdd_appeal_amount = mdd_appeal_amount;
	}
	public double getMdd_int_on_appeal_amt() {
		return mdd_int_on_appeal_amt;
	}
	public void setMdd_int_on_appeal_amt(double mdd_int_on_appeal_amt) {
		this.mdd_int_on_appeal_amt = mdd_int_on_appeal_amt;
	}
	public double getMdd_kvah() {
		return mdd_kvah;
	}
	public void setMdd_kvah(double mdd_kvah) {
		this.mdd_kvah = mdd_kvah;
	}
	public char getMdd_meter_tvm() {
		return mdd_meter_tvm;
	}
	public void setMdd_meter_tvm(char mdd_meter_tvm) {
		this.mdd_meter_tvm = mdd_meter_tvm;
	}
	public char getMdd_inst_typ() {
		return mdd_inst_typ;
	}
	public void setMdd_inst_typ(char mdd_inst_typ) {
		this.mdd_inst_typ = mdd_inst_typ;
	}
	public double getMdd_p_credit() {
		return mdd_p_credit;
	}
	public void setMdd_p_credit(double mdd_p_credit) {
		this.mdd_p_credit = mdd_p_credit;
	}
	public double getMdd_p_debit() {
		return mdd_p_debit;
	}
	public void setMdd_p_debit(double mdd_p_debit) {
		this.mdd_p_debit = mdd_p_debit;
	}
	public String getMdd_due_date() {
		return mdd_due_date;
	}
	public void setMdd_due_date(String mdd_due_date) {
		this.mdd_due_date = mdd_due_date;
	}
	public String getMdd_dlmnr() {
		return mdd_dlmnr;
	}
	public void setMdd_dlmnr(String mdd_dlmnr) {
		this.mdd_dlmnr = mdd_dlmnr;
	}
	public int getMdd_bill_num() {
		return mdd_bill_num;
	}
	public void setMdd_bill_num(int mdd_bill_num) {
		this.mdd_bill_num = mdd_bill_num;
	}
	public double getMdd_delay_arrs() {
		return mdd_delay_arrs;
	}
	public void setMdd_delay_arrs(double mdd_delay_arrs) {
		this.mdd_delay_arrs = mdd_delay_arrs;
	}
	public double getMdd_int_on_tax() {
		return mdd_int_on_tax;
	}
	public void setMdd_int_on_tax(double mdd_int_on_tax) {
		this.mdd_int_on_tax = mdd_int_on_tax;
	}
	public String getMdd_ivrs_id() {
		return mdd_ivrs_id;
	}
	public void setMdd_ivrs_id(String mdd_ivrs_id) {
		this.mdd_ivrs_id = mdd_ivrs_id;
	}
	public char getMdd_ecs_flg() {
		return mdd_ecs_flg;
	}
	public void setMdd_ecs_flg(char mdd_ecs_flg) {
		this.mdd_ecs_flg = mdd_ecs_flg;
	}
	public double getMdd_part_period() {
		return mdd_part_period;
	}
	public void setMdd_part_period(double mdd_part_period) {
		this.mdd_part_period = mdd_part_period;
	}
	public String getMdd_user() {
		return mdd_user;
	}
	public void setMdd_user(String mdd_user) {
		this.mdd_user = mdd_user;
	}
	public String getMdd_tmpstp() {
		return mdd_tmpstp;
	}
	public void setMdd_tmpstp(String mdd_tmpstp) {
		this.mdd_tmpstp = mdd_tmpstp;
	}
	public double getMdd_diffamt() {
		return mdd_diffamt;
	}
	public void setMdd_diffamt(double mdd_diffamt) {
		this.mdd_diffamt = mdd_diffamt;
	}
	public char getMdd_tax_flg() {
		return mdd_tax_flg;
	}
	public void setMdd_tax_flg(char mdd_tax_flg) {
		this.mdd_tax_flg = mdd_tax_flg;
	}
	public char getMdd_DC_Flg() {
		return mdd_DC_Flg;
	}
	public void setMdd_DC_Flg(char mdd_DC_Flg) {
		this.mdd_DC_Flg = mdd_DC_Flg;
	}
	public double getMdd_Int_Arrears2() {
		return mdd_Int_Arrears2;
	}
	public void setMdd_Int_Arrears2(double mdd_Int_Arrears2) {
		this.mdd_Int_Arrears2 = mdd_Int_Arrears2;
	}
	public double getMdd_Int_Tax() {
		return mdd_Int_Tax;
	}
	public void setMdd_Int_Tax(double mdd_Int_Tax) {
		this.mdd_Int_Tax = mdd_Int_Tax;
	}
	public char getMdd_First_Rdg_Flg() {
		return mdd_First_Rdg_Flg;
	}
	public void setMdd_First_Rdg_Flg(char mdd_First_Rdg_Flg) {
		this.mdd_First_Rdg_Flg = mdd_First_Rdg_Flg;
	}
	public int getMdd_Creaping_Perc() {
		return mdd_Creaping_Perc;
	}
	public void setMdd_Creaping_Perc(int mdd_Creaping_Perc) {
		this.mdd_Creaping_Perc = mdd_Creaping_Perc;
	}
	public char getMdd_MNR_Flg() {
		return mdd_MNR_Flg;
	}
	public void setMdd_MNR_Flg(char mdd_MNR_Flg) {
		this.mdd_MNR_Flg = mdd_MNR_Flg;
	}
	public int getMdd_Old_Mtr_Rdg() {
		return mdd_Old_Mtr_Rdg;
	}
	public void setMdd_Old_Mtr_Rdg(int mdd_Old_Mtr_Rdg) {
		this.mdd_Old_Mtr_Rdg = mdd_Old_Mtr_Rdg;
	}
	public double getMdd_RecordedBMD() {
		return mdd_RecordedBMD;
	}
	public void setMdd_RecordedBMD(double mdd_RecordedBMD) {
		this.mdd_RecordedBMD = mdd_RecordedBMD;
	}
	public double getMdd_PrstCKWH() {
		return mdd_PrstCKWH;
	}
	public void setMdd_PrstCKWH(double mdd_PrstCKWH) {
		this.mdd_PrstCKWH = mdd_PrstCKWH;
	}
	public double getMdd_PrevCKWH() {
		return mdd_PrevCKWH;
	}
	public void setMdd_PrevCKWH(double mdd_PrevCKWH) {
		this.mdd_PrevCKWH = mdd_PrevCKWH;
	}
	public char getMdd_PLFlag() {
		return mdd_PLFlag;
	}
	public void setMdd_PLFlag(char mdd_PLFlag) {
		this.mdd_PLFlag = mdd_PLFlag;
	}
	public double getMdd_no_tax_comp() {
		return mdd_no_tax_comp;
	}
	public void setMdd_no_tax_comp(double mdd_no_tax_comp) {
		this.mdd_no_tax_comp = mdd_no_tax_comp;
	}
	public char getMdd_prev_rdg_flg() {
		return mdd_prev_rdg_flg;
	}
	public void setMdd_prev_rdg_flg(char mdd_prev_rdg_flg) {
		this.mdd_prev_rdg_flg = mdd_prev_rdg_flg;
	}
	public char getMdd_pf_flag() {
		return mdd_pf_flag;
	}
	public void setMdd_pf_flag(char mdd_pf_flag) {
		this.mdd_pf_flag = mdd_pf_flag;
	}
	public double getMdd_reg_penalty() {
		return mdd_reg_penalty;
	}
	public void setMdd_reg_penalty(double mdd_reg_penalty) {
		this.mdd_reg_penalty = mdd_reg_penalty;
	}
	public int getMdd_lastmonthfraction() {
		return mdd_lastmonthfraction;
	}
	public void setMdd_lastmonthfraction(int mdd_lastmonthfraction) {
		this.mdd_lastmonthfraction = mdd_lastmonthfraction;
	}
	public double getMdd_Annual_Min_Fix() {
		return mdd_Annual_Min_Fix;
	}
	public void setMdd_Annual_Min_Fix(double mdd_Annual_Min_Fix) {
		this.mdd_Annual_Min_Fix = mdd_Annual_Min_Fix;
	}
	public double getMdd_capacity_rbt() {
		return mdd_capacity_rbt;
	}
	public void setMdd_capacity_rbt(double mdd_capacity_rbt) {
		this.mdd_capacity_rbt = mdd_capacity_rbt;
	}
	public int getMdd_cur_qrtr() {
		return mdd_cur_qrtr;
	}
	public void setMdd_cur_qrtr(int mdd_cur_qrtr) {
		this.mdd_cur_qrtr = mdd_cur_qrtr;
	}
	public double getMdd_OrphnRbt() {
		return mdd_OrphnRbt;
	}
	public void setMdd_OrphnRbt(double mdd_OrphnRbt) {
		this.mdd_OrphnRbt = mdd_OrphnRbt;
	}
	public char getMdd_mtr_chng_flg() {
		return mdd_mtr_chng_flg;
	}
	public void setMdd_mtr_chng_flg(char mdd_mtr_chng_flg) {
		this.mdd_mtr_chng_flg = mdd_mtr_chng_flg;
	}
	public double getMdd_lt1_rbt() {
		return mdd_lt1_rbt;
	}
	public void setMdd_lt1_rbt(double mdd_lt1_rbt) {
		this.mdd_lt1_rbt = mdd_lt1_rbt;
	}
	public String getMdd_trf_code() {
		return mdd_trf_code;
	}
	public void setMdd_trf_code(String mdd_trf_code) {
		this.mdd_trf_code = mdd_trf_code;
	}
	
	

}
