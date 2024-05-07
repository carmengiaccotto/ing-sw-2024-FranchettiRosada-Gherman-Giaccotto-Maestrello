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
        if (DeckType != ObjectiveCard.class) {
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
                }
            }
        } else {
            ArrayList<ObjectiveCard> deck = new ArrayList<>();
            String filePathD = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/JsonHandler/DispositionObjectiveCard.Json";
            try (FileReader fileReaderD = new FileReader(filePathD)) {
                Gson gsonD = new Gson();
                JsonObject jsonObject1 = gsonD.fromJson(fileReaderD, JsonObject.class);

                JsonArray dispositionCardsArray = jsonObject1.getAsJsonArray("DispositionObjectiveCards");
                for (JsonElement jsonElement : dispositionCardsArray) {
                    JsonObject DispositionCardJson = jsonElement.getAsJsonObject();
                    DispositionObjectiveCard dispositionCard = JsonCardsMapper.MapDispositionObjectiveCard(DispositionCardJson);
                    deck.add(dispositionCard);
                }
                String filePathS = "CodexNaturalis/src/main/java/it/polimi/ingsw/model/JsonHandler/SymbolObjectiveCard.Json";
                try (FileReader fileReaderS = new FileReader(filePathS)) {
                    Gson gsonS = new Gson();
                    JsonObject jsonObject2 = gsonS.fromJson(fileReaderS, JsonObject.class);
                    JsonArray symbolCardsArray = jsonObject2.getAsJsonArray("SymbolObjectiveCards");
                    for (JsonElement jsonElement : symbolCardsArray) {
                        JsonObject SymbolCardJson = jsonElement.getAsJsonObject();
                        SymbolObjectiveCard symbolCard = JsonCardsMapper.MapSymbolObjectiveCard(SymbolCardJson);
                        deck.add(symbolCard);
                    }

                }

            }
            return deck;
        }
        throw  new IllegalArgumentException("InvalidDeck");
    }
}





