package it.polimi.ingsw.Model.Cards;

import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.Side;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ResourceCardTest {

    @Test
    public void testGetPointFront() {
        Side chosenSide = Side.FRONT;
        boolean frontPoint = true;
        ResourceCard resourceCard = new ResourceCard(2, null, null, CardColors.BLUE, frontPoint);

        assertEquals(1, resourceCard.getPoints(chosenSide));
    }

    @Test
    public void testGetPointBack() {
        Side chosenSide = Side.BACK;
        boolean frontPoint = false;
        ResourceCard resourceCard = new ResourceCard(2, null, null, CardColors.BLUE, frontPoint);
        assertEquals(0, resourceCard.getPoints(chosenSide));
    }







}