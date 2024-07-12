package it.polimi.ingsw.View.FX;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.FX.ui.*;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class represents the main application for the Codex Naturalis game in the FX view.
 * It extends the Application class from JavaFX and implements the UserInterface interface.
 */
public class FXCodexNaturalisApp extends Application implements UserInterface {

    /**
     * This static initializer block is used to start up the JavaFX platform.
     */
    static
    {
        Platform.startup(() -> {});
    }

    /**
     * The main user interface for the application.
     */
    private FXMainUI mainUI;

    /**
     * The initial card for the game.
     */
    private InitialCard card;

    /**
     * A flag indicating whether the waiting UI has been shown.
     */
    private boolean waitingUIShowed = false;

    /**
     * The user interface for selecting the player color.
     */
    FXSelectPlayerColorUI colorUI;

    /**
     * A flag indicating whether the application is busy.
     */
    boolean busy = true;

    /**
     * The main method for the application.
     * It launches the JavaFX application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is the entry point for the JavaFX application.
     * It is called when the application is launched.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set.
     * @throws Exception If an error occurs during the start of the application.
     */
    @Override
    public void start(Stage stage) throws Exception {}

    /**
     * This method is used to open the main user interface of the application.
     * It sets up the cards view, creates a new stage, sets up the main user interface, and adds it to the stage.
     * It also sets the size and scale of the main user interface, and adds a CSS stylesheet to the scene.
     * After the stage is shown, it sets the busy flag to false.
     * If the application is still busy, it sleeps for 500 milliseconds and then checks again.
     */
    public void open() {

        FXCardFactory.setupCardsView();

        Platform.runLater(()->{

            Stage mainStage = new Stage();

            mainStage.setResizable(false);

            mainStage.centerOnScreen();

            mainUI = new FXMainUI();

            AnchorPane root = new AnchorPane();

            root.getChildren().add(mainUI);

            AnchorPane.setTopAnchor(mainUI,0.0);
            AnchorPane.setLeftAnchor(mainUI,0.0);
            AnchorPane.setRightAnchor(mainUI,0.0);
            AnchorPane.setBottomAnchor(mainUI,0.0);

            mainUI.setClip(new Rectangle(0,0,1440,900)); //1920,1080

            mainUI.resize(1440,900); //1920,1080

            Scene scene = new Scene(root,1440,900); //1920,1080
            mainUI.setScaleX(0.5);
            mainUI.setScaleY(0.5);
            scene.getStylesheets().add(FXCodexNaturalisApp.class.getResource("skin/skin.css").toExternalForm());

            mainStage.setScene(scene);

            mainStage.show();

            root.layout();

            busy = false;

        });

        while (busy)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * This method is used to prompt the user to choose a card to draw.
     * It opens an info panel with a message, enables interaction with the resource deck, gold deck, and board cards, and then waits for the user to interact.
     *
     * @return String The string representing the card chosen by the user.
     */
    @Override
    public String chooseCardToDraw() {
        Platform.runLater(()->{

            mainUI.openInfoPanel("Draw a card from a deck or from the Board");
            mainUI.enableResourceDeckInteraction();
            mainUI.enableGoldDeckInteraction();
            mainUI.enableBoardCardInteraction();

        });

        mainUI.waitUserInteraction();
        return mainUI.getCurrentPlayCardString();
    }

    /**
     * This method is used to prompt the user to choose a card to play.
     * It opens an info panel with a message asking the user which card they want to play.
     * It then prepares the player picking interface and waits for the user to interact.
     * Once the user has interacted, it returns the index of the card chosen by the user.
     *
     * @param cardInHand An ArrayList of PlayCard objects representing the cards in the user's hand.
     * @return int The index of the card chosen by the user.
     */
    @Override
    public int chooseCardToPlay(ArrayList<PlayCard> cardInHand) {

        Platform.runLater(()->
        {
            mainUI.openInfoPanel("Which card do you want to play ?");
            mainUI.preparePlayerPicking();
        });

        mainUI.waitUserInteraction();

        return mainUI.getCurrentCardIndexPlayed();
    }

    /**
     * This method is used to prompt the user to choose a side of the card to play.
     * If the user is about to play a card (determined by checking if the current card index played is greater than 0), it opens a dialog for the user to choose the side to play.
     * If the user is not about to play a card, it opens a dialog for the user to choose the initial card.
     * It then returns the side chosen by the user.
     *
     * @return String The side chosen by the user.
     */
    @Override
    public String chooseSide() {

        if (mainUI.getCurrentCardIndexPlayed() > 0)  // sto per giocare la carta ?
        {
            FXChooseSideToPlayUI choosePlayUI = new FXChooseSideToPlayUI(mainUI);
            mainUI.presentDialog(choosePlayUI,null);
            mainUI.closeInfoPanel();
            return choosePlayUI.getSide();
        }
        else {
            FXChooseInitialCardUI initialCardUI = new FXChooseInitialCardUI(mainUI);
            mainUI.presentDialog(initialCardUI, () -> initialCardUI.showInitialCard(card));
            return initialCardUI.getSide();
        }

    }

    /**
     * This method is used to prompt the user to choose a position on the play area to place a card.
     * It opens an info panel with a message instructing the user to choose the row and column where they want to place the card.
     * It then enables corner interaction on the main user interface and waits for the user to interact.
     * Once the user has interacted, it returns the selected card position as a pair of integers representing the row and column indices.
     *
     * @param playArea The play area where the card is to be placed.
     * @return Pair<Integer, Integer> The pair of integers representing the row and column indices of the selected card position.
     */
    @Override
    public Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea) {
        Platform.runLater(()->{

            mainUI.openInfoPanel("Choose the row and column in which you want to place the card");
            mainUI.enableCornerInteraction();

        });
        mainUI.waitUserInteraction();

        return mainUI.getCardPositionSelected();
    }

    /**
     * This method is used to prompt the user to select a nickname.
     * It creates a new FXNicknameUI object and presents it as a dialog.
     * It then returns the nickname chosen by the user.
     *
     * @return String The nickname chosen by the user.
     */
    @Override
    public String selectNickName() {
        FXNicknameUI nickNameUI = new FXNicknameUI(mainUI);
        mainUI.presentDialog(nickNameUI,null);
        return nickNameUI.getNickName();
    }

    /**
     * This method is used to prompt the user to either create or join a game.
     * It creates a new FXCreateOrJoinUI object and presents it as a dialog.
     * It then returns the choice made by the user.
     *
     * @return int The choice made by the user (either create or join).
     */
    @Override
    public int createOrJoin() {
        FXCreateOrJoinUI createJoinUI = new FXCreateOrJoinUI(mainUI);
        mainUI.presentDialog(createJoinUI,null);
        return createJoinUI.getChoose();
    }

    /**
     * This method is used to prompt the user to select the maximum number of players for the game.
     * It creates a new FXSelectPlayerUI object and presents it as a dialog.
     * It then returns the number of players chosen by the user.
     *
     * @return int The number of players chosen by the user.
     */
    @Override
    public int MaxNumPlayers() {
        FXSelectPlayerUI selectPlayerUI = new FXSelectPlayerUI(mainUI);
        mainUI.presentDialog(selectPlayerUI,null);
        return selectPlayerUI.getChoose();
    }

    /**
     * This method is used to display the available games to the user.
     * It creates a new FXSelectLobbyUI object, sets up the lobby with the available games and number of players, and presents it as a dialog.
     * It then returns the game chosen by the user.
     *
     * @param games A map of the available games.
     * @param numPlayers A list of pairs representing the number of players for each game.
     * @return int The game chosen by the user.
     * @throws RemoteException If a remote method invocation error occurs.
     */
    @Override
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        FXSelectLobbyUI selectLobbyUI = new FXSelectLobbyUI(mainUI);
        mainUI.presentDialog(selectLobbyUI,()->selectLobbyUI.setup(games,numPlayers));
        return selectLobbyUI.getChoose();
    }

    /**
     * This method is used to print the game board to the user interface.
     * It removes the waiting UI, clears the cards, shows the players, common cards, player cards, and play area.
     *
     * @param model The PlayGround model representing the game board.
     * @param opponents A list of the opponent players.
     * @param me The current player.
     */
    @Override
    public void printBoard(PlayGround model, ArrayList<Player> opponents, Player me) {
        Platform.runLater(()->{
            mainUI.removeWaitingUI();
            mainUI.clearCards();
            mainUI.showPlayers(me,opponents);
            mainUI.showCommonCards(model);
            mainUI.showPlayerCards(me);
            mainUI.showPlayArea(me);
        });
    }

    /**
     * This method is used to prompt the user to choose a persona objective card.
     * It creates a new FXChooseObjectiveCardUI object, fills it with the provided objectives, and presents it as a dialog.
     * It then returns the choice made by the user.
     *
     * @param objectives An ArrayList of ObjectiveCard objects representing the available persona objective cards.
     * @return int The index of the persona objective card chosen by the user.
     */
    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        FXChooseObjectiveCardUI chooseObjCardUI = new FXChooseObjectiveCardUI(mainUI);
        mainUI.presentDialog(chooseObjCardUI,()->chooseObjCardUI.fill(objectives));
        return chooseObjCardUI.getChoose();
    }

    /**
     * This method is used to show the initial card to the user.
     * It sets the card field to the provided card.
     *
     * @param card The InitialCard object representing the initial card.
     */
    @Override
    public void showInitialCard(InitialCard card) {
        this.card = card;
    }

    /**
     * This method is used to display the available colors to the user.
     * It creates a new FXSelectPlayerColorUI object, sets up the available colors, and presents it as a dialog.
     *
     * @param availableColors A List of PawnColor objects representing the available colors.
     */
    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) {
        colorUI = new FXSelectPlayerColorUI(mainUI);
        mainUI.presentDialog(colorUI,()->colorUI.setup(availableColors));

    }

    /**
     * This method is used to indicate that the application is waiting for players.
     * Currently, it does not perform any actions.
     */
    @Override
    public void waitingForPlayers() {

    }

    /**
     * This method is used to print a message to the user interface.
     * Depending on the content of the message, it may show a waiting UI, present an error dialog, or inject the message into the main user interface.
     *
     * @param message The string representing the message to be printed.
     */
    @Override
    public void printMessage(String message) {
        if (message.equals("Waiting for more players to join the game..."))
        {
            if (!waitingUIShowed)
            {
                waitingUIShowed = true;
                Platform.runLater(()->{

                    mainUI.showWaitUI();

                });
            }

        }

        if (message.startsWith("Sorry") || message.startsWith("Oops"))
        {
              if (!message.equals("Sorry, you have lost the game!"))
              {
                  FXErrorDialogMessageUI errorUI = new FXErrorDialogMessageUI(mainUI,message);
                  mainUI.presentDialog(errorUI,null);
              }

        }

        Platform.runLater(()->mainUI.injectMessage(message));
    }

    /**
     * This method is used to receive a command from the user.
     * It creates a new FXChooseYourOptionUI object, presents it as a dialog, and then returns the command chosen by the user.
     *
     * @param IsMyTurn A Boolean indicating whether it is the user's turn.
     * @return Command The command chosen by the user.
     */
    @Override
    public Command receiveCommand(Boolean IsMyTurn) {
        FXChooseYourOptionUI optionUI = new FXChooseYourOptionUI(mainUI,IsMyTurn);
        mainUI.presentDialog(optionUI,null);
        return optionUI.getChoose();
    }

    /**
     * This method is used to show a string to the user.
     * Depending on the content of the string, it may present a final result dialog to the user.
     *
     * @param s The string to be shown to the user.
     */
    @Override
    public void showString(String s) {

        String message = "";
        switch (s)
        {
            case "WIN" : message = "YOU WIN !"; break;
            case "GAME_OVER" : message = "GAME OVER"; break;
        }

        if (!message.isEmpty())
        {
            FXFinalResultUI resultUI = new FXFinalResultUI(mainUI,message);
            mainUI.presentDialog(resultUI,null);
        }


    }

    /**
     * This method is used to prompt the user to select a color.
     * It calls the getChoose method from the colorUI object, which represents the user interface for selecting the player color.
     * The getChoose method returns the index of the color chosen by the user.
     *
     * @return int The index of the color chosen by the user.
     */
    @Override
    public int getInput() {
        return colorUI.getChoose();
    }

}
