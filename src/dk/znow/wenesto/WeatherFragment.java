package dk.znow.wenesto;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
	TextView title, txtTemp, txtDate, txtCondition, txtWind, txtHumidity, day1, day2;
	ImageView image;
	ArrayList<String> weather = new ArrayList<String>();
	ProgressDialog dialog;
	Bitmap icon = null;
	

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) 
//	{
//		return inflater.inflate(R.layout.weatherfragment, container, false);
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
