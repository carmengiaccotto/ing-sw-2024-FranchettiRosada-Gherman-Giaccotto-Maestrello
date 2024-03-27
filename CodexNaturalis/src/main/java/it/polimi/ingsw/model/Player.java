package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;

import java.awt.*;
import java.util.ArrayList;


/**@author Denisa Minodora Gherman
* Class that represents the Player of the game which coincides with the client*/
public class Player {
    /*Every Player has its own playArea.*/
    private PlayArea playArea;
    private String nickname;
    private static Color pawnColor;
    private int score;
    private ArrayList<PairOfCards> cardsInHand;
    private int round;


    public Player(String nickname, Color pawnColor) {
        this.playArea = new PlayArea();
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.score = 0;
        this.cardsInHand = new ArrayList<>(); //Play Ground initializer will add the elements to this list for the first round
        this.round = 0;
    }

    /**
     * getter method for the Player's playArea
     *
     * @return PlayaArea
     */
    public PlayArea getPlayArea() {
        return playArea;
    }


    /**
     * playArea setter Method
     *
     * @param playArea
     */
    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
    }


    /**
     * getter method for the Player's Nickname
     *
     * @return string NickName
     */

    public String getNickname() {
        return nickname;
    }


    /**
     * Nickname setter Method
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * pawnColor getter method
     *
     * @return Color
     */





    /** String that allows the player to set their nickname , that has to be different from other players' ones
     * @param chosenNickName the nickname the user wants to have
     * @return chosenNickname if there are no players with the same nickname, we can set the player's nickname
     * to the chosen one*/

    public String chooseNickName(String chosenNickName){
        Boolean AvailableNickName= true;
        ArrayList<Player>OtherPlayers=new ArrayList<>();
        while(AvailableNickName==true) {
            for (Player player : OtherPlayers) {
                AvailableNickName=!(player.getNickname().equals(chosenNickName));
            }


        }
        if (AvailableNickName){
            setNickname(chosenNickName);
            return chosenNickName;
        }
        else{
            return null; //What do we return in this case?
        }


    }










    public static Color getPawnColor() {
        return pawnColor;
    }


    /**
     * pawnColor setter Method
     *
     * @param pawnColor the type is the enumeration Color
     */
    public void setPawnColor(Color pawnColor) {
        this.pawnColor = pawnColor;
    }


    /**
     * Method to give the player the pawnColors that have not been already taken by other players
     *
     * @return colorList the list of colors the player can still choose from
     */

    public ArrayList<Color> DisplayAvailablePawnColors() {
        ArrayList<Color> colorsList = new ArrayList<Color>();
        ArrayList<Color> colorsArray = new ArrayList<Color>();//verificare questa parte per iterare sui componenti di una enum
        for (Color color : colorsArray) {
            colorsList.add(Color.decode(String.valueOf(color)));
        }
        for (Color color : colorsList) {/*creating a list of the possible colors a Player can choose from*/
            for (Player player : PlayGround.getListOfPlayers()) {
                if (Player.getPawnColor() != null) {
                    if (Player.getPawnColor().equals(color)) {
                        colorsList.remove(color);
                    }
                }


            }
        }
        return colorsList;
    }
/**
 * Method to allow the player to choose its own pawn Color between thw ones that have not already been
 * by other players.
 *
 * @param chosenColor the color that the user wants to use
 * @return temporaryColor the chosen color
 * @throws RuntimeException if the player did not choose any of the available colors
 */
public Color ChoosePawnColor(Color chosenColor){
    ArrayList<Color> AvailableColors = new ArrayList<Color>();
    AvailableColors=DisplayAvailablePawnColors();
    Boolean colorChoiceOk=false;
    int i=0;
    Color temporaryColor= null;
    while(colorChoiceOk==false && i<=4){
        i++;
        for (Color color: AvailableColors){
            colorChoiceOk= (chosenColor.equals(color));
            temporaryColor=color;

        }
    }
    if (colorChoiceOk==false) {
        throw new RuntimeException("Sorry, this color is already taken. Please pick a new one");
    }
    else{
        setPawnColor(temporaryColor);
        return temporaryColor;
    }



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


    /**
     * Cards getter. The cards are still declared as PairOfCards cards since the player has them in hand, so
     * he didn't play them yet and hasn't decided which side they want to play
     *
     * @return ArrayList<PairOfCards> cards that the player can currently play with
     */
    public ArrayList<PairOfCards> getCardsInHand() {
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


    /**
     * Used If the Players wants to draw a Card from the common Cards on the playGround
     *
     * @param card PairOfCards type
     *             Once the player added the card to their hand, it has to be removed from the playground
     *             A new card of the same type needs to be drawn. We get the type of the drawn Card, and we use it as TypeOfDeck to
     *             search for
     */
    public void drawCardFromPlayground(PairOfCards card) {
        cardsInHand.add(card);
        PlayGround.getCommonCard().remove(card);
        String cardType = card.getClass().getSimpleName();
        for (Deck deck : PlayGround.getDecks()) {
            if (deck.getTypeOfDeck().equals(cardType))
                PlayGround.getCommonCard().add(deck.DrawCard());

        }
    }




    /*public Card ChooseSide(PairOfCards DoubleSidedCard, Sides side) {// modifiable
        Card cardToPlay = DoubleSidedCard.setSide(side); //the player chooses the side of the card they want to play
        //card is the class that represents the chosen side of the card that the player decided to play
        return cardToPlay;
    }*/
    //This method is probably going to be implemented in PairOfCards class


    /**In this class the player plays pairOfCard; PairOfCard is going to implement the method in the previous comment to choose the side and assign it to Card
     * @param cardToPlay
     * @param sideToPlay
     * @return chosenSide
     * this is the last time the card gets used as PaiOfCards: from now on it is going to exist in the game just as the side it was played*/
    public Card ChooseCardToPlay(PairOfCards  cardToPlay , Side sideToPlay) {
        return cardToPlay.chooseSide(sideToPlay);
    }


}




