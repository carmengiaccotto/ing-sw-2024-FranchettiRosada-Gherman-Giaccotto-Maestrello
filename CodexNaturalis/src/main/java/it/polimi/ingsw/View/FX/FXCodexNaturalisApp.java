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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FXCodexNaturalisApp extends Application implements UserInterface {

    static
    {
        Platform.startup(() -> {});
    }

    private FXMainUI mainUI;

    private InitialCard card;

    private boolean waitingUIShowed = false;

    FXSelectPlayerColorUI colorUI;

    boolean busy = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {}


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

    @Override
    public Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea) {
        Platform.runLater(()->{

            mainUI.openInfoPanel("Choose the row and column in which you want to place the card");
            mainUI.enableCornerInteraction();

        });
        mainUI.waitUserInteraction();

        return mainUI.getCardPositionSelected();
    }

    @Override
    public String selectNickName() {
        FXNicknameUI nickNameUI = new FXNicknameUI(mainUI);
        mainUI.presentDialog(nickNameUI,null);
        return nickNameUI.getNickName();
    }

    @Override
    public int createOrJoin() {
        FXCreateOrJoinUI createJoinUI = new FXCreateOrJoinUI(mainUI);
        mainUI.presentDialog(createJoinUI,null);
        return createJoinUI.getChoose();
    }

    @Override
    public int MaxNumPlayers() {
        FXSelectPlayerUI selectPlayerUI = new FXSelectPlayerUI(mainUI);
        mainUI.presentDialog(selectPlayerUI,null);
        return selectPlayerUI.getChoose();
    }

    @Override
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        FXSelectLobbyUI selectLobbyUI = new FXSelectLobbyUI(mainUI);
        mainUI.presentDialog(selectLobbyUI,()->selectLobbyUI.setup(games,numPlayers));
        return selectLobbyUI.getChoose();
    }

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

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        FXChooseObjectiveCardUI chooseObjCardUI = new FXChooseObjectiveCardUI(mainUI);
        mainUI.presentDialog(chooseObjCardUI,()->chooseObjCardUI.fill(objectives));
        return chooseObjCardUI.getChoose();
    }

    @Override
    public void showInitialCard(InitialCard card) {
        this.card = card;
    }

    @Override
    public void displayAvailableColors(List<PawnColor> availableColors) {
        colorUI = new FXSelectPlayerColorUI(mainUI);
        mainUI.presentDialog(colorUI,()->colorUI.setup(availableColors));

    }

    @Override
    public void waitingForPlayers() {

    }

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

    @Override
    public Command receiveCommand(Boolean IsMyTurn) {
        FXChooseYourOptionUI optionUI = new FXChooseYourOptionUI(mainUI,IsMyTurn);
        mainUI.presentDialog(optionUI,null);
        return optionUI.getChoose();
    }


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
     * This method prompts the user to input a number.
     *
     * @return An integer representing the user's input.
     */
    @Override
    public int getInput() {
        return colorUI.getChoose();
    }


}
