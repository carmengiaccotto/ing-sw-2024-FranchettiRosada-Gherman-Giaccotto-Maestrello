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

/**
 * This class represents the main UI for the game.
 * It extends the FXGamePane class and contains various UI elements such as labels, image views, and anchor panes.
 * It also contains methods for showing the play area, player picking, user interaction, and more.
 */
public class FXMainUI extends FXGamePane {

    /**
    * The background view.
     */
    @FXML
    private ImageView backgroundView;

    /**
    * The anchor labels for various card types and decks.
     */
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

    /**
    * The anchor label for your card.
     */
    @FXML
    private Label yourCardLabelAnchor;

    /**
    * The anchor label for personal objective.
     */
    @FXML
    private Label personalObjectiveAnchor;

    /**
    * The anchor label for player scores.
     */
    @FXML
    private Label playersScoresLabelAnchor;

    /**
    * The overlay panel.
     */
    @FXML
    private AnchorPane overlayPanel;

    /**
     *The info move panel.
     */
    @FXML
    private AnchorPane infoMovePanel;

    /**
    * The info move label.
     */
    @FXML
    private Label infoMoveLabel;

    /**
    * The info move icon.
     */
    @FXML
    private ImageView infoMoveIcon;

    /**
    * Flag to indicate if the UI is busy.
     */
    private boolean fxBusy = false;

    /**
     * UI for waiting for a player.
     */
    private FXWaitingForPlayerUI waitUI;

    /**
    * UI for console messages.
     */
    private FXConsoleMessageUI messageUI;

    /**
     * Flag to indicate if a message is opened.
     */
    private boolean messageOpened = false;

    /**
     * Map of pawn colors to images.
     */
    public static Map<PawnColor, Image> colorMap = new HashMap<>();

    /**
    * List of player score UI items.
     */
    private List<FXPlayerScoreUI> playerScoreItems = new ArrayList<>();

    /**
    * Constants for the starting X coordinates of common objective and gold.
     */
    private static final Double START_COMMON_OBJECTIVE_X = 520.0;
    private static final Double START_GOLD_X = 520.0;

    /**
    * List of card views on the table.
     */
    private List<FXCardView> cardsOnTable = new ArrayList<>();

    /**
    * List of card views in hand.
     */
    private List<FXCardView> cardsInHand = new ArrayList<>();

    /**
     * List of card views for the resource deck.
     */
    private List<FXCardView> resourcesDeckFXCards = new ArrayList<>();

    /**
    * List of card views for the gold deck.
     */
    private List<FXCardView> goldDeckFXCards = new ArrayList<>();

    /**
    * List of card views for the resource board.
     */
    private List<FXCardView> resourcesBoardFXCards = new ArrayList<>();

    /**
    * List of card views for the gold board.
     */
    private List<FXCardView> goldBoardFXCards = new ArrayList<>();

    /**
    * Animation for the info icon.
     */
    private TranslateTransition infoIconAnimator;

    /**
    * Index of the current card played.
     */
    private int currentCardIndexPlayed = -1;

    /**
    * Constants for the card dimensions.
     */
    private static final int cardHeight = 7;
    private static final int cardWidth = 25;
    private static final int RowDimensions = cardHeight - 3;
    private static final int ColumnDimensions = cardWidth - 4;

    /**
    * String for the current play card.
     */
    private String currentPlayCardString = "";

    /**
    * Pair for the selected card position.
     */
    private Pair<Integer, Integer> cardPositionSelected = null;

    /**
    * Current Z level.
     */
    private int currentZLevel = 0;

    /**
    * Map of pairs to card views in the matrix.
     */
    private Map<Pair<Integer, Integer>, FXCardView> cardsInMatrix = new HashMap<>();

    /**
    * Static block to initialize the colorMap with PawnColor as key and corresponding Image as value.
     */
    static {
        // Mapping RED PawnColor to its corresponding image.
        colorMap.put(PawnColor.RED, new Image(FXMainUI.class.getResource("RED.png").toExternalForm()));
        // Mapping GREEN PawnColor to its corresponding image.
        colorMap.put(PawnColor.GREEN, new Image(FXMainUI.class.getResource("GREEN.png").toExternalForm()));
        // Mapping BLUE PawnColor to its corresponding image.
        colorMap.put(PawnColor.BLUE, new Image(FXMainUI.class.getResource("BLUE.png").toExternalForm()));
        // Mapping YELLOW PawnColor to its corresponding image.
        colorMap.put(PawnColor.YELLOW, new Image(FXMainUI.class.getResource("YELLOW.png").toExternalForm()));
    }

    /**
     * This is the constructor for the FXMainUI class.
     * It initializes the new FXMainUI object by loading the FXML file, setting the opacity of the overlay panel, relocating and resizing the background view, setting up the message UI, and setting up the info move panel.
     * It also adds a listener to the boundsInLocalProperty to resize the background view when the bounds change.
     */
    public FXMainUI() {
        // Load the FXML file into this FXMainUI object.
        FXMLUtils.load(FXMainUI.class, "FXMainUI.fxml", this);

        // Set the opacity of the overlay panel to 0.
        overlayPanel.setOpacity(0);

        // Relocate the background view to the top-left corner and set its size to match the size of this FXMainUI object.
        backgroundView.relocate(0, 0);
        backgroundView.setFitWidth(getWidth());
        backgroundView.setFitHeight(getHeight());

        // Initialize the message UI and set it up.
        messageUI = new FXConsoleMessageUI(this);
        setupMessageUI();

        // Set the opacity of the info move panel to 0 and make it transparent to mouse events.
        infoMovePanel.setOpacity(0);
        infoMovePanel.setMouseTransparent(true);

        // Add a listener to the boundsInLocalProperty to resize the background view when the bounds change.
        boundsInLocalProperty().addListener((obs,oldv,newv)->{
            backgroundView.setFitWidth(newv.getWidth());
            backgroundView.setFitHeight(newv.getHeight());
        });
    }

    /**
     * This method returns the selected card position as a Pair of Integers.
     * The first Integer represents the row and the second Integer represents the column of the selected card in the matrix.
     * @return Pair<Integer, Integer> The position of the selected card.
     */
    public Pair<Integer, Integer> getCardPositionSelected() {
        return cardPositionSelected;
    }

    /**
     * This method opens the information panel with a given message.
     * It sets the text of the infoMoveLabel to the given message, plays a fade transition to make the infoMovePanel visible, and plays a translate transition to animate the infoMoveIcon.
     * @param message The message to display in the information panel.
     */
    public void openInfoPanel(String message) {
        infoMoveLabel.setText(message);

        infoMovePanel.toFront();

        FadeTransition fade = new FadeTransition(Duration.seconds(0.2), infoMovePanel);
        fade.setToValue(1);
        fade.play();

        infoIconAnimator = new TranslateTransition(Duration.seconds(0.3), infoMoveIcon);
        infoIconAnimator.setToX(10);
        infoIconAnimator.setCycleCount(-1);
        infoIconAnimator.setAutoReverse(true);
        infoIconAnimator.play();
    }

    /**
     * This method closes the information panel.
     * It creates a new FadeTransition for the infoMovePanel, sets the target opacity to 0, and plays the transition.
     * It also stops the animation of the infoMoveIcon.
     */
    public void closeInfoPanel() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.2), infoMovePanel);
        fade.setToValue(0);
        fade.play();
        infoIconAnimator.stop();
    }

    /**
     * This method sets up the message UI.
     * It binds the preferred width of the message UI to the width property of this FXMainUI object, sets the layout Y of the message UI to the height of this FXMainUI object minus 46, and adds the message UI to the children of this FXMainUI object.
     * It also adds a listener to the height property of this FXMainUI object to update the layout Y of the message UI when the height changes.
     */
    private void setupMessageUI() {
        messageUI.prefWidthProperty().bind(widthProperty());
        messageUI.setLayoutY(getHeight() - 46);

        heightProperty().addListener((obs, oldv, newv) -> messageUI.setLayoutY((Double) newv - 46));
        getChildren().add(messageUI);
    }

    /**
     * This method opens the message UI.
     * It sets the messageOpened flag to true, brings the message UI to the front, and plays an opening animation.
     * It also creates a new TranslateTransition for the message UI, sets the target Y coordinate to the negative of the preferred height of the message UI minus 46, and plays the transition.
     */
    public void openMessage() {
        messageOpened = true;
        messageUI.toFront();
        messageUI.makeOpenAnimation();
        TranslateTransition anim = new TranslateTransition(Duration.seconds(0.5), messageUI);
        anim.setToY(-(messageUI.getPrefHeight() - 46));
        anim.play();
    }

    /**
     * This method closes the message UI.
     * It sets the messageOpened flag to false and plays a closing animation for the message UI.
     * It also creates a new TranslateTransition for the message UI, sets the target Y coordinate to 0, and plays the transition.
     */
    public void closeMessage() {
        messageOpened = false;
        messageUI.makeCloseAnimation();
        TranslateTransition anim = new TranslateTransition(Duration.seconds(0.5), messageUI);
        anim.setToY(0);
        anim.play();
    }

    /**
     * This method toggles the visibility of the message UI.
     * If the message UI is currently opened, it will be closed.
     * If the message UI is currently closed, it will be opened.
     */
    public void openCloseMessages() {
        if (messageOpened)
            closeMessage();
        else
            openMessage();
    }

    /**
     * This method displays the waiting UI.
     * It creates a new FXWaitingForPlayerUI object, calculates the coordinates for centering the UI on the screen,
     * relocates the UI to the calculated coordinates, and adds the UI to the children of this FXMainUI object.
     */
    public void showWaitUI() {
        waitUI = new FXWaitingForPlayerUI(this);

        double x = (getWidth() - waitUI.getPrefWidth()) / 2;
        double y = (getHeight() - waitUI.getPrefHeight()) / 2;

        waitUI.relocate(x, y);
        getChildren().add(waitUI);
    }

    /**
     * This method removes the waiting UI from the children of this FXMainUI object.
     * It first checks if the waitUI is not null, then stops the waitUI and removes it from the children.
     */
    public void removeWaitingUI() {
        if (waitUI != null) {
            waitUI.stop();
            getChildren().remove(waitUI);
        }

    }

    /**
     * This method presents a dialog on the screen.
     * It first sets the fxBusy flag to true, then runs a series of operations on the JavaFX Application thread.
     * These operations include bringing the overlay panel to the front, running a pre-operation (if provided), adding the dialog to the overlay panel, and playing a fade transition to make the overlay panel visible.
     * After these operations, it waits for the fxBusy flag to be false.
     * Then, it runs another series of operations on the JavaFX Application thread to play a fade transition to make the overlay panel invisible and remove the dialog from the overlay panel.
     * Finally, it waits again for the fxBusy flag to be false.
     * @param dialog The dialog to present.
     * @param preOperation The pre-operation to run before presenting the dialog. This can be null.
     */
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

    /**
     * This method is used to wait until the UI is not busy.
     * It continuously checks the fxBusy flag and sleeps for 300 milliseconds if the flag is true.
     * If the flag is false, it breaks the loop and returns.
     * If the sleep operation is interrupted, it throws a RuntimeException.
     */
    private void waitFX() {
        while (fxBusy) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method is used to display the players in the game.
     * It creates a new FXPlayerScoreUI object for each player and adds it to the playerScoreItems list.
     * It then relocates each FXPlayerScoreUI object, adds it to the children of this FXMainUI object, and updates it.
     * @param me The current player.
     * @param opponents A list of the other players.
     */
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

    /**
     * This method is used to display the cards of a player.
     * It creates a new FXCardView object for each card in the player's hand and adds it to the children of this FXMainUI object, the cardsOnTable list, and the cardsInHand list.
     * It also sets the rotation of each FXCardView object to 0, relocates each FXCardView object, flips each FXCardView object if it is covered, and sets the card index of each FXCardView object.
     * If the player has a personal objective card, it creates a new FXCardView object for the personal objective card and adds it to the children of this FXMainUI object and the cardsOnTable list.
     * @param me The player whose cards are to be displayed.
     */
    public void showPlayerCards(Player me) {
        double y = yourCardLabelAnchor.getLayoutY() + 40;
        double x = yourCardLabelAnchor.getLayoutX();

        int index = 1;

        for (Card c : me.getCardsInHand()) {
            FXCardView view = FXCardFactory.getView(c);

            if (!getChildren().contains(view)) {
                getChildren().add(view);
            }

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

    /**
     * This method is used to display the common cards in the game.
     * It retrieves the common resource cards, gold cards, and objective cards from the model.
     * It also retrieves the resource and gold decks from the model.
     * Then, it calls the methods to display the common objective cards, gold cards, resource cards, resource deck, and gold deck.
     * @param model The PlayGround model from which the common cards and decks are retrieved.
     */
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

    /**
     * This method is used to display the resource deck in the game.
     * It retrieves the cards from the resource deck and creates a new FXCardView object for each card.
     * It then adds each FXCardView object to the children of this FXMainUI object, the cardsOnTable list, and the resourcesDeckFXCards list.
     * It also relocates each FXCardView object, flips each FXCardView object if it is facing front, and sets the rotation of each FXCardView object to a random angle between 0 and 5 degrees.
     * @param resourceDeck The resource deck from which the cards are retrieved.
     */
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

    /**
     * This method is used to display the gold deck in the game.
     * It retrieves the cards from the gold deck and creates a new FXCardView object for each card.
     * It then adds each FXCardView object to the children of this FXMainUI object, the cardsOnTable list, and the goldDeckFXCards list.
     * It also relocates each FXCardView object, flips each FXCardView object if it is facing front, and sets the rotation of each FXCardView object to a random angle between 0 and 5 degrees.
     * @param goldDeck The gold deck from which the cards are retrieved.
     */
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

    /**
     * This method is used to display the gold cards in the game.
     * It retrieves each card from the provided list of gold cards and creates a new FXCardView object for each card.
     * It then adds each FXCardView object to the children of this FXMainUI object, the cardsOnTable list, and the goldBoardFXCards list.
     * It also relocates each FXCardView object, flips each FXCardView object if it is covered, and sets the rotation of each FXCardView object to 0.
     * @param goldCards The list of gold cards to be displayed.
     */
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

    /**
     * This method is used to display the resource cards in the game.
     * It retrieves each card from the provided list of resource cards and creates a new FXCardView object for each card.
     * It then adds each FXCardView object to the children of this FXMainUI object, the cardsOnTable list, and the resourcesBoardFXCards list.
     * It also relocates each FXCardView object, flips each FXCardView object if it is covered, and sets the rotation of each FXCardView object to 0.
     * @param resourceCards The list of resource cards to be displayed.
     */
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

    /**
     * This method is used to display the common objective cards in the game.
     * It retrieves each card from the provided list of common objective cards and creates a new FXCardView object for each card.
     * It then adds each FXCardView object to the children of this FXMainUI object and the cardsOnTable list.
     * It also relocates each FXCardView object, sets the rotation of each FXCardView object to 0, and flips each FXCardView object if it is covered.
     * @param commonObjectives The list of common objective cards to be displayed.
     */
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

    /**
     * This method is used to close the dialog.
     * It sets the fxBusy flag to false, indicating that the UI is no longer busy.
     */
    public void closeDialog() {
        fxBusy = false;
    }

    /**
     * This method is used to inject a message into the message UI.
     * It calls the inject method of the messageUI object with the provided message.
     * @param message The message to be injected into the message UI.
     */
    public void injectMessage(String message) {
        messageUI.inject(message);
    }

    /**
     * This method is used to clear all the cards from the game.
     * It removes all the cards from the children of this FXMainUI object, the cardsOnTable list, and the playerScoreItems list.
     * It also disables interaction for each card on the table and sets its card index to -1.
     * Finally, it clears the cardsInHand list, the cardsOnTable list, the cardsInMatrix map, the resourcesDeckFXCards list, the goldDeckFXCards list, the resourcesBoardFXCards list, the goldBoardFXCards list, and the playerScoreItems list.
     */
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

    /**
     * This method is used to display the play area of a player in the game.
     * It retrieves the play area from the provided player and calculates the number of rows and columns based on the cards in the play area.
     * It then creates a list of visible rows and columns based on the dimensions of the play area.
     * For each card in the play area, it checks if a corresponding FXCardView object already exists in the matrix.
     * If not, it creates a new FXCardView object for the card and adds it to the list of cards in the game.
     * Finally, it reorders the cards in the game by their Z level.
     * @param me The player whose play area is to be displayed.
     */
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

    /**
     * This method is used to reorder the list of cards in the game based on their Z level.
     * It sorts the provided list of cards in ascending order of their Z level.
     * Then, it brings each card in the sorted list to the front.
     * @param cardsInGame The list of cards to be reordered.
     */
    private void reorderByZLevel(List<FXCardView> cardsInGame) {
        Collections.sort(cardsInGame, (o1, o2) -> o1.getZLevel() - o2.getZLevel());
        cardsInGame.forEach(c -> c.toFront());
    }

    /**
     * This method is used to retrieve a card from the matrix based on its row and column indices.
     * It iterates over the keys of the cardsInMatrix map, which are pairs of integers representing the row and column indices of the cards.
     * If it finds a pair that matches the provided row and column indices, it retrieves the corresponding card from the map and returns it.
     * If no matching pair is found, it returns null.
     * @param row The row index of the card to be retrieved.
     * @param col The column index of the card to be retrieved.
     * @return FXCardView The card at the provided row and column indices, or null if no such card exists.
     */
    public FXCardView getCardInMatrix(int row, int col) {
        for (Pair<Integer, Integer> p : cardsInMatrix.keySet()) {
            if (p.getFirst() == row && p.getSecond() == col) {
                FXCardView card = cardsInMatrix.get(p);
                return card;
            }
        }

        return null;
    }

    /**
     * This method is used to display a card on the game board.
     * It calculates the position of the card based on the provided row and column indices and the dimensions of the game board.
     * It then creates a new FXCardView object for the card and sets its position on the game board.
     * The card is also added to the list of cards on the table and the map of cards in the matrix.
     *
     * @param sideCard The side of the card to be displayed.
     * @param row The row index of the card on the game board.
     * @param col The column index of the card on the game board.
     * @param visibleRows A list of the visible rows on the game board.
     * @param visibleCols A list of the visible columns on the game board.
     * @return FXCardView The FXCardView object representing the displayed card.
     */
    private FXCardView showCard(SideOfCard sideCard, int row, int col, List<Integer> visibleRows, List<Integer> visibleCols) {

        int deltaCardX = 74;
        int deltaCardY = 39;

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

    /**
     * This method prepares the player for picking a card.
     * It enables interaction for each card in the player's hand and sets a click listener for each card.
     * When a card is clicked, it sets the currentCardIndexPlayed to the index of the clicked card, increases the Z level of the clicked card by 1, and sets the fxBusy flag to false.
     */
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

    /**
     * This method is used to wait for user interaction.
     * It sets the fxBusy flag to true and then calls the waitFX method to wait until the fxBusy flag is false.
     */
    public void waitUserInteraction() {
        fxBusy = true;
        waitFX();
    }

    /**
     * This method returns the index of the current card played.
     * @return int The index of the current card played.
     */
    public int getCurrentCardIndexPlayed() {
        return currentCardIndexPlayed;

    }

    /**
     * This method is used to get the list of cards in the player's hand.
     * @return List<FXCardView> The list of FXCardView objects representing the cards in the player's hand.
     */
    public List<FXCardView> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * This method is used to enable interaction with the resource deck.
     * It retrieves the last card from the resourcesDeckFXCards list and enables interaction for it.
     * It also sets a click listener for the card that sets the currentPlayCardString to "RESOURCE-DECK", disables interaction for the card, removes the click listener, closes the info panel, and sets the fxBusy flag to false.
     */
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

    /**
     * This method is used to enable interaction with the gold deck.
     * It retrieves the last card from the goldDeckFXCards list and enables interaction for it.
     * It also sets a click listener for the card that sets the currentPlayCardString to "GOLD-DECK", disables interaction for the card, removes the click listener, closes the info panel, and sets the fxBusy flag to false.
     */
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

    /**
     * This method is used to enable interaction with the resource and gold cards on the board.
     * For each card in the resourcesBoardFXCards and goldBoardFXCards lists, it enables interaction for the card and sets a click listener for the card.
     * When a card is clicked, it sets the currentPlayCardString to "RESOURCE-CARD" or "GOLD-CARD" followed by the index of the card in the list (starting from 1),
     * disables interaction for the card, removes the click listener, closes the info panel, and sets the fxBusy flag to false.
     */
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

    /**
     * This method is used to get the current play card string.
     * @return String The string representing the current play card.
     */
    public String getCurrentPlayCardString() {

        return currentPlayCardString;
    }

    /**
     * This method is used to disable corner interaction for all cards in the matrix.
     * It iterates over the values of the cardsInMatrix map (which are FXCardView objects representing the cards) and calls the disableCornerInteraction method for each card.
     */
    public void disableCornerInteraction() {
        cardsInMatrix.values().forEach(c -> {
            c.disableCornerInteraction();
        });
    }

    /**
     * This method is used to enable corner interaction for all cards in the matrix.
     * It iterates over the values of the cardsInMatrix map (which are FXCardView objects representing the cards) and calls the enableCornerInteraction method for each card.
     * It also sets a card corner event listener for each card that calculates the pair of the card and corner, disables corner interaction, and sets the fxBusy flag to false.
     */
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

    /**
     * This method calculates the position of a corner of a card in the matrix.
     * It takes as input a card and a corner of the card, and returns a pair of integers representing the row and column indices of the corner in the matrix.
     * The method first retrieves the current position of the card in the matrix.
     * Then, depending on the corner, it calculates the position of the corner by adding or subtracting 1 from the row and column indices of the card.
     * If the corner is the top left, it subtracts 1 from both the row and column indices.
     * If the corner is the top right, it subtracts 1 from the row index and adds 1 to the column index.
     * If the corner is the bottom left, it adds 1 to the row index and subtracts 1 from the column index.
     * If the corner is the bottom right, it adds 1 to both the row and column indices.
     * If the corner is not one of the four defined corners, it returns null.
     *
     * @param card The card for which the corner position is to be calculated.
     * @param corner The corner of the card for which the position is to be calculated.
     * @return Pair<Integer, Integer> The pair of integers representing the row and column indices of the corner in the matrix, or null if the corner is not one of the four defined corners.
     */
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

    /**
     * This method is used to display the play area of a specific player.
     * It creates a new Pane and resizes it to fit the screen dimensions.
     * It then creates an ImageView for the background and adds it to the Pane.
     * The method retrieves the play area from the player and calculates the number of rows and columns based on the cards in the play area.
     * It then creates a map to store the cards in the matrix.
     * The method then iterates over the rows and columns and adds the visible rows and columns to their respective lists.
     * It then iterates over the rows and columns again and for each card in the play area, it checks if a corresponding FXCardView object already exists in the matrix.
     * If not, it creates a new FXCardView object for the card and adds it to the list of cards in the game.
     * Finally, it takes a snapshot of the root Pane and displays it in a new Stage.
     *
     * @param player The player whose play area is to be displayed.
     */
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
                            int deltaCardX = 74;
                            int deltaCardY = 39;


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
                            card.relocate(deltaXFromCenter - (card.getPrefWidth() / 2)*getScaleX(), deltaYFromCenter - (card.getPrefHeight() / 2)*getScaleY());

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

    /**
     * This method is used to determine if a given side of a card is the front side.
     * It compares the provided side of the card with the front side of the parent card.
     * If they are equal, it returns Side.FRONT, indicating that the provided side is the front side.
     * If they are not equal, it returns Side.BACK, indicating that the provided side is the back side.
     *
     * @param card The side of the card to be checked.
     * @return Side The side of the card (either FRONT or BACK).
     */
    public static Side isFrontSide(SideOfCard card) {
        if(card.getParentCard().getFront().equals(card))
            return Side.FRONT;
        else
            return Side.BACK;
    }
}