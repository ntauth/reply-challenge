/**
 * @author Team.setName("Ψduck");
 * @file   App.java
 * @todo   Add JUnit test cases, a logging facility and proper input sanitation
 * @brief  Program Driver
 * 
 */
package me.chouak.replyc18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * 
 * @author Ayoub Chouak (ntauth)
 * @brief  Program Driver
 */
public class App
{
    public static void main(String[] args)
    {
        Map<GreedyFilter, Boolean> filterOrder = new HashMap<GreedyFilter, Boolean>(); // True if ascending
        EnumSet<GreedyFilter> filter = EnumSet.of(GreedyFilter.NONE);
        
        // Filters must be (filter_type, order)
        assert args.length % 2 == 0;
        
        // Read filters
        for (int i = 0; i < args.length; i += 2)
        {
        	if (args[i].equalsIgnoreCase("byPenalty")) {
        		filter.add(GreedyFilter.BY_PENALTY);
        		
            	if (args[i + 1].equalsIgnoreCase("asc"))
            		filterOrder.put(GreedyFilter.BY_PENALTY, true);
            	else
            		filterOrder.put(GreedyFilter.BY_PENALTY, false);
        	}
        	else if (args[i].equalsIgnoreCase("byResourceAvg")) {
        		filter.add(GreedyFilter.BY_RESOURCE_AVG);
        		
            	if (args[i + 1].equalsIgnoreCase("asc"))
            		filterOrder.put(GreedyFilter.BY_RESOURCE_AVG, true);
            	else
            		filterOrder.put(GreedyFilter.BY_RESOURCE_AVG, false);
        	}
        	else
        		throw new IllegalArgumentException("Invalid filter");
        }
        
        // Parse File
        ParseFile.parseFile("first_adventure.in");
        
        // Declare the structures and fill them
        List<Project>  proj = ParseFile.progetti;
        List<Region>   rgns = ParseFile.ret;
        List<Purchase> outp = new ArrayList<Purchase>();
        
        // Sort the projects by filter
        final EnumSet<GreedyFilter> filter_ = filter;
        final Map<GreedyFilter, Boolean> filterOrder_ = filterOrder;
        
        // Print unsorted array
        for (Project prj : proj)
        	out.print(String.format("Penalty: %d, Country: %s%s", prj.slaPenalty, prj.country, System.lineSeparator()));
        
        proj.sort((lhs, rhs) -> {
        	
        	int bias = 0;
        	
        	if (filter_.contains(GreedyFilter.BY_PENALTY)) {
        		bias = (filterOrder_.get(GreedyFilter.BY_PENALTY) == true ? -1 : 1)  * (lhs.slaPenalty - rhs.slaPenalty);
        	}
        	if (filter_.contains(GreedyFilter.BY_RESOURCE_AVG))
        	{
        		// Calculate average for each
        		float lhsAvg = lhs.resAmt.entrySet().stream().map(x -> x.getValue()).reduce(0, Integer::sum) / (float) lhs.resAmt.size();
        		float rhsAvg = rhs.resAmt.entrySet().stream().map(x -> x.getValue()).reduce(0, Integer::sum) / (float) rhs.resAmt.size();
        		
        		// @todo: bias should be weighted depending upon the filter
        		bias += (filterOrder_.get(GreedyFilter.BY_PENALTY) == true ? -1 : 1)  * (lhsAvg - rhsAvg);
        	}
        	
        	return bias;
        });
        
        // Print sorted out array
        for (Project prj : proj)
        	out.print(String.format("Penalty: %d, Country: %s%s", prj.slaPenalty, prj.country, System.lineSeparator()));
        
        for (int prjIdx = 0; prjIdx < proj.size(); prjIdx++)
        {
        	// Sort the regions array by latency
        	Project prj = proj.get(prjIdx);
        	
        	// Sort by min latency from provider to project's country
        	rgns.sort((lhs, rhs) -> {
        		
        		int latency;	
        		
        		latency = rhs.latency.get(prj.country) - lhs.latency.get(prj.country);
        		return latency;
        	});
        	
        	// Print sorted regions
//            for (Region rgn : rgns)
//            	out.print(String.format("%s", System.lineSeparator()));
        	
        	boolean didAlloc = false;
        	int rgnIdx = 0;
        	
    		Purchase prch = new Purchase(prj, new HashMap<Region, Integer>());
    		
        	while (!didAlloc && rgnIdx < rgns.size())
        	{
        		// Pop the rgnIdx'th best region
        		Region rgn = rgns.get(rgnIdx);
        		Map<String, Integer> rsrcK = new HashMap<String, Integer>();
        		
        		for (Map.Entry<String, Integer> rsrc : prj.resAmt.entrySet())
        		{
        			rsrcK.put(rsrc.getKey(), (int) Math.ceil(rsrc.getValue() / (float) rgn.min.get(rsrc.getKey())));
        		}
        		
        		// Get max K
        		int maxK = rsrcK.entrySet().stream().map(x -> x.getValue()).max(Integer::max).orElse(-1);
        		
        		// Something went wrong. Dataset is ill-formed?
        		assert maxK != -1;
        		
        		// Is allocation request satisfiable?
        		if (maxK <= rgn.packages)
        		{
        			didAlloc = true;
        			rgn.packages -= maxK;
        			
        			prch.servers.put(rgn, maxK);
        			
        			// Remove the region-provider association if it offers no more packages
        			if (rgn.packages == 0)
        				rgns.remove(rgnIdx);
        		}
        		// else, try by mixing
        		else
        		{
        			rgn.packages = 0;
        			
        			// Remove the region-provider association since it offers no more packages
        			rgns.remove(rgnIdx);
        			
        			// Update the project resource amounts
            		for (Map.Entry<String, Integer> rsrc : prj.resAmt.entrySet())
            		{
            			rsrcK.put(rsrc.getKey(), rsrc.getValue() - maxK * rgn.min.get(rsrc.getKey()));
            		}
        		}
        		
        		// If alloc'd, add to the purchase list
        		if (didAlloc)
        		{
        			
        		}
        		
        		// else, try with next provider
        		rgnIdx++;
        	}
        }
        
        // Execution converged, now save the results
        // @todo: File output
        // IMPLEMENT ME!
    }
}
