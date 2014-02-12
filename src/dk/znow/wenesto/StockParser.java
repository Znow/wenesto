package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class StockParser 
{
	private final String ns = null;
	
	// Parse the inputstream as a list of News Items
	public List<StockItem> parse(InputStream inputStream) throws XmlPullParserException, IOException
	{
		try
		{
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			xmlPullParser.setInput(inputStream, null);
			xmlPullParser.nextTag();
            return readFeed(xmlPullParser);
		}
		finally
		{
			inputStream.close();
		}
	}
	
	// 
	private List<StockItem> readFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
	{
		xmlPullParser.require(XmlPullParser.START_TAG, null, "query");
		
		// col0 - name of company
        String title = null;
        // col1 - volume
        String volume = null;
        // col2 - stock quote open
        String open = null;
        // col3 - stock quote change
        String change = null;
        // col4 - last trade
        String lastTrade = null;
        
        List<StockItem> items = new ArrayList<StockItem>();
        
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) 
        {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) 
            {
                continue;
            }
            
            String name = xmlPullParser.getName();
            
            if (name.equals("col0")) 
            {
                title = readTitle(xmlPullParser);
            }
            else if (name.equals("col1"))
            {
            	volume = readVolume(xmlPullParser);
            }
            else if (name.equals("col2"))
            {
            	open = readOpen(xmlPullParser);
            }
            else if (name.equals("col3")) 
            {
                change = readChange(xmlPullParser);
            }
            else if (name.equals("col4"))
            {
            	lastTrade = readLastTrade(xmlPullParser);
            }
            
            if (title != null && volume != null && open != null && change != null && lastTrade != null) 
            {
                StockItem item = new StockItem(title, volume, open, change, lastTrade);
                items.add(item);
                title = null;
                volume = null;
                open = null;
                change = null;
                lastTrade = null;
            }
        }
        
        return items;
	}
	
	// Read the title tag and return the result
    private String readTitle(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
    {
    	xmlPullParser.require(XmlPullParser.START_TAG, ns, "col0");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "col0");
        
        return title;
    }
    
 // Read the title tag and return the result
    private String readVolume(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
    {
    	xmlPullParser.require(XmlPullParser.START_TAG, ns, "col1");
        String volume = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "col1");
        
        return volume;
    }
	
	// Read the content tag and return the result
	private String readOpen(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "col2");
		String open = readText(xmlPullParser);
		xmlPullParser.require(XmlPullParser.END_TAG, ns, "col2");
		
		return open;
	}
	
	// Read the link tags and return the result
	private String readChange(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "col3");
        String change = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "col3");
        
        return change;
    }
	
	// Read the link tags and return the result
	private String readLastTrade(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "col4");
        String lastTrade = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "col4");
        
        return lastTrade;
    }
	
    // For the tags title, content and link, extract their text values.
    private String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException 
    {
        String result = "";
        
        if (xmlPullParser.next() == XmlPullParser.TEXT) 
        {
        	result = xmlPullParser.getText();
        	xmlPullParser.nextTag();
        }
        
        return result;
    }
}
