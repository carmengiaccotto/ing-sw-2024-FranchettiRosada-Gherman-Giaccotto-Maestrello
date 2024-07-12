package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.StringTokenizer;

/**
 * This class represents an item in the game lobby.
 * It extends the AnchorPane class and contains various UI elements and a reference to the lobby UI.
 */
public class FXLobbyGameItem extends AnchorPane {

    /**
     * The label for the lobby number.
     */
    @FXML
    private Label lobby;

    /**
     * The label for the number of players needed.
     */
    @FXML
    private Label playerNeed;

    /**
     * The label for the names of the logged players.
     */
    @FXML
    private Label loggedPlayerNames;

    /**
     * The button for entering the game.
     */
    @FXML
    private Button enterButton;

    /**
     *The lobby UI.
     */
    private FXSelectLobbyUI owner;

    /**
     * This is the constructor for the FXLobbyGameItem class.
     * It initializes the new FXLobbyGameItem object with the given lobby UI and loads the FXML file.
     * @param owner The lobby UI.
     */
    public FXLobbyGameItem(FXSelectLobbyUI owner)
    {
        FXMLUtils.load(FXLobbyGameItem.class,"FXLobbyGameItem.fxml",this);
        this.owner = owner;
    }

    /**
     * Sets up the lobby game item with the given lobby number, number of players needed, and names of the logged players.
     * It sets the text of the lobby, playerNeed, and loggedPlayerNames labels, and sets up the action event for the enter button to set the chosen lobby number and close the lobby UI.
     * @param lobbyNum The lobby number.
     * @param playerNeeded The number of players needed.
     * @param names The names of the logged players.
     */
    public void setup(int lobbyNum, int playerNeeded, String names)
    {
        lobby.setText(""+lobbyNum);
        playerNeed.setText(""+playerNeeded);
        loggedPlayerNames.setText(names);

        enterButton.setOnAction(e->{

            owner.setChoose(lobbyNum);
            owner.close();

        });
    }
}