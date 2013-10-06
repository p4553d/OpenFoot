package core;

import java.io.IOException;

import configuration.ConfPredictData;
import configuration.ConfResultData;
import evaluate.DaussCalculation;
import evaluate.IPredictor;
import game.GameResult;
import game.GaussTeam;

public class MPredictResults {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String learnSet = "src/data/smallset"; // learn from this game set
		String testSet = "src/data/testset"; // predict this set
		String orakelSet = "src/data/controllset"; // use this set, to evaluate

		System.out.println("+------- Skill -------+");

		// get game data
		ConfResultData controll;
		ConfPredictData pd;
		try {
			pd = new ConfPredictData(testSet);
			controll = new ConfResultData(orakelSet);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		for (double param = 1; param < 2.5; param = param + .0125) {

			// learn
			GaussTeam.step = param;
			IPredictor sc = new DaussCalculation(learnSet);

			// Collect some statistic
			int points = 0;
			int unknown = 0;

			int p0pred = 0;
			int p1pred = 0;
			int p2pred = 0;
			int p3pred = 0;

			for (int i = 0; i < pd.getSize(); i++) {
				GameResult gr = pd.getResultAt(i);
				GameResult ogr = controll.getResultAt(i);

				sc.predict(gr);

				points = points
						+ gr.getPredPoints(ogr.getScore1(), ogr.getScore2());

				switch (gr.getPredPoints(ogr.getScore1(), ogr.getScore2())) {
				case 0:
					p0pred++;
					break;
				case 1:
					p1pred++;
					break;
				case 2:
					p2pred++;
					break;
				case 3:
					p3pred++;
					break;
				}
			}

			// final statistics
			System.out.println("-------------------------");
			System.out.println("param = " + param);
			System.out.println(pd.getSize() + " games (" + unknown
					+ ") unknown");
			System.out.println(points + "/" + (pd.getSize() * 3)
					+ " prediction points");
			System.out.printf("\t0 points x %d (%3.1f%%)\n", p0pred, 100.0
					* p0pred / pd.getSize());
			System.out.printf("\t1 points x %d (%3.1f%%)\n", p1pred, 100.0
					* p1pred / pd.getSize());
			System.out.printf("\t2 points x %d (%3.1f%%)\n", p2pred, 100.0
					* p2pred / pd.getSize());
			System.out.printf("\t3 points x %d (%3.1f%%)\n", p3pred, 100.0
					* p3pred / pd.getSize());
			System.out.println((points * 9.0 / pd.getSize())
					+ " pro game average");
		}

	}
}
