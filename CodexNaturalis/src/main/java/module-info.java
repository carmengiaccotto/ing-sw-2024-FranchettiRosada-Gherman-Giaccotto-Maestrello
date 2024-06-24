module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;
    requires java.desktop;

    exports it.polimi.ingsw.Controller.Main to java.rmi;
    exports it.polimi.ingsw.Controller.Client to java.rmi;
    exports it.polimi.ingsw.Controller.Game to java.rmi;
    exports it.polimi.ingsw.View to javafx.graphics;
    exports it.polimi.ingsw.View.GUI to javafx.graphics;

    opens it.polimi.ingsw.View to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.GUI to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.GUI.Controllers to javafx.fxml;
    opens it.polimi.ingsw.Model to com.google.gson;
    opens it.polimi.ingsw.Model.Enumerations to com.google.gson;
}
