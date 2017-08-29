package friendtracker.assignment1_s2_2017.entities;

import java.util.Date;
import java.util.List;

/**
 * Created by Jason on 7/27/2017.
 */

public class Meeting
{
    private String id;
    private String title;
    private Date startTime;
    private Date endTime;
    private List<Friend> friends;
    private String location;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public List<Friend> getFriends()
    {
        return friends;
    }

    public void setFriends(List<Friend> friends)
    {
        this.friends = friends;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}
