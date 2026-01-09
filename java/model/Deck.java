package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Deck implements Iterable<Card>{
    ArrayList<Card> cards;
    
    public Deck(){
        this.cards = new ArrayList<Card>();
        //Filling the deck with all the cards:
        this.fillDeck();

        /*
        cards.add(new Card('S', 2));
        cards.add(new Card('S', 2));
        cards.add(new Card('S', 2));
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));        
        cards.add(new Card('S', 2));     
        */   
    }

    /**
     * Adds all 52 cards in a regular deck of playing cards to our deck.
     */
    public void fillDeck(){
        //Hearts
        for(int i = 1; i < 14; i++){
            Card newCard = new Card('H', i);
            cards.add(newCard);
        }

        //Diamonds
        for(int i = 1; i < 14; i++){
            Card newCard = new Card('D', i);
            cards.add(newCard);
        }

        //Spades
        for(int i = 1; i < 14; i++){
            Card newCard = new Card('S', i);
            cards.add(newCard);
        }

        //Clubs
        for(int i = 1; i < 14; i++){
            Card newCard = new Card('C', i);
            cards.add(newCard);
        }
    }

    /**
     * Removes all cards from the deck
     */
    public void clearDeck(){
        cards.clear();
    }


    /**
     * Gets the top card in the deck and removes it from the deck
     * @return the top Card in the deck
     */
    public Card dealTop(){
        Card topCard = this.cards.get(0);
        this.cards.remove(topCard);
        return topCard;
    }

    /**
     * Checks whether the deck is empty.
     * @return true if the deck is empty, false if not.
     */
    public boolean isEmpty(){
        if(this.groupSize() > 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks how many cards are in the deck.
     * @return an Integer representing the amount of cards currently in the deck.
     */
    public int groupSize(){
        return cards.size();
    }

    /**
     * Shuffles the deck of cards (randomizing order).
     */
    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    @Override
    public Iterator<Card> iterator(){
        return this.cards.iterator();
    }

}
