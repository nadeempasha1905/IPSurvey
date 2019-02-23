/**
 * 
 */
package com.recon.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.util.StringTokenizer;

import com.recon.controller.ReconProgram;

/**
 * @author Nadeem
 *
 */
public class ExecuteShellCommand {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ExecuteShellComand obj = new ExecuteShellComand();

		String domainName = "google.com";
		
		//in mac oxs
		//String command = "ping -c 3 " + domainName;
		
		//in windows
		String command = "";
		String output = "";
		
		// command = "ping -n 3 " + domainName;
		
		//output = executeCommand(command);

		//System.out.println(output);
		
		command = " tasklist //v //fo csv | findstr //i 'Report' " ;
		
		command = " tasklist " ;
		
		output = executeCommand(command);

		System.out.println(output);
		
		

	}
*/
	
	 public static void main(String[] args) throws Exception {
	        /*ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "cd \"C:\\Program Files\\Microsoft SQL Server\" && dir");*/
		 
		 String name = ExecuteShellCommand.getClassName(ExecuteShellCommand.class);
		 
		 /*ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", " tasklist /v /fo csv | findstr /i  \""+name+"\" " );*/  //temporaya commented
		 
		 ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", " tasklist /v /fi \"IMAGENAME eq JobScheduler.jar\" " );
		 
		 
//		 tasklist /v /fi "IMAGENAME eq eclipse.exe"
		 
		 //"Image Name","PID","Session Name","Session#","Mem Usage","Status","User Name","CPU Time","Window Title"
	       
		 builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        String s1 = null;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	            s1 = s1 + line ;
	        }
	        
	        System.out.println("PID : "+GetProcessId(s1));
	        
	        System.out.println(ExecuteShellCommand.getClassName(ExecuteShellCommand.class));
	        
	        
	    }
	 
	 public static int GetCurrentProcessId(Class<ReconProgram> class1) {
		 
		 String name = ExecuteShellCommand.getClassName(class1);
		 int PID = 0;
		 
		 try{
		 ProcessBuilder builder = new ProcessBuilder(
		            "cmd.exe", "/c", " tasklist /v /fo csv | findstr /i  \""+name+"\" " );
		 
		 builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        String s1 = null;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	            s1 = s1 + line ;
	        }
	        
	        System.out.println("PID : "+(PID = GetProcessId(s1)));
	        
	        System.out.println(ExecuteShellCommand.getClassName(ExecuteShellCommand.class));
	        
	    	p = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return PID;
		 
	 }
	
	 public static String getClassName(Class c) {
		    String FQClassName = c.getName();
		    int firstChar;
		    firstChar = FQClassName.lastIndexOf ('.') + 1;
		    if ( firstChar > 0 ) {
		      FQClassName = FQClassName.substring ( firstChar );
		      }
		    return FQClassName;
		    }
	 
	 
	
	private static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
	
	private static int GetProcessId(String value){
		
		String str = "";
		String[] s = new String[10];
		
		if(value != null && value.length() > 0){
			
			str = value.replace('"', ' ');
			str = str.replace(" ","");
			
			s = str.split(",");
			
			System.out.println("IMAGENAME : "+s[0]);
			System.out.println("PID : "+s[1]);
			
		}
		
		System.out.println("PID : "+s[1]);
		
		return 0;
		
	}

	/*public static int GetCurrentProcessId(Class<ReconProgram> class1) {
		// TODO Auto-generated method stub
		return 0;
	}*/
}
