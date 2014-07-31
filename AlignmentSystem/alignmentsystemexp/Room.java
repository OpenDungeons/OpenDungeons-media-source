/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class Room {
    private RoomType type;
    private Dimension dimension;

    public Room(RoomType type, Dimension dimension){
        this.type = type;
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public RoomType getType() {
        return type;
    }

    /**
     * Checks if this room fulfills the requirements of the the other Room
     * @param other
     * @return
     */
    public boolean fulfillsRequirement(Room other){
        return (other.type == type && dimension.canContain(other.dimension))
                || other.dimension.isZero();
    }

    @Override
    public String toString(){
        return "" + type + " " + dimension;
    }
}
