package evaluate;

import game.ITeam;

import java.util.HashMap;
import java.util.Vector;

public abstract class AbstractSkillCalculator implements ISkillCalculator, IPredictor {

	protected HashMap<String, ITeam> teamsMap;
	protected int gameCount;

	public AbstractSkillCalculator() {
		super();
	}

	@Override
	public int getTeamCount() {
		return teamsMap.size();
	}

	@Override
	public ITeam getTeam(String name) {
		return teamsMap.get(name);
	}
	
	@Override
	public int getGameCount(){
		return gameCount;
	}

	@Override
	public Vector<ITeam> getTeamSet() {
		Vector<ITeam> vit = new Vector<ITeam>();
		for (String name : teamsMap.keySet()) {
			vit.add(teamsMap.get(name));
		}
		return vit;
	}

	@Override
	// Use default implementation from team class
	public double getWinProb(ITeam t1, ITeam t2) {
		return t1.getWinProb(t2);
	}
	
	public int round (double val, double treshold){
		int res;
		if(val>0){
			 res = (int)(val+treshold);
		}
		else{
			res = (int)(val-treshold);
		}
		return res;
	}
	
	public int round (double val){
		return round(val, 0);
	}

}