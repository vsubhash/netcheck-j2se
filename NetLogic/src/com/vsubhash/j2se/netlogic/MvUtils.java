package com.vsubhash.j2se.netlogic;

public class MvUtils {
	
	public boolean mbIS_DEBUG = false;
	String msProgram;	
	
	public MvUtils(String asApplicationName) {
		msProgram = asApplicationName;	  
	}
	
	public void debug(String asMessage) {
		if (this.mbIS_DEBUG) {
			System.out.println(this.msProgram + " : " + asMessage);
		}
	}
	
	public void debugr(String asMessage) {
		if (this.mbIS_DEBUG) {
			System.err.println(this.msProgram + " : " + asMessage);
		}
	}
	

}
