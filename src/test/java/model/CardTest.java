package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    void testGets(){
        Card c1 = new Card('H', 7);
        assertEquals('H', c1.getSuit());
        assertEquals(7, c1.getValue());
    }

    @Test
    void testEquals(){
        Card c1 = new Card('S', 9);
        Card c2 = new Card('S', 9);

        assertTrue(c1.equals(c2));
    }
}
