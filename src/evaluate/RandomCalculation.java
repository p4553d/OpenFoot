package evaluate;

import game.GameResult;

import java.util.Random;

public class RandomCalculation extends AbstractSkillCalculator {

	private Random r = new Random();
	public RandomCalculation(String fileName) {
	}

	@Override
	public double predict(GameResult gr) {
		
		double p = r.nextDouble();

		if (p < 0.35) {
			gr.setScore1(0);
			gr.setScore2(1);
		} else {
			if (p > 0.65) {
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
