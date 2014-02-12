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

public class StockService extends IntentService 
{	
		
	private static final String STOCKS_LINK = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20csv%20where%20url%3D%22http%3A%2F%2Ffinance.yahoo.com%2Fd%2Fquotes.csv%3Fs%3DCARL-B.CO%2BCHR.CO%2BCOLOB.CO%2BDANSKE.CO%2BDSV.CO%2BFLS.CO%2BGEN.CO%2BGN.CO%2BJYSK.CO%2BMAERSK-A.CO%2BMAERSK-B.CO%2BNDA-DKK.CO%2BNOVO-B.CO%2BNZYM-B.CO%2BPNDORA.CO%2BTDC.CO%2BTOP.CO%2BTRYG.CO%2BVWS.CO%2BWDH.CO%26f%3Dnov%22%3B&diagnostics=true";
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";
	
	public StockService()
	{
		super("StockService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		Log.d(Constants.TAG, "Stock Service started");
		List<StockItem> stockItems = null;
		Bundle bundle = new Bundle();
		
		try
		{
			StockParser parser = new StockParser();
			stockItems = parser.parse(getInputStream(STOCKS_LINK));
		}
		catch (XmlPullParserException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		catch (IOException exception)
		{
			Log.w(exception.getMessage(), exception);
		}
		
		bundle.putSerializable(ITEMS, (Serializable) stockItems);
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
