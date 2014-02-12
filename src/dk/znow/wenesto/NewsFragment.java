package dk.znow.wenesto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsFragment extends Fragment implements OnItemClickListener
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		if (view == null) 
		{
            view = inflater.inflate(R.layout.newsfragment, container, false);
            // Find the progressbar and listview
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            
            // set a click listener
            listView.setOnItemClickListener(this);
            
            // Start hte service
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
		// Create a new intent of the news service class
        Intent intent = new Intent(getActivity(), NewsService.class);
        
        // push the NewsService into the intent
        intent.putExtra(NewsService.RECEIVER, resultReceiver);
        
        getActivity().startService(intent);
    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) 
    {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) 
        {
            List<NewsItem> items = (List<NewsItem>) resultData.getSerializable(NewsService.ITEMS);
            
            // If the items are not null and contains something
            if (items != null) 
            {
            	// Create a news adapter with the activity and the items list
                NewsAdapter adapter = new NewsAdapter(getActivity(), items);
                
                // Set the adapter to the list view
                listView.setAdapter(adapter);
            } 
            else 
            {
                Toast.makeText(getActivity(), "An error occured while downloading the news rss feed.",
                Toast.LENGTH_LONG).show();
            }
            
            // Remove the progressbar
            progressBar.setVisibility(View.GONE);
            
            // Show the listview
            listView.setVisibility(View.VISIBLE);
        }
    };
 
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    {
    	// Creates a new news adapter from the parent
        NewsAdapter adapter = (NewsAdapter) parent.getAdapter();
        
        // creates a news item from the item clicked in the list, with the position
        NewsItem item = (NewsItem) adapter.getItem(position);	
       
        // Create a new intent
        Intent newsIntent = new Intent();
        
        // set the class to our activity class
        newsIntent.setClass(getActivity(), NewsActivity.class);
        
        //newsIntent.putExtra("image", item.getImageUrl());
        newsIntent.putExtra("title", item.getTitle());
        newsIntent.putExtra("description", Html.fromHtml(item.getDescription()).toString());
        newsIntent.putExtra("pubDate", item.getPubDate());
        
        // start the activity with the intent
        startActivity(newsIntent);
    }
}
