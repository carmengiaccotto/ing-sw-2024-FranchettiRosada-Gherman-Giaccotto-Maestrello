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

public class FXCardView extends AnchorPane {

    public enum CardCorner
    {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    @FXML
    private ImageView cardImageView;

    @FXML
    private Rectangle topleft;

    @FXML
    private Rectangle topright;

    @FXML
    private Rectangle bottleft;

    @FXML
    private Rectangle bottright;

    private Image frontImage;
    private Image backImage;

    private Card card;

    boolean isFrontShowing = false;

    boolean interactionEnabled = false;

    private CardEventListener listener;
    private CardCornerEventListener cornerListener;

    private int cardIndex = -1;

    private Pair<Integer,Integer> currentMatrixPosition;

    private Timeline animatorCorners;

    private int ZLevel = 0;

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

    public double getRotationAngle()
    {
        return cardImageView.getRotate();
    }

    public int getZLevel() {
        return ZLevel;
    }

    public void setZLevel(int ZLevel) {
        this.ZLevel = ZLevel;
    }

    public Image getCurrentImage()
    {
        return cardImageView.getImage();
    }

    public void setCardClickListener(CardEventListener listener) {
        this.listener = listener;
    }

    public void setCardCornerEventListener(CardCornerEventListener listener)
    {
        cornerListener = listener;
    }

    public Pair<Integer, Integer> getCurrentMatrixPosition() {
        return currentMatrixPosition;
    }

    public void setCurrentMatrixPosition(Pair<Integer, Integer> currentMatrixPosition) {
        this.currentMatrixPosition = currentMatrixPosition;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public String getSide()
    {
        return (isFrontShowing) ? "FRONT" : "BACK";
    }

    public void flipIfCoveredNoAnim()
    {
        if (!isFrontShowing)
            flipNoAnim();
    }

    public void flipIfFrontNoAnim()
    {
        if (isFrontShowing)
            flipNoAnim();
    }

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

    private void uninstallScaleEvents()
    {
        setCursor(Cursor.DEFAULT);
        setOnMouseEntered(e->{});
        setOnMouseExited(e->{});

    }
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



    public void moveAt(double x, double y)
    {
        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1),new KeyValue(layoutXProperty(),x),new KeyValue(layoutYProperty(),y)));
        animation.play();
        RotateTransition rotate = new RotateTransition(Duration.seconds(1),this);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        rotate.play();
    }

    public void flipNoAnim()
    {
        cardImageView.setRotate(0);
        isFrontShowing = !isFrontShowing;
        cardImageView.setImage((isFrontShowing) ? frontImage : backImage);
    }

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



    public Image getFrontImage() {
        return frontImage;
    }

    public Image getBackImage() {
        return backImage;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

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

    public boolean isInteractionEnabled() {
        return interactionEnabled;
    }

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
