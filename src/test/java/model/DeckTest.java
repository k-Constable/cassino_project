package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    void testGroupSize(){
        Deck deck = new Deck();
        assertEquals(deck.groupSize(), 52);
    }

    @Test 
    void testIterator(){
        Deck deck = new Deck();

        ArrayList<Card> items = new ArrayList<>();

        for(Card card : deck){
            items.add(card);
        }
        assertEquals(items.size(), 52);
    }

    @Test
    void testClearDeck(){
        Deck deck = new Deck();
        assertEquals(52, deck.groupSize());

        deck.clearDeck();

        assertEquals(0, deck.groupSize());
    }

    @Test
    void testFillDeck(){
        Deck deck = new Deck();
        deck.clearDeck();

        deck.fillDeck();

        assertEquals(52, deck.groupSize());
    }

    @Test
    void testDealTopCard(){
        Deck deck = new Deck();
        assertTrue(deck.dealTop() instanceof Card);
        assertEquals(51, deck.groupSize());
    }

    @Test
    void testIsEmpty(){
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());

        deck.clearDeck();
        assertTrue(deck.isEmpty());
    }

    @Test
    void testShuffle(){
        Deck deck = new Deck();

        ArrayList<Card> items = new ArrayList<>();
        ArrayList<Card> items2 = new ArrayList<>();

        for(Card card : deck){
            items.add(card);
        }
        
        deck.shuffle();

        for(Card card : deck){
            items2.add(card);
        }

        //Statistically the chances of the two lists being the same is 52!, which is a number so big that it's practically
        //impossible. Though technically, it could happen, so the test is not 100% tight.
        boolean areDifferent = false;
        for(int i = 0; i < items.size(); i++){
            if(!items.get(i).equals(items2.get(i))){
                areDifferent = true;
            }
        }


        assertTrue(areDifferent);
    }
}
