package CodexNaturalis.src.main.java.it.polimi.ingsw.View.TUI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Client;
import CodexNaturalis.src.main.java.it.polimi.ingsw.View.UserInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.MainController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayArea;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class TextualUI extends UserInterface {

    private int lobbySize;
    private String logo;
    private GameController gameController;

    private MainController mainController;


    public void run() {


    }

    /**
     * Asks the user to enter the nickname
     */
    private String askNickname() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your nickname: " + "\n >>");
        String nickName = scanner.next();
        return nickName;
    }

    /**
     * The player logs in
     */
    private void userLogin() throws RemoteException, NotBoundException, NotReadyToRunException, MaxNumPlayersException {
        Scanner scanner = new Scanner(System.in);
        boolean logSuccessfull = false;
        String nickname = null;
        System.out.println(logo + "\n");


        System.out.println("Welcome to the game!");


        String choice = mainController.joinOrcreate();

        switch (choice) {

            case "CREATE":

                System.out.println("Please choose a nickName: ");
                while (!logSuccessfull) {

                    nickname = askNickname();

                    logSuccessfull = mainController.checkUniqueNickname(nickname);

                    if (logSuccessfull) {
                        System.out.println("Login successful");

                    } else {
                        System.out.println("Login failed. Please choose a different nickname. " + "\n >>");
                    }
                }


                System.out.println("How many players do you want to play with?" + "\n >>");
                lobbySize = Integer.parseInt(scanner.next());
                while (lobbySize < 2 || lobbySize > 4) {

                    System.out.println("Please choose a number between 2 and 4");
                    lobbySize = Integer.parseInt(scanner.next());
                }
                System.out.println("Choose PawnColor between the following: ");
                List<PawnColor> listOfColors = gameController.AvailableColors();

                System.out.println(listOfColors);

                PawnColor color = PawnColor.valueOf(scanner.next());
                while (!listOfColors.contains(color)) {
                    System.out.println("Please insert a valid color: ");
                    color = PawnColor.valueOf(scanner.next());
                }

                mainController.createGame(nickname, lobbySize, color);
                System.out.println("Wait for the chosen number of players to enter...");
                //inserire una wait


            case "JOIN":

                System.out.println("Please choose a nickName: ");
                while (!logSuccessfull) {

                    nickname = askNickname();

                    logSuccessfull = mainController.checkUniqueNickname(nickname);

                    if (logSuccessfull) {
                        System.out.println("Login successful");

                    } else {
                        System.out.println("Login failed. Please choose a different nickname. " + "\n >>");
                    }
                }

                System.out.println("Choose PawnColor between the following: ");
                List<PawnColor> listOfColor = gameController.AvailableColors();

                System.out.println(listOfColor);

                PawnColor colors = PawnColor.valueOf(scanner.next());
                while (!listOfColor.contains(colors)) {
                    System.out.println("Please insert a valid color: ");
                    colors = PawnColor.valueOf(scanner.next());
                }
                mainController.addClientToLobby(nickname, colors);
        }

    }

    /**
     * displays the current state of the game
     */
    public void showGameBoard(PlayArea playArea) {

    }

    /**
     * Menu to show once the game starts
     */
    public void showInitialMenu() {

    }

    /**
     * Show player information
     */
    public void printPlayerInformation(String nickName) {
        for (Client client : gameController.getClients()) {
            if (nickName == client.getPlayer().getNickname()) {
                System.out.println("NickName: " + client.getPlayer().getNickname());
                System.out.println("Score: " + client.getPlayer().getScore());
                System.out.println("PawnColor: " + client.getPlayer().getNickname());
                System.out.println("Round: " + client.getPlayer().getRound());
            }
        }
    }

    /**
     * Print Chat
     */
    public void printChat() {

    }


    public void printChoosePersonalObjective() {
        boolean isOk = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your personal goal: [1/2]");
        List<ObjectiveCard> objectiveCards = gameController.drawObjectiveCardForPlayer();
        System.out.println(objectiveCards);
        String personalObjectiveCard = scanner.next();
    }


}


