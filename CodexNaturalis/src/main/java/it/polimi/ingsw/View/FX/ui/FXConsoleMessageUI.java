package it.polimi.ingsw.View.FX.ui;

import it.polimi.ingsw.View.FX.utils.FXMLUtils;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FXConsoleMessageUI extends AnchorPane {

    private FXMainUI owner;

    @FXML
    private Button openCloseButton;

    @FXML
    private Button zoomIn;

    @FXML
    private Button zoomOut;

    @FXML
    private TextArea messageArea;

    public FXConsoleMessageUI(FXMainUI owner)
    {
        FXMLUtils.load(FXConsoleMessageUI.class,"FXConsoleMessageUI.fxml",this);

        this.owner = owner;

        openCloseButton.setRotationAxis(new Point3D(1,0,0));
        openCloseButton.setOnAction(e->owner.openCloseMessages());

        zoomIn.setOnAction(e->{

            double scale = owner.getScaleX();
            owner.setScaleX(scale+0.1);
            owner.setScaleY(scale+0.1);



        });
        zoomOut.setOnAction(e->{

            double scale = owner.getScaleX();
            owner.setScaleX(scale-0.1);
            owner.setScaleY(scale-0.1);

        });

    }

    public void inject(String message)
    {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        messageArea.appendText("[CODE NATURALIX] ["+format.format(now)+"] "+message+"\n");
    }

    public void makeOpenAnimation()
    {
        RotateTransition anim = new RotateTransition(Duration.seconds(0.5),openCloseButton);
        anim.setToAngle(180);
        anim.play();
    }

    public void makeCloseAnimation()
    {
        RotateTransition anim = new RotateTransition(Duration.seconds(0.5),openCloseButton);
        anim.setToAngle(0);
        anim.play();
    }

}
