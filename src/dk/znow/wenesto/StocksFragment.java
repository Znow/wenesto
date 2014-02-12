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
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            //listView.setOnItemClickListener(this);
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
	        Intent intent = new Intent(getActivity(), StockService.class);
	        intent.putExtra(NewsService.RECEIVER, resultReceiver);
	        getActivity().startService(intent);
	    }

		/**
	     * Once the {@link StockService} finishes its task, the result is sent to this ResultReceiver.
	     */
	    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) 
	    {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void onReceiveResult(int resultCode, Bundle resultData) 
	        {
	            List<StockItem> items = (List<StockItem>) resultData.getSerializable(StockService.ITEMS);
	            
	            if (items != null) 
	            {
	                StockAdapter adapter = new StockAdapter(getActivity(), items);
	                listView.setAdapter(adapter);
	            } 
	            else 
	            {
	                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
	                Toast.LENGTH_LONG).show();
	            }
	            
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
