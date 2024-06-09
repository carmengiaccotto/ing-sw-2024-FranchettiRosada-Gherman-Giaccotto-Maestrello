package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.ObjectivePoints;
import it.polimi.ingsw.Model.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SymbolObjectiveCardTest {
    private static SymbolObjectiveCard symbolObjectiveCard;

    @BeforeAll
    static void setUp() {
        HashMap<Symbol, Integer> goal = new HashMap<>();
        goal.put(Symbol.MANUSCRIPT, 3);
        symbolObjectiveCard = new SymbolObjectiveCard(1, ObjectivePoints.THREE, goal);
    }

    @Test
    void checkGoals() {
        HashMap<Symbol, Integer> symbols = new HashMap<>();
        symbols.put(Symbol.MANUSCRIPT, 3);
        assertEquals(1, symbolObjectiveCard.CheckGoals(symbols));
    }

    @Test
    void getGoal() {
        HashMap<Symbol, Integer> goal = new HashMap<>();
        goal.put(Symbol.MANUSCRIPT, 3);
        assertEquals(goal, symbolObjectiveCard.getGoal());

    }
}