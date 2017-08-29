package PP20170809BasketballSim;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*Basketball simulation application
*User inputs two teams
*The app then pulls the data down for each team from teamrankings.com/ncaa-basketball/stat
*
*The app then runs through a game displaying each possession
* */

//TODO Rebounding is implemented for missed shots. Still need to implement rebounding for miss FTs
public class Main {
	public static void main(String[] args) throws IOException {
		//Asking the user how many games the teams will play
		Scanner sc = new Scanner(System.in);
		int games = 0;
		do {
			System.out.print("How many games will these teams play?  ");
			try {
				games = sc.nextInt();
			}
			catch (InputMismatchException e) {
				sc.nextLine();
			}
			if (games < 1) {
				System.out.println("\nPlease enter a positive whole number for the amount of games these teams will play.");
			}
		} while (games < 1);
		
		//Creating the game
		Game game = new Game(games);
		
		//Printing out the percentage of wins for each team
		if (games > 1) {
			System.out.println(game.teams[0].getName() + " wins " + String.format("%.2f", ((double)game.getTeams0Wins()/games)*100) + "% of the time.");
			System.out.println(game.teams[1].getName() + " wins " + String.format("%.2f", ((double)game.getTeams1Wins()/games)*100) + "% of the time.");
		}
		
		sc.close();
	}
}