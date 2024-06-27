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

public class FXSelectLobbyUI extends FXDialogGamePane {

    @FXML
    private ScrollPane scrollPanel;

    @FXML
    private VBox vbox;

    private int choose = 0;

    public FXSelectLobbyUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXSelectLobbyUI.class,"FXSelectLobbyUI.fxml",this);

        style();

        vbox.prefWidthProperty().bind(scrollPanel.widthProperty());

    }

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

    public void setChoose(int c)
    {
        choose = c;
    }

    public int getChoose() {
        return choose;
    }
}
