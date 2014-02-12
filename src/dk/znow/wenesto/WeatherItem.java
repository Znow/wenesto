package dk.znow.wenesto;

public class WeatherItem {
	
	private final String _day;
	private final String _date;
	private final String _low;
	private final String _high;
	private final String _text;
	
	public WeatherItem(String day, String date, String low, String high, String text)
	{
		_day = day;
		_date = date;
		_low = low;
		_high = high;
		_text = text;
	}
	public WeatherItem(WeatherItem wi)
	{
		_day = wi.getDay();
		_date = wi.getDate();
		_low = wi.getLow();
		_high = wi.getHigh();
		_text = wi.getText();
	}
	
	public String getDay()
	{
		return _day;
	}
	public String getDate()
	{
		return _date;
	}
	public String getLow()
	{
		return _low;
	}
	public String getHigh()
	{
		return _high;
	}
	public String getText()
	{
		return _text;
	}

}
