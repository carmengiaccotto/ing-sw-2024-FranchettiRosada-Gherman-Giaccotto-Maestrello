package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.*;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CornerPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.ObjectivePoints;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.UpDownPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.PlayArea;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DispositionObjectiveCard extends ObjectiveCard {

    private CardColors CentralCardColor;
    private Map<Position, CardColors> Neighbors;



    public DispositionObjectiveCard(int id, ObjectivePoints points, CardColors CentralCardColor, Map<Position, CardColors> Neighbors) {
        super(id,points);
        this.CentralCardColor=CentralCardColor;
        this.Neighbors=Neighbors;
    }

    public DispositionObjectiveCard mapFromJson(JsonObject jsonObject){
        ObjectiveCard card=super.mapFromJson(jsonObject);
        CentralCardColor = CardColors.valueOf(jsonObject.get("CentralCardColor").getAsString());
        JsonObject neighbors = jsonObject.getAsJsonObject("NEIGHBORS");
        for (Map.Entry<String, JsonElement> entry : neighbors.entrySet()) {
            Position position=null;
            for (CornerPosition corner: CornerPosition.values()){
                if (entry.getKey().toUpperCase().equals(corner.toString())) {
                    position = CornerPosition.valueOf(entry.getKey());
                }
            }
            for (UpDownPosition pos: UpDownPosition.values()){
                if (entry.getKey().toUpperCase().equals(pos.toString())) {
                    position = UpDownPosition.valueOf(entry.getKey());
                }
            }
            CardColors neighborColor=CardColors.valueOf(entry.getValue().toString());
            Neighbors.put(position,neighborColor);
        }
        return new DispositionObjectiveCard(card.getIdCard(),card.getPoints(),CentralCardColor,Neighbors);

        }







/**Method that returns the number of times a certain configuration has been found on the PlayArea
 * Support Variables:
 *              -tempConfig: Contains all the Cards that might be in a configuration; Parameter for resetConfig method
 *              -TemporaryCentralCard: The card we are currently considering as the central one in the configuration
 *              */
    public int CheckGoals(PlayArea playArea){
        int NumberOfDispositions=0;
        List<List<SideOfCard>> Area= playArea.getCardsOnArea();
        ArrayList<SideOfCard> tempConfig=new ArrayList<>();
        for (int i=0; i<Area.size();i++){
            for(int j=0; j<Area.get(0).size(); j++){//scroll through the cards in the PlayArea
                SideOfCard TemporaryCentralCard=playArea.getCardInPosition(i,j); //Each card is temporary set as the central one
                if (!TemporaryCentralCard.isInConfiguration()) { //We can begin to check if we found a configuration that has this card as its central one, only if this card is not already in a config
                    if (playArea.getCardInPosition(i, j).getColor().equals(getCentralCardColor())) {//checks if the current card has the same color as the central card of the disposition we are searching for
                        tempConfig.add(TemporaryCentralCard);//adding the card to the list of cards that are part of the configuration
                        for(Position currentPosition: Neighbors.keySet()){ //checking the Neighbours of the card that are indicated in the ObjectiveDisposition
                            SideOfCard cardToCheck=currentPosition.getNeighbourCard(TemporaryCentralCard,currentPosition,playArea);//we get the card that is the desired neighbour
                            if(cardToCheck.getColor().equals(Neighbors.get(currentPosition))){//we check if the color is the wanted one
                                tempConfig.add(cardToCheck);//we add the card to the list of card that make the desired configuration
                            }
                            else{//if the card does not have the color we need, we remove the cards from the configurationList
                                tempConfig.clear();
                                break; //we don't need to check anything else
                            }
                        }
                        if(!tempConfig.isEmpty()){//if we found a card that does not match the Disposition, we clear the list, so the list is not empty, it means we found a disposition
                            for(SideOfCard CardInConfig: tempConfig)
                                CardInConfig.setInConfiguration(true);//setting all the attributes to true, so these cards can not be used
                            NumberOfDispositions+=1; //increasing the number of dispositions we found on the PlayArea

                        }

                    }
                }

            }
        }
        playArea.resetConfig();//resetting everything to zero for the next DispositionCheck
        return NumberOfDispositions;//Occurrences of the disposition on the PlayArea
    }

    public CardColors getCentralCardColor() {
        return CentralCardColor;
    }

    public void setCentralCardColor(CardColors centralCardColor) {
        CentralCardColor = centralCardColor;
    }
}
