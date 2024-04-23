package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;
import com.google.gson.JsonObject;

/** Class that represents a play Card of the game. Each card is a Pair of sideOfCard,
 * where the first element is the front and the second element is the back.
 */
public class PlayCard extends Card {

    private SideOfCard Front;
    private  SideOfCard Back;



    private CardColors color;


    public PlayCard(int id,SideOfCard front, SideOfCard back, CardColors color) {
        super(id);
        Front = front;
        Back = back;
        this.color = color;
    }


    public PlayCard mapFromJson(JsonObject jsonObject) {
        Card Card = super.mapFromJson(jsonObject);

        if ( this.getClass().equals(InitialCard.class))
            this.color=null;
        else
            this.color = CardColors.valueOf(jsonObject.get("color").getAsString());

        JsonObject frontObject = jsonObject.getAsJsonObject("front");
        this.Front = Front.mapFromJson(frontObject);

        JsonObject backObject = jsonObject.getAsJsonObject("back");
        this.Back = Back.mapFromJson(backObject);
        return new PlayCard(Card.getIdCard(),this.Front,this.Back,this.color);
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


