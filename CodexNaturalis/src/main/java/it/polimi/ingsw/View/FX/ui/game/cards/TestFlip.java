package it.polimi.ingsw.View.FX.ui.game.cards;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class TestFlip extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene s = new Scene(root,800,800);
        stage.setScene(s);

        stage.show();

        Random r = new Random();

        for (int i = 102; i <= 102; i++)
        {

            FXCardView c = new FXCardView(i);
            FXCardView c1 = new FXCardView(c);
            root.getChildren().add(c);
            root.getChildren().add(c1);

            c.relocate(100,100);
            c.relocate(200,200);

            c.setOnMouseClicked(e->c.flip());
            c1.setOnMouseClicked(e->c1.flipNoAnim());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
