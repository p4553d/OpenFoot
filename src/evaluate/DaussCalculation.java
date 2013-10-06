package evaluate;

import game.GameResult;
import game.GaussTeam;
import game.ITeam;

import java.io.IOException;
import java.util.HashMap;

import configuration.ConfResultData;

public class DaussCalculation extends AbstractSkillCalculator {

	public static double gs = 0.125;
	public static double pgs = 0.375;
	public static double flatten = 3.0;

	private int goalCount;

	public DaussCalculation(String fileName) {
		teamsMap = new HashMap<String, ITeam>();
		goalCount = 0;
		ConfResultData set;

		try {
			set = new ConfResultData(fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		gameCount = set.getSize();

		for (int i = 0; i < gameCount; i++) {
			GameResult gr = set.getResultAt(i);
			ITeam t1, t2;

			if (!teamsMap.containsKey(gr.getOpponent1())) {
				t1 = new GaussTeam(gr.getOpponent1());
				teamsMap.put(gr.getOpponent1(), t1);
			} else {
				t1 = teamsMap.get(gr.getOpponent1());
			}

			if (!teamsMap.containsKey(gr.getOpponent2())) {
				t2 = new GaussTeam(gr.getOpponent2());
				teamsMap.put(gr.getOpponent2(), t2);
			} else {
				t2 = teamsMap.get(gr.getOpponent2());
			}

			t1.updateSkill(t2, gr.getScore1(), gr.getScore2());
			goalCount = goalCount + gr.getScore1() + gr.getScore2();

		}

		System.out.println("Teams: " + teamsMap.size());
		System.out.println("Games: " + gameCount);
		System.out.println("Goals: " + goalCount + " (average "
				+ ((float) goalCount / gameCount) + ")");
	}

	@Override
	public double predict(GameResult gr) {

		double avgGoal = (double) goalCount / gameCount;

		ITeam t1 = getTeam(gr.getOpponent1());
		ITeam t2 = getTeam(gr.getOpponent2());

		if (t1 == null) {
			t1 = new GaussTeam(gr.getOpponent1());
		}

		if (t2 == null) {
			t2 = new GaussTeam(gr.getOpponent2());
		}

		double p;
		p = this.getWinProb((GaussTeam) t1, (GaussTeam) t2);

		double pg1 = ((double) (avgGoal) * p);
		double pg2 = ((double) (avgGoal) * (1 - p));

		int gDiff = round(pg1 - pg2, pgs);

		// System.out.println("\n\t\t\t\t\t\t\t\t\t" +pg1+"-"+
		// pg2+"="+(pg1-pg2)+":"+(gDiff));

		double g1 = (avgGoal + gDiff) / 2;
		double g2 = (avgGoal - gDiff) / 2;

		// double g1 = (avgGoal + gDiff + pgs + (t1.getGoalsWon() -
		// t2.getGoalsLos())/gameCount) / 2;
		// double g2 = (avgGoal - gDiff + pgs+ (t2.getGoalsWon() -
		// t1.getGoalsLos())/gameCount) / 2;

		// System.out.println("\t\t\t\t\t\t\t\t\t" + g1 + " - " + g2);

		gr.setScore1(round(g1, gs));
		gr.setScore2(round(g2, gs));

		return p;
	}

	public double getWinProb(ITeam t1, ITeam t2) {
		double p;
		if (t1 instanceof GaussTeam && t2 instanceof GaussTeam) {
			double t1Skill = t1.getSkill() - ((GaussTeam) t1).getSigma();
			double t2Skill = t2.getSkill() - ((GaussTeam) t2).getSigma();
			p = 1 / (1 + Math.exp((t2Skill - t1Skill) / flatten));
		} else {
			p = super.getWinProb(t1, t2);
		}

		return p;
	}

}
