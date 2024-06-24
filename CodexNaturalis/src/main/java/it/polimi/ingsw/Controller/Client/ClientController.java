package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Controller.Observer;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Model.Chat.PrivateMessage;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.GameStatus;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
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
import java.util.concurrent.*;

public class ClientController extends UnicastRemoteObject implements ClientControllerInterface, Observer {
    private UserInterface view;
    private GameControllerInterface game;
    private Player player = new Player();
    private MainControllerInterface server;
    private boolean ItsMyTurn;

    public ClientController() throws RemoteException {
        game=null;
        view=null;
    }
    @Override
    public void connect(String ip) throws RemoteException {
        server.connect(this);
        System.out.println("Connected to the server");
        JoinLobby();
    }


    @Override
    public void setGame(GameControllerInterface game) throws RemoteException {
        this.game = game;
    }

    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return player.getPersonalObjectiveCard();
    }


    /**getter method for PawnColor attribute in Player attribute
     * @return pawnColor that the player chose when joining the game */
    @Override
    public PawnColor getPawnColor() throws RemoteException{
        return player.getPawnColor();
    }


    /**
     * method that sets the PersonalObjective card of the client.
     *
     * @param objectiveCard chosen objective card
     * */
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) throws RemoteException {
        player.setPersonalObjectiveCard(objectiveCard);
    }

    /**Player attribute getter method
     * @return  player*/
    @Override
    public Player getPlayer() throws RemoteException {
        return player;
    }

    @Override
    public void showBoardAndPlayAreas(PlayGround model) throws RemoteException {
        ArrayList<Player> opponents= getOpponents();
            view.printBoard(model, opponents, this.player,viewMyChat() );
    }

    private ArrayList<Player> getOpponents() throws RemoteException {
        ArrayList<Player> opponents= new ArrayList<>();
        for(Player p: game.getPlayers()){
            if(!(p.getNickname().equals(this.player.getNickname()))){
                opponents.add(p);
            }
        }
        return opponents;
    }

    /**Getter method for Player's playArea attribute
     * @return PlayArea*/
    public PlayArea getPlayArea() throws RemoteException {
        return player.getPlayArea();
    }


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


    @Override
    public void sendUpdateMessage(String message) throws RemoteException{
        view.printMessage(message);
    }

    /**
     * Method used to add a card to a player's hand of cards.
     * Uses Model class Player
     * @param card to be added to the hand of cards
     * */
    @Override
    public void addCardToHand(PlayCard card) throws RemoteException {
        player.addCardToHand(card);
    }


    /**
     * Player's Score getter method
     * @return score
     * */
    @Override
    public int getScore() {
        return player.getScore();
    }

    /**
     * Player's Round getter method
     * @return round
     * */
    @Override
    public int getRound() throws RemoteException {
        return player.getRound();
    }

    public void setRound(int round) throws RemoteException{
        player.setRound(round);
    }

    @Override
    public void setServer(MainControllerInterface server) throws RemoteException {
        this.server=server;
    }

    /**Method that sets the view to GUI or TUI basing on the choice the client made in Client class
     * in SelectView method
     * @param view chosen */
    @Override
    public void setView(UserInterface view) throws RemoteException{
        this.view=view;
    }



    /**
     * Method that disconnects the player
     * */
        @Override
        public void disconnect() throws RemoteException {//todo do we need this?
        //TODO implement disconnection
        }



        /**
         * Method that allows the player to choose a player from a list of the available colors,
         * so the colors that have not already been taken by other players in the same game
         * */

        @Override
        public void ChoosePawnColor() throws RemoteException {
            List<PawnColor> availableColors = game.getAvailableColors();
            int choice = view.displayAvailableColors(availableColors);
            if(choice <= 0 || choice > availableColors.size()){
                System.out.println("Invalid color, please select a new One");
                ChoosePawnColor();
            } else {
                player.setPawnColor(availableColors.get(choice - 1));
                game.removeAvailableColor(availableColors.get(choice - 1));
            }
        }

    /**
     * Method that allows the player to set their nickname once the server verified that
     * no other player has already chosen it.
     *
     * @return nickname chosen by the player
     *
     */
        @Override
        public String ChooseNickname() throws RemoteException {
            return view.selectNickName();
        }



    /**
     * Method that allows the player to choose the game they want to join between a list of available ones.
     * availableGames are provided by MainController DisplayAvailableGames method.
     * If there are no available games to join, createGame method is called.
     * If the player chooses a game that is not on the list (control needed for TUI),
     * the player gets to choose again if they want to create a new game or join an existing one.
     */

    @Override
    public void JoinGame() throws RemoteException {
        Map<Integer, ArrayList<String>> availableGames= server.DisplayAvailableGames();
        if (availableGames.isEmpty()) {
            System.out.println("Sorry, there are no Available games, you have to start a new one");
            try {
                newGameSetUp();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            int chosenGame = view.displayavailableGames(availableGames, server.numRequiredPlayers());
            if (chosenGame == 0) {
                try {
                    newGameSetUp();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
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

    /**Getter method for Nickname attribute of Player attribute
     * @return  nickname that identifies the client */
    @Override
    public String getNickname() throws RemoteException{
        return player.getNickname();
    }

    @Override
    public void setNickname(String nickname) throws RemoteException {
        player.setNickname(nickname);
    }


    /**Method that allows the player to decide whether they want to create a new game or
     * join one that has already been created by another player*/
    @Override
    public void JoinOrCreateGame() throws RemoteException{
        view.showString("CODEX_NATURALIS");
        int choice = view.createOrJoin();
        switch (choice) {
            case 1 -> {
                try {
                    newGameSetUp();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }//Ask for desired number of players of the new game before creation
            case 2 -> {
                try {
                    JoinGame();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } //ask server for available games before choice
            default -> {
                System.out.println("Invalid choice ");
                JoinOrCreateGame();
            }


        }

    }

    /**Method called when a player wants to create a new game during the login phase.
     * It allows the player to choose how many players they want for the new game.
     * The game is created only if the number of players is between 2 and 4.
     * The actual creation of the game is handled by the server.*/
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


    /**Method that tells the player that more players have to join the game in order to start.
     * Uses UserInterface method */
    @Override
    public void Wait() throws RemoteException {
        view.waitingForPlayers();
    }




    /**
     * Method used by the player to join the lobby
     * */
    @Override
    public void JoinLobby() throws RemoteException {
        String name = ChooseNickname();
        try {
            boolean ok = server.checkUniqueNickName(name);
            if (ok) {
                setNickname(name);
                server.addNickname(name);
            } else {
                System.out.println("Nickname already taken, checking for previous game state...");
                GameState gameState = game.loadGameState(name);
                if (gameState != null && !gameState.getPlayerByNickname(name).isReconnected()) {
                    System.out.println("Found previous game state for this nickname, rejoining...");
                    gameState.getPlayerByNickname(name).setReconnected(true);
                    game.loadGameState(name);
                    game.GameLoop(game.getStatus());
                } else {
                    System.out.println("No previous game state found or player already reconnected, please choose a new nickname");
                    JoinLobby();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error in the connection");
        }
        JoinOrCreateGame();
    }

    @Override
    public void WhatDoIDoNow(String doThis) throws RemoteException {
        switch(doThis){
            case("INITIALIZE")->{//the player gets its hand of cards, chooses its personalObjective, and gets its InitialCard.
                view.printBoard(game.getModel(), getOpponents(), player, viewMyChat());
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
                view.printBoard(game.getModel(), getOpponents(), player, viewMyChat());
                Command c;
                do {
                    c = view.receiveCommand(true);
                    switch (c) {
                        case MOVE -> playMyTurn();
                        case CHAT -> sendChatMessage();
                    }
                }while(c!=Command.MOVE);
            }
            case("NOT-MY-TURN")-> {
                ItsNotMyTurn();

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
     * Method that shows the Player only the message they sent or that was intended for them
     *
     * @return          ArrayList of Messages that are either sent by the player
     *                  or are private messages directed to this player
     */

    private ArrayList<Message> viewMyChat() {
        try {
            ArrayList<Message> chat= game.getChat().getMessage(); //retrieve the list of messages from the game chat
            ArrayList<Message> myChat= new ArrayList<>();   // create a new ArrayList for the messages I can see:
                                                            // some messages are private not directed to me
            for(Message m: chat) {//Iterate through the chat messages
                if(!(m instanceof PrivateMessage) || //if the message is a broadcast message
                        ((PrivateMessage) m).getReceiver().equals(getNickname()) ||  // if it is a private message and I'm the receiver
                         m.getSender().getNickname().equals(player.getNickname())){ //  I'm the sender
                    myChat.add(m);//add the message to the list of messages I can see
                }
            }
            return myChat; //return the list of messages I can see
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Method that allows the player to choose its private Objective as per game rules.
     * A list of two cards is given, and the player sets its personal objective to the one it chooses.
     * */
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
     *
     * Method that distributes the initial hand of cards to the player.

     * */

    private synchronized void getMyHandOfCards(){
        try {
            for(PlayCard card: game.extractPlayerHandCards())
                player.addCardToHand(card);
        } catch (RemoteException e) {
            System.out.println("an error occurred when extracting the initial hand of cards");
            //todo add error handling
        }

    }

    private void playMyInitialCard() throws RemoteException {
        view.printMessage("It's your turn to play the initial card!");
        game.getListener().updatePlayers("It's " + getNickname() + "'s turn to choose the initial card!", this);
        view.showInitialCard(player.getInitialCard());
        Side side = Side.valueOf(view.chooseSide());
        getPlayArea().addInitialCardOnArea(player.getInitialCard().chooseSide(side));
        game.getListener().updatePlayers(getNickname()+ " has played the initial card.", this);
    }



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
                //todo add error handling
            }

        }

    }


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


    private void sendChatMessage() throws RemoteException {
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RESET = "\u001B[0m";
        Pair<String, String> mex= view.sendChatMessage(getOpponents());
        Message message= new Message(mex.getSecond());
        message.setSender(player);
        char envelope = '\u2709';
        String bold = "\033[1m";
        String reset = "\033[0m";
        String s2= message.getText();
        if(mex.getFirst().equals("everyone")){
            try {
                game.getListener().updatePlayers("\n"+ANSI_CYAN+bold+envelope+"["+ this.getNickname()+"] to [ALL] : "+s2+"\n"+reset+ANSI_RESET, this);
                game.addMessageToChat(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                PrivateMessage privateMessage= new PrivateMessage(mex.getSecond());
                privateMessage.setSender(player);
                privateMessage.setReceiver(mex.getFirst());
                game.sendPrivateMessage(message, mex.getFirst());
                game.addMessageToChat(privateMessage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

   /**
     * Method that adds the Common Objective Cards points scored by the player.
     * */

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
     * Calculates and adds the points from the personal objective card to each player's score.
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

    private void ItsNotMyTurn() throws RemoteException {
//        Command c = view.receiveCommand(false);
//        while(c==Command.CHAT){
//            sendChatMessage();
//            c = view.receiveCommand(false);
//        }
    }




}
