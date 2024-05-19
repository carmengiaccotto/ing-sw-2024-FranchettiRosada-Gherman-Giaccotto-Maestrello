package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ResourceCardTest {

    @Test
    public void testGetPointFront() {
        Side chosenSide = Side.FRONT;
        boolean frontPoint = true;
        ResourceCard resourceCard = new ResourceCard(2, null, null, CardColors.BLUE, frontPoint);

        assertEquals(frontPoint, resourceCard.getPoint(chosenSide));
    }

    @Test
    public void testGetPointBack() {
        // Dati di prova
        Side chosenSide = Side.BACK;
        boolean frontPoint = false;
        ResourceCard resourceCard = new ResourceCard(2, null, null, CardColors.BLUE, frontPoint);

        // Assert
        assertEquals(frontPoint, resourceCard.getPoint(chosenSide));
    }



//    @Test
//    public void testResourceCardConstructor() {
//        int id = 1;
//        SideOfCard front = new SideOfCard(null, null);
//        SideOfCard back = new SideOfCard(null, null);
//        CardColors color = CardColors.RED;
//        boolean frontPoint = true;
//        ResourceCard resourceCard = new ResourceCard(id, front, back, color, frontPoint);
//
//        // Assert
//        assertEquals(id, resourceCard.getIdCard());
//        assertEquals(front, resourceCard.getFront());
//        assertEquals(back, resourceCard.getBack());
//        assertEquals(color, resourceCard.getColor());
//        assertEquals(frontPoint, resourceCard.getPoint(Side.FRONT));
//        assertFalse( resourceCard.getPoint(Side.BACK));
//    }
//
//    @Test
//    public void testMapFromJson() {
//        JsonObject jsonObject = new JsonObject();
//        JsonObject FrontjsonObject = new JsonObject();
//        jsonObject.addProperty("id", 1);
//
//        HashMap<Symbol, Integer> jsonMap=new HashMap<>();
//        jsonMap.put(Symbol.ANIMAL,2);
//        jsonMap.put(Symbol.INKWELL,3);
//
//        JsonObject JsonSymbol=new JsonObject();
//        for (Map.Entry<Symbol, Integer> entry : jsonMap.entrySet()) {
//            JsonSymbol.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
//        }
//        FrontjsonObject.add("symbols", JsonSymbol);
//        Corner[][] corners= new Corner[2][2];
//        corners[0][0]=new Corner(Symbol.ANIMAL, false);
//        corners[0][1]=new Corner(Symbol.INKWELL, false);
//        corners[1][0]=new Corner(Symbol.INKWELL, false);
//        corners[1][1]=new Corner(Symbol.INKWELL, false);
//
//
//
//        FrontjsonObject.addProperty("corner1", corners[0][0].getSymbol().toString());
//        FrontjsonObject.addProperty("corner2", corners[0][1].getSymbol().toString());
//        FrontjsonObject.addProperty("corner3", corners[1][0].getSymbol().toString());
//        FrontjsonObject.addProperty("corner4", corners[1][1].getSymbol().toString());
//        FrontjsonObject.addProperty("point", true);
//        jsonObject.add("front", FrontjsonObject);
//
//        JsonObject BackjsonObject = new JsonObject();
//        HashMap<Symbol, Integer> BackJsonMap=new HashMap<>();
//        BackJsonMap.put(Symbol.FUNGI,2);
//        JsonObject JsonSymbol2=new JsonObject();
//        for (Map.Entry<Symbol, Integer> entry : BackJsonMap.entrySet()) {
//            JsonSymbol2.add(String.valueOf(entry.getKey()), new JsonPrimitive(entry.getValue()));
//        }
//        BackjsonObject.add("symbols", JsonSymbol2);
//        Corner [][] Backcorners= new Corner[2][2];
//        Backcorners[0][0]=new Corner(Symbol.FUNGI, false);
//        Backcorners[0][1]=new Corner(null, true);
//        Backcorners[1][0]=new Corner(null, false);
//        Backcorners[1][1]=new Corner(Symbol.FUNGI, false);
//
//
//        int n=0;
//        for (int i=0; i<=1; i++){
//            for (int j=0; j<=1; j++) {
//                n+=1;
//                if (Backcorners[i][j].getSymbol() != null) {
//                    BackjsonObject.addProperty("corner" +n, Backcorners[i][j].getSymbol().toString());
//                } else {
//                    if (Backcorners[i][j].isHidden()) {
//                        BackjsonObject.addProperty("corner"+n, "HIDDEN");
//                    } else {
//                        BackjsonObject.addProperty("corner"+n, "EMPTY");
//                    }
//                }
//
//            }
//            }
//
//
//
//
//        jsonObject.add("front", FrontjsonObject);
//        jsonObject.add("back", BackjsonObject);
//
//        ResourceCard resourceCard = new ResourceCard(0, null, null, null, false);
//        resourceCard = resourceCard.mapFromJson(jsonObject);
//        Assertions.assertNotNull(resourceCard);
//        assertEquals(1, resourceCard.getIdCard());
//        Assertions.assertEquals(jsonMap, resourceCard.getFront().getSymbols());
//        Assertions.assertEquals(BackJsonMap, resourceCard.getBack().getSymbols());
//        Assertions.assertTrue( resourceCard.getPoint(Side.FRONT));
//        Assertions.assertFalse(resourceCard.getPoint(Side.BACK));
//
//        Assertions.assertEquals(corners[0][0].getSymbol(), resourceCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
//        Assertions.assertEquals(corners[0][0].isHidden(), resourceCard.getFront().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
//        Assertions.assertEquals(corners[0][1].getSymbol(), resourceCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
//        Assertions.assertEquals(corners[0][1].isHidden(), resourceCard.getFront().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
//        Assertions.assertEquals(corners[1][0].getSymbol(), resourceCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
//        Assertions.assertEquals(corners[1][0].isHidden(), resourceCard.getFront().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
//        Assertions.assertEquals(corners[1][1].getSymbol(), resourceCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
//        Assertions.assertEquals(corners[1][1].isHidden(), resourceCard.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());
//
//
//        Assertions.assertEquals(Backcorners[0][0].getSymbol(), resourceCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).getSymbol());
//        Assertions.assertEquals(Backcorners[0][0].isHidden(), resourceCard.getBack().getCornerInPosition(CornerPosition.TOPLEFT).isHidden());
//        Assertions.assertEquals(Backcorners[0][1].getSymbol(), resourceCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol());
//        Assertions.assertEquals(Backcorners[0][1].isHidden(), resourceCard.getBack().getCornerInPosition(CornerPosition.TOPRIGHT).isHidden());
//        Assertions.assertEquals(Backcorners[1][0].getSymbol(), resourceCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol());
//        Assertions.assertEquals(Backcorners[1][0].isHidden(), resourceCard.getBack().getCornerInPosition(CornerPosition.BOTTOMLEFT).isHidden());
//        Assertions.assertEquals(Backcorners[1][1].getSymbol(), resourceCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol());
//        Assertions.assertEquals(Backcorners[1][1].isHidden(), resourceCard.getBack().getCornerInPosition(CornerPosition.BOTTOMRIGHT).isHidden());

//}





}