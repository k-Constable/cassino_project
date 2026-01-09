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

public class HandTest {
    @Test
    void testSet(){
        Hand hand = new Hand();
        hand.set(new Location(0, 0), new Card('S', 9));
        hand.set(new Location(0, 1), new Card('D', 9));
        hand.set(new Location(0, 2), new Card('C', 9));
        hand.set(new Location(0, 3), new Card('H', 9));

        assertTrue(hand.getSpace(0).getCard().getSuit() == 'S');
        assertTrue(hand.getSpace(1).getCard().getSuit() == 'D');
        assertTrue(hand.getSpace(2).getCard().getSuit() == 'C');
        assertTrue(hand.getSpace(3).getCard().getSuit() == 'H');
    }

    @Test
    void testGroupSize(){
        Hand hand = new Hand();
        hand.set(new Location(0, 0), new Card('S', 9));
        hand.set(new Location(0, 1), new Card('D', 9));
        hand.set(new Location(0, 2), new Card('C', 9));
        hand.set(new Location(0, 3), new Card('H', 9));

        assertEquals(hand.groupSize(), 4);
    }

    @Test
    void testIsEmpty(){
        Hand hand = new Hand();
        assertTrue(hand.isEmpty());

        hand.set(new Location(0, 0), new Card('S', 9));
        assertFalse(hand.isEmpty());
    }

    @Test
    void testRemoveSpace(){
        Hand hand = new Hand();
        hand.set(new Location(0, 0), new Card('S', 9));
        hand.set(new Location(0, 1), new Card('D', 9));

        hand.removeSpace(new Space(new Location(0, 0), new Card('S', 9)));

        assertEquals(1, hand.groupSize());
        assertTrue(hand.getSpace(0).getCard().getSuit() == 'D');
    }

    @Test
    void testIterator(){
        Hand hand = new Hand();
        hand.set(new Location(0, 0), new Card('S', 9));
        hand.set(new Location(0, 1), new Card('D', 9));
        hand.set(new Location(0, 2), new Card('C', 9));
        hand.set(new Location(0, 3), new Card('H', 9));

        ArrayList<Space> spaceList = new ArrayList<>();
        for(Space space : hand){
            spaceList.add(space);
        }

        assertEquals(4, spaceList.size());
        assertTrue(spaceList.contains(new Space(new Location(0, 0), new Card('S', 9))));
        assertTrue(spaceList.contains(new Space(new Location(0, 1), new Card('D', 9))));
        assertTrue(spaceList.contains(new Space(new Location(0, 2), new Card('C', 9))));
        assertTrue(spaceList.contains(new Space(new Location(0, 3), new Card('H', 9))));

        hand.removeSpace(new Space(new Location(0, 0), new Card('S', 9)));
        ArrayList<Space> newSpaceList = new ArrayList<>();
        for(Space space : hand){
            newSpaceList.add(space);
        }

        assertEquals(3, newSpaceList.size());
        assertTrue(newSpaceList.contains(new Space(new Location(0, 1), new Card('D', 9))));
        assertTrue(newSpaceList.contains(new Space(new Location(0, 2), new Card('C', 9))));
        assertTrue(newSpaceList.contains(new Space(new Location(0, 3), new Card('H', 9))));
    }

    @Test
    void testAddGetPoints(){
        Hand hand = new Hand();

        //Spadecounts
        assertEquals(0, hand.getSpadeCount());
        hand.addToSpadeCount(3);
        assertEquals(3, hand.getSpadeCount());

        //Cards
        assertEquals(0, hand.getCardCount());
        hand.addToCardCount(2);
        assertEquals(2, hand.getCardCount());

        //Points
        assertEquals(0, hand.getPoints());
        hand.addToPoints(4);
        assertEquals(4, hand.getPoints());
    }

    @Test
    void testGetSpace(){
        Hand hand = new Hand();
        hand.set(new Location(0, 2), new Card('D', 9));
        hand.set(new Location(0, 5), new Card('S', 9));

        assertEquals(new Space(new Location(0, 5), new Card('S', 9)), hand.getSpace(1));
    }

    @Test
    void testClearHand(){
        Hand hand = new Hand();
        hand.set(new Location(0, 2), new Card('D', 9));
        hand.set(new Location(0, 5), new Card('S', 9));

        assertEquals(2, hand.groupSize());
        hand.clearSpaces();
        assertEquals(0, hand.groupSize());
    }
}
