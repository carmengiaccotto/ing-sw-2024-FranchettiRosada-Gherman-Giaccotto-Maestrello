package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.ObjectiveCard;
    public class DispositionObjectiveCard extends ObjectiveCard {

    private Card[][] disposition;

        public DispositionObjectiveCard(ObjectivePoints objectivePoint, Card[][] disposition) {
            super(objectivePoint);
            this.disposition = disposition;
        }

        public int Check(){
        int numberOfDispositions;

        return numberOfDispositions;

    }
    public Card[][] getDisposition(){
        return disposition;

    }
}
