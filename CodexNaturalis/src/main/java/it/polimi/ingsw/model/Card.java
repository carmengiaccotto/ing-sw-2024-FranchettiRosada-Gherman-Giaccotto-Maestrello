package it.polimi.ingsw.model;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Colors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Side;
import java.util.Optional;
public class Card {

    private final Pair<SideOfCard, SideOfCard> sides;

    private int idCard;

    private  Optional<Colors> color;

    public Card(Pair<SideOfCard, SideOfCard> sides, int idCard, Optional<Colors> color) {
        this.sides = sides;
        this.idCard = idCard;
        this.color = color;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public Pair<SideOfCard, SideOfCard> getSides() {
        return sides;
    }

    public SideOfCard chooseSide(Side sideToPlay){
        if (sideToPlay == Side.FRONT) return sides.getFirst();
        else{
            return sides.getSecond();
        }
    }

}


