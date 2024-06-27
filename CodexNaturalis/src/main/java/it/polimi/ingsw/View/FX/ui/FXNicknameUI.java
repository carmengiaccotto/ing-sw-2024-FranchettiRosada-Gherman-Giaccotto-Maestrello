package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXNicknameUI extends FXDialogGamePane {

    @FXML
    private Button ok;

    @FXML
    private TextField nickText;

    public FXNicknameUI(FXMainUI owner)
    {
        super(owner);

        FXMLUtils.load(FXNicknameUI.class,"FXNicknameUI.fxml",this);

        style();

        ok.setOnAction(e->{

            close();

        });
    }

    public String getNickName()
    {
        return nickText.getText();
    }
}
