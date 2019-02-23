package com.backend.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.backend.controller.BillingProgram;
import com.backend.pojo.BillingConstants;
import com.backend.pojo.BillingRecordBO;
import com.backend.pojo.DownloadDataBO;
import com.backend.pojo.FlagsBO;
import com.backend.pojo.RRNumberBO;
import com.backend.pojo.TariffConstant;

public class DownloadUploadDAO implements Serializable{

	public static boolean MakeRrNumberList() {

		RRNumberBO RRnoBO = null;
		Boolean bool = false;
		
		try{
			
			BillingProgram.RRNOList = new ArrayList<RRNumberBO>();
			
			String Query = " SELECT a.HDD_rr_no  HDD_rr_no from HHD_DOWNLOAD_DATA a  " +
					       " WHERE  a.HDD_BILL_DT = to_date('"+BillingProgram.BILL_DATE+"', 'dd/mm/yyyy') " +
					       " AND a.HDD_MTR_RDR_CD = '" +BillingProgram.METER_CODE+"' " + 
					       " AND a.HDD_BILL_GEN_FLAG != 'Y' AND NVL(a.HDD_BILL_FLG, 'N') = 'N' " +
					       " AND NVL(a.HDD_SRVR_HHD, ' ') = 'S'" +
					       "";
			
			System.out.println("RRNOList = "+Query);
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				RRnoBO = new RRNumberBO();
				
				RRnoBO.setRrNo(BillingProgram.rs.getString("HDD_rr_no"));
				RRnoBO.setStatus("N");
				
				System.out.println("RRNO : ------ >"+BillingProgram.rs.getString("HDD_rr_no"));
				
				BillingProgram.RRNOList.add(RRnoBO);
				
				bool = true;
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bool = false;
		}
		
		DatabaseOperations.cleanUp(BillingProgram.ps,BillingProgram.rs);
		
		return bool;
	}
	
	
	public static boolean MakeRrNumberList(String RRno) {

		RRNumberBO RRnoBO = null;
		Boolean bool = false;
		
		try{
			
			BillingProgram.RRNOList = new ArrayList<RRNumberBO>();

			RRnoBO = new RRNumberBO();
				
				RRnoBO.setRrNo(RRno);
				RRnoBO.setStatus("N");
				
				System.out.println("RRNO : ------ >"+RRno);
				
				BillingProgram.RRNOList.add(RRnoBO);
				
				bool = true;
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bool = false;
		}
		
		return bool;
	}


	public static boolean DownloadRecordFromHDD() {
		// TODO Auto-generated method stub
		
		boolean bool = false;
		try{
			
			String Query = "SELECT HDD_loc_cd,HDD_rr_no,nvl(HDD_sanct_kw, 0) HDD_sanct_kw,nvl(HDD_ldgr_no, ' ') HDD_ldgr_no," +
					"nvl(HDD_actl_folio_no, ' ') HDD_actl_folio_no,	nvl(HDD_spt_folio_no, ' ') HDD_spt_folio_no,nvl(HDD_trf_code, '0') HDD_trf_code, " +
					"to_char(HDD_bill_dt, 'dd/mm/yyyy') HDD_bill_dt,HDD_consmr_name,nvl(HDD_addr1, ' ') HDD_addr1,nvl(HDD_addr2, ' ') HDD_addr2," +
					"nvl(HDD_addr3, ' ') HDD_addr3,	HDD_billing_month,to_char(HDD_rdg_dt, 'dd/mm/yyyy') HDD_rdg_dt,	HDD_mtr_rdr_cd," +
					"HDD_instal_sts,HDD_line_min,nvl(HDD_sanct_hp, 0) HDD_sanct_hp,nvl(HDD_ct_ratio, 0) HDD_ct_ratio,nvl(HDD_prev_rdg, 0) HDD_prev_rdg," +
					"nvl(HDD_avg_consmp, 0) HDD_avg_consmp,	nvl(HDD_solar_rebate, 'N') HDD_solar_rebate,nvl(HDD_fl_rebate, 'N') HDD_fl_rebate," +
					"nvl(HDD_ph_rebate, 'N') HDD_ph_rebate,	nvl(HDD_telep_rebate, 'N') HDD_telep_rebate,nvl(HDD_pwr_factor, 0) HDD_pwr_factor," +
					"to_char(HDD_mtrchng_dt1, 'dd/mm/yyyy') HDD_mtrchng_dt1,HDD_mtrchng_rdg1,to_char(HDD_mtrchn_gdt2, 'dd/mm/yyyy') HDD_mtrchn_gdt2," +
					"HDD_mtrchn_grdg2,nvl(HDD_bill_amt, 0) HDD_bill_amt,nvl(HDD_demand_arrears, 0) HDD_demand_arrears,nvl(HDD_int_arrears, 0) HDD_int_arrears," +
					"nvl(HDD_tax_arrears, 0) HDD_tax_arrears,nvl(HDD_delay_int, 0) HDD_delay_int,nvl(HDD_amt_paid1, 0) HDD_amt_paid1," +
					"to_char(HDD_paid_date1, 'dd/mm/yyyy') HDD_paid_date1,nvl(HDD_amt_paid2, 0) HDD_amt_paid2,to_char(HDD_paid_date2, 'dd/mm/yyyy') HDD_paid_date2 ," +
					"nvl(HDD_others, 0) HDD_others,nvl(HDD_bill_gen_flag, 'N') HDD_bill_gen_flag,nvl(HDD_meter, 'N') HDD_meter,	nvl(HDD_rebate_cap, 'N') HDD_rebate_cap," +
					"nvl(HDD_previous_demand1, 0) HDD_previous_demand1,	nvl(HDD_previous_demand2, 0) HDD_previous_demand2,nvl(HDD_previous_demand3, 0) HDD_previous_demand3 ," +
					"nvl(HDD_billing_mode, ' ') HDD_billing_mode,nvl(HDD_mtr_const, ' ') HDD_mtr_const,HDD_demand_based,HDD_rural_rebate,HDD_normal," +
					"nvl(HDD_appeal_amount, 0) HDD_appeal_amount,nvl(HDD_int_on_appeal_amt, 0) HDD_int_on_appeal_amt,nvl(HDD_kvah, 0) HDD_kvah,	HDD_meter_tvm," +
					"HDD_inst_typ,nvl(HDD_p_credit, 0) HDD_p_credit,nvl(HDD_p_debit,0) HDD_p_debit,	to_char(nvl(HDD_due_date, HDD_rdg_dt + 15), 'dd/mm/yyyy') HDD_due_date," +
					"nvl(HDD_dlmnr, '0') HDD_dlmnr,to_char(HDD_bill_num, '09999999') HDD_bill_num,	nvl(HDD_delay_arrs, 0) HDD_delay_arrs,nvl(HDD_int_on_tax, 0) HDD_int_on_tax," +
					"nvl(HDD_ivrs_id, ' ') HDD_ivrs_id,nvl(HDD_ecs_flg, 'N') HDD_ecs_flg,nvl(HDD_part_period, 1) HDD_part_period,nvl(HDD_user, ' ') HDD_user,nvl(HDD_tax_flg,'N') HDD_tax_flg," +
					"nvl(HDD_dc_flg,'N') HDD_dc_flg,nvl(HDD_intrst_arrs,0) HDD_intrst_arrs,	nvl(HDD_intrst_tax,0) HDD_intrst_tax,nvl(HDD_first_rdg_flg,'N') HDD_first_rdg_flg," +
					"nvl(HDD_err_prcnt,0) HDD_err_prcnt,nvl(HDD_mnr_flg,'N') HDD_mnr_flg,nvl(HDD_old_mtr_consmp,0) HDD_old_mtr_consmp,nvl(HDD_BMD,0) HDD_BMD,nvl(HDD_Prsnt_CKWH ,0) HDD_Prsnt_CKWH," +
					"nvl(HDD_Prev_CKWH ,0) HDD_Prev_CKWH,nvl(HDD_prev_rdg_flg,'Y') HDD_prev_rdg_flg,nvl(HDD_no_tax_comp,0) HDD_no_tax_comp,nvl(HDD_pl_flg,'N') HDD_pl_flg," +
					"nvl(HDD_calc_pf_flg, 'N') HDD_calc_pf_flg,	nvl(HDD_reg_penalty,0) HDD_reg_penalty,nvl(HDD_last_dl_days,30) HDD_last_dl_days,nvl(HDD_Annual_Min_Fix,0) HDD_Annual_Min_Fix," +
					"nvl(HDD_capacity_rbt,0) HDD_capacity_rbt,nvl(HDD_cur_qrtr,0) HDD_cur_qrtr,nvl(HDD_orphanage_rebate,0) HDD_orphanage_rebate,nvl(HDD_mc_flg, 'N') HDD_mc_flg," +
					"to_char(HDD_tmpstp, 'dd/mm/yyyy') HDD_tmpstp  " +
					"  FROM HHD_DOWNLOAD_DATA  " +
					"	WHERE HDD_RR_NO = '"+BillingProgram.CURRENT_RRNO+"' " +
				    "   AND HDD_BILL_DT = to_date('"+BillingProgram.BILL_DATE+"' , 'dd/mm/yyyy' ) " ;
			     
			if(BillingProgram.METER_CODE !=  null && BillingProgram.METER_CODE.length() > 0){
				Query = Query + " AND HDD_MTR_RDR_CD =  '"+BillingProgram.METER_CODE+"'  ";
			}
			
			
					Query = Query + "  AND NVL(HDD_SRVR_HHD, ' ')  = 'S' AND NVL(HDD_BILL_GEN_FLAG,'N') != 'Y'  "; 
			//Above line should be uncommented after testing
			
			System.out.println("Qry : "+Query);
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			BillingProgram.DownloadRecordList = new ArrayList<DownloadDataBO>();
			DownloadDataBO download = null;
			
			while(BillingProgram.rs.next()){
				
				download = new DownloadDataBO();
				
				download.setMdd_loc_cd(BillingProgram.rs.getString("HDD_loc_cd")); 
				download.setMdd_rr_no(BillingProgram.rs.getString("HDD_rr_no")); 
				download.setMdd_sanct_kw(BillingProgram.rs.getFloat("HDD_sanct_kw")); 
				download.setMdd_ldgr_no(BillingProgram.rs.getString("HDD_ldgr_no")); 
				download.setMdd_actl_folio_no(BillingProgram.rs.getString("HDD_actl_folio_no")); 
				download.setMdd_spot_folio_no(BillingProgram.rs.getString("HDD_spt_folio_no")); 
				download.setMdd_trf_code(BillingProgram.rs.getString("HDD_trf_code")); 
				download.setMdd_bill_dt(BillingProgram.rs.getString("HDD_bill_dt")); 
				download.setMdd_consmr_name(BillingProgram.rs.getString("HDD_consmr_name")); 
				download.setMdd_addr1(BillingProgram.rs.getString("HDD_addr1")); 
				download.setMdd_addr2(BillingProgram.rs.getString("HDD_addr2")); 
				download.setMdd_addr3(BillingProgram.rs.getString("HDD_addr3")); 
				download.setMdd_billing_month(BillingProgram.rs.getString("HDD_billing_month")); 
				download.setMdd_rdg_dt(BillingProgram.rs.getString("HDD_rdg_dt")); 
				download.setMdd_mtr_rdr_cd(BillingProgram.rs.getString("HDD_mtr_rdr_cd")); 
				
				System.out.println(" BillingProgram.METER_CODE : HDD_mtr_rdr_cd " +BillingProgram.rs.getString("HDD_mtr_rdr_cd"));
				
				if(BillingProgram.METER_CODE == null){
					BillingProgram.METER_CODE = download.getMdd_mtr_rdr_cd();
				}
				
				
				download.setMdd_instal_sts(BillingProgram.rs.getInt("HDD_instal_sts")); 
				download.setMdd_line_min(BillingProgram.rs.getDouble("HDD_line_min")); 
				download.setMdd_sanct_hp(BillingProgram.rs.getFloat("HDD_sanct_hp")); 
				download.setMdd_ct_ratio(BillingProgram.rs.getDouble("HDD_ct_ratio")); 
				download.setMdd_prev_rdg(BillingProgram.rs.getLong("HDD_prev_rdg")); 
				download.setMdd_avg_consmp(BillingProgram.rs.getDouble("HDD_avg_consmp")); 
				download.setMdd_solar_rebate(BillingProgram.rs.getString("HDD_solar_rebate").charAt(0)); 
				download.setMdd_fl_rebate(BillingProgram.rs.getString("HDD_fl_rebate").charAt(0)); 
				download.setMdd_ph_rebate(BillingProgram.rs.getString("HDD_ph_rebate").charAt(0)); 
				download.setMdd_telep_rebate(BillingProgram.rs.getString("HDD_telep_rebate").charAt(0)); 
				download.setMdd_pwr_factor(BillingProgram.rs.getDouble("HDD_pwr_factor")); 
				download.setMdd_mtrchng_dt1(BillingProgram.rs.getString("HDD_mtrchng_dt1")); 
				download.setMdd_mtrchng_rdg1(BillingProgram.rs.getDouble("HDD_mtrchng_rdg1")); 
				download.setMdd_mtrchn_gdt2(BillingProgram.rs.getString("HDD_mtrchn_gdt2")); 
				download.setMdd_mtrchn_grdg2(BillingProgram.rs.getDouble("HDD_mtrchn_grdg2")); 
				download.setMdd_bill_amt(BillingProgram.rs.getDouble("hdd_bill_amt")); 
				download.setMdd_demand_arrears(BillingProgram.rs.getDouble("HDD_demand_arrears")); 
				download.setMdd_int_arrears(BillingProgram.rs.getDouble("HDD_int_arrears")); 
				download.setMdd_tax_arrears(BillingProgram.rs.getDouble("HDD_tax_arrears")); 
				download.setMdd_delay_int(BillingProgram.rs.getDouble("HDD_delay_int")); 
				download.setMdd_amt_paid1(BillingProgram.rs.getDouble("HDD_amt_paid1")); 
				download.setMdd_paid_date1(BillingProgram.rs.getString("HDD_paid_date1")); 
				download.setMdd_amt_paid2(BillingProgram.rs.getDouble("HDD_amt_paid2")); 
				download.setMdd_paid_date2(BillingProgram.rs.getString("HDD_paid_date2")); 
				download.setMdd_others(BillingProgram.rs.getDouble("HDD_others")); 
				download.setMdd_bill_gen_flag(BillingProgram.rs.getString("HDD_bill_gen_flag").charAt(0)); 
				download.setMdd_meter(BillingProgram.rs.getString("HDD_meter").charAt(0)); 
				download.setMdd_rebate_cap(BillingProgram.rs.getString("HDD_rebate_cap").charAt(0)); 
				download.setMdd_previous_demand1(BillingProgram.rs.getDouble("HDD_previous_demand1")); 
				download.setMdd_previous_demand2(BillingProgram.rs.getDouble("HDD_previous_demand2")); 
				download.setMdd_previous_demand3(BillingProgram.rs.getDouble("HDD_previous_demand3")); 
				download.setMdd_billing_mode(BillingProgram.rs.getString("HDD_billing_mode")); 
				download.setMdd_mtr_const(BillingProgram.rs.getString("HDD_mtr_const")); 
				download.setMdd_demand_based(BillingProgram.rs.getString("HDD_demand_based").charAt(0)); 
				download.setMdd_rural_rebate(BillingProgram.rs.getString("HDD_rural_rebate").charAt(0)); 
				download.setMdd_normal(BillingProgram.rs.getString("HDD_normal").charAt(0)); 
				download.setMdd_appeal_amount(BillingProgram.rs.getDouble("HDD_appeal_amount")); 
				download.setMdd_int_on_appeal_amt(BillingProgram.rs.getDouble("HDD_int_on_appeal_amt")); 
				download.setMdd_kvah(BillingProgram.rs.getDouble("HDD_kvah")); 
				download.setMdd_meter_tvm(BillingProgram.rs.getString("HDD_meter_tvm").charAt(0)); 
				download.setMdd_inst_typ(BillingProgram.rs.getString("HDD_inst_typ").charAt(0)); 
				download.setMdd_p_credit(BillingProgram.rs.getDouble("HDD_p_credit")); 
				download.setMdd_p_debit(BillingProgram.rs.getDouble("HDD_p_debit")); 
				download.setMdd_due_date(BillingProgram.rs.getString("HDD_due_date")); 
				download.setMdd_dlmnr(BillingProgram.rs.getString("HDD_dlmnr")); 
				download.setMdd_bill_num(BillingProgram.rs.getInt("HDD_bill_num")); 
				download.setMdd_delay_arrs(BillingProgram.rs.getDouble("HDD_delay_arrs")); 
				download.setMdd_int_on_tax(BillingProgram.rs.getDouble("HDD_int_on_tax")); 
				download.setMdd_ivrs_id(BillingProgram.rs.getString("HDD_ivrs_id")); 
				download.setMdd_ecs_flg(BillingProgram.rs.getString("HDD_ecs_flg").charAt(0)); 
				download.setMdd_part_period(BillingProgram.rs.getDouble("HDD_part_period")); 
				download.setMdd_user(BillingProgram.rs.getString("HDD_user")); 
				download.setMdd_tax_flg(BillingProgram.rs.getString("HDD_tax_flg").charAt(0)); 
				download.setMdd_DC_Flg(BillingProgram.rs.getString("HDD_dc_flg").charAt(0));  
				download.setMdd_Int_Arrears2(BillingProgram.rs.getDouble("HDD_intrst_arrs"));   
				download.setMdd_Int_Tax(BillingProgram.rs.getDouble("HDD_intrst_tax"));   
				download.setMdd_First_Rdg_Flg(BillingProgram.rs.getString("HDD_first_rdg_flg").charAt(0));   
				download.setMdd_Creaping_Perc(BillingProgram.rs.getInt("HDD_err_prcnt"));   
				download.setMdd_MNR_Flg(BillingProgram.rs.getString("HDD_mnr_flg").charAt(0));   
				download.setMdd_Old_Mtr_Rdg(BillingProgram.rs.getInt("HDD_old_mtr_consmp"));   
				download.setMdd_RecordedBMD(BillingProgram.rs.getDouble("HDD_BMD"));   
				download.setMdd_PrstCKWH(BillingProgram.rs.getDouble("HDD_Prsnt_CKWH"));   
				download.setMdd_PrevCKWH(BillingProgram.rs.getDouble("HDD_Prev_CKWH"));   
				download.setMdd_prev_rdg_flg(BillingProgram.rs.getString("HDD_prev_rdg_flg").charAt(0));   
				download.setMdd_no_tax_comp(BillingProgram.rs.getDouble("HDD_no_tax_comp"));   
				download.setMdd_PLFlag(BillingProgram.rs.getString("HDD_pl_flg").charAt(0));   
				download.setMdd_pf_flag(BillingProgram.rs.getString("HDD_calc_pf_flg").charAt(0));   
				download.setMdd_reg_penalty(BillingProgram.rs.getDouble("HDD_reg_penalty")); // System.out.println("check 2");
				download.setMdd_lastmonthfraction(BillingProgram.rs.getInt("HDD_last_dl_days")); // System.out.println("check 2");
				download.setMdd_Annual_Min_Fix(BillingProgram.rs.getDouble("HDD_Annual_Min_Fix"));  //System.out.println("check 2");
				download.setMdd_capacity_rbt(BillingProgram.rs.getDouble("HDD_capacity_rbt"));  //System.out.println("check 2");
				download.setMdd_cur_qrtr(BillingProgram.rs.getInt("HDD_cur_qrtr")); // System.out.println("check 2");
				download.setMdd_OrphnRbt(BillingProgram.rs.getDouble("HDD_orphanage_rebate")); // System.out.println("check 2");
				download.setMdd_mtr_chng_flg(BillingProgram.rs.getString("HDD_mc_flg").charAt(0)); // System.out.println("check 2");
				download.setMdd_tmpstp(BillingProgram.rs.getString("HDD_tmpstp"));//  System.out.println("check 2");
				
				
				BillingProgram.DownloadRecordList.add(download);
				
				bool = true;
				
			}
			
			
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured in DownloadRecordFromHDD : "+e.getMessage());
			bool = false;
		}
		
		
		return bool;
		
	}


	public static void MoveFromHDDToHHDRecord() {
		// TODO Auto-generated method stub
		
		try{
			if(BillingProgram.DownloadRecordList != null && BillingProgram.DownloadRecordList.size() > 0){
				
				Iterator<DownloadDataBO> it = BillingProgram.DownloadRecordList.iterator();
				DownloadDataBO downBO = null;
				
				BillingProgram.BillRecList =  new ArrayList<BillingRecordBO>();
				
				while(it.hasNext()){
					
					downBO = (DownloadDataBO)it.next();
					BillingProgram.BillRec = new BillingRecordBO();
					
					BillingProgram.BillRec.setLocation(downBO.getMdd_loc_cd());
					BillingProgram.BillRec.setRr_No(downBO.getMdd_rr_no());
					BillingProgram.BillRec.setLedger_No(downBO.getMdd_ldgr_no());
					BillingProgram.BillRec.setActual_Folio_No(downBO.getMdd_actl_folio_no());
					BillingProgram.BillRec.setSpot_Folio_No(downBO.getMdd_spot_folio_no());
					BillingProgram.BillRec.setTariff_Code(Integer.parseInt(downBO.getMdd_trf_code()));
					BillingProgram.BillRec.setBill_Date(downBO.getMdd_bill_dt());
					BillingProgram.BillRec.setConsmr_Name(downBO.getMdd_consmr_name());
					BillingProgram.BillRec.setAddress1(downBO.getMdd_addr1());
					BillingProgram.BillRec.setAddress2(downBO.getMdd_addr2());
					BillingProgram.BillRec.setAddress3(downBO.getMdd_addr3());
					BillingProgram.BillRec.setBilling_Month(downBO.getMdd_billing_month());
					BillingProgram.BillRec.setReading_Date(downBO.getMdd_rdg_dt());
					BillingProgram.BillRec.setReader_Code(downBO.getMdd_mtr_rdr_cd());
					BillingProgram.BillRec.setInstallation_Status(downBO.getMdd_instal_sts());
					BillingProgram.BillRec.setLine_Minimum(downBO.getMdd_line_min());
					BillingProgram.BillRec.setSanct_HP(downBO.getMdd_sanct_hp());
					BillingProgram.BillRec.setSanct_KW(downBO.getMdd_sanct_kw());
					BillingProgram.BillRec.setCT_Ratio(downBO.getMdd_ct_ratio());
					BillingProgram.BillRec.setPrev_Mtr_Rdg(downBO.getMdd_prev_rdg());
					BillingProgram.BillRec.setAvg_Consumption(downBO.getMdd_avg_consmp());
					BillingProgram.BillRec.setPower_Factor(downBO.getMdd_pwr_factor());
					BillingProgram.BillRec.setPf_flag(""+downBO.getMdd_pf_flag());
					
					if(BillingProgram.BillRec.getPf_flag().equals("N")){
						downBO.setMdd_pwr_factor(0.0);
					}
					
					if(downBO.getMdd_pwr_factor() > 0.001 && downBO.getMdd_pwr_factor() < BillingConstants.FIXEDPF){
						BillingProgram.BillRec.setPower_Factor((BillingConstants.FIXEDPF - BillingProgram.BillRec.getPower_Factor()) * BillingConstants.PENALTYAMT * 100.0);
					}else{
						BillingProgram.BillRec.setPower_Factor(0.0);
					}
					
					if(BillingProgram.BillRec.getPower_Factor() > 0.30){
						BillingProgram.BillRec.setPower_Factor(0.30);
					}
					
					BillingProgram.BillRec.setMtr_Chng_Dt1(downBO.getMdd_mtrchng_dt1());
					BillingProgram.BillRec.setMtr_Chng_Rdg1(downBO.getMdd_mtrchng_rdg1());
					BillingProgram.BillRec.setMtr_Chng_Dt2(downBO.getMdd_mtrchn_gdt2());
					BillingProgram.BillRec.setMtr_Chng_Rgd2(downBO.getMdd_mtrchn_grdg2());
					BillingProgram.BillRec.setPrevious_Bill_Amnt(downBO.getMdd_bill_amt());
					BillingProgram.BillRec.setDmd_Arrears(downBO.getMdd_demand_arrears());
					BillingProgram.BillRec.setInt_Arrears(downBO.getMdd_int_arrears());
					BillingProgram.BillRec.setTax_Arrears(downBO.getMdd_tax_arrears());
					BillingProgram.BillRec.setDelay_interest(downBO.getMdd_delay_int());
					BillingProgram.BillRec.setAmt_Paid1(downBO.getMdd_amt_paid1());
					BillingProgram.BillRec.setPaid_Date1(downBO.getMdd_paid_date1());
					BillingProgram.BillRec.setAmt_Paid2(downBO.getMdd_amt_paid2());
					BillingProgram.BillRec.setPaid_Date2(downBO.getMdd_paid_date2());
					BillingProgram.BillRec.setOthers(downBO.getMdd_others());
					BillingProgram.BillRec.setPrevious_Demand1(downBO.getMdd_previous_demand1());
					BillingProgram.BillRec.setPrevious_Demand2(downBO.getMdd_previous_demand2());
					BillingProgram.BillRec.setPrevious_Demand3(downBO.getMdd_previous_demand3());
					BillingProgram.BillRec.setBilling_Mode(downBO.getMdd_billing_mode());
					BillingProgram.BillRec.setMeter_Const(Double.parseDouble(downBO.getMdd_mtr_const()));
					BillingProgram.BillRec.setAppeal_Amount(downBO.getMdd_appeal_amount());
					BillingProgram.BillRec.setInt_on_Appeal_Amt(downBO.getMdd_int_on_appeal_amt());
					BillingProgram.BillRec.setKVAH(downBO.getMdd_kvah());
					BillingProgram.BillRec.setInst_Typ(""+downBO.getMdd_inst_typ());

					if(BillingProgram.BillRec.getInst_Typ().charAt(0) == (char)52
							||BillingProgram.BillRec.getInst_Typ().charAt(0) == (char)50
							||BillingProgram.BillRec.getInst_Typ().charAt(0) == (char)49){
						
					}else{
						if(BillingProgram.BillRec.getPower_Factor() > 0.30){
							BillingProgram.BillRec.setPower_Factor(0.30);
						}
					}
					
					BillingProgram.BillRec.setHP_Round(Math.ceil(downBO.getMdd_sanct_hp() * 100));
					BillingProgram.BillRec.setKW_Round(Math.ceil(downBO.getMdd_sanct_kw() * 100));
					BillingProgram.BillRec.setCreditBF(downBO.getMdd_p_credit());
					BillingProgram.BillRec.setDebitBF(downBO.getMdd_p_debit());
					BillingProgram.BillRec.setDebitBF(BillingProgram.BillRec.getDebitBF() / 100.0);
					BillingProgram.BillRec.setDue_Date(downBO.getMdd_due_date());
					BillingProgram.BillRec.setIvrsId(downBO.getMdd_ivrs_id());
					BillingProgram.BillRec.setPartFraction(downBO.getMdd_part_period());
					BillingProgram.BillRec.setNumDL(Integer.parseInt(downBO.getMdd_dlmnr()));
					BillingProgram.BillRec.setLess_MoreClaimedint(0.0);
					BillingProgram.BillRec.setMtr_Sts(""+downBO.getMdd_instal_sts());
					BillingProgram.BillRec.setnUnitsConsumed(0);
					BillingProgram.BillRec.setnTax(0.0);
					BillingProgram.BillRec.setTemp_Tax(0.0);
					BillingProgram.BillRec.setTotalBill(0.0);
					BillingProgram.BillRec.setnRebate(0.0);
					BillingProgram.BillRec.setnTotalFixedTariff(0.0);
					BillingProgram.BillRec.setP_nTotalEnergyTariff(0.0);
					BillingProgram.BillRec.setCreditCF(0.0);
					BillingProgram.BillRec.setMoreclaimed(0.0);
					BillingProgram.BillRec.setLessclaimed(0.0);
					BillingProgram.BillRec.setEnergyamtplustax(0.0);

					FlagsBO flagsBO =  new FlagsBO();
					flagsBO.setSolar_rebate(downBO.getMdd_solar_rebate());
					flagsBO.setFL_Rebate(downBO.getMdd_fl_rebate());

					if(flagsBO.getFL_Rebate() == 'Y'){
						BillingProgram.BillRec.setTemp_Tariff_Code(BillingProgram.BillRec.getTariff_Code());
						
						if(BillingProgram.BillRec.getTariff_Code() == TariffConstant.LT2AI)
							BillingProgram.BillRec.setTariff_Code(TariffConstant.LTFL1);
						
						if(BillingProgram.BillRec.getTariff_Code() == TariffConstant.LT2AII)
							BillingProgram.BillRec.setTariff_Code(TariffConstant.LTFL2);
						
						if(BillingProgram.BillRec.getTariff_Code() == TariffConstant.LT2AIII)
							BillingProgram.BillRec.setTariff_Code(TariffConstant.LTFL3);
					}else{
						BillingProgram.BillRec.setTemp_Tariff_Code(0);
					}
					
					
					BillingProgram.BillRec.setInt_on_tax(0);
					flagsBO.setTelep_Rebate(downBO.getMdd_telep_rebate());
					flagsBO.setPH_Rebate(downBO.getMdd_ph_rebate());
					flagsBO.setBill_Gen_Flag(downBO.getMdd_bill_gen_flag());
					flagsBO.setMeter(downBO.getMdd_meter());
					flagsBO.setRebate_Cap(downBO.getMdd_rebate_cap());
					flagsBO.setDemand_Based(downBO.getMdd_demand_based());
					flagsBO.setRural_Rebate(downBO.getMdd_rural_rebate());
					flagsBO.setNormal(downBO.getMdd_normal());
					flagsBO.setMeter_TVM(downBO.getMdd_meter_tvm());
					flagsBO.setECSUser(downBO.getMdd_ecs_flg());
					
					BillingProgram.BillRec.setDiffAmt(0.0);
					
					BillingProgram.BillRec.setCGexempt_flg(""+downBO.getMdd_tax_flg());
					BillingProgram.BillRec.setDC_Flg(""+downBO.getMdd_DC_Flg());
					BillingProgram.BillRec.setInt_Arrears2(downBO.getMdd_Int_Arrears2());
					BillingProgram.BillRec.setInt_Tax(downBO.getMdd_Int_Tax());
					BillingProgram.BillRec.setFirst_Rdg_Flg(""+downBO.getMdd_First_Rdg_Flg());
					BillingProgram.BillRec.setCreaping_Perc(downBO.getMdd_Creaping_Perc());
					BillingProgram.BillRec.setMNR_Flg(""+downBO.getMdd_MNR_Flg());
					BillingProgram.BillRec.setOld_Mtr_Rdg(downBO.getMdd_Old_Mtr_Rdg());
					BillingProgram.BillRec.setRecordedBMD(downBO.getMdd_RecordedBMD());
					BillingProgram.BillRec.setPrevCKWH(downBO.getMdd_PrstCKWH());
					BillingProgram.BillRec.setPrevCKWH(downBO.getMdd_PrevCKWH());
					BillingProgram.BillRec.setReg_penalty(downBO.getMdd_reg_penalty());
					BillingProgram.BillRec.setLastmonthfraction(downBO.getMdd_lastmonthfraction());
					BillingProgram.BillRec.setAnnual_Min_Fix(downBO.getMdd_Annual_Min_Fix());
					BillingProgram.BillRec.setCapRbtAmt(downBO.getMdd_capacity_rbt());
					BillingProgram.BillRec.setOrphnRbt(downBO.getMdd_OrphnRbt());
					BillingProgram.BillRec.setCur_qrtr(downBO.getMdd_cur_qrtr());
					BillingProgram.BillRec.setFirstflag("N");
					BillingProgram.BillRec.setFullmonflag("N");
					BillingProgram.BillRec.setNo_tax_comp(downBO.getMdd_no_tax_comp());
					BillingProgram.BillRec.setPLFlag(""+downBO.getMdd_PLFlag());
					BillingProgram.BillRec.setMtr_chng_flg(""+downBO.getMdd_mtr_chng_flg());
					BillingProgram.BillRec.setLt1_rbt_rate(downBO.getMdd_lt1_rbt());
					
					if(BillingProgram.BillRec.getPLFlag().equals("P")){
						BillingProgram.BillRec.setTemp_PLTariff_Code(BillingProgram.BillRec.getTariff_Code());
						BillingProgram.BillRec.setTariff_Code(TariffConstant.LTPL);
					}else{
						BillingProgram.BillRec.setTemp_PLTariff_Code(0);
					}
					
					if(BillingProgram.BillRec.getMNR_Flg().equals("Y") || BillingProgram.BillRec.getMNR_Flg().equals("y")){
						BillingProgram.BillRec.setMtr_Sts("M");
					}
					
					BillingProgram.BillRec.setPrev_rdg_flg(""+downBO.getMdd_prev_rdg_flg());
					
					if(BillingProgram.BillRec.getMtr_Sts().equals("M") || BillingProgram.BillRec.getMtr_Sts().equals("D")){
						if(BillingProgram.BillRec.getPrev_rdg_flg().equals("N")){
							if(BillingProgram.BillRec.getAvg_Consumption() * 1.0 == 1.0){
								System.out.println("***************************** NOT SETTING DC FLAG.");
							}else{
								BillingProgram.BillRec.setDC_Flg("Y");
							}
						}
					}
					
					BillingProgram.BillRec.setFlagsBO(flagsBO);
					
					BillingProgram.BillRec.setUser(downBO.getMdd_user());
					
					BillingProgram.BillRec.setBill_No(downBO.getMdd_bill_num());
					
					BillingProgram.BillRecList.add(BillingProgram.BillRec);
				}
				
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured MoveFromHDDToHHDRecord : "+e.getMessage());
		}
		
	}
	
	
	
	

}
