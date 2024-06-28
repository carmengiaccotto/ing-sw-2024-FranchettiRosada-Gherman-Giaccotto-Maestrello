package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;

import java.io.Serializable;

/**
 * This class represents the Objective Cards in the game.
 * It extends the Card class and implements the Serializable interface, which means its instances can be converted to a byte stream and restored later.
 * Each Objective Card has points, which represent the points a player can earn by achieving the objective on the card.
 */
public class ObjectiveCard extends Card implements Serializable {

    /**
     * The points of the Objective Card.
     * This attribute represents the points a player can earn by achieving the objective on the card.
     * It is of type ObjectivePoints enum.
     */
    private ObjectivePoints points;

    /**
     * Class Constructor.
     * Initializes the card with the given id and points.
     *
     * @param id The id of the card.
     * @param points The points of the card.
     */
    public ObjectiveCard(int id, ObjectivePoints points) {
        super(id);
        this.points = points;
    }

    /**
     * Getter method for Points attribute.
     *
     * @return The points of the card. It is of type ObjectivePoints enum.
     */
    public ObjectivePoints getPoints(){
        return points;
    }

    /**
     * Method that checks how many times the goal has been reached on the playArea.
     * This method is to be overwritten by SymbolObjectivePoints and DispositionObjectivePoints.
     *
     * @return 0 as a default number.
     */
    public int CheckGoals(){
        return 0;
    }

    /**
     * Method that is used to calculate the total points that an Objective card gives to the player.
     *
     * @param numberOfGoals The number of times the goal has been reached.
     * @return The total points that an Objective card gives to the player. It is calculated as numberOfGoals * points.getValue().
     */
    public int calculatePoints(int numberOfGoals) {
        return numberOfGoals * points.getValue();

    }
}
