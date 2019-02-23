package com.recon.singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import com.recon.controller.ReconProgram;
import com.recon.dao.DatabaseOperations;
import com.recon.dao.ExecuteShellCommand;
import com.recon.pojo.BillBreakupBO;
import com.recon.pojo.BillReceiptBreakUpBO;
import com.recon.pojo.ReceiptTableData;
import com.recon.pojo.ReceiptUpdateTable;


public class ReconSingleton {
	
	private static ReconSingleton ReconOBJ ;
	
	public static ReconSingleton getInstance(){
		if(ReconOBJ == null){
			ReconOBJ = new ReconSingleton();
		}
		
		return ReconOBJ;
	}
	
/*public int validate_arguements(String[] args){
		
		if(args.length < 1){
			System.out.println("Usage: "+ExecuteShellCommand.getClassName(ReconProgram.class)+" user/password  date(dd/mm/yyyy)  [RRNumber [Options]]\n");
			return -1;
		}
		
		for(int i=0;i < args.length;i++){
			
			if(i == 0){
				if(args[0].length() > 0 && args[0].contains("/")){
					String[] s = args[0].split("/");
					ReconProgram.ORACLE_USER = s[0];
					ReconProgram.ORACLE_PASS = s[1];
					
					ReconProgram.USER_PASSWORD = args[i];
					System.out.println(" USER_PASSWORD  : "+ReconProgram.USER_PASSWORD);
				}
			}
			else if(i == 1){
				ReconProgram.RECON_DATE = args[i];
				System.out.println(" RECON_DATE  : "+ReconProgram.RECON_DATE);
			}
			else if(i == 2){
				ReconProgram.G_RR_NUMBER = args[i];
				System.out.println(" G_RR_NUMBER  : "+ReconProgram.G_RR_NUMBER);
				ReconProgram.USAGE_MODE = 1;
				System.out.println(" USAGE_MODE  : "+ReconProgram.USAGE_MODE);
			}
			else{
				if(args[i].contains("GOV_ADJ")){
					System.out.println("GOV Adjustment...!");
					ReconProgram.G_GOVT_ADJ = 1;
					System.out.println(" G_GOVT_ADJ  : "+ReconProgram.G_GOVT_ADJ);
				}
			}
		}
		
		return 0;
		
	}*/
	
public int validate_arguements(String[] args){
		
		if(args.length < 3){
			/*System.out.println("Usage: "+ExecuteShellCommand.getClassName(ReconProgram.class)+" user/password  date(dd/mm/yyyy)  [RRNumber [Options]]\n");*/
			System.out.println("Usage: ip_address user/password  date(dd/mm/yyyy)  [RRNumber [Options]]\n");
			return -1;
		}else{
			if(args.length > 2){
				
				for(int i=0;i < args.length;i++){
					
					if(i == 0){
						if(args[i].length() > 0){
							ReconProgram.DATABASE_IP = args[i];
							
							System.out.println(" IP  : "+ReconProgram.DATABASE_IP);
						}else{
							System.out.println("Invalid Argument : Database_IP ");
							return -1;
						}
					}
					else if(i == 1){
						if(args[i].length() > 0 && args[i].contains("/")){
							String[] s = args[i].split("/");
							ReconProgram.ORACLE_USER = s[0];
							ReconProgram.ORACLE_PASS = s[1];
							
							ReconProgram.USER_PASSWORD = args[i];
							System.out.println(" USER_PASSWORD  : "+ReconProgram.USER_PASSWORD);
						}
					}
					else if(i == 2){
						ReconProgram.DATABASE_NAME = args[i];
						System.out.println(" DATABASE_NAME  : "+ReconProgram.DATABASE_NAME);
					}
					else if(i == 3){
						ReconProgram.RECON_DATE = args[i];
						System.out.println(" RECON_DATE  : "+ReconProgram.RECON_DATE);
					}
					else if(i == 4){
						
						if(args[i].startsWith("-l", 0) || args[i].startsWith("-L", 0)){
							System.out.println(args[i].substring(args[i].indexOf('=')+1));
							ReconProgram.LOCATION = args[i].substring(args[i].indexOf('=')+1);
							System.out.println(" LOCATION  : "+ReconProgram.LOCATION);
						}else{
							ReconProgram.G_RR_NUMBER = args[i];
							System.out.println(" G_RR_NUMBER  : "+ReconProgram.G_RR_NUMBER);
							ReconProgram.USAGE_MODE = 1;
							System.out.println(" USAGE_MODE  : "+ReconProgram.USAGE_MODE);
						}
					}
					else if(i == 5){
						
						if(args[i].startsWith("-l", 0) || args[i].startsWith("-L", 0)){
							System.out.println(args[i].substring(args[i].indexOf('=')+1));
							ReconProgram.LOCATION = args[i].substring(args[i].indexOf('=')+1);
							System.out.println(" LOCATION  : "+ReconProgram.LOCATION);
						}
					}
					else{
						if(args[i].contains("GOV_ADJ")){
							System.out.println("GOV Adjustment...!");
							ReconProgram.G_GOVT_ADJ = 1;
							System.out.println(" G_GOVT_ADJ  : "+ReconProgram.G_GOVT_ADJ);
						}
					}
				}
				
			}else{
				
				System.out.println("Usage: ip_address user/password  date(dd/mm/yyyy)  [RRNumber [Options]]\n");
				return -1;
			}
		}
		
		return 0;
		
	}

public int IsProcessRunning() {
	// TODO Auto-generated method stub
	return 0;
}

public int GetLastDay(String tempDate) {
	// TODO Auto-generated method stub
	int value = 0;
	String temp = null;
	try {
		
		ReconProgram.ps = 
				ReconProgram.con.prepareStatement("SELECT to_char(LAST_DAY(to_date('"+tempDate+"', 'dd/mm/yyyy')), 'dd') " +
						" temp from dual");
		
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		while(ReconProgram.rs.next()){
			temp = ReconProgram.rs.getString("temp");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured in GetLastDays :  "+e.getMessage());
	}
		
	if(temp != null){
		
		value = Integer.parseInt(temp);
	}
	
	return value;
}

public int Do_Reconcilation(String reconDate) {
	// TODO Auto-generated method stub
	
	String tname = null;
	String curBillDate = null;
	String curBillNumber = null; 
	String l_rrno = null;
	String l_sts = null;
	int l_check = 0;
	int     l_exists = 0;
	String l_check2 = null;
	
	String currentRrno = null; 
	String ptrRrno = null;
	String Query = null;
	
	ResultSet rs1 = null;
	
	try {
		
		if(ReconProgram.USAGE_MODE == 1){
			ptrRrno = ReconProgram.G_RR_NUMBER;
		}else{
			ptrRrno = null;
		}
		
		if(InspectReconProcess() == 0){
			
			if(Validate_Date_Fromat(reconDate) != 0){
				
				return 1;
			}

			if(Post_Deposits(ptrRrno,reconDate)  <  0){
				
				ReconProgram.con.rollback();
				
				return 4;
			}else{
				
				ReconProgram.con.commit();
				
				ReconProgram.con.setAutoCommit(true);
			}
			
			
			if(Build_Recon_Temp_Table(reconDate) < 0){
				
				System.out.println("return Build_Recon_Temp_Table;;;;;;;;;;;;;;;;;;;;;;;;;;");
				return 2;
			}
			
			LoadRRnumberList();
			
			if(ReconProgram.RRNumberList == null){
				
				System.out.println("No Records Found For Reconcilation . ");
				
				return -1;
			}
			
			if(ReconProgram.RRNumberList != null){
				
				for(int i = 0;i < ReconProgram.RRNumberList.size() ; i++){
					
					//List
					 ReconProgram.receiptTableDataList = new ArrayList<ReceiptTableData>();
					 ReconProgram.BillBreakupList = new ArrayList<BillBreakupBO>();
					 ReconProgram.BillReceiptBreakUpList = new ArrayList<BillReceiptBreakUpBO>();
					
					currentRrno = ReconProgram.RRNumberList.get(i);
					l_rrno = currentRrno ; 
					ReconProgram.G_CURRENT_RRNO = currentRrno ; 
					
					l_check = 0 ;
					
					System.out.println("Getting Credit Approval Status For RRNO : "+l_rrno);
					
					Query =  " SELECT COUNT(*)  l_check " +
							 " FROM CREDIT_DETL " +
							 " WHERE CRD_RR_NO = '"+l_rrno+"'   " +
							 " AND CRD_APPRV_STS = 'P'  " +
							 " AND CRD_CR_FROM = '07'  ";
					
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					ReconProgram.rs = ReconProgram.ps.executeQuery();
					
					if(ReconProgram.rs.next()){
						l_check = ReconProgram.rs.getInt("l_check");
						System.out.println("Records Found : "+l_check);
						
						if(l_check > 0){
							
							System.out.println("APPROVED STATUS IS P for "+l_rrno + " .NO OF UNCREDITED RECORDS  : "+l_check);
						}else{
							
							if(Load_Receipts(currentRrno) > 0 ){
								
								System.out.println("Get Receipts Into The Load Receipt Loop...!");
								
								Query = " SELECT nvl(SB_BILL_NO, '0') curBillNumber, " +
										" TO_CHAR(nvl(SB_BILL_DT, to_date('15/08/1947', 'dd/mm/yyyy')), 'dd/mm/yyyy')  curBillDate , " +
										" SB_RR_STS  l_sts " +
										" FROM SHOP_BILL  " +
										" WHERE SB_RR_NO = '"+l_rrno+"' ";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								rs1 = ReconProgram.ps.executeQuery();
								
								while(rs1.next()){
									
									curBillNumber = rs1.getString("curBillNumber");
									curBillDate   = rs1.getString("curBillDate");
									l_sts         = rs1.getString("l_sts");
								}
								
								if(!l_sts.equals("N") && !l_sts.equals("I")){
									System.out.println("Cannot Do Recon For This RRNo : "+currentRrno +
											" , Status : "+l_sts);
									
									Query = " UPDATE SHOP_BILL SET SB_RR_STS = 'N' " +
											"  WHERE SB_RR_NO = '"+l_rrno+"'  ";
									
									ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
									int k = ReconProgram.ps.executeUpdate();
									
									if(k >  0){
										System.out.println("No Record Found is Consumer Bill For RRNO : "+currentRrno);
									}
									
									//ReconProgram.con.setAutoCommit(true);
									
									DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
									
									break;
								}
								
								System.out.println("RRno : "+currentRrno + " , Bill_date : "+curBillDate + " , Bill No : "+curBillNumber);
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
								
								l_check = 0;
								
								Query = " SELECT COUNT(*) l_check FROM CREDIT_DETL  " +
										" WHERE CRD_RR_NO = '"+l_rrno+"' " +
										" AND CRD_APPRV_STS = 'P' " +
										" AND CRD_CR_FROM = '07'";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								rs1 = ReconProgram.ps.executeQuery();
								
								while(rs1.next()){
									
									l_check = rs1.getInt("l_check");
								}
								if(l_check > 0){
									System.out.println("Approved Status is P for RRNO : "+currentRrno);
								}
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
								
								Query = " SELECT COUNT(1) l_exists " +
										" FROM BILL_MASTER  " +
										" WHERE BM_RR_NO = '"+l_rrno+"'  " +
										" AND   BM_BILL_DT = TO_DATE('"+curBillDate+"', 'dd/mm/yyyy') " +
										" AND   BM_BILL_NO = '"+curBillNumber+"'  ";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								rs1 = ReconProgram.ps.executeQuery();
								
								while(rs1.next()){
									
									l_exists = rs1.getInt("l_exists");
								}
								if(l_check ==  0){
									System.out.println("Bill Not Found for RRNO : "+currentRrno);
								}
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
								
								
								Query = " UPDATE SHOP_BILL SET SB_RR_STS = 'R', " +
										"  SB_PREV_STS = '"+l_sts+"'  " +
										"  WHERE SB_RR_NO = '"+l_rrno+"'  ";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								int k = ReconProgram.ps.executeUpdate();
								
								if(k >  0){
									System.out.println("Consumer Bill status updated to 'R' for RRNO : "+currentRrno);
								}
								
								//ReconProgram.con.setAutoCommit(true);
								
								System.out.println("after ..");
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
								
								LoadBillBreakupTab(currentRrno,curBillDate,curBillNumber);
								
								LoadBillReceuiptBreakupTab(currentRrno,curBillDate,curBillNumber);
								
								if(PostReceipts(currentRrno) > 0){
									
									System.out.println("Inside Post Receipts...");
									
									if(UpdateTables(currentRrno,curBillDate,curBillNumber) < 0){
										
										System.out.println("transaction rollbacked...");
										
										ReconProgram.con.rollback();
										Report_Error("rewrite",0,1);
										
										ReconProgram.con.setAutoCommit(true);
										
									}else{
										
										System.out.println("transaction commited...");
										
										ReconProgram.con.commit();
										
										ReconProgram.con.setAutoCommit(true);
									}
								}
								
								
								Query = " UPDATE SHOP_BILL SET SB_RR_STS = 'N' " +
										"  WHERE SB_RR_NO = '"+l_rrno+"'  ";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								k = ReconProgram.ps.executeUpdate();
								
								if(k >  0){
									/*System.out.println("Consumer Bill status updated to 'R' for RRNO : "+currentRrno);*/
									System.out.println("Consumer Bill status updated to 'N' for RRNO : "+currentRrno);
								}
								
								//ReconProgram.con.commit();
								
								//ReconProgram.con.setAutoCommit(true);
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
							}
							else{
								
								System.out.println("Receipts Records Not Found For : "+currentRrno);
								
								
								Query = " UPDATE SHOP_BILL SET SB_RR_STS = 'N' " +
										"  WHERE SB_RR_NO = '"+l_rrno+"'  ";
								
								ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
								int k = ReconProgram.ps.executeUpdate();
								
								if(k >  0){
									/*System.out.println("Consumer Bill status updated to 'R' for RRNO : "+currentRrno);*/
									System.out.println("Consumer Bill status updated to 'N' for RRNO : "+currentRrno);
								}
							
								System.out.println("after.............");
								//ReconProgram.con.commit();System.out.println("1");
								
								//ReconProgram.con.setAutoCommit(true);System.out.println("2");
								
								DatabaseOperations.cleanUp(ReconProgram.ps, rs1);System.out.println("3");
							}
							System.out.println("4");
						}
						System.out.println("5");
					}
					System.out.println("6");
					
					DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);

					//UpdateStatus();
					
					ResetReconProcessFlag();
				}
				
				//ResetReconProcessFlag();
				
			}
			else{
				
				System.out.println("Reconciliation under progress can not start one more process for this activity");
			}
		}
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occurec : "+e.getMessage());
	}
	return 0;
}


private void ResetReconProcessFlag() {
	// TODO Auto-generated method stub
	
	ResultSet rs = null;
	String Query = "";
	
	try {
		
		Query = "	UPDATE RECON_PROCESS " +                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
				"   SET  RP_DATE = SYSDATE, " +
				" 	RP_RECON_STS = 'N' ";

		
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		int v = ReconProgram.ps.executeUpdate();
		
		if(v > 0){
			System.out.println("RECON_PROCESS updated :  RP_RECON_STS = 'N'  ");
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
	} catch (SQLException e) {
		// TODO: handle exception
	}
	
}

private int UpdateTables(String currentRrno, String curBillDate,
		String curBillNumber) {
	// TODO Auto-generated method stub
	
	float credit = (float) 0.0;
	
	System.out.println("Inside The UpdateTables ... !");
	try {
		
		if( InsertIntoBillReceipt() < 0){
			return Report_Error("Error while updating BILL_RCPT_BREAKUP", -1, 0);
		}	

		/*if( UpdateReceiptsTables(currentRrno, curBillDate, curBillNumber, credit) < 0 ){*/
		if( UpdateReceiptsTables(currentRrno, curBillDate, curBillNumber, (float) ReconProgram.G_CREDIT) < 0 ){
			return Report_Error("Error while updating rcptTables", -2, 1);
		}	

		/*if( UpdateBillMaster(currentRrno, curBillDate, curBillNumber, credit) < 0){*/
		if( UpdateBillMaster(currentRrno, curBillDate, curBillNumber, (float) ReconProgram.G_CREDIT) < 0){
			return Report_Error("Error while updating BILL_MASTER", -3, 0);
		}
		
		System.out.println("ReconProgram.G_CREDIT : "+ReconProgram.G_CREDIT);

		/*if( credit >= 0.01 )*/
		if(  ReconProgram.G_CREDIT >= 0.01 )
		{
			/*if( UpdateCredits(currentRrno, credit) < 0)*/
			if( UpdateCredits(currentRrno, (float) ReconProgram.G_CREDIT) < 0)
				return Report_Error("Error while updating consumer credits\n", -4, 0);
		}
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	return 0;
}

private int UpdateCredits(String currentRrno, float credit) {
	// TODO Auto-generated method stub
	
	float l_credit = (float) 0.0;
	float l_tot_credit = (float) 0.0;

	String l_dtl_from; 
	String l_dtls_from = null; 

	String l_cc_rr_no;    
	String l_cc_credit_no;
	String l_cc_credit_dt; 
	float 	l_cc_credit_amt;
	float 	l_cc_credited_amt;
	String l_cc_credited_dt;
	String l_cc_credit_sts;
	String l_cc_user;
	String l_cc_tmpstp;

	String l_crd_rr_no; 
	String l_crd_cr_no = null;
	String l_crd_sl_no;
	String l_crd_cr_dt;
	float 	l_crd_cr_amt;
	String l_crd_cr_from_detl;
	String l_crd_cr_from;
	String l_crd_cr_user; 
	String l_crd_tmpstp;
	String l_dateDetl = null; 
	int ind;
	
	String Query = "";
	ResultSet rs = null;
	
	int i;
	int newRec = 0;
	
	l_crd_cr_amt =(float) 0.0;
	l_cc_rr_no = currentRrno ; 
	l_cc_credit_no = "1";
	l_crd_sl_no = "000";
	
	System.out.println("Inside Update Receipts  : ");
	
	try {
		
		ind = 0 ;
		
		
		Query = "  SELECT NVL(MAX(CR_CREDIT_NO),0)+ 1 l_cc_credit_no   " +
				"  FROM CREDIT WHERE CR_RR_NO = '"+l_cc_rr_no+"'  ";  
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		if(rs.next()){
			l_cc_credit_no = rs.getString("l_cc_credit_no");
			
			if(l_cc_credit_no == "0"){
				l_cc_credit_no = "1";
			}
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		
		if(ReconProgram.receiptTableDataList != null){
			
			Iterator< ReceiptTableData> rcptTab = ReconProgram.receiptTableDataList.iterator();
			while(rcptTab.hasNext()){
				
				ReceiptTableData receiptTableDataBO = (ReceiptTableData)rcptTab.next();
				
				l_credit = (float)0.0 ; 
				
				if((receiptTableDataBO.getAmountReceived() - receiptTableDataBO.getAmountPosted()) >= 0.01){
					
					l_credit = (float) (receiptTableDataBO.getAmountReceived()	-   receiptTableDataBO.getAmountPosted());

					l_tot_credit += l_credit;
					
					l_dtl_from = GetDetail_From(receiptTableDataBO.getRtt_tab_name());
					
					l_dtls_from = GetDetailsFrom(receiptTableDataBO.getRtt_tab_name(), receiptTableDataBO.getRowId());
					
					l_dateDetl = GetDateDetail(receiptTableDataBO.getRtt_tab_name(), receiptTableDataBO.getRowId());
					
					Query = "  SELECT lpad(to_char(TO_NUMBER("+l_crd_sl_no+") + 1), 3, '0') l_crd_sl_no FROM DUAL "; 
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					rs = ReconProgram.ps.executeQuery();
					
					if(rs.next()){
						l_crd_sl_no = rs.getString("l_crd_sl_no");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs);
					
					l_crd_rr_no = l_cc_rr_no;
					l_crd_cr_no = l_cc_credit_no;
					l_crd_cr_amt = l_credit; 
					
					System.out.println("Inserting consumer CREDIT_DETL for ,l_dtl_from : "+l_dtl_from  + " , " + l_dtls_from
							+ " , l_crd_cr_amt : "+l_crd_cr_amt+" , l_crd_cr_no : "+l_crd_cr_no);
					
					
					
					Query = "	INSERT INTO CREDIT_DETL( " +
							"	CRD_RR_NO,CRD_CR_NO,CRD_SL_NO,CRD_CR_DT,CRD_CR_AMT,	CRD_CR_FROM_DETL,CRD_CR_FROM," +
							"	CRD_CR_USER,CRD_APPRV_STS,CRD_RMKS) " +
							" 	VALUES(  " +
							"	'"+l_crd_rr_no+"',to_number('"+l_crd_cr_no+"'),'"+l_crd_sl_no+"'," +
							"    to_date('"+l_dateDetl+"', 'yyyymmdd'),'"+l_crd_cr_amt+"'," +
							"	'"+l_dtls_from+"','"+l_dtl_from+"',UPPER('"+ReconProgram.G_USER+"'),'Y','CREDIT')";
	
					
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					int v = ReconProgram.ps.executeUpdate();
					
					if(v > 0){
						System.out.println("Record Inserted BILL_RCPT_BREAKUP : ");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs);
					
					newRec = 1 ;
				}
			}
		}
		
		if(newRec == 1){
			
			l_cc_credit_amt = l_tot_credit ; 
			
			System.out.println("Inserting RRNO : "+l_crd_cr_no
					+ " l_cc_credit_no : "+l_cc_credit_no + " l_cc_credit_amt : "+l_cc_credit_amt);
			
			
			Query = "	INSERT INTO CREDIT (  " +
					"   CR_RR_NO,CR_CREDIT_NO,CR_CREDIT_DT, CR_CREDIT_AMT," +
					"	CR_CREDITED_AMT,CR_CREDITED_DT,	CR_CREDIT_STS,	CR_USER ) " +
					" 	VALUES( '"+l_cc_rr_no+"',to_number('"+l_cc_credit_no+"'), " +
							"to_date('"+l_dateDetl+"', 'yyyymmdd'), '"+l_cc_credit_amt+"', " +
							"0,null,'N',UPPER('"+ReconProgram.G_USER+"'))";

			System.out.println("insert CREDIT : "+Query);
			
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int v = ReconProgram.ps.executeUpdate();
			
			if(v > 0){
				System.out.println("Existing  Update Credits: ");
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		}
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
		return -1;
	}
	return 0;
}

private String GetDateDetail(String rtt_tab_name, String rowId) {
	// TODO Auto-generated method stub
	
	String l_date = null;
	String l_rowId;
	String l_row_id;
	String l_new_rowid = null;
	
	
	ResultSet rs = null;
	ResultSet rs1 = null;
	String Query = "";
	
	try {
		
		if(rtt_tab_name.equals("DELTD_BILL_RCPT")){
			
			l_row_id = rowId ; 
			
			Query = "  SELECT a.rowid l_new_rowid " +
					"  from RCPT_PYMNT a, DELTD_BILL_RCPT b " +
					"  WHERE b.rowid = '"+l_row_id+"' " +
					"  AND   a.RP_rcpt_no = b.DBR_rcpt_no " +
					"  and   a.RP_rcpt_dt = b.DBR_rcpt_dt  " +
					"  and   a.RP_cash_countr_no = b.DBR_cash_cntr  " ;
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs = ReconProgram.ps.executeQuery();
			
			if(rs.next()){
				l_new_rowid = rs.getString("l_new_rowid");
				rtt_tab_name = "RCPT_PYMNT";
				rowId = l_new_rowid;
			}else{
				
				Query = "  SELECT to_char(DBR_RCPT_DT, 'yyyymmdd') l_date from DELTD_BILL_RCPT " +
						"	where rowid = '"+l_row_id+"' "; 
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				rs1 = ReconProgram.ps.executeQuery();
				
				if(rs1.next()){
					l_date = rs1.getString("l_date");
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		}
		
		l_rowId = rowId ;
		
		if(rtt_tab_name.equals("RCPT_PYMNT")){
			
			Query = "  SELECT to_char(RP_RCPT_DT, 'yyyymmdd') l_date  FROM RCPT_PYMNT where rowid = '"+l_rowId+"' "; 
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs1 = ReconProgram.ps.executeQuery();
			
			if(rs1.next()){
				l_date = rs1.getString("l_date");
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			
		}
		
		if(rtt_tab_name.equals("CREDIT")){
					
					Query = "  SELECT to_CHAR(CR_CREDIT_DT, 'yyyymmdd') INTO  FROM CREDIT where rowid = '"+l_rowId+"' "; 
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					rs1 = ReconProgram.ps.executeQuery();
					
					if(rs1.next()){
						l_date = rs1.getString("l_date");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			}
		
		
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	
	
	return l_date;
}

private int UpdateBillMaster(String currentRrno, String curBillDate,
		String curBillNumber, float credit) {
	// TODO Auto-generated method stub
	
	String l_maxPaidDate = null;
	float l_postedAmount = (float) 0.0;
	String l_rrno;
	String l_billDt;
	String l_billNo;
	float l_credit;
	String l_credit_dt;	
	String l_temp_dt;
	int i = 0 ;
	String Query = "";
	ResultSet rs = null;
	
	try {
		
		l_credit = credit ; 
		
		l_credit = (float) ReconProgram.G_CREDIT ;  //added newly
		
		if(ReconProgram.receiptTableDataList != null){
			
			Iterator<ReceiptTableData> rcptTab_it = ReconProgram.receiptTableDataList.iterator();
			
			while(rcptTab_it.hasNext()){
				System.out.println("1231231 : "+ReconProgram.receiptTableDataList);
				
				ReceiptTableData receiptTableDataBO = (ReceiptTableData)rcptTab_it.next();
				
				if(receiptTableDataBO.getAmountPosted() >= 0.001){System.out.println("1231231" + receiptTableDataBO.getRtt_date());
					
					l_postedAmount += receiptTableDataBO.getAmountPosted() ;  //temporary commeneted for bill_master update issue
				
				/*l_postedAmount = receiptTableDataBO.getAmountPosted() ;*/
				
				System.out.println("****************** l_postedAmount : "+l_postedAmount+ " ************ receiptdate : "+receiptTableDataBO.getRtt_date()+"**********rrno : "+currentRrno);
					
					/*if(receiptTableDataBO.getRtt_date().equals(l_maxPaidDate)){System.out.println("1231231");
						
						l_maxPaidDate = receiptTableDataBO.getRtt_date();
						
					}*/
					
					l_maxPaidDate = receiptTableDataBO.getRtt_date();
					
					
				}
			}
			
		}
		
		
		if(l_postedAmount >= 0.01 ){
			
			l_rrno = currentRrno ;
			l_billDt = curBillDate ; 
			l_billNo = curBillNumber ;
			
			
			System.out.println("Updating Bill Master : rrno : "+ currentRrno
					+ ", Dt : "+curBillDate + " , bill no : "+curBillNumber + " , amt  : "+l_postedAmount);
			
			
			
			Query = "  SELECT to_char(to_date('"+l_billDt+"', 'dd/mm/yyyy'), 'YYYYMMDD') l_temp_dt from dual "; 
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs = ReconProgram.ps.executeQuery();
			
			if(rs.next()){
				l_temp_dt = rs.getString("l_temp_dt");
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs);
			
			
			Query =  "	UPDATE BILL_MASTER SET " +
					 "	BM_AMT_PAID =  NVL(BM_AMT_PAID,0) + '"+l_postedAmount+"',  " +
					 "  BM_PAID_DT  =  to_date('"+l_maxPaidDate+"', 'yyyymmdd'), " +
					 "	BM_credit_amt = '"+l_credit+"'  " +
					 "	WHERE BM_RR_NO = '"+l_rrno+"' " +
					 "  AND   BM_BILL_DT = to_date('"+l_billDt+"', 'dd/mm/yyyy') " +
					 "  AND   BM_BILL_NO = '"+l_billNo+"'  ";
			
			System.out.println("UPDATE BILL_MASTER  : "+Query);
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int v = ReconProgram.ps.executeUpdate();
			
			if(v > 0){

				ReconProgram.G_CREDIT = credit ; 
			}
			else{
				System.out.println("Error While Updating RRno : "+currentRrno + " , billno : "+curBillNumber);
				return -1 ;
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs);
			
			return 0 ;
			
		}else{
			
			if(credit > 0.001){
				
				l_rrno = currentRrno ; 
				l_billDt = curBillDate ; 
				l_billNo = curBillNumber ;
				
				l_credit_dt = ReconProgram.G_CREDIT_DATE ; 
				
				Query = "  SELECT  to_char(nvl(BM_paid_dt, to_date('"+l_credit_dt+"', 'yyyymmdd')), 'yyyymmdd') l_credit_dt  " +
						"  from  BILL_MASTER   " +
						"  WHERE BM_RR_NO = '"+l_rrno+"'  " +
						"  AND   BM_BILL_DT = to_date('"+l_billDt+"', 'dd/mm/yyyy')  " +
						"  AND   BM_BILL_NO = '"+l_billNo+"' "; 
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				rs = ReconProgram.ps.executeQuery();
				
				if(rs.next()){
					l_credit_dt = rs.getString("l_credit_dt");
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, rs);
				
				
				if(ReconProgram.G_CREDIT_DATE.equals(l_credit_dt)){
					l_credit_dt = ReconProgram.G_CREDIT_DATE ;
				}
				
				Query =  "	UPDATE BILL_MASTER SET " +
						 "  BM_credit_amt = '"+l_credit+"', " +
						 "  BM_PAID_DT = to_date('"+l_credit_dt+"', 'yyyymmdd') " +
						 "  WHERE BM_RR_NO = '"+l_rrno+"'  " +
						 "  AND   BM_BILL_DT = to_date('"+l_billDt+"', 'dd/mm/yyyy') " +
						 "  AND   BM_BILL_NO = '"+l_billNo+"'  ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				int v = ReconProgram.ps.executeUpdate();
				
				if(v > 0){

					ReconProgram.G_CREDIT = credit ; 
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, rs);
			}
		}
	} catch (SQLException e) {
		// TODO: handle exception
	}
	return 0;
}

private int UpdateReceiptsTables(String currentRrno, String curBillDate,
		String curBillNumber, float crd) {
	// TODO Auto-generated method stub
	
	int i = 0;
	int j = 0;
	int ret = 0;
	float totCredit = (float) 0.0;
	float credit = (float) 0.0;
	String errBuf;
	
	try {
		
		if(ReconProgram.receiptTableDataList != null){
			
			Iterator<ReceiptTableData> rcpt_tab_it = ReconProgram.receiptTableDataList.iterator();
			
			while(rcpt_tab_it.hasNext()){
				
				ReceiptTableData rcptTableBO = (ReceiptTableData)rcpt_tab_it.next();
				
				if(i == 0){
					ReconProgram.G_CREDIT_DATE = rcptTableBO.getRtt_tab_name() ; 
				}
				
				
				if(rcptTableBO.getAmountPosted() < rcptTableBO.getAmountReceived()){
					
					credit = (float) (rcptTableBO.getAmountReceived() - rcptTableBO.getAmountPosted()) ; 
					
					totCredit += credit ;
					
					if(!ReconProgram.G_CREDIT_DATE.equals(rcptTableBO.getRtt_date())){
						
						ReconProgram.G_CREDIT_DATE = rcptTableBO.getRtt_date() ; 
					}
						
				}
			}
			
		}
		
		crd = totCredit ;
		
		ReconProgram.G_CREDIT = totCredit ; //added newly 
		
		
		
		if(ReconProgram.receiptTableDataList != null){
			
			Iterator<ReceiptTableData> rcpt_tab_it = ReconProgram.receiptTableDataList.iterator();
			
			i = 0;
			
			while(rcpt_tab_it.hasNext()){
				
				ReceiptTableData receiptTableDataBO = (ReceiptTableData)rcpt_tab_it.next();
				
				if(ReconProgram.ReceiptUpdateTablesList != null){
					
					Iterator<ReceiptUpdateTable> rcptUpdt_it = ReconProgram.ReceiptUpdateTablesList.iterator();
					
					while(rcptUpdt_it.hasNext()){
						
						ReceiptUpdateTable receiptUpdateTableBO = (ReceiptUpdateTable)rcptUpdt_it.next();
						
						System.out.println("");
						
						if(receiptTableDataBO.getRtt_tab_name().equals(receiptUpdateTableBO.getTable_Name())){
							
							System.out.println("Calling Update function for : "+receiptUpdateTableBO.getTable_Name()
									+" , " + receiptUpdateTableBO.getFunction_Name());
							
							if(j == 0){
								ret = UpdateReceiptPaymentTable(currentRrno, receiptTableDataBO , curBillDate, curBillNumber, totCredit);
							}
							
							if(j == 1){
								ret = UpdateConsumerCreditTable(currentRrno, receiptTableDataBO , curBillDate, curBillNumber, totCredit);
							}
							
							if(j == 2){
								ret = UpdateDeletedBillReceiptTable(currentRrno, receiptTableDataBO , curBillDate, curBillNumber, totCredit);
							}
							
							if(ret < 0){
								
								System.out.println(" Error While Updating : "+receiptTableDataBO.getRtt_tab_name());
								
								return Report_Error("Error While Updating", -1, 0);
							}
							
						}
						
						j++;
					}
					
				}
				
				i++;
				
			}
			
		}
			
			
		
		
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return 0;
}

private int UpdateDeletedBillReceiptTable(String currentRrno,
		ReceiptTableData receiptTableDataBO, String curBillDate,
		String curBillNumber, float totCredit) {
	// TODO Auto-generated method stub
	
	String l_rowId = "";
	ResultSet rs = null;
	
	try {
		
		l_rowId = receiptTableDataBO.getRowId() ;
		
		String Query =  "	UPDATE DELTD_BILL_RCPT SET DBR_ADJ_FLG = 'Y' WHERE ROWID = '"+l_rowId+"'  ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				int v = ReconProgram.ps.executeUpdate();
				
				if(v > 0){
				System.out.println("Record updated : " +receiptTableDataBO.getRtt_tab_name());
				}
				else{
					return -1 ;
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println(" Error Occured : "+e.getMessage());
	}
	
	
	return 0;
}

private int UpdateConsumerCreditTable(String currentRrno,
		ReceiptTableData receiptTableDataBO, String curBillDate,
		String curBillNumber, float totCredit) {
	// TODO Auto-generated method stub
	
	String l_rowId;
	float  l_credit_amt;  
	float  l_balance = (float) 0.0;
	String l_cc_credit_no;
	String l_rrno;
	String l_billDt;
	String l_billNo;
	
	int i = 0 ;
	
	ResultSet rs = null;
	
	try {
		l_rowId = receiptTableDataBO.getRowId() ;
		l_billDt = curBillDate ;
		l_billNo = curBillNumber ; 
		l_credit_amt = (float) receiptTableDataBO.getAmountPosted();
		
		
		String Query =  "	UPDATE CREDIT  " +
				        "	SET   CR_CREDITED_AMT = '"+l_credit_amt+"', " +
				        "   CR_CREDITED_DT = trunc(sysdate, 'dd'), " +
				        "   CR_USER = UPPER('"+ReconProgram.G_USER+"'), " +
				        "   CR_CREDIT_STS = 'Y', " +
				        "   CR_BILL_NO = '"+l_billNo+"', " +
				        "  	CR_BILL_DT = to_date('"+l_billDt+"', 'dd/mm/yyyy') " +
				        "	WHERE ROWID = '"+l_rowId+"'  ";

		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		int v = ReconProgram.ps.executeUpdate();
		
		if(v > 0){
		System.out.println("Record updated : " +receiptTableDataBO.getRtt_tab_name());
		}
		else{
			return -1 ;
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println(" Error Occured : "+e.getMessage());
	}
	
	return 0;
}

private int UpdateReceiptPaymentTable(String currentRrno,
		ReceiptTableData receiptTableDataBO, String curBillDate,
		String curBillNumber, float totCredit) {
	// TODO Auto-generated method stub
	
	String RowID = "";
	ResultSet rs = null;
	
	System.out.println("Updating table : "+receiptTableDataBO.getRtt_tab_name());
	
	RowID = receiptTableDataBO.getRowId() ;
	
	try {
		
		String Query =  "	UPDATE RCPT_PYMNT SET RP_POSTED_STS = 'Y' WHERE ROWID = '"+RowID+"' ";

		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		int v = ReconProgram.ps.executeUpdate();
		
		if(v > 0){
		System.out.println("Record updated : " +receiptTableDataBO.getRtt_tab_name());
		}
		else{
			return -1 ;
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		
		Query =  "	INSERT INTO 	RECON_LOG( RL_RCPT_NO,RL_RCPT_DT,RL_RCPT_CASH_CNTER,RL_PID,	RL_TMPSTP) " +
			                        "SELECT 	RP_RCPT_NO,RP_RCPT_DT,RP_CASH_COUNTR_NO,'"+ReconProgram.G_PROCESS_ID+"'," +
			                        		" 	SYSDATE " +
			                        		" 	FROM RCPT_PYMNT " +
			                        		"   WHERE ROWID = '"+RowID+"'  ";
		
		System.out.println("RECON_LOG : "+ Query);

		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		v = ReconProgram.ps.executeUpdate();
		
		if(v > 0){
		System.out.println("Record Inserted  RECON_LOG  : ");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return 0;
}

private int Report_Error(String Message, int errorCode, int reWrite) {
	// TODO Auto-generated method stub
	
	String l_err_msg = ""; 
	int    l_err_code = 0;
	String l_rrno = ""; 
	String l_ver = "";
	ResultSet rs = null;
	
	
	try {
		if(reWrite == 0){
			
			l_rrno = ReconProgram.G_CURRENT_RRNO ;
			l_err_msg = Message ;
			l_err_code = errorCode ; 
		}
		
		l_ver = ReconProgram.VERSION ; 
		
		String Query =  "	INSERT INTO recon_error_log( REL_ERR_RR_NO, " +
						"	REL_ERR_DT,	REL_ERR_CODE,REL_ERR_MSG,REL_VER ) " +
						"	VALUES('"+l_rrno+"', sysdate, '"+l_err_code+"', '"+l_err_msg+"', '"+l_ver+"')";

				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				int v = ReconProgram.ps.executeUpdate();
				
				if(v > 0){
				System.out.println("Record Inserted recon_error_log : ");
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return errorCode;
}

private int InsertIntoBillReceipt() {
	// TODO Auto-generated method stub
	
	String l_itemNo;
	String l_billDt;
	String l_rrno;
    float	l_amt_paid;
    String l_billNo;
	String l_user;
	String l_tmpstp;
	String l_charge_cd;
	String l_brb_rcpt_detl;
	String l_brb_detl_from;
	String l_brb_rcpt_dt;
	float l_revenue;
	float l_interest;
	float l_tax;
	float l_arr_revenue;
	float l_arr_int;
	float l_arr_tax;
	int l_num_cd;	
	String l_temp_dt = null;
	
	
	String dt1;
	String dt2;

    int i=0;
	int counter = 0; 
	l_num_cd = 0;
	
	l_revenue = (float) 0.0;
	l_interest = (float) 0.0;
	l_tax = (float) 0.0;
	l_arr_revenue = (float) 0.0;
	l_arr_int = (float) 0.0;
	l_arr_tax = (float) 0.0;
	
	String Query = "";
	ResultSet rs = null;
	BillReceiptBreakUpBO billRcptBrkpBO = null ;
	int v = 0;
	
	try {
		
		System.out.println("Inserting Data Into BillReceiptBreakup...!");
		
		System.out.println(" BillReceiptBreakUpList : "+ReconProgram.BillReceiptBreakUpList);
		
		if(ReconProgram.BillReceiptBreakUpList != null){
			
			System.out.println(" BillReceiptBreakUpList : "+ReconProgram.BillReceiptBreakUpList.size());
			
			Iterator<BillReceiptBreakUpBO> rcpt_it = ReconProgram.BillReceiptBreakUpList.iterator();
			
			while(rcpt_it.hasNext()){
				
				billRcptBrkpBO = (BillReceiptBreakUpBO)rcpt_it.next();
				
				if(billRcptBrkpBO.getAmountTobeAdjusted() > 0.01){
					
					System.out.println("Inseting : rrno : "+billRcptBrkpBO.getRrno()+" , chargcode : "+billRcptBrkpBO.getChargeCode()
							+ " , slno : "+billRcptBrkpBO.getMaxItemNum() + " , amountadjusted :  "+billRcptBrkpBO.getAmountTobeAdjusted() );
					
					
					
					l_itemNo  =  String.valueOf(billRcptBrkpBO.getMaxItemNum()); 
					l_rrno    =  billRcptBrkpBO.getRrno();
					l_billDt  =  billRcptBrkpBO.getBillDate();
					l_billNo  =  billRcptBrkpBO.getBillNumber();
					l_charge_cd = billRcptBrkpBO.getChargeCode();
					l_brb_rcpt_detl = billRcptBrkpBO.getRcpt_Detail();
					l_brb_detl_from = billRcptBrkpBO.getDetail_from();
					
					Query = "  SELECT to_char(to_date('"+l_billDt+"', 'dd/mm/yyyy'), 'YYYYMMDD') l_temp_dt from dual "; 
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					rs = ReconProgram.ps.executeQuery();
					
					if(rs.next()){
						l_temp_dt = rs.getString("l_temp_dt");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs);
					
					dt1 = l_temp_dt;
					
					if(dt1.equals(billRcptBrkpBO.getRcptDate())){
						l_brb_rcpt_dt = dt1;
					}else{
						l_brb_rcpt_dt = billRcptBrkpBO.getRcptDate() ;
					}
					
					l_amt_paid = (float) billRcptBrkpBO.getAmountTobeAdjusted();
					
					System.out.println("Inseting : rrno : "+billRcptBrkpBO.getRrno()+" , chargcode : "+billRcptBrkpBO.getChargeCode()
							+ " , slno : "+l_itemNo );
					System.out.println("rcpt_detail : "+l_brb_rcpt_detl+" , detail_from  : "+billRcptBrkpBO.getDetail_from());
					System.out.println("BRB_DETL_FROM : "+l_brb_detl_from+" , detail_from  : "+billRcptBrkpBO.getDetail_from());
					
					Query = "	INSERT INTO  BILL_RCPT_BREAKUP( " +
											"	BRB_RR_NO,BRB_BILL_DT, " +
											" 	BRB_BILL_NO,BRB_CHRG_CD, " +
											"   BRB_SLNO,BRB_AMT_PAID, " +
											"	BRB_RCPT_DT, " +
											"	BRB_RCPT_DETL, " +
											"	BRB_DETL_FROM, " +
											"   BRB_BILL_RCPT_STATUS, " +
											"	BRB_USER, " +
											"	BRB_TMPSTP " +
											"	)   " +
											"   VALUES (  " +
											"  '"+l_rrno+"', " +
											"  to_date('"+l_billDt+"', 'dd/mm/yyyy')," +
											"  '"+l_billNo+"', " +
											"  '"+l_charge_cd+"',  " +
											"  '"+l_itemNo+"' ,  " +
											"  '"+l_amt_paid+"', " +
											"   to_date('"+l_brb_rcpt_dt+"', 'yyyymmdd'), " +
										    "	'"+l_brb_rcpt_detl+"', " +
										    "   '"+l_brb_detl_from+"' ,  " +
										    "	'Y'," +
										    "    upper('"+ReconProgram.G_USER+"'), " +
										    "	sysdate)";
					
					counter++;
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					v = ReconProgram.ps.executeUpdate();
					
					if(v > 0){
						System.out.println("Record Inserted BILL_RCPT_BREAKUP : ");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs);
					
					
					Query = "  SELECT to_number('"+l_charge_cd+"') l_num_cd FROM dual "; 
					
					ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
					rs = ReconProgram.ps.executeQuery();
					
					if(rs.next()){
						l_num_cd = rs.getInt("l_num_cd");
					}
					DatabaseOperations.cleanUp(ReconProgram.ps, rs);
					
					switch (l_num_cd) {
					case 1:
					case 5:
					case 8:
					case 9:
					case 11:	
					case 27:
					case 77:
					case 88:
					case 89:	
						l_revenue = l_revenue + l_amt_paid;
						break;
							
					case 4:
					case 7:	
					case 67:
					case 81:
					case 82:
						l_interest = l_interest + l_amt_paid;
						break;
						
					case 2:
						l_tax = l_tax + l_amt_paid;
						break;
							
					case 1009:
					case 1011:
					case 1006:
						l_arr_revenue = l_arr_revenue + l_amt_paid;
						break;

					case 1067:
						l_arr_int = l_arr_int + l_amt_paid;
						break;

					case 1002:
						l_arr_tax = l_arr_tax + l_amt_paid;
						break;

					default:
						break;	
					}
					
				}
			}
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	
	return counter;
}

private int PostReceipts(String currentRrno) {
	// TODO Auto-generated method stub
	
	try {
		
		int i;
		int j;
		float dmd;
		float rcpt;
		
		System.out.println(" BillBreakupList : " +ReconProgram.BillBreakupList.size());
		
		if(ReconProgram.BillBreakupList != null){
			
			Iterator<BillBreakupBO> bb_it = ReconProgram.BillBreakupList.iterator();
			
			while(bb_it.hasNext()){
				
				BillBreakupBO brkpBO = (BillBreakupBO) bb_it.next();
				
				brkpBO.setAmountPaid(brkpBO.getAmountPaid() + GetAmountPosted(brkpBO.getChargeCode()));
				
				System.out.println("before brkpBO.getAmountPaid() : "+brkpBO.getAmountPaid());
				
			}
		}
		
		if(ReconProgram.BillBreakupList != null){
			
			Iterator<BillBreakupBO> bb_it = ReconProgram.BillBreakupList.iterator();
			
			while(bb_it.hasNext()){
				
				BillBreakupBO brkpBO = (BillBreakupBO) bb_it.next();
				
				//brkpBO.setAmountPaid(brkpBO.getAmountPaid() + GetAmountPosted(brkpBO.getChargeCode()));
				
				System.out.println(" after brkpBO.getAmountPaid() : "+brkpBO.getAmountPaid());
				
			}
		}
		
		i = j = 0;
		
		System.out.println("Inside The Post Receipts...!");
		
		System.out.println("MAX_BILL_BREAKUP_COUNT : "+ReconProgram.MAX_BILL_BREAKUP_COUNT);
		System.out.println("MAX_RECEIPT_COUNT : "+ReconProgram.MAX_RECEIPT_COUNT);
		System.out.println("MAX_BILL_RCPT_BREAKUP_COUNT : "+ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT);
		
		BillBreakupBO billbrkpBO = null;
		Iterator<BillBreakupBO> brkp_it = null;
		
		ReceiptTableData receiptTableDataBO = null;
		Iterator<ReceiptTableData> rcptTable_it = null;
		
		if(ReconProgram.BillBreakupList != null){
			brkp_it = ReconProgram.BillBreakupList.iterator();
		}
		
		if(ReconProgram.receiptTableDataList != null){
			rcptTable_it  = ReconProgram.receiptTableDataList.iterator();
		}
		
		
		while(i < ReconProgram.MAX_BILL_BREAKUP_COUNT && 
				j < ReconProgram.MAX_RECEIPT_COUNT){
			
			System.out.println("Looping "+(i + 1) + " ...... time ...");
		

			
			if(brkp_it.hasNext()){
				billbrkpBO = (BillBreakupBO)brkp_it.next();
			}
			
			if(rcptTable_it.hasNext()){
				receiptTableDataBO = (ReceiptTableData)rcptTable_it.next();
			}
			
			if(billbrkpBO.getAmountPaid() >= billbrkpBO.getAmount()){
				i++;
				continue;
			}
			
			if(receiptTableDataBO.getAmountPosted() >= receiptTableDataBO.getAmountReceived()){
				j++;
				continue;
			}
			
			dmd  = (float) (billbrkpBO.getAmount() - billbrkpBO.getAmountPaid());
			rcpt = (float) (receiptTableDataBO.getAmountReceived() - receiptTableDataBO.getAmountPosted());
			
			System.out.println("Looping : dmd  : "+dmd);
			System.out.println("Looping : rcpt  : "+rcpt);
			
			if(rcpt > dmd){
				billbrkpBO.setAmountPaid(billbrkpBO.getAmountPaid() + dmd);
				
				System.out.println("Amount To Be Posted : "+dmd);
				
				
				rcpt = rcpt - dmd ;
				
				PostIntoBreakupReceipt(billbrkpBO,receiptTableDataBO,dmd);
				
				/*receiptTableDataBO.setAmountPosted(receiptTableDataBO.getAmountPosted() + rcpt);*/
				
				receiptTableDataBO.setAmountPosted(receiptTableDataBO.getAmountPosted() + dmd);
				i++;
				continue;
			}else{
				
				billbrkpBO.setAmountPaid(billbrkpBO.getAmountPaid() + rcpt);
				
				PostIntoBreakupReceipt(billbrkpBO,receiptTableDataBO,rcpt);
				
				receiptTableDataBO.setAmountPosted(receiptTableDataBO.getAmountPosted() + rcpt );
				
				System.out.println("Amount To Be Posted  : "+(receiptTableDataBO.getAmountPosted()+rcpt));
				
				rcpt = 0;
				j++;
				continue;
			}
		}
		
		System.out.println("Out Of Receipts");
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return 1;
}

private float GetAmountPosted(String chargeCode) {
	// TODO Auto-generated method stub
	
	float amountPosted = (float) 0.0;

	try {
		
		if(ReconProgram.BillReceiptBreakUpList != null){
			System.out.println("GetAmountPosted : chargeCode : "+ chargeCode);
			Iterator<BillReceiptBreakUpBO> billRcp_it = ReconProgram.BillReceiptBreakUpList.iterator();
			
			while(billRcp_it.hasNext()){
				
				BillReceiptBreakUpBO billReceiptBO = (BillReceiptBreakUpBO)billRcp_it.next();
				
				if(billReceiptBO.getChargeCode().equals(chargeCode)){
					
					amountPosted += billReceiptBO.getAmountPaid() ;
					
					System.out.println("amountPosted : "+ amountPosted);
				}
			}
			
		}
		
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	
	
	return amountPosted;
}

private void PostIntoBreakupReceipt(BillBreakupBO billbrkpBO,
		ReceiptTableData receiptTableDataBO, float Amount) {
	// TODO Auto-generated method stub
	
	int i,j;
	int maxSlno = 0 ;
	
	BillReceiptBreakUpBO billRcptBrkpBO = null;
	
	
	try {
		
		if(ReconProgram.BillReceiptBreakUpList != null){
			
			Iterator<BillReceiptBreakUpBO> bill_rcpt_it = ReconProgram.BillReceiptBreakUpList.iterator();
			
			while(bill_rcpt_it.hasNext()){
				
				billRcptBrkpBO = (BillReceiptBreakUpBO)bill_rcpt_it.next();
				
				if(billbrkpBO.getChargeCode().equals(billRcptBrkpBO.getChargeCode())){
					
					maxSlno = billRcptBrkpBO.getMaxItemNum();
					
					Iterator<BillReceiptBreakUpBO> rcpt_it_temp = ReconProgram.BillReceiptBreakUpList.iterator();
					
					while(rcpt_it_temp.hasNext()){
						BillReceiptBreakUpBO rcptTempBO = (BillReceiptBreakUpBO)rcpt_it_temp.next();
						
						if((billbrkpBO.getChargeCode().equals(rcptTempBO.getChargeCode())) 
							&& rcptTempBO.getMaxItemNum() > maxSlno){
							
							maxSlno = rcptTempBO.getMaxItemNum() ; 
						}
					}
					break;
				}
			}
		}
		
		i = ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT++;
		
		System.out.println(" i = "+i + " , MAX_BILL_RCPT_BREAKUP_COUNT : "+ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT);
		
		BillReceiptBreakUpBO billReceiptBreakUpBO_NEW = new BillReceiptBreakUpBO();
		
		billReceiptBreakUpBO_NEW.setChargeCode(billbrkpBO.getChargeCode());
		billReceiptBreakUpBO_NEW.setMaxItemNum(++maxSlno);
		billReceiptBreakUpBO_NEW.setAmountTobeAdjusted(Amount);
		billReceiptBreakUpBO_NEW.setRrno(billbrkpBO.getRrno());
		billReceiptBreakUpBO_NEW.setBillDate(billbrkpBO.getBillDate());
		billReceiptBreakUpBO_NEW.setBillNumber(billbrkpBO.getBillNumber());
		billReceiptBreakUpBO_NEW.setRcptDate(receiptTableDataBO.getRtt_date());
		
		System.out.println("RRNO : "+billReceiptBreakUpBO_NEW.getRrno()
				+" , Charge code : "+billReceiptBreakUpBO_NEW.getChargeCode()
				+" , amount : " + billReceiptBreakUpBO_NEW.getAmountTobeAdjusted());
		
		billReceiptBreakUpBO_NEW.setDetail_from(GetDetail_From(receiptTableDataBO.getRtt_tab_name()));
		
		System.out.println("Receipt Key : Detail_from( " + billReceiptBreakUpBO_NEW.getDetail_from());
		
		billReceiptBreakUpBO_NEW.setRcpt_Detail(GetDetailsFrom(receiptTableDataBO.getRtt_tab_name(),receiptTableDataBO.getRowId()));
		
		System.out.println("Receipt Key : Rcpt_Detail " + billReceiptBreakUpBO_NEW.getRcpt_Detail());
		
		ReconProgram.BillReceiptBreakUpList.add(billReceiptBreakUpBO_NEW);
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
}

private String GetDetail_From(String table_Name){
	
	String l_rcpt_detl_table = "";
	String l_rcpt_detl_key = "";;
	String Query = "";
	ResultSet rs = null;
	
	System.out.println("table_name : "+table_Name);
	
	try {

		if(table_Name.equals("BILL_DAILY_TRANS")){
			
			Query = "  SELECT ltrim(to_char(nvl(a.dbr_rcpt_amt,1), '09'))  dbr_rcpt_amt " +
					"  FROM DELTD_BILL_RCPT a " +
					"  WHERE a.DBR_rr_no = '"+ReconProgram.G_CURRENT_RRNO +"'" +
					"  AND a.DBR_rcpt_dt = to_date('"+ReconProgram.G_RECON_DATE+"','DD/MM/YYYY') " +
					"  AND nvl(a.DBR_adj_flg,'N') != 'Y' AND rownum < 2 "; 
			
			System.err.println(Query);
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs = ReconProgram.ps.executeQuery();
			
			if(rs.next()){
				
				l_rcpt_detl_key = rs.getString("dbr_rcpt_amt");
				
			}
			
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		l_rcpt_detl_table  = table_Name ; 
		
		
		Query = "  SELECT RCPT_DETL_KEY " +
				"  FROM DETL_FROM_TABLE  " +
				"  WHERE RCPT_DETL_TABLE = '"+l_rcpt_detl_table + "'" ; 
		
		System.out.println("GetDetail_From , Query = "+ Query);
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		if(rs.next()){
			
			l_rcpt_detl_key = rs.getString("RCPT_DETL_KEY");
			
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);

	} catch (Exception e) {
		// TODO: handle exception
		return "";
	}
	return l_rcpt_detl_key;
}

private String GetDetailsFrom(String table_Name, String rowId) {
	// TODO Auto-generated method stub
	
	String sqlStmt = null;
	String outStr = null;
	String l_rcpt_detl_key= null;
	String l_rcpt_detl_table= null;
	String l_rd_detl_key= null;
	String l_rd_column_name= null;
	String l_rd_column_type= null;
	String l_rd_column_size= null;
	String l_rd_column_format= null;
	String l_rd_slno= null;
	String l_row_id= null;
	String l_new_rowid= null;
	
	String outPutString = null;
	String temp= null;
	String p= null;
	int counter = 0;
	String Query = "";
	ResultSet rs = null;
	
	try {
		
		if(table_Name.equals("DELTD_BILL_RCPT"))
		{
			l_row_id = rowId;
			
			Query = "  SELECT a.rowid l_new_rowid " +
					"  from RCPT_PYMNT a, DELTD_BILL_RCPT b " +
					"  WHERE b.rowid = '"+l_row_id+"' " +
					"  AND   a.RP_rcpt_no = b.dbr_rcpt_no " +
					"  and   a.RP_rcpt_dt = b.dbr_rcpt_dt " +
					"  and   a.RP_cash_countr_no = b.dbr_cash_cntr "; 
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs = ReconProgram.ps.executeQuery();
			
			if(rs.next()){
				l_new_rowid = rs.getString("l_new_rowid");
				
				table_Name = "RCPT_PYMNT";
				rowId = l_new_rowid ;
			}
			DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		}
		
		
		l_rcpt_detl_table = table_Name ;
		
		Query = "  SELECT RCPT_DETL_KEY l_rcpt_detl_key  " +
				"  FROM DETL_FROM_TABLE  " +
				"  WHERE RCPT_DETL_TABLE = '"+l_rcpt_detl_table+"' "; 
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		if(rs.next()){
			l_rcpt_detl_key = rs.getString("l_rcpt_detl_key");
		}else{
			return null;
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		outPutString = "  SELECT  " ;
		
		counter = 0;
		
		Query = "  SELECT TO_NUMBER(DFC_SL_NO) l_rd_slno,DFC_COLUMN_NAME l_rd_column_name," +
				"  DFC_COLUMN_TYPE l_rd_column_type,NVL(DFC_COLUMN_SIZE, 0) l_rd_column_size," +
				"  NVL(DFC_COLUMN_FORMAT, ' ') l_rd_column_format " +
				"  FROM DETL_FROM_COLUMN  " +
				"  WHERE DFC_DETL_KEY = '"+l_rcpt_detl_key+"' " +
				"  ORDER BY 1 "; 
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		while(rs.next()){
			l_rd_slno = rs.getString("l_rd_slno");
			l_rd_column_name = rs.getString("l_rd_column_name");
			l_rd_column_type = rs.getString("l_rd_column_type");
			l_rd_column_size = rs.getString("l_rd_column_size");
			l_rd_column_format = rs.getString("l_rd_column_format");
			
			if(counter > 0){
				outPutString = outPutString.concat("||'~'||");
			}
			
			if(l_rd_column_type.equals("D")){
				temp = " to_char("+l_rd_column_name+",'"+l_rd_column_format+"') " ;
			}else{
				temp = l_rd_column_name +" " ;
			}
			
			outPutString = outPutString.concat(temp);
			
			counter++;
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		temp = " from "+table_Name + " where rowid = '"+rowId+"' " ;
		
		outPutString = outPutString.concat(temp);
		
		System.out.println("outputstring = "+outPutString);
		
		
		Query = outPutString ; 
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		System.out.println("Inside Outputstring cursor ");
		while(rs.next()){
			outStr = rs.getString(1);
			System.out.println("outstr = "+outStr);
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, rs);
		
		outPutString = "";
		
		outPutString = outStr ; 
		
		System.out.println("return outputstring : "+outPutString);
	} 
	catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return outPutString;
}

private int LoadBillReceuiptBreakupTab(String currentRrno, String curBillDate,
		String curBillNumber) {
	// TODO Auto-generated method stub
	
	String chargeCode;
	int maxSlno;
	float amountPaid;
	String charge;
	String billNumber;
	int priority;

	String l_rrno;
	String l_billDate;
	String l_billNo;
	
	ResultSet rs1 = null;
	
	try {
		
		
		int counter = 0;
		int recordsFound = 0;
		
		l_rrno = currentRrno;
		l_billDate = curBillDate;
		l_billNo = curBillNumber;
		
		String Query = " SELECT  B.PAP_PRIORITY priority,A.BRB_BILL_NO billNumber," +
				       " A.BRB_CHRG_CD chargeCode,	A.BRB_AMT_PAID amountPaid,	" +
				       " TO_NUMBER(NVL(A.BRB_SLNO,'0')) maxSlno,	B.PAP_DESCR  charge   " +
				       " FROM BILL_RCPT_BREAKUP A, PYMNT_ADJ_PRIORITY B " +
				       " WHERE A.BRB_RR_NO = '"+l_rrno+"' " +
				       " AND   A.BRB_BILL_DT = TO_DATE('"+l_billDate+"', 'dd/mm/yyyy') " +
				       " AND   A.BRB_BILL_NO = '"+l_billNo+"' " +
				       " AND   A.BRB_CHRG_CD = B.PAP_CHRG_CD " +
				       " ORDER BY 1, 3, 5";
		
		System.out.println(" Receipts Query = "+Query);
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs1 = ReconProgram.ps.executeQuery();
		
		BillReceiptBreakUpBO bRcptBO = null;
		
		bRcptBO = new BillReceiptBreakUpBO();
		bRcptBO.setChargeCode(null);
		bRcptBO.setAmountPaid(0);
		
		while(rs1.next()){
			
			priority = rs1.getInt("priority");
			billNumber = rs1.getString("billNumber");
			chargeCode = rs1.getString("chargeCode");
			amountPaid = rs1.getFloat("amountPaid");
			maxSlno = rs1.getInt("maxSlno");
			charge = rs1.getString("charge");
			
			if(!chargeCode.equals(bRcptBO.getChargeCode())){
				
				counter++;
				bRcptBO = new BillReceiptBreakUpBO();
				bRcptBO.setAmountPaid(0);
			}
			
			bRcptBO.setRrno(l_rrno);
			bRcptBO.setBillNumber(billNumber);
			bRcptBO.setBillDate(l_billDate);
			
			bRcptBO.setChargeCode(chargeCode);
			bRcptBO.setAmountPaid(bRcptBO.getAmountPaid() + amountPaid );
			bRcptBO.setMaxItemNum(maxSlno);
			bRcptBO.setAmountTobeAdjusted(0.0);
			
			System.out.println("chargecode : "+bRcptBO.getChargeCode()
					+", amountpaid : "+bRcptBO.getAmountPaid() 
					+", maxslno : "+maxSlno
					+", charge : "+charge);
			
			recordsFound = 1 ;	
			
			ReconProgram.BillReceiptBreakUpList.add(bRcptBO);
			
			
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
		
		if(recordsFound == 1){
			ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT = counter + 1 ;
		}
		
		System.out.println("Records From :  BRB  : "+ReconProgram.BillReceiptBreakUpList);
		
		System.out.println("Records From :  BRB  : "+ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT);
		System.out.println("rrno : "+currentRrno + " , bill date : "+curBillDate);
		
		
	}catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return ReconProgram.MAX_BILL_RCPT_BREAKUP_COUNT;
	
	
}

private int LoadBillBreakupTab(String currentRrno, String curBillDate,
		String curBillNumber) {
	// TODO Auto-generated method stub
	
	String chargeCode;
	String bill_no;
	short priority;
	short jv_priority;
	float amount;
	float amount_wdl = (float) 0.0;
	float amount_rbt = (float) 0.0;

	String charge;
	String l_rrno;
	String l_billDate;
	String l_billNo;
	int l_order = 1;
	ResultSet rs1 = null ;
	
	try {
		
		if(ReconProgram.G_GOVT_ADJ > 0){
			l_order = 6;
		}
		
		l_rrno = currentRrno;
		l_billDate = curBillDate;
		l_billNo = curBillNumber;
		
		System.out.println("GOV-ADJ : "+ReconProgram.G_GOVT_ADJ);
		
		String Query = " SELECT  decode("+l_order+",6, B.PAP_JV_PRIORITY, B.PAP_PRIORITY) priority, " +
				       " A.BB_BILL_NO bill_no,A.BB_CHRG_CD chargeCode,	A.BB_AMT_BILLED amount," +
				       " B.PAP_DESCR charge,B.PAP_JV_PRIORITY jv_priority " +
				       " FROM BILL_BREAKUP A, PYMNT_ADJ_PRIORITY B  " +
				       " WHERE A.BB_RR_NO = '"+l_rrno+"' " +
				       " AND   A.BB_BILL_DT = TO_DATE('"+l_billDate+"', 'dd/mm/yyyy')  " +
				       " AND   A.BB_BILL_NO = '"+l_billNo+"' " +
				       " AND   A.BB_CHRG_CD = B.PAP_CHRG_CD " +
				       " ORDER BY 1 ";
	
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs1 = ReconProgram.ps.executeQuery();
		
		while(rs1.next()){
			priority    = rs1.getShort("priority");
			bill_no     = rs1.getString("bill_no");
			chargeCode  = rs1.getString("chargeCode");
			amount      = rs1.getFloat("amount");
			charge      = rs1.getString("charge");
			jv_priority = rs1.getShort("jv_priority");
			
			System.out.println("priority : "+priority + " , bill_no : "+bill_no+ " , chargeCode : "+chargeCode
					+ " , amount : "+amount + " , charge : "+charge+ " , jv_priority : "+jv_priority);
			
			amount_wdl = (float) 0.0 ;
			
			amount_wdl = GetWithdrawlAmount(currentRrno,curBillDate,curBillNumber,chargeCode);
			
			if(amount_wdl > 0.001){
				amount -= amount_wdl ;
			}
			
			amount_rbt = GetRebateAmount(currentRrno,curBillDate,curBillNumber,chargeCode);
			
			if(amount_rbt > 0.001){
				amount -= amount_rbt ;
			}
			
			BillBreakupBO brkpBO = new BillBreakupBO();
			brkpBO.setPriority(priority);
			brkpBO.setChargeCode(chargeCode);
			brkpBO.setAmount(amount);
			brkpBO.setRrno(l_rrno);
			brkpBO.setBillDate(l_billDate);
			brkpBO.setBillNumber(l_billNo);
			brkpBO.setAmountPaid(0.0);
			
			System.out.println("priority : "+brkpBO.getPriority() + " , chargecode : "+brkpBO.getChargeCode() 
					+ " , amount : "+brkpBO.getAmount());
			
			ReconProgram.BillBreakupList.add(brkpBO);
			
			
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
		
		
		ReconProgram.MAX_BILL_BREAKUP_COUNT =  ReconProgram.BillBreakupList.size();
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	
	
	return ReconProgram.MAX_BILL_BREAKUP_COUNT;
}

private float GetRebateAmount(String currentRrno, String curBillDate,
		String curBillNumber, String chargeCode) {
	// TODO Auto-generated method stub
	
	String l_chargeCode;
	String bill_no;
	int priority;
	float amount = 0;
	short ind;

	String charge;

	String l_rrno;
	String l_billDate;
	String l_billNo;
	
	ResultSet rs1 = null ;

	try {
		
		l_rrno = currentRrno;
		l_chargeCode = chargeCode ;
		l_billDate = curBillDate ;
		l_billNo = curBillNumber ;
		
		amount = (float) 0.0 ;
		
		
		String Query = " SELECT sum(nvl(BRD_REBATE_AMT, 0)) amount  " +
				       " FROM BILL_REBATE_DETL  " +
				       " WHERE BRD_RR_NO = '"+l_rrno+"'  " +
				       " AND   BRD_BILL_DT = to_date('"+l_billDate+"', 'dd/mm/yyyy')  " +
				       " AND   BRD_BILL_NO = '"+l_billNo+"' " +
				       " AND   BRD_CHRG_CD = '"+l_chargeCode+"' "; 

			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs1 = ReconProgram.ps.executeQuery();
			
			if(rs1.next()){
				amount = rs1.getFloat("amount");
			}else{
				amount = (float) 0.0 ;
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
		
	}catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occureed : "+e.getMessage());
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return amount;
}

private float GetWithdrawlAmount(String currentRrno, String curBillDate,
		String curBillNumber, String chargeCode) {
	// TODO Auto-generated method stub
	
	String l_chargeCode;
	String bill_no;
	int priority;
	float amount;
	float amount_wdl = 0;
	short  ind;

	String charge;

	String l_rrno;
	String l_billDate;
	String l_billNo;
	
	ResultSet rs1 = null ;

	try {
		
		l_rrno = currentRrno;
		l_chargeCode = chargeCode ;
		l_billDate = curBillDate ;
		l_billNo = curBillNumber ;
		
		amount_wdl = (float) 0.0 ;
		
		
		String Query = " SELECT sum(nvl(WD_AMT_WDRN , 0.0)) amount_wdl " +
				       " FROM WDRL_DETL  " +
				       " WHERE WD_RR_NO = '"+l_rrno+"'  " +
				       " AND   WD_BILL_DT =  to_date('"+l_billDate+"', 'dd/mm/yyyy') " +
				       " AND   WD_BILL_NO = '"+l_billNo+"' " +
				       " AND   WD_CHRG_CD = '"+l_chargeCode+"' " +
				       " AND   WD_ACCT_STS = 'Y' " +
				       " AND   WD_APPRV_STS = 'Y' ";

			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			rs1 = ReconProgram.ps.executeQuery();
			
			if(rs1.next()){
				amount_wdl = rs1.getFloat("amount_wdl");
			}else{
				amount_wdl = (float) 0.0 ;
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
		
	}catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occureed : "+e.getMessage());
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return amount_wdl;
}

private int Load_Receipts(String currentRrno) {
	// TODO Auto-generated method stub
	
	String rtt_tab_name;
	String rtt_date;
	String rtt_rowId;
	float amountReceived;
	float amountPosted;
	char status;
	String l_rtt_rr_no;
	String l_sts = null;
	
	int i;
	int counter = 0;
	String Query = "";
	
	ResultSet rs = null ;
	ResultSet rs1 = null;
	
	System.out.println("Inside Load Receipts Function ...!");
	
	try {
		
		l_rtt_rr_no = currentRrno ; 
		
		Query = "  SELECT RTT_TAB_NAME,TO_CHAR(RTT_DATE, 'yyyymmdd')  RTT_DATE ," +
				"  RTT_ROWID,RTT_AMT  " +
				"  FROM RECON_TMP_TAB " +
				"  WHERE  RTT_PID = '"+ReconProgram.G_PROCESS_ID+"'  " +
				"  AND RTT_RR_NO = '"+l_rtt_rr_no+"'   " +
				"  AND RTT_AMT > 0  " ;
		
		System.out.println("rcpt : "+Query);
			
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		rs = ReconProgram.ps.executeQuery();
		
		while(rs.next()){
			
			rtt_tab_name = rs.getString("RTT_TAB_NAME");
			rtt_date  = rs.getString("RTT_DATE");
			rtt_rowId   =  rs.getString("RTT_ROWID");
			amountReceived  =  rs.getFloat("RTT_AMT");
			
			System.out.println("RTT Tab : "+rtt_tab_name + " , AMT RECEIVED : " +amountReceived  );
			
			if(rtt_tab_name.equals("RCPT_PYMNT")){
				
				Query = " SELECT NVL(RP_POSTED_STS, 'N') l_sts " +
						" FROM RCPT_PYMNT WHERE rowid = '"+rtt_rowId+"' ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				rs1 =  ReconProgram.ps.executeQuery(); 
				
				if(rs1.next()){
					l_sts = rs1.getString("l_sts");
				}
				
				if(l_sts.equals("Y")){
					continue ;
				}
				
				DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			}
			
			if(rtt_tab_name.equals("CREDIT")){
				
				Query = " SELECT NVL(CR_CREDIT_STS, 'N') l_sts " +
						" FROM CREDIT WHERE rowid = '"+rtt_rowId+"' ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				rs1 =  ReconProgram.ps.executeQuery(); 
				
				if(rs1.next()){
					l_sts = rs1.getString("l_sts");
				}
				
				if(l_sts.equals("Y")){
					continue ;
				}
				
				DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			}
			
			if(rtt_tab_name.equals("DELTD_BILL_RCPT")){
				
				Query = " SELECT NVL(DBR_ADJ_FLG, 'N') l_sts " +
						" FROM DELTD_BILL_RCPT WHERE rowid = '"+rtt_rowId+"' ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				rs1 =  ReconProgram.ps.executeQuery(); 
				
				if(rs1.next()){
					l_sts = rs1.getString("l_sts");
				}
				
				if(l_sts.equals("Y")){
					continue ;
				}
				
				DatabaseOperations.cleanUp(ReconProgram.ps, rs1);
			}
			
			if(counter > 0){
				
				/*if( strncmp( (char *) rtt_rowId.arr, 
						rcptTableDataTab[counter - 1].rowId,
						rtt_rowId.len ) == 0)
					continue;  */
			}
			
			
			ReceiptTableData receiptTableDataBO = new ReceiptTableData();
			
			receiptTableDataBO.setRtt_tab_name(rtt_tab_name);
			receiptTableDataBO.setRtt_date(rtt_date);
			receiptTableDataBO.setRowId(rtt_rowId);
			receiptTableDataBO.setAmountReceived(amountReceived);
			receiptTableDataBO.setAmountPosted(0.0);
			
			
			
			
			System.out.println("Load Receipts , RRno : "+currentRrno);
			
			System.out.println("LAST , Table : "+receiptTableDataBO.getRtt_tab_name() + " " +
					" , Date : "+receiptTableDataBO.getRtt_date() + " " + 
					" , AmountReceived  : "+receiptTableDataBO.getAmountReceived());
			
			
			ReconProgram.receiptTableDataList.add(receiptTableDataBO);
			
			counter++;
		}
		
		ReconProgram.MAX_RECEIPT_COUNT = counter ; 
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		if(ReconProgram.receiptTableDataList != null){
			
			Iterator<ReceiptTableData> rcpt_it = ReconProgram.receiptTableDataList.iterator();
			
			while(rcpt_it.hasNext()){
				
				ReceiptTableData receiptTableDataBO = (ReceiptTableData) rcpt_it.next();
				
				Query = " UPDATE RECON_TMP_TAB SET RTT_AMT = 0 	WHERE RTT_ROWID = '"+receiptTableDataBO.getRowId()+"' " ;
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				int u = ReconProgram.ps.executeUpdate();
				if(u > 0){
					System.out.println("RECON_TMP_TAB updated to RTT_AMT = 0 . ");
				}
			}
			
			
		}
		
		
	} catch (SQLException e) {
		// TODO: handle exception
	}
	
	return counter;
}

private int Build_Recon_Temp_Table(String reconDate) {
	// TODO Auto-generated method stub
	
	System.out.println("Inside Bulid_Recon_Template...!");
	System.out.println("ReconDate : "+reconDate);
	
	String recDate;
	int rrt_index = 1;
	String l_rrno = null;
	String l_sts;
	String l_bill_dt = null;
	String l_bill_no = null;
	String l_credit_amt = null;
	String l_credit_dt = null;
	String l_credit_rowId = null;
	short  ind_bdt, ind_bno;
	String l_d_rrno;
	String l_d_bill_dt; 
	String l_d_bill_no;
	String l_d_adj_amt;
	String l_d_adj_rowId;
	String l_d_adj_dt;
	int i;
	int l;	
	short  ind_d_bdt, ind_d_bno;
	String l_tmp_rowid;
	
	String Query = "";
	int ins = 0;
	
	recDate = reconDate;
	try {
		 
		Query = " INSERT INTO RECON_TMP_TAB  " +
				" SELECT '"+rrt_index+"'," +
				" RTRIM(a.RP_PURPOSE_KEY), " +
				" 'RCPT_PYMNT', " +
				" a.RP_RCPT_DT, " +
				" a.ROWID, " +
				" nvl(a.RP_AMT_PAID,0), " +
				" UPPER('"+ReconProgram.G_USER+"')," +
				" SYSDATE, " +
				" '"+ReconProgram.G_PROCESS_ID+"'  " +
			    "  	FROM RCPT_PYMNT a  " +
			    " WHERE a.RP_RCPT_DT = TO_DATE('"+recDate+"', 'dd/mm/yyyy') " +
			    " AND   a.RP_PURPOSE = '1' " +
			    " AND   nvl(a.RP_CANCEL_FLG, 'N') != 'Y' " +
			    " AND   nvl(a.RP_POSTED_STS, 'N') != 'Y' " +
			    " AND   NOT EXISTS( " +
			                       " SELECT 'X' FROM RECON_LOG " +
			    			       "  WHERE RL_RCPT_NO  = a.RP_RCPT_NO " +
			    			       "  AND RL_RCPT_DT  = a.RP_RCPT_DT " +
			    			       "  AND RL_RCPT_CASH_CNTER = a.RP_CASH_COUNTR_NO)  ";
		
		System.out.println("RECON_TMP_TAB query : "+Query);
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ins = ReconProgram.ps.executeUpdate();
		
		if(ins > 0){
			System.out.println("Records Inserted to RECON_TMP_TAB : " + ins +"  , With Index :  " + rrt_index +" ...!");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		rrt_index = 3;
		
		
		Query = " INSERT INTO RECON_TMP_TAB  " +
				" SELECT '"+rrt_index+"'," +
				" RTRIM(C.CR_RR_NO), " +
				" 'CREDIT', " +
				" C.CR_CREDIT_DT, " +
				" C.ROWID, " +
				" C.CR_CREDIT_AMT, " +
				" UPPER('"+ReconProgram.G_USER+"')," +
				" SYSDATE, " +
				" '"+ReconProgram.G_PROCESS_ID+"'  " +
			    "  	FROM CREDIT C  " +
			    	"  WHERE EXISTS  " +
			    	          " ( SELECT 'X' " +
			    	          "  FROM RCPT_PYMNT A, SHOP_BILL B " +
			    	      /*    "  WHERE A.RP_RCPT_DT = to_date('"+recDate+"', 'dd/mm/yyyy') " +
			    	          "  AND   A.RP_PURPOSE = '1' " +
			    	          "  AND   nvl(A.RP_CANCEL_FLG,'N') != 'Y' " +
			    	          "  AND   nvl(A.RP_POSTED_STS,'N') != 'Y' " +
			    	          "  AND   A.RP_PURPOSE_KEY = C.CC_RR_NO " +
			    	          "  AND   A.RP_PURPOSE_KEY = B.CB_RR_NO " +
			    	          "  AND   B.CB_RR_STS != 'I' " +
			    	          "  AND   C.CC_CREDIT_STS = 'N' " +
			    	          "  AND   C.CC_CREDIT_AMT > 0.0)  ";*/
		
		                      " WHERE B.SB_RR_NO = C.CR_RR_NO  " +
		                      " AND   B.SB_RR_STS != 'I'   " +
		                      " AND   C.CR_CREDIT_STS = 'N'   " +
		                      " AND   C.CR_CREDIT_AMT > 0.0  " +
		                      " AND   A.RP_RCPT_DT = to_date('"+recDate+"', 'dd/mm/yyyy')  " +
		                      " AND   A.RP_PURPOSE_KEY = B.SB_RR_NO  " +
		                      " AND   A.RP_PURPOSE = '1'   " +
		                      " AND   nvl(A.RP_CANCEL_FLG,'N') != 'Y'   " +
		                      " AND   nvl(A.RP_POSTED_STS,'N') != 'Y' ) " ; 
		
		
		/*
		 * 	WHERE B.CB_RR_NO = C.CC_RR_NO AND   B.CB_RR_STS != 'I'   AND   C.CC_CREDIT_STS = 'N'   AND   C.CC_CREDIT_AMT > 0.0
	 and A.RP_RCPT_DT >= to_date('01/01/2014', 'dd/mm/yyyy')	and A.RP_RCPT_DT <= to_date('31/01/2016', 'dd/mm/yyyy')  AND   A.RP_PURPOSE_KEY = b.Cb_RR_NO
		AND   A.RP_PURPOSE = '1'   AND   nvl(A.RP_CANCEL_FLG,'N') != 'Y'   AND   nvl(A.RP_POSTED_STS,'N') != 'Y' 
		*/
		System.out.println("RECON_TMP_TAB, rtt_index = 3, query : "+Query);
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ins = ReconProgram.ps.executeUpdate();
		
		if(ins > 0){
			System.out.println("Records Inserted to RECON_TMP_TAB : " + ins +"  , With Index :  " + rrt_index +" ...!");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		
		Query = " SELECT  RTRIM(A.SB_RR_NO)  SB_RR_NO, " +
				"  to_char(A.SB_BILL_DT, 'YYYYMMDD')  SB_BILL_DT, " +
				"   A.SB_BILL_NO SB_BILL_NO," +
				"   C.CR_CREDIT_AMT  CR_CREDIT_AMT, " +
				"   C.rowid row_id, " +
				"   to_char(C.CR_CREDIT_DT, 'YYYYMMDD')  CR_CREDIT_DT " +
				" 	FROM SHOP_BILL A, " +
				"   CREDIT C " +
				"   WHERE A.SB_RR_STS = 'I' " +
				"   AND   A.SB_RR_NO  = C.CR_RR_NO" +
				"   AND   NVL(C.CR_CREDIT_STS, 'N') = 'N' " +
				"   AND   C.CR_CREDIT_AMT >= 0.01 " ; 
		
		System.out.println("SHOP_BILL : "+Query);
	
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		if(ReconProgram.rs.next()){
			
			l_rrno = ReconProgram.rs.getString("SB_RR_NO");
			l_bill_dt = ReconProgram.rs.getString("SB_BILL_DT");
			l_bill_no = ReconProgram.rs.getString("SB_BILL_NO");
			l_credit_amt  = ReconProgram.rs.getString("CR_CREDIT_AMT");
			l_credit_rowId = ReconProgram.rs.getString("row_id");
			l_credit_dt = ReconProgram.rs.getString("CR_CREDIT_DT");
				
			System.out.println("Fetching Credit Data RRno : "+l_rrno+ " , credit date : "+l_credit_dt 
					+ "  , credit Amount : "+l_credit_amt);
			
			Query = "   INSERT INTO RECON_TMP_TAB " +
					"   SELECT '" + rrt_index + "', " + 
					"	'"+l_rrno+"', " +
					" 	' CREDIT', " +
					"	to_date('"+l_credit_dt+"', 'YYYYMMDD'), " +
					" 	'"+l_credit_rowId+"', " +
					"   '"+l_credit_amt+"', " +
					"	UPPER('"+ReconProgram.G_USER+"'),  " +
					"	SYSDATE, " +
					"   '"+ReconProgram.G_PROCESS_ID+"'  " +
					" 	FROM	BILL_MASTER  " +
					"	WHERE   BM_RR_NO = '"+l_rrno+"'  " +
					"	AND     BM_BILL_DT = to_date('"+l_bill_dt+"', 'YYYYMMDD')  " +
					" 	AND     BM_BILL_NO = '"+l_bill_no+"'  ";
			
			System.out.println("RECON_TMP_TAB, rtt_index = 3, query : "+Query);
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int r = ReconProgram.ps.executeUpdate();
			
			if(r > 0){
				System.out.println("Record Inserted ... : "+Query);
			}
			
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		
		Query = "  SELECT  " +
				"  RTRIM(A.DBR_RR_NO) DBR_RR_NO, " +
				"  to_char(A.DBR_BILL_DT, 'YYYYMMDD') DBR_BILL_DT, " +
				"  A.DBR_BILL_NO  DBR_BILL_NO," +
				"  A.DBR_BILL_ADJ_AMT  DBR_BILL_ADJ_AMT, " +
				"  A.rowid row_id ," +
				"  to_char(DBR_RCPT_DT, 'YYYYMMDD')  DBR_RCPT_DT  " +
				"  FROM DELTD_BILL_RCPT A " +
				"  where A.DBR_RCPT_DT = to_date('"+recDate+"', 'dd/mm/yyyy') " +
				"  AND   NVL(A.DBR_ADJ_FLG,'N') != 'Y' " +
				"  AND   A.DBR_BILL_ADJ_AMT >= 0.01 " +
				"  AND EXISTS ( SELECT 'X' from BILL_MASTER B " +
				"  WHERE B.BM_RR_NO = A.DBR_RR_NO " +
				/*"  AND   b.CBM_bill_dt = a.CDCR_bill_dt)";*/
				"  AND   B.BM_bill_dt >= A.DBR_BILL_DT)";
		
		System.out.println("DELTD_BILL_RCPT : "+Query);
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		rrt_index = 4 ;
		
		while(ReconProgram.rs.next()){
			
			l_d_rrno = ReconProgram.rs.getString("DBR_RR_NO");
			l_d_bill_dt = ReconProgram.rs.getString("DBR_BILL_DT");
			l_d_bill_no = ReconProgram.rs.getString("DBR_BILL_NO");
			l_d_adj_amt = ReconProgram.rs.getString("DBR_BILL_ADJ_AMT");
			l_d_adj_rowId = ReconProgram.rs.getString("row_id");
			l_d_adj_dt = ReconProgram.rs.getString("DBR_RCPT_DT");
			
			System.out.println("Fetching Deleted Data RRno : "+l_d_rrno+ " , Adj date : "+l_d_adj_dt 
					+ "  , Adj Amount : "+l_d_adj_amt);
			
			Query = "  INSERT INTO RECON_TMP_TAB  " +
					"  SELECT '"+rrt_index+"', " +
					"  '"+l_d_rrno+"', " +
					"  'DELTD_BILL_RCPT', " +
					"   to_date('"+l_d_adj_dt+"', 'YYYYMMDD'), " +
					"   '"+l_d_adj_rowId+"', " +
					"	'"+l_d_adj_amt+"', " +
					"  	UPPER('"+ReconProgram.G_USER+"'), " +
					"   SYSDATE, " +
					"   '"+ReconProgram.G_PROCESS_ID+"'  " +
					"	FROM	BILL_MASTER  " +
					"	WHERE   BM_RR_NO = '"+l_d_rrno+"'  " +
					"	AND     BM_BILL_DT = to_date('"+l_d_bill_dt+"', 'YYYYMMDD')  ";
			
			System.out.println("Query : "+Query);
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int r = ReconProgram.ps.executeUpdate();
			
			if(r > 0){
				System.out.println("Record Inserted .... : "+l_d_rrno);
			}
		}
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		
		/*Query = " SELECT A.ROWID  ROWID" +
				" FROM RECON_TMP_TAB A, RECON_TMP_TAB B " +
				" WHERE	A.RTT_ROWID = B.RTT_ROWID " +
				" AND 	A.ROWID != B.ROWID " +
				" AND     A.RTT_PID = :g_pid " +
				" AND		B.RTT_PID = :g_pid" ;
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		while(ReconProgram.rs.next()){
			
			ReconProgram.G_DUP_RCPTS = ReconProgram.rs.getString("ROWID");
		}*/
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : Build_Recon_Temp_Table : "+e.getMessage());
	}
	
	return 0;
}

private int Post_Deposits(String Rrno, String reconDate) {
	// TODO Auto-generated method stub
	
	String l_rrno;
	String l_reconDate;
	String l_loc;
	String temp;
	String Query = "";
	int updt = 0 ;
	
	try {
		
		l_reconDate = reconDate;
		l_loc = ReconProgram.G_LOCATION+"%";
		l_rrno = Rrno;
		System.out.println("Posting Deposits For : RRno  : " +Rrno + "  ,   Date : "+reconDate );
		
		if(Rrno != null){
			
			Query = " INSERT INTO  SHOP_DEPOSITS (" +
					" SDP_RCPT_NO, " +
					" SDP_RCPT_DT," +
					" SDP_CASH_COUNTR, " +
					" SDP_PYMNT_PURPOSE, " +
					" SDP_AMT_PAID, " +
					" SDP_RR_NO, " +
					" SDP_USER )  " +
					"" +
					"SELECT " +
					"RP_RCPT_NO, " +
					"RP_RCPT_DT, " +
					"RP_CASH_COUNTR_NO, " +
					"RP_PURPOSE, " +
					"RP_AMT_PAID," +
					"RP_PURPOSE_KEY," +
					"UPPER('"+ReconProgram.G_USER+"')  " +
					"" +
					"FROM RCPT_PYMNT  " +
					" " +
					" WHERE RP_RCPT_DT  = to_date('"+l_reconDate+"', 'dd/mm/yyyy') " +
					" AND   RP_PURPOSE_KEY = '"+l_rrno+"'   " +
					" AND   RP_POSTED_STS != 'Y'  " +
					" AND   RP_CANCEL_FLG != 'Y'  " +
					" AND   RP_PURPOSE IN('6', '15', '75') ";
			
			System.out.println("Posting Deposits query : "+Query );
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int ins = ReconProgram.ps.executeUpdate();
			
			if(ins > 0){
				System.out.println("Records Inserted to CUST_DEPOSITS : " + ins +"...!");
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
			Query = " UPDATE RCPT_PYMNT SET RP_POSTED_STS = 'Y'  " +
					" WHERE RP_RCPT_DT = to_date('"+l_reconDate+"', 'dd/mm/yyyy') " +
					" AND   RP_PURPOSE_KEY = '"+l_rrno+"' " +
					" AND   RP_POSTED_STS != 'Y' " +
					" AND   RP_CANCEL_FLG != 'Y' " +
					" AND   RP_PURPOSE IN('6', '15', '75') ";
			
			System.out.println("Posting RCPT_PYMNT query : "+Query );
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			updt = ReconProgram.ps.executeUpdate();
			
			if(updt > 0){
				System.out.println("Records Updated RCPT_PYMNT : "+ updt+"...!");
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
		}else{
			
			Query = " INSERT INTO  SHOP_DEPOSITS (" +
					" SDP_RCPT_NO, " +
					" SDP_RCPT_DT," +
					" SDP_CASH_COUNTR, " +
					" SDP_PYMNT_PURPOSE, " +
					" SDP_AMT_PAID, " +
					" SDP_RR_NO, " +
					" SDP_USER )  " +
					"" +
					"SELECT " +
					"RP_RCPT_NO, " +
					"RP_RCPT_DT, " +
					"RP_CASH_COUNTR_NO, " +
					"RP_PURPOSE, " +
					"RP_AMT_PAID," +
					"RP_PURPOSE_KEY," +
					"UPPER('"+ReconProgram.G_USER+"')  " +
					"" +
					"FROM RCPT_PYMNT  " +
					" " +
					" WHERE RP_RCPT_DT  = to_date('"+l_reconDate+"', 'dd/mm/yyyy') " +
					" AND   RP_PURPOSE_KEY like '"+l_loc+"%'   " +
					" AND   RP_POSTED_STS != 'Y'  " +
					" AND   RP_CANCEL_FLG != 'Y'  " +
					" AND   RP_PURPOSE IN('6', '15', '75') ";
			
			System.out.println("Posting CUST_DEPOSITS query : "+Query );
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			updt = ReconProgram.ps.executeUpdate();
			
			if(updt > 0){
				System.out.println("Records Inserted to CUST_DEPOSITS : " + updt +"...!");
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
			Query = " UPDATE RCPT_PYMNT SET RP_POSTED_STS = 'Y'  " +
					" WHERE RP_RCPT_DT = to_date('"+l_reconDate+"', 'dd/mm/yyyy') " +
					" AND   RP_PURPOSE_KEY = '"+l_loc+"%' " +
					" AND   RP_POSTED_STS != 'Y' " +
					" AND   RP_CANCEL_FLG != 'Y' " +
					" AND   RP_PURPOSE IN('6', '15', '75') ";
			
			System.out.println("updating RCPT_PYMNT query : "+Query );
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			updt = ReconProgram.ps.executeUpdate();
			
			if(updt > 0){
				System.out.println("Records Updated RCPT_PYMNT : "+ updt+"...!");
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
			
		}
		
		ReconProgram.G_NON_REVENUE_POSTED += updt ;
		
		System.out.println(" Posted Deposits for Date : "+reconDate + " , RrNo : "+Rrno + " , Records : "+updt);
		
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured , Post_deposits : "+e.getMessage());
	}
	
	return 0;
}

private int Validate_Date_Fromat(String reconDate) {
	// TODO Auto-generated method stub
	String tmp1 = null;
	String tmp2 = null;
	
	tmp1 = reconDate ; 
	
	ReconProgram.G_RECON_DATE = tmp1;
	
	try {
		
		String Query = " SELECT TO_CHAR(TO_DATE('"+tmp1+"', 'dd/mm/yyyy'), 'dd/mm/yyyy')  dt  " +
				       "   FROM RECON_PROCESS ";
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		if(ReconProgram.rs.next()){
			tmp2 = ReconProgram.rs.getString("dt");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		if(!tmp1.equals(tmp2)){
			 return 1;
		}else{
			return 0;
		}
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
	
	return 0;
}

private int InspectReconProcess() {
	// TODO Auto-generated method stub
	
	System.out.println("Inside InspectReconProcess ... !");
	
	String reconFlag = null;
	int    l_count   = 0;

	try {
		if(ReconProgram.USAGE_MODE == 0){
			return 0;
		}
		
		String Query = " SELECT COUNT(*) CNT FROM RECON_PROCESS ";
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		if(ReconProgram.rs.next()){
			l_count = ReconProgram.rs.getInt("CNT");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
		
		if(l_count == 0){
			System.out.println("Recon Process Table Is Not Upto Date.Cannot Continue .!");
			System.out.println("Contact Support For Correcting The Error...!");
		
			return 1;
		}
		
		Query = " SELECT RP_RECON_STS  FROM RECON_PROCESS ";
		
		ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
		ReconProgram.rs = ReconProgram.ps.executeQuery();
		
		if(ReconProgram.rs.next()){
			reconFlag = ReconProgram.rs.getString("RP_RECON_STS");
		}
		
		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);

		if(reconFlag.equals("Y")){
			return 1;
		}else{
			
			Query = " UPDATE RECON_PROCESS SET" +
					" RP_DATE = TO_DATE(SYSDATE,'DD/MM/YYYY') ," +
					" RP_RECON_STS = 'Y' ";
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			int loc = ReconProgram.ps.executeUpdate();
			
			if(loc > 0){
				System.out.println("Recon Process Table Locked...!");
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
			return 0;
		}
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
}

private void LoadRRnumberList() {
	// TODO Auto-generated method stub
	
	System.out.println("Inside Load RR number list function...!");
	
	String rtt_rr_no;
	
	String prv_rrNo = null;
	String cur_rrNo;
	String buf;
	
	String Query = null;
	
	try {
		
		if(ReconProgram.USAGE_MODE == 0){
			
			Query = " SELECT DISTINCT A.RTT_RR_NO   " +
					" FROM RECON_TMP_TAB A, SHOP_BILL B " +
					" WHERE A.RTT_PID = '"+ReconProgram.G_PROCESS_ID+"' AND A.RTT_RR_NO = B.SB_RR_NO " +
					" AND   (B.SB_RR_STS = 'N' OR B.SB_RR_STS = 'I') " +
					" ORDER by 1 ";
			
			System.out.println("RECON_TMP_TAB : "+Query);
			
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			ReconProgram.rs = ReconProgram.ps.executeQuery();
			
			while(ReconProgram.rs.next()){
				
				rtt_rr_no = ReconProgram.rs.getString("RTT_RR_NO");
				
				cur_rrNo = rtt_rr_no;
				
				if(!cur_rrNo.equals(prv_rrNo)){
					ReconProgram.RRNumberList.add(cur_rrNo);
					
					prv_rrNo = cur_rrNo ;
				}
				
			}
			
			DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
		}else{
			
			ReconProgram.RRNumberList.add(ReconProgram.G_RR_NUMBER);
			
			System.out.println("G-RRNumber : "+ReconProgram.G_RR_NUMBER);
			
		}
		
	} catch (SQLException e) {
		// TODO: handle exception
		System.out.println("Error Occured : "+e.getMessage());
	}
	
	return ;
}

public void Delete_Process_ID() {
	// TODO Auto-generated method stub
	
}

public void AddReceiptUpdateTable(String tableName, String functionName, int fun_no) {
	// TODO Auto-generated method stub
	
	ReceiptUpdateTable rcptUpdtBO = new ReceiptUpdateTable();
	
	rcptUpdtBO.setTable_Name(tableName);
	rcptUpdtBO.setFunction_Name(functionName);
	rcptUpdtBO.setFun_Count(fun_no);
	
	ReconProgram.ReceiptUpdateTablesList.add(rcptUpdtBO);
	
}

public int Get_Process_ID() {
	// TODO Auto-generated method stub
	
		String Query = " SELECT RECON_SEQUENCE.NEXTVAL process_id  FROM DUAL ";
		int process_id = 0;

		try {
			ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
			ReconProgram.rs = ReconProgram.ps.executeQuery();

			if (ReconProgram.rs.next()) {

				process_id = ReconProgram.rs.getInt("process_id");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);

		return process_id;
}

public void getNewRRnoList() {
	// TODO Auto-generated method stub
	
		System.out.println("Inside getNewRRnoList...!");
		
		String Query = null;
		
		try {
				Query = " SELECT RP_PURPOSE_KEY FROM RCPT_PYMNT  WHERE RP_RCPT_DT = TO_DATE('"+ReconProgram.RECON_DATE+"','DD/MM/YYYY')   and RP_POSTED_STS = 'N'  ";
				
				ReconProgram.ps = ReconProgram.con.prepareStatement(Query);
				ReconProgram.rs = ReconProgram.ps.executeQuery();
				
				while(ReconProgram.rs.next()){
						ReconProgram.NEW_RRNumberList.add(ReconProgram.rs.getString("RP_PURPOSE_KEY"));
				}
				DatabaseOperations.cleanUp(ReconProgram.ps, ReconProgram.rs);
			
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error Occured : "+e.getMessage());
		}
}






}
