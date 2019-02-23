package com.backend.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.backend.controller.BillingProgram;
import com.backend.pojo.EnergyChargeList;
import com.backend.pojo.FixedChargeList;
import com.backend.pojo.RRNumberBO;
import com.backend.pojo.UploadDataBO;

public class DatabaseOperations {
	
	 /*
	*/
	static String  g_rr_no;
	static String  g_bill_dt;
	static String  g_prev_bill_no;
	static String  g_prev_bill_dt;
	static int g_tot_days;
	static int g_pre_full_week_days;
	static int g_no_full_weeks;
	static int g_post_full_week_days;
	static String  g_wm_ref_dt; 
	static String  g_new_wm_ref_dt;
	static int g_wm_week_no;
	static int g_wm_no_days;
	static double g_wm_energy_charge; 
	static double g_wm_weekly_min;
	static int g_num;
	static int g_num2;
	static double adjamt;
	
	//
	
	
	public static Connection getOracleConnection(String databaseIP,String databaseUSERNAME,String databasePASSWORD, String databaseNAME) throws SQLException {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					//"jdbc:oracle:thin:@192.168.0.201:1521:billnet", "bill",
					"jdbc:oracle:thin:@"+databaseIP+":1521:"+databaseNAME, ""+databaseUSERNAME+"", ""+databasePASSWORD+"");
		} catch (Exception e) {
			System.out.println("Error Occured :" + e.getMessage());
		}
		return con;
	}

	public static void cleanUp(PreparedStatement ps, ResultSet rs) {
		// TODO Auto-generated method stub
			try {
				if(ps != null){
					ps.close();
				}
				
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void cleanUp(PreparedStatement ps, ResultSet rs,Connection con) {
		// TODO Auto-generated method stub
			try {
				if(con != null){
					con.close();
				}
				
				if(ps != null){
					ps.close();
				}
				
				if(rs != null){
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void LogErrorIntoBILL_GEN_ERR(String Module,String Message) {
		// TODO Auto-generated method stub
		
		
		String query = " INSERT INTO BILL_GEN_ERR (BGE_RR_NO,BGE_BILL_DT,BGE_MR_CD,BGE_MOD_NAME,BGE_ERR_DESCR )" +
			           " VALUES ('"+BillingProgram.CURRENT_RRNO+"',TO_DATE('"+BillingProgram.BILL_DATE+"','DD/MM/YYYY')," +
			           " '"+BillingProgram.METER_CODE+"','"+Module+"','"+Message+"')";
		try {
			BillingProgram.ps = BillingProgram.con.prepareStatement(query);
			int i = BillingProgram.ps.executeUpdate();
			if(i > 0){
				System.out.println("Error Message logged For : "+BillingProgram.CURRENT_RRNO);
			}
			
			cleanUp(BillingProgram.ps, BillingProgram.rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void LogErrorIntoBILL_GEN_ERR(String BillDate,String MrCode,String Rrno,String Module,String Message) {
		// TODO Auto-generated method stub
		
		
		String query = " INSERT INTO BILL_GEN_ERR (BGE_RR_NO,BGE_BILL_DT,BGE_MR_CD,BGE_MOD_NAME,BGE_ERR_DESCR )" +
			           " VALUES ('"+Rrno+"',TO_DATE('"+BillDate+"','DD/MM/YYYY')," +
			           " '"+MrCode+"','"+Module+"','"+Message+"')";
		try {
			BillingProgram.ps = BillingProgram.con.prepareStatement(query);
			int i = BillingProgram.ps.executeUpdate();
			if(i > 0){
				System.out.println("Error Message logged For : "+BillingProgram.CURRENT_RRNO);
			}
			
			cleanUp(BillingProgram.ps, BillingProgram.rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static double Get_LT4_PDM_UNits(String bILL_DATE, String rr_No) {
		// TODO Auto-generated method stub
		
		double LT4Units = 0.0;
		String Query = " SELECT PDM_UNITS  FROM  PRE_DOMINANT_IP_PARAM,SHOP_MASTER  " +
				       " WHERE PDM_MONTH_YEAR = TRUNC(TO_DATE('"+bILL_DATE+"', 'dd/mm/yyyy'),'MM') " +
				       " AND SM_RR_NO =  '"+rr_No+"' AND SM_FDR_NO = PDM_FDR " +
				       " AND SM_STATION_no = PDM_STN";
		try{
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
					
			
			while(BillingProgram.rs.next()){
				if(BillingProgram.rs.getDouble("PDM_UNITS") > 0.0){
					LT4Units = BillingProgram.rs.getDouble("PDM_UNITS");
					break;
				}	
			}
			
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
		return LT4Units;
	}

	public static double GetWeeklyMinimumAmount(double minamt) {
		// TODO Auto-generated method stub
		
		String  l_bill_dt;
		String  l_prev_bill_dt;
		String  l_prev_bill_no;
		String  l_rrno;
		String  l_user;
		double  l_sanHP;
		double l_sanKW;
		double l_minamt;
		double l_wamt;
		double l_weeks;
		double l_tf;
		double l_tlong;
		double l_units;
		int l_prst_mod;
		int l_prev_mod;
		int l_weeknum;
		int l_no_days;
		double l_fixed_wamt;
		double l_ec_rate;
		double l_wamt_adj;
		double l_pre_ec_amt; 	
		double l_post_ec_amt; 
		double l_full_ec_amt; 	
		
		l_sanKW = 0.0;
		l_wamt = 0.0;
		l_weeks = 0.0;
		l_tf = 0.0;
		l_tlong = 0L;
		l_sanHP = BillingProgram.BillRecordBO.getSanct_HP();
		l_minamt = minamt;
		l_prst_mod = 0;
		l_prev_mod = 0;
		l_units = 0;
		l_weeknum = 0;
		l_no_days = 0;
		l_fixed_wamt = 0.0;
		l_ec_rate = 0.0;
		l_wamt_adj = 0.0;
		l_pre_ec_amt = 0.0; 	
		l_post_ec_amt = 0.0; 
		l_full_ec_amt = 0.0; 	
		g_num = 0;
		g_num2 = 0;
		
		l_bill_dt = BillingProgram.BILL_DATE;
		g_bill_dt = BillingProgram.BILL_DATE;
		l_rrno = BillingProgram.BillRecordBO.getRr_No();
		g_rr_no = BillingProgram.BillRecordBO.getRr_No();
		
		
		System.out.println("Bill date : "+BillingProgram.BILL_DATE);
		System.out.println("Sanct HP  : "+BillingProgram.BillRecordBO.getSanct_HP());
		System.out.println("Sanct KW : "+BillingProgram.BillRecordBO.getSanct_KW());
		
		try{
		
		
		String Query = " SELECT mod(nvl(SB_BILL_DT, (SM_SERVICE_DT - 1)) - (SM_SERVICE_DT-1) ,7) g_num," +
				       " mod(to_date(:g_bill_dt, 'dd/mm/yyyy')- (SM_SERVICE_DT - 1),7)	g_num2 " +
				       " FROM  SHOP_BILL, SHOP_MASTER   	" +
				       " WHERE SB_RR_NO = SM_RR_NO AND SM_CONSMR_STS IN (1, 9, 10) " +
				       " AND SB_RR_NO = '"+l_rrno+"' ";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		BillingProgram.rs = BillingProgram.ps.executeQuery();
		
		while(BillingProgram.rs.next()){
			
			if(BillingProgram.rs.getInt("g_num") != 0){
				g_num = 7 - BillingProgram.rs.getInt("g_num");
			}
			if(BillingProgram.rs.getInt("g_num2") != 0){
				g_num2 = 7 - BillingProgram.rs.getInt("g_num2");
			}
		}
		
		cleanUp(BillingProgram.ps, BillingProgram.rs);
		
		Query = " SELECT  to_char((nvl(SB_BILL_DT, SM_SERVICE_DT - 1) + "+g_num+"),'dd/mm/yyyy')  l_prev_bill_dt," +
				" nvl(SB_BILL_NO, '0') SB_BILL_NO, " +
				" to_date('"+l_bill_dt+"', 'dd/mm/yyyy') - (nvl(SB_BILL_DT, SM_SERVICE_DT - 1) + "+g_num+")  l_no_days " +
				" FROM SHOP_BILL, SHOP_MASTER  " +
				" WHERE SB_RR_NO = SM_RR_NO AND SM_CONSMR_STS IN (1, 9, 10) " +
				" AND SB_RR_NO = '"+l_rrno+"' ";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		BillingProgram.rs = BillingProgram.ps.executeQuery();
		
		while(BillingProgram.rs.next()){
			g_prev_bill_dt = BillingProgram.rs.getString("l_prev_bill_dt");
			g_prev_bill_no = BillingProgram.rs.getString("SB_BILL_NO");
			
			System.out.println("No of Days : "+BillingProgram.rs.getInt("l_no_days"));
		}
		
		cleanUp(BillingProgram.ps, BillingProgram.rs);
		
		Query = " SELECT to_char((nvl(SB_BILL_DT, SM_SERVICE_DT - 1)  + "+g_num+"),'dd/mm/yyyy')  l_prev_bill_dt , " +
				" nvl(SB_BILL_NO, '0')  SB_BILL_NO," +
				" to_date('"+l_bill_dt+"', 'dd/mm/yyyy') - (nvl(SB_BILL_DT, SM_SERVICE_DT - 1) + "+g_num+")  l_no_days " +
				" FROM SHOP_BILL, SHOP_MASTER  " +
				" WHERE SB_RR_NO = SM_RR_NO AND SM_CONSMR_STS IN (1, 9, 10) " +
				" AND SB_RR_NO = '"+l_rrno+"' ";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		BillingProgram.rs = BillingProgram.ps.executeQuery();
		
		while(BillingProgram.rs.next()){
			g_prev_bill_dt = BillingProgram.rs.getString("l_prev_bill_dt");
			g_prev_bill_no = BillingProgram.rs.getString("SB_BILL_NO");
			
			System.out.println("No of Days : l_no_days : "+BillingProgram.rs.getInt("l_no_days"));
			
			l_no_days =  BillingProgram.rs.getInt("l_no_days")  + g_num2;   
			g_tot_days = l_no_days;
			
			System.out.println("No of Days : l_no_days : "+l_no_days);
		}
		
		cleanUp(BillingProgram.ps, BillingProgram.rs);
		
		/* 
		This function fetchs following components 
		g_pre_full_week_days
		g_post_full_week_days
		g_no_full_weeks 
		g_new_wm_ref_dt
	*/
		GetDays();
		
		System.out.println("g_pre_full_week_days : "+g_pre_full_week_days);
		System.out.println("g_post_full_week_days : "+g_post_full_week_days);
		System.out.println("g_no_full_weeks : "+g_no_full_weeks);
		System.out.println("g_new_wm_ref_dt : "+g_new_wm_ref_dt);
		
		l_sanKW = l_sanHP * 0.746;

		l_sanKW += BillingProgram.BillRecordBO.getSanct_KW();

		l_tlong = (long)l_sanKW;

		l_tf = (l_sanKW - l_tlong) * 100;
		
		if (l_tf > 87)
			l_sanKW = l_tlong + 1.0;
		else if (l_tf > 62)
			l_sanKW = l_tlong + 0.75;
		else if (l_tf > 37)
			l_sanKW = l_tlong + 0.50;
		else if (l_tf > 12)
			l_sanKW = l_tlong + 0.25;
		else 
			l_sanKW = l_tlong;
		
		System.out.println("out l_sanKW : " +l_sanKW);
		System.out.println("nunits : " + BillingProgram.BillRecordBO.getnUnitsConsumed());
		
		l_units = 1.0 * BillingProgram.BillRecordBO.getnUnitsConsumed() / l_no_days;  
		
		System.out.println("l_units : "+l_units);
		
		l_fixed_wamt = l_sanKW * l_minamt;
		
		l_ec_rate = Get_LT7_Rate(BillingProgram.BILL_DATE);
		  
		
		l_pre_ec_amt = g_pre_full_week_days * l_units * l_ec_rate;

		l_post_ec_amt = g_post_full_week_days * l_units * l_ec_rate;
		
		l_full_ec_amt = g_no_full_weeks * l_units * l_ec_rate * 7; 
		
		System.out.println("l_pre_ec_amt : "+l_pre_ec_amt);
		System.out.println("l_pre_ec_amt : "+l_pre_ec_amt);
		System.out.println("l_pre_ec_amt : "+l_pre_ec_amt);
		
		if(g_pre_full_week_days > 0){
			
			Query = "  SELECT nvl(sum(WM_ENERGY_CHARGE), 0) g_wm_energy_charge,nvl(sum(WM_WEEKLY_MIN),0) g_wm_weekly_min" +
					"  FROM WEEKLY_MIN_LT7 	" +
					"  WHERE WM_REF_DT = to_date('"+g_wm_ref_dt+"', 'dd/mm/yyyy') " +
					"	AND WM_RRNO = '"+g_rr_no+"' ";
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				g_wm_energy_charge = BillingProgram.rs.getInt("g_wm_energy_charge");
				g_wm_weekly_min = BillingProgram.rs.getInt("g_wm_weekly_min");
				
				System.out.println("g_wm_energy_charge : "+g_wm_energy_charge);
				System.out.println("g_wm_weekly_min    : "+g_wm_weekly_min);
			}
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			if(g_wm_energy_charge < 1 && g_wm_weekly_min < 1 ){
				if (l_fixed_wamt > l_pre_ec_amt)
				{
					if (g_post_full_week_days > 0 || g_no_full_weeks > 0)
						l_wamt_adj = l_fixed_wamt - l_pre_ec_amt;	
					else	
						l_wamt = l_fixed_wamt - l_pre_ec_amt;
				}
				else if((g_wm_energy_charge + l_pre_ec_amt ) > l_fixed_wamt) 
				{
					l_wamt_adj = g_wm_weekly_min * -1;
					l_wamt = 0.0;  
				}
				else
				{
					l_wamt_adj = l_pre_ec_amt * -1;
					l_wamt = 0.0;  
				}
				adjamt = l_wamt_adj;
				
			}
		}
		if (g_no_full_weeks > 0)
		{
			if ((l_fixed_wamt * g_no_full_weeks) > l_full_ec_amt)
			{
				if (g_post_full_week_days > 0)
				{
					adjamt += l_fixed_wamt * g_no_full_weeks - l_full_ec_amt;
				}
				else
				{	
					l_wamt = l_fixed_wamt - (l_full_ec_amt / g_no_full_weeks);	
					adjamt += l_wamt * (g_no_full_weeks - 1);
				}
			}
		} 	

		if (g_post_full_week_days > 0)
		{
			if (l_fixed_wamt > l_post_ec_amt)
			{
				l_wamt = l_fixed_wamt - l_post_ec_amt;	
			}
		}
		
		Query = "  SELECT HDD_bill_num  " +
				"  FROM HHD_DOWNLOAD_DATA  " +
				"  WHERE HDD_rr_no=:g_rr_no AND  " +
				"  HDD_bill_dt=to_date(:g_bill_dt, 'dd/mm/yyyy') ";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		BillingProgram.rs = BillingProgram.ps.executeQuery();
		
		while(BillingProgram.rs.next()){
			g_prev_bill_no = BillingProgram.rs.getString("HDD_bill_num");
			
			System.out.println("g_prev_bill_no : "+g_prev_bill_no);
		}
		cleanUp(BillingProgram.ps, BillingProgram.rs);
		
		Query = "  SELECT ((to_date(:g_new_wm_ref_dt,'dd/mm/yyyy') - to_date(:g_wm_ref_dt,'dd/mm/yyyy')) / 7 + 1)  g_wm_week_no " +
				"  FROM dual ";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		BillingProgram.rs = BillingProgram.ps.executeQuery();
		
		while(BillingProgram.rs.next()){
			g_wm_week_no = BillingProgram.rs.getInt("g_wm_week_no");
			
			System.out.println("g_wm_week_no : "+g_wm_week_no);
		}
		cleanUp(BillingProgram.ps, BillingProgram.rs);
		
		g_wm_no_days = (g_post_full_week_days > 0) ? g_post_full_week_days :(g_no_full_weeks > 0) ?	7 : g_pre_full_week_days;	

		g_wm_energy_charge = (l_post_ec_amt > 0) ? l_post_ec_amt : (l_full_ec_amt > 0) ? (l_full_ec_amt / g_no_full_weeks) : l_pre_ec_amt;
		
		/*if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() > g_wm_energy_charge){
			
			l_wamt -=(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() - g_wm_energy_charge);
			
			adjamt +=(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() - g_wm_energy_charge);
		}*/
		
		l_user = BillingProgram.BillRecordBO.getUser();
		
		if (g_tot_days <= 0)
		{
			l_wamt = 0.0;
			adjamt = 0.0;
		}
		
		Query = " INSERT INTO weekly_min_lt7 (WM_RRNO,WM_BILL_NO,WM_BILL_DT,	WM_REF_DT,WM_WEEK_NO,WM_NO_DAYS,WM_ENERGY_CHARGE," +
				" WM_WEEKLY_MIN,WM_WEEKLY_ADJ,WM_USER_ID,WM_TMPSTP )" +
				" VALUES ('"+g_rr_no+"','"+g_prev_bill_no+"',TO_DATE('"+g_bill_dt+"','DD/MM/YYYY'),TO_DATE('"+g_new_wm_ref_dt+"','DD/MM/YYYY')" +
						",'"+g_wm_week_no+"','"+g_wm_no_days+"','"+g_wm_energy_charge+"','"+l_wamt+"','"+adjamt+"','"+l_user+"',SYSDATE)";
		
		BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
		int result  = BillingProgram.ps.executeUpdate();
		
		if(result > 0 ){
			System.out.println("Weekly Minimum LT7 inserted ...!");
		}
		
		System.out.println("l_wamt : "+l_wamt);
		System.out.println("l_wamt_adj : "+l_wamt_adj);
		System.out.println("l_pre_ec_amt : "+l_pre_ec_amt);
		System.out.println("l_full_ec_amt : "+l_full_ec_amt);
		System.out.println("l_post_ec_amt : "+l_post_ec_amt);
		System.out.println("g_wm_ref_dt : "+g_wm_ref_dt);
		System.out.println("g_new_wm_ref_dt : "+g_new_wm_ref_dt);
		
		
		if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() < l_fixed_wamt){
			adjamt += l_wamt - (l_fixed_wamt - BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
			l_wamt = l_fixed_wamt - BillingProgram.BillRecordBO.getP_nTotalEnergyTariff(); 
		}else{
			adjamt += l_wamt;
			l_wamt = 0.0;
		}
		
		if (g_tot_days <= 0)
		{
			l_wamt = 0.0;
			adjamt = 0.0;
		}
		
				
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
		return l_wamt;
	}

	private static double Get_LT7_Rate(String bILL_DATE) {
		// TODO Auto-generated method stub
		
		String l_bill_date = "";
		double LT7Rate = 0.0;
		
		l_bill_date = bILL_DATE;
		
		String Query = " SELECT  (ts_trf_amt / 100.0 ) LT7Rate	FROM  TRF_SLAB " +
				" WHERE ts_trf_code = "+BillingProgram.BillRecordBO.getTariff_Code()+"  " +
				"  AND to_date('"+l_bill_date+"', 'dd/mm/yyyy') " +
				" BETWEEN ts_fr_dt AND ts_to_dt ";
		
		try {
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				LT7Rate = BillingProgram.rs.getInt("LT7Rate");
				
				System.out.println("LT7Rate : "+BillingProgram.rs.getInt("LT7Rate"));
			}
			
			cleanUp(BillingProgram.ps, BillingProgram.rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return LT7Rate;
	}

	private static void GetDays() {
		// TODO Auto-generated method stub
		
		int l_count = 0;	
		int l_ref_dt_sts = 0;
		
		g_wm_week_no = 0;
		g_wm_no_days = 0;
		g_wm_energy_charge = 0.0;
		g_wm_weekly_min = 0.0;
	 	g_wm_ref_dt = "";
		g_pre_full_week_days = 0;
		
		try{
			String Query = " SELECT round(to_date('"+g_bill_dt+"', 'dd/mm/yyyy') - (nvl(SB_BILL_DT, SM_SERVICE_DT - 1 ) + " + g_num +"), 0)	g_tot_days " +
							" FROM SHOP_BILL, SHOP_MASTER " +
							" WHERE SB_rr_no = '"+g_rr_no+"'  AND SB_RR_NO = SM_RR_NO  AND SM_CONSMR_STS IN (1, 9, 10); ";
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				g_tot_days = BillingProgram.rs.getInt("g_tot_days");

				System.out.println("g_tot_days : "+g_tot_days);
				
				g_tot_days = g_tot_days + g_num2;   
			}
			
			l_count = 0;
			
			System.out.println(" g_prev_bill_dt : "+g_prev_bill_dt);
			System.out.println(" g_prev_bill_no : "+g_prev_bill_no);
			
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			Query = " SELECT count(9) l_count  FROM weekly_min_lt7 WHERE WM_RRNO = '"+g_rr_no+"' " ;
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				l_count = BillingProgram.rs.getInt("l_count");

				System.out.println("l_count : "+l_count);
			}
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			if(l_count > 0){
				
				Query = " SELECT WM_WEEK_NO,nvl(WM_NO_DAYS, 0) WM_NO_DAYS,nvl(WM_ENERGY_CHARGE, 0) WM_ENERGY_CHARGE, " +
						" nvl(WM_WEEKLY_MIN,0) WM_WEEKLY_MIN," +
						" to_char((nvl(SB_BILL_DT, SM_SERVICE_DT - 1) + "+g_num+"),'dd/mm/yyyy')  g_wm_ref_dt  " +
						" FROM WEEKLY_MIN_LT7, SHOP_BILL, SHOP_MASTER " +
						" WHERE wm_RRNO = '"+g_rr_no+"' "  +
						" AND SB_RR_NO = wm_rrno " +
						" AND SM_RR_NO = wm_rrno " +
						" AND SB_RR_NO = SM_RR_NO " +
						" AND wm_bill_dt = SB_BILL_DT";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					
					g_wm_week_no = BillingProgram.rs.getInt("WM_WEEK_NO");
					g_wm_no_days = BillingProgram.rs.getInt("WM_NO_DAYS");
					g_wm_energy_charge = BillingProgram.rs.getInt("WM_ENERGY_CHARGE");
					g_wm_weekly_min = BillingProgram.rs.getInt("WM_WEEKLY_MIN");
					g_wm_ref_dt      = BillingProgram.rs.getString("g_wm_ref_dt");

				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				System.out.println("g_wm_ref_dt : "+ g_wm_ref_dt);
				
			}else{
				g_wm_week_no = 0;
				g_wm_no_days = 0;
				g_wm_energy_charge = 0.0;
				g_wm_weekly_min = 0.0;
				
				Query = " SELECT to_char( to_date('"+g_prev_bill_dt+"', 'dd/mm/yyyy') + 7,'dd/mm/yyyy') g_wm_ref_dt from dual";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					g_wm_ref_dt      = BillingProgram.rs.getString("g_wm_ref_dt");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
			}
			
			
			Query = " SELECT count(9) l_ref_dt_sts  " +
					" FROM DUAL WHERE to_date('"+g_bill_dt+"', 'dd/mm/yyyy') > to_date('"+g_wm_ref_dt+"', 'dd/mm/yyyy') " ;
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				l_ref_dt_sts      = BillingProgram.rs.getInt("l_ref_dt_sts");
			}
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			g_pre_full_week_days = 0;
			g_no_full_weeks = 0; 
			
			System.out.println("g_wm_ref_dt : "+g_wm_ref_dt);
			System.out.println("l_count     : "+l_count);
			
			if(l_count > 0 && l_ref_dt_sts > 0){
				
				Query = " SELECT to_date('"+g_wm_ref_dt+"', 'dd/mm/yyyy') - to_date('"+g_prev_bill_dt+"', 'dd/mm/yyyy') g_pre_full_week_days " +
						" FROM dual";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					g_pre_full_week_days      = BillingProgram.rs.getInt("g_pre_full_week_days");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				if (g_pre_full_week_days > 0)
					g_no_full_weeks = (g_tot_days - g_pre_full_week_days) / 7;
				else
				{	
					g_no_full_weeks = (g_tot_days) / 7;
					g_pre_full_week_days = 0; 		
				}
				
				System.out.println("g_no_full_weeks = "+g_no_full_weeks);
				
				g_post_full_week_days = (g_tot_days - g_pre_full_week_days) - g_no_full_weeks * 7;	

				l_count = (g_no_full_weeks > 0 && g_post_full_week_days == 0) ? 0 : 1; 
				
				System.out.println("l_count = "+l_count);
				
				Query = "SELECT to_char(to_date('"+g_wm_ref_dt+"', 'dd/mm/yyyy') + (("+g_no_full_weeks+" + "+l_count+")* 7),'dd/mm/yyyy') g_new_wm_ref_dt  FROM dual";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					g_new_wm_ref_dt      = BillingProgram.rs.getString("g_new_wm_ref_dt");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
			}else{
				if (g_tot_days < 7)
				{
					g_pre_full_week_days = g_tot_days;
				}
				else 
				{
					g_no_full_weeks = g_tot_days / 7;
					g_post_full_week_days = g_tot_days - g_no_full_weeks * 7;	
				}	
				
				g_new_wm_ref_dt =  g_wm_ref_dt;
			}
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
	}

	public static double Get_Solar_Rate(String bILL_DATE) {
		// TODO Auto-generated method stub
		String l_bill_date ;
		String l_yyDate = null ; 
		
		double SolarRate = 50.0;
		
		l_bill_date = bILL_DATE ;
		
		try {
			
			String Query = "  SELECT to_char( to_date('"+l_bill_date+"', 'dd/mm/yyyy'),'YYYYMMDD')  l_yyDate  " +
						   "  FROM dual ";
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();
			
			while(BillingProgram.rs.next()){
				l_yyDate      = BillingProgram.rs.getString("l_yyDate");
				
				if(l_yyDate.equals("20051109")){
					SolarRate = 35.0 ;
				}else if(l_yyDate.equals("20091231")){
					SolarRate = 40.0;
				}else{
					SolarRate = 50.0;
				}
			}
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			System.out.println("Billdate : "+bILL_DATE + "  ,  solarrate : "+SolarRate);
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		return SolarRate;
	}

	public static int GetCurrentReading(String cURRENT_RRNO, String bILL_DATE,
			int pRESENT_READING) {
		// TODO Auto-generated method stub
		
		double f_prst_rdg = 0;
		int ind;
		long l_last_rdg = 0;
		String l_RRNo;
		String l_Billdt;
		String l_Mtr_sts = null;
		String l_last_Mtr_sts;
		String l_reading_day;
		String l_cm_mtr_rdg_cycle;
		int l_cm_mtr_rdg_day;
		int l_rdg_cycle;
		int l_mtr_rep;
		String l_from_date;
		String l_to_date;
		String l_last_reading_date = null;
		short indicator; 
		
		Readingtable readingtable = new Readingtable();
		ArrayList<Readingtable> arrRdgTable = new ArrayList<Readingtable>();
		
		int avg_consmp = 0; 
		int interReading = 0;
	 	int	daysDif = 0; 
		int i;
		
		String Query = "";
		
		try {
			

			
			l_RRNo = cURRENT_RRNO;
			l_Billdt = bILL_DATE;
			l_to_date = bILL_DATE;
			
			System.out.println("get current reading for :  "+cURRENT_RRNO + " and  "+bILL_DATE);
			
		//	l_rdg_cycle = (rdg_cycle * (-1)); 
			
			Query = " SELECT SM_MTR_RDG_CYCLE, SM_MTR_RDG_DAY   " +
					" FROM SHOP_MASTER " +
					" WHERE SM_RR_NO = '"+l_RRNo+"' " ;
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();

//NotFound :{//NotFound label starts here...			
			if(BillingProgram.rs.next()){
				l_cm_mtr_rdg_cycle      = BillingProgram.rs.getString("SM_MTR_RDG_CYCLE");
				l_cm_mtr_rdg_day        = BillingProgram.rs.getInt("SM_MTR_RDG_DAY");
				System.out.println("Record found in shop master");
			}else{
				//break NotFound;
				BillingProgram.BillRecordBO.setPrst_Rdg(0);
				BillingProgram.BillRecordBO.setMtr_Sts(" ");
				/*return -1 ;*/
				return -2 ;
			}
//}//NotFound label ends here...				
			cleanUp(BillingProgram.ps, BillingProgram.rs);
		
			
			System.out.println("l_rrno : "+l_RRNo);
			System.out.println("l_Billdt : "+l_Billdt);
			
			Query = " SELECT  NVL( MR_DAY_KWH , 0 )  f_prst_rdg, " +
					" DECODE(NVL(MR_dl_mnr_ind, 'RDG'), '1', 'D', '2', 'M', 'RDG', ' ')   l_Mtr_sts  " +
					" FROM MTR_RDG  " +
					" WHERE MR_RR_NO = '"+l_RRNo+"'  " +
					" AND	MR_RDG_DT = TO_DATE('"+l_Billdt+"' ,'dd/mm/yyyy' ) " ;
			
			System.out.println("Present Rdg : "+Query);
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();

//NotFound :{//NotFound label starts here...				
			if(BillingProgram.rs.next()){
				f_prst_rdg      = BillingProgram.rs.getInt("f_prst_rdg");
				l_Mtr_sts        = BillingProgram.rs.getString("l_Mtr_sts");
			}else{
				//break NotFound;
				BillingProgram.BillRecordBO.setPrst_Rdg(0);
				BillingProgram.BillRecordBO.setMtr_Sts(" ");
				return -2 ;
			}
//}//NotFound label ends here...				
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			System.out.println("Reading found : "+f_prst_rdg);
			
			
			
			Query = " SELECT  nvl(TO_CHAR(max(MR_RDG_DT), 'dd/mm/yyyy'), '01/01/1901')  l_last_reading_date " + 
					" FROM MTR_RDG  " +
					" WHERE MR_RR_NO = '"+l_RRNo+"'  " +
					" AND	MR_RDG_DT <  TO_DATE('"+l_Billdt+"' ,'dd/mm/yyyy' )  " +
					" AND NVL(MR_MTR_CHG_FLG, 'N') != 'Y'" ;
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			BillingProgram.rs = BillingProgram.ps.executeQuery();

//NotFound :{//NotFound label starts here...	
			if(BillingProgram.rs.next()){
				l_last_reading_date      = BillingProgram.rs.getString("l_last_reading_date");
			}else{
				//break NotFound;
				BillingProgram.BillRecordBO.setPrst_Rdg(0);
				BillingProgram.BillRecordBO.setMtr_Sts(" ");
				/*return -1 ;*/
				return -2 ;
			}
//}//NotFound label ends here...				
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			System.out.println("l_last_reading_date : "+l_last_reading_date);
			
			l_mtr_rep = 0;
			
			if(l_last_reading_date.equals("01/01/1901")){
				
				Query = " SELECT  NVL( MR_DAY_KWH , 0 )  l_last_rdg, " +
						" DECODE(NVL(MR_dl_mnr_ind, 'RDG'), '1', 'D', '2', 'M', 'RDG', ' ')   l_last_Mtr_sts  " +
						" FROM MTR_RDG  " +
						" WHERE MR_RR_NO = '"+l_RRNo+"'  " +
						" AND	MR_RDG_DT = TO_DATE('"+l_last_reading_date+"' ,'dd/mm/yyyy' ) " ;
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					l_last_rdg      = BillingProgram.rs.getInt("l_last_rdg");
					l_last_Mtr_sts        = BillingProgram.rs.getString("l_last_Mtr_sts");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				System.out.println("Last Reading : "+l_last_rdg);
				
				
				Query = " SELECT COUNT(9) l_mtr_rep  " +
						" FROM MTR_RDG  " +
						" WHERE MR_RR_NO = '"+l_RRNo+"'  " +
						" AND  (MR_RDG_DT > TO_DATE('"+l_last_reading_date+"', 'dd/mm/yyyy')  " +
						" AND  MR_RDG_DT < TO_DATE('"+l_Billdt+"', 'dd/mm/yyyy'))  " +
						" AND  NVL(MR_MTR_CHG_FLG, 'N') = 'Y'" ;
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					l_mtr_rep      = BillingProgram.rs.getInt("l_mtr_rep");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				System.out.println("Mtr Rep  : "+l_mtr_rep);
			}
			
			l_mtr_rep = 0;
			if(l_mtr_rep > 0){
				
				Query = " SELECT  TO_CHAR(MR_RDG_DT, 'yyyymmdd') MR_RDG_DT, " +
						" DECODE(NVL(MR_dl_mnr_ind, 'RDG'), '1', 'D', '2', 'M', 'RDG', ' ') MR_dl_mnr_ind, " +
						" NVL( MR_DAY_KWH , 0 ) MR_DAY_KWH, NVL(MR_MTR_CHG_FLG, 'N')  MR_MTR_CHG_FLG, " +
						" NVL(MR_IR_FR, ' ')  MR_IR_FR  " +
						" FROM MTR_RDG  " +
						" WHERE 	MR_RR_NO = '"+l_RRNo+"'  " +
						" AND  (MR_RDG_DT > TO_DATE('"+l_last_reading_date+"', 'dd/mm/yyyy')  " +
						" AND  MR_RDG_DT < TO_DATE('"+l_Billdt+"', 'dd/mm/yyyy'))  " +
						" ORDER BY 1" ;
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					
					readingtable.rdg_dt  = BillingProgram.rs.getString("MR_RDG_DT");
					readingtable.rdg_ind = BillingProgram.rs.getString("MR_dl_mnr_ind");
					readingtable.rdg     = BillingProgram.rs.getInt("MR_DAY_KWH");
					readingtable.sts_flg = BillingProgram.rs.getString("MR_MTR_CHG_FLG");
					readingtable.irfr    = BillingProgram.rs.getString("MR_IR_FR");
					
					arrRdgTable.add(readingtable);
					//l_mtr_rep      = BillingProgram.rs.getInt("l_mtr_rep");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				Query = " SELECT nvl(sum(nvl(BCD_AVG_CONSMP, 0)),0) avg_consmp  " +
						" FROM BILL_CONSMP_DETL  " +
						" WHERE 	BCD_RR_NO = '"+l_RRNo+"'  " +
						" AND	BCD_TO_DT = TO_DATE('"+l_Billdt+"' ,'dd/mm/yyyy' ) " ;
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				while(BillingProgram.rs.next()){
					avg_consmp      = BillingProgram.rs.getInt("avg_consmp");
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				interReading = 0;

				i = 0;
				
				while(i < (l_mtr_rep + 2)){
					
					Readingtable rdgTemp = (Readingtable)arrRdgTable.get(i);
					Readingtable rdgTemp1 = (Readingtable)arrRdgTable.get(i+1);
					
					System.out.println("From : "+rdgTemp.rdg_dt + " Rdg : "+rdgTemp.rdg);
					System.out.println("To : "+rdgTemp1.rdg_dt + " Rdg : "+rdgTemp1.rdg);
					
					if(i > 0){
						
						Readingtable rdgTemp2 = (Readingtable)arrRdgTable.get(i-1);
						Readingtable rdgTemp3 = (Readingtable)arrRdgTable.get(i);
						
						if(rdgTemp2.irfr == "F"
								&& rdgTemp3.irfr == "I" ){
							
							l_from_date = rdgTemp2.rdg_dt;
							l_to_date   = rdgTemp3.rdg_dt;
							daysDif = 0;
							
							Query = "  SELECT to_date('"+l_to_date+"', 'yyyymmdd') " +
									" 			       -					  " +
									" to_date('"+l_from_date+"', 'yyyymmdd') daysDif  " +
									" FROM dual  " ;
									
							
							BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
							BillingProgram.rs = BillingProgram.ps.executeQuery();
							
							while(BillingProgram.rs.next()){
								daysDif      = BillingProgram.rs.getInt("daysDif");
							}
							cleanUp(BillingProgram.ps, BillingProgram.rs);
							
							if(daysDif > 1){
								interReading += (avg_consmp / 30 ) * daysDif ;
							}
						}
					}
					
					interReading += rdgTemp1.rdg - rdgTemp.rdg;
					
					i += 2;	
					
				}
				
				BillingProgram.BillRecordBO.setMtr_Sts(l_Mtr_sts);
				BillingProgram.BillRecordBO.setPrst_Rdg(interReading);		
				return 0;
				
			}
			
			BillingProgram.BillRecordBO.setMtr_Sts(l_Mtr_sts);
			BillingProgram.BillRecordBO.setPrst_Rdg((long) f_prst_rdg);		
			return 0;
			
			
		
				/*BillingProgram.BillRecordBO.setPrst_Rdg(0);
				BillingProgram.BillRecordBO.setMtr_Sts(" ");
				return -1 ;*/
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return 0;
	}

	public static void MoveFromHHD2HUDRec(char status) {
		// TODO Auto-generated method stub
		
		BillingProgram.upload = new UploadDataBO();
		
		BillingProgram.upload.setMud_mtr_rdr_cd(BillingProgram.BillRecordBO.getReader_Code());
		BillingProgram.upload.setMud_rr_no(BillingProgram.BillRecordBO.getRr_No());
		
		
		BillingProgram.upload.setMud_bill_no(BillingProgram.BillRecordBO.getBill_No());
		BillingProgram.upload.setMud_bill_dt(BillingProgram.BillRecordBO.getBill_Date());
		BillingProgram.upload.setMud_mtr_rdg(BillingProgram.BillRecordBO.getPrst_Rdg());
		BillingProgram.upload.setMud_mtr_rdg_sts(BillingProgram.BillRecordBO.getMtr_Sts());
		BillingProgram.upload.setMud_units_consmp(BillingProgram.BillRecordBO.getnUnitsConsumed());
		BillingProgram.upload.setMud_fec(BillingProgram.BillRecordBO.getnTotalFixedTariff());
		BillingProgram.upload.setMud_tot_demand(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
		BillingProgram.upload.setMud_tax(BillingProgram.BillRecordBO.getnTax());
		BillingProgram.upload.setMud_rebate(BillingProgram.BillRecordBO.getnRebate() - BillingProgram.BillRecordBO.getPHRebate());
		
		
		BillingProgram.upload.setMud_cr_amt_fwd(BillingProgram.BillRecordBO.getCreditCF());
		BillingProgram.upload.setMud_billed_sts(status);
		BillingProgram.upload.setMud_pf_penalty(BillingProgram.BillRecordBO.getPower_Factor() * BillingProgram.BillRecordBO.getnUnitsConsumed());
		BillingProgram.upload.setMud_diff_amt(BillingProgram.BillRecordBO.getDiffAmt());
		BillingProgram.upload.setMud_penaltyonexcessload(BillingProgram.BillRecordBO.getPenaltyonexcessload());
		BillingProgram.upload.setMud_plrebate(BillingProgram.BillRecordBO.getPLRebate());
		BillingProgram.upload.setMud_phrebate(BillingProgram.BillRecordBO.getPHRebate());
		BillingProgram.upload.setMud_DLmoreclaimed(BillingProgram.BillRecordBO.getMoreclaimed());
		BillingProgram.upload.setMud_RR_Rebate(BillingProgram.BillRecordBO.getRR_Rebate());
		BillingProgram.upload.setMud_CapRbtAmt(BillingProgram.BillRecordBO.getCapRbtAmt());
		BillingProgram.upload.setMud_OrphnAmt(BillingProgram.BillRecordBO.getOrphnAmt());
		
		ArrayList<FixedChargeList> fixedarray = BillingProgram.BillRecordBO.getFixedChargeListBO();
		
		for(int i =0;i<fixedarray.size();i++){
			
			FixedChargeList fixed = fixedarray.get(i);
			
			System.out.println("rate1 : "+fixed.getRate() + " units1 : "+fixed.getUnits() + "amt1 : "+fixed.getAmount());
			
			if(i == 0){
				if(fixed.getRate() > 0){
					BillingProgram.upload.setMud_fx_rt1(fixed.getRate());
				}else{
					BillingProgram.upload.setMud_fx_rt1(0);
				}
				if(fixed.getUnits() > 0){
					BillingProgram.upload.setMud_fx_un1((int) fixed.getUnits());
				}else{
					BillingProgram.upload.setMud_fx_un1(0);
				}
			}
			if(i == 1){
				if(fixed.getRate() > 0){
					BillingProgram.upload.setMud_fx_rt2(fixed.getRate());
				}else{
					BillingProgram.upload.setMud_fx_rt2(0);
				}
				if(fixed.getUnits() > 0){
					BillingProgram.upload.setMud_fx_un2((int) fixed.getUnits());
				}else{
					BillingProgram.upload.setMud_fx_un2(0);
				}
			}
			if(i == 2){
				if(fixed.getRate() > 0){
					BillingProgram.upload.setMud_fx_rt3(fixed.getRate());
				}else{
					BillingProgram.upload.setMud_fx_rt3(0);
				}
				if(fixed.getUnits() > 0){
					BillingProgram.upload.setMud_fx_un3((int) fixed.getUnits());
				}else{
					BillingProgram.upload.setMud_fx_un3(0);
				}
			}
			if(i == 3){
				if(fixed.getRate() > 0){
					BillingProgram.upload.setMud_fx_rt4(fixed.getRate());
				}else{
					BillingProgram.upload.setMud_fx_rt4(0);
				}
				if(fixed.getUnits() > 0){
					BillingProgram.upload.setMud_fx_un4((int) fixed.getUnits());
				}else{
					BillingProgram.upload.setMud_fx_un4(0);
				}
			}
			if(i == 4){
				if(fixed.getRate() > 0){
					BillingProgram.upload.setMud_fx_rt5(fixed.getRate());
				}else{
					BillingProgram.upload.setMud_fx_rt5(0);
				}
				if(fixed.getUnits() > 0){
					BillingProgram.upload.setMud_fx_un5((int) fixed.getUnits());
				}else{
					BillingProgram.upload.setMud_fx_un5(0);
				}
			}
		}
		
		
		ArrayList<EnergyChargeList> energyarray = BillingProgram.BillRecordBO.getEnergyChargeListBO();
		
		for(int i=0;i < energyarray.size() ;i++){
			
			EnergyChargeList energy = energyarray.get(i);
			
			if(i == 0){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt1(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt1(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un1((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un1(0);
				}
			}
			if(i == 1){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt2(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt2(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un2((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un2(0);
				}
			}
			if(i == 2){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt3(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt3(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un3((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un3(0);
				}
			}
			if(i == 3){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt4(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt4(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un4((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un4(0);
				}
			}
			if(i == 4){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt5(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt5(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un5((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un5(0);
				}
			}
			if(i == 5){
				if(energy.getRate() > 0){
					BillingProgram.upload.setMud_en_rt6(energy.getRate());
				}else{
					BillingProgram.upload.setMud_en_rt6(0);
				}
				if(energy.getUnits() > 0){
					BillingProgram.upload.setMud_en_un6((int) energy.getUnits());
				}else{
					BillingProgram.upload.setMud_en_un6(0);
				}
			}
		}

		
		
		System.out.println("Data Movode from hhd to Upload rec...!");
		
	}

	public static int InsertIntoHHDUploadData() {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			
		String	Query = " Insert INTO HHD_UPLOAD_DATA (  " +
			        	" 		hud_mtr_rdr_cd, " +
			        	" 		hud_rr_no, " +
			        	" 		hud_bill_no, " +
			        	" 		hud_bill_dt, " +
			        	"		hud_mtr_rdg, " +
			        	" 		hud_mtr_rdg_sts, " +
			        	"		hud_units_consmp, " +
			        	"		hud_fec, " +
			        	" 		hud_tot_demand, " +
			        	"		hud_tax, " +
			        	"		hud_rebate, " +
			        	"		hud_cr_amt_fwd," +
			        	"		hud_billed_sts," +
			        	"		hud_mudload_flg," +
			        	"		hud_fx_un1," +
			        	"		hud_fx_rt1," +
			        	"		hud_fx_un2," +
			        	"		hud_fx_rt2," +
			        	"		hud_fx_un3," +
			        	"		hud_fx_rt3," +
			        	"		hud_fx_un4," +
			        	"		hud_fx_rt4," +
			        	"		hud_fx_un5," +
			        	"		hud_fx_rt5," +
			        	"		hud_en_un1," +
			        	"		hud_en_rt1," +
			        	"		hud_en_un2," +
			        	"		hud_en_rt2," +
			        	"		hud_en_un3," +
			        	"		hud_en_rt3," +
			        	"		hud_en_un4," +
			        	"		hud_en_rt4," +
			        	"		hud_en_un5," +
			        	"		hud_en_rt5," +
			        	"		hud_en_un6," +
			        	"		hud_en_rt6," +
			        	"		hud_err_descr," +
			        	"		hud_user," +
			        	"		hud_pf_penalty," +
			        	"		hud_diff_amt," +
			        	"		hud_pnlty_excs_ld," +
			        	"		hud_pl_rbt," +
			        	"		hud_ph_rbt," +
			        	"		hud_act_dl_cr," +
			        	"		hud_RR_Rebate," +
			        	"		hud_capacity_rbt," +
			        	"		hud_orphanage_rebate," +
			        	"		hud_wkly_min_amt," +
			        	"		hud_wkly_adj_amt," +
			        	"		hud_tmpstp " +
			        	")  " +
			        	"  VALUES  (" +
			        	"		'"+BillingProgram.upload.getMud_mtr_rdr_cd()+"'," +
			        	" 		'"+BillingProgram.upload.getMud_rr_no()+"'," +
			        	" 		'"+BillingProgram.upload.getMud_bill_no()+"'," +
			        	" 		TO_DATE('"+BillingProgram.upload.getMud_bill_dt()+"','DD/MM/YYYY')," +
			        	"		'"+BillingProgram.upload.getMud_mtr_rdg()+"'," +
			        	" 		'"+BillingProgram.upload.getMud_mtr_rdg_sts()+"'," +
			        	"		'"+BillingProgram.upload.getMud_units_consmp()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fec()+"'," +
			        	" 		'"+BillingProgram.upload.getMud_tot_demand()+"'," +
			        	"		'"+BillingProgram.upload.getMud_tax()+"'," +
			        	"		'"+BillingProgram.upload.getMud_rebate()+"'," +
			        	"		'"+BillingProgram.upload.getMud_cr_amt_fwd()+"'," +
			        	"		'"+BillingProgram.upload.getMud_billed_sts()+"'," +
			        	"		NVL("+BillingProgram.upload.getMud_mudload_flg()+",'')," +
			        	"		'"+BillingProgram.upload.getMud_fx_un1()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_rt1()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_un2()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_rt2()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_un3()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_rt3()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_un4()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_rt4()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_un5()+"'," +
			        	"		'"+BillingProgram.upload.getMud_fx_rt5()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un1()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt1()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un2()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt2()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un3()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt3()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un4()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt4()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un5()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt5()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_un6()+"'," +
			        	"		'"+BillingProgram.upload.getMud_en_rt6()+"'," +
			        	"		'"+BillingProgram.upload.getMud_err_descr()+"'," +
			        	"		'"+BillingProgram.upload.getMud_user()+"'," +
			        	"		'"+BillingProgram.upload.getMud_pf_penalty()+"'," +
			        	"		'"+BillingProgram.upload.getMud_diff_amt()+"'," +
			        	"		'"+BillingProgram.upload.getMud_penaltyonexcessload()+"'," +
			        	"		'"+BillingProgram.upload.getMud_plrebate()+"'," +
			        	"		'"+BillingProgram.upload.getMud_phrebate()+"'," +
			        	"		'"+BillingProgram.upload.getMud_DLmoreclaimed()+"'," +
			        	"		'"+BillingProgram.upload.getMud_RR_Rebate()+"'," +
			        	"		'"+BillingProgram.upload.getMud_CapRbtAmt()+"'," +
			        	"		'"+BillingProgram.upload.getMud_OrphnAmt()+"'," +
			        	"		'"+BillingProgram.upload.getMud_WeekMinAmt()+"'," +
			        	"		'"+BillingProgram.upload.getMud_weekadjamt()+"'," +
			        	"		TO_DATE(NVL("+BillingProgram.upload.getMud_tmpstp ()+",SYSDATE),'DD/MM/YYYY') " +
			        	")" ;
					
			System.out.println("Insert Query = "+Query);
			
			BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
			result = BillingProgram.ps.executeUpdate();
			
			cleanUp(BillingProgram.ps, BillingProgram.rs);
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured ,Inserting upload record to table : "+e.getMessage());
		}
		
		
		return result;
	}

	public static int UploadHHDDownloadData(char status) {
		// TODO Auto-generated method stub
		int result = 0;
		
		String query = " UPDATE  HHD_DOWNLOAD_DATA SET HDD_BILL_GEN_FLAG = '"+status+"'  " +
					   " WHERE HDD_RR_NO = '"+BillingProgram.upload.getMud_rr_no()+"' " +
					   " AND   HDD_BILL_DT = to_date('"+BillingProgram.upload.getMud_bill_dt()+"', 'dd/mm/yyyy') " +
					   " AND   HDD_bill_NUM = '"+BillingProgram.upload.getMud_bill_no()+"' ";
		try {
			
			System.out.println("update query = "+query);
			
				BillingProgram.ps = BillingProgram.con.prepareStatement(query);
				result = BillingProgram.ps.executeUpdate();

				cleanUp(BillingProgram.ps, BillingProgram.rs);
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured , Updating HHD-Download Data : "+e.getMessage());
		}
		
		return result;
	}

	public static void UploadBillMaster() {
		// TODO Auto-generated method stub
		
		String l_billDate;
		String l_mrCode;
		String l_rrno;
		String l_sts;
		String l_user;
		String l_location;
		
		int i,ret;
		String temp;
		String rcpt_dt;
		
		l_billDate = BillingProgram.BILL_DATE;
		l_mrCode   = BillingProgram.METER_CODE;
		l_user     = BillingProgram.USER;
		l_location = BillingProgram.LOCATION;
		
		CallableStatement cstmt = null;
		
		System.out.println("BillingProgram.BILL_DATE : "+BillingProgram.BILL_DATE);
		System.out.println("BillingProgram.METER_CODE : "+BillingProgram.METER_CODE);
		System.out.println("BillingProgram.USER : "+BillingProgram.USER);
		System.out.println("BillingProgram.LOCATION : "+BillingProgram.LOCATION);
		
		try {
			System.out.println("Inside PKG_HHD.UP_HHD_DATA ");
			
			cstmt = BillingProgram.con.prepareCall("{CALL PKG_HHD.UP_HHD_DATA (?,?,?,?,?)}");
			
			
			cstmt.setString(1, l_mrCode);
			cstmt.setString(2, l_billDate);
			cstmt.setString(3, l_user);
			cstmt.setString(4, "SRV");
			cstmt.setString(5, l_location);
			
			cstmt.execute();
			
			
			if(cstmt != null){
				cstmt.close();
			}
			
			Iterator<RRNumberBO> it = BillingProgram.RRNOList.iterator();
			while(it.hasNext()){
				RRNumberBO RrNoBO = (RRNumberBO)it.next();
				System.out.println("Inside ShopBill ---> RRNO : "+RrNoBO.getRrNo());
				
				l_rrno = RrNoBO.getRrNo();
				
				
				String Query = " SELECT NVL(SB_RR_STS, 'N') SB_RR_STS " +
								"  FROM SHOP_BILL  " +
								"  WHERE SB_RR_NO = '"+l_rrno+"' ";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();
				
				if(BillingProgram.rs.next()){
					
					l_sts = BillingProgram.rs.getString("SB_RR_STS");
					
					System.out.println("Before Recon : SB_RR_STS  = "+l_sts);
					
					if(l_sts.equals("I")){
						
						//Call Reconcilation Program Here...................
						System.out.println("Call Reconcilation Program Here...........");
						
						
						System.out.println("Recon Program Starts Here...!");
						
						//Call The Respective Child Process...!
						System.out.println("java -jar "+BillingProgram.RECON_PATH+"Reconcilation.jar "+BillingProgram.DATABASE_IP+" "+BillingProgram.ORACLE_USER+"/"+BillingProgram.ORACLE_PASS+" "+BillingProgram.DATABASE_NAME+" "+BillingProgram.BILL_DATE+"  "+BillingProgram.CURRENT_RRNO);
						
						
						Process p = null;
						try {
							p = Runtime.getRuntime().exec("java -jar "+BillingProgram.RECON_PATH+"Reconcilation.jar "+BillingProgram.DATABASE_IP+" "+BillingProgram.ORACLE_USER+"/"+BillingProgram.ORACLE_PASS+" "+BillingProgram.DATABASE_NAME+" "+BillingProgram.BILL_DATE+"  "+BillingProgram.CURRENT_RRNO);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 
				        final InputStream is = p.getInputStream();
				        Thread t = new Thread(new Runnable() {
				            public void run() {
				                InputStreamReader isr = new InputStreamReader(is);
				                int ch;
				                try {
				                    while ((ch = isr.read()) != -1) {
				                        System.out.print((char) ch);
				                    }
				                } catch (IOException e) {
				                    e.printStackTrace();
				                }
				            }
				        });
				        t.start();
				        try {
							p.waitFor();
							t.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
				        System.out.println("Recon Program Ends Here");
				        
						System.out.println("Recon done for RRNO : "+l_rrno);
						
					}
					
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
				
				Query = " SELECT SB_RR_STS  FROM SHOP_BILL  "
						+ "  WHERE SB_RR_NO = '" + l_rrno + "' ";

				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();

				if (BillingProgram.rs.next()) {

					l_sts = BillingProgram.rs.getString("SB_RR_STS");
					System.out.println("After Recon : SB_RR_STS  = " + l_sts);
				}
				cleanUp(BillingProgram.ps, BillingProgram.rs);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : UploadBillMaster : -> "+e.getMessage());
		}
		
		
		
	}

}

class Readingtable{

	String rdg_dt;
	String rdg_ind;
	long rdg ;
	String sts_flg;
	String irfr;
}




