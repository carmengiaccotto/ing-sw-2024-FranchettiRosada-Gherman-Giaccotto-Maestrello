package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.ObjectiveCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.PlayCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.PawnColor;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;

import java.util.ArrayList;
import java.util.NoSuchElementException;


/** Class that represents the Player of the game which coincides with the client*/
public class Player {
    /**Every Player has its own playArea.*/
    private PlayArea playArea;
    private String nickname;
    private PawnColor pawnColor;
    private int score;
    private final ArrayList<PlayCard> cardsInHand;
    private ObjectiveCard personalObjectiveCard;
    private int round;


    public Player(String nickname, PawnColor pawnColor, int Score, int Round) {
        this.playArea = new PlayArea(null);
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.score = Score;
        this.cardsInHand = new ArrayList<>(); //Play Ground initializer will add the elements to this list for the first round
        this.round = Round;
        this.personalObjectiveCard=null;
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
     * Setter Method for PlayArea Attribute
     * @param playArea player's PlayArea
     * */
    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
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
    public ArrayList<PlayCard> getCardsInHand() {
        return cardsInHand;
    }


    /**Substitute of setter method for CardsInHand
     * @param card to add to player's hand*/
    public void addCardToHand (PlayCard card){
        cardsInHand.add(card);

    }


    /**General DrawCard Method.
     * @param deckOrCommon this list is going to be the list of cards form a Deck if the player wants to draw a card from a Deck,
     *                      or the list of CommonCards on the PlayGround if the Player wants to draw a card from the PlayGround
     * @param drawnCard PlayCard: if the player draws a card from a deck, drawnCard is going to be set to deck.getLastCard(),
     *                  else, the player can choose a card from the list, as long as the card belongs to the list
     * @throws NoSuchElementException if the player is trying to draw a card that is not between the available ones. Used especially for CommonCards
     * @throws IllegalStateException if the list is empty. Used especially if the player wants to draw from an empty deck*/
    public void DrawCardFrom(ArrayList<PlayCard> deckOrCommon, PlayCard drawnCard){
        if(!deckOrCommon.isEmpty()) {
            if (deckOrCommon.contains(drawnCard)) {
                addCardToHand(drawnCard);
                deckOrCommon.remove(drawnCard);
            } else
                throw new NoSuchElementException("This card is not between the options");
        }
        else throw new IllegalStateException("No cards Left");

    }


    /**In this class the player plays pairOfCard; PairOfCard is going to implement the method in the previous comment to choose the side and assign it to Card
     * @param playCardToPlay card with both sides that the player wants to play
     * @param sideToPlay side of the card that the Player wants to play
     * @return chosenSide
     * this is the last time the card gets used as PaiOfCards: from now on it is going to exist in the game just as the side it was played*/
    public SideOfCard ChooseCardToPlay(PlayCard playCardToPlay, Side sideToPlay) {
        cardsInHand.remove(playCardToPlay);
        return playCardToPlay.chooseSide(sideToPlay);
    }



    /**Getter method for personalObjectiveCard attribute
     * @return ObjectiveCard */
    public ObjectiveCard getPersonalObjectiveCard() {
        return personalObjectiveCard;
    }


    /**Setter method for personalObjectiveCard attribute
     * @param PersonalObjectiveCard  the Objective that the player has to reach in order
     *                                 to gain more points; It can not be seen by other players,
     *                                 and it is different from Player to Player*/
    public void setPersonalObjectiveCard(ObjectiveCard PersonalObjectiveCard) {
        this.personalObjectiveCard = PersonalObjectiveCard;
    }
}




