package it.polimi.ingsw.Model.Enumerations;

import java.io.Serializable;

public enum GameStatus implements Serializable {
    WAITING,
    RUNNING,
    SETUP,
    INITIAL_CIRCLE,
    LAST_CIRCLE,
    ENDED
}
