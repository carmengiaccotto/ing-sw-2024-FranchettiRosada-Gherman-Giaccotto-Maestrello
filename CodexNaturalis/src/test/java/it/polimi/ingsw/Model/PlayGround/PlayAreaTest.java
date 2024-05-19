package it.polimi.ingsw.Model.PlayGround;

import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayAreaTest {
    PlayArea playArea=new PlayArea();



        @Test
        void getNumSymbols() { //Test passed. The number of symbols is correctly returned
            playArea.getSymbols().put(Symbol.ANIMAL, 1);
            Assertions.assertEquals(1, playArea.getNumSymbols(Symbol.ANIMAL));
            playArea.getSymbols().put(Symbol.INKWELL, 2);
            Assertions.assertEquals(2, playArea.getNumSymbols(Symbol.INKWELL));
            playArea.getSymbols().put(Symbol.FUNGI, 1);
            Assertions.assertEquals(1, playArea.getNumSymbols(Symbol.FUNGI));

        }


        @Test
        void initializeSymbolMap() { //Test passed. All symbols are initialized with 0
            for(Symbol symbol: Symbol.values())
                Assertions.assertEquals(0, playArea.getNumSymbols(symbol));
        }
        @Test
        void addInitialCardOnArea() { //Test passed. CardsOnArea now has a new card in the center, and has 3x3 dimensions
            //Initial Card
            Corner coner1= new Corner(null, false); //corner1 empty
            Corner coner2= new Corner(Symbol.ANIMAL, false); //corner2
            Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
            Corner corner4= new Corner(null, true); //corner4 hidden
            Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
            HashMap<Symbol, Integer> symbols = new HashMap<>();
            symbols.put(Symbol.ANIMAL, 1);
            symbols.put(Symbol.INKWELL, 1);

            SideOfCard card = new SideOfCard(symbols, corners);

            playArea.addInitialCardOnArea(card);

            // Assert
            Assertions.assertEquals(card, playArea.getCardsOnArea().get(1).get(1)); // Check if the card is at the center
            assertNull(playArea.getCardsOnArea().get(0).get(0)); // Check if the corners are null
            assertNull(playArea.getCardsOnArea().get(0).get(2));
            assertNull(playArea.getCardsOnArea().get(2).get(0));
            assertNull(playArea.getCardsOnArea().get(2).get(2));

        }



        @Test
        void columnExists() {
            Corner coner1= new Corner(null, false); //corner1 empty
            Corner coner2= new Corner(Symbol.ANIMAL, false); //corner2
            Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
            Corner corner4= new Corner(null, true); //corner4 hidden
            Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
            HashMap<Symbol, Integer> symbols = new HashMap<>();
            symbols.put(Symbol.ANIMAL, 1);
            symbols.put(Symbol.INKWELL, 1);

            SideOfCard card = new SideOfCard(symbols, corners);
            playArea.addInitialCardOnArea(card);
            Assertions.assertTrue(playArea.columnExists(0));
            Assertions.assertTrue(playArea.columnExists(1));
            Assertions.assertTrue(playArea.columnExists(2));
            Assertions.assertFalse(playArea.columnExists(3));
        }

        @Test
        void rowExists() {
            Corner coner1= new Corner(null, false); //corner1 empty
            Corner coner2= new Corner(Symbol.ANIMAL, false); //corner2
            Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
            Corner corner4= new Corner(null, true); //corner4 hidden
            Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
            HashMap<Symbol, Integer> symbols = new HashMap<>();
            symbols.put(Symbol.ANIMAL, 1);
            symbols.put(Symbol.INKWELL, 1);

            SideOfCard card = new SideOfCard(symbols, corners);
            playArea.addInitialCardOnArea(card);
            Assertions.assertTrue(playArea.rowExists(0));
            Assertions.assertTrue(playArea.rowExists(1));
            Assertions.assertTrue(playArea.rowExists(2));
            Assertions.assertFalse(playArea.rowExists(3));
        }


    @Test
    void getCardInPosition() {//TODO

    }

    @Test
    void getCardInPositionTest(){
        Corner coner1= new Corner(null, false); //corner1 empty
        Corner coner2= new Corner(Symbol.ANIMAL, false); //corner2
        Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
        Corner corner4= new Corner(null, true); //corner4 hidden
        Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        symbols.put(Symbol.ANIMAL, 1);
        symbols.put(Symbol.INKWELL, 1);

        SideOfCard card = new SideOfCard(symbols, corners);

        playArea.addInitialCardOnArea(card);

        Assertions.assertEquals(card, playArea.getCardInPosition(1,1));
        Assertions.assertNull(playArea.getCardInPosition(0,0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            playArea.getCardInPosition(3, 0);
        });

    }

    @Test
    void addCardOnAreaEdgeCasePosition(){
        Corner coner1= new Corner(Symbol.ANIMAL, false); //corner1 empty
        Corner coner2= new Corner(null, false); //corner2
        Corner coner3= new Corner(Symbol.INKWELL, false); //corner3
        Corner corner4= new Corner(null, true); //corner4 hidden
        Corner[][] corners = {{coner1, coner2}, {coner3, corner4}}; //corners of the card
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        symbols.put(Symbol.ANIMAL, 1);
        symbols.put(Symbol.INKWELL, 1);

        SideOfCard card = new SideOfCard(symbols, corners);

        playArea.addInitialCardOnArea(card); //setUp playArea with initial card


        //creation of the new card to be added
        Corner coner5= new Corner(null, false); //corner1 empty
        Corner coner6= new Corner(Symbol.ANIMAL, false); //corner2
        Corner coner7= new Corner(Symbol.INKWELL, false); //corner3
        Corner corner8= new Corner(null, true); //corner4 hidden
        Corner[][] corners1 = {{coner5, coner6}, {coner7, corner8}}; //corners of the card
        HashMap<Symbol, Integer> symbols1 = new HashMap<>();
        symbols1.put(Symbol.ANIMAL, 1);
        symbols1.put(Symbol.INKWELL, 1);

        SideOfCard card1=new SideOfCard(symbols1, corners1);

        playArea.addCardOnArea(card1, 0,0);
        //add new card in position (0,0), covering corner1

        //Assertions.assertEquals(card1, playArea.getCardInPosition(0,0));//check if the card is correctly placed
        Assertions.assertEquals(card.getCornerInPosition(CornerPosition.TOPLEFT),card1.getCornerInPosition(CornerPosition.BOTTOMRIGHT).getNextCorner());
//        //check if the cornersNext has been correctly set
        Assertions.assertEquals(card1.getCornerInPosition(CornerPosition.BOTTOMRIGHT), card.getCornerInPosition(CornerPosition.TOPLEFT).getNextCorner());
//        //check if the dimensions of the Matrix have been correctly modified
        Assertions.assertEquals(4, playArea.getCardsOnArea().size());//correct number of rows
        Assertions.assertEquals(4, playArea.getCardsOnArea().get(2).size());// correct number of columns
        //check if the symbols map has been correctly modified
        //Assertions.assertEquals(1,playArea.getSymbols().get(Symbol.ANIMAL) );// the animal symbol in top left corner has been covered
        Assertions.assertEquals(2, playArea.getSymbols().get(Symbol.INKWELL));// this symbol just has to be added,it did not get covered



    }

}