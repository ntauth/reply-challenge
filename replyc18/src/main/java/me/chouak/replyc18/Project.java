package me.chouak.replyc18;

import java.util.Map;

public class Project
{
	public String country;
	public int    slaPenalty;
	public Map<String, Integer> resAmt;
	
	public Project() {
		// UNIMPLEMENTED
	}
	
	public Project(String c, int sp, Map<String, Integer> ra) {
		country = c;
		slaPenalty = sp;
		resAmt = ra;
	}
}
