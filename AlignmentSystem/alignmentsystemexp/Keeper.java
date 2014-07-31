/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alignmentsystemexp;

import java.util.List;
import java.util.Random;

/**
 *
 * @author ckr
 */
public class Keeper {
    // TODO factor this out into a strategy, call it creatureStrategy
    // All the creatures that can be drawn from the portals
    private List<Creature> creaturePool;
    // All the creatures that the keeper controls
    private List<Creature> creatures;
    // All the rooms that the keeper has build
    private List<Room> rooms;
    private Alignment alignment;
    private Allies allies;
    private double evolveRate;
    private double roomCreScale;
    private StatisticsStrategy statisticsStrat;
    private RoomTypeStrategy roomTypeStrat;
    private int maxCreatures;
    private Random rand;

    public void setEvolveRate(double evolveRate) {
        this.evolveRate = evolveRate;
    }

    public void setRoomCreScale(double roomCreScale) {
        this.roomCreScale = roomCreScale;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setAllies(Allies allies) {
        this.allies = allies;
    }

    /**
     * Returns the RoomTypes that the keeper is allowed to/can build.
     * @return The list of RoomTypes that the keeper can build.
     */
    public List<RoomType> getAvailableRoomTypes() {
        return roomTypeStrat.getAvailableRoomTypes(this);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public double getEvolveRate() {
        return evolveRate;
    }
    
    public double getRoomCreScale() {
        return roomCreScale;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public List<Creature> getCreaturePool() {
        return creaturePool;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Allies getAllies() {
        return allies;
    }

    public int getMaxCreatures() {
        return maxCreatures;
    }

    public Keeper(StatisticsStrategy alignStrat, RoomTypeStrategy roomTypeStrat, List<Creature> creaturePool, List<Creature> creatures,
            List<Room> rooms, Allies allies, Alignment alignment, double evolveRate,
            double roomCreScale, int maxCreatures, long seed) {
        this.creaturePool = creaturePool;
        this.creatures = creatures;
        this.allies = allies;
        this.alignment = alignment;
        this.evolveRate = evolveRate;
        this.roomCreScale = roomCreScale;
        this.maxCreatures = maxCreatures;
        this.rand = new Random(seed);
        this.statisticsStrat = alignStrat;
        this.roomTypeStrat = roomTypeStrat;
        this.rooms = rooms;
    }

    public void updateStatistics() {
        statisticsStrat.calculateStatistics(this, creatures, rooms);
        statisticsStrat.setStatistics(this, creatures, rooms);
    }

    public void waitForCreature() {
        double currentProb = 0.0;
        double prevProb = 0.0;
        double selected = rand.nextDouble();
        Creature chosen = null;
        for (Creature c : creaturePool) {
            prevProb = currentProb;
            currentProb += c.appearProb(this);
            if (prevProb <= selected && selected <= currentProb) {
                chosen = c;
            }
        }
        // Add the creature we chose, if any
        if(chosen != null){
            creatures.add(chosen);
        } // Else no creature where interested in our dungeon :(
    }

    /*public void evictCreatures(Alignment target, int numberToEvict) {
        for(int i=0; i<numberToEvict; i++){
            double currentDist = Double.POSITIVE_INFINITY;
            Creature evicted = null;
            for (Creature c : creatures) {
                List<Creature> tmp = new ArrayList<Creature>(creatures);
                tmp.remove(c);
                statisticsStrat.calculateStatistics(this, tmp, rooms);
                double dist = target.LpDistance(statisticsStrat.getAlignment(), 2.0);
                if (dist < currentDist) {
                    evicted = c;
                }
            }
            creatures.remove(evicted);
        }
    }

    public boolean evictNonWanted(Set<CreatureType> wanted){
        Set<CreatureType> unwanted = new HashSet<CreatureType>();
        for(Creature c : creatures){
            unwanted.add(c.getType());
        }
        unwanted.removeAll(wanted);
        Map<CreatureType, Integer> numOfType = new HashMap<CreatureType, Integer>();
        for(Creature c : creatures){
            if(numOfType.get(c.getType()) == null){
                numOfType.put(c.getType(), 1);
            }
            numOfType.put(c.getType(), numOfType.get(c.getType()) + 1);
        }
        Creature chosen = null;
        for(Creature c : creatures){
            if(unwanted.contains(c.getType()) || numOfType.get(c.getType()) > 1){
                chosen = c;
            }
        }
        boolean res = false;
        if(chosen != null){
            creatures.remove(chosen);
            res = true;
        }
        return res;
    }*/

    public String printCreatures() {
        String res = "";
        for (Creature c : creatures) {
            res += c.getType() + " ";
        }
        return res;
    }

    public String printRooms(){
        String res = "";
        for(Room r : rooms){
            res += r.toString() + " ";
        }
        return res;
    }
}
