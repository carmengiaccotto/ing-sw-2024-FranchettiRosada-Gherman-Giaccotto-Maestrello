package it.polimi.ingsw.Model.PlayGround;


import it.polimi.ingsw.Model.Cards.*;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * PlayGround class
 * PlayGround is the class that represents the game playground, it contains all the information about the game playground, and it's based on MVC pattern.
 * It contains the list of common play cards and common Objectives, the four decks (GoldCardDeck, ResourceCardDeck, ObjectiveCardDeck, InitialCardDeck).
 * It also contains the filepath to the resources.
 */
public class PlayGround implements Serializable {

    /**
    *The filepath to the resources
     */
    private String filepath = "CodexNaturalis/src/main/resources/";

    /**
    * The deck of GoldCard objects
     */
    private final Deck GoldCardDeck;

    /**
    * The deck of ResourceCard objects
     */
    private final Deck ResourceCardDeck;

    /**
    * The deck of ObjectiveCard objects
     */
    private final Deck ObjectiveCardDeck;

    /**
     * The deck of InitialCard objects
     */
    private final Deck InitialCardDeck;

    /**
    * The list of common ResourceCard objects
     */
    private final ArrayList<ResourceCard> commonResourceCards;

    /**
    * The list of common GoldCard objects
     */
    private final ArrayList<GoldCard> commonGoldCards;

    /**
    * The list of common ObjectiveCard objects
     */
    private final ArrayList<ObjectiveCard> commonObjectivesCards;

    /**
     * Default constructor for the PlayGround class.
     *
     * This constructor initializes the four decks of cards (GoldCardDeck, ResourceCardDeck, ObjectiveCardDeck, InitialCardDeck)
     * by creating new instances of the Deck class for each type of card.
     * Each deck is then shuffled to ensure a random order of cards.
     *
     * It also initializes the lists of common cards (commonResourceCards, commonGoldCards, commonObjectivesCards)
     * as new ArrayLists, ready to hold the common cards that will be used in the game.
     *
     * @throws IOException If there is an error reading the card data files.
     */
    public PlayGround() throws IOException {

        GoldCardDeck = new Deck(GoldCard.class);
        GoldCardDeck.shuffle();
        ResourceCardDeck = new Deck(ResourceCard.class);
        ResourceCardDeck.shuffle();
        ObjectiveCardDeck = new Deck(ObjectiveCard.class);
        ObjectiveCardDeck.shuffle();
        InitialCardDeck = new Deck(InitialCard.class);
        InitialCardDeck.shuffle();

        commonResourceCards = new ArrayList<>();
        commonGoldCards = new ArrayList<>();
        commonObjectivesCards = new ArrayList<>();

    }

    /**
     * Constructor for the PlayGround class that uses a filepath to retrieve JSON cards.
     *
     * This constructor initializes the four decks of cards (GoldCardDeck, ResourceCardDeck, ObjectiveCardDeck, InitialCardDeck)
     * by creating new instances of the Deck class for each type of card, using the provided filepath to load the card data.
     * Each deck is then shuffled to ensure a random order of cards.
     *
     * It also initializes the lists of common cards (commonResourceCards, commonGoldCards, commonObjectivesCards)
     * as new ArrayLists, ready to hold the common cards that will be used in the game.
     *
     * @param filepath A string representing the filepath to the JSON files containing the card data.
     * @throws IOException If there is an error reading the card data files.
     */
    public PlayGround(String filepath) throws IOException {
        GoldCardDeck = new Deck(GoldCard.class);
        GoldCardDeck.shuffle();
        ResourceCardDeck = new Deck(ResourceCard.class);
        ResourceCardDeck.shuffle();
        ObjectiveCardDeck = new Deck(ObjectiveCard.class);
        ObjectiveCardDeck.shuffle();
        InitialCardDeck = new Deck(InitialCard.class);
        InitialCardDeck.shuffle();

        commonResourceCards = new ArrayList<>();
        commonGoldCards = new ArrayList<>();
        commonObjectivesCards = new ArrayList<>();
    }

    /**
     * This method is a getter for the 'GoldCardDeck' attribute of the PlayGround class.
     * The 'GoldCardDeck' attribute is an instance of the Deck class that represents the deck of GoldCard objects in the game.
     * This method is used to retrieve the deck of GoldCard objects.
     *
     * @return An instance of the Deck class representing the deck of GoldCard objects.
     */
    public Deck getGoldCardDeck() {
        return GoldCardDeck;
    }

    /**
     * This method is a getter for the 'InitialCardDeck' attribute of the PlayGround class.
     * The 'InitialCardDeck' attribute is an instance of the Deck class that represents the deck of InitialCard objects in the game.
     * This method is used to retrieve the deck of InitialCard objects.
     *
     * @return An instance of the Deck class representing the deck of InitialCard objects.
     */
    public Deck getInitialCardDeck() {
        return InitialCardDeck;
    }

    /**
     * This method is a getter for the 'ObjectiveCardDeck' attribute of the PlayGround class.
     * The 'ObjectiveCardDeck' attribute is an instance of the Deck class that represents the deck of ObjectiveCard objects in the game.
     * This method is used to retrieve the deck of ObjectiveCard objects.
     *
     * @return An instance of the Deck class representing the deck of ObjectiveCard objects.
     */
    public Deck getObjectiveCardDeck() {
        return ObjectiveCardDeck;
    }

    /**
     * This method is a getter for the 'ResourceCardDeck' attribute of the PlayGround class.
     * The 'ResourceCardDeck' attribute is an instance of the Deck class that represents the deck of ResourceCard objects in the game.
     * This method is used to retrieve the deck of ResourceCard objects.
     *
     * @return An instance of the Deck class representing the deck of ResourceCard objects.
     */
    public Deck getResourceCardDeck() {
        return ResourceCardDeck;
    }

    /**
     * This method adds a common card to the game model.
     *
     * The card to be added is passed as a parameter. The method uses a switch statement to check the type of the card.
     * If the card is an instance of GoldCard, it is added to the 'commonGoldCards' list.
     * If the card is an instance of ResourceCard, it is added to the 'commonResourceCards' list.
     * If the card is an instance of ObjectiveCard, it is added to the 'commonObjectivesCards' list.
     * If the card is null or of an unsupported type, an IllegalArgumentException is thrown.
     *
     * @param c The card to be added. This should be an instance of GoldCard, ResourceCard, or ObjectiveCard.
     * @throws IllegalArgumentException If the card is null or of an unsupported type.
     */
    public void addCommonCard(Card c) {

        switch (c) {
            case GoldCard goldCard -> commonGoldCards.add(goldCard);
            case ResourceCard resourceCard -> commonResourceCards.add(resourceCard);
            case ObjectiveCard objectiveCard -> commonObjectivesCards.add(objectiveCard);
            case null, default -> throw new IllegalArgumentException("Unsupported card type");
        }
    }

    /**
     * This method is a getter for the 'commonResourceCards' attribute of the PlayGround class.
     * The 'commonResourceCards' attribute is an ArrayList that represents the list of common ResourceCard objects in the game.
     * This method is used to retrieve the list of common ResourceCard objects.
     *
     * @return An ArrayList of ResourceCard objects representing the common ResourceCard objects in the game.
     */
    public ArrayList<ResourceCard> getCommonResourceCards() {
        return commonResourceCards;
    }

    /**
     * This method is a getter for the 'commonObjectivesCards' attribute of the PlayGround class.
     * The 'commonObjectivesCards' attribute is an ArrayList that represents the list of common ObjectiveCard objects in the game.
     * This method is used to retrieve the list of common ObjectiveCard objects.
     *
     * @return An ArrayList of ObjectiveCard objects representing the common ObjectiveCard objects in the game.
     */
    public ArrayList<ObjectiveCard> getCommonObjectivesCards() {
        return commonObjectivesCards;
    }

    /**
     * This method is a getter for the 'commonGoldCards' attribute of the PlayGround class.
     * The 'commonGoldCards' attribute is an ArrayList that represents the list of common GoldCard objects in the game.
     * This method is used to retrieve the list of common GoldCard objects.
     *
     * @return An ArrayList of GoldCard objects representing the common GoldCard objects in the game.
     */
    public ArrayList<GoldCard> getCommonGoldCards() {
        return commonGoldCards;
    }

    /**
     * This method substitutes the drawn card from the playground.
     *
     * The method takes in two parameters: the index of the card to be replaced and the card that the player wants to draw.
     * If the card to be drawn is an instance of GoldCard, the method removes the card from the 'commonGoldCards' list and adds a new GoldCard from the 'GoldCardDeck' at the same index.
     * If the card to be drawn is not a GoldCard, the method assumes it is a ResourceCard. It removes the card from the 'commonResourceCards' list and adds a new ResourceCard from the 'ResourceCardDeck' at the same index.
     *
     * @param i The index of the card to be replaced. This is used to substitute the card in the correct position.
     * @param card The card that the player wants to draw. This should be an instance of GoldCard or ResourceCard.
     */
    public void drawCardFromPlayground(int i, PlayCard card){
        if (card instanceof GoldCard){
            commonGoldCards.remove(card);
            commonGoldCards.add(i,(GoldCard) GoldCardDeck.drawCard());

    }else{
            commonResourceCards.remove(card);
            commonResourceCards.add(i,(ResourceCard) ResourceCardDeck.drawCard());
        }
    }

}




