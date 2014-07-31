/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

import java.util.List;

/**
 *
 * @author ckr
 */
public class Creature {
    private Alignment alignment;
    private Allies allies;
    private Faction faction;
    private CreatureType type;
    private List<Room> roomRequirements;
    private AppearanceStrategy appStrat;

    public Faction getFaction() {
        return faction;
    }

    public CreatureType getType() {
        return type;
    }

    public Creature(AppearanceStrategy appStrat, CreatureType type, double hu, double cor, double con, double un,
            double el, double me, double AE, double OC, double PV, Faction faction, List<Room> requirements){
        this(appStrat, type, new Allies(hu, cor, con, un, el, me), new Alignment(AE, OC, PV), faction, requirements);
    }

    public Creature(AppearanceStrategy appStrat, CreatureType type, Allies allies,
            Alignment alignment, Faction faction, List<Room> requirements){
        this.appStrat = appStrat;
        this.type = type;
        this.allies = allies;
        this.alignment = alignment;
        this.faction = faction;
        this.roomRequirements = requirements;
    }

    public Allies getAllies() {
        return allies;
    }
    
    public Alignment getAlignment() {
        return alignment;
    }

    public double appearProb(Keeper keeper){
        return appStrat.appearProb(keeper, this);
    }

    public List<Room> getRoomRequirements(){
        return roomRequirements;
    }
}
