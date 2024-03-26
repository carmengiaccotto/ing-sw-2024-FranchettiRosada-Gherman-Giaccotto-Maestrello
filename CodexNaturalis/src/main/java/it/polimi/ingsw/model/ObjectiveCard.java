package it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
import it.polimi.ingsw.model.Card;

public class ObjectiveCard extends Card {
    private ObjectivePoints objectivePoint;

    public ObjectiveCard(ObjectivePoints objectivePoint) {
        this.objectivePoint = objectivePoint;
    }

    public void play(){

    }

    public void Check(){

    }

    public int getPoints(int numberOfGoals, ObjectivePoints n) {
        return numberOfGoals * n.getvalue();  // Remember to implement getValue

    }



}
