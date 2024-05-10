package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.CardColors;
import it.polimi.ingsw.model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.model.PlayGround.PlayArea;
import it.polimi.ingsw.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DispositionObjectiveCard extends ObjectiveCard {

    private CardColors CentralCardColor;
    private Map<Position, CardColors> Neighbors;


    public DispositionObjectiveCard(int id, ObjectivePoints points, CardColors CentralCardColor, Map<Position, CardColors> Neighbors) {
        super(id, points);
        this.CentralCardColor = CentralCardColor;
        this.Neighbors = Neighbors;
    }

    public Map<Position, CardColors> getNeighbors() {
        return this.Neighbors;
    }


    /**
     * Method that returns the number of times a certain configuration has been found on the PlayArea
     * Support Variables:
     * -tempConfig: Contains all the Cards that might be in a configuration; Parameter for resetConfig method
     * -TemporaryCentralCard: The card we are currently considering as the central one in the configuration
     */
    public int CheckGoals(PlayArea playArea) {
        int NumberOfDispositions = 0;
        List<List<SideOfCard>> Area = playArea.getCardsOnArea();

        for (int i = 0; i < Area.size(); i++) {
            for (int j = 0; j < Area.get(0).size(); j++) {
                if (playArea.getCardInPosition(i, j) != null) {
                    SideOfCard TemporaryCentralCard = playArea.getCardInPosition(i, j);
                    if (!TemporaryCentralCard.isInConfiguration()) {
                        if (TemporaryCentralCard.getColor().equals(getCentralCardColor())) {
                            ArrayList<SideOfCard> tempConfig = new ArrayList<>();
                            tempConfig.add(TemporaryCentralCard);

                            boolean isValidConfiguration = true;
                            for (Position currentPosition : Neighbors.keySet()) {
                                SideOfCard cardToCheck = currentPosition.getNeighbourCard(TemporaryCentralCard, playArea);
                                if (cardToCheck == null || !cardToCheck.getColor().equals(Neighbors.get(currentPosition))|| cardToCheck.isInConfiguration()) {
                                    isValidConfiguration = false;
                                    break;
                                }
                            }

                            if (isValidConfiguration) {
                                for (SideOfCard CardInConfig : tempConfig)
                                    CardInConfig.setInConfiguration(true);
                                NumberOfDispositions++;
                            }
                        }
                    }
                }
            }
        }
        playArea.resetConfig();
        return NumberOfDispositions;
    }

        public CardColors getCentralCardColor() {
            return CentralCardColor;
        }

        public void setCentralCardColor(CardColors centralCardColor){
            CentralCardColor = centralCardColor;
        }

}
