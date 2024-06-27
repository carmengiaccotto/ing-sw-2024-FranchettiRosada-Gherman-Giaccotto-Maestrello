package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.base.FXGamePane;
import it.polimi.ingsw.View.FX.ui.game.FXPlayerScoreUI;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardView;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


public class FXMainUI extends FXGamePane {

    @FXML
    private ImageView backgroundView;

    // anchor
    @FXML
    private Label resourceCardsLabelAnchor;
    @FXML
    private Label goldCardsLabelAnchor;
    @FXML
    private Label commonObjCardsLabelAnchor;
    @FXML
    private Label resourceDeckLabelAnchor;
    @FXML
    private Label goldDeckLabelAnchor;

    @FXML
    private Label yourCardLabelAnchor;

    @FXML
    private Label personalObjectiveAnchor;

    @FXML
    private Label playersScoresLabelAnchor;

    @FXML
    private AnchorPane overlayPanel;

    @FXML
    private AnchorPane infoMovePanel;

    @FXML
    private Label infoMoveLabel;

    @FXML
    private ImageView infoMoveIcon;

    private boolean fxBusy = false;

    private FXWaitingForPlayerUI waitUI;
    private FXConsoleMessageUI messageUI;

    private boolean messageOpened = false;

    public static Map<PawnColor, Image> colorMap = new HashMap<>();

    private List<FXPlayerScoreUI> playerScoreItems = new ArrayList<>();

    private static final Double START_COMMON_OBJECTIVE_X = 520.0;
    private static final Double START_GOLD_X = 520.0;

    private List<FXCardView> cardsOnTable = new ArrayList<>();

    private List<FXCardView> cardsInHand = new ArrayList<>();

    private List<FXCardView> resourcesDeckFXCards = new ArrayList<>();
    private List<FXCardView> goldDeckFXCards = new ArrayList<>();

    private List<FXCardView> resourcesBoardFXCards = new ArrayList<>();
    private List<FXCardView> goldBoardFXCards = new ArrayList<>();

    private TranslateTransition infoIconAnimator;

    private int currentCardIndexPlayed = -1;

    private static final int cardHeight = 7;
    private static final int cardWidth = 25;
    private static final int RowDimensions = cardHeight - 3;
    private static final int ColumnDimensions = cardWidth - 4;

    private String currentPlayCardString = "";
    private Pair<Integer, Integer> cardPositionSelected = null;
    private int currentZLevel = 0;

    private Map<Pair<Integer, Integer>, FXCardView> cardsInMatrix = new HashMap<>();

    static {
        colorMap.put(PawnColor.RED, new Image(FXMainUI.class.getResource("RED.png").toExternalForm()));
        colorMap.put(PawnColor.GREEN, new Image(FXMainUI.class.getResource("GREEN.png").toExternalForm()));
        colorMap.put(PawnColor.BLUE, new Image(FXMainUI.class.getResource("BLUE.png").toExternalForm()));
        colorMap.put(PawnColor.YELLOW, new Image(FXMainUI.class.getResource("YELLOW.png").toExternalForm()));
    }

    public FXMainUI() {
        FXMLUtils.load(FXMainUI.class, "FXMainUI.fxml", this);

        overlayPanel.setOpacity(0);

        backgroundView.relocate(0, 0);
        backgroundView.setFitWidth(getWidth());
        backgroundView.setFitHeight(getHeight());

        messageUI = new FXConsoleMessageUI(this);
        setupMessageUI();

        infoMovePanel.setOpacity(0);
        infoMovePanel.setMouseTransparent(true);

        boundsInLocalProperty().addListener((obs,oldv,newv)->{

            backgroundView.setFitWidth(newv.getWidth());
            backgroundView.setFitHeight(newv.getHeight());

        });

    }

    public Pair<Integer, Integer> getCardPositionSelected() {
        return cardPositionSelected;
    }

    public void openInfoPanel(String message) {
        infoMoveLabel.setText(message);

        FadeTransition fade = new FadeTransition(Duration.seconds(0.2), infoMovePanel);
        fade.setToValue(1);
        fade.play();

        infoIconAnimator = new TranslateTransition(Duration.seconds(0.3), infoMoveIcon);
        infoIconAnimator.setToX(10);
        infoIconAnimator.setCycleCount(-1);
        infoIconAnimator.setAutoReverse(true);
        infoIconAnimator.play();


    }

    public void closeInfoPanel() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.2), infoMovePanel);
        fade.setToValue(0);
        fade.play();
        infoIconAnimator.stop();
    }

    private void setupMessageUI() {
        messageUI.prefWidthProperty().bind(widthProperty());
        messageUI.setLayoutY(getHeight() - 46);

        heightProperty().addListener((obs, oldv, newv) -> messageUI.setLayoutY((Double) newv - 46));
        getChildren().add(messageUI);
    }

    public void openMessage() {
        messageOpened = true;
        messageUI.toFront();
        messageUI.makeOpenAnimation();
        TranslateTransition anim = new TranslateTransition(Duration.seconds(0.5), messageUI);
        anim.setToY(-(messageUI.getPrefHeight() - 46));
        anim.play();
    }

    public void closeMessage() {
        messageOpened = false;
        messageUI.makeCloseAnimation();
        TranslateTransition anim = new TranslateTransition(Duration.seconds(0.5), messageUI);
        anim.setToY(0);
        anim.play();
    }

    public void openCloseMessages() {
        if (messageOpened)
            closeMessage();
        else
            openMessage();
    }

    public void showWaitUI() {
        waitUI = new FXWaitingForPlayerUI(this);

        double x = (getWidth() - waitUI.getPrefWidth()) / 2;
        double y = (getHeight() - waitUI.getPrefHeight()) / 2;

        waitUI.relocate(x, y);
        getChildren().add(waitUI);
    }

    public void removeWaitingUI() {
        if (waitUI != null) {
            waitUI.stop();
            getChildren().remove(waitUI);
        }

    }

    public void presentDialog(FXDialogGamePane dialog, Runnable preOperation) {
        fxBusy = true;

        Platform.runLater(() -> {

            overlayPanel.toFront();

            if (preOperation != null)
                preOperation.run();

            double x = (getWidth() - dialog.getPrefWidth()) / 2;
            double y = (getHeight() - dialog.getPrefHeight()) / 2;

            overlayPanel.getChildren().add(dialog);
            dialog.relocate(x, y);
            overlayPanel.setMouseTransparent(false);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), overlayPanel);
            fadeOut.setToValue(1);
            fadeOut.play();

        });

        waitFX();

        fxBusy = true;
        Platform.runLater(() -> {

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), overlayPanel);
            fadeIn.setToValue(0);
            fadeIn.setOnFinished(en -> {
                overlayPanel.getChildren().remove(dialog);
                overlayPanel.setMouseTransparent(true);
                fxBusy = false;
            });
            fadeIn.play();

        });
        waitFX();

    }

    private void waitFX() {
        while (fxBusy) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showPlayers(Player me, ArrayList<Player> opponents) {

        double y = playersScoresLabelAnchor.getLayoutY() + 30;
        double x = playersScoresLabelAnchor.getLayoutX();

        FXPlayerScoreUI meItem = new FXPlayerScoreUI(this, me, true);
        playerScoreItems.add(meItem);

        opponents.forEach(o -> {
            FXPlayerScoreUI opponent = new FXPlayerScoreUI(this, o, false);
            playerScoreItems.add(opponent);

        });

        for (FXPlayerScoreUI i : playerScoreItems) {
            i.relocate(x, y);
            x += i.getPrefWidth() + 10;
            getChildren().add(i);
            i.update();
        }

    }

    public void showPlayerCards(Player me) {
        double y = yourCardLabelAnchor.getLayoutY() + 40;
        double x = yourCardLabelAnchor.getLayoutX();

        int index = 1;

        for (Card c : me.getCardsInHand()) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);

            cardsOnTable.add(view);

            cardsInHand.add(view);

            view.setRotate(0);

            view.relocate(x, y);
            view.flipIfCoveredNoAnim();
            x += view.getPrefWidth() + 10;

            view.setCardIndex(index);
            index++;
        }

        double yObj = personalObjectiveAnchor.getLayoutY() + 40;
        double xObj = personalObjectiveAnchor.getLayoutX();

        if (me.getPersonalObjectiveCard() != null) {
            FXCardView view = FXCardFactory.getView(me.getPersonalObjectiveCard());
            getChildren().add(view);
            view.relocate(xObj, yObj);

            cardsOnTable.add(view);
        }

    }

    public void showCommonCards(PlayGround model) {

        ArrayList<ResourceCard> resourceCards = model.getCommonResourceCards();
        ArrayList<GoldCard> goldCards = model.getCommonGoldCards();

        Deck resourceDeck = model.getResourceCardDeck();
        Deck goldDeck = model.getGoldCardDeck();
        ArrayList<ObjectiveCard> commonObjectives = model.getCommonObjectivesCards();

        showCommonObjective(commonObjectives);
        showGoldCards(goldCards);
        showResourceCards(resourceCards);

        showResourceDeck(resourceDeck);
        showGoldDeck(goldDeck);

    }

    private void showResourceDeck(Deck resourceDeck) {
        double y = resourceDeckLabelAnchor.getLayoutY() + 40;
        double x = resourceDeckLabelAnchor.getLayoutX();

        Random rotZ = new Random();

        for (Card c : resourceDeck.getCards()) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);
            cardsOnTable.add(view);
            view.relocate(x, y);
            view.flipIfFrontNoAnim();
            resourcesDeckFXCards.add(view);

            double zAngle = rotZ.nextDouble() * 5.0;
            view.setRotate(zAngle);
        }
    }

    private void showGoldDeck(Deck goldDeck) {
        double y = goldDeckLabelAnchor.getLayoutY() + 40;
        double x = goldDeckLabelAnchor.getLayoutX();

        Random rotZ = new Random();

        for (Card c : goldDeck.getCards()) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);
            cardsOnTable.add(view);
            view.relocate(x, y);
            goldDeckFXCards.add(view);
            view.flipIfFrontNoAnim();
            double zAngle = rotZ.nextDouble() * 5.0;
            view.setRotate(zAngle);
        }
    }

    private void showGoldCards(ArrayList<GoldCard> goldCards) {
        double y = goldCardsLabelAnchor.getLayoutY() + 40;
        double x = goldCardsLabelAnchor.getLayoutX();

        for (Card c : goldCards) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);
            cardsOnTable.add(view);
            goldBoardFXCards.add(view);
            view.relocate(x, y);
            view.flipIfCoveredNoAnim();
            view.setRotate(0);
            x += view.getPrefWidth() + 10;
        }
    }

    private void showResourceCards(ArrayList<ResourceCard> resourceCards) {
        double y = resourceCardsLabelAnchor.getLayoutY() + 40;
        double x = resourceCardsLabelAnchor.getLayoutX();

        for (Card c : resourceCards) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);
            cardsOnTable.add(view);
            view.relocate(x, y);
            view.flipIfCoveredNoAnim();
            resourcesBoardFXCards.add(view);
            view.setRotate(0);


            x += view.getPrefWidth() + 10;
        }
    }

    private void showCommonObjective(ArrayList<ObjectiveCard> commonObjectives) {
        double y = commonObjCardsLabelAnchor.getLayoutY() + 40;
        double x = commonObjCardsLabelAnchor.getLayoutX();

        for (Card c : commonObjectives) {
            FXCardView view = FXCardFactory.getView(c);
            getChildren().add(view);

            cardsOnTable.add(view);

            view.relocate(x, y);
            view.setRotate(0);

            view.flipIfCoveredNoAnim();

            x += view.getPrefWidth() + 10;
        }
    }

    public void closeDialog() {
        fxBusy = false;
    }

    public void injectMessage(String message) {
        messageUI.inject(message);
    }

    public void clearCards() {
        getChildren().removeAll(cardsOnTable);
        getChildren().removeAll(playerScoreItems);
        cardsOnTable.forEach(c -> {

            c.disableInteraction();
            c.setCardIndex(-1);

        });
        cardsInHand.clear();
        cardsOnTable.clear();
        cardsInMatrix.clear();
        resourcesDeckFXCards.clear();
        goldDeckFXCards.clear();
        resourcesBoardFXCards.clear();
        goldBoardFXCards.clear();
        playerScoreItems.clear();
    }

    public void showPlayArea(Player me) {

        PlayArea playArea = me.getPlayArea();

        List<Integer> visibleRows = new ArrayList<>();
        List<Integer> visibleCols = new ArrayList<>();

        int rows = (playArea.getCardsOnArea().size()) * (RowDimensions) + 10;
        int columns = (playArea.getCardsOnArea().getFirst().size()) * (ColumnDimensions) + 32;

        List<FXCardView> cardsInGame = new ArrayList<>();

        for (int i = 0; i < rows - 1; i++)
            for (int j = 0; j < columns - 1; j++) {
                if (i == 0 && (j + ColumnDimensions / 2) % ColumnDimensions == 0) {
                    visibleCols.add(j / ColumnDimensions);
                } else if (j == 0 && (i + RowDimensions / 2) % RowDimensions == 0) {
                    visibleRows.add(i / RowDimensions);
                }
            }

        for (int i = 0; i < rows - 1; i++) { //leaving one row free ad the beginning plus space for index
            for (int j = 0; j < columns - 1; j++) {//leaving one column free ad the beginning plus space for index
                int cardRowIndex = i / RowDimensions; //Equivalent of the row in the playArea
                int cardColumnIndex = j / ColumnDimensions;//Equivalent of the column in the playArea
                int startRow = (cardRowIndex) * RowDimensions;
                int startColumn = (cardColumnIndex) * ColumnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() && cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        SideOfCard sideOfCard = playArea.getCardInPosition(cardRowIndex, cardColumnIndex);

                        FXCardView currentCard = getCardInMatrix(cardRowIndex, cardColumnIndex);

                        if (currentCard == null) {
                            FXCardView card = showCard(sideOfCard, cardRowIndex, cardColumnIndex, visibleRows, visibleCols);
                            cardsInGame.add(card);
                        }

                        //DesignSupportClass.printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex), startRow, startColumn, cardHeight, cardWidth);
                    }
                }
            }
        }

        reorderByZLevel(cardsInGame);
    }

    private void reorderByZLevel(List<FXCardView> cardsInGame) {
        Collections.sort(cardsInGame, (o1, o2) -> o1.getZLevel() - o2.getZLevel());
        cardsInGame.forEach(c -> c.toFront());

    }

    public FXCardView getCardInMatrix(int row, int col) {
        for (Pair<Integer, Integer> p : cardsInMatrix.keySet()) {
            if (p.getFirst() == row && p.getSecond() == col) {
                FXCardView card = cardsInMatrix.get(p);
                return card;
            }
        }

        return null;
    }

    private FXCardView showCard(SideOfCard sideCard, int row, int col, List<Integer> visibleRows, List<Integer> visibleCols) {

        int deltaCardX = 148;
        int deltaCardY = 78;

        //visibleRows.removeLast();
        //visibleRows.removeLast();
        //visibleCols.removeLast();

        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2;

        // centro schermo = centro matrice

        int centralRow = visibleRows.getLast() / 2;
        int centralCol = visibleCols.getLast() / 2;

        int deltaIndexXFromCenter = row - centralRow;
        int deltaIndexYFromCenter = col - centralCol;

        double deltaXFromCenter = centerX + (deltaIndexXFromCenter * deltaCardX);
        double deltaYFromCenter = centerY + (deltaIndexYFromCenter * deltaCardY);

        FXCardView card = FXCardFactory.getView(sideCard.getParentCard());

        card.setCurrentMatrixPosition(new Pair<>(row, col));
        getChildren().add(card);
        card.relocate(deltaXFromCenter - (card.getPrefWidth() / 2), deltaYFromCenter - (card.getPrefHeight() / 2));

        cardsOnTable.add(card);

        cardsInMatrix.put(new Pair<>(row, col), card);

        return card;
    }

    public void preparePlayerPicking() {
        cardsInHand.forEach(c -> {

            c.enableInteraction();
            c.setCardClickListener(card -> {

                currentCardIndexPlayed = card.getCardIndex();
                card.setZLevel(currentZLevel + 1);
                fxBusy = false;

            });

        });


    }


    public void waitUserInteraction() {
        fxBusy = true;
        waitFX();
    }

    public int getCurrentCardIndexPlayed() {
        return currentCardIndexPlayed;

    }

    public List<FXCardView> getCardsInHand() {
        return cardsInHand;
    }

    public void enableResourceDeckInteraction() {
        FXCardView card = resourcesDeckFXCards.getLast();
        card.enableInteraction();
        card.setCardClickListener(c -> {

            currentPlayCardString = "RESOURCE-DECK";

            card.disableInteraction();
            card.setCardClickListener(null);
            closeInfoPanel();

            fxBusy = false;
        });
    }

    public void enableGoldDeckInteraction() {
        FXCardView card = goldDeckFXCards.getLast();
        card.enableInteraction();
        card.setCardClickListener(c -> {
            currentPlayCardString = "GOLD-DECK";

            card.disableInteraction();
            card.setCardClickListener(null);
            closeInfoPanel();

            fxBusy = false;
        });
    }

    public void enableBoardCardInteraction() {
        resourcesBoardFXCards.forEach(c -> {
            c.enableInteraction();
            c.setCardClickListener(card -> {
                int indexOf = resourcesBoardFXCards.indexOf(card);
                currentPlayCardString = "RESOURCE-CARD" + (indexOf + 1);

                card.disableInteraction();
                card.setCardClickListener(null);
                closeInfoPanel();

                fxBusy = false;
            });
        });
        goldBoardFXCards.forEach(c -> {

            c.enableInteraction();
            c.setCardClickListener(card -> {
                int indexOf = goldBoardFXCards.indexOf(card);
                currentPlayCardString = "GOLD-CARD" + (indexOf + 1);

                card.disableInteraction();
                card.setCardClickListener(null);
                closeInfoPanel();

                fxBusy = false;

            });

        });
    }

    public String getCurrentPlayCardString() {
        return currentPlayCardString;
    }

    public void disableCornerInteraction() {
        cardsInMatrix.values().forEach(c -> {
            c.disableCornerInteraction();
        });
    }

    public void enableCornerInteraction() {

        cardsInMatrix.values().forEach(c -> {

            c.enableCornerInteraction();
            c.setCardCornerEventListener((card, corner) -> {

                cardPositionSelected = calculatePair(card, corner);
                disableCornerInteraction();
                fxBusy = false;
            });

        });

    }

    private Pair<Integer, Integer> calculatePair(FXCardView card, FXCardView.CardCorner corner) {
        Pair<Integer, Integer> cardPosition = card.getCurrentMatrixPosition();
        if (corner == FXCardView.CardCorner.TOP_LEFT) {
            return new Pair<>(cardPosition.getFirst() - 1, cardPosition.getSecond() - 1);
        }

        if (corner == FXCardView.CardCorner.TOP_RIGHT) {
            return new Pair<>(cardPosition.getFirst() - 1, cardPosition.getSecond() + 1);
        }

        if (corner == FXCardView.CardCorner.BOTTOM_LEFT) {
            return new Pair<>(cardPosition.getFirst() + 1, cardPosition.getSecond() - 1);
        }

        if (corner == FXCardView.CardCorner.BOTTOM_RIGHT) {
            return new Pair<>(cardPosition.getFirst() + 1, cardPosition.getSecond() + 1);
        }

        return null;
    }

    public void showAreaOf(Player player) {

        Pane root = new Pane();
        root.resize(1920, 1080);
        ImageView background = new ImageView(new Image(FXMainUI.class.getResource("background.jpg").toExternalForm()));
        root.getChildren().add(background);
        background.relocate(0, 0);

        PlayArea playArea = player.getPlayArea();

        List<Integer> visibleRows = new ArrayList<>();
        List<Integer> visibleCols = new ArrayList<>();

        int rows = (playArea.getCardsOnArea().size()) * (RowDimensions) + 10;
        int columns = (playArea.getCardsOnArea().getFirst().size()) * (ColumnDimensions) + 32;

        Map<Pair<Integer, Integer>, FXCardView> cardsInMatrixOpponent = new HashMap<>();

        for (int i = 0; i < rows - 1; i++)
            for (int j = 0; j < columns - 1; j++) {
                if (i == 0 && (j + ColumnDimensions / 2) % ColumnDimensions == 0) {
                    visibleCols.add(j / ColumnDimensions);
                } else if (j == 0 && (i + RowDimensions / 2) % RowDimensions == 0) {
                    visibleRows.add(i / RowDimensions);
                }
            }

        for (int i = 0; i < rows - 1; i++) { //leaving one row free ad the beginning plus space for index
            for (int j = 0; j < columns - 1; j++) {//leaving one column free ad the beginning plus space for index
                int cardRowIndex = i / RowDimensions; //Equivalent of the row in the playArea
                int cardColumnIndex = j / ColumnDimensions;//Equivalent of the column in the playArea
                int startRow = (cardRowIndex) * RowDimensions;
                int startColumn = (cardColumnIndex) * ColumnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() && cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        SideOfCard sideOfCard = playArea.getCardInPosition(cardRowIndex, cardColumnIndex);

                        FXCardView currentCard = null;

                        for (Pair<Integer, Integer> p : cardsInMatrixOpponent.keySet()) {
                            if (p.getFirst() == cardRowIndex && p.getSecond() == cardColumnIndex) {
                                FXCardView card = cardsInMatrixOpponent.get(p);
                                currentCard = card;
                            }
                        }

                        if (currentCard == null) {
                            int deltaCardX = 148;
                            int deltaCardY = 78;


                            double centerX = getWidth() / 2;
                            double centerY = getHeight() / 2;

                            // centro schermo = centro matrice

                            int centralRow = visibleRows.getLast() / 2;
                            int centralCol = visibleCols.getLast() / 2;

                            int deltaIndexXFromCenter = cardRowIndex - centralRow;
                            int deltaIndexYFromCenter = cardColumnIndex - centralCol;

                            double deltaXFromCenter = centerX + (deltaIndexXFromCenter * deltaCardX);
                            double deltaYFromCenter = centerY + (deltaIndexYFromCenter * deltaCardY);

                            FXCardView card = new FXCardView(sideOfCard.getParentCard().getIdCard());

                            if (isFrontSide(sideOfCard) == Side.FRONT)
                            {
                                card.flipNoAnim();
                            }

                            card.setCurrentMatrixPosition(new Pair<>(cardRowIndex, cardColumnIndex));
                            root.getChildren().add(card);
                            card.relocate(deltaXFromCenter - (card.getPrefWidth() / 2), deltaYFromCenter - (card.getPrefHeight() / 2));

                        }

                    }
                }
            }


        }

        Image snapshot = root.snapshot(new SnapshotParameters(), null);


        Stage stageArea = new Stage();
        stageArea.setTitle("Play area of " + player.getNickname());
        Pane sceneRoot = new Pane();
        ImageView imageView = new ImageView(snapshot);
        sceneRoot.getChildren().add(imageView);
        sceneRoot.resize(960, 540);

        imageView.relocate(0, 0);
        imageView.setFitWidth(960);
        imageView.setFitHeight(540);

        Scene scene = new Scene(sceneRoot, 960, 540);
        stageArea.setScene(scene);
        stageArea.showAndWait();
        stageArea.setResizable(false);

        root.getChildren().clear();
    }

    public static Side isFrontSide(SideOfCard card) {
        if(card.getParentCard().getFront().equals(card))
            return Side.FRONT;
        else
            return Side.BACK;
    }
}