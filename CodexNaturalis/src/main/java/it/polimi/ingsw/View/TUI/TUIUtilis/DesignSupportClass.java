package it.polimi.ingsw.View.TUI.TUIUtilis;

import it.polimi.ingsw.Model.Enumerations.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DesignSupportClass {

        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_COLOR_FORMAT = "\u001B[38;2;%d;%d;%dm";
        private static final String CORNER_TOP_LEFT = "┌";
        private static final String CORNER_TOP_RIGHT = "┐";
        private static final String CORNER_BOTTOM_LEFT = "└";
        private static final String CORNER_BOTTOM_RIGHT = "┘";
        private static final String HORIZONTAL_LINE = "─";
        private static final String VERTICAL_LINE = "│";
        private static final String SPACE = " ";

        private static String getAnsiColor(CardColors color) {
            int[] rgb = color == null ? new int[]{255, 255, 255} : GraphicUsage.getRGBColor(color);
            return String.format(ANSI_COLOR_FORMAT, rgb[0], rgb[1], rgb[2]);
        }

    public static Pair<Integer, Integer> getStartForCorner(CornerPosition corner, int h, int w){
        switch (corner){
            case TOPLEFT:
                return new Pair<>(h-3, w-4);
            case TOPRIGHT:
                return new Pair<>(h-3, -w+4);
            case BOTTOMLEFT:
                return new Pair<>(-h+3, w-4);
            case BOTTOMRIGHT:
                return new Pair<>(-h+3, -w+4);
            default:
                return new Pair<>(0, 0);
        }
    }

    public static String[][] drawPlayCard(String[][] matrix, SideOfCard card, int h, int w, int startRow, int startColumn){
        CardColors color = card.getColor();
        String ANSIbackgroundColor = getAnsiColor(color);
        String ANSIreset = ANSI_RESET;
        for(int i=startRow; i<startRow+h; i++){
            for(int j=startColumn; j<startColumn+w; j++){
                if((i==startRow  || (i==startRow+h-1))&&j>startColumn+3 && j<startColumn+w-4) {
                    matrix[i][j] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                }else if((j==startColumn || j==startColumn+w-1) && i>startRow+2 && i<startRow+h-3) {
                    matrix[i][j] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                }else
                    matrix[i][j] = ANSIbackgroundColor + SPACE + ANSIreset;
            }
        }
        for(Corner[] corners: card.getCorners()){
            for(Corner corner: corners){
                if(!corner.isHidden()) {
                    if (!corner.isCovered()) {
                        drawCorner(matrix, corner.getPosition(), h, w, startRow, startColumn, color, corner.getSymbol());
                    } else {
                        Pair<Integer, Integer> offset = getStartForCorner(corner.getNextCorner().getPosition(), h, w);
                        drawCorner(matrix, corner.getNextCorner().getPosition(), h, w, startRow + offset.getFirst(),
                                startColumn + offset.getSecond(), corner.getNextCorner().getParentCard().getColor(), corner.getNextCorner().getSymbol());
                    }
                }else{
                    drawHiddenOutline(matrix, corner.getPosition(), h, w, startRow, startColumn, color);
                }
            }
        }
        return matrix;
    }



    public static void drawCorner(String[][] matrix, CornerPosition corner, int h, int w, int startRow, int startColumn, CardColors color, Symbol symbol){
        String ANSIbackgroundColor = getAnsiColor(color);
        String ANSIreset = ANSI_RESET;
        String graphicSymbol;
        if(symbol!=null)
            graphicSymbol=ANSIbackgroundColor+GraphicUsage.symbolDictionary.get(symbol)+ANSIreset;
        else
            graphicSymbol=ANSIbackgroundColor+SPACE+ANSIreset;

        int[][] offsets;
        String[][] symbols;

        switch (corner) {
            case TOPLEFT:
                offsets = new int[][]{
                        {0, 3}, {1, 0}, {1, 1}, {1, 2}, {1, 3}, {2, 0}, {2, 1}, {2, 2}, {2, 3},{0, 0},{0,1},{0,2}
                };
                symbols = new String[][]{
                        {"┬"}, {VERTICAL_LINE}, {graphicSymbol}, {SPACE}, {VERTICAL_LINE},
                        {"├"}, {HORIZONTAL_LINE}, {HORIZONTAL_LINE}, {CORNER_BOTTOM_RIGHT},
                        {CORNER_TOP_LEFT},{HORIZONTAL_LINE},{HORIZONTAL_LINE}
                };
                break;
            case TOPRIGHT:
                offsets = new int[][]{
                        {0, w-4}, {1, w-4}, {1, w-3}, {1, w-2}, {1, w-1}, {2, w-4}, {2, w-3}, {2, w-2}, {2, w-1}, {0, w-1},{0, w-2},{0, w-3}
                };
                symbols = new String[][]{
                        {"┬"}, {VERTICAL_LINE}, {SPACE}, {graphicSymbol}, {VERTICAL_LINE},
                        {CORNER_BOTTOM_LEFT}, {HORIZONTAL_LINE}, {HORIZONTAL_LINE},
                        {"┤"},{CORNER_TOP_RIGHT},{HORIZONTAL_LINE},{HORIZONTAL_LINE}
                };
                break;
            case BOTTOMLEFT:
                offsets = new int[][]{
                        {h-3, 0}, {h-3, 1}, {h-3, 2}, {h-3, 3}, {h-2, 0}, {h-2, 1}, {h-2, 2}, {h-2, 3}, {h-1, 3},{h-1, 0},{h-1, 1},{h-1, 2}
                };
                symbols = new String[][]{
                        {"├"}, {HORIZONTAL_LINE}, {HORIZONTAL_LINE}, {CORNER_TOP_RIGHT},
                        {VERTICAL_LINE}, {graphicSymbol}, {SPACE}, {VERTICAL_LINE},
                        {"┴"},{CORNER_BOTTOM_LEFT},{HORIZONTAL_LINE},{HORIZONTAL_LINE}
                };
                break;
            case BOTTOMRIGHT:
                offsets = new int[][]{
                        {h-3, w-4}, {h-3, w-3}, {h-3, w-2}, {h-3, w-1}, {h-2, w-4}, {h-2, w-3}, {h-2, w-2}, {h-2, w-1}, {h-1, w-4},{h-1, w-1},{h-1, w-2},{h-1, w-3}
                };
                symbols = new String[][]{
                        {CORNER_TOP_LEFT}, {HORIZONTAL_LINE}, {HORIZONTAL_LINE},
                        {"┤"}, {VERTICAL_LINE}, {graphicSymbol}, {SPACE}, {VERTICAL_LINE},
                        {"┴"},{CORNER_BOTTOM_RIGHT},{HORIZONTAL_LINE},{HORIZONTAL_LINE}
                };
                break;
            default:
                throw new IllegalArgumentException("Invalid corner position");
        }

        for (int i = 0; i < offsets.length; i++) {
            int row = startRow + offsets[i][0];
            int col = startColumn + offsets[i][1];
            matrix[row][col] = ANSIbackgroundColor + symbols[i][0] + ANSIreset;
        }
    }

    public static void drawHiddenOutline(String[][] matrix, CornerPosition corner, int h, int w, int startRow, int startColumn, CardColors color){
        String ANSIbackgroundColor = getAnsiColor(color);
        String ANSIreset = ANSI_RESET;
        switch (corner) {
            case TOPLEFT:
                matrix[startRow][startColumn] = ANSIbackgroundColor + CORNER_TOP_LEFT + ANSIreset;
                matrix[startRow] [startColumn +1] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow][startColumn +2] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow][startColumn +3] = ANSIbackgroundColor + HORIZONTAL_LINE+ ANSIreset;

                matrix[startRow + 1][startColumn] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                matrix[startRow + 2][startColumn] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;

                break;

            case TOPRIGHT:
                matrix[startRow][startColumn +w-4] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow][startColumn + w-3] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow][startColumn + w-2] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow][startColumn + w-1] = ANSIbackgroundColor + CORNER_TOP_RIGHT + ANSIreset;

                matrix[startRow + 1][startColumn + w-1] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                matrix[startRow + 2][startColumn + w-1] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                break;
            case BOTTOMRIGHT:
                matrix[startRow+h-1][startColumn+w-4] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow+h-1][startColumn+w-3] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow+h-1][startColumn +w-2] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow+h-1][startColumn +w-1] = ANSIbackgroundColor + CORNER_BOTTOM_RIGHT + ANSIreset;

                matrix[startRow+h-2][startColumn +w-1] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                matrix[startRow+h-3][startColumn +w-1] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                break;
            case BOTTOMLEFT:
                matrix[startRow+h-1][startColumn] = ANSIbackgroundColor + CORNER_BOTTOM_LEFT + ANSIreset;
                matrix[startRow+h-1][startColumn +1] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow+h-1][startColumn +2] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;
                matrix[startRow+h-1][startColumn +3] = ANSIbackgroundColor + HORIZONTAL_LINE + ANSIreset;

                matrix[startRow+h-2][startColumn] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                matrix[startRow+h-3][startColumn] = ANSIbackgroundColor + VERTICAL_LINE + ANSIreset;
                break;


    }
        }


    public static void printBackCentral(String[][] matrix, PlayCard card, int startRow, int startColumn, int h, int w) {
        CardColors color = card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";
        String centralChar = GraphicUsage.cardColorDictionary.get(color);
        matrix[startRow + h/ 2][startColumn + w / 2] = ANSIbackgroundColor + centralChar + ANSIreset;
        matrix[startRow + h / 2][startColumn + w / 2 - 2] = ANSIbackgroundColor + "│" + ANSIreset;
        matrix[startRow + h / 2][startColumn + w / 2 + 2] = ANSIbackgroundColor + "│" + ANSIreset;
    }


    public static void printResourceCard(String[][] matrix, ResourceCard card, int startRow, int startColumn, int h, int w, Side side) {
            if(side.equals(Side.FRONT)) {
                drawPlayCard(matrix, card.getFront(), h, w, startRow, startColumn);
                int points = card.getPoints(Side.FRONT);
                if (points==1) {
                    drawPoints(matrix, startRow, startColumn, w, "1");
                }
            }
        else{
            drawPlayCard(matrix, card.getBack(), h, w, startRow, startColumn);
            printBackCentral(matrix, card, startRow, startColumn, h, w);
            }
    }

    public static void printGoldCard(String[][] matrix, GoldCard card, int startRow, int startColumn, int h, int w, Side side) {
        if(side.equals(Side.FRONT)) {
            drawPlayCard(matrix, card.getFront(), h, w, startRow, startColumn);
            int points = card.getPoints(Side.FRONT);
            ArrayList<Symbol> requirements = new ArrayList<>();
        for (Symbol symbol : card.getRequirement(Side.FRONT).keySet()) {
            for (int i = 0; i < card.getRequirement(Side.FRONT).get(symbol); i++) {
                requirements.add(symbol);
            }
        }
        int offset = w / 2 - requirements.size() / 2;
        for (int i = offset; i < offset + requirements.size(); i++) {
            matrix[startRow + h - 3][startColumn + i] = GraphicUsage.symbolDictionary.get(requirements.get(i - offset));
        }
                    if (card instanceof PointPerVisibleSymbol) {
            Symbol goal = ((PointPerVisibleSymbol) card).getGoldGoal();
            matrix[startRow][startColumn + w / 2 - 2] = "┬";
            matrix[startRow][startColumn + w / 2 + 3] = "┬";
            matrix[startRow + 1][startColumn + w / 2 - 2] = "└";
            matrix[startRow + 1][startColumn + w / 2 + 3] = "┘";
            matrix[startRow + 1][startColumn + w / 2 + 3] = "┘";
            matrix[startRow + 1][startColumn + w / 2 - 1] = "─";
            matrix[startRow + 1][startColumn + w / 2 + 2] = "─";
            matrix[startRow + 1][startColumn + w / 2] = "" + points;
            matrix[startRow + 1][startColumn + w / 2 + 1] = GraphicUsage.symbolDictionary.get(goal);

        } else if (card instanceof PointPerCoveredCorner) {
            matrix[startRow][startColumn + w / 2 - 2] = "┬";
            matrix[startRow][startColumn + w / 2 + 3] = "┬";
            matrix[startRow + 1][startColumn + w / 2 - 2] = "└";
            matrix[startRow + 1][startColumn + w / 2 + 3] = "┘";
            matrix[startRow + 1][startColumn + w / 2 + 3] = "┘";
            matrix[startRow + 1][startColumn + w / 2 - 1] = "─";
            matrix[startRow + 1][startColumn + w / 2 + 2] = "─";
            matrix[startRow + 1][startColumn + w / 2] = "" + points;
            matrix[startRow + 1][startColumn + w / 2 + 1] = "■";
        } else {
            drawPoints(matrix, startRow, startColumn, w, Integer.toString(points));
        }

        }
        else{
            drawPlayCard(matrix, card.getBack(), h, w, startRow, startColumn);
            printBackCentral(matrix, card, startRow, startColumn, h, w);
            //todo add something to show that it is gold(?)
        }
    }

    private static void drawPoints(String[][] matrix, int startRow, int startColumn, int w, String pointSymbol) {
        int midColumn = startColumn + w / 2;
        StringBuilder sb = new StringBuilder();
        sb.append("┬");
        matrix[startRow][midColumn - 2] = sb.toString();
        matrix[startRow][midColumn + 2] = sb.toString();
        sb.setLength(0);
        sb.append("└");
        matrix[startRow + 1][midColumn - 2] = sb.toString();
        sb.setLength(0);
        sb.append("┘");
        matrix[startRow + 1][midColumn + 2] = sb.toString();
        sb.setLength(0);
        sb.append("─");
        matrix[startRow + 1][midColumn - 1] = sb.toString();
        matrix[startRow + 1][midColumn + 1] = sb.toString();
        sb.setLength(0);
        sb.append(pointSymbol);
        matrix[startRow + 1][midColumn] = sb.toString();
    }


    public static String[][] DrawGeneralOutline(int h, int w, String[][] matrix, int startRow, int startColumn, CardColors color){
        String ANSIbackgroundColor = getAnsiColor(color);
        for(int i=startRow; i<startRow+h; i++){
            for(int j=startColumn; j<startColumn+w; j++){
                if(i==startRow && j==startColumn){
                    matrix[i][j] = ANSIbackgroundColor+CORNER_TOP_LEFT+ANSI_RESET;
                }else if(i==startRow && j==startColumn+w-1){
                    matrix[i][j] = ANSIbackgroundColor+CORNER_TOP_RIGHT+ANSI_RESET;
                }else if(i==startRow+h-1 && j==startColumn){
                    matrix[i][j] = ANSIbackgroundColor+CORNER_BOTTOM_LEFT+ANSI_RESET;
                }else if(i==startRow+h-1 && j==startColumn+w-1){
                    matrix[i][j] = ANSIbackgroundColor+CORNER_BOTTOM_RIGHT+ANSI_RESET;
                }else if(i==startRow || i==startRow+h-1){
                    matrix[i][j] = ANSIbackgroundColor+HORIZONTAL_LINE+ANSI_RESET;
                }else if(j==startColumn || j==startColumn+w-1){
                    matrix[i][j] =ANSIbackgroundColor+ VERTICAL_LINE+ANSI_RESET;
                }else{
                    matrix[i][j] = SPACE;
                }
            }
        }
        return matrix;

    }



    /**
     * Method that prints the SymbolObjectiveCard.
     *
     * @param card that we want to print
     */
    public static String printSymbolObjectiveCard(SymbolObjectiveCard card) {
        Map<Symbol, Integer> objectives = card.getGoal();
        ArrayList<Symbol> goalList = new ArrayList<>();
        for (Symbol s : objectives.keySet()) {
            for (int i = 0; i < objectives.get(s); i++) {
                goalList.add(s);
            }
        }
        int h = 9;
        int w = 31;
        int startRow = 0;
        int startColumn = 0;
        String[][] matrix = new String[h][w];
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        int points = card.getPoints().getValue();
        drawPoints(matrix, startRow, startColumn, w, String.valueOf(points));
        matrix[startRow + h - 3][startColumn + w / 2] = GraphicUsage.symbolDictionary.get(goalList.getLast());
        matrix[startRow + h - 5][startColumn + w / 2 - 3] = GraphicUsage.symbolDictionary.get(goalList.getFirst());
        matrix[startRow + h - 5][startColumn + w / 2 + 3] = GraphicUsage.symbolDictionary.get(goalList.get(1));

        StringBuilder sb = new StringBuilder();

        for (String[] strings : matrix) {
            for (int j = 0; j < strings.length; j++) {
                sb.append(strings[j]);
            }
            sb.append("\n");
        }

        return sb.toString();

    }

    /**
     * support method for the Disposition Design
     */
    public static Pair<Integer, Integer> getStartingPosition(Position p) {
        if (p == CornerPosition.TOPLEFT) {
            return new Pair<>(-1, -4);
        } else if (p == CornerPosition.TOPRIGHT) {
            return new Pair<>(-1, +4);
        } else if (p == UpDownPosition.UP) {
            return new Pair<>(-2, 0);
        } else if (p == CornerPosition.BOTTOMLEFT) {
            return new Pair<>(1, -4);
        } else if (p == CornerPosition.BOTTOMRIGHT) {
            return new Pair<>(1, 4);
        } else if (p == UpDownPosition.DOWN) {
            return new Pair<>(2, 0);
        } else return new Pair<>(0, 0);

    }

    /**
     * Method that prints the dispositionObjectiveCard.
     *
     * @param card that we want to print
     */
    public static String printDispositionObjectiveCard(DispositionObjectiveCard card) {
        CardColors color = card.getCentralCardColor();
        int h = 9;
        int w = 31;
        int startRow = 0;
        int startColumn = 0;
        String[][] matrix = new String[h][w];
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        Map<Position, CardColors> neighbors = card.getNeighbors();
        int centralCardRow = startRow + h / 2;
        int centralCardColumn = startColumn + w / 2 - 2;
        int height = 2;
        int width = 6;
        DrawGeneralOutline(height, width, matrix, centralCardRow, centralCardColumn, color);
        for (Position p : neighbors.keySet()) {
            Pair<Integer, Integer> position = getStartingPosition(p);
            int row = centralCardRow + position.getFirst();
            int column = centralCardColumn + position.getSecond();
            DrawGeneralOutline(height, width, matrix, row, column, neighbors.get(p));

        }
        int points = card.getPoints().getValue();
        drawPoints(matrix, startRow, startColumn, w, String.valueOf(points));


        StringBuilder sb = new StringBuilder();

        for (String[] strings : matrix) {
            for (int j = 0; j < strings.length; j++) {
                sb.append(strings[j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }


    /**
     * Method that returns the central Symbols of a card, so the symbols that are no one of the corners
     *
     * @param card sideOfCard to check
     * @return symbols that are not in the corners but are on the card
     */
    public static ArrayList<Symbol> getCentralSymbols(SideOfCard card) {
        ArrayList<Symbol> symbols = new ArrayList<>();
        //Adding all the symbols of the map to the arrayList
        for (Symbol symbol : card.getSymbols().keySet()) {
            for (int i = 0; i < card.getSymbols().get(symbol); i++) {
                symbols.add(symbol);
            }
        }
        //Removing all the symbols that are in the corner
        for (CornerPosition position : CornerPosition.values()) {
            if (!card.getCornerInPosition(position).isHidden() && !card.getCornerInPosition(position).isCovered()) {
                symbols.remove(card.getCornerInPosition(position).getSymbol());
            }
        }
        return symbols;
    }

    /**
     * Method that checks if a SideOfCard is a front or a back for Resource and goldCards (InitialCards are reverted).
     *
     * @param card sideOfCard to check
     * @return true if the getCentralSymbols generated array is empty.
     */
    public static Side isFrontSide(SideOfCard card) {
        if(card.getParentCard().getFront().equals(card))
            return Side.FRONT;
        else
            return Side.BACK;
    }


    public static void printInitialCardBack(String[][] matrix, InitialCard card, int startRow, int startColumn, int h, int w) {
        drawPlayCard(matrix, card.getBack(), h, w, startRow, startColumn);

    }

    public static void printInitialCardFront(String[][] matrix, InitialCard card, int startRow, int startColumn, int h, int w) {
        drawPlayCard(matrix, card.getFront(), h, w, startRow, startColumn);
        ArrayList<Symbol> symbols = getCentralSymbols(card.getFront());
        int offset = h / 2 - symbols.size() / 2;

        //Draw the top line of the box
        matrix[startRow + offset + symbols.size()][startColumn + w / 2] = "─";
        matrix[startRow + offset - 1][startColumn + w / 2 - 1] = "┌";
        matrix[startRow + offset - 1][startColumn + w / 2 + 1] = "┐";
        //Draw the bottom line of the box

        matrix[startRow + offset - 1][startColumn + w / 2] = "─";
        matrix[startRow + offset + symbols.size()][startColumn + w / 2 - 1] = "└";
        matrix[startRow + offset + symbols.size()][startColumn + w / 2 + 1] = "┘";


// Draw left and right lines of the box
        for (int i = offset; i < offset + symbols.size(); i++) {
            matrix[startRow + i][startColumn + w / 2 - symbols.size() / 2] = "│";
            matrix[startRow + i][startColumn + w / 2 + symbols.size() / 2] = "│";
        }

// Print symbols inside the box
        for (int i = offset; i < offset + symbols.size(); i++) {
            matrix[startRow + i][startColumn + w / 2] = "" + GraphicUsage.symbolDictionary.get(symbols.get(i - offset));
        }


    }
//
//
    public static void printCard(String[][] matrix, SideOfCard card, int startRow, int startColumn, int h, int w) {
        if (card.getParentCard() instanceof ResourceCard) {
            printResourceCard(matrix, (ResourceCard) card.getParentCard(), startRow, startColumn, h, w, isFrontSide(card));
        }
        else if(card.getParentCard() instanceof GoldCard){
            printGoldCard(matrix, (GoldCard) card.getParentCard(), startRow, startColumn, h, w, isFrontSide(card));
        }
        else if(card.getParentCard() instanceof InitialCard){
            if(isFrontSide(card).equals(Side.FRONT)){
                printInitialCardFront(matrix, (InitialCard) card.getParentCard(), startRow, startColumn, h, w);
            }else{
                printInitialCardBack(matrix, (InitialCard) card.getParentCard(), startRow, startColumn, h, w);
            }
        }
    }

//    public static void main(String[] args) throws IOException {
//        String[][] matrix = new String[9][31];
//        for(int i=0; i<9; i++){
//            Arrays.fill(matrix[i], " ");
//        }
//        Deck deck = new Deck(ResourceCard.class);
//        deck.shuffle();
//        PlayCard card = (ResourceCard) deck.drawCard();
//        drawPlayCard(matrix, card.getFront(), 9, 31, 0, 0);
//        for(int i=0; i<9; i++){
//            for(int j=0; j<31; j++){
//                System.out.print(matrix[i][j]);
//            }
//            System.out.println();
//        }
//        PlayCard card2= (ResourceCard) deck.drawCard();
//        //System.out.println(card2.getColor());
//        card.getFront().getCornerInPosition(CornerPosition.TOPLEFT).setCovered();
//
//
//        card.getFront().getCornerInPosition(CornerPosition.TOPLEFT).setNextCorner(card2.getFront().getCornerInPosition(CornerPosition.BOTTOMRIGHT));
//        System.out.println(card.getFront().getCornerInPosition(CornerPosition.TOPLEFT).getNextCorner().getParentCard().getColor());
//
//    }


}