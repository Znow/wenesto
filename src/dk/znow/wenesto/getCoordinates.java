package dk.znow.wenesto;

import android.location.Criteria;
import android.location.Location;

public class getCoordinates {
	
	Location location;
	String provider;
	String coordinates;
	
	public getCoordinates()
	{
	// Define the criteria how to select the locatioin provider -> use
    // default
    Criteria criteria = new Criteria();
    provider = MainActivity.locationManager.getBestProvider(criteria, false);
    Location location = MainActivity.locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (location != null) {
      System.out.println("Provider " + provider + " has been selected.");
      
      coordinates = location.getLatitude()+","+location.getLongitude();
    } else {
      
    }
	}
	
	public String getCoords()
	{
		return coordinates;
	}

}
