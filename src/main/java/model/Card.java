package model;

public class Card {
    private Character suit;
    private int value;

    public Card(Character suit, int value){
        this.suit = suit;
        this.value = value;
    }

    /**
     * Gets the suit of the card (eg. 'D' for Diamond, 'S' for Spades etc.)
     * @return a Character representing the suit of the card.
     */
    public Character getSuit(){
        return this.suit;
    }

    /**
     * Gets the value of the card (the values of Aces are 1. Jacks, Queens and Kings are 11, 12 and 13 respectively).
     * @return an Integer representing the value of the card.
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Turns the suit and value of the card into one string, eg. 9 of hearts becomes "9H"
     * @return a String representing the value and suit of the card.
     */
    public String toString(){
        String convertedInt = String.valueOf(this.value);
        return convertedInt + "-" + this.suit;
    }

    /**
     * Creates a path to the imagefile that corresponds to the card.
     * @return a String representing the filepath of the image of the card.
     */
    public String getFileLocation(){
        return "./cards/" + this.toString() + ".png";
    }

    @Override
    public boolean equals(Object c){
        if(this == c){
            return true;
        }

        if(c == null || getClass() != c.getClass()){
            return false;
        }

        Card card = (Card) c;
        return this.suit == card.suit && this.value == card.value;
    }
}
