package it.polimi.ingsw.View.GUI.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class NicknameScene {
    private Pane root;
    @FXML
    private TextField nicknameTextField;
    private AtomicReference<String> nickname = new AtomicReference<>();

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
        this.nicknameTextField = (TextField) root.lookup("#nicknameBox");
        this.nicknameTextField.setText(null);

        loginButton.setOnAction(event -> {
            nickname.set(nicknameTextField.getText());

//            if(nickname != null) {
//                notify(nickname);
//            }
//            else
//                //ALert
        });
    }

    public AtomicReference<String> getnickname(){
        return nickname;
    }

    public Pane getRoot() {
        return root;
    }

}
