package space;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;

public class SpaceTest {
    @Test
    void testGets(){
        Space s1 = new Space(new Location(0, 1), new Card('S', 9));
        assertEquals(new Location(0, 1), s1.getPlace());
        assertEquals(new Card('S', 9), s1.getCard());
    }

    @Test
    void testEquals(){
        Space s1 = new Space(new Location(0, 1), new Card('S', 9));
        Space s2 = new Space(new Location(0, 1), new Card('S', 9));

        assertTrue(s1.equals(s2));
    }
}
