module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    exports it.polimi.ingsw.View to javafx.graphics;
    requires com.google.gson;
    requires java.rmi;
    requires java.desktop;
    requires jdk.unsupported.desktop;
    exports it.polimi.ingsw.Controller.Main to java.rmi;
    exports it.polimi.ingsw.Controller.Client to java.rmi;
    exports it.polimi.ingsw.Controller.Game to java.rmi;
    opens it.polimi.ingsw.View to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.Model to com.google.gson;
    exports it.polimi.ingsw.View.GUI to javafx.graphics;
    opens it.polimi.ingsw.View.GUI to javafx.fxml, javafx.graphics;
}