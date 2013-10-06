package evaluate;

import game.EloTeam;
import game.GameResult;
import game.ITeam;

import java.io.IOException;
import java.util.HashMap;

import configuration.ConfResultData;

public class EloCalculation extends AbstractSkillCalculator {

	public EloCalculation(String fileName) {
		teamsMap = new HashMap<String, ITeam>();
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
				t1 = new EloTeam(gr.getOpponent1());
				teamsMap.put(gr.getOpponent1(), t1);
			} else {
				t1 = teamsMap.get(gr.getOpponent1());
			}

			if (!teamsMap.containsKey(gr.getOpponent2())) {
				t2 = new EloTeam(gr.getOpponent2());
				teamsMap.put(gr.getOpponent2(), t2);
			} else {
				t2 = teamsMap.get(gr.getOpponent2());
			}

			t1.updateSkill(t2, gr.getScore1(), gr.getScore2());

		}

		System.out.println("Teams: " + teamsMap.size());
		System.out.println("Games: " + gameCount);
	}

	@Override
	public double predict(GameResult gr) {

		ITeam t1 = getTeam(gr.getOpponent1());
		ITeam t2 = getTeam(gr.getOpponent2());

		if (t1 == null) {
			t1 = new EloTeam(gr.getOpponent1());
		}

		if (t2 == null) {
			t2 = new EloTeam(gr.getOpponent2());
		}

		double p;
		p = getWinProb(t1, t2);

		if (p < 0.45) {
			gr.setScore1(0);
			gr.setScore2(1);
		} else {
			if (p > 0.55) {
				gr.setScore1(1);
				gr.setScore2(0);
			} else {
				gr.setScore1(0);
				gr.setScore2(0);
			}
		}
		return p;
	}

}
