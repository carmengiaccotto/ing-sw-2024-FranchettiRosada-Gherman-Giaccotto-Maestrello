package it.polimi.ingsw.View.FX.base;

import it.polimi.ingsw.View.FX.ui.FXMainUI;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

public abstract class FXDialogGamePane extends AnchorPane {

    private FXMainUI owner;

    public FXDialogGamePane(FXMainUI owner)
    {
        this.owner = owner;
    }

    protected void style()
    {
        setStyle("-fx-border-color:orange; -fx-background-color:rgba(0,0,0,0.7);");
    }

    public void close()
    {
        owner.closeDialog();
    }

}
