package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.Corner;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.Symbol;

import java.util.ArrayList;
import java.util.List;

public class PrintPlayArea {
    private static final int cardHeight=7;
    private static final int cardWidth=25;
    private static final int RowDimensions=4;
    private static final int ColumnDimensions=19;

    public static int getCardHeight() {
        return cardHeight;
    }
    public static int getCardWidth() {
        return cardWidth;
    }

    /**Method that allows to choose the dimensions of the Card: Cards on PlayGround are going to have bigger dimensions, cards on other player's
     * PlayAreas are going to be smaller.*/
    public static void DrawCardCustomDimensions(String[][] matrix, int startRow, int startColumn, SideOfCard card, int Height, int Width){
        int[] rgb = GraphicUsage.getRGBColor(card.getColor());
        String ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        for (int i = 0; i < Height; i++) {
            for (int j = 0; j <Width; j++) {
                if ((i == 0 && j == Width - 1)) {
                    matrix[startRow + i][startColumn + j] =ANSIbackgroundColor +  String.valueOf('┐') + ANSIreset;
                } else if ((i == 0 || i == Height - 1) && (j > 0 && j < Width - 1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.valueOf('─') + ANSIreset;
                } else if ((i > 0 && i < Height - 1) && (j == 0 || j == Width - 1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor +  String.valueOf('│') + ANSIreset; ;
                } else if ((i == 0 && j == 0)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor +   String.valueOf('┌') + ANSIreset;
                } else if (i == Height - 1 && j == 0) {
                    matrix[startRow + i][startColumn + j] =  ANSIbackgroundColor + String.valueOf('└')+ ANSIreset;;
                } else if (i == Height - 1 && j == Width - 1) {
                    matrix[startRow + i][startColumn + j] =ANSIbackgroundColor +  String.valueOf('┘') + ANSIreset;
                }
            }
        }
    }


    public static void DrawGraphicPlayAreaCustomDimensions(PlayArea playArea, int rowDimensions, int columnDimensions){
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
                        DrawCardCustomDimensions(playAreaMatrix, startRow, startColumn, playArea.getCardInPosition(cardRowIndex, cardColumnIndex), rowDimensions+2, columnDimensions+4);
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

    public static void DrawCardDeFaultDimensions(String[][] matrix, int startRow, int startColumn, SideOfCard card) {
        int[] rgb = GraphicUsage.getRGBColor(card.getColor());
        String ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        for (int i = 0; i < cardHeight; i++) {
            for (int j = 0; j < cardWidth; j++) {
//                else if ((i == 2 && j == 2)) {
//                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.format( GraphicUsage.symbolDictionary.get(card.getCornerInPosition(CornerPosition.TOPLEFT).getSymbol())) + ANSIreset;
//                } else if (i == 2 && j == cardWidth - 5) {
//                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.format( GraphicUsage.symbolDictionary.get(card.getCornerInPosition(CornerPosition.TOPRIGHT).getSymbol())) + ANSIreset;
//                } else if (i == cardHeight - 3 && j == 2) {
//                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.format( GraphicUsage.symbolDictionary.get(card.getCornerInPosition(CornerPosition.BOTTOMLEFT).getSymbol())) + ANSIreset;
//                } else if (i == cardHeight - 3 && j == cardWidth - 5) {
//                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.format( GraphicUsage.symbolDictionary.get(card.getCornerInPosition(CornerPosition.BOTTOMRIGHT).getSymbol())) + ANSIreset;
//                } else
               if ((i == 0 || i == cardHeight - 1) && (j > 0 && j < cardWidth - 1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.valueOf('─') + ANSIreset;
                } else if ((i > 0 && i < cardHeight - 1) && (j == 0 || j == cardWidth - 1)) {
                    matrix[startRow + i][startColumn + j] = ANSIbackgroundColor + String.valueOf('│') + ANSIreset;
                } else if (i == 0 && j == cardWidth - 1) {
                    matrix[startRow + i][startColumn + j] = String.valueOf('┐');
                } else if ((i == 0 && j == 0)) {
                    matrix[startRow + i][startColumn + j] = String.valueOf('┌');
                } else if (i == cardHeight - 1 && j == 0) {
                    matrix[startRow + i][startColumn + j] = String.valueOf('└');
                } else if (i == cardHeight - 1 && j == cardWidth - 1) {
                    matrix[startRow + i][startColumn + j] = String.valueOf('┘');
                }
//               else if(i!=2 &&  i!=(cardHeight-2) && (j==cardWidth-2|| j==1)) {
//                    matrix[startRow+i][ j]=("  ");}
               else {
                    matrix[startRow + i][startColumn + j] = " " ;
                    }
            }
        }
    }

    private static Corner[][] generateCorners() {
        Corner[][] corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners[i][j] = new Corner(Symbol.FUNGI, false);
            }
        }
        return corners;
    }

    public static void DrawGraphicPlayArea(PlayArea playArea){
        int rows=playArea.getCardsOnArea().size()*RowDimensions+10;
        int columns=playArea.getCardsOnArea().getFirst().size()*ColumnDimensions+32;
        String[][] playAreaMatrix= new String[rows][columns];
        //InitializeMatrix
        for(int i=0; i<rows;i++ )
            for(int j=0; j<columns; j++)
                playAreaMatrix[i][j]=" ";
        //Add Cards
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns - 1; j++) {
                int cardRowIndex = i / RowDimensions;
                int cardColumnIndex = j / ColumnDimensions;
                int startRow = cardRowIndex * RowDimensions;
                int startColumn = cardColumnIndex * ColumnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() &&
                        cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        DrawCardDeFaultDimensions(playAreaMatrix, startRow, startColumn, playArea.getCardInPosition(cardRowIndex, cardColumnIndex));
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

    public static void main(String[] args) {
        List<List<SideOfCard>> cardsOnArea= new ArrayList<>();
        List<SideOfCard> row1 = new ArrayList<>();
        SideOfCard card1= new SideOfCard(null, generateCorners());
        card1.setColor(CardColors.GREEN);
        card1.setPositionOnArea(new Pair<>(0, 0));
        row1.add(card1);
        row1.add(null);
        row1.add(card1);
        cardsOnArea.add(row1);
        List<SideOfCard> row2 = new ArrayList<>();
        SideOfCard card2= new SideOfCard(null, generateCorners());
        card1.setColor(CardColors.RED);
        row2.add(null);
        card2.setPositionOnArea(new Pair<>(1, 0));
        row2.add(card2);
        row2.add(null);
        cardsOnArea.add(row2);
        List<SideOfCard> row3 = new ArrayList<>();
        SideOfCard card3= new SideOfCard(null, generateCorners());
        card3.setColor(CardColors.PURPLE);
        row3.add(card3);
        row3.add(null);
        row3.add(null);
        cardsOnArea.add(row3);
        PlayArea playArea=new PlayArea();
        playArea.setCardsOnArea(cardsOnArea);

        PrintPlayArea.DrawGraphicPlayArea(playArea);
        PrintPlayArea.DrawGraphicPlayAreaCustomDimensions(playArea,3, 15);
    }




}
