package PP20170809BasketballSim;

import utilities.BinarySearch;
public class Team {
	private double avgFG, 	// Average FG %
			avg3, 			// Avg 3 pt %
			matchupAvgFg, 	// (team1 avgFG+team2 oppFG)/2
			matchupAvg3, 	// (team1 avg3+team2 opp3)/2
			oppFG, 			// Avg opp fg %
			opp3, 			// Avg opp 3 %
			ftPP, 			// Avg freethrows taken per possession
			ft, 			// Avg ft %
			ftStored, 		// ftPP added each possession. On a miss, if ftStored = 2, shoot free throws. On a make, if ftStored=1 shoot free throw
			shotsFrom2, 	// Avg points % from 2
			shotsFrom3, 	// Avg points % from 3
			possPG, 		// Avg possessions during a game
			turnovers, 		// Avg turnovers during a game
			toRate; 		// Turnover rate during the match-up
	private int score; 		//Hold the score of the team object
	private String name; 	//Hold the name of the team object
	public static String[] possibleTeamNames; //Static variable of all possible team names

	Team() {
		//Setting all the values to zero upon instantiation.
		avgFG = 0;
		avg3 = 0;
		matchupAvgFg = 0;
		matchupAvg3 = 0;
		oppFG = 0;
		opp3 = 0;
		ftPP = 0;
		ft = 0;
		ftStored = 0;
		shotsFrom2 = 0;
		shotsFrom3 = 0;
		possPG = 0;
		turnovers = 0;
		toRate = 0;
		name = "";
		score = 0;
	}
	
	//Function to validate the name of each team inputted
	public static boolean validateName(String name) {
		 int index = BinarySearch.search(name, possibleTeamNames);
		 
		 if (index == -1) return false;
		 else return true;
	}

	// Each fgAtt add to ftStored
	public void addToFtStored() {
		ftStored += ftPP;
	}

	// After each ftAtt subtract from ftStored
	public void subFromFtStored() {
		ftStored -= 1;
	}

	// Add points to score
	public void addToScore(int points) {
		score += points;
	}
	
	/* 
	 * GETTERS AND SETTERS 
	 */

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getAvgFG() {
		return avgFG;
	}

	public void setAvgFG(double avgFG) {
		this.avgFG = avgFG;
	}

	public double getAvg3() {
		return avg3;
	}

	public void setAvg3(double avg3) {
		this.avg3 = avg3;
	}

	public double getMatchupAvgFg() {
		return matchupAvgFg;
	}

	public void setMatchupAvgFg(double matchupAvgFg) {
		this.matchupAvgFg = matchupAvgFg;
	}

	public double getMatchupAvg3() {
		return matchupAvg3;
	}

	public void setMatchupAvg3(double matchupAvg3) {
		this.matchupAvg3 = matchupAvg3;
	}

	public double getOppFG() {
		return oppFG;
	}

	public void setOppFG(double oppFG) {
		this.oppFG = oppFG;
	}

	public double getOpp3() {
		return opp3;
	}

	public void setOpp3(double opp3) {
		this.opp3 = opp3;
	}

	public double getFtPP() {
		return ftPP;
	}

	public void setFtPP(double ftPP) {
		this.ftPP = ftPP;
	}

	public double getFtStored() {
		return ftStored;
	}

	public void setFtStored(double ftStored) {
		this.ftStored = ftStored;
	}

	public double getFt() {
		return ft;
	}

	public void setFt(double ft) {
		this.ft = ft;
	}

	public double getShotsFrom2() {
		return shotsFrom2;
	}

	public void setShotsFrom2(double shotsFrom2) {
		this.shotsFrom2 = shotsFrom2;
	}

	public double getShotsFrom3() {
		return shotsFrom3;
	}

	public void setShotsFrom3(double shotsFrom3) {
		this.shotsFrom3 = shotsFrom3;
	}

	public double getPossPG() {
		return possPG;
	}

	public void setPossPG(double possPG) {
		this.possPG = possPG;
	}

	public double getTurnovers() {
		return turnovers;
	}

	public void setTurnovers(double turnovers) {
		this.turnovers = turnovers;
	}

	public double getToRate() {
		return toRate;
	}

	public void setToRate(double toRate) {
		this.toRate = toRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}