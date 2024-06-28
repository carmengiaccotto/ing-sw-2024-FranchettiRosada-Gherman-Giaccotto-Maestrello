package it.polimi.ingsw.View.FX.ui.game.cards;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * This class represents a view of a card in the FX user interface.
 * It extends the AnchorPane class from the JavaFX library.
 * It contains an ImageView for the card image and Rectangles for the card corners.
 * It also contains fields for the front and back images of the card, the card object, and various properties of the card view.
 */
public class FXCardView extends AnchorPane {

    /**
     * This enum represents the corners of a card.
     */
    public enum CardCorner
    {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    @FXML
    private ImageView cardImageView; // The ImageView for the card image.

    @FXML
    private Rectangle topleft; // The Rectangle for the top left corner of the card.

    @FXML
    private Rectangle topright; // The Rectangle for the top right corner of the card.

    @FXML
    private Rectangle bottleft; // The Rectangle for the bottom left corner of the card.

    @FXML
    private Rectangle bottright; // The Rectangle for the bottom right corner of the card.

    private Image frontImage; // The image for the front of the card.
    private Image backImage; // The image for the back of the card.

    private Card card; // The card object.

    boolean isFrontShowing = false; // Whether the front of the card is showing.

    boolean interactionEnabled = false; // Whether interaction with the card is enabled.

    private CardEventListener listener; // The event listener for card clicks.
    private CardCornerEventListener cornerListener; // The event listener for card corner clicks.

    private int cardIndex = -1; // The index of the card.

    private Pair<Integer,Integer> currentMatrixPosition; // The current position of the card in the matrix.

    private Timeline animatorCorners; // The timeline for the corner animations.

    private int ZLevel = 0; // The Z level of the card.

    /**
     * This is a constructor for the FXCardView class.
     * It initializes the FXCardView object with the given card ID.
     * It loads the FXML file for the card view and sets up the card image view and card corners.
     * It also sets up mouse click events for the card corners to call the appropriate methods on the corner listener.
     * @param idCard The ID of the card for this card view.
     */
    public FXCardView(int idCard)
    {
        FXMLUtils.load(FXCardView.class,"FXCardView.fxml",this);
        frontImage = new Image(FXCardFactory.class.getResource("front/"+idCard+".jpg").toExternalForm());
        backImage = new Image(FXCardFactory.class.getResource("back/"+idCard+".jpg").toExternalForm());

        cardImageView.setImage(backImage);

        cardImageView.setRotationAxis(new Point3D(0,1,0));
        cardImageView.setRotate(180);


        topleft.setOpacity(0.0);
        topright.setOpacity(0.0);
        bottleft.setOpacity(0.0);
        bottright.setOpacity(0.0);

        topleft.setMouseTransparent(true);
        topright.setMouseTransparent(true);
        bottleft.setMouseTransparent(true);
        bottright.setMouseTransparent(true);

        topleft.setCursor(Cursor.HAND);
        topright.setCursor(Cursor.HAND);
        bottleft.setCursor(Cursor.HAND);
        bottright.setCursor(Cursor.HAND);

        topleft.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.TOP_LEFT));
        topright.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.TOP_RIGHT));
        bottleft.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.BOTTOM_LEFT));
        bottright.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.BOTTOM_RIGHT));
    }

    /**
     * This is a copy constructor for the FXCardView class.
     * It initializes the new FXCardView object with the properties of the given FXCardView object.
     * It loads the FXML file for the card view and sets up the card image view and card corners.
     * It also sets up mouse click events for the card corners to call the appropriate methods on the corner listener.
     * @param card The FXCardView object to copy.
     */
    public FXCardView(FXCardView card)
    {
        FXMLUtils.load(FXCardView.class,"FXCardView.fxml",this);

        frontImage = card.getFrontImage();
        backImage = card.getBackImage();
        cardImageView.setImage(card.getCurrentImage());

        cardImageView.setRotationAxis(new Point3D(0,1,0));
        cardImageView.setRotate(card.getRotationAngle());

        topleft.setOpacity(0.0);
        topright.setOpacity(0.0);
        bottleft.setOpacity(0.0);
        bottright.setOpacity(0.0);

        topleft.setMouseTransparent(true);
        topright.setMouseTransparent(true);
        bottleft.setMouseTransparent(true);
        bottright.setMouseTransparent(true);

        topleft.setCursor(Cursor.HAND);
        topright.setCursor(Cursor.HAND);
        bottleft.setCursor(Cursor.HAND);
        bottright.setCursor(Cursor.HAND);

        topleft.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.TOP_LEFT));
        topright.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.TOP_RIGHT));
        bottleft.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.BOTTOM_LEFT));
        bottright.setOnMouseClicked(e->cornerListener.onCornerClick(this,CardCorner.BOTTOM_RIGHT));

        isFrontShowing = card.isFrontShowing;
    }

    /**
     * Returns the current rotation angle of the card image view.
     * @return The current rotation angle of the card image view.
     */
    public double getRotationAngle()
    {
        return cardImageView.getRotate();
    }

    /**
     * Returns the current Z level of the card.
     * @return The current Z level of the card.
     */
    public int getZLevel() {
        return ZLevel;
    }

    /**
     * Sets the Z level of the card.
     * @param ZLevel The new Z level for the card.
     */
    public void setZLevel(int ZLevel) {
        this.ZLevel = ZLevel;
    }

    /**
     * Returns the current image of the card image view.
     * @return The current image of the card image view.
     */
    public Image getCurrentImage()
    {
        return cardImageView.getImage();
    }

    /**
     * Sets the event listener for card clicks.
     * @param listener The new event listener for card clicks.
     */
    public void setCardClickListener(CardEventListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the event listener for card corner clicks.
     * @param listener The new event listener for card corner clicks.
     */
    public void setCardCornerEventListener(CardCornerEventListener listener)
    {
        cornerListener = listener;
    }

    /**
     * Returns the current position of the card in the matrix.
     * @return The current position of the card in the matrix.
     */
    public Pair<Integer, Integer> getCurrentMatrixPosition() {
        return currentMatrixPosition;
    }

    /**
     * Sets the current position of the card in the matrix.
     * @param currentMatrixPosition The new position of the card in the matrix.
     */
    public void setCurrentMatrixPosition(Pair<Integer, Integer> currentMatrixPosition) {
        this.currentMatrixPosition = currentMatrixPosition;
    }

    /**
     * Returns the index of the card.
     * @return The index of the card.
     */
    public int getCardIndex() {
        return cardIndex;
    }

    /**
     * Sets the index of the card.
     * @param cardIndex The new index for the card.
     */
    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    /**
     * Returns the side of the card that is currently showing.
     * @return "FRONT" if the front of the card is showing, "BACK" otherwise.
     */
    public String getSide()
    {
        return (isFrontShowing) ? "FRONT" : "BACK";
    }

    /**
     * Flips the card without animation if the back of the card is showing.
     */
    public void flipIfCoveredNoAnim()
    {
        if (!isFrontShowing)
            flipNoAnim();
    }

    /**
     * Flips the card without animation if the front of the card is showing.
     */
    public void flipIfFrontNoAnim()
    {
        if (isFrontShowing)
            flipNoAnim();
    }

    /**
     * Enables interaction with the card.
     * It sets the interactionEnabled field to true, changes the cursor to a hand cursor, installs scale events for mouse hover, and sets up a mouse click event to call the onCardClick method on the listener.
     */
    public void enableInteraction()
    {
        interactionEnabled = true;
        setCursor(Cursor.HAND);
        installScaleEvents();
        setOnMouseClicked(e->{
            if (listener != null)
                listener.onCardClick(this);
        });
    }

    /**
     * Disables interaction with the card.
     * It sets the interactionEnabled field to false, changes the cursor to a default cursor, removes the mouse click event, sets the listener to null, uninstalls scale events, and resets the scale of the card.
     */
    public void disableInteraction()
    {
        interactionEnabled = false;
        setCursor(Cursor.DEFAULT);
        setOnMouseClicked(e->{});
        listener = null;
        uninstallScaleEvents();
        setScaleX(1);
        setScaleY(1);
    }

    /**
     * Uninstalls scale events for mouse hover.
     * It changes the cursor to a default cursor and removes the mouse entered and exited events.
     */
    private void uninstallScaleEvents()
    {
        setCursor(Cursor.DEFAULT);
        setOnMouseEntered(e->{});
        setOnMouseExited(e->{});

    }

    /**
     * Installs scale events for mouse hover.
     * It sets up mouse entered and exited events to scale the card up and down, respectively.
     */
    private void installScaleEvents()
    {
        setOnMouseEntered(e->{
            toFront();
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.2),this);
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.play();

        });

        setOnMouseExited(e->{
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.2),this);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });
    }

    /**
     * Moves the card to the given x and y coordinates with animation.
     * It creates a timeline for the layoutX and layoutY properties of the card to animate the movement, and a rotate transition to rotate the card during the movement.
     * @param x The x coordinate to move the card to.
     * @param y The y coordinate to move the card to.
     */
    public void moveAt(double x, double y)
    {
        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(layoutXProperty(),x),new KeyValue(layoutYProperty(),y)));
        animation.play();
        RotateTransition rotate = new RotateTransition(Duration.seconds(1),this);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        rotate.play();
    }

    /**
     * Flips the card without animation.
     * It sets the rotation of the card image view to 0, toggles the isFrontShowing field, and sets the image of the card image view to the front or back image depending on the isFrontShowing field.
     */
    public void flipNoAnim()
    {
        cardImageView.setRotate(0);
        isFrontShowing = !isFrontShowing;
        cardImageView.setImage((isFrontShowing) ? frontImage : backImage);
    }

    /**
     * Flips the card with animation.
     * It creates a RotateTransition for the card image view, a PauseTransition to change the card face, and a ScaleTransition to scale the card up and down.
     * It then plays these animations in parallel.
     */
    public void flip()
    {
        RotateTransition rotator = createRotator(cardImageView);
        PauseTransition ptChangeCardFace = changeCardFace(cardImageView);
        ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3),this);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);

        ParallelTransition parallelTransition = new ParallelTransition(rotator, ptChangeCardFace,scale);
        parallelTransition.play();


    }

    /**
     * Changes the card face after a pause.
     * It toggles the isFrontShowing field and sets the image of the card image view to the front or back image depending on the isFrontShowing field.
     * @param card The ImageView of the card.
     * @return The PauseTransition that changes the card face.
     */
    private PauseTransition changeCardFace(ImageView card) {
        isFrontShowing = !isFrontShowing;
        PauseTransition pause = new PauseTransition(Duration.millis(150));

        if (isFrontShowing) {
            pause.setOnFinished(
                    e -> {
                        card.setImage(frontImage);
                    });
        } else {
            pause.setOnFinished(
                    e -> {
                        card.setImage(backImage);
                    });
        }

        return pause;
    }

    /**
     * Returns the image for the front of the card.
     * @return The image for the front of the card.
     */
    public Image getFrontImage() {
        return frontImage;
    }

    /**
     * Returns the image for the back of the card.
     * @return The image for the back of the card.
     */
    public Image getBackImage() {
        return backImage;
    }

    /**
     * Returns the card object.
     * @return The card object.
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the card object.
     * @param card The new card object.
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * Creates a RotateTransition for the card.
     * It sets the rotation axis to the Y axis and the rotation angle to 180 or 360 depending on the isFrontShowing field.
     * @param card The ImageView of the card.
     * @return The RotateTransition for the card.
     */
    private RotateTransition createRotator(ImageView card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(300), card);
        rotator.setAxis(Rotate.Y_AXIS);

        if (!isFrontShowing) {
            rotator.setFromAngle(180);
            rotator.setToAngle(360);
        } else {
            rotator.setFromAngle(360);
            rotator.setToAngle(180);
        }
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);

        return rotator;
    }

    /**
     * Returns whether interaction with the card is enabled.
     * @return True if interaction with the card is enabled, false otherwise.
     */
    public boolean isInteractionEnabled() {
        return interactionEnabled;
    }

    /**
     * Disables interaction with the card corners.
     * It stops the corner animations, makes the card corners transparent to mouse events, and sets the opacity of the card corners to 0.
     */
    public void disableCornerInteraction()
    {
        animatorCorners.stop();
        topleft.setMouseTransparent(true);
        topright.setMouseTransparent(true);
        bottleft.setMouseTransparent(true);
        bottright.setMouseTransparent(true);

        topleft.setOpacity(0);
        topright.setOpacity(0);
        bottleft.setOpacity(0);
        bottright.setOpacity(0);
    }

    /**
     * Enables interaction with the card corners.
     * It creates a new timeline for the corner animations, sets the timeline to auto reverse and loop indefinitely, and starts the timeline.
     * It also makes the card corners not transparent to mouse events.
     */
    public void enableCornerInteraction()
    {
        animatorCorners = new Timeline(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(topleft.opacityProperty(),1),
                new KeyValue(topright.opacityProperty(),1),
                new KeyValue(bottleft.opacityProperty(),1),
                new KeyValue(bottright.opacityProperty(),1)));

        animatorCorners.setAutoReverse(true);
        animatorCorners.setCycleCount(-1);
        animatorCorners.play();

        topleft.setMouseTransparent(false);
        topright.setMouseTransparent(false);
        bottleft.setMouseTransparent(false);
        bottright.setMouseTransparent(false);
    }
}
