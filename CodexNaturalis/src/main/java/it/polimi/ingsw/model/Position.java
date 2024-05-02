package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayGround.PlayArea;

public interface Position {

    SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea);
}
