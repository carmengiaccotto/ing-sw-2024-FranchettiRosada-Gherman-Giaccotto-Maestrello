package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Class that represents the Player of the game which coincides with the client.
 * This class implements the Serializable interface, allowing instances of this class to be saved to a file.
 * Each Player has its own playArea, nickname, pawnColor, score, cardsInHand, personalObjectiveCard, round, initialCard, and reconnected status.
 */
public class Player implements Serializable {

    /**
     * The playArea attribute represents the play area of the Player.
     * It is an instance of the PlayArea class.
     */
    private PlayArea playArea;

    /**
     * The nickname attribute represents the nickname of the Player.
     * It is a String.
     */
    private String nickname;

    /**
     * The pawnColor attribute represents the color of the Player's pawn.
     * It is an instance of the PawnColor enumeration.
     */
    private PawnColor pawnColor;

    /**
     * The score attribute represents the score of the Player.
     * It is an integer.
     */
    private int score;

    /**
     * The cardsInHand attribute represents the cards that the Player has in hand.
     * It is an ArrayList of PlayCard objects.
     */
    private final ArrayList<PlayCard> cardsInHand;

    /**
     * The personalObjectiveCard attribute represents the personal objective card of the Player.
     * It is an instance of the ObjectiveCard class.
     */
    private ObjectiveCard personalObjectiveCard;

    /**
     * The round attribute represents the current round of the Player.
     * It is an integer.
     */
    private int round;

    /**
     * The initialCard attribute represents the initial card of the Player.
     * It is an instance of the InitialCard class.
     */
    private InitialCard initialCard;

    /**
     * The reconnected attribute indicates whether the Player has reconnected to the game.
     * It is a boolean.
     */
    private boolean reconnected;


    /**
     * Default constructor for the Player class.
     * Initializes a new Player with the following default values:
     * - playArea: A new instance of the PlayArea class.
     * - nickname: null, as the nickname will be set later.
     * - pawnColor: null, as the pawn color will be set later.
     * - score: 0, as the player starts with a score of 0.
     * - cardsInHand: An empty ArrayList, as the player starts with no cards in hand.
     * - round: 0, as the player starts at the beginning of the game.
     * - personalObjectiveCard: null, as the personal objective card will be set later.
     * - initialCard: null, as the initial card will be set later.
     * - reconnected: false, as the player is initially considered to be connected.
     */
    public Player() {
        this.playArea = new PlayArea();
        this.nickname = null;
        this.pawnColor = null;
        this.score = 0;
        this.cardsInHand = new ArrayList<>();//Play Ground initializer will add the elements to this list for the first round
        this.round = 0;
        this.personalObjectiveCard = null;
        this.initialCard=null;
        this.reconnected = false;
    }

    /**
     * Copy constructor for the Player class.
     * Initializes a new Player with the same values as the provided Player object.
     * The playArea is initialized as a new PlayArea object, while the other attributes are copied from the provided Player object.
     * This constructor is typically used when creating a copy of a Player object, for example, when saving the state of the game.
     *
     * @param p The Player object to copy. This should be a non-null Player object.
     */
    public Player(Player p) {
        this.playArea = new PlayArea();
        this.nickname = p.getNickname();
        this.pawnColor = p.getPawnColor();
        this.score = p.getScore();
        this.cardsInHand = p.getCardsInHand();
        this.round = p.getRound();
        this.personalObjectiveCard = p.getPersonalObjectiveCard();
        this.initialCard=p.getInitialCard();
        this.reconnected = p.isReconnected();
    }

    /**
     * This method is a getter for the 'reconnected' attribute of the Player class.
     * The 'reconnected' attribute is a boolean that indicates whether the player has reconnected to the game.
     * This method is used to check if the player has reconnected.
     *
     * @return A boolean value indicating whether the player has reconnected to the game.
     */
    public boolean isReconnected() {
        return reconnected;
    }

    /**
     * This method is a setter for the 'round' attribute of the Player class.
     * The 'round' attribute is an integer that represents the current round of the Player.
     * This method is used to set the current round of the Player.
     *
     * @param r An integer representing the current round of the Player.
     */
    public void setRound(int r) {
        this.round = r;
    }

    /**
     * This method is a getter for the 'playArea' attribute of the Player class.
     * The 'playArea' attribute is an instance of the PlayArea class that represents the play area of the Player.
     * This method is used to retrieve the play area of the Player.
     *
     * @return An instance of the PlayArea class representing the play area of the Player.
     */
    public PlayArea getPlayArea() {
        return this.playArea;
    }

    /**
     * This method is a getter for the 'nickname' attribute of the Player class.
     * The 'nickname' attribute is a String that represents the nickname of the Player.
     * This method is used to retrieve the nickname of the Player.
     *
     * @return A String representing the nickname of the Player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method is a setter for the 'nickname' attribute of the Player class.
     * The 'nickname' attribute is a String that represents the nickname of the Player.
     * This method is used to set the nickname of the Player.
     *
     * @param nickname A String representing the nickname that the player wants to be called in the game.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method is a getter for the 'pawnColor' attribute of the Player class.
     * The 'pawnColor' attribute is an instance of the PawnColor enumeration that represents the color of the Player's pawn.
     * This method is used to retrieve the color of the Player's pawn.
     *
     * @return An instance of the PawnColor enumeration representing the color of the Player's pawn.
     */
    public PawnColor getPawnColor() {
        return pawnColor;
    }

    /**
     * This method is a setter for the 'pawnColor' attribute of the Player class.
     * The 'pawnColor' attribute is an instance of the PawnColor enumeration that represents the color of the Player's pawn.
     * This method is used to set the color of the Player's pawn.
     *
     * @param pawnColor An instance of the PawnColor enumeration representing the color that the player wants their pawn to be.
     */
    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }

    /**
     * This method is a setter for the 'playArea' attribute of the Player class.
     * The 'playArea' attribute is an instance of the PlayArea class that represents the play area of the Player.
     * This method is used to set the play area of the Player.
     *
     * @param playArea An instance of the PlayArea class representing the play area that the player wants to set.
     */
    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
    }

    /**
     * This method is a getter for the 'score' attribute of the Player class.
     * The 'score' attribute is an integer that represents the score of the Player.
     * This method is used to retrieve the score of the Player.
     *
     * @return An integer representing the score of the Player.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method is a setter for the 'score' attribute of the Player class.
     * The 'score' attribute is an integer that represents the score of the Player.
     * This method is used to set the score of the Player.
     *
     * @param score An integer representing the score that the player has achieved in the game.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method is used to increase the score of the Player.
     * The method takes an integer 'points' as a parameter, which represents the additional points that the player has gained.
     * These points can be gained either during the match (Resource or Gold Cards) or at the end of the game, when the objective Cards points are to be added.
     *
     * @param points An integer representing the additional points that the player has gained.
     */
    public void increaseScore(int points) {
        score += points;
    }

    /**
     * This method is a getter for the 'round' attribute of the Player class.
     * The 'round' attribute is an integer that represents the current round of the Player.
     * This method is used to retrieve the current round of the Player.
     *
     * @return An integer representing the current round of the Player.
     */
    public int getRound() {
        return round;
    }

    /**
     * This method is used to increment the 'round' attribute of the Player class.
     * The 'round' attribute is an integer that represents the current round of the Player.
     * This method is used to increase the current round of the Player by one.
     */
    public void IncreaseRound() {
        this.round += 1;
    }

    /**
     * This method is a getter for the 'cardsInHand' attribute of the Player class.
     * The 'cardsInHand' attribute is an ArrayList of PlayCard objects that represents the cards that the Player has in hand.
     * This method is used to retrieve the cards that the Player currently has in hand.
     *
     * @return An ArrayList of PlayCard objects representing the cards that the Player currently has in hand.
     */
    public ArrayList<PlayCard> getCardsInHand() {
        return cardsInHand;
    }

    /**
     * This method is used to add a card to the 'cardsInHand' attribute of the Player class.
     * The 'cardsInHand' attribute is an ArrayList of PlayCard objects that represents the cards that the Player has in hand.
     * This method is used to add a card to the cards that the Player currently has in hand.
     *
     * @param card A PlayCard object representing the card that the player wants to add to their hand.
     */
    public void addCardToHand(PlayCard card) {
        cardsInHand.add(card);
    }

    /**
     * This method allows the player to play a card with a chosen side.
     * The card is removed from the player's hand and the chosen side is set.
     * This is the last time the card is used as a PlayCard: from now on it exists in the game only as the side it was played.
     *
     * @param playCardToPlay The PlayCard object that the player wants to play. This should be a card currently in the player's hand.
     * @param sideToPlay The side of the card that the player wants to play. This should be a valid side of the card.
     * @return The SideOfCard object representing the chosen side of the card.
     */
    public SideOfCard ChooseCardToPlay(PlayCard playCardToPlay, Side sideToPlay) {
        cardsInHand.remove(playCardToPlay);
        return playCardToPlay.chooseSide(sideToPlay);
    }

    /**
     * This method is a getter for the 'personalObjectiveCard' attribute of the Player class.
     * The 'personalObjectiveCard' attribute is an instance of the ObjectiveCard class that represents the personal objective card of the Player.
     * This method is used to retrieve the personal objective card of the Player.
     *
     * @return An instance of the ObjectiveCard class representing the personal objective card of the Player.
     */
    public ObjectiveCard getPersonalObjectiveCard() {
        return personalObjectiveCard;
    }

    /**
     * This method is a setter for the 'personalObjectiveCard' attribute of the Player class.
     * The 'personalObjectiveCard' attribute is an instance of the ObjectiveCard class that represents the personal objective card of the Player.
     * This method is used to set the personal objective card of the Player.
     *
     * @param PersonalObjectiveCard An instance of the ObjectiveCard class representing the objective that the player has to reach in order to gain more points. It cannot be seen by other players and it is different from player to player.
     */
    public void setPersonalObjectiveCard(ObjectiveCard PersonalObjectiveCard) {
        this.personalObjectiveCard = PersonalObjectiveCard;
    }

    /**
     * This method is a getter for the 'initialCard' attribute of the Player class.
     * The 'initialCard' attribute is an instance of the InitialCard class that represents the initial card of the Player.
     * This method is used to retrieve the initial card of the Player.
     *
     * @return An instance of the InitialCard class representing the initial card of the Player.
     */
    public InitialCard getInitialCard() {
        return initialCard;
    }

    /**
     * This method is a setter for the 'initialCard' attribute of the Player class.
     * The 'initialCard' attribute is an instance of the InitialCard class that represents the initial card of the Player.
     * This method is used to set the initial card of the Player.
     *
     * @param initialCard An instance of the InitialCard class representing the initial card that the player starts with.
     */
    public void setInitialCard(InitialCard initialCard) {
        this.initialCard = initialCard;
    }
}



