package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * This class represents the UI for entering a nickname in the game.
 * It extends the FXDialogGamePane class and contains a button for confirming the nickname and a text field for entering the nickname.
 */
public class FXNicknameUI extends FXDialogGamePane {

    /**
    * The button for confirming the nickname.
     */
    @FXML
    private Button ok;

    /**
    * The text field for entering the nickname.
     */
    @FXML
    private TextField nickText;

    /**
     * This is the constructor for the FXNicknameUI class.
     * It initializes the new FXNicknameUI object with the given owner, loads the FXML file, sets up the style of the UI, and sets up the action event for the ok button to close the UI.
     * @param owner The owner of this UI.
     */
    public FXNicknameUI(FXMainUI owner)
    {
        super(owner);

        FXMLUtils.load(FXNicknameUI.class,"FXNicknameUI.fxml",this);

        style();

        ok.setOnAction(e->{

            close();

        });
    }

    /**
     * Returns the entered nickname.
     * @return The entered nickname.
     */
    public String getNickName()
    {
        return nickText.getText();
    }
}