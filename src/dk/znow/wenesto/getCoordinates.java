package dk.znow.wenesto;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class getCoordinates extends Service implements LocationListener{
	
	 private final Context mContext;
	 
	    // flag for GPS status
	    boolean isGPSEnabled = false;
	 
	    // flag for network status
	    boolean isNetworkEnabled = false;
	 
	    boolean canGetLocation = false;
	 
	    Location location; // location
	    double latitude; // latitude
	    double longitude; // longitude
	 
	    // The minimum distance to change Updates in meters
	    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	 
	    // The minimum time between updates in milliseconds
	    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	 
	    // Declaring a Location Manager
	    protected LocationManager locationManager;
	 
	    public getCoordinates(Context context) {
	        this.mContext = context;
	        getLocation();
	    }
	    public Location getLocation() {
	        try {
	            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	 
	            // getting GPS status
	            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	   
	            // getting network status
	            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	 
	            if (!isGPSEnabled && !isNetworkEnabled) {
	                // no network provider is enabled
	            } else {
	                this.canGetLocation = true;
	                // First get location from Network Provider
	                if (isNetworkEnabled) 
	                {
//	                    locationManager.requestLocationUpdates(
//	                            LocationManager.NETWORK_PROVIDER,
//	                            MIN_TIME_BW_UPDATES,
//	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//	                    Log.d("Network", "Network");
//	                    if (locationManager != null) {
//	                        location = locationManager
//	                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//	                        if (location != null) {
//	                            latitude = location.getLatitude();
//	                            longitude = location.getLongitude();
//	                        }
//	                    }
	                }
	                // if GPS Enabled get lat/long using GPS Services
	                if (isGPSEnabled) 
	                {
	                    if (location == null) 
	                    {
	                        locationManager.requestLocationUpdates(
	                                LocationManager.GPS_PROVIDER,
	                                MIN_TIME_BW_UPDATES,
	                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                        Log.d("GPS Enabled", "GPS Enabled");
	                        if (locationManager != null) 
	                        {
	                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                            if (location != null) 
	                            {
	                                latitude = location.getLatitude();
	                                longitude = location.getLongitude();
	                                Log.d("Latitude",latitude+"");
	                                Log.d("Longitude",longitude+"");
	                            }
	                            else
	                            	Log.d("SUT MIN","DILLER");
	                        }
	                    }
	                }
	                else
	                	Log.d("GPS FAILED", "GPS FAILED");
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return location;
	    }
	    
	   public String getCoords()
	    {
		   String str ="";
	    	if (location != null)
	    	{
	    		str = latitude+","+longitude;
	    	}
	    	return str;
	    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
