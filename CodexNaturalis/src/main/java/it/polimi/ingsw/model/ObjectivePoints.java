package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public enum ObjectivePoints {
    TWO(2),
    THREE(3);

    private final int value;

    ObjectivePoints(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
