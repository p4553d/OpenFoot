package game;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistributionImpl;

/**
 * Example implementation of team Skill calculation derived from Elo-points in
 * chess
 * 
 * @author mutant
 */
public class GaussTeam extends AbstractTeam {

	public static double step = 1.4625;
	public static double discr = 0.0125;
	public static double dev = 0.2625;

	protected double sigma;

	public GaussTeam(String name) {
		super(name);
		sigma = skill;
	}

	@Override
	public double getWinProb(ITeam opp) {
		double p = 0.5;
		GaussTeam gopp = (GaussTeam) opp;
		NormalDistributionImpl nd = new NormalDistributionImpl(this.skill
				- gopp.getSkill(), 1 + this.sigma + gopp.getSigma());
		try {
			p = 1 - nd.cumulativeProbability(0);
		} catch (MathException e) {
			e.printStackTrace();
		}
		return p;
	}

	public double getSigma() {
		return sigma;
	}

	public void updateSkill(AbstractTeam opponent, int ownScore, int oppScore) {
		GaussTeam oppt = (GaussTeam) opponent;
		int resPoint = (int) (Math.signum(ownScore - oppScore) + 1);

		double prob = getWinProb(oppt);

		double ownNewSigma = this.sigma;
		double oppNewSigma = oppt.sigma;

		// review variation according to expected and real result
		switch (resPoint) {
		case 2:
			this.winCount++;
			oppt.lossCount++;

			if (prob > 0.5 - discr) {
				ownNewSigma = ownNewSigma * (1 - prob + dev);
				oppNewSigma = oppNewSigma * (1 - prob + dev);
			}
			break;

		case 0:
			oppt.winCount++;
			this.lossCount++;

			if (prob < 0.5 + discr) {
				ownNewSigma = ownNewSigma * (prob + dev);
				oppNewSigma = oppNewSigma * (prob + dev);
			}

			break;

		case 1:
			this.drawCount++;
			oppt.drawCount++;
			if (prob > 0.5 - discr && prob < 0.5 + discr) {
				ownNewSigma = ownNewSigma * (prob + dev);
				oppNewSigma = oppNewSigma * (prob + dev);
			}

			break;

		default:
			break;
		}

		this.skill = this.skill + step * (resPoint / 2.0 - prob)
				* (this.sigma / (this.sigma + oppt.sigma));
		oppt.skill = oppt.skill - step * (resPoint / 2.0 - prob)
				* (oppt.sigma / (this.sigma + oppt.sigma));

		this.sigma = ownNewSigma;
		oppt.sigma = oppNewSigma;
	}

}
