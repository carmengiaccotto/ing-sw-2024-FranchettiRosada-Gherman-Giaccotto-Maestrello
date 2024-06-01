package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;

import java.io.Serializable;

/** Class that represents the Objective Cards of the game.
 */

public class ObjectiveCard extends Card implements Serializable {
    private ObjectivePoints points;

    /**Class Constructor*/
    public ObjectiveCard(int id,ObjectivePoints points) {
        super(id);
        this.points = points;
    }

    public ObjectivePoints getPoints(){
        return points;
    }


    public int CheckGoals(){
        return 0;
    }


    /**
     * method that is used to calculate the total points that an Objective card gives to the player.
     *
     * @return numberOfGoals * n.getValue()
     */
    public int calculatePoints(int numberOfGoals) {
        return numberOfGoals * points.getValue();

    }
}
