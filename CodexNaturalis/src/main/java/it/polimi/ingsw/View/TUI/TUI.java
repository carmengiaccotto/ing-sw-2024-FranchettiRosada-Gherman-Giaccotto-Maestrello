package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.View.UserInterface;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class TUI implements UserInterface {
    Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to choose where to draw a card from.
     * The user can choose to draw from the gold deck, resource deck, or one of the four available cards on the playground.
     * The method will keep asking until a valid option is chosen.
     *
     * @return A string representing the chosen deck or card to draw from.
     */
    @Override
    public String chooseCardToDraw () {
        String draw;
        do {
            System.out.println("Choose where you want to draw the card from: [GOLD-DECK / RESOURCE-DECK / RESOURCE-CARD1 / RESOURCE-CARD2 / GOLD-CARD1 / GOLD-CARD2]");
            draw = scanner.next().toUpperCase();
            if (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2")) {
                System.out.println("Invalid option! Please choose a valid option!");
            }
        } while (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2"));
        return draw;
    }

    @Override
    public String chooseCardToPlay(ArrayList<PlayCard> cardInHand) {
//        System.out.println("Choose which card you want to play; [1/2/3]");
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
        return null;
    }

    @Override
    public void showBoardAndPlayAreas() {

    }

    /**
     * Prompts the user to choose a side of the initial card to play.
     * The user can choose either the front or the back side of the card.
     * The method will keep asking until a valid option is chosen.
     *
     * @param c The initial card from which the user needs to choose a side.
     * @return A string representing the chosen side of the card.
     */
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

    /**
     * Prompts the user to choose a nickname.
     *
     * @return A string representing the chosen nickname.
     */
    @Override
    public String selectNickName() {
        System.out.println("Insert your Nickname: ");
        return scanner.next();
    }

    /**
     * Prompts the user to either create a new game or join an existing one.
     * The user can choose to create a new game by entering 0 or join an existing game by entering 1.
     *
     * @return An integer representing the user's choice. 0 for creating a new game, 1 for joining an existing game.
     */
    @Override
    public int createOrJoin() {
        System.out.println("Do you want to create a new game or join an existing one? ");
        System.out.println("[0]: Create new Game ");
        System.out.println("[1]: Join existing one ");
        return scanner.nextInt();

    }

    /**
     * Prompts the user to select the maximum number of players for the match.
     * The user can choose a number between 2 and 4.
     *
     * @return An integer representing the maximum number of players chosen by the user.
     */
    @Override
    public int MaxNumPlayers() {
        System.out.println("How Many Players do you want to have for this match? Please select a Number between 2 and 4");
        return scanner.nextInt();
    }

    /**
     * Displays the available games that the user can join.
     * For each game, it shows the game number, the number of players needed to start the match, and the nicknames of the players that are already in.
     * It also provides an option for the user to create a new game instead of joining an existing one.
     *
     * @param games An ArrayList of GameController objects representing the available games.
     * @return An integer representing the user's choice. The index of the chosen game in the list, or 0 to create a new game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public int displayavailableGames(ArrayList<GameControllerInterface> games) throws RemoteException {
        System.out.println("Please choose one of the following games");
        for (int i = 0; i < games.size(); i++) {
            System.out.println("[" + (i + 1) + "] Game" + (i + 1) + "   Needed players to start the match: " + games.get(i).getNumPlayers());
            System.out.println("Current players: ");
            for (ClientControllerInterface client : games.get(i).getListener().getPlayers()) {
                System.out.println(client.getNickname());
            }
        }
        System.out.println("If you don't want to join any of the available games and you want to create a new one, please insert 0 (zero)");
        return (scanner.nextInt());

    }


    /**
     * Prompts the user to choose a color from the available colors.
     * The user can choose a color by entering the corresponding number.
     *
     * @param availableColors An ArrayList of PawnColor objects representing the available colors.
     * @return An integer representing the index of the chosen color in the list.
     */
    @Override
    public int displayAvailableColors(ArrayList<PawnColor> availableColors) {
        System.out.println("Choose one of the following colors: ");
        for (int i = 0; i < availableColors.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + availableColors.get(i).toString());
        }
        return (scanner.nextInt());
    }

    /**
     * Notifies the user that the system is waiting for other players to join the game.
     */
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

    /**
     * Prints a given message to the console.
     *
     * @param message The string message to be printed.
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user to select a command from the available options.
     * The user can choose either the "MOVE" or "CHAT" command.
     * The method will keep asking until a valid command is chosen.
     *
     * @return A Command enum representing the chosen command by the user.
     */
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

    /**
     * Prompts the user to choose a personal Objective Card from a list of available objective cards.
     * The user can choose an objective card by entering the corresponding number.
     *
     * @param objectives An ArrayList of ObjectiveCard objects representing the available objective cards.
     * @return An integer representing the index of the chosen objective card in the list.
     */
    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        System.out.println("Please, choose your personal Objective Card");
        for (int i = 0; i < objectives.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            printObjectives(objectives.get(i));
        }
        return (scanner.nextInt() - 1);
    }


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




