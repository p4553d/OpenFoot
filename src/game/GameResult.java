package game;

/**
 * Game description. Used for calculating with known and for forecasting with
 * predicted results.
 * Home team is always first!
 * @author mutant
 * 
 */
public class GameResult {

	private String opponent1;
	private String opponent2;
	private int score1;
	private int score2;
	private int year;
	private int day;

	public GameResult(String opp1, String opp2, int score1, int score2,
			int year, int day) {
		this.opponent1 = opp1;
		this.opponent2 = opp2;

		this.score1 = score1;
		this.score2 = score2;

		this.year = year;
		this.day = day;
	}

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(opponent1);
		sbf.append(" - ");
		sbf.append(opponent2);
		sbf.append(" (");
		sbf.append(score1);
		sbf.append(":");
		sbf.append(score2);
		sbf.append(")");

		return sbf.toString();
	}

	public String getOpponent1() {
		return this.opponent1;
	}

	public String getOpponent2() {
		return this.opponent2;
	}

	public int getScore1() {
		return this.score1;
	}

	public int getScore2() {
		return this.score2;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int y) {
		this.year = y;
	}

	public int getDay() {
		return this.day;
	}

	public void setDay(int d) {
		this.day = d;
	}

	/**
	 * Calculate prediction points.<br/>
	 * <p>
	 * Exact game result = 3 pts<br/>
	 * Right score difference = 2pts<br/>
	 * Right winner/draw = 1pt
	 * </p>
	 * @param s1 real score for home team
	 * @param s2 real score for guests
	 * @return
	 */
	public int getPredPoints(int s1, int s2) {
		int res = 0;

		if ((score1 == s1) && (score2 == s2)) {
			res++;
		}

		if ((score1 - score2) == (s1 - s2)) {
			res++;
		}

		if (Math.signum(score1 - score2) == Math.signum(s1 - s2)) {
			res++;
		}

		return res;
	}
}
