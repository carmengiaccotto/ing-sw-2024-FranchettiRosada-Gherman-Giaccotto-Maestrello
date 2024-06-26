/**
 * This package contains the client-side controller for the game.
 */
package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Exceptions.GameJoinException;
import it.polimi.ingsw.Model.Exceptions.NicknameException;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the client-side controller for the game.
 * It extends UnicastRemoteObject and implements ClientControllerInterface.
 */
public class ClientController extends UnicastRemoteObject implements ClientControllerInterface {
    private UserInterface view;
    private GameControllerInterface game;
    private Player player = new Player();
    private MainControllerInterface server;
    private boolean ItsMyTurn;


    /**
     * Constructor for the ClientController class.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    public ClientController() throws RemoteException {
        game=null;
        view=null;
    }

    /**
     * This method is used to connect the client to the server.
     * @param ip The IP address of the server.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void connect(String ip) throws RemoteException {
        server.connect(this);
        System.out.println("Connected to the server");
        JoinLobby();
    }

    /**
     * This method is used to set the game controller for the client.
     * @param game The game controller to be set for the client.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        this.game = game;
    }

    /**
     * This method is used to get the personal objective card of the player.
     * @return ObjectiveCard representing the personal objective card of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return player.getPersonalObjectiveCard();
    }

    /**
     * This method is used to get the color of the pawn of the player.
     * @return PawnColor representing the color of the pawn of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public PawnColor getPawnColor() throws RemoteException{
        return player.getPawnColor();
    }

    /**
     * Sets the personal objective card for the player.
     *
     * @param objectiveCard The objective card to be set for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {
        player.setPersonalObjectiveCard(objectiveCard);
    }

    /**
     * Retrieves the player.
     *
     * @return Player object representing the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public Player getPlayer() throws RemoteException {
        return player;
    }

    /**
     * Displays the board and play areas.
     *
     * @param model The playground to be displayed.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void showBoardAndPlayAreas(PlayGround model) throws RemoteException {
        ArrayList<Player> opponents= getOpponents();
        view.printBoard(model, opponents, this.player);
    }

    /**
     * This method is used to get the opponents of the player.
     * @return ArrayList<Player> representing the opponents of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    private ArrayList<Player> getOpponents() throws RemoteException {
        ArrayList<Player> opponents= new ArrayList<>();
        for(Player p: game.getPlayers()){
            if(!(p.getNickname().equals(this.player.getNickname()))){
                opponents.add(p);
            }
        }
        return opponents;
    }

    /**
     * This method is used to get the play area of the player.
     * @return PlayArea representing the play area of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    public PlayArea getPlayArea() throws RemoteException {
        return player.getPlayArea();
    }


    /**
     * Allows the player to choose a card to draw from the playground.
     * The player can choose from the gold deck, resource deck, or the common resource or gold cards.
     * If the player makes an invalid choice, they are prompted to make a valid selection.
     * The chosen card is added to the player's hand.
     *
     * @param model The playground from which the card is to be drawn.
     * @return PlayGround object after the card has been drawn.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public PlayGround chooseCardToDraw(PlayGround model) throws RemoteException {
        PlayCard card;
//        view.printMessage("This is the Playground: ");
//        showBoardAndPlayAreas(model);
        String draw = view.chooseCardToDraw();
        switch (draw) {

            case "GOLD-DECK":
                card = (GoldCard) model.getGoldCardDeck().drawCard();

                break;

            case "RESOURCE-DECK":
                card = (ResourceCard) model.getResourceCardDeck().drawCard();
                break;

            case "RESOURCE-CARD1":
                card =  model.getCommonResourceCards().getFirst();
                model.drawCardFromPlayground(0,card);
                break;

            case "RESOURCE-CARD2":
                card =  model.getCommonResourceCards().get(1);
                model.drawCardFromPlayground(1,card);
                break;

            case "GOLD-CARD1":
                card =  model.getCommonGoldCards().getFirst();
                model.drawCardFromPlayground(0,card);
                break;

            case "GOLD-CARD2":
                card =  model.getCommonGoldCards().get(1);
                model.drawCardFromPlayground(1,card);
                break;

            default: {
                view.printMessage("Invalid choice, please select a valid one");
                chooseCardToDraw(model);
                card=null;
                break;
            }
        }
        player.addCardToHand(card);

        return model;
    }

    /**
     * Sends an update message to the player.
     *
     * @param message The message to be sent.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void sendUpdateMessage(String message) throws RemoteException{
        view.printMessage(message);
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to be added to the player's hand.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
        player.addCardToHand(card);
    }

    /**
     * Retrieves the score of the player.
     *
     * @return int representing the score of the player.
     */
    @Override
    public int getScore() {
        return player.getScore();
    }

    /**
     * Retrieves the current round of the game.
     *
     * @return int representing the current round of the game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public int getRound() throws RemoteException {
        return player.getRound();
    }

    /**
     * Sets the current round of the game.
     *
     * @param round The round to be set for the game.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    public void setRound(int round) throws RemoteException{
        player.setRound(round);
    }

    /**
     * Sets the server for the client.
     *
     * @param server The server to be set for the client.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        this.server=server;
    }

    /**
     * Sets the view to GUI or TUI based on the choice the client made in Client class in SelectView method.
     *
     * @param view The view to be set for the client.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void setView(UserInterface view) throws RemoteException{
        this.view=view;
    }

    /**
     * Disconnects the player.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void disconnect() throws RemoteException {//todo do we need this?
        //TODO implement disconnection
    }

    /**
     * Allows the player to choose a color for their pawn from the list of available colors.
     * The player is prompted to make a selection, and if the selection is invalid, they are asked to choose again.
     * Once a valid selection is made, the chosen color is set for the player's pawn and removed from the list of available colors.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void ChoosePawnColor() throws RemoteException {
        List<PawnColor> availableColors;

        synchronized (game.getAvailableColors()) {
            // Fetch and display available colors inside synchronized block
            availableColors = new ArrayList<>(game.getAvailableColors());
            System.out.println("Available colors before choosing: " + availableColors.size());
            view.displayAvailableColors(availableColors);//todo per socket è equivalente chiamare view o metodo client?
        }

        int choice = -1;
        boolean validChoice = false;

        while (!validChoice) {
            int input = view.getInput(); // This method should handle retrieving the input from the user

            synchronized (game.getAvailableColors()) {
                // Fetch the latest list of available colors to ensure up-to-date information
                availableColors = new ArrayList<>(game.getAvailableColors());

                if (input > 0 && input <= availableColors.size()) {
                    choice = input;
                    validChoice = true;
                } else {
                    System.out.println("Invalid color, please select a valid one.");
                    view.displayAvailableColors(availableColors);
                }
            }
        }

        // Fetch the chosen color
        PawnColor chosenColor = availableColors.get(choice - 1);

        // Communicate with the server to remove the chosen color from the main list
        synchronized (game.getAvailableColors()) {
            player.setPawnColor(chosenColor);
            game.removeAvailableColor(chosenColor);

            //message.append("Player ").append(getNickname()).append(" has chosen the color ").append(chosenColor).append("\n");


            // Update the local list of available colors
            availableColors.remove(chosenColor);
        }

        System.out.println("You have chosen the color " + chosenColor);
    }

    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) throws RemoteException {
        view.displayAvailableColors(availableColors);
    }

    /**
     * This method allows the player to set their nickname. The server verifies that
     * no other player has already chosen the nickname. If the nickname is unique, it is set for the player.
     *
     * @return String representing the nickname chosen by the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public String ChooseNickname() throws RemoteException {
        return view.selectNickName();
    }

    /**
     * This method allows the player to join a game.
     * It first retrieves a list of available games from the server.
     * If there are no available games, the player is prompted to set up a new game.
     * If there are available games, the player is prompted to choose a game to join.
     * If the player chooses a game that is not on the list, they are asked to choose again.
     * Once a valid game is chosen, the player joins the game, chooses a color for their pawn,
     * and a notification is sent to the server that the player has joined the game.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void JoinGame() throws RemoteException {
        Map<Integer, ArrayList<String>> availableGames= server.DisplayAvailableGames();
        if (availableGames.isEmpty()) {
            System.out.println("Sorry, there are no Available games, you have to start a new one");
            try {
                newGameSetUp();
            } catch (RemoteException e) {
                throw new GameJoinException("Error while setting up a new game", e);
            }
        } else {
            int chosenGame = view.displayavailableGames(availableGames, server.numRequiredPlayers());
            if (chosenGame == 0) {
                try {
                    newGameSetUp();
                } catch (RemoteException e) {
                    throw new GameJoinException("Error while setting up a new game", e);
                }
            } else if (chosenGame < 0 || chosenGame > availableGames.size()) {
                System.out.println("Invalid game, please select a valid one, or start a new game");
                JoinOrCreateGame();

            } else {
                server.joinGame(this, chosenGame-1);
                ChoosePawnColor();
                server.NotifyGamePlayerJoined(game, this);
            }
        }


    }

    /**
     * Retrieves the nickname of the player.
     *
     * @return String representing the nickname of the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public String getNickname() throws RemoteException{
        return player.getNickname();
    }

    /**
     * Sets the nickname for the player.
     *
     * @param nickname The nickname to be set for the player.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void setNickname(String nickname) throws RemoteException {
        player.setNickname(nickname);
    }

    /**
     * This method allows the player to decide whether they want to create a new game or
     * join one that has already been created by another player.
     * The player is presented with the options to either create a new game or join an existing one.
     * If the player chooses to create a new game, the newGameSetUp() method is called.
     * If the player chooses to join an existing game, the JoinGame() method is called.
     * If the player makes an invalid choice, they are prompted to make a valid selection.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void JoinOrCreateGame() throws RemoteException{
        view.showString("CODEX_NATURALIS");
        int choice = view.createOrJoin();
        switch (choice) {
            case 1 -> {
                try {
                    newGameSetUp();
                } catch (RemoteException e) {
                   throw new GameJoinException("Error while setting up a new game", e);
                }
            }//Ask for desired number of players of the new game before creation
            case 2 -> {
                try {
                    JoinGame();
                } catch (RemoteException e) {
                    throw new GameJoinException("Error while joining a new game", e);
                }
            } //ask server for available games before choice
            default -> {
                System.out.println("Invalid choice ");
                JoinOrCreateGame();
            }


        }

    }

    /**
     * This method is called when a player wants to create a new game during the login phase.
     * It allows the player to choose the number of players they want for the new game.
     * The game is created only if the number of players is between 2 and 4.
     * The actual creation of the game is handled by the server.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void newGameSetUp() throws RemoteException {
        int n=view.MaxNumPlayers();
        if(n>=2 && n<=4) {
            server.createGame(this,n);
            ChoosePawnColor();
            server.NotifyGamePlayerJoined(game, this);
        }
        else{
            System.out.println("Invalid number of players");
            newGameSetUp();
        }

    }

    /**
     * Informs the player that more players need to join the game before it can start.
     * This method uses the UserInterface method to display the waiting message.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void Wait() throws RemoteException {
        view.waitingForPlayers();
    }

    /**
     * This method is used by the player to join the lobby.
     * The player chooses a nickname and the server checks if it's unique.
     * If the nickname is already taken, the server checks for a previous game state.
     * If a previous game state is found and the player hasn't reconnected yet, the player rejoins the game.
     * If no previous game state is found or the player has already reconnected, the player is asked to choose a new nickname.
     * After successfully setting a nickname, the player is asked to join or create a game.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void JoinLobby() throws RemoteException {
        String name = ChooseNickname();
        try {
            boolean ok=server.checkUniqueNickName(name);
            if(ok){
                setNickname(name);
                server.addNickname(name);
            }
            else{
                System.out.println("Nickname already taken, please choose a new one");
                JoinLobby();
            }
        } catch (IOException e) {
            throw new NicknameException("Error while checking nickname", e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error in the connection");
        }
        JoinOrCreateGame();
    }


    /**
     * This method is called when the player needs to perform an action based on the current game state.
     * The possible actions are: initialize, play the initial card, play a turn, count the personal objective,
     * and handle the end of the game (either as a winner or a loser).
     *
     * @param doThis A string representing the action the player needs to perform.
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {
        switch(doThis){
            case("INITIALIZE")->{//the player gets its hand of cards, chooses its personalObjective, and gets its InitialCard.
                view.printBoard(game.getModel(), getOpponents(), player);
                getMyObjectiveCard();
                getMyHandOfCards();
                player.setInitialCard(game.extractInitialCard());
                //Now we are ready to start Playing
            }
            case("INITIAL")->{ //the player plays its initial card
                try {
                    playMyInitialCard();
                } catch (RemoteException e) {
                    System.out.println("An error occurred when playing the initial card");
                }
            }
            case("PLAY-TURN")->{ //the player plays a card
                ItsMyTurn=true;
                sendUpdateMessage("It's your turn!");
                player.IncreaseRound();
                view.printBoard(game.getModel(), getOpponents(), player);
                Command c;
                do {
                    c = view.receiveCommand(true);
                    switch (c) {
                        case MOVE -> playMyTurn();
                    }
                }while(c!=Command.MOVE);
            }
            case("OBJECTIVE-COUNT")->{ //the player has to count its personal objective
                addCommonObjectiveCardsPoints();
                addPersonalObjectiveCardPoints();
            }
            case("WINNER")->{ //the player has won
                view.showString("WIN");
                view.printMessage("Here's the final ranking: ");
                view.printMessage(game.finalRanking());
            }
            case("LOSER")->{ //the player has lost
                view.printMessage("Sorry, you have lost the game!");
                view.printMessage("Here's the final ranking: ");
                view.printMessage(game.finalRanking());
                view.showString("GAME_OVER");

            }
        }

    }

    /**
     * This method allows the player to choose their private Objective as per game rules.
     * The player is given a list of two cards and they choose one to set as their personal objective.
     * After the player has chosen their card, the game is updated to reflect that the player has chosen their objective.
     * If all players have chosen their objectives, a message is sent to all players.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    private synchronized void getMyObjectiveCard(){
        try {
            ArrayList<ObjectiveCard> objectives=game.getPersonalObjective();
            int n=view.choosePersonaObjectiveCard(objectives);
            player.setPersonalObjectiveCard(objectives.get(n));
            game.getListener().updatePlayers(getNickname()+ " has chosen the personal objective card.", this);
            game.incrementPlayersWhoChoseObjective();
            if(game.getPlayersWhoChoseObjective() < game.getPlayers().size()){
                view.printMessage("Waiting for other players to choose their personal objective card...");
            }
            else{
                game.getListener().updatePlayers("All players have chosen their personal objective card.");
            }
        } catch (RemoteException e) {
            System.out.println("an error occurred when extracting the personal objective card");
        }

    }

    /**
     * This method is responsible for distributing the initial hand of cards to the player.
     * It retrieves a set of cards from the game and adds them to the player's hand.
     * If a RemoteException occurs during the execution of the method, an error message is printed.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    private synchronized void getMyHandOfCards(){
        try {
            for(PlayCard card: game.extractPlayerHandCards())
                player.addCardToHand(card);
        } catch (RemoteException e) {
            System.out.println("An error occurred when extracting the initial hand of cards");
        }

    }

    /**
     * This method is used when the player needs to play their initial card.
     * It first sends a message to the player indicating that it's their turn to play the initial card.
     * Then, it updates all players about the current player's turn to choose the initial card.
     * The player is then shown their initial card and asked to choose a side for the card.
     * The chosen side of the card is then added to the player's play area.
     * Finally, it updates all players that the current player has played their initial card.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    private void playMyInitialCard() throws RemoteException {
        view.printMessage("It's your turn to play the initial card!");
        game.getListener().updatePlayers("It's " + getNickname() + "'s turn to choose the initial card!", this);
        view.showInitialCard(player.getInitialCard());
        Side side = Side.valueOf(view.chooseSide());
        getPlayArea().addInitialCardOnArea(player.getInitialCard().chooseSide(side));
        game.getListener().updatePlayers(getNickname()+ " has played the initial card.", this);
    }

    /**
     * This method allows the player to play a card during their turn.
     * It first prompts the player to choose a card from their hand to play.
     * If the chosen card is a GoldCard, it checks if the player has sufficient resources on their play area to play the card.
     * If the player does not have sufficient resources, they are prompted to choose another card.
     * If the player has sufficient resources, or if the chosen card is not a GoldCard, the player is asked to choose a side for the card.
     * The player then chooses a position on their play area to place the card.
     * If the chosen position is not valid according to the game rules, the player is prompted to choose a valid position.
     * Once a valid position is chosen, the card is added to the player's play area and the player's score is increased by the points of the card.
     * If a RemoteException occurs during the execution of the method, an error message is printed.
     */
    private void playMyCard(){
        Boolean isValidPlay= true;
        int n= view.chooseCardToPlay(player.getCardsInHand());
        PlayCard card= player.getCardsInHand().get(n-1);
        Side side = Side.valueOf(view.chooseSide());
        if (card instanceof GoldCard){
            isValidPlay=((GoldCard) card).checkRequirement(player.getPlayArea().getSymbols(), side);

        }
        if(!isValidPlay){
            view.printMessage("Sorry, insufficient resources on the playArea, please choose another card");
            playMyCard();
        }
        else{
            SideOfCard sideOfCard=player.ChooseCardToPlay(card, side);// player method that already removes the card from the hand
            Pair<Integer, Integer> position;
            try {
                do{
                    position = view.choosePositionCardOnArea(player.getPlayArea());
                    if(!game.isValidMove(getPlayArea(), position.getFirst(), position.getSecond(), sideOfCard)){
                        view.printMessage("Oops, the position is not correct according to the game rules. Please choose a valid position.");
                    }
                }while(!game.isValidMove(getPlayArea(), position.getFirst(), position.getSecond(), sideOfCard));
                player.getPlayArea().addCardOnArea(sideOfCard, position.getFirst(), position.getSecond());
                player.increaseScore(card.getPoints(side));
            } catch (RemoteException e) {
                System.out.println("An error occurred when playing the card");
            }

        }

    }

    /**
     * This method is used when the player needs to play their turn.
     * It first prompts the player to play a card from their hand.
     * Then, it sends an update to all players indicating that the current player has played a card.
     * If the game is still running, the player is prompted to choose a card to draw from the playground.
     * The playground is then updated with the chosen card.
     * Finally, it sends an update to all players showing the current state of the playground.
     *
     * @throws RemoteException if a communication-related exception occurred during the execution of a remote method call
     */
    private void playMyTurn() throws RemoteException {
        playMyCard();
        game.getListener().updatePlayers(getNickname() + " has played a card.", this);
        if(game.getStatus() == GameStatus.RUNNING) {
            PlayGround model = chooseCardToDraw(game.getModel());
            game.setModel(model);
            game.getListener().updatePlayers("This is the current Playground: ");
            game.getListener().updatePlayers(game.getModel());
        }
    }

    /**
     * This method calculates and adds the points scored by the player from the Common Objective Cards.
     * It iterates over the common objective cards in the game model.
     * If the card is a SymbolObjectiveCard, it calculates the number of goals the player has achieved and the points they have earned.
     * If the card is a DispositionObjectiveCard, it calculates the number of dispositions the player has got and the points they have earned.
     * The points are then added to the player's score.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void addCommonObjectiveCardsPoints() throws RemoteException {
        for (ObjectiveCard card : game.getModel().getCommonObjectivesCards()) {
            if (card instanceof SymbolObjectiveCard s) {
                int numOfGoals = s.CheckGoals(getPlayer().getPlayArea().getSymbols());
                int points = s.calculatePoints(numOfGoals);
                getPlayer().increaseScore(points);
            } else {
                DispositionObjectiveCard s = (DispositionObjectiveCard) card;
                int numOfGoals = s.CheckGoals(getPlayer().getPlayArea());
                int points = s.calculatePoints(numOfGoals);
                getPlayer().increaseScore(points);
            }
        }
    }

    /**
     * This method calculates and adds the points scored by the player from their Personal Objective Card.
     * It checks the type of the Personal Objective Card.
     * If the card is a SymbolObjectiveCard, it calculates the number of goals the player has achieved and the points they have earned.
     * If the card is a DispositionObjectiveCard, it calculates the number of dispositions the player has got and the points they have earned.
     * The points are then added to the player's score.
     *
     * @throws RemoteException If a remote or network communication error occurs.
     */
    public void addPersonalObjectiveCardPoints() throws RemoteException {
        if (getPlayer().getPersonalObjectiveCard() instanceof SymbolObjectiveCard card) {
            int numOfGoals = card.CheckGoals(getPlayer().getPlayArea().getSymbols());
            int points = card.calculatePoints(numOfGoals);
            getPlayer().increaseScore(points);
        } else {
            DispositionObjectiveCard card = (DispositionObjectiveCard) getPlayer().getPersonalObjectiveCard();
            int numOfGoals = card.CheckGoals(getPlayer().getPlayArea());
            int points = card.calculatePoints(numOfGoals);
            getPlayer().increaseScore(points);
        }
    }
}