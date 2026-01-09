package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import space.Location;
import space.Space;

public class Table implements CardGroup{
    ArrayList<Space> spaces;
    public Table(){
        this.spaces = new ArrayList<>();
    }

    @Override
    public int groupSize(){
        return spaces.size();
    }

    @Override
    public Iterator<Space> iterator(){
        return this.spaces.iterator();
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
    public void set(Location location, Card card){
        spaces.add(new Space(location, card));
    }

    @Override
    public void removeSpace(Space space){
        spaces.remove(space);
    }

    @Override
    public void clearSpaces(){
        spaces.clear();
    }
}
