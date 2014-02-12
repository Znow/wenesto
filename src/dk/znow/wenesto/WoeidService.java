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

public class WoeidService extends IntentService 
{
	private String queryString;
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	public String woeid;
	
	public WoeidService()
	{
		super("WoeidService");
		Log.v("Wuhuuu","Yes");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		//queryString = "http://where.yahooapis.com/geocode?q="+MainActivity.coords+"&appid=y0vCd27i";
		queryString = "http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20geo.placefinder%20WHERE%20text%3D%22{"+MainActivity.latitude+"%2C"+MainActivity.longitude+"%22%20and%20gflags%3D%22R%22";
		Log.v("WoeidService", "Service started");
		List<WoeidItem> woeidItems = null;

		Bundle bundle = new Bundle();
		
		try
		{
			WoeidParser parser = new WoeidParser();
			woeidItems = parser.parse(getInputStream(queryString));
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
		
		bundle.putSerializable(ITEMS, (Serializable) woeidItems);
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
