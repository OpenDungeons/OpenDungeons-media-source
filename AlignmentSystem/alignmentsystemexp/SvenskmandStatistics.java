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
public class SvenskmandStatistics implements StatisticsStrategy{
    private Alignment alignment;
    private Allies allies;
    
    public void calculateStatistics(Keeper keeper, List<Creature> creatures, List<Room> rooms) {
        // The average creature alignment
        Alignment creatureAlignment = new Alignment();
        // The average creature factions
        Allies creatureAllies = new Allies();
        for (Creature c : creatures) {
            creatureAlignment = creatureAlignment.add(c.getAlignment());
            Allies tmp = null;
            switch(c.getFaction()){
                case Hum:
                    tmp = new Allies(1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    break;
                case Cor:
                    tmp = new Allies(0.0, 1.0, 0.0, 0.0, 0.0, 0.0);
                    break;
                case Con:
                    tmp = new Allies(0.0, 0.0, 1.0, 0.0, 0.0, 0.0);
                    break;
                case Und:
                    tmp = new Allies(0.0, 0.0, 0.0, 1.0, 0.0, 0.0);
                    break;
                case Elv:
                    tmp = new Allies(0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
                    break;
                case Mec:
                    tmp = new Allies(0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
                    break;
            }
            creatureAllies = creatureAllies.add(tmp);
        }
        if (creatures.size() > 0) {
            creatureAlignment = creatureAlignment.scale(1.0 / creatures.size());
            creatureAllies = creatureAllies.scale(1.0 / creatures.size());
        }

        // The weighted average alignment of the rooms, where weight is by room size
        Alignment roomAlignment = new Alignment();
        int totalArea = 0;
        for(Room r : rooms){
            totalArea += r.getDimension().area();
            roomAlignment = roomAlignment.add(r.getType().getAlignment().scale(r.getDimension().area()));
        }
        if (rooms.size() > 0) {
            roomAlignment = roomAlignment.scale(1.0 / totalArea);
        }
        // The the creature alignment weighs lambda and the room alignment weighs 1-lambda
        Alignment creaturesAndRooms = creatureAlignment.scale(keeper.getRoomCreScale())
                .add(roomAlignment.scale(1-keeper.getRoomCreScale()));
        // The new alignment weighs epsilon and the keepers old alignment weighs 1-epsilon
        alignment = creaturesAndRooms.scale(keeper.getEvolveRate())
                .add(keeper.getAlignment().scale(1-keeper.getEvolveRate()));
        // The factional mix of creatures is assigned to be the keepers factional mix
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
