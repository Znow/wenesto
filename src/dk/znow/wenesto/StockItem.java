package dk.znow.wenesto;

public class StockItem {
	private final String title;
	private final String open;
	private final String change;
	
	public StockItem(String title, String open, String change)
	{
		this.title = title;
		this.open = open;
		this.change = change;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getOpen()
	{
		return open;
	}
	
	public String getChange()
	{
		return change;
	}
}
