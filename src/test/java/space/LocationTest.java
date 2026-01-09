package space;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class LocationTest {
    @Test
    void testGets(){
        Location l1 = new Location(1, 3);
        assertEquals(1, l1.getRow());
        assertEquals(3, l1.getCol());;
    }

    @Test
    void testEquals(){
        Location l1 = new Location(0, 0);
        Location l2 = new Location(0, 0);

        assertTrue(l1.equals(l2));
    }
}
