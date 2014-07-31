/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class Dimension {
    // We maintain the invariant that height >= width
    private int height;
    private int width;

    public Dimension(int height, int width){
        if(height >= width){
            this.height = height;
            this.width = width;
        }else{
            this.height = width;
            this.width = height;
        }
    }

    public int area(){
        return height*width;
    }

    public boolean canContain(Dimension other){
        return height >= other.height && width >= other.width;
    }

    public boolean isZero(){
        return height == 0 && width == 0;
    }

    @Override
    public String toString(){
        return height + " " + width;
    }
}
