package configuration;

import game.GameResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Read and parse configuration for prediction data, without game results.
 * 
 * @author mutant
 * 
 */
public class ConfResultData {

	private Vector<GameResult> results;

	public ConfResultData(String file) throws IOException {

		this.results = new Vector<GameResult>();

		BufferedReader bis = new BufferedReader(new FileReader(file));
		Pattern pTime = Pattern.compile("\\[\\s*(\\d{4})\\s+(\\d{1,2})\\s*\\]");
		Pattern pResult = Pattern
				.compile("\\s*(.*?)\\s*-\\s*(.*?)\\s*\\((\\d)\\s*:\\s*(\\d)\\)");

		String cLine;
		int currentYear = 0;
		int currentDay = 0;

		while ((cLine = bis.readLine()) != null) {
			Matcher mTime = pTime.matcher(cLine);
			Matcher mResult = pResult.matcher(cLine);

			if (mResult.find()) {
				String opp1 = mResult.group(1);
				String opp2 = mResult.group(2);

				int res1 = Integer.parseInt(mResult.group(3));
				int res2 = Integer.parseInt(mResult.group(4));

				GameResult gr = new GameResult(opp1, opp2, res1, res2,
						currentYear, currentDay);
				results.add(gr);
			} else {
				if (mTime.find()) {
					currentYear = Integer.parseInt(mTime.group(1));
					currentDay = Integer.parseInt(mTime.group(2));
				}
			}
		}
	}

	public int getSize() {
		return results.size();
	}

	public GameResult getResultAt(int i) {
		return results.get(i);
	}
}
