package dk.znow.wenesto;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class WoeidParser {
	private final String ns = null;

	// Parse the inputstream as a list of News Items
	public List<WoeidItem> parse(InputStream inputStream)
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
	private List<WoeidItem> readFeed(XmlPullParser xmlPullParser)
			throws XmlPullParserException, IOException {
		xmlPullParser.require(XmlPullParser.START_TAG, null, "rss");
		String woeid = null;
		List<WoeidItem> items = new ArrayList<WoeidItem>();

		while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
			if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = xmlPullParser.getName();

			if (name.equals("woeid")) {
				woeid = readWoeid(xmlPullParser);
			}
			if (woeid != null) {
				WoeidItem item = new WoeidItem(woeid);
				items.add(item);
				woeid = null;
			}
		}

		return items;
	}

	// Read the link tags and return the result
	private String readWoeid(XmlPullParser xmlPullParser)
			throws XmlPullParserException, IOException {
		xmlPullParser.require(XmlPullParser.START_TAG, ns, "link");
		String woeid = readText(xmlPullParser);
		xmlPullParser.require(XmlPullParser.END_TAG, ns, "link");

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
