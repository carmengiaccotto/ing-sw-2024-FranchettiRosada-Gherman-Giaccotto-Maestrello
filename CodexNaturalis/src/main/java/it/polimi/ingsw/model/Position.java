package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.SideOfCard;
import it.polimi.ingsw.model.PlayGround.PlayArea;

public interface Position {

    SideOfCard getNeighbourCard(SideOfCard temporaryCentralCard, PlayArea playArea);
}
