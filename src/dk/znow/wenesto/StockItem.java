package dk.znow.wenesto;

public class StockItem {
	private final String title;
	private final String volume;
	private final String open;
	private final String change;
	private final String lastTrade;
	
	public StockItem(String title, String volume, String open, String change, String lastTrade)
	{
		this.title = title;
		this.volume = volume;
		this.open = open;
		this.change = change;
		this.lastTrade = lastTrade;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getVolume()
	{
		return volume;
	}
	
	public String getOpen()
	{
		return open;
	}
	
	public String getChange()
	{
		return change;
	}
	
	public String getLastTrade()
	{
		return lastTrade;
	}
}
