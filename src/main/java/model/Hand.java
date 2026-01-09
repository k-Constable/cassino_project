package model;

import java.util.ArrayList;
import java.util.Iterator;

import space.Location;
import space.Space;

public class Hand implements CardGroup{
    private ArrayList<Space> spaces;
    private int cardCount;
    private int spadeCount;
    private int points;
    public Hand(){
        this.spaces = new ArrayList<>();
        this.cardCount = 0;
        this.spadeCount = 0;
        this.points = 0;
    }

    /**
     * Gets the count of how many cards this hand has collected.
     * @return an Integer representing the total amount of cards this hand has collected.
     */
    public int getCardCount(){
        return cardCount;
    }

    /**
     * Gets the count of how many cards this hand has collected that are spades.
     * @return an Integer representing the total amount of spades this hand has collected.
     */
    public int getSpadeCount(){
        return spadeCount;
    }

    /**
     * Gets the count of how many points this hand has collected.
     * @return an Integer representing the total amount of points this hand has.
     */
    public int getPoints(){
        return points;
    }

    @Override
    public int groupSize(){
        return spaces.size();
    }

    @Override
    public boolean isEmpty(){
        if(this.groupSize() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Iterator<Space> iterator(){
        return spaces.iterator();
    }

    @Override
    public void set(Location location, Card card){
        spaces.add(new Space(location, card));
    }

    @Override
    public void removeSpace(Space space){
        spaces.remove(space);
    }

    /**
     * Gets the Space corresponding to the given index in the ArrayList of Spaces.
     * @param index, an integer representing the index of the Space to get.
     * @return the Space with the given index in the ArrayList of Spaces the hand contains.
     */
    public Space getSpace(int index){
        return spaces.get(index);
    }

    @Override
    public void clearSpaces(){
        spaces.clear();
    }

    /**
     * Adds the given amount of cards to the total cardcount of the hand.
     * @param addedCards, an Integer representing the amount of cards to add to the count.
     */
    public void addToCardCount(int addedCards){
        this.cardCount += addedCards;
    }

    /**
     * Adds the given amount of cards that are spades to the total spadecount of the hand.
     * @param addedSpades, an Integer representing the amount of cards to add to the count.
     */
    public void addToSpadeCount(int addedSpades){
        this.spadeCount += addedSpades;
    }

    /**
     * Adds the given amount of points to the total points of the hand.
     * @param addedPoints, an Integer representing the amount of points to add to the count.
     */
    public void addToPoints(int addedPoints){
        this.points += addedPoints;
    }
}
