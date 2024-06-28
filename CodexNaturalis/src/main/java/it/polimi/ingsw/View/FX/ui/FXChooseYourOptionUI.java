package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class represents the UI for choosing an option in the game.
 * It extends the FXDialogGamePane class and contains a Command indicating the chosen option, a button for moving, and a label for displaying messages.
 */
public class FXChooseYourOptionUI extends FXDialogGamePane {

    // The button for moving.
    @FXML
    private Button moveButton;

    // The label for displaying messages.
    @FXML
    private Label messageLabel;

    // The chosen option.
    private Command choose;

    /**
     * This is the constructor for the FXChooseYourOptionUI class.
     * It initializes the new FXChooseYourOptionUI object with the given owner and a boolean indicating whether it's the user's turn, sets up the style of the UI, and sets up the message label and move button.
     * @param owner The owner of this UI.
     * @param myTurn A boolean indicating whether it's the user's turn.
     */
    public FXChooseYourOptionUI(FXMainUI owner, boolean myTurn) {
        super(owner);
        FXMLUtils.load(FXChooseYourOptionUI.class,"FXChooseYourOptionUI.fxml",this);
        style();

        if (myTurn)
        {
            messageLabel.setText("It's your turn");
            moveButton.setOnAction(e->{
                choose = Command.MOVE;
                close();
            });
        }
        else
        {
            messageLabel.setText("Wait for your game turn");
            moveButton.setDisable(true);
        }
    }

    /**
     * Returns the chosen option.
     * @return The chosen option.
     */
    public Command getChoose() {
        return choose;
    }
}
