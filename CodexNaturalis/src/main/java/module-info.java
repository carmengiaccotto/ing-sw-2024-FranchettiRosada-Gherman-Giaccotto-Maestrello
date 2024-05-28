module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;
    requires java.desktop;
    exports it.polimi.ingsw.View;
    opens it.polimi.ingsw.View to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.Model to com.google.gson;
}