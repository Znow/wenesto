package dk.znow.wenesto;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StockAdapter extends BaseAdapter {
	
	private final List<StockItem> items;
	private final Context context;
	//public ImageLoader imageLoader;
	
	public StockAdapter(Context context, List<StockItem> items)
	{
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() 
	{
		return items.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return items.get(position);
	}

	@Override
	public long getItemId(int id) 
	{
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder viewHolder = new ViewHolder();
		
		if (convertView == null)
		{
			convertView = View.inflate(context, R.layout.list_stock_item, null);
			
			// Set the item title
			viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
			viewHolder.itemVolume = (TextView) convertView.findViewById(R.id.itemVolume);
			viewHolder.itemOpen = (TextView) convertView.findViewById(R.id.itemOpen);
			viewHolder.itemChange = (TextView) convertView.findViewById(R.id.itemChange);
			viewHolder.itemLastTrade = (TextView) convertView.findViewById(R.id.itemLastTrade);
	        
	        convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.itemTitle.setText(items.get(position).getTitle());
		viewHolder.itemVolume.setText("volume: " + items.get(position).getVolume());
		viewHolder.itemOpen.setText("open: " + items.get(position).getOpen());
		viewHolder.itemChange.setText("change: " + items.get(position).getChange());
		
		if (items.get(position).getChange().startsWith("+"))
		{
			viewHolder.itemChange.setTextColor(Color.GREEN);
		}
		else if (items.get(position).getChange().startsWith("-"))
		{
			viewHolder.itemChange.setTextColor(Color.RED);
		}
		
		
		viewHolder.itemLastTrade.setText("last trade: " + items.get(position).getLastTrade());
		
		return convertView;
	}
	
	// Holds the item title in a text view
	static class ViewHolder {
		TextView itemTitle;
		TextView itemVolume;
		TextView itemOpen;
		TextView itemChange;
		TextView itemLastTrade;
    }
	
}

