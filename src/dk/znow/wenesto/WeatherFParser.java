package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class WeatherFParser {

	private final String ns = null;
	String temperature, condition, date, humidity, wind, city, country, statuscode;
	String fDay, fDate, fLow, fHigh, fText;

	// Parse the inputstream as a list of WeatherF Items
	public List<WeatherFItem> parse(InputStream inputStream)
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
	private List<WeatherFItem> readFeed(XmlPullParser xmlPullParser)
			throws XmlPullParserException, IOException {
		xmlPullParser.require(XmlPullParser.START_TAG, null, "rss");
		WeatherFItem weatherfitem;
		List<WeatherFItem> items = new ArrayList<WeatherFItem>();

		while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
			if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = xmlPullParser.getName();

			if (name.equals("yweather:forecast"))
			{
				fDay = getFDay(xmlPullParser);
				fDate = getFDate(xmlPullParser);
				fLow = getFLow(xmlPullParser);
				fHigh = getFHigh(xmlPullParser);
				fText = getFText(xmlPullParser);
				weatherfitem = new WeatherFItem(fDay, fDate, fLow,fHigh, fText);
				items.add(weatherfitem);
			}
//			weatherfitem = new WeatherFItem(fDay, fDate, fLow,fHigh, fText);
//			items.add(weatherfitem);
		}
//		weatherfitem = new WeatherFItem(fDay, fDate, fLow,fHigh, fText);
//		items.add(weatherfitem);
		fDay = null;
		fDate = null;
		fLow = null;
		fHigh = null;
		fText = null;
		return items;
	}
	private String getFDay(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "day");
	}
	private String getFDate(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "date");
	}
	private String getFLow(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "low");
	}
	private String getFHigh(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "high");
	}
	private String getFText(XmlPullParser xmlPullParser)
			throws IOException, XmlPullParserException {
		return xmlPullParser.getAttributeValue(null, "text");
	}
}
