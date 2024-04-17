package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

import com.google.gson.JsonObject;

/** @author Carmen Giaccotto
 * Class that represents a play Card of the game. Each card is a Pair of sideOfCard,
 * where the first element is the front and the second element is the back.
 */
public class Card {

    private  SideOfCard Front;
    private  SideOfCard Back;

    private int idCard;

    private CardColors color;

    public Card( SideOfCard front, SideOfCard back, int idCard, CardColors color) {
        Front = front;
        Back = back;
        this.idCard = idCard;
        this.color = color;
    }


    void mapFromJson(JsonObject jsonObject) {
        this.idCard = jsonObject.get("id").getAsInt();

        if (this.getClass().equals(ObjectiveCard.class) || this.getClass().equals(InitialCard.class))
            this.color=null;
        else
            this.color = CardColors.valueOf(jsonObject.get("color").getAsString());

        JsonObject frontObject = jsonObject.getAsJsonObject("front");
        this.Front = Front.buildFromJson(frontObject);

        JsonObject backObject = jsonObject.getAsJsonObject("back");
        this.Back = Back.buildFromJson(backObject);
    }


    /**
     * getter method for the card's ID.
     *
     * @return idCard
     */
    public int getIdCard() {
        return idCard;
    }

    /**
     * Card's ID setter Method
     *
     * @param idCard ID is set in JSON file
     */
    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }


    /**
     * Method that allows to choose the side of the card to play.
     *
     * @param sideToPlay the side of the card to play (either {@code Side.FRONT} or {@code Side.BACK})
     * @return the chosen side of the card
     */
    public SideOfCard chooseSide(Side sideToPlay){
        if (sideToPlay == Side.FRONT) return Front;
        else{
            return Back;
        }
    }

    public CardColors getColor() {
        return color;
    }


    public SideOfCard getFront(){
        return Front;
    }

    public SideOfCard getBack() {
        return Back;
    }
}


