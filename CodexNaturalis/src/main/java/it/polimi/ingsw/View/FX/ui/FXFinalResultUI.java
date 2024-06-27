package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FXFinalResultUI extends FXDialogGamePane {

    @FXML
    private Label message;

    @FXML
    private Button ok;

    public FXFinalResultUI(FXMainUI owner,String message) {
        super(owner);
        FXMLUtils.load(FXFinalResultUI.class,"FXFinalResultUI.fxml",this);

        this.message.setText(message);

        ok.setOnAction(e->close());
    }
}
