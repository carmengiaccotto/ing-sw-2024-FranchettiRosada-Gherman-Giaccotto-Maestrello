package CodexNaturalis.src.main.java.it.polimi.ingsw.model.JsonHandler;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonDeckCreator {
    private JsonCardsMapper mapper;


    /**
     * JSON General Deck Constructor
     *
     * @param DeckType class of cards we want to punt in the Deck
     * @return deck arrayList od cards on the desired type
     * JSON Card files and Card Classes have the sam name, so we use DeckType.getSimpleName() to build the Json path
     */
    public static ArrayList<? extends Card> createDeckFromJson(Class<? extends Card> DeckType) throws IllegalArgumentException, IOException {
        if (DeckType == null) {
            throw new IllegalArgumentException("DeckType cannot be null");
        }
        String filePath = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/JsonHandler/" + DeckType.getSimpleName() + ".Json";

        try (FileReader fileReader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
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
        }
        return null;
    }
}







