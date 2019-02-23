package com.backend.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.backend.controller.BillingProgram;
import com.backend.pojo.TariffFixBO;
import com.backend.pojo.TariffMainBO;
import com.backend.pojo.TariffRebateBO;
import com.backend.pojo.TariffSlabBO;

public class TariffDetailsDAO {
	
	public static void fillTariffDetailsToObject(String option){
		
		String Query = "";
		
		TariffMainBO MainBO = null;
		TariffRebateBO RebateBO = null;
		TariffSlabBO SlabBO = null;
		TariffFixBO FixBO = null;
		
		
		if(option.equals("MAIN")){
			
			System.out.println("Getting Tariff Main..........");
			
			try {
				Query =    " SELECT TM_TRF_CODE,TM_TRF_TYPE , TM_TRF_PWR_UNIT , TM_MIN_FXD ,TM_DC_UNTS ," +
						   " TM_TRF_TAX , TM_CONST_CHRGS " +
						   " FROM TRF_MAIN  " +
						   " WHERE TO_DATE('"+BillingProgram.BILL_DATE+"','dd/mm/yyyy')  " +
						   " BETWEEN TM_FR_DT AND TM_TO_DT ORDER BY TM_TRF_CODE,TM_TRF_TYPE";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();

				while(BillingProgram.rs.next()){
					MainBO = new TariffMainBO();
					
					MainBO.setTariffCode(BillingProgram.rs.getInt("TM_TRF_CODE"));
					MainBO.setTariffType(BillingProgram.rs.getString("TM_TRF_TYPE"));
					MainBO.setTariffPowerUnit(BillingProgram.rs.getString("TM_TRF_PWR_UNIT"));
					MainBO.setMinFixed(BillingProgram.rs.getInt("TM_MIN_FXD"));
					MainBO.setDCUnits(BillingProgram.rs.getLong("TM_DC_UNTS"));
					MainBO.setTariffTax(BillingProgram.rs.getInt("TM_TRF_TAX"));
					MainBO.setConstCharge(BillingProgram.rs.getInt("TM_CONST_CHRGS"));
					
					BillingProgram.TariffMainList.add(MainBO);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DatabaseOperations.cleanUp(BillingProgram.ps,BillingProgram.rs);
			
		}
		
		if(option.equals("FIX")){
			
			System.out.println("Getting Tariff Fix..........");
			
			try {
				Query =    " SELECT TF_TRF_CODE ,TF_ITEM ,TF_CHARGE_TYP,TF_FROM_UNITS ," +
						   " TF_TO_UNITS , TF_TRF_AMT " +
						   " FROM TRF_FIX  " +
						   " WHERE TO_DATE('"+BillingProgram.BILL_DATE+"','dd/mm/yyyy') " +
						   " BETWEEN TF_FR_DT AND TF_TO_DT";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();

				while(BillingProgram.rs.next()){
					FixBO = new TariffFixBO();
					
					FixBO.setTariffCode(BillingProgram.rs.getInt("TF_TRF_CODE"));
					FixBO.setItem(BillingProgram.rs.getString("TF_ITEM").charAt(0));
					FixBO.setChargeType(BillingProgram.rs.getString("TF_CHARGE_TYP").charAt(0));
					FixBO.setFrom_Units(BillingProgram.rs.getInt("TF_FROM_UNITS"));
					FixBO.setTo_Units(BillingProgram.rs.getInt("TF_TO_UNITS"));
					FixBO.setTariff_Amount(BillingProgram.rs.getInt("TF_TRF_AMT"));
					
					BillingProgram.TariffFixList.add(FixBO);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DatabaseOperations.cleanUp(BillingProgram.ps,BillingProgram.rs);
		}
		
		if(option.equals("REBATE")){
			
			System.out.println("Getting Tariff Reabate..........");
			
			try {
				Query =    "SELECT TR_RBT_CODE , TR_CH_TYPE , TR_RBT , TR_MAX_RBT , TR_RBT_TYP  " +
						   " FROM TRF_REBATE WHERE TO_DATE('"+BillingProgram.BILL_DATE+"','dd/mm/yyyy')  " +
						   " BETWEEN TR_FR_DT AND TR_TO_DT ";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();

				while(BillingProgram.rs.next()){
					RebateBO = new TariffRebateBO();
					
					RebateBO.setRebateCode(BillingProgram.rs.getString("TR_RBT_CODE"));
					RebateBO.setChType(BillingProgram.rs.getString("TR_CH_TYPE").charAt(0));
					RebateBO.setRebate(BillingProgram.rs.getInt("TR_RBT"));
					RebateBO.setMaxRebate(BillingProgram.rs.getInt("TR_MAX_RBT"));
					RebateBO.setRebateType(BillingProgram.rs.getString("TR_RBT_TYP").charAt(0));
					
					BillingProgram.TariffRebateList.add(RebateBO);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DatabaseOperations.cleanUp(BillingProgram.ps,BillingProgram.rs);
		}
		
		if(option.equals("SLAB")){
			
			System.out.println("Getting Tariff Slabs..........");
			
			try {
				Query =    "SELECT TS_TRF_CODE , TS_ITEM, TS_FROM_UNITS ,TS_TO_UNITS , TS_TRF_AMT " +
						   "FROM TRF_SLAB  " +
						   "WHERE TO_DATE('"+BillingProgram.BILL_DATE+"','dd/mm/yyyy') BETWEEN TS_FR_DT AND TS_TO_DT " +
						   "ORDER BY TS_TRF_CODE,TS_ITEM";
				
				BillingProgram.ps = BillingProgram.con.prepareStatement(Query);
				BillingProgram.rs = BillingProgram.ps.executeQuery();

				while(BillingProgram.rs.next()){
					SlabBO = new TariffSlabBO();
					
					SlabBO.setTariffCode(BillingProgram.rs.getInt("TS_TRF_CODE"));
					SlabBO.setItem(BillingProgram.rs.getString("TS_ITEM").charAt(0));
					SlabBO.setFromUnits(BillingProgram.rs.getInt("TS_FROM_UNITS"));
					SlabBO.setToUnits(BillingProgram.rs.getInt("TS_TO_UNITS"));
					SlabBO.setTariffAmount(BillingProgram.rs.getInt("TS_TRF_AMT"));
					
					BillingProgram.TariffSlabList.add(SlabBO);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DatabaseOperations.cleanUp(BillingProgram.ps,BillingProgram.rs);
			
			
		}
	}
	
	
	

}
