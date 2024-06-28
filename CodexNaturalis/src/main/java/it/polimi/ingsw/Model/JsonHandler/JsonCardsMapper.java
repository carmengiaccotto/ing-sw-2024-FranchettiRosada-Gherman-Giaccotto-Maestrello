package it.polimi.ingsw.Model.JsonHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for mapping JSON objects to Card objects.
 * It provides static methods to map a JSON object to different types of Card objects.
 */
public class JsonCardsMapper {

    /**
     * This method maps a JSON object to a Card object.
     * It extracts the "id" field from the JSON object and uses it to create a new Card object.
     *
     * @param jsonObject The JSON object to map to a Card object.
     * @return A new Card object with the ID extracted from the JSON object.
     */
    public static Card MapCardFromJson(JsonObject jsonObject){
        int idCard= jsonObject.get("id").getAsInt();
        return new Card(idCard);
    }

    /**
     * This method maps a JSON object to a PlayCard object.
     * It first extracts the "id" field from the JSON object and uses it to create a new Card object.
     * Then it checks if the "color" field exists in the JSON object and if it does, it uses it to set the color of the card.
     * It then extracts the "front" and "back" fields from the JSON object and uses them to create new SideOfCard objects.
     * Finally, it creates a new PlayCard object using the id, front, back, and color and returns it.
     *
     * @param jsonObject The JSON object to map to a PlayCard object.
     * @return A new PlayCard object with the ID, front, back, and color extracted from the JSON object.
     */
    public static PlayCard MapPlayCardFromJson(JsonObject jsonObject){
        Card card = MapCardFromJson(jsonObject);
        CardColors color = null;
        JsonElement jsonColorElement = jsonObject.get("color");
        if (jsonColorElement != null && !jsonColorElement.isJsonNull()) {
            color = CardColors.valueOf(jsonColorElement.getAsString());
        }

        JsonObject frontObject = jsonObject.getAsJsonObject("front");

        SideOfCard front= mapSideFromJson(frontObject);
        JsonObject backObject = jsonObject.getAsJsonObject("back");
        SideOfCard back= mapSideFromJson(backObject);
        return new PlayCard(card.getIdCard(),front,back,color);
    }

    /**
     * This method is used to map a JSON object to a SideOfCard object.
     * It first extracts the "symbols" field from the JSON object and uses it to create a HashMap of symbols.
     * Then it extracts the "corner1", "corner2", "corner3", and "corner4" fields from the JSON object and uses them to create a 2D array of Corners.
     * Finally, it creates a new SideOfCard object using the symbols and corners and returns it.
     *
     * @param jsonObject The JSON object to map to a SideOfCard object.
     * @return A new SideOfCard object with the symbols and corners extracted from the JSON object.
     */
    public static SideOfCard mapSideFromJson(JsonObject jsonObject){
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        JsonObject symbolsObject = jsonObject.getAsJsonObject("symbols");
        if (symbolsObject != null) {
            for (Map.Entry<String, JsonElement> entry : symbolsObject.entrySet()) {
                String symbolName = entry.getKey();
                JsonElement symbolValueElement = entry.getValue();
                if (symbolValueElement != null && symbolValueElement.isJsonPrimitive()) {
                    int symbolValue = symbolValueElement.getAsInt();
                    Symbol symbol = Symbol.valueOf(symbolName.toUpperCase());
                    symbols.put(symbol, symbolValue);
                }
            }
        }
        else
            symbols = new HashMap<>();

        Corner[][] corners = new Corner[2][2];
        JsonElement corner1Element = jsonObject.get("corner1");
        JsonElement corner2Element = jsonObject.get("corner2");
        JsonElement corner3Element = jsonObject.get("corner3");
        JsonElement corner4Element = jsonObject.get("corner4");

        if (corner1Element != null && !corner1Element.isJsonNull()) {
            corners[0][0] = CornerFactory.createCornerFromJson(corner1Element.getAsString());
        }

        if (corner2Element != null && !corner2Element.isJsonNull()) {
            corners[0][1] = CornerFactory.createCornerFromJson(corner2Element.getAsString());
        }

        if (corner3Element != null && !corner3Element.isJsonNull()) {
            corners[1][0] = CornerFactory.createCornerFromJson(corner3Element.getAsString());
        }

        if (corner4Element != null && !corner4Element.isJsonNull()) {
            corners[1][1] = CornerFactory.createCornerFromJson(corner4Element.getAsString());
        }
        return new SideOfCard(symbols, corners);

    }

    /**
     * This method is used to map a JSON object to an InitialCard object.
     * It first uses the MapPlayCardFromJson method to map the JSON object to a PlayCard object.
     * Then it creates a new InitialCard object using the id, front, back, and color from the PlayCard object and returns it.
     *
     * @param jsonObject The JSON object to map to an InitialCard object.
     * @return A new InitialCard object with the id, front, back, and color extracted from the JSON object.
     */
    public static InitialCard MapInitialCardFromJson(JsonObject jsonObject){
        PlayCard playCard= JsonCardsMapper.MapPlayCardFromJson(jsonObject);
        return new InitialCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor());
    }

    /**
     * This method is used to map a JSON object to a GoldCard object.
     * It first uses the MapPlayCardFromJson method to map the JSON object to a PlayCard object.
     * Then it extracts the "point" field from the JSON object and uses it to set the point of the card.
     * It also extracts the "requirement" field from the JSON object and uses it to create a HashMap of requirements.
     * It checks if the "coveredCorners" field exists in the JSON object and if it does, it creates a new PointPerCoveredCorner object and returns it.
     * If the "coveredCorners" field does not exist, it extracts the "goal" field from the JSON object and uses it to create a HashMap of goals.
     * It then checks if the goal symbol exists in the goal map and if it does, it creates a new PointPerVisibleSymbol object and returns it.
     * If the goal symbol does not exist, it creates a new GoldCard object and returns it.
     *
     * @param jsonObject The JSON object to map to a GoldCard object.
     * @return A new GoldCard object with the id, front, back, color, requirement, point, and goal symbol extracted from the JSON object.
     */
    public static GoldCard MapGoldCardFromJson (JsonObject jsonObject) {
        PlayCard playCard = MapPlayCardFromJson(jsonObject);
        JsonObject FrontCard = jsonObject.getAsJsonObject("front");
        int point = FrontCard.get("point").getAsInt();
        JsonObject requirementJson = FrontCard.getAsJsonObject("requirement");
        HashMap<Symbol, Integer> requirementMap = new HashMap<>();
        for (String key : requirementJson.keySet()) {
            Symbol symbol = Symbol.valueOf(key);
            int value = requirementJson.get(key).getAsInt();
            requirementMap.put(symbol, value);
        }
        boolean typeOfGoal = FrontCard.get("coveredCorners").getAsBoolean();
        if (typeOfGoal)
            return new PointPerCoveredCorner(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), requirementMap, point);
        else {
            JsonObject JsonGoal = FrontCard.getAsJsonObject("goal");
            HashMap<Symbol, Integer> GoalMap = new HashMap<>();
            for (String key : JsonGoal.keySet()) {
                Symbol symbol = Symbol.valueOf(key);
                int value = JsonGoal.get(key).getAsInt();
                GoalMap.put(symbol, value);
            }
            Symbol goalSymbol = null;
            for (Symbol symbol : GoalMap.keySet()) {
                if (GoalMap.get(symbol) == 1)
                    goalSymbol = symbol;
            }
            if (goalSymbol != null)
                return new PointPerVisibleSymbol(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), requirementMap, point, goalSymbol);
            else
                return new GoldCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), requirementMap, point);

        }
    }

    /**
     * This method is used to map a JSON object to a ResourceCard object.
     * It first uses the MapPlayCardFromJson method to map the JSON object to a PlayCard object.
     * Then it extracts the "point" field from the "front" object in the JSON object and uses it to set the point of the card.
     * Finally, it creates a new ResourceCard object using the id, front, back, color, and point from the PlayCard object and returns it.
     *
     * @param jsonObject The JSON object to map to a ResourceCard object.
     * @return A new ResourceCard object with the id, front, back, color, and point extracted from the JSON object.
     */
    public static ResourceCard MapResourceCardFromJson(JsonObject jsonObject){
        PlayCard playCard = MapPlayCardFromJson(jsonObject);
        JsonObject FrontCard = jsonObject.getAsJsonObject("front");
        boolean point = FrontCard.get("point").getAsBoolean();
        return new ResourceCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), point);

    }

    /**
     * This method is used to map a JSON object to an ObjectiveCard object.
     * It first uses the MapCardFromJson method to map the JSON object to a Card object.
     * Then it extracts the "points" field from the JSON object and uses it to set the points of the card.
     * It iterates over the ObjectivePoints enumeration values and if the points from the JSON object match any of the enumeration values, it sets the cardPoints to that enumeration value.
     * Finally, it creates a new ObjectiveCard object using the id from the Card object and the cardPoints and returns it.
     *
     * @param jsonObject The JSON object to map to an ObjectiveCard object.
     * @return A new ObjectiveCard object with the id and points extracted from the JSON object.
     */
    public static ObjectiveCard MapObjectiveCardFromJson(JsonObject jsonObject){
        Card card= MapCardFromJson(jsonObject);
        ObjectivePoints cardPoints=null;
        int pointsFromJson= jsonObject.get("points").getAsInt();
        for(ObjectivePoints points: ObjectivePoints.values())
            if(pointsFromJson==points.getValue())
                cardPoints =points;
        return new ObjectiveCard(card.getIdCard(), cardPoints);
    }

    /**
     * This method is used to map a JSON object to a DispositionObjectiveCard object.
     * It first uses the MapObjectiveCardFromJson method to map the JSON object to an ObjectiveCard object.
     * Then it extracts the "CentralCardColor" field from the JSON object and uses it to set the central card color.
     * It also extracts the "NEIGHBORS" field from the JSON object and uses it to create a map of neighbors.
     * It iterates over the CornerPosition and UpDownPosition enumeration values and if the key from the JSON object matches any of the enumeration values, it sets the position to that enumeration value.
     * It then checks if the position is not null and if it is not, it gets the color associated with the position and adds the position-color pair to the neighbors map.
     * Finally, it creates a new DispositionObjectiveCard object using the id, points, central card color, and neighbors from the ObjectiveCard object and the JSON object and returns it.
     *
     * @param jsonObject The JSON object to map to a DispositionObjectiveCard object.
     * @return A new DispositionObjectiveCard object with the id, points, central card color, and neighbors extracted from the JSON object.
     */
    public static DispositionObjectiveCard MapDispositionObjectiveCard(JsonObject jsonObject){
        ObjectiveCard objectiveCard= MapObjectiveCardFromJson(jsonObject);
        CardColors CentralCardColor = CardColors.valueOf(jsonObject.get("CentralCardColor").getAsString());
        JsonObject neighbors = jsonObject.getAsJsonObject("NEIGHBORS");
        Map<Position, CardColors> Neighbors= new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : neighbors.entrySet()) {
            Position position = null;

            // Search for the position in the CornerPosition enumeration
            for (CornerPosition corner : CornerPosition.values()) {
                if (entry.getKey().toUpperCase().equals(corner.toString())) {
                    position = corner;
                }
            }

            if (position == null) {
                for (UpDownPosition pos : UpDownPosition.values()) {
                    if (entry.getKey().toUpperCase().equals(pos.toString())) {
                        position = pos;
                    }
                }
            }

            // Ensure that the position has been found
            if (position != null) {
                // Get the color associated with the position
                CardColors neighborColor = CardColors.valueOf(entry.getValue().getAsString());

                // Add the position-color pair to the map
                Neighbors.put(position, neighborColor);
            }
        }

        return new DispositionObjectiveCard(objectiveCard.getIdCard(),objectiveCard.getPoints(),CentralCardColor,Neighbors);
    }

    /**
     * This method is used to map a JSON object to a SymbolObjectiveCard object.
     * It first uses the MapObjectiveCardFromJson method to map the JSON object to an ObjectiveCard object.
     * Then it extracts the "RequiredSymbols" field from the JSON object and uses it to create a HashMap of goals.
     * It iterates over the entries in the "RequiredSymbols" object and for each entry, it gets the key as a Symbol and the value as an Integer and adds them to the goal map.
     * Finally, it creates a new SymbolObjectiveCard object using the id, points from the ObjectiveCard object and the goal map and returns it.
     *
     * @param jsonObject The JSON object to map to a SymbolObjectiveCard object.
     * @return A new SymbolObjectiveCard object with the id, points, and goal extracted from the JSON object.
     */
    public  static SymbolObjectiveCard MapSymbolObjectiveCard(JsonObject jsonObject){
        ObjectiveCard objectiveCard= MapObjectiveCardFromJson(jsonObject);
        JsonObject requiredSymbols = jsonObject.getAsJsonObject("RequiredSymbols");
        HashMap<Symbol, Integer> goal= new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : requiredSymbols.entrySet()) {
            Symbol symbol = Symbol.valueOf(entry.getKey());
            int quantity = entry.getValue().getAsInt();
            goal.put(symbol, quantity);
        }
        return new SymbolObjectiveCard(objectiveCard.getIdCard(),objectiveCard.getPoints(), goal);

    }
}
