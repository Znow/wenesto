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
		xmlPullParser.require(XmlPullParser.START_TAG, null, "query");
		String woeid = null;
		List<WeatherItem> items = new ArrayList<WeatherItem>();

		while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
			if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = xmlPullParser.getName();

			if (name.equals("woeid")) {
				woeid = readWoeid(xmlPullParser);
			}
			if (woeid != null) {
				WeatherItem item = new WeatherItem(woeid);
				items.add(item);
				woeid = null;
			}
		}

		return items;
	}

	// Read the link tags and return the result
	private String readWoeid(XmlPullParser xmlPullParser)
			throws XmlPullParserException, IOException {
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "woeid");
		String woeid = readText(xmlPullParser);
		xmlPullParser.require(XmlPullParser.END_TAG, ns, "woeid");

		return woeid;
	}

	private String readText(XmlPullParser xmlPullParser) throws IOException,
			XmlPullParserException {
		String result = "";

		if (xmlPullParser.next() == XmlPullParser.TEXT) {
			result = xmlPullParser.getText();
			xmlPullParser.nextTag();
		}

		return result;
	}

}
