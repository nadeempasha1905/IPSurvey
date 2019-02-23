/**
 * 
 */
package com.recon.pojo;

import com.recon.controller.ReconProgram;

/**
 * @author Nadeem
 *
 */
public class ReceiptUpdateTable {
	
	String table_Name;
	String function_Name;
	int    fun_Count;
	
/*	public ReceiptUpdateTable(String table_Name,String function_Name,int fun_Count) {
		// TODO Auto-generated constructor stub
		this.table_Name = table_Name;
		this.function_Name = function_Name;
		this.fun_Count = fun_Count;
		
		ReconProgram.ReceiptUpdateTablesList.add(this);
	}*/

	public String getTable_Name() {
		return table_Name;
	}

	public void setTable_Name(String table_Name) {
		this.table_Name = table_Name;
	}

	public String getFunction_Name() {
		return function_Name;
	}

	public void setFunction_Name(String function_Name) {
		this.function_Name = function_Name;
	}

	public int getFun_Count() {
		return fun_Count;
	}

	public void setFun_Count(int fun_Count) {
		this.fun_Count = fun_Count;
	}
	
	
	
}
