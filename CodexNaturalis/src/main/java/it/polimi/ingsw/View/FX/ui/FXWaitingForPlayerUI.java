package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class FXWaitingForPlayerUI extends FXDialogGamePane {

    @FXML
    private ImageView preloader;

    private RotateTransition rotate;

    public FXWaitingForPlayerUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXWaitingForPlayerUI.class,"FXWaitingForPlayerUI.fxml",this);

        style();

        preloader.setRotationAxis(new Point3D(0,1,0));
        rotate = new RotateTransition(Duration.seconds(2),preloader);
        rotate.setToAngle(360);
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        rotate.setCycleCount(-1);
        rotate.play();
    }

    public void stop() {
        rotate.stop();
    }
}
