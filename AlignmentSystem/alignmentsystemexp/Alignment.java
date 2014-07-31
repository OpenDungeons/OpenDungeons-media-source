/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class Alignment{
    private Vector vector;

    public Alignment(){
        vector = new Vector(3);
    }

    public Alignment(Vector vec){
        vector = vec;
    }

    public Alignment(double[] vec){
        vector = new Vector(vec);
    }

    public Alignment(double AE, double OC, double PV){
        vector = new Vector(new double[]{ AE, OC, PV});
    }

    public int getDimension(){
        return vector.getDimension();
    }

    public double[] getArray(){
        return vector.getArray();
    }

    public Alignment add(Alignment other){
        return new Alignment(vector.add(other.vector));
    }

    public Alignment sub(Alignment other){
        return new Alignment(vector.sub(other.vector));
    }

    public Alignment scale(double scale){
        return new Alignment(vector.scale(scale));
    }

    public double dot(Alignment other){
        return vector.dot(other.vector);
    }

    public double LpDistance(Alignment other, double p){
        return vector.LpDistance(other.vector, p);
    }

    public static Alignment randPointInBall(double radius, Alignment origin){
        return new Alignment(Vector.randPointInBall(radius, origin.vector));
    }

    public static Alignment forcePoint(Alignment vector){
        return new Alignment(Vector.forcePoint(vector.vector));
    }

    @Override
    public String toString(){
        return vector.toString();
    }
}
