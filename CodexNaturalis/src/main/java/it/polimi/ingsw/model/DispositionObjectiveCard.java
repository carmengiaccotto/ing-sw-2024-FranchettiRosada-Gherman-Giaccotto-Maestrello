package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.ObjectivePoints;
    public class DispositionObjectiveCard extends ObjectiveCard {

    private SideOfCard[][] disposition;

        public DispositionObjectiveCard(ObjectivePoints points, SideOfCard[][] disposition) {
            super(points);
            this.disposition = disposition;
        }

        public int Check(){
        int numberOfDispositions;

        return numberOfDispositions;

    }
    public SideOfCard[][] getDisposition(){
        return disposition;

    }
}
