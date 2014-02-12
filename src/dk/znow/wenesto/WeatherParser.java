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
	String temperature, condition, date, humidity, wind, city, country, statuscode;

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
				condition = getCondition(xmlPullParser);
				temperature = getTemp(xmlPullParser);
				date = getDate(xmlPullParser);
				statuscode = getStatusCode(xmlPullParser);
			}
			// else if (name.equals("yweather:forecast"))
			// {
			// day = getDay(xmlPullParser);
			// date = getDate(xmlPullParser);
			// low = getLow(xmlPullParser);
			// high = getHigh(xmlPullParser);
			// text = getText(xmlPullParser);
			// }
			else if (name.equals("yweather:location")) 
			{
				city = getCity(xmlPullParser);
				country = getCountry(xmlPullParser);
			} 
			else if (name.equals("yweather:wind")) 
			{
				wind = getWind(xmlPullParser);
			} 
			else if (name.equals("yweather:atmosphere")) 
			{
				humidity = getHumidity(xmlPullParser);
			}
			
		}
		weatheritem = new WeatherItem(temperature, condition, date,humidity, wind, city, country, statuscode);
		items.add(weatheritem);
		temperature = null;
		condition = null;
		date = null;
		humidity = null;
		wind = null;
		city = null;
		return items;
	}

	// private void readyweathercondition(XmlPullParser xmlPullParser)
	// throws IOException, XmlPullParserException {
	// temperature = xmlPullParser.getAttributeValue(null, "temp");
	// date = xmlPullParser.getAttributeValue(null, "date");
	// text = xmlPullParser.getAttributeValue(null, "text");
	// }
	//
	// private WeatherItem readyweatherforecast(XmlPullParser xmlPullParser)
	// throws IOException, XmlPullParserException {
	// String day = xmlPullParser.getAttributeValue(null, "day");
	// String low = xmlPullParser.getAttributeValue(null, "low");
	// String high = xmlPullParser.getAttributeValue(null, "high");
	// date = xmlPullParser.getAttributeValue(null, "date");
	// text = xmlPullParser.getAttributeValue(null, "text");
	// WeatherItem wi = new WeatherItem(day, date, low, high, text);
	// // weather.add(wi);
	// return wi;
	// }

	private String getTemp(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "temp");
	}

	private String getCondition(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "text");
	}

	private String getDate(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "date");
	}
	private String getHumidity(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "humidity");
	}
	private String getWind(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "speed");
	}
	private String getCity(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "city");
	}
	private String getCountry(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "country");
	}
	private String getStatusCode(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "code");
	}

}
