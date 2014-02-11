package dk.znow.wenesto;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {
	
	private final List<NewsItem> items;
	private final Context context;
	//public ImageLoader imageLoader;
	
	public NewsAdapter(Context context, List<NewsItem> items)
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
			convertView = View.inflate(context, R.layout.list_news_item, null);
			
			// Set the item title
			viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
			viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
	        
	        convertView.setTag(viewHolder);			
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.itemTitle.setText(items.get(position).getTitle() + items.get(position).getPubDate());
		//viewHolder.itemImage.setText(items.get(position).getImage());
		
		
		return convertView;
	}
	
	// Holds the item title in a text view
	static class ViewHolder {
        ImageView itemImage;
		TextView itemTitle;
    }
	
}
