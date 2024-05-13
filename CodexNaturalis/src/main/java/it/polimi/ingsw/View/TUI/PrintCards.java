package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.Cards.InitialCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;

public class PrintCards {


    public static void printCardFrontBack(PlayCard card){
        int i=PrintPlayArea.getCardHeight();
        int j=PrintPlayArea.getCardWidth();
        String[][] maxiMatrix= new String[i*2 +1][j*4+1];
        for (int k = 0; k < maxiMatrix.length; k++) {
            for (int l = 0; l < maxiMatrix[k].length; l++) {
                maxiMatrix[k][l] = " ";
            }
        }

        maxiMatrix[0][j/2]="FRONT";
        maxiMatrix[0][j+j/2+2]="BACK";
        PrintPlayArea.DrawCardDeFaultDimensions(maxiMatrix,1,0,card.getFront());
        PrintPlayArea.DrawCardDeFaultDimensions(maxiMatrix,1,j+10,card.getBack());
        for(int k=0; k<i+1; k++){
            for(int l=0; l<j*3+1; l++){
                System.out.print(maxiMatrix[k][l]);
            }
            System.out.println();
        }



    }
    public static void main(String[] args){
        PrintCards.printCardFrontBack(new InitialCard(1, new SideOfCard(null, null), new SideOfCard(null, null), null));
    }
}
