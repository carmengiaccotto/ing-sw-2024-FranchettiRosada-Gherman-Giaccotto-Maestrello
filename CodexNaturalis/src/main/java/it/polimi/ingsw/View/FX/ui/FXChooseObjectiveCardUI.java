package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardView;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.ScaleTransition;
import javafx.scene.Cursor;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This class represents the UI for choosing an objective card in the game.
 * It extends the FXDialogGamePane class and contains a variable to store the chosen card.
 */
public class FXChooseObjectiveCardUI extends FXDialogGamePane {

    /**
    * The chosen card.
     */
    private int choose = 0;

    /**
     * This is the constructor for the FXChooseObjectiveCardUI class.
     * It initializes the new FXChooseObjectiveCardUI object with the given owner and sets up the style of the UI.
     * @param owner The owner of this UI.
     */
    public FXChooseObjectiveCardUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXChooseObjectiveCardUI.class,"FXChooseObjectiveCardUI.fxml",this);

        style();

    }

    /**
     * Fills the UI with the given objective cards.
     * It gets the views of the cards, sets their locations, adds them to the children of this UI, sets up mouse click events to choose a card and close the UI, installs scale events for the cards, and flips the cards.
     * @param objectives The objective cards to fill the UI with.
     */
    public void fill(ArrayList<ObjectiveCard> objectives)
    {
        FXCardView obj1 = FXCardFactory.getView(objectives.get(0));
        FXCardView obj2 = FXCardFactory.getView(objectives.get(1));

        double y = (getPrefHeight()-obj1.getPrefHeight()) / 2;

        obj1.relocate(50,y);
        obj2.relocate(getPrefWidth()-obj2.getPrefWidth()-50,y);

        getChildren().add(obj1);
        getChildren().add(obj2);

        obj1.setOnMouseClicked(e->{
            choose = 1;
            close();

        });

        obj2.setOnMouseClicked(e->{
            choose = 2;
            close();

        });

        installScaleEvents(obj1);
        installScaleEvents(obj2);

        obj1.flip();
        obj2.flip();
    }

    /**
     * Installs scale events for the given card.
     * It sets the cursor to a hand cursor, and sets up mouse entered and exited events to scale the card up and down, respectively.
     * @param card The card to install scale events for.
     */
    private void installScaleEvents(FXCardView card)
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
     * Returns the chosen card.
     * @return The chosen card.
     */
    public int getChoose() {
        return choose;
    }
}