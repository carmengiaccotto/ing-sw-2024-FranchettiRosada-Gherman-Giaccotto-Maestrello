package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public class ObjectiveCard {
    private final ObjectivePoints points;

    public ObjectiveCard(ObjectivePoints points) {
        this.points = points;
    }

    public void play(){

    }

    public void Check(){  //to revise

    }

    public int getPoints(int numberOfGoals, ObjectivePoints n) {
        return numberOfGoals * n.getValue();

    }



}
