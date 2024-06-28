package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the UI for console messages in the game.
 * It extends the AnchorPane class and contains various UI elements and a reference to the main UI.
 */
public class FXConsoleMessageUI extends AnchorPane {

    // The main UI.
    private FXMainUI owner;

    // The button for opening and closing the console messages.
    @FXML
    private Button openCloseButton;

    // The button for zooming in.
    @FXML
    private Button zoomIn;

    // The button for zooming out.
    @FXML
    private Button zoomOut;

    // The area for displaying messages.
    @FXML
    private TextArea messageArea;

    /**
     * This is the constructor for the FXConsoleMessageUI class.
     * It initializes the new FXConsoleMessageUI object with the given owner, loads the FXML file, and sets up the buttons.
     * @param owner The owner of this UI.
     */
    public FXConsoleMessageUI(FXMainUI owner)
    {
        FXMLUtils.load(FXConsoleMessageUI.class,"FXConsoleMessageUI.fxml",this);

        this.owner = owner;

        openCloseButton.setRotationAxis(new Point3D(1,0,0));
        openCloseButton.setOnAction(e->owner.openCloseMessages());

        zoomIn.setOnAction(e->{

            double scale = owner.getScaleX();
            owner.setScaleX(scale+0.1);
            owner.setScaleY(scale+0.1);

        });
        zoomOut.setOnAction(e->{

            double scale = owner.getScaleX();
            owner.setScaleX(scale-0.1);
            owner.setScaleY(scale-0.1);

        });

    }

    /**
     * Injects a message into the message area.
     * It appends the given message to the text of the message area, prefixed with the current date and time and "[CODE NATURALIX]".
     * @param message The message to inject.
     */
    public void inject(String message)
    {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        messageArea.appendText("[CODE NATURALIX] ["+format.format(now)+"] "+message+"\n");
    }

    /**
     * Makes an open animation for the open/close button.
     * It creates a RotateTransition for the open/close button to rotate it 180 degrees.
     */
    public void makeOpenAnimation()
    {
        RotateTransition anim = new RotateTransition(Duration.seconds(0.5),openCloseButton);
        anim.setToAngle(180);
        anim.play();
    }

    /**
     * Makes a close animation for the open/close button.
     * It creates a RotateTransition for the open/close button to rotate it back to 0 degrees.
     */
    public void makeCloseAnimation()
    {
        RotateTransition anim = new RotateTransition(Duration.seconds(0.5),openCloseButton);
        anim.setToAngle(0);
        anim.play();
    }

}