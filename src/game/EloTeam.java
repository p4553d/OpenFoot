package game;

/**
 * Example implementation of team
 * Skill calculation derived from Elo-points in chess
 * 
 * @author mutant
 */
public class EloTeam extends AbstractTeam {

	static final double step = 1.0;
	static final double flatten = 7.0;

	public EloTeam(String name) {
		super(name);
	}

	@Override
	public double getWinProb(ITeam opp) {
		double oppSkill = opp.getSkill();
		double p = 1 / (1 + Math.exp((oppSkill-skill) / flatten));
		return p;
	}

	public void updateSkill(AbstractTeam opponent, int ownScore, int oppScore) {
		int resPoint = (int)(Math.signum(ownScore - oppScore) + 1);
		
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

		this.skill = this.skill + (resPoint/2.0 - getWinProb(opponent)) * step;
		opponent.skill = opponent.skill - (resPoint/2.0 - getWinProb(opponent)) * step;
	}

}
