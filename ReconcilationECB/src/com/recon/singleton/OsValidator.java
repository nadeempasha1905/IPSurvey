/**
 * 
 */
package com.recon.singleton;

/**
 * @author Admin
 *
 */
public class OsValidator {
	
private static String OS = System.getProperty("os.name").toLowerCase();


public static int IdentifyOperatingSystem(){

	System.out.println(OS);
	
	if (isWindows()) {
		System.out.println("This is Windows");
		 
		return 1;
	} else if (isMac()) {
		System.out.println("This is Mac");
		
		return 2;
	} else if (isUnix()) {
		System.out.println("This is Unix or Linux");
		
		return 3;
	} else if (isSolaris()) {
		System.out.println("This is Solaris");
		
		return 4;
	} else {
		System.out.println("Your OS is not support!!");
		
		return 0;
	}
}
	
	public static void main(String[] args) {
		
System.out.println(OS);
		
		if (isWindows()) {
			System.out.println("This is Windows");
		} else if (isMac()) {
			System.out.println("This is Mac");
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
		} else if (isSolaris()) {
			System.out.println("This is Solaris");
		} else {
			System.out.println("Your OS is not support!!");
		}
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}
