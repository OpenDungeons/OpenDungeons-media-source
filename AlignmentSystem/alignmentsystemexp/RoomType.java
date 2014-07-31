/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public enum RoomType {
    // Basic rooms that every keeper will have access to
    Room1(new Alignment(0.1, 0.3, -0.2), Double.POSITIVE_INFINITY),
    Room2(new Alignment(0.1, 0.2, 0.4), Double.POSITIVE_INFINITY),
    Room3(new Alignment(0.4, 0.25, 0.8), Double.POSITIVE_INFINITY),
    Room4(new Alignment(0.85, 0.3, 0.0), Double.POSITIVE_INFINITY),
    Room5(new Alignment(0.5, -0.5, -0.5), Double.POSITIVE_INFINITY),
    // Rooms that are available to most keepers, if their alingment is not
    // too far from the rooms
    Room6(new Alignment(0.3, 0.25, -0.6), 0.6),
    Room7(new Alignment(-0.4, -0.2, 0.1), 0.5),
    Room8(new Alignment(0.15, -0.23, -0.41), 0.5),
    Room9(new Alignment(-0.71, 0.21, -0.05), 0.4),
    Room10(new Alignment(0.42, -0.22, -0.61), 0.3),
    Room11(new Alignment(-0.31, -0.12, -0.07), 0.3),
    Room12(new Alignment(-0.17, -0.34, 0.11), 0.35),
    Room13(new Alignment(0.15, -0.6, 0.53), 0.4),
    Room14(new Alignment(0.13, 0.59, -0.9), 0.45),
    // Faction specific rooms, the keeper must have an alignment very close to
    // the average alingment of the faction
    Room15(new Alignment(0.0, 0.0, 0.05), 0.1),
    Room16(new Alignment(0.5, 0.50, 0.65), 0.1),
    Room17(new Alignment(0.1, -0.65, 0.65), 0.1),
    Room18(new Alignment(-0.65, 0.50, 0.60), 0.1),
    Room19(new Alignment(0.60, -0.20, -0.55), 0.1),
    Room20(new Alignment(-0.50, -0.05, -0.65), 0.1);

    private Alignment alignment;
    private double radius;

    public Alignment getAlignment(){
        return alignment;
    }

    public double getRadius() {
        return radius;
    }

    private RoomType(Alignment alignment, double radius){
        this.alignment = alignment;
        this.radius = radius;
    }
}
