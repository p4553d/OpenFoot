package evaluate;

import game.ITeam;
import java.util.Vector;

/**
 * Collect all relevant data from a game set.
 * Calculate and update skill of teams
 * 
 * @author mutant
 */
public interface ISkillCalculator {
	
	/**
	 * @return count of teams involved in processed game set
	 */
	public abstract int getTeamCount();

	/**
	 * Return ITeam object associated with give name
	 * @param name
	 * @return associated team
	 */
	public abstract ITeam getTeam(String name);

	/**
	 * Raw set of teams
	 * @return
	 */
	public abstract Vector<ITeam> getTeamSet();

	/**
	 * @return count of processed games
	 */
	public abstract int getGameCount();

}