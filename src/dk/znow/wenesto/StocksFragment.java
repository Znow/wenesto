package dk.znow.wenesto;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StocksFragment extends Fragment
{
	private ProgressBar progressBar;
	private ListView listView;
	private View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) 
		{
            view = inflater.inflate(R.layout.stocksfragment, container, false);
            
            // Find the progressbar in the view
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            
            // find the list view in the view
            listView = (ListView) view.findViewById(R.id.listView);
            //listView.setOnItemClickListener(this);
            
            // Start the service
            startService();
        } 
		else 
		{
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
		
		return view;
	}
	
	// Start the news service to retrieve the news
		private void startService() 
		{
			// Create a new intent with the activity and the stockservice class
	        Intent intent = new Intent(getActivity(), StockService.class);
	        
	        // parse the news service to the intent
	        intent.putExtra(NewsService.RECEIVER, resultReceiver);
	        getActivity().startService(intent);
	    }

		
	    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) 
	    {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void onReceiveResult(int resultCode, Bundle resultData) 
	        {
	        	// Create a new list of stock items from the result data's Stock Service items
	            List<StockItem> items = (List<StockItem>) resultData.getSerializable(StockService.ITEMS);
	            
	            // IF the items are not null
	            if (items != null) 
	            {
	            	// Create a new Stock Adapter with the activity and the items found
	                StockAdapter adapter = new StockAdapter(getActivity(), items);
	                // Set the adapter to the listview
	                listView.setAdapter(adapter);
	            } 
	            else 
	            {
	                Toast.makeText(getActivity(), "An error occured while downloading the stock rss feed.",
	                Toast.LENGTH_LONG).show();
	            }
	            
	            // Remove the progressbar and show the listview
	            progressBar.setVisibility(View.GONE);
	            listView.setVisibility(View.VISIBLE);
	        }
	    };
	 
	    /*@Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    {
	    	// Creates a new news adapter from the parent
	        StockAdapter adapter = (StockAdapter) parent.getAdapter();
	        
	        // creates a news item from the item clicked in the list, with the position
	        Stockitem item = (Stockitem) adapter.getItem(position);		
	       
	        Intent stockIntent = new Intent();
	        
	        stockIntent.setClass(getActivity(), StockActivity.class);
	        
	        //newsIntent.putExtra("image", item.getImageUrl());
	        newsIntent.putExtra("title", item.getTitle());
	        newsIntent.putExtra("description", Html.fromHtml(item.getDescription()).toString());
	        newsIntent.putExtra("pubDate", item.getPubDate());
	        
	        startActivity(newsIntent);
	    }*/

}
