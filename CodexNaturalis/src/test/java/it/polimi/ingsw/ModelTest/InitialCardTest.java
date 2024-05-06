package CodexNaturalis.src.test.java.it.polimi.ingsw.ModelTest;

class InitialCardTest {
//    @Test
//    public void testMapFromJson() {
//        JsonObject jsonObject = new JsonObject();
//        HashMap<Symbol, Integer> jsonMap=new HashMap<>();
//        jsonMap.put(Symbol.ANIMAL,2);
//        jsonMap.put(Symbol.INKWELL,3);
//        JsonObject FrontjsonObject = new JsonObject();
//        JsonObject JsonSymbol=new JsonObject();
//        for (Map.Entry<Symbol, Integer> entry : jsonMap.entrySet()) {
//            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
//        }
//        FrontjsonObject.add("symbols", JsonSymbol);
//        Corner [][] corners= new Corner[2][2];
//        corners[0][0]=new Corner(Symbol.ANIMAL, false);
//        corners[0][1]=new Corner(Symbol.INKWELL, false);
//        corners[1][0]=new Corner(Symbol.INKWELL, false);
//        corners[1][1]=new Corner(Symbol.INKWELL, false);
//
//        jsonObject.addProperty("id", 2);
//
//        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
//        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
//        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
//        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());
//        jsonObject.add("front", FrontjsonObject);
//
//        JsonObject BackjsonObject = new JsonObject();
//        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
//        BackJsonMap.put(Symbol.FUNGI,2);
//        Corner [][] Backcorners= new Corner[2][2];
//        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
//        Backcorners[0][1]=new Corner(null, true);
//        Backcorners[1][0]=new Corner(null, false);
//        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
//        for (Corner[] cornerRow: Backcorners)
//            for (Corner corner: cornerRow){
//                if (corner.getSymbol() != null) {
//                    BackjsonObject.addProperty("symbol", corner.getSymbol().toString());
//                } else {
//                    if (corner.isHidden()) {
//                        BackjsonObject.addProperty("corner", "HIDDEN");
//                    } else {
//                        BackjsonObject.addProperty("corner", "EMPTY");
//                    }
//                }
//
//            }
//        jsonObject.add("back", BackjsonObject);
//
//        InitialCard initialCard = new InitialCard(0, null, null,null);
//        initialCard = initialCard.mapFromJson(jsonObject);
//        assertNotNull(initialCard);
//        assertNotNull(initialCard.getFront().getSymbols());
//        Assertions.assertEquals(jsonMap, initialCard.getFront().getSymbols());
//        Assertions.assertEquals(corners[1][0].getSymbol(), initialCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
//        Assertions.assertEquals(corners[1][0].isHidden(), initialCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
//    }

}