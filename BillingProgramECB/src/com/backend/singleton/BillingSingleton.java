package com.backend.singleton;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.TransferHandler.DropLocation;

import com.backend.controller.BillingProgram;
import com.backend.dao.DatabaseOperations;
import com.backend.dao.DownloadUploadDAO;
import com.backend.dao.TariffDetailsDAO;
import com.backend.pojo.BillingRecordBO;
import com.backend.pojo.CompInfoTemp;
import com.backend.pojo.EnergyChargeList;
import com.backend.pojo.EtvPenaltyTabBO;
import com.backend.pojo.FixCompInfoTemp;
import com.backend.pojo.FixedChargeList;
import com.backend.pojo.FlagsBO;
import com.backend.pojo.TariffConstant;
import com.backend.pojo.TariffFixBO;
import com.backend.pojo.TariffMainBO;
import com.backend.pojo.TariffRebateBO;
import com.backend.pojo.TariffSlabBO;

public class BillingSingleton {
	
	private static BillingSingleton BillOBJ;
	
	static double lt4credit;
	
	
	private BillingSingleton(){
		
	}
	
	public static BillingSingleton getInstance(){
		if(BillOBJ == null){
			BillOBJ = new BillingSingleton();
		}
		
		return BillOBJ;
	}
	
	public void getSomeThing(){
        // do something here
        System.out.println("I am here....");
    }
	
/*public int validate_arguements(String[] args){
		
		if(args.length < 1){
			System.out.println("Usage: oracleUser/password [-n=RRNO] | [-f=FileName]\n");
			return -1;
		}
		for(int i=0;i < args.length;i++){
			
			System.out.println("Argument "+(i+1) +" : "+args[i]);
			String temp = null;
			if(i == 0){
				if(args[0].length() > 0 && args[0].contains("/")){
					String[] s = args[0].split("/");
					BillingProgram.ORACLE_USER = s[0];
					BillingProgram.ORACLE_PASS = s[1];
				}
			}else{
				if(args[i].startsWith("-m", 0) || args[i].startsWith("-M", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "MONTH ----> " + temp ;
					BillingProgram.MONTH = args[i].substring(args[i].indexOf('=')+1);
				}else if(args[i].startsWith("-r", 0) || args[i].startsWith("-R", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "MRCODE ----> " + temp ;
					BillingProgram.METER_CODE = args[i].substring(args[i].indexOf('=')+1);
				}else if(args[i].startsWith("-n", 0) || args[i].startsWith("-N", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "RRNO ----> " + temp ;
					BillingProgram.CURRENT_RRNO = args[i].substring(args[i].indexOf('=')+1);
				}else if(args[i].startsWith("-d", 0) || args[i].startsWith("-D", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "DATE ----> " + temp ;
					BillingProgram.BILL_DATE = args[i].substring(args[i].indexOf('=')+1);
				}
				else if(args[i].startsWith("-l", 0) || args[i].startsWith("-L", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "LOCATION ----> " + temp ;
					BillingProgram.LOCATION = args[i].substring(args[i].indexOf('=')+1);
				}
				else if(args[i].startsWith("-u", 0) || args[i].startsWith("-U", 0)){
					temp = args[i].substring(args[i].indexOf('=')+1);
					temp = "USER ----> " + temp ;
					BillingProgram.USER = args[i].substring(args[i].indexOf('=')+1);
				}
			}
			
			System.out.println("new args["+i+"] : "+temp);
			
			 
		}
		return 0;
		
	}*/

	
public int validate_arguements(String[] args){
		
	try{
		
		if(args.length < 3){
		
			System.out.println("Usage: ip_address oracleUser/password [-n=RRNO] | [-f=FileName]\n");
			return -1;
		
		}else{
			
				if(args.length > 2){
					
					for(int i=0;i < args.length;i++){
						
						System.out.println("Argument "+(i+1) +" : "+args[i]);
						String temp = null;
						if(i == 0){
							if(args[i].length() > 0 ){
								/*String[] s = args[0].split("/");
								BillingProgram.ORACLE_USER = s[0];
								BillingProgram.ORACLE_PASS = s[1];*/
								BillingProgram.DATABASE_IP = args[i];
								temp = args[i];
							}
						}else if(i == 1){
							if(args[i].length() > 0 && args[i].contains("/")){
								String[] s = args[i].split("/");
								BillingProgram.ORACLE_USER = s[0];
								BillingProgram.ORACLE_PASS = s[1];
							}
							temp = args[i];
						}else if(i == 2){
							BillingProgram.DATABASE_NAME = args[i];
							temp = args[i];
						}			
						else{
							if(args[i].startsWith("-m", 0) || args[i].startsWith("-M", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "MONTH ----> " + temp ;
								BillingProgram.MONTH = args[i].substring(args[i].indexOf('=')+1);
							}else if(args[i].startsWith("-r", 0) || args[i].startsWith("-R", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "MRCODE ----> " + temp ;
								BillingProgram.METER_CODE = args[i].substring(args[i].indexOf('=')+1);
							}else if(args[i].startsWith("-n", 0) || args[i].startsWith("-N", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "RRNO ----> " + temp ;
								BillingProgram.CURRENT_RRNO = args[i].substring(args[i].indexOf('=')+1);
							}else if(args[i].startsWith("-d", 0) || args[i].startsWith("-D", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "DATE ----> " + temp ;
								BillingProgram.BILL_DATE = args[i].substring(args[i].indexOf('=')+1);
							}
							else if(args[i].startsWith("-l", 0) || args[i].startsWith("-L", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "LOCATION ----> " + temp ;
								BillingProgram.LOCATION = args[i].substring(args[i].indexOf('=')+1);
							}
							else if(args[i].startsWith("-u", 0) || args[i].startsWith("-U", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "USER ----> " + temp ;
								BillingProgram.USER = args[i].substring(args[i].indexOf('=')+1);
							}
							else if(args[i].startsWith("-p", 0) || args[i].startsWith("-P", 0)){
								temp = args[i].substring(args[i].indexOf('=')+1);
								temp = "RECON PATH ----> " + temp ;
								BillingProgram.RECON_PATH = args[i].substring(args[i].indexOf('=')+1);
							}
						}
						
						System.out.println("new args["+i+"] : "+temp);
					}
						
						
			}else{
				System.out.println("Usage: ip_address oracleUser/password [-n=RRNO] | [-f=FileName]\n");
				return -1;
			}
			
			
			
		}
		
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		System.out.println("Error Occured , Validate arguements : " +e.getMessage());
		return -1;
	}
		
		return 0;
		
	}
	
	public void GetSlabDetails() {
		// TODO Auto-generated method stub
		
		if(BillingProgram.TariffMainList == null){
			BillingProgram.TariffMainList = new ArrayList<TariffMainBO>();
			TariffDetailsDAO.fillTariffDetailsToObject("MAIN");
		}
		
		if(BillingProgram.TariffFixList == null){
			BillingProgram.TariffFixList = new ArrayList<TariffFixBO>();
			TariffDetailsDAO.fillTariffDetailsToObject("FIX");
		}
		
		if(BillingProgram.TariffSlabList == null){
			BillingProgram.TariffSlabList = new ArrayList<TariffSlabBO>();
			TariffDetailsDAO.fillTariffDetailsToObject("SLAB");
		}
		
		if(BillingProgram.TariffRebateList == null){
			BillingProgram.TariffRebateList = new ArrayList<TariffRebateBO>();
			TariffDetailsDAO.fillTariffDetailsToObject("REBATE");
		}
		
		
		/**/
	}

	public int loadRRnoList() {
		// TODO Auto-generated method stub
		
		int option = 0;
		
		if(BillingProgram.METER_CODE != null && BillingProgram.METER_CODE.length() > 0){
			option = 1;
		}
		
		if(option == 1){
			if(DownloadUploadDAO.MakeRrNumberList()){
				System.out.println("1.Data Loaded For RRno List - MeterCode Wise");
			}else{
				return -1;
			}
		}else{
			if(BillingProgram.CURRENT_RRNO != null && BillingProgram.CURRENT_RRNO.length() > 0){
				if(DownloadUploadDAO.MakeRrNumberList(BillingProgram.CURRENT_RRNO)){
					System.out.println("2.Data Loaded For RRno List - RRnowise");
				}else{
					return -1;
				}
			}
		}
		
		return BillingProgram.RRNOList.size();
	}

	public boolean GetDownloadRecord() {
		// TODO Auto-generated method stub
		boolean down_bool = false;
		if(DownloadUploadDAO.DownloadRecordFromHDD()){
			
			DownloadUploadDAO.MoveFromHDDToHHDRecord();
			down_bool = true;
		}else{
			down_bool = false;
			
			DatabaseOperations.LogErrorIntoBILL_GEN_ERR("GetDownloadRec","Record Not Found");
		}
		
		return down_bool;
		
	}

	public void ComputeBill() {
		// TODO Auto-generated method stub
		
		double diff = 0;
		int k = 0;
		int t = 0;
		double DLmoreclaimed , DLlessclaimed , Units ;
		DLmoreclaimed = DLlessclaimed = Units = 0.0;
		
		initglobals();
		
		System.out.println("tempMainBo.getTariffCode() :"+BillingProgram.tempMainBo.getTariffCode() +
				"BillRec.getTariff_Code() : "+BillingProgram.BillRecordBO.getTariff_Code());
		
		if(BillingProgram.tempMainBo.getTariffType().contains("LT4")){
				
			System.out.println("TariffType().contains(LT4)");
			BillingProgram.BillRecordBO.setNumDL(0);
			BillingProgram.BillRecordBO.setAvg_Consumption(0);
		}
		
		if(BillingProgram.BillRecordBO.getMtr_chng_flg().equals("Y")){
			System.out.println("Inside getMtr_chng_flg is Y");
			BillingProgram.BillRecordBO.setNumDL(0);
		}
		
		if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT7){
			
			System.out.println("********** In Temp Billing *********  LT7");
			
			ComputeTempoaryBill();
		}
		
		if(BillingProgram.BillRecordBO.getMtr_Sts().equals(" ") 
				&& BillingProgram.BillRecordBO.getNumDL() != 0){
			
			if(BillingProgram.BillRecordBO.getLastmonthfraction() != 30){
				
				AdjustDL();
				
				DLmoreclaimed = BillingProgram.BillRecordBO.getMoreclaimed();
				DLlessclaimed = BillingProgram.BillRecordBO.getLessclaimed();
				Units = BillingProgram.BillRecordBO.getnUnitsConsumed();
				
				System.out.println("units : "+Units);
				System.out.println("units test : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
				
				if(BillingProgram.BillRecordBO.getNumDL() > 1){
					
					AdjustDL();
					
					System.out.println("Avg : "+BillingProgram.BillRecordBO.getAvg_Consumption());
					System.out.println("Acttual : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
					
					DLmoreclaimed = DLmoreclaimed + BillingProgram.BillRecordBO.getMoreclaimed() ;
					DLlessclaimed = DLlessclaimed + BillingProgram.BillRecordBO.getLessclaimed() ;
					
					Units = Units + BillingProgram.BillRecordBO.getnUnitsConsumed();
				}
				
				GetUnitsConsumed();
				
				System.out.println("UC 2 : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
				System.out.println("units 2 : "+Units);
				
				BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() - Units) );
				BillingProgram.BillRecordBO.setMoreclaimed(BillingProgram.BillRecordBO.getMoreclaimed() - DLmoreclaimed );
				BillingProgram.BillRecordBO.setLessclaimed(BillingProgram.BillRecordBO.getLessclaimed() - DLlessclaimed );
				
				System.out.println("DLmore : "+DLmoreclaimed);
				System.out.println("DLLes : "+DLlessclaimed);
				System.out.println("nunits : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
				
				DLComputeBill();
			}
			else{
				BillingProgram.BillRecordBO.setFirstflag("Y");
				AdjustDL();		
				
				Units = BillingProgram.BillRecordBO.getnUnitsConsumed();
				
				GetUnitsConsumed();
				
				BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() - Units));
				
				System.out.println("Present monnth units after adjustment : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
				
				System.out.println("BF1 : "+BillingProgram.BillRecordBO.getCreditBF());
				System.out.println("CF1 : "+BillingProgram.BillRecordBO.getCreditCF());
				
				DLComputeBill();
			}
		}else{
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'Y'){
				ComputeFreeLightingBill();
			}else{
				GetUnitsConsumed();
				
				if(BillingProgram.BillRecordBO.getPf_flag().charAt(0) == 'P'){

					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTPL);
					
					ComputeEnergyTariff();
					
					BillingProgram.BillRecordBO.setPLRebate(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
					BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_PLTariff_Code());
				}
				
				if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
					
					BillingProgram.BillRecordBO.setTemp_Tariff_Code(BillingProgram.BillRecordBO.getTariff_Code());
					BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(0.0);
					
					if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AI){
						BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL1);
					}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AII){
						BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL2);
					}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AIII){
						BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL3);
					}
						
						ComputeEnergyTariff();
						BillingProgram.BillRecordBO.setnRebate(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());	
						BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_Tariff_Code());  
						ComputeFixedTariff();
				}
				
				ComputeEnergyTariff();
				
				if(BillingProgram.BillRecordBO.getPf_flag().charAt(0) == 'P'){
					BillingProgram.BillRecordBO.setPLRebate(
							BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() - BillingProgram.BillRecordBO.getPLRebate());
				}
				if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
					BillingProgram.BillRecordBO.setnRebate(
							BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
							- 
							BillingProgram.BillRecordBO.getnRebate());
					
					BillingProgram.BillRecordBO.setnRebate(
							BillingProgram.BillRecordBO.getnRebate() 
							+
							BillingProgram.BillRecordBO.getnTotalFixedTariff());
					
					System.out.println("nrebate : "+BillingProgram.BillRecordBO.getnRebate());
				}
				
				if(BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)52
						||BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)50
							||BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)49){
					ComputeBMDPenalty();
				}
				ComputeFixedTariff();
				
				if(BillingProgram.BillRecordBO.getFlagsBO().getSolar_rebate() == 'Y'){
					CalculateRebate();
				}
				
				if(BillingProgram.BillRecordBO.getFlagsBO().getRural_Rebate() == 'Y'){
					CalculateRRRebate();
				}
					
				if(BillingProgram.BillRecordBO.getCapacitor() > 0.00){
					BillingProgram.BillRecordBO.setCapRbtAmt(
							BillingProgram.BillRecordBO.getCapRbtAmt() + CalculateCapRbt());
				}
				
				if(BillingProgram.BillRecordBO.getOrphnRbt() > 0.00){
					BillingProgram.BillRecordBO.setOrphnAmt(
							BillingProgram.BillRecordBO.getOrphnAmt() + CalculateOrphnRebate());
				}
				System.out.println("Rebate before ph rebate : " + BillingProgram.BillRecordBO.getnRebate());
				
				if(BillingProgram.BillRecordBO.getFlagsBO().getPH_Rebate() == 'Y'){
					BillingProgram.BillRecordBO.setPHRebate(ComputePHRebate());
					BillingProgram.BillRecordBO.setnRebate(
							BillingProgram.BillRecordBO.getnRebate() + BillingProgram.BillRecordBO.getPHRebate());
				}
				
				System.out.println("rebate after ph rebate : "+BillingProgram.BillRecordBO.getnRebate());
				
				ComputeTax();
				ComputeTotalAmount();
				BillingProgram.BillRecordBO.setCreditCF(0);
				
				if((BillingProgram.BillRecordBO.getTotalBill() 
						- (BillingProgram.BillRecordBO.getCreditBF() / 100.0)) < 0){
					
					BillingProgram.BillRecordBO.setCreditCF(
							(BillingProgram.BillRecordBO.getCreditCF() / 100.0)
							-
							(BillingProgram.BillRecordBO.getTotalBill()
									- BillingProgram.BillRecordBO.getnRebate()
									- BillingProgram.BillRecordBO.getCapRbtAmt()
									- BillingProgram.BillRecordBO.getOrphnAmt()));
					
					BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getCreditCF() * -1);
					
					System.out.println("1.BillingProgram.BillRecordBO.getCreditCF()  = "+ BillingProgram.BillRecordBO.getCreditCF() );
				}
				
				BillingProgram.BillRecordBO.setTotalBill(BillingProgram.BillRecordBO.getTotalBill() - BillingProgram.BillRecordBO.getCreditBF());
				
				//added by nadeem - to round to two digits
				BillingProgram.BillRecordBO.setTotalBill(formatDecimalToTwoDigits(BillingProgram.BillRecordBO.getTotalBill()));
				
				System.out.println("BillingProgram.BillRecordBO.getTotalBill() = "+BillingProgram.BillRecordBO.getTotalBill());
				
				if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT4D || 
						BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT4D){
					
					BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getCreditCF() + lt4credit); 
					
					System.out.println("2.BillingProgram.BillRecordBO.getCreditCF()  = "+ BillingProgram.BillRecordBO.getCreditCF() );
				}
				
				//diff = getRoundDiff(BillingProgram.BillRecordBO.getTotalBill());
				diff = getRoundDiff(BillingProgram.BillRecordBO.getTotalBill());
				
				System.out.println("diff = " + diff);
				
				BillingProgram.BillRecordBO.setDiffAmt(diff);
				
				
				//This code commented because it adds diff amt to total demand
				/*if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() > 0.00){
					BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(
							BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + diff);
				}else{
					BillingProgram.BillRecordBO.setnTotalFixedTariff(
							BillingProgram.BillRecordBO.getnTotalFixedTariff() + diff);
				}*/
			}
		}
		
	}
	
	private double formatDecimalToTwoDigits(double value){
		
		DecimalFormat df = new DecimalFormat(".##");
		String v = df.format((double)value);
		
		return Double.parseDouble(v);
	}
	
	private double getRoundDiff(double d) {
		// TODO Auto-generated method stub
		
		//double f1;
		
		double rnd = Math.round(d);

	   /*// memset(buff36,'\0',36);
	    //sprintf(buff36,"%.0f", f);
		System.out.println("d : "+d);
	    f1 = (double)d;
	    System.out.println("f1 : "+f1);
	    f1 = (double) (f1 - d);
	    //BillRec.setDiffAmt(f1);
	    System.out.println("f1 : "+f1);
	    return f1;*/
		
		DecimalFormat df = new DecimalFormat(".##");
		String v = df.format(rnd - d);
		System.out.println(" v  = "+Double.parseDouble(v));
		
		//return rnd - d ;
		
		
		
		return Double.parseDouble(v);
		
		
		
	}
	
	private double CalculateOrphnRebate() {
		// TODO Auto-generated method stub
		double orpnrbt = 0.0;
		orpnrbt = BillingProgram.BillRecordBO.getnUnitsConsumed() * BillingProgram.BillRecordBO.getOrphnRbt();
		return orpnrbt;
	}

	private double CalculateCapRbt() {
		// TODO Auto-generated method stub
		double caprbt = 0.0;
		caprbt = BillingProgram.BillRecordBO.getnUnitsConsumed() * BillingProgram.BillRecordBO.getCapacitor();
		return caprbt;
	}

	private void ComputeBMDPenalty() {
		// TODO Auto-generated method stub
		
		double KW = 0.0; 
		double flkw = 0.0;  
		double tempfixedcharges = 0.0;  
		double excessfixedcharges = 0.0;
		int i = 0 ,intKW = 0;
		double diff = 0.0;
		double plty = 0.0;
		long  LKW , Lflkw , tempKW , tempHP;
		int j = 0;
		LKW = Lflkw = tempHP = tempKW = 0L;
		
		try {
			
			TariffFixBO fixBO = null;
			
			EtvPenaltyTabBO etvPenaltyTabBO = null ; // = new EtvPenaltyTabBO();
			/*etvPenaltyTabBO.setStartHP(-1.0);
			etvPenaltyTabBO.setEndHP(-1.0);
			etvPenaltyTabBO.setRate(0);*/
			
			/*List<EtvPenaltyTabBO> etvLsit = new ArrayList<EtvPenaltyTabBO>();*/
			BillingProgram.etvList = new ArrayList<EtvPenaltyTabBO>();
			
			
			if(BillingProgram.TariffFixList != null){
				
				Iterator<TariffFixBO> fixed_it = BillingProgram.TariffFixList.iterator();
				
				while(fixed_it.hasNext()){
					fixBO = (TariffFixBO)fixed_it.next();
					
					if(BillingProgram.BillRecordBO.getTariff_Code() == fixBO.getTariffCode()){
						
						if(fixBO.getTo_Units() != -1){
							etvPenaltyTabBO = new EtvPenaltyTabBO();
							
							etvPenaltyTabBO.setStartHP(fixBO.getFrom_Units());
							etvPenaltyTabBO.setEndHP(fixBO.getTo_Units());
							etvPenaltyTabBO.setRate(fixBO.getTariff_Amount());
							
							BillingProgram.etvList.add(etvPenaltyTabBO);
						}
					}
				}
				
				etvPenaltyTabBO = new EtvPenaltyTabBO();
				
				etvPenaltyTabBO.setStartHP(-1);
				etvPenaltyTabBO.setEndHP(-1);
				etvPenaltyTabBO.setRate(fixBO.getTariff_Amount());
				
				BillingProgram.etvList.add(etvPenaltyTabBO);
			}
			
			TariffMainBO MainBo = null;
			
			if(BillingProgram.TariffMainList != null){
				
				Iterator<TariffMainBO> main_it = BillingProgram.TariffMainList.iterator();
				
				while(main_it.hasNext()){
					
					MainBo =  (TariffMainBO)main_it.next();
					
					if(BillingProgram.BillRecordBO.getTariff_Code() == MainBo.getTariffCode()){
						
						if(MainBo.getTariffType().contains("DD")){
							
							ChangeDBTariffstructure();
							
							KW = BillingProgram.BillRecordBO.getRecordedBMD() * BillingProgram.BillRecordBO.getCT_Ratio();
							
							System.out.println("Penalty KW : "+KW);
							
							flkw = ((double)BillingProgram.BillRecordBO.getHP_Round()) / 100.0;    
							flkw = (flkw * 0.746) * 100.0;
							flkw += (double)BillingProgram.BillRecordBO.getKW_Round();
							flkw = flkw / 100.0;
							
							System.out.println("Reular KW : "+flkw);
						}
						else{
							
							if(GetTariffCode()){
								
								KW = BillingProgram.BillRecordBO.getRecordedBMD() * BillingProgram.BillRecordBO.getCT_Ratio();
								KW = KW / 100.0;    
								KW = ( KW / 0.746 ) * 100.0;
								
								System.out.println("Penalty HP : "+KW);
								
								flkw = ((double)BillingProgram.BillRecordBO.getHP_Round()) / 100.0;    
								flkw = (flkw * 0.746) * 100.0;
								flkw += (double)BillingProgram.BillRecordBO.getKW_Round();
								flkw = flkw / 100.0;
								
								System.out.println("Reular hp : "+flkw);
								
							}else{
								
								if(MainBo.getTariffPowerUnit().charAt(0) == 'K'){
									
									KW = BillingProgram.BillRecordBO.getRecordedBMD() * BillingProgram.BillRecordBO.getCT_Ratio();
									flkw = ((double)BillingProgram.BillRecordBO.getHP_Round()) / 100.0;    
									flkw = (flkw * 0.746) * 100.0;
				     	  			flkw += BillingProgram.BillRecordBO.getKW_Round() ;

									tempHP = (long) BillingProgram.BillRecordBO.getHP_Round();
									tempKW = (long) BillingProgram.BillRecordBO.getKW_Round();
									
									BillingProgram.BillRecordBO.setHP_Round(0);
									BillingProgram.BillRecordBO.setKW_Round((long)flkw);
									
									ComputeFixedTariff();
									tempfixedcharges = BillingProgram.BillRecordBO.getnTotalFixedTariff();
									
									BillingProgram.BillRecordBO.setKW_Round((long)KW * 100 );
									ComputeFixedTariff();
									
									excessfixedcharges = BillingProgram.BillRecordBO.getnTotalFixedTariff();
									BillingProgram.BillRecordBO.setHP_Round(tempHP);
									BillingProgram.BillRecordBO.setKW_Round(tempKW);
								}else{
									if(MainBo.getTariffPowerUnit().charAt(0) == 'H'){
										
										KW = BillingProgram.BillRecordBO.getRecordedBMD() * BillingProgram.BillRecordBO.getCT_Ratio();
										KW = KW / 100.0;    
										KW = ( KW / 0.746 ) * 100.0;

										flkw =((double)BillingProgram.BillRecordBO.getKW_Round()) / 100.0;    
										flkw = (flkw / 0.746) * 100.0;
				           				flkw += BillingProgram.BillRecordBO.getHP_Round() ;
				           				
				           				System.out.println("Inside free ligthg : "+ flkw+ " , "+ KW);
				           				
				           				tempHP = (long) BillingProgram.BillRecordBO.getHP_Round();
				           				tempKW = (long) BillingProgram.BillRecordBO.getKW_Round();
				           				
				           				BillingProgram.BillRecordBO.setHP_Round((long)flkw);
				           				BillingProgram.BillRecordBO.setKW_Round(0);
				           				
				           				ComputeFixedTariff();
				           				tempfixedcharges = BillingProgram.BillRecordBO.getnTotalFixedTariff();
				           				
				           				System.out.println("tempfixedcharges : "+tempfixedcharges);
				           				
				           				BillingProgram.BillRecordBO.setHP_Round((long)KW * 100);
				           				
				           				ComputeFixedTariff();
				           				
				           				excessfixedcharges = BillingProgram.BillRecordBO.getnTotalFixedTariff();
				           				
				           				System.out.println("excessfixedcharges : "+excessfixedcharges);
				           				
				           				BillingProgram.BillRecordBO.setHP_Round(tempHP);
				           				BillingProgram.BillRecordBO.setKW_Round(tempKW);
				           				
				           				
									}
									flkw /= 100;
								}
								
								if(flkw < KW ){
									plty = ((excessfixedcharges - tempfixedcharges) * TariffConstant.ETVPENALTY);
								}
								
								BillingProgram.BillRecordBO.setPenaltyonexcessload(plty);
								
								return ;
							}
						}
					}
				}
				
			}
			
			LKW = Math.round(KW * 100);
			Lflkw = Math.round(flkw * 100);
			
			KW = LKW / 100.0;
			flkw = Lflkw / 100.0;
			
			System.out.println("rounded KW or Hp : "+KW);
			System.out.println("flkw = "+flkw);
			
			intKW = (int) KW ;
			
			if(KW > flkw){
				plty = calcETVbmdpenalty(KW,flkw);
				BillingProgram.BillRecordBO.setPenaltyonexcessload(plty);
				
				System.out.println("penalty on ex load : "+plty);
			}else{
				BillingProgram.BillRecordBO.setPenaltyonexcessload(0.0);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
	}

	private double calcETVbmdpenalty(double recHP, double sanHP) {
		// TODO Auto-generated method stub
		
		int i;
		double plty;
		double calUnits,partround;
		partround = 0.0;

		plty = 0.0;
		
		try {
			
			partround = BillingProgram.BillRecordBO.getPartFraction() * 30.0;
			partround = Math.round(partround);
			
			
			System.out.println("recHP before partperiod  : "+recHP);
			System.out.println("sanHP before partperiod  : "+sanHP);
			
			if(BillingProgram.TariffMainList != null){
				
				if(BillingProgram.BillRecordBO.getTariff_Code() == BillingProgram.tempMainBo.getTariffCode()){
					
					System.out.println("TARIFF POWER UNIT : "+BillingProgram.tempMainBo.getTariffPowerUnit());
					System.out.println("TARIFF CODE IN BILLRECORD : "+BillingProgram.BillRecordBO.getTariff_Code());
						
					if(BillingProgram.tempMainBo.getTariffPowerUnit().charAt(0) == 'K'){
						ChangeHPtoKWinstructure();
					}
				}
			}
			
			if (BillingProgram.BillRecordBO.getPartFraction() < 1.00 
					|| BillingProgram.BillRecordBO.getPartFraction() > 1.00) {
			
				ChangeETVTableval();
			}
		
			recHP = recHP * partround / 30 ;
			sanHP = sanHP * partround / 30 ;	
			
			System.out.println("recHP after partperiod  : "+recHP);
			System.out.println("sanHP after partperiod  : "+sanHP);
			
			EtvPenaltyTabBO etvPenaltyTabBO = null;
			
			if(BillingProgram.etvList != null){
				
				Iterator<EtvPenaltyTabBO> etv_it = BillingProgram.etvList.iterator();
				
				while(etv_it.hasNext()){
					 
					etvPenaltyTabBO = (EtvPenaltyTabBO)etv_it.next();
					
					if(etvPenaltyTabBO.getStartHP() != -1){
						
						if(sanHP >= etvPenaltyTabBO.getStartHP() &&
									sanHP <= etvPenaltyTabBO.getEndHP() ){
							break;
						}
					}
				}
			}
			calUnits = recHP -  sanHP;
			plty +=  (( (double)  (calUnits ) * 
					( (double) etvPenaltyTabBO.getRate()) / 100.0) * TariffConstant.ETVPENALTY);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return plty;
	}

	private void ChangeETVTableval() {
		// TODO Auto-generated method stub
		
		int i=0;
		double partround = 0.0;
		
		partround = BillingProgram.BillRecordBO.getPartFraction() * 30.0;
		partround = Math.round(partround);
		
		EtvPenaltyTabBO etvPenaltyTabBO = null;
		
		if(BillingProgram.etvList != null){
			
			Iterator<EtvPenaltyTabBO> etv_it = BillingProgram.etvList.iterator();
			
			while(etv_it.hasNext()){
				 
				etvPenaltyTabBO = (EtvPenaltyTabBO)etv_it.next();
				
				if(etvPenaltyTabBO.getStartHP() != -1){
					
					etvPenaltyTabBO.setStartHP(etvPenaltyTabBO.getStartHP() * partround / 30.0);
					etvPenaltyTabBO.setEndHP(etvPenaltyTabBO.getEndHP() * partround / 30.0);
				}
				
				System.out.println("statrhp : "+ etvPenaltyTabBO.getStartHP() + "   ------  endhp :  "+etvPenaltyTabBO.getEndHP());
			}
			
		}
				
		
	}

	private void ChangeHPtoKWinstructure() {
		// TODO Auto-generated method stub
		
		int i=0;
		double startKW , endKW;
		startKW = endKW = 0.0;
		
		System.out.println("INSIDE  HP TO KW CONVERSION IN STRUCTURE");
		
		EtvPenaltyTabBO etvPenaltyTabBO = null;
		
		if(BillingProgram.etvList != null){
			
			Iterator<EtvPenaltyTabBO> etv_it = BillingProgram.etvList.iterator();
			
			while(etv_it.hasNext()){
				 
				etvPenaltyTabBO = (EtvPenaltyTabBO)etv_it.next();
				
				if(etvPenaltyTabBO.getStartHP() != -1){
					
					startKW = etvPenaltyTabBO.getStartHP() * 0.746 ;
					endKW   = etvPenaltyTabBO.getEndHP()   * 0.746;
					
					etvPenaltyTabBO.setStartHP(startKW);
					etvPenaltyTabBO.setEndHP(endKW);
				}
				
				System.out.println(etvPenaltyTabBO.getStartHP() + "   :   "+etvPenaltyTabBO.getEndHP());
			}
		}
		
	}

	private boolean GetTariffCode() {
		// TODO Auto-generated method stub
		int ch = BillingProgram.BillRecordBO.getTariff_Code();
		
		if(ch == TariffConstant.LT5CI || 
				ch == TariffConstant.LT5CII || 
					ch == TariffConstant.LT5DI || 
						ch == TariffConstant.LT5DII ){
			return true;
		}
		return false;
	}

	private void ChangeDBTariffstructure() {
		// TODO Auto-generated method stub
		
		EtvPenaltyTabBO etvBO = null;
		/*List<EtvPenaltyTabBO> etvList = new ArrayList<EtvPenaltyTabBO>();*/
		
		BillingProgram.etvList =  new ArrayList<EtvPenaltyTabBO>();
		
		etvBO = new EtvPenaltyTabBO();
		etvBO.setRate(2500);
		BillingProgram.etvList.add(etvBO);
		
		etvBO = new EtvPenaltyTabBO();
		etvBO.setRate(5000);
		BillingProgram.etvList.add(etvBO);
		
		etvBO = new EtvPenaltyTabBO();
		etvBO.setRate(7000);
		BillingProgram.etvList.add(etvBO);
		
		etvBO = new EtvPenaltyTabBO();
		etvBO.setRate(15000);
		BillingProgram.etvList.add(etvBO);
		
		
		
	}

	private void ComputeFreeLightingBill() {
		// TODO Auto-generated method stub
		
		double diff = 0.0;	
		
		System.out.println("*********** Free Lighting Bill");
		
		GetUnitsConsumed();
		ComputeFreeLightingTempTax();
		
		GetUnitsConsumed();
		ComputeEnergyTariff();
		ComputeFixedTariff();
		ComputeTotalAmount();
		
		BillingProgram.BillRecordBO.setCreditCF(0);
		
		if((BillingProgram.BillRecordBO.getTotalBill() - (BillingProgram.BillRecordBO.getCreditBF() / 100.0)) < 0){
			
			BillingProgram.BillRecordBO.setCreditCF(
													BillingProgram.BillRecordBO.getCreditBF() / 100.0
													-
													(BillingProgram.BillRecordBO.getTotalBill() - BillingProgram.BillRecordBO.getnRebate())
																);
			BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getCreditCF() * -1);
		}
		
		BillingProgram.BillRecordBO.setTotalBill(BillingProgram.BillRecordBO.getTotalBill() - BillingProgram.BillRecordBO.getCreditBF());
		
		diff = Math.round(BillingProgram.BillRecordBO.getTotalBill());
		
		BillingProgram.BillRecordBO.setDiffAmt(diff);
		
		if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() > 0.00){
			BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + diff);
		}else{
			BillingProgram.BillRecordBO.setnTotalFixedTariff(BillingProgram.BillRecordBO.getnTotalFixedTariff() + diff);
		}
		
	}

	private void DLComputeBill() {
		// TODO Auto-generated method stub
		
		double TempRbt = 0.0;
		long temp_Units = 0;
		double diff = 0.0;
		
		System.out.println("creditbf = "+BillingProgram.BillRecordBO.getCreditBF());
		System.out.println("creditcf = "+BillingProgram.BillRecordBO.getCreditCF());
		
		try {
			
			ComputeEnergyTariff();
			if(BillingProgram.BillRecordBO.getFlagsBO().getSolar_rebate() == 'Y'){
				CalculateRebate();
			}
			
			ComputeFixedTariff();
			if(BillingProgram.BillRecordBO.getFlagsBO().getPH_Rebate() == 'Y'){
				BillingProgram.BillRecordBO.setPHRebate(ComputePHRebate());
				BillingProgram.BillRecordBO.setnRebate(BillingProgram.BillRecordBO.getnRebate() + BillingProgram.BillRecordBO.getPHRebate());
			}
			
			if(BillingProgram.BillRecordBO.getOrphnRbt() > 0.00){
				BillingProgram.BillRecordBO.setOrphnAmt(BillingProgram.BillRecordBO.getOrphnAmt() + CalculateOrphnRbt());
			}
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() != 'Y'){
				ComputeTax();
			}
			ComputeTotalAmount();
			
			BillingProgram.BillRecordBO.setCreditCF(0);
			
			if(BillingProgram.BillRecordBO.getTotalBill() - (BillingProgram.BillRecordBO.getCreditBF()/100.0 ) < 0){
				BillingProgram.BillRecordBO.setCreditCF(
											(BillingProgram.BillRecordBO.getCreditBF() / 100.0 )
											 - 
											 (BillingProgram.BillRecordBO.getTotalBill() - BillingProgram.BillRecordBO.getnRebate()));
				
				BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getCreditCF() * -1);
			}
			
			BillingProgram.BillRecordBO.setTotalBill(BillingProgram.BillRecordBO.getTotalBill() - BillingProgram.BillRecordBO.getCreditCF());
			
			BillingProgram.BillRecordBO.setTotalBill(BillingProgram.BillRecordBO.getTotalBill() 
													 + BillingProgram.BillRecordBO.getLessclaimed()
													 - BillingProgram.BillRecordBO.getMoreclaimed());
			
			BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getCreditCF() 
													+ BillingProgram.BillRecordBO.getMoreclaimed()
													- BillingProgram.BillRecordBO.getLessclaimed());
			
			System.out.println("credit cf : "+BillingProgram.BillRecordBO.getCreditCF());
			System.out.println("moreclaimed : "+BillingProgram.BillRecordBO.getMoreclaimed());
			System.out.println("lessclaimed : "+BillingProgram.BillRecordBO.getLessclaimed());
			
			diff = Math.round(BillingProgram.BillRecordBO.getTotalBill());
			BillingProgram.BillRecordBO.setDiffAmt(diff);
			
			if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() > 0.00){
				BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + diff);
			}else{
				BillingProgram.BillRecordBO.setnTotalFixedTariff(BillingProgram.BillRecordBO.getnTotalFixedTariff() + diff);
			}
			
			System.out.println("BF2 : "+BillingProgram.BillRecordBO.getCreditBF());
			System.out.println("CF2 : "+BillingProgram.BillRecordBO.getCreditCF());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
		
	}

	private void AdjustDL() {
		// TODO Auto-generated method stub
		int i = 0,j=0 , tempunits = 0;
		double energyamtandtax = 0.0;
		double temppartfraction = 0.0;
		double Units ,penaltyamt , wrongsolarrbt ;
		double wrongrbt , correctrbt,rbtlessclaimed,rbtmoreclaimed;
		double correctsolarrbt , solarrbtwrongclaimed , solarrbt;
		double wrongPHRebate , correctPHRebate , PHrebate , PHrebatewrongclaimed;
		double wrongRRRebate, correctRRRebate, RRRebate, RRRebatewrongclaimed;
		double wrongohrbt, corctohrbt, ohrbt, ohwrongrbt;
		double tempAvgConsumption;
		
		wrongRRRebate = correctRRRebate  = RRRebate = RRRebatewrongclaimed = 0.0;
		wrongPHRebate = correctPHRebate = PHrebate = 0.0;
		PHrebatewrongclaimed = 0.0;
		wrongrbt = correctrbt = rbtlessclaimed = rbtmoreclaimed = 0.0;
		Units = penaltyamt = wrongsolarrbt = 0.0;
		correctsolarrbt = solarrbtwrongclaimed =  solarrbt = 0.0;
		wrongohrbt = corctohrbt = ohrbt = ohwrongrbt = 0.0;
		tempAvgConsumption = 0.0;
		
		try {
			
			System.out.println("first flag : " +BillingProgram.BillRecordBO.getFirstflag() );
			
			tempAvgConsumption = BillingProgram.BillRecordBO.getAvg_Consumption();
			
			GetAverageUnits();
			
			BillingProgram.BillRecordBO.setnUnitsConsumed((long) BillingProgram.BillRecordBO.getAvg_Consumption());
			
			temppartfraction = BillingProgram.BillRecordBO.getPartFraction();
			
			if(BillingProgram.BillRecordBO.getFirstflag().charAt(0) != 'Y'){
				BillingProgram.BillRecordBO.setPartFraction(BillingProgram.BillRecordBO.getLastmonthfraction() / 30.0);
			}else{
				if(BillingProgram.BillRecordBO.getFirstflag().charAt(0) == 'Y'){
					BillingProgram.BillRecordBO.setPartFraction(30 / 30.0);
				}
			}
			
			System.out.println("Avg Consmp : "+BillingProgram.BillRecordBO.getAvg_Consumption());
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
				ComputeFreeLightingTempTax();
			}
			
			System.out.println("FL TAX : "+BillingProgram.BillRecordBO.getnTax());
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
				
				BillingProgram.BillRecordBO.setTemp_Tariff_Code(BillingProgram.BillRecordBO.getTariff_Code());
				BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(0.0);
				
				if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AI){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL1);
				}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AII){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL2);
				}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AIII){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL3);
				}
				ComputeEnergyTariff();
				BillingProgram.BillRecordBO.setnRebate(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
				BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_Tariff_Code());
				ComputeFixedTariff();
			}
			
			ComputeEnergyTariff();
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
				BillingProgram.BillRecordBO.setnRebate(
						BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
						-
						BillingProgram.BillRecordBO.getnRebate());
				
				BillingProgram.BillRecordBO.setnRebate(
						BillingProgram.BillRecordBO.getnRebate() 
						+ 
						BillingProgram.BillRecordBO.getnTotalFixedTariff());
				System.out.println("FL nrebate: "+BillingProgram.BillRecordBO.getnRebate());
			}

			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() != 'Y'){
				ComputeFixedTariff();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getPH_Rebate() == 'Y'){
				wrongPHRebate  = ComputePHRebate();
			}
			
			if(BillingProgram.BillRecordBO.getOrphnRbt() > 0.0){
				wrongohrbt = CalculateOrphnRbt();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getRural_Rebate() == 'Y'){
				CalculateRRRebate();
			}
			
			if(BillingProgram.BillRecordBO.getPLFlag().charAt(0) == 'P'){
				BillingProgram.BillRecordBO.setPLRebate(
						BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
						-
						BillingProgram.BillRecordBO.getPLRebate());
				
				wrongrbt = BillingProgram.BillRecordBO.getPLRebate();
				penaltyamt = BillingProgram.BillRecordBO.getPower_Factor() * BillingProgram.BillRecordBO.getnUnitsConsumed();
			}
			System.out.println("EC in Avg Cons = "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
					+ " penalty = "+penaltyamt);
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getSolar_rebate() == 'Y'){
				CalculateRebate();
				wrongsolarrbt = BillingProgram.BillRecordBO.getnRebate();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'Y'){
				
				BillingProgram.BillRecordBO.setnTax(
						(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + penaltyamt)
						* 
						(double)BillingProgram.tempMainBo.getTariffTax() / 100.0 );
			}
			
			if(BillingProgram.BillRecordBO.getCGexempt_flg().charAt(0) == 'Y'){
				BillingProgram.BillRecordBO.setnTax(0.0);
			}
			
			System.out.println("FL TAX : "+BillingProgram.BillRecordBO.getnTax());			
			
			
			BillingProgram.BillRecordBO.setEnergyamtplustax(
					  BillingProgram.BillRecordBO.getnTax() + penaltyamt
					- BillingProgram.BillRecordBO.getnRebate() - wrongrbt - wrongPHRebate 
					- wrongRRRebate - wrongohrbt
					+ BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
			
			System.out.println("partfraction : "+BillingProgram.BillRecordBO.getPartFraction());
			System.out.println("units : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			System.out.println("Avg EC : "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
			System.out.println("Avg tax : "+BillingProgram.BillRecordBO.getnTax());
			System.out.println("energy + tax in cons : "+BillingProgram.BillRecordBO.getEnergyamtplustax());
			System.out.println("energyplustax : "+ BillingProgram.BillRecordBO.getEnergyamtplustax());
			System.out.println("nrebate : "+(BillingProgram.BillRecordBO.getnRebate() 
					+ wrongPHRebate + wrongRRRebate + wrongohrbt + wrongrbt));
			
			GetUnitsConsumed();
			
			System.out.println("units cosumd 1 : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			
			GetPartUnitsInAdj(temppartfraction);
			
			System.out.println("units consmd 2 : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			
			System.out.println("units consmd in DL ADJ ACTUAL = "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'Y'){
				ComputeFreeLightingTempTax();
			}
			
			System.out.println("FL TAx : "+BillingProgram.BillRecordBO.getnTax());
			
			penaltyamt = BillingProgram.BillRecordBO.getPower_Factor() * BillingProgram.BillRecordBO.getnUnitsConsumed();
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
				
				BillingProgram.BillRecordBO.setTemp_Tariff_Code(BillingProgram.BillRecordBO.getTariff_Code());
				BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(0.0);
				
				if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AI){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL1);
				}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AII){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL2);
				}else if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2AIII){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL3);
				}
				ComputeEnergyTariff();
				BillingProgram.BillRecordBO.setnRebate(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
				BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_Tariff_Code());
				ComputeFixedTariff();
			}
			
			if(BillingProgram.BillRecordBO.getPLFlag().charAt(0) == 'P'){
				BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTPL);
				ComputeEnergyTariff();
				BillingProgram.BillRecordBO.setPLRebate(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
				BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_PLTariff_Code());
			}
			
			ComputeEnergyTariff();
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'R'){
				BillingProgram.BillRecordBO.setnRebate(
						BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() 
						- 
						BillingProgram.BillRecordBO.getnRebate());
				
				BillingProgram.BillRecordBO.setnRebate(
						BillingProgram.BillRecordBO.getnRebate() 
						+
						BillingProgram.BillRecordBO.getnTotalFixedTariff());
				
				correctsolarrbt = BillingProgram.BillRecordBO.getnRebate();
				
				System.out.println("FL nrebate : "+BillingProgram.BillRecordBO.getnRebate());
				
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() != 'Y'){
				ComputeEnergyTariff();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getPH_Rebate() == 'Y'){
				correctPHRebate = ComputePHRebate();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getRural_Rebate() == 'Y'){
				CalculateRRRebate();
				correctRRRebate = BillingProgram.BillRecordBO.getRR_Rebate();
			}
			
			if(BillingProgram.BillRecordBO.getPLFlag() == "P"){
				BillingProgram.BillRecordBO.setPLRebate(
						BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
						-
						BillingProgram.BillRecordBO.getPLRebate());
				correctrbt = BillingProgram.BillRecordBO.getPLRebate();
			}
			
			System.out.println("Correct rdbt : "+correctrbt +  " wrongrbt : "+wrongrbt);
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getSolar_rebate() == 'Y'){
				CalculateRebate();
				correctsolarrbt = BillingProgram.BillRecordBO.getnRebate();
			}
			
			if(BillingProgram.BillRecordBO.getOrphnRbt() > 0.0 ){
				corctohrbt = CalculateOrphnRbt();
			}
			
			if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() != 'Y'){
				BillingProgram.BillRecordBO.setnTax(
						(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + penaltyamt)
						* 
						(double)BillingProgram.tempMainBo.getTariffTax() / 100.0);
			}
			
			if(BillingProgram.BillRecordBO.getCGexempt_flg() == "Y"){
				BillingProgram.BillRecordBO.setnTax(0.0);
			}
			
			System.out.println("FL tax : "+BillingProgram.BillRecordBO.getnTax());
			
			
			energyamtandtax = BillingProgram.BillRecordBO.getnTax() + penaltyamt  
		        	 + BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
					 - BillingProgram.BillRecordBO.getnRebate()  - correctrbt 
					 - correctPHRebate  - correctRRRebate
					 - corctohrbt;
			
			System.out.println("energyplustax : "+energyamtandtax);
			
			System.out.println("partfraction : "+BillingProgram.BillRecordBO.getPartFraction());
			System.out.println("units : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			System.out.println("Avg EC : "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
			System.out.println("Avg tax : "+BillingProgram.BillRecordBO.getnTax());
			System.out.println("energy + tax in cons : "+BillingProgram.BillRecordBO.getEnergyamtplustax());
			System.out.println("energyplustax : "+ BillingProgram.BillRecordBO.getEnergyamtplustax());
			System.out.println("nrebate : "+(BillingProgram.BillRecordBO.getnRebate() 
					+ correctrbt + correctPHRebate + correctRRRebate + corctohrbt));
			
			
			if(energyamtandtax < BillingProgram.BillRecordBO.getEnergyamtplustax()){
				
				BillingProgram.BillRecordBO.setMoreclaimed(
						BillingProgram.BillRecordBO.getEnergyamtplustax() 
						- energyamtandtax );
			}else{
				BillingProgram.BillRecordBO.setLessclaimed(
						energyamtandtax 
						- 
						BillingProgram.BillRecordBO.getEnergyamtplustax() 
						);
			}
			
			if(BillingProgram.BillRecordBO.getFullmonflag() == "Y"){
				
				if(BillingProgram.BillRecordBO.getLastmonthfraction() == 30){
					BillingProgram.BillRecordBO.setMoreclaimed(
							BillingProgram.BillRecordBO.getMoreclaimed()
							*
							BillingProgram.BillRecordBO.getNumDL());
					
					BillingProgram.BillRecordBO.setLessclaimed(
							BillingProgram.BillRecordBO.getLessclaimed()
							*
							BillingProgram.BillRecordBO.getNumDL());
					
					BillingProgram.BillRecordBO.setnUnitsConsumed(
							BillingProgram.BillRecordBO.getnUnitsConsumed()
							*
							BillingProgram.BillRecordBO.getNumDL());
				}else{
					BillingProgram.BillRecordBO.setMoreclaimed(
							BillingProgram.BillRecordBO.getMoreclaimed()
							*
							(BillingProgram.BillRecordBO.getNumDL() - 1));
					
					BillingProgram.BillRecordBO.setLessclaimed(
							BillingProgram.BillRecordBO.getLessclaimed()
							*
							(BillingProgram.BillRecordBO.getNumDL() - 1));
					
					BillingProgram.BillRecordBO.setnUnitsConsumed(
							BillingProgram.BillRecordBO.getnUnitsConsumed()
							*
							(BillingProgram.BillRecordBO.getNumDL() - 1));
				}
			}
			
			System.out.println("DL moreclaimed : "+BillingProgram.BillRecordBO.getMoreclaimed());
			System.out.println("DL lessclaimed : "+BillingProgram.BillRecordBO.getLessclaimed());
			
			BillingProgram.BillRecordBO.setPartFraction(temppartfraction);
			BillingProgram.BillRecordBO.setAvg_Consumption(tempAvgConsumption);
			
			System.out.println("ADJ Units : "+BillingProgram.BillRecordBO.getnUnitsConsumed());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
	}

	private void GetPartUnitsInAdj(double temppartfraction) {
		// TODO Auto-generated method stub
		
		double Units = 0.0;
		double partround = 0.0;
		int thismonthfraction = 0;
		int days = 0;
		
		try{
			
			partround = temppartfraction * 30.0;
			partround = Math.round(temppartfraction);
			thismonthfraction = (int) partround;
			
			System.out.println("thismonthfraction : "+thismonthfraction);
			System.out.println("*************Inside getpartunitsandadj************");
			System.out.println("Num DL : "+BillingProgram.BillRecordBO.getNumDL());
			
			if(BillingProgram.BillRecordBO.getLastmonthfraction() != 30
					&& BillingProgram.BillRecordBO.getFirstflag().charAt(0) != 'Y'){
				
				if(thismonthfraction != 30){
					
					days = (int) (BillingProgram.BillRecordBO.getLastmonthfraction() + partround);
					days += ( 30 * (BillingProgram.BillRecordBO.getNumDL() - 1));
					System.out.println("dyas if partfractoin : "+days);
				}else{
					days = BillingProgram.BillRecordBO.getLastmonthfraction() + 30 * 
							BillingProgram.BillRecordBO.getNumDL() - 1;
				}
				
				Units = ((BillingProgram.BillRecordBO.getnUnitsConsumed() 
						* BillingProgram.BillRecordBO.getLastmonthfraction()) / days);
			
				BillingProgram.BillRecordBO.setnUnitsConsumed((long) Units);
				BillingProgram.BillRecordBO.setFirstflag("Y");
				
				System.out.println("units : "+Units);
				System.out.println("BillingProgram.BillRecordBO.units = "+BillingProgram.BillRecordBO.getnUnitsConsumed());
		
			}else{
				if(BillingProgram.BillRecordBO.getFirstflag().charAt(0) == 'Y'){
					
					if(thismonthfraction != 30){
						days  = (int) (BillingProgram.BillRecordBO.getLastmonthfraction() + partround) ;
						days += (30 * (BillingProgram.BillRecordBO.getNumDL() - 1));
						System.out.println("Days in partfraction : "+days);
					}else{
						days = 	BillingProgram.BillRecordBO.getLastmonthfraction() + 30 * 
								BillingProgram.BillRecordBO.getNumDL() ;
					}
					System.out.println("Total Days : "+days);
					
					Units  = ((BillingProgram.BillRecordBO.getnUnitsConsumed() * 30 ) / days ) ;
					BillingProgram.BillRecordBO.setnUnitsConsumed((long) Units);
					BillingProgram.BillRecordBO.setFullmonflag("Y");
				}
			}
			
			BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() + 0.05));
			
			System.out.println("units in above func : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
		
		
	}

	private void CalculateRebate() {
		// TODO Auto-generated method stub
		
		double nTemp = 0.0 , Solar_Rate = 50.0 , Solar_Units = 100.0;
		double Solar_Max_Amt = 0.0 , Solar_Units_Limit = 0.0 ;
		//double Get_Solar_Rate( char *billdt);
		
		Solar_Rate = DatabaseOperations.Get_Solar_Rate(BillingProgram.BILL_DATE);
		
		System.out.println("Solar Rate : "+Solar_Rate);
		
		Solar_Max_Amt = (Solar_Rate * BillingProgram.BillRecordBO.getPartFraction() );
		Solar_Units_Limit = (Solar_Units * BillingProgram.BillRecordBO.getPartFraction());
		
		System.out.println("solar max amt = "+ Solar_Max_Amt);
		System.out.println("solar_units_limit = "+Solar_Units_Limit);
		
		if(BillingProgram.BillRecordBO.getnUnitsConsumed() >= Solar_Units_Limit){
			nTemp = Solar_Max_Amt;
		}else{
			nTemp = (double) (Solar_Rate * BillingProgram.BillRecordBO.getnUnitsConsumed() / 100.0);
		}
		
		BillingProgram.BillRecordBO.setnRebate(nTemp);
		
		System.out.println("Rebate Amount : "+nTemp);
		
		
	}

	private void CalculateRRRebate() {
		// TODO Auto-generated method stub
		int i=0; 
		double rrrebate = 0.0;
		TariffRebateBO rebateBO = null ; 
		
		Iterator<TariffRebateBO> it  = BillingProgram.TariffRebateList.iterator();
		while(it.hasNext()){
			rebateBO = (TariffRebateBO)it.next();
			if(i == 4){
				break;
			}
			i++;
		}
		
		if(rebateBO != null){
			rrrebate = BillingProgram.BillRecordBO.getnTotalFixedTariff() *  rebateBO.getRebate() / 100.0; 
			BillingProgram.BillRecordBO.setRR_Rebate(rrrebate);
		}
	}

	private double CalculateOrphnRbt() {
		// TODO Auto-generated method stub
		double caprbt = 0.0;
		caprbt = BillingProgram.BillRecordBO.getnUnitsConsumed() * BillingProgram.BillRecordBO.getCapRbtAmt();
		return caprbt;
	}

	private double ComputePHRebate() {
		// TODO Auto-generated method stub
		
		double partround; 
		double rebate ,Erebate , Frebate ;
		rebate  = Erebate =  Frebate = 0.0 ;
		partround = 0.0;
		
		if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT3I ||
				BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT3II ||
					BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT3){
			Erebate = BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() * TariffConstant.PHREBATEPERCENT / 100.0;
			Erebate = BillingProgram.BillRecordBO.getnTotalFixedTariff() * TariffConstant.PHREBATEPERCENT / 100.0;
			Erebate += Erebate ;
		}else
		{  
			partround = BillingProgram.BillRecordBO.getPartFraction() * 30.0;
			partround = Math.round(partround);
			Erebate   = BillingProgram.BillRecordBO.getnUnitsConsumed() * 0.25 * partround / 30.0; 
		}

		return Erebate;
	}

	private void ComputeFreeLightingTempTax() {
		// TODO Auto-generated method stub
		
		BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_Tariff_Code());
		
		System.out.println("Inside TAx Calculation");
		
		ComputeEnergyTariff();
		ComputeFixedTariff();
		ComputeTax();
		
		System.out.println("Tax in FL is : "+BillingProgram.BillRecordBO.getnTax());
		
		if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AI){
			BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL1);
		}else if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AII){
			BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL2);
		}else if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AIII){
			BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL3);
		}
		
	}

	private void GetAverageUnits() {
		// TODO Auto-generated method stub
		
		double currentperc , actualunits , Units;
		currentperc = actualunits = Units = 0.0;
		
		try {
			
			if( BillingProgram.BillRecordBO.getCreaping_Perc() != 0 )
			{
				currentperc = 100 + BillingProgram.BillRecordBO.getCreaping_Perc();
				actualunits = ( BillingProgram.BillRecordBO.getnUnitsConsumed() * 100 )
								/ currentperc;
				BillingProgram.BillRecordBO.setAvg_Consumption(actualunits);
			}
			
			BillingProgram.BillRecordBO.setAvg_Consumption(
					BillingProgram.BillRecordBO.getAvg_Consumption()
					* BillingProgram.BillRecordBO.getCT_Ratio());
			
			
			if( BillingProgram.BillRecordBO.getFirstflag() == "N" 
					&&  BillingProgram.BillRecordBO.getLastmonthfraction() > 0 )
			{
				Units =  BillingProgram.BillRecordBO.getAvg_Consumption() / 30.0;
				BillingProgram.BillRecordBO.setAvg_Consumption(Units * BillingProgram.BillRecordBO.getLastmonthfraction()); 	
			} 
			
			System.out.println("ANG Consmp : "+BillingProgram.BillRecordBO.getAvg_Consumption());
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+ e.getMessage());
		}
		
	}

	private void ComputeTempoaryBill() {
		// TODO Auto-generated method stub
		
		double wmin = 0.0;	
		double tempUnits = 0.0;
		double minamt = 160.0;  		/* minimum amount */
		double rate = 0.0;
		double adjamt = 0.0;
		long units = 0L; 
		
		BillingProgram.BillRecordBO.setWeekMinAmt(0.0);
		
		System.out.println("In Temp Billing ...!");
		
		BillingProgram.BillRecordBO.setPartFraction(((BillingProgram.BillRecordBO.getPartFraction() * 30) + 1 ) / 30.0);
		
		GetUnitsConsumed();
		
		if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT7){
			
			ComputeEnergyTariff();
			
			wmin = DatabaseOperations.GetWeeklyMinimumAmount(minamt);
			
			System.out.println("wmin : "+wmin);
			ComputeEnergyTariff();
			
			BillingProgram.BillRecordBO.setWeekadjamt(adjamt);
			BillingProgram.BillRecordBO.setWeekMinAmt(wmin);
		}
		else{
			ComputeFixedTariff();
			ComputeEnergyTariff();
		}
		ComputeTax();
		ComputeTotalAmount();
	}

	private void ComputeTotalAmount() {
		// TODO Auto-generated method stub
		
		double penaltyamt ;
		double diff = 0.0;
		double  anamt = 0.0;
		lt4credit = 0.0;
		int i = 0;
		
		try {
			
			
			if( ( BillingProgram.BillRecordBO.getPower_Factor() * 100.0 ) > 0.0 )
			{
				penaltyamt = BillingProgram.BillRecordBO.getPower_Factor() 
		            	                    *
		            	                    BillingProgram.BillRecordBO.getnUnitsConsumed();
		   	}
		   	else
				penaltyamt = 0.0;


			BillingProgram.BillRecordBO.setTotalBill(
						(	BillingProgram.BillRecordBO.getnTotalFixedTariff() 
												+ 
									BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
												+
									BillingProgram.BillRecordBO.getnTax()
												+
									BillingProgram.BillRecordBO.getInt_Tax()
												+ 
									BillingProgram.BillRecordBO.getDmd_Arrears()
												+ 
									BillingProgram.BillRecordBO.getInt_Arrears ()
												+
									BillingProgram.BillRecordBO.getInt_Arrears2()	
												+ 
									BillingProgram.BillRecordBO.getTax_Arrears()
												+
									BillingProgram.BillRecordBO.getDelay_interest()
												+
									BillingProgram.BillRecordBO.getInt_on_tax()
												+
									BillingProgram.BillRecordBO.getDebitBF()
		                		   				+
									BillingProgram.BillRecordBO.getPenaltyonexcessload()
												+
									BillingProgram.BillRecordBO.getDiffAmt()
												+	
		           					penaltyamt 		
												+
									BillingProgram.BillRecordBO.getWeekMinAmt()
												+
									BillingProgram.BillRecordBO.getRR_Rebate()));
			
			if(BillingProgram.BillRecordBO.getAnnual_Min_Fix() > 0.0 &&
					BillingProgram.BillRecordBO.getCur_qrtr() == 4){
				
				BillingProgram.BillRecordBO.setPrevious_Demand1(
						BillingProgram.BillRecordBO.getPrevious_Demand1() 
						+
						BillingProgram.BillRecordBO.getPrevious_Demand2()
						+
						BillingProgram.BillRecordBO.getPrevious_Demand3());
				
				anamt = BillingProgram.BillRecordBO.getnTotalFixedTariff()
						+
						BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
						+
						BillingProgram.BillRecordBO.getPenaltyonexcessload()
						+
						BillingProgram.BillRecordBO.getPrevious_Demand1()
						+
						penaltyamt ;
				
				if(BillingProgram.BillRecordBO.getHP_Min_Fix() > anamt ){
					
					BillingProgram.BillRecordBO.setCreditCF(BillingProgram.BillRecordBO.getHP_Min_Fix() - anamt );
					
					if(BillingProgram.BillRecordBO.getCreditCF() < 0){
						BillingProgram.BillRecordBO.setCreditCF(0.0);
						
						//lt4credit = BillingProgram.BillRecordBO.getCreditCF();
					}
					lt4credit = BillingProgram.BillRecordBO.getCreditCF();
				}
			}
	
		System.out.println("Debit outside : "+lt4credit);	
		System.out.println("Total Bill : "+BillingProgram.BillRecordBO.getTotalBill());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
	}

	private void ComputeTax() {
		// TODO Auto-generated method stub
		
		int i;
		double penaltyamt;
		i = 0; 
		penaltyamt = 0.0;
		BillingProgram.BillRecordBO.setPFPenaltyamt(0.0);
		
		try{
			
			if((BillingProgram.BillRecordBO.getPower_Factor() * 100.0)  > 0 ){
				
				penaltyamt = BillingProgram.BillRecordBO.getPower_Factor() * BillingProgram.BillRecordBO.getnUnitsConsumed();
			}else{
				penaltyamt = 0.0;
			}
			
			System.out.println("penaltyamt : "+penaltyamt);
			System.out.println("Int_Arrears : "+BillingProgram.BillRecordBO.getInt_Arrears());
			System.out.println("Delay_Interest : "+BillingProgram.BillRecordBO.getDelay_interest());
			System.out.println("int_on_tax : "+BillingProgram.BillRecordBO.getInt_on_tax());
			System.out.println("no_tax_comp : "+BillingProgram.BillRecordBO.getNo_tax_comp());
			System.out.println("getP_nTotalEnergyTariff : "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
			
			penaltyamt = penaltyamt + BillingProgram.BillRecordBO.getReg_penalty();
			
			BillingProgram.BillRecordBO.setnTax(( 	
					
			BillingProgram.BillRecordBO.getnTotalFixedTariff() 
					+ 
	      	BillingProgram.BillRecordBO.getP_nTotalEnergyTariff()
					+
	      	penaltyamt 
					+ 
			/*BillingProgram.BillRecordBO.getInt_Arrears() 
					+
			BillingProgram.BillRecordBO.getDelay_interest() 
					+*/
			BillingProgram.BillRecordBO.getInt_on_tax() 
					+
				BillingProgram.BillRecordBO.getPenaltyonexcessload() 
					+
			BillingProgram.BillRecordBO.getDebitBF() 
					+ 
			BillingProgram.BillRecordBO.getNo_tax_comp()
					+ 
	    	BillingProgram.BillRecordBO.getWeekMinAmt()
					+
			BillingProgram.BillRecordBO.getRR_Rebate()) 
					*
			(((double)BillingProgram.tempMainBo.getTariffTax()) / 100.0 ));	
			
			if(BillingProgram.BillRecordBO.getWeekadjamt() > 0.0){
				BillingProgram.BillRecordBO.setnTax(
						
						BillingProgram.BillRecordBO.getnTax() + 
						(BillingProgram.BillRecordBO.getWeekadjamt() 
								* 
								( ((double)BillingProgram.tempMainBo.getTariffTax())/ 100.0)));
			}
			
			if(BillingProgram.BillRecordBO.getCGexempt_flg().charAt(0) == 'Y'  ||
					BillingProgram.BillRecordBO.getCGexempt_flg().charAt(0) == 'y'){
				
				BillingProgram.BillRecordBO.setnTax(0.0);
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
		
		
		
		System.out.println("Tax amount : "+BillingProgram.BillRecordBO.getnTax());
		
		
	}

	private void ComputeFixedTariff() {
		// TODO Auto-generated method stub
		
		System.out.println("Inside Fixed Charges .........................");
		int i,j;
		long nUnits ;
		long KW ;
		double Adj;
		double flkw,partround , tempKW , KWRound,tempHPRound, tempfloat,tempKWRound;
		partround = 0.0;
		tempKW = 0.0;
		KWRound = tempHPRound = 0.0;
		Adj = 0.0;
		tempfloat = 0.0;
		
		FixCompInfoTemp fixCompInfoTemp = new FixCompInfoTemp(); 
		
		BillingProgram.BillRecordBO.setnTotalFixedTariff(0.0);
		BillingProgram.BillRecordBO.setP_nCompFixInfoCounter(0);
		tempHPRound = BillingProgram.BillRecordBO.getHP_Round();
		BillingProgram.BillRecordBO.setHP_Min_Fix(0.0);
		
		tempKWRound = BillingProgram.BillRecordBO.getKW_Round();
		
		if(BillingProgram.tempMainBo.getTariffPowerUnit().charAt(0) == 'K'){
			
			flkw = ((double) BillingProgram.BillRecordBO.getHP_Round()) / 100.0;    
			flkw = (flkw * 0.746) * 100.0;
			BillingProgram.BillRecordBO.setKW_Round(BillingProgram.BillRecordBO.getKW_Round() + (long) flkw);
		}else{
			if(BillingProgram.tempMainBo.getTariffPowerUnit().charAt(0) == 'H'){
				
				
				flkw =((double) BillingProgram.BillRecordBO.getKW_Round()) / 100.0;    
				flkw = (flkw / 0.746) * 100.0;
				 BillingProgram.BillRecordBO.setHP_Round( BillingProgram.BillRecordBO.getHP_Round() + (long) flkw);
				 BillingProgram.BillRecordBO.setKW_Round( BillingProgram.BillRecordBO.getHP_Round());
				
				
			}
		}
		
		KW = (long)  BillingProgram.BillRecordBO.getKW_Round();
		System.out.println("Kw : "+KW);
		
		i = 0;
		if( KW > 0L )
		{
			if( (KW % 25L) > 0 )
			{
				j = (int) (KW % 25L);
				if( j > 12 )
					j = 25;
				else
					j = 0;
				KW = (KW - (KW % 25L)) + (long) j; 
			}

			if( KW  < 100L )
				KW = 100L;
		}	
		tempKW = KW / 100.0 ;
		
		System.out.println("KW : "+KW);
		System.out.println("tempKw : "+tempKW);
		
		BillingProgram.BillRecordBO.setHP_Min_Fix(BillingProgram.BillRecordBO.getAnnual_Min_Fix() * tempKW);
		
		System.out.println("hp_min : "+ BillingProgram.BillRecordBO.getHP_Min_Fix());
		
		System.out.println("annual Min fix : "+BillingProgram.BillRecordBO.getAnnual_Min_Fix());
		
		ArrayList<FixedChargeList> al = new ArrayList<FixedChargeList>();
		 
		 for(int iloop = 0; iloop < 6; iloop++)
			{
			 FixedChargeList fixedList = new FixedChargeList();
			// CompInfoTemp compInfoTemp =  new CompInfoTemp();
			 
			 fixedList.setUnits(0);
			 fixedList.setRate(0);
			// compInfoTemp.setUnits(0L);
			// compInfoTemp.setRate(0);
		        
			 al.add(fixedList);
		     
			}
		 
		 BillingProgram.BillRecordBO.setFixedChargeListBO(al);
		 
		 partround =  BillingProgram.BillRecordBO.getPartFraction() * 30;
			KWRound = KW * partround / 30.0;
			KWRound += 0.05;

			KW =(long) KWRound;
			
			System.out.println(" KW : "+KW);
			
			 if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT4B ||
					 BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT4CI ||
					 BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT4CII ){
				 
				 if(BillingProgram.BillRecordBO.getCapacitor() > 0.0 ){
					 
				 }else{
					 BillingProgram.BillRecordBO.setRR_Rebate(KW / 100.0 * 15.0);
				 }
			 }
			 
		 
			 ArrayList<FixedChargeList> fixedList = new ArrayList<FixedChargeList>();
			 
		 if(BillingProgram.TariffFixList != null){
			 
			 Iterator<TariffFixBO> fixed_it = BillingProgram.TariffFixList.iterator();
			 
			 while(fixed_it.hasNext()){
				 
				 TariffFixBO tariffix = (TariffFixBO) fixed_it.next();
						 
				 if(tariffix.getTariffCode() == BillingProgram.BillRecordBO.getTariff_Code()){
					 
					 if(tariffix.getTo_Units() != -1){
						 
						 nUnits = (long) ((tariffix.getTo_Units() - tariffix.getFrom_Units() ) * BillingProgram.BillRecordBO.getPartFraction() * 100);   
					 }else{
						 nUnits = KW;
					 }
					 
					 KW = KW -nUnits;
		    			
		    			FixedChargeList fixed =  new FixedChargeList();
			    	    
		    	
			    	    fixCompInfoTemp.setUnits(nUnits);
			    	    fixCompInfoTemp.setRate((long)tariffix.getTariff_Amount());
			    	    
			    	    fixed.setUnits(fixCompInfoTemp.getUnits());
			    	    fixed.setRate((long)fixCompInfoTemp.getRate());
			    	    
			    	    //System.out.println("nUnits : "+nUnits);
			    	    //System.out.println("long)tariffix.getTariff() : "+(long)tariffix.getTariff_Amount());
			    	    
			    	    
			    	    //System.out.println("fixCompInfoTemp.getUnits() : "+fixCompInfoTemp.getUnits());
			    	    //System.out.println("(long)fixCompInfoTemp.getRate() : "+(long)fixCompInfoTemp.getRate());
			    	    fixCompInfoTemp.setAmount((double)nUnits * (double)tariffix.getTariff_Amount());
			    	    fixCompInfoTemp.setAmount(fixCompInfoTemp.getAmount() / 10000.0);
			    	    fixCompInfoTemp.setAmount(fixCompInfoTemp.getAmount());
			    	    
			    	    fixed.setAmount((double)nUnits * (double)tariffix.getTariff_Amount());
			    	    fixed.setAmount(fixCompInfoTemp.getAmount() / 10000.0);
			    	    fixed.setAmount(fixCompInfoTemp.getAmount());
			    	    
			    	    System.out.println(fixed.getUnits() + " ~ "+fixed.getRate() + " ~ "+fixed.getAmount());
			    	    
			    	    BillingProgram.BillRecordBO.setnTotalFixedTariff(BillingProgram.BillRecordBO.getnTotalFixedTariff() + fixCompInfoTemp.getAmount());
			    	    
			    	   // FixedSlabCount ++;
			    	    
			    	    fixedList.add(fixed);
				   }
  			    }
			 }
			 
			 
			 BillingProgram.BillRecordBO.setFixedChargeListBO(fixedList);
			 
			 for(int k = 0;k< BillingProgram.BillRecordBO.getFixedChargeListBO().size();k++){
				 FixedChargeList fc = (FixedChargeList)BillingProgram.BillRecordBO.getFixedChargeListBO().get(k);
				 
				 System.out.println("rate : "+fc.getRate() + " units : "+fc.getUnits() + "amt : "+fc.getAmount());
				 
			 }
			 
			 ArrayList<FixedChargeList> fixedList2 = new ArrayList<FixedChargeList>();
			 
			 if(BillingProgram.tempMainBo != null){
		    	
		    	if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2B || 
		    			BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2BI || 
		    					BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT2BII ){
		    		
		    	
		    		FixedChargeList fixedChargeList = new FixedChargeList();
		            
		            tempKW =  tempKW  * fixCompInfoTemp.getRate() / 100 ;
		            if(tempKW < BillingProgram.tempMainBo.getMinFixed()){
		            	
		            	Adj = (partround *  100 ) / 30.0 ;
		            	Adj = Adj + 0.05 ;
		            	
		            	fixCompInfoTemp.setUnits((long) Adj);
		            	
		            	fixCompInfoTemp.setAmount(BillingProgram.tempMainBo.getMinFixed() * fixCompInfoTemp.getUnits() / 100.0);
		            	
		            	BillingProgram.BillRecordBO.setnTotalFixedTariff(fixCompInfoTemp.getAmount());
		            	
		            	fixCompInfoTemp.setRate(BillingProgram.tempMainBo.getMinFixed() * 100);
		            	
		            	fixedChargeList.setUnits(fixCompInfoTemp.getUnits());
		            	
		            	fixedChargeList.setRate(fixCompInfoTemp.getRate());
		            	
		            	fixedChargeList.setAmount(BillingProgram.tempMainBo.getMinFixed() * fixCompInfoTemp.getUnits() / 100.0);
		            	
		            	fixedList2.add(fixedChargeList);
		            	
		            	BillingProgram.BillRecordBO.setFixedChargeListBO(fixedList2);
		            }
		            		
		            
		    	}
		    	else{
					 
					 if(BillingProgram.BillRecordBO.getAnnual_Min_Fix() > 0.0 
							 && BillingProgram.BillRecordBO.getMtr_Sts().equals("D") ){
						 
						 FixedChargeList fixedChargeList = new FixedChargeList();
						 
						 fixCompInfoTemp.setAmount(0.0);
						 
						 tempfloat = BillingProgram.BillRecordBO.getAnnual_Min_Fix() / 12.0 ;
						 
						 fixCompInfoTemp.setRate((long) (tempfloat * 3));
						 fixCompInfoTemp.setAmount(fixCompInfoTemp.getUnits() * fixCompInfoTemp.getRate()) ;
						 fixCompInfoTemp.setAmount(fixCompInfoTemp.getAmount() / 100.00);
						 
						 fixedChargeList.setUnits(fixCompInfoTemp.getUnits());
						 fixedChargeList.setRate(fixCompInfoTemp.getRate());
						 fixedChargeList.setAmount(fixCompInfoTemp.getAmount());
						 
						 BillingProgram.BillRecordBO.setnTotalFixedTariff(fixCompInfoTemp.getAmount());
						 fixedList2.add(fixedChargeList);
						 
						 BillingProgram.BillRecordBO.setFixedChargeListBO(fixedList2);
					 }
					 
					 
					 BillingProgram.BillRecordBO.setKW_Round(tempKWRound);
					 BillingProgram.BillRecordBO.setHP_Round(tempHPRound);
				 }
		    	
			 }
			 
	}

	private void ComputeEnergyTariff() {
		// TODO Auto-generated method stub
		
		System.out.println("Inside Energy Charges .........................");
		
		long nUnits = 0L , nUnitsConsumed = 0L ;
		int i = 0 , j , start = 0,unitsinslab = 0 ;
		double diff,partmul;
		double partround;
		partround = 0.0;
		
		BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(0.0);
		BillingProgram.BillRecordBO.setP_nComputedInfoCounter(0);
		
		nUnitsConsumed = (long) BillingProgram.BillRecordBO.getnUnitsConsumed();
		
		ArrayList<EnergyChargeList> al = new ArrayList<EnergyChargeList>();
		 
		 for(int iloop = 0; iloop < 6; iloop++)
			{
			 EnergyChargeList EnergyList = new EnergyChargeList();
			// CompInfoTemp compInfoTemp =  new CompInfoTemp();
			 
			 EnergyList.setUnits(0);
			 EnergyList.setRate(0);
			// compInfoTemp.setUnits(0L);
			// compInfoTemp.setRate(0);
		        
			 al.add(EnergyList);
		     
			}
		 
		 BillingProgram.BillRecordBO.setEnergyChargeListBO(al);
		 
		 partround = Math.round(BillingProgram.BillRecordBO.getPartFraction() * 30.0);
		 
		 i = j = 0 ;
		 
		 ArrayList<EnergyChargeList> alenergy1 = new ArrayList<EnergyChargeList>(); 
		 
		 if(BillingProgram.TariffSlabList != null){
			 
			 Iterator<TariffSlabBO> it_slabs = BillingProgram.TariffSlabList.iterator();
			 
			 while(it_slabs.hasNext()){
				 
				 TariffSlabBO SlabBO = (TariffSlabBO) it_slabs.next();
				 
				 if(SlabBO.getTariffCode() == BillingProgram.BillRecordBO.getTariff_Code()){
					// System.out.println("partround : "+partround);
					 
					 unitsinslab = (int) ((SlabBO.getToUnits() - SlabBO.getFromUnits()) * partround  / 30) ;
					 
					// System.out.println(SlabBO.getToUnits() + "," + SlabBO.getFromUnits() + "," + (SlabBO.getToUnits() - SlabBO.getFromUnits())+" , unitslab = " + unitsinslab);
					 
					 if(SlabBO.getToUnits() != -1 && nUnitsConsumed > unitsinslab ){
						 
						 if(SlabBO.getFromUnits() == 0){
							 diff = (SlabBO.getToUnits() - SlabBO.getFromUnits()) * partround / 30.0;
						 }else{
							 diff = (SlabBO.getToUnits() - SlabBO.getFromUnits() + 1) * partround / 30.0;
						 }
						 
						 nUnits = (long) diff;
					 }
					 else{
						 
						 nUnits = nUnitsConsumed ;
						 
					 }
						 
						 nUnitsConsumed = nUnitsConsumed - nUnits;
						 
						 CompInfoTemp compInfoTemp =  new CompInfoTemp();
						 EnergyChargeList EnergyList = new EnergyChargeList();
						 
						 compInfoTemp.setUnits(nUnits);
						 EnergyList.setUnits(nUnits);
						 compInfoTemp.setRate(SlabBO.getTariffAmount());
						 EnergyList.setRate(compInfoTemp.getRate());
						 compInfoTemp.setAmount(((double)nUnits *  (double)SlabBO.getTariffAmount()) / 100.0);
						 EnergyList.setAmount(((double)nUnits *  (double)SlabBO.getTariffAmount()) / 100.0);
						 BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() + compInfoTemp.getAmount());
					 
						 SlabBO.setTariffAmount((int) compInfoTemp.getRate());
						 
						 j++;
						 
						 //System.out.println(EnergyList.getUnits() + " ~ "+EnergyList.getRate() + " ~ "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
						 System.out.println(EnergyList.getUnits() + " ~ "+EnergyList.getRate() + " ~ "+EnergyList.getAmount());
						 
						 alenergy1.add(EnergyList);
				 }
			 }
			 
			 BillingProgram.BillRecordBO.setEnergyChargeListBO(alenergy1);
			 
			 BillingProgram.BillRecordBO.setP_nComputedInfoCounter(j);
			 
			 System.out.println("P_nComputedInfoCounter  :  "+BillingProgram.BillRecordBO.getP_nComputedInfoCounter());
		 }
		
		 if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT1A){
			 
			 if(BillingProgram.BillRecordBO.getP_nTotalEnergyTariff() < (30.0 * (partround / 30))){
				 
				 BillingProgram.BillRecordBO.setWeekMinAmt(30.0 * (partround / 30 ));
				 BillingProgram.BillRecordBO.setP_nTotalEnergyTariff(0.0);
				 
				 for(int iloop = 0; iloop < 6; iloop++)
					{
					 EnergyChargeList EnergyList = new EnergyChargeList();
					 
					 EnergyList.setUnits(0);
					 EnergyList.setRate(0);
				        
					 al.add(EnergyList);
					}
				 BillingProgram.BillRecordBO.setEnergyChargeListBO(al);
				 
			 }
		 }
		 
		 System.out.println("Total Energy Charge  :  "+BillingProgram.BillRecordBO.getP_nTotalEnergyTariff());
		 System.out.println("No Of Slabs  :  "+BillingProgram.BillRecordBO.getP_nComputedInfoCounter());
		
		
	}

	private void GetUnitsConsumed() {
		// TODO Auto-generated method stub
		
		float actualunits = 0;
		int currentperc = 0 ;
		long longunits = 0L;
		double partround = 0.0 , Diff = 0.0;
		
		System.out.println("Presentr Resding : "+ BillingProgram.BillRecordBO.getPrst_Rdg());
		System.out.println("Mtr_sts : "+ BillingProgram.BillRecordBO.getMtr_Sts());
		
		//(char)50 : 2
		//(char)52 : 4
		//(char)49 : 1
		if(BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)52
				||BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)50
					||BillingProgram.BillRecordBO.getInst_Typ().charAt(0) == (char)49){
			
			System.out.println("PREVCHWH : "+ BillingProgram.BillRecordBO.getPrevCKWH() + " ,RECBKWH : "
								+ BillingProgram.BillRecordBO.getRecordedBKWH()+ " ,PrstCKWH : "+BillingProgram.BillRecordBO.getPrstCKWH());
			
			
			if((BillingProgram.BillRecordBO.getPrevCKWH() + BillingProgram.BillRecordBO.getRecordedBKWH()) 
					<  BillingProgram.BillRecordBO.getPrstCKWH()){
				
				if(BillingProgram.BillRecordBO.getMtr_Sts().equals("M") || BillingProgram.BillRecordBO.getMtr_Sts().equals("D")){
					
					BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getAvg_Consumption() * BillingProgram.BillRecordBO.getCT_Ratio()));
				}else{
					BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getPrst_Rdg() * BillingProgram.BillRecordBO.getCT_Ratio()));
				}
				
				System.out.println("Units Consumed : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
			
			}else{
				
				DatabaseOperations.LogErrorIntoBILL_GEN_ERR(BillingProgram.BillRecordBO.getBill_Date(),
						BillingProgram.BillRecordBO.getReader_Code(), BillingProgram.BillRecordBO.getRr_No(), 
						"Billing Program", "ETV METER READING IS WRONG BILL WILL NOT BE GENERATED");
				
				System.out.println("nUnitsConsumed : "+BillingProgram.BillRecordBO.getnUnitsConsumed());
				System.out.println("Prst_Rdg :"+BillingProgram.BillRecordBO.getPrst_Rdg());
				System.out.println("CT_Ratio : "+BillingProgram.BillRecordBO.getCT_Ratio());
				
				BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getPrst_Rdg() * BillingProgram.BillRecordBO.getCT_Ratio()));
			}
				
			if(BillingProgram.BillRecordBO.getCreaping_Perc() != 0){
				currentperc = 100 + BillingProgram.BillRecordBO.getCreaping_Perc();
				actualunits = (BillingProgram.BillRecordBO.getnUnitsConsumed() * 100 ) / currentperc;
				
				BillingProgram.BillRecordBO.setnUnitsConsumed((long)actualunits);
				
			}
			
			BillingProgram.BillRecordBO.setnUnitsConsumed(BillingProgram.BillRecordBO.getnUnitsConsumed() + BillingProgram.BillRecordBO.getOld_Mtr_Rdg());
			return;
			
		}
		
		if(BillingProgram.BillRecordBO.getInstallation_Status() == 10 ||
				BillingProgram.BillRecordBO.getInstallation_Status() == 2)
				{
					BillingProgram.BillRecordBO.setPrst_Rdg(BillingProgram.BillRecordBO.getPrev_Mtr_Rdg());
			
					if(BillingProgram.BillRecordBO.getMtr_Sts().equals("M") || 
						BillingProgram.BillRecordBO.getMtr_Sts().equals("D")){
						
						partround = BillingProgram.BillRecordBO.getPartFraction() * 30;
						partround = (double)partround;
						
						if(BillingProgram.BillRecordBO.getNumDL() != 0 ){
							BillingProgram.BillRecordBO.setnUnitsConsumed((long) ((BillingProgram.BillRecordBO.getAvg_Consumption() * partround) 
									/ BillingProgram.BillRecordBO.getLastmonthfraction()));
						}else{
							BillingProgram.BillRecordBO.setnUnitsConsumed((long) ((BillingProgram.BillRecordBO.getAvg_Consumption() * partround) 
									/ 30.0));
						}
						
						BillingProgram.BillRecordBO.setnUnitsConsumed((long) ((BillingProgram.BillRecordBO.getAvg_Consumption() * partround) 
								/ 30.0));
						
						System.out.println("AVG in DL OR MNR **** =**** " + BillingProgram.BillRecordBO.getnUnitsConsumed());
						
						 BillingProgram.BillRecordBO.setPrst_Rdg(0);
					}
				}else{
					
					if( BillingProgram.BillRecordBO.getPrst_Rdg() <  BillingProgram.BillRecordBO.getPrev_Mtr_Rdg()){
						
						if(( BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() <= 999) 
								&& ( BillingProgram.BillRecordBO.getPrst_Rdg() <= 999)){
							
							 BillingProgram.BillRecordBO.setnUnitsConsumed(
									 1000 -  BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() +  BillingProgram.BillRecordBO.getPrst_Rdg());
						}else{
							
							if(( BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() <= 9999) 
									&& ( BillingProgram.BillRecordBO.getPrst_Rdg() <= 9999)){
								
								 BillingProgram.BillRecordBO.setnUnitsConsumed(
										 10000 -  BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() +  BillingProgram.BillRecordBO.getPrst_Rdg());
							}else{
								if(( BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() <= 99999l) 
										&& ( BillingProgram.BillRecordBO.getPrst_Rdg() <= 99999l)){
									
									 BillingProgram.BillRecordBO.setnUnitsConsumed(
											 100000l -  BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() +  BillingProgram.BillRecordBO.getPrst_Rdg());
								}else{
									 BillingProgram.BillRecordBO.setnUnitsConsumed(
											 1000000l -  BillingProgram.BillRecordBO.getPrev_Mtr_Rdg() +  BillingProgram.BillRecordBO.getPrst_Rdg());
								}
							}
						}
						
					}else{
						BillingProgram.BillRecordBO.setnUnitsConsumed(BillingProgram.BillRecordBO.getPrst_Rdg() - BillingProgram.BillRecordBO.getPrev_Mtr_Rdg()  );
					}
					
				}
		
		BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() * BillingProgram.BillRecordBO.getCT_Ratio()));
					
		System.out.println("Units after rounding : "+BillingProgram.BillRecordBO.getnUnitsConsumed());	
		
		if(BillingProgram.BillRecordBO.getCreaping_Perc() != 0){
			currentperc = 100 + BillingProgram.BillRecordBO.getCreaping_Perc();
			actualunits = (BillingProgram.BillRecordBO.getnUnitsConsumed() * 100 ) / currentperc;
			
			BillingProgram.BillRecordBO.setnUnitsConsumed((long)actualunits);
		}
		
		if(BillingProgram.BillRecordBO.getDC_Flg().equals("Y")){
			GetDCUnits();
		}else{
			
			if(BillingProgram.BillRecordBO.getFirst_Rdg_Flg().equals("Y")){
				
				if(BillingProgram.BillRecordBO.getMtr_Sts().equals("D") || 
						BillingProgram.BillRecordBO.getMtr_Sts().equals("M")){
					GetDCUnits();
				}
			}
		}
		
		BillingProgram.BillRecordBO.setOld_Mtr_Rdg((long) (BillingProgram.BillRecordBO.getOld_Mtr_Rdg() * BillingProgram.BillRecordBO.getCT_Ratio()));
		BillingProgram.BillRecordBO.setnUnitsConsumed(BillingProgram.BillRecordBO.getnUnitsConsumed() + BillingProgram.BillRecordBO.getOld_Mtr_Rdg());
		longunits = (long)BillingProgram.BillRecordBO.getnUnitsConsumed();
		Diff      = BillingProgram.BillRecordBO.getnUnitsConsumed() - (double)longunits;
		if(Diff != 0.50){
			
		}else{
			BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() + Diff));
		}
		
		partround = BillingProgram.BillRecordBO.getPartFraction() * 30;
		
		partround = partround /  30.0 ;
		
		longunits = 0;
		longunits = (long)partround * 18 ;
		
		System.out.println("others : " + BillingProgram.BillRecordBO.getOthers());
		
		if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT1A
				&& BillingProgram.BillRecordBO.getnUnitsConsumed() > longunits){
			
			if(BillingProgram.BillRecordBO.getOthers() < 1.0 ){
				BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LT2AI);
			}else{
				if(BillingProgram.BillRecordBO.getOthers() > 1.0){
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LT2AII);
				}else{
					BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LT2AI);
				}
			}
		}
			
		System.out.println("Units after adding old con = "+BillingProgram.BillRecordBO.getnUnitsConsumed());	
			///
	
			
			///
			
			
		
		
		
		
	}

	private void GetDCUnits() {
		// TODO Auto-generated method stub
		
		int i,j;
		double retUnits = 0; //added by nadeem to store DCunits for LT4
		double flkw,Units,partround,tempHPRound,tempKW_Round;
		long KW=0;
		i = j = 0;
		flkw = Units = partround = tempHPRound = 0.0;
		
		System.out.println("Inside DC Units");
		
		tempHPRound = (float) BillingProgram.BillRecordBO.getHP_Round();
		partround = (float) (BillingProgram.BillRecordBO.getPartFraction() * 30.0);
		
		tempKW_Round = (float) BillingProgram.BillRecordBO.getKW_Round();
		
		if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'Y'){
			BillingProgram.BillRecordBO.setTariff_Code(BillingProgram.BillRecordBO.getTemp_Tariff_Code());
		}
		
		if(BillingProgram.tempMainBo.getTariffPowerUnit().charAt(0) == 'K'){
			flkw  = ((double)BillingProgram.BillRecordBO.getHP_Round()) / 100.0;
			flkw  = (flkw * 0.746) * 100.0;
			BillingProgram.BillRecordBO.setKW_Round(BillingProgram.BillRecordBO.getKW_Round() + (long)flkw);
		}else{
			if(BillingProgram.tempMainBo.getTariffPowerUnit().charAt(0) == 'H'){
				flkw  = ((double)BillingProgram.BillRecordBO.getKW_Round()) / 100.0 ;
				flkw  = (flkw * 0.746) * 100.0 ;
				BillingProgram.BillRecordBO.setHP_Round(BillingProgram.BillRecordBO.getHP_Round() + (long)flkw);
				BillingProgram.BillRecordBO.setKW_Round(BillingProgram.BillRecordBO.getHP_Round());
			}
		}
		
		KW = (long) BillingProgram.BillRecordBO.getKW_Round();
		
		if( KW > 0L )
		{
			if( (KW % 25L) > 0 )
			{
				j = (int) (KW % 25L);
				if( j > 12 )
					j = 25;
				else
					j = 0;
				KW = (KW - (KW % 25L)) + (long) j; 
			}
		}
		
		System.out.println("Inside DC units ****************  KW : "+KW);
		
		Units = KW * BillingProgram.tempMainBo.getDCUnits() / 	10000.0 ;
		Units = Units * partround / 30.0;
		BillingProgram.BillRecordBO.setnUnitsConsumed((long) Units);
		BillingProgram.BillRecordBO.setnUnitsConsumed((long) (BillingProgram.BillRecordBO.getnUnitsConsumed() * BillingProgram.BillRecordBO.getCT_Ratio()));
		
		//For Unmetered Pre-Dominant IP
		if(BillingProgram.METER_CODE.startsWith("PIP", 3)){
			
			retUnits = DatabaseOperations.Get_LT4_PDM_UNits(BillingProgram.BILL_DATE,BillingProgram.BillRecordBO.getRr_No());
			
			if(absDouble((retUnits - Units))  > 0.01){
				BillingProgram.BillRecordBO.setnUnitsConsumed((long) ((retUnits  * BillingProgram.BillRecordBO.getHP_Round()) / 100.0));
			}
			
		}
		
		System.out.println("DC Units after mul with CT  = "+BillingProgram.BillRecordBO.getnUnitsConsumed());
		
		BillingProgram.BillRecordBO.setKW_Round(tempKW_Round);
		BillingProgram.BillRecordBO.setHP_Round(tempHPRound);
		
		if(BillingProgram.BillRecordBO.getFlagsBO().getFL_Rebate() == 'Y'){
			if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AI){
				BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL1);
			}else if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AII){
				BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL2);
			}else if(BillingProgram.BillRecordBO.getTemp_Tariff_Code() == TariffConstant.LT2AIII){
				BillingProgram.BillRecordBO.setTariff_Code(TariffConstant.LTFL3);
			}
		}
		
		if(BillingProgram.BillRecordBO.getTariff_Code() == TariffConstant.LT1A){
			
			partround = BillingProgram.BillRecordBO.getPartFraction() * 30.0;
			
			Units = BillingProgram.tempMainBo.getDCUnits() / 100.0 ;
			Units = Units * partround / 30.0 ;
			BillingProgram.BillRecordBO.setnUnitsConsumed((long)Units);
		}
		
	}

	private double absDouble(double d) {
		// TODO Auto-generated method stub
		return (d < 0.0 ) ? (d) * -1 : d ;
	}

	void initglobals()
	{
		BillingProgram.BillRecordBO.setPLRebate(0.0); 
		BillingProgram.BillRecordBO.setCapRbtAmt(0.0); 
		BillingProgram.BillRecordBO.setOrphnAmt(0.0); 
		BillingProgram.BillRecordBO.setPHRebate(0.0); 
		BillingProgram.BillRecordBO.setnRebate(0.0); 
		BillingProgram.BillRecordBO.setWeekMinAmt(0.0); 
		BillingProgram.BillRecordBO.setWeekadjamt(0.0); 
		BillingProgram.BillRecordBO.setLt1_rbt_rate(0.0); 
		BillingProgram.BillRecordBO.setRR_Rebate(0.0); 
		BillingProgram.BillRecordBO.setFlRebate(0.0) ;
	}

	public int get_current_reading(String cURRENT_RRNO, String bILL_DATE,
			int pRESENT_READING) {
		// TODO Auto-generated method stub
		return DatabaseOperations.GetCurrentReading(cURRENT_RRNO,bILL_DATE,pRESENT_READING);
	}

	public void LoadUploadRecord(char status) {
		// TODO Auto-generated method stub
		
		try {
			BillingProgram.con.setAutoCommit(false);
			
			DatabaseOperations.MoveFromHHD2HUDRec(status);
			
			System.out.println("Inside LoadUploadRecord after move .....");
			
			int v = DatabaseOperations.InsertIntoHHDUploadData();  //AEH1102 , 18/02/2015,2110105
			
			if(v > 0){
				System.out.println("Inserted Upload Record for RRno : "+BillingProgram.CURRENT_RRNO);
			}
			
			int u = DatabaseOperations.UploadHHDDownloadData(status);
			
			if(u > 0){
				System.out.println("HHD Downlaod Data Updated for RRno : "+BillingProgram.CURRENT_RRNO);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

	public void UploadBillMaster() {
		// TODO Auto-generated method stub
		DatabaseOperations.UploadBillMaster();
	}
	
}
