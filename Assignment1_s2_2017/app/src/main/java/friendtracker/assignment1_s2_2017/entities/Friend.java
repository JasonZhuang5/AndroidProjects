package friendtracker.assignment1_s2_2017.entities;

import java.util.Date;

import friendtracker.assignment1_s2_2017.utility.AppUtility;

/**
 * Created by Jason on 7/27/2017.
 */

public class Friend
{
    private String id;
    private String name;
    private String email;
    private Date   birthDay;
    private String photoPath;


    public Friend(String id)
    {
        this.id = id;
    }

    public Friend(String name, String email, Date birthDay, String photoPath)
    {
        this.id = AppUtility.generateIdForFriend();
        this.name = name;
        this.email = email;
        this.birthDay = birthDay;
        this.photoPath = photoPath;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getBirthDay()
    {
        return birthDay;
    }

    public void setBirthDay(Date birthDay)
    {
        this.birthDay = birthDay;
    }

    public String getPhotoPath()
    {
        return photoPath;
    }

    public void setPhotoPath(String photoPath)
    {
        this.photoPath = photoPath;
    }

}
