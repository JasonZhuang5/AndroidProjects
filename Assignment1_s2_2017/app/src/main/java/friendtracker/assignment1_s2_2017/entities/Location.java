package friendtracker.assignment1_s2_2017.entities;

/**
 * Created by Jason on 8/18/2017.
 */

public class Location
{
    private String longitude;
    private String latitude;

    public Location()
    {
        super();
    }
    public Location(String longitude, String latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }
}
