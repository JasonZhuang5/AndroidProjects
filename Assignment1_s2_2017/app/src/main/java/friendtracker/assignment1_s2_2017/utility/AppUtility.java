package friendtracker.assignment1_s2_2017.utility;

import java.util.ArrayList;
import java.util.Random;

import friendtracker.assignment1_s2_2017.entities.Meeting;

/**
 * Created by Jason on 7/27/2017.
 */

public class AppUtility
{
    private static String randomString(int length)
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String generateIdForFriend()
    {
        String retId = "f";
        retId = retId + randomString(30);
        return retId;

    }

    public static String generateIdForMeeting()
    {
        String retId = "m";
        retId = retId + randomString(30);
        return retId;

    }

    public static ArrayList<Meeting> doInsertSort(ArrayList<Meeting> arr, int
            sortFieldIndex,int AorD)
    {
        int length = arr.size();
        int j;
        int i;
        Meeting key;
        if (sortFieldIndex==3)
        {
            for(j=1;j<length;j++)
            {
                key=arr.get(j);
                i=j-1;
                if (AorD == 0)
                {
                    while (i >= 0 && (arr.get(i).getStartTime().getTime() > key.getStartTime().getTime()))
                    {
                        arr.set(i + 1, arr.get(i));
                        i--;
                    }
                }else
                {
                    while (i >= 0 && (arr.get(i).getStartTime().getTime() < key.getStartTime().getTime()))
                    {
                        arr.set(i + 1, arr.get(i));
                        i--;
                    }
                }
                arr.set(i+1, key);
            }
        }

        return arr;
    }



    public static String getFriendLocation()
    {
        String retValue="";
        return retValue;
    }
}
