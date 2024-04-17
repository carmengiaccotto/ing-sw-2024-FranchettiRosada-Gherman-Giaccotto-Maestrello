package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;

/** @author Carmen Giaccotto
 * Class that represents the Objective Cards of the game.
 */

public class ObjectiveCard extends Card {
    private ObjectivePoints points;

    public ObjectiveCard(int id,ObjectivePoints points) {
        super(id);
        this.points = points;
    }

    public ObjectiveCard mapFromJson(JsonObject jsonObject){
        Card card = super.mapFromJson(jsonObject);
        int pointsFromJson= jsonObject.get("points").getAsInt();
        for(ObjectivePoints points: ObjectivePoints.values())
            if(pointsFromJson==points.getValue())
                this.points =points;
        return new ObjectiveCard(card.getIdCard(),this.points);
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
