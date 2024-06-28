package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardView;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class represents the UI for choosing a side to play in the game.
 * It extends the FXDialogGamePane class and contains a string indicating the selected side and a button for confirmation.
 */
public class FXChooseSideToPlayUI extends FXDialogGamePane {

    /**
     *The selected side ("FRONT" or "BACK").
     */
    private String sideSelected;

    /**
    * The button for confirmation.
     */
    @FXML
    private Button ok;

    /**
     * This is the constructor for the FXChooseSideToPlayUI class.
     * It initializes the new FXChooseSideToPlayUI object with the given owner, sets up the style of the UI, and sets up the card and button.
     * @param owner The owner of this UI.
     */
    public FXChooseSideToPlayUI(FXMainUI owner) {
        super(owner);

        FXCardView card = new FXCardView(owner.getCardsInHand().get(owner.getCurrentCardIndexPlayed()-1));

        FXMLUtils.load(FXChooseSideToPlayUI.class,"FXChooseSideToPlayUI.fxml",this);
        style();

        double x = (getPrefWidth() - card.getPrefWidth()) / 2;
        double y = (getPrefHeight() - card.getPrefHeight()) / 2;

        card.relocate(x,y);

        getChildren().add(card);

        card.setMouseTransparent(false);

        sideSelected = card.getSide();

        card.setOnMouseClicked(e->{

                card.flip();
                FXCardView nativeCard = FXCardFactory.getView(owner.getCardsInHand().get(owner.getCurrentCardIndexPlayed()-1).getCard());
                nativeCard.flip();
                if (sideSelected.equals("FRONT"))
                    sideSelected = "BACK";
                else
                    sideSelected = "FRONT";

        });


        ok.setOnAction(e->{
            close();
        });
    }

    /**
     * Returns the selected side.
     * @return The selected side ("FRONT" or "BACK").
     */
    public String getSide() {
        return sideSelected;
    }
}
