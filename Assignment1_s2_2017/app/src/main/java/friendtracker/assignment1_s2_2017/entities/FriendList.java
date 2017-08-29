package friendtracker.assignment1_s2_2017.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jason on 8/23/2017.
 */

public class FriendList
{
    private  static HashMap<String,Friend> hm_Friends = new HashMap<String,Friend>();

    public static HashMap<String,Friend> getFriends()
    {
        return hm_Friends;
    }

    public static void addFriend(Friend friend)
    {
        hm_Friends.put(friend.getId(),friend);
    }

    public static void removeFriend(Friend friend)
    {
        getFriends().remove(friend.getId());
    }

    public static void removeFriendByKey(String friendId)
    {
        getFriends().remove(friendId);
    }

    public static Friend getFriendById(String id)
    {
        return getFriends().get(id);
    }

    public static ArrayList<Friend> getFriendArrayList()
    {
        ArrayList<Friend> ret_friendArrayList = new ArrayList<>();
        Iterator<Map.Entry<String, Friend>> iter = getFriends().entrySet()
                .iterator();
        while(iter.hasNext())
        {
            Map.Entry entry = iter.next();
            Friend f = (Friend) entry.getValue();
            ret_friendArrayList.add(f);
        }
        return ret_friendArrayList;
    }


}
