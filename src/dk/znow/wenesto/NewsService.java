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

public class NewsService extends IntentService 
{
	private static final String NEWS_LINK = "http://news.yahoo.com/rss/entertainment";
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	
	public NewsService()
	{
		super("NewsService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		Log.d(Constants.TAG, "Service started");
		List<NewsItem> newsItems = null;
		Bundle bundle = new Bundle();
		
		try
		{
			NewsParser parser = new NewsParser();
			newsItems = parser.parse(getInputStream(NEWS_LINK));
		}
		catch (XmlPullParserException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		catch (IOException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		
		bundle.putSerializable(ITEMS, (Serializable) newsItems);
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
