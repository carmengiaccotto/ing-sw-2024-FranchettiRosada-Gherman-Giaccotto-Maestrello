package it.polimi.ingsw.View.FX.ui.game.cards;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;


public class FXCardFactory {

    private static Map<Card,FXCardView> cardViews;
    private static Map<Integer,FXCardView> cardByIDViews;

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

    public static FXCardView getView(Card card)
    {
        FXCardView view = cardByIDViews.get(card.getIdCard());
        view.setCard(card);
        cardViews.put(card,view);
        return view;
    }

}
