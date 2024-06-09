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


    /**Getter method fot Points attribute
     *
     * @return ObjectivePoints type: ObjectivePoints enum
     * */

    public ObjectivePoints getPoints(){
        return points;
    }




    /**Method that is to be overwritten by SymbolObjectivePoints and DispositionObjectivePoints, that checks how many times
     * the goal has been reached on the playArea.
     * @return 0 as a default number
     * */

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
