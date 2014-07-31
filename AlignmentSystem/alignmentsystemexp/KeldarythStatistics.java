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
public class KeldarythStatistics implements StatisticsStrategy{
    public Alignment alignment;
    public Allies allies;
    
    public void calculateStatistics(Keeper keeper, List<Creature> creatures, List<Room> rooms) {
        // Keldaryths computation of alignment
        Alignment creatureAlignment = new Alignment();
        Allies creatureAllies = new Allies();
        for (Creature c : keeper.getCreatures()) {
            creatureAlignment = new Alignment(creatureAlignment.add(c.getAlignment()).getArray());
            creatureAllies = new Allies(creatureAllies.add(c.getAllies()).getArray());
        }
        if (keeper.getCreatures().size() > 0) {
            creatureAlignment = new Alignment(creatureAlignment.scale(1.0 / keeper.getCreatures().size()).getArray());
            creatureAllies = new Allies(creatureAllies.scale(1.0 / keeper.getCreatures().size()).getArray());
        }
        alignment = creatureAlignment;
        allies = creatureAllies;
    }

    public void setStatistics(Keeper keeper, List<Creature> creatures, List<Room> rooms) {
        if(alignment == null || allies == null){
            calculateStatistics(keeper, creatures, rooms);
        }
        keeper.setAlignment(alignment);
        keeper.setAllies(allies);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Allies getAllies() {
        return allies;
    }
}