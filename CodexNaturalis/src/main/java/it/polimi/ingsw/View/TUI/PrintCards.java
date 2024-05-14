package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.DispositionObjectiveCard;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Position;

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
        PrintPlayArea.DrawCardDeFaultDimensions(maxiMatrix, 1, 0, card.getFront());
        PrintPlayArea.DrawCardDeFaultDimensions(maxiMatrix, 1, j + 10, card.getBack());
        for (int k = 0; k < i + 1; k++) {
            for (int l = 0; l < j * 3 + 1; l++) {
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
        PrintPlayArea.DrawCardCustomDimensions(matrix, 6, 0,new SideOfCard(null, null),  10,20);

        SideOfCard centralCard=new SideOfCard(null,null);
        centralCard.setColor(color);
        PrintPlayArea.DrawCardCustomDimensions(matrix,10,6,centralCard,3,8);
        for(Position p: neighbors.keySet()){
            SideOfCard side=new SideOfCard(null,null);
            side.setColor(neighbors.get(p));
            PrintPlayArea.DrawCardCustomDimensions(matrix,getStartingRow(p),getStaringColumn(p),side,3,8);


        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
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

    public static void printSymbolObjectiveCard(ObjectiveCard card){
        //creazione carta normale, gestione emoji
    }

    public static void main(String[] args){
        Map<Position, CardColors> neighbors=new HashMap<>();
        neighbors.put(CornerPosition.BOTTOMLEFT, CardColors.BLUE);
        neighbors.put(UpDownPosition.UP, CardColors.PURPLE);
        neighbors.put(CornerPosition.TOPRIGHT, CardColors.BLUE);
        neighbors.put(UpDownPosition.DOWN, CardColors.RED);
        DispositionObjectiveCard card=new DispositionObjectiveCard(1, ObjectivePoints.THREE,CardColors.GREEN, neighbors);
        printDispositionCard(card);

    }
}
