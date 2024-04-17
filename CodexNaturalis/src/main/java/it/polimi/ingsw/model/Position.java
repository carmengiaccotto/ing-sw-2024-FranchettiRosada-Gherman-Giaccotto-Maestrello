package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public interface Position {

    SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, Position positionToCheck, PlayArea playArea);
}
