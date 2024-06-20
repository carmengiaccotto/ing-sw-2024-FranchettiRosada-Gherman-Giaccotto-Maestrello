package it.polimi.ingsw.View.TUI.TUIUtilis;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Side;
import it.polimi.ingsw.Model.PlayGround.Deck;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.TUI.GraphicUsage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass.printCard;

public class TUIComponents {
    private static final String bold = "\033[1m";
    private static final String reset = "\033[0m";
    private static final int cardHeight=7;
    private static final int cardWidth=25;
    private static final int RowDimensions=cardHeight-3;
    private static final int ColumnDimensions=cardWidth-4;

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

    /**Full method to show the opponent, with reduced playArea, info, and the back of the card they have in hand*/
    public static String showOpponent(Player player){
        String[][] matrix= new String[2][1];
        String s=showPlayerInfo(player);
        matrix[0][0]=s;
        if(player.getPlayArea()!=null)
            matrix[1][0]=DrawOthersPlayArea(player.getPlayArea());
        else
            matrix[1][0]="";
        return ConvertToString(matrix);
    }

    /**Method used to see the player's hand of cards and PlayArea*/
    public static String showMySelf(Player player){
        String[][] matrix= new String[4][1];
        String me;
        String s=showPlayerInfo(player);
        matrix[0][0]=s;
        if(player.getPersonalObjectiveCard()!=null&& player.getCardsInHand()!=null && player.getPlayArea()!=null) {
            String objective = printObjectives(player.getPersonalObjectiveCard());
            String hand = showMyCardsInHand(player.getCardsInHand());
            String ObjHand = concatString(objective, hand, 5);
            matrix[1][0] = hand;

            matrix[2][0] = "\n" + bold + "YOUR PLAY AREA " + reset + "\n";
            String area = DrawMyPlayArea(player.getPlayArea());
            matrix[3][0] = area;
        }
        else{
            matrix[1][0]="";
            matrix[2][0]="";
            matrix[3][0]="";
        }
          me=ConvertToString(matrix);
            return me;
    }

    /**method used to see the commonCards on the PlayGround*/
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
        String commonGold= concatString(DesignSupportClass.concatString ( printCommonCard(goldCards.get(0), Side.FRONT), printCommonCard(goldCards.get(1), Side.FRONT),
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

    public static String printObjectives(ObjectiveCard card) {
        if (card.getClass().equals(DispositionObjectiveCard.class))
            return DesignSupportClass.printDispositionObjectiveCard((DispositionObjectiveCard) card);
        else if (card.getClass().equals(SymbolObjectiveCard.class))
            return DesignSupportClass.printSymbolObjectiveCard((SymbolObjectiveCard) card);
        else return "";

    }



    public static String printCommonCard(PlayCard card, Side side){
        String[][] matrix = new String[cardHeight][cardWidth];
        return getCardString(matrix, card, cardHeight, cardWidth, side);
    }

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
    public static String concatString(String s1, String s2, int space) {
        String[] s1Lines = s1.split("\n");
        String[] s2Lines = s2.split("\n");
        StringBuilder sb = new StringBuilder();
        String[] shortS = s1Lines.length > s2Lines.length ? s2Lines : s1Lines;
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
                sb.append(s2Lines[i]).append("\n");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Deck resourceDeck= new Deck (ResourceCard.class);
        Deck ObjectiveDeck= new Deck (ObjectiveCard.class);
        Player player1= new Player();
        player1.setNickname("Player1");
        player1.setPawnColor(PawnColor.BLUE);
        player1.addCardToHand((ResourceCard) resourceDeck.drawCard());
        player1.addCardToHand((PlayCard) resourceDeck.drawCard());
        player1.addCardToHand((PlayCard) resourceDeck.drawCard());
        player1.setPersonalObjectiveCard((ObjectiveCard) ObjectiveDeck.drawCard());
        System.out.println(showMySelf(player1));
        System.out.println(showOpponent(player1));
    }
}