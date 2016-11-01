package com.sy.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_drawer.R;


public class DrawerManager
{
	private static final DrawerManager instance = new DrawerManager();
	private Activity mActivity;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPageTitles;
    
   
    private DrawerManager()
    {    	
    	
    }

    public static DrawerManager getInstance()
    {
        return instance;
    }
  
    public void Create(final Activity activity, Bundle savedInstanceState, String ...items)
    {
    	mActivity = activity;
    	mTitle = mDrawerTitle = activity.getTitle();
    	mPageTitles = items;
 		mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
 		mDrawerList = (ListView) activity.findViewById(R.id.left_drawer);

 		// set a custom shadow that overlays the main content when the drawer
 		// opens
 		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
 				GravityCompat.START);
 		// set up the drawer's list view with items and click listener
 		mDrawerList.setAdapter(new ArrayAdapter<String>(activity,
 				R.layout.drawer_list_item, mPageTitles));
 		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

 		// enable ActionBar app icon to behave as action to toggle nav drawer
 		activity.getActionBar().setDisplayHomeAsUpEnabled(true);
 		activity.getActionBar().setHomeButtonEnabled(true);

 		// ActionBarDrawerToggle ties together the the proper interactions
 		// between the sliding drawer and the action bar app icon
 		mDrawerToggle = new ActionBarDrawerToggle(activity, /* host Activity */
 		mDrawerLayout, /* DrawerLayout object */
 		R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
 		R.string.drawer_open, /* "open drawer" description for accessibility */
 		R.string.drawer_close /* "close drawer" description for accessibility */
 		) {
 			public void onDrawerClosed(View view) {
 				activity.getActionBar().setTitle(mTitle);
 				activity.invalidateOptionsMenu(); // creates call to
 											// onPrepareOptionsMenu()
 			}

 			public void onDrawerOpened(View drawerView) {
 				activity.getActionBar().setTitle(mDrawerTitle);
 				activity.invalidateOptionsMenu(); // creates call to
 											// onPrepareOptionsMenu()
 			}
 		};
 		mDrawerLayout.setDrawerListener(mDrawerToggle);

 		if (savedInstanceState == null) {
 			selectItem(0);
 		}
    }
    
    /* Called whenever we call invalidateOptionsMenu() */
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return drawerOpen;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		// Handle action buttons
		switch (item.getItemId()) {
		case android.R.id.home:
			FragmentManager fragmentManager = mActivity.getFragmentManager();

			if (fragmentManager.getBackStackEntryCount() > 0) {
				fragmentManager.popBackStack();
				return true;
			}
			if (mDrawerToggle.onOptionsItemSelected(item)) {
				return true;
			}
			return false;			
	
		default:
			return false;
		}
	}
	
    /* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments

		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new SampleFragment("page 1");
			break;
		case 1:
			fragment = new SampleFragment("page 2");
			break;
		case 2:
			fragment = new SampleFragment("page 3");
			break;
		case 3:
			fragment = new SampleFragment("page 4");
			break;
		case 4:
			fragment = new SampleFragment("page 5");
			break;
			
		default:
			fragment = new SampleFragment("page other");
		}

		// can pass param		
		//Bundle args = new Bundle();
		//args.putInt("PARAM", param);
		//fragment.setArguments(args);

		FragmentManager fragmentManager = mActivity.getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mActivity.setTitle(mPageTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void setTitle(CharSequence title)
	{
		mTitle = title;
	}
	
	/**
	 * Fragment that appears in the "content_frame", shows a planet
	 */
	public static class SampleFragment extends Fragment {
		private String mTitle = "";

		public SampleFragment(String title) {
			// Empty constructor required for fragment subclasses
			this.mTitle = title;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.sample_fragment,
					container, false);	
			getActivity().setTitle(this.mTitle);
			setHasOptionsMenu(true);
			
			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			if (this.mTitle.equals("page 1"))
			{
				inflater.inflate(R.menu.sample_menu, menu);
			}else{
				inflater.inflate(R.menu.main, menu);
			}
			super.onCreateOptionsMenu(menu, inflater);
		}
				
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// The action bar home/up action should open or close the drawer.
			// ActionBarDrawerToggle will take care of this.

			// Handle action buttons
			switch (item.getItemId()) 
			{
				case R.id.action_search:
					Toast.makeText(this.getActivity(), "Search", Toast.LENGTH_SHORT).show();
					return true;
				
				default:
					return super.onOptionsItemSelected(item);
			}
		}
		
	}
}
