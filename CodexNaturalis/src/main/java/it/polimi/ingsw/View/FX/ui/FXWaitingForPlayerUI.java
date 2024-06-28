package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class represents the UI for waiting for a player in the game.
 * It extends the FXDialogGamePane class and contains an image view for the preloader and a rotate transition for the preloader.
 */
public class FXWaitingForPlayerUI extends FXDialogGamePane {

    /**
    * The image view for the preloader.
     */
    @FXML
    private ImageView preloader;

    /**
    * The rotate transition for the preloader.
     */
    private RotateTransition rotate;

    /**
     * This is the constructor for the FXWaitingForPlayerUI class.
     * It initializes the new FXWaitingForPlayerUI object with the given owner, loads the FXML file, sets up the style of the UI, sets the rotation axis of the preloader, and sets up and plays the rotate transition for the preloader.
     * @param owner The owner of this UI.
     */
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

    /**
     * Stops the rotate transition for the preloader.
     */
    public void stop() {
        rotate.stop();
    }
}