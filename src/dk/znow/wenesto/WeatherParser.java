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
	String temperature, condition, date, humidity, wind, link, text, day, low, high;

	// Parse the inputstream as a list of News Items
	public List<WeatherItem> parse(InputStream inputStream)
			throws XmlPullParserException, IOException {
		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
					false);
			xmlPullParser.setInput(inputStream, null);
			xmlPullParser.nextTag();
			return readFeed(xmlPullParser);
		} finally {
			inputStream.close();
		}
	}

	//
	private List<WeatherItem> readFeed(XmlPullParser xmlPullParser)
			throws XmlPullParserException, IOException {
		xmlPullParser.require(XmlPullParser.START_TAG, null, "rss");
		WeatherItem weatheritem;
		List<WeatherItem> items = new ArrayList<WeatherItem>();

		while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
			if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = xmlPullParser.getName();

			if (name.equals("yweather:condition")) 
			{
				text = getText(xmlPullParser);
				temperature = getTemp(xmlPullParser);
				date = getDate(xmlPullParser);
			} 
			else if (name.equals("yweather:forecast")) 
			{
				day = getDay(xmlPullParser);
				date = getDate(xmlPullParser);
				low = getLow(xmlPullParser);
				high = getHigh(xmlPullParser);
				text = getText(xmlPullParser);
			}
			else if (name.equals("yweather:location"))
			{
				
			}
			else if (name.equals("yweather:wind"))
			{
				
			}
			else if (name.equals("yweather:atmosphere"))
			{
				
			}
			weatheritem = new WeatherItem(day, date, low, high, text);
			items.add(weatheritem);
			temperature = null;
			day = null;
			date = null;
			low = null;
			high = null;
			text = null;
		}

		return items;
	}

//	private void readyweathercondition(XmlPullParser xmlPullParser)
//			throws IOException, XmlPullParserException {
//		temperature = xmlPullParser.getAttributeValue(null, "temp");
//		date = xmlPullParser.getAttributeValue(null, "date");
//		text = xmlPullParser.getAttributeValue(null, "text");
//	}
//
//	private WeatherItem readyweatherforecast(XmlPullParser xmlPullParser)
//			throws IOException, XmlPullParserException {
//		String day = xmlPullParser.getAttributeValue(null, "day");
//		String low = xmlPullParser.getAttributeValue(null, "low");
//		String high = xmlPullParser.getAttributeValue(null, "high");
//		date = xmlPullParser.getAttributeValue(null, "date");
//		text = xmlPullParser.getAttributeValue(null, "text");
//		WeatherItem wi = new WeatherItem(day, date, low, high, text);
//		// weather.add(wi);
//		return wi;
//	}

	private String getDay(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "day");
	}

	private String getTemp(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "temp");
	}

	private String getLow(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "low");
	}

	private String getHigh(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "high");
	}

	private String getDate(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "date");
	}

	private String getText(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "text");
	}

}
