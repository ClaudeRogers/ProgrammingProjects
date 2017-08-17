import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utilities.BinarySearch;

/*Basketball simulation application
*User inputs two teams
*The app then pulls the data down for each team from teamrankings.com/ncaa-basketball/stat
*
*The app then runs through a game displaying each possession
* */

//TODO Save the teams stats in a text file
//TODO After a month, or week, update the text file by re-getting stats on the website

//TODO Set up threading so the app opens, checks if a text file is there, and if not gets the teams via the website. While asking the user the names of the teams.
//TODO implement rebounding
//TODO See if I can cleanup code/move some code to new/other methods for ease of use when adding features (run possession for rebounding)

public class PP20170809BasketballSim {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		Game game = new Game();
	}
}

class Game {
	
	
	private int totalPoss = 0, // Total possessions of the game
			half = 0, // 0 = first half, 1 = second half
			possession = 0; // 0 = team1, 1 = team2
	private List<Integer> periodPoss = new ArrayList<>(); // Array List to hold the amount of possession for each period
	private double timeLeft = 1200.00; // 1200 = 20:00, 300 = 5:00
	private List<Double> timeOfPoss = new ArrayList<>(); // Average time of possession for each period
	private final static String[] STATS_NEEDED_LINKS = { "https://www.teamrankings.com/ncaa-basketball/stat/two-point-pct", // avgFG
			"https://www.teamrankings.com/ncaa-basketball/stat/three-point-pct", // avg3
			"https://www.teamrankings.com/ncaa-basketball/stat/opponent-two-point-pct", /// oppFG
			"https://www.teamrankings.com/ncaa-basketball/stat/opponent-three-point-pct", // opp3
			"https://www.teamrankings.com/ncaa-basketball/stat/fta-per-fga", // ftPP
			"https://www.teamrankings.com/ncaa-basketball/stat/free-throw-pct", // ft
			"https://www.teamrankings.com/ncaa-basketball/stat/two-point-rate", // shotsFrom2
			"https://www.teamrankings.com/ncaa-basketball/stat/three-point-rate", // shotsFrom3
			"https://www.teamrankings.com/ncaa-basketball/stat/possessions-per-game", // possPG
			"https://www.teamrankings.com/ncaa-basketball/stat/turnovers-per-possession" // toRate
	};

	Game() throws IOException {
		saveStatsToTextFile();
		
		Scanner sc = new Scanner(System.in); // For input
		Team[] teams = new Team[2]; // Array to hold the two teams
		for (int i = 0; i < 2; i++) {
			teams[i] = new Team(); // Initializing each team
		}

		// Getting the teams's names
		for (int i = 0; i < 2; i++) {
			System.out.print("Please enter Team "+(i+1)+"'s name:  ");
			teams[i].setName(sc.nextLine().trim().toUpperCase());
			while (!validateName(teams[i])) {
				System.out.println("Team name not found on www.teamrankings.com/ncb/.\nPlease visit the site and find the teams name.");
				
				System.out.print("Please enter Team "+(i+1)+"'s name:  ");
				teams[i].setName(sc.nextLine().trim().toUpperCase());
			}
		}
		sc.close(); // Closes scanner

		System.out.println("\nTonight's game is " + teams[0].getName() + " vs " + teams[1].getName() + "\n");

		getStats(teams, sc); // Getting the stats via user input

		// Calculating the total possessions in the game. Rounding down as a team cannot
		// have less than one possession.
		this.setTotalPoss((int) Math.floor(teams[0].getPossPG() + teams[1].getPossPG()));

		// Calculating the total possessions for the first half and the second half of
		// the game.
		this.appendPeriodPoss(this.getTotalPoss() / 2); // Gives half of the possessions to the first half
		this.appendPeriodPoss(this.getTotalPoss() - this.getPeriodPoss(0)); // Subtracts the first half from the total,
																			// that way it allows for an odd number of
																			// possessions.

		// Calculating the time of possession for each half.
		this.appendTimeOfPoss(this.getTimeLeft() / this.getPeriodPoss(0));
		this.appendTimeOfPoss(this.getTimeLeft() / this.getPeriodPoss(1));

		// Calculates each teams FG% for the game
		teams[0].setMatchupAvgFg((teams[0].getAvgFG() + teams[1].getOppFG()) / 2);
		teams[1].setMatchupAvgFg((teams[1].getAvgFG() + teams[0].getOppFG()) / 2);
		teams[0].setMatchupAvg3((teams[0].getAvg3() + teams[1].getOpp3()) / 2);
		teams[1].setMatchupAvg3((teams[1].getAvg3() + teams[0].getOpp3()) / 2);

		// Play a game
		playAGame(teams);
	}

	public boolean validateName(Team team) throws IOException{
		//Connecting to a table to pull the team names
		Document doc = Jsoup.connect(STATS_NEEDED_LINKS[0]).get();
	
		for (Element row : doc.select("td > a")) {
			if (row.html().toUpperCase().equals(team.getName())) return true;
		}			
		return false;
	}

	// Function to play the whole game
	public void playAGame(Team[] teams) {
		String firSec = "first";
		// Calculating who wins the tipoff and gets the first possession
		double tipoff = Math.random() * 100 + 1;

		// Loops through both halves
		for (int i = 0; i < 2; i++) {
			this.setHalf(i); // Sets the half

			// Prints which half is starting.
			if (this.getHalf() == 1)
				firSec = "second";
			System.out.println("The " + firSec + " half is starting!");

			// Printing out who won the tip if it is the first half
			if (this.getHalf() == 0) {
				printTipWinner(tipoff, teams);
			}

			// Call the method to play a half
			playAHalf(teams);
		}

		// If game is tied after the first two halves
		if (teams[0].getScore() == teams[1].getScore()) {
			System.out.println("End of Regulation! Time to go to overtime!");
		}

		// Run an overtime period
		while (teams[0].getScore() == teams[1].getScore()) {
			this.setHalf(this.getHalf() + 1);
			System.out.println("Overtime period #" + (this.getHalf() - 1) + " is starting!");
			playAHalf(teams);
		}

		if (teams[0].getScore() > teams[1].getScore()) {
			System.out.println(teams[0].getName() + " wins!");
		} else {
			System.out.println(teams[1].getName() + " wins!");
		}
	}

	// A function to play a half/OT of the game
	public void playAHalf(Team[] teams) {
		// Variables used to hold a random number to determine if the possession is
		// turned over, or what shot is attempted, and whether the shot goes in
		double turnover = 0, fg = 0, twoOrThree = 0;

		// Sets the time left for each period. 1200 = 20:00, 300 = 5:00
		if (this.getHalf() < 2)
			this.setTimeLeft(1200.00);
		else {
			this.setTimeLeft(300.00);

			// Setting the total possessions, time of possessions and tip off for overtime
			this.appendTimeOfPoss(this.getTimeOfPoss(1));
			int otPoss = (int) (300 / this.getTimeOfPoss(1));
			this.appendPeriodPoss(otPoss);

			// Calculating who wins the tipoff and gets the first possession
			double tipoff = Math.random() * 100 + 1;
			printTipWinner(tipoff, teams);
		}

		// Looping through each possesion
		for (int i = 0; i < this.getPeriodPoss(this.getHalf()); i++) {
			// Displaying the time left
			this.setTimeLeft(this.getTimeLeft() - this.getTimeOfPoss(this.getHalf()));
			System.out.println(
					(int) (this.getTimeLeft() / 60) + ":" + String.format("%02d", (int) (this.getTimeLeft() % 60)));

			// Calculates if possession results in turnover.
			turnover = Math.random() * 100 + 1;

			// If a turnover occurs. if the random turnover number is less than the TO rate
			// of the team
			if (turnover < teams[this.getPossession()].getToRate()) {
				System.out.println(teams[this.getPossession()].getName() + " turned the ball over!");

				// Get game score and change possession
				getGameScore(teams);
				changePossession();
				continue;
			}

			// Adding ftPP to ftStored
			teams[this.getPossession()].addToFtStored();

			// Calculating what shot is attempted and whether it is made
			twoOrThree = Math.random() * 100 + 1;// Calculating if a 2 or 3 is shot
			fg = Math.random() * 100 + 1; // Calculating if a FG is made
			if (twoOrThree > teams[this.getPossession()].getShotsFrom3()) {
				if (fg < teams[this.getPossession()].getAvgFG()) {
					teams[this.getPossession()].addToScore(2);
					System.out.println(teams[this.getPossession()].getName() + " scores a two pointer!");
					willShootFT(teams[this.getPossession()], 1);
				} else {
					System.out.println(teams[this.getPossession()].getName() + " misses a two pointer!");
					willShootFT(teams[this.getPossession()], 2);
				}
			} else {
				if (fg < teams[this.getPossession()].getAvg3()) {
					teams[this.getPossession()].addToScore(3);
					System.out.println(teams[this.getPossession()].getName() + " scores a three pointer!");
					willShootFT(teams[this.getPossession()], 1);
				} else {
					System.out.println(teams[this.getPossession()].getName() + " misses a three pointer!");
					willShootFT(teams[this.getPossession()], 3);
				}
			}

			// Get game score and change possession
			getGameScore(teams);
			changePossession();

			// The second ticker to make it seem like a simulation
			try {
				Thread.sleep(250); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// Determines if the team will shoot freethrows
	public void willShootFT(Team team, double ftAtt) {
		// If the team has enough FT stored up to shoot
		if (team.getFtStored() >= ftAtt) {
			for (int i = 0; i < ftAtt; i++) {
				isFTMade(team);
			}
		}
	}

	// Determines if a FT is made or missed
	public void isFTMade(Team team) {
		double ft = Math.random() * 100 + 1;

		if (ft < team.getFt()) {
			team.addToScore(1);
			System.out.println(team.getName() + " made a free throw!");
		} else {
			System.out.println(team.getName() + " missed a free throw!");
		}

		team.subFromFtStored();
	}
	
	public static void saveStatsToTextFile() throws IOException{
		//The directory and file name
		File teamStatsDir = new File("teamStats");
		File teamStats = new File("teamStats/teamStats.csv");
		
		//If directory does not exist, create the directory and the file
		if (!teamStatsDir.exists()) {
			teamStatsDir.mkdir();
			teamStats.createNewFile();
		}
		//Else if directory exists but the file does not, create the file.
		else if (!teamStats.exists()) {
			teamStats.createNewFile();
		}
		//Else the directory and file exist.
		else {
			FileReader fileReader = new FileReader(teamStats);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			//Getting the oldDate in the CSV file
			String dateInCSV = bufferedReader.readLine();
			LocalDate oldDate;
			try {
				oldDate = LocalDate.parse(dateInCSV);
			}
			catch (NullPointerException e) {
				oldDate = LocalDate.parse("0000-01-01");
			}
			
			//getting the count for the total of rows in the csv
			int count = 1;
			while (bufferedReader.readLine() != null) {
				count++;
			}

			//If date is older than a week OR if the file does not 353 rows (351 teams plus the two header lines), update file
			if (LocalDate.now().isAfter(oldDate.plusDays(7)) || count != 353) {
				//TODO UPDATE FILE
				//TODO Save the correct date
				
				//Creating the writers and variables
				PrintWriter pw = new PrintWriter(teamStats);
				StringBuilder sb = new StringBuilder();
				LocalDate localDate = LocalDate.now().minusDays(10);
				
				//Adding the date and the headers
				sb.append(localDate.toString()+"\n");
				sb.append("team_name,avgFG,avg3,oppFG,opp3,ftPP,ft,shotsFrom2,shotsFrom3,possPG,toRate\n");
				
				//Getting the team names in alphabetical order and converts it to an array
				List<String> teamNamesList = new ArrayList<>();
				Document doc = Jsoup.connect(STATS_NEEDED_LINKS[0]).get();
				for (Element row : doc.select("td > a")) {
					teamNamesList.add(row.html().toUpperCase());
				}
				teamNamesList.sort(String::compareToIgnoreCase);
				String[] teamNames = teamNamesList.toArray(new String[teamNamesList.size()]);
				
				//Now that the teams are sorted, let's go through each link and save the data
				double[][] stats = new double[351][STATS_NEEDED_LINKS.length];
				int cols; //Number of columns for each table
				for (int i = 0; i < STATS_NEEDED_LINKS.length; i++) {
					Document statDoc = Jsoup.connect(STATS_NEEDED_LINKS[i]).get(); // Getting the stat link
					cols = statDoc.select("tr").get(2).select("td").size(); // The number of columns in this table
					
					//Going through each row and saving the teams info while skipping the first header row
					int skipHeaderRow = 0;
					for (Element teams : statDoc.select("tr")) {
						if (skipHeaderRow == 0) {
							skipHeaderRow++;
							continue;
						}
						
						//Getting the team name and stat
						String teamName = teams.select("td").get(1).select("a").html();
						double stat = Double.parseDouble(teams.select("td").get(2).html().replace("%", ""));
						
						//Doing a binary search for the team name to pull the index it needs to store in
						int index = BinarySearch.search(teamName.toUpperCase(), teamNames);
						
						//Saving the stat
						stats[index][i] = stat;						
					}
					System.out.println("Finshed with a table");
				}
				
				//TODO stopped here. Now I have that stats[][] array populated and is parallel to teamsNames[].
				//TODO Now I need to write the information to the csv file
				System.out.println(stats.length);
				System.out.println(teamNames[1] + " " + stats[1][1]);
				
				pw.write(sb.toString());
				pw.close();
				
			}
			bufferedReader.close();
		}
	}

	// Gets the stats of the teams
	public void getStats(Team[] teams, Scanner sc) throws IOException {
		//TODO Will need to be rewritten to take from the file, or read from the website if the file is not made/updated
		//TODO Since the file will be updating in a background thread, if updating read from website.
		int cols = 0, // The number of columns in each of the stats table. Will be used in the for
						// loop directly below.
				teamsIndexCount = 0; // The index counter
		int[] teamsIndex = { -1, -1 }; // The index of the teams for each stats table. -1, -1 for default values

		// A loop to cycle through each of the links and pulls the needed values
		for (int i = 0; i < STATS_NEEDED_LINKS.length; i++) {
			// Setting the default value for variables
			teamsIndexCount = 0;
			teamsIndex[0] = -1;
			teamsIndex[1] = -1;

			// Getting the stat link
			Document doc = Jsoup.connect(STATS_NEEDED_LINKS[i]).get();
			cols = doc.select("tr").get(2).select("td").size(); // The number of columns in this table

			for (Element row : doc.select("td > a ")) {
				if (teamsIndex[0] != -1 && teamsIndex[1] != -1) {
					break; // Do not continue with the for loop because both indexes have been set.
				}

				// Checking if the name in the row matches to one of the names inputted by the
				// user
				if (row.html().toUpperCase().equals(teams[0].getName())) {
					teamsIndex[0] = teamsIndexCount;
				} else if (row.html().toUpperCase().equals(teams[1].getName())) {
					teamsIndex[1] = teamsIndexCount;
				}

				// Count will be used to hold the current index being tested
				teamsIndexCount++;
			}

			// Setting the values for each team
			switch (i) {
			case 0:
				for (int x = 0; x < 2; x++) {
					teams[x].setAvgFG(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 1:
				for (int x = 0; x < 2; x++) {
					teams[x].setAvg3(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 2:
				for (int x = 0; x < 2; x++) {
					teams[x].setOppFG(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 3:
				for (int x = 0; x < 2; x++) {
					teams[x].setOpp3(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 4:
				for (int x = 0; x < 2; x++) {
					teams[x].setFtPP(Double.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html()));
				}
				break;
			case 5:
				for (int x = 0; x < 2; x++) {
					teams[x].setFt(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 6:
				for (int x = 0; x < 2; x++) {
					teams[x].setShotsFrom2(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 7:
				for (int x = 0; x < 2; x++) {
					teams[x].setShotsFrom3(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			case 8:
				for (int x = 0; x < 2; x++) {
					teams[x].setPossPG(Double.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html()));
				}
				break;
			case 9:
				for (int x = 0; x < 2; x++) {
					teams[x].setToRate(Double
							.parseDouble(doc.select("td").get((teamsIndex[x] * cols) + 2).html().replace("%", "")));
				}
				break;
			}
		}
	}

	// Function that prints who won the tipoff
	public void printTipWinner(double tipoff, Team[] teams) {
		if (tipoff < 50) {
			this.setPossession(0);
			System.out.println(teams[0].getName() + " won the tip!\n");
		} else {
			this.setPossession(1);
			System.out.println(teams[1].getName() + " won the tip!\n");
		}
	}

	// Function that changes the possession of the game
	public void changePossession() {
		if (this.getPossession() == 0)
			this.setPossession(1);
		else
			this.setPossession(0);
	}

	// Displays the game score
	public void getGameScore(Team[] teams) {
		// Print out the score after every possession
		System.out.println(teams[0].getName() + " " + teams[0].getScore() + " - " + teams[1].getScore() + " "
				+ teams[1].getName() + "\n");
	}

	// Getters and Setters
	public int getTotalPoss() {
		return this.totalPoss;
	}

	public void setTotalPoss(int totalPoss) {
		this.totalPoss = totalPoss;
	}

	public int getPeriodPoss(int index) {
		return this.periodPoss.get(index);
	}

	public void setPeriodPoss(int index, int periodPoss) {
		this.periodPoss.set(index, periodPoss);
	}

	public void appendPeriodPoss(int periodPoss) {
		this.periodPoss.add(periodPoss);
	}

	public int getHalf() {
		return this.half;
	}

	public void setHalf(int half) {
		this.half = half;
	}

	public double getTimeLeft() {
		return this.timeLeft;
	}

	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}

	public double getTimeOfPoss(int index) {
		return this.timeOfPoss.get(index);
	}

	public void setTimeOfPoss(int index, double timeOfPoss) {
		this.timeOfPoss.set(index, timeOfPoss);
	}

	public void appendTimeOfPoss(double timeOfPoss) {
		this.timeOfPoss.add(timeOfPoss);
	}

	public int getPossession() {
		return this.possession;
	}

	public void setPossession(int possession) {
		this.possession = possession;
	}
}

class Team {
	private double avgFG, // Average FG %
			avg3, // Avg 3 pt %
			matchupAvgFg, // (team1 avgFG+team2 oppFG)/2
			matchupAvg3, // (team1 avg3+team2 opp3)/2
			oppFG, // Avg opp fg %
			opp3, // Avg opp 3 %
			ftPP, // Avg freethrows taken per possession
			ft, // Avg ft %
			ftStored, // ftPP added each possession. On a miss, if ftStored = 2, shoot free throws. On
						// a make, if ftStored=1 shoot free throw
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