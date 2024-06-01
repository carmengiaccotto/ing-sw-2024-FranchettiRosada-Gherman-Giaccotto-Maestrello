package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Symbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
    public static void printBackCard(String[][] matrix , PlayCard card, int startRow, int startColumn,  int height, int width){
        CardColors color=card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        String[][] Matrix= DrawGeneralOutline(height,width,matrix, startRow, startColumn, color);
        String centralChar= GraphicUsage.cardColorDictionary.get(color);
        Matrix[startRow+height/2][startColumn+width/2]=ANSIbackgroundColor+centralChar+ANSIreset;
        Matrix[startRow+height/2][startColumn+width/2-2]=ANSIbackgroundColor+"│"+ ANSIreset;
        Matrix[startRow+height/2][startColumn+width/2+2]=ANSIbackgroundColor+"│"+ANSIreset;

        for(CornerPosition position: CornerPosition.values())
            cornerOutline(Matrix,position,startRow,startColumn, color, height, width);
    }



    /**Method that prints the front of a card, with the symbols in the corners
     * @param matrix where we want to print the card
     * @param card that we want to print
     * @param startRow row where we want to start to print
     * @param startColumn column where we want to start to print*/
    public static void printFrontCard(String[][] matrix , PlayCard card, int startRow, int startColumn, int height, int width){
        SideOfCard front=card.getFront();
        CardColors color=card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";
        String[][] Matrix= DrawGeneralOutline(height,width,matrix, startRow, startColumn, color);
        for(CornerPosition position: CornerPosition.values()){
            if (!(front.getCornerInPosition(position).isHidden() || front.getCornerInPosition(position).isCovered())){
                cornerOutline(Matrix, position, startRow, startColumn, color, height, width);
                Symbol symbol=null;
                if(!front.getCornerInPosition(position).isCovered())
                    symbol=front.getCornerInPosition(position).getSymbol();

                printSymbolInCorner(Matrix, position, startRow, startColumn,symbol, height, width);
            }

        }
    }

    /**Method that draws the outline of the corner of the card.
     * @param card matrix where we want to print
     * @param startColumn column where we want to start to print
     * @param startRow row where we want to start to print
     * @param corner CornerPosition type
     * @param color of the card*/
    public static void cornerOutline(String[][] card, CornerPosition corner, int startRow, int startColumn, CardColors color, int h, int w){
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor=String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";
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
    public static  void printSymbolInCorner(String[][] card, CornerPosition corner, int startRow, int startColumn, Symbol symbol, int h, int w){
        String graphicSymbol=null;
        if (symbol==null)
            graphicSymbol=" ";
        else
            graphicSymbol=GraphicUsage.symbolDictionary.get(symbol);
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
     * @param startColumn column where we want to start to print
     * @param h height dimension of the card
     * @param w width dimension of the card*/
    public static void printResourceFront(String[][] matrix , ResourceCard card, int startRow, int startColumn, int h, int w){
        boolean points= card.getPoint(Side.FRONT);
        printFrontCard(matrix,card,startRow,startColumn,h, w );//designing a general front with corners and symbols inside it
        if (points){
            matrix[startRow][startColumn+ w/2-2]="┬";
            matrix[startRow][startColumn+ w/2+2]="┬";
            matrix[startRow+1][startColumn+w/2-2]="└";
            matrix[startRow+1][startColumn+w/2+2]="┘";
            matrix[startRow+1][startColumn+w/2-1]="─";
            matrix[startRow+1][startColumn+w/2+1]="─";
            matrix[startRow+1][startColumn+w/2]="1";
        }
    }


    /**Method that draws the front of Gold Cards, based on how the card gives points when placed
     *@param matrix where we want to print the card
     * @param card that we want to print
     * @param startRow row where we want to start to print
     * @param startColumn column where we want to start to print
     * @param h height dimension of the card
     * @param w width dimension of the card*/
    public static void printGoldFront(String[][] matrix , GoldCard card, int startRow, int startColumn, int h, int w){
        printFrontCard(matrix,card,startRow,startColumn, h, w);
        int points=card.getPoints(Side.FRONT);
        ArrayList<Symbol> requirements=new ArrayList<>();
        for (Symbol symbol: card.getRequirement(Side.FRONT).keySet()){
            for (int i=0; i<card.getRequirement(Side.FRONT).get(symbol); i++){
                requirements.add(symbol);
            }
        }
        int offset=w/2- requirements.size()/2;
        for (int i=offset; i<offset+requirements.size(); i++){
            matrix[startRow+h-3][startColumn+i]=""+GraphicUsage.symbolDictionary.get(requirements.get(i-offset));
        }

        if (card instanceof PointPerVisibleSymbol)  {
            Symbol goal = ((PointPerVisibleSymbol) card).getGoldGoal();
            matrix[startRow][startColumn+ w/2-2]="┬";
            matrix[startRow][startColumn+ w/2+3]="┬";
            matrix[startRow+1][startColumn+w/2-2]="└";
            matrix[startRow+1][startColumn+w/2+3]="┘";
            matrix[startRow+1][startColumn+w/2+3]="┘";
            matrix[startRow+1][startColumn+w/2-1]="─";
            matrix[startRow+1][startColumn+w/2+2]="─";
            matrix[startRow+1][startColumn+w/2]=""+points;
            matrix[startRow+1][startColumn+w/2+1]=GraphicUsage.symbolDictionary.get(goal);

        }
        else if(card instanceof PointPerCoveredCorner){
            matrix[startRow][startColumn+ w/2-2]="┬";
            matrix[startRow][startColumn+ w/2+3]="┬";
            matrix[startRow+1][startColumn+w/2-2]="└";
            matrix[startRow+1][startColumn+w/2+3]="┘";
            matrix[startRow+1][startColumn+w/2+3]="┘";
            matrix[startRow+1][startColumn+w/2-1]="─";
            matrix[startRow+1][startColumn+w/2+2]="─";
            matrix[startRow+1][startColumn+w/2]=""+points;
            matrix[startRow+1][startColumn+w/2+1]="■";
        }
        else{
            matrix[startRow][startColumn+ w/2-2]="┬";
            matrix[startRow][startColumn+ w/2+2]="┬";
            matrix[startRow+1][startColumn+w/2-2]="└";
            matrix[startRow+1][startColumn+w/2+2]="┘";
            matrix[startRow+1][startColumn+w/2-1]="─";
            matrix[startRow+1][startColumn+w/2+1]="─";
            matrix[startRow+1][startColumn+w/2]=""+points;

        }

    }

    /**Method that prints the SymbolObjectiveCard.
     *@param matrix where we want to print the card
     *@param card that we want to print
     *@param startRow row where we want to start to print
     *@param startColumn column where we want to start to print
     *@param h height dimension of the card
     *@param w width dimension of the card*/
    public static void printSymbolObjectiveCard(String[][] matrix, SymbolObjectiveCard card, int startRow, int startColumn, int h, int w){
        Map<Symbol, Integer> objectives=card.getGoal();
        ArrayList<Symbol> goalList=new ArrayList<>();
        for(Symbol s: objectives.keySet()){
            for(int i=0; i<objectives.get(s); i++){
                goalList.add(s);
            }
        }
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        int points= card.getPoints().getValue();
        matrix[startRow][startColumn+ w/2-3]="┬";
        matrix[startRow][startColumn+ w/2+3]="┬";
        matrix[startRow+2][startColumn+w/2-3]="└";
        matrix[startRow+2][startColumn+w/2+3]="┘";
        matrix[startRow+1][startColumn+w/2-3]="│";
        matrix[startRow+1][startColumn+w/2+3]="│";
        matrix[startRow+2][startColumn+w/2-1]="─";
        matrix[startRow+2][startColumn+w/2+1]="─";
        matrix[startRow+2][startColumn+w/2-2]="─";
        matrix[startRow+2][startColumn+w/2+2]="─";
        matrix[startRow+2][startColumn+w/2]="─";
        matrix[startRow+1][startColumn+w/2]=""+points;
        matrix[startRow+h-3][startColumn+w/2]=GraphicUsage.symbolDictionary.get(goalList.getLast());
        matrix[startRow+h-5][startColumn+w/2-3]=GraphicUsage.symbolDictionary.get(goalList.getFirst());
        matrix[startRow+h-5][startColumn+w/2+3]=GraphicUsage.symbolDictionary.get(goalList.get(1));

    }

    /**support method for the Disposition Design*/
    public static Pair<Integer, Integer> getStartingPosition(Position p){
        if(p== CornerPosition.TOPLEFT){
            return new Pair<>(-1, -4);
        }else if (p== CornerPosition.TOPRIGHT){
            return new Pair<>(-1, +4);
        }else if (p== UpDownPosition.UP){
            return new Pair<>(-2, 0);
        } else if (p==CornerPosition.BOTTOMLEFT) {
            return new Pair<>(1, -4);
        } else if (p== CornerPosition.BOTTOMRIGHT){
            return new Pair<>(1, 4);
        } else if (p== UpDownPosition.DOWN){
            return new Pair<>(2, 0);
        }
        else return new Pair<>(0, 0);

    }
    /**Method that prints the dispositionObjectiveCard.
     *@param matrix where we want to print the card
     *@param card that we want to print
     *@param startRow row where we want to start to print
     *@param startColumn column where we want to start to print
     *@param h height dimension of the card
     *@param w width dimension of the card
      */
    public static void printDispositionObjectiveCard(String[][] matrix, DispositionObjectiveCard card,int startRow, int startColumn, int h, int w){
        CardColors color=card.getCentralCardColor();
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        Map<Position, CardColors> neighbors=card.getNeighbors();
        int centralCardRow=startRow+h/2;
        int centralCardColumn=startColumn+w/2-2;
        int height=2;
        int width=6;
        DrawGeneralOutline(height, width, matrix, centralCardRow, centralCardColumn, color);
        for(Position p: neighbors.keySet()){
            Pair<Integer, Integer> position=getStartingPosition(p);
            int row=centralCardRow+position.getFirst();
            int column=centralCardColumn+position.getSecond();
            DrawGeneralOutline(height, width, matrix, row, column, neighbors.get(p));

        }
        int points=card.getPoints().getValue();
        matrix[startRow][startColumn+ w/2-2]="┬";
        matrix[startRow][startColumn+ w/2+2]="┬";
        matrix[startRow+1][startColumn+w/2-2]="└";
        matrix[startRow+1][startColumn+w/2+2]="┘";
        matrix[startRow+1][startColumn+w/2-1]="─";
        matrix[startRow+1][startColumn+w/2+1]="─";
        matrix[startRow+1][startColumn+w/2]=""+points;

    }


    /**Method that returns the central Symbols of a card, so the symbols that are no one of the corners
     * @param card sideOfCard to check
     * @return symbols that are not in the corners but are on the card*/
    public static ArrayList<Symbol> getCentralSymbols(SideOfCard card){
        ArrayList<Symbol> symbols=new ArrayList<>();
        //Adding all the symbols of the map to the arrayList
        for(Symbol symbol: card.getSymbols().keySet()){
            for(int i=0; i<card.getSymbols().get(symbol); i++){
                symbols.add(symbol);
            }
        }
        //Removing all the symbols that are in the corner
        for(CornerPosition position: CornerPosition.values()){
            if(!card.getCornerInPosition(position).isHidden() && !card.getCornerInPosition(position).isCovered()){
                symbols.remove(card.getCornerInPosition(position).getSymbol());
            }
        }
        return symbols;
    }
    /**Method that checks if a SideOfCard is a front or a back for Resource and goldCards (InitialCards are reverted).
     * @param card sideOfCard to check
     * @return true if the getCentralSymbols generated array is empty. */
    public static boolean isFrontSide(SideOfCard card){
        return getCentralSymbols(card).isEmpty();
    }


    public static void printCard(String[][] matrix, SideOfCard card, int startRow, int startColumn, int h, int w){
        if(card.getParentCard() instanceof ResourceCard){
            if(isFrontSide(card)){
                printResourceFront(matrix, (ResourceCard) card.getParentCard(), startRow, startColumn, h, w);
            }else{
                printBackCard(matrix, card.getParentCard(), startRow, startColumn, h, w);
            }
        }else if(card.getParentCard() instanceof GoldCard) {
            if (isFrontSide(card)) {
                printGoldFront(matrix, (GoldCard) card.getParentCard(), startRow, startColumn, h, w);
            } else {
                printBackCard(matrix, card.getParentCard(), startRow, startColumn, h, w);
            }
        }
    }




    public static void main(String[] args) throws IOException {
        Deck resourceDeck = new Deck(ResourceCard.class);
        Deck goldDeck = new Deck(GoldCard.class);

        ResourceCard card1= (ResourceCard) resourceDeck.getCards().getLast();
        GoldCard card2= (GoldCard) goldDeck.getCards().getFirst();
        GoldCard card3= (GoldCard) goldDeck.getCards().get(4);
        GoldCard card4= (GoldCard) goldDeck.getCards().get(7);

        String[][] playGround = new String[45][90];
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j <90; j++) {
                playGround[i][j]=" ";
            }
        }
        //printResourceFront(playGround,card1,11,0, 7, 25);
//        printGoldFront(playGround,card2,16,0, 7, 25);
//        printGoldFront(playGround,card3,0,27, 7, 25);
//        printGoldFront(playGround,card4,8,27, 7, 25);

        Deck objectiveDeck= new Deck(ObjectiveCard.class);
        //SymbolObjectiveCard card5= (SymbolObjectiveCard) objectiveDeck.getCards().get(10);
        //printSymbolObjectiveCard(playGround,card5, 0, 0, 9, 31);
//        DispositionObjectiveCard card6= (DispositionObjectiveCard) objectiveDeck.getCards().get(7);
//        printDispositionObjectiveCard(playGround,card6, 0, 0, 9, 31);
        printCard(playGround, card3.getFront(), 0, 0, 7, 25);
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j <90; j++) {
                System.out.print(playGround[i][j]);
            }
            System.out.println();
        }
    }



    }



