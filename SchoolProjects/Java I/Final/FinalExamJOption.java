/*
*    Claude Rogers III
*    April 12, 2015
*    This program is a rock, paper, scissors game.
*    The user will be able to select their option. Computer's hand is randomly generated.
*    Program will then calculate a winner and display the winner.
*
*    Will also write a .txt document that will store how many times the PC has won, and how many times the user has won.
*    Will ask the user to play again.
*/

import java.awt.Dimension;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class FinalExamJOption {
    public static void main (String[] args){
    	//Declaring the variables used in this program.
    	String[] StartingChoices = {"Rock", "Paper", "Scissors"}, //The first three choices offered in the program.
    		PlayAgainChoices = {"Rock", "Paper", "Scissors", "End Game"}; //The same three options, but this time with the End Game option.
    	String computerHand = "", //Will hold the value for the Computer's hand.
        	userHand = "", //Will hold the value for the User's hand.
        	outputString = ""; //Wild hold the string that is output by the program
    	int chosenOption = 0, //The option picked by the user.
    			cpuWins = 0, //CPU wins for the current session.
    			totalCPUwins = 0, //The total amount of CPU wins.
    			userWins = 0, //User wins for the current session.
    			totalUserWins = 0, //The total amount of User wins.
    			tieGames = 0, //Tie games for the current session.
    			totalTieGames = 0, //The total amount of Tie games.
    			errors = 0; //Amount of errors in program.
    	File winsFile = new File("winsJOption.txt"); //The file that will hold the records of wins, losses, and ties.
    	UIManager.put("OptionPane.minimumSize",new Dimension(500,0)); //Sets the width of the JOptionPane. I wanted the hieght to be flexible, but the width to be the same and uniform.
    	
    	chosenOption = JOptionPane.showOptionDialog(null,"Please select one", "Rock, Paper, Scissors Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, StartingChoices, StartingChoices[0]);
    	do { //The loop that will ask the user to pick an option, and aslong as the option is not of the index [3], or the option "End Game" the loop will continue
    		computerHand = StartingChoices[(int)(Math.random()*10)%3]; //Finding a random number between 0 and 2, selecting the hand of the computer
            userHand = StartingChoices[chosenOption];
            
            //Calculating who wins.
            if (userHand == "Rock"){
            	//If user selects rock
                if (computerHand == "Rock"){
                	//If computer selects rock. Tie game. Rock ties Rock.
                    outputString = "Tie game! Computer played Rock too! Please try again!";
                    tieGames++;
                }
                else if (computerHand == "Paper"){
                	//If computer selects paper. CPU Wins. Rock loses to Paper.
                    outputString = "You lose! Computer played Paper! Better luck next time!";
                    cpuWins++;
                }
                else if (computerHand == "Scissors"){
                	//If computer selects scissors. User Wins. Rock beats Scissors.
                    outputString = "You win! Computer played Scissors! Congratulations!";
                    userWins++;
                }
            }
            else if (userHand == "Scissors"){
            	//If user selects scissors.
                if (computerHand == "Rock"){
                	//If computer selects rock. CPU Wins. Scissors loses to Rock.
                    outputString = "You lose! Computer played Rock! Better luck next time!";
                    cpuWins++;
                }
                else if (computerHand == "Paper"){
                	//If computer selects paper. User Wins. Scissors beats Paper.
                    outputString = "You win! Computer played Paper! Congratulations!";
                    userWins++;
                }
                else if (computerHand == "Scissors"){
                	//If computer selects scissors. Tie game. Scissors ties Scissors.
                    outputString = "Tie game! Computer played Scissors too! Please try again!";
                    tieGames++;
                }
            }
            else if (userHand == "Paper"){
            	//If user selects paper.
                if (computerHand == "Rock"){
                	//If computer selects rock. User Wins. Paper beats Rock.
                    outputString = "You win! Computer played Rock! Congratulations!";
                    userWins++;
                }
                else if (computerHand == "Paper"){
                	//If computer selects paper. Tie game. Paper ties Paper.
                    outputString = "Tie game! Computer played Paper too! Please try again!";
                    tieGames++;
                }
                else if (computerHand == "Scissors"){
                	//If computer selects scissors. CPU Wins. Paper loses to Scissors.
                    outputString = "You lose! Computer played Scissors! Better luck next time!";
                    cpuWins++;
                }
            }
    		
            outputString += "\n\nWould you like to play again?";
            
    		chosenOption = JOptionPane.showOptionDialog(null,outputString, "Rock, Paper, Scissors Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, PlayAgainChoices, PlayAgainChoices[0]);
    	} while (chosenOption != 3);
    	
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
	        	outputString = "ERROR: winsJOption.txt file not found!";
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
        	outputString = "ERROR: winsJOption.txt file not found!";
        	errors++;
        }
    	//If there are errors, print those errors.
        if (errors != 0){
        	JOptionPane.showMessageDialog(null, outputString, "Rock, Paper, Scissors Game", JOptionPane.ERROR_MESSAGE);
        }
        
        //If no errors in the tries and catches.
        if (errors == 0){
	    	outputString = "Stats for the current session:"
	    			+ "\nCPU Wins: " + cpuWins
	    			+ "\nUser Wins: " + userWins
	    			+ "\nTie Games: " + tieGames
	    			+ "\n\nAll time stats:"
	    			+ "\nTotal CPU Wins: " + totalCPUwins
	    			+ "\nTotal User Wins: " + totalUserWins
	    			+ "\nTotal Tie Games: " + totalTieGames;
	    	
	    	JOptionPane.showMessageDialog(null, outputString, "Rock, Paper, Scissors Game", JOptionPane.PLAIN_MESSAGE);
        }	
    }
}

