package me.chouak.replyc18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ParseFile
{
	public static String[] resources;
	public static ArrayList<Region> ret;
	public static ArrayList<Project> progetti;

	public static void parseFile(String path) {

		ret = new ArrayList<Region>();

		File f = new File(path);
		try
		{
			int numProvider = 0;
			int numServices = 0;
			int numCountries = 0;
			int numProgetti = 0;

			ArrayList<String> services = new ArrayList<String>();
			ArrayList<String> countries = new ArrayList<String>();

			Scanner sc = new Scanner(f);

			numProvider = sc.nextInt();
			numServices = sc.nextInt();
			numCountries = sc.nextInt();
			numProgetti = sc.nextInt();

			// //////////////////////////////////////////////////////////////////

			sc.nextLine();
			String str = sc.nextLine();
			StringTokenizer st = new StringTokenizer(str, " ");

			while (st.hasMoreTokens())
				services.add(st.nextToken());

			resources = new String[services.size()];

			for (int i = 0; i < services.size(); i++)
				resources[i] = services.get(i);
			// ////////////////////////////////////////////////////////////////////
			String str1 = sc.nextLine();

			StringTokenizer st1 = new StringTokenizer(str1, " ");

			while (st1.hasMoreTokens())
				countries.add(st1.nextToken());

			// /////////////////////////////////////////////////////////////////////////////////////////////////
			HashMap<String, Integer> min = new HashMap<String, Integer>();
			HashMap<String, Integer> latenze = new HashMap<String, Integer>();

			String nomeRegione = null;
			int numServizi = 0;
			float costoPacchetto = 0;
			for (int i = 0; i < numProvider; i++) {

				String str2 = sc.nextLine();

				StringTokenizer st2 = new StringTokenizer(str2, " ");

				String provider = null;
				int numRegioniPerProvider = 0;

				while (st2.hasMoreTokens()) {
					provider = st2.nextToken();
					numRegioniPerProvider = Integer.parseInt(st2.nextToken());
				}

				for (int j = 0; j < numRegioniPerProvider; j++) {

					nomeRegione = sc.nextLine();
					numServizi = sc.nextInt();
					costoPacchetto = sc.nextFloat();

					ArrayList<Integer> s = new ArrayList<Integer>();

					for (int k = 0; k < numServices; k++) {
						s.add(sc.nextInt());
					}

					Region r = new Region();

					for (int l = 0; l < s.size(); l++) {

						r.min.put(services.get(l), s.get(l));
					}

					ArrayList<Integer> l = new ArrayList<Integer>();

					for (int k = 0; k < numCountries; k++) {
						l.add(sc.nextInt());
					}

					for (int k = 0; k < l.size(); k++) {

						r.latency.put(countries.get(k), s.get(k));
					}

					r.packages = numServizi;
					r.provider = provider;
					r.name = nomeRegione;
					r.cost = costoPacchetto;

					ret.add(r);

					sc.nextLine();
				}
			}

			progetti = new ArrayList<Project>();
			Project p = null;
			for (int i = 0; i < numProgetti; i++) {

				String s = sc.nextLine();

				StringTokenizer strt = new StringTokenizer(s, " ");

				p = new Project();

				while (strt.hasMoreTokens()) {

					p.slaPenalty = Integer.parseInt(strt.nextToken());

					p.country = strt.nextToken();

					for (String j : services)
						p.resAmt.put(j, Integer.parseInt(strt.nextToken()));

					progetti.add(p);
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// UNIMPLEMENTED
	}

}
