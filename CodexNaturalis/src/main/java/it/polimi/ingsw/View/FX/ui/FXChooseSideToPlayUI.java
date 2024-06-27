package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardFactory;
import it.polimi.ingsw.View.FX.ui.game.cards.FXCardView;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXChooseSideToPlayUI extends FXDialogGamePane {

    private String sideSelected;

    @FXML
    private Button ok;

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

    public String getSide() {
        return sideSelected;
    }
}
