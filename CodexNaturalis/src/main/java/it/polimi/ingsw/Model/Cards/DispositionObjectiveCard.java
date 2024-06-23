package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DispositionObjectiveCard extends ObjectiveCard {

    private CardColors CentralCardColor;
    private Map<Position, CardColors> Neighbors;





    /**Class Constructor*/

    public DispositionObjectiveCard(int id, ObjectivePoints points, CardColors CentralCardColor, Map<Position, CardColors> Neighbors) {
        super(id, points);
        this.CentralCardColor = CentralCardColor;
        this.Neighbors = Neighbors;
    }




    /**Method that returns the neighbors of the central card in the configuration
     * @return a map that associates the Color of the card to the neighboring position*/

    public Map<Position, CardColors> getNeighbors() {
        return this.Neighbors;
    }




    /**
     * Method that returns the number of times a certain configuration has been found on the PlayArea
     * Support Variables:
     * -tempConfig: Contains all the Cards that might be in a configuration; Parameter for resetConfig method
     * -TemporaryCentralCard: The card we are currently considering as the central one in the configuration
     * @param playArea we are checking the configuration on
     */

    public int CheckGoals(PlayArea playArea) {
        int NumberOfDispositions = 0;//starting with 0 dispositions
        List<List<SideOfCard>> Area = playArea.getCardsOnArea(); //retrieve the cards on the playArea

        for (int i = 0; i < Area.size(); i++) {//for all rows
            for (int j = 0; j < Area.get(0).size(); j++) {//for all columns
                if (playArea.getCardInPosition(i, j) != null) { //if there is a card in the given position
                    SideOfCard TemporaryCentralCard = playArea.getCardInPosition(i, j);//set this card to be the central one
                    if (!TemporaryCentralCard.isInConfiguration()) {//continue to use it as a central one only if it is not already in a configuration (as per game rules)
                        if (TemporaryCentralCard.getColor().equals(getCentralCardColor())) {//check if the card we are considering is of the desired color
                            ArrayList<SideOfCard> tempConfig = new ArrayList<>();//initialize the temporary configuration
                            tempConfig.add(TemporaryCentralCard);//adding the central card to the configuration
                            boolean isValidConfiguration = true;//initialize the boolean that will tell us if the configuration is valid
                            for (Position currentPosition : Neighbors.keySet()) {//for all the neighbors of the central card given by our map
                                SideOfCard cardToCheck = currentPosition.getNeighbourCard(TemporaryCentralCard, playArea);//get the card in the given position
                                if (cardToCheck == null || !cardToCheck.getColor().equals(Neighbors.get(currentPosition))|| cardToCheck.isInConfiguration()) {
                                    //if the card we are considering is  not of the desired color, or is already in a configuration, or in the desired position there is no card
                                    //we can interrupt the check and set the configuration as invalid
                                    isValidConfiguration = false;
                                    break;
                                }
                            }
                            if (isValidConfiguration) {
                                for (SideOfCard CardInConfig : tempConfig)
                                    //if the configuration is valid, we set all the cards in the configuration as in a configuration
                                    CardInConfig.setInConfiguration(true);
                                NumberOfDispositions++;//increment the number of dispositions
                            }
                        }
                    }
                }
            }
        }
        //clean the playArea and set it ready for the next check
        playArea.resetConfig();
        return NumberOfDispositions;
    }



    /**Getter method for CentralCardColor attribute
     * @return centralCardColor color of the central card of the disposition*/

    public CardColors getCentralCardColor() {
            return CentralCardColor;
        }




    /**Setter method for CentralCardColor attribute
     * @param centralCardColor color of the central card of the disposition*/

    public void setCentralCardColor(CardColors centralCardColor){
            CentralCardColor = centralCardColor;
        }

}
