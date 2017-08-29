package friendtracker.assignment1_s2_2017.entities;

import java.util.HashMap;

/**
 * Created by Jason on 8/17/2017.
 */

public class MeetingList
{
    private static HashMap<String,Meeting> hm_Meetings=new HashMap<String,Meeting>();

    public static HashMap<String,Meeting> getMeetings()
    {
        return hm_Meetings;
    }

    public static Meeting getMeetingById(String aId)
    {
        return hm_Meetings.get(aId);
    }

    public static void addMeeting(Meeting meeting)
    {
        getMeetings().put(meeting.getId(),meeting);
    }

    public static void removeMeeting(Meeting meeting)
    {
        getMeetings().remove(meeting.getId());
    }
    public static void removeMeetingByKey(String meetingId)
    {
        getMeetings().remove(meetingId);
        //System.out.println(hm_Meetings.size());
    }
    public static void clearMeeting()
    {
        getMeetings().clear();
    }

}
