package dk.znow.wenesto;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.util.Log;
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
	String woeid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		if (view == null) 
		{
            view = inflater.inflate(R.layout.weatherfragment, container, false);
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
            
            //getCoordinates getCoords = new getCoordinates(getActivity());
            //title.setText(getCoords.getCoords()+"");
            //title.setText(text)
            
            image = (ImageView) view.findViewById(R.id.icon);
            
            
            //startService();
            getWoeid();
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
		Intent intent = new Intent(getActivity(), WeatherService.class);
		intent.putExtra(WeatherService.RECEIVER,  resultReceiver);
		getActivity().startService(intent);
		
	}
	public void getWoeid()
	{
		Intent intent = new Intent(getActivity(), WoeidService.class);
        intent.putExtra(WoeidService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
        Log.d("Noob","Noob");
	}
	
	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) 
    {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) 
        {
            List<WoeidItem> items = (List<WoeidItem>) resultData.getSerializable(WoeidService.ITEMS);
            //String str = (String) resultData.getSerializable(WoeidService.ITEMS);
            
//            if (items != null) 
//            {
//                NewsAdapter adapter = new NewsAdapter(getActivity(), items);
//            } 
//            else 
//            {
//                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
//                Toast.LENGTH_LONG).show();
//            }
        }
    };

}
