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

public class FXChooseInitialCardUI extends FXDialogGamePane {

    @FXML
    private ImageView frontView;

    @FXML
    private ImageView backView;

    String side = "";

    public FXChooseInitialCardUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXChooseInitialCardUI.class,"FXChooseInitialCardUI.fxml",this);

        style();

    }

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

    public String getSide() {
        return side;
    }
}
