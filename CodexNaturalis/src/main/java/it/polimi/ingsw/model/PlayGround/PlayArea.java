package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CornerPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Pair;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**This class represents the playArea. Each player has its own*/
public class PlayArea {
    /**
     * A map that contains all the symbols that are visible on the playArea. It is going to be used for
     * goldCards requirement check and to attribute ObjectiveCards points and goldCard points
     */
    private final Map<Symbol, Integer> symbols;


    /**
     * Dynamic matrix of SideOfCard. We are using SideOfCard, because, if the card is present here, is because the player has
     * already made a choice about what side they want to play
     */
    private List<List<SideOfCard>> cardsOnArea;


    /**
     * Class Constructor

     */
    public PlayArea() {
        cardsOnArea=new ArrayList<>();
        symbols = InitializeSymbolMap();
    }


    public int getNumSymbols(Symbol symbol) {
        return symbols.get(symbol);
    }

    public void setCardsOnArea(List<List<SideOfCard>> cards){
        this.cardsOnArea=cards;
    }


    /**
     * Method that is used to initialize the Symbols map of the playArea.
     * Populates the map with all the symbols from Symbol enum, and sets all their values to zero
     *
     * @return playAreaMap which is going to be our symbols map
     */
    public Map<Symbol, Integer> InitializeSymbolMap() {
        Map<Symbol, Integer> playAreaMap = new HashMap<>();
        for (Symbol symbol : Symbol.values()) {
            playAreaMap.put(symbol, 0);
        }
        return playAreaMap;

    }


    public SideOfCard getCardInPosition(int i, int j) {
        if(i<cardsOnArea.size() && j< cardsOnArea.get(0).size()) {
            List<SideOfCard> internalList = getCardsOnArea().get(i);
            return internalList.get(j);
        }
        else
            throw new ArrayIndexOutOfBoundsException("Invalid Position");
    }


    /**
     * Checks if the PlayArea has a column in the given position
     *
     * @param column the column whose existence we have to check
     * @return boolean true if the column exists, false if it doesn't
     */
    public boolean columnExists(int column) {
        return !cardsOnArea.getFirst().isEmpty() && column >= 0 && column < cardsOnArea.getFirst().size();
    }


    /**
     * Method that we are using to check if we are trying to access a row that does not exist
     *
     * @param row the row whose existence we want to check
     * @return exists boolean
     */
    public boolean rowExists(int row) {
        return row >= 0 && row < cardsOnArea.size();
    }


    /**
     * Once the Card is placed, we also want to check if it covers other corners besides the one the player has indicated.
     * We check the positions where the card can cover other cards without covering two corners of the same card.
     * If  a card in the searched position is found, get the right cover to corner, through the CornerPosition Enum and set the corner to covered
     * Update the NextCorner attribute: these corners are now each others next.
     * Update the symbols map: if the covered corner contained a symbol, now it is not visible on the map anymore
     *
     * @param newCard the card that has just been placed: this method is called after the card has been added to the PlayArea
     */
    public void checkCloseNeighbours(SideOfCard newCard) {
        for (Corner[] Rowcorner : newCard.getCorners()) {
            for (Corner corner : Rowcorner) {
                Pair<Integer, Integer> newPosition = corner.getPosition().PositionNewCard(newCard);
                if (newPosition != null) {
                    int rowToCheck = newPosition.getFirst();
                    int columnToCheck = newPosition.getSecond();
                    if (rowExists(rowToCheck) && columnExists(columnToCheck)) {
                        List<SideOfCard> row = cardsOnArea.get(rowToCheck);
                        if (row != null) {
                            SideOfCard neighbourCard = row.get(columnToCheck);
                            if (neighbourCard != null) {
                                CornerPosition cornerToCover = corner.getPosition().CoverCorners();
                                if (neighbourCard.getCornerInPosition(cornerToCover) != null) {
                                    neighbourCard.getCornerInPosition(cornerToCover).setCovered();
                                    Symbol coveredSymbol = neighbourCard.getCornerInPosition(cornerToCover).getSymbol();
                                    if (symbols.containsKey(coveredSymbol)) {
                                        symbols.put(coveredSymbol, symbols.getOrDefault(coveredSymbol, 0) - 1);
                                    }
                                    neighbourCard.getCornerInPosition(cornerToCover).setNextCorner(corner);
                                    corner.setNextCorner(neighbourCard.getCornerInPosition(cornerToCover));
                                }
                            }
                        }
                    }
                }
            }
        }
    }




    public void resetConfig() {
        for (List<SideOfCard> row :cardsOnArea)
            for (SideOfCard card : row) {
                if(card!=null) {
                    if (card.isInConfiguration())
                        card.setInConfiguration(false);
                }
            }
    }



    /**
     * Once the card has been placed, the symbols that the corners of the card contain, need to update the playArea symbols map
     *
     * @param NewCard card that has just been placed
     */
    public void AddSymbolsToArea(SideOfCard NewCard) {
        for (Symbol symbol : NewCard.getSymbols().keySet()) {
            int newValue = NewCard.getSymbols().get(symbol) + symbols.get(symbol);
            symbols.put(symbol, newValue);

        }
    }


    /**
     * Main method of the player's round. Once a player has chosen the card they want to play, and the side of the card, they have to place it on the area.
     * They can choose the corner they want to place the card on.
     * If the corner and the card position make up an edge case, the PlayArea has to be updated with new dimensions, the card can only be placed after this is done,
     * else the playArea stays the same and the card gets Placed in the chosen position.
     * The corner to cover is set to covered, and the nextCorners are set
     * Card symbols are added to the PlayAreaMap
     * Neighbours check
     *
     * @param NewCard       card the player wants to place
     * @param CornerToCover corner the player wants to place the card on
     */

    public void AddCardOnArea(SideOfCard NewCard, Corner CornerToCover, SideOfCard CoveredCard) {
        IsEdgeCase(CornerToCover, CoveredCard, cardsOnArea);

        NewCard.setPositionOnArea(CornerToCover.getPosition().PositionNewCard(CoveredCard));
        int i= CornerToCover.getPosition().PositionNewCard(CoveredCard).getFirst();

        int j=CornerToCover.getPosition().PositionNewCard(CoveredCard).getSecond();
        List<SideOfCard> row = cardsOnArea.get(i);
        row.set(j, NewCard);
        checkCloseNeighbours(NewCard);
        AddSymbolsToArea(NewCard);
    }



    /**Method that verifies if the placement of the new card in the desired position is an edge case
     * and the matrix needs to be Expanded
     * @param cardsOnArea cards on the playArea
     * @param CardToCover chosen By The Player
     * @param cornerToCover  also chosen by the player*/
    public void IsEdgeCase(Corner cornerToCover, SideOfCard CardToCover, List<List<SideOfCard>> cardsOnArea) {
        EdgePositions edgePositions=new EdgePositions();
        Map <EdgePositions.EdgeCases, ArrayList<CornerPosition>> cornersToCheck=edgePositions.cornersToCheck;
        for (EdgePositions.EdgeCases key : cornersToCheck.keySet()) {
            if (key.isEdgePosition(CardToCover, cardsOnArea)) {
                ArrayList<CornerPosition> value = cornersToCheck.get(key);
                for (CornerPosition corner : value) {
                    if (cornerToCover.getPosition().equals(corner)) {
                        key.ExpandArea(cardsOnArea);

                    }
                }

            }
        }
    }

    /**Getter method for Symbols attribute
     * @return  symbols  on the playArea*/
    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    /**Getter method for cardsOnArea attribute
     * @return cardsOnArea */

    public List<List<SideOfCard>> getCardsOnArea() {
        return cardsOnArea;
    }
}

