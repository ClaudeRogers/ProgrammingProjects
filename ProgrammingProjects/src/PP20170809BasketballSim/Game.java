package PP20170809BasketballSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utilities.BinarySearch;
public class Game {
	private int totalPoss = 0, 	// Total possessions of the game
			half = 0, 			// 0 = first half, 1 = second half, 2 = OT
			possession = 0, 	// 0 = team1, 1 = team2
			otCount = 0;		// Count of OT periods

	// Array List to hold the amount of possession for each period
	private int[] periodPoss = new int[3];
	
	private double timeLeft = 1200.00; // 1200 = 20:00, 300 = 5:00
	
	private double[] timeOfPoss = new double[3]; // Average time of possession for each period
	
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
	Team[] teams = new Team[2];
	Team teamWPoss;
	
	//The directory and file name
	private static final File TEAM_STATE_DIR = new File("teamStats");
	private static final File TEAM_STATS = new File("teamStats/teamStats.csv");

	Game() throws IOException {
		saveStatsToTextFile();
		
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 2; i++) {
			teams[i] = new Team(); // Initializing each team
		}

		// Getting the teams's names from user input, validate name, and then set name
		for (int i = 0; i < 2; i++) {
			System.out.print("Please enter Team "+(i+1)+"'s name:  ");
			String tempName = sc.nextLine().trim().toUpperCase();
			
			int index = Team.validateName(tempName);
			
			//While validation of tempName fails, ask again.
			while (Team.validateName(tempName) == -1) {
				System.out.println("Team name not found on www.teamrankings.com/ncb/\nPlease visit the site to find the team's name.");
				System.out.print("Please enter Team "+(i+1)+"'s name:  ");
				tempName = sc.nextLine().trim().toUpperCase();
				index = Team.validateName(tempName);
			}
			
			//When validation passes, setName
			teams[i].setName(tempName);
			//Set the team index of the csv file
			teams[i].setIndex(index);
		}
		sc.close();

		System.out.println("\nTonight's game is " + teams[0].getName() + " vs " + teams[1].getName() + "\n");

		getStats(); // Getting the stats

		// Calculating the total possessions in the game. Rounding down as a team cannot have less than one possession.
		setTotalPoss((int) Math.floor(teams[0].getPossPG() + teams[1].getPossPG()));

		//Getting total possessions for first and second half.
		//Divide by 2 for first to round down if odd number of total possessions.
		//Subtract first half possessions by total possessions to get second half.
		setPeriodPoss((getTotalPoss() / 2), 0);
		setPeriodPoss((getTotalPoss() - getPeriodPoss(0)), 1);
		
		// Calculating the time of possession for each half.
		setTimeOfPoss((1200/getPeriodPoss(0)), 0);
		setTimeOfPoss((1200/getPeriodPoss(1)), 1);
		
		//Calculating the periodPoss using the second half avg timeOfPoss for OT
		setPeriodPoss((int)(300/getTimeOfPoss(1)),2);
		setTimeOfPoss(getTimeOfPoss(1), 2);
		
		// Calculates each teams FG% for the game
		// Take team1 offense FG% + team2 defense FG% and divide by 2 to find average
		teams[0].setMatchupAvgFg((teams[0].getAvgFG() + teams[1].getOppFG()) / 2);
		teams[1].setMatchupAvgFg((teams[1].getAvgFG() + teams[0].getOppFG()) / 2);
		teams[0].setMatchupAvg3((teams[0].getAvg3() + teams[1].getOpp3()) / 2);
		teams[1].setMatchupAvg3((teams[1].getAvg3() + teams[0].getOpp3()) / 2);

		// Play a game
		playGame();
	}

	// Function to play the whole game
	public void playGame() {
		String firSec = "first";
		// Calculating who wins the tipoff and gets the first possession
		double tipoff = Math.random() * 100 + 1;

		// Loops through both halves
		for (int i = 0; i < 2; i++) {
			setHalf(i); // Sets the half

			// Prints which half is starting.
			if (getHalf() == 1)
				firSec = "second";
			System.out.println("The " + firSec + " half is starting!");

			// Printing out who won the tip if it is the first half
			if (getHalf() == 0) {
				printTipWinner(tipoff);
			}

			// Call the method to play a half
			playPeriod();
		}

		// If game is tied after the first two halves
		if (teams[0].getScore() == teams[1].getScore()) {
			System.out.println("End of Regulation! Time to go to overtime!");
		}

		// Run an overtime period
		while (teams[0].getScore() == teams[1].getScore()) {
			setHalf(2);
			addOneToOtCounter();
			System.out.println("Overtime period #" + getOtCount() + " is starting!");
			playPeriod();
		}

		//Prints out the winner
		if (teams[0].getScore() > teams[1].getScore()) {
			System.out.println(teams[0].getName() + " wins!");
		} else {
			System.out.println(teams[1].getName() + " wins!");
		}
	}

	public void playPeriod() {
		// Variables used to hold a random number to determine if the possession is
		// turned over, or what shot is attempted, and whether the shot goes in
		double turnover = 0, fg = 0, twoOrThree = 0;

		// Sets the time left for each period. 1200 = 20:00, 300 = 5:00
		if (getHalf() < 2)
			setTimeLeft(1200.00);
		else {
			setTimeLeft(300.00);

			// Calculating who wins the tipoff and gets the first possession
			double tipoff = Math.random() * 100 + 1;
			printTipWinner(tipoff);
		}

		// Looping through each possession
		for (int i = 0; i < getPeriodPoss(getHalf()); i++) {
			// Displaying the time left
			setTimeLeft(getTimeLeft() - getTimeOfPoss(getHalf()));
			System.out.println(
					(int) (getTimeLeft() / 60) + ":" + String.format("%02d", (int) (getTimeLeft() % 60)));

			// Calculates if possession results in turnover.
			turnover = Math.random() * 100 + 1;

			// If a turnover occurs. if the random turnover number is less than the TO rate
			// of the team
			if (turnover < getTeamWPoss().getToRate()) {
				System.out.println(getTeamWPossName() + " turned the ball over!");

				// Get game score and change possession
				getGameScore(teams);
				changePossession();
				continue;
			}

			// Adding ftPP to ftStored
			getTeamWPoss().addToFtStored();

			// Calculating what shot is attempted and whether it is made
			twoOrThree = Math.random() * 100 + 1;// Calculating if a 2 or 3 is shot
			fg = Math.random() * 100 + 1; // Calculating if a FG is made
			if (twoOrThree > getTeamWPoss().getShotsFrom3()) {
				if (fg < getTeamWPoss().getAvgFG()) {
					getTeamWPoss().addToScore(2);
					System.out.println(getTeamWPossName() + " scores a two pointer!");
					willShootFT(1);
				} else {
					System.out.println(getTeamWPossName() + " misses a two pointer!");
					willShootFT(2);
				}
			} else {
				if (fg < getTeamWPoss().getAvg3()) {
					getTeamWPoss().addToScore(3);
					System.out.println(getTeamWPossName() + " scores a three pointer!");
					willShootFT(1);
				} else {
					System.out.println(getTeamWPossName() + " misses a three pointer!");
					willShootFT(3);
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
	public void willShootFT(double ftAtt) {
		// If the team has enough FT stored up to shoot
		if (getTeamWPoss().getFtStored() >= ftAtt) {
			for (int i = 0; i < ftAtt; i++) {
				isFTMade();
			}
		}
	}

	// Determines if a FT is made or missed
	public void isFTMade() {
		double ft = Math.random() * 100 + 1;
		
		if (ft < getTeamWPoss().getFt()) {
			getTeamWPoss().addToScore(1);
			System.out.println(getTeamWPossName() + " made a free throw!");
		} else {
			System.out.println(getTeamWPossName() + " missed a free throw!");
		}

		getTeamWPoss().subFromFtStored();
	}
	
	public static void saveStatsToTextFile() throws IOException{
		//If directory does not exist, create the directory and the file
		if (!TEAM_STATE_DIR.exists()) {
			TEAM_STATE_DIR.mkdir();
			TEAM_STATS.createNewFile();
		}
		//Else if directory exists but the file does not, create the file.
		else if (!TEAM_STATS.exists()) {
			TEAM_STATS.createNewFile();
		}
		//Else the directory and file exist.
		else {
			FileReader fileReader = new FileReader(TEAM_STATS);
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
				System.out.println("Please wait a couple of seconds while this application creates a .CSV file will all of the teams' data.");
				
				//Creating the writers and variables
				PrintWriter pw = new PrintWriter(TEAM_STATS);
				StringBuilder sb = new StringBuilder();
				LocalDate localDate = LocalDate.now();
				
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
				//TODO Save possible names to text doc, then have teams.POSSIBLE_TEAM_NAMES pull from the text doc.
				//TODO updat names doc when update stats doc
//				Team.POSSIBLE_TEAM_NAMES = teamNamesList.toArray(new String[teamNamesList.size()]);
				
				//Now that the teams are sorted, let's go through each link and save the data
				double[][] stats = new double[351][STATS_NEEDED_LINKS.length];
				for (int i = 0; i < STATS_NEEDED_LINKS.length; i++) {
					Document statDoc = Jsoup.connect(STATS_NEEDED_LINKS[i]).get(); // Getting the stat link
					
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
						int index = BinarySearch.search(teamName.toUpperCase(), Team.POSSIBLE_TEAM_NAMES);
						
						//Saving the stat
						stats[index][i] = stat;						
					}
				}
				
				//Writing the teams and stats to the CSV file
				for (int i = 0; i < stats.length; i++) {
					sb.append(Team.POSSIBLE_TEAM_NAMES[i]+",");
					for (int x = 0; x < stats[0].length; x++) {
						sb.append(stats[i][x] + ",");
					}
					sb.deleteCharAt(sb.lastIndexOf(","));
					sb.append("\n");
				}
				
				//Printing the stats to the CSV file
				pw.write(sb.toString());
				pw.close();
				System.out.println("Finished updating the local .CSV file!\n");
			}
			bufferedReader.close();
		}
	}

	// Gets the stats of the teams
	public void getStats() throws IOException {
		FileReader fileReader = new FileReader(TEAM_STATS);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int count = 0, //Will hold the line number that we are currently reading
				team0set = 0, //0 for information not yet set, 1 for information found and set
				team1set = 0; //0 for information not yet set, 1 for information found and set
		String line; //Will hold the line of text on the current line
		String[] lineArray; //Will hold the split line
		
		while (((line = bufferedReader.readLine()) != null) && (team0set == 0 || team1set == 0)) {
			count++;
			
			//Checking if the current line is either team's information
			if (count == (teams[0].getIndex()+3)) {
				lineArray = line.split(","); //Creating an array of the values for the team
				teams[0].setAvgFG(Double.parseDouble(lineArray[1]));
				teams[0].setAvg3(Double.parseDouble(lineArray[2]));
				teams[0].setOppFG(Double.parseDouble(lineArray[3]));
				teams[0].setOpp3(Double.parseDouble(lineArray[4]));
				teams[0].setFtPP(Double.parseDouble(lineArray[5]));
				teams[0].setFt(Double.parseDouble(lineArray[6]));
				teams[0].setShotsFrom2(Double.parseDouble(lineArray[7]));
				teams[0].setShotsFrom3(Double.parseDouble(lineArray[8]));
				teams[0].setPossPG(Double.parseDouble(lineArray[9]));
				teams[0].setToRate(Double.parseDouble(lineArray[10]));
				team0set = 1;
			}
			else if (count == (teams[1].getIndex()+3)) {
				lineArray = line.split(","); //Creating an array of the values for the team
				teams[1].setAvgFG(Double.parseDouble(lineArray[1]));
				teams[1].setAvg3(Double.parseDouble(lineArray[2]));
				teams[1].setOppFG(Double.parseDouble(lineArray[3]));
				teams[1].setOpp3(Double.parseDouble(lineArray[4]));
				teams[1].setFtPP(Double.parseDouble(lineArray[5]));
				teams[1].setFt(Double.parseDouble(lineArray[6]));
				teams[1].setShotsFrom2(Double.parseDouble(lineArray[7]));
				teams[1].setShotsFrom3(Double.parseDouble(lineArray[8]));
				teams[1].setPossPG(Double.parseDouble(lineArray[9]));
				teams[1].setToRate(Double.parseDouble(lineArray[10]));
				team1set = 1;
			}
			
			continue;
		}
		
		bufferedReader.close();
		
		//These two lines will check the print out the stats of each team so I can manually check with .CSV file to make sure it's pulling currectly
//		System.out.println(teams[0].getName()+"\n"+teams[0].getAvgFG()+"\n"+teams[0].getAvg3()+"\n"+teams[0].getOppFG()+"\n"+teams[0].getOpp3()+"\n"+teams[0].getFtPP()+"\n"+teams[0].getFt()+"\n"+teams[0].getShotsFrom2()+"\n"+teams[0].getShotsFrom3()+"\n"+teams[0].getPossPG()+"\n"+teams[0].getToRate());
//		System.out.println(teams[1].getName()+"\n"+teams[1].getAvgFG()+"\n"+teams[1].getAvg3()+"\n"+teams[1].getOppFG()+"\n"+teams[1].getOpp3()+"\n"+teams[1].getFtPP()+"\n"+teams[1].getFt()+"\n"+teams[1].getShotsFrom2()+"\n"+teams[1].getShotsFrom3()+"\n"+teams[1].getPossPG()+"\n"+teams[1].getToRate());

}
	
	public void addOneToOtCounter() {
		otCount++;
	}

	// Function that prints who won the tipoff
	public void printTipWinner(double tipoff) {
		if (tipoff < 50) setPossession(0);
		else setPossession(1);
		System.out.println(getTeamWPossName() + " won the tip!\n");
	}

	// Function that changes the possession of the game
	public void changePossession() {
		if (getPossession() == 0) setPossession(1);
		else setPossession(0);
	}

	// Displays the game score
	public void getGameScore(Team[] teams) {
		// Print out the score after every possession
		System.out.println(teams[0].getName() + " " + teams[0].getScore() + " - " + teams[1].getScore() + " "
				+ teams[1].getName() + "\n");
	}

	// Getters and Setters
	public int getTotalPoss() {
		return totalPoss;
	}

	public void setTotalPoss(int totalPoss) {
		this.totalPoss = totalPoss;
	}

	public int getPeriodPoss(int index) {
		return periodPoss[index];
	}

	public void setPeriodPoss(int periodPoss, int index) {
		this.periodPoss[index] = periodPoss;
	}

	public int getHalf() {
		return half;
	}

	public void setHalf(int half) {
		this.half = half;
	}

	public double getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}

	public double getTimeOfPoss(int index) {
		return timeOfPoss[index];
	}

	public void setTimeOfPoss(double timeOfPoss, int index) {
		this.timeOfPoss[index] = timeOfPoss;
	}

	public int getPossession() {
		return possession;
	}

	public void setPossession(int possession) {
		this.possession = possession;
		setTeamWPoss(teams[getPossession()]);
	}
	
	public Team getTeamWPoss () {
		return teamWPoss;
	}
	
	public void setTeamWPoss(Team team) {
		teamWPoss = team;
	}
	
	public String getTeamWPossName() {
		return getTeamWPoss().getName();
	}
	
	public int getOtCount() {
		return otCount;
	}
	
	public void setOtCount(int otCount) {
		this.otCount = otCount;
	}
	
	public int[] getPeriodPoss() {
		return periodPoss;
	}

	public void setPeriodPoss(int[] periodPoss) {
		this.periodPoss = periodPoss;
	}

	public double[] getTimeOfPoss() {
		return timeOfPoss;
	}

	public void setTimeOfPoss(double[] timeOfPoss) {
		this.timeOfPoss = timeOfPoss;
	}

	public Team[] getTeams() {
		return teams;
	}

	public void setTeams(Team[] teams) {
		this.teams = teams;
	}
}

