package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardView;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class represents the UI for choosing an initial card in the game.
 * It extends the FXDialogGamePane class and contains various UI elements and a string indicating the side of the card.
 */
public class FXChooseInitialCardUI extends FXDialogGamePane {

    // The ImageView for the front view of the card.
    @FXML
    private ImageView frontView;

    // The ImageView for the back view of the card.
    @FXML
    private ImageView backView;

    // A string indicating the side of the card ("FRONT" or "BACK").
    String side = "";

    /**
     * This is the constructor for the FXChooseInitialCardUI class.
     * It initializes the new FXChooseInitialCardUI object with the given owner and sets up the style of the UI.
     * @param owner The owner of this UI.
     */
    public FXChooseInitialCardUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXChooseInitialCardUI.class,"FXChooseInitialCardUI.fxml",this);

        style();

    }

    /**
     * Shows the initial card in the UI.
     * It gets the view of the card, sets the images of the front and back views, installs scale events for the front and back views, and sets up mouse click events to flip the card and close the UI.
     * @param card The initial card to show.
     */
    public void showInitialCard(InitialCard card)
    {
        FXCardView cardView = FXCardFactory.getView(card);

        Platform.runLater(()->{

            frontView.setImage(cardView.getFrontImage());
            backView.setImage(cardView.getBackImage());

            installScaleEvents(frontView);
            installScaleEvents(backView);

            frontView.setOnMouseClicked(e->{
                side = "FRONT";
                cardView.flipIfCoveredNoAnim();
                close();
            });
            backView.setOnMouseClicked(e->{
                side = "BACK";
                cardView.flipIfFrontNoAnim();
                close();
            });

        });
    }

    /**
     * Installs scale events for the given card.
     * It sets the cursor to a hand cursor, and sets up mouse entered and exited events to scale the card up and down, respectively.
     * @param card The card to install scale events for.
     */
    private void installScaleEvents(ImageView card)
    {
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(e->{

            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3),card);
            scale.setToX(1.2);
            scale.setToY(1.2);
            scale.play();

        });

        card.setOnMouseExited(e->{
            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3),card);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });
    }

    /**
     * Returns the side of the card.
     * @return The side of the card ("FRONT" or "BACK").
     */
    public String getSide() {
        return side;
    }
}
