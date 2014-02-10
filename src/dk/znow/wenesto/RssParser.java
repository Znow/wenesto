package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class RssParser {
	private final String ns = null;
	
	public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException
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
	
	private List<RssItem> readFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
	{
		xmlPullParser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) 
        {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) 
            {
                continue;
            }
            
            String name = xmlPullParser.getName();
            
            if (name.equals("title")) 
            {
                title = readTitle(xmlPullParser);
            } 
            else if (name.equals("link")) 
            {
                link = readLink(xmlPullParser);
            }
            
            if (title != null && link != null) 
            {
                RssItem item = new RssItem(title, link);
                items.add(item);
                title = null;
                link = null;
            }
        }
        
        return items;
	}
	
	private String readLink(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "link");
        
        return link;
    }
 
    private String readTitle(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
    {
    	xmlPullParser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "title");
        
        return title;
    }
 
    // For the tags title and link, extract their text values.
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
