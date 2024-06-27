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

public class FXChooseObjectiveCardUI extends FXDialogGamePane {

    private int choose = 0;

    public FXChooseObjectiveCardUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXChooseObjectiveCardUI.class,"FXChooseObjectiveCardUI.fxml",this);

        style();

    }

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

    public int getChoose() {
        return choose;
    }
}
