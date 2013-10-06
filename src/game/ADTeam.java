package game;

/**
 * Example implementation of team
 * 
 * @author mutant
 */
public class ADTeam extends AbstractTeam {

	static final double step = 1.0;
	static final double flatten = 7.0;

	private double attack = 100;
	private double defence = 100;

	public ADTeam(String name) {
		super(name);
	}

	@Override
	public double getWinProb(ITeam opp) {
		double oppSkill = opp.getSkill();
		double p = 1 / (1 + Math.exp((oppSkill - skill) / flatten));
		return p;
	}

	public double getWinProb(double att, double def) {
		double p = 1 / (1 + Math.exp((def - att) / flatten));
		return p;
	}

	public void updateSkill(AbstractTeam opponent, int ownScore, int oppScore) {
		ADTeam oppt = (ADTeam) opponent;
		int resPoint = (int) (Math.signum(ownScore - oppScore) + 1);

		switch (resPoint) {
		case 2:
			this.winCount++;
			opponent.lossCount++;
			break;

		case 0:
			opponent.winCount++;
			this.lossCount++;
			break;

		case 1:
			this.drawCount++;
			opponent.drawCount++;
			break;

		default:
			break;
		}
		
		double t1att = this.attack;
		double t1def = this.defence;
		double t2att = oppt.attack;
		double t2def = oppt.defence;
		
		// Home team
		
		
		
		
	}

}
