package dk.znow.wenesto;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherFragment extends Fragment
{
	String temperature, condition, date, humidity, wind, link;
	TextView title, txtTemperature, txtDate, txtCondition, txtWind, txtHumidity, day1, day2, day3, day4;
	ImageView image;
	ArrayList<String> weather = new ArrayList<String>();
	ProgressDialog dialog;
	Bitmap icon = null;
	View view;
	//LocationManager locationManager;
	Location location;
	String provider;
	String coordinates;
	String woeid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if (view == null) 
		{
            view = inflater.inflate(R.layout.newsfragment, container, false);
            //progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            title = (TextView) view.findViewById(R.id.weather_title);
            txtTemperature = (TextView) view.findViewById(R.id.tempText);
            txtDate = (TextView) view.findViewById(R.id.dateText);
            txtCondition = (TextView) view.findViewById(R.id.conditionText);
            txtWind = (TextView) view.findViewById(R.id.windText);
            txtHumidity = (TextView) view.findViewById(R.id.humidityText);
            day1 = (TextView) view.findViewById(R.id.day1);
            day2 = (TextView) view.findViewById(R.id.day2);
            day3 = (TextView) view.findViewById(R.id.day3);
            day4 = (TextView) view.findViewById(R.id.day4);
            
            image = (ImageView) view.findViewById(R.id.icon);
            
            
            startService();
        } 
		else 
		{
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
		
		return view;
	}
	
	public void startService()
	{
		
	}
	
	public void getCoordinates()
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
	public void getWoeid()
	{
		
	}

}
