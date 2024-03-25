package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;
import it.polimi.ingsw.model.Card;


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
        this.cardsInHand = new ArrayList<>(); //Play Ground initializer will add the elements to this list for the first round
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
    public void drawCardFromPlayground(PairOfCards card) {
        cardsInHand.add(card);
        String cardType = card.getClass().getSimpleName();
        for (Deck deck : Playground.getDecks) {
            if (deck.getTypeOfDeck().equals(cardType))
                commonCards.add(drawCardFromDeck(deck));

        }
    }


    /*public Card ChooseSide(PairOfCards DoubleSidedCard, Sides side) {// modifiable
        Card cardToPlay = DoubleSidedCard.setSide(side); //the player chooses the side of the card they want to play
        //card is the class that represents the chosen side of the card that the player decided to play
        return cardToPlay;
    }*/
    //This method is probably going to be implemented in PairOfCards class

    /*In this class the player plays pairOfCard; PairOfCard is going to implement the method in the previous comment to choose the side*/
    public void playCard(PairOfCards cardToPlay) {
        cardToPlay.ChooseSide();
    }
}

