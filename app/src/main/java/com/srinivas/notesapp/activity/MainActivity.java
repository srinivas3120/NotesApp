package com.srinivas.notesapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.srinivas.notesapp.R;
import com.srinivas.notesapp.activity.base.BaseActivity;
import com.srinivas.notesapp.fragments.NotesListFragment;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 200;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Handler mDrawerActionHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setUpDrawerView();
        displayView(R.id.nav_my_notes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavigationView.getMenu().findItem(R.id.nav_my_notes).setChecked(true);
    }

    private void initViews() {

        mToolbar = setupToolbar(false);
        if(getSupportActionBar() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private void setUpDrawerView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setMotionEventSplittingEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        displayView(item.getItemId());
        return true;
    }

    private void displayView(int id) {
        Fragment fragment = null;
        String title=null;
        mDrawerLayout.closeDrawer(GravityCompat.START);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (id){
            case R.id.nav_my_notes:
                fragment=getSupportFragmentManager().findFragmentByTag(NotesListFragment.TAG_NOTE_LIST_FRAGMENT);
                title=getString(R.string.my_notes);
                if(fragment == null){
                    fragment=new NotesListFragment();
                }
                fragmentTransaction.replace(R.id.container_body, fragment,NotesListFragment.TAG_NOTE_LIST_FRAGMENT);
                break;
            case R.id.nav_add_tag:
                title=getString(R.string.add_tag);
                mDrawerActionHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, AddTagActivity.class));
                    }
                }, DRAWER_CLOSE_DELAY_MS);

                break;
            case R.id.nav_add_note:
                title=getString(R.string.add_note);
                mDrawerActionHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, CreateNotesActivity.class));
                    }
                }, DRAWER_CLOSE_DELAY_MS);
                break;
            default:
                break;
        }

        if (fragment != null) {
            // set the toolbar title
            setToolbarTitle(title);
            mDrawerActionHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentTransaction.commit();
                }
            }, DRAWER_CLOSE_DELAY_MS);

        }

    }
}
