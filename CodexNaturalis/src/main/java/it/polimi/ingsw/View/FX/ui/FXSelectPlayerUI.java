package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXSelectPlayerUI extends FXDialogGamePane {

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    private int choose = 0;

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

    public int getChoose() {
        return choose;
    }
}
