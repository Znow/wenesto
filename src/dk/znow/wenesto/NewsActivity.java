package dk.znow.wenesto;

import com.androidquery.AQuery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends Activity 
{
	TextView title;
	TextView description;
	AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_item);
		
		//aq = new AQuery(this);
		
		((TextView)findViewById(R.id.newsTitle)).setText(getIntent().getStringExtra("title"));
		((TextView)findViewById(R.id.newsDescription)).setText(getIntent().getStringExtra("description"));
		((TextView)findViewById(R.id.newsPubDate)).setText(getIntent().getStringExtra("pubDate"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

}
