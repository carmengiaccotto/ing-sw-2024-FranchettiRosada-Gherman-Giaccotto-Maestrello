package it.polimi.ingsw.View.GUI.Controllers;

import it.polimi.ingsw.Controller.Main.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class NicknameScene {

    @FXML
    private Button loginButton;

    @FXML
    private TextField nicknameTextField;

    private Pane root;

    public NicknameScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/View/GUI/Nickname.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getRoot() {
        return root;
    }

    @FXML
    public void initialize() {
        nicknameTextField.setText(null);
    }

    @FXML
    public void loginButtonAction(ActionEvent event) {
        try {
            String nickname = nicknameTextField.getText();
            if (MainController.getInstance().checkUniqueNickName(nickname)) {
                MainController.getInstance().addNickname(nickname);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Users/alessiafranchettirosada/IdeaProjects/ing-sw-2024-FranchettiRosada-Gherman-Giaccotto-Maestrello/CodexNaturalis/src/main/resources/it/polimi/ingsw/View/GUI/Menu.fxml"));
                Parent menuRoot = fxmlLoader.load();
                MenuScene menuController = fxmlLoader.getController();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(menuRoot));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
