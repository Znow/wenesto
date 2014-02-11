package dk.znow.wenesto;

import dk.znow.wenesto.adapter.TabsPagerAdapter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements TabListener, LocationListener
{
	
	//Tab titles
	private String[] tabs = {"Weather","News","Stocks"};
	private ViewPager viewPager;
	private TabsPagerAdapter tabsAdapter;
	private ActionBar actionBar;
	public static LocationManager locationManager;
	Location location;
	String provider;
	public static String coords;
	
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
		getCoordinates getCoord = new getCoordinates(this);
		coords = getCoord.getCoords();
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
            addNewsFragment();
        }
	}

	
	private void addNewsFragment() {
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
	public void onTabSelected(Tab tab, android.app.FragmentTransaction arg1) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

}
