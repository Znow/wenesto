package dk.znow.wenesto;

public class WeatherItem {
	
	private final String _longLang;
	
	public WeatherItem(String longLang)
	{
		_longLang = longLang;
	}
	
	public String getLongLang()
	{
		return _longLang;
	}

}
