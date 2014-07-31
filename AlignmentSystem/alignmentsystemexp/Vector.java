/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class Vector {
    protected double[] vector;

    public Vector(int size){
        this.vector = new double[size];
    }

    public Vector(double[] vector){
        this.vector = vector;
    }

    public int getDimension(){
        return vector.length;
    }

    public double[] getArray(){
        return vector;
    }

    public Vector add(Vector other){
        if(vector.length != other.vector.length){
            // Todo Handle vector dimension mismatch by throwing a exception
            System.err.println("Vector dimension error");
            System.exit(0);
        }
        double[] res = new double[vector.length];
        for(int i=0; i<vector.length; i++){
            res[i] = vector[i] + other.vector[i];
        }
        return new Vector(res);
    }

    public Vector sub(Vector other){
        if(vector.length != other.vector.length){
            // Todo Handle vector dimension mismatch by throwing a exception
            System.err.println("Vector dimension error");
            System.exit(0);
        }
        double[] res = new double[vector.length];
        for(int i=0; i<vector.length; i++){
            res[i] = vector[i] - other.vector[i];
        }
        return new Vector(res);
    }

    public Vector scale(double scale){
        double[] res = new double[vector.length];
        for(int i=0; i<vector.length; i++){
            res[i] = scale * vector[i];
        }
        return new Vector(res);
    }

    public double dot(Vector other){
        if(vector.length != other.vector.length){
            // Todo Handle vector dimension mismatch by throwing a exception
            System.err.println("Vector dimension error");
            System.exit(0);
        }
        double res = 0.0;
        for(int i=0; i<vector.length; i++){
            res += vector[i] * other.vector[i];
        }
        return res;
    }

    /**
     * Computes the L_p distance for p >= 1
     * @param other
     * @return
     */
    public double LpDistance(Vector other, double p){
        if(vector.length != other.vector.length){
            // Todo Handle vector dimension mismatch by throwing a exception
            System.err.println("Vector dimension error");
            System.exit(0);
        }
        if(p < 1.0){
            // Todo Handle vector dimension mismatch by throwing a exception
            System.err.println("Cannot compute L_p distance for p < 1");
            System.exit(0);
        }
        double res = 0.0;
        for(int i=0; i<vector.length; i++){
            res += Math.pow(Math.abs(vector[i] - other.vector[i]), p);
        }
        return Math.pow(res, 1/p);
    }

    @Override
    public String toString(){
        String res = "";
        for(int i=0; i<vector.length-1; i++){
            res += vector[i] + " ";
        }
        res += vector[vector.length-1];
        return res;
    }

    /**
     * This method selects a random point in a ball of radius "radius" around
     * the center point "origin", it is implemented from the descriptions in:
     * http://en.wikipedia.org/wiki/Hypersphere#Hyperspherical_coordinates
     * OBS: This method seems to have numerical problems: the length of the
     * vector res is not always r as it should, but is often less.
     * @param radius
     * @param origin
     * @return
     */
    public static Vector randPointInBall(double radius, Vector origin){
        double r = Math.random() * radius;
        double[] angles = new double[origin.getDimension()-1];
        for(int i=0; i<angles.length; i++){
            angles[i] = Math.random() * 2 * Math.PI;
        }
        Vector res = new Vector(origin.getDimension());
        double[] resarray = res.getArray();
        for (int i = 0; i < resarray.length; i++) {
            resarray[i] = r;
        }
        for (int i = 0; i < resarray.length; i++) {
            for (int j = 0; j < i; j++) {
                resarray[i] *= Math.sin(angles[j]);
            }
            if(i <= angles.length-1){
                resarray[i] *= Math.cos(angles[i]);
            }else{
                resarray[i] *= Math.sin(angles[angles.length-1]);
            }
        }
        return res.add(origin);
    }

    public static Vector forcePoint(Vector vector){
        double[] v = vector.getArray();
        double[] newv = new double[v.length];
        for(int i=0; i<v.length; i++){
            newv[i] = v[i];
            if(newv[i] > 1.0){
                newv[i] = 1.0;
            }
            if(newv[i] < -1.0){
                newv[i] = -1.0;
            }
        }
        return new Vector(newv);
    }
}
