package core;

import game.GameResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import networking.INetCollector;
import networking.PredictNetCollector;

/**
 * Read data from URL and write it to configs
 * 
 * @author mutant
 * 
 */
public class ParseData {

	// Ugly, but I didn't run it with params :)
	static String filename = "src/data/fullpred";
	static int yearFrom = 2011;
	static int yearTo = 2011;
	static int dayFrom = 18;
	static int dayTo = 34;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String source = "http://fussballdaten.de/bundesliga/";

		INetCollector snc = new PredictNetCollector();
		FileWriter fw = null;
		BufferedWriter out = null;
		try {
			fw = new FileWriter(filename);
			out = new BufferedWriter(fw);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (out != null) {
			try {
				for (int year = yearFrom; year <= yearTo; year++) {
					for (int day = dayFrom; day <= dayTo; day++) {

						System.out.println("[" + year + " " + day + "]");
						out.write("[" + year + " " + day + "]\n");

						snc.readURL(source + year + "/" + day + "/");

						GameResult gr;
						while ((gr = snc.getResult()) != null) {
							out.write("\t");
							gr.setYear(year);
							gr.setDay(day);
							out.write(gr.toString());
							out.write("\n");
						}

						out.write("\n");

					}
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			if (out != null) {
				out.close();
			}
			if (fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
