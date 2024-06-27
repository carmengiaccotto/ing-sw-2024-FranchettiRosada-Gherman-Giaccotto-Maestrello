/**
 * This package contains the classes related to the Text User Interface (TUI) of the application.
 */
package it.polimi.ingsw.View.TUI;

// Importing necessary classes and packages
import it.polimi.ingsw.Model.Cards.*;

import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass;
import it.polimi.ingsw.View.TUI.TUIUtilis.GraphicUsage;
import it.polimi.ingsw.View.TUI.TUIUtilis.TUIComponents;
import it.polimi.ingsw.View.UserInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The TUI class implements the UserInterface and Serializable interfaces.
 * It provides the functionality for a text-based user interface for the application.
 */
public class TUI implements UserInterface, Serializable {
    private final ReentrantLock lock = new ReentrantLock();
    Scanner scanner = new Scanner(System.in);
    private static final String bold = "\033[1m";
    private static final String reset = "\033[0m";
    private static final String ANSIreset = "\u001B[0m";

    /**
     * This method prompts the user to choose where to draw a card from.
     * The user can choose to draw from the gold deck, resource deck, or one of the four available cards on the playground.
     * The method will keep asking until a valid option is chosen.
     *
     * @return A string representing the chosen deck or card to draw from.
     */
    @Override
    public String chooseCardToDraw() {
        String draw;
        do {
            System.out.println("Do you want to draw a card from a deck or from the Board?\n [1]Board \n [2]Deck \n >>");
            draw = scanner.next().toUpperCase();
            if (draw.equals("1")) {
                System.out.println("Which card would you like to draw?\n [1]Resource Card1 \n [2]Resource Card2 \n [3]Gold Card1 \n [4]Gold Card2 \n >>");
                draw = scanner.next().toUpperCase();
                if (draw.equals("1"))
                    draw = "RESOURCE-CARD1";
                if (draw.equals("2"))
                    draw = "RESOURCE-CARD2";
                if (draw.equals("3"))
                    draw = "GOLD-CARD1";
                if (draw.equals("4"))
                    draw = "GOLD-CARD2";
            } else {
                System.out.println("Which deck would you like to draw from?\n [1]Resource Deck \n [2]Gold Deck \n >>");
                draw = scanner.next().toUpperCase();
                if (draw.equals("1"))
                    draw = "RESOURCE-DECK";
                if (draw.equals("2"))
                    draw = "GOLD-DECK";
            }
            if (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2")) {
                System.out.println("Invalid option! Please choose a valid option!");
            }
        } while (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2"));
        return draw;
    }

    /**
     * This method prompts the user to choose a card to play from their hand.
     * The user can choose a card by entering the corresponding number (1, 2, or 3).
     * The method will keep asking until a valid option is chosen.
     *
     * @param cardInHand An ArrayList of PlayCard objects representing the cards in the user's hand.
     * @return An integer representing the index of the chosen card in the list.
     */
    @Override
    public int chooseCardToPlay(ArrayList<PlayCard> cardInHand) {
        System.out.println("Which card do you want to play? [1/2/3]");
        int c = Integer.parseInt(scanner.next());
        while ((c != 1) && (c != 2) && (c != 3)) {
            System.out.println("Choose a valid card! [1/2/3]");
            c = Integer.parseInt(scanner.next());
        }
        return c;
    }

    /**
     * This method displays the initial card to the user.
     * It creates a 2D array to represent the front and back of the card.
     * The front and back of the card are printed using the printCard method from the DesignSupportClass.
     * The method then prints the 2D array to the console, showing the user their initial card.
     *
     * @param card An InitialCard object representing the initial card of the user.
     */
    public void showInitialCard(InitialCard card) {// todo modify using string builder
        System.out.println("This is your initial card: ");
        String[][] frontBack = new String[11][70];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 70; j++) {
                frontBack[i][j] = " ";
            }
        }
        frontBack[0][10] = "FRONT";
        frontBack[0][40] = "BACK";
        DesignSupportClass.printCard(frontBack, card.getFront(), 0, 0, 10, 34);
        DesignSupportClass.printCard(frontBack, card.getBack(), 0, 36, 10, 34);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 70; j++) {
                System.out.print(frontBack[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * This method prompts the user to choose a side of the card to play.
     * The user can choose either the "FRONT" or "BACK" side.
     * The method will keep asking until a valid side is chosen.
     *
     * @return A string representing the chosen side. It can be either "FRONT" or "BACK".
     */
    @Override
    public String chooseSide() {
        System.out.println("Which side of the card do you want to play? [FRONT/BACK]");
        String side = scanner.next().toUpperCase();
        while (!side.equals("FRONT") && !side.equals("BACK")) {
            System.out.println("Please insert a valid side: [FRONT/BACK]");
            side = scanner.next().toUpperCase();
        }
        return side;
    }

    /**
     * This method prompts the user to choose a position on the play area to place a card.
     * The user is asked to input the row and column numbers for the desired position.
     * The method will keep asking until valid row and column numbers are provided.
     *
     * @param playArea A PlayArea object representing the current state of the play area.
     * @return A Pair object containing the chosen row and column numbers.
     */
    @Override
    public Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea) {
        System.out.println("Choose the row and column in which you want to place the card: [row] [column]");
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        return new Pair<>(row, column);
    }


    /**
     * This method prompts the user to input their nickname.
     * The user's input is then returned as a string.
     *
     * @return A string representing the user's chosen nickname.
     */
    @Override
    public String selectNickName() {
        System.out.println("Insert your Nickname: ");
        return scanner.next();
    }

    /**
     * This method prompts the user to either create a new game or join an existing one.
     * The user can choose to create a new game by entering 1 or join an existing game by entering 2.
     * The method will keep asking until a valid option is chosen.
     *
     * @return An integer representing the user's choice. 1 for creating a new game, 2 for joining an existing game.
     */
    @Override
    public int createOrJoin() {
        System.out.println("Do you want to create a new game or join an existing one? ");
        System.out.println(" 1. Create new Game ");
        System.out.println(" 2. Join existing one ");
        System.out.println(">>");
        return scanner.nextInt();

    }

    /**
     * This method prompts the user to select the maximum number of players for the match.
     * The user can choose a number between 2 and 4.
     * The method will keep asking until a valid number is chosen.
     *
     * @return An integer representing the maximum number of players chosen by the user.
     */
    @Override
    public int MaxNumPlayers() {
        System.out.println("How Many Players do you want to have for this match? Please select a Number between 2 and 4!");
        return scanner.nextInt();
    }

    /**
     * This method displays the available games that the user can join.
     * For each game, it shows the game number, the number of players needed to start the match, and the nicknames of the players that are already in.
     * It also provides an option for the user to create a new game instead of joining an existing one.
     *
     * @param games A Map where the key is an Integer representing the game number and the value is an ArrayList of Strings representing the nicknames of the players in the game.
     * @param numPlayers An ArrayList of Pair objects where each Pair contains two Integers. The first Integer is the game number and the second Integer is the number of players needed to start the match.
     * @return An integer representing the user's choice. The index of the chosen game in the list, or 0 to create a new game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        System.out.println("Please choose one of the following games");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < games.size(); i++) {
            s.append("   ").append("\n" + bold + bold + "  LOBBY " + reset).append(bold).append(i + 1).append(reset).append("\n");
            s.append("─────────────────").append("\n");
            s.append("needed: ").append(numPlayers.get(i).getSecond());
            if (games.get(i) != null) {
                for (String name : games.get(i)) {
                    s.append("\n").append("      ").append(bold).append("• ").append(name).append(reset);
                }
            } else
                s.append("\nNo players in this lobby");
            s.append("\n\n");

        }
        System.out.println(s);
        System.out.println("If you don't want to join any of the available games and you want to create a new one, please insert 0 (zero)");
        return (scanner.nextInt());

    }

    /**
     * This method displays the available colors that the user can choose from.
     * The user can choose a color by entering the corresponding number.
     * Each color is printed to the console with its corresponding number.
     *
     * @param availableColors A List of PawnColor objects representing the available colors.
     */
    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) {
        System.out.println("Choose one of the following colors: ");
        for (int i = 0; i < availableColors.size(); i++) {
            System.out.println(GraphicUsage.pawnColorDictionary.get(availableColors.get(i)) + " [" + (i + 1) + "] " + availableColors.get(i).toString() + ANSIreset);
        }
    }

    /**
     * This method notifies the user that the system is waiting for other players to join the game.
     * The notification is printed to the console.
     */
    @Override
    public void waitingForPlayers() {
        System.out.println("Waiting for other players to Join...");
    }

    /**
     * This method prints a given message to the console.
     * It can be used to display information to the user.
     *
     * @param message A String representing the message to be printed.
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * This method prompts the user to select a command when it's their turn.
     * The user can only choose the "MOVE" command to play their turn.
     * The method will keep asking until the "MOVE" command is chosen.
     * If it's not the user's turn, the method will return the "MOVE" command by default.
     *
     * @param IsMyTurn A Boolean indicating whether it's the user's turn.
     * @return A Command enum representing the chosen command by the user. It will always be Command.MOVE.
     */
    @Override
    public Command receiveCommand(Boolean IsMyTurn) {
        if(IsMyTurn) {
            System.out.println("Insert MOVE to play Your Turn");
            while (!scanner.hasNext()) {
            } // Wait for the next input
            String command = scanner.next().toUpperCase();
            while (!command.equals("MOVE")) {
                System.out.println("Invalid command! Please choose a valid command: [MOVE]");
                while (!scanner.hasNext()) {
                } // Wait for the next input
                command = scanner.next().toUpperCase();
            }

        }
        return Command.MOVE;
    }

    /**
     * This method prints the current state of the game board to the console.
     * It displays information about the opponents and the current player (me).
     * For each opponent, it shows their information using the showOpponent method from the TUIComponents class.
     * It also displays the common cards on the board and the current player's information.
     *
     * @param model A PlayGround object representing the current state of the game board.
     * @param opponents An ArrayList of Player objects representing the opponents in the game.
     * @param me A Player object representing the current player.
     */
    @Override
    public void printBoard(PlayGround model, ArrayList<Player> opponents, Player me) {
        clearConsole();
        StringBuilder opponentsInfo = new StringBuilder();
        for(Player p: opponents){
            if(p!=me)
                opponentsInfo.append(TUIComponents.showOpponent(p)).append("\n");
        }


        System.out.println(opponentsInfo.toString());
        System.out.println();
        System.out.println(TUIComponents.showCommonCards(model));
        System.out.println();
        System.out.println(TUIComponents.showMySelf(me));
    }

    /**
     * This method prompts the user to choose a personal Objective Card from a list of available objective cards.
     * The user can choose an objective card by entering the corresponding number.
     * The method will keep asking until a valid option is chosen.
     *
     * @param objectives An ArrayList of ObjectiveCard objects representing the available objective cards.
     * @return An integer representing the index of the chosen objective card in the list.
     */
    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        String s="";
        System.out.println("Please, choose your personal Objective Card!");
            for (int i = 0; i < objectives.size(); i++) {
                s=TUIComponents.concatString(s,TUIComponents.printObjectives(objectives.get(i)), 4);
            }
            System.out.println(TUIComponents.concatString("[1]","[2]", 40));
            System.out.println(s);

        int choice;
        do {
            choice = scanner.nextInt();
            if (choice != 1 && choice != 2) {
                System.out.println("Invalid option! Please choose 1 or 2.");
            }
        } while (choice != 1 && choice != 2);
        return choice - 1;
    }

    /**
     * This method displays a specific message to the user based on the provided string.
     * The string can be one of the following: "WIN", "GAME_OVER", or "CODEX_NATURALIS".
     * If the string is "WIN", it displays a winning message.
     * If the string is "GAME_OVER", it displays a game over message.
     * If the string is "CODEX_NATURALIS", it displays the game's legend.
     *
     * @param s A String representing the type of message to be displayed.
     */
    @Override
    public void showString(String s){
        if(s == "WIN"){
            printMessage(GraphicUsage.you_win);
        }else if(Objects.equals(s, "GAME_OVER")){
            printMessage(GraphicUsage.game_over);
        }else if(s == "CODEX_NATURALIS"){
            String sb = GraphicUsage.codex_naturalis_string + "\n\n" +
                    bold + "Before you start the game, read carefully the following legend about the game symbols: \n\n" + reset +
                    GraphicUsage.legenda;
            System.out.println(sb);;
        }
    }

    /**
     * This method prompts the user to input a number.
     * It keeps asking for input until a valid integer is provided.
     * If the input is not a valid integer, it displays an error message and clears the invalid input.
     *
     * @return An integer representing the user's input.
     */
    @Override
    public int getInput() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input, please enter a number.");
            scanner.next(); // Clear the invalid input
        }
        return scanner.nextInt();
    }


    /**method used to clear the console.
     * Does not necessarily work on all OS.
     * This method, first checks if the operating system is Windows:
     * If it is, it runs the cls command in a new process to clear the console.
     * If the operating system is not Windows, it prints an escape sequence (\033\143) to the console.
     * This escape sequence is interpreted by the terminal as a command to clear the screen.*/
    public static void clearConsole() {
        try {
            if( System.getProperty("os.name").contains("Windows") ) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else {
                System.out.print("\033\143");
            }
        }
        catch( Exception ignored ) {
        }
    }
}