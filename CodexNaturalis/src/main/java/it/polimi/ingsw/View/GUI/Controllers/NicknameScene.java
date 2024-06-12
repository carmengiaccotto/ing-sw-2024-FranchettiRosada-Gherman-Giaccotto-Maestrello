package it.polimi.ingsw.View.GUI.Controllers;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class NicknameScene {
    private Pane root;

    /**
     * Constructs a new NicknameController instance.
     */
    public NicknameScene() {
        try {
            root = FXMLLoader.load(getClass().getResource("/Nickname.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
    }

        Button loginButton = (Button) root.lookup("#loginButton");
        TextField nicknameTextField = (TextField) root.lookup("#nicknameBox");
        nicknameTextField.setText(null);

        loginButton.setOnAction(event -> {
            String nickname = nicknameTextField.getText();

//            if(nickname != null) {
//                notify(nickname);
//            }
//            else
//                //ALert
        });
    }

    public Pane getRoot() {
        return root;
    }

}
