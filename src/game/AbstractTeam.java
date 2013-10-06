package game;

/**
 * Some basic functions and statistics to a team
 * @author mutant
 */
public abstract class AbstractTeam implements ITeam {

	protected String name;

	public abstract double getWinProb(ITeam opp);

	// Collect some basic statistic about team
	protected int winCount;
	protected int lossCount;
	protected int drawCount;
	protected int goalsWon;
	protected int goalsLos;

	protected double skill;

	public AbstractTeam() {
		super();
	}
	
	public AbstractTeam(String name) {
		this.name = name;
		winCount = 0;
		drawCount = 0;
		lossCount = 0;
		
		goalsWon = 0;
		goalsLos = 0;

		skill = 100;
	}

	@Override
	public int getGoalsWon() {
		return goalsWon;
	}

	@Override
	public int getGoalsLos() {
		return goalsLos;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getWin() {
		return winCount;
	}

	@Override
	public int getLoss() {
		return lossCount;
	}

	@Override
	public int getDraw() {
		return drawCount;
	}

	@Override
	public int getGameCount() {
		return winCount + drawCount + lossCount;
	}

	@Override
	public double getSkill() {
		return skill;
	}

	@Override
	// Make teams comparable according to their skill points
	public int compareTo(ITeam t) {
		return (int) Math.signum(skill - t.getSkill());
	}

	public void updateSkill(ITeam opponent, int ownScore, int oppScore) {
		if (opponent instanceof AbstractTeam) {
			goalsWon = goalsWon + ownScore;
			goalsLos = goalsLos + oppScore;
			updateSkill((AbstractTeam)opponent, ownScore, oppScore);
		}
	}

	public abstract void updateSkill(AbstractTeam opponent, int ownScore,
			int oppScore);
}