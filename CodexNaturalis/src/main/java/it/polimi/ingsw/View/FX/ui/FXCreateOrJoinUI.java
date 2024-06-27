package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXCreateOrJoinUI extends FXDialogGamePane {

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    private int choose = 0;

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

    public int getChoose()
    {
        return choose;
    }
}
