package it.polimi.ingsw.Model.JsonHandler;

import com.google.gson.*;
import it.polimi.ingsw.JsonParser.JsonParser;
import it.polimi.ingsw.Model.Cards.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class is responsible for creating a deck of cards from a JSON file.
 * It uses the Gson library to parse the JSON file and map it to Java objects.
 */
public class JsonDeckCreator {
    /**
     * The JsonCardsMapper instance used for mapping JSON objects to card objects.
     * This mapper is responsible for converting the JSON representation of a card into a Java object.
     * It is used in the process of creating a deck of cards from a JSON file.
     */
    private JsonCardsMapper mapper;

    /**
     * This method creates a deck of cards from a JSON file.
     * It first checks if the DeckType is null and throws an IllegalArgumentException if it is.
     * Then it creates a FileReader to read the JSON file and a Gson object to parse it.
     * It gets the simple name of the DeckType and uses a switch statement to determine which type of card to create.
     * For each type of card, it gets the corresponding array from the JSON object, iterates over it, maps each JSON object to a card object, and adds it to the deck.
     * It returns the deck of cards.
     *
     * @param DeckType The class of the cards to put in the deck.
     * @param filePath The path to the JSON file.
     * @return An ArrayList of cards of the desired type.
     * @throws IllegalArgumentException If DeckType is null.
     * @throws IOException If there is an error reading the JSON file.
     */
    public static ArrayList<? extends Card> createDeckFromJson(Class<? extends Card> DeckType, String filePath) throws IllegalArgumentException, IOException {
        if (DeckType == null) {
            throw new IllegalArgumentException("DeckType cannot be null");
        }
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(JsonParser.getStreamFromPath(filePath)), JsonObject.class);
            switch (DeckType.getSimpleName()) {
                case "InitialCard" -> {
                    ArrayList<InitialCard> deck = new ArrayList<>();

                    JsonArray initialCardsArray = jsonObject.getAsJsonArray("InitialCards");

                    for (JsonElement jsonElement : initialCardsArray) {
                        JsonObject InitialCardJson = jsonElement.getAsJsonObject();
                        InitialCard initialCard = JsonCardsMapper.MapInitialCardFromJson(InitialCardJson);
                        deck.add(initialCard);
                    }
                    return deck;
                }
                case "ResourceCard" -> {
                    JsonArray resourceCardsJson = jsonObject.getAsJsonArray("ResourceCards");
                    ArrayList<ResourceCard> deck = new ArrayList<>();
                    for (JsonElement jsonElement : resourceCardsJson) {
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
                    return deck;
                }
                case "ObjectiveCard"->{
                    ArrayList<ObjectiveCard> deck = new ArrayList<>();

                    JsonArray dispositionCardsArray = jsonObject.getAsJsonArray("ObjectiveCards").get(0).getAsJsonObject().getAsJsonArray("DispositionObjectiveCards");
                    for (JsonElement element : dispositionCardsArray) {
                        JsonObject dispositionCardJson = element.getAsJsonObject();
                        DispositionObjectiveCard dispositionCard = JsonCardsMapper.MapDispositionObjectiveCard(dispositionCardJson);
                        deck.add(dispositionCard);
                    }

                    JsonArray symbolCardsArray = jsonObject.getAsJsonArray("ObjectiveCards").get(1).getAsJsonObject().getAsJsonArray("SymbolObjectiveCards");
                    for (JsonElement element : symbolCardsArray) {
                        JsonObject symbolCardJson = element.getAsJsonObject();
                        SymbolObjectiveCard symbolCard = JsonCardsMapper.MapSymbolObjectiveCard(symbolCardJson);
                        deck.add(symbolCard);
                    }

                    return deck;
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println(e);
        } catch (JsonIOException e) {
            System.out.println(e);
        }
        return null;
    }
}











