package evaluate;

import game.GameResult;
import game.ITeam;

/**
 * Predictor interface
 * Calculate quotes and forecast game results
 * 
 * @author mutant
 */
public interface IPredictor {
	
	/**
	 * Predict the result of game
	 * As appointed is first team home team
	 * @param gr
	 * @return probability for win of first team / goodness of prediction
	 */
	public abstract double predict(GameResult gr);

	/**
	 * Calculate the possibility for team t1 wins against team t2
	 * @param Iteam t1
	 * @param Iteam t2
	 * @return double
	 */
	double getWinProb(ITeam t1, ITeam t2);

}
