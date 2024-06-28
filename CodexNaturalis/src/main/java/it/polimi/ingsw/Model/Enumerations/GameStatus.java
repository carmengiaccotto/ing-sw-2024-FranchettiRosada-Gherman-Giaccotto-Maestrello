package it.polimi.ingsw.Model.Enumerations;

import java.io.Serializable;

/**
 * This enumeration represents the possible statuses of a game.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each game can be in one of the following statuses: WAITING, RUNNING, SETUP, INITIAL_CIRCLE, LAST_CIRCLE, ENDED, FINILIZE.
 */
public enum GameStatus implements Serializable {
    /**
    *Represents the status when the game is waiting for players.
     */
    WAITING,

    /**
     *Represents the status when the game is running.
     */
    RUNNING,

    /**
     *Represents the status when the game is in setup phase.
     */
    SETUP,

    /**
     *Represents the status when the game is in the initial circle phase.
     */
    INITIAL_CIRCLE,

    /**
     *Represents the status when the game is in the last circle phase.
     */
    LAST_CIRCLE,

    /**
     *Represents the status when the game has ended.
     */
    ENDED,

    /**
     *Represents the status when the game is finalizing.
     */
    FINILIZE;
}