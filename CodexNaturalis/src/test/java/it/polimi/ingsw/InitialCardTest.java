package CodexNaturalis.src.test.java.it.polimi.ingsw;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.InitialCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InitialCardTest {
    @Test
    public void testMapFromJson() {
        JsonObject jsonObject = new JsonObject();
        HashMap<Symbol, Integer> jsonMap=new HashMap<>();
        jsonMap.put(Symbol.ANIMAL,2);
        jsonMap.put(Symbol.INKWELL,3);
        Corner [][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);
        JsonObject FrontjsonObject = new JsonObject();
        jsonObject.addProperty("id", 2);
        FrontjsonObject.addProperty("symbols", String.valueOf(jsonMap));
        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());
        jsonObject.add("front", FrontjsonObject);

        JsonObject BackjsonObject = new JsonObject();
        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
        jsonMap.put(Symbol.FUNGI,2);
        Corner [][] Backcorners= new Corner[2][2];
        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
        Backcorners[0][1]=new Corner(null, true);
        Backcorners[1][0]=new Corner(null, false);
        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
        jsonObject.add("back", BackjsonObject);

        InitialCard initialCard = new InitialCard(0, null, null,null);
        initialCard = initialCard.mapFromJson(jsonObject);
        assertNotNull(initialCard);
        Assertions.assertEquals(jsonMap, initialCard.getFront().getSymbols());
        //Assertions.assertEquals(corners[0][0], initialCard.getCornerInPosition(CornerPosition.TOPLEFT));
    }

}