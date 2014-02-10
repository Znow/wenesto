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
import android.provider.SyncStateContract.Constants;
import android.util.Log;

public class RssService extends IntentService 
{
	private static final String RSS_LINK = "http://news.yahoo.com/rss/entertainment";
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	
	public RssService()
	{
		super("RssService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		Log.d(Constants.TAG, "Service started");
		List<RssItem> rssItems = null;
		Bundle bundle = new Bundle();
		
		try
		{
			RssParser parser = new RssParser();
			rssItems = parser.parse(getInputStream(RSS_LINK));
		}
		catch (XmlPullParserException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		catch (IOException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		
		bundle.putSerializable(ITEMS, (Serializable) rssItems);
		ResultReceiver resultReceiver = intent.getParcelableExtra(RECEIVER);
		resultReceiver.send(0, bundle);
		
	}
	
	public InputStream getInputStream(String link)
	{
		try
		{
			URL url = new URL(link);
			return url.openConnection().getInputStream();
		}
		catch (IOException exception)
		{
			Log.w(Constants.TAG, "Exception while retrieving the input stream", exception);
            return null;
		}
	}
	
	
	
}
