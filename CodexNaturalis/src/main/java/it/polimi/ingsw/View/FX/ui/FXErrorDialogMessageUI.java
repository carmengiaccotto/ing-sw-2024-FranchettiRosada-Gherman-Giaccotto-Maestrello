package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class represents the UI for displaying error dialog messages in the game.
 * It extends the FXDialogGamePane class and contains a label for the message and a button for closing the dialog.
 */
public class FXErrorDialogMessageUI extends FXDialogGamePane {

    /**
     * The label for the message.
     */
    @FXML
    private Label message;

    /**
    * The button for closing the dialog.
     */
    @FXML
    private Button ok;

    /**
     * This is the constructor for the FXErrorDialogMessageUI class.
     * It initializes the new FXErrorDialogMessageUI object with the given owner and message, loads the FXML file, sets the message text, and sets up the action event for the ok button to close the dialog.
     * @param owner The owner of this UI.
     * @param message The message to display.
     */
    public FXErrorDialogMessageUI(FXMainUI owner,String message) {
        super(owner);

        FXMLUtils.load(FXErrorDialogMessageUI.class,"FXErrorDialogMessageUI.fxml",this);
        this.message.setText(message);

        ok.setOnAction(e->close());
    }
}
