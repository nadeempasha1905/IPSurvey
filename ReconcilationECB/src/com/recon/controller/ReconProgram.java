/**
 * 
 */
package com.recon.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.recon.dao.DatabaseOperations;
import com.recon.dao.ExecuteShellCommand;
import com.recon.pojo.BillBreakupBO;
import com.recon.pojo.BillReceiptBreakUpBO;
import com.recon.pojo.ReceiptTableData;
import com.recon.pojo.ReceiptUpdateTable;
import com.recon.singleton.ReconSingleton;

/**
 * @author Nadeem
 *
 */
public class ReconProgram {

	/**
	 * @param args
	 */
	
	/*Global Variables
	 * */
	public static final String VERSION = "R1.0";
	
	public static int    USAGE_MODE = 0;
	public static int    G_GOVT_ADJ = 0;
	
	public static String RTT_FILENAME   = null;
	public static double G_CREDIT       = 0.0;
	public static String G_RR_NUMBER    = null;
	public static String G_CURRENT_RRNO = null;
	public static String G_CREDIT_DATE  = null;
	public static String G_RECON_DATE  = null;
	public static String G_LOCATION  = null;
	
	public static long   G_REVENUE_POSTED        = 0;
	public static long   G_NON_REVENUE_POSTED    = 0;
	
	public static int  MAX_BILL_BREAKUP_COUNT  = 0;
	public static int  CUR_BILL_BREAKUP_COUNT  = 0;
	
	public static int  MAX_BILL_RCPT_BREAKUP_COUNT  = 0;
	public static int  CUR_BILL_RCPT_BREAKUP_COUNT  = 0;
	
	public static int  MAX_RECEIPT_COUNT  = 0;
	public static int  CUR_RECEIPT_COUNT  = 0;
	
	
	
	/*Variable Declaration...
	 * */
	public static String DATABASE_IP   = null;
	public static String DATABASE_NAME = null;
	public static String USER_PASSWORD = null;
	public static String RECON_DATE    = null;
	public static String ACTUAL_USER   = null;
	public static String LOCATION      = null;
	public static String ORACLE_USER   = null;
	public static String ORACLE_PASS   = null;
	public static String G_USER        = null;
	public static int    G_PROCESS_ID  = 0;
	public static String G_DUP_RCPTS   = null;
	
	//
	
	/*
	 * Database Objects
	*/
	public static Connection           con = null;
	public static PreparedStatement    ps  = null;
	public static ResultSet            rs  = null;
	//
	
	//List
	public static ArrayList<ReceiptUpdateTable> ReceiptUpdateTablesList = new ArrayList<ReceiptUpdateTable>();
	public static ArrayList<String> RRNumberList = new ArrayList<String>();
	public static List<ReceiptTableData> receiptTableDataList = new ArrayList<ReceiptTableData>();
	public static List<BillBreakupBO> BillBreakupList = new ArrayList<BillBreakupBO>();
	public static List<BillReceiptBreakUpBO> BillReceiptBreakUpList = new ArrayList<BillReceiptBreakUpBO>();
	
	public static ArrayList<String> NEW_RRNumberList = new ArrayList<String>();
	//
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ReconSingleton ReconOBJ = ReconSingleton.getInstance();
		
		String temp1 = null ;
		String tempDate  = null ; 
		String tDate = null;

		

		try {
			
			/*G_PROCESS_ID = ExecuteShellCommand.GetCurrentProcessId(ReconProgram.class);*/
			
			/*G_PROCESS_ID = ReconOBJ.Get_Process_ID();
			
			
			//Check Recon Process is already running...!
			if(ReconOBJ.IsProcessRunning() == 1){
				//Coding has to be done....
				return ;
			}
			
			System.out.println("Process ID , pid : " + G_PROCESS_ID);*/
			
			System.out.println("****************************************************");
			System.out.println("Reconcilation Program Version : ");
			System.out.println("****************************************************");
			
			if (ReconOBJ.validate_arguements(args) == 0) {
				//con = DatabaseOperations.getOracleConnection("192.168.0.205",ORACLE_USER, ORACLE_PASS);
				//con = DatabaseOperations.getOracleConnection(DATABASE_IP,ORACLE_USER, ORACLE_PASS);
				
				//ReceiptUpdateTable rcpt_table = null;
				
				//rcpt_table = new ReceiptUpdateTable("RCPT_PYMNT", "UpdateReceiptPaymentTable", 1);
				//rcpt_table = new ReceiptUpdateTable("CONSUMER_CREDIT", "UpdateConsumerCreditTable", 2);
				//rcpt_table = new ReceiptUpdateTable("BN_DELETED_BILL_RCPT", "UpdateDeletedBillReceiptTable", 3);
				//rcpt_table = new ReceiptUpdateTable(null, null, 0);
				
				/*ReconOBJ.AddReceiptUpdateTable("RCPT_PYMNT", "UpdateReceiptPaymentTable", 1);
				ReconOBJ.AddReceiptUpdateTable("CONSUMER_CREDIT", "UpdateConsumerCreditTable", 2);
				ReconOBJ.AddReceiptUpdateTable("BN_DELETED_BILL_RCPT", "UpdateDeletedBillReceiptTable", 3);*/
				
				ReconOBJ.AddReceiptUpdateTable("RCPT_PYMNT", "UpdateReceiptPaymentTable", 1);
				ReconOBJ.AddReceiptUpdateTable("CREDIT", "UpdateConsumerCreditTable", 2);
				ReconOBJ.AddReceiptUpdateTable("DELTD_BILL_RCPT", "UpdateDeletedBillReceiptTable", 3);
				
				
				con = DatabaseOperations.getOracleConnection(DATABASE_IP, ORACLE_USER, ORACLE_PASS,DATABASE_NAME);
				
				
				/*
				 * New code added to reconcil bill by rrno if only receipt date exists also
				*/
				
				// changes done on 30/06/2018
				
				
				/*ReconOBJ.getNewRRnoList();
				
				for(int k = 0;k < ReconProgram.NEW_RRNumberList.size() ; k++){
					
					RefreshValues();
					
					System.out.println("**************************** Looping : "+ReconProgram.NEW_RRNumberList.get(k)+" ************************************************");
					System.out.println("**************************** *****************************************************************************************************");
					
					ReconProgram.G_RR_NUMBER = ReconProgram.NEW_RRNumberList.get(k);
					System.out.println("NEW G_RR_NUMBER  : "+ReconProgram.G_RR_NUMBER);
					ReconProgram.USAGE_MODE = 1;
					System.out.println("NEW USAGE_MODE  : "+ReconProgram.USAGE_MODE);*/
					
				////
				
				
				
				G_PROCESS_ID = ReconOBJ.Get_Process_ID();
				
				System.out.println("Process ID , pid : " + G_PROCESS_ID);
				
				if(con != null){
					
					G_USER = ORACLE_USER;
					
					if(RECON_DATE.length() == 7){
						
						tempDate = "01/"+RECON_DATE;
						System.out.println("Date : "+tempDate);
						
						int LastDay = ReconOBJ.GetLastDay(tempDate);
						
						if(LastDay > 0){
							
							for(int i=1;i<=LastDay;i++){
								
								tDate = i + "/" + RECON_DATE ;
								
								System.out.println("Recon Date : " + tDate );
								
								con.setAutoCommit(false);
								
								ReconOBJ.Do_Reconcilation(tDate);
								
								ReconOBJ.Delete_Process_ID();
								
								con.commit();
								
								con.setAutoCommit(true);
								
							}
						}
					}else{
						
						con.setAutoCommit(false);
						
						ReconOBJ.Do_Reconcilation(RECON_DATE);
						
						ReconOBJ.Delete_Process_ID();
						
						con.commit();
						
						con.setAutoCommit(true);
					}
				}else{
					System.out.println("Failed To Connect Database... !");
					return ;
				}
				
				
				//}
				
				System.out.println("********************Reconcilation Done     ********************");
				
			}else{
				System.out.println("Exiting With Error ... !");
				return ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ;
	}
	private static void RefreshValues() {
		// TODO Auto-generated method stub
		
		//List
		 ReceiptUpdateTablesList = new ArrayList<ReceiptUpdateTable>();
		 RRNumberList = new ArrayList<String>();
		 receiptTableDataList = new ArrayList<ReceiptTableData>();
		 BillBreakupList = new ArrayList<BillBreakupBO>();
		BillReceiptBreakUpList = new ArrayList<BillReceiptBreakUpBO>();
		
	}

}
