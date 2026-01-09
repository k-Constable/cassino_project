package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import space.Location;
import space.Space;
import view.CassinoView;

public class CassinoModelTest {
    @Test
    void testGetHand(){
        CassinoModel model = new CassinoModel();
        ArrayList<Card> cards = new ArrayList<>();
        for(Space space : model.getHand1()){
            cards.add(space.getCard());
        }

        assertEquals(4, cards.size());

        model.getHand1().removeSpace(model.getHand1().getSpace(0));
        ArrayList<Card> newCards = new ArrayList<>();
        for(Space space : model.getHand1()){
            newCards.add(space.getCard());
        }

        assertEquals(3, newCards.size());
    }

    @Test
    void testGetGameState(){
        CassinoModel model = new CassinoModel();
        assertEquals(GameState.PLAYER1, model.getGameState());
    }

    @Test
    void testDealToPlayers(){
        CassinoModel model = new CassinoModel();
        
        assertEquals(4, model.getHand1().groupSize());
        assertEquals(4, model.getHand2().groupSize());

        model.getHand1().clearSpaces();
        model.getHand2().clearSpaces();
        
        ArrayList<Space> spaces = new ArrayList<>();
        for(Space space : model.getHand1()){
            spaces.add(space);
        }
        for(Space space : model.getHand2()){
            spaces.add(space);
        }
        assertEquals(0, spaces.size());

        model.dealToPlayers();

        assertEquals(4, model.getHand1().groupSize());
        assertEquals(4, model.getHand2().groupSize());

        for(Space space : model.getHand1()){
            spaces.add(space);
        }
        for(Space space : model.getHand2()){
            spaces.add(space);
        }
        assertEquals(8, spaces.size());
    }

    @Test
    void testCollectCard(){
        CassinoModel model = new CassinoModel();
        model.getHand1().clearSpaces();
        model.getTable().clearSpaces();
        assertEquals(0, model.getHand1().groupSize());
        assertEquals(0, model.getTable().groupSize());

        model.getHand1().set(new Location(0, 0), new Card('S', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        assertEquals(1, model.getHand1().groupSize());
        assertEquals(1, model.getTable().groupSize());

        assertEquals(0, model.getHand1().getCardCount());
        assertEquals(0, model.getHand1().getSpadeCount());

        ArrayList<Space> spaces = new ArrayList<>();
        spaces.add(new Space(new Location(0, 0), new Card('S', 9)));
        spaces.add(new Space(new Location(0, 1), new Card('D', 9)));

        assertTrue(model.collectCard(spaces));

        assertEquals(2, model.getHand1().getCardCount());
        assertEquals(1, model.getHand1().getSpadeCount());

        assertEquals(0, model.getHand1().groupSize());
        assertEquals(0, model.getTable().groupSize());

        assertEquals(GameState.PLAYER2, model.getGameState());
    }

    @Test
    void testGetNextTableCol(){
        CassinoModel model = new CassinoModel();
        model.getTable().clearSpaces();
        model.getTable().set(new Location(0, 0), new Card('D', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        model.getTable().set(new Location(0, 2), new Card('D', 9));

        int nextTableCol = model.getNextTableCol();
        assertEquals(3, nextTableCol);
    }

    @Test
    void testGetStack(){
        CassinoModel model = new CassinoModel();
        model.getTable().clearSpaces();
        model.getTable().set(new Location(0, 0), new Card('D', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        model.getTable().set(new Location(0, 2), new Card('D', 9));
        model.getTable().set(new Location(1, 1), new Card('D', 9));
        model.getTable().set(new Location(2, 1), new Card('D', 9));
        

        ArrayList<Space> stack = model.getStack(1);
        assertEquals(3, stack.size());
        assertTrue(stack.contains(new Space(new Location(0, 1), new Card('D', 9))));
        assertTrue(stack.contains(new Space(new Location(1, 1), new Card('D', 9))));
        assertTrue(stack.contains(new Space(new Location(2, 1), new Card('D', 9))));
    }

    @Test
    void testPlaceCard(){
        CassinoModel model = new CassinoModel();
        model.getHand1().clearSpaces();
        model.getTable().clearSpaces();
        assertEquals(0, model.getHand1().groupSize());
        assertEquals(0, model.getTable().groupSize());

        model.getHand1().set(new Location(0, 0), new Card('S', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        assertEquals(1, model.getHand1().groupSize());
        assertEquals(1, model.getTable().groupSize());

        assertEquals(0, model.getHand1().getCardCount());
        assertEquals(0, model.getHand1().getSpadeCount());

        ArrayList<Space> spaces = new ArrayList<>();
        spaces.add(new Space(new Location(0, 0), new Card('S', 9)));

        assertTrue(model.placeCard(spaces));

        assertEquals(0, model.getHand1().groupSize());
        assertEquals(2, model.getTable().groupSize());

        assertEquals(GameState.PLAYER2, model.getGameState());
    }

    @Test
    void testStackCard(){
        CassinoModel model = new CassinoModel();
        model.getHand1().clearSpaces();
        model.getTable().clearSpaces();
        assertEquals(0, model.getHand1().groupSize());
        assertEquals(0, model.getTable().groupSize());

        model.getHand1().set(new Location(0, 0), new Card('S', 9));
        model.getHand1().set(new Location(0, 0), new Card('H', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        assertEquals(2, model.getHand1().groupSize());
        assertEquals(1, model.getTable().groupSize());

        ArrayList<Space> spaces = new ArrayList<>();
        spaces.add(new Space(new Location(0, 0), new Card('S', 9)));
        spaces.add(new Space(new Location(0, 1), new Card('D', 9)));

        assertTrue(model.stackCard(spaces, 9));

        assertEquals(1, model.getHand1().groupSize());
        assertEquals(2, model.getTable().groupSize());

        assertEquals(GameState.PLAYER2, model.getGameState());

        model.getHand2().clearSpaces();
        model.getHand2().set(new Location(0, 0), new Card('S', 3));
        model.getHand2().set(new Location(0, 1), new Card('S', 5));
        model.getTable().set(new Location(0, 2), new Card('D', 2));

        ArrayList<Space> spaces2 = new ArrayList<>();
        spaces2.add(new Space(new Location(0, 0), new Card('S', 3)));
        spaces2.add(new Space(new Location(0, 2), new Card('D', 2)));

        assertTrue(model.stackCard(spaces2, 5));

        assertEquals(1, model.getHand2().groupSize());
        assertEquals(4, model.getTable().groupSize());

        assertEquals(GameState.PLAYER1, model.getGameState());
    }

    @Test
    void testStackTableCard(){
        CassinoModel model = new CassinoModel();
        model.getHand1().clearSpaces();
        model.getTable().clearSpaces();
        assertEquals(0, model.getHand1().groupSize());
        assertEquals(0, model.getTable().groupSize());

        model.getHand1().set(new Location(0, 0), new Card('S', 9));
        model.getTable().set(new Location(0, 0), new Card('H', 9));
        model.getTable().set(new Location(0, 1), new Card('D', 9));
        assertEquals(1, model.getHand1().groupSize());
        assertEquals(2, model.getTable().groupSize());

        ArrayList<Space> spaces = new ArrayList<>();
        spaces.add(new Space(new Location(0, 0), new Card('H', 9)));
        spaces.add(new Space(new Location(0, 1), new Card('D', 9)));

        assertTrue(model.stackCardsOnTable(spaces, 9));

        assertEquals(1, model.getHand1().groupSize());
        assertEquals(2, model.getTable().groupSize());

        assertEquals(GameState.PLAYER1, model.getGameState());

        model.getHand1().set(new Location(0, 1), new Card('S', 5));
        model.getTable().set(new Location(0, 3), new Card('S', 3));
        model.getTable().set(new Location(0, 4), new Card('D', 2));

        ArrayList<Space> spaces2 = new ArrayList<>();
        spaces2.add(new Space(new Location(0, 3), new Card('S', 3)));
        spaces2.add(new Space(new Location(0, 4), new Card('D', 2)));

        assertTrue(model.stackCardsOnTable(spaces2, 5));

        assertEquals(2, model.getHand1().groupSize());
        assertEquals(4, model.getTable().groupSize());

        assertEquals(GameState.PLAYER1, model.getGameState());
    }
}
