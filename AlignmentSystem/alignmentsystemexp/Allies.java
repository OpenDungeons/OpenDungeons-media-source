/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class Allies{
    private Vector vector;

    public Allies(){
        vector = new Vector(6);
    }

    public Allies(Vector vec){
        vector = vec;
    }

    public Allies(double[] vec){
        vector = new Vector(vec);
    }

    public Allies(double human, double corpar, double construct, double undead, double elve, double merc){
        vector = new Vector(new double[]{ human, corpar, construct, undead, elve, merc});
    }

    public int getDimension(){
        return vector.getDimension();
    }

    public double[] getArray(){
        return vector.getArray();
    }

    public Allies add(Allies other){
        return new Allies(vector.add(other.vector));
    }

    public Allies sub(Allies other){
        return new Allies(vector.sub(other.vector));
    }

    public Allies scale(double scale){
        return new Allies(vector.scale(scale));
    }

    public double dot(Allies other){
        return vector.dot(other.vector);
    }

    public double LpDistance(Allies other, double p){
        return vector.LpDistance(other.vector, p);
    }

    public static Allies randPointInBall(double radius, Allies origin){
        return new Allies(Vector.randPointInBall(radius, origin.vector));
    }

    public static Allies forcePoint(Allies vector){
        return new Allies(Vector.forcePoint(vector.vector));
    }

    @Override
    public String toString(){
        return vector.toString();
    }
}
