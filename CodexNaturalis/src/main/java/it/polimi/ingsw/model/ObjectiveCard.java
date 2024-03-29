package it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;

public class ObjectiveCard {
    private final ObjectivePoints points;

    public ObjectiveCard(ObjectivePoints points) {
        this.points = points;
    }

    public void play(){

    }

    public int Check(){

    }

    public int getPoints(int numberOfGoals, ObjectivePoints n) {
        return numberOfGoals * n.getValue();

    }



}
