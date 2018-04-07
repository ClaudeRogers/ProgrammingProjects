/*
*    Claude Rogers III
*    April 11, 2015
*    This program is a rock, paper, scissors game.
*    The user will be able to type in their input. Computers hand options are stored in an array, and then random selects an index. [0] = Rock, [1] = Paper, [2] Scissors
*    Program will then calculate a winner and display the winner.
*
*    Will also write a .txt document that will store how many times the PC has won, and how many times the user has won.
*    Will ask the user to play again.
*/

import java.io.*;
import java.util.*;

public class FinalExam {
    public static void main (String[] args){
        //Declaring the variables used in this program
        Scanner input = new Scanner(System.in); //To allow for user input.
        char[] computerHand = {'R', 'P', 'S'}; //The char array for computer's hand.
        int index = 0, //The index that will select a random char from computerHand array.
            cpuWins = 0, //How many wins during the current session by the CPU.
            totalCPUwins = 0, //How many wins over all sessions, not just the current session.
            userWins = 0, //How many wins during the current session by the User.
            totalUserWins = 0, //How many wins over all sessions, not just the current session.
            tieGames = 0, //How many ties during the current session.
            totalTieGames = 0, //How many ties over all sessions, not just the current session.
            errors = 0; //The amount of errors in the program.
        String userHandInput = "", //The string the user will type for their hand.
            outputString = "\n", //The string that will tell the winner.
            playAgainInput = ""; //The string the user will input to play again.
        char userHand, //The first char of the user's string.
            playAgain; //This will allow the user to input whether they would like to play again.
        File winsFile = new File("wins.txt"); //The file that will hold the records of wins, losses, and ties.

        do {
        	//The loop that allows the user to play the game.
            outputString = "\n"; //Resetting the output string for the start of the loop.
            
            System.out.println("\nWelcome! To play, please type either Rock, Paper, or Scissors!");
            userHandInput = input.nextLine().toLowerCase(); //Sets the string to all lowercase to match the chars.
            userHand = userHandInput.charAt(0); //Taking the first char of the input string, this way even if Rock, Paper, or Scissors is misspelled, as long as the first character is correct the input will still count.

            index = (int)(Math.random()*10)%3; //Finding a random number between 0 and 2, this is the index used for the computerHand
            
            //Calculating who wins.
            if (userHand == 'r'){
            	//If user selects rock
                if (computerHand[index] == 'R'){
                	//If computer selects rock. Tie game. Rock ties Rock.
                    outputString += "Tie game! Computer played Rock too! Please try again!";
                    tieGames++;
                }
                else if (computerHand[index] == 'P'){
                	//If computer selects paper. CPU Wins. Rock loses to Paper.
                    outputString += "You lose! Computer played Paper! Better luck next time!";
                    cpuWins++;
                }
                else if (computerHand[index] == 'S'){
                	//If computer selects scissors. User Wins. Rock beats Scissors.
                    outputString += "You win! Computer played Scissors! Congratulations!";
                    userWins++;
                }
            }
            else if (userHand == 's'){
            	//If user selects scissors.
                if (computerHand[index] == 'R'){
                	//If computer selects rock. CPU Wins. Scissors loses to Rock.
                    outputString += "You lose! Computer played Rock! Better luck next time!";
                    cpuWins++;
                }
                else if (computerHand[index] == 'P'){
                	//If computer selects paper. User Wins. Scissors beats Paper.
                    outputString += "You win! Computer played Paper! Congratulations!";
                    userWins++;
                }
                else if (computerHand[index] == 'S'){
                	//If computer selects scissors. Tie game. Scissors ties Scissors.
                    outputString += "Tie game! Computer played Scissors too! Please try again!";
                    tieGames++;
                }
            }
            else if (userHand == 'p'){
            	//If user selects paper.
                if (computerHand[index] == 'R'){
                	//If computer selects rock. User Wins. Paper beats Rock.
                    outputString += "You win! Computer played Rock! Congratulations!";
                    userWins++;
                }
                else if (computerHand[index] == 'P'){
                	//If computer selects paper. Tie game. Paper ties Paper.
                    outputString += "Tie game! Computer played Paper too! Please try again!";
                    tieGames++;
                }
                else if (computerHand[index] == 'S'){
                	//If computer selects scissors. CPU Wins. Paper loses to Scissors.
                    outputString += "You lose! Computer played Scissors! Better luck next time!";
                    cpuWins++;
                }
            }

            outputString += "\nWould you like to play again?\nYes or No?";
            System.out.println(outputString);
            playAgainInput = input.nextLine().toLowerCase(); //Taking the input by the user, making it lowercase. 
            playAgain = playAgainInput.charAt(0); //Taking the char at index 0 of the input string.
        } while (playAgain == 'y'); //Continue loop as long as playAgain char is equal to y.
        
        //If the file already exists, and if the file path is not a directory.
        if (winsFile.exists() && !winsFile.isDirectory()){
        	//Try inputting information from the file, catch FileNotFoundException
	        try {
	            Scanner inputFile = new Scanner(winsFile); //New Scanner, the winsFile.
	            //Inputting the data from the file. 
	            inputFile.nextLine();
	            totalCPUwins = inputFile.nextInt(); //CPU wins recorded in the winsFile.
	            inputFile.nextLine();
	            inputFile.nextLine();
	            totalUserWins = inputFile.nextInt(); //User wins recorded in the winsFile.
	            inputFile.nextLine();
	            inputFile.nextLine();
	            totalTieGames = inputFile.nextInt(); //Tie games recorded in the winsFile.
	            inputFile.close(); //Closing the scanner.
	
	            //Calculating the new total wins/loses/ties.
	            totalCPUwins += cpuWins;
	            totalUserWins += userWins;
	            totalTieGames += tieGames;
	        } catch (FileNotFoundException e1) {
	        	System.out.println("ERROR: wins.txt file not found!");
	        	errors++;
	        }
        }
        else {
        	//Adding the wins of the current gameplay sessions to the total wins variables.
            totalCPUwins += cpuWins;
            totalUserWins += userWins;
            totalTieGames += tieGames;
        }
        try {
        	//A new PrintWriter to write to the winsFile.
            PrintWriter output = new PrintWriter(winsFile);
            output.println("cpuWins:");
            output.println(totalCPUwins); //CPU Wins.
            output.println("userWins");
            output.println(totalUserWins); //User Wins.
            output.println("tieGames:");
            output.println(totalTieGames); //Tie games.
            output.close();
        } catch (FileNotFoundException e) {
        	System.out.println("ERROR: wins.txt file not found!");
        	errors++;
        }
        //If no errors in the opening and writing of the file.
	    if (errors == 0);{    
	        System.out.println("Thank you for playing! Come again!");
	        System.out.println("\nPC won " + cpuWins + " games this session.");
	        System.out.println("User won " + userWins + " games this session.");
	        System.out.println("The PC and User tied " + tieGames + " games this session.");
	
	        System.out.println("\nPC won " + totalCPUwins + " games all time.");
	        System.out.println("User won " + totalUserWins + " games all time.");
	        System.out.println("The PC and User tied " + totalTieGames + " games all time.");
	    }
    }
}

