package dk.znow.wenesto;

public class WeatherItem {
	
	private final String _temp;
	private final String _condition;
	private final String _date;
	private final String _humidity;
	private final String _wind;
	private final String _city;
	private final String _country;
	private final String _statuscode;
	
	public WeatherItem(String temp, String condition, String date, String humidity, String wind, String city, String country, String statuscode)
	{
		_temp = temp;
		_condition = condition;
		_date = date;
		_humidity = humidity;
		_wind = wind;
		_city = city;
		_country = country;
		_statuscode = statuscode;
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
	public String getCity()
	{
		return _city;
	}
	public String getCountry()
	{
		return _country;
	}
	public String getStatusCode()
	{
		return _statuscode;
	}

}
