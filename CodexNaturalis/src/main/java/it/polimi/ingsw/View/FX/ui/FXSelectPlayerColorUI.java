package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.View.FX.base.FXDialogGamePane;
import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the UI for selecting a player color in the game.
 * It extends the FXDialogGamePane class and contains a map of colors to buttons, buttons for each color, an integer indicating the number of unavailable colors, and an integer indicating the chosen color.
 */
public class FXSelectPlayerColorUI extends FXDialogGamePane {

    /**
    * The map of colors to buttons.
     */
    private Map<PawnColor, Button> colorMap = new HashMap<>();

    /**
    * The button for the color blue.
     */
    @FXML
    private Button blueButton;

    /**
    * The button for the color green.
     */
    @FXML
    private Button greenButton;

    /**
    * The button for the color red.
     */
    @FXML
    private Button redButton;

    /**
    * The button for the color yellow.
     */
    @FXML
    private Button yellowButton;

    /**
    * The number of unavailable colors.
     */
    private int unavailableColors = 0;

    /**
    * The chosen color.
     */
    private int choose = 0;

    /**
     * This is the constructor for the FXSelectPlayerColorUI class.
     * It initializes the new FXSelectPlayerColorUI object with the given owner, loads the FXML file, sets up the style of the UI, and sets up the map of colors to buttons and the action events for the buttons.
     * @param owner The owner of this UI.
     */
    public FXSelectPlayerColorUI(FXMainUI owner)
    {
        super(owner);
        FXMLUtils.load(FXSelectPlayerColorUI.class,"FXSelectPlayerColorUI.fxml",this);

        style();

        colorMap.put(PawnColor.RED,redButton);
        colorMap.put(PawnColor.GREEN,greenButton);
        colorMap.put(PawnColor.BLUE,blueButton);
        colorMap.put(PawnColor.YELLOW,yellowButton);

        colorMap.entrySet().forEach(e->{

            e.getValue().setOpacity(0.1);
            e.getValue().setMouseTransparent(true);

            e.getValue().setOnAction(ev->{

                choose = e.getKey().ordinal()+1 - unavailableColors;
                close();

            });

            e.getValue().setCursor(Cursor.HAND);
            e.getValue().setOnMouseEntered(ev->{

                ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3),e.getValue());
                scale.setToX(1.2);
                scale.setToY(1.2);
                scale.play();

            });

            e.getValue().setOnMouseExited(ev->{
                ScaleTransition scale = new ScaleTransition(Duration.seconds(0.3),e.getValue());
                scale.setToX(1);
                scale.setToY(1);
                scale.play();
            });
        });
    }

    /**
     * Sets up the available colors.
     * It sets the number of unavailable colors and sets the opacity and mouse transparency of the buttons for the available colors.
     * @param availableColors The available colors.
     */
    public void setup(List<PawnColor> availableColors) {
        unavailableColors = PawnColor.values().length - availableColors.size();
        availableColors.stream().map(c->colorMap.get(c)).forEach(b->{
            b.setOpacity(1);
            b.setMouseTransparent(false);
        });
    }

    /**
     * Returns the chosen color.
     * @return The chosen color.
     */
    public int getChoose()
    {
        return choose;
    }
}