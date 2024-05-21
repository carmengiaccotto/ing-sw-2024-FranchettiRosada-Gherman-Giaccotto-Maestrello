package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrintCards {
    public static void printCardFrontBack(PlayCard card) {
        int i = PrintPlayArea.getCardHeight();
        int j = PrintPlayArea.getCardWidth();
        String[][] maxiMatrix = new String[i * 2 + 1][j * 4 + 1];
        for (int k = 0; k < maxiMatrix.length; k++) {
            for (int l = 0; l < maxiMatrix[k].length; l++) {
                maxiMatrix[k][l] = " ";
            }
        }

        maxiMatrix[0][j / 2] = "FRONT";
        maxiMatrix[0][j + j / 2 + 2] = "BACK";
        PrintCard.DrawCardDefaultDimensions(maxiMatrix, 1, 0, card.getFront());
        PrintCard.DrawCardDefaultDimensions(maxiMatrix, 1, j + 10, card.getBack());

        for (int k = 0; k < maxiMatrix.length; k++) {
            for (int l = 0; l < maxiMatrix[k].length; l++) {
                System.out.print(maxiMatrix[k][l]);
            }
            System.out.println();
        }



    }



    public static int getStartingRow(Position p){
        if(p==CornerPosition.TOPLEFT){
            return 9;
        }
        if(p==CornerPosition.TOPRIGHT){
            return 9;
        }
        if(p== UpDownPosition.UP){
            return 7;
        }
        if(p==CornerPosition.BOTTOMLEFT){
            return 11;
        }
        if(p==CornerPosition.BOTTOMRIGHT){
            return 11;}
        if(p==UpDownPosition.DOWN){
            return 13;
        }
        return -1;
    }


    public static void printDispositionCard(DispositionObjectiveCard card){
        CardColors color=card.getCentralCardColor();
        Map<Position, CardColors> neighbors=card.getNeighbors();
        //Creation of the big matrix
        String[][] matrix=new String[20][30];
        for(int i=0; i<20; i++){
            for(int j=0; j<30; j++){
                matrix[i][j]=" ";
            }
        }
        PrintCard.DrawCardDefaultDimensions(matrix, 6, 0,new SideOfCard(null, null));

        SideOfCard centralCard=new SideOfCard(null,null);
        centralCard.setColor(color);
        PrintPlayArea.DrawCardCustomDimensions(matrix,10,6,centralCard,3,8);
        for(Position p: neighbors.keySet()){
            SideOfCard side=new SideOfCard(null,null);
            side.setColor(neighbors.get(p));
            PrintPlayArea.DrawCardCustomDimensions(matrix,getStartingRow(p),getStaringColumn(p),side,3,8);


        }



    }

    private static int getStaringColumn(Position p) {
        if(p==CornerPosition.TOPLEFT){
            return 1;
        }
        if(p==CornerPosition.TOPRIGHT){
            return 11;
        }
        if(p== UpDownPosition.UP){
            return  6;
        }
        if(p==CornerPosition.BOTTOMLEFT){
            return 1;
        }
        if(p==CornerPosition.BOTTOMRIGHT){
            return 11;}
        if(p==UpDownPosition.DOWN){
            return 6;
        }
        return -1;
    }

    public static void printSymbolObjectiveCard(SymbolObjectiveCard card){
        Map<Symbol, Integer> objectives=card.getGoal();
        ArrayList<Symbol> goalList=new ArrayList<>();
        for(Symbol s: objectives.keySet()){
            for(int i=0; i<objectives.get(s); i++){
                goalList.add(s);
            }
        }

        String graphic_card= "┌──────┬────┬──────┐\n"+
                "│      └─"+card.getPoints().getValue()+"──┘      │\n"+
                "│    "+GraphicUsage.symbolDictionary.get(goalList.get(0))+"    "+GraphicUsage.symbolDictionary.get(goalList.get(1))+"     │\n"+
                "│       "+GraphicUsage.symbolDictionary.get(goalList.get(1))+"\u2004"+"        │\n"+
                "└──────────────────┘";

        System.out.print(graphic_card);
    }

    public static void printGoldGardPlayGround(GoldCard card){
        Map<Symbol, Integer> requirements=card.getRequirement(Side.FRONT);

        int i=PrintPlayArea.getCardHeight();
        int j=PrintPlayArea.getCardWidth();
        String[][] matrix=new String[i+i/2][j+j/2];

        // Inizializza tutti gli elementi della matrice con una stringa vuota
        for(int h=0; h<matrix.length; h++){
            for(int x=0; x<matrix[h].length; x++){
                matrix[h][x]=" ";
            }
        }

        int symbolIndex = 1;
        for(Symbol s: requirements.keySet()){
            matrix[0][symbolIndex] = requirements.get(s)+ ""+  GraphicUsage.symbolDictionary.get(s);
            symbolIndex += 4;
        }

        PrintCard.DrawCardDefaultDimensions(matrix,1,1,card.getFront());
//        for (int h = 0; h < matrix.length; h++) {
//            for (int x = 0; x < matrix[h].length; x++) {
//                System.out.print(matrix[h][x]);
//            }
//            System.out.println();
//        }
    }

    public static void main(String[] args){
        Map<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMLEFT, CardColors.BLUE);
        neighbors.put(UpDownPosition.UP, CardColors.PURPLE);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.BLUE);
        neighbors.put(UpDownPosition.DOWN, CardColors.RED);
        DispositionObjectiveCard card=new DispositionObjectiveCard(1, ObjectivePoints.THREE,CardColors.GREEN, neighbors);
        //printDispositionCard(card);

        HashMap<Symbol, Integer> requirements=new HashMap<>();
        requirements.put(Symbol.ANIMAL, 2);
        requirements.put(Symbol.MANUSCRIPT, 3);
        Corner corner1 = new Corner(Symbol.FUNGI, false);
        Corner corner2 = new Corner(Symbol.INKWELL, false);
        Corner corner3 = new Corner(Symbol.ANIMAL, false);
        Corner corner4 = new Corner(Symbol.MANUSCRIPT, false);

        Corner[][] corners= {{corner1, corner2}, {corner3, corner4}};

        SideOfCard card1=new SideOfCard(null, corners);
        card1.setColor(CardColors.BLUE);
        GoldCard card2=new GoldCard(1,card1, card1,CardColors.PURPLE,requirements,3);
        //PrintCards.printGoldGardPlayGround(card2);
        //PrintCards.printCardFrontBack(card2);

        SymbolObjectiveCard card3 = new SymbolObjectiveCard(1, ObjectivePoints.THREE, requirements);
        printSymbolObjectiveCard(card3);

    }
}
