package dk.znow.wenesto;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

public class WeatherFragment extends Fragment {
	String temperature, condition, date, humidity, wind, link;
	TextView title, txtTemperature, txtDate, txtCondition, txtWind,
			txtHumidity, day1, day2, day3, day4, day5;
	ImageView image;
	ArrayList<String> weather = new ArrayList<String>();
	ProgressDialog dialog;
	Bitmap icon = null;
	View view;
	// LocationManager locationManager;
	String woeid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.weatherfragment, container, false);
			// progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			title = (TextView) view.findViewById(R.id.weather_title);
			txtTemperature = (TextView) view.findViewById(R.id.tempText);
			txtDate = (TextView) view.findViewById(R.id.dateText);
			txtCondition = (TextView) view.findViewById(R.id.conditionText);
			txtWind = (TextView) view.findViewById(R.id.windText);
			txtHumidity = (TextView) view.findViewById(R.id.humidityText);
			image = (ImageView) view.findViewById(R.id.icon);
			day1 = (TextView) view.findViewById(R.id.day1);
			day2 = (TextView) view.findViewById(R.id.day2);
			day3 = (TextView) view.findViewById(R.id.day3);
			day4 = (TextView) view.findViewById(R.id.day4);
			day5 = (TextView) view.findViewById(R.id.day5);
			// getCoordinates getCoords = new getCoordinates(getActivity());
			// title.setText(getCoords.getCoords()+"");
			// title.setText(text)

			image = (ImageView) view.findViewById(R.id.icon);

			getWoeid();
			// startService();
		} else {
			// If we are returning from a configuration change:
			// "view" is still attached to the previous view hierarchy
			// so we need to remove it and re-attach it to the current one
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}

		return view;
	}

	public void startService() {
		Intent intent = new Intent(getActivity(), WeatherService.class);
		intent.putExtra(WeatherService.RECEIVER, resultReceiver2);
		intent.putExtra("woeid", woeid);
		getActivity().startService(intent);
	}

	public void getWoeid() {
		Intent intent = new Intent(getActivity(), WoeidService.class);
		intent.putExtra(WoeidService.RECEIVER, resultReceiver);
		getActivity().startService(intent);
	}

	private final ResultReceiver resultReceiver = new ResultReceiver(
			new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
				List<WoeidItem> items = (List<WoeidItem>) resultData
						.getSerializable(WoeidService.ITEMS);

				for (WoeidItem i : items) {
					woeid = i.getWoeid();
				}
				startService();
		}
	};
	private final ResultReceiver resultReceiver2 = new ResultReceiver(
			new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			List<WeatherItem> items2 = (List<WeatherItem>) resultData.getSerializable(WeatherService.ITEMS);
			List<WeatherFItem> fitems = (List<WeatherFItem>) resultData.getSerializable(WeatherService.FITEMS);
			if (items2 != null) {
				for (WeatherItem i : items2) {
					title.setText("Forecast for " + i.getCity() + ", "
							+ i.getCountry());
					try
					{
						String url = "http://l.yimg.com/a/i/us/we/52/"+i.getStatusCode()+".gif";
						image.setTag(url);
						new DownloadBitMap().execute(image);
					}
					catch(Exception e){}

					txtDate.setText("Date: " + i.getDate());
					txtTemperature.setText("Temperature: " + i.getTemp() + " °C");
					txtCondition.setText("Condition: " +i.getCondition());
					txtWind.setText("Wind: " + i.getWind() + " km/h");
					txtHumidity.setText("Humidity: " + i.getHumidity());

				}
			} 
			else 
			{
				Toast.makeText(
						getActivity(),
						"An error occured while downloading the weather rss feed.",
						Toast.LENGTH_LONG).show();
			}
			
			if(fitems != null)
			{			
				for (int i = 0; i < fitems.size(); i++)
				{
					
					switch(i)
					{
					case 0: 
						day1.setText(fitems.get(i).getDay()+ " - "+fitems.get(i).getText()+" - "+ "High: " +fitems.get(i).getHigh()+" - " + "Low: "+fitems.get(i).getLow());
						break;
					case 1:
						day2.setText(fitems.get(i).getDay()+ " - "+fitems.get(i).getText()+" - "+ "High: " +fitems.get(i).getHigh()+" - " + "Low: "+fitems.get(i).getLow());
						break;
					case 2:
						day3.setText(fitems.get(i).getDay()+ " - "+fitems.get(i).getText()+" - "+ "High: " +fitems.get(i).getHigh()+" - " + "Low: "+fitems.get(i).getLow());
						break;
					case 3:
						day4.setText(fitems.get(i).getDay()+ " - "+fitems.get(i).getText()+" - "+ "High: " +fitems.get(i).getHigh()+" - " + "Low: "+fitems.get(i).getLow());
						break;
					case 4:
						day5.setText(fitems.get(i).getDay()+ " - "+fitems.get(i).getText()+" - "+ "High: " +fitems.get(i).getHigh()+" - " + "Low: "+fitems.get(i).getLow());
						break;
					}
				}
			}
		}

	};
}
