package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.StringTokenizer;

public class FXLobbyGameItem extends AnchorPane {

    @FXML
    private Label lobby;

    @FXML
    private Label playerNeed;


    @FXML
    private Label loggedPlayerNames;

    @FXML
    private Button enterButton;

    private FXSelectLobbyUI owner;

    public FXLobbyGameItem(FXSelectLobbyUI owner)
    {
        FXMLUtils.load(FXLobbyGameItem.class,"FXLobbyGameItem.fxml",this);
        this.owner = owner;
    }

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
