package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This class represents the UI for selecting the number of players in the game.
 * It extends the FXDialogGamePane class and contains buttons for each number of players and an integer indicating the chosen number of players.
 */
public class FXSelectPlayerUI extends FXDialogGamePane {

    /**
    * The button for two players.
     */
    @FXML
    private Button twoButton;

    /**
    * The button for three players.
     */
    @FXML
    private Button threeButton;

    /**
    * The button for four players.
     */
    @FXML
    private Button fourButton;

    /**
    * The chosen number of players.
     */
    private int choose = 0;

    /**
     * This is the constructor for the FXSelectPlayerUI class.
     * It initializes the new FXSelectPlayerUI object with the given owner, loads the FXML file, sets up the style of the UI, and sets up the action events for the buttons to set the chosen number of players and close the UI.
     * @param owner The owner of this UI.
     */
    public FXSelectPlayerUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXSelectPlayerUI.class,"FXSelectPlayerUI.fxml",this);

        style();

        twoButton.setOnAction(e->{

            choose = 2;
            close();

        });

        threeButton.setOnAction(e->{

            choose = 3;
            close();

        });

        fourButton.setOnAction(e->{

            choose = 4;
            close();

        });
    }

    /**
     * Returns the chosen number of players.
     * @return The chosen number of players.
     */
    public int getChoose() {
        return choose;
    }
}