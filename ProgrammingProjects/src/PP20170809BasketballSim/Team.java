package PP20170809BasketballSim;

public class Team {
	private double avgFG, // Average FG %
	avg3, // Avg 3 pt %
	matchupAvgFg, // (team1 avgFG+team2 oppFG)/2
	matchupAvg3, // (team1 avg3+team2 opp3)/2
	oppFG, // Avg opp fg %
	opp3, // Avg opp 3 %
	ftPP, // Avg freethrows taken per possession
	ft, // Avg ft %
	ftStored, // ftPP added each possession. On a miss, if ftStored = 2, shoot free throws. On a make, if ftStored=1 shoot free throw
	shotsFrom2, // Avg points % from 2
	shotsFrom3, // Avg points % from 3
	possPG, // Avg possessions during a game
	turnovers, // Avg turnovers during a game
	toRate; // Turnover rate during the match-up
private int score;
private String name;

Team() {
this.avgFG = 0;
this.avg3 = 0;
this.matchupAvgFg = 0;
this.matchupAvg3 = 0;
this.oppFG = 0;
this.opp3 = 0;
this.ftPP = 0;
this.ft = 0;
this.ftStored = 0;
this.shotsFrom2 = 0;
this.shotsFrom3 = 0;
this.possPG = 0;
this.turnovers = 0;
this.toRate = 0;
this.name = "";
this.score = 0;
}

// Each fgAtt add to ftStored
public void addToFtStored() {
this.ftStored += this.ftPP;
}

// After each ftAtt subtract from ftStored
public void subFromFtStored() {
this.ftStored -= 1;
}

// Add points to score
public void addToScore(int points) {
this.score += points;
}

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