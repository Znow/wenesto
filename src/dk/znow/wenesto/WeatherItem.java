package dk.znow.wenesto;

public class WeatherItem {
	
	private final String _temp;
	private final String _condition;
	private final String _date;
	private final String _humidity;
	private final String _wind;
	private final String _location;
	private final String _country;
	
	public WeatherItem(String temp, String condition, String date, String humidity, String wind, String location, String country)
	{
		_temp = temp;
		_condition = condition;
		_date = date;
		_humidity = humidity;
		_wind = wind;
		_location = location;
		_country = country;
	}
	
	public String getTemp()
	{
		return _temp;
	}
	public String getCondition()
	{
		return _condition;
	}
	public String getDate()
	{
		return _date;
	}
	public String getHumidity()
	{
		return _humidity;
	}
	public String getWind()
	{
		return _wind;
	}
	public String getLocation()
	{
		return _location;
	}
	public String getCountry()
	{
		return _country;
	}

}
