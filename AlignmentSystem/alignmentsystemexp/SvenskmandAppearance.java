/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

/**
 *
 * @author ckr
 */
public class SvenskmandAppearance implements AppearanceStrategy{
    private double steepness;
    private double radius;
    // Adjusts how much the factional mix should influence the probability:
    // 1.0 total influence, 0.0 no influence
    private double mix;

    public SvenskmandAppearance(double steepness, double radius, double mix){
        this.steepness = steepness;
        this.radius = radius;
        this.mix = mix;
    }

    public double appearProb(Keeper keeper, Creature creature) {
        double denominator = 0.0;
        double creatureAlignDist = creature.getAlignment().LpDistance(keeper.getAlignment(), 2.0);
        double creatureAllieDist = creature.getAllies().LpDistance(keeper.getAllies(), 1.0);
        // We check if the keeper fulfills the creatures room requirements
        boolean requirementsFulfilled = true;
        for(Room r : creature.getRoomRequirements()){
            boolean foundRoom = false;
            for(Room kr : keeper.getRooms()){
                foundRoom |= kr.fulfillsRequirement(r);
            }
            requirementsFulfilled &= foundRoom;
        }
        if(!requirementsFulfilled){
            return 0.0;
        }
        // We calculate the probability that the creature will appear given that is requirements are fulfilled.
        double nominator =
                (1.0 - mix) * (1.0 /(steepness * Math.pow(creatureAlignDist, radius) + 1))
                + mix * (1.0 /(steepness * Math.pow(creatureAllieDist, radius) + 1));
        for(Creature c : keeper.getCreaturePool()){
            double cAlignDist = c.getAlignment().LpDistance(keeper.getAlignment(), 2.0);
            double cAllieDist = c.getAllies().LpDistance(keeper.getAllies(), 1.0);
            denominator +=
                    (1.0 - mix) * (1.0 /(steepness * Math.pow(cAlignDist, radius) + 1))
                    + mix * (1.0 /(steepness * Math.pow(cAllieDist, radius) + 1));
        }
        return nominator/denominator;
    }
}
