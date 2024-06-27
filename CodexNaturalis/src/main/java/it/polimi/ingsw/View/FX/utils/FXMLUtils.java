
package it.polimi.ingsw.View.FX.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public final class FXMLUtils {

    public static void load(Class sender, String fileFXML, Object root) {
        FXMLLoader fxmlLoader = new FXMLLoader(sender.getResource(fileFXML));
        fxmlLoader.setRoot(root);
        fxmlLoader.setController(root);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
