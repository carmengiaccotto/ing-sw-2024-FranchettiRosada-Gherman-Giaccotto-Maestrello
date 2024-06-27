module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    exports it.polimi.ingsw.View to javafx.graphics;
    requires com.google.gson;
    requires java.rmi;
    requires java.desktop;
    requires java.sql;
    //requires jdk.unsupported.desktop;
    exports it.polimi.ingsw.Controller.Main to java.rmi;
    exports it.polimi.ingsw.Controller.Client to java.rmi;
    exports it.polimi.ingsw.Controller.Game to java.rmi;
    opens it.polimi.ingsw.View to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.Model to com.google.gson;
    exports it.polimi.ingsw.View.GUI to javafx.graphics;
    exports it.polimi.ingsw.View.FX to javafx.graphics;
    opens it.polimi.ingsw.View.GUI to javafx.fxml, javafx.graphics;

    opens it.polimi.ingsw.View.FX to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.FX.ui to javafx.fxml, javafx.graphics;
    exports it.polimi.ingsw.View.FX.base to javafx.graphics;
    opens it.polimi.ingsw.View.FX.base to javafx.fxml, javafx.graphics;
    exports it.polimi.ingsw.View.FX.utils to javafx.graphics;
    opens it.polimi.ingsw.View.FX.utils to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.FX.ui.game to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.View.FX.ui.game.cards to javafx.fxml, javafx.graphics;
}
