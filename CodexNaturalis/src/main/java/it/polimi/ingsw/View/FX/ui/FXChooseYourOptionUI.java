package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FXChooseYourOptionUI extends FXDialogGamePane {

    @FXML
    private Button moveButton;


    @FXML
    private Label messageLabel;

    private Command choose;

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

    public Command getChoose() {
        return choose;
    }
}
