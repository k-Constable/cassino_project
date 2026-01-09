package space;
import javax.swing.JButton;

import model.Card;

public class Space extends JButton{
    private Location location;
    private Card card;

    public Space(Location location, Card card){
        this.location = location;
        this.card = card;
    }

    /**
     * Gets the location of the Space. (The name getLocation is used by a built in Java method that I did not wish to override)
     * @return a Location object representing the location this Space inhabits in a cardgroup.
     */
    public Location getPlace(){
        return this.location;
    }
  
    /**
     * Gets the card the Space contains.
     * @return the Card object the Space contains.
     */
    public Card getCard(){
        return this.card;
    }

    @Override
    public boolean equals(Object s){
        if(this == s){
            return true;
        }

        if(s == null || getClass() != s.getClass()){
            return false;
        }

        Space space = (Space) s;
        return location.equals(space.location) && card.equals(space.card);
    }
}
