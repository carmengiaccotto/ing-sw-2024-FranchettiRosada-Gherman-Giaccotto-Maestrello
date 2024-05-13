package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

public interface Position {

    SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea);
}
