package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class NewsParser 
{
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
        String title = null;
        String description = null;
        String link = null;
        String pubDate = null;
        String imageUrl = null;
        List<NewsItem> items = new ArrayList<NewsItem>();
        
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) 
        {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) 
            {
                continue;
            }
            
            String name = xmlPullParser.getName();
            
            /*if (name.equals("image"))
            {
            	
            	imageUrl = readImageUrl(xmlPullParser);
            	
            }
            else*/ if (name.equals("title")) 
            {
                title = readTitle(xmlPullParser);
            } 
            else if (name.equals("description"))
            {
            	description = readDescription(xmlPullParser);
            }
            else if (name.equals("link")) 
            {
                link = readLink(xmlPullParser);
            }
            else if (name.equals("pubDate"))
            {
            	pubDate = readPubDate(xmlPullParser);
            }
            
            //imageUrl = "http://upload.wikimedia.org/wikipedia/commons/d/d3/Nelumno_nucifera_open_flower_-_botanic_garden_adelaide2.jpg";
            
            if (title != null && description != null && link != null && pubDate != null) 
            {
                NewsItem item = new NewsItem(title, description, link, pubDate);
                items.add(item);
                title = null;
                description = null;
                link = null;
                pubDate = null;
                //imageUrl = null;
            }
        }
        
        return items;
	}
	
	// Read the link tags and return the result
	private String readLink(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "link");
        
        return link;
    }
	
	// Read the content tag and return the result
	private String readDescription(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException
	{
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "description");
		String description = readText(xmlPullParser);
		xmlPullParser.require(XmlPullParser.END_TAG, ns, "description");
		
		return description;
	}
 
	// Read the title tag and return the result
    private String readTitle(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
    {
    	xmlPullParser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "title");
        
        return title;
    }
    
    // Read the title tag and return the result
    private String readPubDate(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
    {
    	xmlPullParser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "pubDate");
        
        return pubDate;
    }
    
    // Read the link tags and return the result
 	private String readImageUrl(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException 
 	{
 		xmlPullParser.require(XmlPullParser.START_TAG, ns, "url");
        String imageUrl = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, ns, "url");
         
        return imageUrl;
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
