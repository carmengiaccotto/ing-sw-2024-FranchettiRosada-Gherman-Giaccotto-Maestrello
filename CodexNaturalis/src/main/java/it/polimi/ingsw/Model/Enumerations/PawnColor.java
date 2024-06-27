package it.polimi.ingsw.Model.Enumerations;

import java.io.Serializable;

/**
 * This enumeration represents the possible colors of a pawn in the game.
 * It implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each pawn can be one of the following colors: RED, GREEN, BLUE, YELLOW.
 */
public enum PawnColor implements Serializable {
    RED,    // Represents the color red.
    GREEN,  // Represents the color green.
    BLUE,   // Represents the color blue.
    YELLOW, // Represents the color yellow.
}