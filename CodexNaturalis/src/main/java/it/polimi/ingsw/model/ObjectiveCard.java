package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

/** @author Carmen Giaccotto
 * Class that represents the Objective Cards of the game.
 */

public class ObjectiveCard {
    private final ObjectivePoints points;

    public ObjectiveCard(ObjectivePoints points) {
        this.points = points;
    }


    public void play(){ //to revise + javadoc

    }

    public void Check(){ //to revise + javadoc
    }

    /**
     * method that is used to calculate the total points that an Objective card gives to the player.
     * @param numberOfGoals
     * @param n
     * @return numberOfGoals * n.getValue()
     */
    public int getPoints(int numberOfGoals, ObjectivePoints n) {
        return numberOfGoals * n.getValue();

    }



}
