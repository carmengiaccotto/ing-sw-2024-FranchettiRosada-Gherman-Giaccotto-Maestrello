package it.polimi.ingsw.Model.JsonHandler;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.Symbol;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.Model.JsonHandler.JsonCardsMapper.MapGoldCardFromJson;
import static org.junit.jupiter.api.Assertions.*;

class JsonCardsMapperTest {

    public static String reverseCornerFactory(Corner corner) {
        if (corner.getSymbol() != null) {
            return corner.getSymbol().toString();
        } else {
            if (corner.isHidden()) {
                 return "HIDDEN";
            } else {
               return "EMPTY";
            }
        }
    }

     @Test
    public void testMapFromJson() {
       JsonObject jsonObject = new JsonObject();
      int expectedId = 123;
       jsonObject.addProperty("id", expectedId);

         Card card= JsonCardsMapper.MapCardFromJson(jsonObject);
        assertEquals(expectedId, card.getIdCard());
   }

    @Test
    public void testMapPlayCardFromJson(){
        JsonObject jsonObject = new JsonObject();
        HashMap<Symbol, Integer> jsonMap=new HashMap<>();
        jsonMap.put(Symbol.ANIMAL,2);
        jsonMap.put(Symbol.INKWELL,3);
        JsonObject FrontjsonObject = new JsonObject();
        JsonObject JsonSymbol=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : jsonMap.entrySet()) {
            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("symbols", JsonSymbol);
        Corner[][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);

        jsonObject.addProperty("id", 2);

        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());
        jsonObject.add("front", FrontjsonObject);

        JsonObject BackjsonObject = new JsonObject();
        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
        BackJsonMap.put(Symbol.FUNGI,2);
        Corner [][] Backcorners= new Corner[2][2];
        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
        Backcorners[0][1]=new Corner(null, true);
        Backcorners[1][0]=new Corner(null, false);
        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
        BackjsonObject.addProperty("corner1", reverseCornerFactory(Backcorners[0][0]));
        BackjsonObject.addProperty("corner2", reverseCornerFactory(Backcorners[0][1]));
        BackjsonObject.addProperty("corner3", reverseCornerFactory(Backcorners[1][0]));
        BackjsonObject.addProperty("corner4", reverseCornerFactory(Backcorners[1][1]));
        jsonObject.add("back", BackjsonObject);

        PlayCard playCard = JsonCardsMapper.MapPlayCardFromJson(jsonObject);
        assertNotNull(playCard);
        assertNotNull(playCard.getFront().getSymbols());
        assertEquals(jsonMap, playCard.getFront().getSymbols());
        assertEquals(corners[1][0].getSymbol(), playCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(corners[1][0].isHidden(), playCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
    }

    @Test
    public void TestMapSideFromJson(){
        HashMap<Symbol, Integer> jsonMap=new HashMap<>();
        jsonMap.put(Symbol.ANIMAL,2);
        jsonMap.put(Symbol.INKWELL,3);
        JsonObject JsonSymbol=new JsonObject();
        JsonObject FrontjsonObject = new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : jsonMap.entrySet()) {
            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("symbols", JsonSymbol);
        Corner[][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);


        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());
        SideOfCard sideOfCard= JsonCardsMapper.mapSideFromJson(FrontjsonObject);
        assertEquals(jsonMap, sideOfCard.getSymbols());
        assertEquals(corners[0][0].getSymbol(), sideOfCard.getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(corners[0][0].isHidden(), sideOfCard.getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(corners[0][1].getSymbol(), sideOfCard.getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(corners[0][1].isHidden(), sideOfCard.getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(corners[1][0].getSymbol(), sideOfCard.getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(corners[1][0].isHidden(), sideOfCard.getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(corners[1][1].getSymbol(), sideOfCard.getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(corners[1][1].isHidden(), sideOfCard.getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());

    }


    @Test
    public void TestMapGoldCardFromJsonGoldCard(){
        JsonObject jsonObject = new JsonObject();
        HashMap<Symbol, Integer> frontJsonMap=new HashMap<>();
        frontJsonMap.put(Symbol.ANIMAL,2);
        frontJsonMap.put(Symbol.INKWELL,3);
        JsonObject FrontjsonObject = new JsonObject();
        JsonObject JsonSymbol=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : frontJsonMap.entrySet()) {
            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("symbols", JsonSymbol);
        Corner[][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);

        jsonObject.addProperty("id", 2);

        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());

        HashMap<Symbol, Integer> requirementsMap=new HashMap<>();
        requirementsMap.put(Symbol.ANIMAL,3);
        JsonObject jsonRequirementsMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : requirementsMap.entrySet()) {
            jsonRequirementsMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("requirement", jsonRequirementsMap);

        int point=2;
        FrontjsonObject.addProperty("point",point);

        boolean coveredCorners=false;
        FrontjsonObject.addProperty("coveredCorners",coveredCorners);

        HashMap<Symbol, Integer> goalMap=new HashMap<>();
        JsonObject jsonGoalMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : goalMap.entrySet()) {
            jsonGoalMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("goal",jsonGoalMap);



        jsonObject.add("front", FrontjsonObject);



        JsonObject BackjsonObject = new JsonObject();
        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
        BackJsonMap.put(Symbol.FUNGI,2);
        Corner [][] Backcorners= new Corner[2][2];
        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
        Backcorners[0][1]=new Corner(null, true);
        Backcorners[1][0]=new Corner(null, false);
        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
        BackjsonObject.addProperty("corner1", reverseCornerFactory(Backcorners[0][0]));
        BackjsonObject.addProperty("corner2", reverseCornerFactory(Backcorners[0][1]));
        BackjsonObject.addProperty("corner3", reverseCornerFactory(Backcorners[1][0]));
        BackjsonObject.addProperty("corner4", reverseCornerFactory(Backcorners[1][1]));
        jsonObject.add("back", BackjsonObject);

        GoldCard goldCard=MapGoldCardFromJson(jsonObject);

        //Testing Front
        assertEquals(frontJsonMap, goldCard.getFront().getSymbols());
        assertEquals(corners[0][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(corners[0][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(corners[0][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(corners[0][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(corners[1][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(corners[1][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(corners[1][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(corners[1][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertEquals(requirementsMap,goldCard.getRequirement(Side.FRONT));
        assertEquals(point, goldCard.getPoints(Side.FRONT));
        assertEquals(goldCard.getClass(), GoldCard.class);


        assertEquals(Backcorners[0][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(Backcorners[0][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(Backcorners[0][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(Backcorners[0][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(Backcorners[1][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(Backcorners[1][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(Backcorners[1][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(Backcorners[1][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertNull(goldCard.getRequirement(Side.BACK));
        assertEquals(0, goldCard.getPoints(Side.BACK));


    }

    @Test
    public void TestMapGoldCardFromJsonSymbolObjectiveCard(){
        JsonObject jsonObject = new JsonObject();
        HashMap<Symbol, Integer> frontJsonMap=new HashMap<>();
        frontJsonMap.put(Symbol.ANIMAL,2);
        frontJsonMap.put(Symbol.INKWELL,3);
        JsonObject FrontjsonObject = new JsonObject();
        JsonObject JsonSymbol=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : frontJsonMap.entrySet()) {
            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("symbols", JsonSymbol);
        Corner[][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);

        jsonObject.addProperty("id", 2);

        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());

        HashMap<Symbol, Integer> requirementsMap=new HashMap<>();
        requirementsMap.put(Symbol.ANIMAL,3);
        JsonObject jsonRequirementsMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : requirementsMap.entrySet()) {
            jsonRequirementsMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("requirement", jsonRequirementsMap);

        int point=2;
        FrontjsonObject.addProperty("point",point);

        boolean coveredCorners=false;
        FrontjsonObject.addProperty("coveredCorners",coveredCorners);

        HashMap<Symbol, Integer> goalMap=new HashMap<>();
        goalMap.put(Symbol.INKWELL,1);
        goalMap.put(Symbol.QUILL,0);
        JsonObject jsonGoalMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : goalMap.entrySet()) {
            jsonGoalMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("goal",jsonGoalMap);



        jsonObject.add("front", FrontjsonObject);



        JsonObject BackjsonObject = new JsonObject();
        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
        BackJsonMap.put(Symbol.FUNGI,2);
        Corner [][] Backcorners= new Corner[2][2];
        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
        Backcorners[0][1]=new Corner(null, true);
        Backcorners[1][0]=new Corner(null, false);
        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
        BackjsonObject.addProperty("corner1", reverseCornerFactory(Backcorners[0][0]));
        BackjsonObject.addProperty("corner2", reverseCornerFactory(Backcorners[0][1]));
        BackjsonObject.addProperty("corner3", reverseCornerFactory(Backcorners[1][0]));
        BackjsonObject.addProperty("corner4", reverseCornerFactory(Backcorners[1][1]));
        jsonObject.add("back", BackjsonObject);

        GoldCard goldCard=MapGoldCardFromJson(jsonObject);

        //Testing Front
        assertEquals(frontJsonMap, goldCard.getFront().getSymbols());
        assertEquals(corners[0][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(corners[0][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(corners[0][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(corners[0][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(corners[1][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(corners[1][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(corners[1][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(corners[1][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertEquals(requirementsMap,goldCard.getRequirement(Side.FRONT));
        assertEquals(point, goldCard.getPoints(Side.FRONT));
        assertEquals(goldCard.getClass(), PointPerVisibleSymbol.class);



        assertEquals(Backcorners[0][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(Backcorners[0][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(Backcorners[0][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(Backcorners[0][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(Backcorners[1][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(Backcorners[1][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(Backcorners[1][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(Backcorners[1][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertNull(goldCard.getRequirement(Side.BACK));
        assertEquals(0, goldCard.getPoints(Side.BACK));

    }

    @Test
    public void TestGoldCardPointPerCoveredCorner(){
        JsonObject jsonObject = new JsonObject();
        HashMap<Symbol, Integer> frontJsonMap=new HashMap<>();
        frontJsonMap.put(Symbol.ANIMAL,2);
        frontJsonMap.put(Symbol.INKWELL,3);
        JsonObject FrontjsonObject = new JsonObject();
        JsonObject JsonSymbol=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : frontJsonMap.entrySet()) {
            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("symbols", JsonSymbol);
        Corner[][] corners= new Corner[2][2];
        corners[0][0]=new Corner(Symbol.ANIMAL, false);
        corners[0][1]=new Corner(Symbol.INKWELL, false);
        corners[1][0]=new Corner(Symbol.INKWELL, false);
        corners[1][1]=new Corner(Symbol.INKWELL, false);

        jsonObject.addProperty("id", 2);

        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());

        HashMap<Symbol, Integer> requirementsMap=new HashMap<>();
        requirementsMap.put(Symbol.ANIMAL,3);
        JsonObject jsonRequirementsMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : requirementsMap.entrySet()) {
            jsonRequirementsMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("requirement", jsonRequirementsMap);

        int point=2;
        FrontjsonObject.addProperty("point",point);

        boolean coveredCorners=true;
        FrontjsonObject.addProperty("coveredCorners",coveredCorners);

        HashMap<Symbol, Integer> goalMap=new HashMap<>();
        goalMap.put(Symbol.INKWELL,1);
        goalMap.put(Symbol.QUILL,0);
        JsonObject jsonGoalMap=new JsonObject();
        for (Map.Entry<Symbol, Integer> entry : goalMap.entrySet()) {
            jsonGoalMap.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
        }
        FrontjsonObject.add("goal",jsonGoalMap);



        jsonObject.add("front", FrontjsonObject);



        JsonObject BackjsonObject = new JsonObject();
        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
        BackJsonMap.put(Symbol.FUNGI,2);
        Corner [][] Backcorners= new Corner[2][2];
        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
        Backcorners[0][1]=new Corner(null, true);
        Backcorners[1][0]=new Corner(null, false);
        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
        BackjsonObject.addProperty("corner1", reverseCornerFactory(Backcorners[0][0]));
        BackjsonObject.addProperty("corner2", reverseCornerFactory(Backcorners[0][1]));
        BackjsonObject.addProperty("corner3", reverseCornerFactory(Backcorners[1][0]));
        BackjsonObject.addProperty("corner4", reverseCornerFactory(Backcorners[1][1]));
        jsonObject.add("back", BackjsonObject);

        GoldCard goldCard=MapGoldCardFromJson(jsonObject);

        //Testing Front
        assertEquals(frontJsonMap, goldCard.getFront().getSymbols());
        assertEquals(corners[0][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(corners[0][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(corners[0][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(corners[0][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(corners[1][0].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(corners[1][0].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(corners[1][1].getSymbol(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(corners[1][1].isHidden(), goldCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertEquals(requirementsMap,goldCard.getRequirement(Side.FRONT));
        assertEquals(point, goldCard.getPoints(Side.FRONT));
        assertEquals(goldCard.getClass(), PointPerCoveredCorner.class);



        assertEquals(Backcorners[0][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
        assertEquals(Backcorners[0][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
        assertEquals(Backcorners[0][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
        assertEquals(Backcorners[0][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
        assertEquals(Backcorners[1][0].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
        assertEquals(Backcorners[1][0].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
        assertEquals(Backcorners[1][1].getSymbol(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
        assertEquals(Backcorners[1][1].isHidden(), goldCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
        assertNull(goldCard.getRequirement(Side.BACK));
        assertEquals(0, goldCard.getPoints(Side.BACK));

    }


//    @Test
//    public void TestMapSymbolObjectiveCardFromJson(){
//
//    }

}