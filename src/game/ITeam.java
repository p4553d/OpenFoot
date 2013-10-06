package game;

/**
 * Representation for a Team
 * @author mutant
 */
public interface ITeam extends Comparable<ITeam> {

	/**
	 * Adjust skill points of teams
	 * 
	 * @param opponent
	 * @param ownScore
	 * @param oppScore
	 */
	public abstract void updateSkill(ITeam opponent, int ownScore, int oppScore);

	/**
	 * Return name of team
	 * 
	 * @return String name
	 */
	public abstract String getName();

	/**
	 * Return count of won games
	 * 
	 * @return
	 */
	public abstract int getWin();

	/**
	 * Return count of lost games
	 * 
	 * @return
	 */
	public abstract int getLoss();

	/**
	 * Return count of draw games
	 * 
	 * @return
	 */
	public abstract int getDraw();

	/**
	 * Return total count of games
	 * 
	 * @return
	 */
	public abstract int getGameCount();

	/**
	 * Return current skill of team
	 * 
	 * @return
	 */
	public abstract double getSkill();

	public abstract double getWinProb(ITeam t2);

	public abstract int getGoalsLos();

	public abstract int getGoalsWon();

}