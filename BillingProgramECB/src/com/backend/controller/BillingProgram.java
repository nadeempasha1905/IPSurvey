/**
 * 
 */
package com.backend.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.backend.dao.DatabaseOperations;
import com.backend.pojo.BillingConstants;
import com.backend.pojo.BillingRecordBO;
import com.backend.pojo.DownloadDataBO;
import com.backend.pojo.EtvPenaltyTabBO;
import com.backend.pojo.RRNumberBO;
import com.backend.pojo.TariffConstant;
import com.backend.pojo.TariffFixBO;
import com.backend.pojo.TariffMainBO;
import com.backend.pojo.TariffRebateBO;
import com.backend.pojo.TariffSlabBO;
import com.backend.pojo.UploadDataBO;
import com.backend.singleton.BillingSingleton;

/**
 * @author Nadeem
 *
 */
public class BillingProgram {

	/**
	 * @param args
	 */
	
	public static String LOCATION     = null;
	public static String METER_CODE   = null;
	public static String BILL_DATE    = null;
	public static String MONTH        = null;
	public static String CURRENT_RRNO = null;
	public static int    PRESENT_READING = 0;
	public static String ORACLE_USER  = null;
	public static String ORACLE_PASS  = null;
	public static String USER         = null;
	public static String RECON_PATH   = null;
	public static String DATABASE_IP  = null;
	public static String DATABASE_NAME  = null;
	
	/*
	 * Tariff Details
	 */ 
	public static List<TariffMainBO>    TariffMainList   = null;
	public static List<TariffFixBO>     TariffFixList    = null;
	public static List<TariffSlabBO>    TariffSlabList   = null;
	public static List<TariffRebateBO>  TariffRebateList = null;
	//
	
	/*
	 * Downlad and Upload Data Objects
	*/
	public static List<DownloadDataBO>  DownloadRecordList = null;
	public static List<UploadDataBO>   UploadRecordList   = null;
	public static List<RRNumberBO>     RRNOList           = null;
	//
	
	/*
	 * Bill Generation objects
	*/
	public static BillingRecordBO BillRec = null;
	public static List<BillingRecordBO> BillRecList = null;
	public static UploadDataBO upload = null;
	//
	
	
	/*
	 * Database Objects
	*/
	public static Connection           con = null;
	public static PreparedStatement    ps  = null;
	public static ResultSet            rs  = null;
	//
	
	/*
	 * Temporary Biiling Objects
	*/
	public static TariffMainBO tempMainBo = null;
	public static boolean continueProgram = false;
	public static BillingRecordBO BillRecordBO = null;
	public static List<EtvPenaltyTabBO> etvList = null;
	//
	
	
	
	
	public static void main(String[] args) throws SQLException,IOException{
		// TODO Auto-generated method stub

		BillingSingleton BillOBJ = BillingSingleton.getInstance();
		//BillOBJ.getSomeThing();
		
		System.out.println("****************************************************");
		System.out.println("Billing Program Version : "+BillingConstants.VER);
		System.out.println("****************************************************");
		
		if(BillOBJ.validate_arguements(args) == 0){
			con = DatabaseOperations.getOracleConnection(DATABASE_IP, ORACLE_USER, ORACLE_PASS,DATABASE_NAME);
			if(con != null){
				BillOBJ.GetSlabDetails();
				
				if(BillOBJ.loadRRnoList() <= 0){
					System.out.println("Billing Program , No Data Loaded For This RR No.");
					
					//Exit the Program here...
					return ;
				}
				
				Iterator<RRNumberBO> it = RRNOList.iterator();
				while(it.hasNext()){
					RRNumberBO RrNoBO = (RRNumberBO)it.next();
					System.out.println("List Item : "+RrNoBO.getRrNo());
					
					BillingProgram.CURRENT_RRNO = RrNoBO.getRrNo();
					
					//For Billing Purpose Make_meter
					//BillingProgram.METER_CODE = "";

					//get rrno details here
					if(BillOBJ.GetDownloadRecord()){
						
						if(BillRecList != null){
							
							Iterator<BillingRecordBO> BillListIterator = BillRecList.iterator();
							
							while(BillListIterator.hasNext()){
								
								BillRecordBO = BillListIterator.next();
								
								if(TariffMainList != null){
									Iterator<TariffMainBO> main_it = TariffMainList.iterator();
									while(main_it.hasNext()){
										    tempMainBo = (TariffMainBO) main_it.next();

											System.out.println("tempMainBo.getTariffCode() :"+tempMainBo.getTariffCode() +
													"BillRec.getTariff_Code() : "+BillRecordBO.getTariff_Code());
											if(tempMainBo.getTariffCode() == BillRecordBO.getTariff_Code()){
												System.out.println("Tariff Code Matches...!");
												continueProgram =true;
												break;
											}else{
											//	System.out.println("Tariff Does Not Match...!");
												continueProgram =false;
											}
									}
								}else{
									System.out.println("Tariff Details Does Not Exists...!");
									continueProgram =false;
								}
								
								if(continueProgram){
									
									if(BillRecordBO.getDC_Flg().equals("Y")){
										System.out.println("Inside BillRecordBO.getDC_Flg().equals(Y)");
										BillOBJ.ComputeBill();
										BillOBJ.LoadUploadRecord('Y');
										return ;
									}else{
										//added by nadeem for mr-wise ,skip record and generate bill for which present reading occurs.
										int return_value = 0;
										return_value = BillOBJ.get_current_reading(CURRENT_RRNO,BILL_DATE,PRESENT_READING);
										System.out.println("get_current_reading , return_value : "+return_value);
										if(return_value == 0){

											
											if(tempMainBo.getTariffType().startsWith("LT4", 3)){
												System.out.println("This is for LT4 billing...!");
												BillRecordBO.setNumDL(0);
												BillRecordBO.setAvg_Consumption(0);
											}
											
											if(BillRecordBO.getMtr_chng_flg().equals("Y")){
												BillRecordBO.setNumDL(0);
											}
											BillOBJ.ComputeBill();
											BillOBJ.LoadUploadRecord('Y');
											
										}else if(return_value == -2){
											continue;
										}
										else{
											if(BillRecordBO.getTariff_Code() == TariffConstant.LT4AUM){
												
												BillRecordBO.setnUnitsConsumed(0);
												BillOBJ.ComputeBill();
												BillOBJ.LoadUploadRecord('Y');
												
											}else{
												if(tempMainBo.getTariffType().startsWith("LT4", 3)){
													
													BillRecordBO.setNumDL(0);
													
													if(BillRecordBO.getMtr_Sts().equals("M") ||
															BillRecordBO.getMtr_Sts().equals("D")){
														BillRecordBO.setAvg_Consumption(0);
													}
													if(!BillRecordBO.getMtr_Sts().equals("M")
															|| !BillRecordBO.getMtr_Sts().equals("D")){
														
														if(BillOBJ.get_current_reading(CURRENT_RRNO,BILL_DATE,PRESENT_READING) != 0){
															
															DatabaseOperations.LogErrorIntoBILL_GEN_ERR(BillingProgram.BillRecordBO.getBill_Date(),
																	BillingProgram.BillRecordBO.getReader_Code(), BillingProgram.BillRecordBO.getRr_No(), 
																	"get_current_reading", 
																	" There is no present reading");
															
															BillOBJ.LoadUploadRecord('N');
														}else{
															BillOBJ.ComputeBill();
															BillOBJ.LoadUploadRecord('Y');
														}
													}else{
														BillOBJ.ComputeBill();
														BillOBJ.LoadUploadRecord('Y');
													}
												}else{
													System.out
															.println("There is no present reading for RRno =  "+CURRENT_RRNO);
													
													DatabaseOperations.LogErrorIntoBILL_GEN_ERR(BillingProgram.BillRecordBO.getBill_Date(),
															BillingProgram.BillRecordBO.getReader_Code(), BillingProgram.BillRecordBO.getRr_No(), 
															"get_current_reading",
															" There is no present reading");
													BillOBJ.LoadUploadRecord('N');
												}
											}
										}
									}
									
								}
								
							}
							
							
						}else{
							System.out.println("No RRno Present For Bill Generation...!");
						}
						
					}else{
						System.out.println("Records Not Found For This RRno : "+ CURRENT_RRNO);
					}
					
				}
				
				BillOBJ.UploadBillMaster();
				
				BillingProgram.con.commit();
				
				BillingProgram.con.setAutoCommit(true);
			}

		}
		
		return ;
		
	}

}
