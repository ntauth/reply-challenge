package me.chouak.replyc18;

import java.util.Map;

public class Region
{
    String provider;                  // Provider
    String name;                      // Nome regione
    Map<String, Integer> min;         // Numero minimo unità per servizio es: min["cpu"]=8
    Map<String, Integer> latency;     // Latenza per destinazione es: latency["rome"]=42
    float cost;                       // Costo per pacchetto
    int   packages;                   // Unità disponibili
    
    public Region() {
    	// UNIMPLEMENTED
    }
    
    public Region(String p, String n, Map<String, Integer> m, Map<String, Integer> l, float c, int pk)
    {
    	provider = p;
    	name = n;
    	min = m;
    	latency = l;
    	cost = c;
    	packages = pk;
    }
}