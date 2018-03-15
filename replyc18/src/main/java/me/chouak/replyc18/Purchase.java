package me.chouak.replyc18;

import java.util.Map;

public class Purchase 
{
	public Project client;
	public Map<Region, Integer> servers;
	
	public Purchase()
	{
		// UNIMPLEMENTED
	}
	
	public Purchase(Project client, Map<Region, Integer> servers)
	{
		this.client = client;
		this.servers = servers;
	}
}
