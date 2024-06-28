package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;

/**
 * This subclass represents a specific type of Gold Card that awards points for each corner covered by this card.
 * It extends the base GoldCard class and provides additional customization
 * tailored to this particular type of Gold Card.
 */
public class PointPerCoveredCorner extends GoldCard {

    /**
     * Class Constructor.
     * Initializes the card with the given id, front side, back side, color, requirement, and point.
     *
     * @param id The id of the card.
     * @param front The front side of the card.
     * @param back The back side of the card.
     * @param color The color of the card.
     * @param requirement The requirement of the card.
     * @param point The point value of the card.
     */
    public PointPerCoveredCorner(int id, SideOfCard front, SideOfCard back, CardColors color, HashMap<Symbol, Integer> requirement, int point) {
        super(id, front, back, color, requirement, point);
    }

    /**
     * Method used to get the number of corners the card covers when it is placed.
     *
     * @return coveredCorners obtained by checking which one of the corners of the current card has nextCorner!=null at
     * nextCorner is set when placing the Card on the area and checking the neighbours
     */
    public int findCoveredCorners(){
        int coveredCorners=0;// initializing the number of covered corners to zero
        for(Corner[] cornerRow: this.getFront().getCorners() ){//iterating through the corners of the front side of the card,
            // because the back does not give points
            for (Corner corner: cornerRow){//iterating through the corners of the current row
                if (corner.getNextCorner()!=null)//if the next corner is not null, then the current corner is covering a corner
                    //this is because this method is called after the card is placed on the area, so the card can not already be covered
                    coveredCorners+=1;//incrementing the number of covered corners
            }
        }
        return coveredCorners;
    }

    /**
     * Method that returns the actual points that Playing this card provides, by multiplying the defaultPoints
     * and the corners the card covers
     *
     * @param point default points
     * @return int the points that are to be added to the Player's score
     */
   @Override
    public int increasePoints(int point){
        return findCoveredCorners() * point;
    }

}

