package model;

import java.util.ArrayList;

import space.Location;
import space.Space;
public interface CardGroup extends Iterable<Space>{

    /**
     * Returns the amount of cards in the group.
     * @return an integer of the amount of cards in the group.
     */
    int groupSize();

    /**
     * Checks if our group of cards is empty
     * @return A boolean, false if our cardgroup contains cards, true if not.
     */
    boolean isEmpty();

    /**
     * Sets a new Space in the cardgroup
     * @param A location object representing the location space will inhabit
     * @param A card object the space will contain
     */
    void set(Location location, Card card);

    /**
     * Removes a the space given as a parameter from the cardgroup
     * @param space, the Space object that will be removed.
     */
    void removeSpace(Space space);

    /**
     * Empties the list of the Spaces the cardgroup contains.
     */
    void clearSpaces();
}