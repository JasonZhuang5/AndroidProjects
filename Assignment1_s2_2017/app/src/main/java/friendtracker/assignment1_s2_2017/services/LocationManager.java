package friendtracker.assignment1_s2_2017.services;

import java.io.InputStream;
import java.util.ArrayList;

import friendtracker.assignment1_s2_2017.entities.Location;

/**
 * Created by Jason on 8/18/2017.
 */

public class LocationManager
{

    private static ArrayList<Location> locationList = new ArrayList<Location>();

    public static ArrayList<Location> getLocationList()
    {
        return locationList;
    }

    public static void addLocation(String longitude, String latitude)
    {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        locationList.add(location);
    }

    public static void clearLocation()
    {
        locationList.clear();
    }

}
