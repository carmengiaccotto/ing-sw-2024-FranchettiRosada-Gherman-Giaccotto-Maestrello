package it.polimi.ingsw.View.TUI.TUIUtilis;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass.printCard;

public class TUIComponents {
    private static final String bold = "\033[1m";
    private static final String reset = "\033[0m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String  ANSI_CYAN = "\u001B[36m";
    private static final int cardHeight=7;
    private static final int cardWidth=25;
    private static final int RowDimensions=cardHeight-3;
    private static final int ColumnDimensions=cardWidth-4;



    /**
     * Method used to convert a matrix to a string. Used for playAreas
     *
     * @param matrix to convert
     *
     * */

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
     * Method used to display the player's information.(Nickname, Score, Round).
     * Information is displayed in the player's pawn color.
     *
     * @param player            whose information we are displaying
     *
     *  */

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
     * Method used to show the player which card they can play. Cards are enumerated for multiple choice purpose.
     *
     * @param cardsInHand               list of cards the player can play. Only front is showed since
     *                                  back does not provide substantial information
     * */

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
     * Method use to show an opponent's information and playArea
     *
     * @param player            opponent whose information we are printing
     *
     * */

    public static String showOpponent(Player player){
        String[][] matrix= new String[2][1];
        String s=showPlayerInfo(player);
        matrix[0][0]=s;
        if(player.getPlayArea()!=null)
            matrix[1][0]=DrawOthersPlayArea(player.getPlayArea());
        else
            matrix[1][0]="";

        String result = ConvertToString(matrix);


        return result;}



    /**
     * Method used to see the Player who is playing their information, by showing them their nickname,
     * score, round, cards in hand and playArea
     *
     * @param player            represents the client that is connected and playing
     *
     * */
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
     * Method used to see the commonCards on the PlayGround. This includes the common Resource cards, the common
     * Gold cards the two Decks (Resource and gold) represented by showing the back of the last card,
     * and the common objectives for all players.
     *
     * @param model we want to represent
     *
     * */
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
     * Method used to print a general objective cards, since we don't know what type of objective card it is.
     *
     * @param card to be printed
     *
     * */
    public static String printObjectives(ObjectiveCard card) {
        if (card.getClass().equals(DispositionObjectiveCard.class))
            return DesignSupportClass.printDispositionObjectiveCard((DispositionObjectiveCard) card);
        else if (card.getClass().equals(SymbolObjectiveCard.class))
            return DesignSupportClass.printSymbolObjectiveCard((SymbolObjectiveCard) card);
        else return "";

    }


    /**
     * Method that prints a common card with the right dimensions and returns the string that represents it
     *
     * @param               card to be printed
     *
     * @param side          side of the card to be printed. It's going to be front for the common cards
     *                      and back for the last card of the deck
     * */
    public static String printCommonCard(PlayCard card, Side side){
        String[][] matrix = new String[cardHeight][cardWidth];
        return getCardString(matrix, card, cardHeight, cardWidth, side);
    }




    /**
     * Method used to print opponent's playArea.
     * Different from MyPlayArea because the size of the cards is smaller,
     * and does not have the indexes on the sides.
     *
     * @param               playArea opponent's playArea
     *
     * */
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
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < columns - 1; j++) {
                int cardRowIndex = i / rowDimensions;
                int cardColumnIndex = j / columnDimensions;
                int startRow = cardRowIndex * rowDimensions;
                int startColumn = cardColumnIndex * columnDimensions;
                if (cardRowIndex < playArea.getCardsOnArea().size() &&
                        cardColumnIndex < playArea.getCardsOnArea().getFirst().size()) {
                    if (playArea.getCardInPosition(cardRowIndex, cardColumnIndex) != null) {
                        printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex),startRow, startColumn, rowDimensions+3, columnDimensions+4);
                    }
                }
            }
        }

        return ConvertToString(playAreaMatrix);
    }




    /**
     * Method do print the client's playArea. It is different from the opponent's playArea
     * because it is represented bigger and has the indexes of the position of the cards on the side,
     * so that the player can choose where to place the next card when playing.
     *
     * @param playArea            "my player PlayArea"
     *
     * */
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
                        printCard(playAreaMatrix,  playArea.getCardInPosition(cardRowIndex, cardColumnIndex),startRow, startColumn, cardHeight, cardWidth);
                    }
                }
            }
        }

       return ConvertToString(playAreaMatrix);
    }



/**
     * Method used to print a card with the right dimensions and returns the string that represents it
     *
     * @param               card to be printed
     *
     * @param side          side of the card to be printed. It's going to be front for the common cards
     *                      and back for the last card of the deck
     * */
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
     * Method used to concatenate two strings with a certain space between them
     *
     * @param s1            first string
     * @param s2            second string
     * @param space         space between the two strings
     *
     * */
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
     * Method used to format a list of strings in a way that they can be printed in a box
     *
     * @param strings           list of strings to be formatted
     *
     * */

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
     * Method used to create a box with a certain space between the box and the content
     *
     * @param input             content of the box
     * @param color             color of the box
     * @param space             space between the box and the content
     *
     * */
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

//    public static void main(String[] args) throws IOException {
//
//        String ANSI_CYAN = "\u001B[36m";
//        Deck resourceDeck= new Deck (ResourceCard.class);
//        Deck ObjectiveDeck= new Deck (ObjectiveCard.class);
//        Player player1= new Player();
//        player1.setNickname("Player1");
//        player1.setPawnColor(PawnColor.BLUE);
//        player1.addCardToHand((ResourceCard) resourceDeck.drawCard());
//        player1.addCardToHand((PlayCard) resourceDeck.drawCard());
//        player1.addCardToHand((PlayCard) resourceDeck.drawCard());
//        player1.setPersonalObjectiveCard((ObjectiveCard) ObjectiveDeck.drawCard());
//        System.out.println(showMySelf(player1));
//        System.out.println(showOpponent(player1));
//
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("This is a test string");
//        strings.add("This is another test string to see how the box behaves with multiple lines of text");
//        strings.add("This is a third test string");
//        strings.add("This is a fourth test string");
//        strings.add("This is a fifth test string because i really really want to see how the box behaves with multiple lines of text");
//        strings.add("This is a sixth test string");
//        strings.add("This is a seventh test string");
//        strings.add("This is an eighth test string");
//        String header= concatString(" ", "CHAT",10);
//
//        List<List<SideOfCard>> cardsOnArea= new ArrayList<>();
//        List<SideOfCard> row1 = new ArrayList<>();
//        Deck goldDeck = new Deck(GoldCard.class);
//        ResourceCard fullCard1= (ResourceCard) resourceDeck.getCards().get(21);
//        SideOfCard card1= fullCard1.chooseSide(Side.FRONT);
//        card1.setPositionOnArea(new Pair<>(0, 0));
//        row1.add(card1);
//        row1.add(null);
//        row1.add(card1);
//        cardsOnArea.add(row1);
//        List<SideOfCard> row2 = new ArrayList<>();
//        PlayCard fullCard2= (GoldCard) goldDeck.getCards().get(3);
//        SideOfCard card2= fullCard2.chooseSide(Side.FRONT);
//        card2.getCornerInPosition(CornerPosition.TOPLEFT).setCovered();
//        row2.add(null);
//        card2.setPositionOnArea(new Pair<>(1, 0));
//        row2.add(card2);
//        row2.add(null);
//        cardsOnArea.add(row2);
//        List<SideOfCard> row3 = new ArrayList<>();
//        PlayCard fullCard3= (GoldCard) goldDeck.getCards().get(9);
//        SideOfCard card3= fullCard3.chooseSide(Side.BACK);
//        row3.add(card3);
//        row3.add(null);
//        row3.add(null);
//        cardsOnArea.add(row3);
//        PlayArea playArea=new PlayArea();
//        playArea.setCardsOnArea(cardsOnArea);
//        player1.setPlayArea(playArea);
//
//        String area=createBoxWithSpace(showOpponent(player1)+showOpponent(player1), "\033[30m", 0);
//        //int numberOfLines1 = area.split("\n").length;
//        String chat=createBoxWithSpace(formatStringList(strings), ANSI_CYAN, 0);
////        System.out.println(chat);
////        System.out.println(area);
//        String areaChat=concatString(area, chat, 20);
//        System.out.println(areaChat);
//        //int numberOfLines = area.split("\n").length;
//    }
}