package CodexNaturalis.src.main.java.it.polimi.ingsw.model.JsonHandler;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonDeckCreator {
    private JsonCardsMapper mapper;


    /**JSON General Deck Constructor
     * @param DeckType class of cards we want to punt in the Deck
     * @return deck arrayList od cards on the desired type
     * JSON Card files and Card Classes have the sam name, so we use DeckType.getSimpleName() to build the Json path*/
    public static ArrayList<? extends Card> createDeckFromJson(Class<? extends Card> DeckType) throws IllegalArgumentException, IOException {
        if (DeckType == null) {
            throw new IllegalArgumentException("DeckType cannot be null");
        }
        String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/JsonFiles/Cards.Json";
        try (FileReader fileReader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            switch (DeckType.getSimpleName()) {
                case "InitialCard" -> {
                    ArrayList<InitialCard> deck = new ArrayList<>();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("InitialCard");
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject InitialCardJson = jsonElement.getAsJsonObject();
                        InitialCard initialCard = JsonCardsMapper.MapInitialCardFromJson(InitialCardJson);
                        deck.add(initialCard);
                    }
                    return deck;
                }
                case "ResourceCard" -> {
                    ArrayList<ResourceCard> deck = new ArrayList<>();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("ResourceCards");
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject ResourceCardJson = jsonElement.getAsJsonObject();
                        ResourceCard resourceCard = JsonCardsMapper.MapResourceCardFromJson(ResourceCardJson);
                        deck.add(resourceCard);
                    }
                    return deck;

                }
                case "GoldCard" -> {
                    ArrayList<GoldCard> deck = new ArrayList<>();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("GoldCards");
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject GoldCardJson = jsonElement.getAsJsonObject();
                        GoldCard goldCard = JsonCardsMapper.MapGoldCardFromJson(GoldCardJson);
                        deck.add(goldCard);
                    }
                }
                case "ObjectiveCard" -> {
                    ArrayList<ObjectiveCard> deck = new ArrayList<>();
                    JsonObject jsonCards = jsonObject.getAsJsonObject("ObjectiveCards");
                    JsonArray dispositionCardsArray = jsonCards.getAsJsonArray("DispositionObjectiveCards");
                    for (JsonElement jsonElement : dispositionCardsArray) {
                        JsonObject DispositionCardJson = jsonElement.getAsJsonObject();
                        DispositionObjectiveCard dispositionCard = JsonCardsMapper.MapDispositionObjectiveCard(DispositionCardJson);
                        deck.add(dispositionCard);
                    }
                    JsonArray symbolCardsArray = jsonCards.getAsJsonArray("SymbolObjectiveCards");
                    for (JsonElement jsonElement : symbolCardsArray) {
                        JsonObject SymbolCardJson = jsonElement.getAsJsonObject();
                        SymbolObjectiveCard symbolCard = JsonCardsMapper.MapSymbolObjectiveCard(SymbolCardJson);
                        deck.add(symbolCard);
                    }
                    return deck;
                }
                default -> throw new IllegalArgumentException("Invalid Class");
            }

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Invalid JSON format in file: " + filePath, e);
        }
        return null;
    }
}
