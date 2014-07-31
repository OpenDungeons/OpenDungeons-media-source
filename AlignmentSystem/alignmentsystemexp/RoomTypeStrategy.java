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
public interface RoomTypeStrategy {
    public List<RoomType> getAvailableRoomTypes(Keeper keeper);
}
