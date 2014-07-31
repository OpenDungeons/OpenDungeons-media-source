/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class TestAppearance implements AppearanceStrategy{
    private double cutOffRadius;

    public TestAppearance(double radius){
        this.cutOffRadius = radius;
    }

    public double appearProb(Keeper keeper, Creature creature) {
        // My computation of creature appearance
        double denominator = 0.0;
        double nominator = 1.0 /(creature.getAlignment().LpDistance(keeper.getAlignment(), 2.0)+1);
        for(Creature c : keeper.getCreaturePool()){
            if(c.getAlignment().LpDistance(keeper.getAlignment(), 2.0) <= cutOffRadius){
                denominator += 1.0 /(c.getAlignment().LpDistance(keeper.getAlignment(), 2.0)+1);
            }
        }
        if(denominator == 0.0){
            //System.out.println(1.0 / keeper.getCreaturePool().size());
            return 1.0 / keeper.getCreaturePool().size();
        }
        //System.out.println(nominator/denominator);
        return nominator/denominator;
    }
}
