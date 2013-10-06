package networking;

import game.GameResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser with settings for the site fussballdaten.de
 * @author mutant
 *
 */
public class SimpleNetCollector implements INetCollector {

	private String siteContent;
	private int offset;

	public SimpleNetCollector() {
		this.offset = 0;
		this.siteContent = "";
	}

	@Override
	public void readURL(String url) throws IOException {
		StringBuffer sbf = new StringBuffer();
		URL u = new URL(url);
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection hc = (HttpURLConnection) u.openConnection();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				hc.getInputStream(), "UTF8"));
		String uLine;

		while ((uLine = br.readLine()) != null && sbf.length() < 35000) {
			sbf.append(uLine);
		}

		br.close();
		
		this.siteContent = sbf.toString();
		this.offset = 0;
	}

	@Override
	public GameResult getResult() {
		GameResult gr = null;
		if (offset < siteContent.length()) {
			Pattern pRes = Pattern
					.compile(							
							"<td class=\"Heim( b)?\"><span.*?<a href=.*?>(.*?)</a></td><td class=\"vs\">-</td><td class=\"Gast.*?<a href=.*?>(.*?)</a></td><td class=\"Ergebnis\"><a href=\".*?>(\\d):(\\d)",
							Pattern.DOTALL);
			Matcher mRes = pRes.matcher(siteContent);

			if (mRes.find(offset)) {
				String opp1 = mRes.group(2);
				String opp2 = mRes.group(3);

				int res1 = Integer.parseInt(mRes.group(4));
				int res2 = Integer.parseInt(mRes.group(5));

				gr = new GameResult(opp1, opp2, res1, res2, 0, 0);

				offset = mRes.end(4);
			}
		}

		return gr;
	}

	@Override
	public void rewind() {
		this.offset = 0;
	}

}
