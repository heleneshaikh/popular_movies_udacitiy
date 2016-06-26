package com.hfad.popularmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Fragment popularFragment;
    String title;
    private static final String TAG = "app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            title = savedInstanceState.getString("title");
            getActionBar().setTitle(title);
        } else {
            popularFragment = new PopularFragment();
            generateTransaction(popularFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_topRated:
                if (isOnline()) {
                    Fragment topRatedFragment = new TopRatedFragment();
                    generateTransaction(topRatedFragment);
                    title = getResources().getString(R.string.topRated);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            case R.id.action_popular:
                if (isOnline()) {
                    popularFragment = new PopularFragment();
                    generateTransaction(popularFragment);
                    title = getResources().getString(R.string.popular);
                    setActionBar(title);
                } else {
                    networkIssue();
                }
                return true;
            case R.id.action_favourites:
                if (isOnline()) {
                    Fragment favouriteFragment = new FavouritesFragment();
                    generateTransaction(favouriteFragment);
                    title = getResources().getString(R.string.favourites);
                    setActionBar(title);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBar(String title) {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager manager = getFragmentManager();
                        Fragment fragment = manager.findFragmentByTag("visible_fragment");
                        if (fragment instanceof TopRatedFragment) {
                            actionBar.setTitle(R.string.topRated);
                        } else if (fragment instanceof PopularFragment) {
                            actionBar.setTitle(R.string.popular);
                        }
                    }
                }
        );
    }

    private void generateTransaction(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.poster_container, fragment, "visible_fragment");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean isOnline() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = con.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void networkIssue() {
        Toast.makeText(this, "Network unavailable", Toast.LENGTH_LONG).show();
    }
}
