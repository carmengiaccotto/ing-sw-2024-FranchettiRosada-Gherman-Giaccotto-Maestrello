
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Deck;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayArea;

import java.util.ArrayList;
/*@author denisagherman19 */
public class Player {
    private PlayArea playArea;
    private String nickname;
    private Color pawnColor;
    private int score;
    private ArrayList<PairOfCards> cardsInHand;
    private int round;

    public Player(String nickname, Color pawnColor) {
        this.playArea= new PlayArea();
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.score = 0;
        this.cardsInHand = new ArrayList<>(); //l'initialize game di Playground dovrà popolare questa lista
        this.round = 0;
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public void setPawnColor(Color pawnColor) {
        this.pawnColor = pawnColor;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRound() {
        return round;
    }

    public ArrayList<PairOfCards> getCardsInHand() {
        return cardsInHand;
    }

    public void drawCardFromDeck(Deck deck) { //TypeOfCards è una enum dei possibili mazzi che ci sono
        cardsInHand.add(deck.DrawCard());
    }

    public void drawCardFromPlayground( PairOfCards card){
        cardsInHand.add(card);
        String cardType=card.getClass().getSimpleName();
        for (Deck deck : Playground.getDecks) {
            if (deck.getTypeOfDeck().equals(cardType))
                commonCards.add(drawCardFromDeck(deck));

        }
    }
}

public Card ChooseSide(PairOfCards DoubleSidedCard, Sides side){//sides è la enum di fronte e retro
    //non so come chiameremo la carta con entrambe le facce, poi al massimo modifichiamo
    Card cardToPlay=DoubleSidedCard.setSide(side); //il player  sceglie la faccia che vuole giocare
    //card è la classe della faccia della carta, quindi pairOfCards è composto da due cards
    return cardToPlay;
}
public void playCard(Card cardToPlay){
    cardToPlay.play();
}

public void main() {
}
