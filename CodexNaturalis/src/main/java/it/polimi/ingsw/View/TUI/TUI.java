package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.View.UserInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class TUI implements UserInterface, Serializable {
    Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to choose where to draw a card from.
     * The user can choose to draw from the gold deck, resource deck, or one of the four available cards on the playground.
     * The method will keep asking until a valid option is chosen.
     *
     * @return A string representing the chosen deck or card to draw from.
     */
    @Override
    public String chooseCardToDraw() {
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
    public int chooseCardToPlay(ArrayList<PlayCard> cardInHand) {
        System.out.println("Those are the cards in your hand: ");
        showCardsInHand(cardInHand);
        System.out.println("Which card do you want to play? [1/2/3]");
        int c = Integer.parseInt(scanner.next());
        while ((c != 1) && (c != 2) && (c != 3)) {
            System.out.println("Choose a valid card! [1/2/3]");
            c = Integer.parseInt(scanner.next());
        }
        return c;
    }

    public void showInitialCard(InitialCard card){
        System.out.println("This is your initial card: ");
        String[][] frontBack= new String[11][70];
        for(int i=0; i<11; i++){
            for(int j=0; j<70; j++){
                frontBack[i][j]=" ";
            }
        }
        frontBack[0][10]="FRONT";
        frontBack[0][40]="BACK";
        DesignSupportClass.printCard(frontBack, card.getFront(), 0, 0, 10, 34);
        DesignSupportClass.printCard(frontBack, card.getBack(), 0, 36, 10, 34);
        for(int i=0; i<11; i++){
            for(int j=0; j<70; j++){
                System.out.print(frontBack[i][j]);
            }
            System.out.println();
        }
    }

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

    @Override
    public ArrayList<Integer> choosePositionCardOnArea(PlayArea playArea) {
        ArrayList<Integer> position = new ArrayList<Integer>();
        System.out.println("Choose the row and column in which you want to place the card: [row] [column]");
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        position.add(row);
        position.add(column);
        return position;
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
        System.out.println(" 1. Create new Game ");
        System.out.println(" 2. Join existing one ");
        System.out.println(">>");
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
        System.out.println("How Many Players do you want to have for this match? Please select a Number between 2 and 4!");
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
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        System.out.println("Please choose one of the following games");
        for (int i = 0; i < games.size(); i++) {
            System.out.println("[" + (i + 1) + "] Game" +"     number of players needed: " + numPlayers.get(i).getSecond() );
            System.out.println("Current players: ");
            if(games.get(i)!=null)
                for (String name: games.get(i)) {

                System.out.println("      "+name);
            }
            else
                System.out.println("          No players yet");
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
            System.out.println(" [" + (i + 1) + "]" + availableColors.get(i).toString());
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
        if (card.getClass().equals(DispositionObjectiveCard.class))
            DesignSupportClass.printDispositionObjectiveCard((DispositionObjectiveCard) card);
        else if (card.getClass().equals(SymbolObjectiveCard.class))
           DesignSupportClass.printSymbolObjectiveCard((SymbolObjectiveCard) card);

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
        String command = "";
        while (!command.equals("MOVE") && !command.equals("CHAT")) {
            command = scanner.nextLine().toUpperCase();
            if (!command.equals("MOVE") && !command.equals("CHAT")) {
                System.out.println("Please insert a valid command: [MOVE/CHAT]");
            }
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
                System.out.println();
        }

        return (scanner.nextInt() - 1);
    }

    /**Method that shows the player the cards they have in hand and can play during their turn
     * @param cards that the player has in its hand*/
    public void showCardsInHand(ArrayList<PlayCard> cards) {
        for(int i=0; i<cards.size(); i++){
            System.out.println("[" + (i + 1) + "]"); //print the index of the card
            String[][] frontBack= new String[10][70];
            for(int j=0; j<10; j++){
                for(int k=0; k<70; k++){
                    frontBack[j][k]=" ";
                }
            }
            DesignSupportClass.printCard(frontBack, cards.get(i).getFront(), 0, 0, 10, 34);
            DesignSupportClass.printCard(frontBack, cards.get(i).getBack(), 0, 36, 10, 34);
            for(int j=0; j<10; j++){
                for(int k=0; k<70; k++){
                    System.out.print(frontBack[j][k]);
                }
                System.out.println();
            }

        }//each player has 3 cards in hand

    }

    @Override
    public void showBoardAndPlayers(ClientControllerInterface me, PlayGround model, ArrayList<ClientControllerInterface> opponents) throws RemoteException {
        for(ClientControllerInterface opponent: opponents){
            showPlayerInfo(opponent);
            showOpponentPlayArea(opponent);
        }
        showCommonCards(model.getCommonResourceCards(), model.getCommonGoldCards(), model.getResourceCardDeck(), model.getGoldCardDeck());
        //todo aggiungi obiettivi comuni
        showPlayerInfo(me);
        System.out.println("Your Personal ObjectiveCard: ");
        printObjectives(me.getPersonalObjectiveCard());
        showMyPlayArea(me.getPlayer().getPlayArea());
        //todo aggiungi mappa simboli
    }

    /**Method used to show the player info
     * @param client whose info we want to show*/
    public void showPlayerInfo(ClientControllerInterface client) {
        try {
            PawnColor color = client.getPawnColor();
            String ANSIcolor = GraphicUsage.pawnColorDictionary.get(color);
            String ANSIreset = "\u001B[0m";
            System.out.println(ANSIcolor + "Player: " + client.getNickname() +ANSIreset);
            System.out.println(ANSIcolor + "Score: " + client.getScore() + ANSIreset);
            System.out.println(ANSIcolor + "Round: " + client.getRound() + ANSIreset);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    /**Method to print the opponent play areas. Prints an empty space if no card has been added to the area.
     * @param opponent other player whose playArea we are printing*/
    public void showOpponentPlayArea(ClientControllerInterface opponent){
        try {
            if((opponent.getPlayer().getPlayArea().getCardsOnArea().size()>1))
                PrintPlayArea.DrawOthersPlayArea(opponent.getPlayer().getPlayArea());
            else
                System.out.println(" ");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public  void showCommonCards(ArrayList<ResourceCard> cards, ArrayList<GoldCard> goldCards, Deck resourceDeck, Deck goldDeck) {
        String[][] playGroundCards = new String[20][140]; //To adjust if we want the dimensions of the cards to be bigger
        // fill the playGroundCards with empty spaces
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 140; j++) {
                playGroundCards[i][j] = " ";
            }
        }
        //Add caption
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 140; j++) {
                if (i==0 && j==7){
                    playGroundCards[i][j] = "RESOURCE CARDS";
                }
                if (i==0 && j==27){
                    playGroundCards[i][j] = "GOLD CARDS";
                }

                if((i==1 && j==0) || (i==1 && j==30)){
                    playGroundCards[i][j] = "[1]";
                }
                if((i==10 && j==0) || (i==10 && j==30)){
                    playGroundCards[i][j] = "[2]";
                }
                if(i==1 && j==72){
                    playGroundCards[i][j] = "RESOURCE DECK";
                }
                if (i==10 && j==74){
                    playGroundCards[i][j] = "GOLD DECK";
                }

            }
        }
        //AddCards
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 140; j++) {
                if (i==2 && j==3)
                    DesignSupportClass.printResourceFront(playGroundCards, cards.get(0),i,j,7,25);
                if (i==2 && j==33)
                    DesignSupportClass.printGoldFront(playGroundCards, goldCards.get(0),i,j,7,25);

                if (i==11 && j==3)
                    DesignSupportClass.printResourceFront(playGroundCards, cards.get(1),i,j,7,25);
                if(i==11 && j==33)
                    DesignSupportClass.printGoldFront(playGroundCards, goldCards.get(1),i,j,7,25);

                if (i==2 && j==70)
                    DesignSupportClass.printBackCard(playGroundCards, (PlayCard) resourceDeck.getCards().getLast(), i, j,7,25);
                if(i==11 && j==70)
                    DesignSupportClass.printBackCard(playGroundCards, (PlayCard) goldDeck.getCards().getLast(), i, j,7,25);
            }
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 140; j++) {
                System.out.print(playGroundCards[i][j]);
            }
            System.out.println();
        }

    }


    /**Method used to show  tha playArea of the player that is calling the method*/
    @Override
    public void showMyPlayArea(PlayArea playArea) {
        if (playArea.getCardsOnArea().size() > 1)
            PrintPlayArea.DrawMyPlayArea(playArea); //if playArea has cards, draw the cards
        else
            System.out.println(" ");//else draw an empty space
    }
}







