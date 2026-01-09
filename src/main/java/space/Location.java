package space;

public class Location {
    private int row;
    private int col;

    public Location(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row of the location.
     * @return an Integer representing the row of the location.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Gets the column of the location.
     * @return an Integer representing the column of the location
     */
    public int getCol(){
        return this.col;
    }

    @Override
    public boolean equals(Object l){
        if(this == l){
            return true;
        }

        if(l == null || getClass() != l.getClass()){
            return false;
        }

        Location location = (Location) l;
        return row == location.row && col == location.col;
    }
}
