package CodexNaturalis.src.main.java.it.polimi.ingsw.View.TUI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.Client;
import CodexNaturalis.src.main.java.it.polimi.ingsw.View.UserInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.MainController;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Card;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.PlayCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayArea;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayGround;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextualUI extends UserInterface {

    private int lobbySize;
    private String logo;
    private GameController gameController;
    private MainController mainController = new MainController();


    /**
     * Asks the user to enter the nickname
     */
    private String askNickname() {
        Scanner scanner = new Scanner(System.in);
        String nickName = scanner.next();
        return nickName;
    }

    /**
     * The player logs in
     */
    public void userLogin() throws IOException, NotBoundException, NotReadyToRunException, MaxNumPlayersException {
        Scanner scanner = new Scanner(System.in);
        boolean logSuccessfull = false;
        String nickname = null;
        //System.out.println(logo + "\n");


        System.out.println("Welcome to the game!");

        String choice = mainController.joinOrcreate();

        switch (choice) {

            case "CREATE":

                System.out.println("Please choose a nickName: " + "\n >>");
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
                ArrayList<Client> c = new ArrayList<>();
                PlayGround model = new PlayGround(0); //provvisorio numero zero
                GameController game = new GameController(c, model);
                Client client = new Client();

                mainController.createGame(nickname, lobbySize, game, client);
                System.out.println("Wait for the chosen number of players to enter...");
                //inserire una wait
                break;


            case "JOIN":

//                System.out.println("Please choose a nickName: ");
//                while (!logSuccessfull) {
//
//                    nickname = askNickname();
//
//                    logSuccessfull = mainController.checkUniqueNickname(nickname);
//
//                    if (logSuccessfull) {
//                        System.out.println("Login successful");
//
//                    } else {
//                        System.out.println("Login failed. Please choose a different nickname. " + "\n >>");
//                    }
//                }
//
//                System.out.println("Choose PawnColor between the following: ");
//                List<PawnColor> listOfColor = gameController.AvailableColors();
//
//                System.out.println(listOfColor);
//
//                PawnColor colors = PawnColor.valueOf(scanner.next());
//                while (!listOfColor.contains(colors)) {
//                    System.out.println("Please insert a valid color: ");
//                    colors = PawnColor.valueOf(scanner.next());
//                }
//                mainController.addClientToLobby(nickname, colors);
                break;
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

    public void printChoosePersonalObjective(String nickname) {
        boolean isOk = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your personal goal: [1/2]");
        List<ObjectiveCard> objectiveCards = gameController.drawObjectiveCardForPlayer();
        System.out.println(objectiveCards);
        String personalObjectiveCard = scanner.next();

        do {
            switch (personalObjectiveCard) {

                case "1":
                    ObjectiveCard o = objectiveCards.getFirst();
                  //  gameController.getPersonalObjective(nickname) = o;
                    isOk = true;
                    break;

                case "2":
                    ObjectiveCard o1 = gameController.getPersonalObjective(nickname);
                    o1 = objectiveCards.getFirst();
                    isOk = true;
                    break;

                default:
                    System.out.println("Enter a valid choice: [1/2]");
                    personalObjectiveCard = scanner.next();
            }
        } while(!isOk);
    }

    public void playInitialCard(Card c){
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the side of the starting card you want to play: [FRONT/BACK]");
        String side = scanner.next().toUpperCase();

        do {
            switch (side) {
                case "FRONT":
                    gameController.addCardOnArea(c, Side.FRONT);
                    valid = true;
                    break;
                case "BACK":
                    gameController.addCardOnArea(c, Side.BACK);
                    valid = true;
                    break;
                default:
                    System.out.println("Choose a valid side: [FRONT/BACK]");

            }
        } while(!valid);

    }

    public void chooseCardToPlay(String nickname){
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose from your cards which one you want to play; [1/2/3]");
        ArrayList<PlayCard> cardInHand = gameController.getCardInHandPlayer(nickname);
        System.out.println(cardInHand);
        int c = Integer.parseInt(scanner.next());

        while((c == 1) || (c == 2) || (c == 3)){
            System.out.println("Choose a valid card");
            c = Integer.parseInt(scanner.next());
        }

        System.out.println("Choose the side you want to play: [FRONT/BACK]");
        String side = scanner.next();

        while((!side.equals("FRONT")) || (!side.equals("BACK")))
        {
            System.out.println("Choose a valid side");
            side = scanner.next();
        }

        switch(side){
            case "FRONT":
                gameController.addCardOnArea(cardInHand.get(c-1),Side.FRONT);
                gameController.removeCardInHand(cardInHand.get(c-1));

            case "BACK":
                gameController.addCardOnArea(cardInHand.get(c-1),Side.BACK);
                gameController.removeCardInHand(cardInHand.get(c-1));

        }

    }

    public void drawCard(){
    }


}






