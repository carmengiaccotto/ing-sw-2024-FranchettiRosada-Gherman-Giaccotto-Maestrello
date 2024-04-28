package CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.Side;
import com.google.gson.JsonElement;
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
        if(front!=null)
            front.setColor(color);
        Back = back;
        if(back!=null)
            back.setColor(color);
        this.color = color;
    }


    public PlayCard mapFromJson(JsonObject jsonObject) {
        Card Card = super.mapFromJson(jsonObject);

        if ( this.getClass().equals(InitialCard.class))
            this.color=null;
        else{
            JsonElement jsonColorElement = jsonObject.get("color");
            if (jsonColorElement != null && !jsonColorElement.isJsonNull()) {
                this.color = CardColors.valueOf(jsonColorElement.getAsString());
            }
        }


        JsonObject frontObject = jsonObject.getAsJsonObject("front");
        SideOfCard front=new SideOfCard(null, null);
        this.Front =front.mapSideFromJson(frontObject);

        JsonObject backObject = jsonObject.getAsJsonObject("back");
        SideOfCard back= new SideOfCard(null, null);
        this.Back = back.mapSideFromJson(backObject);
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

  /**Getter Method for Color Attribute
   * @return  color of the card*/
    public CardColors getColor() {
        return color;
    }

    /**Getter Method for Front Attribute
     * @return Front: front side of the Card*/

    public SideOfCard getFront(){
        return Front;
    }

    /**Getter Method for Back Attribute
     * @return Back: back side of the Card*/

    public SideOfCard getBack() {
        return Back;
    }
}


