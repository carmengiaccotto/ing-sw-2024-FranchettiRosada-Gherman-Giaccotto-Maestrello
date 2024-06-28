package it.polimi.ingsw.Model.Enumerations;

import java.io.Serializable;

/**
 * This enumeration represents the possible colors of a pawn in the game.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each pawn can be one of the following colors: RED, GREEN, BLUE, YELLOW.
 */
public enum PawnColor implements Serializable {
    /**
     * Represents the color red.
     */
    RED,

    /**
     * Represents the color green.
     */
    GREEN,

    /**
     * Represents the color blue.
     */
    BLUE,

    /**
     * Represents the color yellow.
     */
    YELLOW,
}