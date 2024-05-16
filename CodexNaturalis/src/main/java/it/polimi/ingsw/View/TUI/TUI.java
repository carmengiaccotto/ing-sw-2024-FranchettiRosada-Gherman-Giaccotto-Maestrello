package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.View.UserInterface;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class TUI implements UserInterface {
    Scanner scanner = new Scanner(System.in);


    @Override
    public PlayCard chooseCardToDraw (PlayGround model) {
        System.out.println("Choose where you want to draw the card from: [GOLD-DECK / RESOURCE-DECK / RESOURCE-CARD1 / RESOURCE-CARD2 / GOLD-CARD1 / GOLD-CARD2]");
        String draw = scanner.next().toUpperCase();
        switch (draw) {

            case "GOLD-DECK":
                GoldCard card = (GoldCard) model.getGoldCardDeck().getCards().getFirst();
                return card;

            case "RESOURCE-DECK":
                ResourceCard card1 = (ResourceCard) model.getResourceCardDeck().getCards().getFirst();
                return card1;

            case "RESOURCE-CARD1":
                ResourceCard card2 = (ResourceCard) model.getCommonResourceCards().get(0);
                return card2;

            case "RESOURCE-CARD2":
                ResourceCard card3 = (ResourceCard) model.getCommonResourceCards().get(1);
                return card3;

            case "GOLD-CARD1":
                GoldCard card4 = (GoldCard) model.getCommonGoldCards().get(0);
                return card4;

            case "GOLD-CARD2":
                GoldCard card5 = (GoldCard) model.getCommonGoldCards().get(1);
                return card5;

            default:
                System.out.println("Choose a valid option!");
                return chooseCardToDraw(model);
        }
    }

    @Override
    public void showBoardAndPlayAreas() {

    }

    @Override
    public String chooseSideInitialCard(InitialCard c) {
        PrintCards.printCardFrontBack(c);
        System.out.println("Choose the side of the starting card you want to play: [FRONT/BACK]");
        String side = scanner.next().toUpperCase();
        while (!side.equals("FRONT") && !side.equals("BACK")) {
            System.out.println("Please insert a valid side: [FRONT/BACK]");
            side = scanner.next().toUpperCase();
        }
        return side;
    }


    @Override
    public void playInitialCard(SideOfCard s, PlayArea playArea) {
        PrintPlayArea.DrawMyPlayArea(playArea);

    }

    @Override
    public String selectNickName() {
        System.out.println("Insert your Nickname: ");
        return scanner.next();
    }

    @Override
    public int createOrJoin() {
        System.out.println("Do you want to create a new game or join an existing one? ");
        System.out.println("[0]: Create new Game ");
        System.out.println("[1]: Join existing one ");
        return scanner.nextInt();

    }

    @Override
    public int MaxNumPlayers() {
        System.out.println("How Many Players do you want to have for this match? Please select a Number between 2 and 4");
        return scanner.nextInt();
    }

    @Override
    public int displayavailableGames(ArrayList<GameController> games) throws RemoteException {
        System.out.println("Please choose one of the following games");
        for (int i = 0; i < games.size(); i++) {
            System.out.println("[" + (i + 1) + "] Game" + (i + 1) + "   Needed players to start the match: " + games.get(i).getNumPlayers());
            System.out.println("Current players: ");
            for (ClientControllerInterface client : games.get(i).getPlayers()) {
                System.out.println(client.getNickname());
            }
        }
        System.out.println("If you don't want to join any of the available games and you want to create a new one, please insert 0 (zero)");
        return (scanner.nextInt());

    }


    @Override
    public int displayAvailableColors(ArrayList<PawnColor> availableColors) {
        System.out.println("Choose one of the following colors: ");
        for (int i = 0; i < availableColors.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + availableColors.get(i).toString());
        }
        return (scanner.nextInt());
    }

    @Override
    public void waitingForPlayers() {
        System.out.println("Waiting for other players to Join...");
    }

    @Override
    public void printObjectives(ObjectiveCard card) {
        if (card.getClass().equals(DisplayMode.class))
            PrintCards.printDispositionCard((DispositionObjectiveCard) card);
        else
            PrintCards.printSymbolObjectiveCard(card);

    }

    @Override
    public Command receiveCommand() {
        System.out.println("Select a command: [MOVE/CHAT]");
        String command = scanner.nextLine().toUpperCase();
        while (!command.equals("MOVE") && !command.equals("CHAT")) {
            System.out.println("Please insert a valid command: [MOVE/CHAT]");
            command = scanner.nextLine().toUpperCase();
        }

        return Command.valueOf(command.toUpperCase());
    }

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        System.out.println("Please, choose your personal Objective Card");
        for (int i = 0; i < objectives.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            printObjectives(objectives.get(i));
        }
        return (scanner.nextInt() - 1);
    }


//    GameController gameController;
//
//    private String CodexNaturalisLogo;
//    private final MainController mainController = new MainController();
//
//
//    /**
//     * The player logs in
//     */
//    public void userLogin() throws IOException, NotBoundException, NotReadyToRunException, MaxNumPlayersException {
//        Scanner scanner = new Scanner(System.in);
//        boolean logSuccessfull = false;
//        String nickname = null;
//        //System.out.println(CodexNaturalisLogo + "\n");
//
//        System.out.println("Welcome to the game!");
//
//        String choice = mainController.joinOrcreate();
//
//        switch (choice) {
//
//            case "CREATE":
//
//                System.out.println("Please choose a nickName: " + "\n ->");
//                while (!logSuccessfull) {
//
//                    nickname = scanner.next();
//
//                    logSuccessfull = mainController.checkUniqueNickname(nickname);
//
//                    if (logSuccessfull) {
//                        System.out.println("Login successful");
//
//                    } else {
//                        System.out.println("Login failed. Please choose a different nickname. " + "\n ->");
//                    }
//                }
//
//
//                System.out.println("How many players do you want to play with? 2/3/4?" + "\n ->");
//                int numOfPlayers = Integer.parseInt(scanner.next());
//                while (numOfPlayers < 2 || numOfPlayers > 4) {
//
//                    System.out.println("Please choose a number from the following: 2/3/4" + "\n ->");
//                    numOfPlayers = Integer.parseInt(scanner.next());
//                }
//                ArrayList<Client> c = new ArrayList<>();
//                PlayGround model = new PlayGround(0);   //provvisorio numero zero
//                GameController game = new GameController(c, model);
//                Client client = new Client();
//
//                mainController.createGame(nickname, numOfPlayers, game, client);
//                System.out.println("Wait for the chosen number of players to enter...");
//                //inserire una wait
//                break;
//
//
//            case "JOIN":
//
//                System.out.println("Please choose a nickName: ");
//                while (!logSuccessfull) {
//
//                    nickname = scanner.next();
//
//                    logSuccessfull = mainController.checkUniqueNickname(nickname);
//
//                    if (logSuccessfull) {
//                        System.out.println("Login successful");
//
//                    } else {
//                        System.out.println("Login failed. Please choose a different nickname. " + "\n ->");
//                    }
//                }
//
//                System.out.println("Here is the list of games you can join: " + "\n");
//
//                for(GameController theGame : mainController.getGames()) {
//
//                    if (theGame.getClients().size() < theGame.getModel().getNumOfPlayers()) {
//
//                        System.out.println("Game ID: " + theGame.getModel().getGameId() + "\n");
//                        System.out.println("Maximum number of players: " + theGame.getModel().getNumOfPlayers() + "\n");
//                        System.out.println("Number of current players in the game: " + theGame.getClients().size() + "\n");
//                        System.out.println("List of players in the game: \n");
//                        for (int i = 0; i < theGame.getClients().size(); i++) {
//                            System.out.println(theGame.getClients().get(i).getPlayer().getNickname());
//                            System.out.println("\n");
//                        }
//                        System.out.println("\n");
//                    }
//                }
//                System.out.println("Select the ID of the game you want to join: ");
//                int ID = Integer.parseInt(scanner.next());
//
//                boolean gameFound = false;
//                boolean playerAdded = false;
//
//                while((!gameFound) || (!playerAdded)){
//
//                    for(GameController theGame1 : mainController.getGames()){
//
//                        if(theGame1.getModel().getGameId() == ID){
//
//                            if(theGame1.getClients().size() < theGame1.getModel().getNumOfPlayers()){
//
//                                mainController.addClientToLobby(nickname, ID);
//                                playerAdded = true;
//
//                            }
//                            else{
//                                System.out.println("Maximum number of players already reached: ");
//
//                            }
//                            gameFound = true;
//                            break;
//                        }
//
//                    }
//                    if(!gameFound){
//                        System.out.println("The ID entered does not exist: ");
//                        ID = Integer.parseInt(scanner.next());
//                    }
//
//                }
//
////                System.out.println("Choose PawnColor between the following: ");
////                List<PawnColor> listOfColor = gameController.AvailableColors();
////
////                System.out.println(listOfColor);
////
////                PawnColor colors = PawnColor.valueOf(scanner.next());
////                while (!listOfColor.contains(colors)) {
////                    System.out.println("Please insert a valid color: ");
////                    colors = PawnColor.valueOf(scanner.next());
////                }
//                break;
//        }
//
//    }
//
//
//    /**
//     * Show player information
//     */
//
//    public void printPlayerInformation(String nickName) {
//        for (Client client : gameController.getClients()) {
//            if (nickName.equals(client.getPlayer().getNickname())) {
//                System.out.println("NickName: " + client.getPlayer().getNickname());
//                System.out.println("Score: " + client.getPlayer().getScore());
//                System.out.println("PawnColor: " + client.getPlayer().getNickname());
//                System.out.println("Round: " + client.getPlayer().getRound());
//            }
//        }
//    }
//
//
//    public void choosePersonalObjectiveCard(String nickname) {
//        boolean isOk = false;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Choose your personal goal: [1/2]");
//        List<ObjectiveCard> objectiveCards = gameController.drawObjectiveCardForPlayer();
//        System.out.println(objectiveCards);
//        String personalObjectiveCard = scanner.next();
//
//        do {
//            switch (personalObjectiveCard) {
//
//                case "1":
//                    ObjectiveCard o = objectiveCards.getFirst();
//                    gameController.getPlayer(nickname).setPersonalObjectiveCard(o);
//                    isOk = true;
//                    break;
//
//                case "2":
//                    ObjectiveCard o1 = objectiveCards.get(1);
//                    gameController.getPlayer(nickname).setPersonalObjectiveCard(o1);
//                    isOk = true;
//                    break;
//
//                default:
//                    System.out.println("Enter a valid choice: [1/2]");
//                    personalObjectiveCard = scanner.next();
//            }
//        } while(!isOk);
//    }
//
//    @Override
//    public void playInitialCard(InitialCard c, String nickname){
//        boolean valid = false;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Choose the side of the starting card you want to play: [FRONT/BACK]");
//        String side = scanner.next().toUpperCase();
//
//        do {
//            switch (side) {
//                case "FRONT":
//                    SideOfCard card = gameController.getPlayer(nickname).ChooseCardToPlay(c, Side.FRONT);
//                    gameController.getPlayer(nickname).getPlayArea().AddCardOnArea(card);
//                    valid = true;
//                    break;
//                case "BACK":
//                    SideOfCard card1 = gameController.getPlayer(nickname).ChooseCardToPlay(c, Side.BACK);
//                    gameController.getPlayer(nickname).getPlayArea().AddCardOnArea(card1);
//                    valid = true;
//                    break;
//                default:
//                    System.out.println("Choose a valid side: [FRONT/BACK]");
//                    side = scanner.next().toUpperCase();
//            }
//        } while(!valid);
//    }
//
//    @Override
//    public void playCard(String nickname){
//        boolean valid = false;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Choose from your cards which one you want to play; [1/2/3]");
//        ArrayList<PlayCard> cardInHand = gameController.getCardInHandPlayer(nickname);
//        System.out.println(cardInHand);
//        int c = Integer.parseInt(scanner.next());
//
//        while((c != 1) && (c != 2) && (c != 3)){
//            System.out.println("Choose a valid card");
//            c = Integer.parseInt(scanner.next());
//        }
//
//        System.out.println("Choose the side you want to play: [FRONT/BACK]");
//        System.out.println(cardInHand.get(c-1));
//        String side = scanner.next();
//
//        while(!valid) {
//            switch (side) {
//                case "FRONT":
//
//                    SideOfCard card = gameController.getPlayer(nickname).ChooseCardToPlay(cardInHand.get(c-1), Side.FRONT);
//                    System.out.println("Choose the row and column in which you want to place the card: ");
//                    int row = scanner.nextInt();
//                    int column = scanner.nextInt();
//                    boolean valid2 = gameController.ValidTwoCornersSameCard(gameController.getPlayer(nickname).getPlayArea(), row, column);
//                    boolean valid3 = gameController.notTryingToCoverHiddenCorners(gameController.getPlayer(nickname).getPlayArea(), row, column, card);
//
//                    boolean isOK = false;
//                    do {
//                        if (valid2 && valid3) {
//                            boolean valid1 = false;
//                            do {
//                                valid1 = gameController.ValidPositionCardOnArea(gameController.getPlayer(nickname).getPlayArea(), row, column, card);
//                                if (!valid1) {
//                                    System.out.println("Invalid positions. Re-enter the row and column where you want to insert the card: ");
//                                    row = scanner.nextInt();
//                                    column = scanner.nextInt();
//                                }
//
//                            } while (!valid1);
//                            isOK = true;
//
//                        } else {
//                            System.out.println("Invalid positions. Re-enter the row and column where you want to insert the card: ");
//                            row = scanner.nextInt();
//                            column = scanner.nextInt();
//                        }
//                    } while(!isOK);
//                    gameController.removeCardInHand(cardInHand.get(c - 1), nickname);
//
//                case "BACK":
//
//                    SideOfCard card1 = gameController.getPlayer(nickname).ChooseCardToPlay(cardInHand.get(c-1), Side.BACK);
//                    System.out.println("Choose the row and column in which you want to place the card: ");
//                    int row1 = scanner.nextInt();
//                    int column1 = scanner.nextInt();
//                    boolean valid5 = gameController.ValidTwoCornersSameCard(gameController.getPlayer(nickname).getPlayArea(), row1, column1);
//                    boolean valid6 = gameController.notTryingToCoverHiddenCorners(gameController.getPlayer(nickname).getPlayArea(), row1, column1, card1);
//
//                    boolean isOK1 = false;
//                    do {
//                        if (valid5 && valid6) {
//                            boolean valid8 = false;
//                            do {
//                                valid8 = gameController.ValidPositionCardOnArea(gameController.getPlayer(nickname).getPlayArea(), row1, column1, card1);
//                                if (!valid8) {
//                                    System.out.println("Invalid positions. Re-enter the row and column where you want to insert the card: ");
//                                    row1 = scanner.nextInt();
//                                    column1 = scanner.nextInt();
//                                }
//
//                            } while (!valid8);
//                            isOK1 = true;
//
//                        } else {
//                            System.out.println("Invalid positions. Re-enter the row and column where you want to insert the card: ");
//                            row1 = scanner.nextInt();
//                            column1 = scanner.nextInt();
//                        }
//                    } while(!isOK1);
//
//                    gameController.removeCardInHand(cardInHand.get(c - 1), nickname);
//
//                default:
//
//                    System.out.println("Choose a valid side: [FRONT/BACK]");
//                    side = scanner.next();
//            }
//        }
//
//    }
//
}




