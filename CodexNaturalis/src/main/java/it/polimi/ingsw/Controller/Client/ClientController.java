package it.polimi.ingsw.Controller.Client;

import it.polimi.ingsw.Controller.Game.GameController;
import it.polimi.ingsw.Controller.Game.GameControllerInterface;
import it.polimi.ingsw.Controller.Main.MainControllerInterface;
import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.UserInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends UnicastRemoteObject implements ClientControllerInterface{
    private UserInterface view;
    private GameControllerInterface game;
    private Player player = new Player();
    private MainControllerInterface server;

    public ClientController(MainControllerInterface server) throws RemoteException {
        this.server=server;
        game=null;
        view=null;
    }

    /**getter method for PawnColor attribute in Player attribute
     * @return pawnColor that the player chose when joining the game */
    public PawnColor getPawnColor() throws RemoteException{
        return player.getPawnColor();
    }



    @Override
    public void updatePlayers(List<ClientControllerInterface> players)  throws RemoteException{
        //TODO implement this
    }


    /**method that sets the PersonalObjective card of the client.
     * @param objectiveCard chosen objective card*/
    @Override
    public void setPersonalObjectiveCard(ObjectiveCard objectiveCard) {
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
        game.setModel(model);
        view.showBoardAndPlayAreas();
    }

    @Override
    public PlayCard chooseCardToDraw(PlayGround model) throws RemoteException {
        return view.chooseCardToDraw(model);
    }

    @Override
    public void receiveCommand() throws RemoteException {

        Command c=  view.receiveCommand();
        game.receiveMessage(c, this);
    }

    @Override
    public String chooseSideInitialCard(InitialCard c) {
        return view.chooseSideInitialCard(c);
    }

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) throws RemoteException {
        return view.choosePersonaObjectiveCard(objectives);
    }

    @Override
    public void printInitialMessage() {
        System.out.println("This is the initial board of the game: \n");
    }

    @Override
    public void printFinalMessage() {
        System.out.println("The game is over!");
    }

    @Override
    public void printFinalScoresMessage(List<ClientControllerInterface> players) throws RemoteException {
        System.out.println("Those are the final scores: \n");
        for(ClientControllerInterface p: players){
            System.out.println(p.getNickname() + " : " + p.getPlayer().getScore());
        }
    }

    @Override
    public void printTurnMessage(String nickname) {
        System.out.println("It's your turn " + nickname + "!");
    }

    @Override
    public void printInitialCardMessage(String nickname) {
        System.out.println(nickname + "has played his initial card.");
    }

    @Override
    public void printObjectiveCardMessage(String nickname) {
        System.out.println(nickname + "has chosen his personal Objective card.");
    }

    @Override
    public void printWinnerMessage(List<ClientControllerInterface> winners) {
        System.out.println("The winner is: ");
        for(ClientControllerInterface p: winners){
            System.out.println(p.getNickname());
        }
    }

    @Override
    public void printWinnersMessage(List<ClientControllerInterface> winners) {
        System.out.println("Tie between the following players: ");
        for(ClientControllerInterface p: winners){
            System.out.println(p.getNickname());
        }
    }

    /**Method that sets the view to GUI or TUI basing on the choice the client made in Client class
     * in SelectView method
     * @param view chosen */
    @Override
    public void setView(UserInterface view) {
        this.view=view;
    }

    /**Method that disconnects the player*/
        @Override
        public void disconnect() throws RemoteException {
        //TODO implement disconnection
        }

        /**Method that allows the player to choose a player from a list of the available colors,
         * so the colors that have not already been taken by other players in the same game
         * * @param availableColors colors that haven't already been taken*/

        @Override
        public void ChoosePawnColor(ArrayList<PawnColor> availableColors) throws RemoteException {
            int choice =view.displayAvailableColors(availableColors);
            if(choice<=0 || choice> availableColors.size()){
                System.out.println( "Invalid color, please select a new One");
                ChoosePawnColor(availableColors);
            }
            else
                player.setPawnColor(availableColors.get(choice-1));


        }

/**
 * Method that allows the player to set their nickname once the server verified that
 * no other player has already chosen it
 */
    @Override
    public void ChooseNickname() throws RemoteException {
        String nickname=view.selectNickName();
        server.checkUniqueNickName(nickname, this);


    }



    /**Method that allows the player to choose the game they want to join between a list of available ones.
     * availableGames are provided by MainController DisplayAvailableGames method.
     * If there are no available games to join, createGame method is called.
     * If the player chooses a game that is not on the list (control needed for TUI),
     * the player gets to choose again if they want to create a new game or join an existing one.
     * @param availableGames games the player can join. Only the ones that do not already have all the needed players*/
    @Override
    public void getGameToJoin(ArrayList<GameController> availableGames) throws RemoteException {
        if (availableGames.isEmpty()) {
            System.out.println("Sorry, there are no Available games, you have to start a new one");
            try {
                newGameSetUp();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            int chosenGame = view.displayavailableGames(availableGames);
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

                game=availableGames.get(chosenGame - 1); //TODO check this
                try {
                    server.joinGame(this,game.getId()); //TODO check this
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    /**Getter method for Nickname attribute of Player attribute
     * @return  nickname that identifies the client */
    @Override
    public String getNickname() {
        return player.getNickname();
    }


    /**Method that allows the player to decide whether they want to create a new game or
     * join one that has already been created by another player*/
    @Override
    public void JoinOrCreateGame() {
        int choice = view.createOrJoin();
        switch (choice) {
            case 0 -> {
                try {
                    newGameSetUp();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }//Ask for desired number of players of the new game before creation
            case 1 -> {
                try {
                    server.DisplayAvailableGames(this);
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
            server.createGame(this, n);
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
        try {
            view.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
