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
public interface StatisticsStrategy {
    public void calculateStatistics(Keeper keeper, List<Creature> creatures, List<Room> rooms);
    public void setStatistics(Keeper keeper, List<Creature> creatures, List<Room> rooms);
    public Alignment getAlignment();
    public Allies getAllies();
}
