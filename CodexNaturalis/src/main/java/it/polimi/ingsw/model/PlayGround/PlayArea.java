package CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.Corner;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Cards.SideOfCard;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.CornerPosition;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.EdgePositions.cornersToCheck;

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
    private final List<List<SideOfCard>> cardsOnArea;


    /**
     * Class Constructor
     *
     * @param cardsOnArea it's going to be set to null, because when a new PlayArea is created, no card has been placed
     *                    uses InitializeSymbolMap method to initialize symbols
     */
    public PlayArea(List<List<SideOfCard>> cardsOnArea) {
        this.cardsOnArea = cardsOnArea;
        symbols = InitializeSymbolMap();
    }


    public int getNumSymbols(Symbol symbol) {
        return symbols.get(symbol);
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
        List<SideOfCard> internalList = getCardsOnArea().get(i);
        return internalList.get(j);
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
                int rowToCheck = corner.getPosition().PositionNewCard(newCard).getFirst();
                int columnToCheck = corner.getPosition().PositionNewCard(newCard).getSecond();
                if (rowExists(rowToCheck)) {
                    List<SideOfCard> row = cardsOnArea.get(rowToCheck);
                    if (columnExists(columnToCheck)) {
                        if (row.get(columnToCheck) != null) {
                            SideOfCard neighbourCard = row.get(columnToCheck);
                            CornerPosition cornerToCover = corner.getPosition().CoverCorners();
                            neighbourCard.getCornerInPosition(cornerToCover).setCovered();
                            Symbol coveredSymbol = neighbourCard.getCornerInPosition(cornerToCover).getSymbol();
                            symbols.put(coveredSymbol, symbols.get(coveredSymbol) - 1);
                            neighbourCard.getCornerInPosition(cornerToCover).setNextCorner(corner);
                            corner.setNextCorner(neighbourCard.getCornerInPosition(cornerToCover));

                        }

                    }
                }
            }
        }
    }




    public void resetConfig() {
        for (List<SideOfCard> row :cardsOnArea)
            for (SideOfCard card : row) {
                if (card.isInConfiguration())
                    card.setInConfiguration(false);
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

    public void AddCardOnArea(SideOfCard NewCard, Corner CornerToCover) {
        IsEdgeCase(CornerToCover, NewCard, cardsOnArea);
        SideOfCard CoveredCard = CornerToCover.getParentCard();
        NewCard.setPositionOnArea(CornerToCover.getPosition().PositionNewCard(CoveredCard));
        checkCloseNeighbours(NewCard);
        AddSymbolsToArea(NewCard);
    }


    public void IsEdgeCase(Corner cornerToCover, SideOfCard CardToCover, List<List<SideOfCard>> cardsOnArea) {
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

    public Map<Symbol, Integer> getSymbols() {
        return symbols;
    }

    public List<List<SideOfCard>> getCardsOnArea() {
        return cardsOnArea;
    }
}

