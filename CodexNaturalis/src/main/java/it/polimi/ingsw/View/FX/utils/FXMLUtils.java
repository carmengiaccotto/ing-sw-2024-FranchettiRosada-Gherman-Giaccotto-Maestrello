
package it.polimi.ingsw.View.FX.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * This is a utility class for loading FXML files.
 * It contains a static method for loading an FXML file into a given root object.
 */
public final class FXMLUtils {

    /**
     * This method loads an FXML file into a given root object.
     * It creates a new FXMLLoader with the resource of the FXML file, sets the root and controller of the FXMLLoader to the given root object, and loads the FXML file.
     * If an IOException occurs during the loading of the FXML file, it throws a RuntimeException with the IOException.
     * @param sender The class of the object that is calling this method.
     * @param fileFXML The name of the FXML file to load.
     * @param root The root object to load the FXML file into.
     */
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