package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class represents the UI for creating or joining a game.
 * It extends the FXDialogGamePane class and contains two buttons for creating and joining a game, respectively, and an integer indicating the chosen option.
 */
public class FXCreateOrJoinUI extends FXDialogGamePane {

    /**
     * The button for creating a game.
     */
    @FXML
    private Button createButton;

    /**
     * The button for joining a game.
     */
    @FXML
    private Button joinButton;

    /**
    * The chosen option (1 for creating a game, 2 for joining a game).
     */
    private int choose = 0;

    /**
     * This is the constructor for the FXCreateOrJoinUI class.
     * It initializes the new FXCreateOrJoinUI object with the given owner, loads the FXML file, sets up the style of the UI, and sets up the action events for the create and join buttons.
     * @param owner The owner of this UI.
     */
    public FXCreateOrJoinUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXCreateOrJoinUI.class,"FXCreateOrJoinUI.fxml",this);

        style();

        createButton.setOnAction(e->{

            choose = 1;
            close();

        });

        joinButton.setOnAction(e->{

            choose = 2;
            close();

        });
    }

    /**
     * Returns the chosen option.
     * @return The chosen option (1 for creating a game, 2 for joining a game).
     */
    public int getChoose()
    {
        return choose;
    }
}
