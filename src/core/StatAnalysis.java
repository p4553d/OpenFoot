/**
 * 
 */
package core;

import java.util.Arrays;
import java.util.Vector;

import evaluate.AbstractSkillCalculator;
import evaluate.GaussCalculation;
import game.GaussTeam;
import game.ITeam;

/**
 * Calculate some statistics from model.
 * 
 * @author mutant
 * 
 */
public class StatAnalysis {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// learn and calculate skill points
		AbstractSkillCalculator sc = new GaussCalculation("src/data/smallset");

		System.out.println("\n------------------------\n");

		// sort teams
		Vector<ITeam> teams = sc.getTeamSet();
		Object[] at = teams.toArray();
		Arrays.sort(at);

		// statistic and values for teams
		for (Object o : at) {
			GaussTeam t = (GaussTeam) o;
			int win = t.getWin();
			int draw = t.getDraw();
			int loss = t.getLoss();
			float total = t.getGameCount();

			System.out.printf("%s\n", t.getName());
			System.out
					.printf("\tWin: %d (%3.1f%%)\tDraw: %d (%3.1f%%)\tLoss: %d (%3.1f%%)\n",
							win, (100.0 * win / total), draw,
							(100.0 * draw / total), loss,
							(100.0 * loss / total));
			System.out.printf("\tGoals +%d (%3.1f)\t-%d (%3.1f)\n",
					t.getGoalsWon(), t.getGoalsWon() / total, t.getGoalsLos(),
					t.getGoalsLos() / total);
			System.out.printf("\tSkill:\t%.1f (%.1f)\n", t.getSkill(), t.getSigma());
			System.out.println();
		}

		// short overview
		System.out.println("\n-------------------\n");
		int count = 0;
		for (Object o : at) {
			ITeam t = (ITeam) o;
			System.out.printf("%3d %30s % 5.2f\n", (count + 1), t.getName(),
					t.getSkill());
			count++;
		}

		// probability table
		System.out.println("\n-------------------\n");
		System.out.printf("%30s", "");
		for (int i = 0; i < count; i++) {
			System.out.printf("%7d", i + 1);
		}

		System.out.println();
		int i = 0;
		for (Object o : at) {
			ITeam t = (ITeam) o;
			System.out.printf("%2d %27s", (i + 1), t.getName());
			for (Object op : at) {
				ITeam tp = (ITeam) op;
				double p = ((GaussCalculation)sc).getWinProb(t, tp);
				System.out.printf("  %1.3f", p);
			}
			System.out.println();
			i++;
		}
	}
}
