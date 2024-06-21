package it.polimi.ingsw.View.TUI.TUIUtilis;

import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.CornerPosition;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.Enumerations.UpDownPosition;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.Position;
import it.polimi.ingsw.Model.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DesignSupportClass {


    /**
     * Support method used to design the general outline of a card inside another matrix.
     * This other matrix can be a playArea, but also a playground or a player's hand
     * Dimensions can be customized basing on the necessities
     *
     * @param height      height of the card
     * @param width       width of the card
     * @param startColumn column where the card starts
     * @param startRow    row where the card starts
     * @param outline     bigger matrix where the card is placed
     * @return matrix that represents the outline of the card inside the bigger matrix.
     */
    public static String[][] DrawGeneralOutline(int height, int width, String[][] outline, int startRow, int startColumn, CardColors color) {
        String ANSIreset = "\u001B[0m";
        String ANSIbackgroundColor;
        if (color == null) {
            int[] rgb = {255, 255, 255};
            ANSIbackgroundColor = String.format("\u001B[48;2;%d;%d;%d;m", rgb[0], rgb[1], rgb[2]);
        } else {
            int[] rgb = GraphicUsage.getRGBColor(color);
            ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        }

        String coloredSpace = ANSIbackgroundColor + " " + ANSIreset;
        String coloredLine = ANSIbackgroundColor + "─" + ANSIreset;
        String coloredPipe = ANSIbackgroundColor + "│" + ANSIreset;

        // First row
        outline[startRow][startColumn] = ANSIbackgroundColor + "┌" + ANSIreset;
        Arrays.fill(outline[startRow], startColumn + 1, startColumn + width - 1, coloredLine);
        outline[startRow][startColumn + width - 1] = ANSIbackgroundColor + "┐" + ANSIreset;

        // Middle rows
        for (int i = 1; i < height - 1; i++) {
            outline[startRow + i][startColumn] = coloredPipe;
            Arrays.fill(outline[startRow + i], startColumn + 1, startColumn + width - 1, coloredSpace);
            outline[startRow + i][startColumn + width - 1] = coloredPipe;
        }

        // Last row
        outline[startRow + height - 1][startColumn] = ANSIbackgroundColor + "└" + ANSIreset;
        Arrays.fill(outline[startRow + height - 1], startColumn + 1, startColumn + width - 1, coloredLine);
        outline[startRow + height - 1][startColumn + width - 1] = ANSIbackgroundColor + "┘" + ANSIreset;

        return outline;
    }


    /**
     * Method that prints the back of a PlayCard that is not an InitialCard (Initial cards are a little different, because the back has more symbols to it).
     *
     * @param startColumn column where we want to start to print
     * @param startRow    row where we want to start to print
     * @param card        whose back we want to draw
     * @param matrix      where we want to draw the card
     */
    public static void printBackCard(String[][] matrix, PlayCard card, int startRow, int startColumn, int height, int width) {
        CardColors color = card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String ANSIbackgroundColor = String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]);
        String ANSIreset = "\u001B[0m";

        String[][] Matrix = DrawGeneralOutline(height, width, matrix, startRow, startColumn, color);
        String centralChar = GraphicUsage.cardColorDictionary.get(color);
        Matrix[startRow + height / 2][startColumn + width / 2] = ANSIbackgroundColor + centralChar + ANSIreset;
        Matrix[startRow + height / 2][startColumn + width / 2 - 2] = ANSIbackgroundColor + "│" + ANSIreset;
        Matrix[startRow + height / 2][startColumn + width / 2 + 2] = ANSIbackgroundColor + "│" + ANSIreset;

        for (CornerPosition position : CornerPosition.values())
            cornerOutline(Matrix, position, startRow, startColumn, color, height, width);
    }


    /**
     * Method that prints the front of a card, with the symbols in the corners
     *
     * @param matrix      where we want to print the card
     * @param card        that we want to print
     * @param startRow    row where we want to start to print
     * @param startColumn column where we want to start to print
     */
    public static void printFrontCard(String[][] matrix, PlayCard card, int startRow, int startColumn, int height, int width) {
        SideOfCard front = card.getFront();
        CardColors color = card.getColor();
        int[] rgb = GraphicUsage.getRGBColor(color);
        String[][] Matrix = DrawGeneralOutline(height, width, matrix, startRow, startColumn, color);
        for (CornerPosition position : CornerPosition.values()) {
            if (!(front.getCornerInPosition(position).isHidden() || front.getCornerInPosition(position).isCovered())) {
                cornerOutline(Matrix, position, startRow, startColumn, color, height, width);
                Symbol symbol = null;
                if (!front.getCornerInPosition(position).isCovered())
                    symbol = front.getCornerInPosition(position).getSymbol();

                printSymbolInCorner(Matrix, position, startRow, startColumn, symbol, height, width);
            }

        }
    }

    /**
     * Method that draws the outline of the corner of the card.
     *
     * @param card        matrix where we want to print
     * @param startColumn column where we want to start to print
     * @param startRow    row where we want to start to print
     * @param corner      CornerPosition type
     * @param color       of the card
     */
    public static void cornerOutline(String[][] card, CornerPosition corner, int startRow, int startColumn, CardColors color, int h, int w) {
        StringBuilder sb = new StringBuilder();
        String ANSIreset = "\u001B[0m";
        if (color == null) {
            int[] rgb = {255, 255, 255};
            sb.append(String.format("\u001B[48;2;%d;%d;%d;m", rgb[0], rgb[1], rgb[2]));
        } else {
            int[] rgb = GraphicUsage.getRGBColor(color);
            sb.append(String.format("\u001B[38;2;%d;%d;%dm", rgb[0], rgb[1], rgb[2]));
        }
        String ANSIbackgroundColor = sb.toString();

        switch (corner) {
            case CornerPosition.TOPLEFT -> {
                card[startRow][startColumn + 3] = ANSIbackgroundColor + "┬" + ANSIreset;
                card[startRow + 2][startColumn] = ANSIbackgroundColor + "├" + ANSIreset;
                card[startRow + 2][startColumn + 3] = ANSIbackgroundColor + "┘" + ANSIreset;
                Arrays.fill(card[startRow + 2], startColumn + 1, startColumn + 3, ANSIbackgroundColor + "─" + ANSIreset);
                card[startRow + 1][startColumn + 3] = ANSIbackgroundColor + "│" + ANSIreset;
            }
            case CornerPosition.BOTTOMLEFT -> {
                card[startRow + h - 1][startColumn + 3] = ANSIbackgroundColor + "┴" + ANSIreset;
                card[startRow + h - 3][startColumn] = ANSIbackgroundColor + "├" + ANSIreset;
                card[startRow + h - 3][startColumn + 3] = ANSIbackgroundColor + "┐" + ANSIreset;
                Arrays.fill(card[startRow + h - 3], startColumn + 1, startColumn + 3, ANSIbackgroundColor + "─" + ANSIreset);
                card[startRow + h - 2][startColumn + 3] = ANSIbackgroundColor + "│" + ANSIreset;

            }
            case CornerPosition.TOPRIGHT -> {
                card[startRow][startColumn + w - 4] = ANSIbackgroundColor + "┬" + ANSIreset;
                card[startRow + 2][startColumn + w - 1] = ANSIbackgroundColor + "┤" + ANSIreset;
                card[startRow + 2][startColumn + w - 4] = ANSIbackgroundColor + "└" + ANSIreset;
                Arrays.fill(card[startRow + 2], startColumn + w - 3, startColumn + w - 1, ANSIbackgroundColor + "─" + ANSIreset);
                card[startRow + 1][startColumn + w - 4] = ANSIbackgroundColor + "│" + ANSIreset;
            }
            case CornerPosition.BOTTOMRIGHT -> {
                card[startRow + h - 1][startColumn + w - 4] = ANSIbackgroundColor + "┴" + ANSIreset;
                card[startRow + h - 3][startColumn + w - 1] = ANSIbackgroundColor + "┤" + ANSIreset;
                card[startRow + h - 3][startColumn + w - 4] = ANSIbackgroundColor + "┌" + ANSIreset;
                Arrays.fill(card[startRow + h - 3], startColumn + w - 3, startColumn + w - 1, ANSIbackgroundColor + "─" + ANSIreset);
                card[startRow + h - 2][startColumn + w - 4] = ANSIbackgroundColor + "│" + ANSIreset;

            }
        }
    }

    /**
     * method that prints the graphic correspondent to the symbol in the corner of the card.
     *
     * @param card        matrix where we want to print
     * @param startRow    row where we want to start to print
     * @param startColumn column where we want to start to print
     * @param symbol      that we want to print
     * @param corner      CornerPosition type
     */
    public static void printSymbolInCorner(String[][] card, CornerPosition corner, int startRow, int startColumn, Symbol symbol, int h, int w) {
        String graphicSymbol;
        if (symbol == null)
            graphicSymbol = " ";
        else
            graphicSymbol = GraphicUsage.symbolDictionary.get(symbol);
        switch (corner) {
            case TOPLEFT -> card[startRow + 1][startColumn + 2] = graphicSymbol;
            case BOTTOMLEFT -> card[startRow + h - 2][startColumn + 2] = graphicSymbol;
            case TOPRIGHT -> card[startRow + 1][startColumn + w - 3] = graphicSymbol;
            case BOTTOMRIGHT -> card[startRow + h - 2][startColumn + w - 3] = graphicSymbol;

            default -> throw new IllegalStateException("Unexpected value: " + corner);
        }

    }

    public static void printResourceFront(String[][] matrix, ResourceCard card, int startRow, int startColumn, int h, int w) {
        int  points = card.getPoints(Side.FRONT);
        printFrontCard(matrix, card, startRow, startColumn, h, w);
        if (points==1) {
            drawPoints(matrix, startRow, startColumn, w, "1");
        }
    }

    public static void printGoldFront(String[][] matrix, GoldCard card, int startRow, int startColumn, int h, int w) {
        printFrontCard(matrix, card, startRow, startColumn, h, w);
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
    public static boolean isFrontSide(SideOfCard card) {
        return getCentralSymbols(card).isEmpty();
    }


    public static void printInitialCardBack(String[][] matrix, InitialCard card, int startRow, int startColumn, int h, int w) {
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        for (CornerPosition corner : CornerPosition.values()) {
            cornerOutline(matrix, corner, startRow, startColumn, null, h, w);
            printSymbolInCorner(matrix, corner, startRow, startColumn, card.getBack().getCornerInPosition(corner).getSymbol(), h, w);

        }

    }

    public static void printInitialCardFront(String[][] matrix, InitialCard card, int startRow, int startColumn, int h, int w) {
        DrawGeneralOutline(h, w, matrix, startRow, startColumn, null);
        for (CornerPosition corner : CornerPosition.values()) {
            cornerOutline(matrix, corner, startRow, startColumn, null, h, w);
            printSymbolInCorner(matrix, corner, startRow, startColumn, card.getFront().getCornerInPosition(corner).getSymbol(), h, w);
        }
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


    public static void printCard(String[][] matrix, SideOfCard card, int startRow, int startColumn, int h, int w) {
        if (card.getParentCard() instanceof ResourceCard) {
            if (isFrontSide(card)) {
                printResourceFront(matrix, (ResourceCard) card.getParentCard(), startRow, startColumn, h, w);
            } else {
                printBackCard(matrix, card.getParentCard(), startRow, startColumn, h, w);
            }
        } else if (card.getParentCard() instanceof GoldCard) {
            if (isFrontSide(card)) {
                printGoldFront(matrix, (GoldCard) card.getParentCard(), startRow, startColumn, h, w);
            } else {
                printBackCard(matrix, card.getParentCard(), startRow, startColumn, h, w);
            }
        } else if (card.getParentCard() instanceof InitialCard) {
            if (!isFrontSide(card)) {
                printInitialCardFront(matrix, (InitialCard) card.getParentCard(), startRow, startColumn, h, w);
            } else {
                printInitialCardBack(matrix, (InitialCard) card.getParentCard(), startRow, startColumn, h, w);
            }
        }
    }


}



