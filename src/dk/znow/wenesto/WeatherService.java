package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class WeatherService extends IntentService 
{
	private String queryString;
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	public String woeid;
	
	public WeatherService()
	{
		super("WeatherService");
		Log.v("Wuhuuu","Yes");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		WoeidItem item;
		woeid = item.getWoeid();
		Log.d("YAHU",woeid);
		queryString = "http://weather.yahooapis.com/forecastrss?w="+woeid+"&u=c&#8221";
		Log.v("WeatherService", "Service started");
		List<WeatherItem> weatherItems = null;

		Bundle bundle = new Bundle();
		
		try
		{
			WeatherParser parser = new WeatherParser();
			weatherItems = parser.parse(getInputStream(queryString));
			//WoeidItem item = new WoeidItem(woeid);
			Log.v("Det går godt","YEAH!");
		}
		catch (XmlPullParserException exception)
		{
			Log.w(exception.getMessage(), exception);
			Log.v("Det går galt her","1");
		}
		catch (IOException exception)
		{
			Log.w(exception.getMessage(), exception);
			Log.v("Det går galt her","2");
		}
		
		bundle.putSerializable(ITEMS, (Serializable) weatherItems);
		ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);
		resultReceiver.send(0, bundle);
		
	}
	
	// Get the input stream specified by the link
	public InputStream getInputStream(String link)
	{
		try
		{
			// Create a URL of the link
			URL url = new URL(link);
			
			// Open a connection and get the input
			return url.openConnection().getInputStream();
		}
		catch (IOException exception)
		{
			Log.w(Constants.TAG, "Exception while retrieving the input stream", exception);
            return null;
		}
	}

}
