package me.chouak.replyc18;

public class Package implements Comparable<Package>
{
	public String serviceType;
	public int unitCount;
	
	public Package() {}

	public int compareTo(Package o) {
		return unitCount - o.unitCount;
	}
}
