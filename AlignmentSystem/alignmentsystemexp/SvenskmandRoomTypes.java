/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ckr
 */
public class SvenskmandRoomTypes implements RoomTypeStrategy{
    public List<RoomType> getAvailableRoomTypes(Keeper keeper){
        List<RoomType> res = new ArrayList<RoomType>();
        for(RoomType t : RoomType.values()){
            if(t.getAlignment().LpDistance(keeper.getAlignment(), 2.0) <= t.getRadius()){
                res.add(t);
            }
        }
        return res;
    }
}
