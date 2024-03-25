package it.polimi.ingsw.model;

public class ObjectiveCard extends Card{
    private ObjectivePoints objectivePoint;

    public ObjectiveCard(ObjectivePoints objectivePoint) {
        this.objectivePoint = objectivePoint;
    }

    public void play(){

    }

    public void Check(){

    }

    public int getPoints(int numberOfGoals, ObjectivePoints n) {
        return numberOfGoals * n.getvalue();  // Remeber to implement getValue

    }



}
