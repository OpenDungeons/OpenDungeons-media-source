/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class KeldarythAppearance implements AppearanceStrategy{
    public double appearProb(Keeper keeper, Creature creature) {
        // Keldaryths computation of creature appearance
        double denominator = 0.0;
        double nominator = 0.0;
        double tmpp = 1.0 / keeper.getCreaturePool().size()
                + keeper.getAlignment().dot(creature.getAlignment())
                + keeper.getAllies().dot(creature.getAllies());
        if(tmpp > 0){
            nominator = tmpp;
        }
        for(Creature c : keeper.getCreaturePool()){
            double tmp = 1.0/keeper.getCreaturePool().size()
                    + keeper.getAlignment().dot(c.getAlignment())
                    + keeper.getAllies().dot(c.getAllies());
            if(tmp > 0){
                denominator += tmp;
            }
        }
        return nominator/denominator;
    }
}
