package it.polimi.ingsw.View.FX.base;

import it.polimi.ingsw.View.FX.ui.FXMainUI;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

/**
 * This abstract class represents a dialog game pane in the FX user interface.
 * It extends the AnchorPane class from the JavaFX library.
 */
public abstract class FXDialogGamePane extends AnchorPane {

    /**
     *The main user interface that owns this dialog game pane.
     */
    private FXMainUI owner;

    /**
     * Constructor for the FXDialogGamePane class.
     * It initializes the owner of this dialog game pane.
     * @param owner The main user interface that owns this dialog game pane.
     */
    public FXDialogGamePane(FXMainUI owner)
    {
        this.owner = owner;
    }

    /**
     * This method applies a style to the dialog game pane.
     * It sets the border color to orange and the background color to a semi-transparent black.
     */
    protected void style()
    {
        setStyle("-fx-border-color:orange; -fx-background-color:rgba(0,0,0,0.7);");
    }

    /**
     * This method closes the dialog game pane.
     * It calls the closeDialog method of the owner.
     */
    public void close()
    {
        owner.closeDialog();
    }

}