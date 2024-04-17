package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import java.util.ArrayList;


/**@author Denisa Minodora Gherman
* Class that represents the Player of the game which coincides with the client*/
public class Player {
    /*Every Player has its own playArea.*/
    private final PlayArea playArea;
    private String nickname;
    private  PawnColor pawnColor;
    private int score;
    private ArrayList<Card> cardsInHand;
    private int round;


    public Player(String nickname, PawnColor pawnColor, int Score, int Round) {
        this.playArea = new PlayArea(null);
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.score = Score;
        this.cardsInHand = new ArrayList<>(); //Play Ground initializer will add the elements to this list for the first round
        this.round = Round;
    }

    /**
     * getter method for the Player's playArea
     *
     * @return PlayaArea
     */
    public PlayArea getPlayArea() {
        return this.playArea;
    }




    /**
     * getter method for the Player's Nickname
     *
     * @return string NickName How the player wants to be called in the game
     */

    public String getNickname() {
        return nickname;
    }


    /**
     * Nickname setter Method
     *
     * @param nickname  How the player wants to be called in the game
     */
    public void setNickname(String nickname) {

        this.nickname = nickname;
    }





    /**
     * pawnColor getter method
     *
     * @return Color
     */
    public PawnColor getPawnColor() {
        return pawnColor;
    }


    /**
     * pawnColor setter Method
     *
     * @param pawnColor the type is the enumeration Color
     */
    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }




    /**
     * Score getter Method
     * @return int Score
     */
    public int getScore() {
        return score;
    }


    /**
     * Score setter Method
     *
     * @param score the current score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Increases the score of the Player
     *
     * @param points points are the additional points that the player just gained, either during the match
     *               (Resource or Gold Cards) or at the end of the game, when the objective Cards points are to be added
     */
    public void increaseScore(int points) {
        score += points;
    }


    /**
     * Round getter Method
     *
     * @return round number of the round the Player already Played
     */
    public int getRound() {
        return round;
    }



/** Substitute for Round Setter Method: we never need to set the round to a specific value, we just
 * need to increase it by one once the player has played*/
    public void IncreaseRound(){
        this.round+=1;
    }


    /**
     * Cards getter. The cards are still declared as PairOfCards cards since the player has them in hand, so
     * he didn't play them yet and hasn't decided which side they want to play
     *
     * @return ArrayList<PairOfCards> cards that the player can currently play with
     */
    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }


    /**
     * When the player placed their card on the PlayArea, they have to draw a new one.
     * If they decided to draw from deck, they have to choose if they want a Gold Card or a Resource Card
     *
     * @param deck the deck they want to draw from
     *             There are going to be two instances of deck, GoldDeck and ResourceDeck.
     */
    public void drawCardFromDeck(Deck deck) {
        cardsInHand.add(deck.DrawCard());
    }


    /*/**
     * Used If the Players wants to draw a Card from the common Cards on the playGround
     *
     * @param card PairOfCards type
     *             Once the player added the card to their hand, it has to be removed from the playground
     *             A new card of the same type needs to be drawn. We get the type of the drawn Card, and we use it as TypeOfDeck to
     *             search for
     */






    /**In this class the player plays pairOfCard; PairOfCard is going to implement the method in the previous comment to choose the side and assign it to Card
     * @param playCardToPlay card with both sides that the player wants to play
     * @param sideToPlay side of the card that the Player wants to play
     * @return chosenSide
     * this is the last time the card gets used as PaiOfCards: from now on it is going to exist in the game just as the side it was played*/
    public SideOfCard ChooseCardToPlay(PlayCard playCardToPlay, Side sideToPlay) {
        cardsInHand.remove(playCardToPlay);
        return playCardToPlay.chooseSide(sideToPlay);
    }


}




