package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Controller.Observer;
import it.polimi.ingsw.Model.Cards.*;
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
import java.util.Scanner;

public class ClientController extends UnicastRemoteObject implements ClientControllerInterface, Observer {
    private UserInterface view;
    private GameControllerInterface game;
    private Player player = new Player();
    private MainControllerInterface server;

    public ClientController() throws RemoteException {
        game=null;
        view=null;
    }
    @Override
    public void connect() throws RemoteException {
        server.connect(this);
        System.out.println("Connected to the server");
        JoinLobby();
    }

    public void setGame(GameControllerInterface game) throws RemoteException {
        this.game = game;
    }

    @Override
    public ObjectiveCard getPersonalObjectiveCard() throws RemoteException {
        return player.getPersonalObjectiveCard();
    }


    /**getter method for PawnColor attribute in Player attribute
     * @return pawnColor that the player chose when joining the game */
    public PawnColor getPawnColor() throws RemoteException{
        return player.getPawnColor();
    }


    /**method that sets the PersonalObjective card of the client.
     * @param objectiveCard chosen objective card*/
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
            view.printBoard(model, opponents, this.player);
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
    public void chooseCardToDraw(PlayGround model) throws RemoteException {
        PlayCard card;
        view.printMessage("This is the Playground: ");
        showBoardAndPlayAreas(model);
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
    }


    @Override
    public SideOfCard chooseCardToPlay() throws RemoteException {
        SideOfCard card = null;
        boolean requirements = false;

        do {
            int indexOfCard = (view.chooseCardToPlay(getPlayer().getCardsInHand())) - 1;
            String side = view.chooseSide();

            PlayCard playCard = getPlayer().getCardsInHand().get(indexOfCard);

            card = getPlayer().ChooseCardToPlay(playCard, Side.valueOf(side));

            if ((playCard) instanceof GoldCard){
                GoldCard goldCard = (GoldCard) playCard;
                if (!(goldCard.checkRequirement(getPlayer().getPlayArea().getSymbols(), Side.valueOf(side)))) {
                    view.printMessage("You can't play this card, the requirement is not met!");
                } else {
                    requirements= true;
                }
            } else {
                requirements = true;
            }
        } while (!requirements);

        return card;

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
    public int getRound() {
        return player.getRound();
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
                sendUpdateMessage("It's your turn!");
                view.printBoard(game.getModel(), getOpponents(), player);
                Command c= view.receiveCommand();
                switch(c){
                    case MOVE -> playMyTurn();
                    case CHAT -> sendChatMessage();
                    case VIEWCHAT -> view.viewChat();
                }
                if (getScore()>=20){
                    game.setStatus(GameStatus.LAST_CIRCLE);
                }
            }
            case("NOT-MY-TURN")->{ //the player can only chat
                ItsNotMyTurn();
            }
            case("OBJECTIVE COUNT")->{ //the player has to count its personal objective
                //return the count to the server
            }
            case("WINNER")->{ //the player has won
                view.showString("WIN");
                view.printMessage("Here's the final ranking: ");
            }
            case("LOSER")->{ //the player has lost
                //TODO schermata con classifica
                view.showString("GAME_OVER");
                view.printMessage("Sorry, you have lost the game!");
                view.printMessage("Here's the final ranking: ");

            }
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
            game.getListener().updatePlayers(getNickname()+ " has chosen the personal objective card ", this);
        } catch (RemoteException e) {
            System.out.println("an error occurred when extracting the personal objective card");
        }

    }


    /**
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
        view.showInitialCard(player.getInitialCard());
        Side side = Side.valueOf(view.chooseSide());
        getPlayArea().addInitialCardOnArea(player.getInitialCard().chooseSide(side));
        game.getListener().updatePlayers(getNickname()+ " has played the initial card ", this);
    }



    private void playMyCard(){
        int n= view.chooseCardToPlay(player.getCardsInHand());
        PlayCard card= player.getCardsInHand().get(n);
        Side side = Side.valueOf(view.chooseSide());
        SideOfCard sideOfCard= card.chooseSide(side);
        if(card instanceof GoldCard){
            if(!((GoldCard) card).checkRequirement(player.getPlayArea().getSymbols(), side)){
                view.printMessage("You can't play this card, the requirement is not met!");
                playMyCard();
            }
        }
        Pair<Integer, Integer> position= view.choosePositionCardOnArea(player.getPlayArea());
        try {
            while(true) {
                if (game.isValidMove(getPlayArea(), position.getFirst(), position.getSecond(), sideOfCard)) {
                    player.getPlayArea().addCardOnArea(sideOfCard, position.getFirst(), position.getSecond());
                    break;
                } else {
                    view.printMessage("Oops, the position is not correct according to the game rules. Please choose a valid position.");
                    position = view.choosePositionCardOnArea(player.getPlayArea());
                }
            }
        } catch (RemoteException e) {
            //todo add error handling
        }
    }


    private void playMyTurn() throws RemoteException {
        playMyCard();
        game.getListener().updatePlayers(getNickname() + " has played a card.", this);
        chooseCardToDraw(game.getModel());
        game.getListener().updatePlayers("This is the current Playground: ");
        game.getListener().updatePlayers(game.getModel());

        //client has all the options: move, chat, view full chat
    }


    private void sendChatMessage() {
        //todo implement this
        //method in view that returns a string
        //method in view that returns a nickname or an "all" string for broadcast message
        //method in server that sends the message to all the players
    }

    private void ItsNotMyTurn(){

        //TODO implement this
        //client can only chat
    }



}
