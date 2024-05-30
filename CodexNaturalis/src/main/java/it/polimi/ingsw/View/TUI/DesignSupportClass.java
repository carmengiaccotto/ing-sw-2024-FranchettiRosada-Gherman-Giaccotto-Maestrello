package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.GoldCard;
import it.polimi.ingsw.Model.Cards.PlayCard;
import it.polimi.ingsw.Model.Cards.ResourceCard;
import it.polimi.ingsw.Model.Cards.SideOfCard;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.Symbol;

import java.io.IOException;

public class DesignSupportClass {



    /**Support method used to design the general outline of a card inside another matrix.
     * This other matrix can be a playArea, but also a playground or a player's hand
     * Dimensions can be customized basing on the necessities
     * @return matrix that represents the outline of the card inside the bigger matrix.
     * @param height height of the card
     * @param width width of the card
     * @param startColumn column where the card starts
     * @param startRow row where the card starts
     * @param outline bigger matrix where the card is placed
     * */
    public static String[][] DrawGeneralOutline(int height, int width, String[][] outline, int startRow, int startColumn, CardColors color ) {
        String ANSIbackgroundColor =null;
        String ANSIreset = "\u001B[0m";
        if(color == null){//if color is null, the card is printed in white
            int[] rgb = {255, 255, 255};
            ANSIbackgroundColor = String.format("\u001B[48;2;%d;%d;%d;m", rgb[0], rgb[1], rgb[2]);
        }
        else{ //if the card has a color, the card is printed of the chosen color
            int[] rgb = GraphicUsage.getRGBColor(color);
            ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);

        }

        // First row
        outline[startRow][startColumn] = ANSIbackgroundColor + "┌" + ANSIreset;
        for (int i = 1; i < width - 1; i++) {
            outline[startRow][startColumn + i] = ANSIbackgroundColor + "─" + ANSIreset;
        }
        outline[startRow][startColumn + width - 1] = ANSIbackgroundColor + "┐" + ANSIreset;

        // Middle rows
        for (int i = 1; i < height - 1; i++) {
            outline[startRow + i][startColumn] = ANSIbackgroundColor + "│" + ANSIreset;
            for (int j = 1; j < width - 1; j++) {
                outline[startRow + i][startColumn + j] = ANSIbackgroundColor + " " + ANSIreset;
            }
            outline[startRow + i][startColumn + width - 1] = ANSIbackgroundColor + "│" + ANSIreset;
        }

        // Last row
        outline[startRow + height - 1][startColumn] = ANSIbackgroundColor + "└" + ANSIreset;
        for (int i = 1; i < width - 1; i++) {
            outline[startRow + height - 1][startColumn + i] = ANSIbackgroundColor + "─" + ANSIreset;
        }
        outline[startRow + height - 1][startColumn + width - 1] = ANSIbackgroundColor + "┘" + ANSIreset;

        return outline;
    }

    /**Method that prints the back of a PlayCard that is not an InitialCard (Initial cards are a little different, because the back has more symbols to it).
     * @param startColumn column where we want to start to print
     * @param startRow row where we want to start to print
     * @param card whose back we want to draw
     * @param matrix where we want to draw the card*/
    public static void printBackCard(String[][] matrix , PlayCard card, int startRow, int startColumn){
        CardColors color=card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        int height=7;
        int width=25;

        String[][] Matrix= DrawGeneralOutline(height,width,matrix, startRow, startColumn, color);
        String centralChar= GraphicUsage.cardColorDictionary.get(color);
        Matrix[startRow+height/2][startColumn+width/2]=ANSIbackgroundColor+centralChar+ANSIreset;
        Matrix[startRow+height/2][startColumn+width/2-2]=ANSIbackgroundColor+"│"+ ANSIreset;
        Matrix[startRow+height/2][startColumn+width/2+2]=ANSIbackgroundColor+"│"+ANSIreset;

        for(CornerPosition position: CornerPosition.values())
            cornerOutline(Matrix,position,startRow,startColumn, color);
    }



    /**Method that prints the front of a card, with the symbols in the corners
     * @param matrix where we want to print the card
     * @param card that we want to print
     * @param startRow row where we want to start to print
     * @param startColumn column where we want to start to print*/
    public static void printFrontCard(String[][] matrix , PlayCard card, int startRow, int startColumn){
        SideOfCard front=card.getFront();
        CardColors color=card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        int height=7;
        int width=25;
        String[][] Matrix= DrawGeneralOutline(height,width,matrix, startRow, startColumn, color);
        for(CornerPosition position: CornerPosition.values()){
            if (!front.getCornerInPosition(position).isHidden()) {
                cornerOutline(Matrix, position, startRow, startColumn, color);
                Symbol symbol=null;
                if(!front.getCornerInPosition(position).isCovered())
                    symbol=front.getCornerInPosition(position).getSymbol();

                printSymbolInCorner(Matrix, position, startRow, startColumn,symbol);
            }

        }
    }

    /**Method that draws the outline of the corner of the card.
     * @param card matrix where we want to print
     * @param startColumn column where we want to start to print
     * @param startRow row where we want to start to print
     * @param corner CornerPosition type
     * @param color of the card*/
    public static void cornerOutline(String[][] card, CornerPosition corner, int startRow, int startColumn, CardColors color){
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";
        int h= startRow+card.length;
        int w= startColumn+card[0].length;
        switch(corner){
            case CornerPosition.TOPLEFT -> {
                card[startRow][startColumn + 3] = ANSIbackgroundColor+"┬"+ANSIreset;
                card[startRow+2][startColumn]=ANSIbackgroundColor+"├"+ANSIreset;
                card[startRow+2][startColumn+3]=ANSIbackgroundColor+"┘"+ANSIreset;
                card[startRow+2][startColumn+2]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+2][startColumn+1]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+1][startColumn+3]=ANSIbackgroundColor+ "│"+ANSIreset;

            }
            case CornerPosition.BOTTOMLEFT -> {
                card[startRow+h-1][startColumn + 3] = ANSIbackgroundColor+"┴"+ANSIreset;
                card[startRow+h-3][startColumn]=ANSIbackgroundColor+"├"+ANSIreset;
                card[startRow+h-3][startColumn+3]=ANSIbackgroundColor+"┐"+ANSIreset;
                card[startRow+h-3][startColumn+1]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+h-3][startColumn+2]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+h-2][startColumn+3]=ANSIbackgroundColor+ "│"+ANSIreset;

            }
            case CornerPosition.TOPRIGHT -> {
                card[startRow][startColumn + w-4] =ANSIbackgroundColor+ "┬"+ANSIreset;
                card[startRow+2][startColumn+w-1]=ANSIbackgroundColor+"┤"+ANSIreset;
                card[startRow+2][startColumn+w-4]=ANSIbackgroundColor+"└"+ANSIreset;
                card[startRow+2][startColumn+w-2]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+2][startColumn+w-3]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+1][startColumn+w-4]= ANSIbackgroundColor+"│"+ANSIreset;
            }
            case CornerPosition.BOTTOMRIGHT -> {
                card[startRow+h-1][startColumn + w-4] =ANSIbackgroundColor+ "┴"+ANSIreset;
                card[startRow+h-3][startColumn+w-1]=ANSIbackgroundColor+"┤"+ANSIreset;
                card[startRow+h-3][startColumn+w-4]=ANSIbackgroundColor+"┌"+ANSIreset;
                card[startRow+h-3][startColumn+w-2]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+h-3][startColumn+w-3]=ANSIbackgroundColor+"─"+ANSIreset;
                card[startRow+h-2][startColumn+w-4]=ANSIbackgroundColor+ "│"+ANSIreset;

            }
        }
    }

    /**method that prints the graphic correspondent to the symbol in the corner of the card.
     *@param card matrix where we want to print
     *@param startRow row where we want to start to print
     *@param startColumn column where we want to start to print
     *@param symbol that we want to print
     *@param corner CornerPosition type
     */
    public static  void printSymbolInCorner(String[][] card, CornerPosition corner, int startRow, int startColumn, Symbol symbol){
        String graphicSymbol=null;
        if (symbol==null)
            graphicSymbol=" ";
        else
            graphicSymbol=GraphicUsage.symbolDictionary.get(symbol);
        int h= startRow+card.length;
        int w= startColumn+card[0].length;
        switch(corner){
            case TOPLEFT -> {
                card[startRow+1][startColumn+2]=graphicSymbol;
            }
            case BOTTOMLEFT -> {
                card[startRow+h-2][startColumn+2]=graphicSymbol;

            }
            case TOPRIGHT -> {
                card[startRow+1][startColumn+w-3]=graphicSymbol;
            }
            case BOTTOMRIGHT -> {
                card[startRow+h-2][startColumn+w-3]=graphicSymbol;

            }
        }

    }

    /**Method to print the front of resource cards. Uses printFrontCard method, and adds the space for points if the resource card
     * gives points.
     * @param matrix where we want to print the card
     * @param card that we want to print
     * @param startRow row where we want to start to print
     * @param startColumn column where we want to start to print*/
    public static void printResourceFront(String[][] matrix , ResourceCard card, int startRow, int startColumn){
        boolean points= card.getPoint(Side.FRONT);
        printFrontCard(matrix,card,startRow,startColumn);//designing a general front with corners and symbols inside it
        if (points){
            matrix[startRow][startColumn+ matrix[0].length/2-2]="┬";
            matrix[startRow][startColumn+ matrix[0].length/2+2]="┬";
            matrix[startRow+1][startColumn+matrix[0].length/2-2]="└";
            matrix[startRow+1][startColumn+matrix[0].length/2+2]="┘";
            matrix[startRow+1][startColumn+matrix[0].length/2-1]="─";
            matrix[startRow+1][startColumn+matrix[0].length/2+1]="─";
            matrix[startRow+1][startColumn+matrix[0].length/2]="1";
        }
    }




    public static void main(String[] args) throws IOException {
        Deck resourceDeck = new Deck(ResourceCard.class);
        Deck goldDeck = new Deck(GoldCard.class);

        ResourceCard card1= (ResourceCard) resourceDeck.getCards().getLast();
        String[][] playGround = new String[7][25];
        printResourceFront(playGround,card1,0,0);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 25; j++) {
                System.out.print(playGround[i][j]);
            }
            System.out.println();
        }
    }


    }



