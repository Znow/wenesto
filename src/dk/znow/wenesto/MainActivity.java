package dk.znow.wenesto;

import dk.znow.wenesto.adapter.TabsPagerAdapter;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements TabListener
{
	
	//Tab titles
	private String[] tabs = {"Weather","News","Stocks"};
	private ViewPager viewPager;
	private TabsPagerAdapter tabsAdapter;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		actionBar = getActionBar();
		tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(tabsAdapter);
		//actionBar.setHomeButtonEnabled = false;
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//Add tabs
		
		for(String s : tabs)
		{
			actionBar.addTab(actionBar.newTab().setText(s).setTabListener(this));
		}
	
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() 
		{
			
	        @Override
	        public void onPageSelected(int position) {
	            // on changing the page
	            // make respected tab selected
	            actionBar.setSelectedNavigationItem(position);
	        }
	
	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) 
	        {
	        	
	        }
	
	        @Override
	        public void onPageScrollStateChanged(int arg0) 
	        {
	        	
	        }
	    });
		
		if (savedInstanceState == null) {
            addRssFragment();
        }
	}
	
	private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NewsFragment fragment = new NewsFragment();
        transaction.add(R.id.pager, fragment);
        transaction.commit();
    }
	
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
