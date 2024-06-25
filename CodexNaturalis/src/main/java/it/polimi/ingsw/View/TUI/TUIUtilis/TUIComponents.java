/**
 * This package contains utility classes for the Text User Interface (TUI) of the application.
 */
package it.polimi.ingsw.View.TUI.TUIUtilis;

// Importing necessary classes and packages
import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass.printCard;

//import static it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass.printCard;

/**
 * The TUIComponents class provides utility methods and constants for the Text User Interface (TUI) of the application.
 * It includes methods for displaying player information, showing cards, and drawing the play area.
 * It also includes constants for formatting the TUI, such as ANSI color codes and dimensions for cards and rows.
 */
public class TUIComponents {

    // ANSI color codes and formatting constants
    private static final String bold = "\033[1m"; // ANSI code for bold text
    private static final String reset = "\033[0m"; // ANSI code to reset text formatting
    private static final String ANSI_RESET = "\u001B[0m"; // ANSI code to reset color
    private static final String  ANSI_CYAN = "\u001B[36m"; // ANSI code for cyan color

    // Dimensions for cards and rows
    private static final int cardHeight=7; // Height of a card in the TUI
    private static final int cardWidth=25; // Width of a card in the TUI
    private static final int RowDimensions=cardHeight-3; // Dimensions for rows in the TUI
    private static final int ColumnDimensions=cardWidth-4; // Dimensions for columns in the TUI

    /**
     * Converts a 2D String array (matrix) to a single String.
     *
     * This method is used to convert a 2D String array, typically representing a play area in the game, into a single String.
     * The conversion is done by iterating over each element in the 2D array, appending it to a StringBuilder, and adding a newline character at the end of each row.
     * The resulting String is then returned.
     *
     * This method is useful for displaying the game state in a text-based user interface, where the game state needs to be represented as a single String.
     *
     * @param matrix The 2D String array to convert. This should be a valid 2D String array representing a game state.
     * @return A String representing the given 2D String array.
     */
    public static String ConvertToString(String[][] matrix){
        StringBuilder sb = new StringBuilder();

        for (String[] strings : matrix) {
            for (String string : strings) {
                sb.append(string);
            }
            sb.append("\n");
        }

        return sb.toString();

    }

    /**
     * Displays the player's information in the player's pawn color.
     *
     * This method is used to display the player's information, including their nickname, score, and round number.
     * The information is displayed in the color associated with the player's pawn.
     * The method retrieves the ANSI color code for the player's pawn color from the GraphicUsage.pawnColorDictionary.
     * It then constructs a 2D String array with the player's information, formatted with the ANSI color code and reset code.
     * The 2D String array is then converted to a single String using the ConvertToString method, and this String is returned.
     *
     * This method is useful for displaying player information in a text-based user interface, where the player's information needs to be color-coded according to their pawn color.
     *
     * @param player The Player whose information is to be displayed. This should be a valid Player object.
     * @return A String representing the player's information, color-coded according to the player's pawn color.
     */
    public static String showPlayerInfo(Player player) {
        PawnColor color = player.getPawnColor();
        String ANSIcolor = GraphicUsage.pawnColorDictionary.get(color);
        String ANSIreset = "\u001B[0m";
        String [][] playerInfo = new String[3][1];
        playerInfo[0][0] = ANSIcolor+ bold+player.getNickname()+ reset+ANSIreset;
        playerInfo[1][0] = ANSIcolor+"Score: " + player.getScore()+ANSIreset;
        playerInfo[2][0] = ANSIcolor+"Round: " + player.getRound()+ANSIreset;
        return ConvertToString(playerInfo);
    }

    /**
     * Displays the cards in the player's hand.
     *
     * This method is used to display the cards that the player can play. The cards are enumerated for multiple choice purposes.
     * The method constructs a 2D String array with the card information, formatted with the card's front side.
     * The 2D String array is then converted to a single String using the ConvertToString method, and this String is returned.
     *
     * This method is useful for displaying player's cards in a text-based user interface, where the player's cards need to be represented as a single String.
     *
     * @param cardsInHand The list of PlayCard objects representing the cards in the player's hand. Only the front side of the cards is shown as the back does not provide substantial information.
     * @return A String representing the player's cards, formatted for display in the user interface.
     */
    public static String showMyCardsInHand(ArrayList<PlayCard> cardsInHand) {
        String[][] matrix=new String[2][1];
        String s="";
        String header="[1]                        [2]                        [3]\n";
        matrix[0][0]=header;
        String c1= printCommonCard(cardsInHand.get(0), Side.FRONT);
        String c2= printCommonCard(cardsInHand.get(1), Side.FRONT);
        String c3= printCommonCard(cardsInHand.get(2), Side.FRONT);
        s=concatString(concatString(c1,c2,2), c3, 2);
        matrix[1][0]=s;
        return ConvertToString(matrix);
    }

    /**
     * Displays the opponent's information and play area.
     *
     * This method is used to display the opponent's information, including their nickname, score, and round number,
     * as well as their play area. The information is displayed in the color associated with the opponent's pawn.
     * The method retrieves the ANSI color code for the opponent's pawn color from the GraphicUsage.pawnColorDictionary.
     * It then constructs a 2D String array with the opponent's information and play area, formatted with the ANSI color code and reset code.
     * The 2D String array is then converted to a single String using the ConvertToString method, and this String is returned.
     *
     * This method is useful for displaying opponent's information and play area in a text-based user interface, where the opponent's information and play area need to be represented as a single String.
     *
     * @param player The Player whose information and play area are to be displayed. This should be a valid Player object.
     * @return A String representing the opponent's information and play area, color-coded according to the opponent's pawn color.
     */
    public static String showOpponent(Player player){
        String[][] matrix= new String[2][1];
        String s=showPlayerInfo(player);
        matrix[0][0]=s;
        if(player.getPlayArea()!=null)
            matrix[1][0]=DrawOthersPlayArea(player.getPlayArea());
        else
            matrix[1][0]="";

        String result = ConvertToString(matrix);


        return result;
    }

    /**
     * Displays the current player's information and game state.
     *
     * This method is used to display the current player's information, including their nickname, score, and round number,
     * as well as their cards in hand and play area. The information is displayed in a formatted manner.
     * The method retrieves the player's personal objective card, cards in hand, and play area.
     * It then constructs a StringBuilder with the player's information and game state.
     * If the player's personal objective card, cards in hand, or play area are null, empty strings are appended to the StringBuilder.
     * The resulting StringBuilder is then converted to a String and returned.
     *
     * This method is useful for displaying the current player's information and game state in a text-based user interface, where the player's information and game state need to be represented as a single String.
     *
     * @param player The Player whose information and game state are to be displayed. This should be a valid Player object.
     * @return A String representing the current player's information and game state.
     */
    public static String showMySelf(Player player){
        StringBuilder sb = new StringBuilder();
        String s = showPlayerInfo(player);
        sb.append(s);

        if(player.getPersonalObjectiveCard() != null && player.getCardsInHand() != null && player.getPlayArea() != null) {
            String objective = printObjectives(player.getPersonalObjectiveCard());
            String hand = showMyCardsInHand(player.getCardsInHand());
            String ObjHand = concatString(objective, hand, 5);
            sb.append(hand);

            sb.append("\n").append(bold).append("YOUR PLAY AREA ").append(reset).append("\n");
            String area = DrawMyPlayArea(player.getPlayArea());
            sb.append(area);
        }
        else{
            sb.append("");
            sb.append("");
            sb.append("");
        }

        return sb.toString();
    }

    /**
     * Displays the common cards on the PlayGround.
     *
     * This method is used to display the common Resource cards, the common Gold cards, the two Decks (Resource and Gold)
     * represented by showing the back of the last card, and the common objectives for all players.
     * The method retrieves the common Resource cards, the common Gold cards, the Resource deck, the Gold deck, and the common objectives from the model.
     * It then constructs a 2D String array with the common cards and objectives, formatted for display in the user interface.
     * The 2D String array is then converted to a single String using a StringBuilder, and this String is returned.
     *
     * This method is useful for displaying the common cards and objectives in a text-based user interface, where the common cards and objectives need to be represented as a single String.
     *
     * @param model The PlayGround model to represent. This should be a valid PlayGround object.
     * @return A String representing the common cards and objectives in the given PlayGround model.
     */
    public static  String showCommonCards(PlayGround model) {
        ArrayList<ResourceCard> cards= model.getCommonResourceCards();
        ArrayList<GoldCard> goldCards=model.getCommonGoldCards();
        Deck resourceDeck=model.getResourceCardDeck();
        Deck goldDeck=model.getGoldCardDeck();
        ArrayList<ObjectiveCard> commonObjectives=model.getCommonObjectivesCards();
        String[][] matrix= new String[4][1];
        String header1=concatString("OBJECTIVE1" ,concatString("                   RESOURCE CARDS", "RESOURCE DECK", 35), 35);
        matrix[0][0]=header1;
        String header2= concatString("OBJECTIVE2",concatString("                    GOLD CARDS", "GOLD DECK", 35), 40);
        String commonResources=concatString(concatString ( printCommonCard(cards.get(0), Side.FRONT), printCommonCard(cards.get(1), Side.FRONT),
                3),printCommonCard((PlayCard) resourceDeck.getCards().getLast(),Side.BACK),3);
        matrix[1][0]=concatString(printObjectives(commonObjectives.get(0)),commonResources, 20);
        String commonGold= concatString(concatString ( printCommonCard(goldCards.get(0), Side.FRONT), printCommonCard(goldCards.get(1), Side.FRONT),
                3),printCommonCard((PlayCard) goldDeck.getCards().getLast(),Side.BACK),3);
        matrix[2][0]=header2;
        matrix[3][0]=concatString(printObjectives(commonObjectives.get(1)),commonGold, 20);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();

    }

    /**
     * Prints the representation of a general ObjectiveCard.
     *
     * This method is used to print the representation of a general ObjectiveCard. Since the type of ObjectiveCard is not known beforehand,
     * the method checks the class of the card and calls the appropriate method to print the card.
     * If the card is of type DispositionObjectiveCard, it calls the printDispositionObjectiveCard method from the DesignSupportClass.
     * If the card is of type SymbolObjectiveCard, it calls the printSymbolObjectiveCard method from the DesignSupportClass.
     * If the card is not of any known type, it returns an empty string.
     *
     * This method is useful for displaying the representation of an ObjectiveCard in a text-based user interface, where the representation of the card needs to be a String.
     *
     * @param card The ObjectiveCard to be printed. This should be a valid ObjectiveCard object.
     * @return A String representing the given ObjectiveCard. If the card is not of a known type, an empty string is returned.
     */
    public static String printObjectives(ObjectiveCard card) {
        if (card.getClass().equals(DispositionObjectiveCard.class))
            return DesignSupportClass.printDispositionObjectiveCard((DispositionObjectiveCard) card);
        else if (card.getClass().equals(SymbolObjectiveCard.class))
            return DesignSupportClass.printSymbolObjectiveCard((SymbolObjectiveCard) card);
        else return "";

    }

    /**
     * Prints a common card with the specified dimensions and returns the string that represents it.
     *
     * This method is used to print a common card in the game. The card can be printed either on its front or back side.
     * The method creates a 2D String array (matrix) with the dimensions specified by the cardHeight and cardWidth constants.
     * It then calls the getCardString method, passing the matrix, the card, and the dimensions as arguments.
     * The getCardString method is responsible for filling the matrix with the representation of the card and converting it to a String.
     * The resulting String is then returned by this method.
     *
     * This method is useful for displaying a common card in a text-based user interface, where the card needs to be represented as a String.
     *
     * @param card The PlayCard to be printed. This should be a valid PlayCard object.
     * @param side The side of the card to be printed. This should be either Side.FRONT for the common cards or Side.BACK for the last card of the deck.
     * @return A String representing the given PlayCard, formatted according to the specified dimensions and side.
     */
    public static String printCommonCard(PlayCard card, Side side){
        String[][] matrix = new String[cardHeight][cardWidth];
        return getCardString(matrix, card, cardHeight, cardWidth, side);
    }

    /**
     * Prints the opponent's play area in a text-based user interface.
     *
     * This method is used to create a visual representation of the opponent's play area in a text-based user interface.
     * The play area is represented as a 2D String array (matrix), with each cell representing a card or an empty space.
     * The size of the cards is smaller compared to the player's own play area, and the cards do not have indexes on the sides.
     * The method first initializes the matrix with empty strings, then iterates over each cell.
     * If a card is present in the corresponding position in the play area, the card is printed into the matrix using the printCard method from the DesignSupportClass.
     * The resulting matrix is then converted to a single String using the ConvertToString method, and this String is returned.
     *
     * This method is useful for displaying the opponent's play area in a text-based user interface, where the play area needs to be represented as a single String.
     *
     * @param playArea The PlayArea of the opponent. This should be a valid PlayArea object.
     * @return A String representing the opponent's play area, formatted for display in a text-based user interface.
     */
    public static String DrawOthersPlayArea(PlayArea playArea){
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int cardRowIndex = i / rowDimensions;
                int cardColumnIndex = j / columnDimensions;
                int startRow = cardRowIndex * rowDimensions;
                int startColumn = cardColumnIndex * columnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() &&
                        cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        DesignSupportClass.printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex),startRow, startColumn, rowDimensions+3, columnDimensions+4 );
                    }
                }
            }
        }

        return ConvertToString(playAreaMatrix);
    }

    /**
     * Prints the client's play area in a text-based user interface.
     *
     * This method is used to create a visual representation of the client's play area in a text-based user interface.
     * The play area is represented as a 2D String array (matrix), with each cell representing a card or an empty space.
     * The size of the cards is larger compared to the opponent's play area, and the cards have indexes on the sides.
     * This allows the player to choose where to place the next card when playing.
     * The method first initializes the matrix with empty strings, then adds indexes to the sides of the matrix.
     * It then iterates over each cell. If a card is present in the corresponding position in the play area,
     * the card is printed into the matrix using the printCard method from the DesignSupportClass.
     * The resulting matrix is then converted to a single String using the ConvertToString method, and this String is returned.
     *
     * This method is useful for displaying the client's play area in a text-based user interface, where the play area needs to be represented as a single String.
     *
     * @param playArea The PlayArea of the client. This should be a valid PlayArea object.
     * @return A String representing the client's play area, formatted for display in a text-based user interface.
     */
    public static String DrawMyPlayArea(PlayArea playArea){
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
                        DesignSupportClass.printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex), startRow, startColumn, cardHeight, cardWidth);
                    }
                }
            }
        }

       return ConvertToString(playAreaMatrix);
    }

    /**
     * Generates a string representation of a given PlayCard with specified dimensions.
     *
     * This method is used to generate a string representation of a PlayCard. The card can be represented either on its front or back side.
     * The method first creates a copy of the provided 2D String array (matrix). Then, depending on the specified side of the card,
     * it calls the printCard method to fill the matrix with the representation of the card's front or back.
     * Finally, it converts the filled matrix into a single string and returns it.
     *
     * This method is useful for displaying a PlayCard in a text-based user interface, where the card needs to be represented as a string.
     *
     * @param matrix The 2D String array to be filled with the card representation. This should be a valid 2D String array with dimensions matching the specified height and width.
     * @param card The PlayCard to be represented. This should be a valid PlayCard object.
     * @param height The height of the card representation in the matrix. This should be a positive integer.
     * @param width The width of the card representation in the matrix. This should be a positive integer.
     * @param side The side of the card to be represented. This should be either Side.FRONT for the common cards or Side.BACK for the last card of the deck.
     * @return A String representing the given PlayCard, formatted according to the specified dimensions and side.
     */
    public static String getCardString(String[][] matrix, PlayCard card,int height, int width, Side side) {
        String[][] matrixCopy = new String[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            matrixCopy[i] = matrix[i].clone();
        }
        if(Side.FRONT.equals(side))
            printCard(matrixCopy, card.getFront(), 0,0, height, width);
        else
            printCard(matrixCopy, card.getBack(), 0,0, height, width);
        StringBuilder result = new StringBuilder();
        for (String[] strings : matrixCopy) {
            for (String string : strings) {
                result.append(Objects.requireNonNullElse(string, " "));
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * Concatenates two strings with a specified amount of space between them.
     *
     * This method is used to concatenate two multiline strings with a certain amount of space between them.
     * The method first splits each input string into lines using the newline character as a delimiter.
     * It then determines which string has fewer lines and assigns this string to the shortS variable, and the other string to the longS variable.
     * The method then iterates over each line in the shortS string, appending the corresponding lines from the s1 and s2 strings to a StringBuilder,
     * with a specified amount of space between them.
     * If the s1 string has more lines than the s2 string, the remaining lines from the s1 string are appended to the StringBuilder.
     * If the s2 string has more lines than the s1 string, the remaining lines from the s2 string are appended to the StringBuilder,
     * preceded by a specified amount of space.
     * The resulting StringBuilder is then converted to a String and returned.
     *
     * This method is useful for formatting multiline strings for display in a text-based user interface, where the strings need to be aligned vertically.
     *
     * @param s1 The first string to concatenate. This should be a valid multiline string.
     * @param s2 The second string to concatenate. This should be a valid multiline string.
     * @param space The amount of space to insert between the two strings. This should be a non-negative integer.
     * @return A String representing the concatenation of the two input strings, with the specified amount of space between them.
     */
    public static String concatString(String s1, String s2, int space) {
        String[] s1Lines = s1.split("\n");
        String[] s2Lines = s2.split("\n");
        StringBuilder sb = new StringBuilder();

        String[] shortS = s1Lines.length < s2Lines.length ? s1Lines : s2Lines;
        String[] longS = s1Lines.length < s2Lines.length ? s2Lines : s1Lines;

        for (int i = 0; i < shortS.length; i++) {
            sb.append(s1Lines[i]);
            sb.append(" ".repeat(Math.max(0, space)));
            sb.append(s2Lines[i]).append("\n");
        }

        for (int i = shortS.length; i < longS.length; i++) {
            if (s1Lines == longS) {
                sb.append(s1Lines[i]).append("\n");
            } else {
                sb.append(" ".repeat(Math.max(0, s1Lines[0].length() + space)));
                if (i < s2Lines.length) {
                    sb.append(s2Lines[i]).append("\n");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Formats a list of strings for display in a chat box.
     *
     * This method is used to format a list of strings so that they can be displayed in a chat box in a text-based user interface.
     * The method first creates a StringBuilder and appends a header for the chat box.
     * It then iterates over each string in the input list. If a string is longer than the maximum line length (50 characters),
     * the method splits the string into multiple lines of up to 50 characters each.
     * Each line is then appended to the StringBuilder, followed by a newline character.
     * The resulting StringBuilder is then converted to a String and returned.
     *
     * This method is useful for formatting chat messages for display in a text-based user interface, where each message needs to be represented as a single String.
     *
     * @param strings The list of strings to be formatted. Each string represents a chat message.
     * @return A String representing the formatted chat messages, ready for display in a chat box.
     */
    public static String formatStringList(ArrayList<String> strings) {
        StringBuilder sb = new StringBuilder();
        int maxLineLength = 50;
        sb.append("                      "+bold+ANSI_CYAN+"CHAT"+ANSI_RESET+reset+ "\n");
        for (String str : strings) {

            while (str.length() > maxLineLength) {
                sb.append(str.substring(0, maxLineLength)).append("\n");
                str = str.substring(maxLineLength);
            }
            sb.append(str).append("\n");
        }

        return sb.toString();
    }

    /**
     * Creates a box with a specified amount of space between the box and the content.
     *
     * This method is used to create a box around a given string (the content of the box), with a specified amount of space between the box and the content.
     * The box is created using ASCII characters, and the color of the box can be specified using ANSI color codes.
     * The method first calculates the maximum length of the lines in the input string, then creates the top and bottom of the box with the calculated width.
     * It then iterates over each line in the input string, adding the left and right sides of the box and the specified amount of space.
     * The resulting box is then returned as a single string.
     *
     * This method is useful for formatting text for display in a text-based user interface, where the text needs to be enclosed in a box.
     *
     * @param input The content of the box. This should be a valid string, possibly multiline.
     * @param color The color of the box. This should be a valid ANSI color code.
     * @param space The amount of space to insert between the box and the content. This should be a non-negative integer.
     * @return A String representing the content enclosed in a box, with the specified color and amount of space.
     */
    public static String createBoxWithSpace(String input, String color, int space) {
        StringBuilder output = new StringBuilder();
        String[] lines = input.split("\n");
        String[] linesNoColor = input.split("\n");
        int maxLength = 0;

        // Iterate over each line to calculate the max length
        for( String line : linesNoColor ) {
            // Remove ANSI color codes before measuring length
            String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
            maxLength = Math.max(maxLength, plainLine.length());
        }

        int width = maxLength; // Add 2 for each side of the box
        String top = color + "┌" + "─".repeat(width + 2) + "┐" + ANSI_RESET + " ".repeat(space) + "\n";
        String bottom = color + "└" + "─".repeat(width + 2) + "┘" + ANSI_RESET + " ".repeat(space)+"\n" ;

        output.append(top);

        for( String line : lines ) {
            output.append(color).append("│ ").append(ANSI_RESET);
            // Replace ANSI color codes with empty strings before padding
            String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
            output.append(line);
            output.append(" ".repeat(maxLength - plainLine.length()));
            output.append(color).append(" │").append(ANSI_RESET).append( " ".repeat(space)).append("\n");
        }

        output.append(bottom);
        return output.toString();
    }

   // JavaDoc
    public static void createASCIILegend(){

    }

    /**
     * The main method for testing the functionality of the PlayArea and Deck classes.
     *
     * This method is used to create a PlayArea and two Decks (one for ResourceCards and one for InitialCards),
     * shuffle the decks, draw cards from them, and add the cards to the play area.
     * It then prints the play area to the console.
     *
     * This method is useful for testing the functionality of the PlayArea and Deck classes, as well as the methods for adding cards to the play area and printing the play area.
     *
     * @param args The command-line arguments. This is not used in this method.
     * @throws IOException If an input or output exception occurred
     */
    public static void main(String[] args) throws IOException {
        PlayArea playArea = new PlayArea();
        Deck resourceDeck= new Deck(ResourceCard.class);
        Deck initialDeck= new Deck(InitialCard.class);
        initialDeck.shuffle();
        resourceDeck.shuffle();
        ResourceCard card1= (ResourceCard) resourceDeck.drawCard();
        ResourceCard card2= (ResourceCard) resourceDeck.drawCard();
        ResourceCard card3= (ResourceCard) resourceDeck.drawCard();
        ResourceCard card4= (ResourceCard) resourceDeck.drawCard();
        InitialCard card5= (InitialCard) initialDeck.drawCard();

        playArea.addInitialCardOnArea(card5.getBack());
        //System.out.println("r:"+ playArea.getCardsOnArea().size()+" c:"+playArea.getCardsOnArea().getFirst().size());
        playArea.addCardOnArea(card1.getBack(), 2, 0);
        //playArea.addCardOnArea(card2.getFront(), 0, 3);
        playArea.addCardOnArea(card3.getBack(), 4, 2);
        //playArea.addCardOnArea(card2.getBack(), 2, 0);
        //System.out.println(card1.getColor());
        //System.out.println(playArea.getCardInPosition(1,1).getColor());
        //playArea.addCardOnArea(card3.getFront(), 0, 0);
       // playArea.addCardOnArea(card4.getFront(), 0, 2);

//        if(card1.getBack().getCornerInPosition(CornerPosition.TOPLEFT).isCovered())
//            System.out.println("CORNER MATCHES");
//        else
//            System.out.println("CORNER DOES NOT MATCH");

        System.out.println(DrawMyPlayArea(playArea));
        System.out.println(DrawOthersPlayArea(playArea));

    }

}