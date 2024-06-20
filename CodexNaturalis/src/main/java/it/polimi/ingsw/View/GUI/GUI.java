package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.GUI.Controllers.NicknameScene;
import it.polimi.ingsw.View.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI extends Application implements UserInterface {

    private StackPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("nickname.fxml"));
        primaryStage.setTitle("Codex Naturalis");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }


    @Override
    public String chooseCardToDraw() {
        return null;
    }

    @Override
    public int chooseCardToPlay(ArrayList<PlayCard> cardInHand) {
        return 0;
    }

    @Override
    public String chooseSide() {
        return null;
    }

    @Override
    public Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea) {
        return null;
    }

    @Override
    public String selectNickName() {
        NicknameScene nickNameScene = new NicknameScene();
        Platform.runLater(() -> {
           Scene scene = new Scene(nickNameScene.getRoot());
           primaryStage.setScene(scene);
            primaryStage.show();
        });

        return toString();
    }

    @Override
    public int createOrJoin() {
//        CreateOrJoinScene createOrJoin = new CreateOrJoinScene();
//        Platform.runLater(() -> {
//            Scene scene = new Scene(createOrJoin.getRoot());
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        });
//
//        return createOrJoin.getChoice();
        return 0;
    }

    @Override
    public int MaxNumPlayers() {
        return 0;
    }

    @Override
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        return 0;
    }

    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        return 0;
    }

    @Override
    public void showInitialCard(InitialCard card) {

    }

    @Override
    public int displayAvailableColors(List<PawnColor> availableColors) {
        return 0;
    }


    @Override
    public void waitingForPlayers() {

    }


    @Override
    public void printMessage(String message) {

    }

    @Override
    public Command receiveCommand() {
        return null;
    }

    @Override
    public void printBoard(PlayGround model, ArrayList<Player> opponents, Player me) {

    }




    @Override
    public void viewChat() {

    }

    @Override
    public void showString(String s) {

    }
}