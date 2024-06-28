package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents the UI for selecting a game lobby.
 * It extends the FXDialogGamePane class and contains a scroll pane for the lobby list, a VBox for the lobby items, and an integer indicating the chosen lobby.
 */
public class FXSelectLobbyUI extends FXDialogGamePane {

    // The scroll pane for the lobby list.
    @FXML
    private ScrollPane scrollPanel;

    // The VBox for the lobby items.
    @FXML
    private VBox vbox;

    // The chosen lobby.
    private int choose = 0;

    /**
     * This is the constructor for the FXSelectLobbyUI class.
     * It initializes the new FXSelectLobbyUI object with the given owner, loads the FXML file, sets up the style of the UI, and binds the width property of the VBox to the width property of the scroll pane.
     * @param owner The owner of this UI.
     */
    public FXSelectLobbyUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXSelectLobbyUI.class,"FXSelectLobbyUI.fxml",this);

        style();

        vbox.prefWidthProperty().bind(scrollPanel.widthProperty());

    }

    /**
     * Sets up the lobby list with the given games and number of players.
     * It sets the spacing of the VBox, creates a new FXLobbyGameItem for each game, sets up the FXLobbyGameItem with the lobby number, number of players needed, and names of the logged players, and adds the FXLobbyGameItem to the VBox.
     * @param games The games.
     * @param numPlayers The number of players.
     */
    public void setup(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers)
    {
        vbox.setSpacing(3);
        Platform.runLater(()->{
            for (int i = 0; i < games.size(); i++) {
                FXLobbyGameItem item = new FXLobbyGameItem(this);

                StringBuilder names = new StringBuilder();
                for (String name : games.get(i)) {
                    names.append(name).append("  ");
                }

                item.setup(i,numPlayers.get(i).getSecond(),names.toString());
                vbox.getChildren().add(item);
            }

        });

    }

    /**
     * Sets the chosen lobby.
     * @param c The chosen lobby.
     */
    public void setChoose(int c)
    {
        choose = c;
    }

    /**
     * Returns the chosen lobby.
     * @return The chosen lobby.
     */
    public int getChoose() {
        return choose;
    }
}