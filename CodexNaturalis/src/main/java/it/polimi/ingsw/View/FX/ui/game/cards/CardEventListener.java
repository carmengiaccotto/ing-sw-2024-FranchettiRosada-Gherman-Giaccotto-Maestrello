package it.polimi.ingsw.View.FX.ui.game.cards;

/**
 * This interface represents an event listener for card events in the FX user interface.
 * It should be implemented by classes that need to respond to clicks on cards.
 */
public interface CardEventListener {

    /**
     * This method is called when a card is clicked.
     * It should be overridden by implementing classes to provide the desired behavior.
     * @param view The card view that was clicked.
     */
    public void onCardClick(FXCardView view);
}