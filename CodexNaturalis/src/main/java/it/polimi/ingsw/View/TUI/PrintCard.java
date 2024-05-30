package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.Symbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**Support class used for designing the cards in order to have TUI class cleaner and easier to read*/
public class PrintCard {
    private static final int cardHeight=7;
    private static final int cardWidth=25;
    private static final int RowDimensions=4;
    private static final int ColumnDimensions=19;

    public static void DrawCardDefaultDimensions(String[][] matrix, int startRow, int startColumn, SideOfCard card) {
        int[] rgb = GraphicUsage.getRGBColor(card.getColor());
        String ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";
        Corner currentCorner= null;

        for (int i = 0; i < cardHeight; i++) {
            for (int j = 0; j < cardWidth; j++) {
                if (i == 1 && j == 1) {

                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + representCorner(card.getCornerInPosition(CornerPosition.TOPLEFT)) + ANSIreset;
                } else if (i == 1 && j == cardWidth-2) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + representCorner(card.getCornerInPosition(CornerPosition.TOPRIGHT)) + ANSIreset;
                } else if (i == cardHeight-2 && j == 1) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + representCorner(card.getCornerInPosition(CornerPosition.BOTTOMLEFT)) + ANSIreset;
                }  else if (i == cardHeight-2 && j == cardWidth-2) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + representCorner(card.getCornerInPosition(CornerPosition.BOTTOMRIGHT)) + ANSIreset;
                }
                else if (i == 0 && j == cardWidth-1) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor +  String.valueOf('┐')+ ANSIreset;
                } else if ((i == 0 && j == 0)) {
                    matrix[startRow + i][startColumn + j] =  ANSIbackgroundColor + String.valueOf('┌')+ ANSIreset;
                }
                else if ((i == cardHeight-1) && (j == cardWidth-1))
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor +String.valueOf('┘')+ ANSIreset;
                else if (i == cardHeight-1 && j == 0) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor +String.valueOf('└')+ ANSIreset;
                }

                else if ((i == 0 || i == cardHeight-1) && (j > 0 && j <cardWidth-1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.valueOf('─' )+ ANSIreset;
                }
                else if ((i > 0 && i < cardWidth-1) && (j == 0 || j == cardWidth-1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.valueOf('│') + ANSIreset;
                }
//                else {
//                    matrix[startRow + i][startColumn + j] = " " ;
//                }
            }
        }
//        for (int x=0; x<cardHeight; x++) {
//            for (int y=0; y<cardWidth; y++) {
//                System.out.print(matrix[x][y]);
//            }
//            System.out.println();
//        }
    }



    public static String representCorner(Corner corner) {
        if (corner.isHidden() || corner.isCovered()) {
            return " ";
        }
        else {
            if (corner.getSymbol() != null) {
                return GraphicUsage.symbolDictionary.get(corner.getSymbol());
            }
            else return "─" ;
        }
    }



//    public  static void showCommonCards(ArrayList<ResourceCard> cards, ArrayList<GoldCard> goldCards, Deck resourceDeck, Deck goldDeck) {
//        String[][] playGroundCards = new String[20][140]; //To adjust if we want the dimensions of the cards to be bigger
//        // fill the playGroundCards with empty spaces
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 140; j++) {
//                playGroundCards[i][j] = " ";
//            }
//        }
//        //Add caption
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 140; j++) {
//                if (i==0 && j==7){
//                    playGroundCards[i][j] = "RESOURCE CARDS";
//                }
//                if (i==0 && j==27){
//                    playGroundCards[i][j] = "GOLD CARDS";
//                }
//
//                if((i==1 && j==0) || (i==1 && j==30)){
//                    playGroundCards[i][j] = "[1]";
//                }
//                if((i==10 && j==0) || (i==10 && j==30)){
//                    playGroundCards[i][j] = "[2]";
//                }
//                if(i==1 && j==72){
//                    playGroundCards[i][j] = "RESOURCE DECK";
//                }
//                if (i==10 && j==74){
//                    playGroundCards[i][j] = "GOLD DECK";
//                }
//
//            }
//        }
//        //AddCards
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 140; j++) {
//                if (i==2 && j==3)
//                    PrintCard.DrawCardDefaultDimensions(playGroundCards, i, j, cards.get(0).getFront());
//                if (i==2 && j==33)
//                    PrintCard.DrawCardDefaultDimensions(playGroundCards, i, j, goldCards.get(0).getFront());
//
//                if (i==11 && j==3)
//                    PrintCard.DrawCardDefaultDimensions(playGroundCards, i, j, cards.get(1).getFront());
//                if(i==11 && j==33)
//                    PrintCard.DrawCardDefaultDimensions(playGroundCards, i, j, goldCards.get(1).getFront());
//
//                if (i==2 && j==70)
//                    PrintCard.PrintDeck(playGroundCards, resourceDeck, i, j);
//                if(i==11 && j==70)
//                    PrintCard.PrintDeck(playGroundCards, goldDeck, i, j);
//            }
//        }
//
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 140; j++) {
//                System.out.print(playGroundCards[i][j]);
//            }
//            System.out.println();
//        }
    //}


    public static void main(String[] args) throws IOException {
        String[][] matrix = new String[cardHeight][cardWidth];
        Corner corner1 = new Corner(Symbol.FUNGI, false);
        Corner corner2 = new Corner(Symbol.INKWELL, false);
        Corner corner3 = new Corner(Symbol.ANIMAL, false);
        Corner corner4 = new Corner(null, false);
        corner2.setCovered();

        Corner[][] corners= {{corner1, corner2}, {corner3, corner4}};
        SideOfCard card= new SideOfCard(null, corners);
        card.setColor(CardColors.BLUE);


        HashMap<Symbol, Integer> requirements=new HashMap<>();
        requirements.put(Symbol.ANIMAL, 2);
        requirements.put(Symbol.MANUSCRIPT, 3);

        SideOfCard card1=new SideOfCard(null, corners);
        card1.setColor(CardColors.BLUE);
        GoldCard card2=new GoldCard(1,card1, card1,CardColors.PURPLE,requirements,3);

        ResourceCard resourceCard= new ResourceCard(1, card, card, CardColors.BLUE, true);
        ArrayList<ResourceCard> resourceCards = new ArrayList<>();
        resourceCards.add(resourceCard);
        resourceCards.add(resourceCard);

        GoldCard goldCard= new GoldCard(1, card, card, CardColors.BLUE, requirements, 3);
        ArrayList<GoldCard> goldCards = new ArrayList<>();
        goldCards.add(goldCard);
        goldCards.add(goldCard);
        Deck goldDeck = new Deck(GoldCard.class);
        Deck resourceDeck = new Deck(ResourceCard.class);
        //PrintCard.showCommonCards(resourceCards, goldCards, resourceDeck, goldDeck);





    }
}
