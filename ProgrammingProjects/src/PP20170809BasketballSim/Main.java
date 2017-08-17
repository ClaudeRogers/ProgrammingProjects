package PP20170809BasketballSim;
import java.io.IOException;

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

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		Game game = new Game();
	}
}