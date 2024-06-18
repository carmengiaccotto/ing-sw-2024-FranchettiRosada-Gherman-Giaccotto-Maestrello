package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintPlayArea {
    private static final int cardHeight=7;
    private static final int cardWidth=25;
    private static final int RowDimensions=cardHeight-3;
    private static final int ColumnDimensions=cardWidth-4;

    public static int getCardHeight() {
        return cardHeight;
    }
    public static int getCardWidth() {
        return cardWidth;
    }



    public static void DrawOthersPlayArea(PlayArea playArea){
        int rowDimensions=3;
        int columnDimensions=10;
        int rows=playArea.getCardsOnArea().size()*rowDimensions+5;
        int columns=playArea.getCardsOnArea().getFirst().size()*columnDimensions+16;
        String[][] playAreaMatrix= new String[rows][columns];
        //InitializeMatrix
        for(int i=0; i<rows;i++ )
            for(int j=0; j<columns; j++)
                playAreaMatrix[i][j]=" ";
        //Add Cards
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns - 1; j++) {
                int cardRowIndex = i / rowDimensions;
                int cardColumnIndex = j / columnDimensions;
                int startRow = cardRowIndex * rowDimensions;
                int startColumn = cardColumnIndex * columnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() &&
                        cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        DesignSupportClass.printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex),startRow, startColumn, rowDimensions+3, columnDimensions+4);
                    }
                }
            }
        }

        //Print Matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(playAreaMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void DrawMyPlayArea(PlayArea playArea){
        int rows=(playArea.getCardsOnArea().size())*(RowDimensions)+10;
        int columns=(playArea.getCardsOnArea().getFirst().size())*(ColumnDimensions)+32;
        String[][] playAreaMatrix= new String[rows][columns];
        //InitializeMatrix
        for(int i=0; i<rows;i++ )
            for(int j=0; j<columns; j++)
                playAreaMatrix[i][j]=" ";

        //Add indexes
        for(int i=0; i<rows-1;i++ )
            for(int j=0; j<columns-1; j++){
                if (i==0 && (j+ColumnDimensions/2)%ColumnDimensions==0){
                    playAreaMatrix[i][j]=String.valueOf(j/ColumnDimensions);
                } else if (j==0 && (i+RowDimensions/2)%RowDimensions==0){
                    playAreaMatrix[i][j]=String.valueOf(i/RowDimensions);

                }
            }


        //Add Cards
        for (int i = 0; i < rows-1; i++) { //leaving one row free ad the beginning plus space for index
            for (int j = 0; j < columns - 1; j++) {//leaving one column free ad the beginning plus space for index
                int cardRowIndex = i/ RowDimensions; //Equivalent of the row in the playArea
                int cardColumnIndex = j / ColumnDimensions;//Equivalent of the column in the playArea
                int startRow = (cardRowIndex) * RowDimensions;
                int startColumn = (cardColumnIndex
                ) * ColumnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() &&
                        cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        DesignSupportClass.printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex),startRow, startColumn, cardHeight, cardWidth);
                    }
                }
            }
        }

        //Print Matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(playAreaMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        List<List<SideOfCard>> cardsOnArea= new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        Deck resourceDeck = new Deck(ResourceCard.class);
        Deck goldDeck = new Deck(GoldCard.class);
        ResourceCard fullCard1= (ResourceCard) resourceDeck.getCards().get(21);
        SideOfCard card1= fullCard1.chooseSide(Side.FRONT);
        card1.setPositionOnArea(new Pair<>(0, 0));
        row1.add(card1);
        row1.add(null);
        row1.add(card1);
        cardsOnArea.add(row1);
        List<SideOfCard> row2 = new ArrayList<>();
        PlayCard fullCard2= (GoldCard) goldDeck.getCards().get(3);
        SideOfCard card2= fullCard2.chooseSide(Side.FRONT);
        card2.getCornerInPosition(CornerPosition.TOPLEFT).setCovered();
        row2.add(null);
        card2.setPositionOnArea(new Pair<>(1, 0));
        row2.add(card2);
        row2.add(null);
        cardsOnArea.add(row2);
        List<SideOfCard> row3 = new ArrayList<>();
        PlayCard fullCard3= (GoldCard) goldDeck.getCards().get(9);
        SideOfCard card3= fullCard3.chooseSide(Side.BACK);
        row3.add(card3);
        row3.add(null);
        row3.add(null);
        cardsOnArea.add(row3);
        PlayArea playArea=new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);

        //PrintPlayArea.DrawMyPlayArea(playArea);
        PrintPlayArea.DrawOthersPlayArea(playArea);
        //PrintPlayArea.DrawCardDefaultDimensions(new String[7][25], 0, 0, card1);
    }




}
