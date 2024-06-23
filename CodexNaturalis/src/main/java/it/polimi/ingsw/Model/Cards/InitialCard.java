package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;

/** This subclass represents the Initial Cards and extends the SideOfCard class*/
public  class InitialCard extends PlayCard {

    /**Class Constructor*/
    public InitialCard(int id, SideOfCard front, SideOfCard back, CardColors color) {
        super(id, front, back, color);
    }

}
