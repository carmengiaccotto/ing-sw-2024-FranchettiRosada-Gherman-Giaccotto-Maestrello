package it.polimi.ingsw.View.FX.ui.game.cards;

/**
 * This interface represents an event listener for card corners in the FX user interface.
 * It should be implemented by classes that need to respond to clicks on card corners.
 */
public interface CardCornerEventListener {

    /**
     * This method is called when a corner of a card is clicked.
     * It should be overridden by implementing classes to provide the desired behavior.
     * @param card The card view that was clicked.
     * @param corner The corner of the card that was clicked.
     */
    public void onCornerClick(FXCardView card,FXCardView.CardCorner corner);
}