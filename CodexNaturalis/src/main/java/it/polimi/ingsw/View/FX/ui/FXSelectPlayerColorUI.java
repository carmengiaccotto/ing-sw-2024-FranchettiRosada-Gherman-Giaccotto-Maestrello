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

public class FXSelectPlayerColorUI extends FXDialogGamePane {

    private Map<PawnColor, Button> colorMap = new HashMap<>();

    @FXML
    private Button blueButton;

    @FXML
    private Button greenButton;

    @FXML
    private Button redButton;

    @FXML
    private Button yellowButton;

    private int unavailableColors = 0;

    private int choose = 0;

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

    public void setup(List<PawnColor> availableColors) {
        unavailableColors = PawnColor.values().length - availableColors.size();
        availableColors.stream().map(c->colorMap.get(c)).forEach(b->{
            b.setOpacity(1);
            b.setMouseTransparent(false);
        });
    }

    public int getChoose()
    {
        return choose;
    }
}
