package me.chouak.replyc18;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Utils 
{
	public static float TP(String[] services, Purchase p, ArrayList<Purchase> purchases)
	{
		float f = 0.f;
		f = (APL(p)/OAI(services, purchases))*TOPC(p);
		return 0.f;
	}
	
	public static float totalOperationalProjectCost(Purchase p)
	{
		float sum = 0.f;
		for (Entry<Region,Integer> entry : p.servers.entrySet())
			sum += entry.getKey().cost * entry.getValue();
		return sum;
	}
	
	public static float TOPC(Purchase p)
	{
		return totalOperationalProjectCost(p);
	}
	
	public static float OPC(Purchase p)
	{
		return totalOperationalProjectCost(p);
	}
	
	public static float overallAvailabilityIndex(String[] services, ArrayList<Purchase> purchases)
	{
		float sum = 0.f;
		for (String s : services)
			sum+=serviceAvailabilityIndex(s, purchases);
		return sum/(services.length);
	}
	
	public static float OAI(String[] services, ArrayList<Purchase> purchases)
	{
		return overallAvailabilityIndex(services, purchases);
	}
	
	private static float serviceAvailabilityIndex(String service, ArrayList<Purchase> purchases)
	{
		float sum1 = 0.f;
		float sum2 = 0.f;
		for (Purchase p : purchases)
			for (Entry<Region, Integer> entry : p.servers.entrySet())
				sum1 += entry.getKey().min.get(service) * p.servers.get(entry.getKey());
		sum1 = sum1*sum1;
		float tmp;
		for (Purchase p : purchases)
			for (Entry<Region, Integer> entry : p.servers.entrySet())
			{
				tmp = (entry.getKey().min.get(service) * p.servers.get(entry.getKey()));
				sum1 += tmp*tmp;
			}
		if (sum2==0)
			return 0.f;
		return sum1/sum2;
	}
	
	public static float averageProjectLatency(Purchase purchase)
	{
		float sum1 = 0.f;
		float sum2 = 0.0f;
		for (Entry<Region,Integer> entry : purchase.servers.entrySet())
			sum1 += uR(entry.getKey(), purchase)*entry.getKey().latency.get(purchase.client.country);
		for (Entry<Region,Integer> entry : purchase.servers.entrySet())
			sum2 += uR(entry.getKey(), purchase);
		if (sum2==0)
			return 0.f;
		return sum1/sum2;
	}
	
	public static float APL(Purchase p)
	{
		return averageProjectLatency(p);
	}
	
	private static float uR(Region r, Purchase p)
	{
		float ret = 0.f;
		for (Entry<Region,Integer> purchase : p.servers.entrySet())
			for (Entry<String, Integer> entry : purchase.getKey().min.entrySet())
				ret = entry.getValue() * purchase.getValue();
		return ret;
	}
	
}
