package it.polimi.ingsw.Model.Enumerations;

/**
 * This enumeration represents the possible points that an objective card can give in the game.
 * An objective card can give either 2 or 3 points.
 * It has a private final attribute 'value' which represents the point value of the objective card.
 */
public enum ObjectivePoints {
    TWO(2),   // Represents an objective card that gives 2 points.
    THREE(3); // Represents an objective card that gives 3 points.

    private final int value; // The point value of the objective card.

    /**
     * This is a constructor for the ObjectivePoints enumeration.
     * It initializes the 'value' attribute with the given value.
     *
     * @param value The point value of the objective card.
     */
    ObjectivePoints(int value) {
        this.value = value;
    }

    /**
     * This method is a getter for the 'value' attribute.
     * The 'value' attribute represents the point value of the objective card.
     *
     * @return The point value of the objective card.
     */
    public int getValue(){
        return value;
    }
}