package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class WeatherParser {
	
private final String ns = null;
	
	// Parse the inputstream as a list of News Items
	public List<NewsItem> parse(InputStream inputStream) throws XmlPullParserException, IOException
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
	private List<NewsItem> readFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
	{
		xmlPullParser.require(XmlPullParser.START_TAG, null, "rss");
        String woeid = null;
        List<NewsItem> items = new ArrayList<NewsItem>();
        
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) 
        {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) 
            {
                continue;
            }
            
            String name = xmlPullParser.getName();
            
            if (name.equals("woeid")) 
            {
                woeid = readWoeid(xmlPullParser);
            } 
            if (woeid != null) 
            {
                //NewsItem item = new NewsItem(title, description, link, pubDate);
                WeatherItem weatherItem = new WeatherItem();
                items.add(item);
                woeid = null;
            }
        }
        
        return items;
	}
	
	// Read the link tags and return the result
	private String readWoeid(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "link");
        String woeid = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "link");
        
        return woeid;
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
