package it.polimi.ingsw.View.FX.ui.game.cards;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

/**
 * This class is a test class for flipping cards in the FX user interface.
 * It extends the Application class from the JavaFX library.
 * It creates a scene with a pane and adds two FXCardView objects to the pane.
 * It sets up mouse click events for the FXCardView objects to flip the cards.
 */
public class TestFlip extends Application {

    /**
     * This method is the entry point for the JavaFX application.
     * It sets up the stage, scene, and pane.
     * It creates two FXCardView objects and adds them to the pane.
     * It sets up mouse click events for the FXCardView objects to flip the cards.
     * @param stage The primary stage for this application.
     * @throws Exception If there is an error during application startup.
     */
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

    /**
     * This is the main method for the application.
     * It launches the JavaFX application.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}