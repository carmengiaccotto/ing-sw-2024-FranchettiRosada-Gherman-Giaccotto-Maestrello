package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DispositionObjectiveCard extends ObjectiveCard {

    private CardColors CentralCardColor;
    private Map<Position, CardColors> Neighbors;
    int FoundDispositions;



    public DispositionObjectiveCard(ObjectivePoints points, List<SideOfCard> disposition) {
        super(points);
        FoundDispositions=0;
    }



    public int getFoundDispositions(){
        return FoundDispositions;
    }

    public void IncreaseFoundDispositions(){
        FoundDispositions+=1;
    }




    public void CheckFoundDisposition(PlayArea playArea){
        List<List<SideOfCard>> Area= playArea.getCardsOnArea();
        ArrayList<SideOfCard> tempConfig=new ArrayList<>();
        for (int i=0; i<Area.size();i++){
            for(int j=0; j<Area.get(0).size(); j++){
                SideOfCard TemporaryCentralCard=playArea.getCardInPosition(i,j);
                if (!TemporaryCentralCard.isInConfiguration()) {
                    if (playArea.getCardInPosition(i, j).getColor().equals(getCentralCardColor())) {
                        tempConfig.add(TemporaryCentralCard);
                        TemporaryCentralCard.setInConfiguration(true);
                        for(Position currentPosition: Neighbors.keySet()){
                            //SideOfCard cardToCheck= TemporaryCentralCard.getNeighbourCard(Neighbors.get(currentPosition.getOriginalEnum()));
                        }

                    }
                }

            }
        }
    }

    public CardColors getCentralCardColor() {
        return CentralCardColor;
    }

    public void setCentralCardColor(CardColors centralCardColor) {
        CentralCardColor = centralCardColor;
    }
}
