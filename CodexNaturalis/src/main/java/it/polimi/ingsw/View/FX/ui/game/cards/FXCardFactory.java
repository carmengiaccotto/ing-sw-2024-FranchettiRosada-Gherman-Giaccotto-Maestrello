package it.polimi.ingsw.View.FX.ui.game.cards;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a factory for creating FXCardView objects.
 * It maintains a mapping of Card objects to their corresponding FXCardView objects.
 * It also maintains a mapping of card IDs to their corresponding FXCardView objects.
 */
public class FXCardFactory {

    /**
     *A map of Card objects to their corresponding FXCardView objects.
     */
    private static Map<Card,FXCardView> cardViews;

    /**
    * A map of card IDs to their corresponding FXCardView objects.
     */
    private static Map<Integer,FXCardView> cardByIDViews;

    /**
     * This method sets up the card views.
     * It initializes the cardByIDViews map and the cardViews map.
     * It creates a new FXCardView object for each card ID and adds it to the cardByIDViews map.
     */
    public static void setupCardsView()
    {
        cardByIDViews = new HashMap<>();
        cardViews = new HashMap<>();

        for (int i = 1; i <= 102; i++)
        {
            FXCardView view = new FXCardView(i);
            cardByIDViews.put(i,view);
        }
    }

    /**
     * This method returns the FXCardView object for a given Card object.
     * It retrieves the FXCardView object from the cardByIDViews map using the card's ID.
     * It sets the card for the FXCardView object and adds it to the cardViews map.
     * @param card The Card object for which to get the FXCardView object.
     * @return The FXCardView object for the given Card object.
     */
    public static FXCardView getView(Card card)
    {
        FXCardView view = cardByIDViews.get(card.getIdCard());
        view.setCard(card);
        cardViews.put(card,view);
        return view;
    }

}