package it.polimi.ingsw.View.TUI.TUIUtilis;

import it.polimi.ingsw.Model.Enumerations.*;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Position;
import java.util.ArrayList;
import java.util.Map;

import static it.polimi.ingsw.Model.Enumerations.CornerPosition.*;
import static it.polimi.ingsw.Model.Enumerations.UpDownPosition.DOWN;
import static it.polimi.ingsw.Model.Enumerations.UpDownPosition.UP;

/**
 * This class provides support for designing the Text User Interface (TUI) of the game.
 * It contains methods and constants for drawing cards and other game elements in the console.
 */
public class DesignSupportClass {

        /**
        * ANSI escape code for resetting color
         */
        private static final String ANSI_RESET = "\u001B[0m";
        /**
        *ANSI escape code format for setting color
         */
        private static final String ANSI_COLOR_FORMAT = "\u001B[38;2;%d;%d;%dm";
        /**
        * Unicode characters for drawing corners and lines
         */
        private static final String CORNER_TOP_LEFT = "┌";
        private static final String CORNER_TOP_RIGHT = "┐";
        private static final String CORNER_BOTTOM_LEFT = "└";
        private static final String CORNER_BOTTOM_RIGHT = "┘";
        private static final String HORIZONTAL_LINE = "─";
        private static final String VERTICAL_LINE = "│";
        private static final String SPACE = " ";

    /**
     * Returns the ANSI escape code for setting the color to the RGB values associated with the given CardColors enum value.
     * If the color is null, the method returns the ANSI escape code for white color.
     *
     * @param color The CardColors enum value for which to get the ANSI escape code.
     * @return The ANSI escape code for setting the color.
     */
        private static String getAnsiColor(CardColors color) {
            int[] rgb = color == null ? new int[]{255, 255, 255} : GraphicUsage.getRGBColor(color);
            return String.format(ANSI_COLOR_FORMAT, rgb[0], rgb[1], rgb[2]);
        }

    /**
     * Determines the starting position for a corner based on its position and the dimensions of the area.
     * <p>
     * This method calculates the starting position for a corner based on its position (top left, top right, bottom left, bottom right)
     * and the dimensions of the area (height and width). The starting position is returned as a Pair of integers, where the first integer
     * is the row and the second integer is the column.
     *
     * @param corner The position of the corner (top left, top right, bottom left, bottom right).
     * @param h The height of the area.
     * @param w The width of the area.
     * @return A Pair of integers representing the starting position for the corner (row, column).
     */
    public static Pair<Integer, Integer> getStartForCorner(CornerPosition corner, int h, int w){
        return switch (corner) {
            case TOPLEFT -> new Pair<>(h - 3, w - 4);
            case TOPRIGHT -> new Pair<>(h - 3, -w + 4);
            case BOTTOMLEFT -> new Pair<>(-h + 3, w - 4);
            case BOTTOMRIGHT -> new Pair<>(-h + 3, -w + 4);
            default -> new Pair<>(0, 0);
        };
    }

    /**
     * Draws a play card on the given matrix.
     * <p>
     * This method is used to draw a play card on a given matrix. The card is represented by a SideOfCard object.
     * The method takes as arguments the matrix to draw on, the card to draw, the height and width of the card,
     * and the starting row and column on the matrix where the card should be drawn.
     * <p>
     * The method first gets the color of the card and the ANSI escape code for that color. It then iterates over the matrix,
     * setting the appropriate characters for the card's outline and filling in the card's color.
     * <p>
     * After drawing the basic card, the method iterates over the card's corners. If a corner is not hidden, it draws the corner's symbol.
     * If a corner is covered, it draws the symbol of the corner that covers it. If a corner is hidden, it draws an outline for the hidden corner.
     *
     * @param matrix The matrix to draw on.
     * @param card The card to draw.
     * @param h The height of the card.
     * @param w The width of the card.
     * @param startRow The starting row on the matrix where the card should be drawn.
     * @param startColumn The starting column on the matrix where the card should be drawn.
     * @return The matrix with the card drawn on it.
     */
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

    /**
     * Draws a corner of a card on the given matrix.
     * <p>
     * This method is used to draw a corner of a card on a given matrix. The corner is represented by a CornerPosition object.
     * The method takes as arguments the matrix to draw on, the corner to draw, the height and width of the card,
     * the starting row and column on the matrix where the corner should be drawn, the color of the card, and the symbol to be drawn in the corner.
     * <p>
     * The method first gets the ANSI escape code for the color of the card and the graphic symbol for the symbol to be drawn.
     * It then determines the offsets and symbols to be used for drawing the corner based on the position of the corner.
     * Finally, it iterates over the offsets and symbols, setting the appropriate characters on the matrix.
     *
     * @param matrix The matrix to draw on.
     * @param corner The corner to draw.
     * @param h The height of the card.
     * @param w The width of the card.
     * @param startRow The starting row on the matrix where the corner should be drawn.
     * @param startColumn The starting column on the matrix where the corner should be drawn.
     * @param color The color of the card.
     * @param symbol The symbol to be drawn in the corner.
     * @throws IllegalArgumentException If an invalid corner position is provided.
     */
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

    /**
     * Draws an outline for a hidden corner on the given matrix.
     * <p>
     * This method is used to draw an outline for a hidden corner on a given matrix. The corner is represented by a CornerPosition object.
     * The method takes as arguments the matrix to draw on, the corner to draw, the height and width of the card,
     * the starting row and column on the matrix where the corner should be drawn, and the color of the card.
     * <p>
     * The method first gets the ANSI escape code for the color of the card. It then determines the symbols to be used for drawing the corner
     * based on the position of the corner. Finally, it sets the appropriate characters on the matrix.
     *
     * @param matrix The matrix to draw on.
     * @param corner The corner to draw.
     * @param h The height of the card.
     * @param w The width of the card.
     * @param startRow The starting row on the matrix where the corner should be drawn.
     * @param startColumn The starting column on the matrix where the corner should be drawn.
     * @param color The color of the card.
     */
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

    /**
     * Prints the central part of the back of a PlayCard on the given matrix.
     * <p>
     * This method is used to print the central part of the back of a PlayCard on a given matrix.
     * The method takes as arguments the matrix to print on, the card to print, the height and width of the card,
     * and the starting row and column on the matrix where the card should be printed.
     * <p>
     * The method first gets the color of the card and the ANSI escape code for that color. It then sets the appropriate characters
     * on the matrix to represent the central part of the back of the card.
     *
     * @param matrix The matrix to print on.
     * @param card The card to print.
     * @param startRow The starting row on the matrix where the card should be printed.
     * @param startColumn The starting column on the matrix where the card should be printed.
     * @param h The height of the card.
     * @param w The width of the card.
     */
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


    /**
     * Prints a ResourceCard on the given matrix.
     * <p>
     * This method is used to print a ResourceCard on a given matrix. The card can be either the front or the back of the card,
     * as specified by the Side parameter. The method takes as arguments the matrix to print on, the card to print, the height and width of the card,
     * the starting row and column on the matrix where the card should be printed, and the side of the card to print.
     * <p>
     * If the side is FRONT, the method draws the front of the card on the matrix and prints the points of the card if they are equal to 1.
     * If the side is BACK, the method draws the back of the card on the matrix and prints the central part of the back of the card.
     *
     * @param matrix The matrix to print on.
     * @param card The ResourceCard to print.
     * @param startRow The starting row on the matrix where the card should be printed.
     * @param startColumn The starting column on the matrix where the card should be printed.
     * @param h The height of the card.
     * @param w The width of the card.
     * @param side The side of the card to print (FRONT or BACK).
     */
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

    /**
     * Prints a GoldCard on the given matrix.
     * <p>
     * This method is used to print a GoldCard on a given matrix. The card can be either the front or the back of the card,
     * as specified by the Side parameter. The method takes as arguments the matrix to print on, the card to print, the height and width of the card,
     * the starting row and column on the matrix where the card should be printed, and the side of the card to print.
     * <p>
     * If the side is FRONT, the method draws the front of the card on the matrix and prints the points of the card.
     * It also prints the requirements of the card and the goal symbol if the card is of type PointPerVisibleSymbol or PointPerCoveredCorner.
     * <p>
     * If the side is BACK, the method draws the back of the card on the matrix and prints the central part of the back of the card.
     *
     * @param matrix            The matrix to print on.
     * @param card              The GoldCard to print.
     * @param startRow          The starting row on the matrix where the card should be printed.
     * @param startColumn       The starting column on the matrix where the card should be printed.
     * @param h                 The height of the card.
     * @param w                 The width of the card.
     * @param side              The side of the card to print (FRONT or BACK).
     */
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
        }
    }

    /**
     * Draws the points of a card on the given matrix.
     * <p>
     * This method is used to draw the points of a card on a given matrix. The points are represented by a string.
     * The method takes as arguments the matrix to draw on, the starting row and column on the matrix where the points should be drawn,
     * the width of the card, and the string representing the points.
     * <p>
     * The method first calculates the middle column of the card. It then creates a StringBuilder and appends the appropriate characters
     * to it to represent the points. These characters are then set on the matrix at the appropriate positions.
     *
     * @param matrix The matrix to draw on.
     * @param startRow The starting row on the matrix where the points should be drawn.
     * @param startColumn The starting column on the matrix where the points should be drawn.
     * @param w The width of the card.
     * @param pointSymbol The string representing the points.
     */
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

    /**
     * Draws a general outline for a card on the given matrix.
     * <p>
     * This method is used to draw a general outline for a card on a given matrix. The outline is represented by a rectangle with rounded corners.
     * The method takes as arguments the height and width of the card, the matrix to draw on, the starting row and column on the matrix where the outline should be drawn,
     * and the color of the card.
     * <p>
     * The method first gets the ANSI escape code for the color of the card. It then iterates over the matrix, setting the appropriate characters for the outline.
     * The corners of the outline are represented by the Unicode characters for the top left, top right, bottom left, and bottom right corners.
     * The sides of the outline are represented by the Unicode characters for the horizontal and vertical lines.
     *
     * @param h The height of the card.
     * @param w The width of the card.
     * @param matrix The matrix to draw on.
     * @param startRow The starting row on the matrix where the outline should be drawn.
     * @param startColumn The starting column on the matrix where the outline should be drawn.
     * @param color The color of the card.
     * @return The matrix with the outline drawn on it.
     */
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
     * Prints a SymbolObjectiveCard on the console.
     * <p>
     * This method is used to print a SymbolObjectiveCard on the console. The card is represented by a SymbolObjectiveCard object.
     * The method takes as an argument the card to print.
     * <p>
     * The method first gets the goal of the card and creates a list of symbols representing the goal.
     * It then creates a matrix to represent the card and draws a general outline for the card on the matrix.
     * The points of the card are drawn on the matrix, and the symbols representing the goal are set on the matrix at the appropriate positions.
     * Finally, the method iterates over the matrix, appending the characters to a StringBuilder, and returns the resulting string.
     *
     * @param card The SymbolObjectiveCard to print.
     * @return A string representing the printed card.
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
     * Determines the starting position for a card based on its position.
     * <p>
     * This method calculates the starting position for a card based on its position (top left, top right, up, bottom left, bottom right, down).
     * The starting position is returned as a Pair of integers, where the first integer is the row and the second integer is the column.
     *
     * @param p The position of the card (top left, top right, up, bottom left, bottom right, down).
     * @return A Pair of integers representing the starting position for the card (row, column).
     */
    public static Pair<Integer, Integer> getStartingPosition(Position p) {
        return switch (p) {
            case TOPLEFT -> new Pair<>(-1, -4);
            case TOPRIGHT -> new Pair<>(-1, +4);
            case UP -> new Pair<>(-2, 0);
            case BOTTOMLEFT -> new Pair<>(1, -4);
            case BOTTOMRIGHT -> new Pair<>(1, 4);
            case DOWN -> new Pair<>(2, 0);
            case null, default -> new Pair<>(0, 0);
        };

    }

    /**
     * Prints a DispositionObjectiveCard as a string.
     * <p>
     * This method is used to print a DispositionObjectiveCard as a string. The card is represented by a DispositionObjectiveCard object.
     * The method first gets the color of the central card and creates a matrix to represent the card. It then draws a general outline for the card on the matrix.
     * The points of the card are drawn on the matrix, and the colors of the neighboring cards are set on the matrix at the appropriate positions.
     * Finally, the method iterates over the matrix, appending the characters to a StringBuilder, and returns the resulting string.
     *
     * @param card The DispositionObjectiveCard to print.
     * @return A string representing the printed card.
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
     * Retrieves the central symbols of a card, excluding the symbols located at the corners.
     * <p>
     * This method is used to get the symbols of a card that are not located at the corners. The card is represented by a SideOfCard object.
     * The method first creates an ArrayList of symbols and adds all the symbols from the card to it.
     * It then iterates over the corners of the card. If a corner is not hidden and not covered, the symbol at that corner is removed from the ArrayList.
     * The method returns the ArrayList of symbols that are not located at the corners.
     *
     * @param card The SideOfCard object representing the card to check.
     * @return An ArrayList of Symbol objects representing the symbols that are not in the corners but are on the card.
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
     * Determines if a given SideOfCard is the front or back side of its parent card.
     * <p>
     * This method checks if a given SideOfCard is the front or back side of its parent card. The parent card is retrieved using the getParentCard() method of the SideOfCard.
     * If the given SideOfCard is equal to the front side of its parent card, the method returns Side.FRONT. Otherwise, it returns Side.BACK.
     * <p>
     * This method is useful for determining the orientation of a card, which can be important for game logic in certain card games.
     *
     * @param card The SideOfCard to check. This should be a valid SideOfCard that is part of a card.
     * @return The Side (FRONT or BACK) representing which side of its parent card the given SideOfCard is.
     */
    public static Side isFrontSide(SideOfCard card) {
        if(card.getParentCard().getFront().equals(card))
            return Side.FRONT;
        else
            return Side.BACK;
    }

    /**
     * Prints the back side of an InitialCard on a given matrix.
     * <p>
     * This method is used to print the back side of an InitialCard on a given matrix. The card is represented by an InitialCard object.
     * The method takes as arguments the matrix to print on, the card to print, the height and width of the card,
     * and the starting row and column on the matrix where the card should be printed.
     * <p>
     * The method calls the drawPlayCard method, passing it the matrix, the back side of the card, the height and width of the card,
     * and the starting row and column. The drawPlayCard method draws the card on the matrix.
     *
     * @param matrix The matrix to print on. This should be a valid 2D String array.
     * @param card The InitialCard to print. This should be a valid InitialCard object.
     * @param startRow The starting row on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param startColumn The starting column on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param h The height of the card. This should be a positive integer.
     * @param w The width of the card. This should be a positive integer.
     */
    public static void printInitialCardBack(String[][] matrix, InitialCard card, int startRow, int startColumn, int h, int w) {
        drawPlayCard(matrix, card.getBack(), h, w, startRow, startColumn);
    }

    /**
     * Prints the front side of an InitialCard on a given matrix.
     * <p>
     * This method is used to print the front side of an InitialCard on a given matrix. The card is represented by an InitialCard object.
     * The method first draws the card on the matrix using the drawPlayCard method. It then retrieves the central symbols of the card that are not located at the corners.
     * The method calculates an offset based on the height of the card and the size of the symbols, and uses this offset to position the symbols on the matrix.
     * The method then draws a box around the symbols on the matrix, and finally prints the symbols inside the box.
     *
     * @param matrix The matrix to print on. This should be a valid 2D String array.
     * @param card The InitialCard to print. This should be a valid InitialCard object.
     * @param startRow The starting row on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param startColumn The starting column on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param h The height of the card. This should be a positive integer.
     * @param w The width of the card. This should be a positive integer.
     */
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
            matrix[startRow + i][startColumn + w / 2] = GraphicUsage.symbolDictionary.get(symbols.get(i - offset));
        }
    }

    /**
     * Prints a card on a given matrix.
     * <p>
     * This method is used to print a card on a given matrix. The card is represented by a SideOfCard object.
     * The method first checks the type of the parent card of the given SideOfCard. Depending on the type of the parent card,
     * it calls the appropriate method to print the card.
     * <p>
     * If the parent card is a ResourceCard, it calls the printResourceCard method.
     * If the parent card is a GoldCard, it calls the printGoldCard method.
     * If the parent card is an InitialCard, it checks if the given SideOfCard is the front or back side of the card.
     * If it is the front side, it calls the printInitialCardFront method. If it is the back side, it calls the printInitialCardBack method.
     *
     * @param matrix The matrix to print on. This should be a valid 2D String array.
     * @param card The SideOfCard to print. This should be a valid SideOfCard that is part of a card.
     * @param startRow The starting row on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param startColumn The starting column on the matrix where the card should be printed. This should be a valid index in the matrix.
     * @param h The height of the card. This should be a positive integer.
     * @param w The width of the card. This should be a positive integer.
     */
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