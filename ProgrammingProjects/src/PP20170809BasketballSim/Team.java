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
			toRate,			// Turnover rate during the match-up
			offRebound,		// Offensive Rebound Percentage
			defRebound, 	// Defensive Rebound Percentage
			offMatchupRebound; // (Team1 offRebound + (100 - Team2 defRebound) ) / 2

	private int score, 		//Hold the score of the team object
			index;					//Index of the team in the csv file
	private String name; 	//Hold the name of the team object

	public static final String[] POSSIBLE_TEAM_NAMES = { //Static variable of all possible team names
			"ABILENE CHRISTIAN",
			"AIR FORCE",
			"AKRON",
			"ALAB A&AMP;M",
			"ALABAMA",
			"ALABAMA ST",
			"ALBANY",
			"ALCORN STATE",
			"AMERICAN",
			"APP STATE",
			"AR LIT ROCK",
			"ARIZONA",
			"ARIZONA ST",
			"ARK PINE BL",
			"ARKANSAS",
			"ARKANSAS ST",
			"ARMY",
			"AUBURN",
			"AUSTIN PEAY",
			"BALL STATE",
			"BAYLOR",
			"BELMONT",
			"BETH-COOK",
			"BINGHAMTON",
			"BOISE STATE",
			"BOSTON COL",
			"BOSTON U",
			"BOWLING GRN",
			"BRADLEY",
			"BROWN",
			"BRYANT",
			"BUCKNELL",
			"BUFFALO",
			"BUTLER",
			"BYU",
			"CAL POLY",
			"CAL ST NRDGE",
			"CALIFORNIA",
			"CAMPBELL",
			"CANISIUS",
			"CENTRAL ARK",
			"CENTRAL CONN",
			"CENTRAL FL",
			"CENTRAL MICH",
			"CHARL SOUTH",
			"CHARLOTTE",
			"CHATTANOOGA",
			"CHICAGO ST",
			"CINCINNATI",
			"CITADEL",
			"CLEMSON",
			"CLEVELAND ST",
			"COASTAL CAR",
			"COL CHARLESTN",
			"COLGATE",
			"COLORADO",
			"COLORADO ST",
			"COLUMBIA",
			"CONNECTICUT",
			"COPPIN STATE",
			"CORNELL",
			"CREIGHTON",
			"CS BAKERSFLD",
			"CS FULLERTON",
			"DARTMOUTH",
			"DAVIDSON",
			"DAYTON",
			"DELAWARE",
			"DELAWARE ST",
			"DENVER",
			"DEPAUL",
			"DETROIT",
			"DRAKE",
			"DREXEL",
			"DUKE",
			"DUQUESNE",
			"E CAROLINA",
			"E ILLINOIS",
			"E KENTUCKY",
			"E MICHIGAN",
			"E TENN ST",
			"E WASHINGTN",
			"ELON",
			"EVANSVILLE",
			"F DICKINSON",
			"FAIRFIELD",
			"FLA ATLANTIC",
			"FLA GULF CST",
			"FLORIDA",
			"FLORIDA A&AMP;M",
			"FLORIDA INTL",
			"FLORIDA ST",
			"FORDHAM",
			"FRESNO ST",
			"FURMAN",
			"GA SOUTHERN",
			"GA TECH",
			"GARD-WEBB",
			"GEO MASON",
			"GEO WSHGTN",
			"GEORGETOWN",
			"GEORGIA",
			"GEORGIA ST",
			"GONZAGA",
			"GRAMBLING ST",
			"GRAND CANYON",
			"HAMPTON",
			"HARTFORD",
			"HARVARD",
			"HAWAII",
			"HIGH POINT",
			"HOFSTRA",
			"HOLY CROSS",
			"HOUSTON",
			"HOUSTON BAP",
			"HOWARD",
			"IDAHO",
			"IDAHO STATE",
			"IL-CHICAGO",
			"ILLINOIS",
			"ILLINOIS ST",
			"INCARNATE WORD",
			"INDIANA",
			"INDIANA ST",
			"IONA",
			"IOWA",
			"IOWA STATE",
			"IPFW",
			"IUPUI",
			"JACKSON ST",
			"JACKSONVILLE",
			"JAMES MAD",
			"JKSNVILLE ST",
			"KANSAS",
			"KANSAS ST",
			"KENNESAW ST",
			"KENT STATE",
			"KENTUCKY",
			"LA LAFAYETTE",
			"LA MONROE",
			"LA SALLE",
			"LA TECH",
			"LAFAYETTE",
			"LAMAR",
			"LEHIGH",
			"LG BEACH ST",
			"LIBERTY",
			"LIPSCOMB",
			"LIU-BROOKLYN",
			"LONGWOOD",
			"LOUISVILLE",
			"LOYOLA MYMT",
			"LOYOLA-CHI",
			"LOYOLA-MD",
			"LSU",
			"MAINE",
			"MANHATTAN",
			"MARIST",
			"MARQUETTE",
			"MARSHALL",
			"MARYLAND",
			"MARYLAND BC",
			"MARYLAND ES",
			"MASSACHUSETTS LOWELL",
			"MCNEESE ST",
			"MEMPHIS",
			"MERCER",
			"MIAMI (FL)",
			"MIAMI (OH)",
			"MICHIGAN",
			"MICHIGAN ST",
			"MIDDLE TENN",
			"MINNESOTA",
			"MISS STATE",
			"MISS VAL ST",
			"MISSISSIPPI",
			"MISSOURI",
			"MISSOURI ST",
			"MONMOUTH",
			"MONTANA",
			"MONTANA ST",
			"MOREHEAD ST",
			"MORGAN ST",
			"MT ST MARYS",
			"MURRAY ST",
			"N ARIZONA",
			"N CAROLINA",
			"N COLORADO",
			"N DAKOTA ST",
			"N FLORIDA",
			"N HAMPSHIRE",
			"N ILLINOIS",
			"N IOWA",
			"N KENTUCKY",
			"N MEX STATE",
			"NAVY",
			"NC A&AMP;T",
			"NC CENTRAL",
			"NC STATE",
			"NC-ASHEVILLE",
			"NC-GRNSBORO",
			"NC-WILMGTON",
			"NEB OMAHA",
			"NEBRASKA",
			"NEVADA",
			"NEW MEXICO",
			"NEW ORLEANS",
			"NIAGARA",
			"NICHOLLS ST",
			"NJIT",
			"NORFOLK ST",
			"NORTH DAKOTA",
			"NORTH TEXAS",
			"NORTHEASTRN",
			"NORTHWESTERN",
			"NOTRE DAME",
			"NW STATE",
			"OAKLAND",
			"OHIO",
			"OHIO STATE",
			"OKLAHOMA",
			"OKLAHOMA ST",
			"OLD DOMINION",
			"ORAL ROBERTS",
			"OREGON",
			"OREGON ST",
			"PACIFIC",
			"PENN STATE",
			"PEPPERDINE",
			"PITTSBURGH",
			"PORTLAND",
			"PORTLAND ST",
			"PRAIRIE VIEW",
			"PRESBYTERIAN",
			"PRINCETON",
			"PROVIDENCE",
			"PURDUE",
			"QUINNIPIAC",
			"RADFORD",
			"RHODE ISLAND",
			"RICE",
			"RICHMOND",
			"RIDER",
			"ROB MORRIS",
			"RUTGERS",
			"S ALABAMA",
			"S CAR STATE",
			"S CAROLINA",
			"S DAKOTA ST",
			"S FLORIDA",
			"S ILLINOIS",
			"S METHODIST",
			"S MISSISSIPPI",
			"S UTAH",
			"SAC STATE",
			"SACRED HRT",
			"SAINT LOUIS",
			"SAM HOUS ST",
			"SAMFORD",
			"SAN DIEGO",
			"SAN DIEGO ST",
			"SAN FRANSCO",
			"SAN JOSE ST",
			"SANTA CLARA",
			"SAVANNAH ST",
			"SC UPSTATE",
			"SE LOUISIANA",
			"SE MISSOURI",
			"SEATTLE",
			"SETON HALL",
			"SIENA",
			"SIU EDWARD",
			"SOUTH DAKOTA",
			"SOUTHERN",
			"ST BONAVENT",
			"ST FRAN (NY)",
			"ST FRAN (PA)",
			"ST JOHNS",
			"ST JOSEPHS",
			"ST MARYS",
			"ST PETERS",
			"STANFORD",
			"STE F AUSTIN",
			"STETSON",
			"STONY BROOK",
			"SYRACUSE",
			"TEMPLE",
			"TENNESSEE",
			"TEXAS",
			"TEXAS A&AMP;M",
			"TEXAS STATE",
			"TEXAS TECH",
			"TN MARTIN",
			"TN STATE",
			"TN TECH",
			"TOLEDO",
			"TOWSON",
			"TROY",
			"TULANE",
			"TULSA",
			"TX A&AMP;M-CC",
			"TX CHRISTIAN",
			"TX EL PASO",
			"TX SOUTHERN",
			"TX-ARLINGTON",
			"TX-PAN AM",
			"TX-SAN ANT",
			"U MASS",
			"U PENN",
			"UAB",
			"UC DAVIS",
			"UC IRVINE",
			"UC RIVERSIDE",
			"UCLA",
			"UCSB",
			"UMKC",
			"UNLV",
			"USC",
			"UTAH",
			"UTAH STATE",
			"UTAH VAL ST",
			"VA MILITARY",
			"VA TECH",
			"VALPARAISO",
			"VANDERBILT",
			"VCU",
			"VERMONT",
			"VILLANOVA",
			"VIRGINIA",
			"W CAROLINA",
			"W ILLINOIS",
			"W KENTUCKY",
			"W MICHIGAN",
			"W VIRGINIA",
			"WAGNER",
			"WAKE FOREST",
			"WASH STATE",
			"WASHINGTON",
			"WEBER STATE",
			"WI-GRN BAY",
			"WI-MILWKEE",
			"WICHITA ST",
			"WINTHROP",
			"WISCONSIN",
			"WM &AMP; MARY",
			"WOFFORD",
			"WRIGHT STATE",
			"WYOMING",
			"XAVIER",
			"YALE",
			"YOUNGS ST"
	}; 

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
		index = 0;
	}
	
	//Function to validate the name of each team inputted
	public static int validateName(String name) {
		 int indexOfName = BinarySearch.search(name, POSSIBLE_TEAM_NAMES);
		 
		 if (indexOfName == -1) return -1;
		 else return indexOfName;
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
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public double getOffRebound() {
		return offRebound;
	}

	public void setOffRebound(double offRebound) {
		this.offRebound = offRebound;
	}

	public double getDefRebound() {
		return defRebound;
	}

	public void setDefRebound(double defRebound) {
		this.defRebound = defRebound;
	}
	
	public double getOffMatchupRebound() {
		return offMatchupRebound;
	}

	public void setOffMatchupRebound(double offMatchupRebound) {
		this.offMatchupRebound = offMatchupRebound;
	}
}