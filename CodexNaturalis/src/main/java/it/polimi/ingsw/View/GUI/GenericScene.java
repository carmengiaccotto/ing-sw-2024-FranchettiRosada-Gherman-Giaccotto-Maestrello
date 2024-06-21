package it.polimi.ingsw.View.GUI;

import javafx.scene.Scene;

public class GenericScene {

    private Scene scene;
    private SceneEnum sceneEnum;


    public GenericScene(Scene scene, SceneEnum sceneEnum) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
    }

    public Scene getScene() {
        return scene;
    }
    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }

}
