package core;

import java.io.IOException;

import configuration.ConfPredictData;
import evaluate.DaussCalculation;
import evaluate.IPredictor;
import game.GameResult;

public class UnknownResults {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String learnSet = "src/data/smallset"; // learn from this game set
		String testSet = "src/data/fullpred"; // predict this set

		// learn
		System.out.println("\n+----- Learn -----+\n");
		IPredictor sc = new DaussCalculation(learnSet);

		// get game data
		ConfPredictData pd;
		try {
			pd = new ConfPredictData(testSet);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		int unknown = 0;

		int predGoals = 0;

		System.out.println("\n+----- Prediction -----+\n");

		for (int i = 0; i < pd.getSize(); i++) {

			GameResult gr = pd.getResultAt(i);

			if ((i % 9) == 0) {
				System.out
						.println("\n[" + gr.getYear() + " " + gr.getDay() + "]");
			}

			double p = sc.predict(gr);

			System.out.printf("\t[%.3f] %55s\n", p, gr.toString());
			predGoals = predGoals + gr.getScore1() + gr.getScore2();
		}

		// final statistics
		System.out.println("\n+-----------------------+");
		System.out.println(pd.getSize() + " games (" + unknown + ") unknown");
		System.out
				.println("Average goals " + (double) predGoals / pd.getSize());

	}
}
