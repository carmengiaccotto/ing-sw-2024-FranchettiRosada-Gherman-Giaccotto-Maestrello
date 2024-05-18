package it.polimi.ingsw.Model.JsonHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Symbol;

import java.util.HashMap;
import java.util.Map;


public class JsonCardsMapper {


    /**JSON  Card Constructor
     * @param jsonObject  JSON
     * @return Card with JSON card ID */
    public static Card MapCardFromJson(JsonObject jsonObject){
        int idCard= jsonObject.get("id").getAsInt();
        return new Card(idCard);
    }

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

    public static InitialCard MapInitialCardFromJson(JsonObject jsonObject){
        PlayCard playCard= JsonCardsMapper.MapPlayCardFromJson(jsonObject);
        return new InitialCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor());
    }

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

    public static ResourceCard MapResourceCardFromJson(JsonObject jsonObject){
        PlayCard playCard = MapPlayCardFromJson(jsonObject);
        JsonObject FrontCard = jsonObject.getAsJsonObject("front");
        boolean point = FrontCard.get("point").getAsBoolean();
        return new ResourceCard(playCard.getIdCard(), playCard.getFront(), playCard.getBack(), playCard.getColor(), point);

    }


    public static ObjectiveCard MapObjectiveCardFromJson(JsonObject jsonObject){
        Card card= MapCardFromJson(jsonObject);
        ObjectivePoints cardPoints=null;
        int pointsFromJson= jsonObject.get("points").getAsInt();
        for(ObjectivePoints points: ObjectivePoints.values())
            if(pointsFromJson==points.getValue())
                cardPoints =points;
        return new ObjectiveCard(card.getIdCard(), cardPoints);
    }

    public static DispositionObjectiveCard MapDispositionObjectiveCard(JsonObject jsonObject){
        ObjectiveCard objectiveCard= MapObjectiveCardFromJson(jsonObject);
        CardColors CentralCardColor = CardColors.valueOf(jsonObject.get("CentralCardColor").getAsString());
        JsonObject neighbors = jsonObject.getAsJsonObject("NEIGHBORS");
        Map<Position, CardColors> Neighbors= new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : neighbors.entrySet()) {
            Position position = null;

            // Cerca la posizione nell'enumerazione CornerPosition
            for (CornerPosition corner : CornerPosition.values()) {
                if (entry.getKey().toUpperCase().equals(corner.toString())) {
                    position = corner;
                    break;
                }
            }

            if (position == null) {
                for (UpDownPosition pos : UpDownPosition.values()) {
                    if (entry.getKey().toUpperCase().equals(pos.toString())) {
                        position = pos;
                        break;
                    }
                }
            }

            // Assicurati che la posizione sia stata trovata
            if (position != null) {
                // Ottieni il colore associato alla posizione
                CardColors neighborColor = CardColors.valueOf(entry.getValue().getAsString());

                // Aggiungi la coppia posizione-colore alla mappa
                Neighbors.put(position, neighborColor);
            }
        }


        return new DispositionObjectiveCard(objectiveCard.getIdCard(),objectiveCard.getPoints(),CentralCardColor,Neighbors);
    }

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
